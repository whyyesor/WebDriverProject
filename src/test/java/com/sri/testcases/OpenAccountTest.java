package com.sri.testcases;


import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;

import com.sri.base.TestBase;
import com.sri.utilities.TestUtil;

public class OpenAccountTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void openAccountTest(Hashtable<String,String> data) {
	
		System.out.println("Customer Name is :"+data.get("customer"));
		System.out.println("Currency is :"+data.get("currency"));
		click("openAccount_CSS");
		select("customerId_CSS",data.get("customer"));
		select("currency_CSS", data.get("currency"));
		click("btnProcess_CSS");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		
	}
	
	

}
