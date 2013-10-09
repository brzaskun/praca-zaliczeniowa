/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
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
public class UzDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade uzFacade;
 
    public UzDAO() {
        super(Uz.class);
    }
   
    public Uz find(String np){
         return uzFacade.findUzNP(np);
     }
    
   
       public  List<Uz> findAll(){
        try {
            System.out.println("Pobieram UzDAO");
            return uzFacade.findAll(Uz.class);
        } catch (Exception e) {
            return null;
        }
   } 
}

