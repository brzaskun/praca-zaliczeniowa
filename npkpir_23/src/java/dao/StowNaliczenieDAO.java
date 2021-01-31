/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.DAO;
import entity.Podatnik;
import entityfk.StowNaliczenie;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class StowNaliczenieDAO  extends DAO implements Serializable{
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

    public StowNaliczenieDAO() {
        super(StowNaliczenie.class);
        super.em = this.em;
    }   
       
 
    public List<StowNaliczenie> findAll() {
        return sessionFacade.findAll(StowNaliczenie.class);
    }

    public void usunnaliczeniemc(WpisView wpisView, String kategoria) {
         sessionFacade.usunnaliczeniemc(wpisView, kategoria);
    }

    public List<StowNaliczenie> findByMcKategoria(Podatnik podatnik, String rokWpisuSt, String mc, String wybranakategoria) {
        return sessionFacade.getEntityManager().createNamedQuery("StowNaliczenie.findByMcKategoria").setParameter("podatnikObj",podatnik).setParameter("rok",rokWpisuSt).setParameter("mc",mc).setParameter("kategoria",wybranakategoria).getResultList();
    }
    
}
