/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pdf;

import static beansPdf.PdfFont.formatujWaluta;
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
import comparator.VatKorektaDokcomparator;
import dao.PodatnikDAO;
import data.Data;
import embeddable.VatKorektaDok;
import entity.Podatnik;
import entity.Uz;
import entity.VATDeklaracjaKorektaDok;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import plik.Plik;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfVATKorektaReczna {

    
    public static void drukuj(VATDeklaracjaKorektaDok selected, WpisView wpisView, PodatnikDAO podatnikDAO){
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, Plik.plikR("dokumentyVATKorektaReczna" + wpisView.getPodatnikWpisu() + ".pdf")).setInitialLeading(16);
            document.addTitle("Wykaz dokumentów dla korekty VAT");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk listy dokumentów korekty");
            document.addKeywords("VAT korekta, PDF");
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
            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie dokumentów dla korekty deklaracji vat",font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase(selected.getDeklaracjaPierwotna().getIdentyfikator(),font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("okres rozliczeniony "+selected.getDeklaracjaKorekta().getMiesiac()+"/"+selected.getDeklaracjaKorekta().getRok(),fontM));
            document.add(miziu1);
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("Firma: "+selected.getDeklaracjaKorekta().getPodatnik(),fontM));
            document.add(miziu1);
            Podatnik pod = podatnikDAO.find(selected.getDeklaracjaKorekta().getPodatnik());
            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
            document.add(miziu1);
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{2, 4, 4, 4, 4, 5});
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            table.addCell(ustawfrazeAlign("lp","center",10));
            table.addCell(ustawfrazeAlign("nip","center",10));
            table.addCell(ustawfrazeAlign("numer","center",10));
            table.addCell(ustawfrazeAlign("netto","center",10));
            table.addCell(ustawfrazeAlign("vat","center",10));
            table.addCell(ustawfrazeAlign("brutto","center",10));
            table.setHeaderRows(2);
            table.setFooterRows(1);
            List<VatKorektaDok> lista = selected.getListadokumentowDoKorekty();
            Collections.sort(lista, new VatKorektaDokcomparator());
            for (VatKorektaDok rs : lista) {
                table.addCell(ustawfrazeAlign(String.valueOf(rs.getId()), "center",10));
                table.addCell(ustawfrazeAlign(rs.getNipKontrahenta(), "left",10));
                table.addCell(ustawfrazeAlign(rs.getNrwłasny(), "left",10));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right",10));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getVat()), "right",10));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getBrutto()), "right",10));
            }
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            Uz uz = wpisView.getWprowadzil();
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
            document.close();
            RequestContext.getCurrentInstance().execute("wydrukvatListaVATKorekta('"+selected.getDeklaracjaKorekta().getPodatnik()+"');");
            Msg.msg("i", "Wydrukowano liste dokumentow");
            

        } catch (FileNotFoundException ex){
        Logger.getLogger(PdfVATKorektaReczna.class.getName()).log(Level.SEVERE, null, ex);

        } catch (DocumentException ex) {
            Logger.getLogger(PdfVATKorektaReczna.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfVATKorektaReczna.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    
    
}
