package bc;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.util.*;

public class allXML
{
    private Map<String,String> oneXMLFileFields = new HashMap<String,String>();
    private String XMLfile = "";

    protected String getXmlFile(){
    	return this.XMLfile;
    }
    
    protected Map<String,String> getXmlFileFields(){
    	return this.oneXMLFileFields;
    }
    
	private static allXML instance=null;
    public static allXML getInstance(Map<String,String> myFields, String myXMLFile){
    	if(instance==null){
    		instance=new allXML(myFields, myXMLFile);
    		instance.parseAndLoadMap();
    	}
    	return instance;
    }
    public static allXML getInstance(){
    	return instance;
    }
	
	protected allXML(Map<String,String> myFields, String myXMLFile) {
    	oneXMLFileFields = myFields;
    	XMLfile = myXMLFile;
    	parseAndLoadMap();
    }
	// not override
    public String getfieldTransl(String lng, String fld){ // отримати переклад поля по мові і полю        
        if (oneXMLFileFields.isEmpty()) { 
            parseAndLoadMap(); // load Map if it's empty
            oneXMLFileFields.put("0", "0"); // щоб не була пустою (natPrsFields.isEmpty()==false) якщо файл пустий
        }
        String key = lng.toUpperCase()+"_"+fld.toUpperCase();     
        //System.out.println("allXML: getfieldTransl");
        return oneXMLFileFields.get(key);
        //return "TEST";
    } 
    // not override
    protected void parseAndLoadMap()
    {
      Document doc = null;
      try
      {
        DocumentBuilderFactory dbf =DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(this.getXmlFile());
        if (doc != null)
          loadMap(doc);
      }
      catch (Exception e)
      {
        System.err.println("Sorry, an error occurred when opening&parsing XML file : " + e);
      }
    }
    // not override
    protected void loadMap(Document doca)
    {      
      Element root = doca.getDocumentElement();
      NodeList fieldsNamesXML = root.getChildNodes();
      for (int i = 0; i < fieldsNamesXML.getLength(); i++){
          Node fieldNameXML = fieldsNamesXML.item(i);
          if (fieldNameXML instanceof Element) {              
              NodeList fieldsTranslationsXML = fieldNameXML.getChildNodes();
              for (int j=0; j < fieldsTranslationsXML.getLength(); j++) {
                      Node fieldTranslationXML = fieldsTranslationsXML.item(j);
                      if (fieldTranslationXML instanceof Element){
                          String lang = fieldTranslationXML.getNodeName().toString();
                          Text translationText = (Text) fieldTranslationXML.getFirstChild();
                          String translation = translationText.getData().trim();
                          this.getXmlFileFields().put(lang.toUpperCase()+"_"+fieldNameXML.getNodeName().toUpperCase(), translation);
                      } // if                     
              } // for j переклад полів
          } // if
      } //for i - назви полів
    } // loadMap
}
