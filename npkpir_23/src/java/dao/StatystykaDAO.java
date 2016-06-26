/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Statystyka;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class StatystykaDAO  extends DAO implements Serializable{

   
    public StatystykaDAO() {
        super(Statystyka.class);
    }
    
    public void usunrok(String rok) {
        sessionFacade.statystykaUsunrok(rok);
        
    }

    public List<Statystyka> findByPodatnik(Podatnik p) {
        return sessionFacade.getEntityManager().createNamedQuery("Statystyka.findByPodatnik").setParameter("podatnik", p).getResultList();
    }
    
    public List<Statystyka> findByRok(String p) {
        return sessionFacade.getEntityManager().createNamedQuery("Statystyka.findByRok").setParameter("rok", p).getResultList();
    }
}
