/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
import java.io.Serializable;
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
   
   public Sesja find(String nrsesji){
       return sesjaFacade.findSesja(nrsesji);
   }
}
