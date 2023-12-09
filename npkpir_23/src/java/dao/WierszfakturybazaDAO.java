/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.EVatOpis;
import entity.Wierszfakturybaza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
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
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class WierszfakturybazaDAO extends DAO implements Serializable {

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

    public WierszfakturybazaDAO() {
        super(Wierszfakturybaza.class);
        super.em = this.em;
    }

    public List<Wierszfakturybaza> findbyRokMc(String rokWpisu, String mc) {
       List<Wierszfakturybaza> zwrot = new ArrayList<>();
       zwrot = getEntityManager().createNamedQuery("Wierszfakturybaza.findByRokMc").setParameter("rok", rokWpisu).setParameter("mc", mc).getResultList();
       if (zwrot == null) {
           zwrot = new ArrayList<>();
       }
       return zwrot;
    }
    
     public List<Wierszfakturybaza> findbyNipRokMc(String nip, String rokWpisu, String mc) {
       List<Wierszfakturybaza> zwrot = new ArrayList<>();
       zwrot = getEntityManager().createNamedQuery("Wierszfakturybaza.findByNipRokMc").setParameter("nip", nip).setParameter("rok", rokWpisu).setParameter("mc", mc).getResultList();
       if (zwrot == null) {
           zwrot = new ArrayList<>();
       }
       return zwrot;
    }

    public Wierszfakturybaza findById(Integer s) {
        Wierszfakturybaza zwrot = (Wierszfakturybaza) getEntityManager().createNamedQuery("Wierszfakturybaza.findById").setParameter("id", s).getSingleResult();
        return zwrot;
    }
    
}
