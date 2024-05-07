/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;

import koneksi.Koneksi;
import Barcode.main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class Input_Barang extends javax.swing.JPanel {
    Koneksi koneksi= new Koneksi();
    private DefaultTableModel model;
    
  private void autoNumberBarang(String jenisBarang) {   
    try {
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();
        
        // Query untuk mendapatkan kode barang terakhir berdasarkan jenis barang
        String sql = "SELECT * FROM `barang` WHERE jenis_barang = '" + jenisBarang + "' ORDER BY kode_barang DESC";
        ResultSet r = s.executeQuery(sql);
        
        if(r.next()) {
            String kodeBarang = r.getString("kode_barang");
            String kodeJenis = kodeBarang.substring(0, 2);
            String NoFaktur = kodeBarang.substring(2);
            String BR = "" + (Integer.parseInt(NoFaktur) + 1);
            String Nol = "";
        
            if (BR.length() == 1) {
                Nol = "00";
            } else if (BR.length() == 2) {
                Nol = "0";
            } else if (BR.length() == 3) {
                Nol = "";
            } else if (BR.length() == 4) {
                Nol = "";
            }
            tx_KodeBarang.setText(kodeJenis + Nol + BR);
        } else {
            // Jika tidak ada data barang dengan jenis yang dipilih, gunakan kode default
            if (jenisBarang.equals("Kaos")) {
                tx_KodeBarang.setText("KA001");
            } else if (jenisBarang.equals("Kemeja")) {
                tx_KodeBarang.setText("KE001");
            } else if (jenisBarang.equals("Celana")) {
                tx_KodeBarang.setText("CE001");
            } else if (jenisBarang.equals("Tas")) {
                tx_KodeBarang.setText("TA001");
            }
        }
        r.close();
        s.close();
    } catch(Exception e){
        System.out.println("autonumber error");
    }

}
    
    public void tambahDataSementara(){
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    model.addRow(new Object[]{
        tx_KodeBarang.getText(),
        tx_NamaBarang.getText(),
        tx_JenisBarang.getSelectedItem(),
        tx_Ukuran.getSelectedItem(),
         txHarga.getText(),
         txHarga1.getText(),
        tx_Jumlah.getText(),
        tx_Nobarcode.getText()
    });
}
    
private void hapusDataSementara() {
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    int rowCount = model.getRowCount();
    
    // Hapus semua baris satu per satu dari belakang ke depan
    for (int i = rowCount - 1; i >= 0; i--) {
        model.removeRow(i);
    }

}

private void editTable () {
    int[] selectedRows = jTable1.getSelectedRows();
    int selectedColumn = jTable1.getSelectedColumn();

    if (selectedRows.length == 1 && selectedColumn != -1) {
        for (int selectedRow : selectedRows) {
            Object oldValue = jTable1.getValueAt(selectedRow, selectedColumn);
            Object newValue = JOptionPane.showInputDialog(null, "Edit value:", oldValue);
            if (newValue != null) {
                model.setValueAt(newValue, selectedRow, selectedColumn);
            }
        }
    } else {
        JOptionPane.showMessageDialog(null, "Silakan pilih satu baris dan satu kolom untuk diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
}

public void kosong(){
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
    
    while (model.getRowCount()>0){
        model.removeRow(0);
    }
} 

public void clear() {
    tx_KodeBarang.setText("");
    tx_NamaBarang.setText("");
    tx_Jumlah.setText("");
    txHarga.setText("");
    txHarga1.setText("");
    tx_Nobarcode.setText("");
}
    
  


    /**
     * Creates new form Input_Barang
     */
        public Input_Barang() {
            initComponents();
            
            
            
        model = new DefaultTableModel();
        
        jTable1.setModel(model);
        //InputBarang
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jenis Barang");
        model.addColumn("Ukuran");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Jumlah");
        model.addColumn("No Barcode");
        
        
    }
    
   
    private void characterDigit(KeyEvent evt) {
        char character = evt.getKeyChar();
        if(!Character.isDigit(character)) {
            evt.consume();
        }
    }
    
    private void tambahData() {
            int baris = jTable1.getRowCount();
            for(int i = 0; i < baris; i++) {
                String kodeBrg = (String) jTable1.getValueAt(i, 0);
                String namaBarang = (String) jTable1.getValueAt(i, 1);
                String jenisBarang = (String) jTable1.getValueAt(i, 2);
                String ukuran = (String) jTable1.getValueAt(i, 3);
                System.out.println(ukuran);
                double hrgBeli = Double.parseDouble((String)jTable1.getValueAt(i, 4));
                double hrgJual = Double.parseDouble((String) jTable1.getValueAt(i, 5));
                int jumlah = Integer.parseInt((String) jTable1.getValueAt(i, 6));
                String noBarcode = (String) jTable1.getValueAt(i, 7);
                addData(kodeBrg, namaBarang, jenisBarang, ukuran, hrgBeli, hrgJual, jumlah, noBarcode);
            }
    }
    
    private void addData(String kodeBrg, String namaBarang, String jenisBarang, String ukuran, double hrgBeli, double hrgJual, int jumlah, String noBarcode) {
    DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
    Connection c = koneksi.getKoneksi();
         try {
            String sql = "INSERT INTO barang (kode_barang, harga_beli, jumlah, ukuran, harga_jual, jenis_barang, nama_barang, no_barcode) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            java.sql.PreparedStatement p = c.prepareStatement(sql);

            p.setString(1, kodeBrg);
            p.setDouble(2, hrgBeli);
            p.setInt(3, jumlah); // K
            p.setString(4, ukuran); // Kolom 4: Ukuran
            p.setDouble(5, hrgJual); // Kolom 5: Harga Jual
            p.setString(6, jenisBarang); // Kolom 6: Jenis barang
            p.setString(7, namaBarang); // Kolom 7: nama barang
            p.setString(8, noBarcode);
            p.executeUpdate();
            p.close();
            
         } catch(Exception ex) {
             System.err.println(ex.getMessage());
         }
    }
    
    
    
        

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        main_panel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tx_NamaBarang = new javax.swing.JTextField();
        tx_KodeBarang = new javax.swing.JTextField();
        tx_JenisBarang = new javax.swing.JComboBox<>();
        tx_Ukuran = new javax.swing.JComboBox<>();
        txHarga1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tx_Jumlah = new javax.swing.JTextField();
        tx_Nobarcode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_Simpan = new javax.swing.JButton();
        btn_Barcode = new javax.swing.JButton();
        btn_Tambah = new javax.swing.JButton();
        btn_Edit2 = new javax.swing.JButton();
        btn_batal2 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(520, 422));
        setLayout(new java.awt.CardLayout());

        main_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("No Barcode");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Nama Barang");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Jenis Barang");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Jumlah");

        txHarga.setBackground(new java.awt.Color(182, 69, 44));
        txHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txHargaActionPerformed(evt);
            }
        });
        txHarga.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txHargaKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Ukuran");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Harga Beli");

        tx_NamaBarang.setBackground(new java.awt.Color(182, 69, 44));
        tx_NamaBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_NamaBarangActionPerformed(evt);
            }
        });

        tx_KodeBarang.setBackground(new java.awt.Color(182, 69, 44));

        tx_JenisBarang.setBackground(new java.awt.Color(182, 69, 44));
        tx_JenisBarang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kaos", "Kemeja", "Celana", "Tas" }));
        tx_JenisBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_JenisBarangActionPerformed(evt);
            }
        });

        tx_Ukuran.setBackground(new java.awt.Color(182, 69, 44));
        tx_Ukuran.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "S", "M,", "L", "XL", "XXL", "XXXL", "-" }));

        txHarga1.setBackground(new java.awt.Color(182, 69, 44));
        txHarga1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txHarga1ActionPerformed(evt);
            }
        });
        txHarga1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txHarga1KeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Harga Jual");

        tx_Jumlah.setBackground(new java.awt.Color(182, 69, 44));
        tx_Jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_JumlahActionPerformed(evt);
            }
        });
        tx_Jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tx_JumlahKeyTyped(evt);
            }
        });

        tx_Nobarcode.setBackground(new java.awt.Color(182, 69, 44));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Kode Barang");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(38, 38, 38))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tx_NamaBarang, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx_KodeBarang))
                        .addGap(30, 30, 30)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(235, 235, 235)
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(tx_JenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(tx_Ukuran, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(70, 70, 70)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7)
                            .addComponent(txHarga1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                            .addComponent(txHarga))))
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_Jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Nobarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_KodeBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tx_Ukuran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tx_Jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel8)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_NamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_JenisBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txHarga1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Nobarcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Input Barang");

        jScrollPane3.setBorder(null);
        jScrollPane3.setForeground(new java.awt.Color(255, 255, 255));

        jTable1.setBackground(new java.awt.Color(221, 197, 162));
        jTable1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jenis Barang", "Ukuran", "Harga Beli", "Harga Jual", "Jumlah"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.setOpaque(false);
        jTable1.setRowHeight(25);
        jTable1.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jTable1.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jTable1AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane3.setViewportView(jTable1);

        btn_Simpan.setBackground(new java.awt.Color(182, 69, 44));
        btn_Simpan.setText("Simpan");
        btn_Simpan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SimpanActionPerformed(evt);
            }
        });

        btn_Barcode.setBackground(new java.awt.Color(182, 69, 44));
        btn_Barcode.setText("Barcode");
        btn_Barcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Barcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BarcodeActionPerformed(evt);
            }
        });

        btn_Tambah.setBackground(new java.awt.Color(182, 69, 44));
        btn_Tambah.setText("Tambah");
        btn_Tambah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TambahActionPerformed(evt);
            }
        });

        btn_Edit2.setBackground(new java.awt.Color(182, 69, 44));
        btn_Edit2.setText("Edit");
        btn_Edit2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Edit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Edit2ActionPerformed(evt);
            }
        });

        btn_batal2.setBackground(new java.awt.Color(182, 69, 44));
        btn_batal2.setText("Batal");
        btn_batal2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_batal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batal2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout main_panelLayout = new javax.swing.GroupLayout(main_panel);
        main_panel.setLayout(main_panelLayout);
        main_panelLayout.setHorizontalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(main_panelLayout.createSequentialGroup()
                        .addGroup(main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(main_panelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addComponent(jScrollPane3)
                    .addGroup(main_panelLayout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(btn_Barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(314, 314, 314)
                        .addComponent(btn_Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btn_Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                        .addComponent(btn_Edit2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btn_batal2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        main_panelLayout.setVerticalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Edit2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        add(main_panel, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void txHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txHargaActionPerformed

    private void tx_NamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_NamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_NamaBarangActionPerformed

    private void tx_JenisBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_JenisBarangActionPerformed
        // TODO add your handling code here:
        String jenisBarang = (String) tx_JenisBarang.getSelectedItem();
        autoNumberBarang(jenisBarang);
    }//GEN-LAST:event_tx_JenisBarangActionPerformed

    private void txHarga1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txHarga1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txHarga1ActionPerformed

    private void tx_JumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_JumlahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_JumlahActionPerformed

    private void jTable1AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jTable1AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1AncestorAdded

    private void btn_SimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SimpanActionPerformed
        int rowCount = jTable1.getRowCount();
        if(rowCount != 0) {
            tambahData();
            JOptionPane.showMessageDialog(null, "Data Tersimpan");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
        } else {
            JOptionPane.showMessageDialog(null, "Silahkan Inputkan Barang");
        }
                
    }//GEN-LAST:event_btn_SimpanActionPerformed

    private void btn_BarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BarcodeActionPerformed
        // TODO add your handling code here:
        String NoBarcode = jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString();
        main barcodeFrame = new main(NoBarcode);
        barcodeFrame.setVisible(true);
    }//GEN-LAST:event_btn_BarcodeActionPerformed

    private void btn_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TambahActionPerformed
        tambahDataSementara();
        clear();
    }//GEN-LAST:event_btn_TambahActionPerformed

    private void btn_Edit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Edit2ActionPerformed
        editTable();
    }//GEN-LAST:event_btn_Edit2ActionPerformed

    private void btn_batal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batal2ActionPerformed
        hapusDataSementara();
    }//GEN-LAST:event_btn_batal2ActionPerformed

    private void txHargaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txHargaKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_txHargaKeyTyped

    private void txHarga1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txHarga1KeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_txHarga1KeyTyped

    private void tx_JumlahKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_JumlahKeyTyped
        characterDigit(evt);
    }//GEN-LAST:event_tx_JumlahKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Barcode;
    private javax.swing.JButton btn_Edit2;
    private javax.swing.JButton btn_Simpan;
    private javax.swing.JButton btn_Tambah;
    private javax.swing.JButton btn_batal2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel main_panel;
    public static javax.swing.JTextField txHarga;
    public static javax.swing.JTextField txHarga1;
    private javax.swing.JComboBox<String> tx_JenisBarang;
    public static javax.swing.JTextField tx_Jumlah;
    public static javax.swing.JTextField tx_KodeBarang;
    public static javax.swing.JTextField tx_NamaBarang;
    public static javax.swing.JTextField tx_Nobarcode;
    private javax.swing.JComboBox<String> tx_Ukuran;
    // End of variables declaration//GEN-END:variables
}
