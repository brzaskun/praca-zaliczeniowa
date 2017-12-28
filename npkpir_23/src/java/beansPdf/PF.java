/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.BaseFont;
import error.E;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.swing.JEditorPane;

/**
 *
 * @author Osito
 */
public class PF {
    public static Font getFont(String nazwafontu) {
        Font zwrot = null;
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            FontFactory.register(realPath+"resources/fonts/"+nazwafontu+".ttf");
            zwrot = FontFactory.getFont(nazwafontu, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 8); //10 is the size
         } catch (Exception ex) {
            E.e(ex);
            zwrot = getDefault(8);
        }
        return zwrot;
    }
    
    public static Font getFont(String nazwafontu, int size) {
        Font zwrot = null;
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            FontFactory.register(realPath+"resources/fonts/"+nazwafontu+".ttf");
            zwrot = FontFactory.getFont(nazwafontu, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size); //10 is the size
         } catch (Exception ex) {
            E.e(ex);
            zwrot = getDefault(size);
        }
        return zwrot;
    }
    
    public static Font getFontColor(String nazwafontu, int size, String kolor) {
        Font zwrot = null;
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            FontFactory.register(realPath+"resources/fonts/"+nazwafontu+".ttf");
            zwrot = FontFactory.getFont(nazwafontu, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, size); //10 is the size
            zwrot.setColor(pobierzkolor(kolor));
         } catch (Exception ex) {
            E.e(ex);
            zwrot = getDefault(size);
        }
        return zwrot;
    }
    
    private static BaseColor pobierzkolor(String kolor) {
        BaseColor zwrot = BaseColor.BLACK;
        switch(kolor) {
            case "red":
                zwrot = BaseColor.RED;
                break;
        }
        return zwrot;
    }
    
    private static Font getDefault(int size) {
        Font zwrot = null;
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            zwrot = new Font(helvetica, size);
        } catch (DocumentException ex) {
            E.e(ex);
        } catch (IOException ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    public static void main(String[] args) {
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Font[] allFonts = e.getAllFonts();
        Font f = getFont("Courier");
        JEditorPane outputArea = new JEditorPane();
        String fontFamily = outputArea.getFont().getFamily();
        System.out.println("dd "+f.getFamilyname());
    }

    
}
