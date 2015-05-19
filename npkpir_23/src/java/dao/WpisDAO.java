/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Wpis;
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
public class WpisDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
    
    public WpisDAO(){
        super(Wpis.class);
    }

    public Wpis find(String login) {
        return wpisFacade.findWpis(login);
    }
    
    public  List<Wpis> findAll(){
        try {
            return wpisFacade.findAll(Wpis.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
