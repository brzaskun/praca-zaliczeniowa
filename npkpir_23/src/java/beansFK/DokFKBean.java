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
import entityfk.DokfkPK;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import viewfk.subroutines.ObslugaWiersza;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class DokFKBean {

    
   
    public static void dodajWalutyDoDokumentu(WalutyDAOfk walutyDAOfk, TabelanbpDAO tabelanbpDAO, Dokfk selected) {
        selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty("PLN"));
        Tabelanbp tabelanbpPLN = null;
        try {
            tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        } catch (Exception e) {
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
    
}
