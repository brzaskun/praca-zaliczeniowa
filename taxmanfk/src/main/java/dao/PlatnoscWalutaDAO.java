/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.Dok;
import entity.PlatnoscWaluta;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class PlatnoscWalutaDAO extends DAO implements Serializable {
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

    public PlatnoscWalutaDAO() {
        super(PlatnoscWaluta.class);
        super.em = this.em;
    }

   
    
    public  List<PlatnoscWaluta> findAll(){
        try {
            return findAll();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<PlatnoscWaluta> findByDok(Dok selected) {
        return sessionFacade.findPlatnoscWalutaByDok(selected);
    }

    public List<PlatnoscWaluta> findByPodRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.findPlatnoscWalutaByPodRokMc(podatnikObiekt, rokWpisuSt, miesiacWpisu);
    }
    
    public List<PlatnoscWaluta> findByPodRokKw(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return sessionFacade.getEntityManager().createNamedQuery("PlatnoscWaluta.findByPodRokKw").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", mce.get(0)).setParameter("mc1", mce.get(1)).setParameter("mc2", mce.get(2)).getResultList();
    }
    
    public List<PlatnoscWaluta> findByPodRok(Podatnik podatnikObiekt, String rokWpisuSt) {
        return sessionFacade.getEntityManager().createNamedQuery("PlatnoscWaluta.findByPodRok").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).getResultList();
    }
}
