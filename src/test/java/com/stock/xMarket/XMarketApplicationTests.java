package com.stock.xMarket;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.fastjson.JSON;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XMarketApplicationTests {

	@Test
	public void contextLoads() {
		Date d=new Date();
	
		System.out.println("Adsxadaffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff");
		System.out.println(JSON.toJSONString(d));
	}

}
