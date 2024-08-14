/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeSpanFont;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.MiejsceZest;
import entityfk.StronaWiersza;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import msg.Msg;
import static pdf.PdfVAT7.absText;
import static pdffk.PdfMain.dodajOpisWstepny;
import plik.Plik;
import view.WpisView;
import viewfk.MiejsceKosztowView;

/**
 *
 * @author Osito
 */

public class PdfMiejsceKosztow {

    public static void drukuj(List<MiejsceKosztowView.TabelaMiejsceKosztow> listasummiejsckosztow, WpisView wpisView, int rodzajdruku) {
        try {
            String nazwapliku = "miejscakosztow"+rodzajdruku + wpisView.getPodatnikObiekt().getNip() + ".pdf";
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            drukujcd(listasummiejsckosztow, wpisView, rodzajdruku);
            Msg.msg("Wydruk zestawienia miejsca kosztów");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia miejsca kosztów");

        }
    }

    private static void drukujcd(List<MiejsceKosztowView.TabelaMiejsceKosztow> listasummiejsckosztow, WpisView wpisView, int rodzajdruku) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("miejscakosztow"+rodzajdruku + wpisView.getPodatnikObiekt().getNip() + ".pdf"));
        document.addTitle("Zestawienie miejsce kosztów");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie miejsce kosztów");
        document.addKeywords("Miejsca Kosztów, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        document.setPageSize(PageSize.A4);
        //naglowek
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
        //stopka
        absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
        absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
        dodajOpisWstepny(document, "Zestawienie przychodów wg miejsc w  ",wpisView.getPodatnikObiekt(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        if (listasummiejsckosztow.size() > 0) {
            for (MiejsceKosztowView.TabelaMiejsceKosztow p : listasummiejsckosztow) {
                document.add(tablica(wpisView,p, rodzajdruku));
            }
            document.close();
            Msg.msg("i", "Wydrukowano Miejsca Kosztów");
        } else {
            document.add(new Chunk("Nie wybrano tabeli do wydruku"));
            document.close();
            Msg.msg("e", "Nie wybrano żadnej tabeli do wydruku");
        }
    }

    private static PdfPTable tablica(WpisView wpisView, MiejsceKosztowView.TabelaMiejsceKosztow l, int rodzajdruku) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(6);
        table.setWidths(new int[]{1, 4, 3, 1, 2, 2});
        table.setWidthPercentage(98);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 2, 0));
            table.addCell(ustawfraze(l.getMiejsceKosztow().getOpismiejsca(), 1, 0));
            table.addCell(ustawfraze("", 1, 0));
            table.addCell(ustawfraze("za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 2, 0));

            table.addCell(ustawfraze("", 0, 1));
            table.addCell(ustawfraze("nazwa konta", 0, 1));
            table.addCell(ustawfraze("numer konta", 0, 1));
            table.addCell(ustawfraze("ilość rach.", 0, 1));
            table.addCell(ustawfraze("suma", 0, 1));
            table.addCell(ustawfraze("suma narast.", 0, 1));

            table.setHeaderRows(2);
        } catch (Exception ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer i = 1;
        for (MiejsceZest rs : l.getMiejsceKosztowZest()) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
            table.addCell(ustawfrazeAlign(rs.getKontonazwa(), "left", 8));
            table.addCell(ustawfrazeAlign(rs.getKontonumer(), "left", 8));
            table.addCell(ustawfrazeAlign(String.valueOf(rs.getStronywiersza().size()), "center", 8));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getSumaokres()), "right", 8));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getSumanarastajaco()), "right", 8));
            if (rodzajdruku==2) {
                PdfPTable p = subtable(rs.getStronywiersza());
                PdfPCell r = new PdfPCell(p);
                r.setColspan(6);
                table.addCell(r);
            }
            i++;
        }
        return table;
    }

    private static PdfPTable subtable(List<StronaWiersza> stronywiersza) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(7);
        table.setWidths(new int[]{1, 2, 2, 2, 4, 3, 2});
        table.setWidthPercentage(95);
        try {
            table.addCell(ustawfrazeSpanFont("", 0, 1, 8));
            table.addCell(ustawfrazeSpanFont("dokument", 0, 1, 8));
            table.addCell(ustawfrazeSpanFont("data", 0, 1, 8));
            table.addCell(ustawfrazeSpanFont("nr własny", 0, 1, 8));
            table.addCell(ustawfrazeSpanFont("kontrahent", 0, 1, 8));
            table.addCell(ustawfrazeSpanFont("wiersz", 0, 1, 8));
            table.addCell(ustawfrazeSpanFont("kwota", 0, 1, 8));

            table.setHeaderRows(1);
        } catch (IOException ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (StronaWiersza rs : stronywiersza) {
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfkS(), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getDatadokumentu(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getNumerwlasnydokfk(), "left", 7));
            String kontr = rs.getWiersz().geteVatwpisFK() == null ? rs.getDokfk().getKontr().getNpelna() : rs.getWiersz().geteVatwpisFK().getKlient().getNpelna();
            table.addCell(ustawfrazeAlign(kontr, "left", 7));
            table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 7));
        }
        return table;
    }
}
