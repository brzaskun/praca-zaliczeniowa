/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evpozycja;
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
public class EvpozycjaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evpozycjaFacade;

    public EvpozycjaDAO() {
        super(Evpozycja.class);
    }
    
    public Evpozycja find(String nazwapola){
        return evpozycjaFacade.findEvpozycjaByName(nazwapola);
    }
    
    public  List<Evpozycja> findAll(){
        try {
            System.out.println("Pobieram EvpozycjaDAO");
            return evpozycjaFacade.findAll(Evpozycja.class);
        } catch (Exception e) {
            return null;
        }
   }
}
