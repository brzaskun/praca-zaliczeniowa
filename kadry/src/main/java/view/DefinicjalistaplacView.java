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
import entity.Definicjalistaplac;
import entity.Firma;
import entity.Rodzajlistyplac;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@RequestScoped
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
    private DefinicjalistaplacFacade definicjalistaplac;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = definicjalistaplac.findAll();
        listafirm = firmaFacade.findAll();
        listarodzajlistyplac = rodzajlistyplacFacade.findAll();
        selected.setOpis("lista główna");
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

    public void uzupelnijdatylisty() {
        if (selected!=null && selected.getDatasporzadzenia()!=null) {
            String lewaczesc = Data.getCzescDaty(selected.getDatasporzadzenia(), 3);
            selected.setDatazus(lewaczesc+"15");
            selected.setDatapodatek(lewaczesc+"20");
            selected.setMc(wpisView.getMiesiacWpisu());
            selected.setRok(wpisView.getRokWpisu());
            selected.setFirma(wpisView.getFirma());
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
