/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.lowagie.tools.Executable;
import comparator.Pozycjenafakturzecomparator;
import dao.FakturadodelementyDAO;
import dao.PozycjenafakturzeDAO;
import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Faktura;
import entity.Fakturadodelementy;
import entity.Fakturywystokresowe;
import entity.Pozycjenafakturze;
import java.io.File;
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
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdf.PdfVAT7.absText;
import slownie.Slownie;
import view.WpisView;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PdfFaktura extends Pdf implements Serializable {

    public static void main(String[] args) throws FileNotFoundException, DocumentException, IOException {
    }
    @Inject
    private PozycjenafakturzeDAO pozycjeDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;

    public void drukujmail(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Faktura selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                drukujcd(selected, fdod, i, "mail", wpisView);
                i++;
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }

    public void drukuj(Faktura selected, int row, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        try {
            String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + String.valueOf(row) + wpisView.getPodatnikWpisu() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected, fdod, row, "druk", wpisView);

        } catch (DocumentException | IOException e) {
            Msg.msg("e", "Błąd - nie wybrano faktury");

        }
    }

    public void drukuj(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Faktura selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                drukujcd(selected, fdod, i, "druk", wpisView);
                i++;
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }
    public void drukujPrinter(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Faktura selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                PDFDirectPrint.silentPrintPdf(drukujcdPrinter(selected, fdod, i, "druk", wpisView));
                i++;
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }

    public void drukujokresowa(Fakturywystokresowe selected, int row, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + String.valueOf(row) + wpisView.getPodatnikWpisu() + ".pdf";
        File file = new File(nazwapliku);
        if (file.isFile()) {
            file.delete();
        }
        try {
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected.getDokument(), fdod, row, "druk", wpisView);
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wybrano faktury");
        }
    }

    public void drukujokresowa(List<Fakturywystokresowe> fakturydruk) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Fakturywystokresowe selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                drukujcdPrinter(selected.getDokument(), fdod, i, "druk",wpisView);
                i++;
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }

    private void drukujcd(Faktura selected, List<Fakturadodelementy> fdod, int nrfakt, String przeznaczenie, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + String.valueOf(nrfakt) + wpisView.getPodatnikWpisu() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
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
        Font fontXS = new Font(helvetica, 4);
        Font fontS = new Font(helvetica, 6);
        Font font = new Font(helvetica, 8);
        Font fontL = new Font(helvetica, 10);
        Font fontXL = new Font(helvetica, 12);
        List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
        Collections.sort(lista, new Pozycjenafakturzecomparator());
        Map<String, Integer> wymiary = new HashMap<>();
        int gornylimit = 836;
        for (Pozycjenafakturze p : lista) {
            int wymiargora = (p.getGora() / 2);
            wymiary.put(p.getPozycjenafakturzePK().getNazwa(), gornylimit - wymiargora);
        }
        Image image = Image.getInstance("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/new-product.jpg");
        // Set the position of image
        image.setAbsolutePosition(500f, 40f); //e
        // Add paragraph to PDF document.
        document.add(image);
        //naglowek
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
        //stopka
        absText(writer, "Fakturę wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
        absText(writer, "Dokument nie wymaga podpisu. Odbiorca dokumentu wyraził zgode na otrzymanie go w formie elektronicznej.", 15, 18, 6);
        prost(writer.getDirectContent(), 12, 15, 560, 20);
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : lista) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:data":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "data");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:data") - 5, 100, 15);
                    absText(writer, selected.getMiejscewystawienia() + " dnia: " + selected.getDatawystawienia(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:data"), 8);
                    break;
                case "akordeon:formwzor:datasprzedazy":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "datasprzedazy");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:datasprzedazy") - 5, 110, 15);
                    absText(writer, "Data sprzedaży: " + selected.getDatasprzedazy(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:datasprzedazy"), 8);
                    break;
                case "akordeon:formwzor:fakturanumer":
                    //Dane do modulu fakturanumer
                    pobrane = zwrocpozycje(lista, "fakturanumer");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:fakturanumer") - 5, 190, 20);
                    absText(writer, "Faktura nr " + selected.getFakturaPK().getNumerkolejny(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:fakturanumer"), 10);
                    break;
                case "akordeon:formwzor:wystawca":
                    //Dane do modulu sprzedawca
                    pobrane = zwrocpozycje(lista, "wystawca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wystawca") - 65, 250, 80);
                    absText(writer, "Sprzedawca:", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca"), 10);
                    absText(writer, selected.getWystawca().getNazwadlafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 20, 8);
                    adres = selected.getWystawca().getAdresdlafaktury();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 40, 8);
                    absText(writer, "NIP: " + selected.getWystawca().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 60, 8);
                    break;
                case "akordeon:formwzor:odbiorca":
                    //Dane do modulu odbiorca
                    pobrane = zwrocpozycje(lista, "odbiorca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:odbiorca") - 65, 250, 80);
                    absText(writer, "Nabywca:", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca"), 10);
                    absText(writer, selected.getKontrahent().getNpelna(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 20, 8);
                    adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 40, 8);
                    absText(writer, "NIP: " + selected.getKontrahent().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 60, 8);
                    break;
                case "akordeon:formwzor:platnosc":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "platnosc");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:platnosc") - 25, 250, 35);
                    absText(writer, "Sposób zapłaty: " + selected.getSposobzaplaty(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Termin płatności: " + selected.getTerminzaplaty(), (int) (pobrane.getLewy() / dzielnik) + 100, wymiary.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Nr konta bankowego: " + selected.getNrkontabankowego(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:platnosc") - 20, 8);
                    break;
                case "akordeon:formwzor:dozaplaty":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "dozaplaty");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:dozaplaty") - 25, 350, 35);
                    absText(writer, "Do zapłaty: " + przerobkwote(selected.getBrutto()) + " " + selected.getWalutafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:dozaplaty"), 8);
                    absText(writer, "Słownie: " + Slownie.slownie(String.valueOf(selected.getBrutto())), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:dozaplaty") - 20, 8);
                    break;
                case "akordeon:formwzor:podpis":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "podpis");
                    absText(writer, selected.getPodpis(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:podpis"), 8);
                    absText(writer, "..........................................", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:podpis") - 20, 8);
                    absText(writer, "wystawca faktury", (int) (pobrane.getLewy() / dzielnik) + 15, wymiary.get("akordeon:formwzor:podpis") - 40, 8);
                    break;
                case "akordeon:formwzor:towary":
                    //Dane do modulu towary
                    pobrane = zwrocpozycje(lista, "towary");
                    PdfPTable table = new PdfPTable(11);
                    wygenerujtablice(table, selected.getPozycjenafakturze(), selected);
                    // write the table to an absolute position
                    table.writeSelectedRows(0, table.getRows().size(), (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:towary"), writer.getDirectContent());
                    break;
                case "akordeon:formwzor:przewłaszczenie":
                    //Dane do modulu przewłaszczenie
                    if (fdod.get(2).getAktywny()) {
                        pobrane = zwrocpozycje(lista, "przewłaszczenie");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:przewłaszczenie") - 5, 230, 15);
                        absText(writer, fdod.get(2).getTrescelementu(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:przewłaszczenie"), 8);
                    }
                    break;
                case "akordeon:formwzor:warunkidostawy":
                    //Dane do modulu przewłaszczenie
                    if (fdod.get(1).getAktywny()) {
                        pobrane = zwrocpozycje(lista, "warunkidostawy");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:warunkidostawy") - 5, 360, 15);
                        absText(writer, fdod.get(1).getTrescelementu(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:warunkidostawy"), 8);
                    }
                    break;
                case "akordeon:formwzor:wezwaniedozapłaty":
                    //Dane do modulu przewłaszczenie
                    if (fdod.get(0).getAktywny()) {
                        pobrane = zwrocpozycje(lista, "wezwaniedozapłaty");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wezwaniedozapłaty") - 5, 230, 15);
                        absText(writer, fdod.get(0).getTrescelementu(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                    }
                    break;
            }
        }
        document.close();
        if (przeznaczenie.equals("druk")) {
            Msg.msg("i", "Wydrukowano Fakture", "form:messages");
            String funkcja = "wydrukfaktura('" + String.valueOf(nrfakt) + wpisView.getPodatnikWpisu() + "');";
            RequestContext.getCurrentInstance().execute(funkcja);
        }

    }
    
    private String drukujcdPrinter(Faktura selected, List<Fakturadodelementy> fdod, int nrfakt, String przeznaczenie, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + String.valueOf(nrfakt) + wpisView.getPodatnikWpisu() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
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
        Font fontXS = new Font(helvetica, 4);
        Font fontS = new Font(helvetica, 6);
        Font font = new Font(helvetica, 8);
        Font fontL = new Font(helvetica, 10);
        Font fontXL = new Font(helvetica, 12);
        List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
        Collections.sort(lista, new Pozycjenafakturzecomparator());
        Map<String, Integer> wymiary = new HashMap<>();
        int gornylimit = 836;
        for (Pozycjenafakturze p : lista) {
            int wymiargora = (p.getGora() / 2);
            wymiary.put(p.getPozycjenafakturzePK().getNazwa(), gornylimit - wymiargora);
        }
        Image image = Image.getInstance("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/resources/images/new-product.jpg");
        // Set the position of image
        image.setAbsolutePosition(500f, 40f); //e
        // Add paragraph to PDF document.
        document.add(image);
        //naglowek
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
        //stopka
        absText(writer, "Fakturę wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
        absText(writer, "Dokument nie wymaga podpisu. Odbiorca dokumentu wyraził zgode na otrzymanie go w formie elektronicznej.", 15, 18, 6);
        prost(writer.getDirectContent(), 12, 15, 560, 20);
        Pozycjenafakturze pobrane = new Pozycjenafakturze();
        String adres = "";
        float dzielnik = 2;
        for (Pozycjenafakturze p : lista) {
            switch (p.getPozycjenafakturzePK().getNazwa()) {
                case "akordeon:formwzor:data":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "data");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:data") - 5, 100, 15);
                    absText(writer, selected.getMiejscewystawienia() + " dnia: " + selected.getDatawystawienia(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:data"), 8);
                    break;
                case "akordeon:formwzor:datasprzedazy":
                    //Dane do moudlu data
                    pobrane = zwrocpozycje(lista, "datasprzedazy");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:datasprzedazy") - 5, 110, 15);
                    absText(writer, "Data sprzedaży: " + selected.getDatasprzedazy(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:datasprzedazy"), 8);
                    break;
                case "akordeon:formwzor:fakturanumer":
                    //Dane do modulu fakturanumer
                    pobrane = zwrocpozycje(lista, "fakturanumer");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:fakturanumer") - 5, 190, 20);
                    absText(writer, "Faktura nr " + selected.getFakturaPK().getNumerkolejny(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:fakturanumer"), 10);
                    break;
                case "akordeon:formwzor:wystawca":
                    //Dane do modulu sprzedawca
                    pobrane = zwrocpozycje(lista, "wystawca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wystawca") - 65, 250, 80);
                    absText(writer, "Sprzedawca:", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca"), 10);
                    absText(writer, selected.getWystawca().getNazwadlafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 20, 8);
                    adres = selected.getWystawca().getAdresdlafaktury();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 40, 8);
                    absText(writer, "NIP: " + selected.getWystawca().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wystawca") - 60, 8);
                    break;
                case "akordeon:formwzor:odbiorca":
                    //Dane do modulu odbiorca
                    pobrane = zwrocpozycje(lista, "odbiorca");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:odbiorca") - 65, 250, 80);
                    absText(writer, "Nabywca:", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca"), 10);
                    absText(writer, selected.getKontrahent().getNpelna(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 20, 8);
                    adres = selected.getKontrahent().getKodpocztowy() + " " + selected.getKontrahent().getMiejscowosc() + " " + selected.getKontrahent().getUlica() + " " + selected.getKontrahent().getDom();
                    absText(writer, adres, (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 40, 8);
                    absText(writer, "NIP: " + selected.getKontrahent().getNip(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:odbiorca") - 60, 8);
                    break;
                case "akordeon:formwzor:platnosc":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "platnosc");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:platnosc") - 25, 250, 35);
                    absText(writer, "Sposób zapłaty: " + selected.getSposobzaplaty(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Termin płatności: " + selected.getTerminzaplaty(), (int) (pobrane.getLewy() / dzielnik) + 100, wymiary.get("akordeon:formwzor:platnosc"), 8);
                    absText(writer, "Nr konta bankowego: " + selected.getNrkontabankowego(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:platnosc") - 20, 8);
                    break;
                case "akordeon:formwzor:dozaplaty":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "dozaplaty");
                    prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:dozaplaty") - 25, 350, 35);
                    absText(writer, "Do zapłaty: " + przerobkwote(selected.getBrutto()) + " " + selected.getWalutafaktury(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:dozaplaty"), 8);
                    absText(writer, "Słownie: " + Slownie.slownie(String.valueOf(selected.getBrutto())), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:dozaplaty") - 20, 8);
                    break;
                case "akordeon:formwzor:podpis":
                    //Dane do modulu platnosc
                    pobrane = zwrocpozycje(lista, "podpis");
                    absText(writer, selected.getPodpis(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:podpis"), 8);
                    absText(writer, "..........................................", (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:podpis") - 20, 8);
                    absText(writer, "wystawca faktury", (int) (pobrane.getLewy() / dzielnik) + 15, wymiary.get("akordeon:formwzor:podpis") - 40, 8);
                    break;
                case "akordeon:formwzor:towary":
                    //Dane do modulu towary
                    pobrane = zwrocpozycje(lista, "towary");
                    PdfPTable table = new PdfPTable(11);
                    wygenerujtablice(table, selected.getPozycjenafakturze(), selected);
                    // write the table to an absolute position
                    table.writeSelectedRows(0, table.getRows().size(), (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:towary"), writer.getDirectContent());
                    break;
                case "akordeon:formwzor:przewłaszczenie":
                    //Dane do modulu przewłaszczenie
                    if (fdod.get(2).getAktywny()) {
                        pobrane = zwrocpozycje(lista, "przewłaszczenie");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:przewłaszczenie") - 5, 230, 15);
                        absText(writer, fdod.get(2).getTrescelementu(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:przewłaszczenie"), 8);
                    }
                    break;
                case "akordeon:formwzor:warunkidostawy":
                    //Dane do modulu przewłaszczenie
                    if (fdod.get(1).getAktywny()) {
                        pobrane = zwrocpozycje(lista, "warunkidostawy");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:warunkidostawy") - 5, 360, 15);
                        absText(writer, fdod.get(1).getTrescelementu(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:warunkidostawy"), 8);
                    }
                    break;
                case "akordeon:formwzor:wezwaniedozapłaty":
                    //Dane do modulu przewłaszczenie
                    if (fdod.get(0).getAktywny()) {
                        pobrane = zwrocpozycje(lista, "wezwaniedozapłaty");
                        prost(writer.getDirectContent(), (int) (pobrane.getLewy() / dzielnik) - 5, wymiary.get("akordeon:formwzor:wezwaniedozapłaty") - 5, 230, 15);
                        absText(writer, fdod.get(0).getTrescelementu(), (int) (pobrane.getLewy() / dzielnik), wymiary.get("akordeon:formwzor:wezwaniedozapłaty"), 8);
                    }
                    break;
            }
        }
        document.close();
        return nazwapliku;

    }
    
    private void silentPrintPdf(String nazwapliku) {
         try{
               Executable ex = new Executable();
               ex.printDocumentSilent(nazwapliku);
            }catch(IOException e){
               e.printStackTrace();
            }        
    }

    private String przerobkwote(double kwota) {
        String tmp = String.valueOf(kwota);
        String poczatek = null;
        String koniec = null;
        if (tmp.contains(".")) {
            String[] tab = tmp.split("\\.");
            poczatek = tab[0];
            koniec = tab[1];
        } else {
            poczatek = tmp;
            koniec = "00";
        }
        int dlugosc = poczatek.length();
        String czesc1 = null;
        String czesc2 = null;
        String czesc3 = null;
        switch (dlugosc) {
            case 1:
                czesc3 = poczatek;
                break;
            case 2:
                czesc3 = poczatek;
                break;
            case 3:
                czesc3 = poczatek;
                break;
            case 4:
                czesc2 = poczatek.substring(0, 1);
                czesc3 = poczatek.substring(1, 4);
                break;
            case 5:
                czesc2 = poczatek.substring(0, 2);
                czesc3 = poczatek.substring(2, 5);
                break;
            case 6:
                czesc2 = poczatek.substring(0, 3);
                czesc3 = poczatek.substring(3, 6);
                break;
            case 7:
                czesc1 = poczatek.substring(0, 1);
                czesc2 = poczatek.substring(1, 4);
                czesc3 = poczatek.substring(4, 7);
                break;
        }
        if (czesc1 != null) {
            poczatek = czesc1 + " " + czesc2 + " " + czesc3;
        } else if (czesc2 != null) {
            poczatek = czesc2 + " " + czesc3;
        } else {
            poczatek = czesc3;
        }
        if (koniec.equals("0")) {
            koniec = "00";
        } else if (koniec.length() == 1) {
            koniec = koniec.concat("0");
        }
        return poczatek + "," + koniec;

    }

    private PdfPTable wygenerujtablice(PdfPTable table, List<Pozycjenafakturzebazadanych> poz, Faktura selected) throws DocumentException, IOException {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        Rectangle rect = new Rectangle(523, 200);
        table.setWidthPercentage(new float[]{20, 100, 40, 40, 40, 50, 60, 50, 60, 60, 30}, rect);
        // set the total width of the table
        table.setTotalWidth(500);
        table.addCell(ustawfrazeAlign("lp", "center", 8));
        table.addCell(ustawfrazeAlign("opis", "center", 8));
        table.addCell(ustawfrazeAlign("PKWiU", "center", 8));
        table.addCell(ustawfrazeAlign("ilość", "center", 8));
        table.addCell(ustawfrazeAlign("jedn.m.", "center", 8));
        table.addCell(ustawfrazeAlign("cena netto", "center", 8));
        table.addCell(ustawfrazeAlign("wartość netto", "center", 8));
        table.addCell(ustawfrazeAlign("stawka vat", "center", 8));
        table.addCell(ustawfrazeAlign("kwota vat", "center", 8));
        table.addCell(ustawfrazeAlign("wartość brutto", "center", 8));
        table.addCell(ustawfrazeAlign("uwagi", "center", 8));
        table.setHeaderRows(1);
        for (Pozycjenafakturzebazadanych pozycje : poz) {
            table.addCell(ustawfrazeAlign("1", "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getNazwa(), "left", 8));
            table.addCell(ustawfrazeAlign(pozycje.getPKWiU(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(pozycje.getIlosc()), "center", 8));
            table.addCell(ustawfrazeAlign(pozycje.getJednostka(), "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getCena())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getNetto())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf((int) pozycje.getPodatek()) + "%", "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getPodatekkwota())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(pozycje.getBrutto())), "right", 8));
            table.addCell(ustawfrazeAlign("", "center", 8));
        }
        table.addCell(ustawfraze("Razem", 6, 0));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getNetto())), "right", 8));
        table.addCell(ustawfrazeAlign("*", "center", 8));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getVat())), "right", 8));
        table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(selected.getBrutto())), "right", 8));
        table.completeRow();
        table.addCell(ustawfraze("w tym wg stawek vat", 6, 0));
        List<EVatwpis> ewidencja = selected.getEwidencjavat();
        int ilerow = 0;
        for (EVatwpis p : ewidencja) {
            if (ilerow > 0) {
                table.addCell(ustawfraze(" ", 6, 0));
            }
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto() + p.getVat())), "right", 8));
            table.completeRow();
            ilerow++;
        }
        // complete the table

        return table;
    }

    private Pozycjenafakturze zwrocpozycje(List<Pozycjenafakturze> lista, String data) {
        for (Pozycjenafakturze p : lista) {
            if (p.getPozycjenafakturzePK().getNazwa().contains(data)) {
                return p;
            }
        }
        return null;
    }

}
