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
import data.Data;
import embeddable.EVatwpisSuma;
import entity.Podatnik;
import entity.Uz;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import plik.Plik;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */

public class PdfVATsuma {

    public static void drukuj(HashMap<String, EVatwpisSuma> sumaewidencji, WpisView wpisView, boolean bezniemcy0niemcy1) throws FileNotFoundException, DocumentException, IOException  {
        Document document = new Document();
        PdfWriter.getInstance(document, Plik.plikR("vatsuma" + wpisView.getPodatnikObiekt().getNip() + ".pdf")).setInitialLeading(16);
        document.addTitle("Zestawienie sum z ewidencji VAT");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z ewidencji VAT");
        document.addKeywords("VAT, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        String symbolwaluty = bezniemcy0niemcy1?"EUR":"PLN";
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
            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie ewidencji VAT ",font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("okres rozliczeniowy "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu(),fontM));
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("Firma: "+wpisView.getPodatnikObiekt().getNazwapelnaPDF(),fontM));
            document.add(miziu1);
            Podatnik pod = wpisView.getPodatnikObiekt();
            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
            document.add(miziu1);
            PdfPTable tableSprzedaz = new PdfPTable(5);
            tableSprzedaz.setWidths(new int[]{1, 5, 2, 2, 2});
            tableSprzedaz.setWidthPercentage(95);
            PdfPTable tableZakup = new PdfPTable(5);
            tableZakup.setWidths(new int[]{1, 5, 2, 2, 2});
            tableZakup.setWidthPercentage(95);
            List<EVatwpisSuma> sumaVatSprzedaz = Collections.synchronizedList(new ArrayList<>());
            List<EVatwpisSuma> sumaVatZakup = Collections.synchronizedList(new ArrayList<>());
            for (EVatwpisSuma ew : sumaewidencji.values()) {
                if (ew.getEwidencja().isNiemcy()==bezniemcy0niemcy1) {
                    String typeewidencji = ew.getEwidencja().getTypewidencji();
                    switch (typeewidencji) {
                        case "s" : sumaVatSprzedaz.add(ew);
                            break;
                        case "z" : sumaVatZakup.add(ew);
                            break;
                        case "sz": sumaVatSprzedaz.add(ew);
    //                               sumaVatZakup.add(ew);
                            break;
                    }
                }
            }
            //tu robimy wykaz ewidencji sprzedazy
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("zestawienie ewidencji sprzedaży",fontM));
            document.add(miziu1);
            tableSprzedaz.addCell(ustawfrazeAlign("lp","center",10));
            tableSprzedaz.addCell(ustawfrazeAlign("ewidencja","center",10));
            tableSprzedaz.addCell(ustawfrazeAlign("netto","center",10));
            tableSprzedaz.addCell(ustawfrazeAlign("vat","center",10));
            tableSprzedaz.addCell(ustawfrazeAlign("brutto","center",10));
            tableSprzedaz.setHeaderRows(1);
            int i = 1;
            double nettosuma = 0.0;
            double vatsuma = 0.0;
            for(EVatwpisSuma p : sumaVatSprzedaz){
                nettosuma += p.getNetto().doubleValue();
                vatsuma += p.getVat().doubleValue();
                tableSprzedaz.addCell(ustawfrazeAlign(String.valueOf(i),"center",10));
                tableSprzedaz.addCell(ustawfrazeAlign(p.getEwidencja().getNazwa(),"left",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(p.getNetto().doubleValue(), symbolwaluty),"right",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(p.getVat().doubleValue(), symbolwaluty),"right",10));
                try {
                    BigDecimal brutto = p.getVat().add(p.getNetto());
                    tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(brutto.doubleValue(), symbolwaluty),"right",10));
                } catch (Exception e){
                    tableSprzedaz.addCell(ustawfrazeAlign("","right",10));
                }
                i++;
                }
            if (tableSprzedaz.size() > 1) {
                tableSprzedaz.addCell(ustawfrazeAlign("","center",10));
                tableSprzedaz.addCell(ustawfrazeAlign("podsumowanie","left",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(nettosuma, symbolwaluty),"right",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(vatsuma, symbolwaluty),"right",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(Z.z(nettosuma+vatsuma), symbolwaluty),"right",10));
            }
            document.add(Chunk.NEWLINE);
            document.add(tableSprzedaz);
            document.add(Chunk.NEWLINE);
            //tu robimy wykaz ewidencji zakupu
            miziu1 = new Paragraph(new Phrase("zestawienie ewidencji zakupu",fontM));
            document.add(miziu1);
            tableZakup.addCell(ustawfrazeAlign("lp","center",10));
            tableZakup.addCell(ustawfrazeAlign("ewidencja","center",10));
            tableZakup.addCell(ustawfrazeAlign("netto","center",10));
            tableZakup.addCell(ustawfrazeAlign("vat","center",10));
            tableZakup.addCell(ustawfrazeAlign("brutto","center",10));
            tableZakup.setHeaderRows(1);
            int j = 1;
            double nettosuma1 = 0.0;
            double vatsuma1 = 0.0;
            for(EVatwpisSuma p : sumaVatZakup){
                nettosuma1 += p.getNetto().doubleValue();
                vatsuma1 += p.getVat().doubleValue();
                tableZakup.addCell(ustawfrazeAlign(String.valueOf(j),"center",10));
                tableZakup.addCell(ustawfrazeAlign(p.getEwidencja().getNazwa(),"left",10));
                 tableZakup.addCell(ustawfrazeAlign(format.F.curr(p.getNetto().doubleValue(), symbolwaluty),"right",10));
                tableZakup.addCell(ustawfrazeAlign(format.F.curr(p.getVat().doubleValue(), symbolwaluty),"right",10));
                try {
                    BigDecimal brutto = p.getVat().add(p.getNetto());
                    tableZakup.addCell(ustawfrazeAlign(format.F.curr(brutto.doubleValue(), symbolwaluty),"right",10));
                } catch (Exception e){
                    tableZakup.addCell(ustawfrazeAlign("","right",10));
                }
                j++;
                }
            if (tableZakup.size()>1) {
                tableZakup.addCell(ustawfrazeAlign("","center",10));
                tableZakup.addCell(ustawfrazeAlign("podsumowanie","left",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(nettosuma, symbolwaluty),"right",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(vatsuma, symbolwaluty),"right",10));
                tableSprzedaz.addCell(ustawfrazeAlign(format.F.curr(Z.z(nettosuma+vatsuma), symbolwaluty),"right",10));
            }
            document.add(Chunk.NEWLINE);
            document.add(tableZakup);
            document.add(Chunk.NEWLINE);
            Uz uz = wpisView.getUzer();
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        //Msg.msg("i", "Wydrukowano sume ewidencji VAT");
    }

       
    
}
