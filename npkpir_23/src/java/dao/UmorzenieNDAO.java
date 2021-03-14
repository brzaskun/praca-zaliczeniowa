/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.SrodekTrw;
import entity.UmorzenieN;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
public class UmorzenieNDAO extends DAO implements Serializable {

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

    public UmorzenieNDAO() {
        super(UmorzenieN.class);
        super.em = this.em;
    }

    public List<UmorzenieN> findByPodatnikRokMc(String podatnik, Integer rokWpisu, String miesiacWpisu) {
        List<UmorzenieN> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("UmorzenieN.findByPodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rokWpisu).setParameter("mc", Integer.parseInt(miesiacWpisu)).getResultList();
        } catch (Exception e){}
        return zwrot;
    }


    public List<UmorzenieN> findBySrodek(SrodekTrw str) {
        List<UmorzenieN> list = getEntityManager().createNamedQuery("UmorzenieN.findStr").setParameter("srodekTrw", str).getResultList();
        if (list == null) {
            list = Collections.synchronizedList(new ArrayList<>());
        }
        return list;
    }

    public List<UmorzenieN> findByDokfk(Dokfk p) {
        List<UmorzenieN> list = getEntityManager().createNamedQuery("UmorzenieN.findByDokfk").setParameter("dokfk", p).getResultList();
        if (list == null) {
            list = Collections.synchronizedList(new ArrayList<>());
        }
        return list;
    }

    public List<UmorzenieN> findByDok(Dok dokdoUsuniecia) {
        List<UmorzenieN> list = getEntityManager().createNamedQuery("UmorzenieN.findByDok").setParameter("dok", p).getResultList();
        if (list == null) {
            list = Collections.synchronizedList(new ArrayList<>());
        }
        return list;
    }
    
   }
