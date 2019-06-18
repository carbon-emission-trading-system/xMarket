package com.stock.xMarket.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.response.CommonReturnType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stock.xMarket.VO.UserVO;
import com.stock.xMarket.model.User;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.UserService;
import com.stock.xMarket.util.*;

import static com.stock.xMarket.response.CommonReturnType.*;


@RestController
public class UserApiController extends BaseApiController {

	private static Logger log = LoggerFactory.getLogger(UserApiController.class);

	@Autowired
	public UserService userService;

	@Autowired
	public UserFundRepository userFundRepository;
	
	@RequestMapping(value = "/login")
	public CommonReturnType login(@ModelAttribute(value = "user")User user, HttpSession session, String validateCode, HttpServletResponse response) throws BusinessException {
	
		//验证码
		String sessionCode = (String) session.getAttribute("code");
		log.info("id为"+user.getUserId()+"的用户输入验证码："+validateCode+"   正确验证码："+sessionCode);
		if(!StringUtils.equalsIgnoreCase(validateCode, sessionCode)){
			throw new BusinessException(EmBusinessError.VALIDATION_ERROR,"验证码错误");
		}

		UserVO dbUser = userService.getUser(user.getUserName());

		if (dbUser != null) {
			if (dbUser.getLoginPassword().equals(MD5Util.inputToDb(user.getLoginPassword()))) {
			
				//保存用户登陆状态
				String token = UUIDUtil.getUUID();
				userService.saveUserToRedisByToken(dbUser, token);
				Cookie cookie = new Cookie("token", token);
				cookie.setMaxAge(3600);
				cookie.setPath("/");
				response.addCookie(cookie);
				log.info("id为"+dbUser.getUserId()+"的用户登录成功");
				return CommonReturnType.success(dbUser.getUserId()); // Result.success(); 200, "success"
			} else {
				throw new BusinessException(EmBusinessError.VALIDATION_ERROR,"密码错误");
			}
		} else {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"用户不存在");
		}

	}

	@RequestMapping(value = "/validateCode")
	public String validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//response.setContentType("image/jpeg");
		// 禁止图像缓存
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);// 在代理服务器端防止缓冲

		HttpSession session = request.getSession();
		ValidateCode validateCode = new ValidateCode(120, 30, 4, 100);

		session.setAttribute("code", validateCode.getCode());
		validateCode.write(response.getOutputStream());
		return null;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public CommonReturnType register(@ModelAttribute(value = "user") @Valid User user,
			HttpSession session, String mailCode, HttpServletResponse response) throws BusinessException {
//		log.info("username=" + user.toString());
//		log.info("username=" + user.getUsername() + ";password=" + user.getLoginPassword());

		String sessionCode = (String) session.getAttribute("mailcode");
		if (!StringUtils.equalsIgnoreCase(mailCode, sessionCode)) {
			throw new BusinessException(EmBusinessError.VALIDATION_ERROR,"验证码错误");

		}

		String newPassword = MD5Util.inputToDb(user.getLoginPassword());
		user.setLoginPassword(newPassword);
		userService.regist(user);
//		log.info(user.getTransactionPassword());
		log.info("用户注册成功,用户名为："+user.getUserName());
		UserFund userFund=new UserFund(user);
		userFundRepository.saveAndFlush(userFund);
		return CommonReturnType.success();

	}

	@RequestMapping("/getMailCode")
	public CommonReturnType sendMail(@RequestParam(value = "mailAdress") String mailAdress, HttpServletRequest request) throws BusinessException {

		String mailCode = String.valueOf(new Random().nextInt(899999) + 100000);
		String message = "您的验证码为：" + mailCode;
		log.info("邮箱验证码为："+mailCode);

		try {
			userService.sendMail(mailAdress, message);
		} catch (Exception e) {
			throw new BusinessException(EmBusinessError.VERIFICATION_CODE_FAIL_ERROR);
		}
		HttpSession session = request.getSession();
		session.setAttribute("mailcode", mailCode);
		return CommonReturnType.success();
	}

	@RequestMapping("/determineIfMailExists")
	public CommonReturnType determineIfMailExists(@RequestParam(value = "mailAdress") String mailAdress, HttpServletRequest request) throws BusinessException {

	
		Boolean isExists=userService.isMailExists(mailAdress);
		
		if(isExists) {

			throw new BusinessException(EmBusinessError.EMAIL_EXIST_ERROR);
		}else {

			return CommonReturnType.success();
		}
	}

	@RequestMapping("/determineIfMailExists2")
	public CommonReturnType determineIfMailExists2(@RequestParam(value = "mailAdress") String mailAdress, HttpServletRequest request) throws BusinessException {


		Boolean isExists=userService.isMailExists(mailAdress);

		if(!isExists) {
			return CommonReturnType.success();
		}else {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"该邮箱地址不存在！");
		}
	}

	@RequestMapping("/determineIfUserNameExists")
	public CommonReturnType determineIfUserNameExists(@RequestParam(value = "userName") String userName, HttpServletRequest request) throws BusinessException {

	
		Boolean isExists=userService.isUserExists(userName);
		
		if(isExists) {

			throw new BusinessException(EmBusinessError.USERNAME_EXIST_ERROR);
		}else {
			return CommonReturnType.success();
		}
	}


	@RequestMapping(value = "/changePassword",method = RequestMethod.POST)
	public CommonReturnType changePassword(@RequestParam(value = "oldPassword") String oldPassword,
										   @RequestParam(value = "newPassword") String newPassword,
										   @RequestParam(value = "userName") String userName) throws BusinessException {
		UserVO dbUser = userService.getUser(userName);

		if (dbUser != null) {
			if (dbUser.getLoginPassword().equals(MD5Util.inputToDb(oldPassword))) {
				userService.changePassword(userName,newPassword);
				log.info("id为"+dbUser.getUserId()+"的用户修改密码成功");
				return CommonReturnType.success();
			} else {
				throw new BusinessException(EmBusinessError.VALIDATION_ERROR,"密码错误");
			}
		} else {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"用户不存在");
		}
	}

	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST)
	public CommonReturnType forgetPassword(@RequestParam(value = "newPassword") String newPassword,
										   @RequestParam(value = "mailCode")  String mailCode,
									       @RequestParam(value = "mailAdress") String mailAddress,
									 HttpSession session, HttpServletResponse response) throws BusinessException {
		String sessionCode = (String) session.getAttribute("mailcode");
		if (!StringUtils.equalsIgnoreCase(mailCode, sessionCode)) {
			throw new BusinessException(EmBusinessError.VALIDATION_ERROR, "验证码错误");
		}

		newPassword = MD5Util.inputToDb(newPassword);
		User user = userService.findByEmail(mailAddress);
		user.setLoginPassword(newPassword);
		userService.regist(user);
		return CommonReturnType.success();
	}


}