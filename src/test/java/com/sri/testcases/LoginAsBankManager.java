package com.sri.testcases;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import java.io.IOException;

import org.openqa.selenium.By;
import com.sri.base.TestBase;

public class LoginAsBankManager extends TestBase {
	
	@Test
	public void loginAsBankManager() throws IOException {
		
		
		log.debug("Inside LoginAsBankManager");
		//driver.findElement(By.cssSelector(OR.getProperty("bmlBtn_CSS"))).click();
		click("bmlBtn_CSS");
		log.debug("Successfully completed LoginAsBankManager !!!");
		Reporter.log("Login Successful !!!");
		Assert.assertTrue(isElementPresent(By.cssSelector(OR.getProperty("addCustomerButton_CSS"))));
		verifyEquals("ABC","XYZ");
	}

}
