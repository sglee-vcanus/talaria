package net.sglee.thrift_service;

import javax.annotation.PreDestroy;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

import thrift_gen_messenger.TransferService;

@Controller
@EnableAutoConfiguration
public class ThriftService {
	
	// Thrift Client
	@RequestMapping("/")
    @ResponseBody
    String home() {
		TTransport transport=FactoryTTransport.getInstance().get("192.168.1.187");
		TProtocol protocol = new  TBinaryProtocol(transport);
		TransferService.Client client = new TransferService.Client(protocol);
        System.out.println("Thrift Client requested");
//        	Message msg = client.getMsg();
		System.out.println("client.getMsg() ends");   
        //transport.close();
        return "Hello World!";
    }

//	@PreDestroy
//    public void stop() {
//		FactoryTServerTransport.getInstance().close();
//		FactoryTTransport.getInstance().close();
//    }
	
	public static void main(String[] args) throws Exception {
//		// TODO Auto-generated method stub
//    	try{
//	        TTransport transport = new TSocket("192.168.0.41", 9090);
//	        transport.open();
//	          
//	        FactoryTTransport TFactory=FactoryTTransport.getInstance();
//	        TFactory.put("192.168.0.92",transport);
//        } catch (Exception e) {
//        	e.printStackTrace();
//        }
//    	System.out.println("TSocket is connected");
        
//    	
		ThriftServer.startServer();
		
//		SpringApplication.run(ThriftService.class, args);
		
	}

}