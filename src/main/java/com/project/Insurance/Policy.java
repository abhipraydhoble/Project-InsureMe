package com.project.Insurance;


import javax.persistence.Entity;
import javax.persistence.Id;




@Entity
public class Policy {
	
	@Id
	private int polId;
	private String polStartDate;
	private String polEndDate;
	private String polHolderName;
	
	public Policy() {
	}
	
	
	
	
	public Policy(int i, String d, String e, String string) {
		this.polId= i;
		this.polStartDate= d;
		this.polEndDate= e;
		this.polHolderName= string;
		
	}
	public int getPolId() {
		return polId;
	}
	public void setPolId(int polId) {
		this.polId = polId;
	}
	public String getPolStartDate() {
		return polStartDate;
	}
	public void setPolStartDate(String d) {
		this.polStartDate = d;
	}
	public String getPolEndDate() {
		return polEndDate;
	}
	public void setPolEndDate(String d) {
		this.polEndDate = d;
	}
	public String getPolHolderName() {
		return polHolderName;
	}
	public void setPolHolderName(String polHolderName) {
		this.polHolderName = polHolderName;
	}
	
	
	

}
