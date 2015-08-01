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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Mce;
import embeddablefk.SaldoKontoNarastajaco;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.B;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfKontaNarastajaco {
    
    public static void drukuj(List<SaldoKontoNarastajaco> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1) {
        try {
            String nazwapliku = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/konta-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = new File(nazwapliku);
            if (file.isFile()) {
                file.delete();
            }
            drukujcd(listaSaldoKonto, wpisView, rodzajdruku, analit0synt1);
            Msg.msg("Wydruk zestawienia obrotów sald narastająco");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia obrotów sald");

        }
    }

    private static void drukujcd(List<SaldoKontoNarastajaco> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1)  throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document(PageSize.A3.rotate(), 5,5,5,5);
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/konta-" + wpisView.getPodatnikWpisu() + ".pdf"));
        document.addTitle("Zestawienie obroty sald");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie obroty sald narastająco");
        document.addKeywords("Wynik Finansowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        document.add(tablica(wpisView, listaSaldoKonto, rodzajdruku, analit0synt1));
        document.close();
        Msg.msg("i", "Wydrukowano symulację wyniku finansowego");
    }

    private static PdfPTable tablica(WpisView wpisView, List<SaldoKontoNarastajaco> listaSaldoKonto, int rodzajdruku, int analit0synt1) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(21);
        table.setWidths(new int[]{1, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        table.setWidthPercentage(100);
        int granica = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikWpisu(), 4, 0));
            table.addCell(ustawfraze(B.b("zestawienieobrotówkontanalitycznych")+ ": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 17, 0));
            table.addCell(ustawfraze(B.b("lp"), 0, 1));
            table.addCell(ustawfraze(B.b("numerkonta"), 0, 1));
            table.addCell(ustawfraze(B.b("nazwakonta"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn")+" 01", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa")+" 01", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn")+" 02", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa")+" 02", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn")+" 03", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa")+" 03", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn")+" 04", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa")+" 04", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn")+" 05", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa")+" 05", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyWn")+" 06", 0, 1));
            table.addCell(ustawfraze(B.b("obrotyMa")+" 06", 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("sumaBOMa"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald analitycznych narastająco", 21, 0, 5));
            table.setHeaderRows(3);
            table.setFooterRows(1);
        int i = 1;
        for (SaldoKontoNarastajaco rs : listaSaldoKonto) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
            table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 8));
            table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 7));
            table.addCell(ustawfrazeAlign(rs.getBoWn()!= 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 8));
            for (int j = 0 ; j < 6 ; j ++ ) {
                if (j < granica) {
                    SaldoKontoNarastajaco.Obrotymca numerlisty = rs.getObrotymiesiecy().get(j);
                    table.addCell(ustawfrazeAlign(numerlisty.getObrotyWn() != 0 ? formatujLiczba(numerlisty.getObrotyWn()) : "", "right", 8));
                    table.addCell(ustawfrazeAlign(numerlisty.getObrotyMa() != 0 ? formatujLiczba(numerlisty.getObrotyMa()) : "", "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "right", 7));
                    table.addCell(ustawfrazeAlign("", "right", 7));
                }
            }
            table.addCell(ustawfrazeAlign(rs.getObrotyBoWn() != 0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getObrotyBoMa() != 0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoWn() != 0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(rs.getSaldoMa() != 0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 8));
        }
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }

  }
