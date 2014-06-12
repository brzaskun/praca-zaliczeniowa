/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.WierszStronafkPK;
import entityfk.Rozrachunekfk;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class RozrachunekfkDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade rozrachunekfkFacade;

    public RozrachunekfkDAO() {
        super(Rozrachunekfk.class);
    }

    public RozrachunekfkDAO(Class entityClass) {
        super(entityClass);
    }
    
    public  List<Rozrachunekfk> findAll(){
        try {
            return rozrachunekfkFacade.findAll(Rozrachunekfk.class);
        } catch (Exception e) {
            return null;
        }
   }
    public  List<Rozrachunekfk> findRozrachybekfkByPodatnik(String podatnik){
        try {
            return rozrachunekfkFacade.findRozrachunekfkByPodatnik(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
    
    public  List<Rozrachunekfk> findRozrachunkifkByPodatnikKonto(String podatnik, String nrkonta){
        try {
            return rozrachunekfkFacade.findRozrachunekfkByPodatnikkonto(podatnik, nrkonta);
        } catch (Exception e) {
            return null;
        }
   }
    
    public  List<Rozrachunekfk> findRozrachunkifkByPodatnikKontoWaluta(String podatnik, String nrkonta, String waluta){
        try {
            return rozrachunekfkFacade.findRozrachunekfkByPodatnikKontoWaluta(podatnik, nrkonta, waluta);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Rozrachunekfk> findRozrachunkifkByPodatnikKontoWalutaSelekcja(String podatnik, String nrkonta, String waluta, String zakres) {
        try {
            switch (zakres) {
                case "wszystkie":
                    return rozrachunekfkFacade.findRozrachunekfkByPodatnikKontoWaluta(podatnik, nrkonta, waluta);
                case "rozliczone":
                    return rozrachunekfkFacade.findRozrachunekfkByPodatnikKontoWalutaRozliczone(podatnik, nrkonta, waluta);
                case "częściowo":
                    return rozrachunekfkFacade.findRozrachunekfkByPodatnikKontoWalutaCzesciowo(podatnik, nrkonta, waluta);
                case "nowe":
                    return rozrachunekfkFacade.findRozrachunekfkByPodatnikKontoWalutaNowe(podatnik, nrkonta, waluta);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
    
    public Rozrachunekfk findRozrachunekfk(Rozrachunekfk p) {
        try {
            return rozrachunekfkFacade.findRozrachunekfk(p);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunkifkByKontoWnMaWaluta(String nrkonta, String wnma, String waluta) {
         try {
            return rozrachunekfkFacade.findRozrachunkifkByKonto(nrkonta, wnma, waluta);
        } catch (Exception e) {
            return null;
        }
    }
    
//     public List<Rozrachunekfk> findRozrachunkifkByKontoWnMaWalutaAktualny(String nrkonta, String wnma, String waluta) {
//         try {
//            return rozrachunekfkFacade.findRozrachunkifkByKontoAktualny(nrkonta, wnma, waluta);
//        } catch (Exception e) {
//            return null;
//        }
//    }
    
     public Rozrachunekfk findRozrachunkifkByWierszStronafk(WierszStronafkPK wierszStronafkPK) {
         try {
            return rozrachunekfkFacade.findRozrachunkifkByWierszStronafk(wierszStronafkPK);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findByDokfk(String seriadokfk, int nrkolejny, String podatnik, String rok) {
         try {
            return rozrachunekfkFacade.findRozrachunkifkByDokfk(seriadokfk, nrkolejny, podatnik, rok);
        } catch (Exception e) {
            return null;
        }
    }

    public void usunniezaksiegowane(String podatnik) {
        try {
          rozrachunekfkFacade.usunniezaksiegowane(podatnik);
        } catch (Exception e)
        {
        }
    }

    
}
