/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.AngazBean;
import beanstesty.DataBean;
import beanstesty.KalendarzmiesiacBean;
import beanstesty.PasekwynagrodzenBean;
import comparator.Defnicjalistaplaccomparator;
import comparator.Kalendarzmiesiaccomparator;
import comparator.Sredniadlanieobecnoscicomparator;
import dao.DefinicjalistaplacFacade;
import dao.FirmaKadryFacade;
import dao.GrupakadryFacade;
import dao.KalendarzmiesiacFacade;
import dao.KalendarzwzorFacade;
import dao.LimitdochodudwaszescFacade;
import dao.NieobecnoscFacade;
import dao.OddelegowanieZUSLimitFacade;
import dao.PasekwynagrodzenFacade;
import dao.PodatkiFacade;
import dao.RachunekdoumowyzleceniaFacade;
import dao.RodzajlistyplacFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SMTPSettingsFacade;
import dao.SwiadczeniekodzusFacade;
import dao.TabelanbpFacade;
import dao.WynagrodzeniahistoryczneFacade;
import dao.WynagrodzenieminimalneFacade;
import daoplatnik.UbezpZusrcaDAO;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Grupakadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Limitdochodudwaszesc;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.OddelegowanieZUSLimit;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajlistyplac;
import entity.SMTPSettings;
import entity.Skladnikwynagrodzenia;
import entity.Tabelanbp;
import entity.Umowa;
import entity.Wynagrodzeniahistoryczne;
import entity.Wynagrodzenieminimalne;
import entity.Zmiennawynagrodzenia;
import error.E;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.model.DualListModel;
import pdf.PdfListaPlac;
import pdf.PdfRachunekZlecenie;
import xls.WriteXLSFile;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PasekwynagrodzenView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private Pasekwynagrodzen selected;
    @Inject
    private Pasekwynagrodzen selectedlista;
    private Definicjalistaplac wybranalistaplac;
    private Definicjalistaplac wybranalistaplac2;
    private Kalendarzmiesiac wybranykalendarz;
    private List<Pasekwynagrodzen> lista;
    private List<Pasekwynagrodzen> listafiltered;
    private List<Definicjalistaplac> listadefinicjalistaplac;
    private List<Definicjalistaplac> listadefinicjalistaplacAnaliza;
    private org.primefaces.model.DualListModel<Kalendarzmiesiac> listakalendarzmiesiac;
    private List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy;
    private List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy2;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private TabelanbpFacade tabelanbpFacade;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    @Inject
    private WynagrodzeniahistoryczneFacade wynagrodzeniahistoryczneFacade;
    @Inject
    private WynagrodzenieminimalneFacade wynagrodzenieminimalneFacade;
    @Inject
    private OddelegowanieZUSLimitFacade oddelegowanieZUSLimitFacade;
    @Inject
    private RodzajlistyplacFacade rodzajlistyplacFacade;
    @Inject
    private PodatkiFacade podatkiFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private Limitdochodudwaszesc limitdochodudwaszesc;
    @Inject
    private LimitdochodudwaszescFacade limitdochodudwaszescFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
    @Inject
    private UbezpZusrcaDAO zusrcaDAO;
    @Inject
    private WpisView wpisView;
    private Rodzajlistyplac rodzajlistyplac;
    private List<Rodzajlistyplac> listarodzajlistyplac;
    List<Naliczenieskladnikawynagrodzenia> listawynagrodzenpracownika;
    List<Naliczenienieobecnosc> listanieobecnoscipracownika;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private GrupakadryFacade grupakadryFacade;
    private double kursdlalisty;
    private String datadlalisty;
    private String datawyplaty;
    private String ileszczegolow;
    private double symulacjabrrutto;
    private double symulacjanetto;
    private double symulacjatotalcost;
    private Definicjalistaplac definicjadlazasilkow;
    private boolean dialogOtwarty;
    private boolean pozwalamusunacpasek;
    
    public void open() {
        dialogOtwarty = true;
    }
    public void close() {
        dialogOtwarty = false;
    }
    
    public void reloadDialog() {
        boolean zwrot = false;
        if (dialogOtwarty) {
            init();
        }
    }

    public PasekwynagrodzenView() {
        listadefinicjalistaplac = new ArrayList<>();
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        listakalendarzmiesiac.setSource(new ArrayList<>());
        listakalendarzmiesiac.setTarget(new ArrayList<>());
    }

    
    
    public void init() {
        String rok = Data.aktualnyRok();
        if (wpisView.getRokWpisu().equals(rok)) {
            pozwalamusunacpasek = true;
        } else {
            pozwalamusunacpasek = false;
        }
        lista = new ArrayList<>();
        if (wpisView.getUmowa() != null) {
            if (wpisView.getUmowa().getUmowakodzus() != null && wpisView.getUmowa().getUmowakodzus().isPraca()) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            } else if  (wpisView.getUmowa().getUmowakodzus() != null &&wpisView.getUmowa().getUmowakodzus().isZlecenie()){
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaZlecenia();
            } else if  (wpisView.getUmowa().getUmowakodzus() != null &&wpisView.getUmowa().getUmowakodzus().isFunkcja()) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaFunkcja();
            } else {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            }
        }
        if (rodzajlistyplac == null) {
            rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
        }
        listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokUprzedni(), rodzajlistyplac);
        if (listadefinicjalistaplac==null) {
            listadefinicjalistaplac = new ArrayList<>();
        }
        listadefinicjalistaplac.addAll(definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), rodzajlistyplac));
        if (listadefinicjalistaplac!=null) {
            Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
            for (Definicjalistaplac def : listadefinicjalistaplac) {
                if (def.getRodzajlistyplac().getId()==1) {
                    definicjadlazasilkow = def;
                    break;
                }
            }
        }
        listadefinicjalistaplacAnaliza = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokUprzedni());
        if (listadefinicjalistaplacAnaliza==null) {
            listadefinicjalistaplacAnaliza = new ArrayList<>();
        }
        listadefinicjalistaplacAnaliza.addAll(definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu()));
        if (listadefinicjalistaplacAnaliza!=null) {
            Collections.sort(listadefinicjalistaplacAnaliza, new Defnicjalistaplaccomparator());
        }
        listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        try {
            wybranalistaplac = listadefinicjalistaplac.stream().filter(p -> p.getRok().equals(wpisView.getRokWpisu()) && p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            wybranalistaplac2 = listadefinicjalistaplac.stream().filter(p -> p.getRok().equals(wpisView.getRokWpisu()) && p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            datawyplaty = zrobdatawyplaty(wpisView.getMiesiacWpisu(), wpisView.getRokWpisu(), wpisView.getFirma());
            listakalendarzmiesiacdoanalizy2 = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac2.getFirma(), wybranalistaplac2.getRok(), wybranalistaplac2.getMc());
            pobierzkalendarzezamc();
            //pobierzkalendarzezamcanaliza();
        } catch (Exception e) {
            E.e(e);
        }
        listarodzajlistyplac = rodzajlistyplacFacade.findAktywne();
        ileszczegolow = "normalna";
        if (wpisView.getFirma().getDomyslnyformatlp()!=null) {
            ileszczegolow = wpisView.getFirma().getDomyslnyformatlp();
        }
        symulacjabrrutto = wpisView.getRokWpisuInt()<2023?3100:3490;
        limitdochodudwaszesc = limitdochodudwaszescFacade.findbyRok(wpisView.getRokWpisu());
    }
    
     public void initanaliza() {
        lista = new ArrayList<>();
        if (wpisView.getUmowa() != null) {
            if (wpisView.getUmowa().getUmowakodzus() != null && wpisView.getUmowa().getUmowakodzus().isPraca()) {
                    rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
                } else if  (wpisView.getUmowa().getUmowakodzus() != null && wpisView.getUmowa().getUmowakodzus().isZlecenie()){
                    rodzajlistyplac = rodzajlistyplacFacade.findUmowaZlecenia();
                } else  if  (wpisView.getUmowa().getUmowakodzus() != null) {
                    rodzajlistyplac = rodzajlistyplacFacade.findUmowaFunkcja();
                }
            }
            if (rodzajlistyplac == null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            } 
            listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), rodzajlistyplac);
            if (listadefinicjalistaplac!=null) {
                Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
            }
            listadefinicjalistaplacAnaliza = definicjalistaplacFacade.findByFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
            if (listadefinicjalistaplac!=null) {
                Collections.sort(listadefinicjalistaplacAnaliza, new Defnicjalistaplaccomparator());
            }
            listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
            try {
                wybranalistaplac = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
                wybranalistaplac2 = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
                datawyplaty = zrobdatawyplaty(wpisView.getMiesiacWpisu(), wpisView.getRokWpisu(), wpisView.getFirma());
                listakalendarzmiesiacdoanalizy2 = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac2.getFirma(), wybranalistaplac2.getRok(), wybranalistaplac2.getMc());
                wybranykalendarz = listakalendarzmiesiacdoanalizy2.stream().filter(s->s.getPesel().equals(wpisView.getPracownik().getPesel())).findFirst().orElse(null);
                //pobierzkalendarzezamc();
                pobierzkalendarzezamcanaliza();
            } catch (Exception e) {
            }
            listarodzajlistyplac = rodzajlistyplacFacade.findAktywne();
            ileszczegolow = "normalna";
            symulacjabrrutto = wpisView.getRokWpisuInt()<2023?3010:3490;
            Msg.msg("Pobrano dane do analizy");
        }

        public void wyborinnejumowy() {
            lista = new ArrayList<>();
            if (rodzajlistyplac == null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            } else {
                listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokRodzaj(wpisView.getFirma(), wpisView.getRokWpisu(), rodzajlistyplac);
            }
            Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
            listakalendarzmiesiac = new org.primefaces.model.DualListModel<>();
        try {
            wybranalistaplac = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            wybranalistaplac2 = listadefinicjalistaplac.stream().filter(p -> p.getMc().equals(wpisView.getMiesiacWpisu())).findFirst().get();
            datawyplaty = zrobdatawyplaty(wpisView.getMiesiacWpisu(), wpisView.getRokWpisu(), wpisView.getFirma());
            pobierzkalendarzezamc();
            pobierzkalendarzezamcanaliza();
        } catch (Exception e) {
        }
    }
        
//    public void editpasek(Pasekwynagrodzen pasek) {
//        if (pasek!=null) {
//                pasek.setNaliczeniepotracenieList(null);
//                pasekwynagrodzenFacade.edit(pasek);
//                pasek.setPodatekdochodowyzagranica(Z.z(pasek.getPodatekdochodowyzagranicawaluta()*pasek.getKurs()));
//                pasek.setNettoprzedpotraceniami(Z.z(pasek.getNettoprzedpotraceniamisafe()-pasek.getPodatekdochodowyzagranica()));
//                KalendarzmiesiacBean.naliczskladnikipotraceniaDB(pasek.getKalendarzmiesiac(), pasek, pasek.getWolneodzajecia());
//                PasekwynagrodzenBean.potracenia(pasek);
//                PasekwynagrodzenBean.dowyplaty(pasek);
//                PasekwynagrodzenBean.przelicznawalute(pasek);
//                pasekwynagrodzenFacade.edit(pasek);
//        } else {
//            Msg.msg("e","Nie wybrano paska");
//        }
//    }
    
    public void editpasek(Pasekwynagrodzen pasekzmiany) {
        if (pasekzmiany!=null) {
            for (Pasekwynagrodzen pasek : lista) {
                if (pasek.getPodstawaopodatkowaniazagranicawaluta()>0.0&&pasek.getPodstawaopodatkowaniazagranicawaluta()==pasekzmiany.getPodstawaopodatkowaniazagranicawaluta()) {
                    pasek.setNaliczeniepotracenieList(null);
                    pasekwynagrodzenFacade.edit(pasek);
                    pasek.setNaliczeniepotracenieList(new ArrayList<>());
                    pasek.setPodatekdochodowyzagranicawaluta(pasekzmiany.getPodatekdochodowyzagranicawaluta());
                    pasek.setPodatekdochodowyzagranica(Z.z(pasek.getPodatekdochodowyzagranicawaluta()*pasek.getKurs()));
                    pasek.setNettoprzedpotraceniami(Z.z(pasek.getNettoprzedpotraceniamisafe()-pasek.getPodatekdochodowyzagranica()));
                    KalendarzmiesiacBean.naliczskladnikipotraceniaDB(pasek.getKalendarzmiesiac(), pasek, pasek.getWolneodzajecia(), pasek.getWolneodzajeciazasilek());
                    PasekwynagrodzenBean.potracenia(pasek);
                    PasekwynagrodzenBean.dowyplaty(pasek);
                    PasekwynagrodzenBean.przelicznawalute(pasek);
                    pasekwynagrodzenFacade.edit(pasek);
                }
            }
        } else {
            Msg.msg("e","Nie wybrano paska");
        }
    }

    public void create() {
        if (selected != null) {
            try {
                PasekwynagrodzenBean.usunpasekjeslijest(selected, pasekwynagrodzenFacade);
                pasekwynagrodzenFacade.create(selected);
                lista.add(selected);
                selected = new Pasekwynagrodzen();
                Msg.msg("Dodano pasek wynagrodzen");
            } catch (Exception e) {
                Msg.msg("e", "Błąd - nie dodano paska wynagrodzen");
            }
        }
    }

    public void zapisz() {
        if (lista != null && lista.size() > 0) {
            for (Pasekwynagrodzen p : lista) {
                if (p.getId() == null) {
                    pasekwynagrodzenFacade.create(p);
                } else {
                    pasekwynagrodzenFacade.edit(p);
                }
            }
            Msg.msg("Zachowano listę płacaaaChocho");
        }
    }

    

    public void przelicz() {
        if (wybranalistaplac != null && !listakalendarzmiesiac.getTarget().isEmpty()) {
            int i = 1;
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(Data.getRok(datawyplaty), "P");
            if (datawyplaty == null) {
                Msg.msg("e", "Brak daty wypłaty");
            } else if (limitdochodudwaszesc==null){
                Msg.msg("e", "Brak limitu dochodu dla osób 26lat");
            } else if (stawkipodatkowe != null && !stawkipodatkowe.isEmpty()) {
                List<Rachunekdoumowyzlecenia> rachunkilista = new ArrayList<>();
                Kalendarzwzor kalendarzwzor = null;
                for (Iterator<Kalendarzmiesiac>  it = listakalendarzmiesiac.getTarget().iterator();it.hasNext();) {
                    Kalendarzmiesiac kalendarzpracownikaLP = it.next();
                    kalendarzpracownikaLP = kalendarzmiesiacFacade.findById(kalendarzpracownikaLP.getId());
                    Angaz angaz = kalendarzpracownikaLP.getAngaz();
                    //ten kalendarz globalny jest ttylko do wyliczenia nominalnych
                    if (kalendarzwzor==null) {
                        kalendarzwzor= kalendarzwzorFacade.findByFirmaGlobalnaRokMc(kalendarzpracownikaLP.getRok(), kalendarzpracownikaLP.getMc());
                    }
                    //wlasnie odkrylem ze on sumuje za kazdym razem godziny z kalendarza zanim zrobi listye 02.12.2023 :) btw - miłej hiszpanii. napisz jak było.
                    kalendarzpracownikaLP.podsumujdnigodziny(kalendarzwzor);
                    kalendarzmiesiacFacade.edit(kalendarzpracownikaLP);
                    boolean czysainnekody = kalendarzpracownikaLP.czysainnekody();
                    List<Pasekwynagrodzen> paskidowyliczeniapodstawy = new ArrayList<>();
                    List<Wynagrodzeniahistoryczne> historiawynagrodzen = new ArrayList<>();
                    if (czysainnekody) {
                        paskidowyliczeniapodstawy = pobierzpaskidosredniej(kalendarzpracownikaLP);
                        historiawynagrodzen = wynagrodzeniahistoryczneFacade.findByAngaz(angaz);
                    }
                    String rokwyplaty = Data.getRok(datawyplaty);
                    String mcwyplaty = Data.getMc(datawyplaty);
                    double sumapoprzednich = PasekwynagrodzenBean.sumapodstawaopodpopmce(pasekwynagrodzenFacade, kalendarzpracownikaLP, stawkipodatkowe.get(1).getKwotawolnaod(), rokwyplaty);
                    double sumabruttopoprzednich = PasekwynagrodzenBean.sumabruttopodstawaopodpopmce(pasekwynagrodzenFacade, rokwyplaty, mcwyplaty,  angaz);
                    double sumabruttoopodatkowanapoprzednich = PasekwynagrodzenBean.sumabruttopolskaopodpopmce(pasekwynagrodzenFacade, rokwyplaty, mcwyplaty,  angaz);
                    Wynagrodzenieminimalne wynagrodzenieminimalne = pobierzwynagrodzenieminimalne(kalendarzpracownikaLP.getRok(), kalendarzpracownikaLP.getMc());
                    //zeby nei odoliczyc kwoty wolnej dwa razy
                    double odliczonajuzkwotawolna = PasekwynagrodzenBean.czyodliczonokwotewolna(kalendarzpracownikaLP.getRok(), kalendarzpracownikaLP.getMc(), angaz, pasekwynagrodzenFacade);
                    double limitzus = 0.0;
                    OddelegowanieZUSLimit oddelegowanieZUSLimit = oddelegowanieZUSLimitFacade.findbyRok(Data.getRok(datawyplaty));
                    if (oddelegowanieZUSLimit != null) {
                        limitzus = oddelegowanieZUSLimit.getKwota();
                    }
                    List<Nieobecnosc> nieobecnosci = nieobecnoscFacade.findByAngaz(angaz);
                    List<Kalendarzmiesiac> kalendarzlista = kalendarzmiesiacFacade.findByAngaz(angaz);
                    Umowa aktywnaumowa = angaz.pobierzumowaZlecenia(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (rodzajlistyplac.getSymbol().equals("UD")) {
                        aktywnaumowa = angaz.pobierzumowaDzielo(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    }
                    Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = rachunekdoumowyzleceniaFacade.findByRokMcUmowa(kalendarzpracownikaLP.getRok(),kalendarzpracownikaLP.getMc(), aktywnaumowa);
                    if (rachunekdoumowyzlecenia!=null) {
                        rachunkilista.add(rachunekdoumowyzlecenia);
                    }
                    try {
                        Pasekwynagrodzen pasek = PasekwynagrodzenBean.obliczWynagrodzenie(kalendarzpracownikaLP, wybranalistaplac, nieobecnosckodzusFacade, paskidowyliczeniapodstawy, historiawynagrodzen, stawkipodatkowe, sumapoprzednich, 
                                wynagrodzenieminimalne, odliczonajuzkwotawolna,
                                kursdlalisty, limitzus, datawyplaty, nieobecnosci, limitdochodudwaszesc.getKwota(), kalendarzlista, rachunekdoumowyzlecenia, sumabruttopoprzednich, kalendarzwzor, definicjadlazasilkow, sumabruttoopodatkowanapoprzednich);
                        usunpasekjakzawiera(pasek);
                        pasek.setSporzadzil(wpisView.getUzer().getImieNazwisko());
                        pasek.setData(new Date());
                        //usuwaniezerowych
                        for (Iterator<Naliczenieskladnikawynagrodzenia> ita = pasek.getNaliczenieskladnikawynagrodzeniaList().iterator(); ita.hasNext();) {
                            Naliczenieskladnikawynagrodzenia skl = ita.next();
                            if (skl.getKwotadolistyplac()==0.0) {
                                ita.remove();
                            }
                        }
                        lista.add(pasek);
                        it.remove();
                        if (rachunekdoumowyzlecenia!=null) {
                            rachunekdoumowyzlecenia.setPasekwynagrodzen(pasek);
                        }
                    } catch (Exception e) {
                        System.out.println(E.e(e));
                        Msg.msg("e","Błąd podczas wyliczania "+angaz.getNazwiskoiImie()+" "+E.e(e));
                    }
                    
                }
                Msg.msg("Sporządzono listę płac");
                zapisz();
                if (rachunkilista.size()>0) {
                    rachunekdoumowyzleceniaFacade.editList(rachunkilista);
                }
                
            } else {
                Msg.msg("e", "Brak stawek podatkowych za bieżący rok");
            }
        } else {
            Msg.msg("e", "Nie wybrano listy lub pracownika");
        }
    }
    
    private Wynagrodzenieminimalne pobierzwynagrodzenieminimalne(String rok, String mc) {
        Wynagrodzenieminimalne wynagrodzenieminimalne = null;
        List<Wynagrodzenieminimalne> wynagrodzenielist = wynagrodzenieminimalneFacade.findByRok(rok);
        if (wynagrodzenielist!=null&&wynagrodzenielist.size()==1) {
            wynagrodzenieminimalne = wynagrodzenielist.get(0);
        } else if (wynagrodzenielist!=null&&wynagrodzenielist.size()>1){
            for (Wynagrodzenieminimalne w : wynagrodzenielist) {
                if (Data.czyjestpomiedzy(w.getDataod(), w.getDatado(), rok, mc)) {
                    wynagrodzenieminimalne = w;
                    break;
                }
            }
        }
        return wynagrodzenieminimalne;
    }
    
    @Inject
    private KalendarzwzorFacade kalendarzwzorFacade;

    public void symulacjaoblicz(String rodzajumowy) {
        if (symulacjabrrutto > 0.0) {
            int i = 1;
            FirmaKadry firmaglobalna = firmaKadryFacade.findByNIP("0000000000");
            List<Podatki> stawkipodatkowe = podatkiFacade.findByRokUmowa(wpisView.getRokWpisu(), "P");
            if (stawkipodatkowe != null && !stawkipodatkowe.isEmpty()) {
                Kalendarzwzor kalendarzwzor = kalendarzwzorFacade.findByFirmaRokMc(firmaglobalna, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                Kalendarzmiesiac kalendarz = new Kalendarzmiesiac();
                kalendarz.setRok(wpisView.getRokWpisu());
                kalendarz.setMc(wpisView.getMiesiacWpisu());
                kalendarz.nanies(kalendarzwzor);
                boolean zlecenie0praca1 = rodzajumowy.equals("1");
                Umowa umowa = new Umowa();
                umowa.setChorobowe(true);
                umowa.setEmerytalne(true);
                umowa.setRentowe(true);
                umowa.setWypadkowe(true);
                kalendarz.setAngaz(AngazBean.create());
                Pasekwynagrodzen pasek = PasekwynagrodzenBean.obliczWynagrodzeniesymulacja(kalendarz, stawkipodatkowe, zlecenie0praca1, symulacjabrrutto);
                symulacjanetto = pasek.getNetto();
                symulacjatotalcost = Z.z(pasek.getKosztpracodawcy());
            }
        }
    }

    private List<Pasekwynagrodzen> pobierzpaskidosredniej(Kalendarzmiesiac p) {
        String[] okrespoprzedni = Data.poprzedniOkres(p);
        List<Pasekwynagrodzen> paskiporzednie = pasekwynagrodzenFacade.findByRokAngaz(okrespoprzedni[1], p);
        String rokpoprzedni = String.valueOf(Integer.parseInt(okrespoprzedni[1]) - 1);
        paskiporzednie.addAll(pasekwynagrodzenFacade.findByRokAngaz(rokpoprzedni, p));
        return paskiporzednie;
    }

    private void usunpasekjakzawiera(Pasekwynagrodzen pasek) {
        for (Iterator<Pasekwynagrodzen> it = lista.iterator(); it.hasNext();) {
            Pasekwynagrodzen pa = it.next();
            if (pa.getKalendarzmiesiac().equals(pasek.getKalendarzmiesiac())) {
                it.remove();
            }
        }
    }

    public void oznaczNie26(Pasekwynagrodzen p) {
        if (p!=null) {
            if (p.isDo26lat()) {
                p.setDo26lat(false);
            } else {
                p.setDo26lat(true);
            }
            pasekwynagrodzenFacade.edit(p);
            Msg.dP();
        }
    }
    
    public void drukuj(Pasekwynagrodzen p) {
        if (p != null) {
            PdfListaPlac.drukuj(p, rodzajnieobecnosciFacade);
            Msg.msg("Wydrukowano pasek wynagrodzeń");
        } else {
            Msg.msg("e", "Błąd drukowania. Pasek null");
        }
    }

    public void drukujliste() {
        if (lista != null && lista.size() > 0) {
            List<Grupakadry> grupyfirma = grupakadryFacade.findByFirma(wpisView.getFirma());
            PdfListaPlac.drukujListaPodstawowa(lista, wybranalistaplac, rodzajnieobecnosciFacade, grupyfirma);
            Msg.msg("Wydrukowano listę płac");
            if (wybranalistaplac.getRodzajlistyplac().getSymbol().equals("UZ")||wybranalistaplac.getRodzajlistyplac().getSymbol().equals("UD")) {
                String nazwa = wpisView.getFirma().getNip()+"rachunekzlecenie.pdf";
                PdfRachunekZlecenie.drukuj(lista, nazwa, rachunekdoumowyzleceniaFacade);
            }
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
    
    public void drukujlisteMini() {
        if (lista != null && lista.size() > 0) {
            List<Grupakadry> grupyfirma = grupakadryFacade.findByFirma(wpisView.getFirma());
            PdfListaPlac.drukujListaPodstawowaMini(lista, wybranalistaplac, rodzajnieobecnosciFacade, grupyfirma);
            Msg.msg("Wydrukowano listę płac");
            if (wybranalistaplac.getRodzajlistyplac().getSymbol().equals("UZ")) {
                String nazwa = wpisView.getFirma().getNip()+"rachunekzlecenie.pdf";
                PdfRachunekZlecenie.drukuj(lista, nazwa, rachunekdoumowyzleceniaFacade);
            }
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }

    public void mailListaPlac() {
        if (lista != null && lista.size() > 0) {
            List<Definicjalistaplac> listadef = new ArrayList<>();
            listadef.add(wybranalistaplac);
            List<Grupakadry> grupyfirma = grupakadryFacade.findByFirma(wpisView.getFirma());
            ByteArrayOutputStream drukujlistaplac = PdfListaPlac.drukujmail(lista, listadef, rodzajnieobecnosciFacade, grupyfirma);
            ByteArrayOutputStream drukujrachunki = null;
            if (wybranalistaplac.getRodzajlistyplac().getSymbol().equals("UZ")) {
                String nazwa = wpisView.getFirma().getNip()+"rachunekzlecenie.pdf";
                drukujrachunki = PdfRachunekZlecenie.drukuj(lista, nazwa, rachunekdoumowyzleceniaFacade);
            }
            Pasekwynagrodzen pasek = lista.get(0);
            SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
            findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
            findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
            String nrpoprawny = wybranalistaplac.getNrkolejny().replaceAll("[^A-Za-z0-9]", "");
            String nazwa = wybranalistaplac.getFirma().getNip() + "_" + nrpoprawny + "_" + "lp.pdf";
            mail.Mail.mailListaPlac(wpisView.getFirma(), pasek.getRok(), pasek.getMc(), wpisView.getFirma().getEmail(), wpisView.getFirma().getEmaillp(), 
                    null, findSprawaByDef, drukujlistaplac, drukujrachunki, nazwa, wpisView.getUzer().getEmail());
            Msg.msg("Wysłano listę płac do pracodawcy");
            for (Pasekwynagrodzen p :lista) {
                p.setDatawysylki(Data.aktualnaData());
            }
            pasekwynagrodzenFacade.editList(lista);
        } else {
            Msg.msg("e", "Błąd drukowania. Brak pasków");
        }
    }
    
    public void usun() {
        if (lista != null && lista.size() > 0) {
            if (rodzajlistyplac.getSymbol().equals("UZ")||rodzajlistyplac.getSymbol().equals("UD")) {
                for (Pasekwynagrodzen p : lista) {
                    p =pasekwynagrodzenFacade.findById(p.getId());
                    Rachunekdoumowyzlecenia rach = rachunekdoumowyzleceniaFacade.findByRokMcAngaz(p.getRok(), p.getMc(), p.getAngaz());
                    if (rach!=null) {
                        rach.setPasekwynagrodzen(null);
                        rachunekdoumowyzleceniaFacade.edit(rach);
                        pasekwynagrodzenFacade.edit(p);
                    }
                    pasekwynagrodzenFacade.remove(p);
                }
            } else {
                pasekwynagrodzenFacade.removeList(lista);
            }
            lista.clear();
            init();
            Msg.msg("Usunięto osoby z listy płac");
        }
    }
    
    public void usun(Pasekwynagrodzen p) {
        if (p != null) {
            if (p.getId() != null) {
                if (p.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("UZ")||p.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("UD")) {
                    Rachunekdoumowyzlecenia rach = rachunekdoumowyzleceniaFacade.findByRokMcAngaz(p.getRok(), p.getMc(), p.getAngaz());
                     if (rach!=null) {
                        rach.setPasekwynagrodzen(null);
                        rachunekdoumowyzleceniaFacade.edit(rach);
                    }
                }
                pasekwynagrodzenFacade.remove(p);
                lista.remove(p);
                pobierzkalendarzezamc();
            } else {
                lista.remove(p);
                pobierzkalendarzezamc();
            }
            Msg.msg("Usunięto wiersz listy płac");
        } else {
            Msg.msg("e", "Błąd usuwania. Pasek null");
        }
    }

    public void aktywuj(Angaz angaz) {
        if (angaz != null) {
            wpisView.setAngaz(angaz);
            Msg.msg("Aktywowano firmę");
        }
    }

    public void pobierzkalendarzezamc() {
        Definicjalistaplac wybranalistaplac = this.wybranalistaplac;
        
        if (wybranalistaplac != null) {
            if (rodzajlistyplac == null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            }
            List<Kalendarzmiesiac> listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            if (listakalendarzmiesiac.isEmpty()) {
                Msg.msg("e","Brak kalendarzy za miesiąc");
            }
            //zmieniam to bo jak to jest to nie mozna wyplacic zaleglych rzeczy zwolnionym praconikom
//            for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
//                Kalendarzmiesiac p = it.next();
//                Umowa umowaAktywna = p.getAngaz().pobierzumowaAktywna(wybranalistaplac.getRok(), wybranalistaplac.getMc());
//                if (umowaAktywna==null) {
//                    it.remove();
//                }
//            }
              if (rodzajlistyplac.getSymbol().equals("NA")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
                Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
                if (listakalendarzmiesiac!=null) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator();it.hasNext();) {
                        Kalendarzmiesiac kal = it.next();
                        List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = kal.getAngaz().getSkladnikwynagrodzeniaList();
                        if (!skladnikwynagrodzeniaList.isEmpty()) {
                            Predicate<Skladnikwynagrodzenia> isQualified = item->item.getRodzajwynagrodzenia().isSwiadczenierzeczowe();
                            skladnikwynagrodzeniaList.removeIf(isQualified.negate());
                            if (skladnikwynagrodzeniaList.isEmpty()) {
                                it.remove();
                            }
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("ZA")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
                Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
                if (listakalendarzmiesiac!=null) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator();it.hasNext();) {
                        Kalendarzmiesiac kal = it.next();
                        if (!kal.czyjestzasilek()) {
                            it.remove();
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
                Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
                if (rodzajlistyplac.getSymbol().equals("UZ")) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                        Kalendarzmiesiac p = it.next();
                        Angaz angaz = p.getAngaz();
                        Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wybranalistaplac.getRok(), wybranalistaplac.getMc());
                        if (znaleziony == null) {
                            it.remove();
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("UD")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
                Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
                if (rodzajlistyplac.getSymbol().equals("UD")) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                        Kalendarzmiesiac p = it.next();
                        Angaz angaz = p.getAngaz();
                        Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekdzielo(angaz, wybranalistaplac.getRok(), wybranalistaplac.getMc());
                        if (znaleziony == null) {
                            it.remove();
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("OS")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
                Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac kal = it.next();
                    List<Umowa> umowy = kal.getAngaz().getUmowaList();
                    boolean czyjestfunkcja = false;
                    for (Umowa u : umowy) {
                        if (u.getUmowakodzus().isFunkcja()) {
                            czyjestfunkcja = true;
                            break;
                        }
                    }
                    if (czyjestfunkcja==false) {
                        it.remove();
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMcNierezydent(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
                Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wybranalistaplac.getRok(), wybranalistaplac.getMc());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("WZ")) {
                 for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac kal = it.next();
                    Pasekwynagrodzen pasek = kal.getPasek(wybranalistaplac);
                    if (kal.getAngaz().getAktywnaUmowa() != null) {
                        List<EtatPrac> etatList = kal.getAngaz().getEtatList();
                        if (kal.getAngaz().getAktywnaUmowa().getUmowakodzus().isZlecenie() ||kal.getAngaz().getAktywnaUmowa().getUmowakodzus().isDzielo() || kal.getAngaz().getAktywnaUmowa().getUmowakodzus().isFunkcja()) {
                            it.remove();
                        } else {
                            List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = kal.getAngaz().getSkladnikwynagrodzeniaList();
                            List<Zmiennawynagrodzenia> zmienneList = new ArrayList<>();
                            if (skladnikwynagrodzeniaList != null) {
                                for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
                                    zmienneList.addAll(skl.getZmiennawynagrodzeniaList());
                                }
                            }
                            if (zmienneList != null && zmienneList.size() > 0) {
                                boolean jestwokresie = false;
                                //System.out.println(kal.getNazwiskoImie());
                                for (Zmiennawynagrodzenia zmienna : zmienneList) {
                                    if (zmienna.getDataod()!=null) {
                                        if (DataBean.czyZmiennasieMiesci(zmienna, kal)) {
                                            jestwokresie = true;
                                            break;
                                        }
                                    }
                                }
                                if (jestwokresie == false) {
                                    it.remove();
                                } else  if (etatList==null||etatList.isEmpty()) {
                                    it.remove();
                                    Msg.msg("e","Brak etatu dla "+kal.getNazwiskoImie());
                                }
                            }
                        }
                    }
                }
            }
            lista = pasekwynagrodzenFacade.findByDef(wybranalistaplac);
            for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                Kalendarzmiesiac kal = it.next();
                Pasekwynagrodzen pasek = kal.getPasek(wybranalistaplac);
                if (lista.contains(pasek)) {
                    it.remove();
                }
            }
            for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                Kalendarzmiesiac p = it.next();
                Angaz angazaktywny = p.getAngaz();
                if (angazaktywny.isUkryj()) {
                    it.remove();
                }
            }
            if (listakalendarzmiesiac != null&&listakalendarzmiesiac.size()>0) {
                this.listakalendarzmiesiac.setSource(listakalendarzmiesiac);
                this.listakalendarzmiesiac.setTarget(new ArrayList<>());
            } else {
                this.listakalendarzmiesiac.setSource(new ArrayList<>());
                this.listakalendarzmiesiac.setTarget(new ArrayList<>());
            }
            if (lista!=null&&lista.size()>0) {
                Pasekwynagrodzen pasek = lista.get(0);
                datawyplaty = pasek.getDatawyplaty();
                ustawtabelenbp(datawyplaty);
            } else {
                datawyplaty = zrobdatawyplaty(wybranalistaplac.getMc(), wybranalistaplac.getRok(), wpisView.getFirma());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (lista.isEmpty()==false) {
                korektaToczek();
            }
        }
    }

    public void pobierzkalendarzezamcanaliza() {
        Definicjalistaplac wybranalistaplac = this.wybranalistaplac2;
        if (wybranalistaplac != null) {
            if (rodzajlistyplac == null) {
                rodzajlistyplac = rodzajlistyplacFacade.findUmowaoPrace();
            }
            List<Kalendarzmiesiac> listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            if (rodzajlistyplac.getSymbol().equals("ZA")) {
                if (listakalendarzmiesiac!=null) {
                    for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator();it.hasNext();) {
                        Kalendarzmiesiac kal = it.next();
                        if (!kal.czyjestzasilek()) {
                            it.remove();
                        }
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (rodzajlistyplac.getSymbol().equals("UZ")) {
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            if (rodzajlistyplac.getSymbol().equals("OS")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMc(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                listakalendarzmiesiac = kalendarzmiesiacFacade.findByFirmaRokMcNierezydent(wybranalistaplac.getFirma(), wybranalistaplac.getRok(), wybranalistaplac.getMc());
            }
            Collections.sort(listakalendarzmiesiac, new Kalendarzmiesiaccomparator());
            if (rodzajlistyplac.getSymbol().equals("ZR")) {
                for (Iterator<Kalendarzmiesiac> it = listakalendarzmiesiac.iterator(); it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    Angaz angaz = p.getAngaz();
                    Rachunekdoumowyzlecenia znaleziony = PasekwynagrodzenBean.pobierzRachunekzlecenie(angaz, wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                    if (znaleziony == null) {
                        it.remove();
                    }
                }
            }
            lista = pasekwynagrodzenFacade.findByDef(wybranalistaplac);
            if (listakalendarzmiesiac != null) {
                this.listakalendarzmiesiacdoanalizy2 = listakalendarzmiesiac;
            }
            if (wybranykalendarz != null) {
                try {
                    wybranykalendarz = listakalendarzmiesiac.stream().filter(p -> p.getPesel().equals(wybranykalendarz.getAngaz().getPracownik().getPesel())).findFirst().get();
                    pobierzpracownika();
                } catch (Exception e) {
                }
            }
           
        }
    }
    
    private void korektaToczek() {
        for (Pasekwynagrodzen pasek : lista) {
            if (pasek.isImportowany()) {
                pasek.setKurs(kursdlalisty);
                double oddelegowaniepln = pasek.getOddelegowanieplnToczek();
                pasek.setOddelegowaniepln(oddelegowaniepln);
                double bruttokraj = pasek.getBruttozuskraj()>0.0?pasek.getBruttozuskraj():pasek.getBrutto()-oddelegowaniepln;
                pasek.setBruttozuskraj(bruttokraj);
                double oddelegowaniewaluta = Z.z(oddelegowaniepln/kursdlalisty);
                pasek.setOddelegowaniewaluta(oddelegowaniewaluta);
                pasek.setPrzychodypodatekpolska(bruttokraj);
                pasek.setPodatekdochodowyzagranica(oddelegowaniepln);
                Kalendarzmiesiac kalendarz = pasek.getKalendarzmiesiac();
                String rok = kalendarz.getAngaz().getPrzekroczenierok();
                String mc = kalendarz.getAngaz().getPrzekroczeniemc()!=null?kalendarz.getAngaz().getPrzekroczeniemc():"01";
                if (kalendarz.getAngaz().getPrzekroczeniemc()!=null&&kalendarz.getAngaz().getPrzekroczeniemc().equals("")) {
                    mc = "01";
                }
                if (rok!=null&&mc!=null&&data.Data.czyjestpomc(mc, rok, pasek.getRokwypl(), pasek.getMcwypl())) {
                    pasek.setPrzekroczenieoddelegowanie(true);
                }
                //podobno niepoczebne
                //PasekwynagrodzenBean.obliczdietedoodliczeniaToczek(pasek);
                data.Data.obliczwiekNaniesnapasek(pasek.getAngaz().getPracownik().getDataurodzenia(), pasek);
            }
        }
        pasekwynagrodzenFacade.editList(lista);
    }

    public void pobierzpracownika() {
        if (wybranykalendarz != null&&wybranalistaplac2!=null) {
            listawynagrodzenpracownika = KalendarzmiesiacBean.skladnikiwynagrodzeniaWybranalista(wybranykalendarz, wybranalistaplac2);
            listanieobecnoscipracownika = wybranykalendarz.skladnikinieobecnosclista(wybranalistaplac2);
            for (Naliczenienieobecnosc p : listanieobecnoscipracownika) {
                Collections.sort(p.getSredniadlanieobecnosciList(), new Sredniadlanieobecnoscicomparator());
            }
            Msg.msg("Pobrano pracownika");
        }
    }
    
    public double ustawtabelenbpXls(String datawyplaty) {
            double zwrot = 0.0;
            if (datawyplaty!=null && datawyplaty.length()==10) {
                String data = datawyplaty;
                boolean znaleziono = false;
                int zabezpieczenie = 0;
                while (!znaleziono && (zabezpieczenie < 365)) {
                    data = Data.odejmijdni(data, 1);
                    Tabelanbp tabelanbppobrana = tabelanbpFacade.findByDateWaluta(data, "EUR");
                    if (tabelanbppobrana instanceof Tabelanbp) {
                        znaleziono = true;
                        zwrot = tabelanbppobrana.getKurssredni();
                        break;
                    }
                    zabezpieczenie++;
                }
            }
            return zwrot;
    }
    
    public void ustawtabelenbp(String datawyplaty) {
            if (datawyplaty!=null && datawyplaty.length()==10) {
                String data = datawyplaty;
                boolean znaleziono = false;
                int zabezpieczenie = 0;
                while (!znaleziono && (zabezpieczenie < 365)) {
                    data = Data.odejmijdni(data, 1);
                    Tabelanbp tabelanbppobrana = tabelanbpFacade.findByDateWaluta(data, "EUR");
                    if (tabelanbppobrana instanceof Tabelanbp) {
                        znaleziono = true;
                        kursdlalisty = tabelanbppobrana.getKurssredni();
                        datadlalisty = tabelanbppobrana.getDatatabeli();
                        break;
                    }
                    zabezpieczenie++;
                }
            }
    }

    private String zrobdatawyplaty(String mc, String rok, FirmaKadry firma) {
        String zwrot;
        if (firma.getDzienlp()==null) {
            zwrot = Data.ostatniDzien(rok, mc);
        } else {
            String[] nastepnyOkres = Data.nastepnyOkres(mc,rok);
            zwrot = nastepnyOkres[1] + "-" + nastepnyOkres[0] + "-"+firma.getDzienlp();
        }
        ustawtabelenbp(zwrot);
        return zwrot;
    }
    
    
    public void generujXLS() {
        try {
            Workbook workbook = WriteXLSFile.listaplacXLS(lista, wpisView, wybranalistaplac, kursdlalisty);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "lp"+wybranalistaplac.getNrkolejny()+wpisView.getOkreswpisu().getRokmc()+".xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            // Logger.getLogger(XLSSymulacjaView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    //to nie ma sensu, bo przy zmianie parametrow bedzie przeciez przeliczany podatek
//    public void pobierzpodatekzagraniczny() {
//        if (ileszczegolow.equals("podatek")) {
//            Msg.msg("Pobieram podatek zagraniczny");
//        }
//    }

    public List<Pasekwynagrodzen> getListafiltered() {
        return listafiltered;
    }

    public void setListafiltered(List<Pasekwynagrodzen> listafiltered) {
        this.listafiltered = listafiltered;
    }

    public Pasekwynagrodzen getSelected() {
        return selected;
    }

    public void setSelected(Pasekwynagrodzen selected) {
        this.selected = selected;
    }

    public List<Pasekwynagrodzen> getLista() {
        return lista;
    }

    public void setLista(List<Pasekwynagrodzen> lista) {
        this.lista = lista;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplac() {
        return listadefinicjalistaplac;
    }

    public void setListadefinicjalistaplac(List<Definicjalistaplac> listadefinicjalistaplac) {
        this.listadefinicjalistaplac = listadefinicjalistaplac;
    }

    public Pasekwynagrodzen getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pasekwynagrodzen selectedlista) {
        this.selectedlista = selectedlista;
    }

    public DualListModel<Kalendarzmiesiac> getListakalendarzmiesiac() {
        return listakalendarzmiesiac;
    }

    public void setListakalendarzmiesiac(DualListModel<Kalendarzmiesiac> listakalendarzmiesiac) {
        this.listakalendarzmiesiac = listakalendarzmiesiac;
    }

    public Definicjalistaplac getWybranalistaplac2() {
        return wybranalistaplac2;
    }

    public void setWybranalistaplac2(Definicjalistaplac wybranalistaplac2) {
        this.wybranalistaplac2 = wybranalistaplac2;
    }

    public Definicjalistaplac getWybranalistaplac() {
        return wybranalistaplac;
    }

    public void setWybranalistaplac(Definicjalistaplac wybranalistaplac) {
        this.wybranalistaplac = wybranalistaplac;
    }

    public Rodzajlistyplac getRodzajlistyplac() {
        return rodzajlistyplac;
    }

    public void setRodzajlistyplac(Rodzajlistyplac rodzajlistyplac) {
        this.rodzajlistyplac = rodzajlistyplac;
    }


    public List<Kalendarzmiesiac> getListakalendarzmiesiacdoanalizy() {
        return listakalendarzmiesiacdoanalizy;
    }

    public void setListakalendarzmiesiacdoanalizy(List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy) {
        this.listakalendarzmiesiacdoanalizy = listakalendarzmiesiacdoanalizy;
    }

    public Kalendarzmiesiac getWybranykalendarz() {
        return wybranykalendarz;
    }

    public void setWybranykalendarz(Kalendarzmiesiac wybranykalendarz) {
        this.wybranykalendarz = wybranykalendarz;
    }

    public List<Kalendarzmiesiac> getListakalendarzmiesiacdoanalizy2() {
        return listakalendarzmiesiacdoanalizy2;
    }

    public void setListakalendarzmiesiacdoanalizy2(List<Kalendarzmiesiac> listakalendarzmiesiacdoanalizy2) {
        this.listakalendarzmiesiacdoanalizy2 = listakalendarzmiesiacdoanalizy2;
    }

    public List<Naliczenieskladnikawynagrodzenia> getListawynagrodzenpracownika() {
        return listawynagrodzenpracownika;
    }

    public void setListawynagrodzenpracownika(List<Naliczenieskladnikawynagrodzenia> listawynagrodzenpracownika) {
        this.listawynagrodzenpracownika = listawynagrodzenpracownika;
    }

    public List<Naliczenienieobecnosc> getListanieobecnoscipracownika() {
        return listanieobecnoscipracownika;
    }

    public void setListanieobecnoscipracownika(List<Naliczenienieobecnosc> listanieobecnoscipracownika) {
        this.listanieobecnoscipracownika = listanieobecnoscipracownika;
    }

    public double getKursdlalisty() {
        return kursdlalisty;
    }

    public void setKursdlalisty(double kursdlalisty) {
        this.kursdlalisty = kursdlalisty;
    }

    public String getDatawyplaty() {
        return datawyplaty;
    }

    public void setDatawyplaty(String datawyplaty) {
        this.datawyplaty = datawyplaty;
    }

    public String getIleszczegolow() {
        return ileszczegolow;
    }

    public void setIleszczegolow(String ileszczegolow) {
        this.ileszczegolow = ileszczegolow;
    }

    public String getDatadlalisty() {
        return datadlalisty;
    }

    public void setDatadlalisty(String datadlalisty) {
        this.datadlalisty = datadlalisty;
    }

    public double getSymulacjabrrutto() {
        return symulacjabrrutto;
    }

    public void setSymulacjabrrutto(double symulacjabrrutto) {
        this.symulacjabrrutto = symulacjabrrutto;
    }

    public double getSymulacjanetto() {
        return symulacjanetto;
    }

    public void setSymulacjanetto(double symulacjanetto) {
        this.symulacjanetto = symulacjanetto;
    }

    public double getSymulacjatotalcost() {
        return symulacjatotalcost;
    }

    public void setSymulacjatotalcost(double symulacjatotalcost) {
        this.symulacjatotalcost = symulacjatotalcost;
    }

    public List<Rodzajlistyplac> getListarodzajlistyplac() {
        return listarodzajlistyplac;
    }

    public void setListarodzajlistyplac(List<Rodzajlistyplac> listarodzajlistyplac) {
        this.listarodzajlistyplac = listarodzajlistyplac;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplacAnaliza() {
        return listadefinicjalistaplacAnaliza;
    }

    public void setListadefinicjalistaplacAnaliza(List<Definicjalistaplac> listadefinicjalistaplacAnaliza) {
        this.listadefinicjalistaplacAnaliza = listadefinicjalistaplacAnaliza;
    }

    public UbezpZusrcaDAO getZusrcaDAO() {
        return zusrcaDAO;
    }

    public void setZusrcaDAO(UbezpZusrcaDAO zusrcaDAO) {
        this.zusrcaDAO = zusrcaDAO;
    }

    public boolean isPozwalamusunacpasek() {
        return pozwalamusunacpasek;
    }

    public void setPozwalamusunacpasek(boolean pozwalamusunacpasek) {
        this.pozwalamusunacpasek = pozwalamusunacpasek;
    }

    

}
