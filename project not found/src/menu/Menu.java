package menu;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;


public class Menu extends JComponent{
    
    private Event event;
    
    public Event getEvent() {
        return this.event;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }
    
    private MigLayout layout;
    private String[][] menuItems = new String[][] {
        {"Dashboard"},
        {"Master", "Input Barang", "Input Suplier", "Input Pengguna"},
        {"Data", "Data Barang", "Data Suplier", "Data Pengguna"},
        {"Promo"},
        {"Transaksi", "Pembelian", "Penjualan"},
        {"Laporan", "Data Barang Masuk", "Data Barang Keluar","Keuangan"},
    };
    
    public Menu() {
        init();
    }
    
    private void init() {
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        
//      Init Menu Item
        for(int i = 0; i < menuItems.length; i++) {
            addMenu(menuItems[i][0], i);
        }
    }
    
//  Icon Menu
    private Icon getIcon(int index) {
        URL url = getClass().getResource("/picture/"+index+".png");
        if(url != null) {
            return new ImageIcon(url);
        } else {
            return null;
        }
    }
    
//  Menu Item
    private void addMenu(String menuName, int index) {
        int length = menuItems[index].length;
        MenuItem item = new MenuItem(menuName, index, length > 1);
        Icon icon = getIcon(index);
        if(icon != null) {
            item.setIcon(icon);
        }
//      Init Submenu
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(length > 1) {
                    if(!item.isSelected()) {
                        item.setSelected(true);
                    addSubmenu(item, index, length, getComponentZOrder(item));                        
                    } else {
//                        Hide submenu
                        hideMenu(item, index);
                        item.setSelected(false);
                    }
                } else {
                    if(event != null) {
                        event.selectedMenu(index, 0);
                    }
                }
            }
        });
        add(item);
        revalidate();
        repaint();
    }
    
//  Sub menu
    private void addSubmenu(MenuItem item, int index, int length, int indexZorder) {
        JPanel panel = new JPanel(new MigLayout("wrap 1, fillx, inset 0, gapy 0", "fill"));
        panel.setName(index + "");
        panel.setOpaque(true);
        panel.setBackground(new Color(51, 51, 51));
        for(int i = 1; i < length; i++) {
            MenuItem subItem = new MenuItem(menuItems[index][i], i, false);
            subItem.initSubmenu(i, length);
            
            subItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(event != null) {
                        event.selectedMenu(index, subItem.getIndex());
                    }
                }
            });
            
            panel.add(subItem);
        }
        add(panel, "h 0!", indexZorder + 1);
        revalidate();
        repaint();
        menuAnimation.showMenu(panel, item, layout, true);
    }
    
    private void hideMenu(MenuItem item, int index) {
        for(Component component : getComponents()) {
            if(component instanceof JPanel && component.getName() != null && component.getName().equals(index + "")) {
                component.setName(null);
                menuAnimation.showMenu(component, item, layout, false);
            }
        }
    }
    
//    Background menu 
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g.create();
        g2D.setColor(new Color(51, 51, 51));
        g2D.fill(new Rectangle(0, 0, getWidth(), getHeight()));
        super.paintComponent(g); 
    }
    
}
