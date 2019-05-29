package com.stock.xMarket.controller.listener;


import java.sql.Time;
import java.text.SimpleDateFormat;

import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.service.OrderService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;



@Controller
public class OrderListener  {
    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @Autowired
    OrderService orderService;
    
    @Autowired
    HoldPositionService holdPositionService;
    
    @Autowired
    UserFundService userFundService;
   
//    @Autowired
//    TimeUtil timeUtil;
    @Autowired
    MarchService marchService;
    
    @RabbitListener(queues = "${order.queue.name}")
    public void consumeOrder(@Payload OrderVO orderVO){
        try {
        	logger.info("委托单监听器监听到消息: {} ",orderVO);
        	
        	
        	orderVO.setLocalTime(new Time(System.currentTimeMillis()));
        	
        	
        	//将委托单添加至Redis
        	orderService.addOrderToRedis(orderVO);
        	
        	//更新持仓股
        	if(orderVO.getType()==0) {
        		holdPositionService.updateHoldPositionByOrder(orderVO);
        	}
        	//更新个人资金
        	userFundService.updateUserFundByOrder(orderVO);
         
        	//匹配
            marchService.march(orderVO);
            

        }catch (Exception e){
            logger.error("委托单监听器监听发生异常：{} ",orderVO,e.fillInStackTrace());
        }
    }

}

