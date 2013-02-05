/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Srodkikst;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class SrodkikstDAO extends DAO implements Serializable{
     @Inject private SessionFacade srodkikstFacade;
      
    public SrodkikstDAO() {
        super(Srodkikst.class);
    }
   
    public Srodkikst finsStr(String nazwa){
        return srodkikstFacade.findSrodekkst(nazwa);
    }
}
