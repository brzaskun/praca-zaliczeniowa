/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Angaz;
import entity.Kartawynagrodzen;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
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
public class PdfKartaWynagrodzen {
    public static void drukuj(List<Kartawynagrodzen> lista, Angaz angaz, String rok) {
        try {
            String nazwa = angaz.getAngazStringPlik()+"lp.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajOpisWstepny(document, angaz, "Karta wynagrodzeń pracownika", rok);
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
    
    private static void dodajtabeleglowna(Angaz a, Document document, String rok, List<Kartawynagrodzen> lista) {
        try {
            PdfPTable table = generujTabele(a.getFirma().getNazwa(),a.getPracownik().getNazwiskoImie(),a.getPracownik().getPesel(), rok);
            dodajwiersze(lista, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfKartaWynagrodzen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPTable generujTabele(String firma, String pracownik, String pesel, String rok) {
        PdfPTable table = new PdfPTable(17);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3,3});
            table.addCell(ustawfraze(pracownik, 4, 0));
            table.addCell(ustawfraze(pesel, 2, 0));
            table.addCell(ustawfraze("firma: " + firma, 8, 0));
            table.addCell(ustawfraze("za okres: " + rok, 3, 0));
            table.addCell(ustawfraze("lp", 0, 2));
            table.addCell(ustawfraze("Składniki wynagrodzenia", 2, 0));
            table.addCell(ustawfraze("Razem przychód", 0, 2));
            table.addCell(ustawfraze("Podst. wymiaru składek ubezp. społecznych", 0, 2));
            table.addCell(ustawfraze("Składki ZUS pracownik", 4, 0));
            table.addCell(ustawfraze("Podst. wymiaru składek ubezp. zdrowotnego", 0, 2));
            table.addCell(ustawfraze("Koszty uzyskania przychodu", 0, 2));
            table.addCell(ustawfraze("Podstawa opodatkowania", 0, 2));
            table.addCell(ustawfraze("Potrącona zaliczka na podatek dochodowy", 0, 2));
            table.addCell(ustawfraze("Ubezpieczenie zdrowotne", 2, 0));
            table.addCell(ustawfraze("Należna zaliczka na podatek dochodowy", 0, 2));
            table.addCell(ustawfraze("Do wypłaty", 0, 2));
            table.addCell(ustawfrazeAlign("Składniki z ZUS", "center",6));
            table.addCell(ustawfrazeAlign("Składniki bez ZUS", "center",6));
            table.addCell(ustawfrazeAlign("Ubezp. Emerytalne", "center",6));
            table.addCell(ustawfrazeAlign("Ubezp. rentowe", "center",6));
            table.addCell(ustawfrazeAlign("Ubezp. chorobowe", "center",6));
            table.addCell(ustawfrazeAlign("Razem składki na ub. Społ.", "center",6));
            table.addCell(ustawfrazeAlign("Potrącona", "center",6));
            table.addCell(ustawfrazeAlign("Odliczona od podatku", "center",6));
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
            table.setHeaderRows(4);
        } catch (DocumentException ex) {
            System.out.println("");
        }
        return table;
    }
    
    public static void dodajwiersze(List<Kartawynagrodzen> lista,PdfPTable table) {
        int i = 1;
        for (Kartawynagrodzen rs : lista) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",6,18f));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttozus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttobezzus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttozus()+rs.getBruttobezzus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getBruttozus())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracemerytalne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracrentowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPracchorobowe())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getRazemspolecznepracownik())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaubezpzdrowotne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getKosztyuzyskania())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodstawaopodatkowania())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekwstepny())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotne())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotnedoodliczenia())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekdochodowy())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getNetto())), "right",6));
        }
    }
    
         
     public static void dodajwierszeSkladniki(List<Naliczenieskladnikawynagrodzenia> wykaz,List<Naliczenienieobecnosc> wykaznieob, PdfPTable table) {
        int i = 1;
        for (Naliczenieskladnikawynagrodzenia rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotadolistyplac()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaumownazacalymc()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotadolistyplac()), "left",6));
        }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getSwiadczeniekodzus().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getSwiadczeniekodzus().getOpisskrocony()+" "+rs.getJakiskladnikredukowalny(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotazus()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotabezzus()), "right",6));
            if (rs.getKwotastatystyczna()!=0.0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(-rs.getKwotastatystyczna()), "left",6));
            } else {
                table.addCell(ustawfrazeAlign(formatujWaluta(-rs.getKwotaredukcji()), "left",6));
            }
        }
    }
     
      public static String wierszeSkladnikiString(List<Naliczenieskladnikawynagrodzenia> wykaz,List<Naliczenienieobecnosc> wykaznieob) {
        StringBuilder sb = new StringBuilder();
        for (Naliczenieskladnikawynagrodzenia rs : wykaz) {
            if (!rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("777")) {
                sb.append(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod());
                sb.append(" ");
                sb.append(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony());
                sb.append(" ");
                if (rs.getSkladnikwynagrodzenia().getUwagi()!=null) {
                    sb.append(rs.getSkladnikwynagrodzenia().getUwagi());
                    sb.append(" ");
                }
                if (Z.z(rs.getKwotadolistyplac())!=0.0&&Z.z(rs.getKwotadolistyplac())!=Z.z(rs.getKwotaumownazacalymc())) {
                    sb.append(formatujWaluta(rs.getKwotadolistyplac()));
                    sb.append(";  ");
                } else {
                    if (Z.z(rs.getKwotadolistyplac())!=0.0) {
                        sb.append("kwLP/ ");
                        sb.append(formatujWaluta(rs.getKwotadolistyplac()));
                        sb.append("; ");
                    }
                    if (Z.z(rs.getKwotaumownazacalymc())!=0.0) {
                        sb.append("kwUmow/ ");
                        sb.append(formatujWaluta(rs.getKwotaumownazacalymc()));
                        sb.append("; ");
                    } 

                }
            }
        }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            if (!rs.getNieobecnosc().getSwiadczeniekodzus().getKod().equals("777")) {
                sb.append(rs.getNieobecnosc().getSwiadczeniekodzus().getKod());
                sb.append(" ");
                sb.append(rs.getNieobecnosc().getSwiadczeniekodzus().getOpisskrocony());
                sb.append(" ");
                if (rs.getKwotazus()!=0.0) {
                    sb.append(formatujWaluta(rs.getKwotazus()));
                    sb.append("; ");
                } else if (rs.getKwotastatystyczna()!=0.0){
                    sb.append(formatujWaluta(rs.getKwotabezzus()));
                    sb.append("; ");
                } else {
                    sb.append(formatujWaluta(rs.getKwotabezzus()));
                    sb.append("; ");
                }
            }
        }
        return sb.toString();
    }
     
      
    
   
}
