package com.xMarket.service.serviceImpl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.xMarket.VO.UserVO;
import com.xMarket.error.BusinessException;
import com.xMarket.error.EmBusinessError;
import com.xMarket.model.User;
import com.xMarket.redis.UserRedis;
import com.xMarket.repository.UserRepository;
import com.xMarket.service.UserService;
import com.xMarket.util.MD5Util;






@Service
@Transactional
public class UserServiceImpl implements UserService{

	private static Logger LOGGER = LoggerFactory.getLogger(TransactionOrderServiceImpl.class);

    @Value("${spring.mail.username}")
    private String from;
    
	@Autowired
	public UserRepository userRepository;
	
	@Autowired
	public UserRedis userRedis;
	
	@Autowired
	public JavaMailSenderImpl mailSender;
	
	@Override
	public User regist(User user) {
		// TODO Auto-generated method stub
		return userRepository.saveAndFlush(user);
	}

	@Override
	public User findByUserId(int userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public void changeMailAddress(String MailAddress, int userId) {
		User user = findByUserId(userId);
		user.setEmail(MailAddress);
		regist(user);
	}

	@Override
	public Boolean validateLoginPassword(int userId, String loginPassword) {
		User user = findByUserId(userId);
		if (user.getLoginPassword().equals(MD5Util.inputToDb(loginPassword))){
			return true;
		}else {
			return false;
		}
	}

	@Override
	public void changeUserName(String userName,int userId){
		User user = userRepository.findByUserId(userId);
		user.setUsername(userName);
		userRepository.saveAndFlush(user);
		return;
	}

	@Override
	public User findByEmail(String emailAddress) {
		return userRepository.findByEmail(emailAddress);
	}

	@Override
	public UserVO getUser(String userName) throws BusinessException {
		LOGGER.info("获取"+userName+"的信息");
		// TODO Auto-generated method stub
		UserVO userVO = new UserVO();
		User user = userRepository.findByUserName(userName);
		if(user != null){
			userRedis.put(user.getUserName(), user, -1);
		}else{
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标用户不存在");
		}

		BeanUtils.copyProperties(user, userVO);
		LOGGER.info("获取"+userName+"的信息成功");
		return userVO;
	}

	@Override
	public void changePassword(String userName, String password) throws BusinessException {
		User user = userRepository.findByUserName(userName);
		if(user != null){
			user.setLoginPassword(MD5Util.inputToDb(password));
			userRepository.saveAndFlush(user);
		}else{
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标用户不存在");
		}
	}

	@Override
	public void saveUserToRedisByToken(UserVO dbUser, String token) {
		// TODO Auto-generated method stub
		User user = new User();
		BeanUtils.copyProperties(dbUser, user);
		userRedis.put(token, user, 3600);
	}

	@Override
	public Object getUserFromRedisByToken(String token) {
		// TODO Auto-generated method stub
		return userRedis.get(token);
	}

	@Override
	public void sendMail(String mailAddress, String content) {
		// TODO Auto-generated method stub
		LOGGER.info("开始创建邮件");
		SimpleMailMessage message=new SimpleMailMessage();
		message.setSubject("感谢您使用xMarket！");
        message.setText(content);
		message.setTo(mailAddress);
        message.setFrom(from);
        mailSender.send(message);
		LOGGER.info("邮件发送成功");
	}


	@Override
	public Boolean isMailExists(String mailAdress) {
		// TODO Auto-generated method stub
		
		User user=userRepository.findByEmail(mailAdress);
		
		if(user!=null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public Boolean isUserExists(String userName) {
		// TODO Auto-generated method stub
		User user=userRepository.findByUserName(userName);
		
		if(user!=null) {
			return true;
		}else {
			return false;
		}
		
		
	}

	
	
	

	

}
