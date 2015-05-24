/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Stateless
public class UzDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade uzFacade;
 
    public UzDAO() {
        super(Uz.class);
    }
   
    public Uz findUzByLogin(String login){
         return uzFacade.findUzNP(login);
     }
    
    public List<Uz> findMultiuser() {
        return uzFacade.findMultiuser();
    }
   
    public  List<Uz> findAll(){
        try {
            return uzFacade.findAll(Uz.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   } 
}

