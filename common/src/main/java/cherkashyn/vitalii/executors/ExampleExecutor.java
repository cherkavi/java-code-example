package cherkashyn.vitalii.executors;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.*;

public class ExampleExecutor {
    private static Logger LOGGER=Logger.getLogger(ExampleExecutor.class.getName());
    static{
        ConsoleHandler handler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.ALL);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService
                = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(3), new ThreadPoolExecutor.CallerRunsPolicy());
        for(int index=0;index<10;index++){
            executorService.execute(new Printer(index));
        }
        executorService.shutdown();
        executorService.awaitTermination(1L, TimeUnit.DAYS);
        LOGGER.fine(".end.");
    }

    private static List<Printer> preparePrinters(int size) {
        List<Printer> returnValue=new LinkedList<>();
        while(size>0){
            returnValue.add(new Printer(size--));
        }
        return returnValue;
    }

}

class Printer extends Thread{
    private static Logger LOGGER=Logger.getLogger(Printer.class.getName());
    static{
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.ALL);
    }
    private final static long DELAY=1L;
    private final int value;

    Printer(int value){
        LOGGER.fine(value+"-create-"+value);
        this.value=value;
    }

    @Override
    public void run() {
        LOGGER.fine(value+"--begin--"+Thread.currentThread().getName());
        try {
            TimeUnit.SECONDS.sleep(DELAY);
        } catch (InterruptedException e) {
        }
        LOGGER.fine(value+"---end---"+Thread.currentThread().getName());
    }

}
