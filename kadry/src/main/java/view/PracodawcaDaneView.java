/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.SMTPSettingsFacade;
import data.Data;
import entity.Pracownik;
import entity.SMTPSettings;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import mail.Mail;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PracodawcaDaneView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pracownik> listapracownikow;
    private List<Pracownik> listafiltered;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private Pracownik selected;
    private Pracownik selectedlista;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        listapracownikow = angazFacade.findPracownicyByFirma(wpisView.getFirma());
    }

    
    public void create() {
      if (selected!=null) {
          try {
            selected.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            selected.setDatalogowania(Data.aktualnaDataCzas());
            selected.setModyfikowal(wpisView.getUzer().getLogin());
            pracownikFacade.create(selected);
            listapracownikow.add(selected);
            wpisView.setPracownik(selected);
            maildodanonowegoprac(selected);
            selected = new Pracownik();
            Msg.msg("Dodano nowego pracownika");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego pracownika");
          }
      }
    }
    
    public void edytuj(Pracownik pracownik) {
      if (pracownik!=null) {
          try {
            pracownik.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            pracownik.setDatalogowania(Data.aktualnaDataCzas());
            pracownik.setModyfikowal(wpisView.getUzer().getLogin());
            pracownikFacade.edit(pracownik);
            wpisView.setPracownik(pracownik);
            Msg.msg("Zachowano zmienione dane pracownika");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie zachowano zmian pracownika");
          }
      }
    }
    
    public void mailbiuro() {
        SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
        Mail.updateemailpracownik(wpisView.getFirma(),"info@taxman.biz.pl", null, findSprawaByDef);
    }
    
    public void maildodanonowegoprac(Pracownik pracownik) {
        SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
        Mail.updateemailnowypracownik(wpisView.getFirma(),pracownik,"info@taxman.biz.pl", null, findSprawaByDef);
    }
    
    public List<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public List<Pracownik> getListafiltered() {
        return listafiltered;
    }

    public void setListafiltered(List<Pracownik> listafiltered) {
        this.listafiltered = listafiltered;
    }

    public Pracownik getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pracownik selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
}
