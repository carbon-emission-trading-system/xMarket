package com.xMarket.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xMarket.VO.ConditionVO;
import com.xMarket.VO.StockListVO;
import com.xMarket.error.BusinessException;
import com.xMarket.error.EmBusinessError;
import com.xMarket.model.RealTime1;
import com.xMarket.model.RealTime2;
import com.xMarket.model.User;
import com.xMarket.response.CommonReturnType;
import com.xMarket.service.RankingListService;
import com.xMarket.service.StockListService;
import com.xMarket.util.DemicalUtil;

@RestController
public class StockListController extends BaseApiController {

	@Autowired
	private StockListService stockListService;

	@Autowired
	private RankingListService rankingListService;

	final static Logger logger = LoggerFactory.getLogger(OrderController.class);

	// 所有个股信息，展示股票列表
	@RequestMapping(value = "/stockList", method = RequestMethod.GET)
	public CommonReturnType findAllRealTime() {

		List<RealTime2> realTime2List = stockListService.findRealTime2();
		List<RealTime1> realTime1List = stockListService.findRealTime1();
		List<StockListVO> stockListVOList = new ArrayList<StockListVO>();

		stockListVOList = stockListService.finalList(realTime1List, realTime2List, stockListVOList);

		return CommonReturnType.success(stockListVOList);

	}

	// 所有个股信息，展示股票列表
	@RequestMapping(value = "/conditionalStockList", method = RequestMethod.POST)
	public CommonReturnType conditionalStockList(@ModelAttribute(value = "condition") ConditionVO conditionVO) {

		List<RealTime2> realTime2List = stockListService.findRealTime2();
		List<RealTime1> realTime1List = stockListService.findRealTime1();
		List<StockListVO> stockListVOList = new ArrayList<StockListVO>();

		stockListVOList = stockListService.finalList(realTime1List, realTime2List, stockListVOList);

		List<StockListVO> stockVOList = new ArrayList<StockListVO>();

		for (StockListVO stockListVO : stockListVOList) {

			if (stockListVO.getIncrease() >= conditionVO.getMinIncrease()
					&& stockListVO.getIncrease() <= conditionVO.getMaxIncrease())
				if (stockListVO.getTotalMarketCapitalization() >= conditionVO.getMinTotalMarketCapitalization()
						&& stockListVO.getTotalMarketCapitalization() <= conditionVO.getMaxTotalMarketCapitalization())
					if (stockListVO.getPeRatio() >= conditionVO.getMinPeRatio()
							&& stockListVO.getPeRatio() <= conditionVO.getMaxPeRatio())
						if (stockListVO.getTradeAmount() >= conditionVO.getMinTradeAmount()
								&& stockListVO.getTradeAmount() <= conditionVO.getMaxTradeAmount()) {
							stockVOList.add(stockListVO);
						}

		}

		return CommonReturnType.success(stockVOList);

	}

	// 所有个股信息，展示股票列表
	@RequestMapping(value = "/getCondition", method = RequestMethod.GET)
	public CommonReturnType getCondition() {

		List<RealTime2> realTime2List = stockListService.findRealTime2();
		List<RealTime1> realTime1List = stockListService.findRealTime1();
		List<StockListVO> stockListVOList = new ArrayList<StockListVO>();

		ConditionVO conditionVO = new ConditionVO();

		stockListVOList = stockListService.finalList(realTime1List, realTime2List, stockListVOList);

		for (StockListVO stockListVO : stockListVOList) {

			if (stockListVO.getIncrease() < conditionVO.getMinIncrease()) {
				conditionVO.setMinIncrease(stockListVO.getIncrease());
			}

			if (stockListVO.getIncrease() > conditionVO.getMaxIncrease()) {
				conditionVO.setMaxIncrease(stockListVO.getIncrease());
			}

			if (stockListVO.getTotalMarketCapitalization() < conditionVO.getMinTotalMarketCapitalization()) {
				conditionVO.setMinTotalMarketCapitalization(stockListVO.getTotalMarketCapitalization());
			}

			if (stockListVO.getTotalMarketCapitalization() > conditionVO.getMaxTotalMarketCapitalization()) {
				conditionVO.setMaxTotalMarketCapitalization(stockListVO.getTotalMarketCapitalization());
			}

			if (stockListVO.getPeRatio() < conditionVO.getMinPeRatio()) {
				conditionVO.setMinPeRatio(stockListVO.getPeRatio());
			}

			if (stockListVO.getPeRatio() > conditionVO.getMaxPeRatio()) {
				conditionVO.setMaxPeRatio(stockListVO.getPeRatio());
			}

			if (stockListVO.getTradeAmount() < conditionVO.getMinTradeAmount()) {
				conditionVO.setMinTradeAmount(stockListVO.getTradeAmount());
			}

			if (stockListVO.getTradeAmount() > conditionVO.getMaxTradeAmount()) {
				conditionVO.setMaxTradeAmount(stockListVO.getTradeAmount());
			}

		}

		return CommonReturnType.success(conditionVO);

	}

	// 根据用户id，展示用户的所有自选股信息
	@RequestMapping(value = "/selfSelectStockList", method = RequestMethod.GET)
	public CommonReturnType findAllSelfSelectStock(@RequestParam("userId") int id) throws BusinessException {

		List<RealTime1> realTime1List = stockListService.findSelfSelectStockRealTime1(id);
		if (realTime1List == null) {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "realTime1List为空");
		}
		List<RealTime2> realTime2List = stockListService.findSelfSelectStockRealTime2(id);
		if (realTime2List == null) {
			throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR, "realTime2List为空");
		}
		List<StockListVO> stockListVOList = new ArrayList<StockListVO>();

		stockListVOList = stockListService.finalList(realTime1List, realTime2List, stockListVOList);

		return CommonReturnType.success(stockListVOList);

	}

	@RequestMapping(value = "/rankList", method = RequestMethod.GET)
	public CommonReturnType rankList(@RequestParam("type") int type) throws BusinessException {

		List<RealTime2> realTime2List = stockListService.findRealTime2();
		List<RealTime1> realTime1List = stockListService.findRealTime1();
		List<StockListVO> stockListVOList = new ArrayList<StockListVO>();
		stockListVOList = stockListService.finalList(realTime1List, realTime2List, stockListVOList);

		switch (type) {
		case 1:
			rankingListService.rankingByIncrease(stockListVOList);
			break;
		case 2:
			rankingListService.rankingByDecrease(stockListVOList);
			break;
		case 3:
			rankingListService.rankingByExchangeAmount(stockListVOList);
			break;
		case 4:
			rankingListService.rankingByconversionHand(stockListVOList);
			break;
		default:
			throw new BusinessException(EmBusinessError.UNKNOWN_ERROR);
		}

		return CommonReturnType.success(stockListVOList);
	}

}
