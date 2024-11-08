/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Logofaktura;
import entity.Podatnik;
import java.io.Serializable;
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
public class LogofakturaDAO  extends DAO implements Serializable {
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

    public LogofakturaDAO() {
        super(Logofaktura.class);
        super.em = this.em;
    }

    public void usun(Podatnik podatnikObiekt, int nrkolejny) {
         getEntityManager().createNamedQuery("Logofaktura.usunlogo").setParameter("podatnik", podatnikObiekt).setParameter("nrkolejny", nrkolejny).executeUpdate();
    }

    

    public Logofaktura findByPodatnik(Podatnik podatnikObiekt, int nrkolejny) {
        return (Logofaktura)  getEntityManager().createNamedQuery("Logofaktura.findByPodatnik").setParameter("podatnik", podatnikObiekt).setParameter("nrkolejny", nrkolejny).getSingleResult();
    }
       
    
}
