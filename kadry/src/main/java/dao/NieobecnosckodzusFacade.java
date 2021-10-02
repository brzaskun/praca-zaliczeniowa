/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Nieobecnosckodzus;
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
public class NieobecnosckodzusFacade extends DAO  {

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

    public NieobecnosckodzusFacade() {
        super(Nieobecnosckodzus.class);
        super.em = em;
    }
    
   
    public Nieobecnosckodzus findByKod(String string) {
        return (Nieobecnosckodzus) getEntityManager().createNamedQuery("Nieobecnosckodzus.findByKod").setParameter("kod", string).getSingleResult();
    }
    
    public Nieobecnosckodzus findByOpis(String opis) {
        return (Nieobecnosckodzus) getEntityManager().createNamedQuery("Nieobecnosckodzus.findByOpis").setParameter("opis", opis).getSingleResult();
    }

    public List<Nieobecnosckodzus> findAktywne() {
        return getEntityManager().createNamedQuery("Nieobecnosckodzus.findByAktywne").getResultList();
    }
}
