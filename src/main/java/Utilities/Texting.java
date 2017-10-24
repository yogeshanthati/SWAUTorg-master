package Utilities;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;










public class Texting
{
  public Texting() {}
  
  public static String[] getArrayOfEmails(String keyword)
  {
    String configProperty = GenericKeywords.getConfigProperty(keyword) + "@sms.ipipi.com";
    if (configProperty.length() != 0)
    {
      String[] arr = configProperty.split(";");
      GenericKeywords.writeToLogFile("INFO", "Splitting email addresses : " + keyword);
      for (int i = 0; i < arr.length; i++)
        GenericKeywords.writeToLogFile("INFO", "Email :" + (i + 1) + " \"" + arr[i] + "\"");
      return arr;
    }
    
    GenericKeywords.writeToLogFile("INFO", "No email addresses found for :" + keyword);
    return null;
  }
  
  public static void sendMsg() {
    String host = GenericKeywords.getConfigProperty("HostName");
    String user = GenericKeywords.getConfigProperty("UserName");
    final String password = GenericKeywords.getConfigProperty("Password");
    String senderName = GenericKeywords.getConfigProperty("SenderAddress");
    String[] to = getArrayOfEmails("PhoneNo");
    
    Properties props = new Properties();
    props.put("mail.smtp.host", host);
    props.put("mail.smtp.auth", "true");
    
    Session session = Session.getDefaultInstance(props, 
      new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
			return null;
          //return new PasswordAuthentication(Texting.this, password);
        }
      });
    try {
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(senderName + "<" + user + ">"));
      
      for (int i = 0; i < to.length; i++)
      {
       // message.addRecipient(Message.RecipientType.TO, new InternetAddress(to[i]));
      }
      



      int totalExecuted = GenericKeywords.testFailureCount + GenericKeywords.testSkippedCount + GenericKeywords.testSuccessCount;
      


      message.setText("SMOKE TEST REPORT\n\n\n" + GenericKeywords.testSuccessCount * 100 / totalExecuted + "% Passed" + '\n' + GenericKeywords.testFailureCount * 100 / totalExecuted + "% Failed" + '\n' + '\n' + "Total Executed-" + (GenericKeywords.testFailureCount + GenericKeywords.testSkippedCount + GenericKeywords.testSuccessCount) + '\n' + "Total Passed-" + GenericKeywords.testSuccessCount + '\n' + "Total Failed-" + GenericKeywords.testFailureCount + '\n' + "Total Skipped-" + GenericKeywords.testSkippedCount);
      









      GenericKeywords.writeToLogFile("INFO", "<<<<<<<<<<<<<Please Wait>>>>>>>>>>>>>>>>>");
      Transport.send(message);
      GenericKeywords.writeToLogFile("INFO", "Message sent successfully...");
    } catch (MessagingException e) { e.printStackTrace();
    }
  }
}
