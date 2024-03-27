package com.project.Insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {
	
	@Autowired
	DemoService demoService;
	
	@GetMapping("/demoPolicy")
	public Policy demoPolicy() {
		return demoService.demoPolicy();
			}
	
	@GetMapping("/welcome")
	public String welcome() {
		return "Welcome to Insurance website";
	}
	
	@PostMapping("/createPolicy")
	public Policy createPolicy(@RequestBody Policy policy) {
		return demoService.createPolicy(policy);
	}
	
	@PutMapping("/updatePolicy/{polId}")
	public Policy updatePolicy(@PathVariable(value="polId")int polId, @RequestBody Policy polDetails) {
		return demoService.updatePolicy(polId, polDetails);
	}
	
	@GetMapping("/viewPolicy/{polId}")
	public List<Policy> viewPolicy(@PathVariable(value="polId")int polId) {
		return demoService.viewPolicy(polId);
	}
	
	@DeleteMapping("/deletePolicy/{polId}")
	public void  deletePolicy(@PathVariable(value="polId")int polId) {
		 demoService.deletePolicy(polId);
	}
	
	
	

}
