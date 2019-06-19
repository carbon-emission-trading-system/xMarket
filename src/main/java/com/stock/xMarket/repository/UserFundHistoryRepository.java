package com.stock.xMarket.repository;

import com.stock.xMarket.model.UserFundHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFundHistoryRepository extends JpaRepository<UserFundRepository, Integer> {
    List<UserFundHistory> findByUserIdOrderByDate(int userId);
}
