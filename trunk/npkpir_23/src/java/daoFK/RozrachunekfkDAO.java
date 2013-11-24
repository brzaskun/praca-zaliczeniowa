/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Kontozapisy;
import entityfk.Rozrachunekfk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class RozrachunekfkDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade rozrachunekfkFacade;

    public RozrachunekfkDAO() {
        super(Rozrachunekfk.class);
    }
    
    public  List<Rozrachunekfk> findAll(){
        try {
            System.out.println("Pobieram RozrachunekfkDAO");
            return rozrachunekfkFacade.findAll(Rozrachunekfk.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public Rozrachunekfk findRozrachunekfk(Rozrachunekfk p) {
        try {
            System.out.println("Szukam Rozrachunekfk");
            return rozrachunekfkFacade.findRozrachunekfk(p);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunkifkByKonto(String nrkonta, String wnma, String waluta) {
         try {
            System.out.println("Szukam Rozrachunekfk");
            return rozrachunekfkFacade.findRozrachunkifkByKonto(nrkonta, wnma, waluta);
        } catch (Exception e) {
            return null;
        }
    }
    
     public Rozrachunekfk findRozrachunkifkByWierszStronafk(WierszStronafkPK wierszStronafkPK) {
         try {
            System.out.println("Szukam Rozrachunekfk by WierszStronafk");
            return rozrachunekfkFacade.findRozrachunkifkByWierszStronafk(wierszStronafkPK);
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
