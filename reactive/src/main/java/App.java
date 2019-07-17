import io.reactivex.*;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableJoin;
import io.reactivex.internal.operators.observable.ObservableJust;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/*
 * @see http://reactivex.io/documentation/operators.html
 */
public class App {

    public static void main(String[] args) {
        flowFromArray();

        System.out.println("------------------");

        observableFromArray();

        System.out.println("------------------");

        mergeThreadObserver();

        System.out.println("------------------");

        joinThreadObserver();
    }

    private static void joinThreadObserver() {
        Observable<String> observable1 = Observable.create(new TimeLineEmulator(new RandomGenerator("A1_", 0, 9, 120)));
        Observable<String> observable2 = Observable.create(new TimeLineEmulator(new RandomGenerator("B2_", 0, 9, 220)));

        new ObservableJoin(observable1, observable2,
                new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        // System.out.println("observable left : "+s);
                        return Observable.never(); //  just("left: "+s);
                    }
                },
                new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        // System.out.println("observable right : "+s);
                        return Observable.never(); //  just("right: "+s);
                    }
                },
                new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String o, String o2) throws Exception {
                        return "BiFunction: "+o+o2;
                    }
                }
        )
                // .window(1, 200, TimeUnit.MILLISECONDS)
                .subscribe(value ->{
                    // System.out.println( ((UnicastSubject<String>)value)) ;
                    System.out.println( value) ;
                });
    }

    private static void mergeThreadObserver() {
        Observable<String> observable1 = Observable.create(new TimeLineEmulator(new RandomGenerator("A1_", 0, 9, 150)));
        Observable<String> observable2 = Observable.create(new TimeLineEmulator(new RandomGenerator("B2_", 0, 9, 220)));
        observable1.mergeWith(observable2)
                .subscribe(System.out::println);
    }

    private static void observableFromArray() {
        Observable<String> observable = ObservableJust.just("one", "two", "three");
        observable = observable.doOnComplete(() -> System.out.print(">>>das ist alles<<<"))
                .doOnEach((e)-> {
                    if(e.isOnNext()){
                        System.out.println(">>>each<<<: "+e.getValue());
                    }
                });
        observable.subscribe((e)-> System.out.println(e));
    }

    private static void flowFromArray() {
        Flowable<String> flow = Flowable.fromArray("this", "is", "result");

        flow = flow
                .doOnComplete(() -> System.out.print(">>>das ist alles<<<"))
                .doOnEach((e)-> {
                    if(e.isOnNext()){
                        System.out.println(">>>each<<<: "+e.getValue());
                    }
                });

        flow.subscribe(System.out::println);
    }

}


class TimeLineEmulator implements ObservableOnSubscribe<String>, Runnable{

    private final RandomGenerator generator;
    private ObservableEmitter<String> emitter;

    TimeLineEmulator(RandomGenerator generator){
        this.generator = generator;
    }

    @Override
    public void subscribe(ObservableEmitter<String> e) throws Exception {
        this.emitter = e;
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(!generator.isDone()){
            try {
                this.emitter.onNext(this.generator.get());
            } catch (InterruptedException e) {
                this.emitter.onError(e);
                break;
            }
        }
    }
}

class RandomGenerator implements Future<String> {

    private int current;
    private final int high;
    private final int delay;
    private final String prefix;

    RandomGenerator(String prefix, int low, int high, int delayInMiliseconds){
        this.prefix = prefix;
        this.current = low;
        this.high = high;
        this.delay = delayInMiliseconds;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.current>this.high;
    }

    @Override
    public String get() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(this.delay);
        return getValue();
    }

    @Override
    public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        throw new IllegalStateException("state error");
    }

    private String getValue() {
        return this.prefix+Integer.toString(++this.current);
    }
}