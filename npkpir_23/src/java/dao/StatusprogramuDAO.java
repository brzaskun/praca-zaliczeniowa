/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Statusprogramu;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
@PersistenceContext(name="ProjectEM", unitName = "npkpir_22PU")
public class StatusprogramuDAO  extends DAO implements Serializable {
    
    private EntityManager em;
    
    @Resource 
    SessionContext ctx;
 
    @PostConstruct
    private void init() {
         try {
            em = (EntityManager)ctx.lookup("ProjectEM");
         } catch (Exception e) {
             System.out.println("");
         }
     }
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }


    public StatusprogramuDAO() {
        super(Statusprogramu.class);
        super.em = this.em;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
   
        
}
