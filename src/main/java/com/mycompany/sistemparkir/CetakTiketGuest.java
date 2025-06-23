/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemparkir;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.client.j2se.MatrixToImageWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CetakTiketGuest {

    public static void cetakTiket(DashboardParkir mainFrame) {
        try {
            // 1. Generate barcode angka saja (berbasis timestamp)
            String barcodeAngka = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String waktuMasuk = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            // 2. Simpan ke database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_data_parkir", "root", "");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO parkir (barcode, tipe, waktu_masuk) VALUES (?, ?, ?)");
            ps.setString(1, barcodeAngka);
            ps.setString(2, "guest");
            ps.setString(3, waktuMasuk);
            ps.executeUpdate();

            // 3. Buat barcode image dari angka murni
            BitMatrix bitMatrix = new MultiFormatWriter().encode(barcodeAngka, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // 4. Simpan ke file
            String fileName = "Tiket_" + barcodeAngka + ".png";
            Path path = Paths.get("tiket", fileName);
            new File("tiket").mkdirs(); // Buat folder jika belum ada
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

            // 5. Tampilkan di dialog
            ImageIcon icon = new ImageIcon(barcodeImage);
            JLabel label = new JLabel("<html><center>Tiket Guest<br>Barcode: " + barcodeAngka + "<br>Waktu Masuk: " + waktuMasuk + "</center></html>", icon, JLabel.CENTER);
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);

            mainFrame.tampilkanData(); // Refresh table
            JOptionPane.showMessageDialog(null, label, "Tiket Guest", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mencetak tiket: " + e.getMessage());
        }
    }
}
