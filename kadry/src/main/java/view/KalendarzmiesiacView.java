/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beans.KalendarzmiesiacBean;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.UmowaFacade;
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
    private List<Kalendarzmiesiac> lista;
    private List<Umowa> listaumowa;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = kalendarzmiesiacFacade.findAll();
        listaumowa = umowaFacade.findAll();
    }

    public void create() {
      if (selected!=null) {
          try {
            kalendarzmiesiacFacade.edit(selected);
            lista.add(selected);
            selected = new Kalendarzmiesiac();
            Msg.msg("Dodano nowy kalendarz dla pracownika");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano kalendarza dla pracownika");
          }
      }
    }
    
    public void reset() {
      if (selected!=null) {
          try {
            Kalendarzwzor kalendarzwzor = kalendarzwzorFacade.findByFirmaRokMc(selected.getUmowa().getAngaz().getFirma(), selected.getRok(), selected.getMc());
            if (kalendarzwzor!=null) {
                selected.nanies(kalendarzwzor);
                kalendarzmiesiacFacade.edit(selected);
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
                Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcUmowa(selected.getUmowa(), selected.getRok(), selected.getMc());
                if (znaleziony!=null) {
                    selected = znaleziony;
                    Msg.msg("Pobrano z bazy zachowany kalendarz");
                } else {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getUmowa().getAngaz().getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono!=null) {
                        selected.ganerujdnizwzrocowego(znaleziono);
                        Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych");
                    } else {
                        KalendarzmiesiacBean.create(selected);
                        Msg.msg("Przygotowano kalendarz");
                    }
                }
            }
        } else {
            Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
     
    public Kalendarzmiesiac getSelected() {
        return selected;
    }

    public void setSelected(Kalendarzmiesiac selected) {
        this.selected = selected;
    }

    public List<Kalendarzmiesiac> getLista() {
        return lista;
    }

    public void setLista(List<Kalendarzmiesiac> lista) {
        this.lista = lista;
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

    
   
    
    
}
