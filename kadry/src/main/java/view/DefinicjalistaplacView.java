/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DefinicjalistaplacFacade;
import dao.FirmaFacade;
import dao.RodzajlistyplacFacade;
import data.Data;
import embeddable.Mce;
import entity.Definicjalistaplac;
import entity.Firma;
import entity.Rodzajlistyplac;
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
public class DefinicjalistaplacView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Definicjalistaplac selected;
    @Inject
    private Definicjalistaplac selectedlista;
    private List<Definicjalistaplac> lista;
    private List<Firma> listafirm;
    private List<Rodzajlistyplac> listarodzajlistyplac;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
        listafirm = firmaFacade.findAll();
        listarodzajlistyplac = rodzajlistyplacFacade.findAll();
        selected.setOpis("lista główna");
    }

    public void create() {
      if (selected!=null) {
          try {
            definicjalistaplacFacade.create(selected);
            lista.add(selected);
            selected = new Definicjalistaplac();
            Msg.msg("Dodano nową definicję listy płac");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej definicji listy płac");
          }
      }
    }
    
    public void createrok() {
        if (lista!=null && lista.size()==1) {
            Definicjalistaplac sel = lista.get(0);
            for (int i = 2; i <13 ;i++) {
                try {
                    selected = new Definicjalistaplac();
                    String rok = Data.getRok(sel.getDatasporzadzenia());
                    String mc = Data.getMc(sel.getDatasporzadzenia());
                    String dzien = Data.getDzien(sel.getDatasporzadzenia());
                    String[] zwiekszone = Mce.zwiekszmiesiac(rok, mc);
                    rok = zwiekszone[0];
                    mc = zwiekszone[1];
                    String lewaczesc = rok+"-"+mc+"-";
                    selected.setDatasporzadzenia(lewaczesc+dzien);
                    zwiekszone = Mce.zwiekszmiesiac(rok, mc);
                    String rokN = zwiekszone[0];
                    String mcN = zwiekszone[1];
                    lewaczesc = rokN+"-"+mcN+"-";
                    selected.setDatazus(lewaczesc+"15");
                    selected.setRodzajlistyplac(sel.getRodzajlistyplac());
                    selected.setDatapodatek(lewaczesc+"20");
                    selected.setMc(mc);
                    selected.setOpis(sel.getOpis());
                    selected.setRok(rok);
                    selected.setFirma(wpisView.getFirma());
                    selected.setNrkolejny(rok+"/"+mc);
                    selected.setId(null);
                    definicjalistaplacFacade.create(selected);
                    sel = selected;
                } catch (Exception e) {}
                
            }
            lista  = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
            Msg.msg("Wygenerowano listy na cały rok");  
            
        } else {
            Msg.msg("e","Lista jest pusta lub ma wiecej pozycji niż jedna");
        }
    }
    
    public void edytuj() {
      if (selected!=null) {
          try {
            definicjalistaplacFacade.edit(selected);
             selected = new Definicjalistaplac();
             selected.setOpis("lista główna");
             
            Msg.msg("Zachowano zmiany edycji definicji listy płac");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie zachowano zmian definicji listy płac");
          }
      }
    }

    public void uzupelnijdatylisty() {
        if (selected!=null && selected.getDatasporzadzenia()!=null) {
            String rok = Data.getRok(selected.getDatasporzadzenia());
            String mc = Data.getMc(selected.getDatasporzadzenia());
            String[] zwiekszone = Mce.zwiekszmiesiac(rok, mc);
            rok = zwiekszone[0];
            mc = zwiekszone[1];
            String lewaczesc = rok+"-"+mc+"-";
            selected.setDatazus(lewaczesc+"15");
            selected.setDatapodatek(lewaczesc+"20");
            selected.setMc(wpisView.getMiesiacWpisu());
            selected.setRok(wpisView.getRokWpisu());
            selected.setFirma(wpisView.getFirma());
            selected.setNrkolejny(wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
        }
    }
    
    public void wybierzdoedycji() {
          if (selectedlista!=null && selectedlista.getDatasporzadzenia()!=null) {
              selected = selectedlista;
              Msg.msg("Wybrano listę do edycji");
          } else {
              Msg.msg("e","Nie wybrano definicji");
          }
    }
    
    public void usun(Definicjalistaplac def) {
        if (def!=null) {
            definicjalistaplacFacade.remove(def);
            lista.remove(def);
            selected = new Definicjalistaplac();
            selected.setOpis("lista główna");
            Msg.msg("Usunieto definicje");
        } else {
            Msg.msg("e", "Nie wybrano definicji");
        }
    }
    
    public Definicjalistaplac getSelected() {
        return selected;
    }

    public void setSelected(Definicjalistaplac selected) {
        this.selected = selected;
    }

    public List<Definicjalistaplac> getLista() {
        return lista;
    }

    public void setLista(List<Definicjalistaplac> lista) {
        this.lista = lista;
    }

    public Definicjalistaplac getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Definicjalistaplac selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Firma> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<Firma> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Rodzajlistyplac> getListarodzajlistyplac() {
        return listarodzajlistyplac;
    }

    public void setListarodzajlistyplac(List<Rodzajlistyplac> listarodzajlistyplac) {
        this.listarodzajlistyplac = listarodzajlistyplac;
    }

      
    
}
