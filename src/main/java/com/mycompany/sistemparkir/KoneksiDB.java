/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemparkir;

/**
 *
 * @author yuyu
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class KoneksiDB {
    public static Connection getConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/db_data_parkir";
            String user = "root";
            String pass = ""; // kosong jika belum kamu atur

            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println("Koneksi gagal: " + e.getMessage());
            return null;
        }
    }
}
