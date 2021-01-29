/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SMTPSettings;
import entity.Uz;
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
public class SMTPSettingsFacade   implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName = "kadryPU")
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

    public void create(SMTPSettings entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public List<SMTPSettings> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(SMTPSettings.class));
        return getEntityManager().createQuery(cq).getResultList();
    }
    
     public void edit(SMTPSettings entity) {
        getEntityManager().merge(entity);
    }
    
     public void edit(List<SMTPSettings> entityList) {
        for (SMTPSettings p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    public SMTPSettings findSprawaByUzytkownik(Uz uzytkownik) {
        SMTPSettings zwrot = null;
        try {
             zwrot = (SMTPSettings) getEntityManager().createNamedQuery("SMTPSettings.findByUzytkownik").setParameter("uzytkownik", uzytkownik).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public SMTPSettings findSprawaByDef() {
        SMTPSettings zwrot = null;
        try {
            zwrot = (SMTPSettings) getEntityManager().createNamedQuery("SMTPSettings.findByDef").getSingleResult();
        } catch (Exception e) {
        
        }
        return zwrot;
    }


}
