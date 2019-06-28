package com.xMarket.service;

import java.util.ArrayList;

import com.xMarket.VO.IndexVO;
import com.xMarket.VO.IndexVO2;

public interface IndexService {

	ArrayList<IndexVO2> getIndex();

	IndexVO indexInfo(String indexId);

}
