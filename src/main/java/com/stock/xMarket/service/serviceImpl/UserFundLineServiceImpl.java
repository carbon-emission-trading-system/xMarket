package com.stock.xMarket.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.stock.xMarket.VO.UserFundHistoryVO;
import com.stock.xMarket.controller.KlineController;
import com.stock.xMarket.error.BusinessException;
import com.stock.xMarket.error.EmBusinessError;
import com.stock.xMarket.model.UserFundHistory;
import com.stock.xMarket.repository.UserFundHistoryRepository;
import com.stock.xMarket.service.UserFundLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserFundLineServiceImpl implements UserFundLineService {

    final static Logger logger=LoggerFactory.getLogger(KlineController.class);

    @Autowired
    private UserFundHistoryRepository userFundHistoryRepository;

    @Override
    public List<UserFundHistoryVO> createUserFundLine(int userId) throws BusinessException {
        List<UserFundHistory> userFundHistoryList= userFundHistoryRepository.findByUserIdOrderByDate(userId);
        if (CollectionUtils.isEmpty(userFundHistoryList)) {
            logger.error("用户资金曲线数据获取失败，没有查询到此用户的历史资金信息");
            throw new BusinessException(EmBusinessError.OBJECT_NOT_EXIST_ERROR,"用户资金曲线数据获取失败，没有查询到此用户的历史资金信息");
        }
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        return JSON.parseArray(JSON.toJSONString(userFundHistoryList,SerializerFeature.WriteDateUseDateFormat), UserFundHistoryVO.class);
    }
}
