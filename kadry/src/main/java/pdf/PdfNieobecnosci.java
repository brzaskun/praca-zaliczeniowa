/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Angaz;
import entity.Nieobecnosc;
import error.E;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfFont.*;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWritera;
import static pdf.PdfMain.naglowekStopkaL;
import static pdf.PdfMain.otwarcieDokumentu;
import z.Z;

/**
 *
 * @author Osito
 */
public class PdfNieobecnosci {
    public static void drukuj(List<Nieobecnosc> lista, Angaz angaz, String rok) {
        try {
            String nazwa = angaz.getPracownik().getPesel()+"lp.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajOpisWstepnyKartaWyn(document, angaz.getFirma(), angaz.getPracownik(), "Zestawienie nieobecności pracownika", rok, angaz.getPracownik().getPesel());
                dodajtabeleglowna(angaz, document, rok, lista);
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajtabeleglowna(Angaz a, Document document, String rok, List<Nieobecnosc> lista) {
        try {
            PdfPTable table = generujTabele(a.getFirma().getNazwa(),a.getPracownik().getNazwiskoImie(),a.getPracownik().getPesel(), rok);
            dodajwiersze(lista, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfNieobecnosci.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPTable generujTabele(String firma, String pracownik, String pesel, String rok) {
        PdfPTable table = new PdfPTable(16);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 2, 2, 7, 3, 3, 2, 2, 2, 2, 2, 4, 3, 3, 3, 3});
            table.addCell(ustawfrazeAlign("lp", "center",8));
            table.addCell(ustawfrazeAlign("kod", "center",8));
            table.addCell(ustawfrazeAlign("zus", "center",8));
            table.addCell(ustawfrazeAlign("nazwa", "center",8));
            table.addCell(ustawfrazeAlign("data od", "center",8));
            table.addCell(ustawfrazeAlign("data do", "center",8));
            table.addCell(ustawfrazeAlign("dni kal.", "center",8));
            table.addCell(ustawfrazeAlign("dni rob.", "center",8));
            table.addCell(ustawfrazeAlign("godz. rob.", "center",8));
            table.addCell(ustawfrazeAlign("nan. na kal.", "center",8));
            table.addCell(ustawfrazeAlign("jest lista", "center",8));
            table.addCell(ustawfrazeAlign("przyczyna", "center",8));
            table.addCell(ustawfrazeAlign("kraj", "center",8));
            table.addCell(ustawfrazeAlign("dieta", "center",8));
            table.addCell(ustawfrazeAlign("procent", "center",8));
            table.addCell(ustawfrazeAlign("ręczna podstawa", "center",8));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
    
    public static void dodajwiersze(List<Nieobecnosc> lista,PdfPTable table) {
        int i = 1;
        for (Nieobecnosc rs : lista) {
            i = i+1;
            table.addCell(ustawfrazeAlign(String.valueOf(i), "center",8,18f));
            table.addCell(ustawfrazeAlign(rs.getRodzajnieobecnosci().getKod(), "center",8));
            if (rs.getSwiadczeniekodzus()!=null) {
                table.addCell(ustawfrazeAlign(rs.getSwiadczeniekodzus().getKod(), "center",8));
            } else {
                table.addCell(ustawfrazeAlign("", "center",8));
            }
            table.addCell(ustawfrazeAlign(rs.getRodzajnieobecnosci().getOpis(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getDataod(), "center",8));
            table.addCell(ustawfrazeAlign(rs.getDatado(), "center",8));
            table.addCell(ustawfrazeAlign(rs.getDnikalendarzowe(), "center",8));
            table.addCell(ustawfrazeAlign(rs.getDniroboczenieobecnosci(), "center",8));
            table.addCell(ustawfrazeAlign(rs.getGodzinyroboczenieobecnosc(), "center",8));
            if (rs.isNaniesiona()) {
                table.addCell(ustawfrazeAlign("✓", "center",8));
            } else {
                table.addCell(ustawfrazeAlign("", "center",8));
            }
            if (rs.getNaliczenienieobecnoscList()!=null&&rs.getNaliczenienieobecnoscList().size()>0) {
                table.addCell(ustawfrazeAlign("✓", "center",8));
            } else {
                table.addCell(ustawfrazeAlign("", "center",8));
            }
            table.addCell(ustawfrazeAlign(rs.getUzasadnienie(), "left",8));
            table.addCell(ustawfrazeAlign(rs.getKrajoddelegowania(), "left",8));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getDietaoddelegowanie())), "right",8));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getZwolnienieprocent())), "right",8));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getSredniazmiennerecznie())), "right",8));
        }
    }
    
    public static void drukujde(List<Nieobecnosc> lista, Angaz angaz, String rok) {
        try {
            String nazwa = angaz.getPracownik().getPesel()+"lp.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Portrait();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajOpisnieobecnoscide(document, angaz, "Zusammenfassung der Entsendungszeiten", rok, angaz.getPracownik().getPesel(), angaz.getPracownik().getDataurodzenia());
                int sumadnikalendarzowych = dodajtabeleglownade(angaz, document, rok, lista);
                String opis = "insgesamt "+sumadnikalendarzowych+ " Kalendertage";
                document.add(Chunk.NEWLINE);
                PdfMain.dodajLinieOpisuBezOdstepuKolor(document, opis, BaseColor.BLACK);
                document.close();
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static int dodajtabeleglownade(Angaz a, Document document, String rok, List<Nieobecnosc> lista) {
        int sumadnikalendarzowych = 0;
        try {
            PdfPTable table = generujTabelede(a.getFirma().getNazwa(),a.getPracownik().getNazwiskoImie(),a.getPracownik().getPesel(), rok);
            sumadnikalendarzowych = dodajwierszede(lista, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfNieobecnosci.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sumadnikalendarzowych;
    }
    
    private static PdfPTable generujTabelede(String firma, String pracownik, String pesel, String rok) {
        PdfPTable table = new PdfPTable(7);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 2, 7, 3, 3, 2, 2, });
            table.addCell(ustawfrazeAlign("lp", "center",8));
            table.addCell(ustawfrazeAlign("sym", "center",8));
            table.addCell(ustawfrazeAlign("Beschreibung", "center",8));
            table.addCell(ustawfrazeAlign("vom", "center",8));
            table.addCell(ustawfrazeAlign("bis", "center",8));
            table.addCell(ustawfrazeAlign("Kalendertage", "center",8));
            table.addCell(ustawfrazeAlign("Arbeitstage", "center",8));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
    
    public static int dodajwierszede(List<Nieobecnosc> lista,PdfPTable table) {
        int sumadnikalendarzowych = 0;
        int i = 0;
        for (Nieobecnosc rs : lista) {
            String kod = rs.getRodzajnieobecnosci().getKod();
            if (kod.equals("Z")||kod.equals("UD")) {
                i = i+1;
                table.addCell(ustawfrazeAlign(String.valueOf(i), "center",8,18f));
                table.addCell(ustawfrazeAlign(kod, "center",8));
                if (kod.equals("Z")) {
                    table.addCell(ustawfrazeAlign("Arbeitszeit", "left",8));
                } else {
                    table.addCell(ustawfrazeAlign("Urlabstage", "left",8));
                }
                table.addCell(ustawfrazeAlign(rs.getDataod(), "center",8));
                table.addCell(ustawfrazeAlign(rs.getDatado(), "center",8));
                table.addCell(ustawfrazeAlign(rs.getDnikalendarzowe(), "center",8));
                sumadnikalendarzowych = (int) (sumadnikalendarzowych + rs.getDnikalendarzowe());
                table.addCell(ustawfrazeAlign(rs.getDniroboczenieobecnosci(), "center",8));
            }
        }
        return sumadnikalendarzowych;
    }
    
   
}
