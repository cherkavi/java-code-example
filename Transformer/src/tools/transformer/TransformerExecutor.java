package tools.transformer;

import org.apache.log4j.BasicConfigurator;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class TransformerExecutor {
	
	public static void main(String[] args){
		BasicConfigurator.configure();
		String pathToConfiguration=null;
		if(args.length==0){
			// pathToConfiguration="test_configuration.xml";
			// pathToConfiguration="jdbc_to_csv_configuration.xml";
			pathToConfiguration="jdbc_to_mdb_configuration.xml";
		}else{
			pathToConfiguration=args[0];
		}
		try{
			BeanFactory beanFactory = new XmlBeanFactory(new FileSystemResource(pathToConfiguration));
			
			Transformer transformer=beanFactory.getBean(Transformer.class);
			transformer.tranform();
		}catch(Exception ex){
			System.err.println("Exception: "+ex.getMessage());
		}
	}
}
