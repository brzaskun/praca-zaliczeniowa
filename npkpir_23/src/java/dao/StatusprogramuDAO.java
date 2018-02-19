/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Statusprogramu;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class StatusprogramuDAO  extends DAO implements Serializable {
   @Inject private SessionFacade sesjaFacade;
      
    public StatusprogramuDAO() {
        super(Statusprogramu.class);
    }

    public StatusprogramuDAO(Class entityClass) {
        super(entityClass);
    }
    
     public  List<Statusprogramu> findAll(){
        try {
            return sesjaFacade.findAll(Statusprogramu.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
}
