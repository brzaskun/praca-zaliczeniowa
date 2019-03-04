
package dao;

import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Named
public class PodatnikDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade podatnikFacade;
   
   
    public PodatnikDAO() {
        super(Podatnik.class);
    }

    public  List<Podatnik> findAll(){
        try {
            return podatnikFacade.findAktywnyPodatnik(true);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findAllRO(){
        try {
            return podatnikFacade.findAktywnyPodatnikRO(true);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
     public  List<Podatnik> findAllManager(){
        try {
            return podatnikFacade.findAll(Podatnik.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikFK(){
        try {
            return podatnikFacade.findPodatnikFK();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikNieFK(){
        try {
            return podatnikFacade.findPodatnikNieFK();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
     
     public  List<Podatnik> findPodatnikFKPkpir(){
        try {
            return podatnikFacade.findPodatnikFKPkpir();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikZUS(){
        try {
            return podatnikFacade.findPodatnikZUS();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
     public Podatnik find(String np){
         return podatnikFacade.findPodatnikNP(np);
     }
     
     public Podatnik findPodatnikByNIP(String np){
        try {
            return podatnikFacade.findPodatnikNPN(np);
        } catch (Exception e) { E.e(e); 
            return null;
        }
     }
     
     
}
