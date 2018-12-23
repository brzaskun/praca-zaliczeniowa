/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.UkladBR;
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

public class UkladBRDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public UkladBRDAO() {
        super(UkladBR.class);
    }

    public UkladBRDAO(Class entityClass) {
        super(entityClass);
    }
    public UkladBR findukladBR(UkladBR ukladBR) {
        return sessionFacade.findUkladBRUklad(ukladBR);
    }
    public  List<UkladBR> findAll(){
        try {
            return sessionFacade.findAll(UkladBR.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UkladBR> findPodatnik(Podatnik nazwapelna) {
        try {
            return sessionFacade.findUkladBRPodatnik(nazwapelna);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UkladBR> findPodatnikRok(WpisView wpisView) {
        try {
            return sessionFacade.findUkladBRPodatnikRok(wpisView);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    
    public List<UkladBR> findPodatnikRok(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findUkladBRPodatnikRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UkladBR> findRok(Podatnik podatnik, String rok) {
        try {
            return sessionFacade.findUkladBRRok(podatnik, rok);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    

//    public List<UkladBR> findukladBRWzorcowyRok(String rokWpisu) {
//        try {
//            return sessionFacade.findUkladBRWzorcowyRok(rokWpisu);
//        } catch (Exception e) { E.e(e); 
//            return null;
//        }
//    }

    public List<UkladBR> findukladBRPodatnikRok(Podatnik podatnikWpisu, String rokWpisuSt) {
         try {
            return sessionFacade.findukladBRPodatnikRok(podatnikWpisu, rokWpisuSt);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public UkladBR findukladBRPodatnikRokPodstawowy(Podatnik podatnikWpisu, String rokWpisuSt) {
         try {
            return sessionFacade.findukladBRPodatnikRokPodstawowy(podatnikWpisu, rokWpisuSt);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public UkladBR findukladBRPodatnikRokAktywny(Podatnik podatnikWpisu, String rokWpisuSt) {
        UkladBR uklad = null;
         try {
            uklad =  sessionFacade.findukladBRPodatnikRokAktywny(podatnikWpisu, rokWpisuSt);
            if (uklad == null) {
                uklad = sessionFacade.findukladBRPodatnikRokPodstawowy(podatnikWpisu, rokWpisuSt);
                uklad.setAktualny(true);
                sessionFacade.edit(uklad);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
         return uklad;
    }

    public void ustawnieaktywne(Podatnik podatnik) {
         try {
            sessionFacade.ukladBRustawnieaktywne(podatnik);
        } catch (Exception e) {
            E.e(e); 
        }
    }
    
}
