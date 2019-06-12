package com.stock.xMarket.service.serviceImpl;


import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.Stock;
import com.stock.xMarket.model.User;
import com.stock.xMarket.model.UserFund;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.repository.HoldPositionRepository;
import com.stock.xMarket.repository.StockRepository;
import com.stock.xMarket.repository.TransactionOrderRepository;
import com.stock.xMarket.repository.UserFundRepository;
import com.stock.xMarket.repository.UserRepository;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.model.Order;
import com.stock.xMarket.VO.HoldPositionVO;
import com.stock.xMarket.VO.UserFundVO;
import com.stock.xMarket.model.HoldPosition;
import com.stock.xMarket.model.TransactionOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
	
	private double totalFunds = 0;
	private double holdPosProAndLos = 0;
	private double totalMarketValue = 0;
	
	
	/*	 * 获得的是double类型	 * 保留两位小数        */	
    public double keepDecimal(double num){	
    	if(num==Double.POSITIVE_INFINITY||num==Double.NEGATIVE_INFINITY||Double.isNaN(num))
    		return num;
    	BigDecimal bg = new BigDecimal(num);		
    	double num1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();		
    	return num1;
    }

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
			holdPositon=new HoldPosition();
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
		UserFund userFund = userFundRepository.findByUser_UserId(userId);
		//用户资金余额 = 冻结资金 + 可用资金
		double amountBalance = userFund.getFrozenAmount() + userFund.getBalance();
		
		
		//判断当前用户是否有持仓股
		if(list!=null) {
			//当用户有持仓股时
			List<HoldPositionVO> holdPositionVOList = new ArrayList<>();
			totalFunds = 0;
			holdPosProAndLos = 0;
			totalMarketValue = 0;
			for(HoldPosition h : list) {
				HoldPositionVO holdPositionVO = new HoldPositionVO();
				int stockId = h.getStock().getStockId();
				double costPrice = h.getCostPrice();//成本价
				int positionNumber = h.getPositionNumber(); 
				double presentPrice = realTime1Redis.get(String.valueOf(stockId)).getLastTradePrice();//现价--也就是市价
				//总盈亏 = 成本价  * 股票余额 - 现价 *股票余额 = （成本价-现价）* 股票余额
				double totalProfitAndLoss = (costPrice-presentPrice)*positionNumber;
				//市值 = 现价*股票余额
				double marketValue = presentPrice * positionNumber;
				BeanUtils.copyProperties(h, holdPositionVO);
				holdPositionVO.setStockId(stockId);
				holdPositionVO.setStockName(h.getStock().getStockName());
				holdPositionVO.setPresentPrice(keepDecimal(presentPrice));
				holdPositionVO.setTotalProfitAndLoss(keepDecimal(totalProfitAndLoss));
				holdPositionVO.setFrozenNumber(h.getPositionNumber() - h.getAvailableNumber());
				//盈亏比例=（ 市价 - 成本价）/成本价
				holdPositionVO.setProfitAndLossRatio(keepDecimal( (presentPrice-costPrice)/costPrice*100 )); //单位应是%
			    //市值 = 市价*股票余额
				holdPositionVO.setMarketValue(keepDecimal(marketValue));
				//仓位占比 = 市值/总资产
				holdPositionVO.setPositionRatio(keepDecimal( marketValue / totalFunds * 100)); //单位应是%		
				holdPositionVOList.add(holdPositionVO);
				
				
				holdPosProAndLos += totalProfitAndLoss; //为计算用户资产信息中的持仓盈亏做服务
				totalMarketValue += marketValue; //为计算用户资产信息中的总市值做服务
			}
			
			//计算用户总资产
			totalFunds = amountBalance + totalMarketValue;
			return holdPositionVOList;
		}else {
			totalFunds = amountBalance;
			return null;
		}
		
	}
	
	//根据用户id，计算用户资产信息
	@Override
	public UserFundVO getFunds(int userId) {
		UserFundVO userFundVO = new UserFundVO();
		UserFund userFund = userFundRepository.findByUser_UserId(userId);
		
		userFundVO.setTotalFunds(keepDecimal(totalFunds));//总资产
		userFundVO.setHoldPosProAndLos(keepDecimal(holdPosProAndLos));//持仓盈亏
		userFundVO.setBalance(keepDecimal(userFund.getBalance()));//可用资金
		userFundVO.setTotalMarketValue(keepDecimal(totalMarketValue));//总市值
		//userFundVO.setTodayProAndLos();//当日盈亏
		userFundVO.setFrozenAmount(keepDecimal(userFund.getFrozenAmount()));//冻结资金
		return userFundVO;
	}
	
	
}
