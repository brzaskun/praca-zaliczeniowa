/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beanstesty.PasekwynagrodzenBean;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Definicjalistaplac;
import entity.Pasekwynagrodzen;
import error.E;
import java.util.ArrayList;
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
public class PdfDRA {
    
    
    private static void dodajtabeleglowna(List<Pasekwynagrodzen> lista, Document document, String[] opisy) {
        try {
            PdfPTable table = generujTabele(opisy);
            dodajwiersze(lista, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfDRA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPTable generujTabele(String[] opisy) {
        PdfPTable table = new PdfPTable(18);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3});
            table.addCell(ustawfrazeAlign(opisy[0], "center",6));
            table.addCell(ustawfrazeAlign(opisy[1], "center",6));
            table.addCell(ustawfrazeAlign(opisy[2], "center",6));
            table.addCell(ustawfrazeAlign(opisy[3], "center",6));
            table.addCell(ustawfrazeAlign(opisy[4], "center",6));
            table.addCell(ustawfrazeAlign(opisy[5], "center",6));
            table.addCell(ustawfrazeAlign(opisy[6], "center",6));
            table.addCell(ustawfrazeAlign(opisy[7], "center",6));
            table.addCell(ustawfrazeAlign(opisy[8], "center",6));
            table.addCell(ustawfrazeAlign(opisy[9], "center",6));
            table.addCell(ustawfrazeAlign(opisy[10], "center",6));
            table.addCell(ustawfrazeAlign(opisy[11], "center",6));
            table.addCell(ustawfrazeAlign(opisy[12], "center",6));
            table.addCell(ustawfrazeAlign(opisy[13], "center",6));
            table.addCell(ustawfrazeAlign(opisy[14], "center",6));
            table.addCell(ustawfrazeAlign(opisy[15], "center",6));
            table.addCell(ustawfrazeAlign(opisy[16], "center",6));
            table.addCell(ustawfrazeAlign(opisy[17], "center",6));
            table.addCell(ustawfrazeAlign("1", "center",6));
            table.addCell(ustawfrazeAlign("2", "center",6));
            table.addCell(ustawfrazeAlign("3", "center",6));
            table.addCell(ustawfrazeAlign("4", "center",6));
            table.addCell(ustawfrazeAlign("5", "center",6));
            table.addCell(ustawfrazeAlign("6", "center",6));
            table.addCell(ustawfrazeAlign("7", "center",6));
            table.addCell(ustawfrazeAlign("8", "center",6));
            table.addCell(ustawfrazeAlign("9", "center",6));
            table.addCell(ustawfrazeAlign("10", "center",6));
            table.addCell(ustawfrazeAlign("11", "center",6));
            table.addCell(ustawfrazeAlign("12", "center",6));
            table.addCell(ustawfrazeAlign("13", "center",6));
            table.addCell(ustawfrazeAlign("14", "center",6));
            table.addCell(ustawfrazeAlign("15", "center",6));
            table.addCell(ustawfrazeAlign("16", "center",6));
            table.addCell(ustawfrazeAlign("17", "center",6));
            table.addCell(ustawfrazeAlign("18", "center",6));
            table.setHeaderRows(2);
        } catch (DocumentException ex) {
        }
        return table;
    }
    
    public static void dodajwiersze(List<Pasekwynagrodzen> lista,PdfPTable table) {
        int i = 1;
        for (Pasekwynagrodzen rs : lista) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",6,18f));
            table.addCell(ustawfrazeAlign(rs.getNazwiskoImie(), "left",6,18f));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBrutto())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttozus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracemerytalne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracrentowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracchorobowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznepracownik())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getEmerytalne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRentowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getWypadkowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznefirma())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaubezpzdrowotne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getFp())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getFgsp())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getKosztpracodawcy())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekdochodowy())), "right",6));
        }
    }
    
          
     
    public static void drukujListaPodstawowa(List<Pasekwynagrodzen> lista, List<Definicjalistaplac> def, String nip, String mc) {
        try {
            String nazwa = nip + "_" + mc + "_" + "DRA.pdf";
            if (lista != null) {
                lista.add(PasekwynagrodzenBean.sumujpaski(lista));
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                List<String> nazwy = new ArrayList<>();
                for (Definicjalistaplac r : def) {
                    nazwy.add(r.getNrkolejny());
                }
                PdfMain.dodajOpisWstepny(document, "Zestawienie DRA", def.get(0).getRok(), def.get(0).getMc(), def.get(0).getFirma().getNip(), nazwy);
                String[] opisy = {"lp","Nazwisko i imię","Razem przychód", "Podst. wymiaru składek ubezp. społecznych", "Ubezp. Emerytalne ", "Ubezp. rentowe", "Ubezp. chorobowe", "Razem składki na ub. społ. prac.",
                    "Ubezp. Emerytalne ", "Ubezp. rentowe", "Ubezp. wypadkowe", "Razem składki na ub. społ. firma", "Podst. wymiaru składek ubezp. zdrowotnego","Składka zdrowotna",
                    "FP", "FGŚP", "Koszt pracodawcy", "Należna zaliczka na podatek dochodowy"};
                dodajtabeleglowna(lista, document, opisy);
                document.add(Chunk.NEWLINE);
                finalizacjaDokumentuQR(document, nazwa);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
}
