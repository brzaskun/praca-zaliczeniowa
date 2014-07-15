/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import java.util.ArrayList;
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

    public static String pobierzSymbolPoprzedniegoDokfk(Dokfk selected) {
        String symbolPoprzedniegoDokumentu = "";
        try {
            symbolPoprzedniegoDokumentu = new String(selected.getDokfkPK().getSeriadokfk());
        } catch (Exception e) {
        }
        return symbolPoprzedniegoDokumentu;
    }

    public static void dodajWalutyDoDokumentu(WalutyDAOfk walutyDAOfk, TabelanbpDAO tabelanbpDAO, Dokfk selected) {
        selected.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty("PLN"));
        Tabelanbp tabelanbpPLN = tabelanbpDAO.findByTabelaPLN();
        selected.setTabelanbp(tabelanbpPLN);
        List<Wiersz> wiersze = selected.getListawierszy();
        for (Wiersz p : wiersze) {
            p.setTabelanbp(tabelanbpPLN);
        }
    }
}
