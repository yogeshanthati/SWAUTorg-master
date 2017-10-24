package Utilities;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
//import javax.xml.soap.Node;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;



public class XmlValidation {
	private static  SOAPMessage createSOAPRequest() throws Exception
	{
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", "http://www.webservicex.net/" + "GetGeoIP");
		SOAPPart soapPart = soapMessage.getSOAPPart();   


		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("web","http://www.webservicex.net/");

		// SOAP Body
		SOAPBody soapBody = envelope.getBody();
		//SOAPElement soapBodyElem = soapBody.addChildElement("getCMLMaintenanceData", "com");
		SOAPElement soapBodyElem = soapBody.addChildElement("GetGeoIP", "web");
		SOAPElement soapBodyElem2=soapBodyElem.addChildElement("IPAddress", "web");
		//soapBodyElem2.addTextNode("192.164.94.65");		
		soapBodyElem2.addTextNode(Common.retrieve("IPAddress"));
		/*SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("ToCurrency", "web");
            soapBodyElem1.addTextNode("AUD");*/

		//            ServiceConfigUtil.

		soapMessage.saveChanges();

		// Check the input
		System.out.println("Request SOAP Message = ");
		soapMessage.writeTo(System.out);
		System.out.println();
		return soapMessage;
	}

	/**
	 * Method used to print the SOAP Response
	 */
	private  static void printSOAPResponse(SOAPMessage soapResponse) throws Exception
	{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		Source sourceContent = soapResponse.getSOAPPart().getContent();
		// System.out.println("\nResponse SOAP Message = ");
		StreamResult result = new StreamResult(System.out);
		transformer.transform(sourceContent, result);
	}

	/**
	 * Starting point for the SAAJ - SOAP Client Testing
	 */

	public static  void SoapCollateralPositionData()
	{

		try
		{


			// Create SOAP Connection
			SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
			SOAPConnection soapConnection = soapConnectionFactory.createConnection();

			//Send SOAP Message to SOAP Server
			String url = "http://www.webservicex.net/geoipservice.asmx?WSDL";
			SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);                      
			// Process the SOAP Response
			printSOAPResponse(soapResponse);

			//getFullNameFromXml(soapResponse,"LCVAmount");                      
			FileOutputStream fOut = new FileOutputStream(System.getProperty("user.dir")+"\\SOAPFiles\\GetGeoIP.xml");

			soapResponse.writeTo(fOut);
			soapConnection.close();




		}
		catch (Exception e)
		{
			System.err.println("Error occurred while sending SOAP Request to Server");
			e.printStackTrace();
		}

	}



	/*public static List<String> getFullNameFromXml(SOAPMessage response, String tagName) throws Exception {

       NodeList nodeList = response.getSOAPPart().getElementsByTagName(tagName);
       List<String> ids = new ArrayList<String>(nodeList.getLength());
       for(int i=0;i<nodeList.getLength(); i++) {
           Node x = (Node) nodeList.item(i);
           ids.add(x.getFirstChild().getNodeValue());
           System.out.println("");
           System.out.println(nodeList.item(i).getFirstChild().getNodeValue());
       }
       return ids;
   }*/


	public static  String getdataCollateralPosition(String tagname) throws SAXException, IOException, ParserConfigurationException{
		String webservicedata =null;

		File fXmlFile = new File(System.getProperty("user.dir")+"\\SOAPFiles\\GetGeoIP.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile); 
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
		doc.getDocumentElement().normalize(); 
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
		NodeList nList = doc.getElementsByTagName("GetGeoIPResponse");
		System.out.println("----------------------------");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode; 
				webservicedata=eElement.getElementsByTagName(tagname).item(0).getTextContent();
				System.out.println("Last Name : " + eElement.getElementsByTagName(tagname).item(0).getTextContent());



			}
		}
		return webservicedata;

	}


	public static void validateXml(){
		SoapCollateralPositionData();
		String strCountryCode,strCountryName,strReturnCodeDetails;
		try {		

			strReturnCodeDetails = getdataCollateralPosition("ReturnCodeDetails");
			if(strReturnCodeDetails.equals("Success")){
				Common.testStepPassed("Got the Response with ReturnCodeDetails as Success");
			}else{
				Common.testStepFailed("Unable to get the Response with ReturnCodeDetails as Success");				
			}

			strCountryCode = getdataCollateralPosition("CountryCode");
			//validation
			if(strCountryCode.equals(Common.retrieve("CountryCode"))){
				Common.testStepPassed("Country Code is matching, Expected->"+Common.retrieve("CountryCode") +", Actual is->"+strCountryCode);
			}else{
				Common.testStepFailed("Country Code is not matching");
			}

			strCountryName = getdataCollateralPosition("CountryName");
			if(strCountryName.equals(Common.retrieve("CountryName"))){
				Common.testStepPassed("Country Name is matching, Expected->"+Common.retrieve("CountryName") +", Actual is->"+strCountryName);
			}else{
				Common.testStepFailed("Country Name is not matching, Expected->"+Common.retrieve("CountryName") +", Actual is->"+strCountryName);				
			}

		} catch (Exception e) {
			Common.testStepFailed("Exception caught while Soap validation->"+e.getMessage());
		} 
	}
}
