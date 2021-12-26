/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOsuperplace;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import kadryiplace.WynKodSkl;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class WynKodSklFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "microsoft")
    private EntityManager em;
    
//    @PreDestroy
//    private void preDestroy() {
//        em.clear();
//        em.close();
//        em.getEntityManagerFactory().close();
//        em = null;
//        
//    }

    protected EntityManager getEntityManager() {
        return em;
    }

   public WynKodSklFacade() {
        super(kadryiplace.WynKodSkl.class);
        super.em = em;
    }

    public WynKodSkl findById(int serial) {
         WynKodSkl zwrot = null;
        try {
            zwrot = (WynKodSkl) getEntityManager().createNamedQuery("WynKodSkl.findByWksSerial").setParameter("wksSerial", serial).getSingleResult();
        } catch (Exception e){}
        return zwrot;
        
    }
    
    

}
