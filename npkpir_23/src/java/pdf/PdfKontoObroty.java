/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

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
import error.E;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import plik.Plik;
import view.WpisView;
import viewfk.KontoObrotyFKView;

/**
 *
 * @author Osito
 */

public class PdfKontoObroty {

    public static void drukujobroty(WpisView wpisView, List<KontoObrotyFKView.ObrotykontaTabela> lista, Konto wybranekonto, List<ListaSum> listasum)  throws DocumentException, FileNotFoundException, IOException {
        Podatnik pod = wpisView.getPodatnikObiekt();
        Konto konto = wybranekonto;
        try {
            List<Parametr> param = pod.getVatokres();
            Document document = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 5);
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR("zapiskonto-" + wpisView.getPodatnikWpisu() + ".pdf"));
            int liczydlo = 1;
            PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
            writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
            writer.setPageEvent(headerfoter);
            document.addTitle("Zapisy na koncie "+konto.getPelnynumer());
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk obrotów na koncie");
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
            PdfPTable table = new PdfPTable(14);
            table.setWidths(new int[]{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
            table.setWidthPercentage(98);
            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
            table.addCell(ustawfraze("wydruk obrotów na koncie "+konto.getPelnynumer(), 4, 0));
            table.addCell(ustawfraze("firma: "+wpisView.getPodatnikObiekt().getNazwapelnaPDF(), 4, 0));
            table.addCell(ustawfraze("za okres: "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt(), 2, 0));
            table.addCell(ustawfraze("lp", 0, 1));
            table.addCell(ustawfraze("miesiąc", 0, 1));
            table.addCell(ustawfraze("obroty Wn", 0, 1));
            table.addCell(ustawfraze("obroty Ma", 0, 1));
            table.addCell(ustawfraze("suma Wn", 0, 1));
            table.addCell(ustawfraze("suma Ma", 0, 1));
            table.addCell(ustawfraze("saldo Wn", 0, 1));
            table.addCell(ustawfraze("saldo Ma", 0, 1));
            table.addCell(ustawfraze("obroty Wn PLN", 0, 1));
            table.addCell(ustawfraze("obroty Ma PLN", 0, 1));
            table.addCell(ustawfraze("suma Wn PLN", 0, 1));
            table.addCell(ustawfraze("suma Ma PLN", 0, 1));
            table.addCell(ustawfraze("saldo Wn PLN", 0, 1));
            table.addCell(ustawfraze("saldo Ma PLN", 0, 1));
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
            Integer i = 1;
            for (KontoObrotyFKView.ObrotykontaTabela rs : lista) {
                table.addCell(ustawfrazeAlign(i.toString(), "center", 7));
                table.addCell(ustawfrazeAlign(rs.getMiesiac(), "left", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaWn()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaMa()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaWnnarastajaco()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaManarastajaco()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaWnsaldo()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaMasaldo()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaWnPLN()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaMaPLN()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaWnnarastajacoPLN()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaManarastajacoPLN()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaWnsaldoPLN()), "right", 7));
                table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaMasaldoPLN()), "right", 7));
                i++;
            }
            dodajpodsumowanie(listasum, table);
            document.setPageSize(PageSize.A4_LANDSCAPE.rotate());
            document.add(table);
            document.close();

            //Msg.msg("i","Wydrukowano ewidencje","form:messages");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajpodsumowanie(List<ListaSum> listasum, PdfPTable table) {
        try {
            double saldo = listasum.get(0).getSaldoWn() > 0.0 ? listasum.get(0).getSaldoWn() : listasum.get(0).getSaldoMa();
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign("podsumowanie", "left", 6));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaWn()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaMa()), "right", 7));
            if (listasum.get(0).getSaldoWn() > 0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoWn()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            if (listasum.get(0).getSaldoMa() > 0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoMa()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            table.addCell(ustawfrazeAlign("", "left", 7));
            table.addCell(ustawfrazeAlign("", "center", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaWnPLN()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSumaMaPLN()), "right", 7));
            if (listasum.get(0).getSaldoWn() > 0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoWnPLN()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
            if (listasum.get(0).getSaldoMa() > 0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(listasum.get(0).getSaldoMaPLN()), "right", 7));
            } else {
                table.addCell(ustawfrazeAlign("", "right", 7));
            }
        } catch (Exception e) {
            E.e(e);
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

