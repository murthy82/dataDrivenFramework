package core.ddf.dev.base;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
	
	public static String screenshotFolderPath;

	private static ExtentHtmlReporter htmlReporter;
	private static ExtentReports extent;
    
    public static ExtentReports getInstance() {
    	
    	if (extent == null)
    	{
    		Date date = new Date();
    		String stringDate=date.toString().replace(":", "-").replace(" ", "-");
    		String reportsFolderPath=System.getProperty("user.dir")+"\\reports\\"+stringDate;
    		screenshotFolderPath=reportsFolderPath+"\\screenshots\\";
    		File file=new File(screenshotFolderPath);
    		file.mkdirs();
    		htmlReporter = new ExtentHtmlReporter(reportsFolderPath+"\\"+stringDate+".html");
            htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
            htmlReporter.config().setChartVisibilityOnOpen(true);
            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setDocumentTitle("automation reports");
            htmlReporter.config().setEncoding("utf-8");
            htmlReporter.config().setReportName("functionality testing");
            
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
              		
    		    	
    	}
    	
    	return extent;
    }
    
}
