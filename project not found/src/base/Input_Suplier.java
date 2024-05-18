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
 * @author user
 */
public class Input_Suplier extends javax.swing.JPanel {
    
    private DefaultTableModel model;
    private Connection connection;
 private void loadData(){
    DefaultTableModel model = (DefaultTableModel) Table_suplier.getModel();
    model.addRow(new Object[]{
        tx_Id.getText(),
        tx_Nama.getText(),
         tx_Alamat.getText(),
          tx_Telp.getText()
    });
}
 
 private void kosong(){
    DefaultTableModel model = (DefaultTableModel) Table_suplier.getModel();
    
    while (model.getRowCount()>0){
        model.removeRow(0);
    }
} 

    private void clear() {
    tx_Id.setText("");
    tx_Nama.setText("");
    tx_Telp.setText("");
    tx_Alamat.setText("");
    }
    
    private void tambahData(){
        int rowCount = Table_suplier.getRowCount();
        for(int i = 0; i < rowCount; i++) {
            String id_supplier = (String) Table_suplier.getValueAt(i, 0);
            String nama_supplier = (String) Table_suplier.getValueAt(i, 1);
            String alamat_supplier = (String) Table_suplier.getValueAt(i, 2);
            String telp_supplier = (String) Table_suplier.getValueAt(i, 3);
            addData(id_supplier, nama_supplier, alamat_supplier, telp_supplier);
        }
        JOptionPane.showMessageDialog(null, "Berhasil Menambahkan Supplier");
    }
    
//    tambah data di database
    private void addData(String id_supplier, String nama_supplier, String alamat_supplier, String telp_supplier) {
        String query = "INSERT INTO supplier VALUES(?,?,?,?)";
        try {
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1, id_supplier);
        pst.setString(2, nama_supplier);
        pst.setString(3, alamat_supplier);
        pst.setString(4, telp_supplier);
        pst.executeUpdate();
        } catch(Exception ex) {
            System.err.println(ex.getMessage());
        } 
    }


    /**
     * Creates new form Input_Suplier
     */
    public Input_Suplier() {
        initComponents();
        connection = Koneksi.getKoneksi();
        model = (DefaultTableModel) Table_suplier.getModel();
    }
    
    private void tambahDataSementara() {
        String idSupplier = tx_Id.getText();
        String nama = tx_Nama.getText();
        String alamat = tx_Alamat.getText();
        String noTlp = tx_Telp.getText();
        model.addRow(new String[]{idSupplier, nama, alamat,noTlp});
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Table_suplier = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btn_Edit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        tx_Nama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        tx_Id = new javax.swing.JTextField();
        tx_Telp = new javax.swing.JTextField();
        tx_Alamat = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        btn_Tambah = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(520, 422));
        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane3.setBorder(null);
        jScrollPane3.setForeground(new java.awt.Color(255, 255, 255));

        Table_suplier.setBackground(new java.awt.Color(204, 204, 204));
        Table_suplier.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Table_suplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Supplier", "Nama Supplier", "Alamat", "No Telepon"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        Table_suplier.setFocusable(false);
        Table_suplier.setGridColor(new java.awt.Color(255, 255, 255));
        Table_suplier.setOpaque(false);
        Table_suplier.setRowHeight(25);
        Table_suplier.setSelectionForeground(new java.awt.Color(255, 255, 255));
        Table_suplier.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                Table_suplierAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane3.setViewportView(Table_suplier);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Input Suplier");

        btn_Edit.setBackground(new java.awt.Color(7, 29, 54));
        btn_Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/EDIT.png"))); // NOI18N
        btn_Edit.setText("Edit");
        btn_Edit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_EditMouseClicked(evt);
            }
        });
        btn_Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(7, 29, 54));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/SAVE.png"))); // NOI18N
        jButton3.setText("Simpan");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tx_Nama.setBackground(new java.awt.Color(204, 204, 204));
        tx_Nama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tx_Nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_NamaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Nama Supplier");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setText("ID");

        tx_Id.setBackground(new java.awt.Color(204, 204, 204));
        tx_Id.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tx_Id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_IdActionPerformed(evt);
            }
        });

        tx_Telp.setBackground(new java.awt.Color(204, 204, 204));
        tx_Telp.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tx_Telp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_TelpActionPerformed(evt);
            }
        });

        tx_Alamat.setBackground(new java.awt.Color(204, 204, 204));
        tx_Alamat.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tx_Alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_AlamatActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("No Telepon");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("Alamat");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_Id, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(204, 204, 204)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(tx_Alamat, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Telp, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(259, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_Telp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Id, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7))
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_Alamat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tx_Nama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19))
        );

        jButton4.setBackground(new java.awt.Color(7, 29, 54));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cancel.png"))); // NOI18N
        jButton4.setText("Batal");
        jButton4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });

        btn_Tambah.setBackground(new java.awt.Color(7, 29, 54));
        btn_Tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/ADD.png"))); // NOI18N
        btn_Tambah.setText("Tambah");
        btn_Tambah.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_TambahMouseClicked(evt);
            }
        });
        btn_Tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TambahActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(0, 4, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_Tambah)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jScrollPane3)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Edit, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void Table_suplierAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_Table_suplierAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_Table_suplierAncestorAdded

    private void btn_EditMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_EditMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_EditMouseClicked

    private void btn_EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_EditActionPerformed

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3MouseClicked

    private void tx_NamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_NamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_NamaActionPerformed

    private void tx_IdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_IdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_IdActionPerformed

    private void tx_TelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_TelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_TelpActionPerformed

    private void tx_AlamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_AlamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tx_AlamatActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tambahData();
        model.setRowCount(0);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_TambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_TambahMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_TambahMouseClicked

    private void btn_TambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TambahActionPerformed
        tambahDataSementara();
        clear();
    }//GEN-LAST:event_btn_TambahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_suplier;
    private javax.swing.JButton btn_Edit;
    private javax.swing.JButton btn_Tambah;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField tx_Alamat;
    private javax.swing.JTextField tx_Id;
    private javax.swing.JTextField tx_Nama;
    private javax.swing.JTextField tx_Telp;
    // End of variables declaration//GEN-END:variables
}
