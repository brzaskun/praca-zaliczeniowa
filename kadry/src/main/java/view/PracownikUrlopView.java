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
import embeddable.Mce;
import entity.Kalendarzmiesiac;
import entity.Urlopprezentacja;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
import z.Z;

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
    private Urlopprezentacja urlopprezentacja1;
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
            urlopprezentacja = new Urlopprezentacja(wpisView.getPracownik(), wpisView.getRokWpisu());  
            urlopprezentacja1 = new Urlopprezentacja(wpisView.getPracownik(), wpisView.getRokWpisu());  
            List<Kalendarzmiesiac> kalendarze = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            naniesdnizkodem(kalendarze, urlopprezentacja, "100");
            naniesdnizkodem(kalendarze, urlopprezentacja1, "331");
            Msg.msg("Pobrano dane");
        }
    }
    
    private void naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Urlopprezentacja urlopprezentacja, String kod) {
        double suma = 0.0;
        for (Kalendarzmiesiac p : kalendarze) {
            double dniurlopu = Z.z(p.roboczenieob(kod)[0]/8.0);
            suma = suma + dniurlopu;
            try {
                Class[] paramString = new Class[1];	
                paramString[0] = Double.class;
                Method met = Urlopprezentacja.class.getDeclaredMethod("setW"+Mce.getMiesiacToNumber().get(p.getMc()), paramString);
                met.invoke(urlopprezentacja, dniurlopu);
            } catch (Exception e){
                System.out.println("");
            }
        }
        urlopprezentacja.setW13(Z.z(suma));
    }


      public Urlopprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Urlopprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    public Urlopprezentacja getUrlopprezentacja1() {
        return urlopprezentacja1;
    }

    public void setUrlopprezentacja1(Urlopprezentacja urlopprezentacja1) {
        this.urlopprezentacja1 = urlopprezentacja1;
    }

    
    
    
}
