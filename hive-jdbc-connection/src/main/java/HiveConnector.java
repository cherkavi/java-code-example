import java.sql.*;

public class HiveConnector {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        System.out.println("begin the game");

        // Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver")
        Class clazz = Class.forName("org.apache.hive.jdbc.HiveDriver");
        System.out.println("loaded driver is: " + clazz);


        // Direct binary
        String jdbcDirect = "jdbc:hive2://localhost:10000/default";

        // Direct http
        // String jdbcDirectHttp = "jdbc:hive2://localhost:10001/default;transportMode=http;httpPath=cliservice";

        // ZooKeeper binary
        String jdbcZooKeeper = "jdbc:hive2://localhost:2181/default;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2";

        // ZooKeeper binary
        String jdbcZooKeeperHttp = "jdbc:hive2://localhost:2181/default;transportMode=http;httpPath=cliservice";

        Connection connection = DriverManager.getConnection(jdbcDirect, "", "");
        System.out.println("JDBC connection with Hive: " + connection);

        // hcat -e "describe school_explorer"

        try(Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery("select count(*) from school_explorer");
            while(rs.next()){
                for(int index=1;index<=rs.getMetaData().getColumnCount();index++){
                    System.out.print("<"+index+">"+ rs.getString(index));
                }
                System.out.println();
            }
        }
    }
}
