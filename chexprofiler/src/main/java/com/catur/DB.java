package com.catur;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
    private Connection con;

    public DB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/dbjava", "root", "");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return con;
    }
}
