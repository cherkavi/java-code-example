```xml
<dependency>
  <groupId>org.graalvm.js</groupId>
  <artifactId>js</artifactId>
  <version>22.0.0</version>
</dependency>  
<dependency>
  <groupId>org.graalvm.js</groupId>
  <artifactId>js-scriptengine</artifactId>
  <version>22.0.0</version>
</dependency>
```

```java
ScriptEngine graalEngine = new ScriptEngineManager().getEngineByName("graal.js");
graalEngine.eval("print('hello from script');");
```
