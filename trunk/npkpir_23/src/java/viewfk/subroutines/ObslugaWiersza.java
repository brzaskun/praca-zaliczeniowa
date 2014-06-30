/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

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
        nowywiersz.setDokfk(selected);
        nowywiersz.setTypWiersza(0);
        nowywiersz.setZaksiegowane(false);
        return nowywiersz;
    }
    
    public static Wiersze ustawNowyWiersz(Dokfk dokfk) {
        Wiersze nowywiersz =  new Wiersze(1, 0);
        nowywiersz.setDokfk(dokfk);
        nowywiersz.setTypWiersza(0);
        nowywiersz.setZaksiegowane(false);
        return nowywiersz;
    }
}
