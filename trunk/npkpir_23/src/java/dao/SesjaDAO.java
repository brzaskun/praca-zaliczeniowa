/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class SesjaDAO extends DAO implements Serializable {
   @Inject private SessionFacade sesjaFacade;
      
    public SesjaDAO() {
        super(Sesja.class);
    }

    public SesjaDAO(Class entityClass) {
        super(entityClass);
    }
    
   
   public  List<Sesja> findAll(){
        try {
            return sesjaFacade.findAll(Sesja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
   public  List<Sesja> findUser(String user){
        try {
            return sesjaFacade.findUser(user);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
   public Sesja find(String nrsesji){
       try{
       return sesjaFacade.findSesja(nrsesji);
       } catch (Exception e) { E.e(e); 
           return null;
       }
       
   }
}
