/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Rejestrlogowan;
import java.io.Serializable;
import java.util.List;
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
public class RejestrlogowanDAO  extends DAO implements Serializable {
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

    public RejestrlogowanDAO() {
        super(Rejestrlogowan.class);
        super.em = this.em;
    }

 
    public RejestrlogowanDAO(Class entityClass) {
        super(entityClass);
    }
    

    public Rejestrlogowan findByIP(String ipusera) {
        Rejestrlogowan zwrot = null;
        try {
            zwrot = (Rejestrlogowan)  getEntityManager().createNamedQuery("Rejestrlogowan.findByIpusera").setParameter("ipusera", ipusera).getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }


    public List<Rejestrlogowan> findByLiczbalogowan0() {
        return  getEntityManager().createNamedQuery("Rejestrlogowan.findByIloscLogowan0").getResultList();
    }

  
    
    
}
