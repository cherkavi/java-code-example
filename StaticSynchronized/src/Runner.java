
public class Runner extends Thread{
	private String preambula;
	private int delay;
	public Runner(String preambula,int delay){
		this.preambula=preambula;
		this.delay=delay;
		this.start();
	}
	
	public void run(){
		while(true){
			System.out.println(this.preambula+":"+Resource.getValue(delay));
		}
	}
}
