/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Granica;
import error.E;
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
public class GranicaDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade zamknietemiesiaceFacade;
   
    public GranicaDAO() {
        super(Granica.class);
    }
   
     
   public  List<Granica> findAll(){
        try {
            return zamknietemiesiaceFacade.findAll(Granica.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<Granica> findByRok(String rokWpisuSt) {
        return sessionFacade.getEntityManager().createNamedQuery("Granica.findByRok").setParameter("rok", rokWpisuSt).getResultList();
    }
}
