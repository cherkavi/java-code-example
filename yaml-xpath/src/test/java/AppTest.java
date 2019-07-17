/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStreamReader;
import java.util.Map;

public class AppTest {

    @Test public void testAppHasAGreeting() throws YamlException {
        // given
        String pathToTestData = "test.yaml";
        YamlReader reader = new YamlReader(new InputStreamReader(App.class.getResourceAsStream(pathToTestData)));
        String controlPath = "spring.profiles";
        String controlValue = "integration";


        // when
        Object result = App.readBlockByControlValue(reader, controlPath, controlValue);

        // then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Map);
        Assert.assertNotNull(App.readPath(result, "datasource.brandserver"));
        Assert.assertNotNull(App.readPath(result, "datasource.ccp"));
    }

}
