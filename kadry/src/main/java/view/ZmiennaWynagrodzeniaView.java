/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.ZmiennaWynagrodzeniacomparator;
import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
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
//musibyc viewscoped bo inaczej to odswieza i nie ma przyporzadkowanej zmiennej wyangrodzeni
public class ZmiennaWynagrodzeniaView  implements Serializable {
    private static final long serialVersionUID = 1L;
//    @Inject
//    private Zmiennawynagrodzenia selected;
    @Inject
    private Zmiennawynagrodzenia selectedlista;
    private List<Zmiennawynagrodzenia> lista;
    private List<Skladnikwynagrodzenia> listaskladnikiwynagrodzenia;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private WpisView wpisView;
    private Skladnikwynagrodzenia biezacyskladnikwynagrodzenia;
    
    
    @PostConstruct
    public void init() {
        if (wpisView.getAngaz() != null) {
            listaskladnikiwynagrodzenia = skladnikWynagrodzeniaFacade.findByAngaz(wpisView.getAngaz());
            if (listaskladnikiwynagrodzenia != null && !listaskladnikiwynagrodzenia.isEmpty()) {
                lista = zmiennaWynagrodzeniaFacade.findBySkladnik(listaskladnikiwynagrodzenia.get(0));
                lista.add(new Zmiennawynagrodzenia(listaskladnikiwynagrodzenia.get(0)));
                Collections.sort(lista, new ZmiennaWynagrodzeniacomparator());
            }
        }
    }
    
    public void init2(Skladnikwynagrodzenia skladnikwynagrodzenia) {
       if (skladnikwynagrodzenia!=null&&skladnikwynagrodzenia.getId()!=null) {
            biezacyskladnikwynagrodzenia = skladnikwynagrodzenia;
            lista  = zmiennaWynagrodzeniaFacade.findBySkladnik(skladnikwynagrodzenia);
            lista.add(new Zmiennawynagrodzenia(skladnikwynagrodzenia));
            Collections.sort(lista, new ZmiennaWynagrodzeniacomparator());
       }  else {
           lista = null;
       }
   }
    

    public void create(Zmiennawynagrodzenia selected) {
        if (selected != null && selected.getSkladnikwynagrodzenia() != null) {
            Skladnikwynagrodzenia skladnikwynagrodzenia = selected.getSkladnikwynagrodzenia();
            try {
                if (selected.getId() == null && selected.getDataod() != null && !selected.getDataod().equals("")) {
                    if (lista != null && lista.size() > 0) {
                        zakonczokrespoprzedni(lista, selected);
                    }
                    selected.setDatadodania(new Date());
                    selected.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                    zmiennaWynagrodzeniaFacade.create(selected);
                    lista.add(new Zmiennawynagrodzenia(skladnikwynagrodzenia));
                    Msg.msg("Dodano zmienną wyn");
                } else {
                    Msg.msg("e", "Brak daty początkowej. Nie można zapisać");
                }
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano zmiennej wyn");
            }
        } else {
            Msg.msg("e", "Nie wybrano składnika");
        }
    }
    
    public void edit(Zmiennawynagrodzenia selected) {
      if (selected!=null && selected.getSkladnikwynagrodzenia()!=null) {
          try {
            if (selected.getId()!=null) {
                zmiennaWynagrodzeniaFacade.edit(selected);
                Msg.msg("Udana edycja zmiennej wyn");
            }
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano zmiennej wyn");
          }
      } else {
          Msg.msg("e", "Nie wybrano składnika");
      }
    }
    
//     public void edytuj(Zmiennawynagrodzenia selected) {
//      if (selected!=null && selected.getSkladnikwynagrodzenia()!=null) {
//          try {
//            zmiennaWynagrodzeniaFacade.edit(selected);
//            Msg.msg("Zmieniono zmienną wyn");
//          } catch (Exception e) {
//              Msg.msg("e", "Błąd edycji zmiennej wyn");
//          }
//      } else {
//          Msg.msg("e", "Nie wybrano składnika");
//      }
//    }
     
//     public void wybierzdoedycji(Zmiennawynagrodzenia selected) {
//      if (selected!=null && selected.getSkladnikwynagrodzenia()!=null) {
//          try {
//            this.selected  = selected;
//            Msg.msg("Zmieniono zmienną do edycji");
//          } catch (Exception e) {
//              Msg.msg("e", "Błąd edycji zmiennej wyn");
//          }
//      } else {
//          Msg.msg("e", "Nie wybrano zmiennej wyn");
//      }
//    }
    
    private void zakonczokrespoprzedni(List<Zmiennawynagrodzenia> lista, Zmiennawynagrodzenia selected) {
        try {
            Collections.sort(lista, new ZmiennaWynagrodzeniacomparator());
            Zmiennawynagrodzenia ostatnia = lista.get(1);
            String nowadataod = selected.getDataod();
            String wyliczonadatado = Data.odejmijdni(nowadataod, 1);
            ostatnia.setDatado(wyliczonadatado);
            zmiennaWynagrodzeniaFacade.edit(ostatnia);
        } catch (Exception e) {}
    }

    public void usunZmiennaWyn(Zmiennawynagrodzenia zmienna) {
        if (zmienna!=null) {
            zmiennaWynagrodzeniaFacade.remove(zmienna);
            lista.remove(zmienna);
            Collections.sort(lista, new ZmiennaWynagrodzeniacomparator());
            Msg.msg("Usunięto zmienną");
        } else {
            Msg.msg("e","Nie wybrano zmiennej");
        }
    }


    public Zmiennawynagrodzenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Zmiennawynagrodzenia selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Zmiennawynagrodzenia> getLista() {
        return lista;
    }

    public void setLista(List<Zmiennawynagrodzenia> lista) {
        this.lista = lista;
    }

    public List<Skladnikwynagrodzenia> getListaskladnikiwynagrodzenia() {
        return listaskladnikiwynagrodzenia;
    }

    public void setListaskladnikiwynagrodzenia(List<Skladnikwynagrodzenia> listaskladnikiwynagrodzenia) {
        this.listaskladnikiwynagrodzenia = listaskladnikiwynagrodzenia;
    }

    

    
    
    
}
