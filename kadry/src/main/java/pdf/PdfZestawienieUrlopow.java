/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.FirmaKadry;
import entity.Rejestrurlopow;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfFont.*;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWriteraOut;
import static pdf.PdfMain.naglowekStopkaL;
import static pdf.PdfMain.otwarcieDokumentu;
import plik.Plik;

/**
 *
 * @author Osito
 */
public class PdfZestawienieUrlopow {
    public static ByteArrayOutputStream drukuj(List<Rejestrurlopow> lista, FirmaKadry firma, String rok) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = firma.getNip()+"urlopyZest.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWriteraOut(document, out);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajLinieOpisu(document, firma.getNazwa()+" - Zestawienie wykorzystania urlopów w roku "+rok,Element.ALIGN_LEFT, 2);
                dodajtabeleglowna(lista, document);
                finalizacjaDokumentuQR(document,nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    public static ByteArrayOutputStream drukujmail(List<Rejestrurlopow> lista, FirmaKadry firma, String rok) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = firma.getNip()+"urlopyZest.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWriteraOut(document, out);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajLinieOpisu(document, firma.getNazwa()+" - Zestawienie wykorzystania urlopów w roku "+rok,Element.ALIGN_LEFT, 2);
                dodajtabeleglowna(lista, document);
                finalizacjaDokumentuQR(document,nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    private static void dodajtabeleglowna(List<Rejestrurlopow> lista, Document document) {
        try {
            PdfPTable table = generujTabele();
            dodajwiersze(lista, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfZestawienieUrlopow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPTable generujTabele() {
        PdfPTable table = new PdfPTable(20);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 6, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 6});
            table.addCell(ustawfrazeAlign("lp", "center",8));
            table.addCell(ustawfrazeAlign("nazwisko i imię", "left",8));
            table.addCell(ustawfrazeAlign("wym.", "center",8));
            table.addCell(ustawfrazeAlign("dod. w.", "center",8));
            table.addCell(ustawfrazeAlign("r.p.", "center",8));
            table.addCell(ustawfrazeAlign("1", "center",8));
            table.addCell(ustawfrazeAlign("2", "center",8));
            table.addCell(ustawfrazeAlign("3", "center",8));
            table.addCell(ustawfrazeAlign("4", "center",8));
            table.addCell(ustawfrazeAlign("5", "center",8));
            table.addCell(ustawfrazeAlign("6", "center",8));
            table.addCell(ustawfrazeAlign("7", "center",8));
            table.addCell(ustawfrazeAlign("8", "center",8));
            table.addCell(ustawfrazeAlign("9", "center",8));
            table.addCell(ustawfrazeAlign("10", "center",8));
            table.addCell(ustawfrazeAlign("11", "center",8));
            table.addCell(ustawfrazeAlign("12", "center",8));
            table.addCell(ustawfrazeAlign("wyk.", "center",8));
            table.addCell(ustawfrazeAlign("zos.", "center",8));
            table.addCell(ustawfrazeAlign("nazwisko i imię", "left",8));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
    
    public static void dodajwiersze(List<Rejestrurlopow> lista,PdfPTable table) {
        int i = 1;
        for (Rejestrurlopow rs : lista) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8,15f));
            table.addCell(ustawfrazeAlign(rs.getAngaz().getNazwiskoiImie(), "left",8,15f));
            table.addCell(ustawfrazeAlignColor(rs.getAngaz().getPracownik().getWymiarurlopu(), "center",8,robcolor(rs.getAngaz().getPracownik().getWymiarurlopu())));
            table.addCell(ustawfrazeAlignColor(rs.getDodatkowywymiar(), "center",8,robcolor1(rs.getDodatkowywymiar())));
            table.addCell(ustawfrazeAlignColor(rs.getUrlopzalegly(), "center",8,robcolor(rs.getUrlopzalegly())));
            table.addCell(ustawfrazeAlignColor(rs.getM1(), "center",8,robcolor(rs.getM1())));
            table.addCell(ustawfrazeAlignColor(rs.getM2(), "center",8,robcolor(rs.getM2())));
            table.addCell(ustawfrazeAlignColor(rs.getM3(), "center",8,robcolor(rs.getM3())));
            table.addCell(ustawfrazeAlignColor(rs.getM4(), "center",8,robcolor(rs.getM4())));
            table.addCell(ustawfrazeAlignColor(rs.getM5(), "center",8,robcolor(rs.getM5())));
            table.addCell(ustawfrazeAlignColor(rs.getM6(), "center",8,robcolor(rs.getM6())));
            table.addCell(ustawfrazeAlignColor(rs.getM7(), "center",8,robcolor(rs.getM7())));
            table.addCell(ustawfrazeAlignColor(rs.getM8(), "center",8,robcolor(rs.getM8())));
            table.addCell(ustawfrazeAlignColor(rs.getM9(), "center",8,robcolor(rs.getM9())));
            table.addCell(ustawfrazeAlignColor(rs.getM10(), "center",8,robcolor(rs.getM10())));
            table.addCell(ustawfrazeAlignColor(rs.getM11(), "center",8,robcolor(rs.getM11())));
            table.addCell(ustawfrazeAlignColor(rs.getM12(), "center",8,robcolor(rs.getM12())));
            table.addCell(ustawfrazeAlignColor(rs.getWykorzystaniesuma(), "center",8,robcolor(rs.getWykorzystaniesuma())));
            table.addCell(ustawfrazeAlignColor(rs.getDowykorzystanianastrok(), "center",8,"black"));
            table.addCell(ustawfrazeAlign(rs.getAngaz().getNazwiskoiImie(), "left",8,15f));
        }
    }
    
    private static String robcolor(int i) {
        String zwrot = "gray";
        if (i>0) {
            zwrot = "blue";
        }
        return zwrot;
    }
    
    private static String robcolor1(int i) {
        String zwrot = "white";
        if (i>0) {
            zwrot = "blue";
        }
        return zwrot;
    }
    
         
     
      
    
   
}
