/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import koneksi.Koneksi;

/**
 *
 * @author user
 */
public class Dashboard extends javax.swing.JPanel {
    public static double[] getPenjualanHariIni() throws SQLException {
        double[] penjualan = new double[1]; // Indeks 0 untuk jumlah, indeks 1 untuk subtotal
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getKoneksi(); // Mendapatkan koneksi ke database
            String query = "SELECT SUM(total_harga) FROM detail_penjualan WHERE no_penjualan IN (SELECT no_penjualan FROM penjualan WHERE tgl_jual = ?);";
            pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now())); // Mendapatkan tanggal hari ini
            
            rs = pstmt.executeQuery();
            
             if (rs.next()) {
            penjualan[0] = rs.getDouble(1); // Mendapatkan subtotal harga barang terjual
        }
    } catch (SQLException e) {
        // Tangani kesalahan saat mengambil koneksi
        System.out.println("Gagal mengambil koneksi dari database: " + e.getMessage());
        throw e;
    }
    
    return penjualan;
}
    public static double getTotalPengeluaranHariIni() throws SQLException {
        double totalPengeluaran = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getKoneksi();
            String query = "SELECT SUM(total_harga) FROM detail_pembelian WHERE no_pembelian IN (SELECT no_pembelian FROM pembelian WHERE tgl_pembelian = ?);";
            pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                totalPengeluaran = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil koneksi dari database: " + e.getMessage());
            throw e;
        }
        return totalPengeluaran;
     }
    
    public static double getJumlahBarangTerjualHariIni() throws SQLException {
        double jumlahTerjual = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getKoneksi();
            String query = "SELECT SUM(jumlah) FROM detail_penjualan WHERE no_penjualan IN (SELECT no_penjualan FROM penjualan WHERE tgl_jual = ?);";
            pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                jumlahTerjual = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil koneksi dari database: " + e.getMessage());
            throw e;
        }
        return jumlahTerjual;
    }
    
     public static double getJumlahBarangMasukHariIni() throws SQLException {
        double totalBarangMasuk = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getKoneksi();
            String query = "SELECT SUM(jumlah) FROM detail_pembelian WHERE no_pembelian IN (SELECT no_pembelian FROM pembelian WHERE tgl_pembelian = ?);";
            pstmt = conn.prepareStatement(query);
            pstmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                totalBarangMasuk = rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil koneksi dari database: " + e.getMessage());
            throw e;
        }
        return totalBarangMasuk;
     }
     
     private DefaultTableModel model;
    public void loadDataBarang(){
    model.getDataVector().removeAllElements();
    model.fireTableDataChanged();
    
    try{
        Connection c = Koneksi.getKoneksi();
        java.sql.Statement s = c.createStatement();
        
        String sql = "SELECT \n" +
"    barang.kode_barang,\n" +
"    barang.nama_barang,\n" +
"    barang.ukuran,\n" +
"    barang.warna,\n" +
"    detail_penjualan.jumlah,\n" +
"    detail_penjualan.total_harga\n" +
"FROM \n" +
"    penjualan\n" +
"JOIN \n" +
"    detail_penjualan ON penjualan.no_penjualan = detail_penjualan.no_penjualan\n" +
"JOIN \n" +
"    barang ON detail_penjualan.kode_barang = barang.kode_barang\n" +
"WHERE \n" +
"    DATE(penjualan.tgl_jual) = CURDATE();";

        ResultSet r = s.executeQuery(sql);
        
        while (r.next()) {
                Object[] obj = new Object[6];  // Adjusted to 6 as per your query result set
                obj[0] = r.getString("kode_barang");
                obj[1] = r.getString("nama_barang");
                obj[2] = r.getString("ukuran");
                obj[3] = r.getString("warna");
                obj[4] = r.getInt("jumlah");
                obj[5] = r.getDouble("total_harga");

                model.addRow(obj);
            }
            r.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
     
     
     
    
    

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        init();
        model = new DefaultTableModel();
        Tabledash.setModel(model);
        //InputBarang
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Ukuran");
        model.addColumn("Warna");
        model.addColumn("Jumlah");
        model.addColumn("Sub Total Harga");
        loadDataBarang();
        try {
            double[] penjualan = getPenjualanHariIni();
            double jumlahPenjualan = penjualan[0];
            double jumlahTerjual = getJumlahBarangTerjualHariIni();
            double totalBarangMasuk = getJumlahBarangMasukHariIni();
            double totalPengeluaran = getTotalPengeluaranHariIni();
            double keuntungan = jumlahPenjualan - totalPengeluaran;
            
            DecimalFormat df = new DecimalFormat("#,###");
            
            lb_Keuntungan.setText(df.format(keuntungan));
            lb_BarangKeluar.setText(df.format(jumlahTerjual));
            lb_BarangMasuk.setText(df.format(totalBarangMasuk));
            lb_TotalPenjualan.setText(df.format(jumlahPenjualan));
        } catch (SQLException e) {
            // Tangani kesalahan
            System.out.println("Terjadi kesalahan saat mengambil data penjualan: " + e.getMessage());
        }
    }
    
    public double hitungProfit(double totalPenjualan, double totalPembelian) {
    return totalPenjualan - totalPembelian;
    
}

    private void init() {
         
            
         

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
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lb_BarangMasuk = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lb_TotalPenjualan = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lb_BarangKeluar = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        lb_Keuntungan = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabledash = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(520, 422));
        setLayout(new java.awt.CardLayout());

        main_panel.setBackground(new java.awt.Color(255, 255, 255));
        main_panel.setOpaque(false);
        main_panel.setPreferredSize(new java.awt.Dimension(520, 422));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("DASHBOARD");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Barang Masuk Hari  Ini");

        lb_BarangMasuk.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lb_BarangMasuk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_BarangMasuk.setText("jLabel6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lb_BarangMasuk, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(38, 38, 38)
                .addComponent(lb_BarangMasuk)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(126, 50));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Total Penjualan");

        lb_TotalPenjualan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lb_TotalPenjualan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_TotalPenjualan.setText("jLabel6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lb_TotalPenjualan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(37, 37, 37)
                .addComponent(lb_TotalPenjualan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setPreferredSize(new java.awt.Dimension(126, 50));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Barang Keluar Hari Ini");

        lb_BarangKeluar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lb_BarangKeluar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_BarangKeluar.setText("jLabel6");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lb_BarangKeluar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(38, 38, 38)
                .addComponent(lb_BarangKeluar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setPreferredSize(new java.awt.Dimension(126, 50));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Total Keuntungan");

        lb_Keuntungan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lb_Keuntungan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_Keuntungan.setText("jLabel6");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(lb_Keuntungan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(39, 39, 39)
                .addComponent(lb_Keuntungan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(13, 13, 13))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        Tabledash.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(Tabledash);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel6.setText("Riwayat Transaksi Terakhir");

        javax.swing.GroupLayout main_panelLayout = new javax.swing.GroupLayout(main_panel);
        main_panel.setLayout(main_panelLayout);
        main_panelLayout.setHorizontalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(main_panelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(main_panelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        main_panelLayout.setVerticalGroup(
            main_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(main_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(main_panel, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabledash;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_BarangKeluar;
    private javax.swing.JLabel lb_BarangMasuk;
    private javax.swing.JLabel lb_Keuntungan;
    private javax.swing.JLabel lb_TotalPenjualan;
    private javax.swing.JPanel main_panel;
    // End of variables declaration//GEN-END:variables
}
