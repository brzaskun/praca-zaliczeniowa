/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.Pozycjenafakturzecomparator;
import dao.PozycjenafakturzeDAO;
import embeddable.Pozycjenafakturzebazadanych;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Faktura;
import entity.Pozycjenafakturze;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import static pdf.Pdf.dodpar;
import static pdf.Pdf.ustawfraze;
import static pdf.Pdf.ustawfrazebez;
import view.FakturaView;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PdfFaktura extends Pdf implements Serializable {
    @Inject private PozycjenafakturzeDAO pozycjeDAO;
    
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
        List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
            Collections.sort(lista, new Pozycjenafakturzecomparator());
            Map<String, Integer> wymiary = new HashMap<>();
            int poprzednie = 0;
            for(Pozycjenafakturze p : lista){
                int wymiargora = (int) (p.getGora()/3);
                if (poprzednie == 0 ){
                    wymiary.put(p.getPozycjenafakturzePK().getNazwa(), wymiargora);
                    poprzednie = wymiargora;
                } else {
                    wymiary.put(p.getPozycjenafakturzePK().getNazwa(), wymiargora-poprzednie);
                    poprzednie = wymiargora;
                }
            }
            document.add(dodpar("Biuro Rachunkowe Taxman - program księgowy online", fontXS, "l", 0, 10));
            Pozycjenafakturze pobrane = new Pozycjenafakturze();
            String adres = "";
            float dzielnik = 2;
            for (Pozycjenafakturze p : lista){
                switch (p.getPozycjenafakturzePK().getNazwa()){
                    case "form:akordeon:data" :
                        //Dane do moudlu data
                        pobrane = zwrocpozycje(lista, "data");
                        document.add(dodpar(selected.getMiejscewystawienia()+ " dnia: "+selected.getDatawystawienia(), font, "l", (int) (pobrane.getLewy()/dzielnik), wymiary.get("form:akordeon:data")));
                        break;
                    case "form:akordeon:fakturanumer" :
                        //Dane do modulu fakturanumer
                        pobrane = zwrocpozycje(lista, "fakturanumer");
                        document.add(dodpar("Faktura nr "+selected.getFakturaPK().getNumerkolejny(), fontL, "l", (int) (pobrane.getLewy()/dzielnik), wymiary.get("form:akordeon:fakturanumer")));
                        break;
                    case "form:akordeon:wystawca" :
                        //Dane do modulu sprzedawca
                        pobrane = zwrocpozycje(lista, "wystawca");
                        document.add(dodpar("Sprzedawca: ", fontL, "l", (int) (pobrane.getLewy()/dzielnik), wymiary.get("form:akordeon:wystawca")));
                        document.add(dodpar(selected.getWystawca().getNazwapelna(), font, "l", (int) (pobrane.getLewy()/dzielnik), 20));
                        adres = selected.getWystawca().getKodpocztowy()+" "+selected.getWystawca().getMiejscowosc()+" "+selected.getWystawca().getUlica()+" "+selected.getWystawca().getNrdomu();
                        document.add(dodpar(adres, font, "l", (int) (pobrane.getLewy()/dzielnik), 20));
                        document.add(dodpar("NIP: "+selected.getWystawca().getNip(), font, "l",(int) (pobrane.getLewy()/dzielnik), 20));
                        break;
                     case "form:akordeon:odbiorca" :
                        //Dane do modulu odbiorca
                        pobrane = zwrocpozycje(lista, "odbiorca");
                        document.add(dodpar("Nabywca: ", fontL, "l", (int) (pobrane.getLewy()/dzielnik), wymiary.get("form:akordeon:odbiorca")));
                        document.add(dodpar(selected.getKontrahent().getNpelna(), font, "l", (int) (pobrane.getLewy()/dzielnik), 20));
                        adres = selected.getKontrahent().getKodpocztowy()+" "+selected.getKontrahent().getMiejscowosc()+" "+selected.getKontrahent().getUlica()+" "+selected.getKontrahent().getDom();
                        document.add(dodpar(adres, font, "l", (int) (pobrane.getLewy()/dzielnik), 20));
                        document.add(dodpar("NIP: "+selected.getKontrahent().getNip(), font, "l", (int) (pobrane.getLewy()/dzielnik), 20));
                        break;
                     case "form:akordeon:platnosc" :
                        //Dane do modulu platnosc
                        pobrane = zwrocpozycje(lista, "platnosc");
                        document.add(dodpar("Sposób zapłaty: "+selected.getSposobzaplaty(), font, "l", (int) (pobrane.getLewy()/dzielnik), wymiary.get("form:akordeon:platnosc")));
                        document.add(dodpar("Termin płatności: "+selected.getTerminzaplaty(), font, "l", (int) (pobrane.getLewy()/dzielnik) + 90, 0));
                        document.add(dodpar("Nr konta bankowego: "+selected.getNrkontabankowego(), font, "l", (int) (pobrane.getLewy()/dzielnik), 20));
                        break;
                }
            }
            
            
            
            
            
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
            
            document.add(dodpar(selected.getPodpis(), font, "l", 10, 50));
            document.add(dodpar("..........................................", font, "l", 0, 20));
            document.add(dodpar("wystawca faktury", font, "l", 15, 20));
            
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
    
    private Pozycjenafakturze zwrocpozycje(List<Pozycjenafakturze> lista, String data) {
        for (Pozycjenafakturze p : lista){
            if(p.getPozycjenafakturzePK().getNazwa().contains(data)){
                return p;
            }
        }
        return null;
    }
    
    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException{
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + "Podatnik" + ".pdf")).setInitialLeading(16);
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
            //miary pdf szerokosc dla jednej litery od 0 do 540 wysokosc kierunek w dol od 0 do 800
            //miary javascript dla jednej litery poziomo od lewej od 78 do 1038 = 960; wysokosc kierunek w dol od 53 do 1607 = 1554;
            // a wiec dane z tabeli mnozymy przez 2 :) ;
            document.add(dodpar("Biuro Rachunkowe Taxman - program księgowy online", fontXS, "l", 0, 10));
            document.add(dodpar("D", font, "l", 540, 800));
            document.add(dodpar("Miejsce wystawienia faktury: ", font, "l", 370, 10));
            document.add(dodpar("Faktura nr ", fontL, "c", 0, 40));
            //wystawca
            document.add(dodpar("Sprzedawca: ", fontL, "l", 0, 40));
            document.add(dodpar("Jestem Sprzedawca", font, "l", 0, 20));
            String adres = "To moj adres";
            document.add(dodpar(adres, font, "l", 0, 20));
            document.add(dodpar("NIP: ", font, "l", 0, 20));
            document.add(dodpar("Nabywca: ", fontL, "l", 0, 30));
            document.add(dodpar("Jestem anbywca", font, "l", 0, 20));
            adres = "to jest moj adres";
            document.add(dodpar(adres, font, "l", 0, 20));
            document.add(dodpar("NIP: ", font, "l", 0, 20));
            document.add(dodpar("Sposób zapłaty: gotowka", font, "l", 0, 30));
            document.add(dodpar("Termin płatności: dzis", font, "l", 100, 0));
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
            //Pozycjenafakturzebazadanych pozycje = selected.getPozycjenafakturze();
            table.addCell(ustawfrazebez("nazwa","left",8));
            table.addCell(ustawfrazebez("pkwiu","center",8));
            table.addCell(ustawfrazebez("123","center",8));
            table.addCell(ustawfrazebez("kg","center",8));
            table.addCell(ustawfrazebez("100","center",8));
            table.addCell(ustawfrazebez("200","center",8));
            table.addCell(ustawfrazebez("23%","center",8));
            table.addCell(ustawfrazebez("1200","center",8));
            table.addCell(ustawfrazebez("brutto","center",8));
            table.addCell(ustawfrazebez("uwagi","center",8));
            //podsumowanie
            table.addCell(ustawfraze("Razem", 6, 0));
            table.addCell(ustawfrazebez("netto","center",8));
            table.addCell(ustawfrazebez("stvat","center",8));
            table.addCell(ustawfrazebez("vatwar","center",8));
            table.addCell(ustawfrazebez("brutto","center",8));
            table.addCell(ustawfrazebez(" ","center",8));
            
            document.add(table);
            document.add(dodpar("Do zapłaty: 100zł", font, "l", 0, 50));
            document.add(dodpar("Słownie: sto złotych", font, "l", 0, 20));
            document.add(dodpar("Nr konta bankowego: 12121", font, "l", 0, 20));
            document.add(dodpar("podpis", font, "l", 10, 50));
            document.add(dodpar("..........................................", font, "l", 0, 20));
            document.add(dodpar("wystawca faktury", font, "l", 15, 20));
            document.close();
    }

    
}
