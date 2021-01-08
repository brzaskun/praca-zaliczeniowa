
package dao;

import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;


/**
 *
 * @author Osito
 */
@Named
public class PodatnikDAO extends DAO implements Serializable{
    
   
    public PodatnikDAO() {
        super(Podatnik.class);
    }

    public  List<Podatnik> findAll(){
        try {
            return sessionFacade.findAktywnyPodatnik();
        } catch (Exception e) { E.e(e); 
            return null;
        }
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
