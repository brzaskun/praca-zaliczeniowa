/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zmianatablicy;
import error.E;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class ZmianatablicyDAO implements Serializable{
    
    @Inject private SessionFacade sessionFacade;
    @Inject private Zmianatablicy zmianatablicy;

        
      public void dodaj(String param, boolean zmiana) {
        try {
            if(sessionFacade==null){
            } else {
            }
            zmianatablicy.setNazwatablicy(param);
            zmianatablicy.setZmiana(zmiana);
            sessionFacade.create(zmianatablicy);
        } catch (Exception e) { E.e(e); 
            throw new PersistenceException();
        }
    }
    
      public void edytuj(String param, boolean zmiana) {
        try {
            if(sessionFacade==null){
            } else {
            }
            zmianatablicy.setNazwatablicy(param);
            zmianatablicy.setZmiana(zmiana);
            sessionFacade.edit(zmianatablicy);
        } catch (Exception e) { E.e(e); 
            throw new PersistenceException();
        }
    }
}
