
package com.xMarket.service.serviceImpl;

import static com.xMarket.util.FeeUtil.serviceFeeCaculator;
import static com.xMarket.util.FeeUtil.stampTaxCaculator;
import static com.xMarket.util.FeeUtil.transferFeeCaculator;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xMarket.VO.TransactionOrderVO;
import com.xMarket.error.BusinessException;
import com.xMarket.error.EmBusinessError;
import com.xMarket.model.HoldPosition;
import com.xMarket.model.Stock;
import com.xMarket.model.TradeOrder;
import com.xMarket.model.TransactionOrder;
import com.xMarket.redis.TransactionRedis;
import com.xMarket.repository.HoldPositionRepository;
import com.xMarket.repository.StockRepository;
import com.xMarket.repository.TransactionOrderRepository;
import com.xMarket.service.HoldPositionService;
import com.xMarket.service.OrderService;
import com.xMarket.service.TransactionOrderService;
import com.xMarket.service.UserFundService;

@Service
@Transactional
public class TransactionOrderServiceImpl implements TransactionOrderService {

	private static Logger LOGGER = LoggerFactory.getLogger(TransactionOrderServiceImpl.class);

	@Autowired
	private TransactionOrderRepository transactionOrderRepository;

	@Autowired
	private TransactionRedis transactionRedis;

	@Autowired
	private OrderService orderService;

	@Autowired
	private HoldPositionService holdPositionService;

	@Autowired
	UserFundService userFundService;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private HoldPositionRepository holdPositionRepository;

	// 返回全部历史成交单
	@Override
	public List<TransactionOrderVO> findByOwnerId(int ownerId) {

		return transactionOrderRepository.findByOwnerIdOrderByDateDescTimeDesc(ownerId);
	}

	// 将交易单转化为成交单
	@Override
//	@Async
	public void addTransactionOrder(TradeOrder tradeOrder) throws BusinessException {

		// 先判断是否为撤单
		if (tradeOrder.getBuyOrderId().equals("-1") || tradeOrder.getSellOrderId().equals("-1")) {
			// 创建撤单成交单
			TransactionOrder cancelOrder = createRevokeOrder(tradeOrder);
			// 存入数据库
			LOGGER.info("委托单号：" + cancelOrder.getOrderId() + " 的委托买单已被撤单，成交单存入数据库");

			HoldPosition holdPosition = holdPositionRepository
					.findByUser_UserIdAndStock_StockId(cancelOrder.getOwnerId(), cancelOrder.getStockId());
			if (holdPosition != null)
				cancelOrder.setStockBalance(holdPosition.getPositionNumber());
			transactionOrderRepository.saveAndFlush(cancelOrder);

			//在redis中寻找委托单，并结算
			TransactionOrder redisOrder = transactionRedis.get(String.valueOf(cancelOrder.getOrderId()));
			if (redisOrder!=null){
				if (redisOrder.getType() == 1){
					userFundService.updateUserFundByTransaction(redisOrder);
				}else {
					holdPositionService.updateHoldPositionByTransaction(redisOrder);
				}
			}

			//根据撤单内容更新持仓或资金
			orderService.updateOrderBytransactionOrder(cancelOrder);
			if (cancelOrder.getType() == 1) {
				holdPositionService.updateHoldPositionByCancelOrder(cancelOrder);
			} else {
				userFundService.updateUserFundByTransaction(cancelOrder);
			}

			rabbitTemplate.convertAndSend("notifyExchange", String.valueOf(cancelOrder.getOwnerId()),
					"您的委托单已完成！(" + cancelOrder.getStockName() + ")");

			return;
		}

		if (tradeOrder.getExchangeAmount() == 0) {
			LOGGER.info("交易单成交数量为零");
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "交易单成交数量为零！");

		}
		// 计算总成交金额
		tradeOrder.setTotalExchangeMoney();
		// 如果买卖标识位都为false，则抛出异常
		if (!tradeOrder.isSellPoint() && !tradeOrder.isBuyPoint()) {
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "买卖标识位都为false，错误原因未知");
		}
		TransactionOrder buyOrder = createBuyTransactionOrder(tradeOrder);
		TransactionOrder sellOrder = createSellTransactionOrder(tradeOrder);

		// 如果卖方标识位为false，将卖方成交单放入redis；反之则放入数据库
		// redis中查重
		TransactionOrder redisOrder = transactionRedis.get(String.valueOf(sellOrder.getOrderId()));
		if (redisOrder != null) {
			LOGGER.info("在redis中找到卖单，更新信息");
			sellOrder.setExchangeAmount(sellOrder.getExchangeAmount() + redisOrder.getExchangeAmount());
			sellOrder.setTotalExchangeMoney(sellOrder.getTotalExchangeMoney() + redisOrder.getTotalExchangeMoney());
		}

		if (!tradeOrder.isSellPoint()) {
			// 存入redis
			orderService.setState(sellOrder.getOrderId());
			LOGGER.info("委托单号：" + sellOrder.getOrderId() + " 的委托卖单未完成交易，存入redis");
			transactionRedis.put(String.valueOf(sellOrder.getOrderId()), sellOrder, -1);
			
		} else {
			// 清除redis中的数据
			if (redisOrder != null) {
				LOGGER.info("委托单号：" + sellOrder.getOrderId() + " 的委托卖单完成交易，清除redis中数据");
				transactionRedis.remove(String.valueOf(sellOrder.getOrderId()));
			}

			// 放入数据库前先计算服务费、成交价和股票余额
			sellOrder.setStampTax(stampTaxCaculator(sellOrder.getTotalExchangeMoney()));
			sellOrder.setTransferFee(transferFeeCaculator(sellOrder.getExchangeAmount()));
			sellOrder.setServiceTax(serviceFeeCaculator(sellOrder.getTotalExchangeMoney()));
			sellOrder.setActualAmount(sellOrder.getTotalExchangeMoney() - sellOrder.getTransferFee()
					- sellOrder.getServiceTax() - sellOrder.getStampTax());
			sellOrder.setTradePrice(sellOrder.getTotalExchangeMoney() / sellOrder.getExchangeAmount());
			sellOrder.setStockBalance(sellOrder.getExchangeAmount());

			// 存入数据库
			try {
				LOGGER.info("委托单号：" + sellOrder.getOrderId() + " 的委托卖单完成交易，成交单存入数据库");
				LOGGER.info(sellOrder.getOrderId() + " 的内容(sell):" + JSON.toJSONString(sellOrder));

				transactionOrderRepository.saveAndFlush(sellOrder);
			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("委托单存入数据库失败！");
			}

			try {
				// 更新委托单
				orderService.updateOrderBytransactionOrder(sellOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新委托单操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "更新操作失败！");
			}

			try {

				// 更新持仓
				holdPositionService.updateHoldPositionByTransaction(sellOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新持仓操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "更新操作失败！");
			}
			try {

				// 更新个人资金
				userFundService.updateUserFundByTransaction(sellOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新个人资金操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "更新操作失败！");
			}

			rabbitTemplate.convertAndSend("notifyExchange", String.valueOf(sellOrder.getOwnerId()),
					"您的委托单已完成！(" + sellOrder.getStockName() + ")");

		}

		// 如果买方标识位为false，则将买方成交单放入redis；反之则放入数据库
		// redis中查重
		redisOrder = transactionRedis.get(String.valueOf(buyOrder.getOrderId()));
		if (redisOrder != null) {
			LOGGER.info("在redis中找到买单，更新信息");
			buyOrder.setExchangeAmount(buyOrder.getExchangeAmount() + redisOrder.getExchangeAmount());
			buyOrder.setTotalExchangeMoney(buyOrder.getTotalExchangeMoney() + redisOrder.getTotalExchangeMoney());
		}
		// 判断
		if (!tradeOrder.isBuyPoint()) {
			// 存入redis
			orderService.setState(buyOrder.getOrderId());
			LOGGER.info("委托单号：" + buyOrder.getOrderId() + " 的委托买单未完成交易，存入redis");
			transactionRedis.put(String.valueOf(buyOrder.getOrderId()), buyOrder, -1);
		} else {
			// 清除redis中的数据
			if (redisOrder != null) {
				LOGGER.info("委托单号：" + buyOrder.getOrderId() + " 的委托买单完成交易，清除redis中数据");
				transactionRedis.remove(String.valueOf(buyOrder.getOrderId()));
			}

			// 计算服务费、成交价和股票余额
			buyOrder.setStampTax(0);
			buyOrder.setTransferFee(transferFeeCaculator(buyOrder.getExchangeAmount()));
			buyOrder.setServiceTax(serviceFeeCaculator(buyOrder.getTotalExchangeMoney()));
			buyOrder.setActualAmount(
					buyOrder.getTotalExchangeMoney() + buyOrder.getServiceTax() + buyOrder.getTransferFee());
			buyOrder.setTradePrice(buyOrder.getTotalExchangeMoney() / buyOrder.getExchangeAmount());
			buyOrder.setStockBalance(buyOrder.getExchangeAmount());

			// 存入数据库
			try {
				// 存入数据库
				LOGGER.info("委托单号：" + buyOrder.getOrderId() + " 的委托买单完成交易，成交单存入数据库");

				LOGGER.info(sellOrder.getOrderId() + " 的内容(buy):" + JSON.toJSONString(buyOrder));
				transactionOrderRepository.saveAndFlush(buyOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("委托单存入数据库失败！");
			}

			try {
				// 委托单完成，更新委托单
				orderService.updateOrderBytransactionOrder(buyOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新委托单操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "更新操作失败！");
			}

			try {

				// 更新持仓
				holdPositionService.updateHoldPositionByTransaction(buyOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新持仓操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "更新操作失败！");
			}
			try {

				// 更新个人资金
				userFundService.updateUserFundByTransaction(buyOrder);

			} catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新个人资金操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "更新操作失败！");
			}
			rabbitTemplate.convertAndSend("notifyExchange", String.valueOf(buyOrder.getOwnerId()),
					"您的委托单已完成！(" + buyOrder.getStockName() + ")");
		}

	}

	// 用于生成卖方成交单的函数
	public TransactionOrder createSellTransactionOrder(TradeOrder tradeOrder) {
		TransactionOrder transactionOrder = new TransactionOrder();
		BeanUtils.copyProperties(tradeOrder, transactionOrder);

		// 插入部分属性：买卖标识符、委托单id、拥有者id
		Stock stock = stockRepository.findByStockId(tradeOrder.getStockId());
		transactionOrder.setStockName(stock.getStockName());
		transactionOrder.setTradeMarket(stock.getTradeMarket());
		transactionOrder.setType(1);
		transactionOrder.setOrderId(tradeOrder.getSellOrderId());
		transactionOrder.setOwnerId(tradeOrder.getSellerId());

		return transactionOrder;
	}

	// 用于生成买方成交单的函数
	public TransactionOrder createBuyTransactionOrder(TradeOrder tradeOrder) {
		TransactionOrder transactionOrder = new TransactionOrder();
		BeanUtils.copyProperties(tradeOrder, transactionOrder);

		// 插入部分属性：买卖标识符、委托单id、拥有者id
		Stock stock = stockRepository.findByStockId(tradeOrder.getStockId());
		transactionOrder.setStockName(stock.getStockName());
		transactionOrder.setTradeMarket(stock.getTradeMarket());
		transactionOrder.setType(0);
		transactionOrder.setOrderId(tradeOrder.getBuyOrderId());
		transactionOrder.setOwnerId(tradeOrder.getBuyerId());

		return transactionOrder;
	}

	public TransactionOrder createRevokeOrder(TradeOrder tradeOrder) {
		TransactionOrder revokeOrder = new TransactionOrder();
		tradeOrder.setTradePrice(0);
		if (tradeOrder.getBuyOrderId().equals("-1")) {
			LOGGER.info("委托单" + tradeOrder.getSellOrderId() + "撤单");
			revokeOrder = createSellTransactionOrder(tradeOrder);

			revokeOrder.setCancelNumber(tradeOrder.getExchangeAmount());
			revokeOrder.setExchangeAmount(0);

			// redis中查重
			TransactionOrder redisOrder = transactionRedis.get(String.valueOf(revokeOrder.getOrderId()));
			if (redisOrder != null) {
				LOGGER.info("在redis中找到已成交卖单");
				revokeOrder.setExchangeAmount(redisOrder.getExchangeAmount());
				revokeOrder.setTotalExchangeMoney(redisOrder.getTotalExchangeMoney());
				revokeOrder.setTradePrice(revokeOrder.getTotalExchangeMoney() / revokeOrder.getExchangeAmount());
				revokeOrder.setStampTax(stampTaxCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setTransferFee(transferFeeCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setServiceTax(serviceFeeCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setActualAmount(revokeOrder.getTotalExchangeMoney() - revokeOrder.getTransferFee()
						- revokeOrder.getServiceTax() - revokeOrder.getStampTax());
			}
		} else {
			LOGGER.info("委托单" + tradeOrder.getBuyOrderId() + "撤单");
			revokeOrder = createBuyTransactionOrder(tradeOrder);

			revokeOrder.setCancelNumber(tradeOrder.getExchangeAmount());
			revokeOrder.setExchangeAmount(0);
			// redis中查重
			TransactionOrder redisOrder = transactionRedis.get(String.valueOf(revokeOrder.getOrderId()));
			if (redisOrder != null) {
				LOGGER.info("在redis中找到已成交买单");
				revokeOrder.setExchangeAmount(redisOrder.getExchangeAmount());
				revokeOrder.setTotalExchangeMoney(redisOrder.getTotalExchangeMoney());
				revokeOrder.setTradePrice(revokeOrder.getTotalExchangeMoney() / revokeOrder.getExchangeAmount());
				revokeOrder.setStampTax(0);
				revokeOrder.setTransferFee(transferFeeCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setServiceTax(serviceFeeCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setActualAmount(revokeOrder.getTotalExchangeMoney() + revokeOrder.getTransferFee()
						+ revokeOrder.getServiceTax() + revokeOrder.getStampTax());
			}
		}

		return revokeOrder;
	}

	@Override
	public List<TransactionOrder> findByOwnerIdAndDate(int userId) throws BusinessException {
		// TODO Auto-generated method stub

		Date date = new Date(System.currentTimeMillis());
		return transactionOrderRepository.findByOwnerIdAndDateOrderByDateDescTimeDesc(userId, date);

	}
}
