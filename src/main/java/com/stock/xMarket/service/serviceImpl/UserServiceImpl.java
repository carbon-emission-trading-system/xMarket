package com.stock.xMarket.service.serviceImpl;

import javax.transaction.Transactional;

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

    @Value("${spring.mail.username}")
    private String from;
    
	@Autowired
	public UserRepository userRpository;
	
	@Autowired
	public UserRedis userRedis;
	
	@Autowired
	public JavaMailSenderImpl mailSender;
	
	@Override
	public User regist(User user) {
		// TODO Auto-generated method stub
		return userRpository.saveAndFlush(user);
	}

	@Override
	public UserVO getUser(String username) {
		// TODO Auto-generated method stub
		UserVO userVO = new UserVO();
		User user = userRedis.get("username");
		if(user == null){
			user = userRpository.findByUsername(username);
			if(user != null){
				userRedis.put(user.getUsername(), user, -1);
			}else{
				return null;
			}
		}
		BeanUtils.copyProperties(user, userVO);
		
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
		SimpleMailMessage message=new SimpleMailMessage();
		message.setSubject("感谢您使用xMarket！");
        message.setText(content);
		message.setTo(mailAddress);
        message.setFrom(from);
        mailSender.send(message);
	}

	
	
	

	

}
