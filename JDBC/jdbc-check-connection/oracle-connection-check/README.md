# Oracle check connection
```sh
cd $HOME_PROJECTS/java-code-example/JDBC/jdbc-check-connection/oracle-connection-check
mvn clean package

ORACLE_USER=$ORACLE_INT_USER
ORACLE_PASS=$ORACLE_INT_PASS
ORACLE_SERVICE=$ORACLE_INT_SERVICE
ORACLE_HOST=$ORACLE_INT_HOST
ORACLE_PORT=$ORACLE_INT_PORT

oracle_url="jdbc:oracle:thin:@${ORACLE_HOST}:${ORACLE_PORT}:${ORACLE_SERVICE}"
oracle_user=$ORACLE_USER
oracle_password=$ORACLE_PASS
oracle_driver='oracle.jdbc.driver.OracleDriver'

# path to driver must be without any ~
java -cp $HOME_PROJECTS/java-code-example/JDBC/jdbc-check-connection/oracle-connection-check/target/oracle-connection-check-1.0-SNAPSHOT.jar:/home/q453337/.m2/repository/com/oracle/database/jdbc/ojdbc8/21.9.0.0/ojdbc8-21.9.0.0.jar tools.checker.App  $oracle_url $oracle_user $oracle_password $oracle_driver
```
