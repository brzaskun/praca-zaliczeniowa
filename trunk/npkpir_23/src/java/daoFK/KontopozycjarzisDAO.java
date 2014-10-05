/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Bilansuklad;
import entityfk.Kontopozycja;
import entityfk.Rzisuklad;
import entityfk.UkladBilansRZiS;
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
        super(Kontopozycja.class);
    }

    public KontopozycjarzisDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<Kontopozycja> findKontaPodatnikUklad (UkladBilansRZiS uklad) {
       try {
           if (uklad instanceof Rzisuklad) {
               return sessionFacade.findKontaPodatnikUklad(((Rzisuklad) uklad).getRzisukladPK().getPodatnik(), ((Rzisuklad) uklad).getRzisukladPK().getRok(), ((Rzisuklad) uklad).getRzisukladPK().getUklad());
           } else {
               return sessionFacade.findKontaPodatnikUklad(((Bilansuklad) uklad).getBilansukladPK().getPodatnik(), ((Bilansuklad) uklad).getBilansukladPK().getRok(), ((Bilansuklad) uklad).getBilansukladPK().getUklad());
           }
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
