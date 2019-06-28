package com.xMarket.service.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xMarket.VO.HistoryHoldPositionVO;
import com.xMarket.repository.HistoryHoldPositionRepository;
import com.xMarket.service.HistoryHoldPositionService;

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
