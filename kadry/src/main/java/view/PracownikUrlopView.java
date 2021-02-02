/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beans.IPaddress;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.UzFacade;
import data.Data;
import entity.Angaz;
import entity.Pracownik;
import java.io.Serializable;
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
    private Pracownik selected;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    private List<Angaz> listapracownikow;
    private Angaz selectedangaz;
    
    
    @PostConstruct
    private void init() {
        listapracownikow = angazFacade.findByFirma(wpisView.getFirma());
    }

        
    public void edit() {
      if (selected!=null) {
          try {
            selected.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            Calendar calendar = Calendar.getInstance();
            selected.setDatalogowania(Data.aktualnaDataCzas());
            selected.setModyfikowal(wpisView.getUzer().getSecname());
            pracownikFacade.edit(selected);
            Msg.msg("Uaktualniono dane");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie zmieniono danych");
          }
      }
    }
    
    public void pobierzdane() {
        if (selectedangaz!=null) {
            selected = selectedangaz.getPracownik();
            wpisView.setPracownik(selected);
            Msg.msg("Pobrano dane");
        }
    }
    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
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

    
    
}
