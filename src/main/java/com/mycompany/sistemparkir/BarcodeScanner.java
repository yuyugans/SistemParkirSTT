/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemparkir;

/**
 *
 * @author yuyu
 */

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import java.awt.Dimension;

import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BarcodeScanner {
    public static void scanning(DashboardParkir mainFrame) {
        new Thread(() -> {
            Webcam webcam = Webcam.getDefault();
            webcam.setViewSize(new Dimension(640, 480));
            WebcamPanel panel = new WebcamPanel(webcam);
            panel.setFPSDisplayed(true);
            panel.setMirrored(true);

            JFrame window = new JFrame("Scan Barcode");
            window.add(panel);
            window.setResizable(false);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true);

            webcam.open();

            while (true) {
                BufferedImage image = webcam.getImage();
                if (image == null) continue;

                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                try {
                    Result result = new MultiFormatReader().decode(bitmap);
                    if (result != null) {
                        String barcode = result.getText();
                        JOptionPane.showMessageDialog(null, "Barcode terbaca: " + barcode);
                        
                        prosesBarcode(barcode);
                        mainFrame.tampilkanData();
                        break;
                    }
                } catch (Exception e) {
                    // ignore
                }

                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }

            webcam.close();
            window.dispose();
        }).start();
    }
    
     public static void scanningOUT(DashboardParkir mainFrame) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Webcam webcam = Webcam.getDefault();
                webcam.setViewSize(new Dimension(640, 480));
                WebcamPanel panel = new WebcamPanel(webcam);
                panel.setFPSDisplayed(true);
                panel.setMirrored(true);
                
                JFrame window = new JFrame("Scan Barcode");
                window.add(panel);
                window.setResizable(false);
                window.pack();
                window.setLocationRelativeTo(null);
                window.setVisible(true);
                
                webcam.open();
                
                while (true) {
                    BufferedImage image = webcam.getImage();
                    if (image == null) continue;
                    
                    LuminanceSource source = new BufferedImageLuminanceSource(image);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    
                    try {
                        Result result = new MultiFormatReader().decode(bitmap);
                        if (result != null) {
                            String barcode = result.getText();
                            JOptionPane.showMessageDialog(null, "Barcode terbaca: " + barcode);
                            
                            prosesBarcodeOut(barcode);
                            mainFrame.tampilkanData();
                            break;
                        }
                    } catch (Exception e) {
                        // ignore
                    }
                    
                    try { Thread.sleep(100); } catch (InterruptedException e) {}
                }
                
                webcam.close();
                window.dispose();
            }
        }).start();
    }
    
    public static void prosesBarcode(String barcode) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_data_parkir", "root", "")) {

            // 1. Cek apakah barcode ada di tabel anggota
            PreparedStatement ps = conn.prepareStatement("SELECT jenis FROM anggota WHERE barcode = ?");
            ps.setString(1, barcode);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String jenis = rs.getString("jenis");

                // 2. Masukkan ke tabel parkir
                PreparedStatement insert = conn.prepareStatement(
                    "INSERT INTO parkir (barcode, tipe, waktu_masuk) VALUES (?, ?, NOW())"
                );
                insert.setString(1, barcode);
                insert.setString(2, "anggota");
                insert.executeUpdate();

                JOptionPane.showMessageDialog(null, "Akses diberikan. (" + jenis + ") tercatat masuk.");
            } else {
                JOptionPane.showMessageDialog(null, "Barcode tidak ditemukan sebagai anggota.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Koneksi database gagal: " + ex.getMessage());
        }
    }
    
   public static void prosesBarcodeOut(String barcode) {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_data_parkir", "root", "")) {

        // 1. Cek apakah barcode ada di parkir
        PreparedStatement psParkir = conn.prepareStatement("SELECT waktu_masuk FROM parkir WHERE barcode = ?");
        psParkir.setString(1, barcode);
        ResultSet rsParkir = psParkir.executeQuery();

        if (rsParkir.next()) {
            Timestamp waktuMasuk = rsParkir.getTimestamp("waktu_masuk");

            // 2. Cek apakah barcode adalah anggota
            PreparedStatement psAnggota = conn.prepareStatement("SELECT * FROM anggota WHERE barcode = ?");
            psAnggota.setString(1, barcode);
            ResultSet rsAnggota = psAnggota.executeQuery();

            if (rsAnggota.next()) {
                // Jika anggota, langsung hapus dari parkir
                PreparedStatement delete = conn.prepareStatement("DELETE FROM parkir WHERE barcode = ?");
                delete.setString(1, barcode);
                delete.executeUpdate();

                JOptionPane.showMessageDialog(null, "Anggota keluar. Data parkir dihapus.");
            } else {
                // Jika tamu, hitung durasi dan biaya
                Timestamp waktuKeluar = new Timestamp(System.currentTimeMillis());
                long selisihMillis = waktuKeluar.getTime() - waktuMasuk.getTime();
                long menit = selisihMillis / (60 * 1000);

                int biayaPerJam = 2000;
                int jam = (int) Math.ceil(menit / 60.0);
                int totalBiaya = jam * biayaPerJam;

                // Simpan ke riwayat_parkir
                PreparedStatement insertRiwayat = conn.prepareStatement(
                    "INSERT INTO riwayat_parkir (barcode, tipe, waktu_masuk, waktu_keluar, biaya) VALUES (?, ?, ?, ?, ?)"
                );
                insertRiwayat.setString(1, barcode);
                insertRiwayat.setString(2, "guest");
                insertRiwayat.setTimestamp(3, waktuMasuk);
                insertRiwayat.setTimestamp(4, waktuKeluar);
                insertRiwayat.setInt(5, totalBiaya);
                insertRiwayat.executeUpdate();

                // Hapus dari tabel parkir
                PreparedStatement delete = conn.prepareStatement("DELETE FROM parkir WHERE barcode = ?");
                delete.setString(1, barcode);
                delete.executeUpdate();

                JOptionPane.showMessageDialog(null,
                    "Tamu keluar.\nDurasi: " + jam + " jam\nBiaya: Rp" + totalBiaya);
            }

        } else {
            JOptionPane.showMessageDialog(null, "Data parkir tidak ditemukan untuk barcode ini.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan: " + e.getMessage());
    }
}

    //tidak di gunakan
    public static String scanFromImage(File file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file);
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            System.out.println("Gagal membaca barcode: " + e.getMessage());
            return null;
        }
        
    }
    
  }
    

