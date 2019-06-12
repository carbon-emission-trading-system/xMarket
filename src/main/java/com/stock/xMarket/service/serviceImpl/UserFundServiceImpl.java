
package com.stock.xMarket.service.serviceImpl;

import java.sql.Time;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TransactionOrder;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.redis.RealTime2Redis;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.service.UserFundService;
import com.stock.xMarket.util.OpeningUtil;

@Service
@Transactional
public class UserFundServiceImpl implements UserFundService {

	@Autowired
	private UserFundRepository userFundRepository;


	@Autowired
	private RealTime2Redis realTime2Redis;

	@Autowired
	private RealTime1Redis realTime1Redis;
	
  
	/**
	 * Update user fund by transaction.
	 *
	 * @param transactionOrder the transaction order
	 */
	@Override
	public void updateUserFundByTransaction(TransactionOrder transactionOrder) {;
		// TODO Auto-generated method stub
		int userId=transactionOrder.getOwnerId();

		UserFund userFund=userFundRepository.findByUser_UserId(userId);
		
		if(transactionOrder.getType()==0) {
			
			Double frozenAmount=userFund.getFrozenAmount()-transactionOrder.getActualAmount();
			userFund.setFrozenAmount(frozenAmount);
			userFundRepository.saveAndFlush(userFund);
			
		}else {
			
			Double balance=userFund.getBalance()+transactionOrder.getActualAmount();
			userFund.setFrozenAmount(balance);
			userFundRepository.saveAndFlush(userFund);
			
		}
		
		
	}

	/**
	 * Update user fund by order.
	 *
	 * @param order the order
	 */
	@Override
	public void updateUserFundByOrder(Order order) {
		// TODO Auto-generated method stub
		
		int userId = order.getUser().getUserId();
		int stockId = order.getStock().getStockId();
		Time time = order.getTime();
		
		UserFund userFund=userFundRepository.findByUser_UserId(userId);
		
		double frozenAmount=0;
		
		if(order.getTradeStraregy()>0) {
			if(OpeningUtil.isOpening(time)) {
				double ytdPrice=realTime1Redis.get(String.valueOf(stockId)).getYesterdayClosePrice();
				frozenAmount=ytdPrice*1.10*order.getOrderAmount();
			}else {
				double ytdPrice=realTime1Redis.get(String.valueOf(stockId)).getYesterdayClosePrice();
				frozenAmount=ytdPrice*1.1*order.getOrderAmount();
			}
			
		}else {
			//计算手续费,买的时候更新
			frozenAmount+=order.getOrderPrice()*order.getOrderAmount()*1.0002887;
			frozenAmount+=serviceFaxCaculator(order.getOrderPrice()*order.getOrderAmount());
			
		}
		
			double balance=userFund.getBalance()-frozenAmount;
			frozenAmount+=userFund.getFrozenAmount();
			userFund.setFrozenAmount(frozenAmount);
			userFund.setBalance(balance);
			userFundRepository.saveAndFlush(userFund);
	}
	
		//用于计算手续费的函数
		public double serviceFaxCaculator(double money){
			if(money>166.666){
				return money*0.03;
			}
			return 5;
		}
}
