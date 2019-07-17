import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Map;

public class App {

    public static Object readBlockByControlValue(YamlReader reader, String path, String controlValue) throws YamlException {
        Object returnValue = null;
        do{
            returnValue = reader.read();
        }while(!controlValue.equals(readPath(returnValue, path)));
        return returnValue;
    }


    public static String readPath(Object root, String path){
        return readPath(root, path.split("\\."));
    }

    private static String readPath(Object root, String[] path){
        if(root==null || path.length==0){
            return null;
        }
        Object newRoot = ((Map)root).get(path[0]);

        if(path.length==1){
            return newRoot.toString();
        }else{
            return readPath(newRoot, Arrays.copyOfRange(path, 1, path.length) );
        }
    }


}
