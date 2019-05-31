package com.stock.xMarket.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.TradeOrder;
import com.stock.xMarket.redis.OrderRedis;
import com.stock.xMarket.repository.OrderRepository;
import com.stock.xMarket.service.OrderService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
    private OrderRepository orderRepository ;
	
	@Autowired
	private OrderRedis orderRedis;
	
	
	
	@Override
	public void updateOrderByTradeOrder(TradeOrder tradeOrder) {
		// TODO Auto-generated method stub
		String buyKey=String.valueOf(tradeOrder.getBuyOrderId());
		String sellKey=String.valueOf(tradeOrder.getSellOrderId());
		Order buyOrder=orderRedis.get(buyKey);
		Order sellOrder=orderRedis.get(sellKey);
		
		if(tradeOrder.getBuyOrderId()==0) {
			
			Order order=new Order();
			BeanUtils.copyProperties(sellOrder, order);
			orderRepository.saveAndFlush(order);
			orderRedis.remove(sellKey);
			
			
		}else if(tradeOrder.getSellOrderId()==0) {
			
			Order order=new Order();
			BeanUtils.copyProperties(buyOrder, order);
			orderRepository.saveAndFlush(order);
			orderRedis.remove(buyKey);
		}else {
		
		int buyExchangeAmount=buyOrder.getExchangeAmount()+tradeOrder.getExchangeAmount();
		double buyExchangeAveragePrice= (buyOrder.getExchangeAmount()*buyOrder.getExchangeAveragePrice()+tradeOrder.getExchangeAmount()*tradeOrder.getTradePrice());
		buyOrder.setExchangeAmount(buyExchangeAmount);
		buyOrder.setExchangeAveragePrice(buyExchangeAveragePrice);
		
		if(buyOrder.getExchangeAmount()==buyOrder.getOrderAmount()) {
			
			Order order=new Order();
			BeanUtils.copyProperties(buyOrder, order);
			orderRepository.saveAndFlush(order);
			orderRedis.remove(buyKey);
		}else {

			orderRedis.put(buyKey, buyOrder, -1);
		}
		
		
		int sellExchangeAmount=sellOrder.getExchangeAmount()+tradeOrder.getExchangeAmount();
		double sellExchangeAveragePrice= (sellOrder.getExchangeAmount()*sellOrder.getExchangeAveragePrice()+tradeOrder.getExchangeAmount()*tradeOrder.getTradePrice());
		sellOrder.setExchangeAmount(sellExchangeAmount);
		sellOrder.setExchangeAveragePrice(sellExchangeAveragePrice);
		orderRedis.put(sellKey, sellOrder, -1);

		if(sellOrder.getExchangeAmount()==sellOrder.getOrderAmount()) {
			
			Order order=new Order();
			BeanUtils.copyProperties(sellOrder, order);
			orderRepository.saveAndFlush(order);
			orderRedis.remove(sellKey);
			
		}else {

			orderRedis.put(sellKey, sellOrder, -1);
		}
		}
	}


	@Override
	public void addOrderToRedis(Order order) {
		// TODO Auto-generated method stub
		
		try {
		String key=String.valueOf(order.getOrderId());
		orderRedis.put(key, order, -1);
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	//根据用户id获取用户当日所有的委托
	@Override
	public List<OrderVO> findByUserId(int userId){
		
		List<OrderVO> orderVOList=new ArrayList<OrderVO>();
		List<OrderVO> orderIdList=orderRepository.findOrderId(userId);			
		for(OrderVO orderId : orderIdList) {
			OrderVO orderVO=new OrderVO();
			Order order=new Order();
			order=orderRedis.get(String.valueOf(orderId.getOrderId()));
			BeanUtils.copyProperties(order,orderVO);
			orderVO.setStockId(order.getStock().getStockId());
			orderVO.setStockName(order.getStock().getStockName());
			orderVOList.add(orderVO);		
		}
		return orderVOList;
		
    }

	
}
