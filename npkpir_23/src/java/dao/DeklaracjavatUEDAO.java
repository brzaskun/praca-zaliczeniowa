/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjavatUE;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class DeklaracjavatUEDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;
    //tablica wciagnieta z bazy danych

    public DeklaracjavatUEDAO() {
        super(DeklaracjavatUE.class);
    }

     public  List<DeklaracjavatUE> findAll(){
        try {
            return sessionFacade.findAll(DeklaracjavatUE.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

    public List<DeklaracjavatUE> findbyPodatnikRok(WpisView wpisView) {
        try {
            return sessionFacade.findDeklUEbyPodatnikRok(wpisView);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public DeklaracjavatUE findbyPodatnikRokMc(WpisView wpisView) {
        try {
            return sessionFacade.findDeklUEbyPodatnikRokMc(wpisView);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public void usundeklaracjeUE(WpisView wpisView) {
        sessionFacade.usundeklaracjeUE(wpisView);
    }
    
    
    
}
