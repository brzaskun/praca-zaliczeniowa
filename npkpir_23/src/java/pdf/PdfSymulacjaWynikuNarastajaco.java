/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeSpanFont;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entityfk.WynikFKRokMc;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.B;
import msg.Msg;
import plik.Plik;
import view.WpisView;
import viewfk.SymulacjaWynikuView;

/**
 *
 * @author Osito
 */

public class PdfSymulacjaWynikuNarastajaco {
    
    public static void drukuj(List<WynikFKRokMc> listamiesiecy, List<SymulacjaWynikuView.PozycjeSymulacji> pozycjePodsumowaniaWyniku, 
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, 
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty, WpisView wpisView) {
        try {
            String nazwapliku = "symulacjawynikunar-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            drukujcd(listamiesiecy, pozycjePodsumowaniaWyniku, pozycjeObliczeniaPodatku, pozycjeDoWyplaty, wpisView);
            Msg.msg("Wydruk zestawienia wyniku narastająco");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie wydrukowano zestawienia wyniku narastająco");

        }
    }

    private static void drukujcd(List<WynikFKRokMc> listamiesiecy, List<SymulacjaWynikuView.PozycjeSymulacji> pozycjePodsumowaniaWyniku, 
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, 
            List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeDoWyplaty, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
        Document document = new Document();
        PdfWriter.getInstance(document, Plik.plikR("symulacjawynikunar-" + wpisView.getPodatnikWpisu() + ".pdf"));
        document.addTitle("Zestawienie wyniku narastająco");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Zestawienie wyniku finansowego narastająco");
        document.addKeywords("Wynik Finansowy Narastająco, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
        document.setPageSize(PageSize.A4);
        document.add(tablica(wpisView, listamiesiecy));
        document.add(tablica2(pozycjePodsumowaniaWyniku));
        //document.add(tablica3(pozycjeObliczeniaPodatkuPoprzedniemiesiace, 1));
        document.add(tablica3(pozycjeObliczeniaPodatku, 2));
        document.add(tablica4(pozycjeDoWyplaty, 3));
        document.close();
        Msg.msg("i", "Wydrukowano symulację wyniku finansowego narastająco");
    }

    private static PdfPTable tablica(WpisView wpisView, List<WynikFKRokMc> listamiesiecy) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(7);
        table.setWidths(new int[]{1, 2, 2, 2, 2, 2, 2});
        table.setWidthPercentage(70);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(wpisView.getPodatnikWpisu(), 3, 0));
            table.addCell(ustawfraze(B.b("zestawieniekwotwyników")+" " + wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisuSt(), 4, 0));
            
            table.addCell(ustawfraze(B.b("mc"), 0, 1));
            table.addCell(ustawfraze(B.b("przychód"), 0, 1));
            table.addCell(ustawfraze(B.b("koszt"), 0, 1));
            table.addCell(ustawfraze(B.b("wynikfinansowy"), 0, 1));
            table.addCell(ustawfraze(B.b("npup"), 0, 1));
            table.addCell(ustawfraze(B.b("nkup"), 0, 1));
            table.addCell(ustawfraze(B.b("wynikpodatkowy"), 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - zestawienie wyniku finansowego narastająco", 7, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        int i = 1;
        for (WynikFKRokMc rs : listamiesiecy) {
            table.addCell(ustawfrazeAlign(rs.getMc(), "center", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getPrzychody()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKoszty()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWynikfinansowy()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNpup()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNkup()), "right", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWynikpodatkowy()), "right", 7));
        }
        return table;
    }


   private static PdfPTable tablica2(List<SymulacjaWynikuView.PozycjeSymulacji> listapozycjisymulacji) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{4, 1});
        table.setWidthPercentage(50);
        table.setSpacingBefore(15);
        try {
            table.addCell(ustawfraze(B.b("obliczeniewynikufinipod"), 2, 0));

            table.addCell(ustawfraze(B.b("opis"), 0, 1));
            table.addCell(ustawfraze(B.b("kwota"), 0, 1));

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
   
   private static PdfPTable tablica3(List<SymulacjaWynikuView.PozycjeSymulacji> pozycjeObliczeniaPodatku, int i) throws DocumentException, IOException {
        PdfPTable table = new PdfPTable(2);
        table.setWidths(new int[]{4, 1});
        table.setWidthPercentage(50);
        table.setSpacingBefore(15);
        try {
            if (i == 1) {
                table.addCell(ustawfraze(B.b("obliczeniepodatkunar"), 2, 0));
            } else {
                table.addCell(ustawfraze(B.b("obliczeniepodatkudozapłaty"), 2, 0));
            }

            table.addCell(ustawfraze(B.b("opis"), 0, 1));
            table.addCell(ustawfraze(B.b("kwota"), 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie podatku", 6, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (SymulacjaWynikuView.PozycjeSymulacji rs : pozycjeObliczeniaPodatku) {
            table.addCell(ustawfrazeAlign(rs.getNazwa(), "left", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWartosc()), "right", 7));
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
                table.addCell(ustawfraze(B.b("obliczeniekwotdowypłaty"), 2, 0));
            }
            table.addCell(ustawfraze(B.b("opis"), 0, 1));
            table.addCell(ustawfraze(B.b("kwota"), 0, 1));

            table.addCell(ustawfrazeSpanFont("Biuro Rachunkowe Taxman - obliczenie kwoty do wypłaty", 6, 0, 5));

            table.setHeaderRows(3);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (SymulacjaWynikuView.PozycjeSymulacji rs : pozycjeDoWyplaty) {
            table.addCell(ustawfrazeAlign(rs.getNazwa(), "left", 7));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getWartosc()), "right", 7));
        }
        return table;
    }
   
}
