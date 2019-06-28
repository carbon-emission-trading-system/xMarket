package com.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xMarket.model.UserFundHistory;

import java.util.List;

@Repository
public interface UserFundHistoryRepository extends JpaRepository<UserFundHistory, Integer> {

    List<UserFundHistory> findByUserIdOrderByDate(int userId);

}
