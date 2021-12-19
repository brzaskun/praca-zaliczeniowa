/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.TytulFacade;
import dao.DefinicjalistaplacFacade;
import dao.FirmaKadryFacade;
import dao.RodzajlistyplacFacade;
import data.Data;
import embeddable.Mce;
import entity.Definicjalistaplac;
import entity.FirmaKadry;
import entity.Rodzajlistyplac;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Tytul;
import msg.Msg;
import viewsuperplace.OsobaBean;

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
    private List<FirmaKadry> listafirm;
    private List<Rodzajlistyplac> listarodzajlistyplac;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private FirmaKadryFacade firmaFacade;
    @Inject
    private WpisView wpisView;
    private Rodzajlistyplac wybranyrodzajlisty;
    
    @PostConstruct
    public void init() {
        if (wybranyrodzajlisty!=null) {
            lista = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), wybranyrodzajlisty);
        } else {
            lista = new ArrayList<>();
        }
        listafirm = firmaFacade.findAll();
        listarodzajlistyplac = rodzajlistyplacFacade.findAktywne();
    }

    public void create() {
      if (selected!=null) {
          try {
            if (selected.getRodzajlistyplac()!=null) {
                String nazwalisty = OsobaBean.generujRodzajLP(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), selected.getRodzajlistyplac());
                if (nazwalisty==null) {
                    Msg.msg("e","Nie mozna wygenerować listy. Nie ma symbolu w ustawieniach!");
                } else {
                    selected.setNrkolejny(nazwalisty);
                    definicjalistaplacFacade.create(selected);
                    lista.add(selected);
                    selected = new Definicjalistaplac();
                    Msg.msg("Dodano nową definicję listy płac");
               }
            } else {
                Msg.msg("e","Nie dodano definicji listy płac");
            }
            
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej definicji listy płac");
          }
      }
    }
    
    

    
    public void reset() {
      if (selected!=null) {
          try {
            selected = new Definicjalistaplac();
            Msg.msg("Reset");
          } catch (Exception e) {
              Msg.msg("e", "Błąd resetu");
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
                    String[] zwiekszone = Mce.zwiekszmiesiac(rok, mc);
                    rok = zwiekszone[0];
                    mc = zwiekszone[1];
                    String nowadata = Data.ostatniDzien(rok, mc);
                    String lewaczesc = rok+"-"+mc+"-";
                    selected.setDatasporzadzenia(nowadata);
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
                    String nazwalisty = OsobaBean.generujRodzajLP(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wybranyrodzajlisty);
                    if (nazwalisty==null) {
                        Msg.msg("e","Nie mozna wygenerować listy. Nie ma symbolu w ustawieniach!");
                        break;
                    } else {
                       selected.setId(null);
                       definicjalistaplacFacade.create(selected);
                       sel = selected;
                    }
                    
                } catch (Exception e) {}
                
            }
            if (wybranyrodzajlisty != null) {
                 lista = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), wybranyrodzajlisty);
            }
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
             selected.setOpis("wynagrodzenie kraj");
             
            Msg.msg("Zachowano zmiany edycji definicji listy płac");
          } catch (Exception e) {
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
            selected.setOpis("wynagrodzenie kraj");
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

    public List<FirmaKadry> getListafirm() {
        return listafirm;
    }

    public void setListafirm(List<FirmaKadry> listafirm) {
        this.listafirm = listafirm;
    }

    public List<Rodzajlistyplac> getListarodzajlistyplac() {
        return listarodzajlistyplac;
    }

    public void setListarodzajlistyplac(List<Rodzajlistyplac> listarodzajlistyplac) {
        this.listarodzajlistyplac = listarodzajlistyplac;
    }

    public Rodzajlistyplac getWybranyrodzajlisty() {
        return wybranyrodzajlisty;
    }

    public void setWybranyrodzajlisty(Rodzajlistyplac wybranyrodzajlisty) {
        this.wybranyrodzajlisty = wybranyrodzajlisty;
    }

    @Inject
    private TytulFacade tytulFacade;
       public void generujtabele() {
        Msg.msg("Start");
        List<Tytul> findAll = tytulFacade.findAll();
        for (Tytul p : findAll) {
            Rodzajlistyplac s  = new Rodzajlistyplac();
            s.setNazwa(p.getTytOpis());
            s.setTyt_kolejnosc(p.getTytKolejnosc());
            s.setTyt_okres(p.getTytOkres());
            s.setTyt_serial(p.getTytSerial());
            rodzajlistyplacFacade.create(s);
        }
        Msg.dP();
    }
    
}
