/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
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
public class PdfListaPlac {
    public static void drukuj(Pasekwynagrodzen p) {
        try {
            Angaz a = p.getKalendarzmiesiac().getUmowa().getAngaz();
            String nazwa = a.getAngazStringPlik()+"lp.pdf";
            if (p != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajOpisWstepny(document, p.getDefinicjalistaplac());
                String[] opisy = {"Razem przychód","Podst. wymiaru składek ubezp. społecznych","Ubezp. Emerytalne","Ubezp. rentowe","Ubezp. chorobowe","Razem składki na ub. Społ.","Podst. wymiaru składek ubezp. zdrowotnego",
                    "Koszty uzyskania przychodu","Podstawa opodatkowania","Potrącona zaliczka na podatek dochodowy","Potrącona","Odliczona od podatku","Należna zaliczka na podatek dochodowy","Do wypłaty"};
                dodajtabeleglowna(p, document);
                PdfPTable tablazestawienia = new PdfPTable(3);
                try {
                    tablazestawienia.setWidthPercentage(95);
                    tablazestawienia.setWidths(new int[]{3, 3, 3});
                    tablazestawienia.addCell(ustawfraze("składniki", 0, 0));
                    tablazestawienia.addCell(ustawfraze("nieobecności", 0, 0));
                    tablazestawienia.addCell(ustawfraze("czas pracy", 0, 0));
                    
                        PdfPTable tabelaskladniki = dodajtabeleskladniki(p, document);
                        PdfPCell cell = new PdfPCell();
                        cell.addElement(tabelaskladniki);
                        tablazestawienia.addCell(cell);
                        if (p.getKalendarzmiesiac().getUmowa().getNieobecnoscList()!=null&&!p.getKalendarzmiesiac().getUmowa().getNieobecnoscList().isEmpty()) {
                            PdfPTable tabelanieobecnosci = dodajtabelenieobecnosci(p, document);
                            cell = new PdfPCell();
                            cell.addElement(tabelanieobecnosci);
                            tablazestawienia.addCell(cell);
                        } else {
                            tablazestawienia.addCell(new PdfPCell());
                        }
                        PdfPTable tabelcaczas = dodajtabelaczaspracy(p, document);
                        cell = new PdfPCell();
                        cell.addElement(tabelcaczas);
                        tablazestawienia.addCell(cell);
                } catch (Exception e){}
                document.add(tablazestawienia);
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
    
    private static void dodajtabeleglowna(Pasekwynagrodzen p, Document document) {
        try {
            Angaz a = p.getKalendarzmiesiac().getUmowa().getAngaz();
            PdfPTable table = generujTabele(a.getFirma().getNazwa(),a.getPracownik().getNazwiskoImie(), "70052809810", p.getKalendarzmiesiac().getRok(),p.getKalendarzmiesiac().getMc(), p.getDefinicjalistaplac().getNrkolejny());
            List<Pasekwynagrodzen> wykaz = new ArrayList<>();
            wykaz.add(p);
            dodajwiersze(wykaz, table);
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static PdfPTable generujTabele(String firma, String pracownik, String pesel, String rok, String mc, String nrkol) {
        PdfPTable table = new PdfPTable(17);
        try {
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,3,3});
            table.addCell(ustawfraze(pracownik, 4, 0));
            table.addCell(ustawfraze(pesel, 2, 0));
            table.addCell(ustawfraze("firma: " + firma, 6, 0));
            table.addCell(ustawfraze("nr kol: " + nrkol, 3, 0));
            table.addCell(ustawfraze("za okres: " + rok + "/" + mc, 2, 0));
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
    
    public static void dodajwiersze(List<Pasekwynagrodzen> wykaz,PdfPTable table) {
        int i = 1;
        for (Pasekwynagrodzen rs : wykaz) {
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
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPraczdrowotnedodoliczenia())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getPodatekdochodowy())), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(Z.z(rs.getNetto())), "right",6));
        }
    }
    
     private static PdfPTable dodajtabeleskladniki(Pasekwynagrodzen p, Document document) {
        PdfPTable table = null;
        try {
            List<Naliczenieskladnikawynagrodzenia> lista = p.getNaliczenieskladnikawynagrodzeniaList();
            List<Naliczenienieobecnosc> lista2 = p.getNaliczenienieobecnoscList();
            table = generujTabeleSkladniki();
            dodajwierszeSkladniki(lista, lista2, table);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
     
     private static String generujpasekskladniki(Pasekwynagrodzen p, Document document) {
        String wierszeSkladnikiString = "";
        try {
            List<Naliczenieskladnikawynagrodzenia> lista = p.getNaliczenieskladnikawynagrodzeniaList();
            List<Naliczenienieobecnosc> lista2 = p.getNaliczenienieobecnoscList();
            wierszeSkladnikiString = wierszeSkladnikiString(lista, lista2);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeSkladnikiString;
    }
     
     private static PdfPTable generujTabeleSkladniki() {
        PdfPTable table = new PdfPTable(6);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 5, 3, 3, 3});
            table.addCell(ustawfraze("", 0, 0, 10f));
            table.addCell(ustawfraze("kod", 0, 0));
            table.addCell(ustawfraze("nazwa", 0, 0));
            table.addCell(ustawfraze("zus", 0, 0));
            table.addCell(ustawfraze("bez zus", 0, 0));
            table.addCell(ustawfraze("redukcja", 0, 0));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
            System.out.println("");
        }
        return table;
    }
     
     public static void dodajwierszeSkladniki(List<Naliczenieskladnikawynagrodzenia> wykaz,List<Naliczenienieobecnosc> wykaznieob, PdfPTable table) {
        int i = 1;
        for (Naliczenieskladnikawynagrodzenia rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getUwagi(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotazus()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotabezzus()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotazredukowana()), "left",6));
        }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getNieobecnosckodzus().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" "+rs.getJakiskladnikredukowalny(), "left",6));
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
            sb.append(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod());
            sb.append(" ");
            sb.append(rs.getSkladnikwynagrodzenia().getUwagi());
            sb.append(" ");
            if (rs.getKwotazus()!=0.0) {
                sb.append(formatujWaluta(rs.getKwotazus()));
                sb.append(";  ");
            } else {
                sb.append(formatujWaluta(rs.getKwotabezzus()));
                sb.append("; ");
            }
        }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            sb.append(rs.getNieobecnosc().getNieobecnosckodzus().getKod());
            sb.append(" ");
            sb.append(rs.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony());
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
        return sb.toString();
    }
     
      private static PdfPTable dodajtabelenieobecnosci(Pasekwynagrodzen p, Document document) {
        PdfPTable table = null;
        try {
            List<Nieobecnosc> lista = p.getKalendarzmiesiac().getUmowa().getNieobecnoscList();
            table = generujTabeleNieobecnosci();
            dodajwierszeNieobecnosci(lista, table);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
      
    private static String generujpasekobecnosci(Pasekwynagrodzen p, Document document) {
         String wierszeString = "";
        try {
            List<Nieobecnosc> lista = p.getKalendarzmiesiac().getUmowa().getNieobecnoscList();
            wierszeString = wierszeNieobecnosciString(lista);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return wierszeString;
    }
     
     private static PdfPTable generujTabeleNieobecnosci() {
        PdfPTable table = new PdfPTable(6);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 2, 4, 3, 3, 4});
            table.addCell(ustawfraze("", 0, 0, 10f));
            table.addCell(ustawfraze("kod", 0, 0));
            table.addCell(ustawfraze("nazwa", 0, 0));
            table.addCell(ustawfraze("data od", 0, 0));
            table.addCell(ustawfraze("data do", 0, 0));
            table.addCell(ustawfraze("umowa", 0, 0));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
            System.out.println("");
        }
        return table;
    }
     
     public static void dodajwierszeNieobecnosci(List<Nieobecnosc> wykaz,PdfPTable table) {
        int i = 1;
        for (Nieobecnosc rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosckodzus().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosckodzus().getOpisskrocony(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getDataod(), "right",6));
            table.addCell(ustawfrazeAlign(rs.getDatado(), "right",6));
            table.addCell(ustawfrazeAlign(rs.getUmowa().getUmowakodzus().getOpis(), "left",6));
        }
    }
      public static String wierszeNieobecnosciString(List<Nieobecnosc> wykaznieob) {
        StringBuilder sb = new StringBuilder();
        for (Nieobecnosc rs : wykaznieob) {
            sb.append(rs.getNieobecnosckodzus().getKod());
            sb.append(" ");
            sb.append(rs.getNieobecnosckodzus().getOpisskrocony());
            sb.append(" ");
            sb.append(rs.getDataod().replace("-", "."));
            sb.append("-");
            sb.append(rs.getDatado().replace("-", "."));
            sb.append("; ");
        }
        return sb.toString();
    }
     private static PdfPTable dodajtabelaczaspracy(Pasekwynagrodzen p, Document document) {
        PdfPTable table = null;
        try {
            table = generujTabeleCzasPracy();
            dodajwierszeCzasPracy(p.getKalendarzmiesiac(), table);
        } catch (Exception ex) {
            Logger.getLogger(PdfListaPlac.class.getName()).log(Level.SEVERE, null, ex);
        }
        return table;
    }
     
     private static PdfPTable generujTabeleCzasPracy() {
        PdfPTable table = new PdfPTable(4);
        try {
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 5, 3, 3});
            table.addCell(ustawfraze("", 0, 0, 10f));
            table.addCell(ustawfraze("nazwa", 0, 0));
            table.addCell(ustawfraze("wymiar", 0, 0));
            table.addCell(ustawfraze("wyk", 0, 0));
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
            System.out.println("");
        }
        return table;
    }
     
     public static void dodajwierszeCzasPracy(Kalendarzmiesiac k,PdfPTable table) {
        int i = 1;
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("dni robocze", "left",6));
        int[] robocze = k.robocze();
        table.addCell(ustawfrazeAlign(robocze[0], "left",6));
        table.addCell(ustawfrazeAlign(robocze[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("godz robocze", "left",6));
        double[] roboczgodz = k.roboczegodz();
        table.addCell(ustawfrazeAlign(roboczgodz[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczgodz[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("choroba", "left",6));
        double[] roboczenieob = k.roboczenieob("331");
        table.addCell(ustawfrazeAlign(roboczenieob[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczenieob[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("urlop", "left",6));
        roboczenieob = k.roboczenieob("100");
        table.addCell(ustawfrazeAlign(roboczenieob[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczenieob[1], "left",6));
        table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
        table.addCell(ustawfrazeAlign("urlopbezplatny", "left",6));
        roboczenieob = k.roboczenieob("111");
        table.addCell(ustawfrazeAlign(roboczenieob[0], "left",6));
        table.addCell(ustawfrazeAlign(roboczenieob[1], "left",6));
    }

    public static void drukujListaPodstawowa(List<Pasekwynagrodzen> lista, Definicjalistaplac def) {
        try {
            String nrpoprawny = def.getNrkolejny().replaceAll("[^A-Za-z0-9]", "");
            String nazwa = def.getFirma().getNip() + "_" + nrpoprawny + "_" + "lp.pdf";
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Landscape();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaL(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajOpisWstepny(document, def);
                String[] opisy = {"Razem przychód", "Podst. wymiaru składek ubezp. społecznych", "Ubezp. Emerytalne", "Ubezp. rentowe", "Ubezp. chorobowe", "Razem składki na ub. Społ.", "Podst. wymiaru składek ubezp. zdrowotnego",
                    "Koszty uzyskania przychodu", "Podstawa opodatkowania", "Potrącona zaliczka na podatek dochodowy", "Potrącona", "Odliczona od podatku", "Należna zaliczka na podatek dochodowy", "Do wypłaty"};
                for (Pasekwynagrodzen p : lista) {
                    dodajtabeleglowna(p, document);
                    StringBuilder sb = new StringBuilder();
                    sb.append(generujpasekskladniki(p, document));
                    document.add(ustawparagrafSmall(sb.toString()));
                    sb = new StringBuilder();
                    sb.append(generujpasekobecnosci(p, document));
                    document.add(ustawparagrafSmall(sb.toString()));
                    document.add(Chunk.NEWLINE);
                }
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
