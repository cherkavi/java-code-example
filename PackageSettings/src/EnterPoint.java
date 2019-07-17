import settings.PropertiesSettings;
import settings.Settings;


/** класс, который тестирует сделанные изменения в файле*/
public class EnterPoint {
	public static void main(String[] args){
		String filePath="c:\\one.ini";
		Settings settings=new PropertiesSettings(filePath);
		settings.setString("parent1.value1", "this is value 1");
		settings.setInteger("//parent1/value2", 55);
		settings.setDouble("parent2/value1", 2.5d);
		if(settings.commitChange()==false){
			System.out.println("CommitChange is false");
		}else{
			System.out.println("OK");
		}
		settings=null;
		
		Settings loader=new PropertiesSettings(filePath);
		System.out.println(loader.getString("parent2.value1", null));
	}
}
