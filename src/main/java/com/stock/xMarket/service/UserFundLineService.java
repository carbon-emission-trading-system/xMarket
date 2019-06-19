package com.stock.xMarket.service;

import com.stock.xMarket.VO.UserFundHistoryVO;
import com.stock.xMarket.error.BusinessException;

import java.util.List;

public interface UserFundLineService {
    List<UserFundHistoryVO> createUserFundLine(int userId) throws BusinessException;
}
