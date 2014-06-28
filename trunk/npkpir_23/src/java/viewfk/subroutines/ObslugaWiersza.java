/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import entityfk.WierszStronafk;
import entityfk.WierszStronafkPK;
import entityfk.Dokfk;
import entityfk.Wiersze;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaWiersza {
    
    public static Wiersze utworzNowyWiersz(Dokfk selected, String podatnik, int liczbawierszyWDokumencie, String grafikawaluty) {
        Wiersze nowywiersz = new Wiersze(liczbawierszyWDokumencie, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKWn = new WierszStronafkPK();
        wierszStronafkWn.setWierszStronafkPK(dodajdanedowiersza(selected, podatnik, liczbawierszyWDokumencie, wierszStronafkPKWn, "Wn"));
        wierszStronafkWn.setGrafikawaluty(grafikawaluty);
        wierszStronafkWn.setWiersz(nowywiersz);
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKMa = new WierszStronafkPK();
        wierszStronafkMa.setWierszStronafkPK(dodajdanedowiersza(selected, podatnik, liczbawierszyWDokumencie, wierszStronafkPKMa, "Ma"));
        wierszStronafkMa.setGrafikawaluty(grafikawaluty);
        wierszStronafkMa.setWiersz(nowywiersz);
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        return nowywiersz;
    }
    
    public static WierszStronafkPK dodajdanedowiersza(Dokfk selected, String podatnik, int numer, WierszStronafkPK w, String wnma) {
        w.setPodatnik(podatnik);
        w.setNrPorzadkowyWiersza(numer);
        w.setTypdokumentu(selected.getDokfkPK().getSeriadokfk());
        w.setNrkolejnydokumentu(selected.getDokfkPK().getNrkolejnywserii());
        w.setStronaWnlubMa(wnma);
        return w;
    }
    
                     
    public static WierszStronafk uzupelnijdaneWwierszu(Dokfk selected, int numer, WierszStronafk wierszStronaFK, String wnma, int lpwiersza, String podatnik) {
        wierszStronaFK.setOpiswiersza(selected.getListawierszy().get(lpwiersza).getOpis());
        //rzeczy dotyczace waluty
        WierszStronafkPK wPK = wierszStronaFK.getWierszStronafkPK();
        wPK.setNrPorzadkowyWiersza(numer);
        wPK.setPodatnik(podatnik);
        wPK.setTypdokumentu(selected.getDokfkPK().getSeriadokfk());
        wPK.setNrkolejnydokumentu(selected.getDokfkPK().getNrkolejnywserii());
        wPK.setStronaWnlubMa(wnma);
        //uzupelniamy dane do walut jezeli sa
        try {
            wierszStronaFK.setSymbolwaluty(selected.getWalutadokumentu());
            wierszStronaFK.setDatawaluty(selected.getTabelanbp().getDatatabeli());
            wierszStronaFK.setKurswaluty(selected.getTabelanbp().getKurssredni());
            wierszStronaFK.setNrtabelinbp(selected.getTabelanbp().getTabelanbpPK().getNrtabeli());
            wierszStronaFK.setKwotaWaluta(wierszStronaFK.getKwota());
        } catch (Exception e) {
            
        }
        return wierszStronaFK;
    }
    
    public static Wiersze ustawNowyWiersz() {
        Wiersze nowywiersz =  new Wiersze(1, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        wierszStronafkWn.setGrafikawaluty("zł");
        wierszStronafkWn.setWiersz(nowywiersz);
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        nowywiersz.getWierszStronaWn().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        wierszStronafkMa.setGrafikawaluty("zł");
        wierszStronafkMa.setWiersz(nowywiersz);
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        nowywiersz.getWierszStronaMa().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        return nowywiersz;
    }
}
