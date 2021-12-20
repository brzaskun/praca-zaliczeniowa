/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.IPaddress;
import comparator.Pracownikcomparator;
import dao.PracownikFacade;
import data.Data;
import entity.Pracownik;
import java.io.Serializable;
import java.util.Collections;
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
public class PracownikView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pracownik selected;
    private List<Pracownik> selectedlista;
    private Pracownik selectedeast;
    private List<Pracownik> lista;
    private List<Pracownik> listafiltered;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazView angazView;
    
    @PostConstruct
    public void init() {
        lista  = pracownikFacade.findAll();
        Collections.sort(lista, new Pracownikcomparator());
    }

        
    public void create() {
      if (selected!=null) {
          try {
            selected.setIpusera(IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()));
            selected.setModyfikowal(wpisView.getUzer().getLogin());
            pracownikFacade.create(selected);
            lista.add(selected);
            wpisView.setPracownik(selected);
            selected = new Pracownik();
            Msg.msg("Dodano nowego pracownika");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowego pracownika");
          }
      }
    }
    
    public void aktywuj(Pracownik pracownik) {
        if (pracownik!=null) {
            wpisView.setPracownik(pracownik);
            Msg.msg("Aktywowano pracownika");
        }
    }
    
    public void usunwybrane() {
        if (selectedlista!=null) {
            for (Pracownik pracownik : selectedlista) {
                try {
                if (wpisView.getPracownik() != null && wpisView.getPracownik().equals(pracownik)) {
                    wpisView.setPracownik(null);
                }
                if (wpisView.getAngaz() != null && wpisView.getAngaz().getPracownik().equals(pracownik)) {
                    if (angazView.getLista()!=null) {
                        angazView.getLista().remove(wpisView.getAngaz());
                    }
                    wpisView.setAngaz(null);
                }
                if (wpisView.getUmowa() != null && wpisView.getUmowa().getAngaz().getPracownik().equals(pracownik)) {
                    wpisView.setUmowa(null);
                }
                pracownikFacade.remove(pracownik);
                lista.remove(pracownik);
            } catch (Exception e) {
                Msg.msg("e", "Nie można usunąć pracownika. "+pracownik.getNazwiskoImie());
            }
            }
            selectedlista = null;
            listafiltered = null;
            Msg.msg("Usunięto pracowników");
        }
    }
    
    
    public void usun(Pracownik pracownik) {
        if (pracownik != null) {
            try {
                if (wpisView.getPracownik() != null && wpisView.getPracownik().equals(pracownik)) {
                    wpisView.setPracownik(null);
                }
                if (wpisView.getAngaz() != null && wpisView.getAngaz().getPracownik().equals(pracownik)) {
                    if (angazView.getLista()!=null) {
                        angazView.getLista().remove(wpisView.getAngaz());
                    }
                    wpisView.setAngaz(null);
                }
                if (wpisView.getUmowa() != null && wpisView.getUmowa().getAngaz().getPracownik().equals(pracownik)) {
                    wpisView.setUmowa(null);
                }
                pracownikFacade.remove(pracownik);
                lista.remove(pracownik);
                if (listafiltered != null && listafiltered.contains(pracownik)) {
                    listafiltered.remove(pracownik);
                }
                 if (selectedlista != null && selectedlista.contains(pracownik)) {
                    selectedlista.remove(pracownik);
                }
                Msg.msg("Usunięto pracownika");
            } catch (Exception e) {
                Msg.msg("e", "Nie można usunąć pracownika. Istnieją angaże");
            }
        } else {
            Msg.msg("e", "Nie wybrano pracownika");
        }
    }
    
    
    public void korektadat() {
        if (!lista.isEmpty()) {
            Msg.msg("poczatek");
            for (Pracownik p : lista) {
                p. setDataurodzenia(Data.zmienkolejnosc(p.getDataurodzenia()));
                if (p.getDatazatrudnienia()!=null&&!p.getDatazatrudnienia().equals("")) {
                    p. setDatazatrudnienia(Data.zmienkolejnosc(p.getDatazatrudnienia()));
                }
                if (p.getDatazwolnienia()!=null&&!p.getDatazwolnienia().equals("")) {
                    p. setDatazwolnienia(Data.zmienkolejnosc(p.getDatazwolnienia()));
                }
            }
            pracownikFacade.editList(lista);
            Msg.msg("koniec");
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
              Msg.msg("e", "Błąd - nie zachowano zmian pracownika");
          }
      }
    }
    
    public Pracownik getSelected() {
        return selected;
    }

    public void setSelected(Pracownik selected) {
        this.selected = selected;
    }

    public List<Pracownik> getLista() {
        return lista;
    }

    public void setLista(List<Pracownik> lista) {
        this.lista = lista;
    }

    public List<Pracownik> getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(List<Pracownik> selectedlista) {
        this.selectedlista = selectedlista;
    }


    public Pracownik getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Pracownik selectedeast) {
        this.selectedeast = selectedeast;
    }

    public List<Pracownik> getListafiltered() {
        return listafiltered;
    }

    public void setListafiltered(List<Pracownik> listafiltered) {
        this.listafiltered = listafiltered;
    }
    
    
    
}

