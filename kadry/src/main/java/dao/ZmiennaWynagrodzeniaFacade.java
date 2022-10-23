/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Skladnikwynagrodzenia;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
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
public class ZmiennaWynagrodzeniaFacade extends DAO implements Serializable {
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

    public ZmiennaWynagrodzeniaFacade() {
        super(Zmiennawynagrodzenia.class);
        super.em = em;
    }

    public List<Zmiennawynagrodzenia> findByUmowa(Umowa umowa) {
        return getEntityManager().createNamedQuery("Zmiennawynagrodzenia.findByUmowa").setParameter("umowa", umowa).getResultList();
    }

    public List<Zmiennawynagrodzenia> findBySkladnik(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        return getEntityManager().createNamedQuery("Zmiennawynagrodzenia.findBySkladnik").setParameter("skladnikwynagrodzenia", skladnikwynagrodzenia).getResultList();
    }

    public boolean findByDataSkladnik(Zmiennawynagrodzenia wiersz) {
        boolean zwrot = false;
        List<Zmiennawynagrodzenia> lista = null;
        try {
            if (wiersz.getDatado()==null) {
                lista =  getEntityManager().createNamedQuery("Zmiennawynagrodzenia.findByDataSkladnikNull").setParameter("rodzajwynagrodzenia", wiersz.getSkladnikwynagrodzenia().getRodzajwynagrodzenia()).setParameter("dataod", wiersz.getDataod()).getResultList();
            } else {
                lista =  getEntityManager().createNamedQuery("Zmiennawynagrodzenia.findByDataSkladnik").setParameter("rodzajwynagrodzenia", wiersz.getSkladnikwynagrodzenia().getRodzajwynagrodzenia()).setParameter("dataod", wiersz.getDataod()).setParameter("datado", wiersz.getDatado()).getResultList();
            }
        } catch (Exception ex) {}
        if (lista!=null&&lista.size()>0) {
            zwrot = true;
        }
        return zwrot;
    }
}
