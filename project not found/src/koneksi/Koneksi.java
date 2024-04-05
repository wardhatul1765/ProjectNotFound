/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author KakaPatria
 */
public class Koneksi {
    public static Connection koneksi;

public static Connection getKoneksi(){
    if (koneksi == null){
     try {
            String url = "jdbc:mysql://localhost:3306/not_found";
            String user = "root";
            String password = "kaka1234";
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            koneksi = DriverManager.getConnection(url, user, password);
            System.out.println("Berhasil");
        } catch (SQLException e) {
            System.out.println(e);
        }   
    }
 return koneksi;
}
public static void main (String[] args){
    getKoneksi();
}
}
    
    

