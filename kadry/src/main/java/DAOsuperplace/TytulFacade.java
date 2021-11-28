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

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class TytulFacade extends DAO  implements Serializable {
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

   public TytulFacade() {
        super(kadryiplace.Tytul.class);
        super.em = em;
    }
    
    

}
