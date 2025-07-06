/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.sistemparkir;

/**
 *
 * @author yuyu
 */
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import javax.swing.*;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
public class DashboardParkir extends javax.swing.JFrame {

    /**
     * Creates new form DashboardParkir
     */
    public DashboardParkir() {
         setUndecorated(false); 
        initComponents();
        setTitle("Dashboard Admin Parkir");
         setExtendedState(JFrame.MAXIMIZED_BOTH);
         setDefaultCloseOperation(EXIT_ON_CLOSE);
        
 }
    public void tampilkanData() {
    DefaultTableModel model = new DefaultTableModel();
    
    model.addColumn("NO");
    model.addColumn("Kode Akses");
    model.addColumn("Tipe");
    model.addColumn("Waktu Masuk");

    try {
        Connection conn = KoneksiDB.getConnection();
        String sql = "SELECT * FROM parkir";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        
        int no = 1;
        while (rs.next()) {
            model.addRow(new Object[]{
                no++,
                rs.getString("barcode"),
                rs.getString("tipe"),
                rs.getString("waktu_masuk"),
            });
        }

        tbl_Anggota.setModel(model);
        
        tbl_Anggota.getColumnModel().getColumn(0).setPreferredWidth(30);
        tbl_Anggota.getColumnModel().getColumn(0).setMinWidth(30);
        tbl_Anggota.getColumnModel().getColumn(0).setMaxWidth(30);

        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < tbl_Anggota.getColumnCount(); i++) {
            tbl_Anggota.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        ((DefaultTableCellRenderer) tbl_Anggota.getTableHeader().getDefaultRenderer())
            .setHorizontalAlignment(JLabel.CENTER);
    } catch (SQLException e) {
        System.out.println("Gagal mengambil data: " + e.getMessage());
    }
    
    tampilkanStatistik(lKendaraan, lPendapatan );
}
    public static void tampilkanStatistik(JLabel lblKendaraan, JLabel lblPendapatan) {
    try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_data_parkir", "root", "")) {

        // 1. Hitung jumlah kendaraan di dalam
        String sqlKendaraan = "SELECT COUNT(*) AS jumlah_kendaraan FROM parkir";
        PreparedStatement ps1 = conn.prepareStatement(sqlKendaraan);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            int jumlahKendaraan = rs1.getInt("jumlah_kendaraan");
            lblKendaraan.setText(String.valueOf(jumlahKendaraan));
        }

        // 2. Hitung total pendapatan
        String sqlPendapatan = "SELECT SUM(biaya) AS total_pendapatan FROM riwayat_parkir";
        PreparedStatement ps2 = conn.prepareStatement(sqlPendapatan);
        ResultSet rs2 = ps2.executeQuery();
        if (rs2.next()) {
            int totalPendapatan = rs2.getInt("total_pendapatan");
            lblPendapatan.setText("Rp " + totalPendapatan);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Gagal menampilkan statistik: " + e.getMessage());
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel_utama = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        logo1 = new javax.swing.JLabel();
        btnScanOUT = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnScanIN = new javax.swing.JButton();
        jSplitPane3 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Anggota = new javax.swing.JTable();
        jSplitPane4 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        lKendaraan = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lPendapatan = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        panel_utama.setBackground(new java.awt.Color(102, 102, 102));
        panel_utama.setPreferredSize(new java.awt.Dimension(1920, 1080));

        jLabel1.setBackground(new java.awt.Color(102, 102, 102));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Dashboard Admin Parkir STT Wastukancana");

        jSplitPane1.setPreferredSize(new java.awt.Dimension(1920, 1080));

        jPanel3.setBackground(new java.awt.Color(204, 204, 204));

        btnScanOUT.setBackground(new java.awt.Color(153, 153, 153));
        btnScanOUT.setText("Scan Barcode Keluar");
        btnScanOUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanOUTActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 153, 153));
        jButton2.setText("cetak tiket");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnScanIN.setBackground(new java.awt.Color(153, 153, 153));
        btnScanIN.setText("Scan Barcode");
        btnScanIN.setBorderPainted(false);
        btnScanIN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanINActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnScanIN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnScanOUT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(logo1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addComponent(btnScanOUT, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(btnScanIN, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(jPanel3);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        tbl_Anggota.setAutoCreateRowSorter(true);
        tbl_Anggota.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tbl_Anggota.setFont(new java.awt.Font("Source Code Pro Medium", 0, 14)); // NOI18N
        tbl_Anggota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"DADA", "DAD", "DAD", null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbl_Anggota.setAutoscrolls(false);
        tbl_Anggota.setRowHeight(40);
        tbl_Anggota.setRowMargin(10);
        tbl_Anggota.setShowGrid(true);
        jScrollPane1.setViewportView(tbl_Anggota);

        jSplitPane3.setTopComponent(jScrollPane1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, new java.awt.Color(255, 255, 255), null, null), "Jumlah Kendaraan Didalam", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));

        lKendaraan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lKendaraan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lKendaraan.setText("disini jumlah kendaraan yang parkir");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(538, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lKendaraan, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(902, Short.MAX_VALUE))
        );

        jSplitPane4.setLeftComponent(jPanel2);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Jumlah Pendapatan ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.ABOVE_TOP));

        lPendapatan.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lPendapatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lPendapatan.setText("disini jumlah pendapatan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(lPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(lPendapatan, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(884, Short.MAX_VALUE))
        );

        jSplitPane4.setRightComponent(jPanel1);

        jSplitPane3.setRightComponent(jSplitPane4);

        jSplitPane1.setRightComponent(jSplitPane3);

        javax.swing.GroupLayout panel_utamaLayout = new javax.swing.GroupLayout(panel_utama);
        panel_utama.setLayout(panel_utamaLayout);
        panel_utamaLayout.setHorizontalGroup(
            panel_utamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_utamaLayout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1948, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(525, 525, 525)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_utamaLayout.setVerticalGroup(
            panel_utamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_utamaLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(996, Short.MAX_VALUE))
            .addGroup(panel_utamaLayout.createSequentialGroup()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1045, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_utama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel_utama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened

      //mengisi label dengan logo stt
      ImageIcon icon = new ImageIcon("src/images/logo.png");
      Image img = icon.getImage();
      int labelWidth = logo1.getWidth();
      int labelHeight = logo1.getHeight();
      Image scaledImg = img.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
      logo1.setIcon(new ImageIcon(scaledImg));
      
      //memanggil fungsi menampilan data di table
      tampilkanData();
      
      tampilkanStatistik(lKendaraan, lPendapatan );
    }//GEN-LAST:event_formWindowOpened

    private void btnScanINActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanINActionPerformed
        BarcodeScanner.scanning(this);
    }//GEN-LAST:event_btnScanINActionPerformed

    private void btnScanOUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanOUTActionPerformed
        // TODO add your handling code here:
        BarcodeScanner.scanningOUT(this);
    }//GEN-LAST:event_btnScanOUTActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        CetakTiketGuest.cetakTiket(this);
    }//GEN-LAST:event_jButton2ActionPerformed

    public static void main(String args[]) {
    
        java.awt.EventQueue.invokeLater(() -> {
            new DashboardParkir().setVisible(true);
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnScanIN;
    private javax.swing.JButton btnScanOUT;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JLabel lKendaraan;
    private javax.swing.JLabel lPendapatan;
    private javax.swing.JLabel logo1;
    private javax.swing.JPanel panel_utama;
    private javax.swing.JTable tbl_Anggota;
    // End of variables declaration//GEN-END:variables

}