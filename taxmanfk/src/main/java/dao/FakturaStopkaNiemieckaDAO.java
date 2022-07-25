/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaStopkaNiemiecka;
import entity.Podatnik;
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
public class FakturaStopkaNiemieckaDAO  extends DAO implements Serializable {


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

    public FakturaStopkaNiemieckaDAO() {
        super(FakturaStopkaNiemiecka.class);
        super.em = this.em;
    }

    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

  
    public FakturaStopkaNiemiecka findByPodatnik(Podatnik podatnikObiekt) {
        FakturaStopkaNiemiecka zwrot = null;
        try {
            zwrot = (FakturaStopkaNiemiecka)  getEntityManager().createNamedQuery("FakturaStopkaNiemiecka.findByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
        } catch (Exception e) {
            //E.e(e); 
        }
        return zwrot;
    }
    
}
