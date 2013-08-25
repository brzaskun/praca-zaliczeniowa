/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import entityfk.Rozrachunki;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class RozrachunkiDAO extends DAO implements Serializable{
    @Inject private SessionFacade rozrachunkiFacade;

    public RozrachunkiDAO() {
        super(Rozrachunki.class);
    }
    
    public  List<Rozrachunki> findAll(){
        try {
            System.out.println("Pobieram RozrachunkiDAO");
            return rozrachunkiFacade.findAll(Rozrachunki.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public  List<Rozrachunki> findRozliczany(Integer zapisrozliczany){
        try {
            System.out.println("Pobieram RozrachunkiDAO wg Rozlczanego");
            return rozrachunkiFacade.findRozliczany(zapisrozliczany);
        } catch (Exception e) {
            return null;
        }
   }
}
