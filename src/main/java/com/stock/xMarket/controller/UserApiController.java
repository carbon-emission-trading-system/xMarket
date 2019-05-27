package com.stock.xMarket.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
import com.stock.xMarket.result.Result;
import com.stock.xMarket.result.ResultCode;
import com.stock.xMarket.service.UserService;
import com.stock.xMarket.util.*;





@RestController
public class UserApiController extends BaseApiController {

	private static Logger log = LoggerFactory.getLogger(UserApiController.class);

	@Autowired
	public UserService userService;

	@RequestMapping(value = "/login")
	public Result<Object> login(@ModelAttribute(value = "user")User user,HttpSession session, String validateCode,HttpServletResponse response) {
	
		//验证码
		String sessionCode = (String) session.getAttribute("code");
		log.info(validateCode);
		log.info(sessionCode);
		if(!StringUtils.equalsIgnoreCase(validateCode, sessionCode)){
			return Result.failure();
		}

		UserVO dbUser = userService.getUser(user.getUsername());

		if (dbUser != null) {
			if (dbUser.getLoginPassword().equals(MD5Util.inputToDb(user.getLoginPassword()))) {
			
				//保存用户登陆状态
				String token = UUIDUtil.getUUID();
				userService.saveUserToRedisByToken(dbUser, token);
				Cookie cookie = new Cookie("token", token);
				cookie.setMaxAge(3600);
				cookie.setPath("/");
				response.addCookie(cookie);

				return Result.success(); // Result.success(); 200, "success"
			} else {
				return Result.failure(ResultCode.USER_LOGIN_ERROR);
			}
		} else {
			return Result.failure(ResultCode.USER_LOGIN_ERROR);
		}

	}

	@RequestMapping(value = "/validateCode")
	public String validate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("image/jpeg");
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
	public Result<Object> register(@ModelAttribute(value = "user") @Valid User user, BindingResult bindingResult,
			HttpSession session, String mailCode, HttpServletResponse response) {
//		log.info("username=" + user.toString());
//		log.info("username=" + user.getUsername() + ";password=" + user.getLoginPassword());
		if (bindingResult.hasErrors()) {
			log.info("bingdingresult haserrors");
			return Result.failure(); // 500, "error"
		}

		String sessionCode = (String) session.getAttribute("mailcode");
		log.info(mailCode);
		if (!StringUtils.equalsIgnoreCase(mailCode, sessionCode)) {
			return Result.failure();

		}

		String newPassword = MD5Util.inputToDb(user.getLoginPassword());
		user.setLoginPassword(newPassword);
		userService.regist(user);
//		log.info(user.getTransactionPassword());
		return Result.success();

	}

	@RequestMapping("/getMailCode")
	public Result<Object> sendMail(@RequestParam(value = "mailAdress") String mailAdress, HttpServletRequest request) {

		String mailCode = String.valueOf(new Random().nextInt(899999) + 100000);
		String message = "您的验证码为：" + mailCode;
		log.info(mailCode);

		try {
			userService.sendMail(mailAdress, message);
		} catch (Exception e) {

			return Result.failure();
		}
		HttpSession session = request.getSession();
		session.setAttribute("mailcode", mailCode);
		return Result.success();
	}

}