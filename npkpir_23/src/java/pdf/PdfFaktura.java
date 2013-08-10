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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.FakturaDAO;
import embeddable.Pozycjenafakturzebazadanych;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Faktura;
import entity.Podatnik;
import entity.Pozycjenafakturze;
import entity.Uz;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import msg.Msg;
import session.SessionFacade;
import view.FakturaView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfFaktura extends Pdf implements Serializable {
    
    public void drukuj() throws DocumentException, FileNotFoundException, IOException {
        Faktura selected = FakturaView.getGosciwybralS().get(0);
        System.out.println("Drukuje Fakture "+selected.toString());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + wpisView.getPodatnikWpisu() + ".pdf")).setInitialLeading(16);
        document.addTitle("Faktura");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk faktury w formacie pdf");
        document.addKeywords("Faktura, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
            //Rectangle rect = new Rectangle(0, 832, 136, 800);
            //rect.setBackgroundColor(BaseColor.RED);
            //document.add(rect);
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font fontXS = new Font(helvetica,4);
            Font fontS = new Font(helvetica,6);
            Font font = new Font(helvetica,8);  
            Font fontL = new Font(helvetica,10);
            Font fontXL = new Font(helvetica,12);
            document.add(dodpar("Biuro Rachunkowe Taxman - program księgowy online", fontXS, "l", 0, 10));
            document.add(dodpar("Data wystawienia faktury: "+selected.getDatawystawienia(), font, "l", 370, 20));
            document.add(dodpar("Miejsce wystawienia faktury: "+selected.getMiejscewystawienia(), font, "l", 370, 10));
            document.add(dodpar("Faktura nr "+selected.getFakturaPK().getNumerkolejny(), fontL, "c", 0, 40));
            //wystawca
            document.add(dodpar("Sprzedawca: ", fontL, "l", 0, 40));
            document.add(dodpar(selected.getWystawca().getNazwapelna(), font, "l", 0, 20));
            String adres = selected.getWystawca().getKodpocztowy()+" "+selected.getWystawca().getMiejscowosc()+" "+selected.getWystawca().getUlica()+" "+selected.getWystawca().getNrdomu();
            document.add(dodpar(adres, font, "l", 0, 20));
            document.add(dodpar("NIP: "+selected.getWystawca().getNip(), font, "l", 0, 20));
            document.add(dodpar("Nabywca: ", fontL, "l", 0, 30));
            document.add(dodpar(selected.getKontrahent().getNpelna(), font, "l", 0, 20));
            adres = selected.getKontrahent().getKodpocztowy()+" "+selected.getKontrahent().getMiejscowosc()+" "+selected.getKontrahent().getUlica()+" "+selected.getKontrahent().getDom();
            document.add(dodpar(adres, font, "l", 0, 20));
            document.add(dodpar("NIP: "+selected.getKontrahent().getNip(), font, "l", 0, 20));
            document.add(dodpar("Sposób zapłaty: "+selected.getSposobzaplaty(), font, "l", 0, 30));
            document.add(dodpar("Termin płatności: "+selected.getTerminzaplaty(), font, "l", 100, 0));
            document.add(dodpar(" ", font, "l", 0, 50));
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(2);
                formatter.setMinimumFractionDigits(2);
                formatter.setGroupingUsed(true);
            PdfPTable table = new PdfPTable(11);
            //table.setTotalWidth(1090);
            //table.setWidthPercentage(new float[]{ 144, 72, 72 }, rect);
            Rectangle rect = new Rectangle(523, 200);
            table.setWidthPercentage(new float[]{ 20, 100, 40, 40, 40, 50, 60, 50, 60, 60, 30},rect);
            table.addCell(ustawfrazebez("lp","center",8));
            table.addCell(ustawfrazebez("opis","center",8));
            table.addCell(ustawfrazebez("PKWiU","center",8));
            table.addCell(ustawfrazebez("ilość","center",8));
            table.addCell(ustawfrazebez("jedn.m.","center",8));
            table.addCell(ustawfrazebez("cena netto","center",8));
            table.addCell(ustawfrazebez("wartość netto","center",8));
            table.addCell(ustawfrazebez("stawka vat","center",8));
            table.addCell(ustawfrazebez("kwota vat","center",8));
            table.addCell(ustawfrazebez("wartość brutto","center",8));
            table.addCell(ustawfrazebez("uwagi","center",8));
            table.setHeaderRows(1);
            table.addCell(ustawfrazebez("1","center",8));
            Pozycjenafakturzebazadanych pozycje = selected.getPozycjenafakturze();
            table.addCell(ustawfrazebez(pozycje.getNazwa(),"left",8));
            table.addCell(ustawfrazebez(pozycje.getPKWiU(),"center",8));
            table.addCell(ustawfrazebez(String.valueOf(pozycje.getIlosc()),"center",8));
            table.addCell(ustawfrazebez(pozycje.getJednostka(),"center",8));
            table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getCena())),"center",8));
            table.addCell(ustawfrazebez("200","center",8));
            table.addCell(ustawfrazebez(pozycje.getPodatek(),"center",8));
            table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getPodatekkwota())),"center",8));
            table.addCell(ustawfrazebez("brutto","center",8));
            table.addCell(ustawfrazebez("uwagi","center",8));
            document.add(table);
            document.add(dodpar("Do zapłaty: 100zł", font, "l", 0, 50));
            document.add(dodpar("Słownie: sto złotych", font, "l", 0, 20));
            document.add(dodpar("Nr konta bankowego: "+selected.getNrkontabankowego(), font, "l", 0, 20));
            document.add(dodpar(selected.getPodpis(), font, "l", 10, 50));
            document.add(dodpar("..........................................", font, "l", 0, 20));
            document.add(dodpar("wystawca faktury", font, "l", 15, 20));
            
//            document.add(Chunk.NEWLINE);
//            Date date = Calendar.getInstance().getTime();
//            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
//            String today = formatt.format(date);
//            System.out.println("Today : " + today);
//            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+today,font));
//            miziu.setAlignment(Element.ALIGN_RIGHT);
//            miziu.setLeading(50);
//            document.add(miziu);
//            document.add(new Chunk().NEWLINE);
//            Paragraph miziu1;
//            if(selected.getTypdokumentu().equals("PK")||selected.getTypdokumentu().equals("OT")||selected.getTypdokumentu().equals("IN")||selected.getTypdokumentu().equals("ZUS")){
//                miziu1 = new Paragraph(new Phrase("Polecenie księgowania "+selected.getNrWlDk(),font));
//            } else {
//                miziu1 = new Paragraph(new Phrase("Faktura VAT "+selected.getNrWlDk(),font));
//            }
//            miziu1.setAlignment(Element.ALIGN_CENTER);
//            document.add(miziu1);
//            document.add(new Chunk().NEWLINE);
//            miziu1 = new Paragraph(new Phrase("okres rozliczeniony "+selected.getPkpirM()+"/"+selected.getPkpirR(),fontM));
//            document.add(miziu1);
//            document.add(new Chunk().NEWLINE);
//            miziu1 = new Paragraph(new Phrase("Firma: "+selected.getPodatnik(),fontM));
//            document.add(miziu1);
//            Podatnik pod = podatnikDAO.find(selected.getPodatnik());
//            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
//            document.add(miziu1);
//            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
//            document.add(miziu1);
//            if(!selected.getTypdokumentu().equals("PK")&&!selected.getTypdokumentu().equals("OT")&&!selected.getTypdokumentu().equals("IN")&&!selected.getTypdokumentu().equals("ZUS")&&!selected.getTypdokumentu().equals("AMO")){
//                document.add(Chunk.NEWLINE);
//                miziu1 = new Paragraph(new Phrase("Kontrahent: "+selected.getKontr().getNpelna(),fontM));
//                document.add(miziu1);
//                miziu1 = new Paragraph(new Phrase("adres: "+selected.getKontr().getMiejscowosc()+" "+selected.getKontr().getUlica()+" "+selected.getKontr().getDom(),fontM));
//                document.add(miziu1);
//                miziu1 = new Paragraph(new Phrase("NIP: "+selected.getKontr().getNip(),fontM));
//                document.add(miziu1);
//            }
//            PdfPTable table = new PdfPTable(6);
//            table.setWidths(new int[]{1, 5, 2, 2, 2, 2});
//            NumberFormat formatter = NumberFormat.getCurrencyInstance();
//                formatter.setMaximumFractionDigits(2);
//                formatter.setMinimumFractionDigits(2);
//                formatter.setGroupingUsed(true);
//            try {
//                table.addCell(ustawfrazebez("lp","center",9));
//                table.addCell(ustawfrazebez("opis","center",9));
//                table.addCell(ustawfrazebez("netto","center",9));
//                table.addCell(ustawfrazebez("vat","center",9));
//                table.addCell(ustawfrazebez("brutto","center",9));
//                table.addCell(ustawfrazebez("uwagi","center",9));
//                table.setHeaderRows(1);
//                
//                table.addCell(ustawfrazebez(String.valueOf(selected.getNrWpkpir()),"center",9));
//                table.addCell(ustawfrazebez(selected.getOpis(),"left",9));
//                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNetto())),"right",9));
//                try {
//                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getBrutto()-selected.getNetto())),"right",9));
//                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getBrutto())),"right",9));
//                } catch (Exception e){
//                    table.addCell(ustawfrazebez("0.00","right",9));
//                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNetto())),"right",9));
//                }
//                table.addCell(ustawfrazebez(selected.getUwagi(),"center",9));
//               } catch (DocumentException | IOException e){
//                
//            }
//            document.add(Chunk.NEWLINE);
//            document.add(table);
//            document.add(Chunk.NEWLINE);
//            if(selected.getOpis().equals("umorzenie za miesiac")){
//                document.add(new Paragraph("Zawartość dokumentu amortyzacji",fontM));
//                document.add(Chunk.NEWLINE);
//                dodajamo(document,formatter);
//                document.add(Chunk.NEWLINE);
//            }
//            Uz uz = uzDAO.find(selected.getWprowadzil());
//            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
//            document.add(new Paragraph("___________________________",fontM));
//            document.add(new Paragraph("sporządził",fontM));
        document.close();
        Msg.msg("i", "Wydrukowano Fakture", "form:messages");
    }
    
    private void dodajamo(Document document, NumberFormat formatter) throws DocumentException, IOException{
        Amodok odpis = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        List<Umorzenie> umorzenia = odpis.getUmorzenia();
        System.out.println("Drukuje " +odpis.toString());
        PdfPTable table = new PdfPTable(4);
        table.setWidths(new int[]{1, 6, 2, 2});
        table.addCell(ustawfrazebez("lp","center",9));
        table.addCell(ustawfrazebez("nazwa środka trwałego","center",9));
        table.addCell(ustawfrazebez("nr umorzenia","center",9));
        table.addCell(ustawfrazebez("kwota umorzenia","center",9));
        table.setHeaderRows(1);
        int i = 1;
        for(Umorzenie p : umorzenia){
            table.addCell(ustawfrazebez(String.valueOf(i++),"center",9));
            table.addCell(ustawfrazebez(p.getNazwaSrodka(),"center",9));
            table.addCell(ustawfrazebez(String.valueOf(p.getNrUmorzenia()),"center",9));
            table.addCell(ustawfrazebez(formatter.format(p.getKwota()),"center",9));
        }
        document.add(table);
    }
    
    
//    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException{
//        
//        System.out.println("Drukuje Fakture "+selected.toString());
//        Document document = new Document();
//        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + "Podatnik" + ".pdf")).setInitialLeading(16);
//        document.addTitle("Faktura");
//        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//        document.addSubject("Wydruk faktury w formacie pdf");
//        document.addKeywords("Faktura, PDF");
//        document.addCreator("Grzegorz Grzelczyk");
//        document.open();
//            //Rectangle rect = new Rectangle(0, 832, 136, 800);
//            //rect.setBackgroundColor(BaseColor.RED);
//            //document.add(rect);
//        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//            Font fontXS = new Font(helvetica,4);
//            Font fontS = new Font(helvetica,6);
//            Font font = new Font(helvetica,8);  
//            Font fontL = new Font(helvetica,10);
//            Font fontXL = new Font(helvetica,12);
//            document.add(dodpar("Biuro Rachunkowe Taxman - program księgowy online", fontXS, "l", 0, 10));
//            document.add(dodpar("Data wystawienia faktury: "+selected.getDatawystawienia(), font, "l", 370, 20));
//            document.add(dodpar("Miejsce wystawienia faktury: "+selected.getMiejscewystawienia(), font, "l", 370, 10));
//            document.add(dodpar("Faktura nr "+selected.getFakturaPK().getNumerkolejny(), fontL, "c", 0, 40));
//            //wystawca
//            document.add(dodpar("Sprzedawca: ", fontL, "l", 0, 40));
//            document.add(dodpar(selected.getWystawca().getNazwapelna(), font, "l", 0, 20));
//            String adres = selected.getWystawca().getKodpocztowy()+" "+selected.getWystawca().getMiejscowosc()+" "+selected.getWystawca().getUlica()+" "+selected.getWystawca().getNrdomu();
//            document.add(dodpar(adres, font, "l", 0, 20));
//            document.add(dodpar("NIP: "+selected.getWystawca().getNip(), font, "l", 0, 20));
//            document.add(dodpar("Nabywca: ", fontL, "l", 0, 30));
//            document.add(dodpar(selected.getKontrahent().getNpelna(), font, "l", 0, 20));
//            adres = selected.getKontrahent().getKodpocztowy()+" "+selected.getKontrahent().getMiejscowosc()+" "+selected.getKontrahent().getUlica()+" "+selected.getKontrahent().getDom();
//            document.add(dodpar(adres, font, "l", 0, 20));
//            document.add(dodpar("NIP: "+selected.getKontrahent().getNip(), font, "l", 0, 20));
//            document.add(dodpar("Sposób zapłaty: "+selected.getSposobzaplaty(), font, "l", 0, 30));
//            document.add(dodpar("Termin płatności: "+selected.getTerminzaplaty(), font, "l", 100, 0));
//            document.add(dodpar(" ", font, "l", 0, 50));
//            NumberFormat formatter = NumberFormat.getCurrencyInstance();
//                formatter.setMaximumFractionDigits(2);
//                formatter.setMinimumFractionDigits(2);
//                formatter.setGroupingUsed(true);
//            PdfPTable table = new PdfPTable(11);
//            //table.setTotalWidth(1090);
//            //table.setWidthPercentage(new float[]{ 144, 72, 72 }, rect);
//            Rectangle rect = new Rectangle(523, 200);
//            table.setWidthPercentage(new float[]{ 20, 100, 40, 40, 40, 50, 60, 50, 60, 60, 30},rect);
//            table.addCell(ustawfrazebez("lp","center",8));
//            table.addCell(ustawfrazebez("opis","center",8));
//            table.addCell(ustawfrazebez("PKWiU","center",8));
//            table.addCell(ustawfrazebez("ilość","center",8));
//            table.addCell(ustawfrazebez("jedn.m.","center",8));
//            table.addCell(ustawfrazebez("cena netto","center",8));
//            table.addCell(ustawfrazebez("wartość netto","center",8));
//            table.addCell(ustawfrazebez("stawka vat","center",8));
//            table.addCell(ustawfrazebez("kwota vat","center",8));
//            table.addCell(ustawfrazebez("wartość brutto","center",8));
//            table.addCell(ustawfrazebez("uwagi","center",8));
//            table.setHeaderRows(1);
//            table.addCell(ustawfrazebez("1","center",8));
//            Pozycjenafakturzebazadanych pozycje = selected.getPozycjenafakturze();
//            table.addCell(ustawfrazebez(pozycje.getNazwa(),"left",8));
//            table.addCell(ustawfrazebez(pozycje.getPKWiU(),"center",8));
//            table.addCell(ustawfrazebez(String.valueOf(pozycje.getIlosc()),"center",8));
//            table.addCell(ustawfrazebez(pozycje.getJednostka(),"center",8));
//            table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getCena())),"center",8));
//            table.addCell(ustawfrazebez("200","center",8));
//            table.addCell(ustawfrazebez(pozycje.getPodatek(),"center",8));
//            table.addCell(ustawfrazebez(String.valueOf(formatter.format(pozycje.getPodatekkwota())),"center",8));
//            table.addCell(ustawfrazebez("brutto","center",8));
//            table.addCell(ustawfrazebez("uwagi","center",8));
//            document.add(table);
//            document.add(dodpar("Do zapłaty: 100zł", font, "l", 0, 50));
//            document.add(dodpar("Słownie: sto złotych", font, "l", 0, 20));
//            document.add(dodpar("Nr konta bankowego: "+selected.getNrkontabankowego(), font, "l", 0, 20));
//            document.add(dodpar(selected.getPodpis(), font, "l", 10, 50));
//            document.add(dodpar("..........................................", font, "l", 0, 20));
//            document.add(dodpar("wystawca faktury", font, "l", 15, 20));
//            document.close();
//    }
}
