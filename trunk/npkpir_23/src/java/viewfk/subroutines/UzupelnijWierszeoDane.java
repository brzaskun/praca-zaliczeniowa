/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import entityfk.Dokfk;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class UzupelnijWierszeoDane implements Serializable {
    
    //uzupeelnia wiersze podczas ich wprowadzania badz edycji, nie rusza pol edycji
    public static void uzupelnijwierszeodane(Dokfk selected) {
        //ladnie uzupelnia informacje o wierszu pk
        List<Wiersz> wierszewdokumencie = selected.getListawierszy();
        try {
            for (Wiersz p : wierszewdokumencie) {
                String opis = p.getOpisWiersza();
                if (opis.contains("kontown")) {
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.getStronaMa().setKwota(0.0);
                    p.setTypWiersza(1);
                    p.setDokfk(selected);
                } else if (opis.contains("kontoma")) {
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.getStronaWn().setKwota(0.0);
                    p.setTypWiersza(2);
                    p.setDokfk(selected);
                } else {
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setTypWiersza(0);
                    p.setDokfk(selected);
                }
            }
        } catch (Exception e) {
        }
    }
    
}
