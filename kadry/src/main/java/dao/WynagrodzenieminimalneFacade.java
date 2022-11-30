/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Wynagrodzenieminimalne;
import java.io.Serializable;
import java.util.ArrayList;
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
public class WynagrodzenieminimalneFacade extends DAO implements Serializable {
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

    public WynagrodzenieminimalneFacade() {
        super(Wynagrodzenieminimalne.class);
        super.em = em;
    }

    public List<Wynagrodzenieminimalne> findByRok(String rokWpisu) {
        List<Wynagrodzenieminimalne> zwrot = new ArrayList<>();
        try {
            zwrot =  getEntityManager().createNamedQuery("Wynagrodzenieminimalne.findByRok").setParameter("rok", rokWpisu).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

 }
