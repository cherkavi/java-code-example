import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// map Pair to Map stream toMap
class PairToMap {
    public static void main(String[] args) {
        class Pair{
            Pair(String s1, String s2){
                this.left=s1;
                this.right=s2;
            }
            String left;
            String right;
        }
        List<String> values = Arrays.asList("one", "two", "three", "four");
        Map<String, String> returnValue = values.stream().map(eachValue -> {
            // import org.apache.commons.lang3.tuple.ImmutablePair;
            if(eachValue.length()<=3){
                return new Pair(eachValue, Integer.toString(eachValue.length())+eachValue);
            }else{
                return new Pair(eachValue, Integer.toString(eachValue.length()));
            }
        }).collect(Collectors.toMap(value->value.left, value->value.right));
        System.out.println(returnValue.toString());
    }
}
