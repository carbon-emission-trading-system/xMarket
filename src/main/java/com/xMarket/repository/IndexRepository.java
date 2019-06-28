package com.xMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xMarket.model.Index;



@Repository
public interface IndexRepository extends JpaRepository<Index, String> {

        Index findByindexName(String indexName);

		Index findByindexId(String indexId);
}
