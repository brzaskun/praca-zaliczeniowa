/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.UmowaBean;
import dao.AngazFacade;
import dao.EtatPracFacade;
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
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Kodyzawodow;
import entity.Rodzajwynagrodzenia;
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
    private EtatPrac etat;
    private List<Umowa> lista;
    private List<Angaz> listaangaz;
    private List<Umowakodzus> listaumowakodzus;
    private List<Kodyzawodow> listakodyzawodow;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private KodyzawodowFacade kodyzawodowFacade;
    @Inject
    private EtatPracFacade etatFacade;
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
    @Inject
    private UpdateClassView updateClassView;
    private double wynagrodzeniemiesieczne;
    private double wynagrodzeniegodzinowe;
    private Integer etat1;
    private Integer etat2;
    private String datadzisiejsza;
    private String miejscowosc;
    private String rodzajumowy; 
    
    @PostConstruct
    public void init() {
        if (wpisView.getUmowa()!=null && rodzajumowy==null) {
            if (wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajumowy = "1";
            } else {
                rodzajumowy = "2";
            }
        }
        if (rodzajumowy==null) {
            rodzajumowy = "1";
        }
        if (rodzajumowy.equals("1")) {
            lista  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
        } else {
            lista  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
        }
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywne();
        listakodyzawodow = kodyzawodowFacade.findAll();
        datadzisiejsza = Data.aktualnaData();
        miejscowosc = wpisView.getFirma().getMiasto();
        if (!lista.contains(wpisView.getUmowa())) {
            wpisView.setUmowa(lista.get(lista.size()-1));
        }
    }
    
    public void wyborinnejumowy() {
        if (rodzajumowy==null) {
            rodzajumowy = "1";
        }
        if (rodzajumowy.equals("1")) {
            lista  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
        } else {
            lista  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
        }
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywne();
        listakodyzawodow = kodyzawodowFacade.findAll();
       datadzisiejsza = Data.aktualnaData();
       miejscowosc = wpisView.getFirma().getMiasto();
       updateClassView.updateUmowa();
    }
    
     
    public void create() {
      if (selected!=null && wpisView.getAngaz()!=null) {
          try {
            selected.setAngaz(wpisView.getAngaz());
            for (Umowa p : lista) {
                p.setAktywna(false);
            }
            String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(wpisView.getAngaz().getUmowaList(), selected);
            selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
            selected.setAktywna(true);
            umowaFacade.editList(lista);
            umowaFacade.create(selected);
            lista.add(selected);
            wpisView.setUmowa(selected);
            if (selected.getUmowakodzus().isPraca()) {
                EtatPrac etat = new EtatPrac(selected, etat1, etat2);
                etatFacade.create(etat);
            }
            Msg.msg("Dodano nową umowę");
            generujKalendarzNowaUmowa();
            if (wynagrodzeniemiesieczne!=0.0 || wynagrodzeniegodzinowe!=0.0){
              Rodzajwynagrodzenia rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findZasadniczePraca(): rodzajwynagrodzeniaFacade.findZasadniczeZlecenie();
              if (wynagrodzeniegodzinowe != 0.0) {
                  rodzajwynagrodzenia = selected.getUmowakodzus().isPraca() ? rodzajwynagrodzeniaFacade.findGodzinowePraca(): rodzajwynagrodzeniaFacade.findGodzinoweZlecenie();
              }
              Skladnikwynagrodzenia skladnikwynagrodzenia = dodajskladnikwynagrodzenia(rodzajwynagrodzenia);
              Zmiennawynagrodzenia zmiennawynagrodzenie = dodajzmiennawynagrodzenie(skladnikwynagrodzenia);
              if (skladnikwynagrodzenia.getId()!=null && zmiennawynagrodzenie!=null){
                  Msg.msg("Dodano składniki wynagrodzania");
              }
              skladnikWynagrodzeniaView.init();
              zmiennaWynagrodzeniaView.init();
            }
            selected = new Umowa();
            wynagrodzeniemiesieczne = 0.0;
            etat1 = null;
            etat2 = null;
            updateClassView.updateUmowa();
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
            for (String mc: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mc);
                if (kolejnymc>=mcod) {
                    Kalendarzmiesiac kal = new Kalendarzmiesiac();
                    kal.setRok(wpisView.getRokWpisu());
                    kal.setMc(mc);
                    kal.setUmowa(wpisView.getUmowa());
                    Kalendarzmiesiac kalmiesiac = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), mc);
                    if (kalmiesiac==null) {
                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(kal.getUmowa().getAngaz().getFirma(), kal.getRok(), mc);
                        if (znaleziono!=null) {
                            kal.ganerujdnizwzrocowego(znaleziono, dzienod);
                            kalendarzmiesiacFacade.create(kal);
                            dzienod = null;
                        } else {
                            Msg.msg("e","Brak kalendarza wzorcowego za "+mc);
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
    
    public void cancel() {
        try {
          wpisView.setUmowa(null);
          selected = new Umowa();
          Msg.msg("Reset umowy zakończony");
        } catch (Exception e) {
            Msg.msg("e", "Błąd - nie dodano resetu umowy.");
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
        int numerkolejny = 1;
        List<Umowa> umowaList = wpisView.getAngaz().getUmowaList();
        if (umowaList!=null) {
            numerkolejny = numerkolejny+umowaList.size();
        }
        if (selected.getId() == null) {
            if (selected.getUmowakodzus().isPraca()) {
                selected.setNrkolejny("UP/"+numerkolejny+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setKosztyuzyskaniaprocent(100.0);
                selected.setKwotawolnaprocent(100.0);
                selected.setNfz("16");
                selected.setOdliczaculgepodatkowa(true);
            } else if (selected.getUmowakodzus().isZlecenie()) {
                selected.setNrkolejny("UC/"+numerkolejny+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(false);
                selected.setChorobowedobrowolne(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setKosztyuzyskaniaprocent(20.0);
                selected.setKwotawolnaprocent(100.0);
                selected.setNfz("16");
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
            selected.setTerminrozpoczeciapracy(selected.getDataod());
        }
    }
    
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;

    private Skladnikwynagrodzenia dodajskladnikwynagrodzenia(Rodzajwynagrodzenia rodzajwynagrodzenia) {
        Skladnikwynagrodzenia skladnikwynagrodzenia = new Skladnikwynagrodzenia();
        skladnikwynagrodzenia.setUmowa(selected);
        skladnikwynagrodzenia.setRodzajwynagrodzenia(rodzajwynagrodzenia);
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
            if (wynagrodzeniemiesieczne!=0.0) {
                zmiennawynagrodzenia.setNazwa("miesięczne");
                zmiennawynagrodzenia.setKwota(wynagrodzeniemiesieczne);
            } else {
                zmiennawynagrodzenia.setNazwa("godzinowe");
                zmiennawynagrodzenia.setKwota(wynagrodzeniegodzinowe);
            }
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
    
    public void drukujumoweselected() {
        if (selected!=null) {
            
        } else {
            Msg.msg("e","Nie wybrano umowy");
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

    public EtatPrac getEtat() {
        return etat;
    }

    public void setEtat(EtatPrac etat) {
        this.etat = etat;
    }

    public List<Kodyzawodow> getListakodyzawodow() {
        return listakodyzawodow;
    }

    public void setListakodyzawodow(List<Kodyzawodow> listakodyzawodow) {
        this.listakodyzawodow = listakodyzawodow;
    }

    public double getWynagrodzeniemiesieczne() {
        return wynagrodzeniemiesieczne;
    }

    public void setWynagrodzeniemiesieczne(double wynagrodzeniemiesieczne) {
        this.wynagrodzeniemiesieczne = wynagrodzeniemiesieczne;
    }

    public Integer getEtat1() {
        return etat1;
    }

    public void setEtat1(Integer etat1) {
        this.etat1 = etat1;
    }

    public Integer getEtat2() {
        return etat2;
    }

    public void setEtat2(Integer etat2) {
        this.etat2 = etat2;
    }

    public String getDatadzisiejsza() {
        return datadzisiejsza;
    }

    public void setDatadzisiejsza(String datadzisiejsza) {
        this.datadzisiejsza = datadzisiejsza;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getRodzajumowy() {
        return rodzajumowy;
    }

    public void setRodzajumowy(String rodzajumowy) {
        this.rodzajumowy = rodzajumowy;
    }

    public double getWynagrodzeniegodzinowe() {
        return wynagrodzeniegodzinowe;
    }

    public void setWynagrodzeniegodzinowe(double wynagrodzeniegodzinowe) {
        this.wynagrodzeniegodzinowe = wynagrodzeniegodzinowe;
    }

    
    


   
      
    
    
}
