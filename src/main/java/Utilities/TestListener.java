package Utilities;


import org.testng.ITestResult;
import org.testng.TestListenerAdapter;









public class TestListener
  extends TestListenerAdapter
{
  public TestListener() {}
  
  public void onTestFailure(ITestResult result)
  {
    GenericKeywords.testFailure = true;
    GenericKeywords.testFailureCount += 1;
  }
  






  public void onTestSuccess(ITestResult result)
  {
    GenericKeywords.testSuccessCount += 1;
  }
  

  public void onTestSkipped(ITestResult result)
  {
    GenericKeywords.testSkippedCount += 1;
  }
}
