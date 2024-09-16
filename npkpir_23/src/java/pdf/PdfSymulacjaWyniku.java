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
import static beansPdf.PdfFont.ustawfrazeSpanFont;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddablefk.PozycjeSymulacjiNowe;
import embeddablefk.SaldoKonto;
import entity.Klienci;
import entityfk.StronaWiersza;
import error.E;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import msg.B;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdffk.PdfMain;
import plik.Plik;
import view.WpisView;
import viewfk.SymulacjaWynikuView;

/**
 *
 * @author Osito
 */

public class PdfSymulacjaWyniku {
    
    public static ByteArrayOutputStream drukuj(List<SaldoKonto> listakontaprzychody, List<SaldoKonto> listakontakoszty, List<PozycjeSymulacjiNowe> listapozycjisymulacji, 
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, WpisView wpisView, int rodzajdruku, List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty, double sumaprzychody, double sumavatprzychody, double sumakoszty, 
            double sumavatkoszty, String mcod, String mcdo) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwapliku = null;
            if (rodzajdruku == 1) {
                nazwapliku = "symulacjawyniku" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
            } else {
                nazwapliku = "symulacjawynikukonta" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
            }
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            out = drukujcd(listakontaprzychody, listakontakoszty, listapozycjisymulacji, pozycjeObliczeniaPodatku, wpisView, rodzajdruku, pozycjeDoWyplaty, sumaprzychody, sumavatprzychody, sumakoszty, sumavatkoszty, mcod, mcdo);
            if (rodzajdruku==1) {
                PrimeFaces.current().executeScript("wydruksymulacjawyniku('"+wpisView.getPodatnikObiekt().getNip()+"', 1);");
            } else if (rodzajdruku==2) {
                PrimeFaces.current().executeScript("wydruksymulacjawyniku('"+wpisView.getPodatnikObiekt().getNip()+"', 2);");
            }
            Msg.msg("Wydruk zestawienia symulacja wyniku");
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }

    private static ByteArrayOutputStream drukujcd(List<SaldoKonto> listakontaprzychody, List<SaldoKonto> listakontakoszty, List<PozycjeSymulacjiNowe> listapozycjisymulacji,
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, WpisView wpisView, int rodzajdruku, List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty, double sumaprzychody, double sumavatprzychody, double sumakoszty, 
            double sumavatkoszty, String mcod, String mcdo) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwapliku = "";
            if (rodzajdruku == 1) {
                nazwapliku = "symulacjawyniku" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                document = PdfMain.inicjacjaA4Portrait();
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
            } else {
                nazwapliku = "symulacjawynikukonta" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                document = PdfMain.inicjacjaA4Portrait();
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
            }
            document.addTitle(B.b("zestawieniesymulacjawyniku"));
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject(B.b("zestawieniesymulacjawyniku"));
            document.addKeywords("Wynik Finansowy, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            document.setPageSize(PageSize.A4);
            document.add(tablica(wpisView, listakontaprzychody, "p", rodzajdruku, sumaprzychody, sumavatprzychody, mcod, mcdo));
            document.add(tablica(wpisView, listakontakoszty, "k", rodzajdruku, sumakoszty, sumavatkoszty, mcod, mcdo));
            document.add(tablica2(listapozycjisymulacji));
            //nie ma tego od momentu jak przebudowalem tabsy i zmienilem kolejnosc wyswietlania
//        document.add(tablica3(pozycjeObliczeniaPodatku));
//        if (pozycjeDoWyplaty != null) {
//            document.add(tablica4(pozycjeDoWyplaty, 3));
//        }
            document.close();
            Plik.zapiszBufferdoPlik(nazwapliku, out);
            Msg.msg("i", "Wydrukowano symulację wyniku finansowego");
        } catch (Exception e) {
            E.e(e);
            document.close();
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia symulacja wyniku");
        }
        return out;
    }

    private static PdfPTable tablica(WpisView wpisView, List<SaldoKonto> listakonta, String pk, int rodzajdruku, double razemnetto, double razemvat, String mcod, String mcdo) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(9);
        table.setWidths(new int[]{1, 3, 6, 3, 3, 3, 3, 3, 4});
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 3, 0));
            if (pk.equals("p")) {
                table.addCell(ustawfraze(B.b("zapisyprzychodowe") + " za okres: "+mcod+"-" + mcdo + "/" + wpisView.getRokWpisuSt(), 6, 0));
            } else {
                table.addCell(ustawfraze(B.b("zapisykosztowe") + " za okres: "+mcod+"-" + mcdo + "/" + wpisView.getRokWpisuSt(), 6, 0));
            }
            table.addCell(ustawfraze(B.b("lp"), 0, 1));
            table.addCell(ustawfraze(B.b("numerkonta"), 0, 1));
            table.addCell(ustawfraze(B.b("nazwakonta"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.addCell(ustawfraze(B.b("vat"), 0, 1));
            table.addCell(ustawfraze(B.b("kontosyntetyczne"), 0, 1));
            String podsumowanie = "Razem koszty netto: "+formatpdf.F.curr(razemnetto)+" vat: "+formatpdf.F.curr(razemvat);
            if (pk.equals("p")) {
                podsumowanie = "Razem przychody netto: "+formatpdf.F.curr(razemnetto)+" vat: "+formatpdf.F.curr(razemvat);
            }
            table.addCell(ustawfrazeSpanFont(podsumowanie, 9, 0, 8));
            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie symulacja wyniku finansowego", 9, 0, 6));

            table.setHeaderRows(4);
            table.setFooterRows(2);
        } catch (IOException ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 1;
        for (SaldoKonto rs : listakonta) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8, 20f));
            table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 8));
            Locale browserLocale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            if (browserLocale.getLanguage().equals("pl")) {
                table.addCell(ustawfrazeAlign(rs.getKonto().getNazwaKontaInt(), "left", 8));
            } else if (browserLocale.getLanguage().equals("de")) {
                table.addCell(ustawfrazeAlign(rs.getKonto().getDe(), "left", 8));
            }
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujWaluta(rs.getObrotyWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujWaluta(rs.getObrotyMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujWaluta(rs.getSaldoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujWaluta(rs.getSaldoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getVat() != 0 ? formatujWaluta(rs.getVat()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getTopKontoOpis(), "left", 8));
            if (rodzajdruku==2) {
                PdfPTable p = subtable(rs.getZapisy());
                PdfPCell r = new PdfPCell(p);
                r.setColspan(9);
                table.addCell(r);
            }
        }
        return table;
    }

    private static PdfPTable subtable(List<StronaWiersza> stronywiersza) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(8);
        table.setWidths(new int[]{1, 2, 2, 3, 5, 6, 2, 2});
        table.setWidthPercentage(95);
        try {
            table.addCell(ustawfrazeSpanFont("", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("dokument"), 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("data"), 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("numerwlasny"), 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("kontrahent"), 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("wiersz"), 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("kwotaWn"), 0, 1, 7));
            table.addCell(ustawfrazeSpanFont(B.b("kwotaMa"), 0, 1, 7));
            table.setHeaderRows(1);
        } catch (IOException ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (StronaWiersza rs : stronywiersza) {
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfkS(), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getDatadokumentu(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getNumerwlasnydokfk(), "left", 7));
            Klienci klient = (rs.getWiersz().geteVatwpisFK() == null && rs.getDokfk().getKontr() != null) ? rs.getDokfk().getKontr() : rs.getWiersz().geteVatwpisFK() != null && rs.getWiersz().geteVatwpisFK().getKlient() != null ? rs.getWiersz().geteVatwpisFK().getKlient() : null;
            String kontr = klient != null ? klient.getNpelna() : "brak kontrakhenta!";
            table.addCell(ustawfrazeAlign(kontr, "left", 7));
            table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 7));
            if (rs.getWnma().equals("Wn")) {
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 6));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 7));
            }
        }
        return table;
    }

   private static PdfPTable tablica2(List<PozycjeSymulacjiNowe> listapozycjisymulacji) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(12);
        table.setWidths(new int[]{1, 5, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3});
        table.setWidthPercentage(107);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze("", 0, 1));
            table.addCell(ustawfraze(B.b("udziałowiec"), 0, 1));
            table.addCell(ustawfraze(B.b("udział"), 0, 1));
            table.addCell(ustawfraze(B.b("przychody"), 0, 1));
            table.addCell(ustawfraze(B.b("koszty"), 0, 1));
            table.addCell(ustawfraze(B.b("wynikfinansowy"), 0, 1));
            table.addCell(ustawfraze(B.b("nkup"), 0, 1));
            table.addCell(ustawfraze(B.b("kupmn"), 0, 1));
            table.addCell(ustawfraze(B.b("kupmnpopmce"), 0, 1));
            table.addCell(ustawfraze(B.b("pmn"), 0, 1));
            table.addCell(ustawfraze(B.b("pmnpopmce"), 0, 1));
            table.addCell(ustawfraze(B.b("wynikpodatkowy"), 0, 1));
            table.setHeaderRows(1);
        } catch (Exception ex) {
            E.e(ex);
        }
        for (PozycjeSymulacjiNowe rs : listapozycjisymulacji) {
            table.addCell(ustawfrazeAlign(rs.getId(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getUdzialowiec(), "left", 7));
            table.addCell(ustawfrazeAlign(formatujProcent(rs.getUdzial()), "right", 6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getPrzychody()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKoszty()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWynikfinansowy()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNkup()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKupmn()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKupmn_poprzedniemce()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getPmn()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getPmn_poprzedniemce()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWynikpodatkowy()), "right", 7));
        }
        return table;
    }
   
   private static PdfPTable tablica3(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{4, 1});
        table.setWidthPercentage(50);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(B.b("obliczeniepodatku"), 2, 0));

            table.addCell(ustawfraze(B.b("opis"), 0, 1));
            table.addCell(ustawfraze(B.b("kwota"), 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie podatku", 2, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
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
            table.addCell(ustawfraze(B.b("opis"), 0, 1));
            table.addCell(ustawfraze(B.b("kwota"), 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie kwoty do wypłaty", 6, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
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
