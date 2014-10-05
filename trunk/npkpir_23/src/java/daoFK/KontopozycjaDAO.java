/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontopozycja;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class KontopozycjaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjaDAO() {
        super(Kontopozycja.class);
    }

    public KontopozycjaDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<Kontopozycja> findKontaPodatnikUklad (UkladBR uklad) {
       try {
            return sessionFacade.findKontaPodatnikUklad(uklad.getUkladBRPK().getPodatnik(), uklad.getUkladBRPK().getRok(), uklad.getUkladBRPK().getUklad());
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
