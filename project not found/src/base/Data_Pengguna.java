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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author iannnnn
 */
public class Data_Pengguna extends javax.swing.JPanel {
     private DefaultTableModel model;
    private Koneksi koneksi = new Koneksi();
    private TableRowSorter<DefaultTableModel> rowSorter;
    public void loadDataPengguna(){
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    try{
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();
        
        String sql = "SELECT * FROM pengguna";
        ResultSet r = s.executeQuery(sql);
        
        while(r.next()){
            Object[] obj = new Object[7];
            obj [0] = r.getString("id_pengguna");
            obj [1] = r.getString("nama_pengguna");
            obj [2] = r.getString("username");
            obj [3] = r.getString("password");
            obj [4] = r.getString("telp_pengguna");
            obj [5] = r.getString("alamat_pengguna");
            obj [6] = r.getString("jabatan");
            
            model.addRow(obj);
        }
        r.close();
        s.close();
    }catch(Exception e){
        e.printStackTrace();
    }
}

    /**
     * Creates new form Data_Pengguna
     */
    public Data_Pengguna() {
    initComponents();
        
        model = new DefaultTableModel();
        rowSorter = new TableRowSorter<>(model);
        TablePengguna.setModel(model);
        TablePengguna.setRowSorter(rowSorter);
        //InputBarang
        model.addColumn("ID Pengguna");
        model.addColumn("Nama Pengguna");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("No Telpon");
        model.addColumn("Alamat");
        model.addColumn("Jabatan");
        loadDataPengguna();
        cariDataPengguna();
    }
    
    private void cariDataPengguna() {
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                init();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                 init();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
    }
    
    private void init() {
        String text = jTextField1.getText();
        if(text.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0, 1));
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablePengguna = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Data Pengguna");

        TablePengguna.setBackground(new java.awt.Color(204, 204, 204));
        TablePengguna.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        TablePengguna.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID Pengguna", "Nama Pengguna", "Username", "Passwoard", "No Telepon", "Alamat", "Jabatan"
            }
        ));
        jScrollPane1.setViewportView(TablePengguna);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TablePengguna;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
