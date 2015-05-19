/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.StornoDok;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class StornoDokDAO extends DAO implements Serializable {
   @Inject private SessionFacade stornoFacade;
      
    public StornoDokDAO() {
        super(StornoDok.class);
    }
   
  public StornoDok find(Integer rok, String mc, String podatnik){
      try{
         return stornoFacade.findStornoDok(rok,mc,podatnik);
      } catch (Exception e) { E.e(e); 
          return null;
      }
     }
  
  public List<StornoDok> find(Integer rok, String podatnik){
         return stornoFacade.findStornoDok(rok,podatnik);
     }
      
}
