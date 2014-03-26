/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontopozycjarzis;
import entityfk.Rzisuklad;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class KontopozycjarzisDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjarzisDAO() {
        super(Kontopozycjarzis.class);
    }
    
    public List<Kontopozycjarzis> findKontaPodatnikUklad (Rzisuklad rzisuklad) {
       try {
            System.out.println("Pobieram KontoZapisyFKDAO wg numeru");
            return sessionFacade.findKontaPodatnikUklad(rzisuklad.getRzisukladPK().getPodatnik(), rzisuklad.getRzisukladPK().getRok(), rzisuklad.getRzisukladPK().getUklad());
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
