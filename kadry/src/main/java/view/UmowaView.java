/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.KodzawoduBean;
import dao.AngazFacade;
import dao.EtatFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.KodyzawodowFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.Etat;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Kodyzawodow;
import entity.Skladnikwynagrodzenia;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
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
public class UmowaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Umowa selected;
    @Inject
    private Umowa selectedlista;
    @Inject
    private Etat etat;
    private List<Umowa> lista;
    private List<Angaz> listaangaz;
    private List<Umowakodzus> listaumowakodzus;
    private List<Kodyzawodow> listakodyzawodow;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private KodyzawodowFacade kodyzawodowFacade;
    @Inject
    private EtatFacade etatFacade;
    @Inject
    private UmowakodzusFacade rodzajumowyFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    private double wynagrodzemieskrot;
    
    @PostConstruct
    public void init() {
        lista  = umowaFacade.findByAngaz(wpisView.getAngaz());
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listaumowakodzus = rodzajumowyFacade.findAll();
        listakodyzawodow = kodyzawodowFacade.findAll();
       
    }
 
    public void create() {
      if (selected!=null && wpisView.getAngaz()!=null) {
          try {
            selected.setAngaz(wpisView.getAngaz());
            for (Umowa p : lista) {
                p.setAktywna(false);
            }
            selected.setAktywna(true);
            umowaFacade.editList(lista);
            umowaFacade.create(selected);
            lista.add(selected);
            Etat etat = new Etat(selected);
            etatFacade.create(etat);
            wpisView.setUmowa(selected);
            Msg.msg("Dodano nową umowę");
            generujKalendarzNowaUmowa();
            if (wynagrodzemieskrot!=0.0){
              Skladnikwynagrodzenia skladnikwynagrodzenia = dodajskladnikwynagrodzenia();
              Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia);
              if (skladnikwynagrodzenia.getId()!=null && zmiennawynagrodzenie!=null){
                  Msg.msg("Dodano składniki wynagrodzania");
              }
              skladnikWynagrodzeniaView.init();
              zmiennaWynagrodzeniaView.init();
            }
            selected = new Umowa();
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
          }
      }
    }
    
    
    public void generujKalendarzNowaUmowa() {
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null && wpisView.getUmowa()!=null) {
            Integer mcod = Integer.parseInt(Data.getMc(wpisView.getUmowa().getDataod()));
            Integer dzienod = Integer.parseInt(Data.getDzien(wpisView.getUmowa().getDataod()));
            for (String mce: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mce);
                if (kolejnymc>=mcod) {
                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
                    kal.setRok(wpisView.getRokWpisu());
                    kal.setMc(mce);
                    kal.setUmowa(wpisView.getUmowa());
                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mce);
                    if (kalmiesiac==null) {
                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getUmowa().getAngaz().getFirma(), kal.getRok(), mce);
                        if (znaleziono!=null) {
                            kal.ganerujdnizwzrocowego(znaleziono, dzienod);
                            kalendarzmiesiacFacade.create(kal);
                            dzienod = null;
                        } else {
                            Msg.msg("e","Brak kalendarza wzorcowego za "+mce);
                        }
                    }
                }
            }
            Msg.msg("Pobrano dane z kalendarza wzorcowego z bazy danych i utworzono kalendarze pracownika");
        } else {
            Msg.msg("e","Nie wybrano pracownika i umowy");
        }
    }
    
    public void edit() {
      if (selected!=null && selected.getId()!=null) {
          try {
            umowaFacade.edit(selected);
            wpisView.setUmowa(selected);
            selected = new Umowa();
            Msg.msg("Edycja umowy zakończona");
          } catch (Exception e) {
              System.out.println("");
              Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
          }
      }
    }
    
    public void aktywuj() {
        if (selectedlista!=null) {
            wpisView.setUmowa(selectedlista);
            wpisView.setFirma(selectedlista.getAngaz().getFirma());
            wpisView.setPracownik(selectedlista.getAngaz().getPracownik());
            Msg.msg("Aktywowano umowę");
        }
    }
    
    public void ustawumowe() {
        if (selected.getUmowakodzus() != null) {
            if (selected.getUmowakodzus().getKod().equals("0110")) {
                selected.setNrkolejny("UP/"+wpisView.getPracownik().getPesel()+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setCzastrwania("umowa na okres próbny");
                selected.setDataod("2020-01-01");
                selected.setDatanfz("2020-01-01");
                selected.setDataspoleczne("2020-01-01");
                selected.setDatazawarcia("2020-01-01");
                selected.setDatazdrowotne("2020-01-01");
                selected.setKosztyuzyskania(250.0);
                selected.setNfz("13");
                selected.setKodzawodu(KodzawoduBean.create());
                selected.setOdliczaculgepodatkowa(true);
            } else if (selected.getUmowakodzus().getKod().equals("0410")) {
                selected.setNrkolejny("UZ/"+wpisView.getPracownik().getPesel()+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(false);
                selected.setChorobowedobrowolne(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setDataod("2020-04-01");
                selected.setDatanfz("2020-04-01");
                selected.setDataspoleczne("2020-04-01");
                selected.setDatazawarcia("2020-04-01");
                selected.setDatazdrowotne("2020-04-01");
                selected.setKosztyuzyskania(0.0);
                selected.setNfz("13");
                selected.setKodzawodu(KodzawoduBean.create());
        }
        }
    }
    
    public void usun(Umowa umowa) {
        if (umowa!=null) {
            wpisView.setUmowa(null);
            umowaFacade.remove(umowa);
            lista.remove(umowa);
            Msg.msg("Usunięto umowę");
        } else {
            Msg.msg("e","Nie wybrano umowy");
        }
    }
    
    public void edytuj(Umowa umowa) {
        if (umowa!=null) {
            selected = umowa;
            Msg.msg("Wybrano umowę do edycji");
        } else {
            Msg.msg("e","Nie wybrano umowy");
        }
    }
    
    public void dodatetat() {
        if (etat!=null&&etat.getDataod()!=null) {
            etatFacade.create(etat);
            selected.getEtatList().add(etat);
            umowaFacade.edit(selected);
        }
    }
    
    public void naniesdatynaumowe() {
        if (selected!=null && selected.getDataod()!=null) {
            selected.setDatanfz(selected.getDataod());
            selected.setDataspoleczne(selected.getDataod());
            selected.setDatazdrowotne(selected.getDataod());
            selected.setDatazawarcia(selected.getDataod());
        }
    }
    
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;

    private Skladnikwynagrodzenia dodajskladnikwynagrodzenia() {
        Skladnikwynagrodzenia skladnikwynagrodzenia = new Skladnikwynagrodzenia();
        skladnikwynagrodzenia.setUmowa(selected);
        skladnikwynagrodzenia.setRodzajwynagrodzenia(rodzajwynagrodzeniaFacade.findZasadnicze());
        try {
            skladnikWynagrodzeniaFacade.create(skladnikwynagrodzenia);
        } catch (Exception e){}
        return skladnikwynagrodzenia;
    }
    
      private Zmiennawynagrodzenia dodajzmiennawynagrodzenie(Skladnikwynagrodzenia skladnikwynagrodzenia) {
        Zmiennawynagrodzenia zmiennawynagrodzenia = new Zmiennawynagrodzenia();
        if (skladnikwynagrodzenia.getId()!=null) {
            zmiennawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            zmiennawynagrodzenia.setWaluta("PLN");
            zmiennawynagrodzenia.setNazwa("zasadnicze");
            zmiennawynagrodzenia.setKwota(wynagrodzemieskrot);
            zmiennawynagrodzenia.setDataod(selected.getDataod());
        }
        try {
            zmiennaWynagrodzeniaFacade.create(zmiennawynagrodzenia);
        } catch (Exception e){}
        return zmiennawynagrodzenia;
    }
    
    public void oznaczjakoaktywna() {
        if (selectedlista!=null) {
            for (Umowa p : lista) {
                p.setAktywna(false);
            }
            umowaFacade.editList(lista);
            selectedlista.setAktywna(true);
            umowaFacade.edit(selectedlista);
            Msg.msg("Oznaczono umowę");
        }
    }
    
    public Umowa getSelected() {
        return selected;
    }

    public void setSelected(Umowa selected) {
        this.selected = selected;
    }

    public List<Umowa> getLista() {
        return lista;
    }

    public void setLista(List<Umowa> lista) {
        this.lista = lista;
    }

    public Umowa getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Umowa selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Angaz> getListaangaz() {
        return listaangaz;
    }

    public void setListaangaz(List<Angaz> listaangaz) {
        this.listaangaz = listaangaz;
    }

    public List<Umowakodzus> getListaumowakodzus() {
        return listaumowakodzus;
    }

    public void setListaumowakodzus(List<Umowakodzus> listaumowakodzus) {
        this.listaumowakodzus = listaumowakodzus;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public List<Kodyzawodow> getListakodyzawodow() {
        return listakodyzawodow;
    }

    public void setListakodyzawodow(List<Kodyzawodow> listakodyzawodow) {
        this.listakodyzawodow = listakodyzawodow;
    }

    public double getWynagrodzemieskrot() {
        return wynagrodzemieskrot;
    }

    public void setWynagrodzemieskrot(double wynagrodzemieskrot) {
        this.wynagrodzemieskrot = wynagrodzemieskrot;
    }

  
    


   
      
    
    
}
