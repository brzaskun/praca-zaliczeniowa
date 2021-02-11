/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
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

public class KliencifkDAO extends DAO implements Serializable{
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

    public KliencifkDAO() {
        super(Kliencifk.class);
        super.em = this.em;
    }
   
    
    public Kliencifk znajdzkontofk(String nip, String podatniknip) {
        try {
            return (Kliencifk)  getEntityManager().createNamedQuery("Kliencifk.findByNipPodatniknip").setParameter("nip", nip).setParameter("podatniknip", podatniknip).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Kliencifk> znajdzkontofkKlient(String podatniknip) {
        return getEntityManager().createNamedQuery("Kliencifk.findByPodatniknip").setParameter("podatniknip", podatniknip).getResultList();
    }
 
    public Kliencifk znajdzkontofkByKonto(Konto konto) {
        return (Kliencifk)  getEntityManager().createNamedQuery("Kliencifk.findByNrkonta").setParameter("nrkonta", konto.getNrkonta()).setParameter("podatniknip", konto.getPodatnik().getNip()).getSingleResult();
    }
    
    
}
