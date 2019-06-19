package com.stock.xMarket.service;

import java.util.ArrayList;

import com.stock.xMarket.VO.IndexVO;
import com.stock.xMarket.VO.IndexVO2;

public interface IndexService {

	ArrayList<IndexVO2> getIndex();

	IndexVO indexInfo(String indexId);

}
