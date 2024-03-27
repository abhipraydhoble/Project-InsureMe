package com.project.Insurance;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InsuranceApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testdemoPolicy() {
		Policy policy =new Policy(1,"1st Jan 2020", "31st Dec 2040","Ruchin");
		DemoService dService = new DemoService();
		assertEquals(policy.getPolId(),dService.demoPolicy().getPolId());
	}

	
}

