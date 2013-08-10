/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Faktura;
import entity.Klienci;
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
public class FakturaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturaFacade;

    public FakturaDAO() {
        super(Faktura.class);
    }
    
    public  List<Faktura> findAll(){
        try {
            System.out.println("Pobieram FakturaDAO");
            return fakturaFacade.findAll(Faktura.class);
        } catch (Exception e) {
            return null;
        }
   }
   
   public String dodaj(Faktura faktura){
       try {
        fakturaFacade.create(faktura);
           return "ok";
       } catch (Exception e){
           return e.getStackTrace().toString();
       }
   }
}
