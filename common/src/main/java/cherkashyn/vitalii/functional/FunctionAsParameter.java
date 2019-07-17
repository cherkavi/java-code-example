package cherkashyn.vitalii.functional;

import java.util.function.Function;

public class FunctionAsParameter {

    public static void main(String ... values){
        // example of reference to function/method
        Function<Integer, String> funcReference = new CommonInstance()::converter;
        funcReference.apply(50);
        executor(funcReference, 50);

        // example of reference to static function/method
        Function<Integer, String> staticFuncReference = FunctionAsParameter::converter;
        staticFuncReference.apply(50);

        // example of reference to function/method with two parameters
        Function2<Integer, Integer, String> func2Reference = new CommonInstance()::summator;
        func2Reference.apply(5, 10);
    }

    static String converter(Integer value){
        System.out.println(String.format("static converter: %d",value));
        return Integer.toString(value);
    }

    static String executor(Function<Integer, String> func, Integer parameter){
        return func.apply(parameter);
    }

    private static class CommonInstance{
        String converter(Integer value){
            System.out.println(String.format("regular function: %d",value));
            return Integer.toString(value);
        }
        String summator(Integer value, Integer value2){
            System.out.println(String.format("regular function with two parameters: %d",value));
            return Integer.toString(value+value2);
        }
    }

    @FunctionalInterface
    public interface Function2<T1, T2, R> {
        R apply(T1 param1, T2 param2);
    }
}
