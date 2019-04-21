/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjevat;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
public class DeklaracjevatDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade deklaracjevatFacade;
    //tablica wciagnieta z bazy danych

    public DeklaracjevatDAO() {
        super(Deklaracjevat.class);
    }

     public  List<Deklaracjevat> findAll(){
        try {
            return deklaracjevatFacade.findAll(Deklaracjevat.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    
    public Deklaracjevat findDeklaracje(String rok, String mc, String pod){
        return deklaracjevatFacade.findDeklaracjevat(rok, mc, pod);
    }
    
     public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod){
        return deklaracjevatFacade.findDeklaracjewszystkie(rok, mc, pod);
    }
     
    public Deklaracjevat findDeklaracjeDowyslania(String pod){
        try {
        return deklaracjevatFacade.findDeklaracjewysylka(pod);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public List<Deklaracjevat> findDeklaracjeDowyslaniaList(String pod){
        try {
            return deklaracjevatFacade.findDeklaracjewysylkaLista(pod);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(String identyfikator, WpisView wpisView) {
        List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjeByPodatnik(wpisView.getPodatnikWpisu());
        Deklaracjevat wynik = new Deklaracjevat();
        for(Deklaracjevat p :temp){
            if(p.getIdentyfikator() != null && p.getIdentyfikator().equals(identyfikator)){
                wynik = p;
                break;
            }
        }
        return wynik;
    }
    
         
    public List<Deklaracjevat> findDeklaracjeWyslane(String pod) {
        return deklaracjevatFacade.findDeklaracjewyslane(pod);
    }
    
    public List<Deklaracjevat> findDeklaracjeWyslaneMc(String podatnik, String rok, String mc) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
        try {
            znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslaneMc(podatnik,rok,mc);
        } catch (Exception e) { 
            E.e(e);
        }
        return znalezionedeklaracje;
    }
    
    
    public List<Deklaracjevat> findDeklaracjeWyslane(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
       try {
            znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane(pod,rok);
        } catch (Exception e) { 
            E.e(e); }
       //szuka zawsze w roku poprzednim. jak nie ma wywala blad
       if (znalezionedeklaracje.isEmpty()) {
        String rokuprzedni = String.valueOf(Integer.parseInt(rok)-1);
        znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane(pod,rokuprzedni);
       }
       return znalezionedeklaracje;
    }
    
    public List<Deklaracjevat> findDeklaracjeWyslane200RokMc(String rok, String mc) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
       try {
            znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane200RokMc(rok, mc);
        } catch (Exception e) { 
            E.e(e); 
        }
       return znalezionedeklaracje;
    }
    
    public List<Deklaracjevat> findDeklaracjeWyslane200(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
       try {
            znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane200(pod,rok);
        } catch (Exception e) { E.e(e); }
       //szuka zawsze w roku poprzednim. jak nie ma wywala blad
       if (znalezionedeklaracje.isEmpty()) {
        String rokuprzedni = String.valueOf(Integer.parseInt(rok)-1);
        znalezionedeklaracje = deklaracjevatFacade.findDeklaracjewyslane200(pod,rokuprzedni);
       }
       return znalezionedeklaracje;
    }

    public List<Deklaracjevat> findDeklaracjewysylka(WpisView wpisView) {
         return deklaracjevatFacade.findDeklaracjewysylka(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    public List<Deklaracjevat> findDeklaracjewysylka(String rok, String mc) {
         return deklaracjevatFacade.findDeklaracjewysylka(rok, mc);
    }
    

    
    public Deklaracjevat findDeklaracjaPodatnik(String identyfikator, String podatnik) {
         return deklaracjevatFacade.findDeklaracjaPodatnik(identyfikator, podatnik);
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(WpisView wpisView) {
        List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjeByPodatnik(wpisView.getPodatnikWpisu());
        Deklaracjevat wynik = null;
        String sporzadzil = wpisView.getUzer().getImie()+" "+wpisView.getUzer().getNazw();
        for(Deklaracjevat p :temp){
            if(p.getStatus().startsWith("3") && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
                wynik = p;
            }
        }
        return wynik;
    }

 
}
