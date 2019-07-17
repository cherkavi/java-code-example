
public class EnterPoint {
	public static void main(String[] args){
		System.out.println("===OS Name===");
		System.out.println(System.getProperty("os.name"));
		System.out.println("=== ====  ===");
		if(isWindows()){
			System.out.println("this is windows");
		}
		if(isMac()){
			System.out.println("this is MAC");
		}
		if(isUnix()){
			System.out.println("this is *NIX");
		}
	}
	
	public static boolean isWindows(){
		String os = System.getProperty("os.name").toLowerCase();
		//windows
	    return (os.indexOf( "win" ) >= 0); 
	}
 
	public static boolean isMac(){
		String os = System.getProperty("os.name").toLowerCase();
		//Mac
	    return (os.indexOf( "mac" ) >= 0); 
	}
 
	public static boolean isUnix(){
		String os = System.getProperty("os.name").toLowerCase();
		//linux or unix
	    return (os.indexOf( "nix") >=0 || os.indexOf( "nux") >=0);
	}
	
}
