package TestCases;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import PageMethods.AdactinApplication;
import PageMethods.AdactinHomePage;
import PageMethods.ConfirmationPage;
import PageMethods.ForgetPassword;
import PageMethods.NewUserRegistration;
import PageMethods.SearchHotel;
import PageMethods.SelectHotel;
import Utilities.Common;
import Utilities.GenericKeywords;
import Utilities.XmlValidation;

@Listeners({ Utilities.TestListener.class })
public class TestCases extends Common {

	public static int count = 1;
	private AdactinApplication adactinApplication;
	private AdactinHomePage adactinHomePage;
	private SearchHotel searchHotel;
	private SelectHotel selectHotel;
	private ForgetPassword ForgetPswd;
	
    private ConfirmationPage confirmpage;
	private NewUserRegistration Registrationpage;
	
	
	@BeforeClass
	public void start(){	
		GenericKeywords.extent.loadConfig(new File("D:\\my test\\SWAUTorg-master\\config\\extent-config.xml"));
		
	}
	
	
	public void testStart(String strName,String testCaseDescription) {
		GenericKeywords.testFailure = false;
		GenericKeywords.currentStep = 0;
		reportStart(strName,testCaseDescription);
		adactinApplication=new AdactinApplication();
		adactinHomePage=adactinApplication.openAdactinApplication();			
	}

	public void testEnd() {
		try {
			adactinApplication.close();			
		} catch (Exception e) {
			System.out.println("Expception : " + e.getMessage());
		}finally{
			GenericKeywords.extent.endTest(GenericKeywords.parent);
			GenericKeywords.extent.flush();
		}
	}


	@Test(alwaysRun = true)
	public void TC_001() {
		String strName = new Exception().getStackTrace()[0].getMethodName();
		testStart(strName,"Login to Adactin Application");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);
			
			searchHotel = adactinHomePage.Login();			
			if(searchHotel.verifyLoginpage())
				searchHotel.logoutFromApp();
			
			testStepInfoEnd(testDataSet);
			}
		testEnd();

	}
	
	
	@Test(alwaysRun = true)
	public void TC_002() {
		String strName = new Exception().getStackTrace()[0].getMethodName();
		testStart(strName,"Search a hotel");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);
			
			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();
			
			testStepInfoEnd(testDataSet);
			}
		testEnd();

	}
	

	@Test(alwaysRun = true)
	public void TC_003() {
		String strName = new Exception().getStackTrace()[0].getMethodName();
		testStart(strName,"Book a hotel");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet );
			
			searchHotel = adactinHomePage.Login();			
			selectHotel=searchHotel.BookHotel();
			selectHotel.Selecthotel();
			
			testStepInfoEnd(testDataSet);
			}
		testEnd();

	}
	
	
	@Test(alwaysRun = true)
	public void TC_004() {
		String strName = new Exception().getStackTrace()[0].getMethodName();
		testStart(strName,"Forget Password functionality");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);
			
			ForgetPswd=adactinHomePage.forgetpassword();
			
			testStepInfoEnd(testDataSet );
			}
		testEnd();

	}
	
	
	@Test(alwaysRun = true)
	public void TC_005() {
		String strName = new Exception().getStackTrace()[0].getMethodName();
		testStart(strName,"New User Register functionality");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);
			
			Registrationpage=adactinHomePage.UserRegistration();
			
			testStepInfoEnd(testDataSet );
			}
		testEnd();

	}
	
	
	@Test(alwaysRun = true)
	public void TC_006() {
		String strName = new Exception().getStackTrace()[0].getMethodName();
		testStart(strName,"XML Validation");
				
		
		for (String testDataSet : GenericKeywords.testCaseDataSets) {
			GenericKeywords.testCaseDataRow = returnRowNumber(testDataSet);
			testStepInfoStart(testDataSet);
			
			XmlValidation.SoapCollateralPositionData();	
			
			//String text;
			try {
				XmlValidation.SoapCollateralPositionData();	
				XmlValidation.validateXml();			
			} catch (Exception e) {
				
			} 
						
			testStepInfoEnd(testDataSet);
			}
		testEnd();

	}
	
}
