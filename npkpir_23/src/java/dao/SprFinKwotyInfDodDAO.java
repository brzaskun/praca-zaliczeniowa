/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.SprFinKwotyInfDod;
import error.E;
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
public class SprFinKwotyInfDodDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
     @Inject
    private SessionFacade sessionFacade;
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

    public SprFinKwotyInfDodDAO() {
        super(SprFinKwotyInfDod.class);
        super.em = this.em;
    }
   
    public SprFinKwotyInfDod findsprfinkwoty(Podatnik podatnikObiekt, String rokWpisuSt) {
        SprFinKwotyInfDod zwrot = null;
        try {
            zwrot = (SprFinKwotyInfDod)sessionFacade.getEntityManager().createNamedQuery("SprFinKwotyInfDod.findsprfinkwoty").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).getSingleResult();
        } catch (Exception e){
            E.e(e);
        }
        return zwrot;
    }
    
    
    
}
