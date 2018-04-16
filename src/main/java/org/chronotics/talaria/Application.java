package org.chronotics.talaria;

import org.chronotics.talaria.thrift.ThriftServer;
import org.chronotics.talaria.thrift.ThriftServerProperties;
import org.chronotics.talaria.thrift.ThriftService;
import org.chronotics.talaria.websocket.springstompserver.ScheduledUpdates;
import org.chronotics.talaria.websocket.springstompserver.SpringStompServerProperties;

import org.chronotics.talaria.common.MessageQueueMap;
import org.chronotics.talaria.common.TalariaProperties;
import org.chronotics.talaria.common.Handler;
import org.chronotics.talaria.impl.HandlerMessageQueueToWebsocket;
import org.chronotics.talaria.impl.ThriftToMessageQueue;
import org.chronotics.talaria.common.MessageQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.chronotics.talaria",
		"org.chronotics.talaria.common",
		"org.chronotics.talaria.websocket.springstompserver", 
		"org.chronotics.talaria.thrift"})
public class Application {
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		// run spring boot
		ApplicationContext context = SpringApplication.run(Application.class,args);
		String[] allBeanNames = context.getBeanDefinitionNames();
		for(String beanName : allBeanNames) {
			System.out.println(beanName);
		}
		
		// getProperties
		TalariaProperties properties = 
				(TalariaProperties)context.getBean("talariaProperties");
		assert(properties != null);
		if(properties == null) {
			return;
		}
		
		ThriftServerProperties thriftServerProperties = 
				properties.getThriftServerProperties();
		assert(thriftServerProperties != null);
		if(thriftServerProperties == null) {
			return;
		}
		
		SpringStompServerProperties stompProperties = 
				properties.getSpringStompServerProperties();
		assert(stompProperties != null);
		if(stompProperties == null) {
			return;
		}
		
		String targetDestination = stompProperties.getTargetDestination();
		String queueMapKey = properties.getQueueMapKey(); //"vib";
		
		// register message queue
		MessageQueue<String> msgqueue = 
				new MessageQueue<String>(
						String.class,
						MessageQueue.default_maxQueueSize,
						MessageQueue.OVERFLOW_STRATEGY.DELETE_FIRST);
		MessageQueueMap.getInstance().put(queueMapKey, msgqueue);

		// start thrift server
		ThriftService thriftServiceHandler = new ThriftToMessageQueue(queueMapKey);
		ThriftServer.startServer(thriftServiceHandler,thriftServerProperties);
 
//		// start websocket server
//		ScheduledUpdates scheduledUpdates = context.getBean(ScheduledUpdates.class);
//		
//		Handler<SimpMessagingTemplate> handlerWebsocketTask = 
//				new HandlerMessageQueueToWebsocket(Handler.PROPAGATION_RULE.SIMULTANEOUSLY, null);
//		
//		handlerWebsocketTask.putProperty(
//				HandlerMessageQueueToWebsocket.targetDestination,
//				targetDestination);
//		
//		scheduledUpdates.setAttribute(queueMapKey,handlerWebsocketTask);
//		
////		Thread thread = new Thread(new DummyMessageGenerator());
////		thread.start();
	}
}
