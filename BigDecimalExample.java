import java.math.BigDecimal;


public class BigDecimalExample {
	public static void main(String[] args){
		
		System.out.println("begin");
		
		BigDecimal value = new BigDecimal("121.765");
		System.out.println("CEILING: "+value.setScale(2, BigDecimal.ROUND_CEILING));
		System.out.println("DOWN: "+value.setScale(2, BigDecimal.ROUND_DOWN));
		System.out.println("FLOOR: "+value.setScale(2, BigDecimal.ROUND_FLOOR));
		System.out.println("HALF_DOWN: "+value.setScale(2, BigDecimal.ROUND_HALF_DOWN));
		System.out.println("HALF_EVEN: "+value.setScale(2, BigDecimal.ROUND_HALF_EVEN));
		System.out.println("HALF_UP: "+value.setScale(2, BigDecimal.ROUND_HALF_UP));
		// System.out.println("UNNECESSARY: "+value.setScale(2, BigDecimal.ROUND_UNNECESSARY));
		System.out.println("UP: "+value.setScale(2, BigDecimal.ROUND_UP));

		double dValue=32.35;
		BigDecimal value2=new BigDecimal(dValue);
		
		
		System.out.println("-end-");
	}
}
