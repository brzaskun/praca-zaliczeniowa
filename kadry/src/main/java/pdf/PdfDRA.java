/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfFont.*;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.naglowekStopkaL;
import static pdf.PdfMain.otwarcieDokumentu;
import plik.Plik;
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
        PdfPTable table = new PdfPTable(20);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3});
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
            table.addCell(ustawfrazeAlign(opisy[18], "center",6));
            table.addCell(ustawfrazeAlign(opisy[19], "center",6));
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
            table.addCell(ustawfrazeAlign(opisy[18], "center",6));
            table.addCell(ustawfrazeAlign(opisy[19], "center",6));
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
            table.addCell(ustawfrazeAlign("19", "center",6));
            table.addCell(ustawfrazeAlign("20", "center",6));
            table.setHeaderRows(2);
            table.setFooterRows(1);
        } catch (DocumentException ex) {
        }
        return table;
    }
    
    public static void dodajwiersze(List<Pasekwynagrodzen> lista,PdfPTable table) {
        int i = 1;
        for (Pasekwynagrodzen rs : lista) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",7,18f));
            table.addCell(ustawfrazeAlign(rs.getNazwiskoImie(), "left",7,18f));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBrutto())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaskladkizus())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracemerytalne())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracrentowe())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracchorobowe())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznepracownik())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getEmerytalne())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRentowe())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getWypadkowe())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznefirma())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznefirma()+rs.getRazemspolecznepracownik())), "right",7));//13
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaubezpzdrowotne())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotne())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getFp())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getFgsp())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getKosztpracodawcy())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekdochodowy())), "right",7));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPotracenia())), "right",7));
        }
    }
    
     public static void dodajwierszeRsa(List<Nieobecnosc> listanieobecnosci,PdfPTable table) {
        int i = 1;
        for (Nieobecnosc rs : listanieobecnosci) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",7,18f));
            table.addCell(ustawfrazeAlign(rs.getAngaz().getPracownik().getNazwiskoImie(), "left",7,18f));
            table.addCell(ustawfrazeAlign(rs.getAngaz().getPracownik().getPesel(), "left",7,18f));
            if (rs.getSwiadczeniekodzus()!=null) {
                table.addCell(ustawfrazeAlign(rs.getSwiadczeniekodzus().getOpis(), "left",7,18f));
            } else {
                table.addCell(ustawfrazeAlign(rs.getRodzajnieobecnosci().getOpis(), "left",7,18f));
            }
            table.addCell(ustawfrazeAlign(rs.getRodzajnieobecnosci().getKod(), "center",7,18f));
            if (rs.getSwiadczeniekodzus()!=null) {
                table.addCell(ustawfrazeAlign(rs.getSwiadczeniekodzus().getKod(), "center",7,18f));
            } else {
                table.addCell(ustawfrazeAlign("", "left",7,18f));
            }
            table.addCell(ustawfrazeAlign(rs.getDataod(), "center",7,18f));
            table.addCell(ustawfrazeAlign(rs.getDatado(), "center",7,18f));
        }
    }
    
//            danezus.put("zus51", zus51);
//            danezus.put("zus51pracownik", zus51pracownik);
//            danezus.put("zus51pracodawca", zus51pracodawca);
//            danezus.put("zus52", zus52);
//            danezus.put("zusFP", zusFP);
//            danezus.put("zusFGSP", zusFGSP);
//            danezus.put("zus53", zus53);
//            danezus.put("zus", zus);
//            danezus.put("pit4", pit4);
     
    public static ByteArrayOutputStream drukujListaPodstawowa(List<Pasekwynagrodzen> lista, List<Definicjalistaplac> def, List<Nieobecnosc> listanieobecnosci, 
            String nip, String mc, Map<String,Double> danezus, String nazwafirmy, FirmaKadry firma) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = nip + "_" + mc + "_" + "DRA.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape(40,20,40,40);
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                List<String> nazwy = new ArrayList<>();
                for (Definicjalistaplac r : def) {
                    nazwy.add(r.getNrkolejny());
                }
                String datawyplaty = null;
                for (Pasekwynagrodzen p :lista) {
                    datawyplaty = p.getDatawyplaty();
                    break;
                }
                PdfMain.dodajOpisWstepny(document, "Zestawienie DRA "+nazwafirmy, def.get(0).getRok(), def.get(0).getMc(), def.get(0).getFirma().getNip(), nazwy, datawyplaty);
                String[] opisy = {"lp","Nazwisko i imię","Razem przychód", "Podst. wymiaru składek ubezp. społecznych", "Ubezp. Emerytalne ", "Ubezp. rentowe", "Ubezp. chorobowe", "Razem składki na ub. społ. prac.",
                    "Ubezp. Emerytalne ", "Ubezp. rentowe", "Ubezp. wypadkowe", "Razem składki na ub. społ. firma", "Razem składki na ub. społ.", "Podst. wymiaru składek ubezp. zdrowotnego","Składka zdrowotna",
                    "FP", "FGŚP", "Koszt pracodawcy", "Należna zaliczka na podatek dochodowy", "Potrącenia"};
                dodajtabeleglowna(lista, document, opisy);
                document.add(Chunk.NEWLINE);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 51 pracownik:", f.F.curr(danezus.get("zus51pracownik")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 51 pracodawca:", f.F.curr(danezus.get("zus51pracodawca")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 51 razem:", f.F.curr(danezus.get("zus51")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 52: ", f.F.curr(danezus.get("zus52")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 53 FP: ", f.F.curr(danezus.get("zusFP")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 53 FGŚP: ", f.F.curr(danezus.get("zusFGSP")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "zus 53 razem:", f.F.curr(danezus.get("zus53")), Element.ALIGN_LEFT, 1, 100);
                document.add(Chunk.NEWLINE);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "wynagrodzenia brutto:", f.F.curr(danezus.get("brutto")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "w tym brutto praca:", f.F.curr(danezus.get("bruttopraca")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "w tym brutto inne:", f.F.curr(danezus.get("bruttozlecenia")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "wynagrodzenia netto", f.F.curr(danezus.get("netto")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "ZUS do wpłaty: ", f.F.curr(danezus.get("zus")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "PIT-4 do wpłaty: ", f.F.curr(danezus.get("pit4")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "PIT-4 Niemcy do wpłaty w walucie: ", f.F.curr(danezus.get("pit4N"),"EUR"), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "potrącenia komornicze: ", f.F.curr(danezus.get("potraceniaKomornik")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "potrącenia PPK: ", f.F.curr(danezus.get("potraceniaPPK")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "potrącenia zaliczki: ", f.F.curr(danezus.get("potraceniaZaliczki")), Element.ALIGN_LEFT, 1, 100);
                PdfMain.dodajLinieOpisuBezOdstepuTab(document, "potrącenia pozostałe: ", f.F.curr(danezus.get("potraceniaPozostale")), Element.ALIGN_LEFT, 1, 100);
                document.add(Chunk.NEWLINE);
                if (firma.getBankpodatki()!=null) {
                    document.add(Chunk.NEWLINE);
                    PdfMain.dodajLinieOpisuBezOdstepuTab(document, "Rachunek bankowy dla przelewów podatków do wynagrodzeń: ", firma.getBankpodatki(), Element.ALIGN_LEFT, 1, 100);
                }
                if (firma.getBankzus()!=null) {
                    document.add(Chunk.NEWLINE);
                    PdfMain.dodajLinieOpisuBezOdstepuTab(document, "Rachunek bankowy dla przelewów składek ZUS: ", firma.getBankzus(), Element.ALIGN_LEFT, 1, 100);
                }
                //lista do RSA
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
//                List<Naliczenienieobecnosc> nieobecnosci = new ArrayList<>();
//                for (Pasekwynagrodzen p : lista) {
//                    nieobecnosci.addAll(p.getNaliczenienieobecnoscList());
//                }
                String[] opisyrsa = {"lp","Nazwisko i imię","Pesel","Opis nieobecności", "Kod", "Kod ZUS", "Data od", "Data do"};
                dodajtabeleRSA(listanieobecnosci, document, opisyrsa);
                finalizacjaDokumentuQR(document, nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }

    private static void dodajtabeleRSA(List<Nieobecnosc> listanieobecnosci, Document document, String[] opisyrsa) {
        try {
            PdfPTable table = generujTabeleRsa(opisyrsa);
            dodajwierszeRsa(listanieobecnosci, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfDRA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private static PdfPTable generujTabeleRsa(String[] opisy) {
        PdfPTable table = new PdfPTable(8);
        try {
            table.setWidthPercentage(70);
            table.setWidths(new int[]{1, 5, 3, 7, 2, 2, 2, 2});
            table.addCell(ustawfrazeAlign(opisy[0], "center",6));
            table.addCell(ustawfrazeAlign(opisy[1], "center",6));
            table.addCell(ustawfrazeAlign(opisy[2], "center",6));
            table.addCell(ustawfrazeAlign(opisy[3], "center",6));
            table.addCell(ustawfrazeAlign(opisy[4], "center",6));
            table.addCell(ustawfrazeAlign(opisy[5], "center",6));
            table.addCell(ustawfrazeAlign(opisy[6], "center",6));
            table.addCell(ustawfrazeAlign(opisy[7], "center",6));
            table.addCell(ustawfrazeAlign("1", "center",6));
            table.addCell(ustawfrazeAlign("2", "center",6));
            table.addCell(ustawfrazeAlign("3", "center",6));
            table.addCell(ustawfrazeAlign("4", "center",6));
            table.addCell(ustawfrazeAlign("5", "center",6));
            table.addCell(ustawfrazeAlign("6", "center",6));
            table.addCell(ustawfrazeAlign("7", "center",6));
            table.addCell(ustawfrazeAlign("8", "center",6));
            table.setHeaderRows(2);
        } catch (DocumentException ex) {
        }
        return table;
    }
}
