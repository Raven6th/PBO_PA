package com.catur;

import java.sql.*;
import java.util.ArrayList;

public class AccountController {
    private Connection connection;

    public AccountController(Connection connection) {
        this.connection = connection;
    }

    // tambah data ke database
    public void createAkun(Account account) throws SQLException {
        String query = "INSERT INTO account (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, account.getPassword());
            pstmt.setString(3, account.getRole());
            pstmt.executeUpdate();
        }
    }

    // membaca data pada database berdasarkan id
    public Account getAkunByUsername(int username) throws SQLException {
        String query = "SELECT * FROM Account WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new Account(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("role")
                    );
                }
            }
        }
        return null;
    }

    // mengambil semua data Account dari database
    public ArrayList<Account> getAllAkun() throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();
        String query = "SELECT username, password, role FROM account";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                String role = rs.getString("role");
                accounts.add(new Account(username, password, role));
            }
        }
        return accounts;
    }

    // update data pada database
    public void updateAkun(Account account) throws SQLException {
        String query = "UPDATE Account SET username = ?, password = ?, role = ? WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getRole());
            preparedStatement.executeUpdate();
        }
    }

    // menghapus data dari database
    public void deleteAkun(int username) throws SQLException {
        String query = "DELETE FROM Account WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, username);
            preparedStatement.executeUpdate();
        }
    }
}
