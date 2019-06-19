package com.stock.xMarket.service.serviceImpl;

import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.*;
import com.stock.xMarket.redis.RealTime1Redis;
import com.stock.xMarket.repository.*;
import com.stock.xMarket.service.HoldPositionService;
import com.stock.xMarket.util.DemicalUtil;
import com.stock.xMarket.VO.HoldPositionVO;
import com.stock.xMarket.VO.UserFundVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	@Autowired
	private HistoryHoldPositionRepository historyHoldPositionRepository;

	// 更新持仓信息
	@Override
	public void updateHoldPositionByTransaction(TransactionOrder transactionOrder) throws BusinessException {
		// TODO Auto-generated method stub
		int userId = transactionOrder.getOwnerId();

		String stockId = transactionOrder.getStockId();

		// 从数据库读取对应数据
		HoldPosition holdPosition = holdPositionRepository.findByUser_UserIdAndStock_StockId(userId, stockId);
		if (holdPosition != null) {
			// 计算新的持仓信息
			int positionNumber;
			double costPrice;
			// 根据成交单是买还是卖，更新信息
			LOGGER.info("用户id：" + userId + "  股票id:" + stockId + " 开始更新持仓信息");
			if (transactionOrder.getType() == 1) {

				// 如果是卖
				// 计算新的持仓数量和可用数量
				positionNumber = holdPosition.getPositionNumber() - transactionOrder.getExchangeAmount();
				// int availableNumber = holdPositon.getAvailableNumber() -
				// transactionOrder.getExchangeAmount();
				if (positionNumber > 0) {
					// 计算成本价
					costPrice = (holdPosition.getCostPrice() * holdPosition.getPositionNumber()
							- transactionOrder.getActualAmount()) / positionNumber;// positionNumber
				}else if (positionNumber < 0) {
					throw new BusinessException(EmBusinessError.UNKNOWN_ERROR, "成交单出现错误！");
				}else {
					// 如果剩余数量为0，则清仓
					HistoryHoldPosition historyHoldPosition = new HistoryHoldPosition();
					historyHoldPosition.setStockId(holdPosition.getStock().getStockId());
					historyHoldPosition.setClearPositionDate(transactionOrder.getDate());
					historyHoldPosition.setBuildPositionDate(holdPosition.getOpeningTime());
					// 总盈亏=最后一次到手的钱-成本价*数量，成本价可以为负

					historyHoldPosition.setStockHoldDay(
							(int) (transactionOrder.getDate().getTime() - holdPosition.getOpeningTime().getTime())
									/ (1000 * 60 * 60 * 24));
					historyHoldPosition.setStockName(holdPosition.getStock().getStockName());
					historyHoldPosition.setTotalProfitAndLoss(transactionOrder.getActualAmount()
							- holdPosition.getCostPrice() * transactionOrder.getExchangeAmount());
					historyHoldPosition
							.setProfitAndLossRatio(transactionOrder.getTradePrice() / holdPosition.getCostPrice() - 1);
					historyHoldPosition.setUserId(holdPosition.getUser().getUserId());

					historyHoldPositionRepository.saveAndFlush(historyHoldPosition);
					holdPositionRepository.delete(holdPosition);
					return;
				}
				// holdPositon.setAvailableNumber(availableNumber);
			} else {
				// 如果是买
				// 计算新的持仓数量和成本价
				positionNumber = holdPosition.getPositionNumber() + transactionOrder.getExchangeAmount();
				costPrice = (holdPosition.getCostPrice() * holdPosition.getPositionNumber()
						+ transactionOrder.getActualAmount()) / positionNumber;// positionNumber
			}
			holdPosition.setPositionNumber(positionNumber);
			holdPosition.setCostPrice(costPrice);
			transactionOrder.setStockBalance(positionNumber);
			// 将新的数据存入数据库
			holdPositionRepository.saveAndFlush(holdPosition);
			transactionOrderRepository.saveAndFlush(transactionOrder);
			LOGGER.info("用户id：" + userId + "  股票id:" + stockId + " 持仓信息更新完毕");
		} else {
			holdPosition = new HoldPosition();
			// 如果持仓信息不存在，可能是用户第一次购买该股票，是建仓。创建一条新的持仓信息插入数据库
			LOGGER.info("用户id：" + userId + "  股票id:" + stockId + " 该用户为第一次购买此股票，开始建仓");

//			User user = userRepository.findById(userId).get();
			User user = userRepository.findByUserId(userId);
			if (user == null) {
				throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "目标用户不存在！");
			}
			holdPosition.setUser(user);

			Stock stock = stockRepository.findByStockId(stockId);
			if (stock == null) {
				throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "目标股票不存在！");
			}
			holdPosition.setStock(stock);
			holdPosition.setCostPrice(transactionOrder.getActualAmount() / transactionOrder.getExchangeAmount());
			holdPosition.setOpeningTime(transactionOrder.getDate());
			holdPosition.setPositionNumber(transactionOrder.getExchangeAmount());
			holdPosition.setAvailableNumber(0);

			// 存入数据库
			holdPositionRepository.saveAndFlush(holdPosition);
			LOGGER.info("用户id：" + userId + "  股票id:" + stockId + " 建仓完毕");
		}

	}

	@Override
	public void updateHoldPositionByOrder(Order order) throws BusinessException {
		// TODO Auto-generated method stub
		int userId = order.getUser().getUserId();

		String stockId = order.getStock().getStockId();

		LOGGER.info("用户id：" + userId + "  股票id:" + stockId + " 开始更新股票可用余额");

		HoldPosition holdPositon = holdPositionRepository.findByUser_UserIdAndStock_StockId(userId, stockId);

		if (holdPositon == null) {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "目标持仓信息不存在！");
		}

		int availableNumber = holdPositon.getAvailableNumber() - order.getOrderAmount();
		if (availableNumber < 0) {
			throw new BusinessException(EmBusinessError.FUND_ERROR);
		}

		holdPositon.setAvailableNumber(availableNumber);

		holdPositionRepository.saveAndFlush(holdPositon);

		LOGGER.info("用户id：" + userId + "  股票id:" + stockId + " 股票可用余额更新完毕");
	}

	// 根据用户id，查看他所有持仓股票
	@Override
	public List<HoldPositionVO> findHoldPosition(int userId) {
		List<HoldPosition> list = new ArrayList<>();
		list = holdPositionRepository.findByUser_UserId(userId);
		UserFund userFund = userFundRepository.findByUser_UserId(userId);
		// 用户资金余额 = 冻结资金 + 可用资金
		double amountBalance = userFund.getFrozenAmount() + userFund.getBalance();

		// 判断当前用户是否有持仓股
		if (list != null) {
			// 当用户有持仓股时
			List<HoldPositionVO> holdPositionVOList = new ArrayList<>();
			double totalFunds = 0;
			////holdPosProAndLos = 0;
			double totalMarketValue = 0;
			//totalTodayProAndLos = 0;

			for (HoldPosition h : list) {
				String stockId = h.getStock().getStockId();
				int positionNumber = h.getPositionNumber();
				double presentPrice = realTime1Redis.get(String.valueOf(stockId)).getLastTradePrice();// 现价--也就是市价
				double marketValue = presentPrice * positionNumber;
				totalMarketValue += marketValue;
			}

			// 计算用户总资产
			totalFunds = amountBalance + totalMarketValue;
			for (HoldPosition h : list) {
				HoldPositionVO holdPositionVO = new HoldPositionVO();
				String stockId = h.getStock().getStockId();
				List<TransactionOrder> transactionOrderList = transactionOrderRepository.findByOwnerIdAndStockId(userId,
						stockId);
				double costPrice = h.getCostPrice();// 成本价
				int positionNumber = h.getPositionNumber();
				double presentPrice = realTime1Redis.get(String.valueOf(stockId)).getLastTradePrice();// 现价--也就是市价
				// 总盈亏 = 现价 *股票余额 - 成本价 * 股票余额 = （现价 - 成本价）* 股票余额
				double totalProfitAndLoss = (presentPrice - costPrice) * positionNumber;
				// 市值 = 现价*股票余额
				double marketValue = presentPrice * positionNumber;

				// 当日盈亏 = 可用数量*(最新价格-昨日收盘价)+∑（发生金额-昨收盘*卖出数量）-∑ （发生金额-昨收盘*买入数量）
				double totalSellActualAmount = 0;
				double totalBuyActualAmount = 0;
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				double yesterdayClosePrice = realTime1Redis.get(String.valueOf(stockId)).getYesterdayClosePrice();
				for (TransactionOrder transactionOrder : transactionOrderList) {

					if (String.valueOf(transactionOrder.getDate()).equals(df.format(date))) {

						if (transactionOrder.getType() == 1) {
							// 卖
							totalSellActualAmount += transactionOrder.getActualAmount()
									- yesterdayClosePrice * transactionOrder.getExchangeAmount();
						} else {
							// 买
							totalBuyActualAmount += transactionOrder.getActualAmount()
									- yesterdayClosePrice * transactionOrder.getExchangeAmount();
						}

					}

				}
				double todayProfitAndLoss = positionNumber * (presentPrice - yesterdayClosePrice)
						+ totalSellActualAmount - totalBuyActualAmount;

				BeanUtils.copyProperties(h, holdPositionVO);
				holdPositionVO.setStockId(stockId);
				holdPositionVO.setStockName(h.getStock().getStockName());
				holdPositionVO.setPresentPrice(DemicalUtil.keepTwoDecimal(presentPrice));
				holdPositionVO.setTotalProfitAndLoss(DemicalUtil.keepTwoDecimal(totalProfitAndLoss));
				holdPositionVO.setFrozenNumber(h.getPositionNumber() - h.getAvailableNumber());
				// 盈亏比例=（ 市价 - 成本价）/成本价
				holdPositionVO.setProfitAndLossRatio(
						DemicalUtil.keepTwoDecimal((presentPrice - costPrice) / costPrice * 100)); // 单位应是%
				// 市值 = 市价*股票余额
				holdPositionVO.setMarketValue(DemicalUtil.keepTwoDecimal(marketValue));
				// 仓位占比 = 市值/总资产
				holdPositionVO.setPositionRatio(DemicalUtil.keepTwoDecimal(marketValue / totalFunds * 100)); // 单位应是%
				holdPositionVO.setTodayProfitAndLoss(DemicalUtil.keepTwoDecimal(todayProfitAndLoss));

				holdPositionVOList.add(holdPositionVO);

				////holdPosProAndLos += totalProfitAndLoss; // 为计算用户资产信息中的持仓盈亏做服务
				////totalTodayProAndLos += todayProfitAndLoss;
			}

			return holdPositionVOList;
		} else {
			////totalFunds = amountBalance;
			return null;
		}

	}

	// 根据用户id，计算用户资产信息
	@Override
	public UserFundVO getFunds(int userId) {
		
		List<HoldPosition> list = holdPositionRepository.findByUser_UserId(userId);
		double totalMarketValue = 0;
		double holdPosProAndLos = 0;
		double totalTodayProAndLos =0;
		if(list!=null) {
			for(HoldPosition h : list) {
				String stockId = h.getStock().getStockId();
				int positionNumber = h.getPositionNumber(); 
				double presentPrice = realTime1Redis.get(String.valueOf(stockId)).getLastTradePrice();//现价--也就是市价
				double marketValue = presentPrice * positionNumber;
				double totalProfitAndLoss = (presentPrice -  h.getCostPrice())*positionNumber;
				
				double totalSellActualAmount = 0;
				double totalBuyActualAmount = 0;
				Date date = new Date();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				double yesterdayClosePrice = realTime1Redis.get(String.valueOf(stockId)).getYesterdayClosePrice();
				List<TransactionOrder> transactionOrderList = transactionOrderRepository.findByOwnerIdAndStockId(userId,stockId);
				for(TransactionOrder transactionOrder : transactionOrderList) {
					
					if(String.valueOf(transactionOrder.getDate()).equals(df.format(date))) {
						
						if(transactionOrder.getType()==1) {
							//卖
							totalSellActualAmount += transactionOrder.getActualAmount()-yesterdayClosePrice*transactionOrder.getExchangeAmount();
						}else  {
							//买
							totalBuyActualAmount += transactionOrder.getActualAmount()-yesterdayClosePrice*transactionOrder.getExchangeAmount();
						}
						
					}
					
				}
				double todayProfitAndLoss = positionNumber*(presentPrice-yesterdayClosePrice)+totalSellActualAmount-totalBuyActualAmount;
				
				totalMarketValue += marketValue;
				holdPosProAndLos += totalProfitAndLoss;
				totalTodayProAndLos += todayProfitAndLoss;
			}
		}
		
		UserFundVO userFundVO = new UserFundVO();
		UserFund userFund = userFundRepository.findByUser_UserId(userId);
		
		//用户资金余额 = 冻结资金 + 可用资金
		double amountBalance = userFund.getFrozenAmount() + userFund.getBalance();
		//计算用户总资产
		double totalFunds = amountBalance + totalMarketValue;
		
		userFundVO.setTotalFunds(DemicalUtil.keepTwoDecimal(totalFunds));//总资产
		userFundVO.setHoldPosProAndLos(DemicalUtil.keepTwoDecimal(holdPosProAndLos));//持仓盈亏
		userFundVO.setBalance(DemicalUtil.keepTwoDecimal(userFund.getBalance()));//可用资金
		userFundVO.setTotalMarketValue(DemicalUtil.keepTwoDecimal(totalMarketValue));//总市值
		userFundVO.setTodayProAndLos(DemicalUtil.keepTwoDecimal(totalTodayProAndLos));//当日盈亏
		userFundVO.setFrozenAmount(DemicalUtil.keepTwoDecimal(userFund.getFrozenAmount()));//冻结资金
		userFundVO.setAmountBalance(DemicalUtil.keepTwoDecimal(amountBalance));//资金余额
		return userFundVO;
	}

	@Override
	public void updateHoldPositionByCancelOrder(TransactionOrder cancelOrder) throws BusinessException {
		int userId = cancelOrder.getOwnerId();

		String stockId = cancelOrder.getStockId();

		// 从数据库读取对应数据
		HoldPosition holdPosition = holdPositionRepository.findByUser_UserIdAndStock_StockId(userId, stockId);
		if (holdPosition != null) {
			holdPosition.setFrozenNumber(holdPosition.getFrozenNumber() - cancelOrder.getCancelNumber());
			holdPosition.setAvailableNumber(holdPosition.getAvailableNumber() + cancelOrder.getCancelNumber());
			// 存入数据库
			holdPositionRepository.saveAndFlush(holdPosition);
		} else {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "目标持仓信息不存在");
		}
	}
}
