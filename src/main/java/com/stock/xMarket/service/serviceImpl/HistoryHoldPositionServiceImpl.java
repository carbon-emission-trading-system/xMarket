package com.stock.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import com.stock.xMarket.VO.HistoryHoldPositionVO;
import com.stock.xMarket.repository.HistoryHoldPositionRepository;
import com.stock.xMarket.service.HistoryHoldPositionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HistoryHoldPositionServiceImpl implements HistoryHoldPositionService {

	@Autowired
    private HistoryHoldPositionRepository historyHoldPositionRepository ;

	@Override
	//查--所有历史持仓
    public List<HistoryHoldPositionVO> findByUserId(int userId){
		String a="h";
		String b="c";
		if(a==b) {
			
		}
        return historyHoldPositionRepository.findByUserId(userId);
    }

}
