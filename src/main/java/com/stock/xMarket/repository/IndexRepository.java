package com.stock.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.xMarket.model.Index;



@Repository
public interface IndexRepository extends JpaRepository<Index, Integer> {

        Index findByindexName(String indexName);

		Index findByindexId(String indexId);
}
