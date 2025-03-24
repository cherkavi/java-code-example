# JDBC connection checker

## env variables
```sh
echo $JDBC_URL       # Examples for Oracle:
                     # no service: jdbc:oracle:thin:@server.ubs.zur:1524:pcm
                     #    service: jdbc:oracle:thin:@server.ubs.zur:1524/pcm
                     #  tns entry: jdbc:oracle:oci:@server.ubs.zur
                     #       ldap: jdbc:oracle:thin:@ldap://server:389/orainv,cn=oraclecontext,dc=zur,dc=ubs,dc=ch
echo $JDBC_USER      # my-user
echo $JDBC_PASS      # my-secret-password
echo $JDBC_DRIVER    # oracle.jdbc.OracleDriver
```


## Java
project creation 
```sh
mvn archetype:generate -DgroupId=tool.checkers -DartifactId=oracle-connection-check -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```

project build
```sh
cd oracle-connection-check
mvn clean install
```

project run 
```sh
cd oracle-connection-check
java -cp ./target/oracle-connection-check-1.0-SNAPSHOT.jar:$HOME/.m2/repository/com/oracle/database/jdbc/ojdbc8/19.3.0.0/ojdbc8-19.3.0.0.jar tools.checker.App  $JDBC_URL $JDBC_USER $JDBC_PASS $JDBC_DRIVER
```

## Groovy
```sh
groovy OracleConnectionCheck.groovy  $JDBC_URL $JDBC_USER $JDBC_PASS $JDBC_DRIVER 
```
