/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import error.E;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import viewfk.subroutines.ObslugaWiersza;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class DokFKBean {

    
   
    public static void dodajWaluteDomyslnaDoDokumentu(WalutyDAOfk walutyDAOfk, TabelanbpDAO tabelanbpDAO, Dokfk selected) {
        selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty("PLN"));
        Tabelanbp tabelanbpPLN = null;
        try {
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        } catch (Exception e) {
            E.e(e);
        }
        if (tabelanbpPLN == null) {
            tabelanbpDAO.dodaj(new Tabelanbp("000/A/NBP/0000",walutyDAOfk.findWalutaBySymbolWaluty("PLN"),"2012-01-01"));
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        }
        selected.setTabelanbp(tabelanbpPLN);
        List<Wiersz> wiersze = selected.getListawierszy();
        for (Wiersz p : wiersze) {
            p.setTabelanbp(tabelanbpPLN);
        }
    }
    
    public static Rodzajedok odnajdzZZ(List<Rodzajedok> rodzajedokKlienta) {
         for (Rodzajedok p : rodzajedokKlienta) {
             if (p.getSkrot().equals("ZZ")) {
                 return p;
             }
         }
         return null;
    }
    
    public static int sortZaksiegowaneDok(Object o1, Object o2) {
        String datao1 = ((Dokfk) o1).getDatadokumentu();
        String datao2 = ((Dokfk) o2).getDatadokumentu();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
            datao2date = formatter.parse(datao2);
            if (datao1date.before(datao2date)) {
                return -1;
            } else if (datao1date.after(datao2date)) {
                return 1;
            } else {
                return porownajseriedok(((Dokfk) o1),((Dokfk) o2));
            }
        } catch (Exception e) {
            E.e(e);
            return 0;
        }
    }
    
    private static int porownajseriedok(Dokfk o1, Dokfk o2) {
        String seriao1 = o1.getDokfkPK().getSeriadokfk();
        String seriao2 = o2.getDokfkPK().getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1,o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }
    
    private static int porownajnrserii(Dokfk o1, Dokfk o2) {
        int seriao1 = o1.getDokfkPK().getNrkolejnywserii();
        int seriao2 = o2.getDokfkPK().getNrkolejnywserii();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
    public static boolean sprawdzczyWnRownasieMa(Wiersz wierszbiezacy) {
        Konto kontoWn;
        Konto kontoMa;
        boolean czyWszystkoWprowadzono = false;
        int typ = wierszbiezacy.getTypWiersza();
         if (!wierszbiezacy.getDokfk().getDokfkPK().getSeriadokfk().equals("BO")) {
                if ((typ == 0 || typ == 5)) {
                    kontoWn = wierszbiezacy.getStronaWn().getKonto();
                    kontoMa = wierszbiezacy.getStronaMa().getKonto();
                    if (kontoWn instanceof Konto && kontoMa instanceof Konto) {
                        czyWszystkoWprowadzono = true;
                    }
                } else if (typ == 7 || typ == 2) {
                    kontoMa = wierszbiezacy.getStronaMa().getKonto();
                    if (kontoMa instanceof Konto) {
                        czyWszystkoWprowadzono = true;
                    }
                } else if (typ == 6 || typ == 1) {
                    kontoWn = wierszbiezacy.getStronaWn().getKonto();
                    if (kontoWn instanceof Konto) {
                        czyWszystkoWprowadzono = true;
                    }
                }
            } else {
                czyWszystkoWprowadzono = true;
            }
         return czyWszystkoWprowadzono;
    }
    
    
}
