package com.macnss;

import com.macnss.Core.database;
import com.macnss.view.Authentication.SigningAgentCNS;

import java.sql.Connection;
import java.sql.SQLException;

public class Agent implements AutoCloseable{

    private Connection connection = null;

    public Agent() {
        try {
            this.connection = database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new SigningAgentCNS();
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
