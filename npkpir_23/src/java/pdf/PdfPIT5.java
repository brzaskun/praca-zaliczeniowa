/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.PodatnikDAO;
import data.Data;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Uz;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import msg.Msg;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfPIT5 {
    
    public static void drukuj(Pitpoz selected, WpisView wpisView, PodatnikDAO podatnikDAO) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje PK dokumentu "+selected.toString());
        Document document = new Document();
        PdfWriter.getInstance(document, Plik.plikR("pit5" + wpisView.getPodatnikWpisu() + ".pdf")).setInitialLeading(16);
        document.addTitle("PIT5");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk PIT5");
        document.addKeywords("PIT5, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
            //Rectangle rect = new Rectangle(0, 832, 136, 800);
            //rect.setBackgroundColor(BaseColor.RED);
            //document.add(rect);
            document.add(new Chunk("Biuro Rachunkowe Taxman"));
            document.add(Chunk.NEWLINE);
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica,12);  
            Font fontM = new Font(helvetica,10);
            Font fontS = new Font(helvetica,6);
            document.add(Chunk.NEWLINE);
            Date date = Calendar.getInstance().getTime();
            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatt.format(date);
            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()),font));
            miziu.setAlignment(Element.ALIGN_RIGHT);
            miziu.setLeading(50);
            document.add(miziu);
            document.add(Chunk.NEWLINE);
            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie PIT-5",font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("okres rozliczeniony "+selected.getPkpirM()+"/"+selected.getPkpirR(),fontM));
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("Firma: "+wpisView.getPrintNazwa(),fontM));
            document.add(miziu1);
            Podatnik pod = wpisView.getPodatnikObiekt();
            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
            document.add(miziu1);
            PdfPTable table0 = new PdfPTable(2);
            PdfPTable table = new PdfPTable(2);
            PdfPTable table1 = new PdfPTable(2);
            PdfPTable table2 = new PdfPTable(2);
            table0.setWidths(new int[]{5, 5,});
            table.setWidths(new int[]{5, 5,});
            table1.setWidths(new int[]{5, 5,});
            table2.setWidths(new int[]{5, 5,});
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(2);
                formatter.setMinimumFractionDigits(2);
                formatter.setGroupingUsed(true);
                table0.addCell(ustawfrazeAlign("imię i nazwisko podatnika","center",10));
                table0.addCell(ustawfrazeAlign(selected.getUdzialowiec(),"right",10));
                table.addCell(ustawfrazeAlign("przychody narastająco","center",10));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getPrzychody())),"right",10));
                table.addCell(ustawfrazeAlign("koszty narastająco","center",10));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getKoszty())),"right",10));
                if (selected.getRemanent()!=null) {
                    table.addCell(ustawfrazeAlign("różnica remanentów","center",10));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getRemanent())),"right",10));
                }
                table.addCell(ustawfrazeAlign("udział","center",10));
                table.addCell(ustawfrazeAlign(selected.getUdzial()+"%","right",10));
                table.addCell(ustawfrazeAlign("","center",10));
                table.addCell(ustawfrazeAlign("","center",10));
                table.addCell(ustawfrazeAlign("przychody narast./udział","center",10));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getPrzychodyudzial())),"right",10));
                table.addCell(ustawfrazeAlign("koszty narast./udział","center",10));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getKosztyudzial())),"right",10));
                table.addCell(ustawfrazeAlign("wynik od początku roku","center",10));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getWynik())),"right",10));
                table.addCell(ustawfrazeAlign("","center",10));
                table.addCell(ustawfrazeAlign("","center",10));
                table1.addCell(ustawfrazeAlign("ZUS 51","center",10));
                if (selected.getZus51() != null) {
                    table1.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getZus51())),"right",10));
                } else {
                    table1.addCell(ustawfrazeAlign(String.valueOf(formatter.format(0)),"right",10));
                }
                table1.addCell(ustawfrazeAlign("strata z lat ub.","center",10));
                table1.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getStrata())),"right",10));
                table1.addCell(ustawfrazeAlign("podstawa op.","center",10));
                table1.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getPodstawa())),"right",10));
                table1.addCell(ustawfrazeAlign("podatek brutto od pocz.roku","center",10));
                table1.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getPodatek())),"right",10));
                table2.addCell(ustawfrazeAlign("ZUS 52","center",10));
                if (selected.getZus52() != null) {
                    table2.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getZus52())),"right",10));
                } else {
                    table2.addCell(ustawfrazeAlign(String.valueOf(formatter.format(0)),"right",10));
                }
                table2.addCell(ustawfrazeAlign("strata z lat ub.","center",10));
                table2.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getStrata())),"right",10));
                table2.addCell(ustawfrazeAlign("podatek należny od pocz.roku","center",10));
                table2.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getPododpoczrok())),"right",10));
                table2.addCell(ustawfrazeAlign("zaliczki za pop.mce","center",10));
                table2.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNalzalodpoczrok())),"right",10));
                table2.addCell(ustawfrazeAlign("do zapłaty","center",10));
        table2.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getDozaplaty())),"right",10));
        table2.addCell(ustawfrazeAlign("termin płatności","center",10));
        table2.addCell(ustawfrazeAlign(selected.getTerminwplaty(),"center",10));
            document.add(Chunk.NEWLINE);
            document.add(table0);
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            document.add(table1);
            document.add(Chunk.NEWLINE);
            document.add(table2);
            document.add(Chunk.NEWLINE);
            Uz uz = wpisView.getWprowadzil();
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        Msg.msg("i", "Wydrukowano PIT5", "form:messages");
    }
    
   
    
    
      
}
