/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
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
public class KontopozycjaZapisDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjaZapisDAO() {
        super(KontopozycjaZapis.class);
    }

    public KontopozycjaZapisDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    public List<KontopozycjaZapis> findKontaPozycjaBiezacaPodatnikUklad (UkladBR uklad, String rb) {
       try {
            return sessionFacade.findKontaZapisPodatnikUklad(uklad, rb);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb) {
        try {
            sessionFacade.usunZapisaneKontoPozycjaPodatnikUklad(uklad, rb);
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
    public KontopozycjaZapis findByKonto(Konto konto) {
        try {
            return sessionFacade.fintKontoPozycjaZapisByKonto(konto);
        } catch (Exception e) {
            E.e(e);
            return null;
            
        }
    }
    
}
