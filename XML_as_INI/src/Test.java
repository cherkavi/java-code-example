import xml_ini.Loader;


public class Test {
	public static void main(String[] args){
		try{
			Loader loader=new Loader("D:\\temp.xml");
			System.out.println("Value:"+loader.getValue("//SETTINGS/VALUE_FOR_LOAD"));
			System.out.println("Value:"+loader.getValue("//SETTINGS/VALUE_FOR_LOAD/"));
			System.out.println("Value:"+loader.getValue("//SETTINGS/VALUE_FOR_LOAD/text()"));
		}catch(Exception ex){
			System.out.println("Loader exception");
		}
		
	}
}
