package Utilities;


import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import jxl.Sheet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.relevantcodes.extentreports.LogStatus;




public class Common
{
	public static String testName;
	public static int testCaseDataNo;

	public Common() {}

	public static int testCaseexecutionNo = 0;

	public static String getConfigProperty(String keyword) {
		Properties properties = new Properties();
		try
		{
			properties.load(new FileInputStream("./Config/TestConfiguration.properties"));
		}
		catch (FileNotFoundException e)
		{
			writeToLogFile("ERROR", "File Not Found Exception thrown while getting value of " + keyword + " from Test Configuration file");
		}
		catch (IOException e) {
			writeToLogFile("ERROR", "IO Exception thrown while getting value of " + keyword + " from Test Configuration file");
		}
		writeToLogFile("INFO", "Getting value of " + keyword + " from Test Configuration file : " + properties.getProperty(keyword));

		return properties.getProperty(keyword);
	}

	public static void writeToLogFile(String type, String message)
	{
		String t = type.toUpperCase();
		if (t.equalsIgnoreCase("DEBUG"))
		{
			GenericKeywords.logger.debug(message);
		}
		else if (t.equalsIgnoreCase("INFO"))
		{
			GenericKeywords.logger.info(message);
		}
		else if (t.equalsIgnoreCase("WARN"))
		{
			GenericKeywords.logger.warn(message);
		}
		else if (t.equalsIgnoreCase("ERROR"))
		{
			GenericKeywords.logger.error(message);
		}
		else if (t.equalsIgnoreCase("FATAL"))
		{
			GenericKeywords.logger.fatal(message);
		}
		else {
			GenericKeywords.logger.error("Invalid log Type :" + type + ". Unable to log the message.");
		}
	}



	public static void supportSeleniumRC() {}



	public static void parseidentifyByAndlocator(String identifyByAndLocator)
	{
		writeToLogFile("INFO", "Parsing : " + identifyByAndLocator);
		try
		{
			GenericKeywords.locatorDescription = identifyByAndLocator.substring(0, identifyByAndLocator.indexOf("#"));
			identifyByAndLocator = identifyByAndLocator.substring(identifyByAndLocator.indexOf("#") + 1);
		}
		catch (Exception e)
		{
			GenericKeywords.locatorDescription = "";
		}
		finally
		{
			GenericKeywords.identifier = identifyByAndLocator.substring(0, identifyByAndLocator.indexOf("=", 0)).toLowerCase();
			GenericKeywords.locator = identifyByAndLocator.substring(identifyByAndLocator.indexOf("=", 0) + 1);
			writeToLogFile("INFO", "Identify By : " + GenericKeywords.identifier);
			writeToLogFile("INFO", "Locator : " + GenericKeywords.locator);
			writeToLogFile("INFO", "Locator Description : " + GenericKeywords.locatorDescription);
			GenericKeywords.idType = identifierType.valueOf(GenericKeywords.identifier);
		}
	}


	public static void startup()
	{


		try
		{
 			OutputAndLog.createOutputDirectory();

			PropertiesFile.properties();
			loadTestCaseData();      
		}
		catch (Exception e)
		{
			writeToLogFile("INFO", "Startup activities - Done...");
		}
	}


	public static void cleanup()
	{
		try
		{    	
			if (GenericKeywords.getConfigProperty("SendMail(Yes/No)").trim().equalsIgnoreCase("yes"))
			{          
				writeToLogFile("INFO", "<<<<<<<<<<<<<Sending mail...>>>>>>>>>>>>>>>>>");
				Mailing.sendMail();
			}
			if (GenericKeywords.getConfigProperty("SendMsg(Yes/No)").trim().equalsIgnoreCase("yes"))
			{
				Texting.sendMsg();
			}
			if (GenericKeywords.getConfigProperty("VoiceCall(Yes/No)").trim().equalsIgnoreCase("yes"))
			{
				VoiceCall.voiceCall();
			}
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
	}

	public void createZipFileOfReport(String reportPath){
		System.out.println(reportPath);
		File dir = new File(reportPath);
		try {
			System.out.println("Getting all files in "
					+ dir.getCanonicalPath()
					+ " including those in subdirectories");
			List<File> files = (List<File>) FileUtils.listFiles(
					dir, TrueFileFilter.INSTANCE,
					TrueFileFilter.INSTANCE);
			byte[] b;
			java.io.File curdir = new java.io.File(".");
			String strPath = curdir.getCanonicalPath()+"\\JenkinsResults\\";
			FileOutputStream fout = new FileOutputStream(strPath+"\\"
					+ "TestExecutionReport.zip");
			ZipOutputStream zout = new ZipOutputStream(
					new BufferedOutputStream(fout));

			for (int i = 0; i < files.size(); i++) {			
				b = new byte[1024];
				FileInputStream fin = new FileInputStream(
						files.get(i));
				zout.putNextEntry(new ZipEntry(files.get(i)
						.getName()));
				int length;
				while (((length = fin.read(b, 0, 1024))) > 0) {
					zout.write(b, 0, length);
				}
				zout.closeEntry();
				fin.close();				
			}
			zout.close();
			File srcDir = new File(GenericKeywords.outputDirectory);
			File destDir = new File(strPath);
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (Exception e) {
			System.out.println("Exception caught");
		}
	}


	public static void testReporter(String color, String report){
		colorTypes ct = colorTypes.valueOf(color.toLowerCase());
		if (!color.contains("white")){
			GenericKeywords.currentStep += 1;
		}
		switch (ct){
		case green: 
			GenericKeywords.child.log(LogStatus.PASS,"<font color=green>" + GenericKeywords.currentStep + ". " + report + "</font><br/>");writeToLogFile("PASS", "Report step generation success : " + report);System.out.println("green" + GenericKeywords.currentStep); break;
		case blue:  GenericKeywords.child.log(LogStatus.INFO,"<font color=blue>" + GenericKeywords.currentStep + ". " + report + "</font><br/>");writeToLogFile("INFO", "Report step generation success : " + report);System.out.println("blue" + GenericKeywords.currentStep); break;
		case orange:  GenericKeywords.child.log(LogStatus.WARNING,"<font color=orange>" + GenericKeywords.currentStep + ". " + report + "</font><br/>");writeToLogFile("WARN", "Report step generation success : " + report); break;
		case red:  GenericKeywords.child.log(LogStatus.FAIL,"<font color=red>" + GenericKeywords.currentStep + ". " + report + "</font><br/>");writeToLogFile("ERROR", "Report step generation success : " + report);System.out.println("red" + GenericKeywords.currentStep); break;
		case white:  GenericKeywords.child.log(LogStatus.INFO,report);writeToLogFile("WARN", "Report step generation success : " + report);
		}

	}

	public static enum colorTypes
	{
		green, 
		red, 
		blue, 
		orange,  white;
	}

	public static enum browserType
	{
		FIREFOX, 
		INTERNETEXPLORER, 
		CHROME, 
		SAFARI;
	}

	public static enum identifierType
	{
		xpath, 
		name, 
		id, 
		lnktext, 
		partiallinktext, 
		classname, 
		cssselector, 
		tagname,  androiduiautomator;
	}

	public static void screenShot(String filename)
	{
		String scrPath = GenericKeywords.outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try {
			Robot robot = new Robot();
			Rectangle captureSize = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage bufferedImage = robot.createScreenCapture(captureSize);
			File outputfile = new File(scrPath + "\\" + filename + ".png");
			ImageIO.write(bufferedImage, "png", outputfile);
			GenericKeywords.writeToLogFile("INFO", "Taken screenshot of failing screen");
		}
		catch (AWTException e) {
			GenericKeywords.writeToLogFile("ERROR", "AWT Exception : While taking screenshot of the failing test case");
		} catch (IOException e) {
			GenericKeywords.writeToLogFile("ERROR", "IO Exception : While taking screenshot of the failing test case");
		}
	}




	public static void testFailed() {

	}



	public static void captureScreenShot(String filename){
		File scrFile = null;
		String scrPath = GenericKeywords.outputDirectory + "\\Screenshots";
		File file = new File(scrPath);
		file.mkdir();
		try{
			//scrFile = (File)((RemoteWebDriver) GenericKeywords.driver).getScreenshotAs(OutputType.FILE);
			scrFile = (File) ((TakesScreenshot) GenericKeywords.driver).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File(scrPath + "\\" + filename + ".png"));
		}catch (Exception e){
			testReporter("Red", e.toString()); return;

		}finally{
			if (scrFile == null) {
				System.out.println("This WebDriver does not support screenshots");
				return;
			}
		}
	}



	public static void testStepFailed(String errMessage){
		GenericKeywords.testFailure = true;
		GenericKeywords.failureNo += 1;

		writeToLogFile("Error", errMessage);
		testReporter("Red", errMessage);
		if (!GenericKeywords.windowreadyStateStatus){
			screenShot("TestFailure" + GenericKeywords.failureNo);
			GenericKeywords.windowreadyStateStatus = true;
		}else{
			captureScreenShot("TestFailure" + GenericKeywords.failureNo);
		}	  
		String pathAndFile = GenericKeywords.outputDirectory + "\\Screenshots\\TestFailure" + GenericKeywords.failureNo+ ".png";
		GenericKeywords.child.log(LogStatus.FAIL, "Check ScreenShot Below: " + GenericKeywords.child.addScreenCapture(pathAndFile));	  
		if (getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)").toUpperCase().contains("YES")){

			GenericKeywords.testCaseExecutionStatus = true;
			GenericKeywords.elementLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			GenericKeywords.textLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			GenericKeywords.pageLoadWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
			GenericKeywords.implicitlyWaitTime = Integer.parseInt(getConfigProperty("OverideTimeoutOnFailure"));
		}else if (getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)").toUpperCase().contains("NO")){
			testFailed();
		}else{
			testReporter("Red", "Invalid option 'ExecuteRemainingStepsOnFailure(Yes/No)--'" + getConfigProperty("ExecuteRemainingStepsOnFailure(Yes/No)"));
			testFailed();
		}	   
	}

	public static void testStepPassed(String errMessage)
	{
		writeToLogFile("Info", errMessage);
		testReporter("Green", errMessage);
	}

	public static void testStepInfo(String errMessage) {
		//GenericKeywords.child.log(LogStatus.INFO, errMessage);
		writeToLogFile("Info", errMessage);
		testReporter("Blue", errMessage);
	}

	public static void testStepInfoStart(String testDataSet) {
		GenericKeywords.child = GenericKeywords.extent.startTest(testDataSet);
		GenericKeywords.child.log(LogStatus.INFO, "########### Start of Test Case Data Set: "+testDataSet + " ###########");	    
	}


	public static void testStepInfoEnd(String testDataSet) {
		GenericKeywords.child.log(LogStatus.INFO, "########### End of Test Case Data Set: "+testDataSet + " ###########");
		GenericKeywords.parent
		.appendChild(GenericKeywords.child);
	}

	public static void reportStart(String strName,String testCaseDescription) {
		GenericKeywords.testCaseExecutionStatus = false;
		GenericKeywords.elementLoadWaitTime = Integer.parseInt(getConfigProperty("ElementLoadWaitTime").toString().trim());
		GenericKeywords.textLoadWaitTime = Integer.parseInt(getConfigProperty("TextLoadWaitTime").toString().trim());
		GenericKeywords.pageLoadWaitTime = Integer.parseInt(getConfigProperty("PageLoadWaitTime").toString().trim());
		GenericKeywords.implicitlyWaitTime = Integer.parseInt(getConfigProperty("ImplicitlyWaitTime").toString().trim());
		writeToLogFile("INFO", "Element time out set");
		GenericKeywords.writeToLogFile("INFO", "##### Start of Test Case : " + testCaseDescription + " #####");        
		for (String testCaseName : GenericKeywords.testCaseNames)
		{
			if (testCaseName.equals(((String)PropertiesFile.testCases.get(testCaseexecutionNo)).trim()))
			{
				writeToLogFile("INFO", "Test Case Name: " + testCaseName);
				updateTestDataSet(testCaseName);
				testCaseexecutionNo += 1;
				break;
			}
			testCaseDataNo += 1;
		}
		GenericKeywords.parent = GenericKeywords.extent.startTest(strName,"<font size=4 color=black>" +testCaseDescription+ "</font><br/>");
	}



	public static void updateTestDataSet(String testCaseName){
		useExcelSheet(getConfigProperty("TestDataFile"), 1);
		Sheet readsheet = DataDriver.w.getSheet(0);
		String testCaseDataSet = null;
		String executionFlag = null;
		Boolean flag = Boolean.valueOf(false);
		for (int caseRow = 0; caseRow < readsheet.getRows(); caseRow++) {
			GenericKeywords.testCaseDataSets.clear();
			if (testCaseName.equals(readsheet.getCell(1, caseRow).getContents()))
			{


				for (int DataRow = caseRow; DataRow < readsheet.getRows(); DataRow++)
				{
					GenericKeywords.testCaseRow = caseRow + 1;
					testCaseDataSet = readsheet.getCell(1, DataRow).getContents();
					writeToLogFile("INFO", "Test Data Set Name: " + testCaseDataSet);
					executionFlag = readsheet.getCell(2, DataRow).getContents();
					writeToLogFile("INFO", "Execution Flag: " + executionFlag);
					if ((testCaseDataSet.startsWith(testCaseName)) && (executionFlag.toUpperCase().equals("YES")))
					{
						GenericKeywords.testCaseDataSets.add(testCaseDataSet);
					} else if (testCaseDataSet.isEmpty())
					{
						flag = Boolean.valueOf(true);
						break;
					}
				}
				if (flag.booleanValue()) {
					break;
				}
			}
		}
	}




	public static void embedScreenshot(String colour, String pathAndFile){   
		GenericKeywords.child.log(LogStatus.INFO, "Manual Verification Point: " + GenericKeywords.child.addScreenCapture(pathAndFile+ ".png"));    
	}


	public static void takeScreenshot(String comment){
		GenericKeywords.screenshotNo += 1;        
		captureScreenShot("Screenshot" + GenericKeywords.screenshotNo);
		embedScreenshot("orange", "./Screenshots" + "/Screenshot" + GenericKeywords.screenshotNo);
	}


	public static void useExcelSheet(String pathOfExcel, int sheetNumber)
	{
		DataDriver.useExcelSheet(pathOfExcel, sheetNumber);
	}





	public static void closeExcelSheet() {}





	public static String retrieve(String Label){
		return DataDriver.retrieve(GenericKeywords.testCaseRow, GenericKeywords.testCaseDataRow, Label);
	}

	public static int returnRowNumber(String Label)
	{
		return DataDriver.returnRowNo(2, Label);
	}

	public static void loadTestCaseData() {
		useExcelSheet(getConfigProperty("TestDataFile"), 1);

		Sheet readsheet = DataDriver.w.getSheet(0);
		for (int i = 0; i < readsheet.getRows(); i++) {
			String testCaseName = readsheet.getCell(1, i).getContents();
			GenericKeywords.testCaseNames.add(testCaseName);
		}
	}


	public static String retrieveExceptionMessage(Integer exceptionNumber){
		String exceptionMessage = null;
		String exceptionNo = exceptionNumber.toString();
		Sheet readsheet = DataDriver.w.getSheet(1);
		for (int i = 0; i < readsheet.getRows(); i++) {
			String testCaseName = readsheet.getCell(0, i).getContents();
			if (testCaseName.equals(exceptionNo))
			{
				exceptionMessage = readsheet.getCell(4, i).getContents();
				writeToLogFile("INFO", "Exception Message: " + exceptionMessage);
				break;
			}
		}
		return exceptionMessage;
	}
}
