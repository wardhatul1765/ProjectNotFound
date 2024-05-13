/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class MenuItem extends JButton{
    

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSubMenu() {
        return subMenu;
    }

    public void setSubMenu(boolean subMenu) {
        this.subMenu = subMenu;
    }
    
    public float getAnimation() {
        return this.animation;
    }
    
    public void setAnimation(float animation) {
        this.animation = animation;
    }

    public int getSubMenuIndex() {
        return subMenuIndex;
    }

    public void setSubMenuIndex(int subMenuIndex) {
        this.subMenuIndex = subMenuIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
    
    private int index;
    private boolean subMenu;
    private float animation;
//    Submenu
    private int subMenuIndex;
    private int length;
    
//  Menu Item
    public MenuItem(String name, int index, boolean subMenu) {
        super(name);
        this.index = index;
        this.subMenu = subMenu;
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255));
        setHorizontalAlignment(SwingConstants.LEADING);
        setBorder(new EmptyBorder(9, 10, 9, 10));
//        setIconTextGap(10);
    }
    
//  Sub Menu
    public void initSubmenu(int subMenuIndex, int length) {
        this.subMenuIndex = subMenuIndex;
        this.length = length;
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255));
        setHorizontalAlignment(SwingConstants.CENTER);
        setBorder(new EmptyBorder(9, 10, 9, 10));
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
  //      if(length != 0) {
 //           g2d.setColor(new Color(255, 255, 255));
 //           if(subMenuIndex == 1) {
//          First Index
//            g2d.drawLine(18, 0, 18, getHeight());
 //           g2d.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
            
  //          } else if(subMenuIndex == length - 1) {
//                Last Index
 //           g2d.drawLine(18, 0, 18, getHeight() / 2);
 //           g2d.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
 //           } else {
 //           g2d.drawLine(18, 0, 18, getHeight());
 //           g2d.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
 //           }
        if(subMenu) {
        g2d.setColor(getForeground());
        int arrowWidth = 8;
        int arrowHeight = 5;
        Path2D path = new Path2D.Double();
        path.moveTo(0, arrowHeight * animation);
        path.lineTo(arrowWidth / 2, (1f - animation) * arrowHeight);
        path.lineTo(arrowWidth, arrowHeight * animation);
        g2d.translate(getWidth() - arrowWidth - 15, (getHeight() - arrowHeight) / 2);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.draw(path);
    }
    g2d.dispose(); 
    super.paintComponent(g); 
    }
    
    
    
}
