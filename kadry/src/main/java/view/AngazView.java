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
public class AngazView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Angaz selected;
    @Inject
    private Angaz selectedlista;
    private List<Angaz> lista;
    private List<Firma> listafirm;
    private List<Pracownik> listapracownikow;
    private List<Pracownik> listapracownikoweast;
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
            listapracownikoweast = angazFacade.findPracownicyByFirma(wpisView.getFirma());
        }
    }
    
    public void initRecznie() {
        init();
    }

    public void create() {
      if (selected!=null) {
          try {
            angazFacade.create(selected);
            lista.add(selected);
            wpisView.setAngaz(selected);
            selected = new Angaz();
            pracownikView.initRecznie();
            zmiennaWynagrodzeniaView.initRecznie();
            skladnikWynagrodzeniaView.initRecznie();
            umowaView.initRecznie();
            Msg.msg("Dodano nowy angaż");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowego angażu");
          }
      }
    }
    
    public void aktywuj() {
        if (selectedlista!=null) {
            wpisView.setAngaz(selectedlista);
            pracownikView.initRecznie();
            zmiennaWynagrodzeniaView.initRecznie();
            skladnikWynagrodzeniaView.initRecznie();
            umowaView.initRecznie();
            Msg.msg("Aktywowano angaż");
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
            pracownikView.initRecznie();
            zmiennaWynagrodzeniaView.initRecznie();
            skladnikWynagrodzeniaView.initRecznie();
            umowaView.initRecznie();
            Msg.msg("Usunięto angaż");
        } else {
            Msg.msg("e","Nie wybrano angażu");
        }
    }
    
    public void findPracownicyByFirma(Firma firma) {
        if (firma!=null) {
            listapracownikoweast = angazFacade.findPracownicyByFirma(firma);
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

    public List<Pracownik> getListapracownikoweast() {
        return listapracownikoweast;
    }

    public void setListapracownikoweast(List<Pracownik> listapracownikoweast) {
        this.listapracownikoweast = listapracownikoweast;
    }

   
    
    
}
