package com.stock.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.VO.TransactionOrderProjection;
import com.stock.xMarket.model.TransactionOrder;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder,Integer> {

	List<TransactionOrderProjection> findByOwnerId(int ownerId);
	
}
