/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoplatnik;

import entityplatnik.Zusrca;
import error.E;
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
public class ZusrcaDAO extends DAO1 implements Serializable {


    @PersistenceContext(unitName = "microsoft1")
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

    public ZusrcaDAO() {
        super(Zusrca.class);
        super.em = this.em;
    }
    
    public List<Zusrca> findByNip(String nip) {
        List<Zusrca> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createQuery("SELECT z FROM Zusrca z WHERE z.ii1Nip = :Nip").setParameter("Nip", nip).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    public List<Zusrca> findByOkres(String okres) {
        List<Zusrca> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createQuery("SELECT z FROM Zusrca z WHERE z.i12okrrozl = :okres").setParameter("okres", okres).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    
}
