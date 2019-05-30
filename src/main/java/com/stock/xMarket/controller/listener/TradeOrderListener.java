package com.stock.xMarket.controller.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.xmarket.order.error.BusinessException;
import com.xmarket.order.model.Order;
import com.xmarket.order.model.TradeOrder;
import com.xmarket.order.service.OrderService;
import com.xmarket.order.service.TradeOrderService;
import com.xmarket.order.service.TransactionOrderService;

@Controller
public class TradeOrderListener  {
    private static final Logger logger = LoggerFactory.getLogger(TradeOrderListener.class);

    
    
    
	@Autowired
	TransactionOrderService transactionService;
    
	@Autowired
	TradeOrderService tradeOrderService;
	
	@Autowired
	OrderService orderService;
	
    @RabbitListener(queues = "${tradeOrder.queue.name}")
    public void tradeOrderListener(@Payload TradeOrder tradeOrder){
        try {
        	logger.info("交易单监听器监听到消息: {} ",tradeOrder);
        	
        }catch (Exception e){
            logger.error("委托单监听器监听发生异常：{} ",e.fillInStackTrace());
        }
        try {
			transactionService.addTransactionOrder(tradeOrder);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    	tradeOrderService.saveTradeOrder(tradeOrder);
//    	orderService.updateOrder(tradeOrder);

    }


}
