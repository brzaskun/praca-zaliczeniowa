/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Sesja;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class SesjaDAO extends DAO implements Serializable {
   @Inject private SessionFacade sesjaFacade;
      
    public SesjaDAO() {
        super(Sesja.class);
    }
   
   public  List<Sesja> findAll(){
        try {
            System.out.println("Pobieram SesjaDAO");
            return sesjaFacade.findAll(Sesja.class);
        } catch (Exception e) {
            return null;
        }
   }
    
   public Sesja find(String nrsesji){
       try{
       return sesjaFacade.findSesja(nrsesji);
       } catch (Exception e){
           return null;
       }
       
   }
}
