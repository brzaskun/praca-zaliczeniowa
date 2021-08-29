/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SMTPSettings;
import entity.Uz;
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
public class SMTPSettingsFacade extends DAO   implements Serializable {
    private static final long serialVersionUID = 1L;
    @PersistenceContext(unitName = "kadryPU")
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

    public SMTPSettingsFacade() {
        super(SMTPSettings.class);
        super.em = em;
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
