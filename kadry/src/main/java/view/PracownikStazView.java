/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.UrlopBean;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.StazFacade;
import data.Data;
import entity.Angaz;
import entity.Slownikszkolazatrhistoria;
import entity.Staz;
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
public class PracownikStazView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Staz selected;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private StazFacade stazFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    private List<Angaz> listapracownikow;
    private List<Staz> listastaz;
    private Angaz selectedangaz;

    
    
    @PostConstruct
    public void init() {
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
    }

      public void dodaj() {
      if (selected!=null&&selected.getAngaz()!=null) {
          try {
            if (selected.getSlownikszkolazatrhistoria().getLata()>0) {
                selected.setLata(selected.getSlownikszkolazatrhistoria().getLata());
            } else {
                Data.obliczstaz(selected.getDataod(),selected.getDatado(), selected);
            }
            stazFacade.create(selected);
            pobierzdane();
            Msg.msg("Dodano historyczna umowę pracownika");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      } else {
          Msg.msg("e", "Nie wybrano angażu");
      }
    }   
   
      
       public void usun(Staz selected) {
      if (selected!=null) {
          try {
            stazFacade.remove(selected);
            listastaz.remove(selected);
            selected = new Staz();
            selected.setAngaz(selectedangaz);
            Msg.msg("Usunięto historyczna umowę pracownika");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie usunięto danych");
          }
      } else {
          Msg.msg("e", "Nie wybrano angażu");
      }
    }   
   
    
    public void pobierzdane() {
        if (selectedangaz!=null) {
            listastaz = stazFacade.findByAngaz(selectedangaz);
            if (!listastaz.isEmpty()) {
                Staz suma = UrlopBean.obliczwymiarwStaz(listastaz);
                suma.setId(999);
                suma.setSlownikszkolazatrhistoria(new Slownikszkolazatrhistoria("podsumowanie"));
                listastaz.add(suma);
                selectedangaz.setStazlata(suma.getLata());
                selectedangaz.setStazdni(suma.getDni());
                angazFacade.edit(selectedangaz);
            }
            selected = new Staz();
            selected.setAngaz(selectedangaz);
            //wpisView.setPracownik(selected);
            Msg.msg("Pobrano dane pracownika");
        }
    }

    public Staz getSelected() {
        return selected;
    }

    public void setSelected(Staz selected) {
        this.selected = selected;
    }
   
    public List<Angaz> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Angaz> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Angaz getSelectedangaz() {
        return selectedangaz;
    }

    public void setSelectedangaz(Angaz selectedangaz) {
        this.selectedangaz = selectedangaz;
    }

    public List<Staz> getListastaz() {
        return listastaz;
    }

    public void setListastaz(List<Staz> listastaz) {
        this.listastaz = listastaz;
    }

   
    
}
