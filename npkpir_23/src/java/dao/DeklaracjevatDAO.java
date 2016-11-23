/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjevat;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
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
        } catch (Exception e) { E.e(e); 
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
            if(p.getIdentyfikator().equals(identyfikator)){
                wynik = p;
                break;
            }
        }
        return wynik;
    }
    
         
    public List<Deklaracjevat> findDeklaracjeWyslane(String pod) {
        return deklaracjevatFacade.findDeklaracjewyslane(pod);
    }
    
    public List<Deklaracjevat> findDeklaracjeWyslane(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = new ArrayList<>();
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
    
    public List<Deklaracjevat> findDeklaracjeWyslane200(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = new ArrayList<>();
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

    public List<String> findDeklaracjeDowyslania(String rok, String mc, WpisView wpisView) {
         List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjewysylka(rok, mc);
         List<String> wynik = new ArrayList<>();
         String sporzadzil = wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw();
         for(Deklaracjevat p :temp){
            if(p.getIdentyfikator().isEmpty() && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
                wynik.add(p.getPodatnik());
            }
        }
        return wynik;
    }

    public List<String> findDeklaracjeBezupo(String rok, String mc, WpisView wpisView) {
         List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjewysylka(rok, mc);
         List<String> wynik = new ArrayList<>();
         String sporzadzil = wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw();
         for(Deklaracjevat p :temp){
            if(p.getStatus().startsWith("3") && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
                wynik.add(p.getPodatnik());
            }
        }
        return wynik;
    }
    
    public Deklaracjevat findDeklaracjaPodatnik(String identyfikator, String podatnik) {
         return deklaracjevatFacade.findDeklaracjaPodatnik(identyfikator, podatnik);
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(WpisView wpisView) {
        List<Deklaracjevat> temp = deklaracjevatFacade.findDeklaracjeByPodatnik(wpisView.getPodatnikWpisu());
        Deklaracjevat wynik = null;
        String sporzadzil = wpisView.getWprowadzil().getImie()+" "+wpisView.getWprowadzil().getNazw();
        for(Deklaracjevat p :temp){
            if(p.getStatus().startsWith("3") && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
                wynik = p;
            }
        }
        return wynik;
    }

 
}
