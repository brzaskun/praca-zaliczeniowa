/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import entityfk.Dokfk;
import entityfk.Wiersze;
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
    
    //uzupeelnia wiersze podczas ich wprowadzania badz edycji i zapisuje do bazy, nie rusza pol edycji
    public static void uzupelnijwierszeodane(Dokfk selected) {
        //ladnie uzupelnia informacje o wierszu pk
        String opisdoprzekazania = "";
        List<Wiersze> wierszewdokumencie = selected.getKonta();
        try {
            for (Wiersze p : wierszewdokumencie) {
                String opis = p.getOpis();
                if (opis.contains("kontown")) {
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.getWierszStronaMa().setKwota(0.0);
                    p.setTypwiersza(1);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else if (opis.contains("kontoma")) {
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.getWierszStronaWn().setKwota(0.0);
                    p.setTypwiersza(2);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else {
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setTypwiersza(0);
                    p.setDokfk(selected);
                    opisdoprzekazania = p.getOpis();
                    p.setZaksiegowane(Boolean.FALSE);
                }
            }
        } catch (Exception e) {
        }
    }
    
}
