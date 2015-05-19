/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class UkladBRDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public UkladBRDAO() {
        super(UkladBR.class);
    }

    public UkladBRDAO(Class entityClass) {
        super(entityClass);
    }
    public UkladBR findukladBR(UkladBR ukladBR) {
        return sessionFacade.findUkladBRUklad(ukladBR);
    }
    public  List<UkladBR> findAll(){
        try {
            return sessionFacade.findAll(UkladBR.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UkladBR> findPodatnik(String nazwapelna) {
        try {
            return sessionFacade.findUkladBRPodatnik(nazwapelna);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
}
