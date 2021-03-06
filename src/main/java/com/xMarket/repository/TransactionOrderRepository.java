package com.xMarket.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xMarket.VO.TransactionOrderVO;
import com.xMarket.model.TransactionOrder;

@Repository
public interface TransactionOrderRepository extends JpaRepository<TransactionOrder, Integer> {

	List<TransactionOrderVO> findByOwnerIdOrderByDateDesc(Integer ownerId);

	List<TransactionOrder> findByOwnerIdAndDateOrderByDateDescTimeDesc(int userId, Date date);

	List<TransactionOrder> findByOwnerIdAndStockId(int ownerId, String stockId);

	@Query(value = "select new com.xMarket.VO.TransactionOrderVO(t.date,t.time,t.stockId,t.stockName,t.type,t.tradePrice,t.exchangeAmount,(t.tradePrice*t.exchangeAmount) as totalExchangeMoney,"
			+ "t.stockBalance,t.orderId,t.transactionOrderId,t.serviceTax,t.stampTax,t.transferFee,t.actualAmount,t.tradeMarket,t.cancelNumber)"
			+ " from TransactionOrder t where t.ownerId=:ownerId order by t.date desc,t.time desc")
	List<TransactionOrderVO> findByOwnerIdOrderByDateDescTimeDesc(int ownerId);

}
