package com.macnss;

import com.macnss.Core.database;
import com.macnss.app.Services.EmailService;
import com.macnss.view.Authentication.Signing;

import java.sql.Connection;
import java.sql.SQLException;

public class App implements AutoCloseable{

    private Connection connection = null;

    public App() {
        try {
            this.connection = database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Signing();
        new EmailService().send("ouharri.outman@gmail.com", "sba7 l5eer","test");
    }

    @Override
    public void close() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}