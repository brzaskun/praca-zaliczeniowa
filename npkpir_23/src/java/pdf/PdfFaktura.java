/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beansPdf.PdfFP;
import static beansPdf.PdfFont.*;
import static beansPdf.PdfGrafika.prost;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;
import comparator.Pozycjenafakturzecomparator;
import dao.FakturaStopkaNiemieckaDAO;
import dao.FakturaXXLKolumnaDAO;
import dao.FakturadodelementyDAO;
import dao.FakturaelementygraficzneDAO;
import dao.PozycjenafakturzeDAO;
import entity.Faktura;
import entity.FakturaDuplikat;
import entity.FakturaStopkaNiemiecka;
import entity.Fakturadodelementy;
import entity.Fakturywystokresowe;
import entity.Podatnik;
import entity.Pozycjenafakturze;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfVAT7.absText;
import plik.Plik;
import view.WpisView;


/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PdfFaktura extends Pdf implements Serializable {

    @Inject
    private PozycjenafakturzeDAO pozycjeDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    @Inject
    private FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO;
    @Inject
    private FakturaStopkaNiemieckaDAO  fakturaStopkaNiemieckaDAO;

    public void drukujmail(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Faktura selected : fakturydruk) {
            try {
                int row = i;
                if (wpisView.getPodatnikObiekt().getNip().equals("9552340951")||wpisView.getPodatnikObiekt().getNip().equals("9552339497")) {
                    String[] numer = selected.getNumerkolejny().split("/");
                    row = Integer.parseInt(numer[0]);
                }
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                drukujcd(selected, fdod, row, "mail", wpisView.getPodatnikObiekt(), false, null);
                i++;
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }

    public void drukuj(Faktura selected, int row, Podatnik podatnik) throws DocumentException, FileNotFoundException, IOException {
        try {
            if (podatnik.getNip().equals("9552340951")||podatnik.getNip().equals("9552339497")) {
                String[] numer = selected.getNumerkolejny().split("/");
                row = Integer.parseInt(numer[0]);
            }
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"wydruki/faktura" + String.valueOf(row) + podatnik.getNip() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(podatnik.getNazwapelna());
            drukujcd(selected, fdod, row, "druk", podatnik, false, null);
            Msg.msg("Wydruk faktury");

        } catch (DocumentException | IOException e) {
            E.e(e);
            Msg.msg("e", "Błąd - nie wybrano faktury");

        }
    }
    
    public void drukujDuplikat(Faktura selected, FakturaDuplikat duplikat, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        try {
            int row = duplikat.getId();
            if (wpisView.getPodatnikObiekt().getNip().equals("9552340951")||wpisView.getPodatnikObiekt().getNip().equals("9552339497")) {
                String[] numer = selected.getNumerkolejny().split("/");
                row = Integer.parseInt(numer[0]);
            }
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"wydruki/fakturaduplikat" + String.valueOf(row) + wpisView.getPodatnikObiekt().getNip() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected, fdod, row, "druk", wpisView.getPodatnikObiekt(), true, duplikat);
            Msg.msg("Wydruk faktury");

        } catch (DocumentException | IOException e) {
            E.e(e);
            Msg.msg("e", "Błąd - nie wybrano faktury");

        }
    }
    
    public void drukujPodgladfaktury(Faktura selected, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"wydruki/faktura0" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected, fdod, 0, "druk", wpisView.getPodatnikObiekt(), false, null);
            Msg.msg("i","Wygenerowano podgląd faktury.","akordeon:formstworz:messagesinline");

        } catch (DocumentException | IOException e) {
            E.e(e);
            Msg.msg("e", "Błąd - nie wybrano faktury");

        }
    }

    public void drukuj(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Faktura selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                int row = i;
                if (wpisView.getPodatnikObiekt().getNip().equals("9552340951")||wpisView.getPodatnikObiekt().getNip().equals("9552339497")) {
                    String[] numer = selected.getNumerkolejny().split("/");
                    row = Integer.parseInt(numer[0]);
                }
                drukujcd(selected, fdod, row, "druk", wpisView.getPodatnikObiekt(), false, null);
                i++;
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }
    
    public void drukujmasa(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        List<String> pliki = new ArrayList<>();
        for (Faktura selected : fakturydruk) {
            try {
                int row = i;
                if (wpisView.getPodatnikObiekt().getNip().equals("9552340951")||wpisView.getPodatnikObiekt().getNip().equals("9552339497")) {
                    String[] numer = selected.getNumerkolejny().split("/");
                    row = Integer.parseInt(numer[0]);
                }
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                pliki.add(drukujcd(selected, fdod, row, "masa", wpisView.getPodatnikObiekt(), false, null));
                i++;
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String [] stockArr = pliki.toArray(new String[pliki.size()]);
        String nazwapliku = realPath + "wydruki/fakturaNr" + String.valueOf(999) + "firma" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
        mergeFiles(stockArr, nazwapliku);
        String funkcja = "wydrukfaktura('" + String.valueOf(999) + "firma" + wpisView.getPodatnikObiekt().getNip() + "');";
        PrimeFaces.current().executeScript(funkcja);
    }
    
    public void mergeFiles(String[] files, String result) throws IOException, DocumentException {
        Document document = new Document();
        PdfCopy copy = new PdfCopy(document, new FileOutputStream(result));
        document.open();
        PdfReader reader;
        int n;
        for (int i = 0; i < files.length; i++) {
            reader = new PdfReader(files[i]);
            n = reader.getNumberOfPages();
            for (int page =0;page <n;) {
                copy.addPage(copy.getImportedPage(reader, ++page));
            }
            copy.freeReader(reader);
            reader.close();
        }
        document.close();
    }
    
    public void drukujPrinter(List<Faktura> fakturydruk, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Faktura selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                //PDFDirectPrint.silentPrintPdf(drukujcdPrinter(selected, fdod, i, "druk", wpisView));
                i++;
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }

    public void drukujokresowa(Fakturywystokresowe selected, int row, Podatnik podatnik) throws DocumentException, FileNotFoundException, IOException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"wydruki/fakturaNr" + String.valueOf(row) + "firma" + podatnik.getNip() + ".pdf";
        File file = new File(nazwapliku);
        if (file.isFile()) {
            file.delete();
        }
        try {
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(podatnik.getNazwapelna());
            drukujcd(selected.getDokument(), fdod, row, "druk", podatnik, false, null);
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Błąd - nie wybrano faktury");
        }
    }

    public void drukujokresowa(List<Fakturywystokresowe> fakturydruk) throws DocumentException, FileNotFoundException, IOException {
        int i = 0;
        for (Fakturywystokresowe selected : fakturydruk) {
            try {
                List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
                drukujcdPrinter(selected.getDokument(), fdod, i, "druk",wpisView.getPodatnikObiekt());
                i++;
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Błąd - nie wybrano faktury");
            }
        }
    }

    private String drukujcd(Faktura selected, List<Fakturadodelementy> elementydod, int nrfakt, String przeznaczenie, Podatnik podatnik, boolean duplikat,
        FakturaDuplikat duplikatobj) throws DocumentException, FileNotFoundException, IOException {
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String nazwapliku = realPath + "wydruki/fakturaNr" + String.valueOf(nrfakt) + "firma" + podatnik.getNip() + ".pdf";
        List<Pozycjenafakturze> skladnikifaktury = pozycjeDAO.findFakturyPodatnik(podatnik);
        if (skladnikifaktury.isEmpty()) {
            Msg.msg("e", "Nie zdefiniowano pozycji faktury. Nie można jej wydrukować. Przejdź do zakładki: 'Wzór faktury'.", "akordeon:formstworz:messagesinline");
        } else {
            Collections.sort(skladnikifaktury, new Pozycjenafakturzecomparator());
            Map<String, Integer> wymiaryGora = PdfFP.pobierzwymiarGora(skladnikifaktury);
            int wierszewtabelach = PdfFP.obliczwierszewtabelach(selected);
            boolean dlugiwiersz = selected.getPozycjenafakturze().get(0).getNazwa().length() > 100;
            boolean jestkorekta = selected.getPozycjepokorekcie() != null;
            if ((wierszewtabelach > 12 && jestkorekta == false) || (dlugiwiersz && jestkorekta) || (wierszewtabelach > 7 && jestkorekta)) {
                Document document = new Document();
                PdfWriter writer = writerCreate(document, nrfakt, podatnik.getNip());
                PdfFP.dodajopisdok(document);
                document.setMargins(0, 0, 400, 20);
                document.open();
                document.setMargins(0, 0, 20, 20);
                if (duplikat) {
                    PdfFP.dodajoznaczenieduplikat(writer, duplikatobj);
                }
                if (selected.isProforma()) {
                    PdfFP.dodajoznaczenieproforma(writer);
                }
                PdfFP.dodajnaglowekstopka(writer, elementydod);
                Image logo = PdfFP.dolaczpozycjedofakturydlugaczlogo(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO);
                if (logo != null) {
                    document.add(logo);
                }
                if (selected.getPozycjepokorekcie() != null) {
                    if (selected.isFakturaxxl()) {
                        document.add(PdfFP.dolaczpozycjedofakturydlugacz2(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO));
                        document.add(new Paragraph(""));
                        document.add(Chunk.NEWLINE);
                        document.add(PdfFP.dolaczpozycjedofakturydlugacz2korekta(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO));
                    } else if (selected.isFakturavatmarza()) {
                        document.add(PdfFP.dolaczpozycjedofakturyvatmarza(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, document, elementydod, fakturaXXLKolumnaDAO));
                        document.add(new Paragraph(""));
                        document.add(Chunk.NEWLINE);
                        document.add(PdfFP.dolaczpozycjedofakturyvatmarzakorekta(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, document, elementydod, fakturaXXLKolumnaDAO));
                    } else {
                        document.add(PdfFP.dolaczpozycjedofaktury2normal(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury,  document, elementydod, fakturaXXLKolumnaDAO));
                        document.add(new Paragraph(""));
                        document.add(Chunk.NEWLINE);
                        document.add(PdfFP.dolaczpozycjedofaktury2normalkorekta(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, document, elementydod, fakturaXXLKolumnaDAO));
                    }
                } else {
                    if (selected.isFakturaxxl()) {
                        document.add(PdfFP.dolaczpozycjedofakturydlugacz2(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO));
                    } else if (selected.isFakturavatmarza()) {
                        document.add(PdfFP.dolaczpozycjedofakturyvatmarza(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury,  document, elementydod, fakturaXXLKolumnaDAO));
                    } else {
                        document.add(PdfFP.dolaczpozycjedofaktury2normal(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury,  document, elementydod, fakturaXXLKolumnaDAO));
                    }
                }
                //writer.close();
                document.close();
                PdfReader reader = new PdfReader(nazwapliku);
                PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                String nazwapliku1 = realPath + "wydruki/fakturaNr1" + String.valueOf(nrfakt) + "firma" + podatnik.getNip() + ".pdf";
                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(nazwapliku1));
                PdfContentByte canvas = stamper.getOverContent(1);
                PdfFP.dolaczpozycjedofakturydlugacz1(fakturaelementygraficzneDAO, canvas, selected, wymiaryGora, skladnikifaktury, document, elementydod, fakturaXXLKolumnaDAO);
                stamper.close();
                reader.close();
                PdfFP.usunplik(nazwapliku);
                reader = new PdfReader(nazwapliku1);
                parser = new PdfReaderContentParser(reader);
                String nazwapliku2 = realPath + "wydruki/fakturaNr" + String.valueOf(nrfakt) + "firma" + podatnik.getNip() + ".pdf";
                stamper = new PdfStamper(reader, new FileOutputStream(nazwapliku2));
                TextMarginFinder finder;
                int n = reader.getNumberOfPages();
                //error.E.s("liczba stron "+n);
                finder = parser.processContent(n, new TextMarginFinder());
                error.E.s(finder.getLlx());
                error.E.s(finder.getLly());
                error.E.s(finder.getWidth());
                error.E.s(finder.getHeight());
                if (finder.getLly() < 300) {
                    stamper.insertPage(++n, reader.getPageSize(1));
                    canvas = stamper.getOverContent(n);
                    PdfFP.dolaczpozycjedofakturydlugacz3(true, fakturaelementygraficzneDAO, canvas, selected, 800, skladnikifaktury,  document, elementydod, fakturaXXLKolumnaDAO);
                } else {
                    canvas = stamper.getOverContent(n);
                    PdfFP.dolaczpozycjedofakturydlugacz3(false, fakturaelementygraficzneDAO, canvas, selected, finder.getLly(), skladnikifaktury, document, elementydod, fakturaXXLKolumnaDAO);
                }
                stamper.close();
                reader.close();
                PdfFP.usunplik(nazwapliku1);
            } else {
                Document document = new Document();
                PdfWriter writer = writerCreate(document, nrfakt, podatnik.getNip());
                PdfFP.dodajopisdok(document);
                document.open();
                document.setMargins(0, 0, 20, 20);
                if (duplikat) {
                    PdfFP.dodajoznaczenieduplikat(writer, duplikatobj);
                }
                if (selected.isProforma()) {
                    PdfFP.dodajoznaczenieproforma(writer);
                }
                if (czyjeststopkaniemiecka(elementydod)) {
                    PdfFP.dodajnaglowek(writer, elementydod);
                    PdfFP.dolaczpozycjedofaktury(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO);
                    FakturaStopkaNiemiecka f = fakturaStopkaNiemieckaDAO.findByPodatnik(podatnik);
                    if (f != null) {
                        PdfFP.stopkaniemiecka(writer, document, f);
                    }
                } else if (selected.isSprzedazsamochoduimportowanego()) {
                    PdfFP.dodajnaglowek(writer, elementydod);
                    PdfFP.dolaczpozycjedofaktury(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO);
                    PdfFP.sprzedazsamochoduimportowanego(writer, document, selected);
                } else {
                    PdfFP.dodajnaglowekstopka(writer, elementydod);
                    PdfFP.dolaczpozycjedofaktury(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, podatnik, document, elementydod, fakturaXXLKolumnaDAO);
                }
                document.close();
            }
            if (przeznaczenie.equals("druk")) {
                String funkcja = "wydrukfaktura('" + String.valueOf(nrfakt) + "firma" + podatnik.getNip() + "');";
                PrimeFaces.current().executeScript(funkcja);
            }
        }
        return nazwapliku;
    }
    
    
    
    private PdfWriter writerCreate(Document document, int nrfakt, String NIP) {
        PdfWriter writer = null;
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"wydruki/fakturaNr" + String.valueOf(nrfakt) + "firma"+ NIP + ".pdf";
            writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
            writer.setBoxSize("art", new Rectangle(800, 830, 0, 0));
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
        } catch (Exception ex) {
            E.e(ex);
        }
        return writer;
    }
    
    private boolean czyjeststopkaniemiecka(List<Fakturadodelementy> elementydod) {
        boolean zwrot = false;
        for (Fakturadodelementy p : elementydod) {
            if (p.getFakturadodelementyPK().getNazwaelementu().equals("stopka niemiecka") && p.getAktywny()) {
                zwrot = true;
            }
        }
        return zwrot;
    }
    
     private String drukujcdPrinter(Faktura selected, List<Fakturadodelementy> elementydod, int nrfakt, String przeznaczenie, Podatnik podatnik) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"wydruki/faktura" + String.valueOf(nrfakt) + podatnik.getNip() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
        PdfFP.dodajopisdok(document);
        document.open();
        List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(podatnik);
        Collections.sort(lista, new Pozycjenafakturzecomparator());
        Map<String, Integer> wymiary = PdfFP.pobierzwymiarGora(lista);
        PdfFP.dodajnaglowekstopka(writer, elementydod);
        //naglowek
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
        //stopka
        absText(writer, "Fakturę wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
        absText(writer, "Dokument nie wymaga podpisu. Odbiorca wyraził zgode na otrzymanie faktury w formie elektr.", 15, 18, 6);
        prost(writer.getDirectContent(), 12, 15, 560, 20);
        PdfFP.dolaczpozycjedofaktury(fakturaelementygraficzneDAO, writer, selected, wymiary, lista, podatnik, document, elementydod, fakturaXXLKolumnaDAO);
        document.close();
        return nazwapliku;

    }
    
     
     
     private static void dodajodbiuorca(PdfWriter writer) {
        //Dane do modulu odbiorca
        String adres = "";
        String text = null;
        float dzielnik = 2;
        prost(writer.getDirectContent(), (int) (79 / dzielnik) - 5, 593 - 65, 250, 80);
        absText(writer, "nabywca"+": ", (int) (79/ dzielnik), 593, 10);
        PdfFP.absColumn(writer, "Maria Kowalska Bukowska De Mono Kikoracka Janecka Obojga Narodów i Marsa Zula", (int) (79/ dzielnik), 593 - 25, 8);
        adres = "72-100 Szczecin ul. Kaczki Moczanowskieh 15";
        absText(writer, adres, (int) (79/ dzielnik), 593 - 40, 8);
        text = "NIP"+": 8511005008";
        absText(writer, text, (int) (79/ dzielnik), 593 - 60, 8);
     }
  
//    private void silentPrintPdf(String nazwapliku) {
//        Executable ex = new Executable();
//        ex.printDocumentSilent(nazwapliku);        
//    }
//    
    

public static void main(String[] args) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String nazwapliku = "testowa.pdf";
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(800, 830, 0, 0));
        //writer.setPageEvent(headerfoter);
        writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
        document.setMargins(0, 0, 400, 20);
        document.addTitle("Faktura");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk faktury w formacie pdf");
        document.addKeywords("Faktura, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        try {
            document.setMargins(0, 0, 40, 20);
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            PdfPTable table = new PdfPTable(15);
            table.setTotalWidth(new float[]{30, 150, 50, 30, 30, 70, 70, 70, 70, 70, 70, 90, 40, 90, 90});
            // set the total width of the table
            table.setWidthPercentage(95);
            table.addCell(ustawfrazeAlign("lp", "center", 7));
            table.addCell(ustawfrazeAlign("opis", "center", 7));
            table.addCell(ustawfrazeAlign("PKWiU", "center", 7));
            table.addCell(ustawfrazeAlign("ilość", "center", 7));
            table.addCell(ustawfrazeAlign("j.m.", "center", 7));
            table.addCell(ustawfrazeAlign("cena AAAAAAAAA", "center", 7));
            table.addCell(ustawfrazeAlign("cena BBBBBB", "center", 7));
            table.addCell(ustawfrazeAlign("cena CCCCCC", "center", 7));
            table.addCell(ustawfrazeAlign("cena netto3", "center", 7));
            table.addCell(ustawfrazeAlign("cena netto4", "center", 7));
            table.addCell(ustawfrazeAlign("cena netto5", "center", 7));
            table.addCell(ustawfrazeAlign("wartość netto", "center", 7));
            table.addCell(ustawfrazeAlign("vat", "center", 7));
            table.addCell(ustawfrazeAlign("kwota vat", "center", 7));
            table.addCell(ustawfrazeAlign("wartość brutto", "center", 7));
            table.setHeaderRows(1);
            for (int i = 0; i < 9; i++) {
                table.addCell(ustawfrazeAlign(String.valueOf(i), "center", 7));
                table.addCell(ustawfrazeAlign("lolo ŚĆĄaŁcÓrŻeŹĘ", "left", 7));
                table.addCell(ustawfrazeAlign("pkwiu śćąóńżź", "center", 7));
                table.addCell(ustawfrazeAlign("10", "center", 7));
                table.addCell(ustawfrazeAlign("kg", "center", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11100)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11101)), "right", 12));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11102)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11103)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11104)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11105)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(22000)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(22) + "%", "center", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(123)), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(4000)), "right", 7));

            }
            table.addCell(ustawfraze("Razem", 11, 0));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(200.0)), "right", 7));
            table.addCell(ustawfrazeAlign("*", "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(46.0)), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(246.0)), "right", 7));
            table.completeRow();
            table.addCell(ustawfraze("w tym wg stawek vat", 11, 0));
    //        List<EVatwpis> ewidencja = selected.getEwidencjavat();
    //        int ilerow = 0;
    //        if (ewidencja != null) {
    //            for (EVatwpis p : ewidencja) {
    //                if (ilerow > 0) {
    //                    table.addCell(ustawfraze(" ", 10, 0));
    //                }
    //                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
    //                table.addCell(ustawfrazeAlign(String.valueOf((int) Double.parseDouble(p.getEstawka())) + "%", "center", 8));
    //                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getVat())), "right", 8));
    //                table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto() + p.getVat())), "right", 8));
    //                table.completeRow();
    //                ilerow++;
    //            }
    //        }
            float dzielnik = 2;
            document.add(table);
            //stopkaniemiecka(writer,document);
            dodajodbiuorca(writer);
            //table.writeSelectedRows(0, table.getRows().size(), 20, 700, writer.getDirectContent());
            document.close();
            writer.close();
            //        PdfReader reader = new PdfReader("C:\\Users\\Osito\\Documents\\NetBeansProjects\\npkpir_23\\build\\web\\wydruki\\testowa.pdf");
            //        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            //        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("d:/tparser.pdf"));
            //        TextMarginFinder finder;
            //        int n = reader.getNumberOfPages();
            //        finder = parser.processContent(n, new TextMarginFinder());
            //        error.E.s(finder.getLlx());
            //        error.E.s(finder.getLly());
            //        error.E.s(finder.getWidth());
            //        error.E.s(finder.getHeight());
            //        //PdfContentByte canvas = stamper.getImportedPage(reader, n);
            ////        PdfContentByte canvas = stamper.getOverContent(1);
            ////        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT, new Phrase("Hello people!"), finder.getLlx()+30, finder.getLly()-20, 0);
            //        stamper.close();
            //        reader.close();
        } catch (Exception e) {
            document.close();
        }
    }

    private static void stopkaniemiecka(PdfWriter writer, Document document) {
        PdfPTable table = null;
        try {
            table = new PdfPTable(4);
            table.setTotalWidth(new float[]{165,120,120,165});
            table.setWidthPercentage(95);
            table.getDefaultCell().setBorderColor(BaseColor.WHITE);
            table.addCell(ustawfrazeAlignNOBorder("TECHNOLOGY BAU GmbH", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Mobil: +49 (0) 170 900 01 05", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Amstgericht Hanau", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Vr Bank Main-Kinzig Bundingen AG", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Geschäftsführer Darius Dankiewitsch", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Büro: +49 (0) 6183 720 882 8", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("HRB 95151", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("IBAN DE80 5066 1639 0003 3091 42", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Römerstrasse 39", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Finanzamt Offebach", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("BIC GENODEF1LSR", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("D-63526 Erlensee", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Mail: info@technology-bau.de", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("Steuernummer 044 248 05197", "left", 7));
            table.addCell(ustawfrazeAlignNOBorder("BLZ 50661639 | KTO-NR 0003309142", "left", 7));
            table.completeRow();
            table.writeSelectedRows(0, -1,
            document.left(document.leftMargin()+15f),
            table.getTotalHeight() + document.bottom(document.bottomMargin()-15f), 
            writer.getDirectContent());
        } catch (DocumentException ex) {
            // Logger.getLogger(PdfFaktura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
