package com.stock.xMarket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.VO.TransactionOrderVO;
import com.stock.xMarket.model.TransactionOrder;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder,Integer> {

	@Query(value="select new com.stock.xMarket.VO.TransactionOrderVO(t.date,t.time,t.stockId,t.stockName,t.point,t.tradePrice,t.exchangeAmount,(t.tradePrice*t.exchangeAmount) as totalExchangeMoney,"
			+ "t.stockBalance,t.orderId,t.transactionOrderId,t.serviceTax,t.stampTax,t.otherFee,t.actualAmount,t.tradeMarket,t.revokeAmount)"
			+ " from TransactionOrder t")
	List<TransactionOrderVO> findByOwnerId(Integer ownerId);

	
}
