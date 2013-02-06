
package dao;

import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
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
        downloaded = new ArrayList<>();
    }

     public Podatnik find(String np){
         return podatnikFacade.findPodatnikNP(np);
     }
     
     public Podatnik findN(String np){
         return podatnikFacade.findPodatnikNPN(np);
     }
     
     
}
