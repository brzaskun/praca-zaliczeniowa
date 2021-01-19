/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzmiesiacBean;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.UmowaFacade;
import embeddable.Mce;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Umowa;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KalendarzmiesiacView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Kalendarzmiesiac selected;
    @Inject
    private Kalendarzmiesiac selectedlista;
    private List<Umowa> listaumowa;
    private List<Kalendarzmiesiac> listakalendarzeprac;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        selectedlista  = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        if (selectedlista!=null) {
            selected = selectedlista;
        }
        listaumowa =  umowaFacade.findPracownik(wpisView.getPracownik());
        pobierzkalendarzeprac();
    }

    public void create() {
      if (selected!=null) {
          try {
            kalendarzmiesiacFacade.edit(selectedlista);
            wpisView.setRokWpisu(selected.getRok());
            wpisView.setMiesiacWpisu(selected.getMc());
            selected = new Kalendarzmiesiac();
            selectedlista = null;
            Msg.msg("Dodano nowy kalendarz dla pracownika");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano kalendarza dla pracownika");
          }
      }
    }
    
    public void reset() {
      if (selectedlista!=null) {
          try {
            Kalendarzwzor kalendarzwzor = kalendarzwzorFacade.findByFirmaRokMc(selectedlista.getUmowa().getAngaz().getFirma(), selectedlista.getRok(), selectedlista.getMc());
            if (kalendarzwzor!=null) {
                selectedlista.nanies(kalendarzwzor);
                kalendarzmiesiacFacade.edit(selectedlista);
                Msg.msg("Zresetowano kalendarz dla pracownika do waartości domyślnych");
            } else {
                Msg.msg("e", "Brak kalendarza wzorcowego za dany okres");
            }
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nieudany reset kalendarza dla pracownika");
          }
      }
    }
    
    public void zrobkalendarzumowa() {
        if (selected!=null && selected.getUmowa()!=null) {
            if (selected.getRok()!=null&&selected.getMc()!=null) {
                selectedlista = kalendarzmiesiacFacade.findByRokMcUmowa(selected.getUmowa(), selected.getRok(), selected.getMc());
                if (selectedlista!=null) {
                    Msg.msg("Pobrano z bazy zachowany kalendarz");
                } else {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getUmowa().getAngaz().getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono!=null) {
                        selected.ganerujdnizwzrocowego(znaleziono, null);
                        Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych");
                    } else {
                        KalendarzmiesiacBean.create(selected);
                        Msg.msg("Przygotowano kalendarz");
                    }
                    selectedlista = new Kalendarzmiesiac(selected);
                }
            }
        } else {
                Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
    public void generujrok() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null && wpisView.getUmowa()!=null) {
            for (String mce: Mce.getMceListS()) {
                Kalendarzmiesiac kal = new Kalendarzmiesiac();
                kal.setRok(wpisView.getRokWpisu());
                kal.setMc(mce);
                kal.setUmowa(wpisView.getUmowa());
                Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mce);
                if (kalmiesiac==null) {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getUmowa().getAngaz().getFirma(), kal.getRok(), mce);
                    if (znaleziono!=null) {
                        kal.ganerujdnizwzrocowego(znaleziono, null);
                        kalendarzmiesiacFacade.create(kal);
                    } else {
                        Msg.msg("e","Brak kalendarza wzorcowego za "+mce);
                    }
                }
            }
            listakalendarzeprac = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
    }
    
    public void pobierzkalendarzeprac() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null) {
            listakalendarzeprac = kalendarzmiesiacFacade.findByRokUmowa(wpisView.getUmowa(), wpisView.getRokWpisu());
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
    }
    
   
    public Kalendarzmiesiac getSelected() {
        return selected;
    }

    public void setSelected(Kalendarzmiesiac selected) {
        this.selected = selected;
    }

    public Kalendarzmiesiac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Kalendarzmiesiac selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Umowa> getListaumowa() {
        return listaumowa;
    }

    public void setListaumowa(List<Umowa> listaumowa) {
        this.listaumowa = listaumowa;
    }

    public List<Kalendarzmiesiac> getListakalendarzeprac() {
        return listakalendarzeprac;
    }

    public void setListakalendarzeprac(List<Kalendarzmiesiac> listakalendarzeprac) {
        this.listakalendarzeprac = listakalendarzeprac;
    }

    
   
    
    
}
