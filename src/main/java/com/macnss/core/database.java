package com.macnss.core;

import com.macnss.helpers.envLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class database {
    private static volatile Connection connection = null;
    private static final envLoader env = new envLoader();

    private database() {
    }

    static {
        if (connection == null) {
            synchronized (database.class) {
                if (connection == null) {
                    try {
                        Class.forName(env.get("DB_CLASS_NAME"));
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

