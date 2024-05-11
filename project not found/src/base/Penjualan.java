/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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


public class Penjualan extends javax.swing.JPanel {
    
    private Connection conn;
    
    private void Pilih() {
        // Create and configure dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Pilih Barang");
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(null);
        
        // Create sample data for demonstration
        String[] columnNames = {"Kode Barang", "Nama Barang", "Harga", "No Barcode"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add table to dialog
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        try {
            String query = "SELECT kode_barang, nama_barang, harga_jual, no_barcode FROM barang";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst =pst.executeQuery();
            
            // Populate table with data from database
            while (rst.next()) {
                String kodeBarang = rst.getString("kode_barang");
                String namaBarang = rst.getString("nama_barang");
                String hargaBarang = rst.getString("harga_jual");
                String noBarcode = rst.getString("no_barcode");
                
                model.addRow(new Object[]{kodeBarang, namaBarang, hargaBarang, noBarcode});
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
                    String noBarcode = (String) table.getValueAt(selectedRow, 3);
                    String kodeBarang = (String) table.getValueAt(selectedRow, 0);
                    String namaBarang = (String) table.getValueAt(selectedRow, 1);
                    String hargaBarang = (String) table.getValueAt(selectedRow, 2);
                    
                    // Set data to text fields
                    txt_kode_barang.setText(kodeBarang);
                    txt_nama_barang.setText(namaBarang);
                    txt_harga_barang.setText(hargaBarang);
                    tx_barcode.setText(noBarcode);
                    
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
    DefaultTableModel model = (DefaultTableModel) Table_Penjualan.getModel();
    model.addRow(new Object[]{
        txt_kode_barang.getText(),
        txt_nama_barang.getText(),
         txt_harga_barang.getText(),
        txt_jumlah.getText(),
        txtSubtotal.getText()
    });
    tx_grandtotal.setText(String.valueOf(updateGrandTotal()));
}
    
    public void clear() {
         tx_barcode.setText("");
         txt_kode_barang.setText("");
         txt_nama_barang.setText("");
         txt_harga_barang.setText("");
         txt_jumlah.setText("");
         txtSubtotal.setText("");
}
    
    private double updateGrandTotal() {
        int rowCount = Table_Penjualan.getRowCount();
        double total = 0;
        for (int i = 0; i < rowCount; i++) {
            double subtotal = Double.parseDouble((String) Table_Penjualan.getValueAt(i, 4));
            total += subtotal;
        }
        return total;
    }
    
    private String autoNumber() {
        String noPenjualan = null;
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMM");
        String format = sdf.format(dateNow);
        String query = "SELECT RIGHT (no_penjualan, 3) AS No_Penjualan FROM penjualan WHERE no_penjualan LIKE 'PJLN-"+format+"%' ORDER BY no_penjualan DESC LIMIT 1";
        
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            if(rst.next()) {
                int number = Integer.parseInt(rst.getString("No_Penjualan"));
                number++;
                noPenjualan = "PJLN-"+format+"-"+String.format("%03d", number);
            } else {
                noPenjualan = "PJLN-"+format+"-001";
            }
        } catch(SQLException ex) {
            ex.printStackTrace();
        }
        
        return noPenjualan;
    }
    
    private void tambahData() {
//        Tambah Data di Database Table Penjualan
            String noPenjualan = label_no_transaksi.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateNow = new Date();
            String tglPenjualan = sdf.format(dateNow);
            double totalHarga = updateGrandTotal();
            double bayar = Double.parseDouble(tx_Bayar.getText());
            double kembali = Double.parseDouble(tx_Kembalian.getText());
            String jenisBayar = "Cash";
            String idPengguna = "1";
            
            String query = "INSERT INTO penjualan (no_penjualan, tgl_jual, total_harga, bayar, kembali, jenis_bayar, id_pengguna) "
                    + "VALUES (?,?,?,?,?,?,?)";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, noPenjualan);
                pst.setString(2, tglPenjualan);
                pst.setDouble(3, totalHarga);
                pst.setDouble(4, bayar);
                pst.setDouble(5, kembali);
                pst.setString(6, jenisBayar);
                pst.setString(7, idPengguna);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Sukses");
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
            
//            Tambah Data Database di Table Detail Penjuala;
            for(int i = 0; i < Table_Penjualan.getRowCount(); i++) {
                String kodeBrg = (String) Table_Penjualan.getValueAt(i, 0);
                int jumlah = Integer.parseInt(Table_Penjualan.getValueAt(i, 3).toString());
                double subtotal = Double.parseDouble(Table_Penjualan.getValueAt(i, 4).toString());
                try {
                    String queryDetail = "INSERT INTO detail_penjualan (no_penjualan, kode_barang, jumlah, diskon, total_harga) VALUES(?,?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(queryDetail);
                    pst.setString(1, noPenjualan);
                    
                    for(String kode : new String[]{kodeBrg}) {
                        pst.setString(2, kode);
                    }
                    
                    for(int jml : new int[]{jumlah}) {
                        pst.setInt(3, jml);
                    }
                    
                    for(double disc : new double[]{0}) {
                        pst.setDouble(4, disc);
                    }
                    
                    for(double totalHrg : new double[]{subtotal}) {
                        pst.setDouble(5, totalHrg);
                    }
                    
                    pst.executeUpdate();
                } catch(SQLException ex) {
                    ex.printStackTrace();
                }
            }
    }
    /**
     * Creates new form Penjualan
     */
    public Penjualan() {
        initComponents();
        conn = Koneksi.getKoneksi();
        label_no_transaksi.setText(autoNumber());
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
        jPanel2 = new javax.swing.JPanel();
        tx_barcode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_nama_barang = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txt_kode_barang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        tx_grandtotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Penjualan = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        tx_Kembalian = new javax.swing.JTextField();
        tx_Bayar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tx_Potongan2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_TransaksiBaru = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        txt_harga_barang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        label_no_transaksi = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Penjualan");

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        tx_barcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_barcodeActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Scan Barcode Here");

        txt_nama_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama_barangActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kode Barang");

        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_kode_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kode_barangActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Nama Barang");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_kode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(tx_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txt_nama_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tx_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_kode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nama_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(19, 19, 19))
        );

        jPanel4.setBackground(new java.awt.Color(0, 102, 153));

        tx_grandtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_grandtotalActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Grand Total :");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(tx_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tx_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        Table_Penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Harga", "Jumlah", "SubTotal Harga", "Action"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table_Penjualan);

        jPanel10.setBackground(new java.awt.Color(0, 102, 153));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Kembalian");

        tx_Bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_BayarActionPerformed(evt);
            }
        });
        tx_Bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tx_BayarKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tx_BayarKeyTyped(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Bayar");

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Potongan");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel10)
                    .addComponent(jLabel16))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tx_Potongan2)
                    .addComponent(tx_Bayar)
                    .addComponent(tx_Kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_Bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_Potongan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap())
        );

        btn_tambah.setBackground(new java.awt.Color(0, 102, 153));
        btn_tambah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambah Transaksi");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btn_TransaksiBaru.setBackground(new java.awt.Color(0, 102, 153));
        btn_TransaksiBaru.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_TransaksiBaru.setForeground(new java.awt.Color(255, 255, 255));
        btn_TransaksiBaru.setText("Simpan Transaksi");
        btn_TransaksiBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TransaksiBaruActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("No Transaksi :");

        jPanel7.setBackground(new java.awt.Color(0, 102, 153));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Jumlah");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Harga");

        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyTyped(evt);
            }
        });

        txt_harga_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_harga_barangActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Subtotal");

        txtSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(txt_harga_barang)))
                        .addGap(12, 12, 12))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubtotal)
                        .addGap(11, 11, 11))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_harga_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        label_no_transaksi.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 999, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(label_no_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_TransaksiBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(btn_tambah)
                                        .addGap(14, 14, 14)))))
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(btn_TransaksiBaru, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(46, 46, 46)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(label_no_transaksi))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_tambah))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(120, Short.MAX_VALUE))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void tx_barcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_barcodeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_barcodeActionPerformed

    private void tx_grandtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_grandtotalActionPerformed

    }//GEN-LAST:event_tx_grandtotalActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        tambahDataSementara();
        clear();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_nama_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nama_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nama_barangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Pilih();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_harga_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_harga_barangActionPerformed

    }//GEN-LAST:event_txt_harga_barangActionPerformed

    private void txtSubtotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSubtotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSubtotalActionPerformed

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
        String strJumlah = txt_jumlah.getText();
        String strHarga = txt_harga_barang.getText();
        int jumlah = 0;
        double harga = 0;
        double subtotal = 0;
        try {
            if(txt_harga_barang.getText().length() > 0) {
                jumlah = Integer.parseInt(strJumlah);
                harga = Double.parseDouble(strHarga);
                subtotal = jumlah * harga;
            }   
        } catch(NumberFormatException ex) {
            subtotal = 0;
        }
        txtSubtotal.setText(String.valueOf(subtotal));
    }//GEN-LAST:event_txt_jumlahKeyReleased

    private void txt_jumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyTyped
        char type = evt.getKeyChar();
        if(!Character.isDigit(type)) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_jumlahKeyTyped

    private void txt_kode_barangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_kode_barangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_kode_barangActionPerformed

    private void tx_BayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_BayarActionPerformed
                                               
    }//GEN-LAST:event_tx_BayarActionPerformed

    private void tx_BayarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_BayarKeyTyped
         char type = evt.getKeyChar();
        if(!Character.isDigit(type)) {
            evt.consume();
        }
    }//GEN-LAST:event_tx_BayarKeyTyped

    private void tx_BayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_BayarKeyReleased
    String strBayar = tx_Bayar.getText();
        String strGrandtotal = tx_grandtotal.getText();
        double Bayar = 0;
        double GrandTotal = 0;
        double Kembalian = 0;
        try {
            if(tx_Bayar.getText().length() > 0) {
                Bayar = Double.parseDouble(strBayar);
                GrandTotal = Double.parseDouble(strGrandtotal);
                Kembalian = (Bayar - GrandTotal);
            }   
        } catch(NumberFormatException ex) {
            Kembalian = 0;
        }
        tx_Kembalian.setText(String.valueOf(Kembalian));
    }//GEN-LAST:event_tx_BayarKeyReleased

    private void btn_TransaksiBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TransaksiBaruActionPerformed
        tambahData();
    }//GEN-LAST:event_btn_TransaksiBaruActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_Penjualan;
    private javax.swing.JButton btn_TransaksiBaru;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_no_transaksi;
    private javax.swing.JTextField tx_Bayar;
    private javax.swing.JTextField tx_Kembalian;
    private javax.swing.JTextField tx_Potongan2;
    private javax.swing.JTextField tx_barcode;
    private javax.swing.JTextField tx_grandtotal;
    private javax.swing.JTextField txtSubtotal;
    private javax.swing.JTextField txt_harga_barang;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_kode_barang;
    private javax.swing.JTextField txt_nama_barang;
    // End of variables declaration//GEN-END:variables
}
