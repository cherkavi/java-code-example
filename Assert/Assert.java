public class Assert{
		public static void main(String[] args){
			System.out.println( withdrawMoney(1000,500) );

			System.out.println( withdrawMoney(1000,2000) ); 		
		}

		public static double withdrawMoney(double balance , double amount){
        		assert balance >= amount;
			return balance-amount;
		} 

}