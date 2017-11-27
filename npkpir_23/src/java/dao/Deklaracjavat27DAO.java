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
            return sessionFacade.findDekl27byPodatnikRok(wpisView);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public Deklaracjavat27 findbyPodatnikRokMc(WpisView wpisView) {
        Deklaracjavat27 zwrot = null;
        try {
            List<Deklaracjavat27> lista = sessionFacade.findDekl27byPodatnikRokMc(wpisView);
            int max = -1;
            for (Deklaracjavat27 p : lista) {
                if (p.getNrkolejny() > max) {
                    max = p.getNrkolejny();
                    zwrot = p;
                }
            }
        } catch (Exception e) {
        }
        return zwrot;
    }

    public void usundeklaracje27(WpisView wpisView) {
        sessionFacade.usundeklaracje27(wpisView);
    }

    public List<Deklaracjavat27> findDeklaracjewysylka(WpisView wpisView) {
         return sessionFacade.findDeklaracje27wysylka(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    
    
}
