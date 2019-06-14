
package com.stock.xMarket.service.serviceImpl;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.redis.TransactionRedis;

import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.util.FeeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.TransactionOrderVO;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.service.OrderService;
import com.stock.xMarket.service.TransactionOrderService;
import com.stock.xMarket.service.UserFundService;

import static com.stock.xMarket.util.FeeUtil.otherTaxCaculator;
import static com.stock.xMarket.util.FeeUtil.serviceFeeCaculator;
import static com.stock.xMarket.util.FeeUtil.stampTaxCaculator;

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
	private StockRepository stockRepository;

	//返回全部历史成交单
	@Override
	public List<TransactionOrderVO> findByOwnerId(int ownerId){
		
		return transactionOrderRepository.findByOwnerId(ownerId);
    }


	//将交易单转化为成交单
	@Override
	public void addTransactionOrder(TradeOrder tradeOrder) throws BusinessException {

		//先判断是否为撤单
		if(tradeOrder.getBuyOrderId() == -1 || tradeOrder.getSellOrderId() == -1){
			//创建撤单成交单
			TransactionOrder revokeOrder = createRevokeOrder(tradeOrder);
			//存入数据库
			LOGGER.info("委托单号："+revokeOrder.getOrderId()+" 的委托买单已被撤单，成交单存入数据库");
			
			transactionOrderRepository.saveAndFlush(revokeOrder);
			
			orderService.updateOrderBytransactionOrder(revokeOrder);
			
			holdPositionService.updateHoldPositionByTransaction(revokeOrder);
			
			userFundService.updateUserFundByTransaction(revokeOrder);
			
			return;
		}
		
		if(tradeOrder.getExchangeAmount()==0)
		{
			LOGGER.info("交易单成交数量为零");
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"交易单成交数量为零！");
			
		}
		//计算总成交金额
		tradeOrder.setTotalExchangeMoney();
		//如果买卖标识位都为false，则抛出异常
		if(!tradeOrder.isSellPoint() && !tradeOrder.isBuyPoint()){
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"买卖标识位都为false，错误原因未知");
		}
		TransactionOrder buyOrder = createBuyTransactionOrder(tradeOrder);
		TransactionOrder sellOrder = createSellTransactionOrder(tradeOrder);

		//如果卖方标识位为false，将卖方成交单放入redis；反之则放入数据库
		//redis中查重
		TransactionOrder redisOrder =  transactionRedis.get(String.valueOf(sellOrder.getOrderId()));
		if(redisOrder!=null){
			LOGGER.info("在redis中找到卖单，更新信息");
			sellOrder.setExchangeAmount(sellOrder.getExchangeAmount()+redisOrder.getExchangeAmount());
			sellOrder.setTotalExchangeMoney(sellOrder.getTotalExchangeMoney()+redisOrder.getTotalExchangeMoney());
		}


		if(!tradeOrder.isSellPoint()){
			//存入redis
			LOGGER.info("委托单号："+sellOrder.getOrderId()+" 的委托卖单未完成交易，存入redis");
			transactionRedis.put(String.valueOf(sellOrder.getOrderId()),sellOrder,-1);
		}else {
			//清除redis中的数据
			if(redisOrder!=null){
				LOGGER.info("委托单号："+sellOrder.getOrderId()+" 的委托卖单完成交易，清除redis中数据");
				transactionRedis.remove(String.valueOf(sellOrder.getOrderId()));
			}

			//放入数据库前先计算服务费、成交价和股票余额
			sellOrder.setStampTax(stampTaxCaculator(sellOrder.getTotalExchangeMoney()));
			sellOrder.setOtherFee(otherTaxCaculator(sellOrder.getExchangeAmount()));
			sellOrder.setServiceTax(serviceFeeCaculator(sellOrder.getTotalExchangeMoney()));
			sellOrder.setActualAmount(sellOrder.getTotalExchangeMoney()-sellOrder.getOtherFee()-sellOrder.getServiceTax()-sellOrder.getStampTax());
			sellOrder.setTradePrice(sellOrder.getTotalExchangeMoney()/sellOrder.getExchangeAmount());
			sellOrder.setStockBalance(sellOrder.getExchangeAmount());

			//存入数据库
			try {
			LOGGER.info("委托单号："+sellOrder.getOrderId()+" 的委托卖单完成交易，成交单存入数据库");
			LOGGER.info(sellOrder.getOrderId()+" 的内容(sell):"+JSON.toJSONString(sellOrder));
			
			transactionOrderRepository.saveAndFlush(sellOrder);
			}catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("委托单存入数据库失败！");
			}
			
			try {
			//更新委托单
			orderService.updateOrderBytransactionOrder(sellOrder);
			
			//更新持仓
			holdPositionService.updateHoldPositionByTransaction(sellOrder);
			
			//更新个人资金
			userFundService.updateUserFundByTransaction(sellOrder);
			}catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"更新操作失败！");
			}
			
			
		
		}

		//如果买方标识位为false，则将买方成交单放入redis；反之则放入数据库
		//redis中查重
		redisOrder =  transactionRedis.get(String.valueOf(buyOrder.getOrderId()));
		if(redisOrder!=null){
			LOGGER.info("在redis中找到买单，更新信息");
			buyOrder.setExchangeAmount(buyOrder.getExchangeAmount()+redisOrder.getExchangeAmount());
			buyOrder.setTotalExchangeMoney(buyOrder.getTotalExchangeMoney()+redisOrder.getTotalExchangeMoney());
		}
		//判断
		if(!tradeOrder.isBuyPoint()){
			//存入redis
			LOGGER.info("委托单号："+buyOrder.getOrderId()+" 的委托买单未完成交易，存入redis");
			transactionRedis.put(String.valueOf(buyOrder.getOrderId()),buyOrder,-1);
		}else {
			//清除redis中的数据
			if(redisOrder!=null){
				LOGGER.info("委托单号："+buyOrder.getOrderId()+" 的委托买单完成交易，清除redis中数据");
				transactionRedis.remove(String.valueOf(buyOrder.getOrderId()));
			}

			//计算服务费、成交价和股票余额
			buyOrder.setStampTax(0);
			buyOrder.setOtherFee(otherTaxCaculator(buyOrder.getExchangeAmount()));
			buyOrder.setServiceTax(serviceFeeCaculator(buyOrder.getTotalExchangeMoney()));
			buyOrder.setActualAmount(buyOrder.getTotalExchangeMoney()+buyOrder.getServiceTax()+buyOrder.getOtherFee());
			buyOrder.setTradePrice(buyOrder.getTotalExchangeMoney()/buyOrder.getExchangeAmount());
			buyOrder.setStockBalance(buyOrder.getExchangeAmount());

			
			

			//存入数据库
			try {
				//存入数据库
				LOGGER.info("委托单号："+buyOrder.getOrderId()+" 的委托买单完成交易，成交单存入数据库");

				LOGGER.info(sellOrder.getOrderId()+" 的内容(buy):"+JSON.toJSONString(buyOrder));
				transactionOrderRepository.saveAndFlush(buyOrder);
				
			}catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("委托单存入数据库失败！");
			}
			
			try {
				//委托单完成，更新委托单
				orderService.updateOrderBytransactionOrder(buyOrder);
				//更新持仓
				holdPositionService.updateHoldPositionByTransaction(buyOrder);
				//更新个人资金
				userFundService.updateUserFundByTransaction(buyOrder);
			}catch (Exception e) {
				// TODO: handle exception
				LOGGER.info("更新操作失败！");
				throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"更新操作失败！");
			}
			
			
			
			
			
		
			
			
		}




	}

	//用于生成卖方成交单的函数
	public TransactionOrder createSellTransactionOrder(TradeOrder tradeOrder){
		TransactionOrder transactionOrder = new TransactionOrder();
		BeanUtils.copyProperties(tradeOrder,transactionOrder);

		//插入部分属性：买卖标识符、委托单id、拥有者id
		transactionOrder.setStockName(stockRepository.findByStockId(tradeOrder.getStockId()).getStockName());
		transactionOrder.setType(1);
		transactionOrder.setOrderId(tradeOrder.getSellOrderId());
		transactionOrder.setOwnerId(tradeOrder.getSellerId());

		return transactionOrder;
	}


	//用于生成买方成交单的函数
	public TransactionOrder createBuyTransactionOrder(TradeOrder tradeOrder){
		TransactionOrder transactionOrder = new TransactionOrder();
		BeanUtils.copyProperties(tradeOrder,transactionOrder);

		//插入部分属性：买卖标识符、委托单id、拥有者id
		transactionOrder.setStockName(stockRepository.findByStockId(tradeOrder.getStockId()).getStockName());
		transactionOrder.setType(0);
		transactionOrder.setOrderId(tradeOrder.getBuyOrderId());
		transactionOrder.setOwnerId(tradeOrder.getBuyerId());

		return transactionOrder;
	}


	public TransactionOrder createRevokeOrder(TradeOrder tradeOrder){
		TransactionOrder revokeOrder = new TransactionOrder();
		tradeOrder.setTradePrice(0);
		if(tradeOrder.getBuyOrderId() == -1){
			LOGGER.info("委托单"+tradeOrder.getSellOrderId()+"撤单");
			revokeOrder = createSellTransactionOrder(tradeOrder);

			revokeOrder.setRevokeAmount(tradeOrder.getExchangeAmount());
			revokeOrder.setExchangeAmount(0);

			//redis中查重
			TransactionOrder redisOrder =  transactionRedis.get(String.valueOf(revokeOrder.getOrderId()));
			if(redisOrder!=null){
				LOGGER.info("在redis中找到已成交卖单");
				revokeOrder.setExchangeAmount(redisOrder.getExchangeAmount());
				revokeOrder.setTotalExchangeMoney(redisOrder.getTotalExchangeMoney());
				revokeOrder.setTradePrice(revokeOrder.getTotalExchangeMoney()/revokeOrder.getExchangeAmount());
				revokeOrder.setStampTax(stampTaxCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setOtherFee(otherTaxCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setServiceTax(serviceFeeCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setActualAmount(revokeOrder.getTotalExchangeMoney()-revokeOrder.getOtherFee()-revokeOrder.getServiceTax()-revokeOrder.getStampTax());
			}
		}else{
			LOGGER.info("委托单"+tradeOrder.getBuyOrderId()+"撤单");
			revokeOrder = createBuyTransactionOrder(tradeOrder);

			revokeOrder.setRevokeAmount(tradeOrder.getExchangeAmount());
			revokeOrder.setExchangeAmount(0);
			//redis中查重
			TransactionOrder redisOrder =  transactionRedis.get(String.valueOf(revokeOrder.getOrderId()));
			if(redisOrder!=null){
				LOGGER.info("在redis中找到已成交买单");
				revokeOrder.setExchangeAmount(redisOrder.getExchangeAmount());
				revokeOrder.setTotalExchangeMoney(redisOrder.getTotalExchangeMoney());
				revokeOrder.setTradePrice(revokeOrder.getTotalExchangeMoney()/revokeOrder.getExchangeAmount());
				revokeOrder.setStampTax(0);
				revokeOrder.setOtherFee(otherTaxCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setServiceTax(serviceFeeCaculator(revokeOrder.getTotalExchangeMoney()));
				revokeOrder.setActualAmount(revokeOrder.getTotalExchangeMoney()+revokeOrder.getOtherFee()+revokeOrder.getServiceTax()+revokeOrder.getStampTax());
			}
		}


		return revokeOrder;
	}


	@Override
	public List<TransactionOrder> findByOwnerIdAndDate(int userId) throws BusinessException {
		// TODO Auto-generated method stub
		
		Date date=new Date(System.currentTimeMillis());
		return transactionOrderRepository.findByOwnerIdAndDate(userId, date);
		
	}
}
