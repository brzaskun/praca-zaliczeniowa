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
import kadryiplace.Urzad;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class UrzadFacade extends DAO  implements Serializable {
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

   public UrzadFacade() {
        super(Urzad.class);
        super.em = em;
    }
    
    

    public Urzad findByUrzSerial(int serial) {
        Urzad zwrot = null;
        try {
            zwrot = (Urzad) getEntityManager().createNamedQuery("Urzad.findByUrzSerial").setParameter("urzSerial", serial).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
}
