
package dao;

import entity.Podatnik;
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
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
     public  List<Podatnik> findAllManager(){
        try {
            return podatnikFacade.findAll(Podatnik.class);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikFK(){
        try {
            return podatnikFacade.findPodatnikFK();
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
     public  List<Podatnik> findPodatnikNieFK(){
        try {
            return podatnikFacade.findPodatnikNieFK();
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
    public  List<Podatnik> findPodatnikZUS(){
        try {
            return podatnikFacade.findPodatnikZUS();
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
    }
    
     public Podatnik find(String np){
         return podatnikFacade.findPodatnikNP(np);
     }
     
     public Podatnik findPodatnikByNIP(String np){
        try {
            return podatnikFacade.findPodatnikNPN(np);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
     }
     
     
}
