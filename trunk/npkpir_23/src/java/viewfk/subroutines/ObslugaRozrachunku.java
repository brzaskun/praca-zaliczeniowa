/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import daoFK.RozrachunekfkDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Rozrachunekfk;
import entityfk.Zestawienielisttransakcji;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class ObslugaRozrachunku {

    public static void utrwalNoweRozachunki(List<Rozrachunekfk> pobierznowododane, RozrachunekfkDAO rozrachunekfkDAO) {
        for (Rozrachunekfk p : pobierznowododane) {
            p.setZaksiegowanodokument(true);
            rozrachunekfkDAO.edit(p);
        }
    }

    public static void usunrozrachunek(WierszStronafk wierszStronafk, RozrachunekfkDAO rozrachunekfkDAO) {
        Rozrachunekfk r = new Rozrachunekfk(wierszStronafk);
        Rozrachunekfk rU = rozrachunekfkDAO.findRozrachunekfk(r);
        rozrachunekfkDAO.destroy(rU);
    }

    public static void usuntransakcje(WierszStronafk wierszStronafk, ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO) {
        WierszStronafkPK wierszPK = wierszStronafk.getWierszStronafkPK();
        Zestawienielisttransakcji znaleziona = zestawienielisttransakcjiDAO.findByKlucz(wierszPK);
        zestawienielisttransakcjiDAO.destroy(znaleziona);
    }

}
