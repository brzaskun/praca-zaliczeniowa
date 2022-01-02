/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import DAOsuperplace.OsobaPropTypFacade;
import DAOsuperplace.WynKodTytFacade;
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
import dao.RodzajwynagrodzeniaFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.StanowiskopracFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Kodyzawodow;
import entity.Kombinacjaubezpieczen;
import entity.Nieobecnosc;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Stanowiskoprac;
import entity.Umowa;
import entity.Umowakodzus;
import entity.Zmiennapotracenia;
import entity.Zmiennawynagrodzenia;
import error.E;
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
import kadryiplace.OsobaPropTyp;
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
    @Inject
    private EtatPrac etat;
    private List<Umowa> lista;
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
        if (wpisView.getUmowa()!=null) {
            if (wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajumowy = "1";
            } else if (wpisView.getUmowa().getUmowakodzus().isZlecenie()) {
                rodzajumowy = "2";
            } else {
                rodzajumowy = "3";
            }
            if (rodzajumowy.equals("1")) {
                lista  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywnePraca();
            } else if (rodzajumowy.equals("2")) {
                lista  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneZlecenie();
            } else {
                lista  = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
                listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneFunkcja();
            }
        } else {
            lista = new ArrayList<>();
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
        if (lista!=null&&lista.size()>0&&wpisView.getUmowa()!=null&&!lista.contains(wpisView.getUmowa())) {
            wpisView.setUmowa(lista.get(lista.size()-1));
        }
    }
    
    public void wyborinnejumowy() {
        if (rodzajumowy==null) {
            rodzajumowy = "1";
        }
        if (rodzajumowy.equals("1")) {
            lista  = umowaFacade.findByAngazPraca(wpisView.getAngaz());
            if (lista!=null&&lista.size()>0) {
                Umowa aktywna = lista.stream().filter(p->p.isAktywna()).findAny().orElse(lista.get(0));
                if (aktywna.isAktywna()==false) {
                    aktywna.setAktywna(true);
                    umowaFacade.edit(aktywna);
                }
                wpisView.setUmowa(aktywna);
            } else {
                wpisView.setUmowa(null);
            }
        } else if (rodzajumowy.equals("2")) {
            lista  = umowaFacade.findByAngazZlecenie(wpisView.getAngaz());
            if (lista!=null&&lista.size()>0) {
                Umowa aktywna = lista.stream().filter(p->p.isAktywna()).findAny().orElse(lista.get(0));
                if (aktywna.isAktywna()==false) {
                    aktywna.setAktywna(true);
                    umowaFacade.edit(aktywna);
                }
                wpisView.setUmowa(aktywna);
            } else {
                wpisView.setUmowa(null);
            }
        } else  {
            lista  = umowaFacade.findByAngazFunkcja(wpisView.getAngaz());
            if (lista!=null&&lista.size()>0) {
                Umowa aktywna = lista.stream().filter(p->p.isAktywna()).findAny().orElse(lista.get(0));
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
            if (lista!=null&&!lista.isEmpty()) {
                for (Umowa p : lista) {
                    p.setAktywna(false);
                }
                umowaFacade.editList(lista);
            }
            lista.add(selected);
            wpisView.setUmowa(selected);
            if (selected.getUmowakodzus().isPraca() && etat1!=null && etat2!=null) {
                EtatPrac etat = new EtatPrac(selected, etat1, etat2);
                etatFacade.create(etat);
                selected.getEtatList().add(etat);
            }
            if (selected.getUmowakodzus().isPraca() && selected.getKodzawodu()!=null) {
                Stanowiskoprac stanowisko = new Stanowiskoprac(selected);
                stanowiskopracFacade.create(stanowisko);
                selected.getStanowiskopracList().add(stanowisko);
            }
            Msg.msg("Dodano nową umowę");
            Kalendarzmiesiac kalendarz = generujKalendarzNowaUmowa();
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
              List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.generuj(kalendarz.getUmowa(),nieobecnosckodzusFacade, kalendarz.getRok(), kalendarz.getMc(), kalendarz);
              if (zatrudnieniewtrakciemiesiaca!=null) {
                nieobecnoscFacade.createList(zatrudnieniewtrakciemiesiaca);
              }
            }
            selected = new Umowa();
            wynagrodzeniemiesieczne = 0.0;
            etat1 = null;
            etat2 = null;
            updateClassView.updateUmowa();
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej umowy. Sprawdź angaż");
          }
      }
    }
    
    
    public Kalendarzmiesiac generujKalendarzNowaUmowa() {
        Kalendarzmiesiac kal = null;
        if (wpisView.getAngaz()!=null && wpisView.getPracownik()!=null && wpisView.getUmowa()!=null) {
            String rok = Data.getRok(wpisView.getUmowa().getDataod());
            Integer mcod = Integer.parseInt(Data.getMc(wpisView.getUmowa().getDataod()));
            Integer dzienod = Integer.parseInt(Data.getDzien(wpisView.getUmowa().getDataod()));
            for (String mc: Mce.getMceListS()) {
                Integer kolejnymc = Integer.parseInt(mc);
                if (kolejnymc>=mcod) {
                    kal = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), rok, mc);
                    if (kal==null) {
                        Kalendarzwzor znaleziono = kalendarzwzorFacade.findByFirmaRokMc(wpisView.getUmowa().getAngaz().getFirma(), rok, mc);
                        if (znaleziono!=null) {
                            kal = new Kalendarzmiesiac();
                            kal.setRok(rok);
                            kal.setMc(mc);
                            kal.setUmowa(wpisView.getUmowa());
                            kal.ganerujdnizwzrocowego(znaleziono, dzienod, wpisView.getUmowa().getEtatList());
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
        return kal;
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
            if (selected.getEtatList()!=null&&selected.getEtatList().size()==1) {
                EtatPrac etat = selected.getEtatList().get(0);
                etat.setDataod(selected.getDataod());
                etat.setDatado(selected.getDatado());
            }
            if (selected.getSkladnikwynagrodzeniaList()!=null) {
                for (Skladnikwynagrodzenia p : selected.getSkladnikwynagrodzeniaList()) {
                    if (p.getZmiennawynagrodzeniaList()!=null&&p.getZmiennawynagrodzeniaList().size()==1) {
                        Zmiennawynagrodzenia zmienna = p.getZmiennawynagrodzeniaList().get(0);
                        zmienna.setDataod(selected.getDataod());
                        zmienna.setDatado(selected.getDatado());
                    }
                }
            }
            if (selected.getSkladnikpotraceniaList()!=null) {
                for (Skladnikpotracenia p : selected.getSkladnikpotraceniaList()) {
                    if (p.getZmiennapotraceniaList()!=null&&p.getZmiennapotraceniaList().size()==1) {
                        Zmiennapotracenia zmienna = p.getZmiennapotraceniaList().get(0);
                        zmienna.setDataod(selected.getDataod());
                        zmienna.setDatado(selected.getDatado());
                    }
                }
            }
            selected.setDatasystem(new Date());
            umowaFacade.edit(selected);
            wpisView.setUmowa(selected);
            generujKalendarzNowaUmowa();
            String rok = Data.getRok(selected.getDataod());
            String mc = Data.getMc(selected.getDataod());
            Kalendarzmiesiac kalendarz = selected.getKalendarzmiesiacList().stream().filter(p->p.getRok().equals(rok)&&p.getMc().equals(mc)).findFirst().get();
            List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = PasekwynagrodzenBean.generuj(selected,nieobecnosckodzusFacade, rok, mc, kalendarz);
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
            if (selected.getSlownikszkolazatrhistoria().getSymbol().equals("P")) {
                Msg.msg("e","Wybrano umowę na czas nieokreślony a wprowadzono datę do!");
            }
        }
    }
    
    public void nanieskosztynaumowe() {
        if (selected!=null) {
            if (selected.isKosztyuzyskania0podwyzszone1()) {
                selected.setKosztyuzyskaniaprocent(100);
            } else {
                selected.setKosztyuzyskaniaprocent(120);
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
      
    
    public void rob() {
        List<OsobaPropTyp> lista = osobaPropTypFacade.findAll();
        for (OsobaPropTyp p : lista) {
            Kombinacjaubezpieczen k = new Kombinacjaubezpieczen(p);
            try {
                kombinacjaubezpieczenFacade.create(k);
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
}
