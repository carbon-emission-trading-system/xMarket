package com.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xMarket.model.UserFund;




@Repository
public interface UserFundRepository extends JpaRepository<UserFund, Integer>{

	public UserFund findByUser_UserId(int userId);

	
	
	//public User findByUsernameAndPassword(String username,String Password);
	
}