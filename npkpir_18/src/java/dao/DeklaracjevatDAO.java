/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjevat;
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
public class DeklaracjevatDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade deklaracjevatFacade;
    //tablica wciagnieta z bazy danych

    public DeklaracjevatDAO() {
        super(Deklaracjevat.class);
    }

    public Deklaracjevat findDeklaracje(String rok, String mc, String pod){
        return deklaracjevatFacade.findDeklaracjevat(rok, mc, pod);
    }
    
     public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod){
        return deklaracjevatFacade.findDeklaracjewszystkie(rok, mc, pod);
    }
     
    public Deklaracjevat findDeklaracjeDowyslania(String pod){
        List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjewysylka(pod);
        Deklaracjevat wynik = new Deklaracjevat();
        for(Deklaracjevat p :temp){
            if(p.getIdentyfikator().equals("")){
                wynik = p;
                break;
            }
        }
        return wynik;
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(String identyfikator) {
        List<Deklaracjevat> temp = deklaracjevatFacade.findAll(Deklaracjevat.class);
        Deklaracjevat wynik = new Deklaracjevat();
        for(Deklaracjevat p :temp){
            if(p.getIdentyfikator().equals(identyfikator)){
                wynik = p;
                break;
            }
        }
        return wynik;
    }
}
