package core.ddf.dev.utility;

import java.util.HashMap;
import java.util.Hashtable;

import core.ddf.dev.base.Xls_Reader;



public class DataUtil {
	
		
	public static Object[][] getTestData(Xls_Reader xls,String testCaseName)
	{
		String testSheetTabName="testData";
		int testCaseNameStartingRowNumber=0;
		
		//calculating test case starting row
		while(!(xls.getCellData(testSheetTabName, 0,testCaseNameStartingRowNumber).equals(testCaseName)))
		{
			testCaseNameStartingRowNumber++;
		}
		
		System.out.println(testCaseName+" starting at "+testCaseNameStartingRowNumber);
		
		//calculating no. of rows
		
		int testCaseColumnsStartingRowNumber=testCaseNameStartingRowNumber+1;
		int testCaseDataStartingRowNumber=testCaseNameStartingRowNumber+2;
		
		//calculating columns starting
		int columns=0;
		while(!(xls.getCellData(testSheetTabName,columns,testCaseColumnsStartingRowNumber).equals("")))
		{
			columns++;
		}
		
		System.out.println(testCaseName+" has "+columns+" columns");
		
		//calculating rows of test case
		
		int rows=0;
		while(!(xls.getCellData(testSheetTabName,0,testCaseDataStartingRowNumber+rows).equals("")))
		{
			rows++;
		}
		
		System.out.println(testCaseName+" has "+rows+" rows");
		
		//displaying data
		
		Object[][] data=new Object[rows][1];
		int dataRow =0;
		//HashMap<String,String> table=null;
		Hashtable<String,String> table=null;
		
		for(int rNum=testCaseDataStartingRowNumber;rNum<testCaseDataStartingRowNumber+rows;rNum++)
		{
			//table=new HashMap<String,String>();
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<columns;cNum++)
			{
				String key=xls.getCellData(testSheetTabName, cNum,testCaseColumnsStartingRowNumber);
				String value=xls.getCellData(testSheetTabName, cNum, rNum);
				table.put(key, value);
			}
			
			data[dataRow][0]=table;
			dataRow++;		
			
		}
		
		
		return data;
	}
	
	public static boolean isRunnable(Xls_Reader xls, String testCaseName)
	{
		int rowCount = xls.getRowCount("testCases");
		for(int rNum=2;rNum<=rowCount;rNum++)
		{
			String testName=xls.getCellData("testCases","TCID", rNum);
					{
						if(testName.equalsIgnoreCase(testCaseName))
						{
							String runmode=xls.getCellData("testCases", "runmode", rNum);
							if(runmode.equalsIgnoreCase("n"))
								return true;
							else
								return false;
						}
					}
		}
		
		return false;
	}

}
