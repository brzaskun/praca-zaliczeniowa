/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import enumy.Fonty;
import error.E;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class PdfFont {

    

    public static Paragraph ustawparagraf(String fraza){
        Paragraph par = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            par = new Paragraph(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, 10)));
        } catch (Exception ex) {
            E.e(ex);
        }
        return par;
    }
    
    public static PdfPCell ustawfraze(String fraza, int colsp, int rowsp){
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, 8)));
            if (rowsp > 0) {
                cell.setRowspan(rowsp);
            } else {
                cell.setColspan(colsp);
            }
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            return cell;
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAF(String fraza, int colsp, int rowsp, int uluz, int fontsize) {
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, fontsize)));
            if (rowsp > 0) {
                cell.setRowspan(rowsp);
            } else {
                cell.setColspan(colsp);
            }
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(uluz);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfraze(String fraza, int colsp, int rowsp, float fixedHeigth){
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, 8)));
            if (rowsp > 0) {
                cell.setRowspan(rowsp);
            } else {
                cell.setColspan(colsp);
            }
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setFixedHeight(fixedHeigth);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeSpanFont(String fraza, int colsp, int rowsp, int fontsize) throws DocumentException, IOException {
        String fraza2 = String.valueOf( fraza != null ? fraza : "");
        PdfPCell cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, fontsize)));
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
     PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    public static PdfPCell ustawfrazeAlign(Object fraza, String orient, int fontsize) {
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
            return cell;
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlign(String fraza, String orient, int fontsize, BaseColor color) {
        PdfPCell cell = null;
        try {
            String fraza2 = fraza != null ? fraza : "";
            Font font = PF.getFont(Fonty.CALIBRI, fontsize);
            font.setColor(color);
            cell = new PdfPCell(new Phrase(fraza2, font));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell emptyCell() {
        return new PdfPCell();
    }
    public static PdfPCell ustawfrazeAlign(String fraza, String orient, int fontsize) {
        PdfPCell cell = null;
        try {
            String fraza2 = fraza != null ? fraza : "";
            cell = new PdfPCell(new Phrase(fraza2,PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlignBGColor(String fraza, String orient, int fontsize, BaseColor color) {
        PdfPCell cell = null;
        try {
            String fraza2 = fraza != null ? fraza : "";
            cell = new PdfPCell(new Phrase(fraza2,PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(color);
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlignLevel(String fraza, String orient, int fontsize, int level) {
        PdfPCell cell = null;
        try {
            String fraza2 = fraza != null ? fraza : "";
            cell = new PdfPCell(new Phrase(fraza2,PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            if (level==0) {
                cell.setBorderWidthTop(1f);
                cell.setBorderWidthBottom(1f);
            }
            if (level==1) {
                cell.setBorderWidthTop(0.8f);
                cell.setBorderWidthBottom(0.8f);
            }
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlignColor(String fraza, String orient, int fontsize, String kolor) {
        PdfPCell cell = null;
        try {
            String fraza2 = fraza != null ? fraza : "";
            cell = new PdfPCell(new Phrase(fraza2,PF.getFontColor(Fonty.CALIBRI, fontsize, kolor)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlignNoBorder(String fraza, String orient, int fontsize) {
        PdfPCell cell = null;
        try {
            String fraza2 = fraza != null ? fraza : "";
            cell = new PdfPCell(new Phrase(fraza2,PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setBorder(0);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    
    public static PdfPCell ustawfrazeAlign(Object fraza, String orient, int fontsize, float fixedHeigth) {
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
            cell.setFixedHeight(fixedHeigth);
            cell.setIndent(20);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlign(Object fraza, String orient, int fontsize, float fixedHeigth, float intend) {
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFont(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
            cell.setFixedHeight(fixedHeigth);
            cell.setIndent(intend);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    public static PdfPCell ustawfrazeAlignBold(Object fraza, String orient, int fontsize, float fixedHeigth, float intend) {
        PdfPCell cell = null;
        try {
            String fraza2 = String.valueOf( fraza != null ? fraza : "");
            cell = new PdfPCell(new Phrase(fraza2, PF.getFontBold(Fonty.CALIBRI, fontsize)));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            getOrient(cell, orient);
            cell.setFixedHeight(fixedHeigth);
            cell.setIndent(intend);
        } catch (Exception ex) {
            E.e(ex);
        }
        return cell;
    }
    
    private static void getOrient(PdfPCell cell, String orient) {
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
        try {
            FontFactory.register("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/fonts/Calibri.ttf", "my_font");
            Font myBoldFont = FontFactory.getFont("my_font");
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 5);
            String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
            for (int i = 0; i < fonts.length; i++) {
                error.E.s(fonts[i]);
            }
            java.awt.Font[] fontsa = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
            //java.awt.Font name = fontsa["Calibri"];
            error.E.s("");
        } catch (DocumentException ex) {
            // Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(PdfFont.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            NumberFormat formatter = NumberFormat.getNumberInstance();
//            formatter.setMinimumFractionDigits(2);
//            formatter.setMaximumFractionDigits(2);
//            Locale l = Locale.GERMAN;
//            String moneyString = formatter.format(10000);
//        } catch (Exception e) {
//            E.e(e);
//        }
    }

}
