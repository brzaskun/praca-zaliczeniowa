/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;



import static beansPdf.PdfFont.formatujLiczba;
import static beansPdf.PdfFont.ustawfraze;
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

/**
 *
 * @author Osito
 */
@Stateless
public class PdfKonta {
    
    public static void drukuj(List<SaldoKonto> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1) {
        try {
            String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/konta-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            drukujcd(listaSaldoKonto, wpisView, rodzajdruku, analit0synt1);
            Msg.msg("Wydruk zestawienia obrotów sald");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia obrotów sald");

        }
    }

    private static void drukujcd(List<SaldoKonto> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1)  throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document(PageSize.A4, 5,5,5,5);
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/konta-" + wpisView.getPodatnikWpisu() + ".pdf"));
        document.addTitle("Zestawienie obroty sald");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie obroty sald");
        document.addKeywords("Wynik Finansowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        document.add(tablica(wpisView, listaSaldoKonto, rodzajdruku, analit0synt1));
        document.close();
        Msg.msg("i", "Wydrukowano symulację wyniku finansowego");
    }

    private static PdfPTable tablica(WpisView wpisView, List<SaldoKonto> listaSaldoKonto, int rodzajdruku, int analit0synt1) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(11);
        table.setWidths(new int[]{1, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2});
        table.setWidthPercentage(95);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikWpisu(), 3, 0));
            if (analit0synt1==1) {
                table.addCell(ustawfraze("zestawienie obrotów kont syntetycznych za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 9, 0));
            } else {
                table.addCell(ustawfraze("zestawienie obrotów kont analitycznych za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 9, 0));
            }
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
            if (analit0synt1==1) {
                table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald syntetycznych", 12, 0, 5));
            } else {
                table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald analitycznych", 12, 0, 5));
            }

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 1;
        for (SaldoKonto rs : listaSaldoKonto) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getBoWn()!= 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getObrotyWn() != 0 ? formatujLiczba(rs.getObrotyWn()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getObrotyMa() != 0 ? formatujLiczba(rs.getObrotyMa()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 7));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 7));
            if (rodzajdruku==2) {
                PdfPTable p = subtable(rs.getZapisy());
                PdfPCell r = new PdfPCell(p);
                r.setColspan(11);
                table.addCell(r);
            }
        }
        return table;
    }

    private static PdfPTable subtable(List<StronaWiersza> stronywiersza) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(11);
        table.setWidths(new int[]{1, 2, 2, 2, 4, 3, 1, 2, 2, 2, 2});
        table.setWidthPercentage(95);
        try {
            table.addCell(ustawfrazeSpanFont("", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("dokument", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("data", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("nr własny", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kontrahent", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("wiersz", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("wal.", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kwota Wn", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kwota Ma", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kwota Wn PLN", 0, 1, 7));
            table.addCell(ustawfrazeSpanFont("kwota Ma PLN", 0, 1, 7));

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
            String waluta = rs.getSymbolWalutyBO() != null ? rs.getSymbolWalutyBO() : rs.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty();
            table.addCell(ustawfrazeAlign(waluta, "center", 6));
            if (rs.getWnma().equals("Wn")) {
                table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwota()), "right", 6));
                table.addCell(ustawfrazeAlign("", "right", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 6));
                table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwota()), "right", 6));
            }
            if (waluta.equals("PLN")) {
               if (rs.getWnma().equals("Wn")) {
                   table.addCell(ustawfrazeAlign("", "right", 6));
                   table.addCell(ustawfrazeAlign("", "right", 6));
               } else {
                   table.addCell(ustawfrazeAlign("", "right", 6));
                   table.addCell(ustawfrazeAlign("", "right", 6));
               }   
            } else {
               if (rs.getWnma().equals("Wn")) {
                   table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwotaPLN()), "right", 6));
                   table.addCell(ustawfrazeAlign("", "right", 6));
               } else {
                   table.addCell(ustawfrazeAlign("", "right", 6));
                   table.addCell(ustawfrazeAlign(formatujLiczba(rs.getKwotaPLN()), "right", 6));
               }   
            }
        }
        return table;
    }

  }
