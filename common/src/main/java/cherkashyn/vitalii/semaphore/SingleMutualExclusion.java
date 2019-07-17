package cherkashyn.vitalii.semaphore;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.System.out;

public class SingleMutualExclusion {

    public static void main(String[] args){
        out.println("- begin - ");
        SharedResource resource=new SharedResource();
        List<ThreadExecutor> executors= Arrays.asList(new ThreadExecutor(resource),
                                                      new ThreadExecutor(resource),
                new ThreadExecutor(resource),
                new ThreadExecutor(resource),
                new ThreadExecutor(resource),
                new ThreadExecutor(resource),
                                                      new ThreadExecutor(resource)
        );
        ExecutorService service=Executors.newFixedThreadPool(5);
        for(ThreadExecutor eachExecutor : executors){
            service.execute(eachExecutor);
            try {
                TimeUnit.SECONDS.sleep(1L);
            } catch (InterruptedException e) {
            }
        }
        out.println("-- end -- ");
        service.shutdown();
    }

}

class ThreadExecutor extends Thread{
    private static int counter=0;
    private final SharedResource resource;

    ThreadExecutor(SharedResource resource){
        super("<<"+(counter++)+"++");
        this.resource=resource;
    }

    @Override
    public void run() {
        out.println(Thread.currentThread().getName()+">>>  attempt to use resource");
        try {
            this.resource.action();
        } catch (InterruptedException e) {
        }
        out.println(Thread.currentThread().getName()+"<<<  end using resource ");
    }

}


class SharedResource{
    // Semaphore mutex=new Semaphore(1);
    ReentrantLock lock = new ReentrantLock();

    public void action() throws InterruptedException {
        // if(mutex.tryAcquire()){
        if(lock.tryLock()){
            try{
                out.println("... attempt to acquire by current Thread: "+Thread.currentThread().getName());
                out.println("+++ resource busy by current Thread: "+Thread.currentThread().getName());
                TimeUnit.SECONDS.sleep(2L);
                out.println("--- resource busy by current Thread: "+Thread.currentThread().getName());
            }finally{
//                     mutex.release();
                lock.unlock();
            }
        }
    }

}
