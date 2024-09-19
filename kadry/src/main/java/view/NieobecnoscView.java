/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.EtatBean;
import beanstesty.NieobecnosciBean;
import beanstesty.UrlopBean;
import comparator.Nieobecnosccomparator;
import comparator.NieobecnosccomparatorDU;
import comparator.Pracownikcomparator;
import comparator.Rodzajnieobecnoscicomparator;
import dao.AngazFacade;
import dao.DzienFacade;
import dao.EkwiwalentUrlopFacade;
import dao.FirmabaustelleFacade;
import dao.KalendarzmiesiacFacade;
import dao.NaliczenienieobecnoscFacade;
import dao.NieobecnoscFacade;
import dao.RejestrurlopowFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SwiadczeniekodzusFacade;
import dao.UmowaFacade;
import data.Data;
import entity.Angaz;
import entity.Dzien;
import entity.EkwiwalentUrlop;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Firmabaustelle;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Nieobecnosc;
import entity.Nieobecnoscprezentacja;
import entity.Pracownik;
import entity.Rejestrurlopow;
import entity.Rodzajnieobecnosci;
import entity.Swiadczeniekodzus;
import entity.Umowa;
import generated.RaportEzla;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.model.FileHeader;
import org.primefaces.model.DualListModel;
import pdf.PdfNieobecnosci;
import zuszla.PobierzRaporty;
import zuszla.PobierzRaportyResponse;
import zuszla.WsdlPlatnikRaportyZlaPortType;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class NieobecnoscView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Nieobecnosc selected;
    @Inject
    private Nieobecnosc selectedzbiorczo;
    private List<Nieobecnosc> selectedlista;
    private List<Nieobecnosc> lista;
    private List<Rodzajnieobecnosci> listaabsencji;
    private List<Umowa> listaumowa;
    private List<Swiadczeniekodzus> swiadczeniekodzusLista;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private SwiadczeniekodzusFacade swiadczeniekodzusFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private DzienFacade dzienFacade;
    @Inject
    private KalendarzmiesiacView kalendarzmiesiacView;
    @Inject
    private NaliczenienieobecnoscFacade naliczenienieobecnoscFacade;
    @Inject
    private RejestrurlopowFacade rejestrurlopowFacade;
    @Inject
    private EkwiwalentUrlopFacade ekwiwalentUrlopFacade;
    @Inject
    private WpisView wpisView;
    private List<Firmabaustelle> listabaustelle;
    @Inject
    private FirmabaustelleFacade firmabaustelleFacade;
    @WebServiceRef(wsdlLocation = "WEB-INF/wsdl/zuszla.wsdl")
    private zuszla.WsdlPlatnikRaportyZla wsdlPlatnikRaportyZla;
    private  boolean pokazcalyrok;
    private boolean bezoddelegowania;
    private org.primefaces.model.DualListModel<Pracownik> listapracownikow;
    private boolean delegacja;
    private boolean zwolnienie;
    private int dniwykorzystanewroku;
    private boolean naniesbezposrednio;
    private Nieobecnoscprezentacja urlopprezentacja;
    private boolean dialogOtwarty;
     private boolean dialogOtwartyzbiorcze;
    
    public void open() {
        dialogOtwarty = true;
    }
    public void close() {
        dialogOtwarty = false;
    }
    
    public void open1() {
        dialogOtwartyzbiorcze = true;
    }
    public void close1() {
        dialogOtwartyzbiorcze = false;
    }
    
    public void reloadDialog() {
        boolean zwrot = false;
        if (dialogOtwarty) {
            init();
        }
        if (dialogOtwartyzbiorcze) {
            initzbiorcze();
        }
    }

    

    
    @PostConstruct
    public void init0() {
        listapracownikow = new org.primefaces.model.DualListModel<>();
        listapracownikow.setSource(new ArrayList<>());
        listapracownikow.setTarget(new ArrayList<>());
        delegacja = false;
        zwolnienie = false;
        naniesbezposrednio = true;
    }
    
    public void init() {
        if (wpisView.getAngaz()!=null) {
            selected = new Nieobecnosc();
            lista  = nieobecnoscFacade.findByAngaz(wpisView.getAngaz());
            selected.setAngaz(wpisView.getAngaz());
            if (pokazcalyrok==false) {
                lista = lista.stream().filter(p->p.getRokod().equals(wpisView.getRokWpisu())||p.getRokdo().equals(wpisView.getRokWpisu())).collect(Collectors.toList());
            }
            if (bezoddelegowania) {
                lista = lista.stream().filter(p->p.getKod().equals("Z")==false).collect(Collectors.toList());
            }
        }
        listabaustelle = firmabaustelleFacade.findbyRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
        listaumowa = umowaFacade.findPracownik(wpisView.getPracownik());
        listaabsencji = rodzajnieobecnosciFacade.findAll();
        Collections.sort(listaabsencji, new Rodzajnieobecnoscicomparator());
        delegacja = false;
        zwolnienie = false;
        naniesbezposrednio = true;
        dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
//        if (dniwykorzystanewroku>=33) {
//            listaabsencji = listaabsencji.stream().filter(p->!p.getKod().equals("CH")).collect(Collectors.toList());
//        }
        Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
        String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
        String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
        Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
        EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
        urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
        Pracownik pracownik = angaznowy.getPracownik();
        pracownik.setWymiarurlopu(urlopprezentacja.getWymiargeneralnydni());
        angazFacade.edit(angaznowy);
        wpisView.setAngaz(angaznowy);
        wpisView.setPracownik(pracownik);
        //Collections.sort(listaabsencji, new Nieobecnoscikodzuscomparator());
    }
    
    public void initzbiorcze() {
        listaabsencji = rodzajnieobecnosciFacade.findAll();
        listabaustelle = firmabaustelleFacade.findbyRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
        Collections.sort(listaabsencji, new Rodzajnieobecnoscicomparator());
        listapracownikow = new org.primefaces.model.DualListModel<>();
        listapracownikow.setSource(new ArrayList<>());
        listapracownikow.setTarget(new ArrayList<>());
        List<Angaz> angazList = wpisView.getFirma().getAngazListAktywne();
        List<Pracownik> listapracownikow = pobierzpracownikow (angazList);
        Collections.sort(listapracownikow, new Pracownikcomparator());
        if (listapracownikow != null) {
            this.listapracownikow.setSource(listapracownikow);
            this.listapracownikow.setTarget(new ArrayList<>());
        }
        delegacja = false;
        zwolnienie = false;
    }

    public void sprawdzdaty() {
        if (lista!=null&&lista.size()>0) {
            String dataod = selected.getDataod();
            String datado = selected.getDatado();
            if (selected.getDataod()!=null&&selected.getDatado()!=null) {
                for (Nieobecnosc p : lista) {
                    try {
                        if (selected.getKod()!=null&&!selected.getKod().equals("UD")) {
                            if (selected.getId()==null || (selected.getId()!=null&&!selected.getId().equals(p.getId()))) {
                                boolean jestwsrodkuod = Data.czydatasiezawieranieobecnosc(dataod, p.getDataod(), p.getDatado());
                                boolean jestwsrodkudo = Data.czydatasiezawieranieobecnosc(datado, p.getDataod(), p.getDatado());
                                if (jestwsrodkuod||jestwsrodkudo) {
                                    Msg.msg("e","Daty nieobecności pokrywają się z inną już wprowadzoną nieobecnością");
                                    selected.setDataod(null);
                                    selected.setDatado(null);
                                    break;
                                }
                            }
                        }
                    } catch (Exception e){}
                }
            }
        }
    }
    
    public boolean sprawdzdaty(List<Nieobecnosc> lista, Nieobecnosc selected) {
        boolean duplikat = false;
        Collections.sort(lista, new Nieobecnosccomparator());
        if (lista!=null&&lista.size()>0) {
            String dataod = selected.getDataod();
            String datado = selected.getDatado();
            for (Nieobecnosc p : lista) {
                if (selected.getKod()!=null&&!selected.getKod().equals("UD")) {
                    boolean jestwsrodkuod = Data.czydatasiezawieranieobecnosc(dataod, p.getDataod(), p.getDatado());
                    boolean jestwsrodkudo = Data.czydatasiezawieranieobecnosc(datado, p.getDataod(), p.getDatado());
                    if (jestwsrodkuod||jestwsrodkudo) {
                        duplikat = true;
                        break;
                    }
                }
            }
        }
        return duplikat;
    }
    //pobiera tylko aktywnych w danym miesiacu umowy 26.12.2023 bielsko-biala :)
    private List<Pracownik> pobierzpracownikow(List<Angaz> angazList) {
        Set<Pracownik> zwrot = new HashSet<>();
        for (Angaz a : angazList) {
            if (a.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu())==true) {
                zwrot.add(a.getPracownik());
            }
        }
        
        return new ArrayList<Pracownik>(zwrot);
    }
    
    public void kasuj() {
      if (selected!=null) {
          selected = new Nieobecnosc();
          selected.setAngaz(wpisView.getAngaz());
      }
    }
    public void create() {
      if (selected!=null) {
          if (selected.getRodzajnieobecnosci()==null || selected.getDataod()==null || selected.getDatado()==null) {
              Msg.msg("e", "Brak kodu/dat");
          } else {
            try {
              selected.setAngaz(wpisView.getAngaz());
              boolean wygenerowano = false;
              if (selected.getId()==null&&selected.getRodzajnieobecnosci().getKod().equals("CA")) {
                double dnizezwolnienia = selected.getDnikalendarzoweOblicz();
                double dniwyplacone = dniwykorzystanewroku;
                double limit = wyliczlimitzewzgledunawiek(wpisView.getPracownik().getDataurodzenia(), selected.getDataod());
                double dorozliczenia = limit-dniwyplacone;
                double rozliczono = dorozliczenia-dnizezwolnienia;
                if (dniwyplacone>=33||dorozliczenia<0) {
                    double dowyplatyjakoZC = dnizezwolnienia;
                    Msg.msg("Tylko Zasiłek chorobowy "+dowyplatyjakoZC);
                    String dataodCH = selected.getDataod();
                    String datadoCH = selected.getDatado();
                    Nieobecnosc selectedCH = new Nieobecnosc();
                    selectedCH.setAngaz(selected.getAngaz());
                    selectedCH.setRokod(Data.getRok(dataodCH));
                    selectedCH.setRokdo(Data.getRok(datadoCH));
                    selectedCH.setMcod(Data.getMc(dataodCH));
                    selectedCH.setMcdo(Data.getMc(datadoCH));
                    selectedCH.setUzasadnienie(selected.getUzasadnienie());
                    selectedCH.setSeriainrzwolnienia(selected.getSeriainrzwolnienia());
                    selectedCH.setZwolnienieprocent(selected.getZwolnienieprocent());
                    selectedCH.setSredniazmiennerecznie(selected.getSredniazmiennerecznie());
                    LocalDate oddata = LocalDate.parse(dataodCH);
                    LocalDate dodata = LocalDate.parse(datadoCH);
                    double iloscdni = DAYS.between(oddata,dodata);
                    selectedCH.setDnikalendarzowe(iloscdni+1.0);
                    Rodzajnieobecnosci nieobecnoscCH = listaabsencji.stream().filter(p->p.getKod().equals("ZC")).findFirst().get();
                    selectedCH.setDataod(dataodCH);
                    selectedCH.setDatado(datadoCH);
                    selectedCH.setRodzajnieobecnosci(nieobecnoscCH);
                    selectedCH.setSwiadczeniekodzus(nieobecnoscCH.getSwiadczeniekodzusList().stream().filter(pr->pr.getKod().equals("313")).findFirst().get());
                    selectedCH.setDatadodania(new Date());
                    selectedCH.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                    nieobecnoscFacade.create(selectedCH);
                    lista.add(selectedCH);
                    Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
                    dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(angaznowy, wpisView.getRokWpisu()));
                    Msg.msg("Dodano nieobecność");
                } else if (rozliczono>=0) {
                    double dowyplatyjakoCH = dnizezwolnienia;
                    Msg.msg("Tylko Wynagrodzenie chorobowe "+dowyplatyjakoCH);
                    String dataodCH = selected.getDataod();
                    String datadoCH = selected.getDatado();
                    Nieobecnosc selectedCH = new Nieobecnosc();
                    selectedCH.setAngaz(selected.getAngaz());
                    selectedCH.setRokod(Data.getRok(dataodCH));
                    selectedCH.setRokdo(Data.getRok(datadoCH));
                    selectedCH.setMcod(Data.getMc(dataodCH));
                    selectedCH.setMcdo(Data.getMc(datadoCH));
                    selectedCH.setUzasadnienie(selected.getUzasadnienie());
                    selectedCH.setSeriainrzwolnienia(selected.getSeriainrzwolnienia());
                    selectedCH.setZwolnienieprocent(selected.getZwolnienieprocent());
                    selectedCH.setSredniazmiennerecznie(selected.getSredniazmiennerecznie());
                    LocalDate oddata = LocalDate.parse(dataodCH);
                    LocalDate dodata = LocalDate.parse(datadoCH);
                    double iloscdni = DAYS.between(oddata,dodata);
                    selectedCH.setDnikalendarzowe(iloscdni+1.0);
                    Rodzajnieobecnosci nieobecnoscCH = listaabsencji.stream().filter(p->p.getKod().equals("CH")).findFirst().get();
                    selectedCH.setDataod(dataodCH);
                    selectedCH.setDatado(datadoCH);
                    selectedCH.setRodzajnieobecnosci(nieobecnoscCH);
                    selectedCH.setSwiadczeniekodzus(nieobecnoscCH.getSwiadczeniekodzusList().stream().filter(pr->pr.getKod().equals("331")).findFirst().get());
                    nieobecnoscFacade.create(selectedCH);
                    lista.add(selectedCH);
                    Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
                    dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(angaznowy, wpisView.getRokWpisu()));
                    Msg.msg("Dodano nieobecność");
                } else if (rozliczono<0 && dorozliczenia>0) {
                    String dataodCH = selected.getDataod();
                    String datadoCH = Data.dodajdni(dataodCH, ((int) dorozliczenia)-1);
                    double dowyplatyjakoCH = dorozliczenia;
                    Msg.msg("Wynagrodzenie chorobowe "+dowyplatyjakoCH+" data "+datadoCH);
                    Nieobecnosc selectedCH = new Nieobecnosc();
                    selectedCH.setAngaz(selected.getAngaz());
                    selectedCH.setRokod(Data.getRok(dataodCH));
                    selectedCH.setRokdo(Data.getRok(datadoCH));
                    selectedCH.setMcod(Data.getMc(dataodCH));
                    selectedCH.setMcdo(Data.getMc(datadoCH));
                    selectedCH.setUzasadnienie(selected.getUzasadnienie());
                    selectedCH.setSeriainrzwolnienia(selected.getSeriainrzwolnienia());
                    selectedCH.setZwolnienieprocent(selected.getZwolnienieprocent());
                    selectedCH.setSredniazmiennerecznie(selected.getSredniazmiennerecznie());
                    LocalDate oddata = LocalDate.parse(dataodCH);
                    LocalDate dodata = LocalDate.parse(datadoCH);
                    double iloscdni = DAYS.between(oddata,dodata);
                    selectedCH.setDnikalendarzowe(iloscdni+1.0);
                    Rodzajnieobecnosci nieobecnoscCH = listaabsencji.stream().filter(p->p.getKod().equals("CH")).findFirst().get();
                    selectedCH.setDataod(dataodCH);
                    selectedCH.setDatado(datadoCH);
                    selectedCH.setRodzajnieobecnosci(nieobecnoscCH);
                    selectedCH.setSwiadczeniekodzus(nieobecnoscCH.getSwiadczeniekodzusList().stream().filter(pr->pr.getKod().equals("331")).findFirst().get());
                    selectedCH.setDatadodania(new Date());
                    nieobecnoscFacade.create(selectedCH);
                    lista.add(selectedCH);
                    Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
                    dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(angaznowy, wpisView.getRokWpisu()));
                    //*************
                    String dataodZC = Data.dodajdni(datadoCH, 1);
                    String datadoZC = Data.dodajdni(dataodZC, ((int) Math.abs(rozliczono)-1));
                    double dowyplatyjakoZC = Math.abs(rozliczono);
                    Msg.msg("Zasiłek chorobowy "+dowyplatyjakoZC+" data "+datadoZC);
                    Nieobecnosc selectedZC = new Nieobecnosc();
                    selectedZC.setAngaz(selected.getAngaz());
                    selectedZC.setRokod(Data.getRok(dataodZC));
                    selectedZC.setRokdo(Data.getRok(datadoZC));
                    selectedZC.setMcod(Data.getMc(dataodZC));
                    selectedZC.setMcdo(Data.getMc(datadoZC));
                    selectedZC.setUzasadnienie(selected.getUzasadnienie());
                    selectedZC.setSeriainrzwolnienia(selected.getSeriainrzwolnienia());
                    selectedZC.setZwolnienieprocent(selected.getZwolnienieprocent());
                    selectedZC.setSredniazmiennerecznie(selected.getSredniazmiennerecznie());
                    oddata = LocalDate.parse(dataodZC);
                    dodata = LocalDate.parse(datadoZC);
                    iloscdni = DAYS.between(oddata,dodata);
                    selectedZC.setDnikalendarzowe(iloscdni+1.0);
                    nieobecnoscCH = listaabsencji.stream().filter(p->p.getKod().equals("ZC")).findFirst().get();
                    selectedZC.setDataod(dataodZC);
                    selectedZC.setDatado(datadoZC);
                    selectedZC.setRodzajnieobecnosci(nieobecnoscCH);
                    selectedZC.setSwiadczeniekodzus(nieobecnoscCH.getSwiadczeniekodzusList().stream().filter(pr->pr.getKod().equals("313")).findFirst().get());
                    nieobecnoscFacade.create(selectedZC);
                    lista.add(selectedZC);
                    angaznowy = angazFacade.findById(wpisView.getAngaz());
                    dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(angaznowy, wpisView.getRokWpisu()));
                }
                
                Msg.msg("Choroba automat");
              } else if (selected.getId()==null&&!selected.getRodzajnieobecnosci().getKod().equals("CA")) {
                  selected.setRokod(Data.getRok(selected.getDataod()));
                  selected.setRokdo(Data.getRok(selected.getDatado()));
                  selected.setMcod(Data.getMc(selected.getDataod()));
                  selected.setMcdo(Data.getMc(selected.getDatado()));
                  LocalDate oddata = LocalDate.parse(selected.getDataod());
                  LocalDate dodata = LocalDate.parse(selected.getDatado());
                  double iloscdni = DAYS.between(oddata,dodata);
                  selected.setDnikalendarzowe(iloscdni+1.0);
                  selected.setDatadodania(new Date());
                  nieobecnoscFacade.create(selected);
                  lista.add(selected);
                  String stannadzien = data.Data.ostatniDzien(wpisView);
                  Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
                  String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
                  Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
                  EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
                  urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
                  dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
                  wygenerowano = true;
                  Msg.msg("Dodano nieobecność");
              } else if (selected.getId()!=null&&!selected.getRodzajnieobecnosci().getKod().equals("CA")) {
                  selected.setRokod(Data.getRok(selected.getDataod()));
                  selected.setRokdo(Data.getRok(selected.getDatado()));
                  selected.setMcod(Data.getMc(selected.getDataod()));
                  selected.setMcdo(Data.getMc(selected.getDatado()));
                  LocalDate oddata = LocalDate.parse(selected.getDataod());
                  LocalDate dodata = LocalDate.parse(selected.getDatado());
                  double iloscdni = DAYS.between(oddata,dodata);
                  selected.setDnikalendarzowe(iloscdni+1.0);
                  selected.setDatadodania(new Date());
                  nieobecnoscFacade.edit(selected);
                  wygenerowano = true;
                  Msg.msg("Edytowano nieobecność");
              }
              if (naniesbezposrednio && wygenerowano) {
                  nanies(selected);
                  String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
                  Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
                  String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
                  Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
                EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
                urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
                  dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
              }
              selected = new Nieobecnosc(wpisView.getAngaz());
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano nowej nieobecnosci");
            }
          }
      }
    }
    
    private double wyliczlimitzewzgledunawiek(String dataurodzenia, String datawyplaty) {
        double zwrot =33;
        if (dataurodzenia!=null&&datawyplaty!=null) {
            int wiek = Data.obliczwiekChoroba(dataurodzenia, datawyplaty);
            if (wiek >=50) {
                zwrot = 14;
            }
        }
        return zwrot;
    }

    
    public void createzbiorczo(FirmaKadry firma) {
      if (listapracownikow.getTarget()!=null) {
          int licznik = 0;
          List<Angaz> angazList = wpisView.getFirma().getAngazListAktywne();
          try {
            for (Pracownik p : listapracownikow.getTarget()) {
                try {
                    Angaz angazpracownika = pobierzangaz(p,angazList);
                    List<Nieobecnosc> nieobecnosci = nieobecnoscFacade.findByAngaz(angazpracownika);
                    boolean nierobic = sprawdzdaty(nieobecnosci, selectedzbiorczo);
                    if (nierobic == false) {
                        Nieobecnosc nowa = new Nieobecnosc();
                        Angaz angaz = angazFacade.findByFirmaPracownik(firma,p);
                        nowa.setAngaz(angaz);
                        nowa.setDataod(selectedzbiorczo.getDataod());
                        nowa.setDatado(selectedzbiorczo.getDatado());
                        nowa.setRokod(Data.getRok(selectedzbiorczo.getDataod()));
                        nowa.setRokdo(Data.getRok(selectedzbiorczo.getDatado()));
                        nowa.setMcod(Data.getMc(selectedzbiorczo.getDataod()));
                        nowa.setMcdo(Data.getMc(selectedzbiorczo.getDatado()));
                        nowa.setRodzajnieobecnosci(selectedzbiorczo.getRodzajnieobecnosci());
                        nowa.setKrajoddelegowania(selectedzbiorczo.getKrajoddelegowania());
                        nowa.setWalutadiety(selectedzbiorczo.getWalutadiety());
                        nowa.setDietaoddelegowanie(selectedzbiorczo.getDietaoddelegowanie());
                        nowa.setSwiadczeniekodzus(selectedzbiorczo.getSwiadczeniekodzus());
                        nowa.setKodzwolnienia(selectedzbiorczo.getKodzwolnienia());
                        nowa.setUzasadnienie(selectedzbiorczo.getUzasadnienie());
                        nowa.setPonpiatek(selectedzbiorczo.isPonpiatek());
                        nowa.setFirmabaustelle(selectedzbiorczo.getFirmabaustelle());
                        LocalDate oddata = LocalDate.parse(selectedzbiorczo.getDataod());
                        LocalDate dodata = LocalDate.parse(selectedzbiorczo.getDatado());
                        double iloscdni = DAYS.between(oddata,dodata);
                        nowa.setDnikalendarzowe(iloscdni+1.0);
                        nowa.setDatadodania(new Date());
                        nowa.setUtworzyl(wpisView.getUzer().getImieNazwisko());
                        nieobecnoscFacade.create(nowa);
                        NieobecnosciBean.nanies(nowa, kalendarzmiesiacFacade, nieobecnoscFacade);
                        licznik++;
                    } else {
                        Msg.msg("w","Nieobecność była już wprowadzona dla "+p.getNazwiskoImie());
                    }
                } catch (Exception e){
                    Msg.msg("w","Nieobecność była już wprowadzona dla "+p.getNazwiskoImie());
                }
            }
            selectedzbiorczo = new Nieobecnosc();
            initzbiorcze();
            Msg.msg("Dodano "+licznik+" nieobecności");
          } catch (Exception e) {
              Msg.msg("e", "Błąd - nie dodano nowej nieobecnosci ");
          }
      } else {
          Msg.msg("e","Nie wybrano pracowników");
      }
    }
    
    
     private Angaz pobierzangaz(Pracownik pracownik, List<Angaz> angazList) {
        Angaz zwrot = null;
        if (angazList!=null) {
            zwrot = angazList.stream().filter(p->p.getPracownik().equals(pracownik)).findFirst().get();
        }
        return zwrot;
    }
     
     public void drukujnieobecnosci() {
         if (selectedlista!=null) {
             PdfNieobecnosci.drukuj(selectedlista, wpisView.getAngaz(), wpisView.getRokWpisu());
         } else {
             Msg.msg("e","Nie wybrano pozycji do wydruku");
         }
     }
     
     public void drukujnieobecnoscide() {
         if (selectedlista!=null) {
             Collections.sort(lista, new NieobecnosccomparatorDU());
             PdfNieobecnosci.drukujde(lista, wpisView.getAngaz(), wpisView.getRokWpisu());
         } else {
             Msg.msg("e","Nie wybrano pozycji do wydruku");
         }
     }

    
    public void nieniesnakalendarz() {
        if (wpisView.getUmowa() != null) {
            boolean czynaniesiono = false;
            for (Nieobecnosc nieobecnosc : lista) {
                if (nieobecnosc.isNaniesiona()==false) {
                    czynaniesiono = nanies(nieobecnosc);
                }
            }
            kalendarzmiesiacView.init();
            String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
            Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
            String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
            Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
            EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
            urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
            if (czynaniesiono) {
                Msg.msg("Naniesiono nieobecnosci");
            } else {
                Msg.msg("e","Nie ma nieobecności do naniesienia");
            }
        }
    }
    
    public void naniesrodzajnieobecnosci() {
        if (selected.getRodzajnieobecnosci()!=null) {
            swiadczeniekodzusLista = swiadczeniekodzusFacade.findByRodzajnieobecnosciAktiv(selected.getRodzajnieobecnosci());
            if (selected.getRodzajnieobecnosci().getKodzbiorczy().equals("CH")) {
                selected.setZwolnienieprocent(80);
            } else {
                selected.setZwolnienieprocent(0);
            }
            zwolnienie = false;
            delegacja = false;
            if (selected.getRodzajnieobecnosci().getKod().equals("Z")) {
                selected.setKrajoddelegowania("Niemcy");
                selected.setWalutadiety("EUR");
                selected.setDietaoddelegowanie(49.0);
                delegacja = true;
            }
            if (selected.getRodzajnieobecnosci().getKodzbiorczy().equals("CH")) {
                zwolnienie = true;
            }
            if (swiadczeniekodzusLista.size()==1) {
                selected.setSwiadczeniekodzus(swiadczeniekodzusLista.get(0));
            }
            Msg.msg("Wybrano rodzaj nieobecności");
        }
    }
    
    public void naniesrodzajnieobecnoscizbiorcze() {
        if (selectedzbiorczo.getRodzajnieobecnosci()!=null) {
            swiadczeniekodzusLista = swiadczeniekodzusFacade.findByRodzajnieobecnosciAktiv(selectedzbiorczo.getRodzajnieobecnosci());
            zwolnienie = false;
            delegacja = false;
            if (selectedzbiorczo.getRodzajnieobecnosci().getKod().equals("Z")) {
                selectedzbiorczo.setKrajoddelegowania("Niemcy");
                selectedzbiorczo.setWalutadiety("EUR");
                selectedzbiorczo.setDietaoddelegowanie(49.0);
                delegacja = true;
            }
            if (selectedzbiorczo.getRodzajnieobecnosci().getKodzbiorczy().equals("CH")) {
                zwolnienie = true;
            }
            if (swiadczeniekodzusLista.size()==1) {
                selectedzbiorczo.setSwiadczeniekodzus(swiadczeniekodzusLista.get(0));
            }
            Msg.msg("Wybrano rodzaj nieobecności");
        }
    }
    
    public void naniesprocent() {
         if (selected.getSwiadczeniekodzus()!=null) {
            if (selected.getSwiadczeniekodzus().getRodzajnieobecnosci().getKodzbiorczy().equals("CH")) {
                selected.setZwolnienieprocent(80.0);
            }
        }
    }
    
    public void naniesprocentzbiorcze() {
         if (selectedzbiorczo.getSwiadczeniekodzus()!=null) {
            if (selectedzbiorczo.getSwiadczeniekodzus().getKod().equals("CH")) {
                selectedzbiorczo.setZwolnienieprocent(80.0);
            }
        }
    }
    
    public String kolornieobecnosci(Nieobecnosc nieobecnosc) {
        String zwrot = "initial";
        if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu())) {
            if (nieobecnosc.isNaniesiona()) {
                zwrot = "green";
            } else {
                zwrot = "red";
            }
        }
        if (nieobecnosc.isImportowana()) {
            zwrot = "#008B8B";
        }
        return zwrot;
    }

//    public boolean nanieszbiorcze(Nieobecnosc nieobecnosc) {
//            boolean czynaniesiono = false;
//            if (nieobecnosc.isNaniesiona() == false) {
//                try {
//                    if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu()) || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                        String mcod = nieobecnosc.getMcod();
//                        if (nieobecnosc.getRokod().equals(wpisView.getRokUprzedni())) {
//                            mcod = "01";
//                        }
//                        String mcdo = nieobecnosc.getMcdo();
//                        for (String mc : Mce.getMceListS()) {
//                            if (Data.jestrownywiekszy(mc, mcod) && Data.jestrownywiekszy(mcdo, mc)) {
//                                Kalendarzmiesiac znaleziony = kalendarzmiesiacFacade.findByRokMcAngaz(nieobecnosc.getAngaz(), wpisView.getRokWpisu(), mc);
//                                if (znaleziony != null) {
//                                    if (nieobecnosc.getRokod().equals(wpisView.getRokWpisu()) || nieobecnosc.getRokdo().equals(wpisView.getRokWpisu())) {
//                                        int dniroboczenieobecnosci = znaleziony.naniesnieobecnosc(nieobecnosc, );
//                                        if (!nieobecnosc.isImportowana()) {
//                                            nieobecnosc.setDniroboczenieobecnosci(nieobecnosc.getDniroboczenieobecnosci()+dniroboczenieobecnosci);
//                                        }
//                                    }
//                                    nieobecnoscFacade.edit(nieobecnosc);
//                                    kalendarzmiesiacFacade.edit(znaleziony);
//                                    czynaniesiono = true;
//                                } else {
//                                    Msg.msg("e", "Brak kalendarza pracownika za miesiąc rozliczeniowy. Nie można nanieść nieobecności!");
//                                }
//                            }
//                        }
//                    }
//                } catch (Exception e) {
//                    Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
//                }
//            }
//            return czynaniesiono;
//        }
//    
    public boolean nanies(Nieobecnosc nieobecnosc) {
        boolean czynaniesiono = NieobecnosciBean.nanies(nieobecnosc, kalendarzmiesiacFacade, nieobecnoscFacade);
        if (czynaniesiono==false) {
            Msg.msg("e", "Wystąpił błąd podczas nanoszenia nieobecności");
            Msg.msg("e", "Program nie obsługuje danego symbolu nieobecności");
        }
        kalendarzmiesiacView.init();
        dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
        String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
        Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
        String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
        Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
        EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
        urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
        return czynaniesiono;
    }

    public void pobierzzzus() {
        try {
            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
            new javax.net.ssl.HostnameVerifier(){

                public boolean verify(String hostname,
                        javax.net.ssl.SSLSession sslSession) {
                    return hostname.equals("193.105.143.40");
                }
            });
            PobierzRaporty parameters = new PobierzRaporty();
            parameters.setNip(wpisView.getFirma().getNip());
            parameters.setLogin("a.barczyk@taxman.biz.pl");
            parameters.setHaslo("Taxman2810*");
            String nowadata = Data.odejmijdniDzis(30);
            parameters.setDataOd(Data.dataoddo(nowadata));
            WsdlPlatnikRaportyZlaPortType port = wsdlPlatnikRaportyZla.getZusChannelPlatnikRaportyZlaWsdlPlatnikRaportyZlaPort();
            BindingProvider prov = (BindingProvider) port;
            prov.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "b2b_platnik_raporty_zla");
            prov.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "b2b_platnik_raporty_zla");
            PobierzRaportyResponse pobierzRaporty = port.pobierzRaporty(parameters);
            if (pobierzRaporty.getKod().equals("0")) {
                if (pobierzRaporty.getRaporty() == null) {
                    Msg.msg("w", "Brak zwolnien w ostatnich 30 dniach");
                } else {
                    Msg.msg("Pobrano zwolnienia z ostatnich 30 dni");
                }
            } else if (pobierzRaporty.getKod().equals("200")) {
                Msg.msg("e", "Serwer ZUS wyłączony");
            }
            zuszla.Raporty rap = pobierzRaporty.getRaporty();
            zuszla.Raport raport = rap.getRaport().get(3);
            Base64.Decoder dec = Base64.getDecoder();
            byte[] dane = raport.getZawartosc();
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            Path path = Paths.get(realPath + "resources/zla/raport.zip");
            Files.write(path, dane);
            ZipFile zp = new ZipFile(realPath + "resources/zla/raport.zip", "Taxman2810*".toCharArray());
            FileHeader fileHeader = zp.getFileHeader("raport.xml");
            InputStream inputStream = zp.getInputStream(fileHeader);
            //File targetFile = new File("src/main/resources/targetFile.tmp");
            RaportEzla zwrot = null;
            try {
                JAXBContext context = JAXBContext.newInstance(RaportEzla.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (RaportEzla) unmarshaller.unmarshal(inputStream);
            } catch (Exception ex) {
                error.E.s("");
            }
            if (zwrot != null) {
                Nieobecnosc nieob = new Nieobecnosc(zwrot, wpisView.getAngaz());
                nieob.setSwiadczeniekodzus(nieobecnosckodzusFacade.findByKod("CH"));
                nieob.setId(999);
                nieob.setPobranaZUS(true);
                nieob.setRokod(Data.getRok(selected.getDataod()));
                nieob.setRokdo(Data.getRok(selected.getDatado()));
                lista.add(nieob);
            }
        } catch (Exception e) {
        }
    }
    
    
    public void zdejmijzkalendarza(Nieobecnosc nieob) {
        if (nieob!=null) {
            List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(nieob.getDataod(), nieob.getDatado(), wpisView.getFirma());
            for (Dzien d : nieob.getDzienList()) {
                 EtatPrac pobierzetat = EtatBean.pobierzetat(nieob.getAngaz(),d.getDatastring());
                d.setNieobecnosc(null);
                //dzienFacade.edit(d);
                try {
                    d.nanieswzorcoweEtat(wzorcowe, pobierzetat);
                } catch (Exception e){
                    Msg.msg("w","Błąd podczas nanoszenia na kalendarz");
                }
                //dzienFacade.edit(d);
            }
            dzienFacade.editList(nieob.getDzienList());
            nieob.setDzienList(null);
            nieob.setNaniesiona(false);
            nieob.setDniroboczenieobecnosci(0.0);
            nieobecnoscFacade.edit(nieob);
            kalendarzmiesiacView.init();
            dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
            String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
            Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
            String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
            Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
        EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
        urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
            Msg.msg("Zdjęto nieobecnośćz kalendarza.");
        } else {
            Msg.msg("e","Nie można usunąc nieobecnosci");
        }
    }
            
    public void edytuj(Nieobecnosc nieob) {
        if (nieob!=null) {
            selected = nieob;
            naniesrodzajnieobecnosci();
            Msg.msg("Wybrano do edycji");
        } else {
            Msg.msg("e","Nie można edytować nieobecności");
        }
    }
    public void usun(Nieobecnosc nieob) {
        if (nieob!=null) {
            List<Dzien> wzorcowe = dzienFacade.findByNrwrokuByData(nieob.getDataod(), nieob.getDatado(), wpisView.getFirma());
            for (Dzien d : nieob.getDzienList()) {
                d.setNieobecnosc(null);
                dzienFacade.edit(d);
                try {
                    d.nanieswzorcowe(wzorcowe);
                } catch (Exception e){}
                dzienFacade.edit(d);
            }
            nieob.setDzienList(null);
            nieobecnoscFacade.edit(nieob);
            List<Naliczenienieobecnosc> naliczenienieobecnosci = naliczenienieobecnoscFacade.findByNieobecnosc(nieob);
            if (naliczenienieobecnosci!=null) {
                naliczenienieobecnoscFacade.removeList(naliczenienieobecnosci);
            }
            nieobecnoscFacade.remove(nieob);
            lista.remove(nieob);
            dniwykorzystanewroku = obliczdnichoroby(kalendarzmiesiacFacade.findByRokAngaz(wpisView.getAngaz(), wpisView.getRokWpisu()));
            String stannadzien = data.Data.ostatniDzien(wpisView.getRokWpisu(),"12");
            Angaz angaznowy = angazFacade.findById(wpisView.getAngaz());
            String dataDlaEtatu = data.Data.ostatniDzien(wpisView.getRokWpisu(),wpisView.getMiesiacWpisu());
            Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
        EkwiwalentUrlop ekwiwalent = ekwiwalentUrlopFacade.findbyRokAngaz(wpisView.getRokWpisu(), wpisView.getAngaz());
        urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(angaznowy, wpisView.getRokWpisu(), stannadzien, dataDlaEtatu, rejestrurlopow, ekwiwalent);
            kalendarzmiesiacView.init();
            Msg.msg("Usunięto nieobecność. Naniesiono zmiany w kalendarzu");
        } else {
            Msg.msg("e","Nie można usunąc nieobecności");
        }
    }

    private int obliczdnichoroby(List<Kalendarzmiesiac> kalendarze) {
        int dnichoroby = 0;
        for (Kalendarzmiesiac kal : kalendarze) {
            double[] chorobadnigodz = kal.chorobadnigodz();
            dnichoroby = (int) (dnichoroby + chorobadnigodz[0]);
        }
        return dnichoroby;
    }
      
    public Nieobecnosc getSelected() {
        return selected;
    }

    public void setSelected(Nieobecnosc selected) {
        this.selected = selected;
    }

    public List<Nieobecnosc> getLista() {
        return lista;
    }

    public void setLista(List<Nieobecnosc> lista) {
        this.lista = lista;
    }


    public List<Umowa> getListaumowa() {
        return listaumowa;
    }

    public void setListaumowa(List<Umowa> listaumowa) {
        this.listaumowa = listaumowa;
    }

    public List<Swiadczeniekodzus> getSwiadczeniekodzusLista() {
        return swiadczeniekodzusLista;
    }

    public void setSwiadczeniekodzusLista(List<Swiadczeniekodzus> swiadczeniekodzusLista) {
        this.swiadczeniekodzusLista = swiadczeniekodzusLista;
    }

    public List<Rodzajnieobecnosci> getListaabsencji() {
        return listaabsencji;
    }

    public void setListaabsencji(List<Rodzajnieobecnosci> listaabsencji) {
        this.listaabsencji = listaabsencji;
    }

    public boolean isPokazcalyrok() {
        return pokazcalyrok;
    }

    public void setPokazcalyrok(boolean pokazcalyrok) {
        this.pokazcalyrok = pokazcalyrok;
    }

    public DualListModel<Pracownik> getListapracownikow() {
        return listapracownikow;
    }

    public void setListapracownikow(DualListModel<Pracownik> listapracownikow) {
        this.listapracownikow = listapracownikow;
    }

    public boolean isDelegacja() {
        return delegacja;
    }

    public void setDelegacja(boolean delegacja) {
        this.delegacja = delegacja;
    }

    public int getDniwykorzystanewroku() {
        return dniwykorzystanewroku;
    }

    public void setDniwykorzystanewroku(int dniwykorzystanewroku) {
        this.dniwykorzystanewroku = dniwykorzystanewroku;
    }

    public Nieobecnosc getSelectedzbiorczo() {
        return selectedzbiorczo;
    }

    public void setSelectedzbiorczo(Nieobecnosc selectedzbiorczo) {
        this.selectedzbiorczo = selectedzbiorczo;
    }

    public boolean isNaniesbezposrednio() {
        return naniesbezposrednio;
    }

    public void setNaniesbezposrednio(boolean naniesbezposrednio) {
        this.naniesbezposrednio = naniesbezposrednio;
    }

    public Nieobecnoscprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Nieobecnoscprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    public boolean isZwolnienie() {
        return zwolnienie;
    }

    public void setZwolnienie(boolean zwolnienie) {
        this.zwolnienie = zwolnienie;
    }

    public List<Nieobecnosc> getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(List<Nieobecnosc> selectedlista) {
        this.selectedlista = selectedlista;
    }

    public boolean isBezoddelegowania() {
        return bezoddelegowania;
    }

    public void setBezoddelegowania(boolean bezoddelegowania) {
        this.bezoddelegowania = bezoddelegowania;
    }

    public List<Firmabaustelle> getListabaustelle() {
        return listabaustelle;
    }

    public void setListabaustelle(List<Firmabaustelle> listabaustelle) {
        this.listabaustelle = listabaustelle;
    }

    
   
    


   
    
    
}
