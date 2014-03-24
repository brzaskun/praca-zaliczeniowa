
package dao;

import entity.Podatnik;
import entity.Sesja;
import java.io.Serializable;
import java.util.ArrayList;
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
            System.out.println("Pobieram PodatnikDAO");
            return podatnikFacade.findAll(Podatnik.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public  List<Podatnik> findPodatnikFK(){
        try {
            System.out.println("Pobieram podatnikow FK PodatnikDAO");
            return podatnikFacade.findPodatnikFK();
        } catch (Exception e) {
            return null;
        }
    }
    
     public Podatnik find(String np){
         return podatnikFacade.findPodatnikNP(np);
     }
     
     public Podatnik findN(String np){
         return podatnikFacade.findPodatnikNPN(np);
     }
     
     
}
