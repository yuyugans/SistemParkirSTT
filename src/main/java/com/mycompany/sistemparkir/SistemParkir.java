/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemparkir;

/**
 *
 * @author yuyu
 */
public class SistemParkir {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        new LoginForm().setVisible(true);
        
        //tes koneksi data base
        if (KoneksiDB.getConnection() != null) {
            System.out.println("Koneksi berhasil!");
        } else {
            System.out.println("Koneksi gagal!");
        }
    }
}
