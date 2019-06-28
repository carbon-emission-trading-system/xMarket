package com.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xMarket.model.SelfSelectStock;
import com.xMarket.model.User;

@Repository
public interface SelfSelectStockRepository extends JpaRepository<SelfSelectStock,Integer> {

	List<SelfSelectStock> findByUserId(int userId);

	SelfSelectStock findByUserIdAndStockId(int userId,String stockId);
}
