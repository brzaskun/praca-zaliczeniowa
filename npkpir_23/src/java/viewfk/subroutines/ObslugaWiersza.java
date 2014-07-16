/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import entityfk.Dokfk;
import entityfk.StronaMa;
import entityfk.StronaWn;
import entityfk.Wiersz;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaWiersza {
    
    public static Wiersz utworzNowyWiersz(Dokfk selected, int liczbawierszyWDokumencie)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 0);
        uzupelnijDane(nowywiersz, selected);
        return nowywiersz;
    }
    
    public static Wiersz ustawPierwszyWiersz(Dokfk dokfk) {
        Wiersz nowywiersz =  new Wiersz(1, 0);
        uzupelnijDane(nowywiersz, dokfk);
        return nowywiersz;
    }
    
    private static void uzupelnijDane (Wiersz nowywiersz, Dokfk selected) {
        nowywiersz.setDokfk(selected);
        nowywiersz.setTypWiersza(0);
        //nowywiersz.setZaksiegowane(false);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        try {
            nowywiersz.setDataWalutyWiersza(selected.getTabelanbp().getDatatabeli());
        } catch (Exception e) {
            
        }
        StronaWn stronaWn = new StronaWn(nowywiersz);
        StronaMa stronaMa = new StronaMa(nowywiersz);
        nowywiersz.setStronaWn(stronaWn);
        nowywiersz.setStronaMa(stronaMa);
    }
}
