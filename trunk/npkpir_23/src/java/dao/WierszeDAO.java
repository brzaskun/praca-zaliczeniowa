/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class WierszeDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade wierszeFacade;
    
    public WierszeDAO() {
        super(Wiersz.class);
    }
    
    public  List<Wiersz> findAll(){
        try {
            return wierszeFacade.findAll(Wiersz.class);
        } catch (Exception e) {
            return null;
        }
   }

    public List<Wiersz> findDokfkRozrachunki(String podatnik, Konto konto, DokfkPK dokfkPK) {
         try {
           return wierszeFacade.findWierszefkRozrachunki(podatnik, konto, dokfkPK);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszeZapisy(String podatnik, String konto) {
         try {
           return wierszeFacade.findWierszeZapisy(podatnik, konto);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Wiersz> findWierszePodatnik(String podatnik) {
         try {
           return wierszeFacade.findWierszePodatnik(podatnik);
       } catch (Exception e ){
           return null;
       }
    }
}
