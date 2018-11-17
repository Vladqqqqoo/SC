package com.vladqqqqoo.dbconnector;

import java.sql.*;

public class MyDBConnector {
    private String URL;
    private String USERNAME;
    private String PASSWORD;
    private Connection connection;
    private Statement statement;

    public MyDBConnector(String DBName, String username, String password) {
        this.URL = "jdbc:mysql://localhost:3306/" +
                DBName +
                "?verifyServerCertificate=false" +
                "&useSSL=false" +
                "&requireSSL=false" +
                "&useLegacyDatetimeCode=false" +
                "&amp" +
                "&serverTimezone=UTC";
        this.USERNAME = username;
        this.PASSWORD = password;

    }

    public void connect() throws SQLException {
        this.connection = DriverManager.getConnection(this.URL, this.USERNAME, this.PASSWORD);
        this.statement = connection.createStatement();

    }

    public Statement getStatement() {
        return this.statement;
    }
}
