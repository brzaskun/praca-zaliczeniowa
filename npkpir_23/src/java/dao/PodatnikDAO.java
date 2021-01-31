
package dao;

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
public class PodatnikDAO extends DAO implements Serializable{
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

    public PodatnikDAO() {
        super(Podatnik.class);
        super.em = this.em;
    }

   
    
    public  List<Podatnik> findAllPrzyporzadkowany(){
        try {
            return sessionFacade.getEntityManager().createNamedQuery("Podatnik.findByPodmiotaktywnyPrzyporzadkowany").setParameter("podmiotaktywny", true).getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findAllRO(){
        try {
            return sessionFacade.findAktywnyPodatnikRO();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
     public  List<Podatnik> findAllManager(){
        try {
            return sessionFacade.findAll(Podatnik.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikFK(){
        try {
            return sessionFacade.findPodatnikFK();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikNieFK(){
        try {
            return sessionFacade.findPodatnikNieFK();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
     
     public  List<Podatnik> findPodatnikFKPkpir(){
        try {
            return sessionFacade.findPodatnikFKPkpir();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikZUS(){
        try {
            return sessionFacade.findPodatnikZUS();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
     public Podatnik find(String np){
         return sessionFacade.findPodatnikNP(np);
     }
     
     public Podatnik findPodatnikByNIP(String np){
        try {
            return sessionFacade.findPodatnikNPN(np);
        } catch (Exception e) { E.e(e); 
            return null;
        }
     }
     
     
}
