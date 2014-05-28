/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import daoFK.RozrachunekfkDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.Transakcja;
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
        try {
            Rozrachunekfk r = new Rozrachunekfk(wierszStronafk);
            Rozrachunekfk rU = rozrachunekfkDAO.findRozrachunekfk(r);
            if (rU instanceof Rozrachunekfk) {
                rozrachunekfkDAO.destroy(rU);
            }
        } catch (Exception e){
        }
    }

    public static void usuntransakcje(WierszStronafk wierszStronafk, ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO, RozrachunekfkDAO rozrachunekfkDAO) {
        try {
            WierszStronafkPK wierszPK = wierszStronafk.getWierszStronafkPK();
            Zestawienielisttransakcji znaleziona = zestawienielisttransakcjiDAO.findByKlucz(wierszPK);
            List<Transakcja> listatransakcji = znaleziona.getListatransakcji();
            if (listatransakcji != null) {
                for (Transakcja p : listatransakcji) {
                    WierszStronafkPK wierszStronafkPK = p.idSparowany();
                    WierszStronafk wierszStronafksparowany = new WierszStronafk(wierszStronafkPK);
                    Rozrachunekfk r = new Rozrachunekfk(wierszStronafksparowany);
                    double zmienKwotaRozliczono = r.getRozliczono() - p.getKwotatransakcji();
                    r.setRozliczono(zmienKwotaRozliczono);
                    double zmienKwotaPozostala = r.getPozostalo() + p.getKwotatransakcji();
                    r.setPozostalo(zmienKwotaPozostala);
                    rozrachunekfkDAO.edit(r);
                }
            }
            zestawienielisttransakcjiDAO.destroy(znaleziona);
        } catch (Exception e){
        }
    }

}
