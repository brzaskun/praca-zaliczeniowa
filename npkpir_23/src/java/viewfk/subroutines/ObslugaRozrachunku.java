/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class ObslugaRozrachunku {

//    public static void utrwalNoweRozachunki(List<Rozrachunekfk> pobierznowododane, RozrachunekfkDAO rozrachunekfkDAO) {
//        for (Rozrachunekfk p : pobierznowododane) {
//            p.setZaksiegowanodokument(true);
//            rozrachunekfkDAO.edit(p);
//        }
//    }
//
//    public static void usunrozrachunek(Rozrachunekfk rozrachunekfk, RozrachunekfkDAO rozrachunekfkDAO) {
//        try {
//            Rozrachunekfk rU = rozrachunekfkDAO.findRozrachunekfk(rozrachunekfk);
//            if (rU instanceof Rozrachunekfk) {
//                rozrachunekfkDAO.destroy(rU);
//            }
//        } catch (Exception e){
//        }
//    }

   // public static void usuntransakcje(Rozrachunekfk rozrachunekfk, TransakcjaDAO transakcjaDAO, RozrachunekfkDAO rozrachunekfkDAO) {
//        try {
//            WierszStronafkPK wierszPK = wierszStronafk.getWierszStronafkPK();
//            //Transakcja znaleziona = transakcjaDAO.findByKlucz(wierszPK);
//            List<Transakcja> listatransakcji = znaleziona.getListatransakcji();
//            if (listatransakcji != null) {
//                for (Transakcja p : listatransakcji) {
//                    WierszStronafkPK wierszStronafkPK = p.idSparowany();
//                    WierszStronafk wierszStronafksparowany = new WierszStronafk(wierszStronafkPK);
//                    Rozrachunekfk r = new Rozrachunekfk(wierszStronafksparowany);
//                    double zmienKwotaRozliczono = r.getRozliczono() - p.getKwotatransakcji();
//                    r.setRozliczono(zmienKwotaRozliczono);
//                    double zmienKwotaPozostala = r.getPozostalo() + p.getKwotatransakcji();
//                    r.setPozostalo(zmienKwotaPozostala);
//                    rozrachunekfkDAO.edit(r);
//                }
//            }
//        } catch (Exception e){
//        }
 //   }

}
