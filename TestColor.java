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
		
		System.out.println("(new Color.BLACK):"+getStringValueFromColor(Color.black));
		System.out.println("(new Color(150,0,0)):"+getStringValueFromColor(new Color(150,0,0)));
		System.out.println("(new Color(50,200,50)):"+getStringValueFromColor(new Color(50,200,50)));
		System.out.println("(new Color(200,50,50)):"+getStringValueFromColor(new Color(200,50,50)));
		System.out.println("(new Color(50,50,200)):"+getStringValueFromColor(new Color(50,50,200)));
		System.out.println("(new Color(238,238,238)):"+getStringValueFromColor(new Color(238,238,238)));
		System.out.println("(new Color.LIGHT_GRAY):"+getStringValueFromColor(Color.LIGHT_GRAY));
		System.out.println("(new Color(238,180,180)):"+getStringValueFromColor(new Color(238,180,180)));
		System.out.println("(new Color.GRAY):"+getStringValueFromColor(Color.GRAY));
		System.out.println("(new Color.green):"+getStringValueFromColor(Color.green));
		System.out.println("(new Color.red):"+getStringValueFromColor(Color.red));
	}
	
	/** преобразовать цвет в строку, которая может быть распознана статическим методом Decode*/
	private static String getStringValueFromColor(Color color){
		return "0x"+Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1); 
	}
}
