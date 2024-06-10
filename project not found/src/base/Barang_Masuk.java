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
public class Barang_Masuk extends javax.swing.JPanel {
    private DefaultTableModel model;
    private Koneksi koneksi = new Koneksi();
    public void loadDataBarang(){
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    try{
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();
        
        String sql = "SELECT " +
                 "p.no_pembelian, " +
                 "b.nama_barang, " +
                 "b.ukuran, " +
                 "b.warna, " +
                 "dp.jumlah, " +
                 "p.tgl_pembelian, " +
                 "dp.total_harga " +
                 "FROM pembelian p " +
                 "JOIN detail_pembelian dp ON p.no_pembelian = dp.no_pembelian " +
                 "JOIN barang b ON dp.kode_barang = b.kode_barang";
        ResultSet r = s.executeQuery(sql);
        
        while (r.next()) {
                Object[] obj = new Object[7];  // Adjusted to 7 as per your query result set
                obj[0] = r.getString("no_pembelian");
                obj[1] = r.getString("nama_barang");
                obj[2] = r.getString("ukuran");
                obj[3] = r.getString("warna");
                obj[4] = r.getInt("jumlah");
                obj[5] = r.getDate("tgl_pembelian");
                obj[6] = r.getDouble("total_harga");

                model.addRow(obj);
            }
            r.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadDataBarangDenganRentangTanggal2(Date startDate, Date endDate) {
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    System.out.println("StartDate: " + startDate);
    System.out.println("EndDate: " + endDate);

    try {
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();

        String sql = "SELECT " +
                     "pb.no_pembelian, " +
                     "b.nama_barang, " +
                     "b.ukuran, " +
                     "b.warna, " +
                     "dpb.jumlah, " +
                     "pb.tgl_pembelian, " +
                     "dpb.total_harga " +
                     "FROM pembelian pb " +
                     "JOIN detail_pembelian dpb ON pb.no_pembelian = dpb.no_pembelian " +
                     "JOIN barang b ON dpb.kode_barang = b.kode_barang " +
                     "WHERE pb.tgl_pembelian BETWEEN ? AND ?";
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setDate(1, (java.sql.Date) startDate);
        ps.setDate(2, (java.sql.Date) endDate);

        ResultSet r = ps.executeQuery();

        int totalJumlah = 0;
        while (r.next()) {
            Object[] obj = new Object[7]; // Adjusted to 7 as per your query result set
            obj[0] = r.getString("no_pembelian");
            obj[1] = r.getString("nama_barang");
            obj[2] = r.getString("ukuran");
            obj[3] = r.getString("warna");
            obj[4] = r.getInt("jumlah");
            obj[5] = r.getDate("tgl_pembelian");
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
     * Creates new form Barang_Masuk
     */
     public Barang_Masuk() {
        initComponents();
        model = new DefaultTableModel();
        TableBrgMasuk.setModel(model);
        model.addColumn("No Pembelian");
        model.addColumn("Nama Barang");
        model.addColumn("Ukuran");
        model.addColumn("Warna");
        model.addColumn("Jumlah");
        model.addColumn("Tanggal Pembelian");
        model.addColumn("Subtotal");
        loadDataBarang();
        TableBrgMasuk.getTableHeader().setBackground(new Color(0,40,85));
        TableBrgMasuk.getTableHeader().setForeground(Color.WHITE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        DateAfter.setDateFormatString("yyyy-MM-dd");
        DateBfr.setDateFormatString("yyyy-MM-dd");
        DateBfr.addPropertyChangeListener("date", evt -> {
            if (DateBfr.getDate() != null) {
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableBrgMasuk = new javax.swing.JTable();
        DateAfter = new com.toedter.calendar.JDateChooser();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        DateBfr = new com.toedter.calendar.JDateChooser();
        jLabel4 = new javax.swing.JLabel();
        Cekmsk = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(625, 503));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Data Barang Masuk");

        TableBrgMasuk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "No Pembelian", "Nama Barang", "Ukuran", "Warna", "Jumlah", "Tanggal", "SubTotal Harga"
            }
        ));
        jScrollPane1.setViewportView(TableBrgMasuk);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Jumlah Barang Masuk :");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setPreferredSize(new java.awt.Dimension(35, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Cekmsk.setText("Cek");
        Cekmsk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CekmskActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(281, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(DateBfr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DateAfter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Cekmsk, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Cekmsk)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DateBfr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(DateAfter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void CekmskActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CekmskActionPerformed
        Date startDate = new java.sql.Date(DateBfr.getDate().getTime());
    Date endDate = new java.sql.Date(DateAfter.getDate().getTime());
    loadDataBarangDenganRentangTanggal2(startDate, endDate);
    }//GEN-LAST:event_CekmskActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cekmsk;
    private com.toedter.calendar.JDateChooser DateAfter;
    private com.toedter.calendar.JDateChooser DateBfr;
    private javax.swing.JTable TableBrgMasuk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
