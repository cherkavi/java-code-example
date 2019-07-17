package free_marker;

import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerExample {
	public static void main(String[] args){
		System.out.println("begin");
		Map<String, Object> datamodel = new HashMap<String, Object>();
		datamodel.put("pet", "Bunny");
		datamodel.put("number", new Integer(6));
		// Process the template using FreeMarker
		try {
			freemarkerDo(datamodel, "there are ${number} of ${pet} \n");
		}catch(Exception e) {
			System.out.println(e.getLocalizedMessage());
		}		
		System.out.println("-end-");
	}
	
	// Process a template using FreeMarker and print the results
	static void freemarkerDo(Map<String, Object> datamodel, String template) throws Exception{
		// Configuration cfg = new Configuration();
		// Template tpl = cfg.getTemplate(template);
		// OutputStreamWriter output = new OutputStreamWriter(System.out);
		Template tpl=new Template("", new StringReader(template), new Configuration());
		String[] attributes=tpl.getCustomAttributeNames();
		int counter=0;
		for(String attr:attributes){
			System.out.println("Attr("+(++counter)+"):"+attr);
		}
		tpl.process(datamodel, new OutputStreamWriter(System.out));
	}
}
