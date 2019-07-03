package com.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xMarket.VO.HistoryHoldPositionVO;
import com.xMarket.model.HistoryHoldPosition;

@Repository
public interface HistoryHoldPositionRepository extends JpaRepository<HistoryHoldPosition,Integer> {

	@Query(value="select new com.xMarket.VO.HistoryHoldPositionVO(h.stockId,h.stockName,h.buildPositionDate,h.clearPositionDate,h.stockHoldDay,h.totalProfitAndLoss,h.profitAndLossRatio*100) from HistoryHoldPosition h where h.userId=:userId")
	List<HistoryHoldPositionVO> findByUserId(Integer userId);
}
