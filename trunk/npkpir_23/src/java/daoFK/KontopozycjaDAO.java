/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.KontopozycjaBiezaca;
import entityfk.UkladBR;
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
public class KontopozycjaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjaDAO() {
        super(KontopozycjaBiezaca.class);
    }

    public KontopozycjaDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<KontopozycjaBiezaca> findKontaPodatnikUklad (UkladBR uklad) {
       try {
            return sessionFacade.findKontaPodatnikUklad(uklad);
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
