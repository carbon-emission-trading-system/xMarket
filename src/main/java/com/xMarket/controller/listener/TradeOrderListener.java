/*
 * <p>项目名称: xMarket </p> 
 * <p>文件名称: TradeOrderListener.java </p> 
 * <p>描述: [类型描述] </p>
 * <p>创建时间: 2019-5-31 </p>
 * <p>公司信息: ************公司 *********部</p> 
 * @author <a href="mail to: *******@******.com" rel="nofollow">作者</a>
 * @version v1.0
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
package com.xMarket.controller.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.xMarket.error.BusinessException;
import com.xMarket.model.Order;
import com.xMarket.model.TradeOrder;
import com.xMarket.service.OrderService;
import com.xMarket.service.TradeOrderService;
import com.xMarket.service.TransactionOrderService;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving tradeOrder events. The class that is
 * interested in processing a tradeOrder event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addTradeOrderListener<code> method. When the tradeOrder
 * event occurs, that object's appropriate method is invoked.
 *
 * @see TradeOrderEvent
 */
@Controller
public class TradeOrderListener {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(TradeOrderListener.class);

	/** The transaction service. */
	@Autowired
	TransactionOrderService transactionService;

	/** The trade order service. */
	@Autowired
	TradeOrderService tradeOrderService;

	/** The order service. */
	@Autowired
	OrderService orderService;

	/**
	 * Trade order listener.
	 *
	 * @param tradeOrder the trade order
	 */
	@RabbitListener(queues = "tradeOrderQueue")
	public void tradeOrderListener(String str) {

		logger.info("交易单监听器监听到交易单: {} ", str);
		TradeOrder tradeOrder = JSON.parseObject(str, TradeOrder.class);
		try {
			logger.info("交易单监听器监听到交易单: {} ", tradeOrder);

		} catch (Exception e) {
			logger.error("委托单监听器监听发生异常：{} ", e.fillInStackTrace());
		}

		execTradeOrder(tradeOrder);

	}

	private void execTradeOrder(TradeOrder tradeOrder) {
		// TODO Auto-generated method stub
		try {
			// 生成成交单
			transactionService.addTransactionOrder(tradeOrder);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// 保存交易单
			tradeOrderService.saveTradeOrder(tradeOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
