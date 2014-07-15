/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import entityfk.Dokfk;
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
    
    public static Wiersz utworzNowyWiersz(Dokfk selected, String podatnik, int liczbawierszyWDokumencie)  {
        Wiersz nowywiersz = new Wiersz(liczbawierszyWDokumencie, 0);
        nowywiersz.setDokfk(selected);
        nowywiersz.setTypWiersza(0);
        //nowywiersz.setZaksiegowane(false);
        nowywiersz.setTabelanbp(selected.getTabelanbp());
        return nowywiersz;
    }
    
    public static Wiersz ustawNowyWiersz(Dokfk dokfk) {
        Wiersz nowywiersz =  new Wiersz(1, 0);
        nowywiersz.setDokfk(dokfk);
        nowywiersz.setTypWiersza(0);
        //nowywiersz.setZaksiegowane(false);
        nowywiersz.setTabelanbp(dokfk.getTabelanbp());
        return nowywiersz;
    }
}
