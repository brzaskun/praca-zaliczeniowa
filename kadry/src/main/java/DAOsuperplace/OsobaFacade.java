/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOsuperplace;


import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import kadryiplace.Osoba;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class OsobaFacade extends DAO{

    @PersistenceContext(unitName = "microsoft")
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

    public OsobaFacade() {
        super(Osoba.class);
        super.em = em;
    }

    public Osoba findByPesel(String pesel) {
        return (Osoba) getEntityManager().createNamedQuery("Osoba.findByOsoPesel").setParameter("osoPesel", pesel).getSingleResult();
    }
    
    public Osoba findBySerial(String serial) {
        return (Osoba) getEntityManager().createNamedQuery("Osoba.findByOsoSerial").setParameter("osoSerial", Integer.valueOf(serial)).getSingleResult();
    }
    

   
}
