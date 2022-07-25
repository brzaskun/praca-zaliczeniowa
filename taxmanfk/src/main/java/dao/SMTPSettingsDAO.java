/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SMTPSettings;
import entity.Uz;
import java.io.Serializable;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class SMTPSettingsDAO extends DAO implements Serializable {
    @Inject
    private SessionFacade wpisFacade;
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

    public SMTPSettingsDAO() {
        super(SMTPSettings.class);
        super.em = this.em;
    }


 
 
    public SMTPSettings findSprawaByUzytkownik(Uz uzytkownik) {
        SMTPSettings zwrot = null;
        try {
            zwrot = wpisFacade.findSMTPSettingsByUzytkownik(uzytkownik);
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public SMTPSettings findSprawaByDef() {
        try {
            return wpisFacade.findSMTPSettingsByDef();
        } catch (Exception e) {
            return null;
        }
    }


}
