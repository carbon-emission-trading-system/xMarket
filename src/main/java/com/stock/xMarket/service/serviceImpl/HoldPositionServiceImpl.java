package com.stock.xMarket.service.serviceImpl;


import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.repository.HoldPositionRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.VO.OrderVO;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.TransactionOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class HoldPositionServiceImpl implements HoldPositionService {

	@Autowired
	private HoldPositionRepository holdPositonRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StockRepository stockRepository;


	//更新持仓信息
	@Override
	public void updateHoldPosition(TransactionOrder transactionOrder) throws BusinessException {
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
			//将新的数据存入数据库
			holdPositonRepository.saveAndFlush(holdPositon);
		}else{
			//如果持仓信息不存在，可能是用户第一次购买该股票，是建仓。创建一条新的持仓信息插入数据库
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

		}


	}

	//更新股票可用余额
	@Override
	public void updateAvailable(OrderVO orderVO) throws BusinessException {
		// TODO Auto-generated method stub
		int userId=orderVO.getUserId();

		int stockId=orderVO.getStockId();
		
		HoldPosition holdPositon = holdPositonRepository.findByUser_UserIdAndStock_StockId(userId,stockId);
		if(holdPositon == null){
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标持仓信息不存在！");
		}

		//更新信息
		int availableNumber=holdPositon.getAvailableNumber()-orderVO.getExchangeAmount();

		holdPositon.setAvailableNumber(availableNumber);

		holdPositonRepository.saveAndFlush(holdPositon);



	}

//	@Override
//	public void updateHoldPosition(TransactionOrder transactionOrder)
//			throws com.xmarket.order.error.BusinessException {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void updateAvailable(OrderVO orderVO) throws BusinessException {
//		// TODO Auto-generated method stub
//		
//	}

	
	
}
