public class InterfacePrivateMethods {
	
    public static void main(String[] args) {
    	Checker checker = new CheckerImpl();
    	checker.preCheck();
    	checker.check();
    }
    
    
}

class CheckerImpl implements Checker{

	@Override
	public void check() {
		System.out.println("> check");		
	}
	
}

interface Checker{
	void check();
	
	default void preCheck() {
		printInfo("> preCheck");
		init();
	}
	
	private void init() { // only private methods can be implemented 
		printInfo("> init");
	}
	
	private static void printInfo(String data) { // private Static method implementation
		System.out.println(data);
	}
	
}
