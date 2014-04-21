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
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PdfFont {

    public static PdfPCell ustawfraze(String fraza, int colsp, int rowsp) throws DocumentException, IOException {
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
    }

    public static PdfPCell ustawfrazeAlign(String fraza, String orient, int fontsize) throws DocumentException, IOException {
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
    }

    public static String formatujliczby(Double wsad) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        try {
            String moneyString = formatter.format(wsad);
            return moneyString;
        } catch (Exception e) {
            return "";
        }
    }

}
