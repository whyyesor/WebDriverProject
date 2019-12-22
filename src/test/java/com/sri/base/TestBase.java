package com.sri.base;



import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.sri.utilities.ExcelReader;
import com.sri.utilities.Extentmanager;
import com.sri.utilities.TestUtil;

public class TestBase {

	// We will be Initializing following in the base class
	/*
	 * WebDriver - Done
	 *  Properties  - Done
	 *  Logs - include log4j jar, Log4j.properties, logger class
	 *  ExtentReports 
	 *  DB 
	 *  Excel 
	 *  Mail
	 *  
	 *  
	 */

	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "//src//test//resources//excel//testdata.xlsx"); 
	public static WebDriverWait wait;
	
	public ExtentReports exRep= Extentmanager.getInstance();
	public static ExtentTest test;
	
	//@BeforeMethod
	@BeforeSuite
	public void setUp() {
		if (driver == null) {
			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//properties//Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
				log.debug("Config Loaded !!");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir") + "//src//test//resources//properties//OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
				log.debug("OR Loaded !!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (config.getProperty("browser").equals("firefox")) {
				driver = new FirefoxDriver();
			} else if (config.getProperty("browser").equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "//src//test//resources//executables//chromedriver");
				driver = new ChromeDriver();
				log.debug("Chrome Launced !!!");
			}

			driver.get(config.getProperty("testsiteurl"));
			log.debug("Navigated to "+ config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt((config.getProperty("impicit.wait"))),
					TimeUnit.SECONDS);
			wait = new WebDriverWait(driver,3);

		}

	}

	public boolean isElementPresent(By by) {
		
		try {
			
			driver.findElement(by);
			return true;
	}catch (NoSuchElementException e) {
		return false;}
	}
	
	public void click(String locator) {
		
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
		} else if (locator.endsWith("XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).click();
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).click();
		}
		
		test.log(LogStatus.INFO, "Clicking On : "+ locator);
	}
	
	public void type(String locator,String value) {
		
		if (locator.endsWith("_CSS")) {
			driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_XPATH")) {
			driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
		} else if (locator.endsWith("_ID")) {
			driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
		}
		
		
		test.log(LogStatus.INFO, "Typing... "+value+" in : "+ locator);
	}
	
	static WebElement dropdown;
	public void select(String locator,String value) {
		
		if (locator.endsWith("_CSS")) {
			dropdown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
		} else if (locator.endsWith("_XPATH")) {
			dropdown = driver.findElement(By.xpath(OR.getProperty(locator)));
		} else if (locator.endsWith("_ID")) {
			dropdown = driver.findElement(By.id(OR.getProperty(locator)));
		}
		
		Select select = new Select(dropdown);
		select.selectByVisibleText(value);
		
		test.log(LogStatus.INFO, "Selecting from dropdown "+locator+" Value : "+ value);
		
	}
	
		
	
	public void verifyEquals(String actual, String expected) throws IOException {
		
		try {
			
			Assert.assertEquals(actual, expected);
			
		} catch (Throwable t) {
			System.setProperty("org.uncommons.reportng.escape-output","false");
			TestUtil.captureScreenshot();
			Reporter.log("<br>");
			Reporter.log("<br>"+"Verification Failed with exception "+t.getMessage());
			Reporter.log("<br>");
			Reporter.log("<a target=\"_blank\" href="+TestUtil.screenshotName+"><img src="+TestUtil.screenshotName+" height=200 width=200></img></a>");
			
			test.log(LogStatus.FAIL, "Verification failed with exception "+t.getMessage());
			test.log(LogStatus.FAIL, test.addScreenCapture(TestUtil.screenshotName));
			exRep.endTest(test);
			exRep.flush();
		}
		
	}
	
	
	//@AfterMethod
	@AfterSuite
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
		
		log.debug("Test Execution Complete !!!");
	}

}
