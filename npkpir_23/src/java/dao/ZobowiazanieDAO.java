/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zobowiazanie;
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
public class ZobowiazanieDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade zobowiazanieFacade;
   
    public ZobowiazanieDAO() {
        super(Zobowiazanie.class);
    }
       
     public Zobowiazanie find(String rok, String mc) throws Exception{
        return zobowiazanieFacade.findZobowiazanie(rok, mc);
     }
     
     public  List<Zobowiazanie> findAll(){
        try {
            return zobowiazanieFacade.findAll(Zobowiazanie.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
}
