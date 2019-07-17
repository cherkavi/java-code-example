import java.util.*;

public class CollectionFactoryMethods {
	
    public static void main(String[] args) {
    	createImmutableList();
    }
    
    
    private static void createImmutableList() {
        System.out.println("list with numbers: " + List.of(1,2,3,4,5));
        
        System.out.println("set with numbers: " + Set.of(1,3,4,5));
        
        System.out.println("map with numbers: " + Map.of("one", 1,"three",3, "four", 4, "five", 5));
    }
    
}
