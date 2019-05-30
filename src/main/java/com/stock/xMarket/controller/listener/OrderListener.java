package com.stock.xMarket.controller.listener;


import java.sql.Time;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.service.MarchService;
import com.stock.xMarket.service.OrderService;
import com.stock.xMarket.service.UserFundService;

@Controller
public class OrderListener  {
    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @Autowired
    OrderService orderService;
    
    @Autowired
    HoldPositionService holdPositionService;
    
    @Autowired
    UserFundService userFundService;
    
	@Autowired
	UserRepository userRepository;
	

	@Autowired
	private StockRepository stockRepository;

    @Autowired
    MarchService marchService;
    
    @RabbitListener(queues = "${order.queue.name}")
    public void consumeOrder(String str){
        try {
        	logger.info("委托单监听器监听到消息: {} ",str);
        	
        	OrderVO orderVO=JSON.parseObject(str, OrderVO.class);
        	Order order=new Order();
        	try {
        		User user = userRepository.findById(orderVO.getUserId()).get();
        		order.setUser(user);
        	}catch (IllegalArgumentException e) {
				// TODO: handle exception
        		throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标用户不存在！");
			}
		

        	try {
			Stock stock = stockRepository.findById(orderVO.getStockId()).get();
			order.setStock(stock);
        	}catch (IllegalArgumentException e) {
				// TODO: handle exception
        		throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标股票不存在！");
			}
		
        	order.setLocalTime(new Time(System.currentTimeMillis()));
        	
        	BeanUtils.copyProperties(orderVO, order);
        	
        	
        	//将委托单添加至Redis
        	orderService.addOrderToRedis(order);
        	
        	//更新持仓股
        	if(orderVO.getType()==0) {
        		holdPositionService.updateHoldPositionByOrder(order);;
        	}
        	//更新个人资金
        	userFundService.updateUserFundByOrder(order);
         
        	//匹配
            marchService.march(order);
            

        }catch (Exception e){
            logger.error("委托单监听器监听发生异常：{} ",str,e.fillInStackTrace());
        }
    }

}

