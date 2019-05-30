package com.stock.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.OrderVO;
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
	
	//根据用户id获取用户当日所有的委托
	@Override
	public List<OrderVO> findByUserId(int userId){
		
		if(orderRedis.hasKey(String.valueOf(userId))){
			//从缓存中获取当日委托
			return orderRedis.get(String.valueOf(userId));
		}else {
			//从DB中获取当日委托列表
			List<OrderVO> list=orderRepository.findByUserId(userId);
			//放入缓存
			//orderRedis.put(String.valueOf(userId), list, 24*60*60);
			return list;
		}
		
    }

	
}
