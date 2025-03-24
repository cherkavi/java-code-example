package tools.checker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java OracleConnectionCheck <url> <user> <password> <driver>");
            System.exit(1);
        }

        String url = args[0];
        String user = args[1];
        String password = args[2];
        String driver = args[3];

        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful!");
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Oracle JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
}
