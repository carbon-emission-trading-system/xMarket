package com.xMarket.service;

import com.xMarket.VO.UserVO;
import com.xMarket.error.BusinessException;
import com.xMarket.model.User;

public interface UserService {

	public User regist(User user);

	public UserVO getUser(String username) throws BusinessException;

	public void saveUserToRedisByToken(UserVO dbUser, String token);

	public Object getUserFromRedisByToken(String token);
	
	public void sendMail(String mailAddress, String message);

	public Boolean isMailExists(String mailAdress);

	public Boolean isUserExists(String mailAdress);

	void changePassword(String userName,String password) throws BusinessException;

	User findByEmail(String emailAddress);

	User findByUserId(int userId);

	void changeUserName(String userName,int userId);

	void changeMailAddress(String MailAddress,int userId);

	Boolean validateLoginPassword(int userId, String loginPassword);
}
