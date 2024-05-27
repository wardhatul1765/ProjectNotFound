/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.JDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import koneksi.Koneksi;



/**
 *
 * @author MyBook Z Series
 */
public class Pembelian extends javax.swing.JPanel {
    
    private String autoNumber() {
        String noPembelian = null;
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String format = sdf.format(dateNow);
        String query = "SELECT RIGHT (no_pembelian, 3) AS No_Pembelian FROM pembelian WHERE no_pembelian LIKE 'PBLN-"+format+"%' ORDER BY no_pembelian DESC LIMIT 1";
        
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("No_Pembelian"));
                number++;
                noPembelian = "PBLN-"+format+"-"+String.format("%03d", number);
            } else {
                noPembelian = "PBLN-"+format+"-001";
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        
        return noPembelian;
    }
    
    private void Pilih() {
        // Create and configure dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Pilih Supplier");
        dialog.setSize(650, 350);
        dialog.setLocationRelativeTo(null);
        
        // Create sample data for demonstration
        String[] columnNames = {"ID Supplier", "Nama Supplier", "Alamat", "No Telepon"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add table to dialog
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        try {
            String query = "SELECT id_supplier, nama_supplier, alamat_supplier, telp_supplier FROM supplier";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst =pst.executeQuery();
            
            // Populate table with data from database
            while (rst.next()) {
                String idSupplier = rst.getString("id_supplier");
                String namaSupplier = rst.getString("nama_supplier");
                String alamatSupplier = rst.getString("alamat_supplier");
                String noTelp = rst.getString("telp_supplier");
                
                model.addRow(new Object[]{idSupplier, namaSupplier, alamatSupplier, noTelp});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Create button to select data
        JButton btnSelect = new JButton("Select");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get data from selected row
                    String idSupplier = (String) table.getValueAt(selectedRow, 0);
                    String namaSupplier = (String) table.getValueAt(selectedRow, 1);
                    
                    // Set data to text fields
                    tx_idsupp.setText(idSupplier);
                    tx_supplier.setText(namaSupplier);
                    
                    // Close the dialog
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Silakan pilih salah satu barang terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        // Add button to dialog
        dialog.add(btnSelect, BorderLayout.SOUTH);
        
        // Make dialog visible
        dialog.setVisible(true);
    }
    
   private void Pilih2() {
        // Create and configure dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Pilih Barang");
        dialog.setSize(650, 300);
        dialog.setLocationRelativeTo(null);
        
        // Create sample data for demonstration
        String[] columnNames = {"Kode Barang", "Nama Barang", "Ukuran", "Warna", "Harga Beli", "Harga Jual", "No Barcode"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add table to dialog
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        try {
            String query = "SELECT kode_barang, nama_barang, ukuran, warna, harga_beli, harga_jual, no_barcode FROM barang";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst =pst.executeQuery();
            
            // Populate table with data from database
            while (rst.next()) {
                String kodeBarang = rst.getString("kode_barang");
                String namaBarang = rst.getString("nama_barang");
                String ukuranBarang = rst.getString("ukuran");
                String warnaBarang = rst.getNString("warna");
                Double hrgBeli = rst.getDouble("harga_beli");
                Double hrgJual = rst.getDouble("harga_jual");
                String noBarcode = rst.getString("no_barcode");
                
                model.addRow(new Object[]{kodeBarang, namaBarang, ukuranBarang, warnaBarang, hrgBeli, hrgJual, noBarcode});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal mengambil data dari database", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // Create button to select data
        JButton btnSelect = new JButton("Select");
        btnSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get selected row
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get data from selected row
                    String kodeBarang = (String) table.getValueAt(selectedRow, 0);
                    String namaBarang = (String) table.getValueAt(selectedRow, 1);
                    String ukuranBarang = (String) table.getValueAt(selectedRow, 2);
                    String warnaBarang = (String) table.getValueAt(selectedRow, 3);
                    Double hargaBeli = (Double) table.getValueAt(selectedRow, 4);
                    Double hargaJual = (Double) table.getValueAt(selectedRow, 5);
                    
                    // Set data to text fields
                    tx_Kodebarang.setText(kodeBarang);
                    txt_namabarr.setText(namaBarang);
                    txt_ukuran.setText(ukuranBarang);
                    txt_warna.setText(warnaBarang);
                    txt_hargabeli1.setText(String.valueOf(hargaBeli));
                    txt_hargajual.setText(String.valueOf(hargaJual));

                    // Close the dialog
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Silakan pilih salah satu barang terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        
        // Add button to dialog
        dialog.add(btnSelect, BorderLayout.SOUTH);
        
        // Make dialog visible
        dialog.setVisible(true);
    }
    
    public void tambahDataSementara(){
    DefaultTableModel model = (DefaultTableModel) Table_pembelian.getModel();
    model.addRow(new Object[]{
        tx_idsupp.getText(),
        tx_supplier.getText(),
        tx_Kodebarang.getText(),
         txt_namabarr.getText(),
         txt_ukuran.getText(),
         txt_warna.getText(),
         txt_hargabeli1.getText(),
         txt_hargajual.getText(),
        txt_jumlah.getText(),
        txt_subtotal.getText()
    });
    txt_grandtotal.setText(String.valueOf(updateGrandTotal()));
}
    
    public void clear() {
         tx_idsupp.setText("");
         tx_supplier.setText("");
         tx_Kodebarang.setText("");
         txt_namabarr.setText("");
         txt_ukuran.setText("");
         txt_warna.setText("");
         txt_hargabeli1.setText("");
         txt_hargajual.setText("");
         txt_jumlah.setText("");
         txt_subtotal.setText("");
}
    
    private double updateGrandTotal() {
        int rowCount = Table_pembelian.getRowCount();
        double total = 0;
        for (int i = 0; i < rowCount; i++) {
            double subtotal = Double.parseDouble((String) Table_pembelian.getValueAt(i, 9));
            total += subtotal;
        }
        return total;
    }
    
    private void tambahData() {
//        Tambah Data di Database Table Pembelian
            String noPembelian = lb_Pembelian.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNow = new Date();
            String tglPembelian = sdf.format(dateNow);
            double totalHarga = updateGrandTotal();
            
            for(int i = 0; i < Table_pembelian.getRowCount(); i++) {
                String idSplr = (String) Table_pembelian.getValueAt(i, 0);
                  
            try { String query = "INSERT INTO pembelian (id_pengguna, id_supplier, no_pembelian, tgl_pembelian, total_harga) VALUES(?,?,?,?,?)";
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, id);
                
                for(String id : new String[]{idSplr}) {
                    pst.setString(2, id);
                }
                pst.setString(3, noPembelian);
                pst.setString(4, tglPembelian);
                pst.setDouble(5, totalHarga);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Sukses");
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }
            
            
//            Tambah Data Database di Table Detail Pembelian;
            for(int i = 0; i < Table_pembelian.getRowCount(); i++) {
                String kodeBrg = (String) Table_pembelian.getValueAt(i, 2);
                String namaSplr = (String) Table_pembelian.getValueAt(i, 1);
                String namaBrg = (String) Table_pembelian.getValueAt(i, 3);
                double hrgbeli = Double.parseDouble(Table_pembelian.getValueAt(i, 6).toString());
                int jumlah = Integer.parseInt(Table_pembelian.getValueAt(i, 8).toString());
                double subtotal = Double.parseDouble(Table_pembelian.getValueAt(i, 9).toString());
                try {
                    String queryDetail = "INSERT INTO detail_pembelian (no_pembelian, kode_barang, nama_supplier, nama_barang, harga_beli, jumlah, total_harga) VALUES(?,?,?,?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(queryDetail);
                    pst.setString(1, noPembelian);
                    
                    for(String kode : new String[]{kodeBrg}) {
                        pst.setString(2, kode);
                    }
                    
                    for(String Supplier : new String[]{namaSplr}) {
                        pst.setString(3, Supplier);
                    }
                    
                    for(String Barang : new String[]{namaBrg}) {
                        pst.setString(4,Barang);
                    }
                    
                    for (double harga : new double[]{hrgbeli}) {
                        pst.setDouble(5, harga);
                    }
                    
                    
                    for(int jml : new int[]{jumlah}) {
                        pst.setInt(6, jml);
                    }
                    
                    
                    for(double totalHrg : new double[]{subtotal}) {
                        pst.setDouble(7, totalHrg);
                    }
                    
                    pst.executeUpdate();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
    }
    
  private void updateDataFromTable() {
    // Mendapatkan koneksi dari sumber yang sesuai (misalnya, Koneksi.getKoneksi())
    Connection conn = null;
    PreparedStatement pstmt = null;
    try {
        conn = Koneksi.getKoneksi();
        // Mengatur auto-commit ke false untuk mengelola transaksi manual
        conn.setAutoCommit(false);

        // Query SQL untuk update data
        String query = "UPDATE barang SET jumlah = jumlah + ? WHERE kode_barang = ?";
        pstmt = conn.prepareStatement(query);

        // Loop melalui semua baris di JTable
        for (int i = 0; i < Table_pembelian.getRowCount(); i++) {
            String kodeBarang = (String) Table_pembelian.getValueAt(i, 2);
            int jumlahBarang = Integer.parseInt(Table_pembelian.getValueAt(i, 8).toString());

            // Debug log untuk setiap parameter yang diset
            System.out.println("Updating kode_barang: " + kodeBarang + " dengan jumlah: " + jumlahBarang);

            // Set parameter untuk query
            pstmt.setInt(1, jumlahBarang);
            pstmt.setString(2, kodeBarang);

            // Tambahkan batch
            pstmt.addBatch();
        }

        // Eksekusi batch update
        int[] updateCounts = pstmt.executeBatch();
        conn.commit();

        // Cek hasil update
        for (int count : updateCounts) {
            if (count == PreparedStatement.EXECUTE_FAILED) {
                System.out.println("Terjadi kesalahan saat memperbarui data.");
            }
        }

        System.out.println("Data berhasil diperbarui.");
    } catch (SQLException e) {
        // Rollback jika terjadi kesalahan
        try {
            if (conn != null) {
                conn.rollback();
                System.out.println("Rollback perubahan karena terjadi kesalahan.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        e.printStackTrace();
    }
}

    
    
    /**
     * Creates new form Pembelian
     */
    private Connection conn;
    private String id;
    public Pembelian(String id) {
        initComponents();
        this.id = id;
         conn = Koneksi.getKoneksi();
        lb_Pembelian.setText(autoNumber());
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
        Table_pembelian = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        tx_idsupp = new javax.swing.JTextField();
        txt_namabarr = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lb_Pembelian = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txt_grandtotal = new javax.swing.JTextField();
        btn_Simpan = new javax.swing.JButton();
        tx_supplier = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        tx_Kodebarang = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txt_ukuran = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txt_warna = new javax.swing.JTextField();
        txt_hargabeli1 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txt_hargajual = new javax.swing.JTextField();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("ReStok");

        Table_pembelian.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Supplier", "Nama Supplier", "Kode Barang", "Nama Barang", "Ukuran", "Warna", "Harga Beli", "Harga Jual", "Jumlah", "SubTotal Harga"
            }
        ));
        jScrollPane1.setViewportView(Table_pembelian);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tx_idsupp.setBackground(new java.awt.Color(204, 204, 204));

        txt_namabarr.setBackground(new java.awt.Color(204, 204, 204));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("Harga Beli");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Jumlah");

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setText("Sub Total");

        txt_jumlah.setBackground(new java.awt.Color(204, 204, 204));
        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
        });

        btn_tambah.setBackground(new java.awt.Color(7, 29, 54));
        btn_tambah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/ADD.png"))); // NOI18N
        btn_tambah.setText("Tambah Transaksi");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("No Pembelian :");

        lb_Pembelian.setText("jLabel4");

        txt_subtotal.setBackground(new java.awt.Color(204, 204, 204));

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setText("Grand Total");

        txt_grandtotal.setBackground(new java.awt.Color(204, 204, 204));

        btn_Simpan.setBackground(new java.awt.Color(7, 29, 64));
        btn_Simpan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_Simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_Simpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save.png"))); // NOI18N
        btn_Simpan.setText("Simpan Transaksi");
        btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SimpanActionPerformed(evt);
            }
        });

        tx_supplier.setBackground(new java.awt.Color(204, 204, 204));
        tx_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_supplierActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Nama Barang");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Nama Supplier");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("ID Supplier");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Kode Barang");

        tx_Kodebarang.setBackground(new java.awt.Color(204, 204, 204));

        jButton1.setBackground(new java.awt.Color(7, 29, 64));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(7, 29, 64));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Pilih");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        txt_ukuran.setBackground(new java.awt.Color(204, 204, 204));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Ukuran");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Warna");

        txt_warna.setBackground(new java.awt.Color(204, 204, 204));

        txt_hargabeli1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Harga Jual");

        txt_hargajual.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel4)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_namabarr, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tx_Kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tx_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ukuran, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_warna, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_hargabeli1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_hargajual, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(tx_idsupp, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lb_Pembelian)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_tambah))
                        .addGap(58, 58, 58))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(33, 33, 33)
                        .addComponent(txt_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lb_Pembelian))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tx_idsupp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tx_supplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tx_Kodebarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_namabarr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_ukuran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txt_warna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_hargabeli1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_hargajual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_tambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_Simpan)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(12, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        tambahDataSementara();
        clear();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
        String strJumlah = txt_jumlah.getText();
        String strHarga = txt_hargabeli1.getText();
        int jumlah = 0;
        double harga = 0;
        double subtotal = 0;
        try {
            if(txt_hargabeli1.getText().length() > 0) {
                jumlah = Integer.parseInt(strJumlah);
                harga = Double.parseDouble(strHarga);
                subtotal = jumlah * harga;
            }   
        } catch(NumberFormatException ex) {
            subtotal = 0;
        }
        txt_subtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_txt_jumlahKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Pilih2();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Pilih();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SimpanActionPerformed
        tambahData();
        updateDataFromTable();
    }//GEN-LAST:event_btn_SimpanActionPerformed

    private void tx_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_supplierActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_pembelian;
    private javax.swing.JButton btn_Simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_Pembelian;
    private javax.swing.JTextField tx_Kodebarang;
    private javax.swing.JTextField tx_idsupp;
    private javax.swing.JTextField tx_supplier;
    private javax.swing.JTextField txt_grandtotal;
    private javax.swing.JTextField txt_hargabeli1;
    private javax.swing.JTextField txt_hargajual;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_namabarr;
    private javax.swing.JTextField txt_subtotal;
    private javax.swing.JTextField txt_ukuran;
    private javax.swing.JTextField txt_warna;
    // End of variables declaration//GEN-END:variables
}
