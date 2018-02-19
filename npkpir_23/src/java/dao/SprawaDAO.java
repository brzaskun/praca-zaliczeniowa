/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sprawa;
import entity.Uz;
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
public class SprawaDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
    
    public SprawaDAO(){
        super(Sprawa.class);
    }

    
    public  List<Sprawa> findAll(){
        try {
            return wpisFacade.findAll(Sprawa.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

 
    public List<Sprawa> findSprawaByOdbiorca(Uz odbiorca) {
        return wpisFacade.findSprawaByOdbiorca(odbiorca);
    }

    public List<Sprawa> findSprawaByNadawca(Uz nadawca) {
        return wpisFacade.findSprawaByNadawca(nadawca);
    }
}
