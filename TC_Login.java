import java.text.SimpleDateFormat;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.ListDocument.List;

public class TC_Login {
	//****** Variables declaration *******
	static AndroidDriver <MobileElement> driverApp;	
	static String testCase;
	static String testDataFile;
	static String testDataTab;
	static String dateTime;
	static String testResultFilename;
	static String deviceName;
	static String platformName;
	static String appPackage;
	static String appActivity;
	static String result;
	static String fileName;
	static int countDesCapTab;
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException { //Main method
		//The following variables are for testing in DEV MODE. Comment it when testing in production
		testCase = "TC_Login"; 		
		testDataFile = "C:\\TA\\TD\\TestData.xlsx";				
		testDataTab = "DesCap";	// defined by tester to indicate which tab in Test Data to refer to
		dateTime = new SimpleDateFormat("yyyyMMdd_HHmm'.html'").format(new Date());
		testResultFilename = "c://TA//TR//"+testCase+"_"+dateTime;
		fileName = testCase+"_"+dateTime;		
		//The following variables are for testing in Production. Comment it when testing in DEV MODE
		/*testCase = args[0];
		testDataFile = args[1];			
		testDataTab = args[2];;		
		dateTime = new SimpleDateFormat("yyyyMMdd_HHmm'.xlsx'").format(new Date());
		testResultFilename = "c://TA//TR//"+testCase+"_"+dateTime;
		*/
		//The following setups call to read Test Data file for Desired Capabilities' definition
		FileInputStream desCap = new FileInputStream(testDataFile);		
	 	XSSFWorkbook wbDesCap = new XSSFWorkbook(desCap);
	 	XSSFSheet tabDesCap = wbDesCap.getSheet(testDataTab);
	 	System.out.println("No. of rows with data in DesCap tab : " + tabDesCap.getLastRowNum());
		for(countDesCapTab = 1; countDesCapTab <= tabDesCap.getLastRowNum(); countDesCapTab++) { //desCap tab loop        			
			XSSFRow currentrowDesCap = tabDesCap.getRow(countDesCapTab); //current row being read
			deviceName = currentrowDesCap.getCell(0).toString();
            platformName = currentrowDesCap.getCell(1).toString();	
            appPackage = currentrowDesCap.getCell(2).toString();	            
		    appActivity = currentrowDesCap.getCell(3).toString();	
		    DesCap desCapObj = new DesCap();
			desCapObj.setDesCap(deviceName, platformName, appPackage, appActivity);			
		}// end desCap tab loop	   
	}// end main method
	static void startScreen(DesiredCapabilities dc, AndroidDriver<MobileElement> driverApp) throws MalformedURLException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException, ClassNotFoundException {
		Page_SplashScreen.clickStartNow(dc, driverApp);	
		Page_LoginSignUp.clickLogin(dc, driverApp);
	}
	static void verification(String loginVerification) { //verify login status (successful / fail)
		if(loginVerification.equals("Home")) {
			result = "PASS";
			createTestResult(result);
		} 
		else {
			result = "FAIL";
			createTestResult(result);
		}		
	}// end verification
	static void createTestResult(String result) {
		File file = new File(testResultFilename);//check whether test result file exist
		if(file.exists()) {
			addRow(testCase, result);
		}
		else { //if test result file not created yet
			createNewResultTable(testCase, result);				
		}	
	}//end createTestResult
	static void createNewResultTable(String testCase, String result) { //construct test result file. Will only be executed if Test Result file absent
		StringBuilder htmlStringBuilder=new StringBuilder();
		//The following create table header
		htmlStringBuilder.append("<html><head><style>table{border-collapse: collapse; border:0px solid black}");
	    htmlStringBuilder.append("th,td {border: 1px solid black;}");
	    htmlStringBuilder.append("table.d {table-layout: fixed; width: 150%;}</style></head><body>");	
	    htmlStringBuilder.append("<h2>"+testCase+": Result</h2>");
	    htmlStringBuilder.append("<table class= 'd' >");
	    htmlStringBuilder.append("<tr><th><b>Test Case</b></th>");
	    htmlStringBuilder.append("<th><b>Expected Result</b></th>");
	    htmlStringBuilder.append("<th><b>Result</b></th></tr>");
	    if(result.equals("PASS")) {
	    	resultPass(htmlStringBuilder.toString(), testCase, result);
	    }
	    else {
	    	resultFail(htmlStringBuilder.toString(), testCase, result);
	    }
	}// end createNewResultTable  
	static void addRow(String testCase, String result) {
		if(result.equals("PASS")) {
	    	resultPass("", testCase, result);
	    }
	    else {
	    	resultFail("", testCase, result);
	    }
	}//end addrow
	static void resultPass(String stringBuilder, String testCase, String result) {
		try {
	        StringBuilder htmlStringBuilder=new StringBuilder();
	        htmlStringBuilder.append("<table class= 'd' >");
	        htmlStringBuilder.append(stringBuilder);
	        htmlStringBuilder.append("<tr><td>"+testCase+"</td>");
		    htmlStringBuilder.append("<td>"+"Home text is displayed"+"</td>");
		    htmlStringBuilder.append("<td>"+result+"</td></tr>");
	        htmlStringBuilder.append("</table></body></html>");
	        WriteToFile(htmlStringBuilder.toString(), fileName);		        
	    } 
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}//end resultPass
	static void resultFail(String stringBuilder, String testCase, String result) {
		try {
			StringBuilder htmlStringBuilder=new StringBuilder();
			htmlStringBuilder.append("<table class= 'd' >");
	        htmlStringBuilder.append(stringBuilder);
	        htmlStringBuilder.append("<tr><td bgcolor = 'red'><p style ='color:white'>"+testCase+"</p></td>");
		    htmlStringBuilder.append("<td bgcolor = 'red'><p style ='color:white'>"+"Home text is displayed"+"</p></td>");
		    htmlStringBuilder.append("<td bgcolor = 'red'><p style ='color:white'>"+result+"</p></td></tr>");
	        htmlStringBuilder.append("</table></body></html>");
	        WriteToFile(htmlStringBuilder.toString(),fileName);		        
	    } 
		catch (IOException e) {
	        e.printStackTrace();
	    }
	}// end resultFail
	static void WriteToFile(String fileContent, String fileName) throws IOException {
	    String projectPath = "C:/TA/TR";
	    String tempFile = projectPath + File.separator+fileName;
	    File file = new File(tempFile);
	    // if file does exists, then delete and create a new file
	          //write to file with OutputStreamWriter
	    //OutputStream outputStream = new FileOutputStream(file);
	    FileOutputStream fos =new FileOutputStream(file, true) ;
	    Writer writer=new OutputStreamWriter(fos);
	    writer.write(fileContent);
	    writer.close();
	}//end Writetofile		
}//end public class 
