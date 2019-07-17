package cherkashyn.vitalii.stream;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class CheckingParallelStream {

    public static void main(String args[]){
        System.out.println("-begin-");
        Arrays.asList("one","two","three","four","five","six","seven").parallelStream().forEach(CheckingParallelStream::process);
        try {
            ForkJoinPool.commonPool().awaitTermination(Integer.MAX_VALUE, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--end--");
    }

    private static void process(String marker){
        String prefix=Thread.currentThread().getName();
        System.out.println(prefix+"  "+marker+" : begin ");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        System.out.println(prefix+"  "+marker+" : end");
    }

}
