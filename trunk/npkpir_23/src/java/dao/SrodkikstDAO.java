/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Srodkikst;
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
public class SrodkikstDAO extends DAO implements Serializable{
     @Inject private SessionFacade srodkikstFacade;
      
    public SrodkikstDAO() {
        super(Srodkikst.class);
    }
   
    public List<Srodkikst> finsStr(String nazwa){
        return srodkikstFacade.findSrodekkst(nazwa);
    }
    
    public Srodkikst finsStr1(String nazwa){
        return srodkikstFacade.findSrodekkst1(nazwa);
    }
    
    public Srodkikst find(Srodkikst srodek){
        return srodkikstFacade.findSr(srodek);
    }
    
    public  List<Srodkikst> findAll(){
        try {
            return srodkikstFacade.findAll(Srodkikst.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
