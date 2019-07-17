
public class RangeIteration {
	public static void main(String[] args){
		int counter=0;
		while(counter<1000){
			if(  ((float)counter/100)==((int)(counter/100)) ){
				System.out.println("Predel:"+counter);
			}
			counter++;
		}
	}
}
