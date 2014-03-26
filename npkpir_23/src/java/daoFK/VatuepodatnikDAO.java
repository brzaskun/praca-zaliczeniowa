/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Rzisuklad;
import entityfk.Vatuepodatnik;
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
public class VatuepodatnikDAO extends DAO<Vatuepodatnik> implements Serializable {
    @Inject
    private SessionFacade sessionFacade;

    public VatuepodatnikDAO() {
        super(Vatuepodatnik.class);
    }
    
    public  List<Vatuepodatnik> findAll(){
        try {
            return sessionFacade.findAll(Vatuepodatnik.class);
        } catch (Exception e) {
            return null;
        }
   }

    public Vatuepodatnik find(String rokWpisu, String miesiacWpisu, String podatnikWpisu) {
        try {
            return (Vatuepodatnik) sessionFacade.findVatuepodatnik(rokWpisu, miesiacWpisu, podatnikWpisu);
        } catch (Exception e) {
            return null;
        }
    }
    
}
