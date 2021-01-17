import java.sql.*;
import java.lang.ClassNotFoundException;

public class DrillCollaboration {
    // private static final String JDBC_DRIVER = "com.mapr.drill.jdbc4.Driver";
    // private static final String JDBC_DRIVER = "com.mapr.drill.jdbc41.Driver";
    // private static final String JDBC_DRIVER = "oadd.org.apache.calcite.avatica.remote.Driver";
    private static final String JDBC_DRIVER = "org.apache.drill.jdbc.Driver";

    private static final String CONNECTION_URL = "jdbc:drill:drillbit=ubsdpdesp000103.vantagedp.org:31010;auth=MAPRSASL";
    // private static final String QUERY = "select sessionId, isReprocessable from dfs.`/mapr/dp.prod.zurich/vantage/data/store/v6/BM67/2020/*/*/part*.parquet`";
    private static final String QUERY = "select * from sys.version";

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        System.out.println("get class by name: "+JDBC_DRIVER);
        Class.forName(JDBC_DRIVER);
        System.out.println("obtaining connection");
        return DriverManager.getConnection(CONNECTION_URL);
    }

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (ClassNotFoundException e) {
            System.err.println("class not found: "+e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.err.println("class not found: "+e.getMessage());
            System.exit(2);
        }
        System.out.println("connection established");
        Statement statement = connection.createStatement();
        System.out.println("statement created");
        ResultSet resultSet = statement.executeQuery(QUERY);
        System.out.println("attempt to retrieve results");
        while (resultSet.next()) {
            System.out.println(resultSet.getString(1));
        }
        // close resultSet, statement
    }
}

