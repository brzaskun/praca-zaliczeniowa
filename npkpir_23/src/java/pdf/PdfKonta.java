/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;



import static beansPdf.PdfFont.formatujLiczba;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlignColor;
import static beansPdf.PdfFont.ustawfrazeSpanFont;
import static beansPdf.PdfFont.ustawparagraf;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.StronaWierszacomparator;
import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import msg.B;
import msg.Msg;import static pdffk.PdfMain.inicjacjaA4Landscape;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.Z;

/**
 *
 * @author Osito
 */

public class PdfKonta {
    
    public static void drukuj(List<SaldoKonto> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1, String mc, List<SaldoKonto> sumaSaldoKonto) {
        try {
            //Collections.sort(listaSaldoKonto, new SaldoKontocomparator());
            String nazwapliku=null;
            if (analit0synt1==0) {
                nazwapliku = "konta_analityczne-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                if (rodzajdruku==4) {
                    nazwapliku = "konta_analityczne_saldaniezerowe-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                }
                if (rodzajdruku==2) {
                    nazwapliku = "zapisy_na_analityce-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                }
            } else {
                nazwapliku = "konta_syntetyczne-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                if (rodzajdruku==4) {
                    nazwapliku = "konta_syntetyczne_saldaniezerowe-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                }
                if (rodzajdruku==2) {
                    nazwapliku = "zapisy_na_syntetyce-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                }
            }
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            if (rodzajdruku == 5) {
                drukujcdWal(listaSaldoKonto, wpisView, rodzajdruku, analit0synt1, mc, sumaSaldoKonto, nazwapliku);
            } else {
                drukujcd(listaSaldoKonto, wpisView, rodzajdruku, analit0synt1, mc, sumaSaldoKonto, nazwapliku);
            }
            pdffk.PdfMain.dodajQR(nazwapliku);
            PrimeFaces.current().executeScript("pokazwydrukpdf('"+nazwapliku+"')");
            Msg.msg("Wydruk zestawienia obrotów sald");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia obrotów sald");

        }
    }

    private static void drukujcd(List<SaldoKonto> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1, String mc, List<SaldoKonto> sumaSaldoKonto, String nazwapliku)  throws DocumentException, FileNotFoundException, IOException {
        Document document = inicjacjaA4Landscape();
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(595, 842, 0, 0));
        writer.setPageEvent(headerfoter);
        document.addTitle("Zestawienie obroty sald");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie obroty sald");
        document.addKeywords("Wynik Finansowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        if (analit0synt1==1) {
                document.add(ustawparagraf(wpisView.getPodatnikObiekt().getNazwapelnaPDF()+" NIP "+wpisView.getPodatnikObiekt().getNip()+" "+B.b("zestawienieobrotówkontsyntetycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt()));
            } else {
                document.add(ustawparagraf(wpisView.getPodatnikObiekt().getNazwapelnaPDF()+" NIP "+wpisView.getPodatnikObiekt().getNip()+" "+B.b("zestawienieobrotówkontanalitycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt()));
            }
        document.add(Chunk.NEWLINE);
        if (rodzajdruku==1 || rodzajdruku==4) {
            document.add(tablicabezdok(wpisView, listaSaldoKonto, rodzajdruku, analit0synt1));
        }
        if (rodzajdruku==2) {
            int i = 1;
            for (SaldoKonto rs : listaSaldoKonto) {
                PdfPTable tDuza = tablica(rs, i++);
                if (rs.getZapisy() != null && rs.getZapisy().size() > 0) {
                    PdfPTable tMala = subtable(rs.getZapisy());
                    tMala.setSpacingAfter(15);
                    document.add(tDuza);
                    document.add(tMala);
                } else {
                    tDuza.setSpacingAfter(15);
                    document.add(tDuza);
                }
            }
        }
        if (rodzajdruku==3) {
            int i = 1;
            for (SaldoKonto rs : listaSaldoKonto) {
                PdfPTable tDuza = tablica(rs, i++);
                List<StronaWiersza> zapisymc = wyluskajzapisymc(rs.getZapisy(),mc);
                if (zapisymc.size() > 0) {
                    PdfPTable tMala = subtable(zapisymc);
                    tMala.setSpacingAfter(15);
                    document.add(tDuza);
                    document.add(tMala);
                } else {
                    tDuza.setSpacingAfter(15);
                    document.add(tDuza);
                }
            }
        }
        document.add(tablicasuma(sumaSaldoKonto));
        document.close();
    }
    
    private static void drukujcdWal(List<SaldoKonto> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1, String mc, List<SaldoKonto> sumaSaldoKonto, String nazwapliku)  throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 20,20,40,30);
        PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(595, 842, 0, 0));
        writer.setPageEvent(headerfoter);
        document.addTitle("Zestawienie obroty sald");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie obroty sald");
        document.addKeywords("Wynik Finansowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        if (analit0synt1==1) {
                document.add(ustawparagraf(wpisView.getPodatnikObiekt().getNazwapelnaPDF()+" NIP "+wpisView.getPodatnikObiekt().getNip()+" "+B.b("zestawienieobrotówkontsyntetycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt()));
            } else {
                document.add(ustawparagraf(wpisView.getPodatnikObiekt().getNazwapelnaPDF()+" NIP "+wpisView.getPodatnikObiekt().getNip()+" "+B.b("zestawienieobrotówkontanalitycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt()));
            }
        document.add(Chunk.NEWLINE);
        document.add(tablicabezdokWal(wpisView, listaSaldoKonto, rodzajdruku, analit0synt1));
        document.add(tablicasuma(sumaSaldoKonto));
        document.close();
    }
    
    private static PdfPTable tablicasuma(List<SaldoKonto> listasuma) throws DocumentException, IOException {
        SaldoKonto rs = listasuma.get(0);
        PdfPTable table = new PdfPTable(11);
        table.setWidths(new int[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        table.setWidthPercentage(80);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze("", 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWnNar"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMaNar"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.setHeaderRows(1);
        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
            table.addCell(ustawfrazeAlign(B.b("suma"), "left", 8));
            table.addCell(ustawfrazeAlign(rs.getBoWn()!= 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWnMc()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMaMc()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 8));
        return table;
    }

    private static PdfPTable tablica(SaldoKonto rs, int i) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(13);
        table.setWidths(new int[]{1, 2, 5, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        table.setWidthPercentage(98);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(B.b("lp"), 0, 1));
            table.addCell(ustawfraze(B.b("numerkonta"), 0, 1));
            table.addCell(ustawfraze(B.b("nazwakonta"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWnNar"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMaNar"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.setHeaderRows(1);
        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8, 20f));
            table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 8));
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            String l = locale.getLanguage();
            if (l.equals("pl")) {
                table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 8));
            } else {
                table.addCell(ustawfrazeAlign(rs.getKonto().getDe(), "left", 8));
            }
            table.addCell(ustawfrazeAlign(rs.getBoWn()!= 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 8));
        return table;
    }

    private static PdfPTable subtable(List<StronaWiersza> stronywiersza) throws DocumentException, IOException {
        Collections.sort(stronywiersza, new StronaWierszacomparator());
        PdfPTable table = new PdfPTable(10);
        table.setWidths(new int[]{3, 2, 2, 6, 1, 2, 2, 2, 2, 2});
        table.setWidthPercentage(92);
        table.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.setSpacingAfter(15);
        try {
            table.addCell(ustawfrazeSpanFont(B.b("dokument"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("data"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("numerwlasny"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("opis"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("waluta"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("kwotaWn"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("kwotaMa"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("kwotaWnPLN"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("kwotaMaPLN"), 0, 1, 8));
            table.addCell(ustawfrazeSpanFont(B.b("konto"), 0, 1, 8));

            table.setHeaderRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (StronaWiersza rs : stronywiersza) {
            table.addCell(ustawfrazeAlign(rs.getDokfkS(), "left", 6, 18f));
            if (rs.getDokfk().getRodzajedok() != null && rs.getDokfk().getRodzajedok().getKategoriadokumentu()== 0) {
                table.addCell(ustawfrazeAlign(rs.getDataWierszaPelna(), "left", 8));
            } else {
                table.addCell(ustawfrazeAlign(rs.getDokfk().getDatadokumentu(), "left", 8));
            }
            table.addCell(ustawfrazeAlign(rs.getDokfk().getNumerwlasnydokfk(), "left", 8));
            table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 8));
            String waluta = rs.getSymbolWalutBOiSW().equals("PLN") ? "" : rs.getSymbolWalutBOiSW();
            table.addCell(ustawfrazeAlign(waluta, "center", 8));
            if (rs.getWnma().equals("Wn")) {
                table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwota()), "right", 8));
                table.addCell(ustawfrazeAlign("", "right", 8));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 8));
                table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwota()), "right", 8));
            }
            if (waluta.equals("PLN")) {
               if (rs.getWnma().equals("Wn")) {
                   table.addCell(ustawfrazeAlign("", "right", 8));
                   table.addCell(ustawfrazeAlign("", "right", 8));
               } else {
                   table.addCell(ustawfrazeAlign("", "right", 8));
                   table.addCell(ustawfrazeAlign("", "right", 8));
               }   
            } else {
               if (rs.getWnma().equals("Wn")) {
                   table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwotaPLN()), "right", 8));
                   table.addCell(ustawfrazeAlign("", "right", 8));
               } else {
                   table.addCell(ustawfrazeAlign("", "right", 8));
                   table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwotaPLN()), "right", 8));
               }   
            }
            if (rs.getDrugaStrona() != null) {
                table.addCell(ustawfrazeAlign(rs.getDrugaStrona().getKonto().getPelnynumer(), "left", 8));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 8));
            }
        }
        return table;
    }
    private static PdfPTable tablicabezdok(WpisView wpisView, List<SaldoKonto> listaSaldoKonto, int rodzajdruku, int analit0synt1) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(14);
        table.setWidths(new int[]{1, 2, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 4});
        table.setWidthPercentage(98);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 5, 0));
            if (analit0synt1==1) {
                table.addCell(ustawfraze(B.b("zestawienieobrotówkontsyntetycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 9, 0));
            } else {
                table.addCell(ustawfraze(B.b("zestawienieobrotówkontanalitycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 9, 0));
            }
            table.addCell(ustawfraze(B.b("lp"), 0, 1));
            table.addCell(ustawfraze(B.b("numerkonta"), 0, 1));
            table.addCell(ustawfraze(B.b("nazwakonta"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWnNar"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMaNar"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.addCell(ustawfraze(B.b("kontosyntetyczne"), 0, 1));
            if (analit0synt1==1) {
                table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald syntetycznych", 14, 0, 5));
            } else {
                table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald analitycznych", 14, 0, 5));
            }

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 1;
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String l = locale.getLanguage();
        if (l.equals("pl")) {

        } else {

        }
        for (SaldoKonto rs : listaSaldoKonto) {
            if (rodzajdruku == 4 && (Z.z(rs.getSaldoWn()) != 0.0 || Z.z(rs.getSaldoMa()) != 0.0)) { 
                dodajwiersztabeli(rs, table, i, l);
                i++;
            } else if (rodzajdruku != 4){
                dodajwiersztabeli(rs, table, i, l);
                i++;
            }
        }
        return table;
    }
    
    private static PdfPTable tablicabezdokWal(WpisView wpisView, List<SaldoKonto> listaSaldoKonto, int rodzajdruku, int analit0synt1) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(14);
        table.setWidths(new int[]{1, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 5});
        table.setWidthPercentage(100);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 5, 0));
            if (analit0synt1==1) {
                table.addCell(ustawfraze(B.b("zestawienieobrotówkontsyntetycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 9, 0));
            } else {
                table.addCell(ustawfraze(B.b("zestawienieobrotówkontanalitycznych")+": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 9, 0));
            }
            table.addCell(ustawfraze(B.b("lp"), 0, 1));
            table.addCell(ustawfraze(B.b("numerkonta"), 0, 1));
            table.addCell(ustawfraze(B.b("nazwakonta"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.addCell(ustawfraze(B.b("nazwakonta"), 0, 1));
            if (analit0synt1==1) {
                table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald syntetycznych", 14, 0, 5));
            } else {
                table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald analitycznych", 14, 0, 5));
            }

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 1;
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String l = locale.getLanguage();
        if (l.equals("pl")) {

        } else {

        }
        for (SaldoKonto rs : listaSaldoKonto) {
            if (Z.z(rs.getSaldoWn()) != 0.0 || Z.z(rs.getSaldoMa()) != 0.0) { 
                dodajwiersztabeliWal(rs, table, i, l);
                i++;
            }
        }
        return table;
    }
    
     private static void dodajwiersztabeli(SaldoKonto rs, PdfPTable table, Integer i, String l) {
         table.addCell(ustawfrazeAlign(i, "center", 9));
         table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 9, 20f));
         if (l.equals("pl")) {
             table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 9));
         } else if (rs.getKonto().getDe() == null || rs.getKonto().getDe().equals("")) {
             table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 9));
         } else {
             table.addCell(ustawfrazeAlign(rs.getKonto().getDe(), "left", 9));
         }
         table.addCell(ustawfrazeAlign(rs.getBoWn() != 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 9));
         table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 9));
         table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWnMc()) : "", "right", 9));
         table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMaMc()) : "", "right", 9));
         table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyWn()) : "", "right", 8));
         table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyMa()) : "", "right", 8));
         table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 8));
         table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 8));
         String kolor = pobierzkolor(rs.getKonto(), rs.getSaldoWn(), rs.getSaldoMa());
         table.addCell(ustawfrazeAlignColor(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 9, kolor));
         table.addCell(ustawfrazeAlignColor(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 9, kolor));
         if (rs.getKonto().getKontomacierzyste() != null) {
            if (l.equals("pl")) {
                table.addCell(ustawfrazeAlign(rs.getKonto().getKontomacierzyste().getNazwapelna(), "left", 8));
            } else if (rs.getKonto().getKontomacierzyste().getDe() == null || rs.getKonto().getKontomacierzyste().getDe().equals("")) {
                table.addCell(ustawfrazeAlign(rs.getKonto().getKontomacierzyste().getNazwapelna(), "left", 8));
            } else {
                table.addCell(ustawfrazeAlign(rs.getKonto().getKontomacierzyste().getDe(), "left", 8));
            }
         } else {
                table.addCell(ustawfrazeAlign("", "left", 9));
        }
         
    }
    
    private static void dodajwiersztabeliWal(SaldoKonto rs, PdfPTable table, Integer i, String l) {
        table.addCell(ustawfrazeAlign(i, "center", 9));
            table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 9, 20f));
            if (l.equals("pl")) {
                table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 9));
            } else {
                if (rs.getKonto().getDe() == null || rs.getKonto().getDe().equals("")) {
                    table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 9));
                } else {
                    table.addCell(ustawfrazeAlign(rs.getKonto().getDe(), "left", 9));
                }
            }
            table.addCell(ustawfrazeAlign(rs.getBoWn()!= 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWn()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMa()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 9));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 9));
            if (rs.getKonto().getKontomacierzyste() != null) {
                if (l.equals("pl")) {
                    table.addCell(ustawfrazeAlign(rs.getKonto().getKontomacierzyste().getNazwapelna(), "left", 8));
                } else if (rs.getKonto().getKontomacierzyste().getDe() == null || rs.getKonto().getKontomacierzyste().getDe().equals("")) {
                    table.addCell(ustawfrazeAlign(rs.getKonto().getKontomacierzyste().getNazwapelna(), "left", 8));
                } else {
                    table.addCell(ustawfrazeAlign(rs.getKonto().getKontomacierzyste().getDe(), "left", 8));
                }
            } else {
                table.addCell(ustawfrazeAlign("", "left", 9));
            }
    }
    
    public static void main(String[] args) {
        try {
            Document document = new Document(PageSize.A4, 10,10,20,20);
            String nazwapliku = "konta-Testowy.pdf";
            PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            document.addTitle("Zestawienie obroty sald");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Zestawienie obroty sald");
            document.addKeywords("Wynik Finansowy, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            document.add(ustawparagraf("LOLO zestawienie obrotów kont syntetycznych za okres: 05/2015"));
            document.add(Chunk.NEWLINE);
            int limit = 2;
            for (int i = 0; i < limit; i++) {
                PdfPTable table = new PdfPTable(11);
                table.setWidths(new int[]{1, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2});
                table.setWidthPercentage(95);
                table.addCell(ustawfraze("testowy", 3, 0));
                table.addCell(ustawfraze("zestawienie obrotów kont za okres: ", 9, 0));
                table.addCell(ustawfraze("lp", 0, 1));
                table.addCell(ustawfraze("nr konta", 0, 1));
                table.addCell(ustawfraze("nazwa konta", 0, 1));
                table.addCell(ustawfraze("saldo BO Wn", 0, 1));
                table.addCell(ustawfraze("saldo BO Ma", 0, 1));
                table.addCell(ustawfraze("obroty Wn", 0, 1));
                table.addCell(ustawfraze("obroty Ma", 0, 1));
                table.addCell(ustawfraze("suma BO Wn", 0, 1));
                table.addCell(ustawfraze("suma BO Ma", 0, 1));
                table.addCell(ustawfraze("saldo Wn", 0, 1));
                table.addCell(ustawfraze("saldo Ma", 0, 1));
                //table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald analitycznych", 12, 0, 5));
                table.setHeaderRows(1);
                //table.setFooterRows(1);
    //            int limit = 1;
    //            for (int i = 0; i < limit; i++) {
    //                stworzliste(table, String.valueOf(i));
    //                if (2==2) {
    //                    PdfPTable p = subtable();
    //                    PdfPCell r = new PdfPRow(p);
    //                    r.setColspan(10);
    //                    table.addCell(r);
    //                }
    //            }
                document.add(table);
                document.add(subtable());
            }
            document.close();
        } catch (Exception ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    private static void stworzliste(PdfPTable table, String i) {
//        table.addCell(ustawfraze(i, 0, 1));
//        table.addCell(ustawfraze("nr konta", 0, 1));
//        table.addCell(ustawfraze("nazwa konta", 0, 1));
//        table.addCell(ustawfraze("saldo BO Wn", 0, 1));
//        table.addCell(ustawfraze("saldo BO Ma", 0, 1));
//        table.addCell(ustawfraze("obroty Wn", 0, 1));
//        table.addCell(ustawfraze("obroty Ma", 0, 1));
//        table.addCell(ustawfraze("suma BO Wn", 0, 1));
//        table.addCell(ustawfraze("suma BO Ma", 0, 1));
//        table.addCell(ustawfraze("saldo Wn", 0, 1));
//        table.addCell(ustawfraze("saldo Ma", 0, 1));
//    }
//    
     private static PdfPTable subtable() {
        PdfPTable table = new PdfPTable(10);
        try {
            table.setWidths(new int[]{2, 2, 2, 4, 3, 1, 2, 2, 2, 2});
            table.setWidthPercentage(90);
            table.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.setSpacingAfter(15);
            try {
                //table.addCell(ustawfrazeSpanFont("LP", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("dokument", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("data", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("nr własny", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("kontrahent", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("wiersz", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("wal.", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("kwota Wn", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("kwota Ma", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("kwota Wn PLN", 0, 1, 8));
                table.addCell(ustawfrazeSpanFont("kwota Ma PLN", 0, 1, 8));
                table.setHeaderRows(1);
            } catch (IOException ex) {
                Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
            }
            int limit = 2;
            for (int i = 0; i < limit; i++) {
                table.addCell(ustawfrazeAlign(String.valueOf(i), "left", 8));
                //table.addCell(ustawfrazeAlign("", "left", 8));
                table.addCell(ustawfrazeAlign("233", "center", 8));
                table.addCell(ustawfrazeAlign("2015-05-05", "left", 8));
                table.addCell(ustawfrazeAlign("155/4545", "left", 8));
                table.addCell(ustawfrazeAlign("Manolis GmbH", "left", 8));
                table.addCell(ustawfrazeAlign("opis wiersza", "left", 8));
                table.addCell(ustawfrazeAlign("EUR", "center", 8));
                table.addCell(ustawfrazeAlign(formatujLiczba(21200.0), "right", 8));
                table.addCell(ustawfrazeAlign(formatujLiczba(2333.0), "right", 8));
                table.addCell(ustawfrazeAlign("", "right", 8));
            }
        } catch (Exception e) {
            
        }
        return table;
    }

    private static List<StronaWiersza> wyluskajzapisymc(List<StronaWiersza> zapisy, String mc) {
       List<StronaWiersza> zmodyfikowana = Collections.synchronizedList(new ArrayList<>());
       for (StronaWiersza p : zapisy) {
           if (p.getDokfk().getMiesiac().equals(mc)) {
               zmodyfikowana.add(p);
           }
       }
       return zmodyfikowana;
    }

    private static String pobierzkolor(Konto konto, double saldoWn, double saldoMa) {
        String color = "black";
        int stronawlasciwe = konto.getWnma0wm1ma2();
        if (stronawlasciwe == 1 && saldoMa != 0.0) {
           color = "red"; 
        }
        if (stronawlasciwe == 2 && saldoWn != 0.0) {
           color = "red"; 
        }
        return color;
    }
  }
