/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import entityfk.Dokfk;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class UzupelnijWierszeoDane implements Serializable {
    
    //uzupeelnia wiersze podczas ich wprowadzania badz edycji, nie rusza pol edycji
    public static void uzupelnijWierszeoDate(Dokfk selected) {
        if (!selected.getRodzajedok().isTylkojpk()) {
            //ladnie uzupelnia informacje o wierszu pk
            List<Wiersz> wierszewdokumencie = selected.getListawierszy();
            try {
                for (Wiersz p : wierszewdokumencie) {
                        p.setDataksiegowania(selected.getDatawystawienia());
                }
            } catch (Exception e) {
            }
        }
    }
    
}
