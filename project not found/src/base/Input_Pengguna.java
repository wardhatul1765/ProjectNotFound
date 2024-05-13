/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;
import koneksi.Koneksi;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author MyBook Z Series
 */
public class Input_Pengguna extends javax.swing.JPanel {
    private DefaultTableModel model;
    private Connection connection;
 private void loadData(){
    DefaultTableModel model = (DefaultTableModel) Table_pengguna.getModel();
    model.addRow(new Object[]{
        tx_Id.getText(),
        tx_Nama.getText(),
         tx_username.getText(),
         tx_password.getText(),
         tx_nomor.getText(),
         tx_alamat.getText(),
         tx_jabatan.getSelectedItem()
    });
}
 
 private void kosong(){
    DefaultTableModel model = (DefaultTableModel) Table_pengguna.getModel();
    
    while (model.getRowCount()>0){
        model.removeRow(0);
    }
} 

    private void clear() {
    tx_Id.setText("");
    tx_Nama.setText("");
    tx_username.setText("");
    tx_password.setText("");
    tx_nomor.setText("");
    tx_alamat.setText("");
    }
    
    private void tambahData(){
        int rowCount = Table_pengguna.getRowCount();
        for(int i = 0; i < rowCount; i++) {
            String id_pengguna = (String) Table_pengguna.getValueAt(i, 0);
            String nama_pengguna = (String) Table_pengguna.getValueAt(i, 1);
            String username = (String) Table_pengguna.getValueAt(i, 2);
            String password = (String) Table_pengguna.getValueAt(i, 3);
            String telp_pengguna = (String) Table_pengguna.getValueAt(i, 4);
            String alamat_pengguna = (String) Table_pengguna.getValueAt(i, 5);
            String jabatan = (String) Table_pengguna.getValueAt(i, 6);
            addData(id_pengguna, nama_pengguna, username, password, telp_pengguna, alamat_pengguna, jabatan);
        }
        JOptionPane.showMessageDialog(null, "Berhasil Menambahkan Pengguna");
    }
    
//    tambah data di database
    private void addData(String id_pengguna, String nama_pengguna, String username, String password, String telp_pengguna, String alamat_pengguna, String jabatan) {
        String query = "INSERT INTO supplier VALUES(?,?,?,?,?,?,?)";
        try {
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, id_pengguna);
        pst.setString(2, nama_pengguna);
        pst.setString(3, username);
        pst.setString(4, password);
        pst.setString(5, telp_pengguna);
        pst.setString(6, alamat_pengguna);
        pst.setString(7, jabatan);
        
        pst.executeUpdate();
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        } 
    }


    /**
     * Creates new form Input_Pengguna
     */
    public Input_Pengguna() {
        initComponents();
        connection = Koneksi.getKoneksi();
        model = (DefaultTableModel) Table_pengguna.getModel();
    }
    
    private void tambahDataSementara() {
        String id_pengguna = tx_Id.getText();
        String nama_pengguna = tx_Nama.getText();
        String username = tx_username.getText();
        String password = tx_password.getText();
        String telp_pengguna = tx_nomor.getText();
        String alamat_pengguna = tx_alamat.getText();
        String jabatan = (String) tx_jabatan.getSelectedItem();
        model.addRow(new String[]{id_pengguna, nama_pengguna, username, password, telp_pengguna, alamat_pengguna, jabatan});
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
        tx_nama = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tx_nomor = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tx_Nama = new javax.swing.JTextField();
        tx_password = new javax.swing.JTextField();
        tx_alamat = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        tx_Id = new javax.swing.JTextField();
        tx_username = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        tx_jabatan = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_pengguna = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        btn_tmbhpengguna = new javax.swing.JButton();
        btn_Edit2 = new javax.swing.JButton();
        btn_batal2 = new javax.swing.JButton();

        main_panel.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tx_nama.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        tx_nama.setText("Nama Pengguna");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Jabatan");

        tx_nomor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_nomorActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Username");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("No Telpon");

        tx_Nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_NamaActionPerformed(evt);
            }
        });

        tx_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_alamatActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Alamat");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("ID Pengguna");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Password");

        tx_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Owner", "Admin", "Kasir" }));

        jScrollPane3.setBorder(null);
        jScrollPane3.setForeground(new java.awt.Color(255, 255, 255));

        Table_pengguna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Table_pengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Pengguna", "Nama Pengguna", "Username", "Password", "No Telp", "Alamat", "Jabatan"
            }
        ));
        Table_pengguna.setFocusable(false);
        Table_pengguna.setGridColor(new java.awt.Color(255, 255, 255));
        Table_pengguna.setOpaque(false);
        Table_pengguna.setRowHeight(25);
        Table_pengguna.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Table_pengguna.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Table_penggunaAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane3.setViewportView(Table_pengguna);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tx_Id)
                    .addComponent(jLabel9)
                    .addComponent(tx_nama, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE)
                    .addComponent(tx_Nama))
                .addGap(67, 67, 67)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_username, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(tx_password, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(69, 69, 69)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel7)
                        .addComponent(tx_alamat, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                        .addComponent(tx_nomor)))
                .addGap(56, 56, 56)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(127, Short.MAX_VALUE))
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6)
                        .addComponent(jLabel9))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tx_nomor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tx_jabatan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tx_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tx_alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tx_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tx_nama, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tx_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Input Pengguna");

        btn_simpan.setBackground(new java.awt.Color(153, 153, 153));
        btn_simpan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_simpan.setText("Simpan");
        btn_simpan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_simpan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_simpanMouseClicked(evt);
            }
        });
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });

        btn_tmbhpengguna.setBackground(new java.awt.Color(153, 153, 153));
        btn_tmbhpengguna.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_tmbhpengguna.setText("Tambah");
        btn_tmbhpengguna.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_tmbhpengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_tmbhpenggunaMouseClicked(evt);
            }
        });
        btn_tmbhpengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tmbhpenggunaActionPerformed(evt);
            }
        });

        btn_Edit2.setBackground(new java.awt.Color(153, 153, 153));
        btn_Edit2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_Edit2.setText("Edit");
        btn_Edit2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_Edit2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_Edit2MouseClicked(evt);
            }
        });
        btn_Edit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Edit2ActionPerformed(evt);
            }
        });

        btn_batal2.setBackground(new java.awt.Color(153, 153, 153));
        btn_batal2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_batal2.setText("Batal");
        btn_batal2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btn_batal2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_batal2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout main_panelLayout = new javax.swing.GroupLayout(main_panel);
        main_panel.setLayout(main_panelLayout);
        main_panelLayout.setHorizontalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(main_panelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, main_panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_tmbhpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_Edit2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_batal2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)))
                .addContainerGap())
        );
        main_panelLayout.setVerticalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_simpan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_tmbhpengguna, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Edit2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_batal2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 858, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(main_panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 607, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(main_panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tx_nomorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_nomorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_nomorActionPerformed

    private void tx_NamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_NamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_NamaActionPerformed

    private void tx_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_alamatActionPerformed

    private void Table_penggunaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Table_penggunaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_Table_penggunaAncestorAdded

    private void btn_simpanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_simpanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_simpanMouseClicked

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
         tambahData();
        model.setRowCount(0);
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_tmbhpenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_tmbhpenggunaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_tmbhpenggunaMouseClicked

    private void btn_tmbhpenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tmbhpenggunaActionPerformed
        tambahDataSementara();
        clear();
    }//GEN-LAST:event_btn_tmbhpenggunaActionPerformed

    private void btn_Edit2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_Edit2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Edit2MouseClicked

    private void btn_Edit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Edit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Edit2ActionPerformed

    private void btn_batal2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_batal2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_batal2MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_pengguna;
    private javax.swing.JButton btn_Edit2;
    private javax.swing.JButton btn_batal2;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tmbhpengguna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel main_panel;
    public static javax.swing.JTextField tx_Id;
    public static javax.swing.JTextField tx_Nama;
    public static javax.swing.JTextField tx_alamat;
    private javax.swing.JComboBox<String> tx_jabatan;
    private javax.swing.JLabel tx_nama;
    public static javax.swing.JTextField tx_nomor;
    public static javax.swing.JTextField tx_password;
    public static javax.swing.JTextField tx_username;
    // End of variables declaration//GEN-END:variables
}
