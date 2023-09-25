package com.macnss.main;

import com.macnss.Core.database;
import com.macnss.view.Authentication.SigningAdministrator;

import java.sql.Connection;
import java.sql.SQLException;

public class admin implements AutoCloseable{

    private Connection connection = null;

    public admin() {
        try {
            this.connection = database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new SigningAdministrator();
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