package com.stock.xMarket.config;


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
	        connectionFactory.setPublisherConfirms(true);
	        connectionFactory.setPublisherReturns(true);
	        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
	        rabbitTemplate.setMandatory(true);
	        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
	            @Override
	            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
	                log.info("消息发送成功:correlationData({}),ack({}),cause({})",correlationData,ack,cause);
	            }
	        });
	        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
	            @Override
	            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
	                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
	            }
	        });
	        return rabbitTemplate;
	    }
	    
	    //委托单
	

	    @Bean
	    public TopicExchange userOrderExchange(){
	        return new TopicExchange(env.getProperty("order.exchange.name"),true,false);
	    }
	    
	    @Bean(name = "userOrderQueue")
	    public Queue userOrderQueue(){
	        return new Queue(env.getProperty("order.queue.name"),true);
	    }

	    @Bean
	    public Binding userOrderBinding(){
	        return BindingBuilder.bind(userOrderQueue()).to(userOrderExchange()).with(env.getProperty("order.routing.key.name"));
	    }
	    
	    @Bean(name = "cancelOrderQueue")
	    public Queue cancelOrderQueue(){
	        return new Queue(env.getProperty("cancelOrder.queue.name"),true);
	    }

	    @Bean
	    public Binding cancelOrderBinding(){
	        return BindingBuilder.bind(cancelOrderQueue()).to(userOrderExchange()).with(env.getProperty("cancelOrder.routing.key.name"));
	    }
	    
	    //匹配
	    @Bean
	    public TopicExchange marchExchange(){
	        return new TopicExchange(env.getProperty("march.exchange.name"),true,false);
	    }
	    //连续竞价
	    @Bean(name = "marchQueue")
	    public Queue marchQueue(){
	        return new Queue(env.getProperty("march.queue.name"),true);
	    }

	    @Bean
	    public Binding marchBinding(){
	        return BindingBuilder.bind(marchQueue()).to(marchExchange()).with(env.getProperty("march.routing.key.name"));
	    }
	    
	    //集合竞价
	    @Bean(name = "allMarchQueue")
	    public Queue allMarchQueue(){
	        return new Queue(env.getProperty("allMarch.queue.name"),true);
	    }

	    @Bean
	    public Binding allMarchBinding(){
	        return BindingBuilder.bind(allMarchQueue()).to(marchExchange()).with(env.getProperty("allMarch.routing.key.name"));
	    }

	  //实时消息
	    @Bean
	    public TopicExchange realTimeExchange(){
	        return new TopicExchange(env.getProperty("realTime.exchange.name"),true,false);
	    }

	    
	    //分时信息
	    @Bean
	    public TopicExchange timeShareExchange(){
	        return new TopicExchange(env.getProperty("timeShare.exchange.name"),true,false);
	    }
	   
	    //交易单
	    @Bean
	    public DirectExchange tradeOrderExchange(){
	        return new DirectExchange(env.getProperty("tradeOrder.exchange.name"),true,false);
	    }
	    
	    @Bean
	    public Queue tradeOrderQueue(){
	        return new Queue(env.getProperty("tradeOrder.queue.name"),true);
	    }

	    @Bean
	    public Binding tradeOrderBinding(){
	        return BindingBuilder.bind(tradeOrderQueue()).to(tradeOrderExchange()).with(env.getProperty("tradeOrder.routing.key.name"));
	    }
	    
	    }

