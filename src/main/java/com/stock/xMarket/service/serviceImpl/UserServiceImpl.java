package com.stock.xMarket.service.serviceImpl;

import javax.transaction.Transactional;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.UserVO;
import com.stock.xMarket.model.User;
import com.stock.xMarket.redis.UserRedis;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.UserService;






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
	public UserVO getUser(String username) throws BusinessException {
		LOGGER.info("获取"+username+"的信息");
		// TODO Auto-generated method stub
		UserVO userVO = new UserVO();
		User user = userRedis.get("username");
		if(user == null){
			user = userRepository.findByUserName(username);
			if(user != null){
				userRedis.put(user.getUserName(), user, -1);
			}else{
				throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标用户不存在");
			}
		}
		BeanUtils.copyProperties(user, userVO);
		LOGGER.info("获取"+username+"的信息成功");
		return userVO;
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
