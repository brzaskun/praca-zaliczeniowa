/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

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
import dao.EwidencjeVatDAO;
import embeddable.STRtabela;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import msg.Msg;
import view.STREwidencja;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfSTR extends Pdf implements Serializable {
    @ManagedProperty(value="#{STREwidencja}")
    protected STREwidencja sTREwidencja;
    public void drukuj() throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_22/build/web/wydruki/srodki" + wpisView.getPodatnikWpisu() + ".pdf"));
        HeaderFooter event = new HeaderFooter();
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(event);
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
        try {
            table.addCell(ustawfrazebez("nr", "center",8));
            table.addCell(ustawfrazebez("nazwa środka", "center",8));
            table.addCell(ustawfrazebez("data przyj.", "center",8));
            table.addCell(ustawfrazebez("KST", "center",8));
            table.addCell(ustawfrazebez("cena zakupu", "center",8));
            table.addCell(ustawfrazebez("odpis roczny", "center",8));
            table.addCell(ustawfrazebez("umorz. dot.", "center",8));
            table.addCell(ustawfrazebez("stycz.", "center",8));
            table.addCell(ustawfrazebez("luty", "center",8));
            table.addCell(ustawfrazebez("marzec", "center",8));
            table.addCell(ustawfrazebez("kwiec.", "center",8));
            table.addCell(ustawfrazebez("maj", "center",8));
            table.addCell(ustawfrazebez("czerw.", "center",8));
            table.addCell(ustawfrazebez("lip.", "center",8));
            table.addCell(ustawfrazebez("sierp.", "center",8));
            table.addCell(ustawfrazebez("wrześ", "center",8));
            table.addCell(ustawfrazebez("paź", "center",8));
            table.addCell(ustawfrazebez("list.", "center",8));
            table.addCell(ustawfrazebez("grudz.", "center",8));
            
            table.setHeaderRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }

//        List<STRtabela> wykaz = obliczsume(sTRTabView.getStrtabela());
        List<STRtabela> wykaz = sTREwidencja.getStrtabela();
        for (STRtabela rs : wykaz) {
            if (rs.getId() != 0) {
                table.addCell(ustawfrazebez(String.valueOf(rs.getId()), "center",6));
            } else {
                table.addCell(ustawfrazebez("", "center",6));
            }
            table.addCell(ustawfrazebez(rs.getNazwa(), "left",6));
            table.addCell(ustawfrazebez(rs.getDataprzek(), "left",6));
            table.addCell(ustawfrazebez(rs.getKst(), "left",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getNetto()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getOdpisrok()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getUmorzeniaDo().doubleValue()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getStyczen()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getLuty()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getMarzec()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getKwiecien()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getMaj()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getCzerwiec()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getLipiec()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getSierpien()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getWrzesien()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getPazdziernik()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getListopad()), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.getGrudzien()), "right",6));
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(new Chunk());
        Paragraph miziu1 = new Paragraph(new Phrase("Biuro Rachunkowe Taxman. Ewidencja środków trwałych klienta: "+wpisView.getPodatnikWpisu()+" za rok "+wpisView.getRokWpisu(),font));
        miziu1.setAlignment(Element.ALIGN_CENTER);
        pdf.add(miziu1);
        pdf.add(new Chunk().NEWLINE);
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

    public EwidencjeVatDAO getEwidencjeVatDAO() {
        return ewidencjeVatDAO;
    }

    public void setEwidencjeVatDAO(EwidencjeVatDAO ewidencjeVatDAO) {
        this.ewidencjeVatDAO = ewidencjeVatDAO;
    }

    public STREwidencja getsTREwidencja() {
        return sTREwidencja;
    }

    public void setsTREwidencja(STREwidencja sTREwidencja) {
        this.sTREwidencja = sTREwidencja;
    }
    
    
}
