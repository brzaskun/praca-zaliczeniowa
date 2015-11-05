/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansPdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class PdfHeaderFooter extends PdfPageEventHelper {
    
        private int liczydlo = 1;

        public PdfHeaderFooter() {
        }
        
        public PdfHeaderFooter(int l) {
            this.liczydlo = l;
        }
        

        Phrase[] header = new Phrase[2];
        Phrase[] footer = new Phrase[2];

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (Exception ex) {
            }
            Font font = new Font(helvetica,6);
            header[0] = new Phrase("Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman. Nie wymaga podpisu.",font);
            footer[0] = new Phrase(String.format("strona nr %d ", liczydlo),font);
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (Exception ex) {
            }
            Font font = new Font(helvetica,6);
            footer[0] = new Phrase(String.format("strona nr %d", liczydlo++),font);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            //dodawanie headera
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, header[0],
                    25, rect.getBottom() - 18, 0);
            //dodawanie footera
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, footer[0],
                    (rect.getLeft() + rect.getRight()) / 2 , rect.getTop() + 18, 0);
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document){
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (Exception ex) {
            }
            Font font = new Font(helvetica,6);
            footer[0] = new Phrase(String.format("strona nr %d - ostatnia", liczydlo),font);
        }
    
}
