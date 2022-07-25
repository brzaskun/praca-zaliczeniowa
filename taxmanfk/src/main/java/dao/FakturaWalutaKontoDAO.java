/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaWalutaKonto;
import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class FakturaWalutaKontoDAO extends DAO implements Serializable{
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

    public FakturaWalutaKontoDAO() {
        super(FakturaWalutaKonto.class);
        super.em = this.em;
    }

   
    public FakturaWalutaKontoDAO(Class entityClass) {
        super(entityClass);
    }
    
    public List<FakturaWalutaKonto> findAll() {
        return findAll();
    }

    public List<FakturaWalutaKonto> findPodatnik(WpisView wpisView) {
        return getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }
    
    public List<FakturaWalutaKonto> findPodatnikAktywne(WpisView wpisView) {
        return getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnikAktywne").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }

    public List<FakturaWalutaKonto> findByWaluta(Podatnik podatnik, String walutafaktury) {
        List<FakturaWalutaKonto> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnikWalutaAktywne").setParameter("podatnik", podatnik).setParameter("symbolwaluty", walutafaktury).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
    
    public List<String> findByWalutaString(Podatnik podatnik, String walutafaktury) {
        List<String> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnikWalutaAktywneString").setParameter("podatnik", podatnik).setParameter("symbolwaluty", walutafaktury).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
}
