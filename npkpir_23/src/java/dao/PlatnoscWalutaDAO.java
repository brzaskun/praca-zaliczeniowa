/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Evopis;
import entity.PlatnoscWaluta;
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
public class PlatnoscWalutaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evopisFacade;

    public PlatnoscWalutaDAO() {
        super(PlatnoscWaluta.class);
    }
    
    public  List<PlatnoscWaluta> findAll(){
        try {
            return evopisFacade.findAll(PlatnoscWaluta.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<PlatnoscWaluta> findByDok(Dok selected) {
        return sessionFacade.getEntityManager().createNamedQuery("PlatnoscWaluta.findByDok").setParameter("dokument", selected).getResultList();
    }
}
