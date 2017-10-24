package Utilities;

import io.appium.java_client.AppiumDriver;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;













public class GenericKeywords
  extends Common
{
  public static WebDriver driver;
  public static ExtentReports extent;
  public static ExtentTest parent;
  public static ExtentTest child;
  public static String identifier;
  public static String locator;
  public static String locatorDescription;
  public static String outputDirectory;
  public static String currentExcelBook;
  public static String mainWindow;
  public static String currentBrowser = "";
  public static Logger logger;
  public static int currentTestCaseNumber;
  public static int currentExcelSheet;
  public static int currentStep;
  public static int failureNo;
  public static int screenshotNo; public static int rowCount; public static int colCount; public static Common.identifierType idType; public static WebElement webElement; public static boolean testFailure = false;
  public static boolean loadFailure = false;
  public static int temp = 1;
  public static String testStatus = "";
  public static int testCaseDataRow; public static int textLoadWaitTime; public static int elementLoadWaitTime; public static int implicitlyWaitTime; public static int pageLoadWaitTime = 0;
  public static int testCaseRow;
  public static final String XSLT_FILE_CoverPage = ".\\xsltfiles\\CoverPage.xslt";
  public static final String XSLT_FILE_SummaryPage = ".\\xsltfiles\\SummaryReport.xslt";
  public static final String XSLT_FILE_PdfPage = ".\\data\\PdfReport.xslt";
  public static final ArrayList<String> testCaseNames = new ArrayList();
  public static ArrayList<String> testCaseDataSets = new ArrayList();
  public static boolean windowreadyStateStatus = true;
  public static int testSuccessCount = 0;
  public static int testFailureCount = 0;
  public static int testSkippedCount = 0;
  public static String timeStamp = "";
  public static boolean testCaseExecutionStatus = false;
  public static boolean webElementIsPresent = false;
  


  public GenericKeywords() {}
  


  public static enum platFormName
  {
    IOS, 
    ANDROID;
  }
  
  public static void openApp() {
    String deviceName = getConfigProperty("DeviceName").toString().trim();
    String platForm = getConfigProperty("PlatFormName").toString().trim();
    String platFormVersion = getConfigProperty("PlatformVersion").toString().trim();
    String appName = getConfigProperty("AppName").toString().trim();
    

    String ip = getConfigProperty("IpAddress").toString().trim();
    String portNumber = getConfigProperty("PortNumber").toString().trim();
    platFormName b = platFormName.valueOf(platForm.toUpperCase());
    

    writeToLogFile("INFO", "Opening " + appName + " Application...");
    try
    {
      DesiredCapabilities capabilities = new DesiredCapabilities();
      capabilities.setCapability("newCommandTimeout", getConfigProperty("AppiumTimeOut").toString().trim());
      switch (b)
      {
      case IOS: 
        break;
      
      case ANDROID: 
        capabilities.setCapability("platformName", platForm);
        capabilities.setCapability("platformVersion", platFormVersion);
        capabilities.setCapability("deviceName", deviceName);
        driver = new AppiumDriver(new URL("http://" + ip + ":" + portNumber + "/wd/hub"), capabilities);
      }
      
      


      elementLoadWaitTime = Integer.parseInt(getConfigProperty("ElementLoadWaitTime").toString().trim());
      textLoadWaitTime = Integer.parseInt(getConfigProperty("TextLoadWaitTime").toString().trim());
      pageLoadWaitTime = Integer.parseInt(getConfigProperty("PageLoadWaitTime").toString().trim());
      implicitlyWaitTime = Integer.parseInt(getConfigProperty("ImplicitlyWaitTime").toString().trim());
      driver.manage().timeouts().implicitlyWait(Integer.parseInt(getConfigProperty("ImplicitlyWaitTime")), TimeUnit.SECONDS);
      
      writeToLogFile("INFO", "Time out set");
      writeToLogFile("INFO", "Application: Open Successful: " + appName);
      testReporter("Green", "Open Application: ''" + appName + "''");

    }
    catch (TimeoutException e)
    {
      testStepFailed("Page fail to load within in " + getConfigProperty("pageLoadWaitTime") + " seconds");
    }
    catch (Exception e)
    {
      writeToLogFile("ERROR", "Browser: Open Failure/Navigation cancelled, please check the application window.");
      writeToLogFile("Error", e.toString());
      testReporter("Red", e.toString());
      testStepFailed("Open App : AppName");
    }
  }
  




































































  public static void navigateTo(String url)
  {
    try
    {
      writeToLogFile("INFO", "Navigating to URL : " + url);
      driver.get(url);
      writeToLogFile("INFO", "Navigation Successful: " + url);
      testReporter("Green", "Navigate to: " + url);
    }
    catch (TimeoutException e)
    {
      testStepFailed("Page fail to load within in " + pageLoadWaitTime + " seconds");
    }
    catch (Exception e)
    {
      writeToLogFile("ERROR", "Browser: Open Failure/Navigation cancelled, please check the application window.");
      testStepFailed("Navigate to: " + url);
    }
  }
  

  public static void closeBrowser()
  {
    try
    {
      writeToLogFile("INFO", "Closing Browser...");
      deleteAllCookies();
      if ((currentBrowser.contains("chrome")) || (currentBrowser.contains("safari")))
      {

        driver.quit();

      }
      else
      {
        driver.close();
      }
      

      writeToLogFile("INFO", "Browser: Close Successful");
      testReporter("Green", "Close Browser");
    }
    catch (Exception e)
    {
      writeToLogFile("ERROR", "Browser: Close Failure");
      testStepFailed("Close Browser");
    }
  }
  
  public static void identifyBy(String identifier) {
    Common.identifierType i = Common.identifierType.valueOf(identifier);
    switch (i) {
    case androiduiautomator: 
      webElement = driver.findElement(By.xpath(locator));
      break;
    case cssselector: 
      webElement = driver.findElement(By.id(locator));
      break;
    case classname: 
      webElement = driver.findElement(By.name(locator));
      break;
    case id: 
      webElement = driver.findElement(By.linkText(locator));
      break;
    case lnktext: 
      webElement = driver.findElement(By.partialLinkText(locator));
      break;
    case name: 
      webElement = driver.findElement(By.className(locator));
      break;
    case partiallinktext: 
      webElement = driver.findElement(By.cssSelector(locator));
      break;
    case tagname: 
      webElement = driver.findElement(By.tagName(locator));
      break;
    case xpath: 
      
    default: 
      writeToLogFile("Error", "Element not found '" + locator + "'");
    }
    
  }
  

  public static void waitForElement(String objName)
  {
    waitForElement(objName, elementLoadWaitTime);
  }
  
  public static void waitForElement(String objectName, int timeout) {
    try {
      webElementIsPresent = false;
      driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
      
      for (int i = 1; i <= timeout; i++)
      {

        try
        {

          findWebElement(objectName);
          webElementIsPresent = true;

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);
        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);

        }
        catch (NoSuchElementException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e) {
          waitTime(1L);
        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (WebDriverException e) {
          waitTime(1L);
        }
        
        if (i == timeout)
        {
          testStepFailed(locatorDescription + " element not found in '" + timeout + "' seconds timeout ");
        }
      }
    }
    catch (Exception e)
    {
      testStepFailed("Exception error '" + e.toString() + "'");

    }
    finally
    {

      driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
    }
  }
  


  public static void verifyElementText(String objectLocator, String expectedText)
  {
    waitForElement(objectLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          if (webElement.getText().trim().equalsIgnoreCase(expectedText.trim()))
          {
            writeToLogFile("INFO", "verify if the " + locatorDescription + " element contains text '" + expectedText);
            testReporter("Green", "verify if the " + locatorDescription + " element contains text '" + expectedText);

          }
          else
          {
            testStepFailed("verify if the " + locatorDescription + " element contains text '" + expectedText);
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  
  public static void findWebElement(String objectLocator)
  {
    parseidentifyByAndlocator(objectLocator);
    identifyBy(identifier);
  }
  

  public static void typeIn(String objectLocator, String inputValue)
  {
    waitForElementToDisplay(objectLocator, elementLoadWaitTime);
    if (webElementIsPresent)
    {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {

        try
        {
          webElement.click();
          webElement.clear();
          webElement.sendKeys(new CharSequence[] { inputValue });
          writeToLogFile("INFO", "Typing '" + inputValue + "' in : " + locatorDescription);
          testReporter("Green", "Type '" + inputValue + "' in : " + locatorDescription);


        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  
  public static void sendkeys(String Locator, String inputValue)
  {
    try
    {
      driver.findElement(By.xpath(Locator)).clear();
      driver.findElement(By.xpath(Locator)).sendKeys(new CharSequence[] { inputValue });
      writeToLogFile("INFO", "Typing '" + inputValue + "' in : " + locatorDescription);
      testReporter("Green", "Type '" + inputValue + "' in : " + locatorDescription);

    }
    catch (Exception e)
    {

      writeToLogFile("ERROR", "Typing '" + inputValue + "' in : " + locatorDescription);
      testStepFailed("Element is not in editable state '" + locatorDescription);
    }
  }
  



  public static void refreshPage()
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try
      {
        driver.navigate().refresh();
        writeToLogFile("INFO", "Sucessfully Refreshed browser");


      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      if (i == elementLoadWaitTime)
      {
        testStepFailed("Error refreshing browser");
      }
    }
  }
  
  public static void clickOnBackButton()
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try
      {
        driver.navigate().back();
        writeToLogFile("INFO", "Sucessfully moved to 'Back' page");

      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      if (i == elementLoadWaitTime)
      {
        testStepFailed("Error moving to 'Back' page");
      }
    }
  }
  
  public static void clickOnForwardButton()
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try
      {
        driver.navigate().forward();
        writeToLogFile("INFO", "Sucessfully moved to 'Forward' page");

      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      if (i == elementLoadWaitTime)
      {
        testStepFailed("Error moving to 'Forward' page");
      }
    }
  }
  


  public static void alertOk()
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try
      {
        Alert alert = driver.switchTo().alert();
        alert.accept();
        writeToLogFile("INFO", "Sucessfully clicked on Alert OK button");
        testReporter("Green", "Click on Alert OK button");

      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      if (i == elementLoadWaitTime)
      {
        testStepFailed("Click on Alert OK button");
      }
    }
  }
  


  public static void alertCancel()
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {

      try
      {
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        writeToLogFile("INFO", "Sucessfully clicked on Alert Cancel button");
        testReporter("Green", "Click on Alert Cancel button");

      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      if (i == elementLoadWaitTime)
      {
        testStepFailed("Click on Alert Cancel button");
      }
    }
  }
  
  public static boolean isAlertWindowPresent()
  {
    try
    {
      driver.switchTo().alert();
      return true;
    }
    catch (Exception E) {}
    

    return false;
  }
  


  public static void verifyElement(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent)
    {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          writeToLogFile("INFO", "Element '" + locatorDescription + "' is present as expected");
          testReporter("Green", "Verify Element : " + locatorDescription);

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        if (i == elementLoadWaitTime)
        {
          testStepFailed("Verify Element : " + locatorDescription);
        }
      }
    }
  }
  




  public static void mouseOver(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent)
    {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          Actions builder = new Actions(driver);
          builder.moveToElement(webElement).build().perform();
          writeToLogFile("INFO", "Successfully moved mouse over '" + locatorDescription + "'");
          testReporter("Green", "Move the mouse over '" + locatorDescription + "'");

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        if (i == elementLoadWaitTime)
        {
          testStepFailed("Move the mouse over '" + locatorDescription + "'");
        }
      }
    }
  }
  


  public static void waitTime(long waittime)
  {
    writeToLogFile("INFO", "Waiting for " + waittime + " seconds...");
    try {
      Thread.sleep(waittime * 1000L);
    }
    catch (InterruptedException e)
    {
      writeToLogFile("ERROR", "Thread.sleep operation failed, during waitTime function call");
    }
  }
  
  public static void selectFromDropdown(String objLocator, String valueToSelect)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      try
      {
        driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
        
        for (int i = 1; i <= elementLoadWaitTime; i++)
        {
          try
          {
            webElement.click();
            Select select = new Select(webElement);
            select.selectByVisibleText(valueToSelect);
            
            writeToLogFile("INFO", "Successfully Selected " + valueToSelect + " from : " + locatorDescription);
            testReporter("Green", "Select '" + valueToSelect + "' from : " + locatorDescription);


          }
          catch (InvalidSelectorException e)
          {

            waitTime(1L);

          }
          catch (StaleElementReferenceException e)
          {
            waitTime(1L);
          }
          catch (ElementNotVisibleException e)
          {
            waitTime(1L);

          }
          catch (UnreachableBrowserException e)
          {
            testStepFailed(e.toString());
          }
          catch (UnhandledAlertException e)
          {
            waitTime(1L);
          }
          catch (WebDriverException e)
          {
            waitTime(1L);
          }
          catch (Exception e)
          {
            testStepFailed("Exception Error '" + e.toString() + "'");
          }
          if (i == elementLoadWaitTime)
          {
            writeToLogFile("Error", "Could not select '" + valueToSelect + "' from : " + locatorDescription);
            testStepFailed("Select '" + valueToSelect + "' from : " + locatorDescription);
          }
          
        }
      }
      catch (Exception e)
      {
        testStepFailed(e.toString());

      }
      finally
      {
        driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
      }
    }
  }
  



  public static void selectFromDropdown(String objLocator, int indexNumber)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      try {
        driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
        
        for (int i = 1; i <= elementLoadWaitTime; i++)
        {
          try
          {
            webElement.click();
            Select select = new Select(webElement);
            select.selectByIndex(indexNumber);
            
            writeToLogFile("INFO", "Successfully Selected " + indexNumber + " option from : " + locatorDescription);
            testReporter("Green", "Select '" + indexNumber + "' option from : " + locatorDescription);


          }
          catch (InvalidSelectorException e)
          {

            waitTime(1L);

          }
          catch (StaleElementReferenceException e)
          {
            waitTime(1L);
          }
          catch (ElementNotVisibleException e)
          {
            waitTime(1L);

          }
          catch (UnreachableBrowserException e)
          {
            testStepFailed(e.toString());
          }
          catch (UnhandledAlertException e)
          {
            waitTime(1L);
          }
          catch (WebDriverException e)
          {
            waitTime(1L);
          }
          catch (Exception e)
          {
            testStepFailed("Exception Error '" + e.toString() + "'");
          }
          if (i == elementLoadWaitTime)
          {
            writeToLogFile("Error", "Could not select '" + indexNumber + "' from : " + locatorDescription);
            testStepFailed("Select '" + indexNumber + "' from : " + locatorDescription);
          }
          
        }
      }
      catch (Exception e)
      {
        testStepFailed(e.toString());

      }
      finally
      {
        driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
      }
    }
  }
  
  public static void verifyPageTitle(String partialTitle)
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try {
        if (driver.getTitle().contains(partialTitle))
        {
          writeToLogFile("INFO", "'" + partialTitle + "' is present in the page title : " + driver.getTitle());
          testReporter("Green", "Verify if the page title contains text '" + partialTitle + "'");
        }
        else
        {
          writeToLogFile("ERROR", "'" + partialTitle + "' is not present in the page title : " + driver.getTitle());
          testStepFailed("Verify if the page title contains text '" + partialTitle + "'");
        }
        
      }
      catch (InvalidSelectorException e)
      {
        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      
      if (i == elementLoadWaitTime)
      {
        testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
      }
    }
  }
  



  public static void verifyLinkText(String txt)
  {
    try
    {
      driver.findElement(By.linkText(txt));
      writeToLogFile("INFO", "The link '" + txt + "' is present");
      testReporter("Green", "Verify if link '" + txt + "' is present");

    }
    catch (Exception e)
    {

      writeToLogFile("ERROR", "The link '" + txt + "' is not present");
      testStepFailed("Verify if link '" + txt + "' is present");
    }
  }
  

  public static void verifyAttribute(String objLocator, String attributeType, String expectedAttributeValue)
  {
    waitForElement(objLocator, elementLoadWaitTime);
    if (webElementIsPresent)
    {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          String attribute = "";
          attribute = webElement.getAttribute(attributeType);
          
          if (!attribute.trim().equalsIgnoreCase(expectedAttributeValue.trim()))
          {

            writeToLogFile("Error", "Error retrieving '" + attribute + "' and/or value '" + expectedAttributeValue + "'");
            testStepFailed("Verify attribute of '" + attribute + "' for value '" + expectedAttributeValue + "'");

          }
          else
          {
            writeToLogFile("INFO", "'" + attribute + "' has the value '" + expectedAttributeValue + "' as expected");
            testReporter("Green", "Verify attribute of '" + attribute + "' for value '" + expectedAttributeValue + "'");
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  

  public static void waitForText(String txt)
  {
    waitForText(txt, textLoadWaitTime);
  }
  

  public static void waitForText(String txt, int timeout)
  {
    for (int second = 0; second < timeout; second++)
    {




      if (second == timeout - 1) {
        writeToLogFile("Error", "Text is not found ' " + txt + "'");
        testStepFailed("The text '" + txt + "' is not found within " + textLoadWaitTime + " seconds timeout");
        break;
      }
      try
      {
        if (driver.getPageSource().contains(txt)) {
          writeToLogFile("INFO", "Text: '" + txt + "' is present");
        }
      }
      catch (Exception localException)
      {
        try {
          Thread.sleep(1000L);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
  


  public static void clickOn(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent)
    {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          webElement.click();
          writeToLogFile("INFO", "Successfuly clicked on " + locatorDescription);
          testReporter("Green", "Click on :" + locatorDescription);

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  
  public static void switchToWindow(String objTitle)
  {
    try
    {
      Set<String> AllHandle = driver.getWindowHandles();
      for (String han : AllHandle) {
        driver.switchTo().window(han);
        try {
          Thread.sleep(2000L);
        }
        catch (InterruptedException e) {
          e.printStackTrace();
        }
        String getTitle = driver.getTitle();
        if (getTitle.contains(objTitle)) {
          writeToLogFile("INFO", "Switched to window..." + objTitle);
          testReporter("Green", "Switch to window :" + objTitle);
          
          break;
        }
        
      }
      
    }
    catch (Exception e)
    {
      writeToLogFile("Error", "Error switching to window..." + objTitle);
      testStepFailed("Switch to window :" + objTitle);
    }
  }
  

  public static void dragAndDrop(String sourceObjLocator, String destinationObjLocator)
  {
    String sourceDesc = "";String destinationDesc = "";
    try
    {
      findWebElement(sourceObjLocator);
      WebElement source = webElement;
      sourceDesc = locatorDescription;
      findWebElement(destinationObjLocator);
      WebElement target = webElement;
      destinationDesc = locatorDescription;
      new Actions(driver).dragAndDrop(source, target).perform();
      writeToLogFile("Info", "Drag and drop successful");
      testReporter("Green", "Drag '" + sourceDesc + "' and drop on '" + destinationDesc + "'");
    }
    catch (Exception e)
    {
      writeToLogFile("Error", "Error during drag and drop");
      testStepFailed("Drag '" + sourceDesc + "' and drop on '" + destinationDesc + "'");
    }
  }
  


  public static void switchFrame(String fr)
  {
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try
      {
        driver.switchTo().frame(fr);
        writeToLogFile("INFO", "Switched to frame :" + fr);
        testReporter("Green", "Switch to frame :" + fr);

      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      
      if (i == elementLoadWaitTime)
      {
        writeToLogFile("Error", "Error Switching to frame :" + fr);
        testStepFailed("Switch to frame :" + fr);
      }
    }
  }
  



  public static void clearEditBox(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          webElement.click();
          webElement.clear();
          writeToLogFile("INFO", "Clear Text Box '' in : " + locatorDescription);

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  
  public static void rightClick(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          Actions builder = new Actions(driver);
          builder.contextClick(webElement).build().perform();
          writeToLogFile("INFO", "Successfully right clicked '" + locatorDescription + "'");
          testReporter("Green", "Right Clicked '" + locatorDescription + "'");

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  
  public static void doubleClick(String objLocator) {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          Actions builder = new Actions(driver);
          builder.doubleClick(webElement).build().perform();
          writeToLogFile("INFO", "Successfully double clicked '" + locatorDescription + "'");
          testReporter("Green", "Double Clicked '" + locatorDescription + "'");

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  
  public static boolean elementPresent(String objectLocator)
  {
    try
    {
      findWebElement(objectLocator);
      return true;

    }
    catch (NoSuchElementException e)
    {
      return false;
    }
    catch (Exception e)
    {
      testStepFailed(e.toString()); }
    return false;
  }
  


  public static void verifyPageShouldContainText(String text)
  {
    if (driver.getPageSource().contains(text))
    {
      writeToLogFile("INFO", "Verify if page '" + text + "' text is present");
      testReporter("Green", "Verify if page '" + text + "' text is present");
    }
    else
    {
      writeToLogFile("ERROR", text + "' text is not present");
      testStepFailed("Verify if page '" + text + "' text is present");
    }
  }
  

  public static void verifyPageShouldNotContainText(String text)
  {
    if (driver.getPageSource().contains(text))
    {
      writeToLogFile("ERROR", "'" + text + "' text is present");
      testStepFailed("Verify if page '" + text + "' text is not present");
    }
    else
    {
      writeToLogFile("INFO", "Verify if page '" + text + "' text is not present");
      testReporter("Green", "Verify if page '" + text + "' text is not present");
    }
  }
  


  public static void pressKey(String objectLocator, String key)
  {
    if ((getConfigProperty("ApplicationType").trim().toString().equalsIgnoreCase(".NET") & getConfigProperty("AppBrowser").trim().toString().equalsIgnoreCase("internetexplorer")))
    {
      waitForElementToDisplay(objectLocator, elementLoadWaitTime);



    }
    else if (!elementPresent(objectLocator))
    {
      writeToLogFile("ERROR", "Web Element NOT found : " + locatorDescription);
      testStepFailed("Web Element NOT found : " + locatorDescription);
    }
    


    parseidentifyByAndlocator(objectLocator);
    identifyBy(identifier);
    writeToLogFile("INFO", "Pressing '" + key + "' in : " + objectLocator);
    testReporter("Green", "Press '" + key + "' in : " + locatorDescription);
    keys k = keys.valueOf(key.toUpperCase());
    switch (k) {
    case ALT: 
      webElement.sendKeys(new CharSequence[] { Keys.ENTER });
    case BACKSPACE:  webElement.sendKeys(new CharSequence[] { Keys.SPACE });
    case CANCEL:  webElement.sendKeys(new CharSequence[] { Keys.ESCAPE });
    case CONTROL:  webElement.sendKeys(new CharSequence[] { Keys.CONTROL });
    case DELETE:  webElement.sendKeys(new CharSequence[] { Keys.ALT });
    case ENTER:  webElement.sendKeys(new CharSequence[] { Keys.BACK_SPACE });
    case ESCAPE:  webElement.sendKeys(new CharSequence[] { Keys.CANCEL });
    case PAGEDOWN:  webElement.sendKeys(new CharSequence[] { Keys.DELETE });
    case PAGEUP:  webElement.sendKeys(new CharSequence[] { Keys.PAGE_DOWN });
    case SPACE:  webElement.sendKeys(new CharSequence[] { Keys.PAGE_UP });
    case TAB:  webElement.sendKeys(new CharSequence[] { Keys.TAB });
    }
    
  }
  





  public static String getTextSelectedOption(String objLocator)
  {
    String getSelectedText = "";
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      String SelectText = "";
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          Select select = new Select(webElement);
          SelectText = select.getFirstSelectedOption().getText().toString();
          getSelectedText = SelectText;

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
    

    return getSelectedText;
  }
  

  public static void verifyTextFieldCount(String objLocator, int CountNumber)
  {
    waitForElement(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          String text = getAttributeValue(objLocator, "value");
          if (text.length() > CountNumber)
          {
            writeToLogFile("Error", locatorDescription + "textfield is getting more than '" + CountNumber + "' value ");
            testStepFailed(locatorDescription + "textfield is getting more than '" + CountNumber + "' value ");

          }
          else
          {
            writeToLogFile("INFO", "Verify if " + locatorDescription + " textfield is not getting more than '" + CountNumber + "' value");
            testReporter("green", "Verify if " + locatorDescription + " textfield is not getting more than '" + CountNumber + "' value");
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  

  public static void verifyTextValueNotCharacter(String objLocator)
  {
    waitForElement(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          String text = getAttributeValue(objLocator, "value");
          if (text.matches("[a-zA-z]+"))
          {
            writeToLogFile("Error", locatorDescription + "textfield is getting character value ");
            testStepFailed(locatorDescription + "textfield is getting character value");

          }
          else
          {

            writeToLogFile("INFO", "Verify if " + locatorDescription + " textfield is  getting only number value");
            testReporter("green", "Verify:" + locatorDescription + " textfield is  getting only number value");
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  


  public static void verifyTextValueNotNumber(String objLocator)
  {
    waitForElement(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          String text = getAttributeValue(objLocator, "value");
          if (text.matches("[0-9]+"))
          {
            writeToLogFile("Error", locatorDescription + "textfield is getting Number value ");
            testStepFailed(locatorDescription + "textfield is getting Number value ");

          }
          else
          {

            writeToLogFile("INFO", "Verify if " + locatorDescription + " textfield is  getting  only character value");
            testReporter("green", "Verify if " + locatorDescription + " textfield is  getting only character value");
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  

  public static void verifyAlertTextShouldContain(String expectedAlertText)
  {
    Alert alert = driver.switchTo().alert();
    String alertText = alert.getText();
    if (alertText.contains(expectedAlertText))
    {
      writeToLogFile("INFO", "Verify if '" + expectedAlertText + "' alert text is present ");
      testReporter("green", "Verify if '" + expectedAlertText + "' alert text is present ");

    }
    else
    {
      writeToLogFile("Error", "'" + expectedAlertText + "' alert text is not present ");
      testStepFailed("'" + expectedAlertText + "' alert text is not present");
    }
  }
  
  public static void verifyTextFieldShouldContain(String objectLocator, String expectedValue)
  {
    waitForElement(objectLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try {
          String actualValue = getAttributeValue(objectLocator, "value");
          if (actualValue.contains(expectedValue))
          {
            writeToLogFile("INFO", "Verify if '" + expectedValue + "' is present in " + locatorDescription + " textfield");
            testReporter("green", "Verify if '" + expectedValue + "' is present in " + locatorDescription + " textfield");

          }
          else
          {
            writeToLogFile("Error", "'" + expectedValue + "' is not present in " + locatorDescription + " textfield");
            testStepFailed("'" + expectedValue + "' is not present in " + locatorDescription + " textfield");
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  

  public static void verifyTextFieldShouldNotContain(String objectLocator, String expectedValue)
  {
    waitForElement(objectLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          String textValue = getAttributeValue(objectLocator, "value");
          if (!textValue.contains(expectedValue))
          {
            writeToLogFile("INFO", "Verify if '" + expectedValue + "' is not present in " + locatorDescription + " textfield");
            testReporter("green", "Verify if '" + expectedValue + "' is not present in " + locatorDescription + " textfield");

          }
          else
          {
            writeToLogFile("Error", "'" + expectedValue + "' is present in " + locatorDescription + " textfield");
            testStepFailed("'" + expectedValue + "' is present in " + locatorDescription + " textfield");
          }
          

        }
        catch (InvalidSelectorException e)
        {
          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
  }
  

  public static enum keys
  {
    ENTER, 
    SPACE, 
    ESCAPE, 
    CONTROL, 
    ALT, 
    BACKSPACE, 
    CANCEL, 
    DELETE, 
    PAGEDOWN, 
    PAGEUP, 
    TAB;
  }
  


  public static void closeAllBrowser()
  {
    deleteAllCookies();
    Set<String> windowhandles = driver.getWindowHandles();
    for (String handle : windowhandles)
    {
      driver.switchTo().window(handle);
      driver.close();
    }
  }
  
  public static void closeChildBrowser(String windowTitle)
  {
    try {
      for (String winHandle : driver.getWindowHandles())
      {
        driver.switchTo().window(winHandle);
        if (driver.getTitle().equalsIgnoreCase(windowTitle))
        {
          driver.close();
          writeToLogFile("INFO", "Browser: Close Successful");
          testReporter("Green", "Close Browser");
          break;
        }
      }
    }
    catch (Exception e)
    {
      writeToLogFile("ERROR", "Browser: Close Failure");
      testStepFailed("Close Browser");
    }
  }
  





























































  public static String getText(String objLocator)
  {
    String getText = null;
    waitForElement(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {

        try
        {
          getText = webElement.getText();
          writeToLogFile("Info", "Sucessfully got the text '" + getText + "'");

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
    return getText;
  }
  

  public static String getAttributeValue(String objLocator, String attributeName)
  {
    String getAttributeValue = null;
    waitForElement(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      for (int i = 1; i <= elementLoadWaitTime; i++)
      {
        try
        {
          getAttributeValue = webElement.getAttribute(attributeName);
          writeToLogFile("Info", "Sucessfully got the attribute value '" + getAttributeValue + "'");

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (UnhandledAlertException e)
        {
          waitTime(1L);
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        catch (Exception e)
        {
          testStepFailed("Exception Error '" + e.toString() + "'");
        }
        
        if (i == elementLoadWaitTime)
        {
          testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
        }
      }
    }
    
    return getAttributeValue;
  }
  

  public static int getMatchingXpathCount(String objLocator)
  {
    List<WebElement> xpathCount = null;
    for (int i = 1; i <= elementLoadWaitTime; i++)
    {
      try
      {
        xpathCount = driver.findElements(By.xpath(objLocator));
        writeToLogFile("Info", "Sucessfully got the matchingxPath Count'" + xpathCount + "'");


      }
      catch (InvalidSelectorException e)
      {

        waitTime(1L);

      }
      catch (StaleElementReferenceException e)
      {
        waitTime(1L);
      }
      catch (ElementNotVisibleException e)
      {
        waitTime(1L);

      }
      catch (UnreachableBrowserException e)
      {
        testStepFailed(e.toString());
      }
      catch (UnhandledAlertException e)
      {
        waitTime(1L);
      }
      catch (WebDriverException e)
      {
        waitTime(1L);
      }
      catch (Exception e)
      {
        testStepFailed("Exception Error '" + e.toString() + "'");
      }
      
      if (i == elementLoadWaitTime)
      {
        testStepFailed(locatorDescription + " element found but its not in editable/clickable state within " + elementLoadWaitTime + " timeouts");
      }
    }
    
    return xpathCount.size();
  }
  


  public static void verifySelectOptionsSortOrder(String objLocator, String sortOrder)
  {
    verifySelectOptionsSortOrder(objLocator, sortOrder, "None");
  }
  

  public static void verifySelectOptionsSortOrder(String objLocator, String sortOrder, String excludeOption)
  {
    ArrayList<String> beforeSorting = new ArrayList();
    ArrayList<String> afterSorting = new ArrayList();
    if ((getConfigProperty("ApplicationType").trim().toString().equalsIgnoreCase(".NET") & getConfigProperty("AppBrowser").trim().toString().equalsIgnoreCase("internetexplorer")))
    {
      waitForElement(objLocator, elementLoadWaitTime);



    }
    else if (!elementPresent(objLocator))
    {
      writeToLogFile("ERROR", "Web Element NOT found : " + locatorDescription);
      testStepFailed("Web Element NOT found : " + locatorDescription);
    }
    
    Select select = new Select(webElement);
    for (int i = 0; i < select.getOptions().size(); i++)
    {
      if (!((WebElement)select.getOptions().get(i)).getText().contains(excludeOption))
      {
        beforeSorting.add(((WebElement)select.getOptions().get(i)).getText().trim());
        afterSorting.add(((WebElement)select.getOptions().get(i)).getText().trim());
      }
    }
    if (sortOrder.equalsIgnoreCase("ascending"))
    {

      Collections.sort(afterSorting);
      if (beforeSorting.equals(afterSorting))
      {
        writeToLogFile("Info", locatorDescription + " is in '" + sortOrder + "' order ");
        testReporter("Green", "Verify if " + locatorDescription + " is in '" + sortOrder + "' order ");
      }
      else
      {
        writeToLogFile("ERROR", locatorDescription + " is not in '" + sortOrder + "' order ");
        testStepFailed(locatorDescription + " is not in '" + sortOrder + "' order ");
      }
      
    }
    else if (sortOrder.equalsIgnoreCase("descending"))
    {

      Comparator<String> comparator = Collections.reverseOrder();
      Collections.sort(afterSorting, comparator);
      if (beforeSorting.equals(afterSorting))
      {
        writeToLogFile("Info", locatorDescription + " is in '" + sortOrder + "' order ");
        testReporter("Green", "Verify if " + locatorDescription + " is in '" + sortOrder + "' order ");
      }
      else
      {
        writeToLogFile("ERROR", locatorDescription + " is not in '" + sortOrder + "' order ");
        testStepFailed(locatorDescription + " is not in '" + sortOrder + "' order ");
      }
    }
  }
  
  public static void UnSelectFrame()
  {
    try {
      writeToLogFile("Info", "Switching to default content frame ");
      driver.switchTo().defaultContent();
    }
    catch (Exception e)
    {
      testStepFailed("Error in swiching to default content frame");
    }
  }
  
  public static void waitForAlertWindow(int timeout)
  {
    for (int i = 0; i <= timeout; i++)
    {
      if (isAlertWindowPresent()) {
        break;
      }
      


      waitTime(1L);
      
      if (i == timeout)
      {
        testStepFailed("Alert Window is not present within '" + timeout + "' timeout");
      }
    }
  }
  

  public static void waitForAlertWindow(String alertTitle, int timeout)
  {
    for (int i = 0; i <= timeout; i++)
    {
      if (isAlertWindowPresent()) {
        break;
      }
      


      waitTime(1L);
      
      if (i == timeout)
      {
        testStepFailed(alertTitle + " alert Window is not present within '" + timeout + "' timeout");
      }
    }
  }
  


  public static void waitForChildWindow(String windowTitle, int timeout)
  {
    for (int i = 1; i <= timeout; i++)
    {
      String loopstatus = "false";
      if (i == timeout)
      {
        writeToLogFile("Info", windowTitle + "window is not present within '" + timeout + "' timeout");
        testStepFailed(windowTitle + "window is not present within '" + timeout + "' timeout");
      }
      
      Set<String> AllHandle = driver.getWindowHandles();
      for (String han : AllHandle)
      {
        driver.switchTo().window(han);
        String getTitle = driver.getTitle();
        if (getTitle.trim().equalsIgnoreCase(windowTitle))
        {
          loopstatus = "true";
          break;
        }
      }
      if (loopstatus.equalsIgnoreCase("true")) {
        break;
      }
      
      waitTime(1L);
    }
  }
  
  public static void waitForElementToDisplay(String objLocator, int timeout)
  {
    boolean webElementStatus = false;
    try
    {
      driver.manage().timeouts().implicitlyWait(0L, TimeUnit.SECONDS);
      
      for (int i = 1; i <= timeout; i++)
      {
        try
        {
          if (!webElementStatus)
          {
            findWebElement(objLocator);
            webElementStatus = true;
          }
          if (webElement.isDisplayed())
          {
            webElementIsPresent = true;
            break;
          }
          

          waitTime(1L);

        }
        catch (InvalidSelectorException e)
        {

          waitTime(1L);

        }
        catch (StaleElementReferenceException e)
        {
          waitTime(1L);

        }
        catch (NoSuchElementException e)
        {
          waitTime(1L);
        }
        catch (ElementNotVisibleException e)
        {
          waitTime(1L);

        }
        catch (UnreachableBrowserException e)
        {
          testStepFailed(e.toString());
        }
        catch (WebDriverException e)
        {
          waitTime(1L);
        }
        
        if (i == timeout)
        {
          if (webElementStatus)
          {
            webElementIsPresent = false;
            testStepFailed(locatorDescription + " element is present but its not in clickable/editable state within '" + timeout + "' timeout");
          }
          else
          {
            webElementIsPresent = false;
            testStepFailed(locatorDescription + " element not found within '" + timeout + "' seconds timeout ");
          }
        }
      }
    }
    catch (Exception e)
    {
      webElementIsPresent = false;
      testStepFailed("Exception error '" + e.toString() + "'");


    }
    finally
    {

      driver.manage().timeouts().implicitlyWait(implicitlyWaitTime, TimeUnit.SECONDS);
    }
  }
  



  public static void clickOnSpecialElement(String objectLocator)
  {
    waitForElement(objectLocator, elementLoadWaitTime);
    if (webElementIsPresent)
    {
      try
      {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", new Object[] { webElement });
        testReporter("green", "Click on :" + locatorDescription);
      }
      catch (Exception e)
      {
        testStepFailed("Click on :" + locatorDescription);
      }
    }
  }
  
  public static boolean isElementDisplayed(String objectLocator)
  {
    findWebElement(objectLocator);
    
    if (webElement.isDisplayed())
    {
      return true;
    }
    

    return false;
  }
  




  public static boolean isTextPresent(String expectedText)
  {
    if (driver.getPageSource().contains(expectedText))
    {
      return true;
    }
    

    return false;
  }
  

  public static void deleteAllCookies()
  {
    try
    {
      driver.manage().deleteAllCookies();
      writeToLogFile("INFO", "Successfully deleted all cookies");
    }
    catch (Exception e)
    {
      windowreadyStateStatus = false;
      testStepFailed("Delete All cookies keyword exception error" + e.toString());
    }
  }
  
  public static void maximiseWindow()
  {
    try
    {
      driver.manage().window().maximize();
      writeToLogFile("INFO", "Successfully Maximised Browser Window");
    }
    catch (Exception e)
    {
      windowreadyStateStatus = false;
      testStepFailed("Maximise window keyword exception error" + e.toString());
    }
  }
  

  public static void selectCheckBox(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      try
      {
        if (!webElement.isSelected())
        {
          webElement.click();
        }
        testStepPassed("Checked on the " + locatorDescription + " checkbox");
      }
      catch (Exception e)
      {
        testStepFailed(e.toString());
      }
    }
  }
  
  public static void unSelectCheckBox(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      try
      {
        if (webElement.isSelected())
        {
          webElement.click();
        }
        testStepPassed("Unchecked the " + locatorDescription + " checkbox");
      }
      catch (Exception e)
      {
        testStepFailed(e.toString());
      }
    }
  }
  
  public static void verifyCheckBoxIsChecked(String objLocator)
  {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      try
      {
        if (webElement.isSelected())
        {
          testStepPassed("Verified that " + locatorDescription + " is checked");
        }
        else
        {
          testStepFailed(locatorDescription + " is not checked");
        }
        
      }
      catch (Exception e)
      {
        testStepFailed(e.toString());
      }
    }
  }
  
  public static void verifyCheckBoxIsUnChecked(String objLocator) {
    waitForElementToDisplay(objLocator, elementLoadWaitTime);
    if (webElementIsPresent) {
      try
      {
        if (!webElement.isSelected())
        {
          testStepPassed("Verified that " + locatorDescription + " is Unchecked");
        }
        else
        {
          testStepFailed(locatorDescription + " is  checked");
        }
        
      }
      catch (Exception e)
      {
        testStepFailed(e.toString());
      }
    }
  }






































































}
