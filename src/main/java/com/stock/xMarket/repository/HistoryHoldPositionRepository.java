package com.stock.xMarket.repository;

import java.util.List;

import com.stock.xMarket.VO.HistoryHoldPositionVO;
import com.stock.xMarket.model.HistoryHoldPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryHoldPositionRepository extends JpaRepository<HistoryHoldPosition,Integer> {

	@Query(value="select new com.stock.xMarket.VO.HistoryHoldPositionVO(h.stockId,h.stockName,h.buildPositionDate,h.clearPositionDate,h.stockHoldDay,h.totalProfitAndLoss,h.totalPurchaseAmount) from HistoryHoldPosition h where h.userId=:userId")
	List<HistoryHoldPositionVO> findByUserId(Integer userId);
}
