/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zamknietemiesiace;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Named
public class ZamknietemiesiaceDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade zamknietemiesiaceFacade;
   
    public ZamknietemiesiaceDAO() {
        super(Zamknietemiesiace.class);
    }
   
   public Zamknietemiesiace findZM(String podatnik){
         return  zamknietemiesiaceFacade.findZM(podatnik);
   }
   
   public  List<Zamknietemiesiace> findAll(){
        try {
            return zamknietemiesiaceFacade.findAll(Zamknietemiesiace.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
