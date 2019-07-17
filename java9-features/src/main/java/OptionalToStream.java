import java.util.Optional;

public class OptionalToStream {
	
    public static void main(String[] args) {
    	System.out.println(" Optional.map: " + Optional.of("2").map(Integer::parseInt));
    	System.out.println(" Optional.stream: " + Optional.of("2").stream().mapToInt(Integer::parseInt).findFirst());
    }
    
    
}
