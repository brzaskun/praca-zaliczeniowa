/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KalendarzWzorBean;
import dao.FirmaFacade;
import dao.KalendarzwzorFacade;
import entity.Firma;
import entity.Kalendarzwzor;
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
public class KalendarzwzorView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Kalendarzwzor selected;
    @Inject
    private Kalendarzwzor selectedlista;
    private List<Kalendarzwzor> lista;
    private List<Firma> listafirm;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = kalendarzwzorFacade.findAll();
        listafirm = firmaFacade.findAll();
        selected.setFirma(wpisView.getFirma());
        selected.setRok(wpisView.getRokWpisu());
    }

    public void create() {
      if (selected!=null) {
          try {
            kalendarzwzorFacade.create(selected);
            lista.add(selected);
            selected = new Kalendarzwzor(selected.getFirma(), selected.getRok());
            Msg.msg("Dodano nowy kalendarz");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej firmy");
          }
      }
    }
    
    public void zrobkalendarzumowa() {
        if (selected!=null && selected.getFirma()!=null) {
            if (selected.getRok()!=null&&selected.getMc()!=null) {
                try {
                    Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(selected.getFirma(), selected.getRok(), selected.getMc());
                    if (znaleziono!=null) {
                        selected = znaleziono;
                        Msg.msg("Pobrano kalendarz z bazy danych");
                    } else {
                        KalendarzWzorBean.create(selected);
                        Msg.msg("Przygotowano kalendarz");
                    }
                } catch (Exception e){}
            }
        } else {
            Msg.msg("e", "Błąd - nie wybrano firmy dla kalendarza");
        }
    }
    
     
    public Kalendarzwzor getSelected() {
        return selected;
    }

    public void setSelected(Kalendarzwzor selected) {
        this.selected = selected;
    }

    public List<Kalendarzwzor> getLista() {
        return lista;
    }

    public void setLista(List<Kalendarzwzor> lista) {
        this.lista = lista;
    }

    public Kalendarzwzor getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Kalendarzwzor selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Firma> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<Firma> listafirm) {
        this.listafirm = listafirm;
    }

   
    
    
}
