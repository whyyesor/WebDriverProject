package com.sri.listeners;

import java.io.IOException;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;

import com.relevantcodes.extentreports.LogStatus;
import com.sri.base.TestBase;
import com.sri.utilities.TestUtil;

public class CustomListeners extends TestBase implements ITestListener{

	public void onTestStart(ITestResult result) {
		test = exRep.startTest(result.getName().toUpperCase());
		
		if (!TestUtil.isTestRunnable(result.getName(), excel)) {
			
			throw new SkipException("Skipping the Test"+result.getName().toUpperCase());
		}
		
	}

	public void onTestSuccess(ITestResult result) {
		
		test.log(LogStatus.PASS, result.getName().toUpperCase()+" PASS");
	
		exRep.endTest(test);
		exRep.flush();
		System.setProperty("org.uncommons.reportng.escape-output","false");
		Reporter.log("<br>");
		Reporter.log(result.getName().toUpperCase()+" PASS");
		Reporter.log("<br>");
	}

	public void onTestFailure(ITestResult result) {
		
		
		
		System.setProperty("org.uncommons.reportng.escape-output","false");
		try {
			TestUtil.captureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(LogStatus.FAIL, result.getName().toUpperCase()+" FAIL");
		test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
		
		exRep.endTest(test);
		exRep.flush();
		
		Reporter.log("<br>");
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
		Reporter.log("<br>");
	}

	public void onTestSkipped(ITestResult result) {
		test.log(LogStatus.SKIP, result.getName().toUpperCase()+" Skipped as runmode is NO.");
		exRep.endTest(test);
		exRep.flush();
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext context) {
		//test = exRep.startTest(context.getName().toUpperCase());
		
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	

}
