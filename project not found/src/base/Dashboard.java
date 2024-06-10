/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package base;

import com.raven.model.Model_Card;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import javax.swing.ImageIcon;
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
        card1.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/raven/icon/stock.png")), "Barang Masuk Hari Ini", ""));
        card2.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/raven/icon/stock.png")), "Barang Keluar Hari Ini", ""));
        card3.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/raven/icon/stock.png")), "Total Penjualan", ""));
        card4.setData(new Model_Card(new ImageIcon(getClass().getResource("/com/raven/icon/profit.png")), "Total Keuntungan", ""));
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
                   Tabledash.getTableHeader().setBackground(new Color(0,40,85));
       Tabledash.getTableHeader().setForeground(Color.WHITE);
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
        lb_BarangMasuk = new javax.swing.JLabel();
        card1 = new com.raven.component.Card();
        lb_BarangKeluar = new javax.swing.JLabel();
        card2 = new com.raven.component.Card();
        lb_TotalPenjualan = new javax.swing.JLabel();
        card3 = new com.raven.component.Card();
        lb_Keuntungan = new javax.swing.JLabel();
        card4 = new com.raven.component.Card();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabledash = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(520, 422));
        setLayout(new java.awt.CardLayout());

        main_panel.setBackground(new java.awt.Color(255, 255, 255));
        main_panel.setOpaque(false);
        main_panel.setPreferredSize(new java.awt.Dimension(520, 422));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("DASHBOARD");
        jPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        lb_BarangMasuk.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_BarangMasuk.setForeground(new java.awt.Color(255, 255, 255));
        lb_BarangMasuk.setText("jLabel3");
        jPanel3.add(lb_BarangMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        card1.setColor1(new java.awt.Color(142, 142, 250));
        card1.setColor2(new java.awt.Color(123, 123, 245));
        jPanel3.add(card1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 94, 285, -1));

        lb_BarangKeluar.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_BarangKeluar.setForeground(new java.awt.Color(255, 255, 255));
        lb_BarangKeluar.setText("jLabel2");
        jPanel3.add(lb_BarangKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 60, -1));

        card2.setColor1(new java.awt.Color(186, 123, 247));
        card2.setColor2(new java.awt.Color(167, 94, 236));
        jPanel3.add(card2, new org.netbeans.lib.awtextra.AbsoluteConstraints(303, 94, 285, -1));

        lb_TotalPenjualan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_TotalPenjualan.setForeground(new java.awt.Color(255, 255, 255));
        lb_TotalPenjualan.setText("jLabel2");
        jPanel3.add(lb_TotalPenjualan, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 200, -1, -1));

        card3.setColor1(new java.awt.Color(241, 208, 62));
        card3.setColor2(new java.awt.Color(211, 184, 61));
        jPanel3.add(card3, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 94, 285, -1));

        lb_Keuntungan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lb_Keuntungan.setForeground(new java.awt.Color(255, 255, 255));
        lb_Keuntungan.setText("jLabel2");
        jPanel3.add(lb_Keuntungan, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 200, -1, -1));

        card4.setColor1(new java.awt.Color(0, 0, 255));
        card4.setColor2(new java.awt.Color(102, 102, 255));
        jPanel3.add(card4, new org.netbeans.lib.awtextra.AbsoluteConstraints(897, 94, 285, -1));

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
                        .addComponent(jScrollPane1)
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
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        add(main_panel, "card2");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabledash;
    private com.raven.component.Card card1;
    private com.raven.component.Card card2;
    private com.raven.component.Card card3;
    private com.raven.component.Card card4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lb_BarangKeluar;
    private javax.swing.JLabel lb_BarangMasuk;
    private javax.swing.JLabel lb_Keuntungan;
    private javax.swing.JLabel lb_TotalPenjualan;
    private javax.swing.JPanel main_panel;
    // End of variables declaration//GEN-END:variables
}
