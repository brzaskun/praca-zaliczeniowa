/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujProcent;
import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeSpanFont;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.SaldoKonto;
import entityfk.StronaWiersza;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import view.WpisView;
import viewfk.SymulacjaWynikuView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfSymulacjaWyniku {
    
    public static void drukuj(List<SaldoKonto> listakontaprzychody, List<SaldoKonto> listakontakoszty, List<SymulacjaWynikuView.PozycjeSymulacji> listapozycjisymulacji, 
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, WpisView wpisView, int rodzajdruku, List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty) {
        try {
            String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/symulacjawyniku-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            drukujcd(listakontaprzychody, listakontakoszty, listapozycjisymulacji, pozycjeObliczeniaPodatku, wpisView, rodzajdruku, pozycjeDoWyplaty);
            Msg.msg("Wydruk zestawienia symulacja wyniku");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia symulacja wyniku");

        }
    }

    private static void drukujcd(List<SaldoKonto> listakontaprzychody, List<SaldoKonto> listakontakoszty, List<SymulacjaWynikuView.PozycjeSymulacji> listapozycjisymulacji,
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, WpisView wpisView, int rodzajdruku, List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty)  throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/symulacjawyniku-" + wpisView.getPodatnikWpisu() + ".pdf"));
        document.addTitle("Zestawienie symulacja wyniku");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie symulacja wyniku finansowego");
        document.addKeywords("Wynik Finansowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        document.setPageSize(PageSize.A4);
        document.add(tablica(wpisView, listakontaprzychody, "p", rodzajdruku));
        document.add(tablica(wpisView, listakontakoszty, "k", rodzajdruku));
        document.add(tablica2(listapozycjisymulacji));
        document.add(tablica3(pozycjeObliczeniaPodatku));
        document.add(tablica4(pozycjeDoWyplaty, 3));
        document.close();
        Msg.msg("i", "Wydrukowano symulację wyniku finansowego");
    }

    private static PdfPTable tablica(WpisView wpisView, List<SaldoKonto> listakonta, String pk, int rodzajdruku) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(8);
        table.setWidths(new int[]{1, 2, 8, 2, 2, 2, 2, 2});
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikWpisu(), 3, 0));
            if (pk.equals("p")) {
                table.addCell(ustawfraze("zestawienie kont przychodowych za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 5, 0));
            } else {
                table.addCell(ustawfraze("zestawienie kont kosztowych za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 5, 0));
            }

            table.addCell(ustawfraze("lp", 0, 1));
            table.addCell(ustawfraze("nr konta", 0, 1));
            table.addCell(ustawfraze("nazwa konta", 0, 1));
            table.addCell(ustawfraze("obroty Wn", 0, 1));
            table.addCell(ustawfraze("obroty Ma", 0, 1));
            table.addCell(ustawfraze("saldo Wn", 0, 1));
            table.addCell(ustawfraze("saldo Ma", 0, 1));
            table.addCell(ustawfraze("uwagi", 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie symulacja wyniku finansowego", 8, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 1;
        for (SaldoKonto rs : listakonta) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujWaluta(rs.getObrotyWn()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujWaluta(rs.getObrotyMa()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujWaluta(rs.getSaldoWn()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujWaluta(rs.getSaldoMa()) : "", "right", 7));
            table.addCell(ustawfrazeAlign("", "right", 7));
            if (rodzajdruku==2) {
                PdfPTable p = subtable(rs.getZapisy());
                PdfPCell r = new PdfPCell(p);
                r.setColspan(8);
                table.addCell(r);
            }
        }
        return table;
    }

    private static PdfPTable subtable(List<StronaWiersza> stronywiersza) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(8);
        table.setWidths(new int[]{1, 2, 2, 2, 4, 3, 2, 2});
        table.setWidthPercentage(95);
        try {
            table.addCell(ustawfrazeSpanFont("", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("dokument", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("data", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("nr własny", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kontrahent", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("wiersz", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kwota Wn", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kwota Ma", 0, 1, 7));

            table.setHeaderRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (StronaWiersza rs : stronywiersza) {
            table.addCell(ustawfrazeAlign("", "left", 6));
            table.addCell(ustawfrazeAlign(rs.getDokfkS(), "center", 6));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getDatadokumentu(), "left", 6));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getNumerwlasnydokfk(), "left", 6));
            String kontr = rs.getWiersz().geteVatwpisFK() == null ? rs.getDokfk().getKontr().getNpelna() : rs.getWiersz().geteVatwpisFK().getKlient().getNpelna();
            table.addCell(ustawfrazeAlign(kontr, "left", 6));
            table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 6));
            if (rs.getWnma().equals("Wn")) {
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 6));
                table.addCell(ustawfrazeAlign("", "right", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 6));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 6));
            }
        }
        return table;
    }

   private static PdfPTable tablica2(List<SymulacjaWynikuView.PozycjeSymulacji> listapozycjisymulacji) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{4, 1});
        table.setWidthPercentage(50);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze("obliczenie wyniku fin. i pod.", 2, 0));

            table.addCell(ustawfraze("opis", 0, 1));
            table.addCell(ustawfraze("kwota", 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie podatku", 2, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (SymulacjaWynikuView.PozycjeSymulacji rs : listapozycjisymulacji) {
            table.addCell(ustawfrazeAlign(rs.getNazwa(), "left", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWartosc()), "right", 7));
        }
        return table;
    }
   
   private static PdfPTable tablica3(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{4, 1});
        table.setWidthPercentage(50);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze("obliczenie podatku dochodowego", 2, 0));

            table.addCell(ustawfraze("opis", 0, 1));
            table.addCell(ustawfraze("kwota", 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie podatku", 2, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (SymulacjaWynikuView.PozycjeSymulacji rs : pozycjeObliczeniaPodatku) {
            table.addCell(ustawfrazeAlign(rs.getNazwa(), "left", 7));
            if (rs.getNazwa().contains("#")) {
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWartosc()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign(formatujProcent(rs.getWartosc()), "right", 7));
            }
        }
        return table;
    }
   private static PdfPTable tablica4(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty, int i) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{4, 1});
        table.setWidthPercentage(50);
        table.setSpacingBefore(15);
        try {
            if (i == 3) {
                table.addCell(ustawfraze("obliczenie kwot do wypłaty", 2, 0));
            }
            table.addCell(ustawfraze("opis", 0, 1));
            table.addCell(ustawfraze("kwota", 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie kwoty do wypłaty", 6, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (SymulacjaWynikuView.PozycjeSymulacji rs : pozycjeDoWyplaty) {
            table.addCell(ustawfrazeAlign(rs.getNazwa(), "left", 7));
            if (rs.getNazwa().contains("#")) {
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWartosc()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign(formatujProcent(rs.getWartosc()), "right", 7));
            }
        }
        return table;
    }
}
