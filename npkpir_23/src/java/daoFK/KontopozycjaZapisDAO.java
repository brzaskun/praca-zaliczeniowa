/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
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
    
    
    public List<KontopozycjaZapis> findKontaPozycjaBiezacaPodatnikUklad (UkladBR uklad) {
       try {
            return sessionFacade.findKontaZapisPodatnikUklad(uklad);
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
