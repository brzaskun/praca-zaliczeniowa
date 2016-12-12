/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import entity.Deklaracjevat;
import error.E;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.metamodel.SingularAttribute;

/**
 *
 * @author Osito
 */
@Named

public class PdfFont {

    public static PdfPCell ustawfraze(String fraza, int colsp, int rowsp){
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 8);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            if (rowsp > 0) {
                cell.setRowspan(rowsp);
            } else {
                cell.setColspan(colsp);
            }
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static PdfPCell ustawfrazeAF(String fraza, int colsp, int rowsp, int uluz, int font_size) {
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, font_size);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            if (rowsp > 0) {
                cell.setRowspan(rowsp);
            } else {
                cell.setColspan(colsp);
            }
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(uluz);
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static PdfPCell ustawfraze(String fraza, int colsp, int rowsp, float fixedHeigth){
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 8);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            if (rowsp > 0) {
                cell.setRowspan(rowsp);
            } else {
                cell.setColspan(colsp);
            }
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setFixedHeight(fixedHeigth);
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static PdfPCell ustawfrazeSpanFont(String fraza, int colsp, int rowsp, int fontsize) throws DocumentException, IOException {
        String fraza2 = String.valueOf( fraza != null ? fraza : "");
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, fontsize);
        PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
        if (rowsp > 0) {
            cell.setRowspan(rowsp);
        } else {
            cell.setColspan(colsp);
        }
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
 
 
 public static PdfPCell ustawfrazeAlignNOBorder(Object fraza, String orient, int fontsize) {
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, fontsize);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            switch (orient) {
                case "right":
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    break;
                case "left":
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    break;
                case "center":
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    break;
                case "just":
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    break;
            }
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static PdfPCell ustawfrazeAlign(Object fraza, String orient, int fontsize) {
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, fontsize);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            switch (orient) {
                case "right":
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    break;
                case "left":
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    break;
                case "center":
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    break;
                case "just":
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    break;
            }
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static PdfPCell ustawfrazeAlign(String fraza, String orient, int fontsize) {
        try {
            String fraza2 = fraza != null ? fraza : "";
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, fontsize);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            switch (orient) {
                case "right":
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    break;
                case "left":
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    break;
                case "center":
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    break;
                case "just":
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    break;
            }
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static PdfPCell ustawfrazeAlignNoBorder(String fraza, String orient, int fontsize) {
        try {
            String fraza2 = fraza != null ? fraza : "";
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, fontsize);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            cell.setBorder(0);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            switch (orient) {
                case "right":
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    break;
                case "left":
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    break;
                case "center":
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    break;
                case "just":
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    break;
            }
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    
    public static PdfPCell ustawfrazeAlign(Object fraza, String orient, int fontsize, float fixedHeigth) {
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, fontsize);
            PdfPCell cell = new PdfPCell(new Phrase(fraza2, font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            switch (orient) {
                case "right":
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    break;
                case "left":
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    break;
                case "center":
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    break;
                case "just":
                    cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    break;
            }
            cell.setFixedHeight(fixedHeigth);
            return cell;
        } catch (DocumentException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static String formatujWaluta(Double wsad) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        try {
            String moneyString = formatter.format(wsad);
            return moneyString;
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String formatujWalutaNoZero(Double wsad) {
        String zwrot = "";
        if (wsad != 0.0) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            formatter.setGroupingUsed(true);
            try {
                zwrot = formatter.format(wsad);
            } catch (Exception e) {
                E.e(e);
            }
        }
        return zwrot;
    }
    
    public static String formatujEuro(Double wsad) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        try {
            String moneyString = formatter.format(wsad)+" €";
            return moneyString;
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String formatujKurs(Double wsad) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(4);
        formatter.setMaximumFractionDigits(4);
        formatter.setGroupingUsed(true);
        try {
            String moneyString = formatter.format(wsad);
            return moneyString;
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String formatujProcent(Double wsad) {
        NumberFormat formatter = NumberFormat.getPercentInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        try {
            String percentString = formatter.format(wsad);
            return percentString;
        } catch (Exception e) {
            return "";
        }
    }
    
    public static String formatujLiczba(Double wsad) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("PL"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
        formatter.setGroupingUsed(true);
        try {
            String numberString = formatter.format(wsad);
            return numberString;
        } catch (Exception e) {
            return "";
        }
    }
    
    //wykrywanie fontow z systemu
    public static void main(String[] args) {
//        try {
//            FontFactory.register("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/fonts/Calibri.ttf", "my_font");
//            Font myBoldFont = FontFactory.getFont("my_font");
//            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//            Font font = new Font(helvetica, 5);
//            String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
//            for (int i = 0; i < fonts.length; i++) {
//                System.out.println(fonts[i]);
//            }
//        } catch (DocumentException ex) {
//            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
//        }
        try {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(2);
            formatter.setMaximumFractionDigits(2);
            Locale l = Locale.GERMAN;
            String moneyString = formatter.format(10000);
            System.out.println(moneyString);
        } catch (Exception e) {
            E.e(e);
        }
    }

}
