/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.OsobaPropTypFacade;
import DAOsuperplace.WynKodTytFacade;
import beanstesty.NieobecnosciBean;
import beanstesty.PasekwynagrodzenBean;
import beanstesty.UmowaBean;
import dao.AngazFacade;
import dao.DzienFacade;
import dao.EtatPracFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.KodyzawodowFacade;
import dao.KombinacjaubezpieczenFacade;
import dao.NieobecnoscFacade;
import dao.RodzajnieobecnosciFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.StanowiskopracFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Kodyzawodow;
import entity.Nieobecnosc;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikwynagrodzenia;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennawynagrodzenia;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.WynKodTyt;
import msg.Msg;
import pdf.PdfUmowaoPrace;

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
    private List<Umowa> wybraneumowy;
    @Inject
    private EtatPrac etat;
    private List<Umowa> listapraca;
    private List<Umowa> listazlecenia;
    private List<Umowa> listafunkcja;
    private List<Umowa> listawypowiedzenia;
    private List<Angaz> listaangaz;
    private List<Umowakodzus> listaumowakodzus;
    private List<Kodyzawodow> listakodyzawodow;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private DzienFacade dzienFacade;
    @Inject
    private KodyzawodowFacade kodyzawodowFacade;
    @Inject
    private EtatPracFacade etatFacade;
    @Inject
    private StanowiskopracFacade stanowiskopracFacade;
    @Inject
    private UmowakodzusFacade rodzajumowyFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private KombinacjaubezpieczenFacade kombinacjaubezpieczenFacade;
    @Inject
    private OsobaPropTypFacade osobaPropTypFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    @Inject
    private PasekwynagrodzenView pasekwynagrodzenView;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private UpdateClassView updateClassView;
    private double wynagrodzeniemiesieczne;
    private double wynagrodzeniegodzinowe;
    private Integer etat1;
    private Integer etat2;
    private String datadzisiejsza;
    private String miejscowosc;
    private String rodzajumowy;
    private int tabView;
    
    @PostConstruct
    public void init() {
        if (wpisView.getUmowa()!=null) {
            if (wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajumowy = "1";
            } else if (wpisView.getUmowa().getUmowakodzus().isZlecenie()) {
                rodzajumowy = "2";
            } else {
                rodzajumowy = "3";
            }
            listapraca  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
            listawypowiedzenia = umowaFacade.findByAngazPraca(wpisView.getAngaz());
            listazlecenia  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
            listafunkcja  = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
            if (rodzajumowy.equals("1")) {
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywnePraca();
            } else if (rodzajumowy.equals("2")) {
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneZlecenie();
            } else {
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneFunkcja();
            }
        } else {
            rodzajumowy = "1";
            listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywnePraca();
            listapraca = new ArrayList<>();
            listawypowiedzenia = new ArrayList<>();
        }
        //to psuje zmiane pracownika jak ma tyylko umowy zlecenia
//        if (rodzajumowy==null) {
//            rodzajumowy = "1";
//        }
        
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        
        listakodyzawodow = kodyzawodowFacade.findAll();
        datadzisiejsza = Data.aktualnaData();
        miejscowosc = wpisView.getFirma().getMiasto();
        selected = new Umowa();
        if (listapraca!=null&&listapraca.size()>0&&wpisView.getUmowa()!=null&&!listapraca.contains(wpisView.getUmowa())) {
            wpisView.setUmowa(listapraca.get(listapraca.size()-1));
        }
        pasekwynagrodzenView.setSymulacjabrrutto(wpisView.getRokWpisuInt()<2022?2800:3010);
        pasekwynagrodzenView.symulacjaoblicz("1");
    }
    
    public void wyborinnejumowy() {
        if (rodzajumowy==null) {
            rodzajumowy = "1";
        }
        if (rodzajumowy.equals("1")) {
            listapraca  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
            if (listapraca!=null&&listapraca.size()>0) {
                Umowa aktywna = listapraca.stream().filter(p->p.isAktywna()).findAny().orElse(listapraca.get(0));
                if (aktywna.isAktywna()==false) {
                    aktywna.setAktywna(true);
                    umowaFacade.edit(aktywna);
                }
                wpisView.setUmowa(aktywna);
            } else {
                wpisView.setUmowa(null);
            }
        } else if (rodzajumowy.equals("2")) {
            listapraca  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
            if (listapraca!=null&&listapraca.size()>0) {
                Umowa aktywna = listapraca.stream().filter(p->p.isAktywna()).findAny().orElse(listapraca.get(0));
                if (aktywna.isAktywna()==false) {
                    aktywna.setAktywna(true);
                    umowaFacade.edit(aktywna);
                }
                wpisView.setUmowa(aktywna);
            } else {
                wpisView.setUmowa(null);
            }
        } else  {
            listapraca  = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
            if (listapraca!=null&&listapraca.size()>0) {
                Umowa aktywna = listapraca.stream().filter(p->p.isAktywna()).findAny().orElse(listapraca.get(0));
                if (aktywna.isAktywna()==false) {
                    aktywna.setAktywna(true);
                    umowaFacade.edit(aktywna);
                }
                wpisView.setUmowa(aktywna);
            } else {
                wpisView.setUmowa(null);
            }
        }
        listaangaz = angazFacade.findByFirma(wpisView.getFirma());
        listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywne();
        listakodyzawodow = kodyzawodowFacade.findAll();
       datadzisiejsza = Data.aktualnaData();
       miejscowosc = wpisView.getFirma().getMiasto();
       updateClassView.updateUmowa();
    }
    
    public void wyborinnejumowy2() {
        if (rodzajumowy==null) {
            rodzajumowy = "1";
        }
        if (rodzajumowy.equals("1")) {
            listawypowiedzenia  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
        } else if (rodzajumowy.equals("2")) {
            listawypowiedzenia  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
        } else  {
            listawypowiedzenia  = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
        }
    }
    
     
    public void create() {
      if (selected!=null && wpisView.getAngaz()!=null) {
          try {
            selected.setAngaz(wpisView.getAngaz());
            if (selected.getUmowakodzus().isPraca()) {
                String dataodkiedywyplatazasilku = UmowaBean.obliczdatepierwszegozasilku(wpisView.getAngaz().getUmowaList(), selected);
                selected.setPierwszydzienzasilku(dataodkiedywyplatazasilku);
            }
            selected.setAktywna(true);
            selected.setDatasystem(new Date());
            umowaFacade.create(selected);
            if (listapraca!=null&&!listapraca.isEmpty()) {
                for (Umowa p : listapraca) {
                    p.setAktywna(false);
                }
                umowaFacade.editList(listapraca);
            }
            listapraca.add(selected);
            wpisView.setUmowa(selected);
            if (selected.getUmowakodzus().isPraca() && etat1!=null && etat2!=null) {
                EtatPrac etat = new EtatPrac(wpisView.getAngaz(), selected.getDataod(), selected.getDatado(), etat1, etat2);
                etatFacade.create(etat);
            }
            if (selected.getUmowakodzus().isPraca() && selected.getKodzawodu()!=null) {
                Stanowiskoprac stanowisko = new Stanowiskoprac(wpisView.getAngaz(), selected.getDataod(), selected.getDatado(), selected.getStanowisko());
                stanowiskopracFacade.create(stanowisko);
            }
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
              Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcAngaz(selected.getAngaz(), selected.getRok(), selected.getMc());
              List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.generuj(kalendarz.getAngaz(),selected.getDataod(), selected.getDatado(),rodzajnieobecnosciFacade, kalendarz.getRok(), kalendarz.getMc(), kalendarz);
              if (zatrudnieniewtrakciemiesiaca!=null) {
                nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
                boolean czynaniesiono = NieobecnosciBean.nanies(zatrudnieniewtrakciemiesiaca.get(0), wpisView.getRokWpisu(), wpisView.getRokUprzedni(), kalendarzmiesiacFacade, nieobecnoscFacade);
                if (czynaniesiono==false) {
                    Msg.msg("e", "Wystąpił błąd podczas nanoszenia rozpoczęcia umowy");
                }
              }
            }
            selected = new Umowa();
            wynagrodzeniemiesieczne = 0.0;
            etat1 = null;
            etat2 = null;
            updateClassView.updateUmowa();
            Msg.msg("Dodano nową umowę");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
          }
      }
    }
    
    
    
    
    public void edytujumowe(Umowa umowa) {
        try {
            if (umowa.getId()!=null) {
                umowaFacade.edit(umowa);
                Msg.msg("Naniesiono zmiany");
            }
        } catch (Exception e){}
    }
    
    public void edit() {
      if (selected!=null && selected.getId()!=null) {
          try {
            if (selected.getDataod()!=null) {
                List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = nieobecnoscFacade.findByUmowa200(selected);
                if (zatrudnieniewtrakciemiesiaca!=null) {
                    for (Nieobecnosc p : zatrudnieniewtrakciemiesiaca) {
                        List<Dzien> dzienList = p.getDzienList();
                        List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(p.getDataod(), p.getDatado(), wpisView.getFirma());
                        for (Dzien d : dzienList) {
                            d.setNieobecnosc(null);
                            dzienFacade.edit(d);
                            try {
                                d.nanieswzorcowe(wzorcowe);
                            } catch (Exception e){}
                            dzienFacade.edit(d);
                        }
                        p.setDzienList(null);
                        nieobecnoscFacade.edit(p);
                    }
                    nieobecnoscFacade.removeList(zatrudnieniewtrakciemiesiaca);
                    zatrudnieniewtrakciemiesiaca = null;
                }
            }
            selected.setDatasystem(new Date());
            umowaFacade.edit(selected);
            wpisView.setUmowa(selected);
            String rok = Data.getRok(selected.getDataod());
            String mc = Data.getMc(selected.getDataod());
            Kalendarzmiesiac kalendarz = selected.getAngaz().getKalendarzmiesiacList().stream().filter(p->p.getRok().equals(rok)&&p.getMc().equals(mc)).findFirst().get();
            List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.generuj(selected.getAngaz(),selected.getDataod(), selected.getDatado(),rodzajnieobecnosciFacade, rok, mc, kalendarz);
            if (zatrudnieniewtrakciemiesiaca!=null) {
              nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
            }
            selected = new Umowa();
            Msg.msg("Edycja umowy zakończona");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie zmieniono danych nowej umowy. Sprawdź angaż.");
              Msg.msg("e", "Czy istnieją już listy płac? Usuń je.");
          }
      }
    }
    
    public void obliczwiek() {
        String zwrot = "";
        if (selected!=null&&selected.getDataod()!=null) {
            String dataurodzenia = wpisView.getAngaz().getPracownik().getDataurodzenia();
            LocalDate dataur = LocalDate.parse(dataurodzenia);
            LocalDate dataumowy = LocalDate.parse(selected.getDataod());
            String rok = Data.getRok(selected.getDataod());
            String pierwszydzienroku = rok+"-01-01";
            LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
            long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
            long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
            selected.setLata((int) lata);
            selected.setDni((int) dni);
        }
    }
    
    
    
    public void sprawdzczyumowajestnaczas() {
        if (selected.getDatado()!=null) {
            if (selected.getSlownikszkolazatrhistoria()!=null&&selected.getSlownikszkolazatrhistoria().getSymbol().equals("P")) {
                Msg.msg("e","Wybrano umowę na czas nieokreślony a wprowadzono datę do!");
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
            updateClassView.updateUmowa();
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
                selected.setNfz("16");
            } else if (selected.getUmowakodzus().isZlecenie()) {
                selected.setNrkolejny("UC/"+numerkolejny+"/"+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu());
                selected.setChorobowe(false);
                selected.setChorobowedobrowolne(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setNfz("16");
            }
        }
    }
    
    public void usun(Umowa umowa) {
        if (umowa!=null) {
            wpisView.setUmowa(null);
            umowaFacade.remove(umowa);
            listapraca.remove(umowa);
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
    
//    public void dodatetat() {
//        if (etat!=null&&etat.getDataod()!=null) {
//            etatFacade.create(etat);
//            selected.getEtatList().add(etat);
//            umowaFacade.edit(selected);
//        }
//    }
//    
    public void naniesdatynaumowe() {
        if (selected!=null && selected.getDatazawarcia()!=null) {
            selected.setDataspoleczne(selected.getDatazawarcia());
            selected.setDatazdrowotne(selected.getDatazawarcia());
            selected.setDataod(selected.getDatazawarcia());
            selected.setTerminrozpoczeciapracy(selected.getDatazawarcia());
            obliczwiek();
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
        skladnikwynagrodzenia.setAngaz(selected.getAngaz());
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
            for (Umowa p : listapraca) {
                p.setAktywna(false);
            }
            umowaFacade.editList(listapraca);
            selectedlista.setAktywna(true);
            umowaFacade.edit(selectedlista);
            Msg.msg("Oznaczono umowę");
        }
    }
    
    public void przedluz() {
        if (selectedlista != null) {
            tabView=1;
            selected = new Umowa(selectedlista);
            Msg.msg("Przygotowano dane");
        } else {
            Msg.msg("e","Nie wybrano umowy");
        }
    }
    
    public void drukujumoweselected() {
        if (selected!=null) {
            PdfUmowaoPrace.drukuj(selected);
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

    public List<Umowa> getListapraca() {
        return listapraca;
    }

    public void setListapraca(List<Umowa> listapraca) {
        this.listapraca = listapraca;
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

    public List<Umowa> getWybraneumowy() {
        return wybraneumowy;
    }

    public void setWybraneumowy(List<Umowa> wybraneumowy) {
        this.wybraneumowy = wybraneumowy;
    }

    public int getTabView() {
        return tabView;
    }

    public void setTabView(int tabView) {
        this.tabView = tabView;
    }

    public List<Umowa> getListawypowiedzenia() {
        return listawypowiedzenia;
    }

    public void setListawypowiedzenia(List<Umowa> listawypowiedzenia) {
        this.listawypowiedzenia = listawypowiedzenia;
    }

    public List<Umowa> getListazlecenia() {
        return listazlecenia;
    }

    public void setListazlecenia(List<Umowa> listazlecenia) {
        this.listazlecenia = listazlecenia;
    }

    public List<Umowa> getListafunkcja() {
        return listafunkcja;
    }

    public void setListafunkcja(List<Umowa> listafunkcja) {
        this.listafunkcja = listafunkcja;
    }

    

    
    public static void main(String[] args) {
        String dataurodzenia = "1970-05-28";
        LocalDate dataur = LocalDate.parse(dataurodzenia);
        LocalDate dataumowy = LocalDate.parse("2021-11-14");
        String rok = Data.getRok("2021-11-14");
        String pierwszydzienroku = rok+"-01-01";
        LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
        long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
        long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
    }


    @Inject
    private WynKodTytFacade wynKodTytFacade;
    @Inject
    private UmowakodzusFacade umowakodzusFacade;
    public void generujtabele() {
        Msg.msg("Start");
        List<WynKodTyt> findAll = wynKodTytFacade.findAll();
        for (WynKodTyt p : findAll) {
            Umowakodzus s  = new Umowakodzus();
            s.setKod(p.getWktKod());
            s.setOpis(p.getWktOpis());
            s.setWkt_serial(p.getWktSerial());
            s.setPraca(p.getWktUmZlec().equals('N'));
            s.setZlecenie(p.getWktUmZlec().equals('T'));
            umowakodzusFacade.create(s);
        }
        Msg.dP();
    }
      
    
//    public void rob() {
//        List<OsobaPropTyp> listapraca = osobaPropTypFacade.findAll();
//        for (OsobaPropTyp p : listapraca) {
//            Kombinacjaubezpieczen k = new Kombinacjaubezpieczen(p);
//            try {
//                kombinacjaubezpieczenFacade.create(k);
//            } catch (Exception e) {
//                E.e(e);
//            }
//        }
//    }
    
}
