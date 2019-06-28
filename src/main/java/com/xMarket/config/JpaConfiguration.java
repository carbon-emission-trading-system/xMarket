package com.xMarket.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.Ordered;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableJpaRepositories(basePackages = {"com.xMarket.**.repository"})
@EntityScan(basePackages = {"com.xMarket.**.model"})
@EnableAutoConfiguration
public class JpaConfiguration { 	
	@Bean	
	PersistenceExceptionTranslationPostProcessor persistenceExceptionTranslationPostProcessor() {		
		return new PersistenceExceptionTranslationPostProcessor();	
		
	}
	
}
