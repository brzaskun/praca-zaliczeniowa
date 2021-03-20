/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entityfk.Delegacja;
import error.E;
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

public class DelegacjaDAO extends DAO implements Serializable{
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

    public DelegacjaDAO() {
        super(Delegacja.class);
        super.em = this.em;
    }
 
    public List<Delegacja> findDelegacjaPodatnik(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return getEntityManager().createNamedQuery("Delegacja.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getResultList();
    }

    public int countDelegacja(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return (int) getEntityManager().createNamedQuery("Delegacja.countByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getSingleResult();
    }

    public Delegacja findDelegacja(Delegacja delegacja) {
        try {
            return (Delegacja) getEntityManager().createNamedQuery("Delegacja.findById").setParameter("id", delegacja.getId()).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public int findDelegacjaByNr(String nrdelegacji) {
        int jest1niema0 = 0;
        try {
            Delegacja p = (Delegacja)  getEntityManager().createNamedQuery("Delegacja.findByOpisdlugiOnly").setParameter("opisdlugi", nrdelegacji).getSingleResult();
            if (p != null) {
                jest1niema0 = 1;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return jest1niema0;
    }
}
