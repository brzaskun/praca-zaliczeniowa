/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontopozycjarzis;
import entityfk.Rzisuklad;
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
public class KontopozycjarzisDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjarzisDAO() {
        super(Kontopozycjarzis.class);
    }

    public KontopozycjarzisDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<Kontopozycjarzis> findKontaPodatnikUklad (Rzisuklad rzisuklad) {
       try {
            return sessionFacade.findKontaPodatnikUklad(rzisuklad.getRzisukladPK().getPodatnik(), rzisuklad.getRzisukladPK().getRok(), rzisuklad.getRzisukladPK().getUklad());
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
