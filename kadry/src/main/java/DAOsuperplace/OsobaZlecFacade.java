/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOsuperplace;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import kadryiplace.OsobaZlec;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class OsobaZlecFacade extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "microsoft")
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

    public OsobaZlecFacade() {
        super(OsobaZlec.class);
        super.em = em;
    }
    
    public List<OsobaZlec> findByOzlOsoSerial(String osoSerial) {
        List<OsobaZlec> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("OsobaZlec.findByOzlOsoSerial").setParameter("osoSerial", Integer.valueOf(osoSerial)).getResultList();
        } catch (Exception e){}
        return zwrot;
    }
   
}
