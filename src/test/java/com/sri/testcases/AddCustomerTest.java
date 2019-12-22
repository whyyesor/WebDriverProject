package com.sri.testcases;

import java.util.Hashtable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.sri.base.TestBase;
import com.sri.utilities.TestUtil;

public class AddCustomerTest extends TestBase{
	
	@Test(dataProviderClass=TestUtil.class,dataProvider="dp")
	public void addCustomerTest(Hashtable<String,String> data) throws InterruptedException {
		
		if (!data.get("runmode").equals("Y")) {
			throw new SkipException("Skipping as the run mode is set to No for the data set. ");
		}
		click("addCustomerButton_CSS");
		type("firstName_CSS",data.get("firstname"));
		type("lastName_CSS",data.get("lastname"));
		type("postCode_CSS", data.get("postcode"));
		click("addButton_CSS");
		
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		Assert.assertTrue(alert.getText().contains(data.get("alerttext")));
		alert.accept();
		
		
	}
	
	

}
