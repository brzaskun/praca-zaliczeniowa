/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.KontopozycjaBiezaca;
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
public class KontopozycjaBiezacaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjaBiezacaDAO() {
        super(KontopozycjaBiezaca.class);
    }

    public KontopozycjaBiezacaDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<KontopozycjaBiezaca> findKontaPozycjaBiezacaPodatnikUklad (UkladBR uklad, String rb) {
       try {
            return sessionFacade.findKontaBiezacePodatnikUklad(uklad, rb);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb) {
        try {
            sessionFacade.usunZapisaneKontoPozycjaBiezacaPodatnikUklad(uklad, rb);
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
}
