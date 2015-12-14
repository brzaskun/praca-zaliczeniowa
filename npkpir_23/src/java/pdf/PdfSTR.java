/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.STRtabela;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfSTR {

    public static void drukuj(WpisView wpisView, List<STRtabela> wykaz) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR("srodki" + wpisView.getPodatnikWpisu() + ".pdf"));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
        pdf.addTitle("Ewidencja środków trwałych");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("PKPiR, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        pdf.setPageSize(PageSize.A4);
        PdfPTable table = new PdfPTable(19);
        table.setWidths(new int[]{1, 4, 2, 1, 2, 2, 2, 2,2,2,2,2,2,2,2,2,2,2,2});
        PdfPCell cell = new PdfPCell();
        table.addCell(ustawfrazeAlign("nr", "center",8));
        table.addCell(ustawfrazeAlign("nazwa środka", "center",8));
        table.addCell(ustawfrazeAlign("data przyj.", "center",8));
        table.addCell(ustawfrazeAlign("KST", "center",8));
        table.addCell(ustawfrazeAlign("cena zakupu", "center",8));
        table.addCell(ustawfrazeAlign("odpis roczny", "center",8));
        table.addCell(ustawfrazeAlign("umorz. dot.", "center",8));
        table.addCell(ustawfrazeAlign("stycz.", "center",8));
        table.addCell(ustawfrazeAlign("luty", "center",8));
        table.addCell(ustawfrazeAlign("marzec", "center",8));
        table.addCell(ustawfrazeAlign("kwiec.", "center",8));
        table.addCell(ustawfrazeAlign("maj", "center",8));
        table.addCell(ustawfrazeAlign("czerw.", "center",8));
        table.addCell(ustawfrazeAlign("lip.", "center",8));
        table.addCell(ustawfrazeAlign("sierp.", "center",8));
        table.addCell(ustawfrazeAlign("wrześ", "center",8));
        table.addCell(ustawfrazeAlign("paź", "center",8));
        table.addCell(ustawfrazeAlign("list.", "center",8));
        table.addCell(ustawfrazeAlign("grudz.", "center",8));
        table.setHeaderRows(1);

//        List<STRtabela> wykaz = obliczsume(sTRTabView.getStrtabela());
        for (STRtabela rs : wykaz) {
            if (rs.getId() != 0) {
                table.addCell(ustawfrazeAlign(String.valueOf(rs.getId()), "center",6));
            } else {
                table.addCell(ustawfrazeAlign("", "center",6));
            }
            table.addCell(ustawfrazeAlign(rs.getNazwa(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getDataprzek(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getKst(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getOdpisrok()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getUmorzeniaDo().doubleValue()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("01")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("02")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("03")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("04")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("05")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("06")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("07")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("08")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("09")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("10")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("11")), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getM().get("12")), "right",6));
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(new Chunk());
        Paragraph miziu1 = new Paragraph(new Phrase("Biuro Rachunkowe Taxman. Ewidencja środków trwałych klienta: "+wpisView.getPodatnikWpisu()+" za rok "+wpisView.getRokWpisu(),font));
        miziu1.setAlignment(Element.ALIGN_CENTER);
        pdf.add(miziu1);
        pdf.add(Chunk.NEWLINE);
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        Msg.msg("i", "Wydrukowano środki trwałe", "form:messages");
    }

//    private List<STRtabela> obliczsume(List<STRtabela> wykaz) {
//        Double nettosuma = 0.0;
//        Double bruttosuma = 0.0;
//        for(STRtabela p : wykaz){
//            nettosuma += p.getNetto();
//            bruttosuma += p.getBrutto();
//        }
//        STRtabela suma = new STRtabela();
//        suma.setNrWpkpir(0);
//        suma.setNrWlDk("");
//        suma.setKontr(new Klienci());
//        suma.setRodzTrans("");
//        suma.setOpis("podsumowanie");
//        suma.setNetto(nettosuma);
//        suma.setBrutto(bruttosuma);
//        wykaz.add(suma);
//        return wykaz;
//    }

       
}
