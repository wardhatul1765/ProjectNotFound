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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.PreparedStatement;

/**
 *
 * @author MyBook Z Series
 */

public class Data_Barang extends javax.swing.JPanel {
   private Connection conn;
    private DefaultTableModel model;
    private Koneksi koneksi = new Koneksi();
    private TableRowSorter<DefaultTableModel> rowSorter;
    public void loadDataBarang(){
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    try{
        Connection c = koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();
        
        String sql = "SELECT * FROM barang";
        ResultSet r = s.executeQuery(sql);
        
        while(r.next()){
            Object[] obj = new Object[9];
            obj [0] = r.getString("kode_barang");
            obj [1] = r.getString("nama_barang");
            obj [2] = r.getString("jenis_barang");
            obj [3] = r.getString("ukuran");
            obj [4] = r.getString("warna");
            obj [5] = r.getString("harga_beli");
            obj [6] = r.getString("harga_jual");
            obj [7] = r.getString("jumlah");
            obj [8] = r.getString("no_barcode");
            
            model.addRow(obj);
        }
        r.close();
        s.close();
    }catch(Exception e){
        e.printStackTrace();
    }
}

    /**
     * Creates new form Data_Barang
     */
    
      private void cetakBarcode() {
        int row = TableBarang.getSelectedRow();
        if (row < 0) {
        // Tidak ada baris yang dipilih
        JOptionPane.showMessageDialog(null, "Pilih baris terlebih dahulu");
        return;
    }
        DecimalFormat df = new DecimalFormat("#,##0.##");
        String kodeBarang = TableBarang.getValueAt(TableBarang.getSelectedRow(),0).toString();
        String namaBarang = TableBarang.getValueAt(TableBarang.getSelectedRow(), 1).toString();
        String Ukuran = TableBarang.getValueAt(TableBarang.getSelectedRow(), 3).toString();
        String Warna = TableBarang.getValueAt(TableBarang.getSelectedRow(), 4).toString();
        Double Harga_Jual = Double.parseDouble(TableBarang.getValueAt(TableBarang.getSelectedRow(), 6).toString());
        String Nobarcode = TableBarang.getValueAt(TableBarang.getSelectedRow(), 8).toString();
        try {
           String path = "D:\\Git\\Project\\ProjectNotFound\\project not found\\src\\report\\Barang.jrxml";
           JasperDesign design = JRXmlLoader.load(path);
           JRDesignQuery updateQuery = new JRDesignQuery();
           String sql = "SELECT nama_barang, ukuran, warna, harga_jual FROM barang WHERE kode_barang = '"+kodeBarang+"' ";
           
           Map paramater = new HashMap();
           paramater.put("no_barcode", Nobarcode);
           updateQuery.setText(sql);
           
           design.setQuery(updateQuery);
           JasperReport report = JasperCompileManager.compileReport(design);
           JasperPrint print = JasperFillManager.fillReport(report, paramater, conn);
           new JasperViewer(print, false).setVisible(true);
       } catch(JRException ex) {
            ex.printStackTrace();
        }
        
      }
      
      private void editTable() {
        int[] selectedRows = TableBarang.getSelectedRows();
        int selectedColumn = TableBarang.getSelectedColumn();

        if (selectedRows.length == 1 && selectedColumn != -1) {
            int selectedRow = selectedRows[0];

            String columnName = TableBarang.getColumnName(selectedColumn);
            if (columnName.equals("Harga Beli") || columnName.equals("Harga Jual")) {
                Object oldValue = TableBarang.getValueAt(selectedRow, selectedColumn);
                Object newValue = JOptionPane.showInputDialog(null, "Edit value:", oldValue);
                if (newValue != null) {
                    // Update JTable
                    model.setValueAt(newValue, selectedRow, selectedColumn);

                    // Retrieve the primary key value or unique identifier of the selected row
                    Object idValue = TableBarang.getValueAt(selectedRow, TableBarang.getColumn("kode_barang").getModelIndex());

                    // Update the database
                    updateDatabase(idValue, columnName, newValue);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Only 'Harga Beli' and 'Harga Jual' can be edited.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select one row and one column to edit.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateDatabase(Object idValue, String columnName, Object newValue) {
        String sql = "UPDATE yourTableName SET " + columnName + " = ? WHERE kode_barang = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setObject(1, newValue);
            pstmt.setObject(2, idValue);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                JOptionPane.showMessageDialog(null, "Failed to update the database.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while updating the database.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

        
    public Data_Barang() {
        initComponents();
        conn = Koneksi.getKoneksi();
        model = new DefaultTableModel();
        rowSorter = new TableRowSorter<>(model);
        TableBarang.setModel(model);
        TableBarang.setRowSorter(rowSorter);
        //InputBarang
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Jenis Barang");
        model.addColumn("Ukuran");
        model.addColumn("Warna");
        model.addColumn("Harga Beli");
        model.addColumn("Harga Jual");
        model.addColumn("Jumlah");
        model.addColumn("No Barcode");
        loadDataBarang();
        cariDataBrg();
        TableBarang.getTableHeader().setBackground(new Color(0,40,85));
        TableBarang.getTableHeader().setForeground(Color.WHITE);
    }
    
    private void cariDataBrg() {
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
        TableBarang = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        btn_Barcode = new javax.swing.JButton();
        btn_Edit2 = new javax.swing.JButton();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Data Barang");

        TableBarang.setBackground(new java.awt.Color(204, 204, 204));
        TableBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Kode Barang", "Nama Barang", "Jenis Barang", "Ukuran", "Warna", "Harga Beli", "Harga Jual", "Jumlah", "No Barcode"
            }
        ));
        TableBarang.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                TableBarangAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        TableBarang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableBarangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TableBarang);

        btn_Barcode.setBackground(new java.awt.Color(7, 29, 54));
        btn_Barcode.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Barcode.setForeground(new java.awt.Color(255, 255, 255));
        btn_Barcode.setText("Barcode");
        btn_Barcode.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Barcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BarcodeActionPerformed(evt);
            }
        });

        btn_Edit2.setBackground(new java.awt.Color(7, 29, 54));
        btn_Edit2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btn_Edit2.setForeground(new java.awt.Color(255, 255, 255));
        btn_Edit2.setText("Edit");
        btn_Edit2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(221, 197, 162), 3));
        btn_Edit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Edit2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_Barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_Edit2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_Barcode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Edit2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 701, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 591, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void TableBarangAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_TableBarangAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_TableBarangAncestorAdded

    private void btn_BarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BarcodeActionPerformed
        // TODO add your handling code here:
     cetakBarcode();
    }//GEN-LAST:event_btn_BarcodeActionPerformed

    private void TableBarangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableBarangMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableBarangMouseClicked

    private void btn_Edit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Edit2ActionPerformed
        editTable();
    }//GEN-LAST:event_btn_Edit2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TableBarang;
    private javax.swing.JButton btn_Barcode;
    private javax.swing.JButton btn_Edit2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    
}