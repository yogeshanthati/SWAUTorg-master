package Utilities;








public class VoiceCall
{
  public VoiceCall() {}
  
  public static final String ACCOUNT_SID = GenericKeywords.getConfigProperty("ACCOUNT_SID");
  public static final String AUTH_TOKEN = GenericKeywords.getConfigProperty("AUTH_TOKEN");
  public static String toMobNo = GenericKeywords.getConfigProperty("ToMobileNo");
  public static String fromMobNo = GenericKeywords.getConfigProperty("FromMobileNo");
  
  public static void voiceCall()  {
    int totalExecuted = GenericKeywords.testFailureCount + GenericKeywords.testSkippedCount + GenericKeywords.testSuccessCount;
    int totalPassPercent = GenericKeywords.testSuccessCount * 100 / totalExecuted;
    int totalFailPercent = GenericKeywords.testFailureCount * 100 / totalExecuted;
    
    try
    {
   /*   TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
      

      Account mainAccount = client.getAccount();
      

      AccountList accountList = client.getAccounts();
      


      for (Account a : accountList) {
        System.out.println(a.getFriendlyName());
      }
      

      Iterator<Account> itr = accountList.iterator();
      while (itr.hasNext()) {
        Account a = (Account)itr.next();
        System.out.println(a.getFriendlyName());
      }
      

      accountList = client.getAccounts();
      Object accounts = accountList.getPageData();
      

      CallFactory callFactory = mainAccount.getCallFactory();
      Map<String, String> callParams = new HashMap();
      callParams.put("To", toMobNo);
      callParams.put("From", fromMobNo);
      callParams.put("Url", "http://twimlets.com/echo?Twiml=%3CResponse%3E%3CSay%3ESMOKE+TEST+REPORT+" + totalPassPercent + "+percent+Passed+" + totalFailPercent + "+percent+Failed+iSAFE+SCRIPT+EXECUTION+COMPLETED" + "%3C%2FSay%3E%3C%2FResponse%3E");
      
      Call call = callFactory.create(callParams);
      System.out.println(call.getSid());
*/

    }
    catch (Exception e)
    {


      System.out.println(e.toString());
    }
  }
}
