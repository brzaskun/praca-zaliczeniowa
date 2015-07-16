/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansPdf;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class PdfFont {

    public static PdfPCell ustawfraze(String fraza, int colsp, int rowsp){
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 8);
            PdfPCell cell = new PdfPCell(new Phrase(fraza, font));
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
    
    public static PdfPCell ustawfrazeSpanFont(String fraza, int colsp, int rowsp, int fontsize) throws DocumentException, IOException {
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica, fontsize);
        PdfPCell cell = new PdfPCell(new Phrase(fraza, font));
        if (rowsp > 0) {
            cell.setRowspan(rowsp);
        } else {
            cell.setColspan(colsp);
        }
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    public static PdfPCell ustawfrazeAlign(Object fraza, String orient, int fontsize) {
        try {
            String fraza2 = String.valueOf(fraza);
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
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, fontsize);
            PdfPCell cell = new PdfPCell(new Phrase(fraza, font));
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

    public static String formatujWaluta(Double wsad) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);
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
        try {
            String numberString = formatter.format(wsad);
            return numberString;
        } catch (Exception e) {
            return "";
        }
    }

}
