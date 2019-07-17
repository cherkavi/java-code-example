import java.text.MessageFormat;


public class RoundFloat {
	public static void main(String[] args){
		double value=1461.59998f;
		Double value2= (double)(Math.round(value*100));
		System.out.println(MessageFormat.format("{0,number,#0.00000}", new Object[]{value2/100}));
	}
}
