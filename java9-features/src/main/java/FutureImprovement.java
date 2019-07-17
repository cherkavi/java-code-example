import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FutureImprovement {
	
    public static void main(String[] args) throws InterruptedException, ExecutionException {
    	CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
    		try {
				TimeUnit.SECONDS.sleep(1L);
			} catch (InterruptedException e) {}
    		System.out.println(" >>> thread from future ");
    		return "hello async: ";
    	});
    	
    	
    	// System.out.println("Future1 result: " + cf1.get());
    	System.out.println("Future1 isDone: " + cf1.isDone());
    	System.out.println("Future1 isCancel: " + cf1.isCancelled());
    	System.out.println();

    	CompletableFuture<String> cf2 = cf1.copy();
    	System.out.println("Future2 isDone: " + cf2.isDone());
    	System.out.println("Future2 isCancel: " + cf2.isCancelled());
    	System.out.println();
    	
    	cf1.complete("new value");
    	System.out.println("Future2 get: " + cf2.get());
    	System.out.println("Future2 isDone: " + cf2.isDone());
    	System.out.println("Future2 isCancel: " + cf2.isCancelled());
    	System.out.println();
    	
    	System.out.println("Future1 isDone: " + cf1.isDone());
    	System.out.println("Future1 isCancel: " + cf1.isCancelled());
    	System.out.println();

    }
    
}

