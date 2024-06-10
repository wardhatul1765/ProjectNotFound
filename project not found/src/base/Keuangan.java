/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;
import koneksi.Koneksi;
import Barcode.main;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.table.TableColumnModel;


/**
 *
 * @author KakaPatria
 */
public class Keuangan extends javax.swing.JPanel {
    private Connection conn;
    private DefaultTableModel model;
    private Koneksi koneksi = new Koneksi();
    
    
public void loadDataBarang(Date fromDate, Date toDate) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            Connection c = koneksi.getKoneksi();

            // Query untuk total harga pembelian dengan filter tanggal
            String sqlPembelian = "SELECT DATE(tgl_pembelian) AS tgl_pembelian, SUM(total_harga) as total_harga " +
                    "FROM pembelian WHERE DATE(tgl_pembelian) BETWEEN ? AND ? GROUP BY DATE(tgl_pembelian);";
            PreparedStatement pstPembelian = c.prepareStatement(sqlPembelian);
            pstPembelian.setDate(1, new java.sql.Date(fromDate.getTime()));
            pstPembelian.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rPembelian = pstPembelian.executeQuery();

            // Simpan data pembelian dalam Map
            Map<String, Double> pembelianMap = new HashMap<>();
            while (rPembelian.next()) {
                String tglPembelian = rPembelian.getString("tgl_pembelian");
                double totalHargaPembelian = rPembelian.getDouble("total_harga");
                pembelianMap.put(tglPembelian, totalHargaPembelian);
            }
            rPembelian.close();
            pstPembelian.close();

            // Query untuk total harga penjualan dengan filter tanggal
            String sqlPenjualan = "SELECT DATE(tgl_jual) AS tgl_jual, SUM(total_harga) as total_harga " +
                    "FROM penjualan WHERE DATE(tgl_jual) BETWEEN ? AND ? GROUP BY DATE(tgl_jual);";
            PreparedStatement pstPenjualan = c.prepareStatement(sqlPenjualan);
            pstPenjualan.setDate(1, new java.sql.Date(fromDate.getTime()));
            pstPenjualan.setDate(2, new java.sql.Date(toDate.getTime()));
            ResultSet rPenjualan = pstPenjualan.executeQuery();

            // Simpan data penjualan dalam Map
            Map<String, Double> penjualanMap = new HashMap<>();
            while (rPenjualan.next()) {
                String tglPenjualan = rPenjualan.getString("tgl_jual");
                double totalHargaPenjualan = rPenjualan.getDouble("total_harga");
                penjualanMap.put(tglPenjualan, totalHargaPenjualan);
            }
            rPenjualan.close();
            pstPenjualan.close();

            // Gabungkan kedua map berdasarkan tanggal
            Set<String> semuaTanggal = new HashSet<>();
            semuaTanggal.addAll(pembelianMap.keySet());
            semuaTanggal.addAll(penjualanMap.keySet());

            for (String tanggal : semuaTanggal) {
                double totalPembelian = pembelianMap.getOrDefault(tanggal, 0.0);
                double totalPenjualan = penjualanMap.getOrDefault(tanggal, 0.0);
                double keuntungan = totalPenjualan - totalPembelian;

                // Tambahkan data ke tabel
                Object[] obj = new Object[4];
                obj[0] = tanggal; // Tambahkan tanggal ke kolom pertama
                obj[1] = totalPembelian;
                obj[2] = totalPenjualan;
                obj[3] = keuntungan;
                model.addRow(obj);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

public class NumberRenderer extends DefaultTableCellRenderer {
    private static final NumberFormat formatter = new DecimalFormat("#,###");

    public NumberRenderer() {
        super();
        setHorizontalAlignment(RIGHT);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Number) {
            value = formatter.format((Number) value);
        }
        super.setValue(value);
    }
}

    /**
     * Creates new form Keuangan
     */
    public Keuangan() {
        initComponents();
        conn = koneksi.getKoneksi();
        model = new DefaultTableModel();
        TableKeuangan.setModel(model);
        // Define the columns in the table model
         model.addColumn("Tanggal");
        model.addColumn("Pengeluaran");
        model.addColumn("Pemasukan");
        model.addColumn("Keuntungan");
            TableKeuangan.getTableHeader().setBackground(new Color(0,40,85));
        TableKeuangan.getTableHeader().setForeground(Color.WHITE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Dateto.setDateFormatString("yyyy-MM-dd");
        DateFrom.setDateFormatString("yyyy-MM-dd");
        DateFrom.addPropertyChangeListener("date", evt -> {
            if (DateFrom.getDate() != null) {
                jLabel2.setText("sampai");
            } else {
                jLabel2.setText("Tanggal belum dipilih");
            }
        });
        
        // Set the custom renderer for the specific numeric columns
        TableColumnModel columnModel = TableKeuangan.getColumnModel();
        NumberRenderer renderer = new NumberRenderer();
        columnModel.getColumn(1).setCellRenderer(renderer); // Pengeluaran column
        columnModel.getColumn(2).setCellRenderer(renderer); // Pemasukan column
        columnModel.getColumn(3).setCellRenderer(renderer); // Keuntungan column
    }

    // Ensure this method exists and is correctly implemented to initialize components
    private void initComponent() {
        JScrollPane scrollPane = new JScrollPane(TableKeuangan);
        this.add(scrollPane);
        
        
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
        TableKeuangan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        Dateto = new com.toedter.calendar.JDateChooser();
        DateFrom = new com.toedter.calendar.JDateChooser();
        cekuang = new javax.swing.JButton();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Keuangan");

        TableKeuangan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Cost", "Income", "Profit"
            }
        ));
        jScrollPane1.setViewportView(TableKeuangan);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setPreferredSize(new java.awt.Dimension(44, 20));

        cekuang.setText("Cek");
        cekuang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cekuangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(DateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(Dateto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cekuang, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(321, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DateFrom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Dateto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cekuang)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void cekuangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cekuangActionPerformed
       Date FromDate = new java.sql.Date(DateFrom.getDate().getTime());
    Date toDate = new java.sql.Date(Dateto.getDate().getTime());
    loadDataBarang(FromDate, toDate);
    }//GEN-LAST:event_cekuangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateFrom;
    private com.toedter.calendar.JDateChooser Dateto;
    private javax.swing.JTable TableKeuangan;
    private javax.swing.JButton cekuang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
