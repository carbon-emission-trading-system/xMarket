package com.stock.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.redis.TransactionRedis;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.TransactionOrderVO;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.service.TransactionOrderService;

@Service
@Transactional
public class TransactionOrderServiceImpl implements TransactionOrderService {

	@Autowired
    private TransactionOrderRepository transactionOrderRepository ;

	@Autowired
	private TransactionRedis transactionRedis;

	//返回全部历史成交单
	@Override
	public List<TransactionOrderVO> findByOwnerId(int ownerId){
		
		return transactionOrderRepository.findByOwnerId(ownerId);
    }


	//将交易单转化为成交单
	@Override
	public void transaction(TradeOrder tradeOrder) throws BusinessException {

		//计算总成交金额
		tradeOrder.setTotalExchangeMoney();
		//如果买卖标识位都为false，则抛出异常
		if(!tradeOrder.isSellPoint() && !tradeOrder.isBuyPoint()){
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
		}
		TransactionOrder buyOrder = createBuyTransactionOrder(tradeOrder);
		TransactionOrder sellOrder = createSellTransactionOrder(tradeOrder);

		//如果卖方标识位为false，将卖方成交单放入redis；反之则放入数据库
		//redis中查重
		TransactionOrder redisOrder =  transactionRedis.get(String.valueOf(sellOrder.getOrderId()));
		if(redisOrder!=null){
			sellOrder.setExchangeAmount(sellOrder.getExchangeAmount()+redisOrder.getExchangeAmount());
			sellOrder.setTotalExchangeMoney(sellOrder.getTotalExchangeMoney()+redisOrder.getTotalExchangeMoney());
		}


		if(!tradeOrder.isSellPoint()){
			//存入redis
			transactionRedis.put(String.valueOf(sellOrder.getOrderId()),sellOrder,-1);
		}else {
			//清除redis中的数据
			if(redisOrder!=null){
				transactionRedis.remove(String.valueOf(sellOrder.getOrderId()));
			}

			//放入数据库前先计算服务费
			sellOrder.setStampTax(sellOrder.getTotalExchangeMoney()*0.01);
			sellOrder.setOtherFee(sellOrder.getExchangeAmount()*0.0002887);
			sellOrder.setServiceTax(serviceFaxCaculator(sellOrder.getTotalExchangeMoney()));

			//存入数据库
			transactionOrderRepository.saveAndFlush(sellOrder);
		}

		//如果买方标识位为false，则将买方成交单放入redis；反之则放入数据库
		//redis中查重
		redisOrder =  transactionRedis.get(String.valueOf(buyOrder.getOrderId()));
		if(redisOrder!=null){
			buyOrder.setExchangeAmount(buyOrder.getExchangeAmount()+redisOrder.getExchangeAmount());
			buyOrder.setTotalExchangeMoney(buyOrder.getTotalExchangeMoney()+redisOrder.getTotalExchangeMoney());
		}
		//判断
		if(!tradeOrder.isBuyPoint()){
			transactionRedis.put(String.valueOf(buyOrder.getOrderId()),buyOrder,-1);
		}else {
			//清除redis中的数据
			if(redisOrder!=null){
				transactionRedis.remove(String.valueOf(buyOrder.getOrderId()));
			}

			//计算服务费
			buyOrder.setStampTax(0);
			buyOrder.setOtherFee(buyOrder.getExchangeAmount()*0.0002887);
			buyOrder.setServiceTax(serviceFaxCaculator(buyOrder.getTotalExchangeMoney()));

			//存入数据库
			transactionOrderRepository.saveAndFlush(buyOrder);
		}




	}

	//用于生成卖方成交单的函数
	public TransactionOrder createSellTransactionOrder(TradeOrder tradeOrder){
		TransactionOrder transactionOrder = new TransactionOrder();
		BeanUtils.copyProperties(tradeOrder,transactionOrder);

		//插入部分属性：买卖标识符、委托单id、拥有者id
		transactionOrder.setPoint(false);
		transactionOrder.setOrderId(tradeOrder.getSellOrderId());
		transactionOrder.setOwnerId(tradeOrder.getSellId());

		return transactionOrder;
	}


	//用于生成买方成交单的函数
	public TransactionOrder createBuyTransactionOrder(TradeOrder tradeOrder){
		TransactionOrder transactionOrder = new TransactionOrder();
		BeanUtils.copyProperties(tradeOrder,transactionOrder);

		//插入部分属性：买卖标识符、委托单id、拥有者id
		transactionOrder.setPoint(true);
		transactionOrder.setOrderId(tradeOrder.getBuyOrderId());
		transactionOrder.setOwnerId(tradeOrder.getBuyId());

		return transactionOrder;
	}


	//用于计算手续费的函数
	public double serviceFaxCaculator(double money){
		if(money>166.666){
			return money*0.03;
		}
		return 5;
	}
}
