package net.sglee.talaria.thrift;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ThriftProperties.class})
public class ThriftTest {
	@Autowired
	private ThriftProperties properties;
	
	@Test 
	public void getSeverProperties() {
		assertEquals(properties.getServerIp(),"192.168.0.41");
		assertEquals(properties.getServerPort(),"9091");
	}
	
	@Test 
	public void getSecureProperties() {
		assertEquals(properties.getSecurePort(),"9092");
		assertEquals(properties.getSecureKeyStore(),"~/.keystore");
		assertEquals(properties.getSecureKeyPass(),"thrift");
	}
}