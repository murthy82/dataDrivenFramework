package core.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import core.ddf.dev.base.ExtentManager;
import core.ddf.dev.base.Xls_Reader;

public class BaseTest {
	
	public static WebDriver driver;
	public static Properties pro;
	public static ExtentReports extent=ExtentManager.getInstance();
	public ExtentTest test;
	public static Xls_Reader xls;
		
	@BeforeClass
	public void Initialize()
	{
		if(pro==null)
		{
			pro=new Properties();
			File file = new File(System.getProperty("user.dir")+"/src/main/resources/projectConfig.properties");
			try {
				FileInputStream fileInputStream = new FileInputStream(file);
				pro.load(fileInputStream);
					if(xls==null)
					{
						xls=new Xls_Reader(System.getProperty("user.dir")+"/dataSheets/"+pro.getProperty("testDataSheetName"));
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
			}
		}
		
		
	}
	
	
	
	@SuppressWarnings("resource")
	public void openBrowser(String browserType)
	{
		
		if(browserType.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver.exe");
			driver=new ChromeDriver();
			test.log(Status.INFO, "chrome browser is opening");
		}
		else
		{
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//drivers//geckodriver.exe");
			driver=new FirefoxDriver();
			test.log(Status.INFO, "firefox browser is opening");
		}
		
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().window().maximize();
				
	}
	
	public void navigate(String urlKey)
	{
		driver.get(pro.getProperty(urlKey));
		test.log(Status.INFO, "navigating to "+pro.getProperty(urlKey));
	}
	
	public void click(String elementKey)
	{
		getElement(elementKey).click();
		test.log(Status.INFO, "clicking on "+pro.getProperty(elementKey));
		
	}
	
	public void type(String elementXpath,String data)
	{
		getElement(elementXpath).sendKeys(data);
		test.log(Status.INFO, "typing in "+pro.getProperty(elementXpath)+" and entering "+data+" as input");
	}
	
	public WebElement getElement(String elementXpath)
	{
		WebElement findElement=null;
		try {
			findElement= driver.findElement(By.xpath(pro.getProperty(elementXpath)));
			test.log(Status.INFO, "finding "+pro.getProperty(elementXpath));
			
		} catch (Exception e) {
			
			test.log(Status.ERROR, "webelement name "+pro.getProperty(elementXpath)+" did not find");
			//log exception
			Assert.fail();
			
		}
		
		return findElement;
	}
	
	/***********************************validations****************************/
	
	public boolean verifyTitle()
	{
		return false;
	}
	
	public boolean isElementPresent(String elementXpath)
	{
		int size = driver.findElements(By.xpath(pro.getProperty(elementXpath))).size();
		test.log(Status.INFO, "checking "+pro.getProperty(elementXpath));
		if(size>0)
		{
			test.log(Status.INFO,pro.getProperty(elementXpath)+" element found");
			return true;
		}
		{	
			test.log(Status.ERROR,pro.getProperty(elementXpath)+" element DID NOT FIND");
			return false;
		}
	}
	/**********************************reporting****************************************/
	
	public void pass()
	{
		//log pass
	}
	
	public void fail()
	{
		//log fail
	}
	
	public void takesScreenshot()
	{
		Date date=new Date();
		String fileName=date.toString().replace(":", "-").replace(" ","-")+".png";
		File sourceFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileHandler.copy(sourceFile, new File(ExtentManager.screenshotFolderPath+fileName));
					
			test.log(Status.INFO, "screenshot->"+test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+fileName));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	/***************************finishing**********************************************/
	
	@AfterMethod
	public void ending()
	{
		if(extent!=null)
		{
			extent.flush();
		}
		
		if(driver!=null)
		{
			test.log(Status.INFO, "closing the browser");
			driver.close();
		}
		
	}
	
	
	public void waitForPageToLoad()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String status = (String)js.executeScript("return document.readyState");
		test.log(Status.INFO, "waiting the page to completely load");
		while(!status.equalsIgnoreCase("complete"))
		{
			try {
				Thread.sleep(3000);
				status = (String)js.executeScript("return document.readyState");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public void timeToWait(int timeInSeconds)
	{
		try {
			Thread.sleep(timeInSeconds*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***********************************App methods****************************/
	
	public void zohoLogin(String userName,String password)
	{
		driver.get(pro.getProperty("zohoHomePage"));
		driver.findElement(By.xpath(pro.getProperty("zohoLogin"))).click();
		test.log(Status.INFO, "login button clicked");
		waitForPageToLoad();
		type("zohoUserName",userName);
		driver.findElement(By.xpath(pro.getProperty("userNameNext"))).click();
		type("password",password);
		driver.findElement(By.xpath(pro.getProperty("singInButton"))).click();
		waitForPageToLoad();
		driver.findElement(By.xpath(pro.getProperty("crm"))).click();
		waitForPageToLoad();
		
		
	}
	
	public void zohoLogout()
	{
		click("zohoSignOut1");
		waitForPageToLoad();
		click("zohoSignOut2");
	}
	
}
