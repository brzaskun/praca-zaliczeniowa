/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class PodatnikOpodatkowanieDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;
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

    public PodatnikOpodatkowanieDAO() {
        super(PodatnikOpodatkowanieD.class);
        super.em = this.em;
    }

   
    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnik(WpisView wpisView) {
        return sessionFacade.findOpodatkowaniePodatnik(wpisView);
    }
    
    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnikRokWiele(Podatnik p, String rok) {
        return sessionFacade.findOpodatkowaniePodatnikRokWiele(p, rok);
    }
    
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(Podatnik p, String rok) {
        PodatnikOpodatkowanieD zwrot = null;
        try {
            zwrot = (PodatnikOpodatkowanieD) sessionFacade.getEntityManager().createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getSingleResult();
        } catch(Exception e) {
            
        }
        return zwrot;
    }
    

    public List<PodatnikOpodatkowanieD> findAll() {
        return sessionFacade.findAll(PodatnikOpodatkowanieD.class);
    }
    
}
