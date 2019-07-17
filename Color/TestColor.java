import java.awt.Color;
/** возможность сохранения цвета как строки текста и обратное преобразование */
public class TestColor {
	
	public static void main(String[] arg){
		Color colorRed=Color.red;
		Color colorDefault=new Color(238,238,238);
		
		String stringRed=getStringValueFromColor(colorRed);
		String stringDefault=getStringValueFromColor(colorDefault);
		
		System.out.println("StringRed:"+stringRed);
		System.out.println("StringDefault:"+stringDefault);
		
		Color restoreColorRed=Color.decode(stringRed);
		Color restoreColorDefault=Color.decode(stringDefault);
		System.out.println("Restore String Red:"+restoreColorRed.toString());
		System.out.println("Restore String Default:"+restoreColorDefault.toString());
	}
	
	/** преобразовать цвет в строку, которая может быть распознана статическим методом Decode*/
	private static String getStringValueFromColor(Color color){
		return "0x"+Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1); 
	}
}
