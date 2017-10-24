<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"/> 
<xsl:template match="/">
<html>
  <title>HTML REPORTING</title>
     <body>
	<a href="http://www.indiumsoft.com/index.php">
	<IMG SRC=".\Logos\Mtouchep.jpg" ALT="picture
	of a pumpkin" ALIGN="Right" height="100" width="100"></IMG>
	</a>
	<a href="http://www.indiumsoft.com/index.php">
	<IMG SRC=".\Logos\18.jpg" ALT="picture
	of a pumpkin" ALIGN="left" height="100" width="100"></IMG>
	</a>
	<br><br><br></br></br></br>
	<br>
	</br>
	<table align="left">
	<tr>
	<td></td>
	</tr>
	<tr>
    	<td><b>DATE:-</b></td>
    	<td ><xsl:value-of select="//Date"/></td>
	</tr>
	<tr>
    	<td><b>DURATION:-</b></td>
    	<xsl:choose>
    	<xsl:when test="round(//test/@duration-ms div 1000) div 60>'59'">
    	<td><xsl:value-of select="round((//test/@duration-ms div 1000) div 60)div 60"/><xsl:text>hr:</xsl:text><xsl:value-of select="round((//test/@duration-ms div 1000) div 60)mod 60"/><xsl:text>min:</xsl:text><xsl:value-of select="round((//test/@duration-ms div 1000) div 60)mod 60"/><xsl:text>sec</xsl:text></td>
	</xsl:when>
    	<xsl:when test="round(//test/@duration-ms div 1000) div 60>'1'">
    	<td><xsl:value-of select="round((//test/@duration-ms div 1000) div 60)"/><xsl:text> Min:</xsl:text><xsl:value-of select="round((//test/@duration-ms div 1000) mod 60)"/><xsl:text>Sec</xsl:text></td>
	</xsl:when>
	<xsl:otherwise>
    	<td><xsl:value-of select="round((//test/@duration-ms div 1000))"/><xsl:text> Seconds</xsl:text></td>
	</xsl:otherwise>
	</xsl:choose>
	</tr>
	<tr>
	<td><b>VERSION NAME:-</b></td>
	<td><xsl:value-of select="//VersionName"/></td>
	</tr>
	</table>
	<br><br></br></br>
	<table align="right">
	<tr>
	<td colspan="2"><center><b>DISTRIBUTION LISTS</b></center></td>
	</tr>
	<tr>
    	<td><b>To:-</b></td>
    	<td ><xsl:value-of select="//MailTo"/></td>
	</tr>
	<tr>
    	<td></td>
    	<td></td>
	</tr>
	<tr>
	<td><b>CC:-</b></td>
	<td><xsl:value-of select="//MailCc"/></td>
	</tr>
	</table>
	<br><br><br><br></br></br></br></br>
	<TABLE border="1"> 
	<TR> 
	<TD colspan="4"><center><b>Test Case Execution status</b></center></TD> 
	</TR> 
	<TR> 
	<TD><b>EXECUTED</b></TD> 
	<TD><b>PASSED</b></TD> 
	<TD><b>FAILED</b></TD> 
	<TD><b>SKIPPED</b></TD>
	</TR> 
	<TR>
	<TD><xsl:value-of select="//testng-results/@total"/></TD> 
	<TD><xsl:value-of select="//testng-results/@passed"/></TD> 
	<TD><xsl:value-of select="//testng-results/@failed"/></TD> 
	<TD><xsl:value-of select="//testng-results/@skipped"/></TD>
	</TR>
	<TR>
	<TD><center><b>Percentage</b></center></TD> 
	<TD><xsl:value-of select="round(//testng-results/@passed div //testng-results/@total*100)"/><xsl:text>%</xsl:text></TD> 
	<TD><xsl:value-of select="round(//testng-results/@failed div //testng-results/@total*100)"/><xsl:text>%</xsl:text></TD> 
	<TD><xsl:value-of select="round(//testng-results/@skipped div //testng-results/@total*100)"/><xsl:text>%</xsl:text></TD>
	</TR>
	</TABLE>
	<br><br></br></br>
	<TABLE border="1"> 
	<tr>
	<td><b>TEST CASE.NO</b></td>
	<td><b>TEST CASE NAME</b></td>
	<td><b>TEST CASE DESCRIPTION</b></td>
	<TD colspan="4"><center><b>DURATION</b></center></TD> 
	<td><b>STATUS</b></td>
	</tr> 
	<TR> 
	<td></td>
	<td></td>
	<td></td>
	<TD><b>PRE CONDITION</b></TD> 
	<TD><b>TEST CASE</b></TD> 
	<TD><b>POST CONDITION</b></TD> 
	<TD><b>TOTAL</b></TD>
	<td></td>
	</TR> 	
	<xsl:for-each select="//test-method">
<xsl:variable name="count" select='position()' />
	<TR>
	<TD><xsl:number/> </TD> 
	<TD>
<xsl:value-of select="@name"/>
	</TD>
	<TD><xsl:value-of select="//testCaseDescription[$count]"/></TD> 
	<xsl:choose>
	<xsl:when test="round(//before-method[$count]/@duration-ms div 1000) div 60>'59'">
    	<td><xsl:value-of select="round((//before-method[$count]/@duration-ms div 1000) div 60)div 60"/><xsl:text>hr:</xsl:text><xsl:value-of select="round((//before-method[$count]/@duration-ms div 1000) div 60)mod 60"/><xsl:text>min:</xsl:text><xsl:value-of select="round((//before-method[$count]/@duration-ms div 1000) div 60)mod 60"/><xsl:text>sec</xsl:text></td>
	</xsl:when>
	<xsl:when test="round(//before-method[$count]/@duration-ms div 1000) div 60>'1'">
    	<td><xsl:value-of select="round((//before-method[$count]/@duration-ms div 1000) div 60)"/><xsl:text> Min:</xsl:text><xsl:value-of select="round((//before-method[$count]/@duration-ms div 1000) mod 60)"/><xsl:text>Sec</xsl:text></td>
	</xsl:when>
	<xsl:otherwise>
    	<td><xsl:value-of select="round((//before-method[$count]/@duration-ms div 1000))"/><xsl:text> Sec</xsl:text></td>
	</xsl:otherwise>
	</xsl:choose>
	<xsl:choose>
	<xsl:when test="round(//test-method[$count]/@duration-ms div 1000) div 60>'59'">
    	<td><xsl:value-of select="round(((//test-method[$count]/@duration-ms div 1000) div 60)div 60)"/><xsl:text>hr:</xsl:text><xsl:value-of select="round((//test-method[$count]/@duration-ms div 1000) div 60)mod 60"/><xsl:text>min:</xsl:text><xsl:value-of select="round((//test-method[$count]/@duration-ms div 1000) div 60)mod 60"/><xsl:text>sec</xsl:text></td>
	</xsl:when>
	<xsl:when test="round(//test-method[$count]/@duration-ms div 1000) div 60>'1'">
    	<td><xsl:value-of select="round((//test-method[$count]/@duration-ms div 1000) div 60)"/><xsl:text> Min:</xsl:text><xsl:value-of select="round((//test-method[$count]/@duration-ms div 1000) mod 60)"/><xsl:text>Sec</xsl:text></td>
	</xsl:when>
	<xsl:otherwise>
    	<td><xsl:value-of select="round((//test-method[$count]/@duration-ms div 1000))"/><xsl:text> Sec</xsl:text></td>
	</xsl:otherwise>
	</xsl:choose>
	<xsl:choose>
	<xsl:when test="round(//after-method[$count]/@duration-ms div 1000) div 60>'59'">
    	<td><xsl:value-of select="round(((//after-method[$count]/@duration-ms div 1000) div 60)div 60)"/><xsl:text>hr:</xsl:text><xsl:value-of select="round((//after-method[$count]/@duration-ms div 1000) div 60)mod 60"/><xsl:text>min:</xsl:text><xsl:value-of select="round((//after-method[$count]/@duration-ms div 1000) div 60)mod 60"/><xsl:text>sec</xsl:text></td>
	</xsl:when>
	<xsl:when test="round(//after-method[$count]/@duration-ms div 1000) div 60>'1'">
    	<td><xsl:value-of select="round((//after-method[$count]/@duration-ms div 1000) div 60)"/><xsl:text> Min:</xsl:text><xsl:value-of select="round((//after-method[$count]/@duration-ms div 1000) mod 60)"/><xsl:text>Sec</xsl:text></td>
	</xsl:when>
	<xsl:otherwise>
    <td><xsl:value-of select="round((//after-method[$count]/@duration-ms div 1000))"/><xsl:text> Sec</xsl:text></td>
	</xsl:otherwise>
	</xsl:choose>
	<xsl:variable name="total" select='round((//before-method[$count]/@duration-ms)) + round((//test-method[$count]/@duration-ms)) + round((//after-method[$count]/@duration-ms))' />
<xsl:choose>
	<xsl:when test="round(($total div 1000)) div 60>'59'">
    	<td><xsl:value-of select="round((($total div 1000) div 60)div 60)"/><xsl:text>hr:</xsl:text><xsl:value-of select="round(($total div 1000) div 60)mod 60"/><xsl:text>min:</xsl:text><xsl:value-of select="round(($total div 1000) div 60)mod 60"/><xsl:text>sec</xsl:text></td>
	</xsl:when>
	<xsl:when test="round(($total div 1000)) div 60>'1'">
    	<td><xsl:value-of select="round(($total div 1000) div 60)"/><xsl:text> Min:</xsl:text><xsl:value-of select="round(($total div 1000) mod 60)"/><xsl:text>Sec</xsl:text></td>
	</xsl:when>
	<xsl:otherwise>
    <td><xsl:value-of select="round($total div 1000)"/><xsl:text> Sec</xsl:text></td>
	</xsl:otherwise>
	</xsl:choose>
	<xsl:choose>	
        <xsl:when test="@status='PASS'">
        <TD><FONT COLOR="DARKGREEN"><xsl:value-of select="@status"/></FONT></TD>
        </xsl:when>
        <xsl:when test="@status='FAIL'">
	<TD><FONT COLOR="RED"><xsl:value-of select="@status"/></FONT></TD>
        </xsl:when>
        <xsl:otherwise>
	<TD><FONT COLOR="ORANGE"><xsl:value-of select="@status"/></FONT></TD>
        </xsl:otherwise>
        </xsl:choose> 
	</TR>
	</xsl:for-each>
	</TABLE>
	<br><br></br></br>
     </body>
     
</html>
</xsl:template>
</xsl:stylesheet>

