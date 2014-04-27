/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk.subroutines;

import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Zestawienielisttransakcji;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaTransakcji {
    
    public static void zaksiegujSparowaneTransakcje(WierszStronafk wierszStronafk, ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO) {
            if (wierszStronafk.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                    WierszStronafkPK wierszStronafkPK = wierszStronafk.getWierszStronafkPK();
                    Zestawienielisttransakcji zestawienielisttransakcji = zestawienielisttransakcjiDAO.findByKlucz(wierszStronafkPK);
                    if (zestawienielisttransakcji instanceof Zestawienielisttransakcji) {
                        zestawienielisttransakcji.setZaksiegowanodokument(true);
                        zestawienielisttransakcjiDAO.edit(zestawienielisttransakcji);
                    }
            }
    }
    
}
