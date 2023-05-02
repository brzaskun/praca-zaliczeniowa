/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class SkladnikWynagrodzeniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Skladnikwynagrodzenia selected;
    @Inject
    private Skladnikwynagrodzenia selectedlista;
    private List<Skladnikwynagrodzenia> lista;
    private List<Rodzajwynagrodzenia> listarodzajwynagrodzenia;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz()!=null) {
            lista  = skladnikWynagrodzeniaFacade.findByAngaz(wpisView.getAngaz()).stream().filter(p->p.getRodzajwynagrodzenia().isTylkosuperplace()==false).collect(Collectors.toList());
            if (lista==null) {
                lista = new ArrayList<>();
            } else {
                selectedlista = lista.get(0);
            }
            lista.add(new Skladnikwynagrodzenia(wpisView.getAngaz()));
        }
        selected.setAngaz(wpisView.getAngaz());
        listarodzajwynagrodzenia = rodzajwynagrodzeniaFacade.findAktywne();
        listarodzajwynagrodzenia.add(new Rodzajwynagrodzenia(-1, null, "dodaj nowy składnik", "dodaj nowy składnik"));

    }
    

    public void create(Skladnikwynagrodzenia selected) {
      if (selected!=null && wpisView.getAngaz()!=null) {
          try {
                selected.setAngaz(wpisView.getAngaz());
                skladnikWynagrodzeniaFacade.create(selected);
                lista.add(new Skladnikwynagrodzenia(wpisView.getAngaz()));
                Msg.msg("Dodano nowy składnik wynagrodzenia");
              } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowego składnika wynagrodzenai");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    public void edycja(Skladnikwynagrodzenia skladnik) {
      if (skladnik!=null && wpisView.getAngaz()!=null) {
          try {
            if (skladnik.getId()==null) {
                create(skladnik);
            } else if (skladnik.getId()!=null) {
                skladnikWynagrodzeniaFacade.edit(skladnik);
                Msg.msg("Udana edycja składnika wyn");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowego składnika wynagrodzenai");
          }
      } else {
          Msg.msg("e","Brak wybranej umowy");
      }
    }
    
    
    public void usunSkladnikWyn(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        if (skladnikwynagrodzenia!=null) {
            skladnikWynagrodzeniaFacade.remove(skladnikwynagrodzenia);
            lista.remove(skladnikwynagrodzenia);
            Msg.msg("Usunięto składnik wynagrodzeani");
        } else {
            Msg.msg("e","Nie wybrano składnika wynagrodzenia");
        }
    }
    
    public void wybierzdoedycji(Skladnikwynagrodzenia selected) {
      if (selected!=null) {
          try {
            this.selected  = selected;
            Msg.msg("Zmieniono składnik do edycji");
          } catch (Exception e) {
              Msg.msg("e", "Błąd edycji składnika wyn");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    public Skladnikwynagrodzenia getSelected() {
        return selected;
    }

    public void setSelected(Skladnikwynagrodzenia selected) {
        this.selected = selected;
    }

    public Skladnikwynagrodzenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Skladnikwynagrodzenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Skladnikwynagrodzenia> getLista() {
        return lista;
    }

    public void setLista(List<Skladnikwynagrodzenia> lista) {
        this.lista = lista;
    }


    public List<Rodzajwynagrodzenia> getListarodzajwynagrodzenia() {
        return listarodzajwynagrodzenia;
    }

    public void setListarodzajwynagrodzenia(List<Rodzajwynagrodzenia> listarodzajwynagrodzenia) {
        this.listarodzajwynagrodzenia = listarodzajwynagrodzenia;
    }

    

    
    
    
}
