package core.ddf.testCases;

import java.io.IOException;
import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import core.ddf.base.BaseTest;
import core.ddf.dev.base.Xls_Reader;
import core.ddf.dev.utility.DataUtil;


public class FirstTest extends BaseTest{
	
	String testCaseName="testB";
	String testSheetTabName="testData";
	
	@Test(dataProvider="getData")
	public void sample(Hashtable<String,String> data) throws IOException {
	  
	  test = extent.createTest("sample test");
	 
	  System.out.println(data.get("runmode"));
		
		  if(DataUtil.isRunnable(xls, testCaseName) || data.get("runmode").equalsIgnoreCase("n"))
		  {
			  test.log(Status.SKIP,"skiping "+testCaseName+" because the run mode is 'N'");
			  throw new SkipException("run mode is no");
		  
		  }
		 
		  test.log(Status.INFO, "this sample test");
				
		  test.log(Status.INFO,"opening chrome browser"); openBrowser("chrome");
		  test.log(Status.INFO,"chrome browser opened"); navigate("facebookUrl");
		  test.log(Status.INFO,"opening facebook");
		  type("userNameXpath","mallimurthy2@gmail.com");
		  test.log(Status.INFO,"entering username into facebook");
		  type("passwordXpath","Sunrise0");
		  test.log(Status.INFO,"entering password into facebook");
		  click("loginButtonXpath");
		  test.log(Status.INFO,"clicking into login button in facebook");
		  takesScreenshot();
		 
	  
  }
  
  
	@DataProvider
	public Object[][] getData()
	{	
		return DataUtil.getTestData(xls, testCaseName);
				
	}
  
  
  
}
