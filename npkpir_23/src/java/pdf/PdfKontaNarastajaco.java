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
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPCell;
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
import plik.Plik;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfKontaNarastajaco {
    
    public static void drukuj(List<SaldoKontoNarastajaco> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1, int polowaroku, boolean drukujkategorie, boolean saldaniezerowe) {
        try {
            String nazwapliku = "konta-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            drukujcd(listaSaldoKonto, wpisView, rodzajdruku, analit0synt1, polowaroku, drukujkategorie, saldaniezerowe);
            Msg.msg("Wydruk zestawienia obrotów sald narastająco");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia obrotów sald");

        }
    }

    private static void drukujcd(List<SaldoKontoNarastajaco> listaSaldoKonto, WpisView wpisView, int rodzajdruku, int analit0synt1, int polowaroku, boolean drukujkategorie, boolean saldaniezerowe)  throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document(PageSize.A3.rotate(), 5,5,10,10);
        PdfWriter.getInstance(document, Plik.plikR("konta-" + wpisView.getPodatnikWpisu() + ".pdf"));
        document.addTitle("Zestawienie obroty sald");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie obroty sald narastająco");
        document.addKeywords("Wynik Finansowy, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        if (polowaroku == 1) {
            document.add(tablica(wpisView, listaSaldoKonto, rodzajdruku, analit0synt1, false, drukujkategorie, saldaniezerowe));
        } else {
            document.add(tablica(wpisView, listaSaldoKonto, rodzajdruku, analit0synt1, true, drukujkategorie, saldaniezerowe));
        }
        document.close();
        Msg.msg("i", "Wydrukowano zestawienie obrotów i sald");
    }

    private static PdfPTable tablica(WpisView wpisView, List<SaldoKontoNarastajaco> listaSaldoKonto, int rodzajdruku, int analit0synt1, boolean ppr0dpr1, boolean drukujkategorie, boolean saldaniezerowe) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(21);
        table.setWidths(new int[]{1, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
        table.setWidthPercentage(98);
        int granica = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikWpisu(), 5, 0));
            table.addCell(ustawfraze(B.b("zestawienieobrotówkontanalitycznych")+ ": " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 16, 0));
            table.addCell(ustawfraze(B.b("lp"), 0, 1, 40f));
            table.addCell(ustawfraze(B.b("numerkonta"), 0, 1));
            String nazwa = B.b("nazwakonta").length() > 40 ? B.b("nazwakonta").substring(0,39) : B.b("nazwakonta");
            table.addCell(ustawfraze(nazwa, 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoBOMa"), 0, 1));
            if (ppr0dpr1 == false) {
                naglowki1_6(table);
            } else {
                naglowki7_12(table);
            }
            if (drukujkategorie == true) {
                table.addCell(ustawfraze("", 2, 0));
            } else {
                table.addCell(ustawfraze(B.b("sumaBOWn"), 0, 1));
                table.addCell(ustawfraze(B.b("sumaBOMa"), 0, 1));
            }
            table.addCell(ustawfraze(B.b("saldoWn"), 0, 1));
            table.addCell(ustawfraze(B.b("saldoMa"), 0, 1));
            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie obrotów sald analitycznych narastająco", 21, 0, 5));
            table.setHeaderRows(3);
            table.setFooterRows(1);
        int i = 1;
        for (SaldoKontoNarastajaco rs : listaSaldoKonto) {
            if (saldaniezerowe == false) {
                tabelawiersze(table, rs, ppr0dpr1, drukujkategorie, granica, i);
            } else if (Z.z(rs.getSaldoWn()) != 0.0 || Z.z(rs.getSaldoMa()) != 0.0) {
                tabelawiersze(table, rs, ppr0dpr1, drukujkategorie, granica, i);
            }
        }
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
    
    private static void tabelawiersze(PdfPTable table, SaldoKontoNarastajaco rs, boolean ppr0dpr1, boolean drukujkategorie,int granica, int i) {
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8, 22f));
        table.addCell(ustawfrazeAlign(rs.getKonto().getPelnynumer(), "left", 8));
        table.addCell(ustawfrazeAlign(rs.getKonto().getNazwapelna(), "left", 7));
        table.addCell(ustawfrazeAlign(rs.getBoWn()!= 0 ? formatujLiczba(rs.getBoWn()) : "", "right", 8));
        table.addCell(ustawfrazeAlign(rs.getBoMa() != 0 ? formatujLiczba(rs.getBoMa()) : "", "right", 8));
        if (ppr0dpr1 == false) {
            wiersze1_6(table, granica, rs);
        } else {
            wiersze7_12(table, granica, rs);
        }
        if (drukujkategorie == true) {
            String opis = rs.getKonto().getKontokategoria() != null ? rs.getKonto().getKontokategoria().getOpispelny() : "";
            PdfPCell c = ustawfraze(opis, 2, 0);
            c.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(c);
        } else {
            table.addCell(ustawfrazeAlign(Z.z(rs.getObrotyBoWn()) != 0.0 ? formatujLiczba(rs.getObrotyBoWn()) : "", "right", 8));
            table.addCell(ustawfrazeAlign(Z.z(rs.getObrotyBoMa()) != 0.0 ? formatujLiczba(rs.getObrotyBoMa()) : "", "right", 8));
        }
        table.addCell(ustawfrazeAlign(Z.z(rs.getSaldoWn()) != 0.0 ? formatujLiczba(rs.getSaldoWn()) : "", "right", 8));
        table.addCell(ustawfrazeAlign(Z.z(rs.getSaldoMa()) != 0.0 ? formatujLiczba(rs.getSaldoMa()) : "", "right", 8));
    }
       
    private static void naglowki1_6(PdfPTable table) {
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
    }
    
     private static void naglowki7_12(PdfPTable table) {
        table.addCell(ustawfraze(B.b("obrotyWn")+" 07", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyMa")+" 07", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyWn")+" 08", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyMa")+" 08", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyWn")+" 09", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyMa")+" 09", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyWn")+" 10", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyMa")+" 10", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyWn")+" 11", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyMa")+" 11", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyWn")+" 12", 0, 1));
        table.addCell(ustawfraze(B.b("obrotyMa")+" 12", 0, 1));
     }
     
     private static void wiersze1_6(PdfPTable table,int granica, SaldoKontoNarastajaco rs) {
         for (int j = 1 ; j < 7 ; j ++ ) {
                if (j < granica) {
                    SaldoKontoNarastajaco.Obrotymca numerlisty = rs.getObrotymiesiecy().get(Mce.getNumberToMiesiac().get(j));
                    table.addCell(ustawfrazeAlign(numerlisty.getObrotyWn() != 0 ? formatujLiczba(numerlisty.getObrotyWn()) : "", "right", 8));
                    table.addCell(ustawfrazeAlign(numerlisty.getObrotyMa() != 0 ? formatujLiczba(numerlisty.getObrotyMa()) : "", "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "right", 7));
                    table.addCell(ustawfrazeAlign("", "right", 7));
                }
            }
     }
     
     private static void wiersze7_12(PdfPTable table,int granica, SaldoKontoNarastajaco rs) {
        for (int j = 7 ; j < 13 ; j ++ ) {
                if (j < granica) {
                    SaldoKontoNarastajaco.Obrotymca numerlisty = rs.getObrotymiesiecy().get(Mce.getNumberToMiesiac().get(j));
                    table.addCell(ustawfrazeAlign(numerlisty.getObrotyWn() != 0 ? formatujLiczba(numerlisty.getObrotyWn()) : "", "right", 8));
                    table.addCell(ustawfrazeAlign(numerlisty.getObrotyMa() != 0 ? formatujLiczba(numerlisty.getObrotyMa()) : "", "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "right", 7));
                    table.addCell(ustawfrazeAlign("", "right", 7));
                }
            } 
     }
  }


