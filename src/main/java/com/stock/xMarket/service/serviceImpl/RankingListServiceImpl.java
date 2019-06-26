package com.stock.xMarket.service.serviceImpl;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.stock.xMarket.VO.StockListVO;
import com.stock.xMarket.service.RankingListService;

@Service
@Transactional
public class RankingListServiceImpl implements RankingListService{

	@Override
	public void rankingByIncrease(List<StockListVO> stockListVOList ) {
		// TODO Auto-generated method stub
		
		
	    for (Iterator<StockListVO> ite = stockListVOList.iterator(); ite.hasNext();) {
	    	   StockListVO str = ite.next();
	            if (str.getIncrease()<0) {
	                ite.remove();
	            }
	        }
		
		Comparator<StockListVO> comparator =new Comparator<StockListVO>() {

			@Override
			public int compare(StockListVO o1, StockListVO o2) {
				// TODO Auto-generated method stub
				 if(o1.getIncrease() <= o2.getIncrease()) {
			          return 1;
			        }
			        else {
			          return -1;
			        }
			}
	
    	};
    	
		Collections.sort(stockListVOList,comparator);
		
	}

	@Override
	public void rankingByDecrease(List<StockListVO> stockListVOList) {
		// TODO Auto-generated method stub
		
		
		
	       
	       
	       for (Iterator<StockListVO> ite = stockListVOList.iterator(); ite.hasNext();) {
	    	   StockListVO str = ite.next();
	            if (str.getIncrease()>0) {
	                ite.remove();
	            }
	        }
		
	
		
		Comparator<StockListVO> comparator =new Comparator<StockListVO>() {

			@Override
			public int compare(StockListVO o1, StockListVO o2) {
				// TODO Auto-generated method stub
				 if(o1.getIncrease() >= o2.getIncrease()) {
			          return 1;
			        }
			        else {
			          return -1;
			        }
			}
	
    	};
    	
		Collections.sort(stockListVOList,comparator);
		
		
	}

	@Override
	public void rankingByExchangeAmount(List<StockListVO> stockListVOList) {
		// TODO Auto-generated method stub
		
	
		
		Comparator<StockListVO> comparator =new Comparator<StockListVO>() {

			@Override
			public int compare(StockListVO o1, StockListVO o2) {
				// TODO Auto-generated method stub
				 if(o1.getTradeAmount() <= o2.getTradeAmount()) {
			          return 1;
			        }
			        else {
			          return -1;
			        }
			}
	
    	};
    	
		Collections.sort(stockListVOList,comparator);
		
		
	}

	@Override
	public void rankingByconversionHand(List<StockListVO> stockListVOList) {
		// TODO Auto-generated method stub
		Comparator<StockListVO> comparator =new Comparator<StockListVO>() {

			@Override
			public int compare(StockListVO o1, StockListVO o2) {
				// TODO Auto-generated method stub
				 if(o1.getConversionHand() <= o2.getConversionHand()) {
			          return 1;
			        }
			        else {
			          return -1;
			        }
			}
	
    	};
    	
		Collections.sort(stockListVOList,comparator);
	}

}
