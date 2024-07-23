/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujKurs;
import static beansPdf.PdfFont.formatujLiczba;
import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Parametr;
import embeddablefk.ListaSum;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdffk.PdfDodajTabele;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
 import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class PdfKontoZapisy {

    public static void drukujzapisy(WpisView wpisView, List<StronaWiersza> kontozapisy, Konto wybranekonto, List<ListaSum> listasum, 
            boolean duzy0maly1, boolean pokaztransakcje, String rokod, String mcod, String rokdo, String mcdo)  throws DocumentException, FileNotFoundException, IOException {
        Podatnik pod = wpisView.getPodatnikObiekt();
        Konto konto = wybranekonto;
        final String rok = wpisView.getRokWpisuSt();
        final String mc = wpisView.getMiesiacDo();
        try {
            List<Parametr> param = pod.getVatokres();
            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 20);
            String nazwapliku = "zapiskonto-" + wpisView.getPodatnikWpisu() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
            int liczydlo = 1;
            PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
            writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
            writer.setPageEvent(headerfoter);
            document.addTitle("Zapisy na koncie "+konto.getPelnynumer());
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk zapisów na koncie");
            document.addKeywords("VAT, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (IOException ex) {
                // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
            }
            Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            font = new Font(helvetica, 8);
            document.setPageSize(PageSize.A4);
            PdfPTable table = null;
            if (duzy0maly1 == false) {
                table = new PdfPTable(12);
                table.setWidths(new int[]{1, 2, 2, 2, 1, 3, 5, 2, 2, 2, 2, 1});
            } else {
                table = new PdfPTable(16);
                table.setWidths(new int[]{1, 2, 2, 2, 1, 2, 3, 4, 2, 2, 2, 2, 2, 2, 1, 2});
            }
            table.setWidthPercentage(98);
            if (duzy0maly1 == true) {
                table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
                table.addCell(ustawfraze("zapisy, konto "+konto.getPelnynumer()+" "+konto.getNazwapelna(), 4, 0));
                table.addCell(ustawfraze("firma: "+wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 6, 0));
                table.addCell(ustawfraze("za okres od: "+rokod+"/"+mcod+" do "+rokdo+"/"+mcdo, 2, 0));
            } else {
                table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
                table.addCell(ustawfraze("zapisy, konto "+konto.getPelnynumer()+" "+konto.getNazwapelna(), 3, 0));
                table.addCell(ustawfraze("firma: "+wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 4, 0));
                table.addCell(ustawfraze("za okres od: "+rokod+"/"+mcod+" do "+rokdo+"/"+mcdo, 2, 0));
            }
            table.addCell(ustawfraze("lp", 0, 1));
            table.addCell(ustawfraze("Data wystawienia", 0, 1));
            table.addCell(ustawfraze("Data op.gosp.", 0, 1));
            table.addCell(ustawfraze("Nr dowodu księgowego", 0, 1));
            table.addCell(ustawfraze("Wiersz", 0, 1));
            table.addCell(ustawfraze("Nr własny", 0, 1));
            if (duzy0maly1 == true) {
                table.addCell(ustawfraze("Kontr.", 0, 1));
            }
            table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 1));
            if (duzy0maly1 == true) {
                table.addCell(ustawfraze("Kurs", 0, 1));
                table.addCell(ustawfraze("Tabela", 0, 1));
            }
            table.addCell(ustawfraze("Wn", 0, 1));
            table.addCell(ustawfraze("Wn PLN", 0, 1));
            table.addCell(ustawfraze("Ma", 0, 1));
            table.addCell(ustawfraze("Ma PLN", 0, 1));
            table.addCell(ustawfraze("Waluta", 0, 1));
            if (duzy0maly1 == true) {
                table.addCell(ustawfraze("Konto przec.", 0, 1));
            }
            table.addCell(ustawfrazeAlign("1", "center", 6));
            table.addCell(ustawfrazeAlign("2", "center", 6));
            table.addCell(ustawfrazeAlign("3", "center", 6));
            table.addCell(ustawfrazeAlign("4", "center", 6));
            table.addCell(ustawfrazeAlign("5", "center", 6));
            table.addCell(ustawfrazeAlign("6", "center", 6));
            table.addCell(ustawfrazeAlign("7", "center", 6));
            table.addCell(ustawfrazeAlign("8", "center", 6));
            table.addCell(ustawfrazeAlign("9", "center", 6));
            table.addCell(ustawfrazeAlign("10", "center", 6));
            table.addCell(ustawfrazeAlign("11", "center", 6));
            table.addCell(ustawfrazeAlign("12", "center", 6));
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign("13", "center", 6));
                table.addCell(ustawfrazeAlign("14", "center", 6));
                table.addCell(ustawfrazeAlign("15", "center", 6));
                table.addCell(ustawfrazeAlign("16", "center", 6));
            }
            table.addCell(ustawfrazeAlign("1", "center", 6));
            table.addCell(ustawfrazeAlign("2", "center", 6));
            table.addCell(ustawfrazeAlign("3", "center", 6));
            table.addCell(ustawfrazeAlign("4", "center", 6));
            table.addCell(ustawfrazeAlign("5", "center", 6));
            table.addCell(ustawfrazeAlign("6", "center", 6));
            table.addCell(ustawfrazeAlign("7", "center", 6));
            table.addCell(ustawfrazeAlign("8", "center", 6));
            table.addCell(ustawfrazeAlign("9", "center", 6));
            table.addCell(ustawfrazeAlign("10", "center", 6));
            table.addCell(ustawfrazeAlign("11", "center", 6));
            table.addCell(ustawfrazeAlign("12", "center", 6));
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign("13", "center", 6));
                table.addCell(ustawfrazeAlign("14", "center", 6));
                table.addCell(ustawfrazeAlign("15", "center", 6));
                table.addCell(ustawfrazeAlign("16", "center", 6));
            }
            table.setHeaderRows(3);
            table.setFooterRows(1);
            Integer i = 1;
            for (StronaWiersza rs : kontozapisy) {
                table.addCell(ustawfrazeAlign(i.toString(), "center", 7,19f));
                table.addCell(ustawfrazeAlign(rs.getDokfk().getDatawystawienia(), "center", 7));
                table.addCell(ustawfrazeAlign(rs.getDokfk().getDataoperacji(), "center", 7));
                table.addCell(ustawfrazeAlign(rs.getDokfkS(), "left", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(rs.getWiersz().getIdporzadkowy()), "center", 7));
                if (duzy0maly1 == false) {
                    table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 6));
                } else {
                    table.addCell(ustawfrazeAlign(rs.getDokfk().getNumerwlasnydokfk(), "left", 6));
                    table.addCell(ustawfrazeAlign(rs.getKontrahetZapisy(wpisView.getPodatnikObiekt().getNip()), "left", 6));
                    
                }
                table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 6));
                if (duzy0maly1 == true) {
                    if (rs.getWiersz().getTabelanbp() == null) {
                        table.addCell(ustawfrazeAlign(formatujKurs(rs.getKursBO()), "right", 7));
                        table.addCell(ustawfrazeAlign("zap. BO", "right", 7));
                    } else {
                        if (!rs.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                            table.addCell(ustawfrazeAlign(formatujKurs(rs.getWiersz().getTabelanbp().getKurssredniPrzelicznik()), "right", 7));
                            table.addCell(ustawfrazeAlign(rs.getWiersz().getTabelanbp().getNrtabeli(), "right", 6));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 7));
                            table.addCell(ustawfrazeAlign("", "right", 7));
                        }
                    }
                }
               double kwotawaluta = rs.getKwota();
               double kwotapln = rs.getKwotaPLN();
               if (pokaztransakcje) {
                   kwotawaluta = rs.getPozostaloZapisynakoncie(rok, mc);
                   kwotapln = rs.getPozostaloPLNZapisynakoncie(rok, mc);
               }
                if (duzy0maly1 == false) {
                    if (rs.getWnma().equals("Wn")) {
                        table.addCell(ustawfrazeAlign(formatujLiczba(kwotawaluta), "right", 9));
                        table.addCell(ustawfrazeAlign(formatujWaluta(kwotapln), "right", 9));
                        table.addCell(ustawfrazeAlign("", "right", 9));
                        table.addCell(ustawfrazeAlign("", "right", 9));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 9));
                        table.addCell(ustawfrazeAlign("", "right", 9));
                        table.addCell(ustawfrazeAlign(formatujLiczba(kwotawaluta), "right", 9));
                        table.addCell(ustawfrazeAlign(formatujWaluta(kwotapln), "right", 9));
                    }
                    if (rs.getWiersz().getTabelanbp() == null) {
                        table.addCell(ustawfrazeAlign(rs.getSymbolWalutyBO(), "center", 9));
                    } else {
                        table.addCell(ustawfrazeAlign(rs.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), "center", 9));
                    }
                } else {
                    if (rs.getWnma().equals("Wn")) {
                        table.addCell(ustawfrazeAlign(formatujLiczba(kwotawaluta), "right", 7));
                        table.addCell(ustawfrazeAlign(formatujWaluta(kwotapln), "right", 7));
                        table.addCell(ustawfrazeAlign("", "right", 7));
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                        table.addCell(ustawfrazeAlign("", "right", 7));
                        table.addCell(ustawfrazeAlign(formatujLiczba(kwotawaluta), "right", 7));
                        table.addCell(ustawfrazeAlign(formatujWaluta(kwotapln), "right", 7));
                    }
                    if (rs.getWiersz().getTabelanbp() == null) {
                        table.addCell(ustawfrazeAlign(rs.getSymbolWalutyBO(), "center", 7));
                    } else {
                        table.addCell(ustawfrazeAlign(rs.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), "center", 7));
                    }
                }
                if (duzy0maly1 == true) {
                    if (rs.getWnma().equals("Wn") && rs.getWiersz().getStronaMa() != null) {
                        table.addCell(ustawfrazeAlign(rs.getWiersz().getStronaMa().getKonto().getPelnynumer(), "left", 7));
                    } else if (rs.getWnma().equals("Ma") && rs.getWiersz().getStronaWn() != null) {
                        table.addCell(ustawfrazeAlign(rs.getWiersz().getStronaWn().getKonto().getPelnynumer(), "left", 7));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    }
                }
                i++;
            }
            dodajpodsumowanie(listasum, table, duzy0maly1);
            document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
            document.add(table);
            document.close();
            pdffk.PdfMain.dodajQR(nazwapliku);
            //Msg.msg("i","Wydrukowano ewidencje","form:messages");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajpodsumowanie(List<ListaSum> listasum, PdfPTable table, boolean duzy0maly1) {
        try {
            table.addCell(ustawfrazeAlign("", "center", 7, 25f));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 6));
            table.addCell(ustawfrazeAlign("", "right", 7));
            table.addCell(ustawfrazeAlign("podsumowanie", "left", 6));
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSumaWn()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaWnPLN()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSumaMa()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaMaPLN()), "right", 7));
                
            } else {
                table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSumaWn()), "right", 9));
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaWnPLN()), "right", 9));
                table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSumaMa()), "right", 9));
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaMaPLN()), "right", 9));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            //************
            table.addCell(ustawfrazeAlign("", "center", 7, 25f));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 6));
            table.addCell(ustawfrazeAlign("saldo", "left", 6));
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            double saldo = listasum.get(0).getSaldoWn() > 0.0 ? listasum.get(0).getSaldoWn() : listasum.get(0).getSaldoMa();
            if (listasum.get(0).getSaldoWnPLN() > 0) {
                if (duzy0maly1 == true) {
                    table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSaldoWn()), "right", 7));
                    table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoWnPLN()), "right", 7));
                } else {
                    table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSaldoWn()), "right", 9));
                    table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoWnPLN()), "right", 9));
                }
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            if (listasum.get(0).getSaldoMaPLN() > 0) {
                if (duzy0maly1 == true) {
                    table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSaldoMa()), "right", 7));
                    table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoMaPLN()), "right", 7));
                } else {
                    table.addCell(ustawfrazeAlign(formatujLiczba(listasum.get(0).getSaldoMa()), "right", 9));
                    table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoMaPLN()), "right", 9));
                }
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            table.addCell(ustawfrazeAlign("", "right", 7));
            if (duzy0maly1 == true) {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public static void drukujzapisyKompakt(WpisView wpisView, List<StronaWiersza> kontozapisy, Konto wybranekonto, List<ListaSum> listasum, 
            int opcja, boolean nierenderujkolumnnywalut, boolean pokaztransakcje, String rokod, String mcod, String rokdo, String mcdo)  {
        final String rok = wpisView.getRokWpisuSt();
        final String mc = wpisView.getMiesiacDo();
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"plankont";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (kontozapisy != null && kontozapisy.size() > 0) {
            List<StronaWiersza> nowalista = Collections.synchronizedList(new ArrayList<>());
            nowalista.addAll(kontozapisy);
            if (!nierenderujkolumnnywalut) {
                nowalista.add(new StronaWiersza(listasum, 2, nierenderujkolumnnywalut));
                nowalista.add(new StronaWiersza(listasum, 3, nierenderujkolumnnywalut));
                nowalista.add(new StronaWiersza(listasum, 1, nierenderujkolumnnywalut));
            }
            nowalista.add(new StronaWiersza(listasum, 2, true));
            nowalista.add(new StronaWiersza(listasum, 3, true));
            nowalista.add(new StronaWiersza(listasum, 1, true));
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            PdfMain.dodajOpisWstepnyZapisynakoncie(document, "Zapisy na koncie "+wybranekonto.getNumerNazwa(), wpisView.getPodatnikObiekt(), rokod, mcod, rokdo, mcdo);
            if (wybranekonto.getKontomacierzyste()!=null) {
                PdfMain.dodajLinieOpisuCenter(document, wybranekonto.getNumerNazwaMacierzyste());
            }
            if (pokaztransakcje) {
                PdfDodajTabele.dodajTabele(document, testobjects.testobjects.getKontoZapisy3(nowalista),95,3, rok, mc);
            } else {
                PdfDodajTabele.dodajTabele(document, testobjects.testobjects.getKontoZapisy(nowalista),95,2, rok, mc);
            }
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } else {
            Msg.msg("w", "Nie wybrano Planu kont do wydruku");
        }
    }
    
    public static void main(String[] args) {
        try {
            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 5);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/zapiskonto.pdf"));
            document.addTitle("Zapisy na koncie");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk zapisów na koncie");
            document.addKeywords("VAT, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            BaseFont helvetica = null;
            Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            font = new Font(helvetica, 8);
            document.setPageSize(PageSize.A4);
            PdfPTable table = new PdfPTable(14);
            table.setWidths(new int[]{1, 2, 2, 1, 2, 4, 2, 2, 2, 2, 2, 2, 1, 2});
            table.setWidthPercentage(98);
            try {
                table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 3, 0));
                table.addCell(ustawfraze("wydruk zapisów na koncie ", 4, 0));
                table.addCell(ustawfraze("firma: nazwafirmy" , 5, 0));
                table.addCell(ustawfraze("za okres: 12/2015", 2, 0));
                
                table.addCell(ustawfraze("lp", 0, 1));
                table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 1));
                table.addCell(ustawfraze("Nr dowodu księgowego", 0, 1));
                table.addCell(ustawfraze("Wiersz", 0, 1));
                table.addCell(ustawfraze("Nr własny", 0, 1));
                table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 1));
                table.addCell(ustawfraze("Kurs", 0, 1));
                table.addCell(ustawfraze("Tabela", 0, 1));
                table.addCell(ustawfraze("Wn", 0, 1));
                table.addCell(ustawfraze("Wn PLN", 0, 1));
                table.addCell(ustawfraze("Ma", 0, 1));
                table.addCell(ustawfraze("Ma PLN", 0, 1));
                table.addCell(ustawfraze("Waluta", 0, 1));
                table.addCell(ustawfraze("Konto przec.", 0, 1));

                
                table.addCell(ustawfrazeAlign("1", "center", 6));
                table.addCell(ustawfrazeAlign("2", "center", 6));
                table.addCell(ustawfrazeAlign("3", "center", 6));
                table.addCell(ustawfrazeAlign("4", "center", 6));
                table.addCell(ustawfrazeAlign("5", "center", 6));
                table.addCell(ustawfrazeAlign("6", "center", 6));
                table.addCell(ustawfrazeAlign("7", "center", 6));
                table.addCell(ustawfrazeAlign("8", "center", 6));
                table.addCell(ustawfrazeAlign("9", "center", 6));
                table.addCell(ustawfrazeAlign("10", "center", 6));
                table.addCell(ustawfrazeAlign("11", "center", 6));
                table.addCell(ustawfrazeAlign("12", "center", 6));
                table.addCell(ustawfrazeAlign("13", "center", 6));
                table.addCell(ustawfrazeAlign("14", "center", 6));

                table.addCell(ustawfrazeAlign("1", "center", 6));
                table.addCell(ustawfrazeAlign("2", "center", 6));
                table.addCell(ustawfrazeAlign("3", "center", 6));
                table.addCell(ustawfrazeAlign("4", "center", 6));
                table.addCell(ustawfrazeAlign("5", "center", 6));
                table.addCell(ustawfrazeAlign("6", "center", 6));
                table.addCell(ustawfrazeAlign("7", "center", 6));
                table.addCell(ustawfrazeAlign("8", "center", 6));
                table.addCell(ustawfrazeAlign("9", "center", 6));
                table.addCell(ustawfrazeAlign("10", "center", 6));
                table.addCell(ustawfrazeAlign("11", "center", 6));
                table.addCell(ustawfrazeAlign("12", "center", 6));
                table.addCell(ustawfrazeAlign("13", "center", 6));
                table.addCell(ustawfrazeAlign("14", "center", 6));

                table.setHeaderRows(3);
                table.setFooterRows(1);
            } catch (Exception e) {

            }
            document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
            document.add(table);
            document.close();
        } catch (Exception e) {
            
        }
    }

    
  
}

