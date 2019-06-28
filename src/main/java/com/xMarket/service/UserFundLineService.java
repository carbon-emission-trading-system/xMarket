package com.xMarket.service;

import java.util.List;

import com.xMarket.VO.UserFundHistoryVO;
import com.xMarket.error.BusinessException;

public interface UserFundLineService {
    List<UserFundHistoryVO> createUserFundLine(int userId) throws BusinessException;
}
