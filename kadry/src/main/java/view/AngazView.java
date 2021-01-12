/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.FirmaFacade;
import dao.PracownikFacade;
import entity.Angaz;
import entity.Firma;
import entity.Pracownik;
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
public class AngazView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Angaz selected;
    @Inject
    private Angaz selectedlista;
    private Angaz selectedeast;
    private List<Angaz> lista;
    private List<Angaz> listaeast;
    private List<Firma> listafirm;
    private List<Pracownik> listapracownikow;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private UmowaView umowaView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    @Inject
    private PracownikView pracownikView;
    
    @PostConstruct
    private void init() {
        lista  = angazFacade.findAll();
        listafirm = firmaFacade.findAll();
        listapracownikow = pracownikFacade.findAll();
        if (wpisView.getFirma()!=null) {
            lista = angazFacade.findByFirma(wpisView.getFirma());
            listaeast = angazFacade.findByFirma(wpisView.getFirma());
        }
         if (wpisView.getAngaz()!=null) {
            selectedeast = wpisView.getAngaz();
        }
    }
    

    public void create() {
      if (selected!=null && wpisView.getFirma()!=null) {
          try {
            selected.setFirma(wpisView.getFirma());
            angazFacade.create(selected);
            lista.add(selected);
            wpisView.setAngaz(selected);
            selected = new Angaz();
            Msg.msg("Dodano nowy angaż");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego angażu");
          }
      }
    }
    
   public void aktywuj(Angaz angaz) {
        if (angaz!=null) {
            wpisView.setAngaz(angaz);
            wpisView.setPracownik(angaz.getPracownik());
            Msg.msg("Aktywowano pracownika");
        }
    }
    
     public void usun(Angaz angaz) {
        if (angaz!=null) {
            if (wpisView.getAngaz()!=null && wpisView.getAngaz().equals(angaz)) {
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
                wpisView.memorize();
            }
            angazFacade.remove(angaz);
            lista.remove(angaz);
            Msg.msg("Usunięto angaż");
        } else {
            Msg.msg("e","Nie wybrano angażu");
        }
    }
    
    public void findByFirma(Firma firma) {
        if (firma!=null) {
            listaeast = angazFacade.findByFirma(firma);
            Msg.msg("Pobrano pracowników firmy");
        } else {
            Msg.msg("e", "Błąd nie wybrano firmy");
        }
    }
    
    public Angaz getSelected() {
        return selected;
    }

    public void setSelected(Angaz selected) {
        this.selected = selected;
    }

    public List<Angaz> getLista() {
        return lista;
    }

    public void setLista(List<Angaz> lista) {
        this.lista = lista;
    }

    public Angaz getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Angaz selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Firma> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<Firma> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(List<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public List<Angaz> getListaeast() {
        return listaeast;
    }

    public void setListaeast(List<Angaz> listaeast) {
        this.listaeast = listaeast;
    }


    public Angaz getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(Angaz selectedeast) {
        this.selectedeast = selectedeast;
    }

   
    
    
}
