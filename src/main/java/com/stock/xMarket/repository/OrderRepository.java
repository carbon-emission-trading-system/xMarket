package com.stock.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

//	@Query(value="select new com.stock.xMarket.VO.OrderVO(o.date,o.localTime,s.stockId as stockId,s.stockName as stockName,o.type,o.orderAmount,o.exchangeAmount,o.exchangeAveragePrice," + 
//			"o.orderPrice,o.cancelNumber,o.tradeStraregy,o.orderId) "
//			+ "from Order o left join o.stock s left join o.user u where u.userId=:userId")
//	List<Order> findByUserId(Integer userId);
//	
//	@Query(value="select new com.stock.xMarket.VO.OrderVO(o.orderId) from Order o left join o.user u where u.userId=:userId")
//	List<Order> findOrderId(Integer userId);
//	
	

	List<Order> findByUser_UserId(int userId);
	
	
	
}
