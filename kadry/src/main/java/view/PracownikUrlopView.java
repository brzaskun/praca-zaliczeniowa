/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import dao.KalendarzmiesiacFacade;
import dao.PracownikFacade;
import dao.UrlopprezentacjaFacade;
import data.Data;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Urlopprezentacja;
import entity.Urlopwykorzystanie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracownikUrlopView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UrlopprezentacjaFacade urlopprezentacjaFacade;
    @Inject
    private WpisView wpisView;
    private Urlopprezentacja urlopprezentacja;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    
    
    @PostConstruct
    public void init() {
        try {
            if (wpisView.getPracownik()!=null) {
                pobierz();
            }
        } catch (Exception e){}
    }

        
    public void edit() {
      if (wpisView.getPracownik()!=null) {
          try {
            wpisView.getPracownik().setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            Calendar calendar = Calendar.getInstance();
            wpisView.getPracownik().setDatalogowania(Data.aktualnaDataCzas());
            wpisView.getPracownik().setModyfikowal(wpisView.getUzer().getSecname());
            pracownikFacade.edit(wpisView.getPracownik());
            Msg.msg("Uaktualniono dane");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      }
    }
    
    public void pobierz() {
        if (wpisView.getPracownik()!=null) {
            urlopprezentacja = new Urlopprezentacja(wpisView.getUmowa(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            urlopprezentacja.setUrlopwykorzystanieList(naniesdnizkodem(kalendarze, urlopprezentacja, "100"));
            Msg.msg("Pobrano dane");
        }
    }
    
    private List<Urlopwykorzystanie> naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Urlopprezentacja urlopprezentacja, String kod) {
        List<Urlopwykorzystanie> lista = new ArrayList<>();
        for (Kalendarzmiesiac p : kalendarze) {
            for (Dzien r : p.getDzienList()) {
                if (r.getNieobecnosc()!=null) {
                    if (r.getNieobecnosc().getNieobecnosckodzus().getKod().equals("100")) {
                        Urlopwykorzystanie wykorzystanie = new Urlopwykorzystanie();
                        wykorzystanie.setMc(p.getMc());
                        wykorzystanie.setData(Data.zrobdate(r.getNrdnia(), p.getMc(), p.getRok()));
                        wykorzystanie.setDni(1);
                        wykorzystanie.setNrdniawroku(r.getNrdniawroku());
                        wykorzystanie.setGodziny(r.getUrlopPlatny());
                        wykorzystanie.setUrlopprezentacja(urlopprezentacja);
                        EtatPrac pobierzetat = p.getUmowa().pobierzetat(wykorzystanie.getData());
                        wykorzystanie.setEtat1(pobierzetat.getEtat1());
                        wykorzystanie.setEtat2(pobierzetat.getEtat2());
                        if (r.getUrlopPlatny()>0) {
                            lista.add(wykorzystanie);
                        }
                    }
                }
            }
        }
        return lista;
    }


      public Urlopprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Urlopprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    
    
    
}
