package com.stock.xMarket.service;

import com.stock.xMarket.VO.UserVO;
import com.stock.xMarket.model.User;

public interface UserService {

	public User regist(User user);

	public UserVO getUser(String username);

	public void saveUserToRedisByToken(UserVO dbUser, String token);

	public Object getUserFromRedisByToken(String token);
	
	public void sendMail(String mailAddress, String message);
}
