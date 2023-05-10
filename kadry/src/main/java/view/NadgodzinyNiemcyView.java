/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.DataBean;
import comparator.Kalendarzmiesiaccomparator;
import dao.KalendarzmiesiacFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.Kalendarzmiesiac;
import entity.Rodzajwynagrodzenia;
import entity.Zmiennawynagrodzenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class NadgodzinyNiemcyView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    private String dataod;
    private String datado;
    private List<Kalendarzmiesiac> kalendarze;
    private Kalendarzmiesiac selectedlista;
    private List<Zmiennawynagrodzenia> zmienne;
    
    
    public void init() {
        List<Kalendarzmiesiac> kalendarzewstep = kalendarzmiesiacFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        Rodzajwynagrodzenia rodzajwynagrodzenia = rodzajwynagrodzeniaFacade.findGodzinoweOddelegowaniePraca();
        zmienne = zmiennaWynagrodzeniaFacade.findByRodzajFirma(rodzajwynagrodzenia, wpisView.getFirma());
        String pierwszyDzien = Data.pierwszyDzien(wpisView);
        String ostatniDzien = Data.ostatniDzien(wpisView);
        kalendarze = new ArrayList<>();
        for (Zmiennawynagrodzenia zm : zmienne) {
            if (DataBean.czysiemiesci(pierwszyDzien, ostatniDzien, zm.getDataod(), zm.getDatado())) {
                Kalendarzmiesiac kalendarzpracownikamiesiac = pobierzkalendarz(kalendarzewstep, zm.getSkladnikwynagrodzenia().getAngaz(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (kalendarzpracownikamiesiac!=null) {
                    kalendarzpracownikamiesiac.setStawkazagodzine(Z.z(zm.getKwota()));
                    kalendarzpracownikamiesiac.setDodatekzanadgodziny(Z.z(zm.getKwota()*1.25));
                    kalendarze.add(kalendarzpracownikamiesiac);
                }
            }
        }
        if (kalendarze!=null&&kalendarze.size()>0) {
            kalendarzmiesiacFacade.editList(kalendarze);
            Collections.sort(kalendarze, new Kalendarzmiesiaccomparator());
        }
        dataod = Data.dzienpierwszy(wpisView);
        datado = Data.ostatniDzien(wpisView);
    }

   

    private Kalendarzmiesiac pobierzkalendarz(List<Kalendarzmiesiac> kalendarze, Angaz angaz, String rokWpisu, String miesiacWpisu) {
        Kalendarzmiesiac zwrot = null;
        if (kalendarze!=null) {
            for (Kalendarzmiesiac kal : kalendarze) {
                if (kal.getAngaz().equals(angaz)&&kal.getRok().equals(rokWpisu)&&kal.getMc().equals(miesiacWpisu)) {
                    zwrot = kal;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public void przeliczgodziny(Kalendarzmiesiac kalendarz) {
        if (kalendarz!=null) {
            kalendarz.setGodzinydelegowaniewymiar(kalendarz.getDnidelegowaniewymiar()*8);
            double nadgodziny = Z.z(kalendarz.getGodzinydelegowanieprzepracowane()-kalendarz.getGodzinydelegowaniewymiar())>0.0?Z.z(kalendarz.getGodzinydelegowanieprzepracowane()-kalendarz.getGodzinydelegowaniewymiar()):0.0;
            kalendarz.setDelegowanienadgodziny(nadgodziny);
            kalendarz.setDodatekzanadgodzinymc(Z.z(kalendarz.getDodatekzanadgodziny()*nadgodziny));
            kalendarzmiesiacFacade.edit(kalendarz);
        }
    }
    
    public void przelicznadgodziny(Kalendarzmiesiac kalendarz) {
        if (kalendarz!=null) {
            double nadgodziny = Z.z(kalendarz.getGodzinydelegowanieprzepracowane()-kalendarz.getGodzinydelegowaniewymiar())>0.0?Z.z(kalendarz.getGodzinydelegowanieprzepracowane()-kalendarz.getGodzinydelegowaniewymiar()):0.0;
            kalendarz.setDelegowanienadgodziny(nadgodziny);
            kalendarz.setDodatekzanadgodzinymc(Z.z(kalendarz.getDodatekzanadgodziny()*nadgodziny));
            kalendarzmiesiacFacade.edit(kalendarz);
            Msg.msg("Przeliczono nadgodziny "+kalendarz.getNazwiskoImie());
        }
    }
   
    public List<Zmiennawynagrodzenia> getZmienne() {
        return zmienne;
    }

    public void setZmienne(List<Zmiennawynagrodzenia> zmienne) {
        this.zmienne = zmienne;
    } 

    public List<Kalendarzmiesiac> getKalendarze() {
        return kalendarze;
    }

    public void setKalendarze(List<Kalendarzmiesiac> kalendarze) {
        this.kalendarze = kalendarze;
    }

    public Kalendarzmiesiac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Kalendarzmiesiac selectedlista) {
        this.selectedlista = selectedlista;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }
    
    
    
}
