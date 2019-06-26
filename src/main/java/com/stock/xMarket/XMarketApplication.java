package com.stock.xMarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;





@SpringBootApplication
//开启定时任务
@EnableScheduling
//开启异步任务
@EnableAsync
public class XMarketApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(XMarketApplication.class, args);
	}

}
