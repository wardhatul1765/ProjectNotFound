/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Barcode;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import javax.swing.ImageIcon;

/**
 *
 * @author MyBook Z Series
 */
public class PrintableBarcode implements Printable {

    private ImageIcon imageIcon;

    public PrintableBarcode(ImageIcon imageIcon) {
        this.imageIcon = imageIcon;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
        if (pageIndex > 0) {
            return Printable.NO_SUCH_PAGE;
        }

        // Translate and scale the graphics context to fit the entire printable area
        g.translate((int) pf.getImageableX(), (int) pf.getImageableY());

        // Draw the image on the graphics context
        g.drawImage(imageIcon.getImage(), 0, 0, null);

        return Printable.PAGE_EXISTS;
    }
}