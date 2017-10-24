<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- Edited by XMLSpy® -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"/> 
<xsl:template match="/">
<html>
  <title>HTML REPORTING</title>
     <body>
        <h1 align="center"><xsl:value-of select="//ProjectName"/></h1>
	<table  align="center" width="100%">
<tr>
<td align="left" width="10%"><a href="http://www.adactin.com/HotelApp/"><IMG ALIGN="left" ALT="Client Logo" SRC=".\Logos\client logo.png"></IMG></a></td>
<td align="right" width="50%"><a href="http://www.suntechnologies.com/"><IMG SRC=".\Logos\Sun.jpg"  ALT="Sun Logo" ALIGN="Right "></IMG></a></td>
</tr>
</table>

	<br></br>
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
    	<xsl:when test="round(//test/@duration-ms div 1000) div 60>'60'">
    	<td><xsl:value-of select="round(round((//test/@duration-ms div 1000) div 60)div 60)"/><xsl:text>hr:</xsl:text><xsl:value-of select="round(round((//test/@duration-ms div 1000) div 60)mod 60)"/><xsl:text>min:</xsl:text><xsl:value-of select="round((//test/@duration-ms div 1000) div 60)mod 60"/><xsl:text>sec</xsl:text></td>
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
	<td><b>Build No:-</b></td>
	<td><xsl:value-of select="//VersionName"/></td>
	</tr>
	</table>
	<br><br><br><br></br></br></br></br>
	<TABLE border="1"> 
	<TR> 
	<TD colspan="4"><center><b>Test Case Execution Status</b></center></TD> 
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
	<TD><center><b>DURATION</b></center></TD> 
	<td><b>STATUS</b></td>
	</tr> 
	<xsl:for-each select="//test-method">
<xsl:variable name="count" select='position()' />
	<TR>
	<TD><xsl:number/> </TD> 
	<TD><a>
<xsl:attribute name="href">
<xsl:value-of select="//testCaseLinks[$count]" />
</xsl:attribute>
<xsl:attribute name="target"></xsl:attribute><xsl:value-of select="@name"/>
</a>

	</TD>
	<TD><xsl:value-of select="//testCaseDescription[$count]"/></TD> 
	
	<xsl:variable name="total" select='round((//test-method[$count]/@duration-ms))' />
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
	<a href=".\CoverPage.html">Click here to see the Cover Page</a>
     </body>
     
</html>
</xsl:template>
</xsl:stylesheet>

