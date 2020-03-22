/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
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
public class UzDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade uzFacade;
 
    public UzDAO() {
        super(Uz.class);
    }
   
    public Uz findUzByLogin(String login){
         return uzFacade.findUzNP(login);
     }
    
    public List<Uz> findByUprawnienia(String uprawnienia) {
        return uzFacade.findByUprawnienia(uprawnienia);
    }
   
    public  List<Uz> findAll(){
        try {
            return uzFacade.findAll(Uz.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public List<String> findUzByUprawnienia(String uprawnienia){
         return uzFacade.getEntityManager().createNamedQuery("Uz.findByUzUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList();
     }
    
}

