import java.io.ByteArrayInputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import test.Money;


/** пример преобразования объекта Java в XML и обратно - из XML в Java объект*/
public class Example {
	public static void main(String[] args){
		System.out.println("begin");
		//ExampleObject tempObject=new ExampleObject("test object");
		Money money=new Money(100,"EUR");
		String marshal=marshalObjectToXml(money);
		System.out.println("Marshal Object:"+marshal);
		
		Object unmarshal=unmarshalObjectFromXml(marshal,Money.class);
		System.out.println("Unmarshal: "+unmarshal);
		System.out.println("end");
	}
	
	private static String marshalObjectToXml(Object javaObject){
		StringWriter writer=new StringWriter();
		try{
			System.out.println("Package:"+javaObject.getClass().getPackage().getName());
			javax.xml.bind.JAXBContext jaxbContext=javax.xml.bind.JAXBContext.newInstance(javaObject.getClass());//javaObject.getClass().getPackage().getName()
			javax.xml.bind.Marshaller marshaller=jaxbContext.createMarshaller();
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.marshal(javaObject, writer);
			writer.close();
		}catch(Exception ex){
			System.err.println("Marshal Exception:"+ex.getMessage());
		}
		return writer.toString();
	}
	
	private static Object unmarshalObjectFromXml(String xmlString, Class forUnmarshal){
		Object returnValue=null;
		try{
			ByteArrayInputStream input = new ByteArrayInputStream (xmlString.getBytes());
			JAXBContext jc = JAXBContext.newInstance(forUnmarshal);
			Unmarshaller u = jc.createUnmarshaller();
			returnValue = u.unmarshal( input); 			
		}catch(Exception ex){
			System.err.println("Unmarshal Exception:"+ex.getMessage());
		}
		return returnValue;
	}
}
