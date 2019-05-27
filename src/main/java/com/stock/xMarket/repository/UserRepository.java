package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.User;




@Repository
public interface UserRepository extends JpaRepository<User, String>{

	//public User findByUsernameAndPassword(String username,String Password);
	
	public User findByUsername(String username);
}