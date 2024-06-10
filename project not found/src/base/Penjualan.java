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
import java.util.Calendar;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import koneksi.Koneksi;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;


public class Penjualan extends javax.swing.JPanel {
    
    private Connection conn;
    
    private void Pilih() {
        // Create and configure dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Pilih Barang");
        dialog.setSize(800, 400);
        dialog.setLocationRelativeTo(null);
        
        // Create sample data for demonstration
        String[] columnNames = {"Kode Barang", "Nama Barang", "Ukuran", "Warna", "Harga", "Jumlah", "No Barcode"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add table to dialog
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        try {
            String query = "SELECT kode_barang, nama_barang, ukuran, warna, harga_jual, jumlah, no_barcode FROM barang";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst =pst.executeQuery();
            
            // Populate table with data from database
            while (rst.next()) {
                String kodeBarang = rst.getString("kode_barang");
                String namaBarang = rst.getString("nama_barang");
                String ukuranBarang = rst.getString("ukuran");
                String warnaBarang = rst.getString("warna");
                String hargaBarang = rst.getString("harga_jual");
                String jmlhBarang = rst.getString("jumlah");
                String noBarcode = rst.getString("no_barcode");
                
                model.addRow(new Object[]{kodeBarang, namaBarang, ukuranBarang, warnaBarang, hargaBarang, jmlhBarang, noBarcode});
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
                    String noBarcode = (String) table.getValueAt(selectedRow, 6);
                    String kodeBarang = (String) table.getValueAt(selectedRow, 0);
                    String namaBarang = (String) table.getValueAt(selectedRow, 1);
                    String ukuranBarang = (String) table.getValueAt(selectedRow, 2);
                    String warnaBarang = (String) table.getValueAt(selectedRow, 3);
                    String hargaBarang = (String) table.getValueAt(selectedRow, 4);
                    
                    // Set data to text fields
                    txt_kode_barang.setText(kodeBarang);
                    txt_nama_barang.setText(namaBarang);
                    txt_ukuran.setText(ukuranBarang);
                    txt_warna.setText(warnaBarang);
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
    
    private void Pilih2() {
        // Create and configure dialog
        JDialog dialog = new JDialog();
        dialog.setTitle("Pilih Promo");
        dialog.setSize(800, 300);
        dialog.setLocationRelativeTo(null);
        
        // Create sample data for demonstration
        String[] columnNames = {"Kode Promo", "Nama Promo", "Tanggal Awal", "Tanggal Akhir", "Besar Promo", "Jenis Promo", "Status"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnNames);
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add table to dialog
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        try {
            String query = "SELECT kode_promo, nama_promo, tgl_awal, tgl_akhir, besar_promo, jenis_promo FROM promo";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rst =pst.executeQuery();
            
            // Populate table with data from database
            while (rst.next()) {
                String kodePromo = rst.getString("kode_promo");
                String namaPromo = rst.getString("nama_promo");
                Date dateAwal = rst.getDate("tgl_awal");
                Date dateAkhir = rst.getDate("tgl_akhir");
                String besarPromo = rst.getString("besar_promo");
                String jenisPromo = rst.getString("jenis_promo");
                
 // Cek status promo berdasarkan tanggal awal dan akhir
            String statusPromo = getStatusPromo(dateAwal, dateAkhir);
            model.addRow(new Object[]{kodePromo, namaPromo, dateAwal, dateAkhir, besarPromo, jenisPromo, statusPromo});
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
                     String status = (String) table.getValueAt(selectedRow, 6);

                // Cek apakah promo masih berlaku
                if ("Berakhir".equals(status)) {
                    JOptionPane.showMessageDialog(null, "Masa berlaku promo telah berakhir, silahkan pilih promo yang masih berlaku.", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    String besarPromo = (String) table.getValueAt(selectedRow, 4);
                    tx_Potongan2.setText(besarPromo);
                    updateGrandTotalWithDiscount(besarPromo);
                    dialog.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Silakan pilih salah satu promo terlebih dahulu.", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        }
    });

    dialog.add(btnSelect, BorderLayout.SOUTH);
    dialog.setVisible(true);
}

private String getStatusPromo(Date dateAwal, Date dateAkhir) {
    Date today = new Date();
    if (today.before(dateAwal)) {
        return "Belum Mulai";
    } else if (today.after(dateAkhir)) {
        return "Berakhir";
    } else {
        return "Aktif";
    }
}

    
    private void updateGrandTotalWithDiscount(String besarPromo) {
    double potonganHarga = 0;
    double GrandTotal = 0;
    double totalSetelahPotongan = 0;
    try {
        if (besarPromo.length() > 0) {
            potonganHarga = Double.parseDouble(besarPromo);
            GrandTotal = Double.parseDouble(tx_grandtotal.getText());
            totalSetelahPotongan = GrandTotal - potonganHarga;
        }
    } catch (NumberFormatException ex) {
        potonganHarga = 0;
        totalSetelahPotongan = GrandTotal;
    }

    // Set the updated grand total
    tx_grandtotal.setText(String.valueOf(totalSetelahPotongan));
}
    
    public void tambahDataSementara() {
    try {
        int jumlahBarangDijual = Integer.parseInt(txt_jumlah.getText());
        String kodeBarang = txt_kode_barang.getText(); // Mendapatkan kode barang dari JTextField lain
        String namaBarang = txt_nama_barang.getText();

        System.out.println("Jumlah Barang Dijual: " + jumlahBarangDijual);
        System.out.println("Kode Barang: " + kodeBarang);
        System.out.println("Nama Barang : " + namaBarang);

        // Pengecekan stok barang
        if (!cekStokBarang(kodeBarang, namaBarang, jumlahBarangDijual)) {
            JOptionPane.showMessageDialog(null, "Jumlah barang " + namaBarang + " tidak mencukupi");
            txt_jumlah.setText("");
            return;
        }

        // Jika stok mencukupi, lanjutkan dengan proses tambah ke dalam tabel penjualan
        DefaultTableModel model = (DefaultTableModel) Table_Penjualan.getModel();
        model.addRow(new Object[]{
            txt_kode_barang.getText(),
            txt_nama_barang.getText(),
            txt_ukuran.getText(),
            txt_warna.getText(),
            txt_harga_barang.getText(),
            txt_jumlah.getText(),
            txtSubtotal.getText()
        });

        // Perbarui grand total
        tx_grandtotal.setText(String.valueOf(updateGrandTotal()));

    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Harap masukkan jumlah barang yang valid.");
    }
}

    
    public void clear() {
         tx_barcode.setText("");
         txt_kode_barang.setText("");
         txt_nama_barang.setText("");
         txt_ukuran.setText("");
         txt_warna.setText("");
         txt_harga_barang.setText("");
         txt_jumlah.setText("");
         txtSubtotal.setText("");
}
    
    private double updateGrandTotal() {
        int rowCount = Table_Penjualan.getRowCount();
        double total = 0;
        for (int i = 0; i < rowCount; i++) {
            double subtotal = Double.parseDouble((String) Table_Penjualan.getValueAt(i, 6));
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
            double diskon = 0;
            if(!tx_Potongan2.getText().isEmpty()) {
                diskon = Double.parseDouble(tx_Potongan2.getText());
            }
            double totalHarga = Double.parseDouble(tx_grandtotal.getText());
            double bayar = Double.parseDouble(tx_Bayar.getText());
            double kembali = Double.parseDouble(tx_Kembalian.getText());
            String jenisBayar = cmb_jenisbyr.getSelectedItem().toString();
            
            if (noPenjualan.isEmpty() || tx_grandtotal.getText().isEmpty() || tx_Bayar.getText().isEmpty() || tx_Kembalian.getText().isEmpty() || cmb_jenisbyr.getSelectedItem() == null || tx_Potongan2.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Semua field wajib diisi! Jika memang tidak ada valuenya maka isi dengan angka 0", "Peringatan", JOptionPane.WARNING_MESSAGE);
        return;
    }
            
            String query = "INSERT INTO penjualan (no_penjualan, tgl_jual, diskon, total_harga, bayar, kembali, jenis_bayar, id_pengguna) "
                    + "VALUES (?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement pst = conn.prepareStatement(query);
                pst.setString(1, noPenjualan);
                pst.setString(2, tglPenjualan);
                pst.setDouble(3, diskon);
                pst.setDouble(4, totalHarga);
                pst.setDouble(5, bayar);
                pst.setDouble(6, kembali);
                pst.setString(7, jenisBayar);
                pst.setString(8, id);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Sukses");
            } catch(SQLException ex) {
                ex.printStackTrace();
            }
            
//            Tambah Data Database di Table Detail Penjualan;
            for(int i = 0; i < Table_Penjualan.getRowCount(); i++) {
                String kodeBrg = (String) Table_Penjualan.getValueAt(i, 0);
                int jumlah = Integer.parseInt(Table_Penjualan.getValueAt(i, 5).toString());
                double subtotal = Double.parseDouble(Table_Penjualan.getValueAt(i, 6).toString());
                try {
                    String queryDetail = "INSERT INTO detail_penjualan (no_penjualan, kode_barang, jumlah, total_harga) VALUES(?,?,?,?)";
                    PreparedStatement pst = conn.prepareStatement(queryDetail);
                    pst.setString(1, noPenjualan);
                    
                    for(String kode : new String[]{kodeBrg}) {
                        pst.setString(2, kode);
                    }
                    
                    for(int jml : new int[]{jumlah}) {
                        pst.setInt(3, jml);
                    }
                    
                    for(double totalHrg : new double[]{subtotal}) {
                        pst.setDouble(4, totalHrg);
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
        String query = "UPDATE barang SET jumlah = jumlah - ? WHERE kode_barang = ?";
        pstmt = conn.prepareStatement(query);

        // Loop melalui semua baris di JTable
        for (int i = 0; i < Table_Penjualan.getRowCount(); i++) {
            String kodeBarang = (String) Table_Penjualan.getValueAt(i, 0);
            int jumlahBarang = Integer.parseInt(Table_Penjualan.getValueAt(i, 5).toString());

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

    private static void transaksiPenjualan () {
        Connection conn = Koneksi.getKoneksi();
    }
    
    private void cetakStruk() {
        String noPenjualan = label_no_transaksi.getText();
        Calendar calendar = Calendar.getInstance();
        Date dateNow = calendar.getTime();
        String strDateNow = new SimpleDateFormat("yyyy-MM-dd").format(dateNow);
        DecimalFormat df = new DecimalFormat("#,##0.##");
        Double totalHarga = Double.valueOf(tx_grandtotal.getText());
        Double diskon = Double.valueOf(tx_Potongan2.getText());
        Double bayar = Double.valueOf(tx_Bayar.getText());
        Double kembali = Double.valueOf(tx_Kembalian.getText());
        try {
           String path = "D:\\Git\\Project\\ProjectNotFound\\project not found\\src\\report\\strukpenjualan.jrxml";
           JasperDesign design = JRXmlLoader.load(path);
           JRDesignQuery updateQuery = new JRDesignQuery();
           String sql = "SELECT brg.nama_barang, brg.harga_jual, dtl.jumlah, dtl.total_harga \n" +
                        "FROM detail_penjualan dtl INNER JOIN barang brg \n" +
                        "ON dtl.kode_barang=brg.kode_barang \n" +
                        "WHERE no_penjualan='"+noPenjualan+"' ";
           Map paramater = new HashMap();
           paramater.put("no_penjualan", noPenjualan);
           paramater.put("tgl_jual", strDateNow);
           paramater.put("total", df.format(totalHarga));
           paramater.put("diskon", df.format(diskon));
           paramater.put("bayar", df.format(bayar));
           paramater.put("kembali", df.format(kembali));
           updateQuery.setText(sql);
           design.setQuery(updateQuery);
           JasperReport report = JasperCompileManager.compileReport(design);
           JasperPrint print = JasperFillManager.fillReport(report, paramater, conn);
           new JasperViewer(print, false).setVisible(true);
        } catch(JRException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    private void fetchBarangData(String barcode) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            conn = Koneksi.getKoneksi(); // Pastikan Anda memiliki metode koneksi yang benar
            String sql = "SELECT kode_barang, nama_barang, ukuran, warna, harga_jual FROM barang WHERE no_barcode = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, barcode);
            rs = pst.executeQuery();

            if (rs.next()) {
                txt_kode_barang.setText(rs.getString("kode_barang"));
                txt_nama_barang.setText(rs.getString("nama_barang"));
                txt_ukuran.setText(rs.getString("ukuran"));
                txt_warna.setText(rs.getString("warna"));
                txt_harga_barang.setText(rs.getString("harga_jual"));
            } else {
                JOptionPane.showMessageDialog(this, "Barang tidak ditemukan", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private boolean cekStokBarang(String kodeBarang, String namaBarang, int jumlahBarangDijual) {
    Connection conn = null;
    PreparedStatement pstmtCheck = null;
    ResultSet rs = null;

    try {
        conn = Koneksi.getKoneksi();
        String checkQuery = "SELECT jumlah FROM barang WHERE kode_barang = ? AND nama_barang = ?";
        pstmtCheck = conn.prepareStatement(checkQuery);
        pstmtCheck.setString(1, kodeBarang);
        pstmtCheck.setString(2, namaBarang);
        rs = pstmtCheck.executeQuery();

        if (rs.next()) {
            int jumlahStok = rs.getInt("jumlah");
            System.out.println("Jumlah Stok di Database: " + jumlahStok);

            if (jumlahBarangDijual > jumlahStok) {
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Barang dengan kode " + kodeBarang + "dan nama " + namaBarang + " tidak ditemukan.");
            return false;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Terjadi kesalahan dalam memeriksa stok barang.");
        return false;
    } 
    return true;
}
    
    
    /**
     * Creates new form Penjualan
     */
    private String id;
    public Penjualan(String id) {
        initComponents();
        this.id = id;
        conn = Koneksi.getKoneksi();
        label_no_transaksi.setText(autoNumber());
            Table_Penjualan.getTableHeader().setBackground(new Color(0,40,85));
        Table_Penjualan.getTableHeader().setForeground(Color.WHITE);
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
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txt_kode_barang = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_ukuran = new javax.swing.JTextField();
        txt_nama_barang = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        tx_grandtotal = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btn_TransaksiBaru = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_Penjualan = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        tx_Kembalian = new javax.swing.JTextField();
        tx_Bayar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tx_Potongan2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        cmb_jenisbyr = new javax.swing.JComboBox<>();
        btn_tambah = new javax.swing.JButton();
        btnCetak = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        txt_harga_barang = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSubtotal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txt_warna = new javax.swing.JTextField();
        label_no_transaksi = new javax.swing.JLabel();

        setLayout(new java.awt.CardLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("KASIR");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        tx_barcode.setBackground(new java.awt.Color(204, 204, 204));
        tx_barcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_barcodeActionPerformed(evt);
            }
        });
        tx_barcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tx_barcodeKeyPressed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Scan Barcode Here");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("Kode Barang");

        jButton1.setBackground(new java.awt.Color(7, 29, 64));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Pilih");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        txt_kode_barang.setBackground(new java.awt.Color(204, 204, 204));
        txt_kode_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_kode_barangActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("Nama Barang");

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Ukuran");

        txt_ukuran.setBackground(new java.awt.Color(204, 204, 204));
        txt_ukuran.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ukuranActionPerformed(evt);
            }
        });

        txt_nama_barang.setBackground(new java.awt.Color(204, 204, 204));
        txt_nama_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nama_barangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))))
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txt_nama_barang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_kode_barang, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tx_barcode, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txt_ukuran))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tx_barcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_kode_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_nama_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txt_ukuran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tx_grandtotal.setBackground(new java.awt.Color(204, 204, 204));
        tx_grandtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_grandtotalActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setText("Grand Total :");

        btn_TransaksiBaru.setBackground(new java.awt.Color(7, 29, 64));
        btn_TransaksiBaru.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_TransaksiBaru.setForeground(new java.awt.Color(255, 255, 255));
        btn_TransaksiBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save.png"))); // NOI18N
        btn_TransaksiBaru.setText("Simpan Transaksi");
        btn_TransaksiBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TransaksiBaruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tx_grandtotal, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_TransaksiBaru, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tx_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_TransaksiBaru)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        Table_Penjualan.setBackground(new java.awt.Color(204, 204, 204));
        Table_Penjualan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Barang", "Nama Barang", "Ukuran", "Warna", "Harga", "Jumlah", "SubTotal Harga"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(Table_Penjualan);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Kembalian");

        tx_Kembalian.setBackground(new java.awt.Color(204, 204, 204));

        tx_Bayar.setBackground(new java.awt.Color(204, 204, 204));
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
        jLabel14.setText("Bayar");

        tx_Potongan2.setBackground(new java.awt.Color(204, 204, 204));
        tx_Potongan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tx_Potongan2ActionPerformed(evt);
            }
        });
        tx_Potongan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tx_Potongan2KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                tx_Potongan2KeyTyped(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel16.setText("Potongan");

        jButton2.setBackground(new java.awt.Color(7, 29, 64));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Pilih");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cmb_jenisbyr.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        cmb_jenisbyr.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tunai", "Non Tunai" }));

        btn_tambah.setBackground(new java.awt.Color(7, 29, 64));
        btn_tambah.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/add.png"))); // NOI18N
        btn_tambah.setText("Tambah Transaksi");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });

        btnCetak.setBackground(new java.awt.Color(7, 29, 64));
        btnCetak.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCetak.setForeground(new java.awt.Color(255, 255, 255));
        btnCetak.setText("Cetak");
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(tx_Kembalian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel10Layout.createSequentialGroup()
                                    .addComponent(tx_Bayar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmb_jenisbyr, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(tx_Potongan2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(21, 21, 21))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(btn_tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCetak)
                        .addGap(10, 10, 10))))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tx_Bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(cmb_jenisbyr, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tx_Potongan2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tx_Kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tambah)
                    .addComponent(btnCetak, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setText("No Transaksi :");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("Jumlah");

        jLabel15.setBackground(new java.awt.Color(255, 255, 255));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel15.setText("Harga");

        txt_jumlah.setBackground(new java.awt.Color(204, 204, 204));
        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyTyped(evt);
            }
        });

        txt_harga_barang.setBackground(new java.awt.Color(204, 204, 204));
        txt_harga_barang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_harga_barangActionPerformed(evt);
            }
        });

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Subtotal");

        txtSubtotal.setBackground(new java.awt.Color(204, 204, 204));
        txtSubtotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSubtotalActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setText("Warna");

        txt_warna.setBackground(new java.awt.Color(204, 204, 204));
        txt_warna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_warnaActionPerformed(evt);
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
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(txt_harga_barang, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSubtotal)
                        .addGap(11, 11, 11))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_warna, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_warna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_jumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_harga_barang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addContainerGap())
        );

        label_no_transaksi.setText("jLabel3");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(label_no_transaksi, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1061, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(337, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(label_no_transaksi))
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        add(jPanel1, "card2");
    }// </editor-fold>//GEN-END:initComponents

    private void tx_barcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_barcodeActionPerformed
        String noBarcode = tx_barcode.getText();
        fetchBarangData(noBarcode);
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
        updateDataFromTable();
    }//GEN-LAST:event_btn_TransaksiBaruActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      Pilih2();
        System.out.println(tx_Potongan2.getText().length() == 0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tx_Potongan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tx_Potongan2ActionPerformed
   
    }//GEN-LAST:event_tx_Potongan2ActionPerformed

    private void tx_Potongan2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_Potongan2KeyReleased
       
    }//GEN-LAST:event_tx_Potongan2KeyReleased

    private void tx_Potongan2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_Potongan2KeyTyped
        // TODO add your handling code here:
         char type = evt.getKeyChar();
        if(!Character.isDigit(type)) {
            evt.consume();
        }
    }//GEN-LAST:event_tx_Potongan2KeyTyped

    private void txt_ukuranActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ukuranActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_ukuranActionPerformed

    private void txt_warnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_warnaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_warnaActionPerformed

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
        cetakStruk();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void tx_barcodeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tx_barcodeKeyPressed
        
    }//GEN-LAST:event_tx_barcodeKeyPressed

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed

    }//GEN-LAST:event_txt_jumlahActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_Penjualan;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btn_TransaksiBaru;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> cmb_jenisbyr;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JTextField txt_ukuran;
    private javax.swing.JTextField txt_warna;
    // End of variables declaration//GEN-END:variables
}
