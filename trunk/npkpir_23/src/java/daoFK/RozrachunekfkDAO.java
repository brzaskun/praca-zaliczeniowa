/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
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

    public List<Rozrachunekfk> findRozrachunkifkByKonto(String nrkonta, String wnma) {
         try {
            System.out.println("Szukam Rozrachunekfk");
            return rozrachunekfkFacade.findRozrachunkifkByKonto(nrkonta, wnma);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
}
