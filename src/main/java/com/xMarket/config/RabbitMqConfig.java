package com.xMarket.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;






@Configuration
public class RabbitMqConfig {

	    private static final Logger log= LoggerFactory.getLogger(RabbitMqConfig.class);

	    @Autowired
	    private Environment env;

	    @Autowired
	    private CachingConnectionFactory connectionFactory;

	    @Autowired
	    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

	    
	    /**
	     * 单一消费者
	     * @return
	     */
	    @Bean(name = "singleListenerContainer")
	    public SimpleRabbitListenerContainerFactory listenerContainer(){
	        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	        factory.setConnectionFactory(connectionFactory);
	        factory.setMessageConverter(new Jackson2JsonMessageConverter());
	        factory.setConcurrentConsumers(1);
	        factory.setMaxConcurrentConsumers(1);
	        factory.setPrefetchCount(1);
	        factory.setTxSize(1);
	        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
	        return factory;
	    }

	    /**
	     * 多个消费者
	     * @return
	     */
	    @Bean(name = "multiListenerContainer")
	    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
	        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
	        factoryConfigurer.configure(factory,connectionFactory);
	        factory.setMessageConverter(new Jackson2JsonMessageConverter());
	        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
	        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
	        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
	        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
	        return factory;
	    }

	    @Bean
	    public RabbitTemplate rabbitTemplate(){
//	        connectionFactory.setPublisherConfirms(true);
//	        connectionFactory.setPublisherReturns(true);
	        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setMandatory(true);
//	        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
//	            @Override
//	            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//	                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
//	            }
//	        });
//	        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
//	            @Override
//	            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
//	                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
//	            }
//	        });
	        return rabbitTemplate;
	    }
	    
	    
	    //交易单
	    @Bean
	    public DirectExchange tradeOrderExchange(){
	        return new DirectExchange("tradeOrderExchange",true,false);
	    }
	    
	    //委托单
	    @Bean
	    public TopicExchange userOrderExchange(){
	        return new TopicExchange("userOrderExchange",true,false);
	    }
	    
	    //匹配
	    @Bean
	    public TopicExchange marchExchange(){
	        return new TopicExchange("marchExchange",true,false);
	    }
	    
	    
	    //实时消息
	    @Bean
	    public TopicExchange realTimeExchange(){
	        return new TopicExchange("realTimeExchange",true,false);
	    }

	    
	    //分时信息
	    @Bean
	    public TopicExchange timeShareExchange(){
	        return new TopicExchange("timeShareExchange",true,false);
	    }
	    
	    
	    //提醒
	    @Bean
	    public TopicExchange notifyExchange(){
	        return new TopicExchange("notifyExchange",true,false);
	    }
	    
	    
	    
	    //交易单队列
	    @Bean(name = "tradeOrderQueue")
	    public Queue tradeOrderQueue(){
	        return new Queue("tradeOrderQueue",true);
	    }

	    //委托单
	    @Bean(name = "userOrderQueue")
	    public Queue userOrderQueue(){
	        return new Queue("userOrderQueue",true);
	    }
	    
	    //撤销单
	    @Bean(name = "cancelOrderQueue")
	    public Queue cancelOrderQueue(){
	        return new Queue("cancelOrderQueue",true);
	    }
	    
	    //连续竞价
	    @Bean(name = "marchQueue")
	    public Queue marchQueue(){
	        return new Queue("marchQueue",true);
	    }
	    
	    
	    //集合竞价
	    @Bean(name = "allMarchQueue")
	    public Queue allMarchQueue(){
	        return new Queue("allMarchQueue",true);
	    }

	    @Bean
	    public Binding allMarchBinding(){
	        return BindingBuilder.bind(allMarchQueue()).to(marchExchange()).with("allMarchRoutingKey");
	    }

	    
	    @Bean
	    public Binding tradeOrderBinding(){
	        return BindingBuilder.bind(tradeOrderQueue()).to(tradeOrderExchange()).with("tradeOrderRoutingKey");
	    }


	    @Bean
	    public Binding userOrderBinding(){
	        return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange()).with("orderRoutingKey");
	    }
	    
	    

	    @Bean
	    public Binding cancelOrderBinding(){
	        return BindingBuilder.bind(cancelOrderQueue()).to(userOrderExchange()).with("cancelOrderRoutingKey");
	    }
	    
	
	    @Bean
	    public Binding marchBinding(){
	        return BindingBuilder.bind(marchQueue()).to(marchExchange()).with("marchRoutingKey");
	    }
	    
	
	
	    
	    }

