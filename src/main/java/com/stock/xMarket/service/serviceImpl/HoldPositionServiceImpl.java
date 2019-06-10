package com.stock.xMarket.service.serviceImpl;


import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.repository.HoldPositionRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.VO.HoldPositionVO;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.TransactionOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class HoldPositionServiceImpl implements HoldPositionService {

	private static Logger LOGGER = LoggerFactory.getLogger(TransactionOrderServiceImpl.class);

	@Autowired
	private HoldPositionRepository holdPositionRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TransactionOrderRepository transactionOrderRepository;
	
	@Autowired
	private RealTime1Redis realTime1Redis;
	
	@Autowired
	private UserFundRepository userFundRepository;

	//更新持仓信息
	@Override
	public void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException {
		// TODO Auto-generated method stub
		int userId=transactionOrder.getOwnerId();

		int stockId=transactionOrder.getStockId();

		//从数据库读取对应数据
		HoldPosition holdPositon = holdPositionRepository.findByUser_UserIdAndStock_StockId(userId,stockId);
		if(holdPositon!=null){
			//计算新的持仓信息
			int positionNumber;
			double costPrice;
			//根据成交单是买还是卖，更新信息
			LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 开始更新持仓信息");
			if(transactionOrder.getType()==1) {
				//计算新的持仓数量和可用数量
				positionNumber = holdPositon.getPositionNumber() - transactionOrder.getExchangeAmount();
				int availableNumber = holdPositon.getAvailableNumber() - transactionOrder.getExchangeAmount();
				//计算成本价
				costPrice = (holdPositon.getCostPrice()*holdPositon.getPositionNumber()-transactionOrder.getActualAmount())/positionNumber;//positionNumber
				if(positionNumber<0||availableNumber<0){
					throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"成交单出现错误！");
				}
				holdPositon.setAvailableNumber(availableNumber);
			}else {
				//计算新的持仓数量和成本价
				positionNumber = holdPositon.getPositionNumber() + transactionOrder.getExchangeAmount();
				costPrice = (holdPositon.getCostPrice()*holdPositon.getPositionNumber()+transactionOrder.getActualAmount())/positionNumber;//positionNumber
			}
			holdPositon.setPositionNumber(positionNumber);
			holdPositon.setCostPrice(costPrice);
			transactionOrder.setStockBalance(positionNumber);
			//将新的数据存入数据库
			holdPositionRepository.saveAndFlush(holdPositon);
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
			holdPositionRepository.saveAndFlush(holdPositon);
			LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 建仓完毕");
		}


	}

	
	
	@Override
	public void updateHoldPositionByOrder(Order order) throws BusinessException {
		// TODO Auto-generated method stub
		int userId=order.getUser().getUserId();

		int stockId=order.getStock().getStockId();
		

		LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 开始更新股票可用余额");
		
		HoldPosition holdPositon=holdPositionRepository.findByUser_UserIdAndStock_StockId(userId,stockId);
		
		if(holdPositon == null){
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"目标持仓信息不存在！");
		}
		
		int availableNumber=holdPositon.getAvailableNumber()-order.getOrderAmount();
		
		holdPositon.setAvailableNumber(availableNumber);
		
		holdPositionRepository.saveAndFlush(holdPositon);

		LOGGER.info("用户id："+userId+"  股票id:"+stockId+" 股票可用余额更新完毕");
	}
	

	//根据用户id，查看他所有持仓股票
	@Override
	public List<HoldPositionVO> findHoldPosition(int userId) {
		List<HoldPosition> list = new ArrayList<>();
		list = holdPositionRepository.findByUser_UserId(userId);
		//用户余额
		double balance = userFundRepository.findByUser_UserId(userId).getBalance();
		//用户持仓股票市值和
		double totalMarketValue =0;
		//判断当前用户是否有持仓股
		if(list!=null) {
			//当用户有持仓股时
			List<HoldPositionVO> holdPositionVOList = new ArrayList<>();
			//持仓股票总市值
			for(HoldPosition h : list) {
				int stockId = h.getStock().getStockId();
				totalMarketValue = realTime1Redis.get(String.valueOf(stockId)).getLastTradePrice() * h.getPositionNumber();
			}
			//计算用户总资产
			double userTotalFund = balance + totalMarketValue;
			
			for(HoldPosition h : list) {
				HoldPositionVO holdPositionVO = new HoldPositionVO();
				int stockId = h.getStock().getStockId();
				double costPrice = h.getCostPrice();//成本价
				int positionNumber = h.getPositionNumber(); 
				double presentPrice = realTime1Redis.get(String.valueOf(stockId)).getLastTradePrice();//现价--也就是市价
				//总盈亏 = 成本价  * 股票余额 - 现价 *股票余额 = （成本价-现价）* 股票余额
				double totalProfitAndLoss = (costPrice-presentPrice)*positionNumber;
				BeanUtils.copyProperties(h, holdPositionVO);
				holdPositionVO.setStockId(stockId);
				holdPositionVO.setStockName(h.getStock().getStockName());
				holdPositionVO.setPresentPrice(presentPrice);
				holdPositionVO.setActualAmount(h.getPositionNumber());
				holdPositionVO.setTotalProfitAndLoss(totalProfitAndLoss);
				//盈亏比例=（ 市价 - 成本价）/成本价
				holdPositionVO.setProfitAndLossRatio( (presentPrice-costPrice)/costPrice );
			    //市值 = 市价*股票余额
				holdPositionVO.setMarketValue( presentPrice * positionNumber );
				//仓位占比 = 市值/总资产-----市值=现价*股票余额
				holdPositionVO.setPositionRatio( presentPrice * positionNumber / userTotalFund );		
				holdPositionVOList.add(holdPositionVO);
			}
			return holdPositionVOList;
		}else {
			return null;
		}
	}
	
	
}
