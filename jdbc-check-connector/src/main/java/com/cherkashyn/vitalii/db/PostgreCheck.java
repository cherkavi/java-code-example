package com.cherkashyn.vitalii.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreCheck {
    private final static String CONTROL_QUERY = "SELECT 1";
    private final static String DRIVER_CLASS_NAME = "org.postgresql.Driver";
    private final static String URL_PREFIX = "jdbc:postgresql://";

    public static void main(String[] args) throws SQLException {
        try {
            Class.forName(DRIVER_CLASS_NAME);
        } catch (ClassNotFoundException e) {
            System.err.println("Where is your PostgreSQL JDBC Driver? Include it in your library path!");
            System.exit(1);
        }

        if(args.length<3){
            System.err.println("should be specified next arguments: \n" +
                    "1 - url to server like 'vldn338:5432/postgres' \n" +
                    "2 - username \n" +
                    "3 - password of the user \n");
            System.exit(2);
        }

        Connection connection = DriverManager.getConnection(
                URL_PREFIX +args[1],
                args[2],
                args[3]);
        if(connection.createStatement().executeQuery(CONTROL_QUERY).next()){
            System.out.println("connection established");
            System.exit(0);
        }else{
            System.err.println("can't read from database");
            System.exit(3);
        }
    }
}
