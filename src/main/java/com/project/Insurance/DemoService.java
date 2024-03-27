package com.project.Insurance;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DemoService {
	
	@Autowired
	PolicyRepository polRepository;
	
	public Policy demoPolicy() {
		Policy p = new Policy(1,"1st Jan 2020", "31st Dec 2040","Ruchin");
		return p;
	}

	public Policy createPolicy(Policy pol) {
		return polRepository.save(pol);
	}

	public Policy updatePolicy(int polId,Policy polDetails) {
		Policy pol = polRepository.findById(polId).get();
		pol.setPolStartDate(polDetails.getPolStartDate());
		pol.setPolEndDate(polDetails.getPolEndDate());
		pol.setPolHolderName(polDetails.getPolHolderName());
				
		return polRepository.save(pol);
	}

	public List<Policy> viewPolicy(int polId) {
	
		return polRepository.findAll();
	}

	public void deletePolicy(int polId) {
	
		 polRepository.deleteById(polId);
	}

}
