/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Mce;
import entity.Podstawki;
import java.io.Serializable;
import javax.annotation.PreDestroy;
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
public class PodStawkiDAO extends DAO implements Serializable{

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

    public PodStawkiDAO() {
        super(Podstawki.class);
        super.em = this.em;
    }

   

    public Podstawki find(Integer rok, String mc){
        Podstawki zwrot = null;
        int mcint = Mce.getMiesiacToNumber().get(mc);
        if (rok < 2022 || rok > 2022) {
            zwrot = (Podstawki)  getEntityManager().createNamedQuery("Podstawki.findByRok").setParameter("rok", rok).getSingleResult();
        } else if (mcint<6){
            zwrot = (Podstawki)  getEntityManager().createNamedQuery("Podstawki.findByRok2022").setParameter("rok", rok).getSingleResult();
        } else {
            zwrot = (Podstawki)  getEntityManager().createNamedQuery("Podstawki.findByRok202207").setParameter("rok", rok).getSingleResult();
        }
        return zwrot;
     }
   
   
}
