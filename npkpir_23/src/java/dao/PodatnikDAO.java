
package dao;

import entity.Podatnik;
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
public class PodatnikDAO extends DAO implements Serializable{
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PodatnikDAO() {
        super(Podatnik.class);
        super.em = this.em;
    }

   
    
    public  List<Podatnik> findAllPrzyporzadkowany(){
        try {
            return getEntityManager().createNamedQuery("Podatnik.findByPodmiotaktywnyPrzyporzadkowany").setParameter("podmiotaktywny", true).getResultList();
        } catch (Exception e) {
            E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findByKsiegowa(Uz ksiegowa){
        try {
            return getEntityManager().createNamedQuery("Podatnik.findByKsiegowa").setParameter("ksiegowa", ksiegowa).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public  List<Podatnik> findAktywny(){
        List<Podatnik>  zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Podatnik.findByPodmiotaktywny").getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    
     public  List<Podatnik> findAllManager(){
        List<Podatnik>  zwrot = null;
        try {
            zwrot = findAll();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }

    public  List<Podatnik> findPodatnikFK(){
        List<Podatnik>  zwrot = null;
        try {
            zwrot =  getEntityManager().createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", 1).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }

    public  List<Podatnik> findPodatnikNieFK(){
        List<Podatnik>  zwrot = null;
        try {
            zwrot =  getEntityManager().createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", 0).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
     
    
     public  List<Podatnik> findPodatnikFKPkpir(){
        List<Podatnik>  zwrot = null;
        try {
            zwrot =  getEntityManager().createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", 3).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
  
    
    public  List<Podatnik> findPodatnikZUS(){
        List<Podatnik>  zwrot = null;
        try {
            zwrot =  getEntityManager().createNamedQuery("Podatnik.findByZUS").getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    public Podatnik findByNazwaPelna(String np) {
        Podatnik zwrot = null;
        try {
            zwrot = (Podatnik)  getEntityManager().createNamedQuery("Podatnik.findByNazwapelna").setParameter("nazwapelna", np).getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
 
     
     public Podatnik findPodatnikByNIP(String np){
        Podatnik zwrot = null;
        try {
            zwrot =(Podatnik)  getEntityManager().createNamedQuery("Podatnik.findByNip").setParameter("nip", np).getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
     }

    public List<Podatnik> findNowi() {
        List<Podatnik>  zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Podatnik.findByPodmiotnowy").getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
     
     
}
