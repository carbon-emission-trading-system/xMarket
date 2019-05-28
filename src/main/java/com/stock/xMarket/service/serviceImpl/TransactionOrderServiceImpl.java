package com.stock.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.redis.TransactionRedis;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.TransactionOrderProjection;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.service.TransactionOrderService;

@Service
@Transactional
public class TransactionOrderServiceImpl implements TransactionOrderService {

	@Autowired
    private TransactionOrderRepository transactionOrderRepository ;

	@Autowired
	private TransactionRedis transactionRedis;


	@Override
	public List<TransactionOrderProjection> findByOwnerId(int ownerId){
		
		return transactionOrderRepository.findByOwnerId(ownerId);
    }


	//将交易单转化为成交单
	@Override
	public void transaction(TradeOrder tradeOrder) throws BusinessException {

		//如果买卖标识位都为false，则抛出异常
		if(!tradeOrder.isSellPoint() && !tradeOrder.isBuyPoint()){
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
		}
		TransactionOrder buyOrder = createBuyTransactionOrder(tradeOrder);
		TransactionOrder sellOrder = createSellTransactionOrder(tradeOrder);

		//如果卖方标识位为false，将卖方成交单放入redis；反之则放入数据库
		//redis中查重
		TransactionOrder redisOrder =  transactionRedis.get(sellOrder.getOrderId()+"");
		if(redisOrder!=null){
			sellOrder.setExchangeAmount(sellOrder.getExchangeAmount()+redisOrder.getExchangeAmount());
			sellOrder.setTotalExchangeMoney(sellOrder.getTotalExchangeMoney()+redisOrder.getTotalExchangeMoney());
		}
		if(!tradeOrder.isSellPoint()){

			transactionRedis.put(sellOrder.getOrderId()+"",sellOrder,-1);
		}else {
			//放入数据库前先计算服务费
			sellOrder.setServiceTax(sellOrder.getTotalExchangeMoney()*0.0102687+serviceFaxCaculator(sellOrder.getTotalExchangeMoney()));

			//存入数据库
			transactionOrderRepository.saveAndFlush(sellOrder);
		}

		//如果买方标识位为false，则将买方成交单放入redis；反之则放入数据库
		//redis中查重
		redisOrder =  transactionRedis.get(buyOrder.getOrderId()+"");
		if(redisOrder!=null){
			buyOrder.setExchangeAmount(buyOrder.getExchangeAmount()+redisOrder.getExchangeAmount());
			buyOrder.setTotalExchangeMoney(buyOrder.getTotalExchangeMoney()+redisOrder.getTotalExchangeMoney());
		}
		if(tradeOrder.isBuyPoint()){
			transactionRedis.put(buyOrder.getOrderId()+"",buyOrder,-1);
		}else {
			//计算服务费
			buyOrder.setServiceTax(buyOrder.getTotalExchangeMoney()*0.0002687+serviceFaxCaculator(buyOrder.getTotalExchangeMoney()));

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
