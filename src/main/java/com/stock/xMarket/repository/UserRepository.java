package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.User;




@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	//public User findByUsernameAndPassword(String username,String Password);
	
	public User findByUserId(int userId);
	public User findByUserName(String userName);
	public User findByEmail(String mailAdress);
}