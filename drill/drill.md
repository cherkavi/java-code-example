execute on cluster
```sh
java -classpath drill-test-1.0.jar drill.DrillCollaboration 
```

```
class not found: Failure in connecting to Drill: org.apache.drill.exec.rpc.NonTransientRpcException: javax.security.sasl.SaslException: Unknown mechanism: MAPRSASL
# -Ddrill.customAuthFactories=org.apache.drill.exec.rpc.security.maprsasl.MapRSaslFactory
```