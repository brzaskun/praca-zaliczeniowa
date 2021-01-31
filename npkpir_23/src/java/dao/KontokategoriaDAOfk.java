/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entityfk.Kontokategoria;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class KontokategoriaDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

 @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public KontokategoriaDAOfk() {
        super(Kontokategoria.class);
        super.em = this.em;
    }
   
   
   
      
}
