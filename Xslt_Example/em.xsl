<?xml version="1.0" encoding="WINDOWS-1251"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
	<xsl:template match="/">

	<center>
		<h1>
			<strong>
				<xsl:value-of select="//title"/>
			</strong>
		</h1>
		<table border="1" >
			<tr> 
				<td><xsl:value-of select="//data/main_information" /></td>
			</tr>
			<tr> 
				<td><xsl:value-of select="//data/add_information" /></td>
			</tr>
		</table>
		<b style="text-color:#ff0000"> <xsl:value-of select="//data/@description" /> </b>
	</center>
	</xsl:template>
</xsl:stylesheet>