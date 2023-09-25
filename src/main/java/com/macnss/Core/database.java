package com.macnss.Core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private static volatile Connection connection = null;

    private database() {}

    static {
        if (connection == null) {
            synchronized (database.class) {
                if (connection == null) {
                    try {
                        Class.forName(env.get("JDBC_DRIVER"));
                        String dbUrl = env.get("DB_URL");
                        String dbUsername = env.get("DB_USERNAME");
                        String dbPassword = env.get("DB_PASSWORD");
                        connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}

