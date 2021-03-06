package com.xMarket.controller;


import static com.xMarket.response.CommonReturnType.*;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.xMarket.VO.OrderVO;
import com.xMarket.VO.StockTradeVO;
import com.xMarket.error.BusinessException;
import com.xMarket.error.EmBusinessError;
import com.xMarket.model.HoldPosition;
import com.xMarket.model.Order;
import com.xMarket.model.RealTime1;
import com.xMarket.model.Stock;
import com.xMarket.model.User;
import com.xMarket.model.UserFund;
import com.xMarket.redis.CallOrderRedis;
import com.xMarket.redis.RealTime1Redis;
import com.xMarket.repository.HoldPositionRepository;
import com.xMarket.repository.OrderRepository;
import com.xMarket.repository.StockRepository;
import com.xMarket.repository.UserFundRepository;
import com.xMarket.repository.UserRepository;
import com.xMarket.response.CommonReturnType;
import com.xMarket.service.HoldPositionService;
import com.xMarket.service.OrderService;
import com.xMarket.service.UserFundService;
import com.xMarket.util.OpeningUtil;

@RestController
public class OrderController extends BaseApiController{

	@Autowired
    private OrderService orderService;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	

	/** The user repository. */
	@Autowired
	UserRepository userRepository;
	

	/** The stock repository. */
	@Autowired
	private StockRepository stockRepository;
	
	/** The hold position service. */
	@Autowired
	HoldPositionService holdPositionService;

	/** The user fund service. */
	@Autowired
	UserFundService userFundService;

	@Autowired
	private CallOrderRedis callOrderRedis;
	
	@Autowired
	private OrderRepository orderRepository;
	
	 @Autowired
	    private RealTime1Redis realTime1Redis;

	    @Autowired
	    private UserFundRepository userFundRepository;


	    @Autowired
	    private HoldPositionRepository holdPositionRepository;
	
	
	final static Logger logger=LoggerFactory.getLogger(OrderController.class);
	 
	 
	@RequestMapping(value = "/buyOrSale")
	public CommonReturnType buyOrSale(@ModelAttribute(value = "SentstockTrading")OrderVO orderVO,HttpSession session, String validateCode,HttpServletResponse response) throws BusinessException{
		
		orderService.buyOrSale(orderVO);

		return success();

	}
	
	
	
	
	
	//找到用户的所有今日委托单信息
    @RequestMapping(value = "/todayOrder", method = RequestMethod.GET)
    public CommonReturnType findAllTodayOrder(@RequestParam("userId") int id) {
    	
    	logger.info("传进来的用户ownerId："+id);
    	List<Order> list=orderService.findByUserId(id);
    	List<OrderVO> userVOList=new ArrayList<>();
    	
    	for(Order order : list) {
    		OrderVO orderVO=new OrderVO();
    		BeanUtils.copyProperties(order, orderVO);
    		orderVO.setStockId(order.getStock().getStockId());
			orderVO.setStockName(order.getStock().getStockName());
    		userVOList.add(orderVO);
    	}
    	
    	
    	
    	
    	logger.info("传出去的结果："+userVOList);
    	return success(userVOList);
    }

	//撤单
    @RequestMapping(value = "/cancelOrder", method = RequestMethod.POST)
    public CommonReturnType cancelOrder(@RequestParam("orderId") long orderId) throws BusinessException {
    	
    	logger.info("传进来的orderId："+orderId);
    
    	orderService.sendCancelOrder(orderId);
    
    	return success();
    }

    @RequestMapping(value = "/QueryStockInformation", method = RequestMethod.GET)
    public CommonReturnType QueryStockInformation(@RequestParam(name = "stockId")String stockId, @RequestParam(name = "userId")int userId) throws BusinessException {
        logger.info("后端接收到查询请求"+"查询股票"+stockId+"查询用户"+userId);
        RealTime1 realTime1 = realTime1Redis.get(String.valueOf(stockId));
        if (realTime1 == null){
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标股票的实时信息不存在！");
        }
        UserFund userFund = userFundRepository.findByUser_UserId(userId);
        if (userFund == null){
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"执行交易的用户不存在！");
        }
        Stock stock = stockRepository.findByStockId(stockId);//stockRepository.findById(stockId).get()
        if (stock == null){
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标股票不存在！");
        }
        HoldPosition holdPosition = holdPositionRepository.findByUser_UserIdAndStock_StockId(userId,stockId);


        return CommonReturnType.success(orderService.createStockTradeVO(userFund,realTime1,stock,holdPosition));
    }




    

}
