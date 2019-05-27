package com.stock.xMarket.controller.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.stock.xMarket.model.Order;

@Controller
public class OrderListener  {
    private static final Logger logger = LoggerFactory.getLogger(OrderListener.class);

    @RabbitListener(queues = "${order.queue.name}")
    public void consumeLogInfo(@Payload String order){
        try {
            logger.info("委托单监听器监听到消息: {} ",order);
            
            

        }catch (Exception e){
            logger.error("委托单监听器监听发生异常：{} ",order,e.fillInStackTrace());
        }
    }

}

