/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.Collections;
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
    private SessionFacade sessionFacade;
 
    public UzDAO() {
        super(Uz.class);
    }
   
    public Uz findUzByLogin(String login){
         Uz zwrot = null;
        try {
            zwrot = (Uz) sessionFacade.getEntityManager().createNamedQuery("Uz.findByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
     }
    
    public List<Uz> findByUprawnienia(String uprawnienia) {
        return Collections.synchronizedList(sessionFacade.getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
    }
   
    public  List<Uz> findAll(){
        try {
            return sessionFacade.findAll(Uz.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public List<String> findUzByUprawnienia(String uprawnienia){
         return Collections.synchronizedList(sessionFacade.getEntityManager().createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
     }
    
}

