/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podstawki;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Named
public class PodStawkiDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade podstawkiFacade;
   
    public PodStawkiDAO() {
        super(Podstawki.class);
    }

    public Podstawki find(Integer rok){
        return podstawkiFacade.findPodstawkiyear(rok);
     }
   
}
