import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class AppClassic {

    public static void main(String ... args){
        System.out.println("--begin--");

        CompletableFuture<String> postponedCalculation = CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {}
            System.out.println("supplyAsync(1) thread: "+Thread.currentThread().getName());
            return "result(1):"+new Date().toString();
        });

        postponedCalculation = postponedCalculation.thenCombineAsync(CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {}
            System.out.println("supplyAsync(2) thread: "+Thread.currentThread().getName());
            return "result(2):"+new Date().toString();
        }),
                (s, u) -> s+u);

        postponedCalculation
            .thenAccept(s-> {
                System.out.println("after calculation ("+Thread.currentThread().getName()+"): " + s);
            })
            .thenRunAsync(() -> System.out.println("run async after accept ("+Thread.currentThread().getName()+"):"));

        System.out.println("---end---");
        try {
            ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) { e.printStackTrace(); }
    }

}
