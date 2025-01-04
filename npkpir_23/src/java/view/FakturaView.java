/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.Kolmn;
import beansDok.ListaEwidencjiVat;
import beansFK.DokFKBean;
import beansFaktura.FDfkBean;
import beansFaktura.FakturaBean;
import beansFaktura.FakturaOkresowaGenNum;
import beansMail.OznaczFaktBean;
import beansMail.SMTPBean;
import beansPodatnik.PodatnikBean;
import beansRegon.SzukajDaneBean;
import comparator.FakturaIncydentalnycomparator;
import comparator.Fakturyokresowecomparator;
import dao.DokDAO;
import dao.DokDAOfk;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturaDodPozycjaKontrahentDAO;
import dao.FakturaStopkaNiemieckaDAO;
import dao.FakturaWaloryzacjaDAO;
import dao.FakturaWalutaKontoDAO;
import dao.FakturadodelementyDAO;
import dao.FakturaelementygraficzneDAO;
import dao.FakturywystokresoweDAO;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.LogofakturaDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.RodzajedokDAO;
import dao.SMTPSettingsDAO;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import dao.WierszfakturybazaDAO;
import data.Data;
import embeddable.EVatwpis;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Faktura;
import entity.FakturaWaloryzacja;
import entity.FakturaWalutaKonto;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import entity.Fakturywystokresowe;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Logofaktura;
import entity.ParamSuper;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.Rodzajedok;
import entity.Wierszfakturybaza;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.Tabelanbp;
import error.E;
import gus.GUSView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import mail.MailOther;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
 import org.joda.time.MutableDateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.autocomplete.AutoComplete;
import params.Params;
import pdf.PdfFaktura;
import pdf.PdfFakturyOkresowe;
import pdf.PdfFakturySporzadzone;
import plik.Plik;
import serialclone.SerialClone;
import sms.SmsSend;
import sortfunction.FakturaSortBean;
import waluty.Z;
import xls.WriteXLSOkresowe;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaView implements Serializable {
    private static final long serialVersionUID = 1L;
    //faktury wybrane z listy
    private List<Faktura> gosciwybral;
    //faktury wybrane z listy proforma
    private List<Faktura> gosciwybralpro;
    //faktury wybrane z listy
    private List<Faktura> gosciwybralarchiuwm;
    //faktury okresowe wybrane z listy
    private List<Fakturywystokresowe> gosciwybralokres;

    @Inject
    protected Faktura selected;
    @Inject
    private PdfFaktura pdfFaktura;
    private boolean pokazfakture;
    @Inject
    private WpisView wpisView;
    @Inject
    private FakturaWalutaKontoView fakturaWalutaKontoView;
    @Inject
    private GUSView gUSView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private KliencifkDAO kliencifkDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private FakturadodelementyDAO fakturadodelementyDAO;
    @Inject
    private FakturaWalutaKontoDAO fakturaWalutaKontoDAO;
    @Inject
    private FakturaDodPozycjaKontrahentDAO fakturaDodPozycjaKontrahentDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private FakturaWaloryzacjaDAO fakturaWaloryzacjaDAO;
    //faktury stworzone
    private List<Faktura> faktury;
    //faktury stworzone do edycji
    private List<Faktura> faktury_edit;
    private List<Faktura> faktury_edit_select;
    private List<Faktura> faktury_edit_filter;
    //faktury z bazy danych przefiltrowane
    private List<Faktura> fakturyFiltered;
    //faktury z bazy danych proforma
    private List<Faktura> fakturypro;
    //faktury z bazy danych przefiltrowane profotma
    private List<Faktura> fakturyFilteredpro;
    //faktury z bazy danych przefiltrowane
    private List<Faktura> fakturyFilteredarchiwum;
    //faktury z bazy danych
    private List<Faktura> fakturyarchiwum;
    //faktury okresowe z bazy danych
    private List<Fakturywystokresowe> fakturyokresowe;
    //faktury okresowe z bazy danych filtrowane
    private List<Fakturywystokresowe> fakturyokresoweFiltered;
    //do zaksiegowania faktury
    @Inject
    private DokDAO dokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    //tego potrzebuje zeby zachowac wiersz wzorcowy
    @Inject
    private PodatnikDAO podatnikDAO;
    private Double podsumowaniewybranychbrutto;
    private Double podsumowaniewybranychnetto;
    private Double podsumowaniewybranychvat;
    private int iloscwybranych;
    private Double podsumowaniewybranychbrutto2;
    private Double podsumowaniewybranychnetto2;
    private Double podsumowaniewybranychvat2;
    private int iloscwybranych2;
    //do usuuwania faktur zaksiegowanych
    private double waloryzajca;
    private double kwotaprzedwaloryzacja;
    //wlasna data dla faktur
    private String datawystawienia;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private String wiadomoscdodatkowa;
    private int aktywnytab;
    private boolean zapis0edycja1;
    private String nazwaskroconafaktura;
    private boolean fakturazwykla;
    private boolean fakturaniemiecka;
    private boolean fakturavatmarza;
    private boolean fakturaxxl;
    private boolean fakturakorekta;
    private boolean rachunek;
    private boolean podazedytorvar;
    private boolean pokazzawieszone;
    private AutoComplete kontrahentstworz;
    private AutoComplete odbiorcastworz;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    @Inject
    private FakturaStopkaNiemieckaDAO fakturaStopkaNiemieckaDAO;
    @Inject
    private FakturaelementygraficzneDAO fakturaelementygraficzneDAO;
    @Inject
    private WierszfakturybazaDAO wierszfakturybazaDAO;
    @Inject
    private LogofakturaDAO logofakturaDAO;
    private Tabelanbp domyslatabela;
    private int jakapobrac;
    private double dolnylimit;
    private double gornylimit;
    private boolean pokaztylkoniewyslane;
    private boolean mailplussms;
    List<String> listakontawwalucie;
    private String stawkaryczaltuksiegowanie;
    private String emailkontrahent;
    private List<Faktura> listaincydentalni;
    private Faktura wybranyicydentalny;
    
        

    public FakturaView() {

    }

    @PostConstruct
    public void init() { //E.m(this);
        emailkontrahent = null;
        fakturyokresowe = Collections.synchronizedList(new ArrayList<>());
        gosciwybral = Collections.synchronizedList(new ArrayList<>());
        gosciwybralokres = Collections.synchronizedList(new ArrayList<>());
        waloryzajca = 0.0;
        kwotaprzedwaloryzacja = 0.0;
        faktury = Collections.synchronizedList(new ArrayList<>());
        faktury_edit = Collections.synchronizedList(new ArrayList<>());
        fakturypro = Collections.synchronizedList(new ArrayList<>());
        fakturyarchiwum = Collections.synchronizedList(new ArrayList<>());
        fakturyokresoweFiltered = null;
        fakturyFiltered = null;
        fakturyFilteredarchiwum = null;
        aktywnytab = 1;
        mailplussms = false;
        if (pokazzawieszone == true) {
            fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        } else {
            fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezaceBezzawieszonych(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        Collections.sort(fakturyokresowe, new Fakturyokresowecomparator());
        List<Faktura> fakturytmp = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (wpisView.isKsiegirachunkowe()==false) {
            List<Faktura> faktury = fakturaDAO.findbyPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            listaincydentalni = filtrujUnikatoweFakturyNieNull(faktury);
            if (listaincydentalni!=null) {
                Collections.sort(listaincydentalni, new FakturaIncydentalnycomparator());
            }
        }
        boolean czybiuro = wpisView.getPodatnikObiekt().getNip().equals("8511005008");
        try {
            String rok = wpisView.getRokWpisuSt();
            String mc = wpisView.getMiesiacWpisu();
            String[] okrespoprzeni = Data.poprzedniOkres(mc, rok);
            mc = okrespoprzeni[0];
            rok = okrespoprzeni[1];
            List<Wierszfakturybaza> wierzfakturybazalist = wierszfakturybazaDAO.findbyRokMc(rok, mc);
            for (Fakturywystokresowe w : fakturyokresowe) {
                Klienci odbiorca = w.getDokument().getKontrahent();
                for (Wierszfakturybaza wb :wierzfakturybazalist) {
                    if (w.isRecznaedycja()&&wb.getNip().equals(odbiorca.getNip())) {
                        if (wb.getIlosc()>0) {
                            w.setSapracownicy(true);
                        }
                    }
                }
            }
//            List<FakturaDodPozycjaKontrahent> lista = fakturaDodPozycjaKontrahentDAO.findByRokMc(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
//            for (Fakturywystokresowe p : fakturyokresowe) {
//                Klienci odbiorca = p.getDokument().getKontrahent();
//                for (FakturaDodPozycjaKontrahent r : lista) {
//                    if (r.getKontrahent().equals(odbiorca) && r.isDowygenerowania() == true && r.getDatafaktury() == null) {
//                        p.setSapracownicy(true);
//                        break;
//                    }
//                }
//            }
        } catch (Exception e){}
//        if (czybiuro) {
//            boolean czyszef = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().equals("szef");
//            if (czyszef) {
//                for (Faktura fakt : fakturytmp) {
//                    if (!fakt.isTylkodlaokresowej()) {
//                        if (fakt.isProforma()) {
//                            fakturypro.add(fakt);
//                        } else if (fakt.getWyslana() == true && fakt.getZaksiegowana() == true) {
//                            fakturyarchiwum.add(fakt);
//                        } else {
//                            faktury.add(fakt);
//                        }
//                    }
//                } 
//            } else {
//                 Map<String, Uz> podatnicylista = podatnikDAO.findAllPrzyporzadkowany().stream().collect(Collectors.toMap(Podatnik::getNip, Podatnik::getKsiegowa));
//                 for (Faktura fakt : fakturytmp) {
//                     try {
//                        Uz uzer = podatnicylista.entrySet().stream().filter(e -> e.getKey().equals(fakt.getKontrahent().getNip())).findFirst().get().getValue();
//                        if (uzer!=null) {
//                        String login = uzer.getLogin().substring(0,3);
//                           if (wpisView.getUzer().getLogin().startsWith(login)) {
//                               if (!fakt.isTylkodlaokresowej()) {
//                                   if (fakt.isProforma()) {
//                                       fakturypro.add(fakt);
//                                   } else if (fakt.getWyslana() == true && fakt.getZaksiegowana() == true) {
//                                       fakturyarchiwum.add(fakt);
//                                   } else {
//                                       faktury.add(fakt);
//                                   }
//                               }
//                           }
//                        }
//                     } catch (Exception ex) {}
//                }
//            }
//        } else {
//            for (Faktura fakt : fakturytmp) {
//                if (!fakt.isTylkodlaokresowej()) {
//                        if (fakt.isProforma()) {
//                            fakturypro.add(fakt);
//                        } else if (fakt.getWyslana() == true && fakt.getZaksiegowana() == true) {
//                            fakturyarchiwum.add(fakt);
//                        } else {
//                            faktury.add(fakt);
//                        }
//                    }
//            }
//        }
        if (wpisView.getUzer().getFakturagrupa() != null && !wpisView.getUzer().getFakturagrupa().equals("") && !wpisView.getUzer().getFakturagrupa().equals("szef")) {
            for (Faktura fakt : fakturytmp) {
                if (!fakt.isTylkodlaokresowej() && fakt.getFakturagrupa() != null && fakt.getFakturagrupa().equals(wpisView.getUzer().getFakturagrupa())) {
                    if (fakt.isProforma()) {
                        fakturypro.add(fakt);
                    } else if (fakt.getWyslana() == true && fakt.getZaksiegowana() == true) {
                        fakturyarchiwum.add(fakt);
                    } else if (fakt.isRecznaedycja()) {
                        faktury_edit.add(fakt);
                    } else if (pokaztylkoniewyslane && fakt.getDatawysylki() == null) {
                        faktury.add(fakt);
                    } else if (!pokaztylkoniewyslane) {
                        faktury.add(fakt);
                    }
                }
            }
        } else {
            for (Faktura fakt : fakturytmp) {
                if (!fakt.isTylkodlaokresowej()) {
                    if (fakt.isProforma()) {
                        fakturypro.add(fakt);
                    } else if (fakt.getWyslana() == true && fakt.getZaksiegowana() == true) {
                        fakturyarchiwum.add(fakt);
                    } else if (fakt.isRecznaedycja()) {
                        faktury_edit.add(fakt);
                    } else if (pokaztylkoniewyslane && fakt.getDatawysylki() == null) {
                        faktury.add(fakt);
                    } else if (!pokaztylkoniewyslane) {
                        faktury.add(fakt);
                    }
                }
            }
        }
        fakturypro = fakturaDAO.findbyPodatnikRokProforma(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Fakturaelementygraficzne logo = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (logo != null) {
            sprawdzczyniezniknalplik(logo.getFakturaelementygraficznePK().getNazwaelementu(),0);
        }
        Fakturaelementygraficzne elementgraficzny = fakturaelementygraficzneDAO.findFaktElementyGraficznePodatnik(wpisView.getPodatnikWpisu());
        if (elementgraficzny != null) {
            sprawdzczyniezniknalplik(elementgraficzny.getFakturaelementygraficznePK().getNazwaelementu(),1);
        }
        filtrujfaktury();
        sumawartosciwybranych(faktury);
        domyslatabela = DokFKBean.pobierzWaluteDomyslnaDoDokumentu(walutyDAOfk, tabelanbpDAO);
//        PrimeFaces.current().ajax().update("akordeon:formsporzadzone");
//        PrimeFaces.current().ajax().update("akordeon:proforma");
//        PrimeFaces.current().ajax().update("akordeon:formarchiwum");
    }

    public List<Faktura> filtrujUnikatoweFakturyNieNull(List<Faktura> faktury) {
        return faktury.stream()
                .filter(faktura -> faktura.getNazwiskoimieincydent() != null) // filtrowanie gdzie nazwiskoimieincydent != null
                .collect(Collectors.toMap(
                        faktura -> faktura.getNazwiskoimieincydent() + faktura.getAdres1(),
                        Function.identity(),
                        (f1, f2) -> f1
                ))
                .values()
                .stream()
                .collect(Collectors.toList());
    }
    
    public void exportToExcel(List<Fakturywystokresowe> fakturyList) {
        WriteXLSOkresowe.exportToExcel(fakturyList);
    }
    
    public void kopiujincydentalnego() {
        if (wybranyicydentalny!=null&&selected!=null&&selected.getKontrahent()==null) {
            selected.setNazwiskoimieincydent(wybranyicydentalny.getNazwiskoimieincydent());
            selected.setAdres1(wybranyicydentalny.getAdres1());
            selected.setAdres2(wybranyicydentalny.getAdres2());
            Msg.msg("Pobrano dane kontrahneta incydentalnego");
        }
    }

    public void nanieswaloryzacje() {
        List<FakturaWaloryzacja> waloryzacja = fakturaWaloryzacjaDAO.findAll();
        //List<Fakturywystokresowe> okresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        List<Fakturywystokresowe> okresowe = gosciwybralokres;
        if (waloryzacja!=null) {
            for (FakturaWaloryzacja wal : waloryzacja) {
                for (Fakturywystokresowe okresowa : okresowe) {
                    if (tensamnip(wal,okresowa)) {
                        double zapracownika = wal.getUmowaopraceN();
                        double zazleceniobiorce = wal.getUmowazlecenieN();
                        if (zapracownika!=0||zazleceniobiorce!=0) {
                            okresowa.setRecznaedycja(true);
                            fakturywystokresoweDAO.edit(okresowa);
                        }
                        if (okresowa.isWystawtylkoraz()==false) {
                            okresowa.setWystawtylkoraz(true);
                            fakturywystokresoweDAO.edit(okresowa);
                        }
                        boolean zmienionokwote = false;
                        double staraksiegowosc = wal.getKwotabiezacanetto();
                        double nowaksiegowosc = wal.getKwotabiezacanettoN();
                        Faktura faktura = fakturaDAO.findByID(okresowa.getDokument().getId());
                        List<Pozycjenafakturzebazadanych> wiersze = faktura.getPozycjenafakturze();
                        if (staraksiegowosc!=nowaksiegowosc) {
                            double wartosc = nowaksiegowosc>0.0?nowaksiegowosc:staraksiegowosc;
                            Pozycjenafakturzebazadanych wiersz = wiersze.get(0);
                            zrobwiersz(wiersz, wartosc);
                            faktura.setStarakwota(staraksiegowosc);
                            zmienionokwote = true;
                        }
                        if (wiersze.size()>1) {
                            double stareniemcy = wal.getObsluganiemcy();
                            double noweniemcy = wal.getObsluganiemcyN();
                            double wartoscniemcy = noweniemcy>0.0?noweniemcy:stareniemcy;
                            if (stareniemcy!=noweniemcy) {
                                Pozycjenafakturzebazadanych wiersz = wiersze.get(1);
                                zrobwiersz(wiersz, wartoscniemcy);
                            }
                            zmienionokwote = true;
                        }
                        if (zmienionokwote) {
                            faktura.setPozycjenafakturze(wiersze);
                            FakturaBean.ewidencjavat(faktura, evewidencjaDAO);
                            faktura.setDatawaloryzacji(Data.aktualnaData());
                            fakturaDAO.edit(faktura);
                            wal.setNaniesione(true);
                            fakturaWaloryzacjaDAO.edit(wal);
                        }
                        break;
                    }
                }
            }
            fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
    }
    
    private void zrobwiersz(Pozycjenafakturzebazadanych wiersz, double wartosc) {
        if (wiersz!=null) {
            if (wiersz.getNazwa().equals("usługi rachunkowe")) {
                wiersz.setNazwa("usługi rachunkowe #mc#");
            }
            if (wiersz.getNazwa().equals("usługi rachunkowe Polska")) {
                wiersz.setNazwa("usługi rachunkowe Polska #mc#");
            }
            if (wiersz.getNazwa().equals("usługi rachunkowe Niemcy")) {
                wiersz.setNazwa("usługi rachunkowe Niemcy #mc#");
            }
            if (wiersz.getNazwa().contains("wirtualne biuro")) {
                wiersz.setNazwa("wirtualne biuro #mc#");
            }
            wiersz.setCena(wartosc);
            wiersz.setNetto(Z.z(wartosc*wiersz.getIlosc()));
            wiersz.setPodatekkwota(Z.z(wartosc*wiersz.getPodatek()/100));
            wiersz.setBrutto(Z.z(wiersz.getNetto()+wiersz.getPodatekkwota()));
        }
    }
    
    private boolean tensamnip(FakturaWaloryzacja wal, Fakturywystokresowe okresowa) {
        boolean zwrot = false;
        if (wal!=null&&okresowa!=null) {
            if (wal.getKontrahent()!=null&&okresowa.getDokument()!=null&&okresowa.getDokument().getKontrahent()!=null) {
                if (wal.getKontrahent().getNip().equals(okresowa.getDokument().getKontrahent().getNip())) {
                    zwrot = true;
                }
            } else {
                System.out.println("Brak jednego kontrahenta");
            }
        } else {
            System.out.println("Jedna z dwóch pusta");
        }
        return zwrot;
    }
    

    
    public void pobierzczesciowe() {
        if (selected.isKoncowa()==true) {
            String projektnumer = selected.getProjektnumer();
            List<Faktura> czeciowe = fakturaDAO.findbyProjekt(projektnumer, wpisView.getPodatnikObiekt());
            if (czeciowe.isEmpty()) {
                Msg.msg("e", "brak faktur częściowych z podanym numerem projektu");
            } else {
                for (Faktura p : czeciowe) {
                    if (!p.equals(selected)) {
                        selected.getPozycjenafakturze().addAll(zamienznaki(p.getPozycjenafakturze(), p.getNumerkolejny(), p.getDatawystawienia()));
                    }
                }
                Msg.msg("Pobrano faktury częściowe");
            }
        }
    }
    
    private Collection<? extends Pozycjenafakturzebazadanych> zamienznaki(List<Pozycjenafakturzebazadanych> pozycjenafakturze, String numer, String data) {
        List<Pozycjenafakturzebazadanych> zwrot = new ArrayList<>();
        for (Pozycjenafakturzebazadanych p : pozycjenafakturze) {
            Pozycjenafakturzebazadanych pozycjenafakturzebazadanych = new Pozycjenafakturzebazadanych(p);
            pozycjenafakturzebazadanych.zmienznak();
            String opis = "faktua częściowa nr "+numer+" z dnia "+data;
            pozycjenafakturzebazadanych.setNazwa(opis);
            zwrot.add(pozycjenafakturzebazadanych);
        }
        return zwrot;
    }

    
    private void sprawdzczyniezniknalplik(String nazwa, int nrkolejny) {
        try {
             ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwapliku = realPath+"resources/images/logo/"+nazwa;
            File oldfile = new File(nazwapliku);
            if (!oldfile.isFile()) {
                Logofaktura logofaktura = logofakturaDAO.findByPodatnik(wpisView.getPodatnikObiekt(),nrkolejny);
                FileUtils.copyInputStreamToFile(new ByteArrayInputStream(logofaktura.getPliklogo()), new File(nazwapliku));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    public void przygotujfakture() {
        resetujoznaczeniefakur();
        fakturazwykla = true;
        inicjalizacjaczesciwspolne();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(wpisView.getPodatnikObiekt(), fakturaniemiecka));
        selected.setRodzajdokumentu("faktura");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę. Należy uzupełnić pozostałe elementy.");
    }
    public void przygotujfakturevatmarza() {
        resetujoznaczeniefakur();
        fakturavatmarza = true;
        inicjalizacjaczesciwspolne();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(wpisView.getPodatnikObiekt(), fakturaniemiecka));
        selected.setRodzajdokumentu("faktura VAT marża");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę VAT marża. Należy uzupełnić pozostałe elementy.");
    }
    
     public void przygotujrachunek() {
        resetujoznaczeniefakur();
        rachunek = true;
        inicjalizacjaczesciwspolne();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(wpisView.getPodatnikObiekt(), fakturaniemiecka));
        selected.setRodzajdokumentu("rachunek baz VAT");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie rachunek baz VAT. Należy uzupełnić pozostałe elementy.");
    }
    
    public void przygotujfaktureniemiecka() {
        resetujoznaczeniefakur();
        fakturaniemiecka = true;
        inicjalizacjaczesciwspolne();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(wpisView.getPodatnikObiekt(), fakturaniemiecka));
        selected.setRodzajdokumentu("faktura niemiecka");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę niemiecką. Należy uzupełnić pozostałe elementy.");
    }
    
    public void przygotujfakturexxl() {
        resetujoznaczeniefakur();
        fakturaxxl = true;
        inicjalizacjaczesciwspolne();
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt, fakturaniemiecka));
        selected.setRodzajdokumentu("faktura xxl");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę XXL. Należy uzupełnić pozostałe elementy.");
    }
    
     public void przygotujfakturekorekte() {
        resetujoznaczeniefakur();
        fakturazwykla = true;
        fakturakorekta = true;
        inicjalizacjaczesciwspolne();
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt, fakturaniemiecka));
        selected.setPozycjepokorekcie(FakturaBean.inicjacjapozycji(podatnikobiekt, fakturaniemiecka));
        selected.setRodzajdokumentu("faktura korekta");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę. Należy uzupełnić pozostałe elementy.");
    }
     
    public void przygotujfakturekorektexxl() {
        resetujoznaczeniefakur();
        fakturaxxl = true;
        fakturakorekta = true;
        inicjalizacjaczesciwspolne();
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt, fakturaniemiecka));
        selected.setPozycjepokorekcie(FakturaBean.inicjacjapozycji(podatnikobiekt, fakturaniemiecka));
        selected.setRodzajdokumentu("faktura xxl korekta");
        selected.setRodzajtransakcji("sprzedaż");
        selected.setSposobzaplaty(wpisView.getPodatnikObiekt().getDomyslnametodaplatnosci());
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę XXL korektę. Należy uzupełnić pozostałe elementy.");
    }
    
    private void resetujoznaczeniefakur() {
        fakturazwykla = false;
        fakturaxxl = false;
        fakturakorekta = false;
        fakturaniemiecka = false;
        fakturavatmarza = false;
        rachunek = false;
    }
    
    private void inicjalizacjaczesciwspolne() {
        emailkontrahent = null;
        selected = new Faktura();
        selected.setWalutafaktury("PLN");
        if (fakturaxxl) {
            selected.setFakturaxxl(true);
        }
        if (fakturaniemiecka) {
            selected.setFakturaniemiecka13b(true);
            selected.setWalutafaktury("EUR");
        }
        if (fakturavatmarza) {
            selected.setFakturavatmarza(true);
        }
        if (rachunek) {
            selected.setRachunek(true);
        }
        String platnoscwdniach = wpisView.getPodatnikObiekt().getPlatnoscwdni() == null ? "14" : wpisView.getPodatnikObiekt().getPlatnoscwdni();
        selected.setDnizaplaty(Integer.parseInt(platnoscwdniach));
        String pelnadata = FakturaBean.obliczdatawystawienia(wpisView);
        selected.setDatawystawienia(pelnadata);
        selected.setDatasprzedazy(pelnadata);
        selected.setNumerkolejny("wpisz numer");
        selected.setWystawcanazwa(null);
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setMiejscewystawienia(FakturaBean.pobierzmiejscewyst(podatnikobiekt));
        selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(podatnikobiekt, pelnadata));
        selected.setNrkontabankowego(FakturaBean.pobierznumerkonta(podatnikobiekt));
        selected.setSwift(FakturaBean.pobierzswift(podatnikobiekt));
        if (selected.getNrkontabankowego().equals("brak numeru konta bankowego")) {
            selected.setSposobzaplaty("gotówka");
        }
        List<FakturaWalutaKonto> listakontaktywne  = fakturaWalutaKontoDAO.findPodatnik(wpisView);
        if (listakontaktywne!=null) {
            FakturaBean.wielekont(selected, listakontaktywne, fakturaStopkaNiemieckaDAO, wpisView.getPodatnikObiekt());
        }
        selected.setPodpis(FakturaBean.pobierzpodpis(wpisView));
        selected.setAutor(wpisView.getUzer().getNazwiskoImie());
        setPokazfakture(true);
        selected.setWystawca(podatnikobiekt);
        selected.setRok(String.valueOf(wpisView.getRokWpisu()));
        selected.setMc(wpisView.getMiesiacWpisu());
        selected.setFakturagrupa(wpisView.getUzer().getFakturagrupa());
        listakontawwalucie = fakturaWalutaKontoDAO.findByWalutaString(wpisView.getPodatnikObiekt(),selected.getWalutafaktury());
        List<FakturaWalutaKonto> listakontawwalucieObject = fakturaWalutaKontoDAO.findByWaluta(wpisView.getPodatnikObiekt(),selected.getWalutafaktury());
        if (listakontawwalucieObject.size()>1) {
            FakturaWalutaKonto konto = listakontawwalucieObject.get(0);
            selected.setNrkontabankowego(konto.getIban());
            if (konto.getSwift()!=null) {
                selected.setSwift(konto.getSwift());
            }
        }
    }

    public void dodaj() {
        try {
            if (selected.getNumerkolejny().equals("wpisz numer")) {
                Msg.msg("e", "Nie wpisano numeru faktury. Nie zachowano faktury");
                return;
            } else {
                FakturaBean.dodajtabelenbp(selected, tabelanbpDAO);
                FakturaBean.ewidencjavat(selected, evewidencjaDAO);
                if (fakturakorekta) {
                    FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
                }
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd podczas tworzenia rejestru VAT. Nie zachowano faktury");
            return;
        }
        if (selected.getKontrahent() != null) {
            selected.setKontrahent_nip(selected.getKontrahent().getNip());
        } else if (selected.getKontrahent() != null&&selected.getNazwiskoimieincydent()==null){
            Msg.msg("e", "Nie wybrano kontrahenta. Nie zachowano faktury");
            return;
        }
        if (selected.getNazwa()!=null && selected.getNazwa().equals("")) {
            selected.setNazwa(null);
        }
        try {
            if (selected.getPozycjepokorekcie() != null) {
                selected.setRok(Data.getRok(selected.getDatasprzedazy()));
                selected.setMc(Data.getMc(selected.getDatasprzedazy()));
            }
            selected.setFakturagrupa(wpisView.getUzer().getFakturagrupa());
            selected.setDatasporzadzenia(new Date());
            fakturaDAO.create(selected);
            if (selected.getKontrahent()!=null) {
                Klienci kontra = selected.getKontrahent();
                kontra.setAktywnydlafaktrozrachunki(true);
                klienciDAO.edit(kontra);
            }
            init();
            Msg.msg("i", "Dodano fakturę.");
            pokazfakture = false;
            resetujoznaczeniefakur();
            selected = new Faktura();
            
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Błąd. Faktura o takim numerze i dla takiego kontrahenta już istnieje");
        }
//        PrimeFaces.current().executeScript("PF('dokTableFaktury').sort();");
    }
    
    public void edytuj() {
        try {
            FakturaBean.dodajtabelenbp(selected, tabelanbpDAO);
            FakturaBean.ewidencjavat(selected, evewidencjaDAO);
            if (fakturakorekta) {
                FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
            }
        } catch (Exception e) {
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd podczas tworzenia rejestru VAT. Nie zachowano faktury");
            return;
        }
        if (selected.getKontrahent()!=null) {
            selected.setKontrahent_nip(selected.getKontrahent().getNip());
        }
        selected.setRok(Data.getCzescDaty(selected.getDatawystawienia(), 0));
        selected.setMc(Data.getCzescDaty(selected.getDatawystawienia(), 1));
        selected.setFakturagrupa(wpisView.getUzer().getFakturagrupa());
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && wpisView.getPodatnikObiekt().getWystawcafaktury().equals("brak")) {
            selected.setPodpis("");
        } else if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && !wpisView.getPodatnikObiekt().getWystawcafaktury().equals("")) {
            selected.setPodpis(wpisView.getPodatnikObiekt().getWystawcafaktury());
        }  else {
            selected.setPodpis(wpisView.getUzer().getImie() + " " + wpisView.getUzer().getNazw());
        }
        if (selected.getNazwa()!=null && selected.getNazwa().equals("")) {
            selected.setNazwa(null);
        }
        try {
            selected.setDatasporzadzenia(new Date());
            if (selected.isWygenerowanaautomatycznie() == true) {
                selected.setWygenerowanaautomatycznie(false);
            }
            if (selected.isRecznaedycja()) {
                selected.setRecznaedycja(false);
            }
            if (selected.getKontrahent()!=null) {
                Klienci kontra = selected.getKontrahent();
                kontra.setAktywnydlafaktrozrachunki(true);
                klienciDAO.edit(kontra);
            }
            if (selected.getIdfakturaokresowa()!=null && selected.isTylkodlaokresowej()) {
                String nowynumer = String.valueOf(new DateTime().getMillis());
                selected.setNumerkolejny(nowynumer);
                selected.setTylkodlaokresowej(true);
                selected.setDatasporzadzenia(new Date());
                fakturaDAO.create(selected);
                selected.getIdfakturaokresowa().setDokument(selected);
                fakturywystokresoweDAO.edit(selected.getIdfakturaokresowa());
            } else {
                selected.setTylkodlaokresowej(false);
                fakturaDAO.edit(selected);
            }
            if (selected.getIdfakturaokresowa()!=null) {
                selected.getIdfakturaokresowa().setDataedycji(new Date());
                fakturywystokresoweDAO.edit(selected.getIdfakturaokresowa());
            } 
            init();
            Msg.msg("i", "Wyedytowano fakturę.");
            pokazfakture = false;
            resetujoznaczeniefakur();
            selected = new Faktura();
            
        } catch (EJBException e) { 
            E.e(e); 
            Msg.msg("e", "Nie można zachowac zmian. Sprawdź czy numer faktury jest unikalny");
        } catch (Exception ex) { 
            E.e(ex); 
            Msg.msg("e", "Błąd. Niedokonano edycji faktury.");
        }
    }
    
    public void faktItemSelect() {
        try {
            if (selected.getKontrahent().getNpelna().equals("dodaj klienta automatycznie")) {
                Klienci dodany = SzukajDaneBean.znajdzdaneregonAutomat(selected.getKontrahent().getNip());
                selected.setKontrahent(dodany);
                if (!dodany.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                    klienciDAO.create(dodany);
                }
                PrimeFaces.current().ajax().update("akordeon:formstworz:acForce");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void faktObiorcaSelect() {
        try {
            if (selected.getOdbiorca().getNpelna().equals("dodaj klienta automatycznie")) {
                Klienci dodany = SzukajDaneBean.znajdzdaneregonAutomat(selected.getOdbiorca().getNip());
                selected.setOdbiorca(dodany);
                if (!dodany.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                    klienciDAO.create(dodany);
                }
                PrimeFaces.current().ajax().update("akordeon:formstworz:acForce");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void wielekontwtejsamejwalucie() {
        listakontawwalucie = fakturaWalutaKontoDAO.findByWalutaString(wpisView.getPodatnikObiekt(),selected.getWalutafaktury());
        if (listakontawwalucie!=null&&listakontawwalucie.size()>0){
            FakturaBean.wielekont(selected, fakturaWalutaKontoView.getListakontaktywne(), fakturaStopkaNiemieckaDAO, wpisView.getPodatnikObiekt());
            Msg.msg("Zmieniono konto");
        } else {
            Msg.msg("Pobrano listę kont. Wybierz jedno");
        }
        
    }
    
    public void skierujfakturedoedycjiZwykla(Faktura faktura) {
        selected = serialclone.SerialClone.clone(faktura);
        selected.setKontrahent(faktura.getKontrahent());
        fakturaxxl = faktura.isFakturaxxl();
        if (fakturaxxl) {
            //dataTablepozycjenafakturze.setStyle("width: 1280px;");
            //dataTablepozycjenafakturzekorekta.setStyle("width: 1280px;");
        } else {
            //dataTablepozycjenafakturze.setStyle("width: 790px;");
            //dataTablepozycjenafakturzekorekta.setStyle("width: 790px;");
        }
        fakturazwykla = faktura.isFakturaNormalna();
        fakturavatmarza = faktura.isFakturavatmarza();
        fakturaxxl = faktura.isFakturaxxl();
        fakturaniemiecka = faktura.isFakturaniemiecka13b();
        fakturakorekta = faktura.getPozycjepokorekcie() != null;
        rachunek = faktura.isRachunek();
        aktywnytab = 0;
        pokazfakture = true;
        zapis0edycja1 = true;
        if (faktura.getKontrahent()!=null) {
            kontrahentstworz.findComponent(faktura.getKontrahent().getNpelna());
            emailkontrahent = faktura.getKontrahent().getEmail();
        }
        if (faktura.getOdbiorca()!=null) {
            odbiorcastworz.findComponent(faktura.getOdbiorca().getNpelna());
        }
        listakontawwalucie = fakturaWalutaKontoDAO.findByWalutaString(wpisView.getPodatnikObiekt(),selected.getWalutafaktury());
        if (selected.getNrkontabankowego()==null||selected.getNrkontabankowego().equals("")) {
            selected.setNrkontabankowego(FakturaBean.pobierznumerkonta(wpisView.getPodatnikObiekt()));
            selected.setSwift(FakturaBean.pobierzswift(wpisView.getPodatnikObiekt()));
            List<FakturaWalutaKonto> listakontaktywne  = fakturaWalutaKontoDAO.findPodatnik(wpisView);
            if (listakontaktywne!=null) {
                FakturaBean.wielekont(selected, listakontaktywne, fakturaStopkaNiemieckaDAO, wpisView.getPodatnikObiekt());
            }
        }
//        String funkcja = "PF('tworzenieklientapolenazwy').search('"+faktura.getKontrahent_nip()+"');";
//        PrimeFaces.current().executeScript(funkcja);
//        funkcja = "PF('tworzenieklientapolenazwy').activate();";
//        PrimeFaces.current().executeScript(funkcja);
       }
    
    public void skierujfakturedoedycji2023(Fakturywystokresowe fakturaokresowa) {
        if (fakturaokresowa.getKwotaroknastepny()!=0.0) {
            skierujfakturedoedycji(fakturaokresowa);
            List<Pozycjenafakturzebazadanych> pozycjenafakturze = selected.getPozycjenafakturze();
            pozycjenafakturze.get(0).setCena(fakturaokresowa.getKwotaroknastepny());
            edytuj();
            aktywnytab = 6;
            Msg.dP();
        }
    }
    
    public void skierujfakturedoedycji(Fakturywystokresowe fakturaokresowa) {
        Faktura faktura = fakturaokresowa.getDokument();
        faktura.setIdfakturaokresowa(fakturaokresowa);
        fakturaDAO.edit(faktura);
        selected.setTylkodlaokresowej(true);
        selected = serialclone.SerialClone.clone(faktura);
        selected.setKontrahent(faktura.getKontrahent());
        fakturaxxl = faktura.isFakturaxxl();
        if (fakturaxxl) {
            //dataTablepozycjenafakturze.setStyle("width: 1280px;");
            //dataTablepozycjenafakturzekorekta.setStyle("width: 1280px;");
        } else {
            //dataTablepozycjenafakturze.setStyle("width: 790px;");
            //dataTablepozycjenafakturzekorekta.setStyle("width: 790px;");
        }
        fakturazwykla = faktura.isFakturaNormalna();
        fakturavatmarza = faktura.isFakturavatmarza();
        fakturaxxl = faktura.isFakturaxxl();
        fakturaniemiecka = faktura.isFakturaniemiecka13b();
        fakturakorekta = faktura.getPozycjepokorekcie() != null;
        rachunek = faktura.isRachunek();
        aktywnytab = 0;
        pokazfakture = true;
        zapis0edycja1 = true;
        kontrahentstworz.findComponent(faktura.getKontrahent().getNpelna());
        if (faktura.getOdbiorca()!=null) {
            odbiorcastworz.findComponent(faktura.getOdbiorca().getNpelna());
        }
        listakontawwalucie = fakturaWalutaKontoDAO.findByWalutaString(wpisView.getPodatnikObiekt(),selected.getWalutafaktury());
//        String funkcja = "PF('tworzenieklientapolenazwy').search('"+faktura.getKontrahent_nip()+"');";
//        PrimeFaces.current().executeScript(funkcja);
//        funkcja = "PF('tworzenieklientapolenazwy').activate();";
//        PrimeFaces.current().executeScript(funkcja);
       }
    
    public void skierujfakturedokorekty(Faktura faktura) {
        Msg.msg("Tworzenie korekty faktury");
        selected = serialclone.SerialClone.clone(faktura);
        String pelnadata = FakturaBean.obliczdatawystawienia(wpisView);
        selected.setDatawystawienia(pelnadata);
        selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(wpisView.getPodatnikObiekt(), pelnadata));
        selected.setZaksiegowana(false);
        selected.setWyslana(false);
        selected.setPrzyczynakorekty("Korekta faktury nr "+faktura.getNumerkolejny()+" z dnia "+faktura.getDatawystawienia()+" z powodu: brak wykonanej usługi");
        rachunek = faktura.isRachunek();
        fakturazwykla = faktura.isFakturaNormalna();
        fakturavatmarza = faktura.isFakturavatmarza();
        fakturaxxl = faktura.isFakturaxxl();
        fakturaniemiecka = faktura.isFakturaniemiecka13b();
        fakturakorekta = true;
        aktywnytab = 0;
        pokazfakture = true;
        zapis0edycja1 = false;
        selected.setNumerkolejny(selected.getNumerkolejny()+"/KOR");
        selected.setPozycjepokorekcie(utworznowepozycje(faktura.getPozycjenafakturze()));
        listakontawwalucie = fakturaWalutaKontoDAO.findByWalutaString(wpisView.getPodatnikObiekt(),selected.getWalutafaktury());
//        String funkcja = "PF('tworzenieklientapolenazwy').search('"+faktura.getKontrahent_nip()+"');";
//        PrimeFaces.current().executeScript(funkcja);
//        funkcja = "PF('tworzenieklientapolenazwy').activate();";
//        PrimeFaces.current().executeScript(funkcja);
       }
    
    //to sa te automaty co mialy dodawac doatkowe wiersze z managera
    private void dodajwierszedodatkowe(Faktura faktura, Fakturywystokresowe okresowa) {
        List<Pozycjenafakturzebazadanych> pozycje = faktura.getPozycjenafakturze();
        if (faktura.isRecznaedycja()&&faktura.isBilansowa()==false) {
            int czyjestcosdodatkowego = dodajpozycje(faktura.isLiczodwartoscibrutto(), pozycje, faktura.getKontrahent(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), okresowa);
            if (czyjestcosdodatkowego==1) {
                double netto = 0.0;
                double vat = 0.0;
                double brutto = 0.0;
                List<Evewidencja> ew = Collections.synchronizedList(new ArrayList<>());
                ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
                List<EVatwpis> el = Collections.synchronizedList(new ArrayList<>());
                for (Pozycjenafakturzebazadanych p : pozycje) {
                    netto = Z.z(netto+p.getNetto());
                    vat = Z.z(vat+p.getPodatekkwota());
                    brutto = Z.z(brutto+p.getBrutto());
                    EVatwpis eVatwpis = new EVatwpis();
                    Evewidencja ewidencja = zwrocewidencje(ew, p);
                    for (EVatwpis r : el) {
                        if (r.getEwidencja().equals(ewidencja)) {
                            eVatwpis = r;
                        }
                    }
                    if (faktura.getWalutafaktury().equals("PLN")) {
                        if (eVatwpis.getNetto() != 0) {
                            eVatwpis.setNetto(eVatwpis.getNetto() + p.getNetto());
                            eVatwpis.setVat(eVatwpis.getVat() + p.getPodatekkwota());
                            //el.add(eVatwpis);
                        } else {
                            eVatwpis.setEwidencja(ewidencja);
                            eVatwpis.setNetto(p.getNetto());
                            eVatwpis.setVat(p.getPodatekkwota());
                            eVatwpis.setEstawka(String.valueOf(p.getPodatek()));
                            el.add(eVatwpis);
                        }
                    } else {
                         if (eVatwpis.getNetto() != 0) {
                            eVatwpis.setEwidencja(ewidencja);
                            eVatwpis.setNetto(Z.z(p.getNetto()));
                            eVatwpis.setVat(Z.z(p.getPodatekkwota()));
                            eVatwpis.setNettopln(Z.z(p.getNetto(faktura.getTabelanbp())));
                            eVatwpis.setVatpln(Z.z(p.getPodatekkwota(faktura.getTabelanbp())));
                            eVatwpis.setEstawka(String.valueOf(p.getPodatek()));
                            el.add(eVatwpis);
                        } else {
                            eVatwpis.setNetto(Z.z(eVatwpis.getNetto() + p.getNetto()));
                            eVatwpis.setVat(Z.z(eVatwpis.getVat() + p.getPodatekkwota()));
                            eVatwpis.setNettopln(Z.z(eVatwpis.getNettopln()+ p.getNetto(faktura.getTabelanbp())));
                            eVatwpis.setVatpln(Z.z(eVatwpis.getVatpln()+ p.getPodatekkwota(faktura.getTabelanbp())));
                        }
                    }
                }
                faktura.setEwidencjavat(el);
                faktura.setNetto(Z.z(netto));
                faktura.setVat(Z.z(vat));
                faktura.setBrutto(Z.z(brutto));
            }
        }
    }

    
    private int dodajpozycje(boolean liczodbrutto, List<Pozycjenafakturzebazadanych> pozycje, Klienci kontrahent, String rok, String mc, Fakturywystokresowe okresowa) {
        int zwrot = 0;
        String[] okrespoprzedni = Data.poprzedniOkres(mc, rok);
        String rokdod = okrespoprzedni[1];
        String mcdod = okrespoprzedni[0];
        List<Wierszfakturybaza> lista =  wierszfakturybazaDAO.findbyNipRokMc(kontrahent.getNip(), rokdod, mcdod);
        if (lista!=null && lista.size()>0) {
            int lp = 0;
            int wystawiono = pobierzpe(okresowa, mc);
            if (okresowa.isWystawtylkoraz()&&wystawiono==0) {
                lp = pozycje.get(pozycje.size()-1).getLp();
            } else if (okresowa.isWystawtylkoraz()&&wystawiono>0) {
                pozycje.clear();
            }
            for (Wierszfakturybaza p : lista) {
                if (p.isNaniesiony()==false) {
                    double ilosc = p.isWymagakorekty()?p.getNowailosc():p.getIlosc();
                    double cena = p.isWymagakorekty()?p.getNowakwota():p.getKwota();
                    if (ilosc>0&&cena>0.0) {
                        if (liczodbrutto) {
                            double brutto = Z.z(ilosc*cena);
                            double podatek = 23.0;
                            double podatekkwota = Z.z(brutto*podatek/(100.0+podatek));
                            double netto = Z.z(brutto - podatekkwota);
                            Pozycjenafakturzebazadanych nowa = new Pozycjenafakturzebazadanych(++lp, p.getOpis(), "", "osb.", ilosc, cena, netto, podatek, podatekkwota, brutto);
                            nowa.setDodatkowapozycja(p.getId());
                            pozycje.add(nowa);
                        } else {
                            double netto = Z.z(ilosc*cena);
                            double podatek = 23.0;
                            double podatekkwota = Z.z(netto*podatek/100);
                            double brutto = Z.z(netto + podatekkwota);
                            Pozycjenafakturzebazadanych nowa = new Pozycjenafakturzebazadanych(++lp, p.getOpis(), "", "osb.", ilosc, cena, netto, podatek, podatekkwota, brutto);
                            nowa.setDodatkowapozycja(p.getId());
                            pozycje.add(nowa);
                        }
                        p.setNaniesiony(true);
                        p.setDatafaktury(new Date());
                         wierszfakturybazaDAO.edit(p);
                        zwrot = 1;
                    }
                } else {
                    
                }
            }
        }
        return zwrot;
    }
//    private int dodajpozycje(boolean liczodbrutto, List<Pozycjenafakturzebazadanych> pozycje, Klienci kontrahent, String rok, String mc, Fakturywystokresowe okresowa) {
//        int zwrot = 0;
//        List<FakturaDodPozycjaKontrahent> lista =  fakturaDodPozycjaKontrahentDAO.findKontrRokMc(kontrahent, rok, mc);
//        if (lista!=null && lista.size()>0) {
//            int lp = 0;
//            int wystawiono = pobierzpe(okresowa, mc);
//            if (okresowa.isWystawtylkoraz()&&wystawiono==0) {
//                lp = pozycje.get(pozycje.size()-1).getLp();
//            } else if (okresowa.isWystawtylkoraz()&&wystawiono>0) {
//                pozycje.clear();
//            }
//            for (FakturaDodPozycjaKontrahent p : lista) {
//                if (p.isDowygenerowania()) {
//                    double ilosc = p.getIloscdra();
//                    double cena = p.getKwotaindywid()!=0.0 ?p.getKwotaindywid() : p.getFakturaDodatkowaPozycja().getKwota();
//                    if (ilosc>0&&cena>0.0&&p.isRozliczone()==false) {
//                        if (liczodbrutto) {
//                            double brutto = Z.z(ilosc*cena);
//                            double podatek = 23.0;
//                            double podatekkwota = Z.z(brutto*podatek/(100.0+podatek));
//                            double netto = Z.z(brutto - podatekkwota);
//                            Pozycjenafakturzebazadanych nowa = new Pozycjenafakturzebazadanych(++lp, p.getFakturaDodatkowaPozycja().getNazwa(), "", "osb.", ilosc, cena, netto, podatek, podatekkwota, brutto);
//                            nowa.setDodatkowapozycja(p.getId());
//                            pozycje.add(nowa);
//                        } else {
//                            double netto = Z.z(ilosc*cena);
//                            double podatek = 23.0;
//                            double podatekkwota = Z.z(netto*podatek/100);
//                            double brutto = Z.z(netto + podatekkwota);
//                            Pozycjenafakturzebazadanych nowa = new Pozycjenafakturzebazadanych(++lp, p.getFakturaDodatkowaPozycja().getNazwa(), "", "osb.", ilosc, cena, netto, podatek, podatekkwota, brutto);
//                            nowa.setDodatkowapozycja(p.getId());
//                            pozycje.add(nowa);
//                        }
//                        p.setRozliczone(true);
//                        p.setDowygenerowania(false);
//                        p.setDatafaktury(Data.data_yyyyMMdd(new Date()));
//                        fakturaDodPozycjaKontrahentDAO.edit(p);
//                        zwrot = 1;
//                    }
//                } else {
//                    
//                }
//            }
//        }
//        return zwrot;
//    }
   
    
    private void waloryzacjakwoty(Faktura faktura, double proc) throws Exception {
        faktura.setDatawaloryzacji(Data.data_yyyyMMdd(new Date()));
        faktura.setProcentwaloryzacji(proc);
        double procent = proc/100;
        kwotaprzedwaloryzacja = faktura.getBrutto();
        List<Pozycjenafakturzebazadanych> pozycje = faktura.getPozycjenafakturze();
        double netto = 0.0;
        double vat = 0.0;
        double brutto = 0.0;
        List<Evewidencja> ew = Collections.synchronizedList(new ArrayList<>());
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
        List<EVatwpis> el = Collections.synchronizedList(new ArrayList<>());
        for (Pozycjenafakturzebazadanych p : pozycje) {
            double nowacena = p.getBrutto() * procent;
            nowacena = nowacena + p.getBrutto();
            nowacena = Z.zm1(nowacena);
            p.setBrutto(nowacena);
            brutto += nowacena;
            double podatekstawka = p.getPodatek();
            double podatek = nowacena * podatekstawka / (100 + podatekstawka) * 100;
            podatek = Math.round(podatek);
            podatek = podatek / 100;
            vat += podatek;
            p.setPodatekkwota(podatek);
            double nettop = nowacena - podatek;
            netto += nettop;
            p.setNetto(nettop);
            p.setCena(p.getNetto() / p.getIlosc());
            EVatwpis eVatwpis = new EVatwpis();
            Evewidencja ewidencja = zwrocewidencje(ew, p);
            for (EVatwpis r : el) {
                if (r.getEwidencja().equals(ewidencja)) {
                    eVatwpis = r;
                }
            }
            if (eVatwpis.getNetto() != 0) {
                eVatwpis.setNetto(eVatwpis.getNetto() + p.getNetto());
                eVatwpis.setVat(eVatwpis.getVat() + p.getPodatekkwota());
                el.add(eVatwpis);
            } else {
                eVatwpis.setEwidencja(ewidencja);
                eVatwpis.setNetto(p.getNetto());
                eVatwpis.setVat(p.getPodatekkwota());
                eVatwpis.setEstawka(String.valueOf(p.getPodatek()));
                el.add(eVatwpis);
            }
        }
        faktura.setEwidencjavat(el);
        faktura.setNetto(Z.z(netto));
        faktura.setVat(Z.z(vat));
        faktura.setBrutto(Z.z(brutto));

    }
    
     private void waloryzacjakwotyKwota(Faktura faktura, double nowakwotanetto) throws Exception {
        faktura.setDatawaloryzacji(Data.data_yyyyMMdd(new Date()));
        faktura.setProcentwaloryzacji(nowakwotanetto);
        List<Pozycjenafakturzebazadanych> pozycje = faktura.getPozycjenafakturze();
        double netto = 0.0;
        double vat = 0.0;
        double brutto = 0.0;
        List<Evewidencja> ew = Collections.synchronizedList(new ArrayList<>());
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
        List<EVatwpis> el = Collections.synchronizedList(new ArrayList<>());
        for (Pozycjenafakturzebazadanych p : pozycje) {
            double podatekstawka = p.getPodatek();
            double podatek = nowakwotanetto * podatekstawka / (100 + podatekstawka) * 100;
            podatek = Math.round(podatek);
            podatek = podatek / 100;
            vat += podatek;
            p.setPodatekkwota(podatek);
            netto += nowakwotanetto;
            p.setNetto(nowakwotanetto);
            p.setCena(p.getNetto() / p.getIlosc());
            double brutt = Z.z(nowakwotanetto+podatek);
            p.setBrutto(brutt);
            brutto += brutt;
            EVatwpis eVatwpis = new EVatwpis();
            Evewidencja ewidencja = zwrocewidencje(ew, p);
            for (EVatwpis r : el) {
                if (r.getEwidencja().equals(ewidencja)) {
                    eVatwpis = r;
                }
            }
            if (eVatwpis.getNetto() != 0) {
                eVatwpis.setNetto(eVatwpis.getNetto() + p.getNetto());
                eVatwpis.setVat(eVatwpis.getVat() + p.getPodatekkwota());
                el.add(eVatwpis);
            } else {
                eVatwpis.setEwidencja(ewidencja);
                eVatwpis.setNetto(p.getNetto());
                eVatwpis.setVat(p.getPodatekkwota());
                eVatwpis.setEstawka(String.valueOf(p.getPodatek()));
                el.add(eVatwpis);
            }
        }
        faktura.setEwidencjavat(el);
        faktura.setNetto(Z.z(netto));
        faktura.setVat(Z.z(vat));
        faktura.setBrutto(Z.z(brutto));

    }

    private Evewidencja zwrocewidencje(List<Evewidencja> ewidencje, Pozycjenafakturzebazadanych p) {
        for (Evewidencja r : ewidencje) {
            if (r.getNazwa().contains(String.valueOf((int) p.getPodatek()))) {
                return r;
            }
        }
        return null;
    }

    public void destroysporzadzone() {
        for (Faktura p : gosciwybral) {
            try {
                fakturaDAO.remove(p);
                faktury.remove(p);
                usundodatkowewiersze(p);
                if (fakturyFiltered != null) {
                    fakturyFiltered.remove(p);
                }
                if (p.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(p);
                }
                Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getNumerkolejny());
            } catch (Exception e) {
                try {
                    Faktura jestjuzjednausunieta = fakturaDAO.findbyNumerPodatnikDlaOkresowej(p.getNumerkolejny(), p.getWystawca());
                    if (jestjuzjednausunieta != null) {
                        fakturaDAO.remove(p);
                        faktury.remove(p);
                        if (fakturyFiltered != null) {
                            fakturyFiltered.remove(p);
                        }
                        if (p.isWygenerowanaautomatycznie() == true) {
                            zaktualizujokresowa(p);
                        }
                        Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getNumerkolejny());
                    } else {
                        p.setTylkodlaokresowej(true);
                        fakturaDAO.edit(p);
                        faktury.remove(p);
                        if (fakturyFiltered != null) {
                            fakturyFiltered.remove(p);
                        }
                        if (p.isWygenerowanaautomatycznie() == true) {
                            zaktualizujokresowa(p);
                        }
                        Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getNumerkolejny());
                    }
                } catch (Exception e1) {
                    E.e(e1);
                    Msg.msg("e", "Nie usunięto faktury sporządzonej: " + p.getNumerkolejny() + ". Problem z aktualizacją okresowych.");
                }
            }

        }
    }
    
    public void destroysporzadzonepro() {
        for (Faktura p : gosciwybralpro) {
            try {
                fakturaDAO.remove(p);
                fakturypro.remove(p);
                if (fakturyFilteredpro != null) {
                    fakturyFilteredpro.remove(p);
                }
                Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getNumerkolejny());
            } catch (Exception e) { 
                E.e(e); 
                Msg.msg("e", "Nie usunięto faktury sporządzonej: " + p.getNumerkolejny());
            }
        }
    }

    public void wymusdestroysporzadzone(List<Faktura> wybrane) {
        for (Faktura p : wybrane) {
            try {
                List<Fakturywystokresowe> f = fakturywystokresoweDAO.findFakturaOkresowaByFaktura(p);
                boolean mialokresowo = false;
                if (f != null && f.size() > 0) {
                    for (Fakturywystokresowe fw : f) {
                        fakturywystokresoweDAO.remove(fw);
                    }
                    mialokresowo = true;
                }
                fakturaDAO.remove(p);
                faktury.remove(p);
                if (fakturyFiltered != null) {
                    fakturyFiltered.remove(p);
                }
                if (p.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(p);
                }
                Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getNumerkolejny());
                if (mialokresowo) {
                    Msg.msg("i", "Usunięto też fakturę okresową, która powstała na bazie usuwanej");
                }
            } catch (Exception e) { 
                E.e(e); 
                Msg.msg("e", "Nie usunięto faktury sporządzonej: " + p.getNumerkolejny());
            }
        }
    }

    public void destroyarchiwalne() {
        for (Faktura p : gosciwybralarchiuwm) {
            try {
                if (p.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(p);
                }
                p.setIdfakturaokresowa(null);
                fakturaDAO.edit(p);
                fakturaDAO.remove(p);
                fakturyarchiwum.remove(p);
                if (fakturyFiltered != null) {
                    fakturyFiltered.remove(p);
                }
                Msg.msg("i", "Usunięto fakturę archiwalną: " + p.getNumerkolejny());
                usunfakturezaksiegowana(p);
            } catch (Exception e) { 
                E.e(e); 
                Msg.msg("e", "Nie usunięto faktury archiwalnej: " + p.getNumerkolejny());
            }
            
        }
    }
    
    private void usunfakturezaksiegowana(Faktura p) {
        try {
                Dok znajdka = dokDAO.findFaktWystawione(p.getWystawca(), p.getKontrahent(), p.getNumerkolejny(), p.getBrutto());
                dokDAO.remove(znajdka);
                Msg.msg("i", "Usunięto księgowanie faktury: " + p.getNumerkolejny());
            } catch (EJBException e1) {
                Msg.msg("w", "Faktura nie była zaksięgowana");
            } catch (Exception e2) {
                Msg.msg("e", "Błąd! Nie usunięto księgowania faktury: " + p.getNumerkolejny());
            }
    }

    private void zaktualizujokresowa(Faktura nowa) {
        String klientnip = nowa.getKontrahent_nip();
        Double brutto = nowa.getBrutto();
        Podatnik wystawca = nowa.getWystawca();
        String rok = nowa.getRok();
        Fakturywystokresowe fakturaokresowa = null;
        if (nowa.getIdfakturaokresowa() != null) {
            fakturaokresowa = nowa.getIdfakturaokresowa();
        } else {
            fakturaokresowa = fakturywystokresoweDAO.findOkresowa(rok, klientnip, wystawca.getNazwapelna(), nowa.getBrutto());
        }
        switch (nowa.getMc()) {
            case "01":
                fakturaokresowa.setM1(fakturaokresowa.getM1() > 0 ? fakturaokresowa.getM1() - 1 : 0);
                break;
            case "02":
                fakturaokresowa.setM2(fakturaokresowa.getM2() > 0 ? fakturaokresowa.getM2() - 1 : 0);
                break;
            case "03":
                fakturaokresowa.setM3(fakturaokresowa.getM3() > 0 ? fakturaokresowa.getM3() - 1 : 0);
                break;
            case "04":
                fakturaokresowa.setM4(fakturaokresowa.getM4() > 0 ? fakturaokresowa.getM4() - 1 : 0);
                break;
            case "05":
                fakturaokresowa.setM5(fakturaokresowa.getM5() > 0 ? fakturaokresowa.getM5() - 1 : 0);
                break;
            case "06":
                fakturaokresowa.setM6(fakturaokresowa.getM6() > 0 ? fakturaokresowa.getM6() - 1 : 0);
                break;
            case "07":
                fakturaokresowa.setM7(fakturaokresowa.getM7() > 0 ? fakturaokresowa.getM7() - 1 : 0);
                break;
            case "08":
                fakturaokresowa.setM8(fakturaokresowa.getM8() > 0 ? fakturaokresowa.getM8() - 1 : 0);
                break;
            case "09":
                fakturaokresowa.setM9(fakturaokresowa.getM9() > 0 ? fakturaokresowa.getM9() - 1 : 0);
                break;
            case "10":
                fakturaokresowa.setM10(fakturaokresowa.getM10() > 0 ? fakturaokresowa.getM10() - 1 : 0);
                break;
            case "11":
                fakturaokresowa.setM11(fakturaokresowa.getM11() > 0 ? fakturaokresowa.getM11() - 1 : 0);
                break;
            case "12":
                fakturaokresowa.setM12(fakturaokresowa.getM12() > 0 ? fakturaokresowa.getM12() - 1 : 0);
                break;
        }
        if (nowa.isBilansowa()) {
              fakturaokresowa.setM13(fakturaokresowa.getM13() > 0 ? fakturaokresowa.getM13() - 1 : 0);
        }
        fakturywystokresoweDAO.edit(fakturaokresowa);
        fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        Msg.msg("i", "Zaktualizowano okresowa");
        PrimeFaces.current().ajax().update("akordeon:formokresowe:dokumentyOkresowe");
    }

    //taki wiersz do wykorzystania przy robieniu faktur
    public void zachowajwierszwzorcowy() {
        Pozycjenafakturzebazadanych pobranywiersz = selected.getPozycjenafakturze().get(0);
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        podatnik.setWierszwzorcowy(pobranywiersz);
        podatnikDAO.edit(podatnik);
        Msg.msg("i", "Dodatno wiersz wzorcowy " + pobranywiersz.getNazwa());
    }

    public void dodajwiersz() {
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        poz.setPodatek(23);
        poz.setIlosc(1);
        selected.getPozycjenafakturze().add(poz);
    }
    
    public void dodajwierszk() {
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        poz.setPodatek(23);
        poz.setIlosc(1);
        selected.getPozycjepokorekcie().add(poz);
    }

    public void usunwiersz() {
        if (!selected.getPozycjenafakturze().isEmpty()) {
            selected.getPozycjenafakturze().remove(selected.getPozycjenafakturze().size() - 1);
            PrimeFaces.current().ajax().update("akordeon:formstworz:panel");
            String nazwafunkcji = "wybierzrzadfaktury()";
            PrimeFaces.current().executeScript(nazwafunkcji);
        }
    }
    
    public void usunwierszSrodek(Pozycjenafakturzebazadanych wiersz) {
        if (!selected.getPozycjenafakturze().isEmpty()) {
            selected.getPozycjenafakturze().remove(wiersz);
            int i = 1;
            for (Pozycjenafakturzebazadanych p : selected.getPozycjenafakturze()) {
                p.setLp(i++);
            }
            String nazwafunkcji = "wybierzrzadfaktury()";
            PrimeFaces.current().executeScript(nazwafunkcji);
        }
    }
    
    public void usunwierszSrodekk(Pozycjenafakturzebazadanych wiersz) {
        if (!selected.getPozycjepokorekcie().isEmpty()) {
            selected.getPozycjepokorekcie().remove(wiersz);
            int i = 1;
            for (Pozycjenafakturzebazadanych p : selected.getPozycjepokorekcie()) {
                p.setLp(i++);
            }
            String nazwafunkcji = "wybierzrzadfakturykorekta()";
            PrimeFaces.current().executeScript(nazwafunkcji);
        }
    }
    
    public void usunwierszk() {
        if (!selected.getPozycjepokorekcie().isEmpty()) {
            selected.getPozycjepokorekcie().remove(selected.getPozycjepokorekcie().size() - 1);
            PrimeFaces.current().ajax().update("akordeon:formstworz:panelkorekty");
            String nazwafunkcji = "wybierzrzadfakturykorekta()";
            PrimeFaces.current().executeScript(nazwafunkcji);
        }
    }
    
    public void odksieguj(List<Faktura> lista) {
        if (!lista.isEmpty()) {
            for (Faktura p : lista) {
                p.setZaksiegowana(false);
                fakturaDAO.edit(p);
                fakturyarchiwum.remove(p);
                faktury.add(p);
                if (fakturyFilteredarchiwum!=null) {
                    fakturyFilteredarchiwum.remove(p);
                }
            }
            Msg.msg("i", "Dokumenty odksięgowane");
        }
    }
    
    
    public void usunwszystkie()  {
        if (faktury_edit_select!=null) {
            try {
                fakturaDAO.removeList(faktury_edit_select);
                faktury_edit.remove(faktury_edit_select);
                faktury_edit_select = null;
                faktury_edit_filter = null;
                Msg.msg("Usunięto wybrane faktury");
            } catch (Exception e){
                Msg.msg("e","Błąd podczas usuwania faktur");
            }
        }
    }

    public void zaakceptuj(List<Faktura> lista)  {
        if (lista != null)
        if (faktury==null) {
            faktury = new ArrayList<>();
        }
        for (Faktura p : lista) {
            p.setRecznaedycja(false);
            faktury.add(p);
        }
        fakturaDAO.editList(lista);
        Msg.msg("Zaakceptowano zbiorczo faktury");
    }
    
    
    public void zaksieguj(List<Faktura> lista) throws Exception {
        boolean vatowiec = nievat0vat1(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        boolean ksiazka = wpisView.isRyczalt0ksiega1();
        if (wpisView.getPodatnikObiekt().getFirmafk() == 1) {
            for (Faktura p : lista) {
                ksiegowanieFK(p, p.getWystawca(), p.getKontrahent(),0, vatowiec);
            }
        } else if (wpisView.getPodatnikObiekt().getFirmafk() == 0) {
            for (Faktura p : lista) {
                String rodzajdok = "SZ";
                if (ksiazka) {
                    rodzajdok = "SZ";
                } else {
                    rodzajdok = "SPRY";
                }
                ksiegowaniePkpirVAT(p, p.getWystawca(), p.getKontrahent(),0, rodzajdok);
            }
        } else {
            if (wpisView.isKsiegirachunkowe() == true) {
                for (Faktura p : lista) {
                    ksiegowanieFK(p, p.getWystawca(), p.getKontrahent(),0, vatowiec);
                }
            } else {
                for (Faktura p : lista) {
                    String rodzajdok = "SZ";
                if (ksiazka) {
                    rodzajdok = "SZ";
                } else {
                    rodzajdok = "SPRY";
                }
                ksiegowaniePkpirVAT(p, p.getWystawca(), p.getKontrahent(),0, rodzajdok);
                }
            }
        }
    }
    
    
    public void zaksiegujUOdbiorcow(List<Faktura> lista){
        if (lista!=null && lista.size()>0) {
        for (Faktura p : lista) {
            Klienci odbiorca = p.getKontrahent();
            Podatnik  wystawca = p.getWystawca();
            if (odbiorca!=null && p.isZaksiegowanakontrahent()==false) {
                Podatnik podatnikdocelowy = podatnikDAO.findPodatnikByNIP(odbiorca.getNip());
                if (podatnikdocelowy!=null) {
                    Klienci wystawcajakoklient = klienciDAO.findKlientByNip(wystawca.getNip());
                    if (wystawcajakoklient!=null) {
                        int pkpir0fk1 = zwrocFormaOpodatkowania(podatnikdocelowy, p.getRok());
                        String miesiac = Data.getMc(p.getDatawystawienia());
                        String rok = Data.getRok(p.getDatawystawienia());
                        boolean vatowiec = nievat0vat1(podatnikdocelowy, Integer.parseInt(rok), miesiac);
                        try {
                            if (pkpir0fk1==0) {
                                if (vatowiec) {
                                    ksiegowaniePkpirVAT(p, podatnikdocelowy, wystawcajakoklient, 1, "ZZ");
                                } else {
                                    ksiegowaniePkpir(p, podatnikdocelowy, wystawcajakoklient, 1, "RACH");
                                }
                            } else {
                                ksiegowanieFK(p, podatnikdocelowy, wystawcajakoklient, 1, vatowiec);
                            }
                        } catch (Exception e) {
                            Msg.msg("e","Błąd przy generowaniu dokumentu");
                        }
                    }
                }
            }
        }
        Msg.msg("Zaksięgowano dokumenty");
        } else {
            Msg.msg("e","Nie wybrano faktur do zaksięgowania u odbiorcy");
        }
    }
    
    private int zwrocFormaOpodatkowania(Podatnik podatnik, String rok) {
        int pkpir0fk1 = 0;
        PodatnikOpodatkowanieD opodatkowanie = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(podatnik, rok);
        if (opodatkowanie !=null) {
            String rodzajopodatkowania = opodatkowanie.getFormaopodatkowania();
              if (rodzajopodatkowania.contains("księgi rachunkowe")) {
                  pkpir0fk1 = 1;
              }
        }
        return pkpir0fk1;
    }
    
    
    private void ksiegowanieFK(Faktura faktura,Podatnik podatnik, Klienci kontrahent, int podatnik0kontrahent, boolean vatowiec) {
        if (faktura.getNetto() != faktura.getBrutto() && (faktura.getEwidencjavat() == null || faktura.getEwidencjavat().size() == 0)) {
            FakturaBean.ewidencjavat(faktura, evewidencjaDAO);
            if (faktura.getPozycjepokorekcie() != null && !faktura.getPozycjepokorekcie().isEmpty()) {
                FakturaBean.ewidencjavatkorekta(faktura, evewidencjaDAO);
            }
        }
        try {
            if (podatnik0kontrahent==0) {
                String rodzajdok = "SZ";
                if (Z.z(faktura.getVat())==0.0) {
                    rodzajdok= "WDT";
                }
                Dokfk dokument = FDfkBean.stworznowydokument(FDfkBean.oblicznumerkolejny(rodzajdok, dokDAOfk, podatnik, wpisView.getRokWpisuSt()),faktura, rodzajdok, podatnik, kontrahent, wpisView, rodzajedokDAO, tabelanbpDAO, walutyDAOfk, kontoDAOfk, kliencifkDAO, evewidencjaDAO,podatnik0kontrahent, null);
                dokument.setImportowany(true);
                dokument.setFaktura(faktura);
                dokDAOfk.create(dokument);
                faktura.setZaksiegowana(true);
                fakturaDAO.edit(faktura);
                Msg.msg("Zaksięgowano dokument "+rodzajdok+" o nr własnym"+dokument.getNumerwlasnydokfk());
            } else {
                Dokfk poprzedni = dokDAOfk.findDokfkLastofaTypeKontrahent(podatnik, "ZZ", kontrahent, wpisView.getRokWpisuSt(), null);
                try {
                    if (poprzedni==null) {
                        poprzedni = dokDAOfk.findDokfkLastofaTypeKontrahent(podatnik, "ZZ", kontrahent, wpisView.getRokUprzedniSt(), null);
                        if (poprzedni!=null) {
                            //to jest ok. trzeba pobrac konta tylkokwtedy gdy poprzenie jest z porpzedniego roku
                            String konto0Wn = poprzedni.getListawierszy().get(0).getKontoWn().getPelnynumer();
                            String konto0Ma = poprzedni.getListawierszy().get(0).getKontoMa().getPelnynumer();
                            String konto1Wn = poprzedni.getListawierszy().get(1).getKontoWn().getPelnynumer();
                            Konto kontoWn0 = kontoDAOfk.findKonto(konto1Wn, podatnik, wpisView.getRokWpisu());
                            Konto kontoMa0 = kontoDAOfk.findKonto(konto1Wn, podatnik, wpisView.getRokWpisu());
                            Konto kontoWn1 = kontoDAOfk.findKonto(konto1Wn, podatnik, wpisView.getRokWpisu());
                            poprzedni.getListawierszy().get(0).getStronaWn().setKonto(kontoWn0);
                            poprzedni.getListawierszy().get(0).getStronaMa().setKonto(kontoMa0);
                            poprzedni.getListawierszy().get(1).getStronaWn().setKonto(kontoWn1);
                        }
                    }
                } catch (Exception e) {}
                Dokfk dokument = null;
                if (vatowiec) {
                     dokument = FDfkBean.stworznowydokument(FDfkBean.oblicznumerkolejny("ZZ", dokDAOfk, podatnik, wpisView.getRokWpisuSt()),faktura, "ZZ", podatnik, kontrahent, wpisView, rodzajedokDAO, tabelanbpDAO, walutyDAOfk, kontoDAOfk, kliencifkDAO, evewidencjaDAO,podatnik0kontrahent, poprzedni);
                } else {
                    dokument = FDfkBean.stworznowydokumentNieVAT(FDfkBean.oblicznumerkolejny("RACH", dokDAOfk, podatnik, wpisView.getRokWpisuSt()),faktura, "RACH", podatnik, kontrahent, wpisView, rodzajedokDAO, tabelanbpDAO, walutyDAOfk, kontoDAOfk, kliencifkDAO, evewidencjaDAO,podatnik0kontrahent, poprzedni);
                }
                if (dokument!=null) {
                    dokument.setImportowany(true);
                    dokument.setFakturakontrahent(faktura);
                    dokDAOfk.create(dokument);
                    faktura.setZaksiegowanakontrahent(true);
                    fakturaDAO.edit(faktura);
                    Msg.msg("Zaksięgowano dokument ZZ/RACH o nr własnym"+dokument.getNumerwlasnydokfk());
                } else {
                    Msg.msg("e", "Błąd podczas generowania dokumentu");
                }
            }
        } catch (javax.ejb.EJBException e1) {
            E.e(e1); 
            Msg.msg("e", "Próba zaksięgowania duplikatu!");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu SZ/ZZ"+E.e(e));
        }
    }
    private void ksiegowaniePkpirVAT(Faktura p ,Podatnik podatnik, Klienci kontrahent, int podatnik0kontrahent, String rodzajdokumentu) {
            Faktura faktura = p;
            Dok selDokument = new Dok();
            selDokument.setEwidencjaVAT1(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = faktura.getDatawystawienia();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(podatnik);
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(faktura.getDatawystawienia());
            selDokument.setDataSprz(faktura.getDatasprzedazy());
            selDokument.setKontr(kontrahent);
            selDokument.setTabelanbp(p.getTabelanbp());
            Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdokumentu, podatnik, wpisView.getRokWpisuSt());
            selDokument.setOznaczenie1(rodzajedok.getOznaczenie1());
            selDokument.setOznaczenie2(rodzajedok.getOznaczenie2());
            selDokument.setRodzajedok(rodzajedok);
            selDokument.setNrWlDk(faktura.getNumerkolejny());
            selDokument.setOpis(faktura.getPozycjenafakturze().get(0).getNazwa());
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(faktura.getNettoPrzelicz());
            tmpX.setNettowaluta(faktura.getNettoPrzeliczWal());
            tmpX.setVatwaluta(faktura.getVatPrzeliczWal());
            tmpX.setVat(faktura.getVatPrzelicz());
            tmpX.setNazwakolumny("przych. sprz");
            if (wpisView.isRyczalt0ksiega1()==false) {
                if (stawkaryczaltuksiegowanie!=null) {
                    String kolumnanazwa = Kolmn.zwrockolumnyR("ryczałt").stream().filter(item->item.contains(stawkaryczaltuksiegowanie)).findFirst().orElse(null);
                    if (kolumnanazwa!=null) {
                        tmpX.setNazwakolumny(kolumnanazwa);
                    }
                }
            }
            if (podatnik0kontrahent==1) {
                tmpX.setNazwakolumny("poz. koszty");
            }
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(faktura.getBruttoPrzelicz()));
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(tmpX.getNetto());
            selDokument.setBrutto(tmpX.getBrutto());
            selDokument.setRozliczony(true);
            if (faktura.getTabelanbp()!=null) {
                selDokument.setWalutadokumentu(faktura.getTabelanbp().getWaluta());
            } else {
                selDokument.setWalutadokumentu(domyslatabela.getWaluta());
            }
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            for (EVatwpis r : faktura.getEwidencjavat()) {
                Evewidencja odnalezionaewidencja = evewidencjaDAO.znajdzponazwie(r.getEwidencja().getNazwa());
                if (podatnik0kontrahent==1) {
                    odnalezionaewidencja = evewidencjaDAO.znajdzponazwie("zakup");
                }
                if (faktura.getEwidencjavatpk() != null) {
                    EVatwpis s  = null;
                    for (EVatwpis t : faktura.getEwidencjavatpk()) {
                        if (t.getEwidencja().equals(r.getEwidencja())) {
                            s = t;
                        }
                    }
                    if (s != null) {
                        EVatwpis1 eVatwpis1 = new EVatwpis1(odnalezionaewidencja, s.getNettopln()-r.getNettopln(), s.getVatpln()-r.getVatpln(), r.getEstawka(), p.getMc(), p.getRok());
                        if (r.getNettopln()==0.0 && r.getVatpln() == 0.0) {
                            eVatwpis1 = new EVatwpis1(odnalezionaewidencja, s.getNetto()-r.getNetto(), s.getVat()-r.getVat(), r.getEstawka(), p.getMc(), p.getRok());
                        }
                        eVatwpis1.setDok(selDokument);
                        ewidencjaTransformowana.add(eVatwpis1);
                    } else {
                        EVatwpis1 eVatwpis1 = new EVatwpis1(odnalezionaewidencja, -r.getNettopln(), -r.getVatpln(), r.getEstawka(), p.getMc(), p.getRok());
                        if (r.getNettopln()==0.0 && r.getVatpln() == 0.0) {
                            eVatwpis1 = new EVatwpis1(odnalezionaewidencja, -r.getNetto(), -r.getVat(), r.getEstawka(), p.getMc(), p.getRok());
                        }
                        eVatwpis1.setDok(selDokument);
                        ewidencjaTransformowana.add(eVatwpis1);
                    }
                } else {
                    EVatwpis1 eVatwpis1 = new EVatwpis1(odnalezionaewidencja, r.getNettopln(), r.getVatpln(), r.getEstawka(), p.getMc(), p.getRok());
                    if (r.getNettopln()==0.0 && r.getVatpln() == 0.0) {
                        eVatwpis1 = new EVatwpis1(odnalezionaewidencja, r.getNetto(), r.getVat(), r.getEstawka(), p.getMc(), p.getRok());
                    }
                    eVatwpis1.setDok(selDokument);
                    ewidencjaTransformowana.add(eVatwpis1);
                }
                if (r.getEwidencja().getNazwa().equals("usługi świad. poza ter.kraju art. 100 ust.1 pkt 4")) {
                    Rodzajedok rodzajedok2 = rodzajedokDAO.find("UPTK100", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                    selDokument.setRodzajedok(rodzajedok2);
                    selDokument.setOznaczenie1(rodzajedok.getOznaczenie1());
                    selDokument.setOznaczenie2(rodzajedok.getOznaczenie2());
                }
                    //tutaj
                }
                selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
             try {
                Dok tmp = null;
                if (selDokument.getKontr()!=null) {
                    tmp = dokDAO.znajdzDuplikat(selDokument, selDokument.getPkpirR());
                }
                if (tmp==null) {
                    dokDAO.create(selDokument);
                    if (podatnik0kontrahent==0) {
                        selDokument.setFaktura(faktura);
                        faktura.setZaksiegowana(true);
                    } else {
                        selDokument.setFakturakontrahent(faktura);
                        faktura.setZaksiegowanakontrahent(true);
                    }
                    dokDAO.edit(selDokument);
                    String kontrahentnazwa = selDokument.getKontr()!=null?selDokument.getKontr().getNpelna():selDokument.getFaktura().getNazwiskoimieincydent();
                    String wiadomosc = "Zaksięgowano fakturę sprzedaży nr: " + selDokument.getNrWlDk() + ", kontrahent: " + kontrahentnazwa + ", kwota: " + selDokument.getBrutto();
                    Msg.msg("i", wiadomosc);
                    fakturaDAO.edit(faktura);
                } else {
                    Msg.msg("e", "Próba zaksięgowania duplikatu!");
                }
            } catch (javax.ejb.EJBException e1) {
                E.e(e1); 
                Msg.msg("e", "Próba zaksięgowania duplikatu!");
            } catch (Exception e) { 
                E.e(e); 
                Msg.msg("e","Błąd poczask księgowania faktury u klienta "+E.e(e));
            }
            PrimeFaces.current().ajax().update("akordeon:formsporzadzone:dokumentyLista");
    }
    
    private void ksiegowaniePkpir(Faktura p ,Podatnik podatnik, Klienci kontrahent, int podatnik0kontrahent, String rodzajdokumentu) {
            Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdokumentu, podatnik, wpisView.getRokWpisuSt());
            if (rodzajedok!=null) {
                Faktura faktura = p;
                Dok selDokument = new Dok();
                selDokument.setEwidencjaVAT1(null);
                HttpServletRequest request;
                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                Principal principal = request.getUserPrincipal();
                selDokument.setWprowadzil(principal.getName());
                String datawystawienia = faktura.getDatawystawienia();
                String miesiac = datawystawienia.substring(5, 7);
                String rok = datawystawienia.substring(0, 4);
                selDokument.setPkpirM(miesiac);
                selDokument.setPkpirR(rok);
                selDokument.setVatM(miesiac);
                selDokument.setVatR(rok);
                selDokument.setPodatnik(podatnik);
                selDokument.setStatus("bufor");
                selDokument.setUsunpozornie(false);
                selDokument.setDataWyst(faktura.getDatawystawienia());
                selDokument.setDataSprz(faktura.getDatawystawienia());
                selDokument.setKontr(kontrahent);
                selDokument.setTabelanbp(p.getTabelanbp());
                selDokument.setOznaczenie1(rodzajedok.getOznaczenie1());
                selDokument.setOznaczenie2(rodzajedok.getOznaczenie2());
                selDokument.setRodzajedok(rodzajedok);
                selDokument.setNrWlDk(faktura.getNumerkolejny());
                selDokument.setOpis(faktura.getPozycjenafakturze().get(0).getNazwa());
                List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
                KwotaKolumna1 tmpX = new KwotaKolumna1();
                tmpX.setNetto(faktura.getBruttoPrzelicz());
                tmpX.setNettowaluta(faktura.getBruttoPrzeliczWal());
                tmpX.setVatwaluta(0.0);
                tmpX.setVat(0.0);
                tmpX.setNazwakolumny("przych. sprz");
                if (podatnik0kontrahent==1) {
                    tmpX.setNazwakolumny("poz. koszty");
                }
                tmpX.setDok(selDokument);
                tmpX.setBrutto(Z.z(faktura.getBruttoPrzelicz()));
                listaX.add(tmpX);
                selDokument.setListakwot1(listaX);
                selDokument.setNetto(tmpX.getBrutto());
                selDokument.setBrutto(tmpX.getBrutto());
                selDokument.setRozliczony(true);
                if (faktura.getTabelanbp()!=null) {
                    selDokument.setWalutadokumentu(faktura.getTabelanbp().getWaluta());
                } else {
                    selDokument.setWalutadokumentu(domyslatabela.getWaluta());
                }
                try {
                    Dok tmp = dokDAO.znajdzDuplikat(selDokument, selDokument.getPkpirR());
                    if (tmp==null) {
                        dokDAO.create(selDokument);
                        if (podatnik0kontrahent==0) {
                            selDokument.setFaktura(faktura);
                            faktura.setZaksiegowana(true);
                        } else {
                            selDokument.setFakturakontrahent(faktura);
                            faktura.setZaksiegowanakontrahent(true);
                        }
                        dokDAO.edit(selDokument);
                        String wiadomosc = "Zaksięgowano fakturę sprzedaży nr: " + selDokument.getNrWlDk() + ", kontrahent: " + selDokument.getKontr().getNpelna() + ", kwota: " + selDokument.getBrutto();
                        Msg.msg("i", wiadomosc);
                        fakturaDAO.edit(faktura);
                    } else {
                        Msg.msg("e", "Próba zaksięgowania duplikatu!");
                    }
                } catch (javax.ejb.EJBException e1) {
                    E.e(e1); 
                    Msg.msg("e", "Próba zaksięgowania duplikatu!");
                } catch (Exception e) { 
                    E.e(e); 
                    Msg.msg("e","Błąd poczask księgowania faktury u klienta "+E.e(e));
                }
                PrimeFaces.current().ajax().update("akordeon:formsporzadzone:dokumentyLista");
            }
    }
    
    private boolean nievat0vat1(Podatnik podatnik, Integer rok, String mic) {
        boolean zwrot = false;
        Integer mc = Integer.parseInt(mic);
        List<Parametr> parametry = podatnik.getVatokres();
        String czyjestvat =  zwrocParametr(parametry, rok, mc);
        if (czyjestvat.equals("blad")) {
            zwrot = false;
        } else {
            zwrot = true;
        }
        return zwrot;
    }
    
    public static String zwrocParametr(List parametry, Integer rok, Integer mc) {
        String zwrot = "blad";
        if (parametry != null) {
            for (Object x : parametry) {
                ParamSuper p = (ParamSuper) x;
                if (p.getRokDo() != null && !"".equals(p.getRokDo())) {
                    int wynikPo = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                    int wynikPrzed = Data.compare(rok, mc, Integer.parseInt(p.getRokDo()), Integer.parseInt(p.getMcDo()));
                    if (wynikPo > -1 && wynikPrzed < 1) {
                        zwrot = p.getParametr();
                    }
                } else {
                    int wynik = Data.compare(rok, mc, Integer.parseInt(p.getRokOd()), Integer.parseInt(p.getMcOd()));
                    if (wynik >= 0) {
                        zwrot = p.getParametr();
                    }
                }
            }
        }
        return zwrot;
    }
   

    public void wgenerujnumerfaktury()  {
        if (zapis0edycja1 == false && fakturakorekta == false) {
            String nazwaklienta = (String) Params.params("akordeon:formstworz:acForce_input");
            if (!nazwaklienta.equals("nowy klient")) {
                if (selected!=null && selected.getKontrahent()!=null) {
                    if (selected.getKontrahent().getNskrocona() == null) {
                        Msg.msg("e", "Brak nazwy skróconej kontrahenta " + selected.getKontrahent().getNpelna() + ", nie mogę poprawnie wygenerować numeru faktury. Uzupełnij dane odbiorcy faktury.");
                        PrimeFaces.current().executeScript("PF('nazwaskroconafaktura').show();");
                        PrimeFaces.current().executeScript("$(document.getElementById(\"formkontowybor:wybormenu\")).focus();");
                    } else {
                        FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
                    }
                }
            }
        }
    }
    
    public void wgenerujnumerfakturyFaktura(Faktura selected)  {
        emailkontrahent = selected.getKontrahent()!=null?selected.getKontrahent().getEmail():null;
        if (selected.getKontrahent_nip()!=null&&selected.getKontrahent_nip()!=null) {
            List<Klienci> findKlienciByNip = klienciDAO.findKlienciByNip(selected.getKontrahent_nip());
            if (findKlienciByNip!=null&&findKlienciByNip.size()>0) {
                if (selected.getKontrahent()==null) {
                    selected.setKontrahent(findKlienciByNip.get(0));
                }
            }
        }
        if (zapis0edycja1 == false && fakturakorekta == false) {
            String nazwaklienta = (String) Params.params("akordeon:formstworz:acForce_input");
            if (!nazwaklienta.equals("nowy klient")) {
                if (selected!=null && selected.getKontrahent()!=null) {
                    if (selected.getKontrahent().getNskrocona() == null) {
                        Msg.msg("e", "Brak nazwy skróconej kontrahenta " + selected.getKontrahent().getNpelna() + ", nie mogę poprawnie wygenerować numeru faktury. Uzupełnij dane odbiorcy faktury.");
                        PrimeFaces.current().executeScript("PF('nazwaskroconafaktura').show();");
                        PrimeFaces.current().executeScript("$(document.getElementById(\"formkontowybor:wybormenu\")).focus();");
                    } else {
                        FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
                    }
                } else if (selected.getNazwiskoimieincydent()!=null) {
                    FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
                }
            }
        }
    }
    
     public void wgenerujnumerfakturyLikwidacjaProforma()  {
        String nazwaklienta = (String) Params.params("akordeon:formstworz:acForce_input");
        if (!nazwaklienta.equals("nowy klient")) {
            if (selected!=null && selected.getKontrahent()!=null) {
                if (selected.getKontrahent().getNskrocona() == null) {
                    Msg.msg("e", "Brak nazwy skróconej kontrahenta " + selected.getKontrahent().getNpelna() + ", nie mogę poprawnie wygenerować numeru faktury. Uzupełnij dane odbiorcy faktury.");
                    PrimeFaces.current().executeScript("PF('nazwaskroconafaktura').show();");
                    PrimeFaces.current().executeScript("$(document.getElementById(\"formkontowybor:wybormenu\")).focus();");
                } else {
                    FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
                }
            }
        }
    }
    
    

    public void dodajfaktureokresowa(List<Faktura> gosciwybral) {
        if (gosciwybral != null && gosciwybral.size()>0) {
            for (Faktura p : gosciwybral) {
                String podatnik = wpisView.getPodatnikWpisu();
                Fakturywystokresowe nowafakturaokresowa = new Fakturywystokresowe();
                nowafakturaokresowa.setDokument(new Faktura(p, wpisView.getRokWpisuSt()));
                nowafakturaokresowa.setPodatnik(podatnik);
                try {
                    Podatnik podid = podatnikDAO.findPodatnikByNIP(p.getKontrahent().getNip());
                    if (podid!=null) {
                        nowafakturaokresowa.setPodid(podid);
                    }
                } catch (Exception es){};
                nowafakturaokresowa.setWystawtylkoraz(true);
                naznaczmiesiacnafakturzeokresowej(nowafakturaokresowa, p);
                nowafakturaokresowa.setBrutto(p.getBrutto());
                nowafakturaokresowa.setNipodbiorcy(p.getKontrahent_nip());
                String rok = p.getDatasprzedazy().split("-")[0];
                nowafakturaokresowa.setRok(rok);
                nowafakturaokresowa.setDatautworzenia(new Date());
                nowafakturaokresowa.setAutor(wpisView.getUzer().getNazwiskoImie());
                try {
                    fakturywystokresoweDAO.create(nowafakturaokresowa);
                    p.setIdfakturaokresowa(nowafakturaokresowa);
                    fakturaDAO.edit(p);
                    fakturyokresowe.add(nowafakturaokresowa);
                    Msg.msg("i", "Dodano fakturę okresową");
                } catch (Exception e) { 
                    E.e(e); 
                    Msg.msg("e", "Błąd podczas zachowania faktury w bazie danych " + e.getMessage());
                }
            }
            PrimeFaces.current().ajax().update("akordeon:formokresowe:dokumentyOkresowe");
        }
    }
    
    private void naznaczmiesiacnafakturzeokresowej(Fakturywystokresowe nowafakturaokresowa, Faktura p) {
        String mc = p.getMc();
        switch (mc) {
            case "01":
                nowafakturaokresowa.setM1(1);
                break;
            case "02":
                nowafakturaokresowa.setM2(1);
                break;
            case "03":
                nowafakturaokresowa.setM3(1);
                break;
            case "04":
                nowafakturaokresowa.setM4(1);
                break;
            case "05":
                nowafakturaokresowa.setM5(1);
                break;
            case "06":
                nowafakturaokresowa.setM6(1);
                break;
            case "07":
                nowafakturaokresowa.setM7(1);
                break;
            case "08":
                nowafakturaokresowa.setM8(1);
                break;
            case "09":
                nowafakturaokresowa.setM9(1);
                break;
            case "10":
                nowafakturaokresowa.setM10(1);
                break;
            case "11":
                nowafakturaokresowa.setM11(1);
                break;
            case "12":
                nowafakturaokresowa.setM12(1);
                break;
        }
        if (p.isBilansowa()) {
            nowafakturaokresowa.setM13(1);
        }
    }
    
    public void dodajfaktureokresowanowyrok(Faktura p) {
        String podatnik = wpisView.getPodatnikWpisu();
        Fakturywystokresowe fakturyokr = new Fakturywystokresowe();
        fakturyokr.setDokument(p);
        try {
            Podatnik podid = podatnikDAO.findPodatnikByNIP(p.getKontrahent().getNip());
            if (podid!=null) {
                fakturyokr.setPodid(podid);
            }
        } catch (Exception es){};
        fakturyokr.setPodatnik(podatnik);
        fakturyokr.setRok(wpisView.getRokWpisu().toString());
        fakturyokr.setBrutto(p.getBrutto());
        fakturyokr.setNipodbiorcy(p.getKontrahent_nip());
        String rok = p.getDatasprzedazy().split("-")[0];
        try {
            Fakturywystokresowe fakturatmp = null;
            if (kwotaprzedwaloryzacja > 0) {
                fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik, p.getBrutto());
                //no bo jak sie juz zrobi z waloryzacja a potem usuwa to jest zaktualizowane
                if (fakturatmp == null) { 
                    fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik, p.getBrutto());
                }
            } else {
                fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik, p.getBrutto());
            }
            if (fakturatmp != null) {
                if (kwotaprzedwaloryzacja > 0) {
                    fakturatmp.setBrutto(p.getBrutto());
                }
                fakturatmp.setM1(1);
                fakturywystokresoweDAO.edit(fakturatmp);
                fakturyokresowe.clear();
                fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                Collections.sort(fakturyokresowe, new Fakturyokresowecomparator());
            } else {
                throw new Exception();
            }
        } catch (Exception ef) {
            fakturyokr.setM1(1);
            fakturyokresowe.add(fakturyokr);
            fakturywystokresoweDAO.create(fakturyokr);
            Msg.msg("i", "Dodano fakturę okresową");
        }
        PrimeFaces.current().ajax().update("akordeon:formokresowe:dokumentyOkresowe");
    }

    public void usunfaktureokresowa() {
        for (Fakturywystokresowe p : gosciwybralokres) {
            p.setBiezaca0archiwalna1(true);
            fakturywystokresoweDAO.edit(p);
            fakturyokresowe.remove(p);
            if (fakturyokresoweFiltered != null) {
                fakturyokresoweFiltered.remove(p);
            }
            Msg.msg("i", "Usunięto fakturę okresową");
        }
        PrimeFaces.current().ajax().update("akordeon:formokresowe:dokumentyOkresowe");
    }

    public void wygenerujzokresowychwaloryzacja() {
        wygenerujzokresowych();
        waloryzajca = 0.0;
        PrimeFaces.current().ajax().update("akordeon:formokresowe:kwotawaloryzacji");
        PrimeFaces.current().ajax().update("akordeon:formsporzadzone:dokumentyLista");
        PrimeFaces.current().ajax().update("akordeon:formokresowe:dokumentyOkresowe");

    }
    
    public void dodajwierszsamochod() {
        if (selected.getPozycjenafakturze() != null) {
            Pozycjenafakturzebazadanych p = selected.getPozycjenafakturze().get(0);
            StringBuilder sb = new StringBuilder();
            sb.append("sprzedaż samochodu marki ");
            sb.append(selected.getMarkapojazdu());
            sb.append(" ");
            sb.append("o numerze identyfikacyjnym VIN ");
            sb.append(selected.getVIN());
            p.setNazwa(sb.toString());
        }
    }

    public void wygenerujzokresowychreczne() {
        List<Faktura> listafakturzamiesiac = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (Iterator<Fakturywystokresowe> it = gosciwybralokres.iterator(); it.hasNext();) {
            Fakturywystokresowe p = it.next();
            if (p.isRecznaedycja()==false || sprawdzmiesiac(p, listafakturzamiesiac)) {
                it.remove();
            }
        }
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury ręcznych lub wybrano tylko faktury jednorazowe");
        } else {
            wygenerujzokresowychcd();
        }
    }
    
    public void wygenerujzokresowychbilansowe() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury ręcznych lub wybrano tylko faktury jednorazowe");
        } else {
            List<Faktura> faktury = wygenerujzokresowychcd();
            for (Faktura f : faktury) {
                f.setBilansowa(true);
                List<Pozycjenafakturzebazadanych> pozycjebd = f.getPozycjenafakturze();
                Pozycjenafakturzebazadanych wiersz = pozycjebd.get(0);
                wiersz.setNazwa("sporządzenie sprawozdania finansowego");
                wiersz.setJednostka("kpl");
                if (pozycjebd.size()>1) {
                    int i = 1;
                    for (Iterator<Pozycjenafakturzebazadanych> it = pozycjebd.iterator(); it.hasNext();) {
                        if (i>1) {
                            it.remove();
                        }
                        i++;
                    }
                }
                Fakturywystokresowe idfakturaokresowa = f.getIdfakturaokresowa();
                idfakturaokresowa.setM13(1);
                fakturywystokresoweDAO.edit(idfakturaokresowa);
            }
            fakturaDAO.editList(faktury);
            faktury_edit = faktury;
            Msg.msg("w", "Faktury do przejrzenia na liście do edycji");
        }
    }
    
    public void wygenerujzokresowychzaleglekadry() {
        List<Faktura> listafakturzamiesiac = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (Iterator<Fakturywystokresowe> it = gosciwybralokres.iterator(); it.hasNext();) {
            Fakturywystokresowe p = it.next();
            if (p.isRecznaedycja()==false && sprawdzmiesiac(p, listafakturzamiesiac)==false) {
                it.remove();
            }
        }
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury ręcznych lub faktury ręczne nie zostały wystawione");
        } else {
            wygenerujzokresowychcd();
        }
    }
    
    public void wygenerujzokresowych() {
        List<Faktura> listafakturzamiesiac = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (Iterator<Fakturywystokresowe> it = gosciwybralokres.iterator(); it.hasNext();) {
            Fakturywystokresowe p = it.next();
            if (p.isRecznaedycja()==true || sprawdzmiesiac(p, listafakturzamiesiac)) {
                it.remove();
            }
        }
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktur okresowych lub wybrano tylko faktury jednorazowe");
        } else {
            wygenerujzokresowychcd();
        }
    }
    
    private boolean sprawdzmiesiac(Fakturywystokresowe p, List<Faktura> listafakturzamiesiac) {
        boolean zwrot = false;
        if (p.isWystawtylkoraz()==true) {
            final String nip = p.getDokument().getKontrahent().getNip();
            if (listafakturzamiesiac!=null) {
                Faktura get = listafakturzamiesiac.parallelStream().filter(fa->fa.getIdfakturaokresowa()!=null&&fa.getIdfakturaokresowa().equals(p)).findAny().orElse(null);
                if (get!=null&&get.isKorekta()==false) {
                    zwrot = true;
                }
            }
        }
        return zwrot;
    }
    
//    private boolean sprawdzmiesiac(Fakturywystokresowe p) {
//        boolean zwrot = false;
//        if (p.isWystawtylkoraz()==true) {
//            String mc = wpisView.getMiesiacWpisu();
//            int liczbafaktur = 0;
//            switch (mc) {
//                case "01":
//                    liczbafaktur = p.getM1();
//                    break;
//                case "02":
//                    liczbafaktur = p.getM2();
//                    break;
//                case "03":
//                    liczbafaktur = p.getM3();
//                    break;
//                case "04":
//                    liczbafaktur = p.getM4();
//                    break;
//                case "05":
//                    liczbafaktur = p.getM5();
//                    break;
//                case "06":
//                    liczbafaktur = p.getM6();
//                    break;
//                case "07":
//                    liczbafaktur = p.getM7();
//                    break;
//                case "08":
//                    liczbafaktur = p.getM8();
//                    break;
//                case "09":
//                    liczbafaktur = p.getM9();
//                    break;
//                case "10":
//                    liczbafaktur = p.getM10();
//                    break;
//                case "11":
//                    liczbafaktur = p.getM11();
//                    break;
//                case "12":
//                    liczbafaktur = p.getM12();
//                    break;
//            }
//            if (liczbafaktur>0) {
//                zwrot = true;
//            }
//        }
//        return zwrot;
//    }
    
    
    public List<Faktura> wygenerujzokresowychcd() {
        List<Faktura> nowododane = new ArrayList<>();
        for (Fakturywystokresowe okresowa : gosciwybralokres) {
            if (!okresowa.isZawieszona()) {
                Faktura nowa = SerialClone.clone(okresowa.getDokument());
                nowa.setId(null);
                nowa.setDatawysylki(null);
                nowa.setRecznaedycja(okresowa.isRecznaedycja());
                if (nowa.getPozycjenafakturze()!=null) {
                    List<Pozycjenafakturzebazadanych> pozycjenafakturze = nowa.getPozycjenafakturze();
                    Pozycjenafakturzebazadanych pozycje = pozycjenafakturze.get(0);
                    if (pozycje.getNazwa().contains("#mc#")) {
                        pozycje.setNazwa(pozycje.getNazwa().replace("#mc#", Mce.getStringToNazwamiesiaca().get(wpisView.getMiesiacWpisu())));
                    }
                }
                if (wpisView.getPodatnikObiekt().getNip().equals("8511005008")) {
                    dodajwierszedodatkowe(nowa, okresowa);
                }
                int dniDoZaplaty = nowa.getDnizaplaty();
                if (datawystawienia.isEmpty()) {
                    DateTime dt = new DateTime();
                    String miesiacBiezacy = Mce.getNumberToMiesiac().get(dt.getMonthOfYear());
                    String miesiacWybrany = wpisView.getMiesiacWpisu();
                    if (miesiacBiezacy.equals(miesiacWybrany) == false) {
                        String nowadata = wpisView.getRokWpisuSt()+"-"+miesiacWybrany+"-05";
                        dt = new DateTime(nowadata);
                    }
                    LocalDate firstDate = dt.toLocalDate();
                    nowa.setDatawystawienia(firstDate.toString());
                    nowa.setDatasprzedazy(firstDate.toString());
                    MutableDateTime dateTime = new MutableDateTime(firstDate.toString());
                    dateTime.addDays(dniDoZaplaty);
                    nowa.setTerminzaplaty(dateTime.toString().substring(0, 10));
                } else {
                    nowa.setDatawystawienia(datawystawienia);
                    nowa.setDatasprzedazy(datawystawienia);
                    MutableDateTime dateTime = new MutableDateTime(datawystawienia);
                    dateTime.addDays(dniDoZaplaty);
                    nowa.setTerminzaplaty(dateTime.toString().substring(0, 10));
                }
                FakturaBean.dodajtabelenbp(nowa, tabelanbpDAO);
                if (waloryzajca > 0.0) {
                    try {
                        waloryzacjakwoty(nowa, waloryzajca);
                        Faktura stara = okresowa.getDokument();
                        //to jest po to zeby potem juz generowac z okresowych ze zwaloryzowana kwota
                        waloryzacjakwoty(stara, waloryzajca);
                        FakturaBean.ewidencjavat(nowa, evewidencjaDAO);
                        fakturaDAO.edit(stara);
                    } catch (Exception e) { E.e(e); 
                        Msg.msg("e", "Nieudane generowanie faktury okresowej z waloryzacją FakturaView:wygenerujzokresowych");
                    }
                } else {
                    try {
                        FakturaBean.ewidencjavat(nowa, evewidencjaDAO);
                        Msg.msg("i", "Generowanie nowej ewidencji vat");
                    } catch (Exception e) { E.e(e); 
                        Msg.msg("e", "Nieudane generowanie nowej ewidencji vat dla faktury generowanej z okresowej FakturaView:wygenerujzokresowych");
                    }
                }
                nowa.setWygenerowanaautomatycznie(true);
                nowa.setIdfakturaokresowa(okresowa);
                nowa.setWyslana(false);
                nowa.setDatazaplaty(null);
                nowa.setZaksiegowana(false);
                nowa.setZatwierdzona(false);
                nowa.setTylkodlaokresowej(false);
                nowa.setAutor(wpisView.getUzer().getNazwiskoImie());
                nowa.setDatasporzadzenia(new Date());
                int fakturanowyrok = 0;
                boolean istnieje = true;
                FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, nowa, wpisView);
                String datasprzedazy = nowa.getDatasprzedazy();
                String miesiacsprzedazy = datasprzedazy.substring(5, 7);
                String roksprzedazy = datasprzedazy.substring(0, 4);
                nowa.setRok(roksprzedazy);
                nowa.setFakturagrupa(wpisView.getUzer().getFakturagrupa());
                nowa.setMc(miesiacsprzedazy);
                nowa.setNrkontabankowego(FakturaBean.pobierznumerkonta(wpisView.getPodatnikObiekt()));
                nowa.setSwift(FakturaBean.pobierzswift(wpisView.getPodatnikObiekt()));
                FakturaBean.wielekont(nowa, fakturaWalutaKontoView.getListakontaktywne(), fakturaStopkaNiemieckaDAO, wpisView.getPodatnikObiekt());
                boolean czygenerowac = czygenerowacfakturetaxman(nowa,wpisView.getPodatnikObiekt().getNip());
                if (czygenerowac) {
                    try {
                        fakturaDAO.create(nowa);
                        Klienci kontra = nowa.getKontrahent();
                        kontra.setAktywnydlafaktrozrachunki(true);
                        klienciDAO.edit(kontra);
                        nowododane.add(nowa);
                        if (nowa.isRecznaedycja()) {
                            faktury_edit.add(nowa);
                        } else {
                            faktury.add(nowa);
                        }
                        if (fakturanowyrok == 0) {
                            String datawystawienia = nowa.getDatawystawienia();
                            String miesiac = datawystawienia.substring(5, 7);
                            switch (miesiac) {
                                case "01":
                                    okresowa.setM1(okresowa.getM1() + 1);
                                    break;
                                case "02":
                                    okresowa.setM2(okresowa.getM2() + 1);
                                    break;
                                case "03":
                                    okresowa.setM3(okresowa.getM3() + 1);
                                    break;
                                case "04":
                                    okresowa.setM4(okresowa.getM4() + 1);
                                    break;
                                case "05":
                                    okresowa.setM5(okresowa.getM5() + 1);
                                    break;
                                case "06":
                                    okresowa.setM6(okresowa.getM6() + 1);
                                    break;
                                case "07":
                                    okresowa.setM7(okresowa.getM7() + 1);
                                    break;
                                case "08":
                                    okresowa.setM8(okresowa.getM8() + 1);
                                    break;
                                case "09":
                                    okresowa.setM9(okresowa.getM9() + 1);
                                    break;
                                case "10":
                                    okresowa.setM10(okresowa.getM10() + 1);
                                    break;
                                case "11":
                                    okresowa.setM11(okresowa.getM11() + 1);
                                    break;
                                case "12":
                                    okresowa.setM12(okresowa.getM12() + 1);
                                    break;
                            }
                            okresowa.setDatawystawienia(nowa.getDatawystawienia());
                            okresowa.setSapracownicy(false);
                            fakturywystokresoweDAO.edit(okresowa);
                        }
                        if (waloryzajca > 0) {
                            Msg.msg("i", "Generuje bieżącą fakturę z okresowej z waloryzacją. Kontrahent: " + nowa.getKontrahent().getNpelna());
                        } else {
                            Msg.msg("i", "Generuje bieżącą fakturę z okresowej. Kontrahent: " + nowa.getKontrahent().getNpelna());
                        }
                    } catch (Exception e) { 
                        E.e(e); 
                        Faktura nibyduplikat = fakturaDAO.findbyNumerPodatnik(nowa.getNumerkolejny(), nowa.getWystawca());
                        Msg.msg("e", "Faktura o takim numerze istnieje juz w bazie danych: data-" + nibyduplikat.getDatawystawienia()+" numer-"+nibyduplikat.getNumerkolejny()+" wystawca-"+nibyduplikat.getWystawca().getNazwapelna());
                    }
                } 
            }
        }
        PrimeFaces.current().ajax().update("akordeon:formsporzadzone:dokumentyLista");
        PrimeFaces.current().ajax().update("akordeon:formokresowe:dokumentyOkresowe");
        return nowododane;
    }

    public void resetujbiezacymiesiac() {
        for (Fakturywystokresowe p : this.fakturyokresowe) {
            Fakturywystokresowe okresowe = p;
            String miesiac = wpisView.getMiesiacWpisu();
            switch (miesiac) {
                case "01":
                    okresowe.setM1(0);
                    break;
                case "02":
                    okresowe.setM2(0);
                    break;
                case "03":
                    okresowe.setM3(0);
                    break;
                case "04":
                    okresowe.setM4(0);
                    break;
                case "05":
                    okresowe.setM5(0);
                    break;
                case "06":
                    okresowe.setM6(0);
                    break;
                case "07":
                    okresowe.setM7(0);
                    break;
                case "08":
                    okresowe.setM8(0);
                    break;
                case "09":
                    okresowe.setM9(0);
                    break;
                case "10":
                    okresowe.setM10(0);
                    break;
                case "11":
                    okresowe.setM11(0);
                    break;
                case "12":
                    okresowe.setM12(0);
                    break;
            }
            okresowe.setM13(0);
            fakturywystokresoweDAO.edit(okresowe);
        }
    }
    
    public void zawiesfakture() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do zawieszenia");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
            if (p.isZawieszona()) {
                p.setZawieszona(false);
            } else {
                p.setZawieszona(true);
            }
        }
        fakturywystokresoweDAO.editList(gosciwybralokres);
        Msg.msg("Naniesiono zawieszenie dla wybranych faktur");
    }
    
    public void oznaczwielorazowa() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do oznaczenia");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
            if (p.isWystawtylkoraz()) {
                p.setWystawtylkoraz(false);
            } else {
                p.setWystawtylkoraz(true);
            }
        }
        fakturywystokresoweDAO.editList(gosciwybralokres);
        Msg.msg("Naniesiono oznaczenie - wielorazowa dla wybranych faktur");
    }
    
    public void oznaczwaloryzacja() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do oznaczenia");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
            if (p.getDokument().getDatawaloryzacji()==null) {
                p.getDokument().setDatawaloryzacji(Data.data_yyyyMMdd(new Date()));
            } else {
                p.getDokument().setDatawaloryzacji(null);
            }
        }
        fakturywystokresoweDAO.editList(gosciwybralokres);
        Msg.msg("Naniesiono oznaczenie - traktuj jako waloryzaowane dla wybranych faktur");
    }
    
    public void oznaczrecznaedycja() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do oznaczenia");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
            if (p.isRecznaedycja()) {
                p.setRecznaedycja(false);
            } else {
                p.setRecznaedycja(true);
            }
        }
        fakturywystokresoweDAO.editList(gosciwybralokres);
        Msg.msg("Naniesiono oznaczenie - ręczna edycja dla wybranych faktur");
    }
    
    public void porzadekbiuro() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do oznaczenia");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
            Faktura dokument = p.getDokument();
            if (dokument!=null) {
                List<Pozycjenafakturzebazadanych> pozycjenafakturze = dokument.getPozycjenafakturze();
                Pozycjenafakturzebazadanych pierwszywiersz = pozycjenafakturze.get(0);
                if (pierwszywiersz.getNazwa().trim().equals("usługi rachunkowe")) {
                    pierwszywiersz.setNazwa("usługi rachunkowe #mc#");
                }
            }
        }
        fakturywystokresoweDAO.editList(gosciwybralokres);
        Msg.msg("Naniesiono oznaczenie - ręczna edycja dla wybranych faktur");
    }
    
    

    public void oznaczbiezacymiesiac() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do resetu");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
            Fakturywystokresowe okresowe = p;
            String miesiac = wpisView.getMiesiacWpisu();
            switch (miesiac) {
                case "01":
                    okresowe.setM1(1);
                    break;
                case "02":
                    okresowe.setM2(1);
                    break;
                case "03":
                    okresowe.setM3(1);
                    break;
                case "04":
                    okresowe.setM4(1);
                    break;
                case "05":
                    okresowe.setM5(1);
                    break;
                case "06":
                    okresowe.setM6(1);
                    break;
                case "07":
                    okresowe.setM7(1);
                    break;
                case "08":
                    okresowe.setM8(1);
                    break;
                case "09":
                    okresowe.setM9(1);
                    break;
                case "10":
                    okresowe.setM10(1);
                    break;
                case "11":
                    okresowe.setM11(1);
                    break;
                case "12":
                    okresowe.setM12(1);
                    break;
            }
            fakturywystokresoweDAO.edit(okresowe);
        }
    }
    
    public void sprawdzbiezacymiesiac() {
        resetujbiezacymiesiac();
        List<Faktura> fakturytmp = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (Faktura r : fakturytmp) {
            for (Fakturywystokresowe p : this.fakturyokresowe) {
                if (p.equals(r.getIdfakturaokresowa())) {
                    if (r.isBilansowa()) {
                        p.setM13(p.getM13() + 1);
                        fakturywystokresoweDAO.edit(p);
                    } 
                    naniesoznaczenienaokresowa(p);
                    break;
                }
            }

        }
        Msg.dP();
    }
    
    private void naniesoznaczenienaokresowa(Fakturywystokresowe p) {
        Fakturywystokresowe okresowe = p;
        String miesiac = wpisView.getMiesiacWpisu();
        switch (miesiac) {
            case "01":
                okresowe.setM1(okresowe.getM1()+1);
                break;
            case "02":
                okresowe.setM2(okresowe.getM2()+1);
                break;
            case "03":
                okresowe.setM3(okresowe.getM3()+1);
                break;
            case "04":
                okresowe.setM4(okresowe.getM4()+1);
                break;
            case "05":
                okresowe.setM5(okresowe.getM5()+1);
                break;
            case "06":
                okresowe.setM6(okresowe.getM6()+1);
                break;
            case "07":
                okresowe.setM7(okresowe.getM7()+1);
                break;
            case "08":
                okresowe.setM8(okresowe.getM8()+1);
                break;
            case "09":
                okresowe.setM9(okresowe.getM9()+1);
                break;
            case "10":
                okresowe.setM10(okresowe.getM10()+1);
                break;
            case "11":
                okresowe.setM11(okresowe.getM11()+1);
                break;
            case "12":
                okresowe.setM12(okresowe.getM12()+1);
                break;
        }
        fakturywystokresoweDAO.edit(okresowe);
    }
    
    public void skopiujdoNowegoroku() {
        if (gosciwybralokres!=null) {
            for (Fakturywystokresowe stara : gosciwybralokres) {
                if (stara.isZawieszona()==false) {
                    Fakturywystokresowe nowa = new Fakturywystokresowe(stara, wpisView.getRokNastepnySt(), wpisView.getUzer().getNazwiskoImie());
                    double nowakwota = stara.getKwotaroknastepny();
                    if (nowakwota > 0.0) {
                        try {
                            Faktura nowafaktura = nowa.getDokument();
                            //to jest po to zeby potem juz generowac z okresowych ze zwaloryzowana kwota
                            waloryzacjakwotyKwota(nowafaktura, nowakwota);
                            FakturaBean.ewidencjavat(nowafaktura, evewidencjaDAO);
                        } catch (Exception e) { E.e(e); 
                            Msg.msg("e", "Nieudane generowanie faktury okresowej z waloryzacją FakturaView:wygenerujzokresowych");
                        }
                    }
                    nowa.setAutor(wpisView.getUzer().getNazwiskoImie());
                    nowa.setId(null);
                    nowa.setRok(wpisView.getRokNastepnySt());
                    nowa.setM1(0);
                    nowa.setM2(0);
                    nowa.setM3(0);
                    nowa.setM4(0);
                    nowa.setM5(0);
                    nowa.setM6(0);
                    nowa.setM7(0);
                    nowa.setM8(0);
                    nowa.setM9(0);
                    nowa.setM10(0);
                    nowa.setM11(0);
                    nowa.setM12(0);
                    nowa.setKwotaroknastepny(0);
                    nowa.setKwotapraca(0);
                    nowa.setKwotazlecenie(0);
                    if (stara.getKwotapraca()>0.0||stara.getKwotazlecenie()>0.0) {
                        nowa.setRecznaedycja(true);
                    } else {
                        nowa.setRecznaedycja(false);
                    }
                    fakturywystokresoweDAO.create(nowa);
                    stara.setWygenerowanoroknastepny(true);
                    fakturywystokresoweDAO.edit(stara);
                }
            }
        Msg.msg("Skopiowano okresowe do nowego roku");
        } else {
            Msg.msg("e", "Nie wybrano faktur");
        }
    }

    
    public void sumawartoscifiltered() {
        boolean filtrowane = fakturyFiltered!=null && fakturyFiltered.size()>0 ? true: false;
        boolean wybrane = gosciwybral!=null && gosciwybral.size()>0 ? true: false;
        List<Faktura> lista = filtrowane && wybrane ? gosciwybral : filtrowane ? fakturyFiltered : gosciwybral;
        sumawartosciwybranych(lista);
    }
    
    public void sumawartosciwybranych(List<Faktura> gosciwybral) {
        podsumowaniewybranychbrutto = 0.0;
        podsumowaniewybranychnetto = 0.0;
        podsumowaniewybranychvat = 0.0;
        if (gosciwybral.size() > 0) {
            iloscwybranych = gosciwybral.size();
            for (Faktura p : gosciwybral) {
      
                    if (p.getPozycjepokorekcie() == null) {
                        podsumowaniewybranychnetto += p.getNettopln();
                        podsumowaniewybranychvat += p.getVatpln();
                        podsumowaniewybranychbrutto += p.getBruttopln();
                    } else {
                        podsumowaniewybranychnetto += (p.getNettopkpln()-p.getNettopln());
                        podsumowaniewybranychvat += (p.getVatpkpln()-p.getVatpln());
                        podsumowaniewybranychbrutto += (p.getBruttopkpln()-p.getBruttopln());
                    }
           
            }
        }
    }
    
    //bylo tak wiec sumowalo waluty jako zlotowki trzeba ewentualnie dodac opcje wyboru waluty
//    public void sumawartosciwybranych2(List<Faktura> gosciwybral) {
//        podsumowaniewybranychbrutto2 = 0.0;
//        podsumowaniewybranychnetto2 = 0.0;
//        podsumowaniewybranychvat2 = 0.0;
//        if (gosciwybral.size() > 0) {
//            iloscwybranych2 = gosciwybral.size();
//            for (Faktura p : gosciwybral) {
//                if (p.getTabelanbp()!=null) {
//                    if (p.getPozycjepokorekcie() == null) {
//                        podsumowaniewybranychnetto2 += p.getNettopln();
//                        podsumowaniewybranychvat2 += p.getVatpln();
//                        podsumowaniewybranychbrutto2 += p.getBruttopln();
//                    } else {
//                        podsumowaniewybranychnetto2 += (p.getNettopkpln()-p.getNettopln());
//                        podsumowaniewybranychvat2 += (p.getVatpkpln()-p.getVatpln());
//                        podsumowaniewybranychbrutto2 += (p.getBruttopkpln()-p.getBruttopln());
//                    }
//                } else {
//                    if (p.getPozycjepokorekcie() == null) {
//                        podsumowaniewybranychnetto2 += p.getNetto();
//                        podsumowaniewybranychvat2 += p.getVat();
//                        podsumowaniewybranychbrutto2 += p.getBrutto();
//                    } else {
//                        podsumowaniewybranychnetto2 += (p.getNettopk()-p.getNetto());
//                        podsumowaniewybranychvat2 += (p.getVatpk()-p.getVat());
//                        podsumowaniewybranychbrutto2 += (p.getBruttopk()-p.getBrutto());
//                    }
//                }
//            }
//        }
//    }
    
    
     public void sumawartosciwybranych2(List<Faktura> gosciwybral) {
        podsumowaniewybranychbrutto2 = 0.0;
        podsumowaniewybranychnetto2 = 0.0;
        podsumowaniewybranychvat2 = 0.0;
        if (gosciwybral.size() > 0) {
            iloscwybranych2 = gosciwybral.size();
            for (Faktura p : gosciwybral) {
                if (p.getTabelanbp()!=null) {
                    if (p.getPozycjepokorekcie() == null) {
                        podsumowaniewybranychnetto2 += p.getNettopln();
                        podsumowaniewybranychvat2 += p.getVatpln();
                        podsumowaniewybranychbrutto2 += p.getBruttopln();
                    } else {
                        podsumowaniewybranychnetto2 += (p.getNettopkpln()-p.getNettopln());
                        podsumowaniewybranychvat2 += (p.getVatpkpln()-p.getVatpln());
                        podsumowaniewybranychbrutto2 += (p.getBruttopkpln()-p.getBruttopln());
                    }
                } else {
                    if (p.getPozycjepokorekcie() == null) {
                        podsumowaniewybranychnetto2 += p.getNetto();
                        podsumowaniewybranychvat2 += p.getVat();
                        podsumowaniewybranychbrutto2 += p.getBrutto();
                    } else {
                        podsumowaniewybranychnetto2 += (p.getNettopk()-p.getNetto());
                        podsumowaniewybranychvat2 += (p.getVatpk()-p.getVat());
                        podsumowaniewybranychbrutto2 += (p.getBruttopk()-p.getBrutto());
                    }
                }
            }
        }
    }
     
     
     public void sumawartosciwybranychokresowe() {
        podsumowaniewybranychbrutto = 0.0;
        podsumowaniewybranychnetto = 0.0;
        podsumowaniewybranychvat = 0.0;
        if (gosciwybralokres.size() > 0) {
            iloscwybranych = gosciwybralokres.size();
            for (Fakturywystokresowe p : gosciwybralokres) {
                if (p.isZawieszona()==false) {
                    if (p.getDokument().getTabelanbp()!=null) {
                        podsumowaniewybranychnetto += p.getDokument().getNettopln();
                        podsumowaniewybranychvat += p.getDokument().getVatpln();
                        podsumowaniewybranychbrutto += p.getDokument().getBruttopln();
                    } else {
                        podsumowaniewybranychnetto += p.getDokument().getNetto();
                        podsumowaniewybranychvat += p.getDokument().getVat();
                        podsumowaniewybranychbrutto += p.getDokument().getBrutto();
                    }
                }
            }
        }
    }
    
    
     //zmiena miesiac wyswietlanych faktur dla ksiegowego pipir
    public void aktualizujTabeleTabela()  {
        fakturyarchiwum.clear();
        PrimeFaces.current().executeScript("try{PF(\"dokTableFaktury\").clearFilters()}catch(e){}");
        PrimeFaces.current().executeScript("try{PF(\"okresTable\").clearFilters()}catch(e){}");
       aktualizuj();
        init();
        Msg.msg("i", "Udana zamiana klienta. Aktualny klient to: " + wpisView.getPodatnikWpisu() + " okres rozliczeniowy: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu());
    }
    
     public void aktualizujTabeleTabelaGuest(AjaxBehaviorEvent e) throws IOException {
        fakturyarchiwum.clear();
        aktualizuj();
        init();
        Msg.msg("i", "Udana zamiana klienta. Aktualny klient to: " + wpisView.getPodatnikWpisu() + " okres rozliczeniowy: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu());
    }
     
     

   
    private void aktualizuj() {
        wpisView.naniesDaneDoWpis();
    }
    
    public void mailfaktura(List<Faktura> wybrane) {
        try {
            pdfFaktura.drukujmail(wybrane, wpisView);
            Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
            MailOther.faktura(wybrane, wpisView, fakturaDAO, wiadomoscdodatkowa, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getUzer()), sMTPSettingsDAO.findSprawaByDef());
            if (mailplussms) {
                Map<String, String> zwrot = SmsSend.wyslijSMSyFakturyLista(wybrane, "Na adres firmy wysłano właśnie fakturę.", podatnikDAO);
                if (zwrot.size()>0) {
                    Msg.msg("e","Błąd podczas wysyłania sms do faktury "+zwrot.size());
                }
            }
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Błąd podczas wysyłki faktury "+e.getMessage());
        }
    }
    
    public void mailfakturaJedna(Faktura faktura) {
        try {
            List<Faktura> wybrane = new ArrayList<>();
            wybrane.add(faktura);
            pdfFaktura.drukujmail(wybrane, wpisView);
            Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
            MailOther.faktura(wybrane, wpisView, fakturaDAO, wiadomoscdodatkowa, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getUzer()), sMTPSettingsDAO.findSprawaByDef());
            if (mailplussms) {
                Map<String, String> zwrot = SmsSend.wyslijSMSyFakturyLista(wybrane, "Na adres firmy wysłano właśnie fakturę.", podatnikDAO);
                if (zwrot.size()>0) {
                    Msg.msg("e","Błąd podczas wysyłania sms do faktury "+zwrot.size());
                }
            }
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Błąd podczas wysyłki faktury "+e.getMessage());
        }
    }
    
    public void pdffaktura() {
        try {
            if (gosciwybral != null && gosciwybral.size() >0) {
                pdfFaktura.drukujmasa(gosciwybral, wpisView);
            } else if (faktury !=null && faktury.size() > 0) {
                pdfFaktura.drukujmasa(faktury, wpisView);
            }
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public void pdffaktura_edit() {
        try {
            pdfFaktura.drukujmasa(f.l.l(faktury_edit, faktury_edit_filter, faktury_edit_select), wpisView);
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public void pdffakturapro() {
        try {
            if (gosciwybralpro != null && gosciwybralpro.size() >0) {
                pdfFaktura.drukujmasa(gosciwybralpro, wpisView);
            } else {
                pdfFaktura.drukujmasa(fakturypro, wpisView);
            }
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public void pdffakturaarch() {
        try {
            if (gosciwybralarchiuwm != null && gosciwybralarchiuwm.size() >0) {
                pdfFaktura.drukujmasa(gosciwybralarchiuwm, wpisView);
            } else {
                pdfFaktura.drukujmasa(fakturyarchiwum, wpisView);
            }
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public void drukujfaktura() {
        try {
            pdfFaktura.drukujPrinter(gosciwybral, wpisView);
        } catch (Exception e) { E.e(e); 
        }
    }
    
    
    
    public void oznaczonejakowyslane(List<Faktura> wybrane) {
        try {
            OznaczFaktBean.oznaczonejakowyslane(wybrane, fakturaDAO);
            Msg.msg("i", "Oznaczono faktury jako wysłane");
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void oznaczonejakozaksiegowane() {
        try {
            OznaczFaktBean.oznaczonejakozaksiegowane(gosciwybral, fakturaDAO);
            Msg.msg("i", "Oznaczono faktury jako zaksięgowaną");
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void drukujokresowa() {
         try {
            if (gosciwybral != null && gosciwybral.size() >0) {
                pdfFaktura.drukujokresowa(gosciwybralokres);
            } else {
                pdfFaktura.drukujokresowa(fakturyokresowe);
            }
        } catch (Exception e) { E.e(e); 
        }
    }

    public void edytujdanepodatnika () {
        podatnikDAO.edit(wpisView.getPodatnikObiekt());
        wpisView.initpublic();
        Msg.msg("Naniesiono dane do faktur.");
    }
    public void usupelnijautomatycznie () {
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        PodatnikBean.uzupelnijdanedofaktury(podatnik);
        podatnikDAO.edit(podatnik);
        wpisView.initpublic();
        Msg.msg("Wygenerowano dane do faktur");
    }
    
    public void edytujnazwaskroconapodatnika() {
        selected.getKontrahent().setNskrocona(nazwaskroconafaktura.toUpperCase());
        klienciDAO.edit(selected.getKontrahent());
        Msg.msg("Dopisano nazwę skróconą podatnika");
        FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
    }
    
    
    public void dopasujterminplatnosci(ValueChangeEvent e) {
        String data = (String) e.getNewValue();
        if (data.matches("\\d{4}-\\d{2}-\\d{2}")) {
            selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(wpisView.getPodatnikObiekt(), data, selected.getDnizaplaty()));
            PrimeFaces.current().ajax().update("akordeon:formstworz:terminzaplaty");
        }
    }
    
     public void dopasujterminplatnoscidni(ValueChangeEvent e) {
        int dnizaplaty = (int) e.getNewValue();
        selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(wpisView.getPodatnikObiekt(), selected.getDatawystawienia(), dnizaplaty));
        PrimeFaces.current().ajax().update("akordeon:formstworz:terminzaplaty");
    }
     
    public int sortZaksiegowaneFaktury(Object o1, Object o2) {
        if (wpisView.getPodatnikObiekt().getSchematnumeracji()!=null) {
            return FakturaSortBean.sortZaksiegowaneDok(o1, o2, wpisView);
        } else {
            Msg.msg("e","Brak schematu numerowania faktur");
            return 0;
        }
    }
    
     public void drukujfakturysporzadzone() {
        try {
            String nazwapliku = "fakturysporzadzone-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            if (gosciwybral != null && gosciwybral.size() > 0) {
                PdfFakturySporzadzone.drukujzapisy(wpisView, gosciwybral);
            } else if (fakturyFiltered != null && fakturyFiltered.size() > 0) {
                PdfFakturySporzadzone.drukujzapisy(wpisView, fakturyFiltered);
            } else {
                PdfFakturySporzadzone.drukujzapisy(wpisView, faktury);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void drukujfakturyarchiwum() {
        try {
            String nazwapliku = "fakturysporzadzone-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            if (gosciwybralarchiuwm != null && gosciwybralarchiuwm.size() > 0) {
                PdfFakturySporzadzone.drukujzapisy(wpisView, gosciwybralarchiuwm);
            } else {
                PdfFakturySporzadzone.drukujzapisy(wpisView, fakturyarchiwum);
            }
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void przenumerujwdol() {
        String[] schemat = wpisView.getPodatnikObiekt().getSchematnumeracji().split("/");
        int j = 0;
        for (String p : schemat) {
            if (p.equals("N")) {
                break;
            } else {
                j++;
            }
        }
        for (int i = gosciwybral.size()-1; i > -1 ; i--) {
           Faktura p = SerialClone.clone(gosciwybral.get(i));
           String[] tabelanumer = p.getNumerkolejny().split("/");
           int nowynumer = Integer.parseInt(tabelanumer[j]);
           nowynumer++;
           String nowynumerS = String.valueOf(nowynumer);
           String nowynumerfakt = "";
           for (int k = 0 ; k < tabelanumer.length; k++) {
               if (k == j) {
                   if (k == 0) {
                       nowynumerfakt = nowynumerfakt.concat(nowynumerS);
                   } else {
                       nowynumerfakt = nowynumerfakt.concat("/");
                       nowynumerfakt = nowynumerfakt.concat(nowynumerS);
                   }
               } else {
                   if (k == 0) {
                       nowynumerfakt = nowynumerfakt.concat(tabelanumer[k]);
                   } else {
                       nowynumerfakt = nowynumerfakt.concat("/");
                       nowynumerfakt = nowynumerfakt.concat(tabelanumer[k]);
                   }
               }
           }
           fakturaDAO.remove(gosciwybral.get(i));
           p.setNumerkolejny(nowynumerfakt);
           fakturaDAO.create(p);
        }
        gosciwybral = Collections.synchronizedList(new ArrayList<>());
        init();
        Msg.msg("Przenumerowałem faktury");
    }
    
    public void przenumerujwgore() {
        String[] schemat = wpisView.getPodatnikObiekt().getSchematnumeracji().split("/");
        int j = 0;
        for (String p : schemat) {
            if (p.equals("N")) {
                break;
            } else {
                j++;
            }
        }
        for (int i = 0; i <gosciwybral.size() ; i++) {
           Faktura p = SerialClone.clone(gosciwybral.get(i));
           String[] tabelanumer = p.getNumerkolejny().split("/");
           int nowynumer = Integer.parseInt(tabelanumer[j]);
           nowynumer--;
           String nowynumerS = String.valueOf(nowynumer);
           String nowynumerfakt = "";
           for (int k = 0 ; k < tabelanumer.length; k++) {
               if (k == j) {
                   if (k == 0) {
                       nowynumerfakt = nowynumerfakt.concat(nowynumerS);
                   } else {
                       nowynumerfakt = nowynumerfakt.concat("/");
                       nowynumerfakt = nowynumerfakt.concat(nowynumerS);
                   }
               } else {
                   if (k == 0) {
                       nowynumerfakt = nowynumerfakt.concat(tabelanumer[k]);
                   } else {
                       nowynumerfakt = nowynumerfakt.concat("/");
                       nowynumerfakt = nowynumerfakt.concat(tabelanumer[k]);
                   }
               }
           }
           fakturaDAO.remove(gosciwybral.get(i));
           p.setNumerkolejny(nowynumerfakt);
           fakturaDAO.create(p);
        }
        gosciwybral = Collections.synchronizedList(new ArrayList<>());
        init();
        Msg.msg("Przenumerowałem faktury");
    }
    
    public void drukujokresowe() {
        try {
            if (gosciwybralokres != null && gosciwybralokres.size() > 0) {
                PdfFakturyOkresowe.drukuj(gosciwybralokres, wpisView.getPodatnikObiekt().getNip());
            } else {
                PdfFakturyOkresowe.drukuj(fakturyokresowe, wpisView.getPodatnikObiekt().getNip());
            }
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void drukujokresowezerowe() {
        try {
            List<Fakturywystokresowe> fakt = new ArrayList<>();
            if (gosciwybralokres != null && gosciwybralokres.size() > 0) {
                for (Fakturywystokresowe p :gosciwybralokres) {
                    int wynik = pobierzpe(p, wpisView.getMiesiacWpisu());
                    if (wynik==0) {
                        fakt.add(p);
                    }
                }
                PdfFakturyOkresowe.drukuj(fakt, wpisView.getPodatnikObiekt().getNip());
            } else {
                for (Fakturywystokresowe p :fakturyokresowe) {
                    int wynik = pobierzpe(p, wpisView.getMiesiacWpisu());
                    if (wynik==0) {
                        fakt.add(p);
                    }
                }
                PdfFakturyOkresowe.drukuj(fakt, wpisView.getPodatnikObiekt().getNip());
            }
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void oznaczproforma() {
        wgenerujnumerfaktury();
        if (selected.getNumerkolejny() != null && selected.getNumerkolejny().length() > 1 && !selected.getNumerkolejny().equals("wpisz numer")) {
            if (selected.isProforma() && !selected.getNumerkolejny().contains("/PROFORMA")) {
                selected.setNumerkolejny(selected.getNumerkolejny()+"/PROFORMA");
                Msg.msg("Oznaczono fakturę jako PROFORMA Zmieniono numer faktury.");
            } else if (!selected.isProforma() && selected.getNumerkolejny().contains("/PROFORMA")) {
                selected.setProforma(false);
                selected.setNumerkolejny(selected.getNumerkolejny().replace("/PROFORMA", ""));
                wgenerujnumerfakturyLikwidacjaProforma();
                Msg.msg("Usunięto oznaczeniePROFORMA Zmieniono numer faktury.");
            }
        } else {
            Msg.msg("e","Nie można oznaczyć faktury jako proforma. Brak wprowadzonego numeru faktury");
            PrimeFaces.current().ajax().update("akordeon:formstworz:proformacheck");
        }
    }
    
//    public void naprawCarrefour() {
//        Klienci k = klienciDAO.findKlientById(4994);
//        for (Faktura f : gosciwybral) {
//            f.setKontrahent_nip(k.getNip());
//            klienciDAO.edit(f);
//        }
//    }
    
    public boolean isFakturakorekta() {
        return fakturakorekta;
    }

//    public void createDynamicColumns() {
//        columns = new ArrayList<ColumnModel>(); 
//        columns.add(new ColumnModel("nowakolumna", "nowakolumna"));
//    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    public void setFakturakorekta(boolean fakturakorekta) {    
        this.fakturakorekta = fakturakorekta;
    }

  
    public boolean isFakturaxxl() {
        return fakturaxxl;
    }

    public boolean isRachunek() {
        return rachunek;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public void setRachunek(boolean rachunek) {
        this.rachunek = rachunek;
    }

    public boolean isFakturavatmarza() {
        return fakturavatmarza;
    }

    public void setFakturavatmarza(boolean fakturavatmarza) {
        this.fakturavatmarza = fakturavatmarza;
    }

    public FakturaWalutaKontoView getFakturaWalutaKontoView() {
        return fakturaWalutaKontoView;
    }

    public void setFakturaWalutaKontoView(FakturaWalutaKontoView fakturaWalutaKontoView) {
        this.fakturaWalutaKontoView = fakturaWalutaKontoView;
    }

    

    public void setFakturaxxl(boolean fakturaxxl) {
        this.fakturaxxl = fakturaxxl;
    }
    
    
    public int getAktywnytab() {
        return aktywnytab;
    }

    public void setAktywnytab(int aktywnytab) {
        this.aktywnytab = aktywnytab;
    }

    public Faktura getSelected() {
        return selected;
    }

    public void setSelected(Faktura selected) {
        this.selected = selected;
    }

    public double getWaloryzajca() {
        return waloryzajca;
    }

    public void setWaloryzajca(double waloryzajca) {
        this.waloryzajca = waloryzajca;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }


    public boolean isPokazfakture() {
        return pokazfakture;
    }

    public void setPokazfakture(boolean pokazfakture) {
        this.pokazfakture = pokazfakture;
    }

    public List<Faktura> getFaktury() {
        return faktury;
    }

    public void setFaktury(List<Faktura> faktury) {
        this.faktury = faktury;
    }

    public List<Faktura> getGosciwybral() {
        return gosciwybral;
    }

    public void setGosciwybral(List<Faktura> gosciwybral) {
        this.gosciwybral = gosciwybral;
    }

    public List<Fakturywystokresowe> getGosciwybralokres() {
        return gosciwybralokres;
    }

    public void setGosciwybralokres(List<Fakturywystokresowe> gosciwybralokres) {
        this.gosciwybralokres = gosciwybralokres;
    }

    public List<Faktura> getGosciwybralpro() {
        return gosciwybralpro;
    }

    public void setGosciwybralpro(List<Faktura> gosciwybralpro) {
        this.gosciwybralpro = gosciwybralpro;
    }

    public List<Faktura> getFakturypro() {
        return fakturypro;
    }

    public void setFakturypro(List<Faktura> fakturypro) {
        this.fakturypro = fakturypro;
    }

    public List<Faktura> getFakturyFilteredpro() {
        return fakturyFilteredpro;
    }

    public void setFakturyFilteredpro(List<Faktura> fakturyFilteredpro) {
        this.fakturyFilteredpro = fakturyFilteredpro;
    }
  
  
    public List<Fakturywystokresowe> getFakturyokresowe() {
        return fakturyokresowe;
    }

    public void setFakturyokresowe(List<Fakturywystokresowe> fakturyokresowe) {
        this.fakturyokresowe = fakturyokresowe;
    }

    public List<Faktura> getFakturyarchiwum() {
        return fakturyarchiwum;
    }

    public void setFakturyarchiwum(List<Faktura> fakturyarchiwum) {
        this.fakturyarchiwum = fakturyarchiwum;
    }

    public Double getPodsumowaniewybranychnetto() {
        return podsumowaniewybranychnetto;
    }

    public void setPodsumowaniewybranychnetto(Double podsumowaniewybranychnetto) {
        this.podsumowaniewybranychnetto = podsumowaniewybranychnetto;
    }

    public Double getPodsumowaniewybranychvat() {
        return podsumowaniewybranychvat;
    }

    public void setPodsumowaniewybranychvat(Double podsumowaniewybranychvat) {
        this.podsumowaniewybranychvat = podsumowaniewybranychvat;
    }

    public Double getPodsumowaniewybranychbrutto() {
        return podsumowaniewybranychbrutto;
    }


    public void setPodsumowaniewybranychbrutto(Double podsumowaniewybranychbrutto) {
        this.podsumowaniewybranychbrutto = podsumowaniewybranychbrutto;
    }

    public List<Faktura> getGosciwybralarchiuwm() {
        return gosciwybralarchiuwm;
    }

    public void setGosciwybralarchiuwm(List<Faktura> gosciwybralarchiuwm) {
        this.gosciwybralarchiuwm = gosciwybralarchiuwm;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public List<Faktura> getFakturyFiltered() {
        return fakturyFiltered;
    }

    public void setFakturyFiltered(List<Faktura> fakturyFiltered) {
        this.fakturyFiltered = fakturyFiltered;
    }

    public List<Fakturywystokresowe> getFakturyokresoweFiltered() {
        return fakturyokresoweFiltered;
    }

    public void setFakturyokresoweFiltered(List<Fakturywystokresowe> fakturyokresoweFiltered) {
        this.fakturyokresoweFiltered = fakturyokresoweFiltered;
    }

    public int getIloscwybranych() {
        return iloscwybranych;
    }

    public void setIloscwybranych(int iloscwybranych) {
        this.iloscwybranych = iloscwybranych;
    }


    public String getWiadomoscdodatkowa() {
        return wiadomoscdodatkowa;
    }

    public void setWiadomoscdodatkowa(String wiadomoscdodatkowa) {
        this.wiadomoscdodatkowa = wiadomoscdodatkowa;
    }

    public boolean isZapis0edycja1() {
        return zapis0edycja1;
    }

    public void setZapis0edycja1(boolean zapis0edycja1) {
        this.zapis0edycja1 = zapis0edycja1;
    }

    public String getNazwaskroconafaktura() {
        return nazwaskroconafaktura;
    }

    public void setNazwaskroconafaktura(String nazwaskroconafaktura) {
        this.nazwaskroconafaktura = nazwaskroconafaktura;
    }

    public boolean isFakturaniemiecka() {
        return fakturaniemiecka;
    }

    public void setFakturaniemiecka(boolean fakturaniemiecka) {
        this.fakturaniemiecka = fakturaniemiecka;
    }

    public boolean isPodazedytorvar() {
        return podazedytorvar;
    }

    public void setPodazedytorvar(boolean podazedytorvar) {
        this.podazedytorvar = podazedytorvar;
    }

    public boolean isFakturazwykla() {
        return fakturazwykla;
    }

    public void setFakturazwykla(boolean fakturazwykla) {
        this.fakturazwykla = fakturazwykla;
    }

    public List<Faktura> getFakturyFilteredarchiwum() {
        return fakturyFilteredarchiwum;
    }

    public void setFakturyFilteredarchiwum(List<Faktura> fakturyFilteredarchiwum) {
        this.fakturyFilteredarchiwum = fakturyFilteredarchiwum;
    }

    public AutoComplete getKontrahentstworz() {
        return kontrahentstworz;
    }

    public void setKontrahentstworz(AutoComplete kontrahentstworz) {
        this.kontrahentstworz = kontrahentstworz;
    }
    
    
//</editor-fold>

  public void edytujemail(Klienci k) {
    if (k.getEmail() != null) {
        try {
            klienciDAO.edit(k);
            Msg.msg("Zmieniono email kontrahenta");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zmieniono emaila kontrahenta");
        }
    }
  }
  
  public void edytujemailfakt(Klienci k) {
    if (emailkontrahent != null) {
        try {
            k.setEmail(emailkontrahent);
            klienciDAO.edit(k);
            Msg.msg("Zmieniono email kontrahenta");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zmieniono emaila kontrahenta");
        }
    }
  }

    private int pobierzpe(Fakturywystokresowe p, String mc) {
        int wynik = 0;
        switch (mc) {
            case "01":
                wynik = p.getM1();
                break;
            case "02":
                wynik = p.getM2();
                break;
            case "03":
                wynik = p.getM3();
                break;
            case "04":
                wynik = p.getM4();
                break;
            case "05":
                wynik = p.getM5();
                break;
            case "06":
                wynik = p.getM6();
                break;
            case "07":
                wynik = p.getM7();
                break;
            case "08":
                wynik = p.getM8();
                break;
            case "09":
                wynik = p.getM9();
                break;
            case "10":
                wynik = p.getM10();
                break;
            case "11":
                wynik = p.getM11();
                break;
            case "12":
                wynik = p.getM12();
                break;
        }
        return wynik;
    }

    private void usundodatkowewiersze(Faktura p) {
        if (p.getPozycjenafakturze()!=null) {
            List<Integer> numery = new ArrayList<>();
            for (Pozycjenafakturzebazadanych r : p.getPozycjenafakturze()) {
                if (r.getDodatkowapozycja()!=0) {
                    numery.add(r.getDodatkowapozycja());
                }
            }
            if (numery!=null && numery.size()>0) {
                for (Integer s : numery) {
                    Wierszfakturybaza findById = wierszfakturybazaDAO.findById(s);
                    findById.setNaniesiony(false);
                    findById.setDatafaktury(null);
                    fakturaDodPozycjaKontrahentDAO.edit(findById);
                    
                }
            }
        }
    }

    private List<Pozycjenafakturzebazadanych> utworznowepozycje(List<Pozycjenafakturzebazadanych> pozycjenafakturze) {
        List<Pozycjenafakturzebazadanych> zwrot = new ArrayList<>();
        if (pozycjenafakturze!=null) {
            for (Pozycjenafakturzebazadanych p : pozycjenafakturze) {
                zwrot.add(new Pozycjenafakturzebazadanych(p));
            }
        }
        return zwrot;
    }
    
    public void edytujokresowa(Fakturywystokresowe fakturaokresowa) {
        if (fakturaokresowa!=null) {
            fakturywystokresoweDAO.edit(fakturaokresowa);
            Msg.msg("Zachowano zmiany faktury okresowej");
        } else {
            Msg.msg("Nie wybrano waloryzacji");
        }
    }
    
    public void edytujokresowaok(Fakturywystokresowe fakturaokresowa) {
        if (fakturaokresowa!=null) {
            if (fakturaokresowa.isKlientzaakceptowal()) {
                fakturaokresowa.setDatazalatwione(new Date());
            } else {
                fakturaokresowa.setDatazalatwione(null);
            }
            fakturywystokresoweDAO.edit(fakturaokresowa);
            Msg.msg("Zachowano zmiany faktury okresowej");
        } else {
            Msg.msg("Nie wybrano waloryzacji");
        }
    }

    private boolean czywystawiona(Fakturywystokresowe p, String miesiacWpisu) {
        boolean zwrot = false;
        switch (miesiacWpisu) {
            case "01":
                if (p.getM1()>0) {
                    zwrot = true;
                }
                break;
            case "02":
                if (p.getM2()>0) {
                    zwrot = true;
                }
                break;
            case "03":
                if (p.getM3()>0) {
                    zwrot = true;
                }
                break;
            case "04":
                if (p.getM4()>0) {
                    zwrot = true;
                }
                break;
            case "05":
                if (p.getM5()>0) {
                    zwrot = true;
                }
                break;
            case "06":
                if (p.getM6()>0) {
                    zwrot = true;
                }
                break;
            case "07":
                if (p.getM7()>0) {
                    zwrot = true;
                }
                break;
            case "08":
                if (p.getM8()>0) {
                    zwrot = true;
                }
                break;
            case "09":
                if (p.getM9()>0) {
                    zwrot = true;
                }
                break;
            case "10":
                if (p.getM10()>0) {
                    zwrot = true;
                }
                break;
            case "11":
                if (p.getM11()>0) {
                    zwrot = true;
                }
                break;
            case "12":
                if (p.getM11()>0) {
                    zwrot = true;
                }
                break;
        }
        return zwrot;
    }
    
    private boolean czyniewystawiona(Fakturywystokresowe p, String miesiacWpisu) {
        boolean zwrot = true;
        switch (miesiacWpisu) {
            case "01":
                if (p.getM1()>0) {
                    zwrot = false;
                }
                break;
            case "02":
                if (p.getM2()>0) {
                    zwrot = false;
                }
                break;
            case "03":
                if (p.getM3()>0) {
                    zwrot = false;
                }
                break;
            case "04":
                if (p.getM4()>0) {
                    zwrot = false;
                }
                break;
            case "05":
                if (p.getM5()>0) {
                    zwrot = false;
                }
                break;
            case "06":
                if (p.getM6()>0) {
                    zwrot = false;
                }
                break;
            case "07":
                if (p.getM7()>0) {
                    zwrot = false;
                }
                break;
            case "08":
                if (p.getM8()>0) {
                    zwrot = false;
                }
                break;
            case "09":
                if (p.getM9()>0) {
                    zwrot = false;
                }
                break;
            case "10":
                if (p.getM10()>0) {
                    zwrot = false;
                }
                break;
            case "11":
                if (p.getM11()>0) {
                    zwrot = false;
                }
                break;
            case "12":
                if (p.getM12()>0) {
                    zwrot = false;
                }
                break;
        }
        return zwrot;
    }
    
    //nie generujemy jak nie ma wygenerowanej listy plac
    private boolean czygenerowacfakturetaxman(Faktura nowa, String nip) {
        boolean zwrot = true;
        if (nowa.isRecznaedycja()&&nip!=null&&nip.equals("8511005008")) {
            if (nowa.getPozycjenafakturze()!=null&&nowa.getPozycjenafakturze().size()<2) {
                zwrot = false;
            }
        }
        return zwrot;
    }

    

    
    
   

    

   static public class ColumnModel implements Serializable {
 
        private String header;
        private String property;
 
        public ColumnModel(String header, String property) {
            this.header = header;
            this.property = property;
        }
 
        public String getHeader() {
            return header;
        }
 
        public String getProperty() {
            return property;
        }
    }
   
   public void skorygujnumer() {
       String numer = selected.getNumerkolejny();
       boolean jest = false;
       if (numer.contains(".") || numer.contains("-") || numer.contains("\\")) {
           jest = true;
           numer = numer.replace(".", "/");
           numer = numer.replace(",", "/");
           numer = numer.replace("\\", "/");
           selected.setNumerkolejny(numer);
       }
       if (jest) {
           Msg.msg("w", "Skorygowano numer faktury. Usunięto niedozwolone znaki");
       }
   }
    
//   public void zrobfakt() {
//       List<Faktura> faktury = fakturaDAO.findAll();
//       for (Faktura p : faktury) {
//           p.setKontrahentID(p.getKontrahent().getId());
//           p.setWystawcaNIP(p.getWystawca().getNip());
//           fakturaDAO.edit(p);
//           error.E.s(p.getKontrahentID());
//           error.E.s(p.getWystawcaNIP());
//       }
//   }
   
      public void okresowepodid() {
       List<Fakturywystokresowe> fakturyokres = fakturywystokresoweDAO.findPodatnik("GRZELCZYK");
       List<Podatnik> podatnicy = podatnikDAO.findAll();
       int liczbafaktur = fakturyokres.size();
       System.out.println("Liczba faktur "+liczbafaktur);
       int i = 1;
       for (Fakturywystokresowe p : fakturyokres) {
           List<Podatnik> lista = podatnicy.stream().filter(podid->podid.getNip().equals(p.getDokument().getKontrahent().getNip())).collect(Collectors.toList());
           if (lista!=null&&lista.size()==1) {
               Podatnik podatnik = lista.get(0);
               p.setPodid(podatnik);
               System.out.println(i+" Jest "+p.getDokument().getKontrahent().getNpelna());
           } else {
               System.out.println(i+" nie ma dla "+p.getDokument().getKontrahent().getNpelna());
           }
           i++;
       }
       fakturywystokresoweDAO.editList(fakturyokres);
       System.out.println("Koniec");
   }
    public static void main(String[] args) {
    }
    
    public void usunfakture(Faktura fakt) {
        if (fakt!=null) {
            try {
                fakturaDAO.remove(fakt);
                faktury_edit.remove(fakt);
                usundodatkowewiersze(fakt);
                if (faktury_edit_filter != null) {
                    faktury_edit_filter.remove(fakt);
                }
                if (fakt.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(fakt);
                }
                Msg.msg("Usunięto wybraną fakturę");
            } catch (Exception e) {
                Msg.msg("e","Wsytąpił błąd nie usunięto wybranej faktury");
            }
        }
    }
    
    public void pokazedytor(int i) {
        if (i==1) {
            podazedytorvar = true;
        } else {
            podazedytorvar = false;
            wiadomoscdodatkowa = "";
        }
    }
    
    public void filtrujfaktury() {
        if (jakapobrac>0) {
            if (fakturyokresoweFiltered!=null) {
                for (Iterator<Fakturywystokresowe> it =fakturyokresoweFiltered.iterator(); it.hasNext();) {
                    Fakturywystokresowe p = it.next();
                    if (jakapobrac==1 && !p.isRecznaedycja()) {
                        it.remove();
                    } else if (jakapobrac==2 && !p.isZawieszona()) {
                        it.remove();
                    } else if (jakapobrac==3 && p.isWystawtylkoraz()) {
                        it.remove();
                    } else if (jakapobrac==4 && p.getDokument().getDatawaloryzacji()==null) {
                        it.remove();
                    } else if (jakapobrac==5 && p.getDokument().getDatawaloryzacji()!=null) {
                        it.remove();
                    } else if (jakapobrac==6 && czywystawiona(p, wpisView.getMiesiacWpisu())) {
                        it.remove();
                    }
                }
            } else {
                fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                for (Iterator<Fakturywystokresowe> it =fakturyokresowe.iterator(); it.hasNext();) {
                    Fakturywystokresowe p = it.next();
                    if (jakapobrac==1 && !p.isRecznaedycja()) {
                        it.remove();
                    } else if (jakapobrac==2 && !p.isZawieszona()) {
                        it.remove();
                    } else if (jakapobrac==3 && p.isWystawtylkoraz()) {
                        it.remove();
                    } else if (jakapobrac==4 && p.getDokument().getDatawaloryzacji()==null) {
                        it.remove();
                    } else if (jakapobrac==5 && p.getDokument().getDatawaloryzacji()!=null) {
                        it.remove();
                    } else if (jakapobrac==6 && czywystawiona(p, wpisView.getMiesiacWpisu())) {
                        it.remove();
                    }
                }
                Msg.msg("Przefiltrowano faktury");
            }
        }
        
    }
    public void filtrujfakturyNowe() {
        if (jakapobrac>0) {
            Predicate<Fakturywystokresowe> isQualified = null;
            if (jakapobrac==1) {
                isQualified = item->item.isRecznaedycja();
            } else if (jakapobrac==2) {
                isQualified = item->item.isZawieszona();
            } else if (jakapobrac==3) {
                isQualified = item->item.isWystawtylkoraz();
            } else if (jakapobrac==4) {
                isQualified = item->item.getDokument().getDatawaloryzacji()==null;
            } else if (jakapobrac==5) {
                isQualified = item->item.getDokument().getDatawaloryzacji()!=null;
            } else if (jakapobrac==6 ) {
                isQualified = item->czyniewystawiona(item, wpisView.getMiesiacWpisu());
            }
            if (fakturyokresoweFiltered!=null) {
                fakturyokresoweFiltered.removeIf(isQualified.negate());
            } else {
                fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                fakturyokresowe.removeIf(isQualified.negate());
            }
            Msg.msg("Przefiltrowano faktury");
        } else {
            fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        
    }
    
    public void pokazotwarte() {
        fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        for (Iterator<Fakturywystokresowe> it =fakturyokresowe.iterator(); it.hasNext();) {
            Fakturywystokresowe p = it.next();
            if (p.getDatazalatwione()!=null) {
                it.remove();
            }
        }
        Msg.msg("Przefiltrowano faktury");
    }
    
    public void filtrujfaktury2() {
        if (dolnylimit>0.0 || gornylimit>0.0) {
            if (fakturyokresoweFiltered!=null) {
                 for (Iterator<Fakturywystokresowe> it =fakturyokresoweFiltered.iterator(); it.hasNext();) {
                    Fakturywystokresowe p = it.next();
                    if (dolnylimit==0.0 && gornylimit>0.0) {
                        if (p.getDokument().getBruttoFakturaOkresowe()>gornylimit) {
                            it.remove();
                        }
                    } else if (dolnylimit>0.0 && gornylimit>0.0) {
                        if (p.getDokument().getBruttoFakturaOkresowe()<dolnylimit || p.getDokument().getBruttoFakturaOkresowe()>gornylimit) {
                            it.remove();
                        }
                    } else if (dolnylimit>0.0 && gornylimit==0.0) {
                        if (p.getDokument().getBruttoFakturaOkresowe()<dolnylimit) {
                            it.remove();
                        }
                    } 
                }
            } else {
                fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                for (Iterator<Fakturywystokresowe> it =fakturyokresowe.iterator(); it.hasNext();) {
                    Fakturywystokresowe p = it.next();
                    if (dolnylimit==0.0 && gornylimit>0.0) {
                        if (p.getDokument().getBruttoFakturaOkresowe()>gornylimit) {
                            it.remove();
                        }
                    } else if (dolnylimit>0.0 && gornylimit>0.0) {
                        if (p.getDokument().getBruttoFakturaOkresowe()<dolnylimit || p.getDokument().getBruttoFakturaOkresowe()>gornylimit) {
                            it.remove();
                        }
                    } else if (dolnylimit>0.0 && gornylimit==0.0) {
                        if (p.getDokument().getBruttoFakturaOkresowe()<dolnylimit) {
                            it.remove();
                        }
                    } 
                }
            }
            Msg.msg("Przefiltrowano faktury");
        } else {
            fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        
    }

    public int getJakapobrac() {
        return jakapobrac;
    }

    public void setJakapobrac(int jakapobrac) {
        this.jakapobrac = jakapobrac;
    }

    public AutoComplete getOdbiorcastworz() {
        return odbiorcastworz;
    }

    public void setOdbiorcastworz(AutoComplete odbiorcastworz) {
        this.odbiorcastworz = odbiorcastworz;
    }

    public double getDolnylimit() {
        return dolnylimit;
    }

    public void setDolnylimit(double dolnylimit) {
        this.dolnylimit = dolnylimit;
    }

    public double getGornylimit() {
        return gornylimit;
    }

    public void setGornylimit(double gornylimit) {
        this.gornylimit = gornylimit;
    }

    public boolean isPokaztylkoniewyslane() {
        return pokaztylkoniewyslane;
    }

    public void setPokaztylkoniewyslane(boolean pokaztylkoniewyslane) {
        this.pokaztylkoniewyslane = pokaztylkoniewyslane;
    }

    public boolean isMailplussms() {
        return mailplussms;
    }

    public void setMailplussms(boolean mailplussms) {
        this.mailplussms = mailplussms;
    }

    public List<Faktura> getFaktury_edit() {
        return faktury_edit;
    }

    public void setFaktury_edit(List<Faktura> faktury_edit) {
        this.faktury_edit = faktury_edit;
    }

    public List<Faktura> getFaktury_edit_select() {
        return faktury_edit_select;
    }

    public void setFaktury_edit_select(List<Faktura> faktury_edit_select) {
        this.faktury_edit_select = faktury_edit_select;
    }

    public List<Faktura> getFaktury_edit_filter() {
        return faktury_edit_filter;
    }

    public void setFaktury_edit_filter(List<Faktura> faktury_edit_filter) {
        this.faktury_edit_filter = faktury_edit_filter;
    }

    public List<String> getListakontawwalucie() {
        return listakontawwalucie;
    }

    public void setListakontawwalucie(List<String> listakontawwalucie) {
        this.listakontawwalucie = listakontawwalucie;
    }

    public Double getPodsumowaniewybranychbrutto2() {
        return podsumowaniewybranychbrutto2;
    }

    public void setPodsumowaniewybranychbrutto2(Double podsumowaniewybranychbrutto2) {
        this.podsumowaniewybranychbrutto2 = podsumowaniewybranychbrutto2;
    }

    public Double getPodsumowaniewybranychnetto2() {
        return podsumowaniewybranychnetto2;
    }

    public void setPodsumowaniewybranychnetto2(Double podsumowaniewybranychnetto2) {
        this.podsumowaniewybranychnetto2 = podsumowaniewybranychnetto2;
    }

    public Double getPodsumowaniewybranychvat2() {
        return podsumowaniewybranychvat2;
    }

    public void setPodsumowaniewybranychvat2(Double podsumowaniewybranychvat2) {
        this.podsumowaniewybranychvat2 = podsumowaniewybranychvat2;
    }

    public int getIloscwybranych2() {
        return iloscwybranych2;
    }

    public void setIloscwybranych2(int iloscwybranych2) {
        this.iloscwybranych2 = iloscwybranych2;
    }

    public boolean isPokazzawieszone() {
        return pokazzawieszone;
    }

    public void setPokazzawieszone(boolean pokazzawieszone) {
        this.pokazzawieszone = pokazzawieszone;
    }

    public String getStawkaryczaltuksiegowanie() {
        return stawkaryczaltuksiegowanie;
    }

    public void setStawkaryczaltuksiegowanie(String stawkaryczaltuksiegowanie) {
        this.stawkaryczaltuksiegowanie = stawkaryczaltuksiegowanie;
    }

    public String getEmailkontrahent() {
        return emailkontrahent;
    }

    public List<Faktura> getListaincydentalni() {
        return listaincydentalni;
    }

    public Faktura getWybranyicydentalny() {
        return wybranyicydentalny;
    }

    public void setWybranyicydentalny(Faktura wybranyicydentalny) {
        this.wybranyicydentalny = wybranyicydentalny;
    }

  

    public void setListaincydentalni(List<Faktura> listaincydentalni) {
        this.listaincydentalni = listaincydentalni;
    }

    public void setEmailkontrahent(String emailkontrahent) {
        this.emailkontrahent = emailkontrahent;
    }


    public void poprawsierpien() {
        Podatnik pod = podatnikDAO.findPodatnikByNIP("8511005008");
        List<Faktura> fakt = fakturaDAO.findbyPodatnikRokMc(pod, "2022","08");
        for (Faktura f : fakt) {
                if (Data.data_yyyyMMdd(f.getDatawysylki()).equals("2022-08-21")) {
                    this.selected = f;
                    edytuj();
                    System.out.println("edytowano "+f.getNumerkolejny());
                }
            }
        }
 
     public void zmienmailkontrahenta(Klienci klient) {
       try {
           klienciDAO.edit(klient);
           Msg.msg("Zmieniono adres mail klienta");
       } catch (Exception e) {
           Msg.msg("e","Wytstąpił błąd, nie zmieniono adres mail klienta");
       }
   }
}