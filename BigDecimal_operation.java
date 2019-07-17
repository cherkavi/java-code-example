package test;

import java.math.BigDecimal;

public class BigDecimal_operation {
	public static void main(String[] args){
		System.out.println("begin");
		BigDecimal value=new BigDecimal(5);
		System.out.println("Multiply to 0: "+value.multiply(new BigDecimal(0)));
		try{
			System.out.println("Multiply to null: "+value.multiply(null));
		}catch(Exception ex){
			System.out.println("Multiply to null Exception: "+ex.getMessage());
		}
		try{
			System.out.println("Divide by 0: "+value.divide(new BigDecimal(0)));
		}catch(Exception ex){
			System.out.println("Divide by 0 Exception:"+ex.getMessage());
		}
		System.out.println("-end-");
	}
}
