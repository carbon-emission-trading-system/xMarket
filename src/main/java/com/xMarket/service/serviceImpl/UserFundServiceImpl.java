
package com.xMarket.service.serviceImpl;

import java.sql.Time;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.xMarket.error.BusinessException;
import com.xMarket.error.EmBusinessError;
import com.xMarket.model.Order;
import com.xMarket.model.TransactionOrder;
import com.xMarket.model.UserFund;
import com.xMarket.redis.RealTime1Redis;
import com.xMarket.redis.RealTime2Redis;
import com.xMarket.repository.OrderRepository;
import com.xMarket.repository.UserFundRepository;
import com.xMarket.service.UserFundService;
import com.xMarket.util.FeeUtil;
import com.xMarket.util.OpeningUtil;

@Service
@Transactional
public class UserFundServiceImpl implements UserFundService {

	@Autowired
	private UserFundRepository userFundRepository;

	@Autowired
	private OrderRepository orderRepository;
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
	public void updateUserFundByTransaction(TransactionOrder transactionOrder) {
		// TODO Auto-generated method stub
		int userId = transactionOrder.getOwnerId();

		UserFund userFund = userFundRepository.findByUser_UserId(userId);

		Order order = orderRepository.findById(transactionOrder.getOrderId()).get();

		if (transactionOrder.getType() == 0) {

			double returnAmount = order.getFrozenAmount() - transactionOrder.getActualAmount();

			double frozenAmount = userFund.getFrozenAmount() - order.getFrozenAmount();

			userFund.setFrozenAmount(frozenAmount);

			userFund.setBalance(userFund.getBalance() + returnAmount);

			userFundRepository.saveAndFlush(userFund);

		} else {

			Double balance = userFund.getBalance() + transactionOrder.getActualAmount();

			userFund.setBalance(balance);

			userFundRepository.saveAndFlush(userFund);

		}

	}

	/**
	 * Update user fund by order.
	 *
	 * @param order the order
	 */
	@Override
	@Async
	public void updateUserFundByOrder(Order order) throws BusinessException {
		// TODO Auto-generated method stub

		int userId = order.getUser().getUserId();

		UserFund userFund = userFundRepository.findByUser_UserId(userId);

		double frozenAmount = order.getFrozenAmount();

		double balance = userFund.getBalance() - frozenAmount;
		if (balance < 0) {
			throw new BusinessException(EmBusinessError.FUND_ERROR);
		}
		frozenAmount += userFund.getFrozenAmount();
		userFund.setFrozenAmount(frozenAmount);
		userFund.setBalance(balance);
		userFundRepository.saveAndFlush(userFund);
	}

	@Override
	public double frozenAmountCaculator(Order order) {
		double frozenAmount = 0;
		String stockId = order.getStock().getStockId();
		Time time = order.getTime();
		if (order.getTradeStraregy() > 0) {
			if (OpeningUtil.isOpening(time)) {
				double ytdPrice = realTime1Redis.get(String.valueOf(stockId)).getYesterdayClosePrice();
				frozenAmount = ytdPrice * 1.10 * order.getOrderAmount();
			} else {
				double ytdPrice = realTime1Redis.get(String.valueOf(stockId)).getYesterdayClosePrice();
				frozenAmount = ytdPrice * 1.10 * order.getOrderAmount();
			}

		} else {
			if (order.getType() == 0) {
				// 计算手续费,买的时候更新
				double orderAmount = order.getOrderPrice() * order.getOrderAmount();
				frozenAmount = orderAmount + FeeUtil.transferFeeCaculator(orderAmount);
				frozenAmount += FeeUtil.serviceFeeCaculator(order.getOrderPrice() * order.getOrderAmount());
			}
		}
		return frozenAmount;
	}

}
