/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import webservice.WierszFaktury;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class WierszFakturyFacade extends DAO  implements Serializable {
    private static final long serialVersionUID = 1L;

    
    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public WierszFakturyFacade() {
        super(WierszFaktury.class);
        super.em = em;
    }

   public List<WierszFaktury> findbyRokMc(String rokWpisu, String mc) {
       List<WierszFaktury> zwrot = new ArrayList<>();
       zwrot = getEntityManager().createNamedQuery("WierszFaktury.findByRokMc").setParameter("rok", rokWpisu).setParameter("mc", mc).getResultList();
       return zwrot;
    }
   
   public List<WierszFaktury> findbyNipRokMc(String nip, String rokWpisu, String mc) {
       List<WierszFaktury> zwrot = new ArrayList<>();
       zwrot = getEntityManager().createNamedQuery("WierszFaktury.findByNipRokMc").setParameter("nip", nip).setParameter("rok", rokWpisu).setParameter("mc", mc).getResultList();
       return zwrot;
    }

      
}
