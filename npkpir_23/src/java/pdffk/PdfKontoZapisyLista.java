/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import pdf.*;
import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
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
import entityfk.KontoZapisy;
import entityfk.StronaWiersza;
import error.E;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import plik.Plik;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfKontoZapisyLista {

    public static void pobierzlistekont(List<KontoZapisy> kontoZapisylista, WpisView wpisView) throws FileNotFoundException {
        try {
            Podatnik pod = wpisView.getPodatnikObiekt();
            List<Parametr> param = pod.getVatokres();
            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 30);
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("zapiskonto-" + wpisView.getPodatnikWpisu() + ".pdf"));
            int liczydlo = 1;
            PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
            writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
            writer.setPageEvent(headerfoter);
            document.addTitle("Zapisy na kontach");
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk zapisów na koncie");
            document.addKeywords("VAT, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (IOException ex) {
                Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
            }
            Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
            font = new Font(helvetica, 8);
            document.setPageSize(PageSize.A4);
            Integer il = 1;
            try {
                for (KontoZapisy p : kontoZapisylista) {
                    PdfPTable table = new PdfPTable(6);
                    table.setSpacingBefore(15);
                    table.setWidths(new int[]{1, 3, 4, 10, 3, 3});
                    table.setWidthPercentage(98);

                    table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
                    table.addCell(ustawfraze("wydruk zapisów na kontach", 1, 0));
                    table.addCell(ustawfraze("firma: " + wpisView.getPodatnikWpisu(), 1, 0));
                    table.addCell(ustawfraze("za okres: " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 2, 0));

                    table.addCell(ustawfraze("lp", 0, 1));
                    table.addCell(ustawfraze("ilość zapisów", 0, 1));
                    table.addCell(ustawfraze("numer konta", 0, 1));
                    table.addCell(ustawfraze("nazwa konta", 0, 1));
                    table.addCell(ustawfraze("pozycja Wn", 0, 1));
                    table.addCell(ustawfraze("pozycja Ma", 0, 1));

                    table.setHeaderRows(2);

                    table.addCell(ustawfrazeAlign(il++, "center", 8));
                    table.addCell(ustawfrazeAlign(p.getStronywiersza().size(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKonto().getPelnynumer(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKonto().getNazwapelna(), "center", 8));
                    if (p.getKonto().getKontopozycjaID()!= null) {
                        if (p.getKonto().getKontopozycjaID().getPozycjaMa()!= null) {
                            table.addCell(ustawfrazeAlign(p.getKonto().getKontopozycjaID().getPozycjaWn(), "center", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "center", 8));
                        }   
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    if (p.getKonto().getKontopozycjaID()!= null) {
                        if (p.getKonto().getKontopozycjaID().getPozycjaMa()!= null) {
                            table.addCell(ustawfrazeAlign(p.getKonto().getKontopozycjaID().getPozycjaMa(), "center", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "center", 8));
                        }
                     } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
                    document.add(table);
                    document.add(new Chunk());
                    List<ListaSum> listasum = new ArrayList<>();
                    ListaSum l = new ListaSum();
                    listasum.add(l);
                    sumazapisow(p.getStronywiersza(), listasum);
                    sumazapisowpln(p.getStronywiersza(), listasum);
                    document.add(dodatKontoTabele(wpisView, p.getStronywiersza(), p.getKonto(), listasum));
                }
                //dodajpodsumowanie(listasum, table);
                document.close();
            } catch (Exception e) {
                E.e(e);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(PdfKontoZapisyLista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static PdfPTable dodatKontoTabele(WpisView wpisView, List<StronaWiersza> kontozapisy, Konto wybranekonto, List<ListaSum> listasum) throws DocumentException {
        PdfPTable table = new PdfPTable(15);
        table.setWidths(new int[]{1, 2, 2, 2, 1, 2, 4, 2, 2, 2, 2, 2, 2, 1, 2});
        table.setSpacingBefore(6);
        table.setWidthPercentage(90);
      
        table.addCell(ustawfraze("lp", 0, 1));
        table.addCell(ustawfraze("Data wystawienia", 0, 1));
        table.addCell(ustawfraze("Data op.gosp.", 0, 1));
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
        table.addCell(ustawfrazeAlign("15", "center", 6));

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
        table.addCell(ustawfrazeAlign("15", "center", 6));

        table.setHeaderRows(2);

        Integer i = 1;
        for (StronaWiersza rs : kontozapisy) {
            table.addCell(ustawfrazeAlign(i.toString(), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getDatawystawienia(), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getDataoperacji(), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfkS(), "left", 7));
            table.addCell(ustawfrazeAlign(String.valueOf(rs.getWiersz().getIdporzadkowy()), "center", 7));
            table.addCell(ustawfrazeAlign(rs.getDokfk().getNumerwlasnydokfk(), "center", 6));
            table.addCell(ustawfrazeAlign(rs.getWiersz().getOpisWiersza(), "left", 6));
            if (rs.getWiersz().getTabelanbp() == null) {
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKursBO()), "right", 7));
                table.addCell(ustawfrazeAlign("zap. BO", "right", 7));
            } else {
                if (rs.getWiersz().getTabelanbp().getKurssredni() > 0.0) {
                    table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWiersz().getTabelanbp().getKurssredni()), "right", 7));
                    table.addCell(ustawfrazeAlign(rs.getWiersz().getTabelanbp().getNrtabeli(), "right", 7));
                } else {
                    table.addCell(ustawfrazeAlign("", "right", 7));
                    table.addCell(ustawfrazeAlign("", "right", 7));
                }
            }
            if (rs.getWnma().equals("Wn")) {
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwota()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwota()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaPLN()), "right", 7));
            }
            if (rs.getWiersz().getTabelanbp() == null) {
                table.addCell(ustawfrazeAlign(rs.getSymbolWalutyBO(), "center", 7));
            } else {
                table.addCell(ustawfrazeAlign(rs.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty(), "center", 7));
            }
            if (rs.getWnma().equals("Wn") && rs.getWiersz().getStronaMa() != null) {
                table.addCell(ustawfrazeAlign(rs.getWiersz().getStronaMa().getKonto().getPelnynumer(), "right", 7));
            } else if (rs.getWnma().equals("Ma") && rs.getWiersz().getStronaWn() != null) {
                table.addCell(ustawfrazeAlign(rs.getWiersz().getStronaWn().getKonto().getPelnynumer(), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            i++;
        }
        dodajpodsumowanie(listasum, table);
        return table;
        //Msg.msg("i","Wydrukowano ewidencje","form:messages");
    }

    private static void dodajpodsumowanie(List<ListaSum> listasum, PdfPTable table) {
        try {
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 6));
            table.addCell(ustawfrazeAlign("podsumowanie", "left", 6));
            table.addCell(ustawfrazeAlign("", "right", 7));
            table.addCell(ustawfrazeAlign("", "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaWn()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaWnPLN()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaMa()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaMaPLN()), "right", 7));
            table.addCell(ustawfrazeAlign("", "right", 7));
            table.addCell(ustawfrazeAlign("", "right", 7));
            //************
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 6));
            table.addCell(ustawfrazeAlign("saldo", "left", 6));
            table.addCell(ustawfrazeAlign("", "right", 7));
            table.addCell(ustawfrazeAlign("", "right", 7));
            double saldo = listasum.get(0).getSaldoWn() > 0.0 ? listasum.get(0).getSaldoWn() : listasum.get(0).getSaldoMa();
            if (listasum.get(0).getSaldoWn() > 0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoWn()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoWnPLN()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            if (listasum.get(0).getSaldoMa() > 0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoMa()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoMaPLN()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            table.addCell(ustawfrazeAlign("", "right", 7));
            table.addCell(ustawfrazeAlign("", "right", 7));
        } catch (Exception e) {
            E.e(e);
        }
    }
    
     public static void sumazapisow(List<StronaWiersza> kontozapisy, List<ListaSum> listasum){
        try {
            double sumaWn = 0.0;
            double sumaMa = 0.0;
            for(StronaWiersza p : kontozapisy){
                    if (p.getWnma().equals("Wn")) {
                        sumaWn = Z.z(sumaWn + p.getKwota());
                    } else if (p.getWnma().equals("Ma")){
                        Z.z(sumaMa = sumaMa + p.getKwota());
                    }
            }
            double saldoWn = 0.0;
            double saldoMa = 0.0;
            if(sumaWn>sumaMa){
                saldoWn = Z.z(sumaWn-sumaMa);
            } else {
                saldoMa = Z.z(sumaMa-sumaWn);
            }
            listasum.get(0).setSumaWn(sumaWn);
            listasum.get(0).setSumaMa(sumaMa);
            listasum.get(0).setSaldoWn(saldoWn);
            listasum.get(0).setSaldoMa(saldoMa);
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public static void sumazapisowpln(List<StronaWiersza> kontozapisy, List<ListaSum> listasum){
        double sumaWnPLN = 0.0;
        double sumaMaPLN = 0.0;
        for(StronaWiersza p : kontozapisy){
            if (p.getWnma().equals("Wn")) {
                Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
            } else if (p.getWnma().equals("Ma")){
                Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
            }
        }
        double saldoWnPLN = 0.0;
        double saldoMaPLN = 0.0;
        if(sumaWnPLN>sumaMaPLN){
            Z.z(saldoWnPLN = sumaWnPLN-sumaMaPLN);
        } else {
            Z.z(saldoMaPLN = sumaMaPLN-sumaWnPLN);
        }
        listasum.get(0).setSumaWnPLN(sumaWnPLN);
        listasum.get(0).setSumaMaPLN(sumaMaPLN);
        listasum.get(0).setSaldoWnPLN(saldoWnPLN);
        listasum.get(0).setSaldoMaPLN(saldoMaPLN);
    }
//
//    public static void main(String[] args) {
//        try {
//            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 5);
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/zapiskonto.pdf"));
//            document.addTitle("Zapisy na koncie");
//            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//            document.addSubject("Wydruk zapisów na koncie");
//            document.addKeywords("VAT, PDF");
//            document.addCreator("Grzegorz Grzelczyk");
//            document.open();
//            BaseFont helvetica = null;
//            Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
//            font = new Font(helvetica, 8);
//            document.setPageSize(PageSize.A4);
//            PdfPTable table = new PdfPTable(14);
//            table.setWidths(new int[]{1, 2, 2, 1, 2, 4, 2, 2, 2, 2, 2, 2, 1, 2});
//            table.setWidthPercentage(98);
//            try {
//                table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 3, 0));
//                table.addCell(ustawfraze("wydruk zapisów na koncie ", 4, 0));
//                table.addCell(ustawfraze("firma: nazwafirmy", 5, 0));
//                table.addCell(ustawfraze("za okres: 12/2015", 2, 0));
//
//                table.addCell(ustawfraze("lp", 0, 1));
//                table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 1));
//                table.addCell(ustawfraze("Nr dowodu księgowego", 0, 1));
//                table.addCell(ustawfraze("Wiersz", 0, 1));
//                table.addCell(ustawfraze("Nr własny", 0, 1));
//                table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 1));
//                table.addCell(ustawfraze("Kurs", 0, 1));
//                table.addCell(ustawfraze("Tabela", 0, 1));
//                table.addCell(ustawfraze("Wn", 0, 1));
//                table.addCell(ustawfraze("Wn PLN", 0, 1));
//                table.addCell(ustawfraze("Ma", 0, 1));
//                table.addCell(ustawfraze("Ma PLN", 0, 1));
//                table.addCell(ustawfraze("Waluta", 0, 1));
//                table.addCell(ustawfraze("Konto przec.", 0, 1));
//
//                table.addCell(ustawfrazeAlign("1", "center", 6));
//                table.addCell(ustawfrazeAlign("2", "center", 6));
//                table.addCell(ustawfrazeAlign("3", "center", 6));
//                table.addCell(ustawfrazeAlign("4", "center", 6));
//                table.addCell(ustawfrazeAlign("5", "center", 6));
//                table.addCell(ustawfrazeAlign("6", "center", 6));
//                table.addCell(ustawfrazeAlign("7", "center", 6));
//                table.addCell(ustawfrazeAlign("8", "center", 6));
//                table.addCell(ustawfrazeAlign("9", "center", 6));
//                table.addCell(ustawfrazeAlign("10", "center", 6));
//                table.addCell(ustawfrazeAlign("11", "center", 6));
//                table.addCell(ustawfrazeAlign("12", "center", 6));
//                table.addCell(ustawfrazeAlign("13", "center", 6));
//                table.addCell(ustawfrazeAlign("14", "center", 6));
//
//                table.addCell(ustawfrazeAlign("1", "center", 6));
//                table.addCell(ustawfrazeAlign("2", "center", 6));
//                table.addCell(ustawfrazeAlign("3", "center", 6));
//                table.addCell(ustawfrazeAlign("4", "center", 6));
//                table.addCell(ustawfrazeAlign("5", "center", 6));
//                table.addCell(ustawfrazeAlign("6", "center", 6));
//                table.addCell(ustawfrazeAlign("7", "center", 6));
//                table.addCell(ustawfrazeAlign("8", "center", 6));
//                table.addCell(ustawfrazeAlign("9", "center", 6));
//                table.addCell(ustawfrazeAlign("10", "center", 6));
//                table.addCell(ustawfrazeAlign("11", "center", 6));
//                table.addCell(ustawfrazeAlign("12", "center", 6));
//                table.addCell(ustawfrazeAlign("13", "center", 6));
//                table.addCell(ustawfrazeAlign("14", "center", 6));
//
//                table.setHeaderRows(3);
//                table.setFooterRows(1);
//            } catch (Exception e) {
//
//            }
//            document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
//            document.add(table);
//            document.close();
//        } catch (Exception e) {
//
//        }
//    }

}
