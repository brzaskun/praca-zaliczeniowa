/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zmianatablicy;
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
                System.out.println("SessionFacade w pliku ZmianatblicyDAO jest null - nie utworzona " + param);
            } else {
                System.out.println("Utworzono SessionFacade w pliku ZmianatablicyDAO " + param);
            }
            zmianatablicy.setNazwatablicy(param);
            zmianatablicy.setZmiana(zmiana);
            sessionFacade.create(zmianatablicy);
            System.out.println("Dodano wpis o zmianie tablicy" + param);
        } catch (Exception e) {
            System.out.println("Nie dodano wpisu o zmianie tablicy" + param + " " + e.toString());
            throw new PersistenceException();
        }
    }
    
      public void edytuj(String param, boolean zmiana) {
        try {
            if(sessionFacade==null){
                System.out.println("SessionFacade w pliku ZmianatblicyDAO jest null - nie utworzona " + param);
            } else {
                System.out.println("Utworzono SessionFacade w pliku ZmianatablicyDAO " + param);
            }
            zmianatablicy.setNazwatablicy(param);
            zmianatablicy.setZmiana(zmiana);
            sessionFacade.edit(zmianatablicy);
            System.out.println("Dodano wpis o zmianie tablicy" + param);
        } catch (Exception e) {
            System.out.println("Nie dodano wpisu o zmianie tablicy" + param + " " + e.toString());
            throw new PersistenceException();
        }
    }
}
