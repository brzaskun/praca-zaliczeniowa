/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DefinicjalistaplacFacade;
import dao.FirmaFacade;
import entity.Definicjalistaplac;
import entity.Firma;
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
    @Inject
    private DefinicjalistaplacFacade definicjalistaplac;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = definicjalistaplac.findAll();
        listafirm = firmaFacade.findAll();
        selected.setRok(wpisView.getRokWpisu());
        selected.setMc(wpisView.getMiesiacWpisu());
    }

    public void create() {
      if (selected!=null) {
          try {
            definicjalistaplac.create(selected);
            lista.add(selected);
            selected = new Definicjalistaplac();
            Msg.msg("Dodano nową definicję listy płac");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej definicji listy płac");
          }
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

      
    
}
