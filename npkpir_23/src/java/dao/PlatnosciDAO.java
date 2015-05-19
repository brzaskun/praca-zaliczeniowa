/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Platnosci;
import entity.PlatnosciPK;
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
public class PlatnosciDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade platnosciFacade;
  
    public PlatnosciDAO() {
        super(Platnosci.class);
    }
  
     public Platnosci findPK(PlatnosciPK key) throws Exception{
        return platnosciFacade.findPlatnosciPK(key);
     }
   
     public List<Platnosci> findPodRok(String rok, String podatnik) throws Exception{
        return platnosciFacade.findPlatnosciPodRok(rok, podatnik);
     }
     
     public  List<Platnosci> findAll(){
        try {
            return platnosciFacade.findAll(Platnosci.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
