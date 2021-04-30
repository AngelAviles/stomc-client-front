package print;

import dominio.Turn;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class Ticket implements Printable {

    private Turn turno;

    public Ticket(Turn turno) {
        this.turno = turno;
    }

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {

        int interline = 12;
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setFont(new Font("CourierThai", Font.PLAIN, 12));
        int x =  (int) pageFormat.getImageableX();
        int y = (int) pageFormat.getImageableY();

        graphics.drawString("Hello world!", 100, 100);

        return Printable.PAGE_EXISTS;
    }
}
