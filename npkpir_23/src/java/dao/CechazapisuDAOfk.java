/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.Cechazapisu;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class CechazapisuDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;
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
   
    
    
    public List<Cechazapisu> findAll() {
        return sessionFacade.findAll(Cechazapisu.class);
    }
    
    public List<Cechazapisu> findPodatnikOnly(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnikOnly(podatnikObiekt);
    }
    
    public List<Cechazapisu> findPodatnikOnlyAktywne(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnikOnlyAktywne(podatnikObiekt);
    }
    
    public List<Cechazapisu> findPodatnikOnlyStatystyczne(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnikOnlyStatystyczne(podatnikObiekt);
    }

    public List<Cechazapisu> findPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findCechaZapisuByPodatnik(podatnikObiekt);
    }

    public Cechazapisu findPodatniknkup() {
        return sessionFacade.findCechaZapisuByPodatnikNKUP();
    }

    
}
