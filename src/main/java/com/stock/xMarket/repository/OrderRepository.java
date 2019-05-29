package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.Order;



@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{

}
