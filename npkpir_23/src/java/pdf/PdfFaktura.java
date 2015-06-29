/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beansPdf.PdfFP;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.TextMarginFinder;
import com.lowagie.tools.Executable;
import comparator.Pozycjenafakturzecomparator;
import dao.FakturaXXLKolumnaDAO;
import dao.FakturadodelementyDAO;
import dao.FakturaelementygraficzneDAO;
import dao.PozycjenafakturzeDAO;
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
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdf.PdfVAT7.absText;
import view.WpisView;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PdfFaktura extends Pdf implements Serializable {

 
    @Inject
    private PozycjenafakturzeDAO pozycjeDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    @Inject
    private FakturaXXLKolumnaDAO fakturaXXLKolumnaDAO;

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
            Msg.msg("Wydruk faktury");

        } catch (DocumentException | IOException e) {
            Msg.msg("e", "Błąd - nie wybrano faktury");

        }
    }
    
    public void drukujPodgladfaktury(Faktura selected, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        try {
            String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura0" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            List<Fakturadodelementy> fdod = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            drukujcd(selected, fdod, 0, "druk", wpisView);
            Msg.msg("i","Wygenerowano podgląd faktury.","akordeon:formstworz:messagesinline");

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
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr" + String.valueOf(row) + "firma" + wpisView.getPodatnikWpisu() + ".pdf";
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

    private void drukujcd(Faktura selected, List<Fakturadodelementy> elementydod, int nrfakt, String przeznaczenie, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
            List<Pozycjenafakturze> skladnikifaktury = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
        if (skladnikifaktury.isEmpty()) {
            Msg.msg("e", "Nie zdefiniowano pozycji faktury. Nie można jej wydrukować. Przejdź do zakładki: 'Wzór faktury'.", "akordeon:formstworz:messagesinline");
        } else {
            Collections.sort(skladnikifaktury, new Pozycjenafakturzecomparator());
            Map<String, Integer> wymiaryGora = PdfFP.pobierzwymiarGora(skladnikifaktury);
            int wierszewtabelach = PdfFP.obliczwierszewtabelach(selected);
            boolean dlugiwiersz = selected.getPozycjenafakturze().get(0).getNazwa().length() > 100;
            boolean jestkorekta = selected.getPozycjepokorekcie() != null;
            if ((wierszewtabelach > 12 && jestkorekta == false) || (dlugiwiersz && jestkorekta)) {
                Document document = new Document();
                String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr0" + String.valueOf(nrfakt) + "firma"+ wpisView.getPodatnikWpisu() + ".pdf";
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
                writer.setBoxSize("art", new Rectangle(800, 830, 0, 0));
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                PdfFP.dodajopisdok(document);
                document.setMargins(0, 0, 400, 20);
                document.open();
                document.setMargins(0, 0, 20, 20);
                PdfFP.dodajnaglowekstopka(writer, elementydod);
                Image logo = PdfFP.dolaczpozycjedofakturydlugaczlogo(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO);
                if (logo != null) {
                    document.add(logo);
                }
                if (selected.getPozycjepokorekcie() != null) {
                    document.add(PdfFP.dolaczpozycjedofakturydlugacz2(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO));
                    document.add(Chunk.NEWLINE );
                    document.add(Chunk.NEWLINE );
                    document.add(PdfFP.dolaczpozycjedofakturydlugacz2korekta(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO));
                } else {
                    if (selected.isFakturaxxl()){
                        document.add(PdfFP.dolaczpozycjedofakturydlugacz2(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO));
                    } else {
                        document.add(PdfFP.dolaczpozycjedofaktury2normal(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO));
                    }
                }
                document.close();
                writer.close();
                PdfReader reader = new PdfReader(nazwapliku);
                PdfReaderContentParser parser = new PdfReaderContentParser(reader);
                String nazwapliku1 = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr1" + String.valueOf(nrfakt) + "firma"+ wpisView.getPodatnikWpisu() + ".pdf";
                PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(nazwapliku1));
                PdfContentByte canvas = stamper.getOverContent(1);
                PdfFP.dolaczpozycjedofakturydlugacz1(fakturaelementygraficzneDAO, canvas, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO);
                stamper.close();
                reader.close();
                PdfFP.usunplik(nazwapliku);
                reader = new PdfReader(nazwapliku1);
                parser = new PdfReaderContentParser(reader);
                String nazwapliku2 = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr" + String.valueOf(nrfakt) + "firma"+ wpisView.getPodatnikWpisu() + ".pdf";
                stamper = new PdfStamper(reader, new FileOutputStream(nazwapliku2));
                TextMarginFinder finder;
                int n = reader.getNumberOfPages();
                System.out.println("liczba stron "+n);
                finder = parser.processContent(n, new TextMarginFinder());
                System.out.println(finder.getLlx());
                System.out.println(finder.getLly());
                System.out.println(finder.getWidth());
                System.out.println(finder.getHeight());
                if (finder.getLly() < 300) {
                    stamper.insertPage(++n, reader.getPageSize(1));
                    canvas = stamper.getOverContent(n);
                    PdfFP.dolaczpozycjedofakturydlugacz3(true, fakturaelementygraficzneDAO, canvas, selected, 800, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO);
                } else {
                    canvas = stamper.getOverContent(n);
                    PdfFP.dolaczpozycjedofakturydlugacz3(false, fakturaelementygraficzneDAO, canvas, selected, finder.getLly(), skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO);
                }
                stamper.close();
                reader.close();
                PdfFP.usunplik(nazwapliku1);
                System.out.println("no ");
            } else {
                Document document = new Document();
                String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/fakturaNr" + String.valueOf(nrfakt) + "firma"+ wpisView.getPodatnikWpisu() + ".pdf";
                PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
                writer.setBoxSize("art", new Rectangle(800, 830, 0, 0));
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                PdfFP.dodajopisdok(document);
                document.setMargins(0, 0, 400, 20);
                document.open();
                document.setMargins(0, 0, 20, 20);
                PdfFP.dodajnaglowekstopka(writer, elementydod);
                PdfFP.dolaczpozycjedofaktury(fakturaelementygraficzneDAO, writer, selected, wymiaryGora, skladnikifaktury, wpisView, document, elementydod, fakturaXXLKolumnaDAO);
                document.close();
            }
             if (przeznaczenie.equals("druk")) {
                    Msg.msg("i", "Wydrukowano Fakture", "form:messages");
                    String funkcja = "wydrukfaktura('" + String.valueOf(nrfakt) + "firma"+  wpisView.getPodatnikWpisu() + "');";
                    RequestContext.getCurrentInstance().execute(funkcja);
                }
        }
    }
    
     private String drukujcdPrinter(Faktura selected, List<Fakturadodelementy> elementydod, int nrfakt, String przeznaczenie, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/faktura" + String.valueOf(nrfakt) + wpisView.getPodatnikWpisu() + ".pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
        PdfFP.dodajopisdok(document);
        document.open();
        List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
        Collections.sort(lista, new Pozycjenafakturzecomparator());
        Map<String, Integer> wymiary = PdfFP.pobierzwymiarGora(lista);
        PdfFP.dodajnaglowekstopka(writer, elementydod);
        //naglowek
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
        //stopka
        absText(writer, "Fakturę wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
        absText(writer, "Dokument nie wymaga podpisu. Odbiorca dokumentu wyraził zgode na otrzymanie go w formie elektronicznej.", 15, 18, 6);
        prost(writer.getDirectContent(), 12, 15, 560, 20);
        PdfFP.dolaczpozycjedofaktury(fakturaelementygraficzneDAO, writer, selected, wymiary, lista, wpisView, document, elementydod, fakturaXXLKolumnaDAO);
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

public static void main(String[] args) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String nazwapliku = "C:/testowa.pdf";
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(800, 830, 0, 0));
        writer.setPageEvent(headerfoter);
        writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
        document.setMargins(0, 0, 400, 20);
        document.addTitle("Faktura");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk faktury w formacie pdf");
        document.addKeywords("Faktura, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
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
        table.addCell(ustawfrazeAlign("cena netto", "center", 7));
        table.addCell(ustawfrazeAlign("cena netto1", "center", 7));
        table.addCell(ustawfrazeAlign("cena netto2", "center", 7));
        table.addCell(ustawfrazeAlign("cena netto3", "center", 7));
        table.addCell(ustawfrazeAlign("cena netto4", "center", 7));
        table.addCell(ustawfrazeAlign("cena netto5", "center", 7));
        table.addCell(ustawfrazeAlign("wartość netto", "center", 7));
        table.addCell(ustawfrazeAlign("vat", "center", 7));
        table.addCell(ustawfrazeAlign("kwota vat", "center", 7));
        table.addCell(ustawfrazeAlign("wartość brutto", "center", 7));
        table.setHeaderRows(1);
        for (int i = 0; i < 69; i++) {
            table.addCell(ustawfrazeAlign(String.valueOf(i), "center", 7));
            table.addCell(ustawfrazeAlign("lolo", "left", 7));
            table.addCell(ustawfrazeAlign("pkwiu", "center", 7));
            table.addCell(ustawfrazeAlign("10", "center", 7));
            table.addCell(ustawfrazeAlign("kg", "center", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11100)), "right", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(11101)), "right", 7));
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
        //table.writeSelectedRows(0, table.getRows().size(), 20, 700, writer.getDirectContent());
        document.close();
        writer.close();
        PdfReader reader = new PdfReader(nazwapliku);
        PdfReaderContentParser parser = new PdfReaderContentParser(reader);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("c:/tparser.pdf"));
        TextMarginFinder finder;
        int n = reader.getNumberOfPages();
        finder = parser.processContent(n, new TextMarginFinder());
        System.out.println(finder.getLlx());
        System.out.println(finder.getLly());
        System.out.println(finder.getWidth());
        System.out.println(finder.getHeight());
        //PdfContentByte canvas = stamper.getImportedPage(reader, n);
        PdfContentByte canvas = stamper.getOverContent(2);
        ColumnText.showTextAligned(canvas,Element.ALIGN_LEFT, new Phrase("Hello people!"), finder.getLlx()+30, finder.getLly()-20, 0);
        stamper.close();
        reader.close();
        System.out.println("no ");
    }
}