/*
- obtain command line parameter
- read file as text
- using XML parser for XPath 
*/
import javax.xml.xpath.*
import javax.xml.parsers.DocumentBuilderFactory

def obtainXmlElement( String xml, String xpathQuery ) {
  def documentBuilder     = DocumentBuilderFactory.newInstance().newDocumentBuilder()
  def dataSource = new ByteArrayInputStream( xml.bytes )
  def rootElement     = documentBuilder.parse(dataSource).documentElement

  def xpath = XPathFactory.newInstance().newXPath()
  xpath.evaluate( xpathQuery, rootElement )
}

def pomXmlFile = this.args[0]
println obtainXmlElement( new File(pomXmlFile).text, "//project/profiles/profile/id[text()='addtestdata']/../build/plugins/plugin/configuration/locations" )
