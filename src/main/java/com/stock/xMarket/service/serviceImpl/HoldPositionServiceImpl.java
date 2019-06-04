package com.stock.xMarket.service.serviceImpl;


import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.repository.HoldPositionRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.TransactionOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HoldPositionServiceImpl implements HoldPositionService {

	private static Logger LOGGER = LoggerFactory.getLogger(TransactionOrderServiceImpl.class);

	@Autowired
	private HoldPositionRepository holdPositonRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TransactionOrderRepository transactionOrderRepository;

	//更新持仓信息
	@Override
	public void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException {
		// TODO Auto-generated method stub
		int userId=transactionOrder.getOwnerId();

		int stockId=transactionOrder.getStockId();

		//从数据库读取对应数据
		HoldPosition holdPositon = holdPositonRepository.findByUser_UserIdAndStock_StockId(userId,stockId);
		if(holdPositon!=null){
			//计算新的持仓信息
			int positionNumber;
			double costPrice;
			//根据成交单是买还是卖，更新信息
			LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 开始更新持仓信息");
			if(transactionOrder.isPoint()) {
				//计算新的持仓数量和可用数量
				positionNumber = holdPositon.getPositionNumber() - transactionOrder.getExchangeAmount();
				int availableNumber = holdPositon.getAvailableNumber() - transactionOrder.getExchangeAmount();
				//计算成本价
				costPrice = (holdPositon.getCostPrice()*holdPositon.getPositionNumber()-transactionOrder.getActualAmount());//positionNumber
				if(positionNumber<0||availableNumber<0){
					throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"成交单出现错误！");
				}
				holdPositon.setAvailableNumber(availableNumber);
			}else {
				//计算新的持仓数量和成本价
				positionNumber = holdPositon.getPositionNumber() + transactionOrder.getExchangeAmount();
				costPrice = (holdPositon.getCostPrice()*holdPositon.getPositionNumber()+transactionOrder.getActualAmount());//positionNumber
			}
			holdPositon.setPositionNumber(positionNumber);
			holdPositon.setCostPrice(costPrice);
			transactionOrder.setStockBalance(positionNumber);
			//将新的数据存入数据库
			holdPositonRepository.saveAndFlush(holdPositon);
			transactionOrderRepository.saveAndFlush(transactionOrder);
			LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 持仓信息更新完毕");
		}else{
			//如果持仓信息不存在，可能是用户第一次购买该股票，是建仓。创建一条新的持仓信息插入数据库
			LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 该用户为第一次购买此股票，开始建仓");
			User user = userRepository.findById(userId).get();
			if(user == null){
				throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标用户不存在！");
			}
			holdPositon.setUser(user);

			Stock stock = stockRepository.findById(stockId).get();
			if(stock == null){
				throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标股票不存在！");
			}
			holdPositon.setStock(stock);
			holdPositon.setCostPrice(transactionOrder.getActualAmount()/transactionOrder.getExchangeAmount());
			holdPositon.setOpeningTime(transactionOrder.getDate());
			holdPositon.setPositionNumber(transactionOrder.getExchangeAmount());
			holdPositon.setAvailableNumber(0);

			//存入数据库
			holdPositonRepository.saveAndFlush(holdPositon);
			LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 建仓完毕");
		}


	}

	
	
	@Override
	public void updateHoldPositionByOrder(Order order) throws BusinessException {
		// TODO Auto-generated method stub
		int userId=order.getUser().getUserId();

		int stockId=order.getStock().getStockId();
		

		LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 开始更新股票可用余额");
		
		HoldPosition holdPositon=holdPositonRepository.findByUser_UserIdAndStock_StockId(userId,stockId);
		
		if(holdPositon == null){
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标持仓信息不存在！");
		}
		
		int availableNumber=holdPositon.getAvailableNumber()-order.getOrderAmount();
		
		holdPositon.setAvailableNumber(availableNumber);
		
		holdPositonRepository.saveAndFlush(holdPositon);

		LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 股票可用余额更新完毕");
	}
	



	
	
}
