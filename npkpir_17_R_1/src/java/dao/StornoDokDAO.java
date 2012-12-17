/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
import entity.StornoDok;
import entity.Uz;
import java.io.Serializable;
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
         return stornoFacade.findStornoDok(rok,mc,podatnik);
     }
      
}
