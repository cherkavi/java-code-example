import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.EncodedByteArrayConverter;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;


/** демонстрация сериализации и десериализации объекта, используя XML */
public class EnterPoint {
	public static void main(String[] args){
		System.out.println("Begin");
		Complex complex=new Complex(new Simple("simple",1),"parent");
		System.out.println("Object before: >>> "+complex);
		
		xmlTransport(complex);
		
		//jsonTransport(complex);
		
		System.out.println("End");
	}
	
	private static void jsonTransport(Object complex ){
		// Сериализация объекта в XML
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
		//xstream.alias("Complex", Complex.class);
		String serialize=xstream.toXML(complex);
		System.out.println(serialize);
		
		// Десириализация объекта из XML - DOM Driver
		xstream = new XStream(new JettisonMappedXmlDriver()); // does not require XPP3 library
		//xstream.alias("Complex", Complex.class);
		System.out.println("Object after: >>> "+xstream.fromXML(serialize));
	}
	
	private static void xmlTransport(Object complex){
		// Сериализация объекта в XML
		XStream xstream = new XStream();
		//xstream.registerConverter(new EncodedByteArrayConverter(),XStream.PRIORITY_VERY_LOW);
		//xstream.alias("Complex", Complex.class);
		String serialize=xstream.toXML(complex);
		System.out.println(serialize);
		
		// Десириализация объекта из XML - DOM Driver
		xstream = new XStream(new DomDriver()); // does not require XPP3 library
		//xstream.registerConverter(new EncodedByteArrayConverter(),XStream.PRIORITY_VERY_LOW);
		System.out.println("Object after: >>> "+xstream.fromXML(serialize));
	}
	
}


class Complex{
	private Simple simpleObject;
	private String name;
	private byte[] arrayOfByte=new byte[20];

	public Complex(){
		
	}

	public byte[] getArray(){
		return arrayOfByte;
	}
	public Complex(Simple simpleObject, String name){
		this.simpleObject=simpleObject;
		this.name=name;
	}
	
	public Simple getSimpleObject() {
		return simpleObject;
	}
	
	public void setSimpleObject(Simple simpleObject) {
		this.simpleObject = simpleObject;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		StringBuffer returnValue=new StringBuffer();
		returnValue.append("Name:"+name+"  SimpleObject:"+this.simpleObject.toString());
		returnValue.append("\n");
		for(int counter=0;counter<this.arrayOfByte.length;counter++){
			returnValue.append(counter+" : "+this.arrayOfByte[counter]+"\n");
		}
		return returnValue.toString();
	}
}

class Simple{
	private String name;
	private Integer value;
	
	public Simple(){
		
	}

	public Simple(String name, Integer value){
		this.name=name;
		this.value=value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
	public String toString(){
		return "Name: "+name+"   Value:"+value;
	}
}