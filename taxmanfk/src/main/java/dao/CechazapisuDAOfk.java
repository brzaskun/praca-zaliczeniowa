/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Podatnik;
import entityfk.Cechazapisu;
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
public class CechazapisuDAOfk extends DAO implements Serializable {
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

    public CechazapisuDAOfk() {
        super(Cechazapisu.class);
        super.em = this.em;
    }
   
     
    public List<Cechazapisu> findPodatnikOnly(Podatnik podatnikObiekt) {
        List<Cechazapisu> zwrot = null;
        try {
            zwrot =  getEntityManager().createNamedQuery("Cechazapisu.findByPodatnikOnly").setParameter("podatnik", podatnikObiekt).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
    
    public List<Cechazapisu> findPodatnikOnlyAktywne(Podatnik podatnikObiekt) {
         List<Cechazapisu> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Cechazapisu.findByPodatnikOnlyAktywne").setParameter("podatnik", podatnikObiekt).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
    
    public List<Cechazapisu> findPodatnikOnlyStatystyczne(Podatnik podatnikObiekt) {
          List<Cechazapisu> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Cechazapisu.findByPodatnikOnlyStatystyczne").setParameter("podatnik", podatnikObiekt).getResultList();
        } catch (Exception e){}
        return zwrot;
    }

    public List<Cechazapisu> findPodatnik(Podatnik podatnikObiekt) {
          List<Cechazapisu> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Cechazapisu.findByPodatnik").setParameter("podatnik", podatnikObiekt).getResultList();
        } catch (Exception e){}
        return zwrot;
    }

    public Cechazapisu findPodatniknkup() {
          Cechazapisu zwrot = null;
        try {
            zwrot = (Cechazapisu) getEntityManager().createNamedQuery("Cechazapisu.findByPodatnikNKUP").setParameter("nazwacechy", "NKUP").getSingleResult();
        } catch (Exception e){}
        return zwrot;
    }
    
}
