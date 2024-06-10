/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;

import koneksi.Koneksi;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *
 * @author iannnnn
 */
public class Barang_Keluar extends javax.swing.JPanel {
    private DefaultTableModel model;
    private Koneksi koneksi = new Koneksi();
    public void loadDataBarang(){
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    try{
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();
        
        String sql = "SELECT " +
                 "pe.no_penjualan, " +
                 "b.nama_barang, " +
                 "b.ukuran, " +
                 "b.warna, " +
                 "dpe.jumlah, " +
                 "pe.tgl_jual, " +
                 "dpe.total_harga " +
                 "FROM penjualan pe " +
                 "JOIN detail_penjualan dpe ON pe.no_penjualan = dpe.no_penjualan " +
                 "JOIN barang b ON dpe.kode_barang = b.kode_barang";
        ResultSet r = s.executeQuery(sql);
        
        while (r.next()) {
                Object[] obj = new Object[7];  // Adjusted to 7 as per your query result set
                obj[0] = r.getString("no_penjualan");
                obj[1] = r.getString("nama_barang");
                obj[2] = r.getString("ukuran");
                obj[3] = r.getString("warna");
                obj[4] = r.getInt("jumlah");
                obj[5] = r.getDate("tgl_jual");
                obj[6] = r.getDouble("total_harga");

                model.addRow(obj);
            }
            r.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    public void loadDataBarangDenganRentangTanggal(Date startDate, Date endDate) {
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    System.out.println("StartDate: " + startDate);
    System.out.println("EndDate: " + endDate);

    try {
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();

        String sql = "SELECT " +
                     "pe.no_penjualan, " +
                     "b.nama_barang, " +
                     "b.ukuran, " +
                     "b.warna, " +
                     "dpe.jumlah, " +
                     "pe.tgl_jual, " +
                     "dpe.total_harga " +
                     "FROM penjualan pe " +
                     "JOIN detail_penjualan dpe ON pe.no_penjualan = dpe.no_penjualan " +
                     "JOIN barang b ON dpe.kode_barang = b.kode_barang " +
                     "WHERE pe.tgl_jual BETWEEN ? AND ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDate(1, (java.sql.Date) startDate);
        ps.setDate(2, (java.sql.Date) endDate);

        ResultSet r = ps.executeQuery();

        int totalJumlah = 0;
        while (r.next()) {
            Object[] obj = new Object[7]; // Adjusted to 7 as per your query result set
            obj[0] = r.getString("no_penjualan");
            obj[1] = r.getString("nama_barang");
            obj[2] = r.getString("ukuran");
            obj[3] = r.getString("warna");
            obj[4] = r.getInt("jumlah");
            obj[5] = r.getDate("tgl_jual");
            obj[6] = r.getDouble("total_harga");

            totalJumlah += r.getInt("jumlah");

            model.addRow(obj);
        }
        jLabel3.setText(String.valueOf(totalJumlah));
        r.close();
        s.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


     
     
 
    /**
     * Creates new form Barang_Keluar
     */
    public Barang_Keluar() {
        initComponents();
        
        model = new DefaultTableModel();
        TableBarangKeluar.setModel(model);
        model.addColumn("No Penjulan");
        model.addColumn("Nama Barang");
        model.addColumn("Ukuran");
        model.addColumn("Warna");
        model.addColumn("Jumlah");
        model.addColumn("Tanggal Penjualan");
        model.addColumn("Subtotal");
        // Mengatur format tanggal pada jDateChooser
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        jDateChooser2.setDateFormatString("yyyy-MM-dd");
        loadDataBarang();
        TableBarangKeluar.getTableHeader().setBackground(new Color(0,40,85));
        TableBarangKeluar.getTableHeader().setForeground(Color.WHITE);
        jDateChooser2.addPropertyChangeListener("date", evt -> {
            if (jDateChooser2.getDate() != null) {
                jLabel4.setText("sampai");
            } else {
                jLabel4.setText("Tanggal belum dipilih");
            }
        });
    }
    
         
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDayChooser1 = new com.toedter.calendar.JDayChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBarangKeluar = new javax.swing.JTable();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Data Barang Keluar");

        TableBarangKeluar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No Penjualan", "Nama Barang", "Ukuran", "Warna", "Jumlah", "Tanggal", "SubTotal Harga"
            }
        ));
        jScrollPane1.setViewportView(TableBarangKeluar);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Jumlah Barang Keluar :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(35, 20));

        jButton1.setText("CEK");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Date startDate = new java.sql.Date(jDateChooser2.getDate().getTime());
    Date endDate = new java.sql.Date(jDateChooser1.getDate().getTime());
    loadDataBarangDenganRentangTanggal(startDate, endDate);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBarangKeluar;
    private javax.swing.JButton jButton1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDayChooser jDayChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
