/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import Settings.Menu;
import base.Barang_Keluar;
import base.Barang_Masuk;
import base.Dashboard;
import base.Data_Barang;
import base.Data_Pengguna;
import base.Data_Suplier;
import base.Input_Barang;
import base.Input_Pengguna;
import base.Input_Suplier;
import base.Keuangan;
import base.Pembelian;
import base.Penjualan;
import base.Promo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JComponent;
import javax.swing.Timer;
import menu.Event;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 *
 * @author usER
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    private Timer timer;
    private String Nama;
    private String jabatan;
    private String statusAkses;
    private String id;
    
    public Main(String Nama, String jabatan, String id) {
        initComponents();
         this.id = id;
         this.Nama = Nama;
         this.jabatan = jabatan;
        
        lb_namaJabatan.setText("Selamat datang " + Nama + " , " + jabatan);
        lb_Username.setText(Nama);
        lb_Jabatan.setText(jabatan);
        
        if (jabatan.equals("Owner")) {
        statusAkses = "Owner";
    } else {
        statusAkses = "karyawan";
    }
        Action();
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
                 timer = new Timer(1000, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
                showDayDateTime();
            } 
        });
        
        timer.start();
    }


    private void showDayDateTime() {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        SimpleDateFormat formatHari = new SimpleDateFormat("EEEE", new Locale("in", "ID"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String hari = formatHari.format(calendar.getTime());
        String dateTime = dateFormat.format(now);
        lb_tanggal.setText(hari+", "+dateTime);
        
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMenu = new javax.swing.JPanel();
        menu1 = new menu.Menu();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        lb_Username = new javax.swing.JLabel();
        lb_Jabatan = new javax.swing.JLabel();
        panelUtama = new javax.swing.JPanel();
        panel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lb_tanggal = new javax.swing.JLabel();
        btn_setting = new javax.swing.JButton();
        lb_namaJabatan = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/iconUser.png"))); // NOI18N

        lb_Username.setBackground(new java.awt.Color(255, 255, 255));
        lb_Username.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lb_Username.setForeground(new java.awt.Color(255, 255, 255));
        lb_Username.setText("Nama");

        lb_Jabatan.setBackground(new java.awt.Color(255, 255, 255));
        lb_Jabatan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lb_Jabatan.setForeground(new java.awt.Color(255, 255, 255));
        lb_Jabatan.setText("Jabatan");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lb_Username)
                    .addComponent(lb_Jabatan))
                .addContainerGap(83, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lb_Username, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lb_Jabatan)))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelMenuLayout = new javax.swing.GroupLayout(panelMenu);
        panelMenu.setLayout(panelMenuLayout);
        panelMenuLayout.setHorizontalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)))
        );
        panelMenuLayout.setVerticalGroup(
            panelMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMenuLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelUtama.setBackground(new java.awt.Color(204, 204, 204));
        panelUtama.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 514, Short.MAX_VALUE)
        );

        panelUtama.add(panel, "card2");

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        lb_tanggal.setBackground(new java.awt.Color(255, 255, 255));
        lb_tanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_tanggal.setForeground(new java.awt.Color(255, 255, 255));
        lb_tanggal.setText("Hari, Tanggal Waktu ");

        btn_setting.setBackground(new java.awt.Color(51, 51, 51));
        btn_setting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/gear2.png"))); // NOI18N
        btn_setting.setBorder(null);
        btn_setting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_settingActionPerformed(evt);
            }
        });

        lb_namaJabatan.setBackground(new java.awt.Color(255, 255, 255));
        lb_namaJabatan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lb_namaJabatan.setForeground(new java.awt.Color(255, 255, 255));
        lb_namaJabatan.setText("Selamat Datang");

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(178, 178, 178)
                .addComponent(lb_namaJabatan)
                .addGap(167, 167, 167)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lb_tanggal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_setting))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_setting)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lb_tanggal)
                        .addComponent(lb_namaJabatan)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelUtama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_settingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_settingActionPerformed
   Settings.Menu menu = new Settings.Menu (this, true, this);
    
    //menampilkan JDialog dibawah button settings
    Point p = btn_setting.getLocationOnScreen();
    int x = p.x + btn_setting.getWidth() - menu.getWidth();
    int y =  p.y + btn_setting.getHeight();
    menu.setLocation(x, y);
    menu.setVisible(true);
    }//GEN-LAST:event_btn_settingActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
             //   new Main().setVisible(true);
            // Menjadi seperti ini
            String Nama = "your_username"; // Ganti dengan username yang sesuai
            String jabatan = "your_jabatan"; // Ganti dengan jabatan yang sesuai
            new Main("", "", "").setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_setting;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lb_Jabatan;
    private javax.swing.JLabel lb_Username;
    private javax.swing.JLabel lb_namaJabatan;
    private javax.swing.JLabel lb_tanggal;
    private menu.Menu menu1;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelMenu;
    private javax.swing.JPanel panelUtama;
    // End of variables declaration//GEN-END:variables

//Akses
private void Action() {
    menu1.setEvent(new Event() {
        @Override
      public void selectedMenu(int index, int subIndex) {
            if (statusAkses.equals("Owner")) {
                // Akses penuh untuk pemilik
                showMenu(index, subIndex);
            } else {
                // Batasi akses untuk karyawan
                if (!((index == 1 && subIndex == 1)||(index == 1 && subIndex == 2) || (index == 1 && subIndex == 3) || (index == 2 && subIndex == 1)||(index == 2 && subIndex == 2)||(index == 2 && subIndex == 3)||(index == 3 && subIndex == 0)||(index == 6 && subIndex == 1)||(index == 6 && subIndex == 2)||(index == 6 && subIndex == 3))) {
                    showMenu(index, subIndex);
                } else {
                    // Tampilkan pesan bahwa karyawan tidak diizinkan mengakses fitur tersebut
                    JOptionPane.showMessageDialog(Main.this, "Maaf, Anda tidak diizinkan mengakses fitur ini.", "Akses Ditolak", JOptionPane.WARNING_MESSAGE);
                    }
           }
        }
    });
} 

private void showMenu(int index, int subIndex) {
     if(index == 0 && subIndex==0) {
               changePanel(new Dashboard());
            } else if (index == 1 && subIndex == 1) {
                changePanel(new Input_Barang());
            } else if (index == 1 && subIndex == 2) {
                changePanel(new Input_Suplier());
            } else if (index == 1 && subIndex == 3) {
                changePanel(new Input_Pengguna());
            } else if (index == 2 && subIndex == 1) {
                changePanel(new Data_Barang());
            } else if (index == 2 && subIndex == 2) {
                changePanel(new Data_Suplier());
            } else if (index == 2 && subIndex == 3) {
                changePanel(new Data_Pengguna());
            } else if (index == 3 && subIndex == 0) {
                    changePanel(new Promo());
            } else if(index == 4 && subIndex == 0)  {
                changePanel(new Pembelian(id));
            } else if(index == 5 && subIndex == 0)  {
                changePanel(new Penjualan(id));
            } else if(index == 6 && subIndex == 1)  {
                changePanel(new Barang_Masuk());
            } else if(index == 6 && subIndex == 2)  {
                changePanel(new Barang_Keluar());
            } else if(index == 6 && subIndex == 3)  {
                changePanel(new Keuangan());
    }
}

private void changePanel(JComponent pn) {
    panelUtama.removeAll();
    panelUtama.add(pn);
    panelUtama.repaint();
    panelUtama.revalidate();
}

    public Object getFrame() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
        public JPanel getpanelUtama() {
        return panelUtama;
    }

}
