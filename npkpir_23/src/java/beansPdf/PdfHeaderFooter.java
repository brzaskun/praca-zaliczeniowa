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
import error.E;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PdfHeaderFooter extends PdfPageEventHelper {
    
        private int liczydlo = 1;
        private Font font;
        private Phrase header;
        private Phrase footer;

        public PdfHeaderFooter() {
            try {
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                this.font = new Font(helvetica,6);
            } catch (Exception ex) {
                E.e(ex);
            }
        }
        
        public PdfHeaderFooter(int l) {
            this.liczydlo = l;
            try {
                BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                this.font = new Font(helvetica,6);
            } catch (Exception ex) {
                E.e(ex);
            }
        }
       

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            header = new Phrase("Dokument wygenerowano elektronicznie dnia "+data.Data.aktualnaDataCzas() +" w autorskim programie księgowym Biura Rachunkowego Taxman. Nie wymaga podpisu.",font);
        }

        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            footer = new Phrase(String.format("strona nr %d", writer.getPageNumber()),font);
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            Rectangle rect = writer.getBoxSize("art");
            //dodawanie headera
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_LEFT, header,
                    25, rect.getBottom() - 18, 0);
            //dodawanie footera
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, footer,
                    (rect.getLeft() + rect.getRight()) / 2 , rect.getTop() + 18, 0);
        }

        @Override
        public void onCloseDocument(PdfWriter writer, Document document){

        }
    
}
