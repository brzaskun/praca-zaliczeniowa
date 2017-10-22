/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjavat27;
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
public class Deklaracjavat27DAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;
    //tablica wciagnieta z bazy danych

    public Deklaracjavat27DAO() {
        super(Deklaracjavat27.class);
    }

     public  List<Deklaracjavat27> findAll(){
        try {
            return sessionFacade.findAll(Deklaracjavat27.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

    public List<Deklaracjavat27> findbyPodatnikRok(WpisView wpisView) {
        try {
            return sessionFacade.findDeklUEbyPodatnikRok(wpisView);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public Deklaracjavat27 findbyPodatnikRokMc(WpisView wpisView) {
        try {
            return null;//sessionFacade.findDeklUEbyPodatnikRokMc(wpisView);
        } catch (Exception e) { 
            return null;
        }
    }

    public void usundeklaracjeUE(WpisView wpisView) {
        sessionFacade.usundeklaracjeUE(wpisView);
    }
    
    
    
}
