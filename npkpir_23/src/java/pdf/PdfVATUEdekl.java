/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dao.PodatnikDAO;
import embeddable.Parametr;
import entity.DeklaracjavatUE;
import entity.Podatnik;
import entity.VatUe;
import error.E;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import static pdf.PdfVAT7.absText;
import pdffk.PdfMain;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.ft;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */

public class PdfVATUEdekl {
    

//    public static void drukujVATUE(PodatnikDAO podatnikDAO, DeklaracjavatUE d, WpisView wpisView) {
//        String nazwa = wpisView.getPodatnikObiekt().getNip()+"vatue";
//        File file = new File(nazwa);
//        if (file.isFile()) {
//            file.delete();
//        }
//        Uz uz = wpisView.getWprowadzil();
//        Document document = PdfMain.inicjacjaA4Portrait();
//        try {
//            PdfWriter writer = inicjacjaWritera(document, nazwa);
//            naglowekStopkaP(writer);
//            otwarcieDokumentu(document, nazwa);
//            Podatnik pod = podatnikDAO.find(d.getPodatnik());
//            dodajOpisWstepny(document, "Deklaracja VAT-UE firma: ", pod, d.getMiesiac(), d.getRok());
//            dodajTabele(document, testobjects.getPozycje(d.getPozycje()),97,1);
//            uzupelnijDlaVAT7(document, d, wpisView);
//            finalizacjaDokumentuQR(document,nazwa);
//            String f = "pokazwydruk('"+nazwa+"');";
//            PrimeFaces.current().executeScript(f);
//        } catch(Exception e) {
//            document.close();
//        }
//    }
    
     public static void drukujVATUE(PodatnikDAO podatnikDAO, DeklaracjavatUE d, WpisView wpisView, Podatnik podatnik) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        String nazwapliku = "VATUEDetale" + podatnik.getNip() + ".pdf";
        try {
            List<Parametr> param = podatnik.getVatokres();
            //problem kwartalu
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
            writer.setInitialLeading(16);
            document.addTitle("Deklaracha VAT-UE"+" dokumenty");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk deklaracji VAT-UE dokumenty");
            document.addKeywords("VATUE PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            //naglowek
            absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
            prost(writer.getDirectContent(), 12, 817, 560, 10);
            //stopka
            absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
            absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
            prost(writer.getDirectContent(), 12, 15, 560, 20);
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 12);
            Font fontM = new Font(helvetica, 10);
            Font fontS = new Font(helvetica, 6);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            formatter.setMaximumFractionDigits(2);
            formatter.setMinimumFractionDigits(2);
            formatter.setGroupingUsed(true);
            PdfMain.dodajOpisWstepny(document, "Deklaracja VAT-UE firma: ", podatnik, d.getMiesiac(), d.getRok());
            int lp = 1;
            for (VatUe p : d.getPozycje()) {
                PdfPTable table = new PdfPTable(7);
                table.setWidthPercentage(95);
                if (!p.getTransakcja().equals("podsumowanie") && p.getKontrahent()!=null) {
                    table.setWidths(new int[]{1, 2, 2, 3, 5, 3, 2});
                    table.addCell(ustawfraze("lp", 0, 1));
                    table.addCell(ustawfraze("Transakcja", 0, 1));
                    table.addCell(ustawfraze("Kod kraju", 0, 1));
                    table.addCell(ustawfraze("NIP", 0, 1));
                    table.addCell(ustawfraze("Kontrahent", 0, 1));
                    table.addCell(ustawfraze("netto", 0, 1));
                    table.addCell(ustawfraze("ilość dok.", 0, 1));
                    table.setHeaderRows(1);
                    table.addCell(ustawfrazeAlign(String.valueOf(lp++), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getTransakcja(), "center", 8));
                    String kod = p.getKontrahent().getKrajkod() != null ? p.getKontrahent().getKrajkod() : "brak";
                    table.addCell(ustawfrazeAlign(kod, "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontrahent().getNip(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontrahent().getNpelna(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getNetto())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLiczbadok()), "center", 8));
                    document.add(table);
                    if (p.getZawiera() != null && p.getZawiera().size()>0) {
                    document.add(PdfVatUE.createsubtable(p.getZawiera()));
                    } else if (p.getZawierafk() != null && p.getZawierafk().size()>0){
                        document.add(PdfVatUE.createsubtablefk(p.getZawierafk()));
                    }
                    document.add(Chunk.NEWLINE);
                }
            }
            uzupelnijDlaVAT7(document, d, wpisView);
            document.close();
            finalizacjaDokumentuQR(document, nazwapliku);
        } catch (Exception e) {
            document.close();
            E.e(e);
        }
        PrimeFaces.current().executeScript("wydrukvatue('" + podatnik.getNip() + "');");
    }

    private static void uzupelnijDlaVAT7(Document document, DeklaracjavatUE d, WpisView wpisView) {
        if (d.getUpo() == null || d.getUpo().equals("")) {
            try {
                document.add(new Chunk());
                document.add(new Paragraph(new Phrase("Deklaracja przygotowana do wysłania", ft[1])));
                document.add(new Paragraph(new Phrase("Urząd Skarbowy: "+d.getKodurzedu(), ft[1])));
                document.add(new Paragraph(new Phrase("Sporządzający: "+d.getSporzadzil(), ft[1])));
            } catch (DocumentException ex) {
                // Logger.getLogger(PdfVATUEdekl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                document.add(new Chunk());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String data = sdf.format(d.getDatazlozenia());
                document.add(new Paragraph(new Phrase("Deklaracja wysłana dnia: "+data, ft[1])));
                document.add(new Paragraph(new Phrase("Sporządzający: "+d.getSporzadzil(), ft[1])));
                document.add(new Paragraph(new Phrase("Status: "+d.getStatus(), ft[1])));
                document.add(new Paragraph(new Phrase("Urząd Skarbowy: "+d.getKodurzedu(), ft[1])));
                document.add(new Paragraph(new Phrase("Identyfikator: "+d.getIdentyfikator(), ft[1])));
                if (d.getDataupo() != null) {
                    data = sdf.format(d.getDataupo());
                    document.add(new Paragraph(new Phrase("Data UPO: "+data, ft[1])));
                }
                int odo = d.getUpo().indexOf("<DataWplyniecia>");
                int doo = d.getUpo().indexOf("</Potwierdzenie>");
                document.add(new Paragraph(new Phrase("UPO: "+d.getUpo().substring(odo, doo), ft[1])));
            } catch (DocumentException ex) {
                // Logger.getLogger(PdfVATUEdekl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
