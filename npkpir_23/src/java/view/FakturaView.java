/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import beansFaktura.FDfkBean;
import beansFaktura.FakturaBean;
import beansFaktura.FakturaOkresowaGenNum;
import beansMail.OznaczFaktBean;
import beansMail.SMTPBean;
import sortfunction.FakturaSortBean;
import comparator.Fakturyokresowecomparator;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturadodelementyDAO;
import dao.FakturywystokresoweDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import dao.WpisDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import embeddable.EVatwpis;
import embeddable.Mce;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Faktura;
import entity.FakturaPK;
import entity.Fakturadodelementy;
import entity.Fakturywystokresowe;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Wpis;
import entityfk.Dokfk;
import error.E;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mail.MailOther;
import msg.Msg;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.MutableDateTime;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.context.RequestContext;
import params.Params;
import pdf.PdfFaktura;
import pdf.PdfFakturySporzadzone;
import plik.Plik;
import serialclone.SerialClone;
import dao.SMTPSettingsDAO;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaView implements Serializable {
    private static final long serialVersionUID = 1L;
    //faktury wybrane z listy
    private List<Faktura> gosciwybral;
    //faktury okresowe wybrane z listy
    private List<Fakturywystokresowe> gosciwybralokres;

    @Inject
    protected Faktura selected;
    @Inject
    private PdfFaktura pdfFaktura;
    private boolean pokazfakture;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
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
    private SMTPSettingsDAO sMTPSettingsDAO;
    //faktury z bazy danych
    private List<Faktura> faktury;
    //faktury z bazy danych przefiltrowane
    private List<Faktura> fakturyFiltered;
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
    //do usuuwania faktur zaksiegowanych
    private double waloryzajca;
    private double kwotaprzedwaloryzacja;
    //wlasna data dla faktur
    private String datawystawienia;
    @Inject
    private WpisDAO wpisDAO;
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
    private boolean fakturaxxl;
    private boolean fakturakorekta;
    private AutoComplete kontrahentstworz;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
        
   
    

    public FakturaView() {
        faktury = new ArrayList<>();
        fakturyarchiwum = new ArrayList<>();
        fakturyokresowe = new ArrayList<>();
        gosciwybral = new ArrayList<>();
        gosciwybralokres = new ArrayList<>();
        waloryzajca = 0.0;
        kwotaprzedwaloryzacja = 0.0;
    }

    @PostConstruct
    private void init() {
        fakturyokresoweFiltered = null;
        fakturyFiltered = null;
        aktywnytab = 1;
        fakturyokresowe = fakturywystokresoweDAO.findPodatnik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        List<Faktura> fakturytmp = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu());
        for (Faktura fakt : fakturytmp) {
            if (fakt.getWyslana() == true && fakt.getZaksiegowana() == true) {
                fakturyarchiwum.add(fakt);
            } else {
                faktury.add(fakt);
            }
        }
    }

    public void przygotujfakture() {
        fakturazwykla = true;
        fakturaxxl = false;
        fakturakorekta = false;
        fakturaniemiecka = false;
        inicjalizacjaczesciwspolne();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(wpisView.getPodatnikObiekt()));
        selected.setRodzajdokumentu("faktura");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę. Należy uzupełnić pozostałe elementy.");
    }
    
    public void przygotujfaktureniemiecka() {
        fakturazwykla = false;
        fakturaxxl = false;
        fakturakorekta = false;
        fakturaniemiecka = true;
        inicjalizacjaczesciwspolne();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(wpisView.getPodatnikObiekt()));
        selected.setWalutafaktury("EUR");
        selected.setRodzajdokumentu("faktura niemiecka");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę niemiecką. Należy uzupełnić pozostałe elementy.");
    }
    
    public void przygotujfakturexxl() {
        fakturazwykla = false;
        fakturaxxl = true;
        fakturakorekta = false;
        fakturaniemiecka = false;
        inicjalizacjaczesciwspolne();
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setRodzajdokumentu("faktura xxl");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę XXL. Należy uzupełnić pozostałe elementy.");
    }
    
     public void przygotujfakturekorekte() {
        fakturazwykla = true;
        fakturaxxl = false;
        fakturakorekta = true;
        inicjalizacjaczesciwspolne();
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setPozycjepokorekcie(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setRodzajdokumentu("faktura korekta");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę. Należy uzupełnić pozostałe elementy.");
    }
     
    public void przygotujfakturekorektexxl() {
        fakturazwykla = false;
        fakturaxxl = true;
        fakturakorekta = true;
        inicjalizacjaczesciwspolne();
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setPozycjepokorekcie(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setRodzajdokumentu("faktura xxl korekta");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę XXL korektę. Należy uzupełnić pozostałe elementy.");
    }
    
    private void inicjalizacjaczesciwspolne() {
        selected = new Faktura();
        if (fakturaxxl) {
            selected.setFakturaxxl(true);
        }
        if (fakturaniemiecka) {
            selected.setFakturaniemiecka13b(true);
        }
        String platnoscwdniach = wpisView.getPodatnikObiekt().getPlatnoscwdni() == null ? "0" : wpisView.getPodatnikObiekt().getPlatnoscwdni();
        selected.setDnizaplaty(Integer.parseInt(platnoscwdniach));
        String pelnadata = FakturaBean.obliczdatawystawienia(wpisView);
        selected.setDatawystawienia(pelnadata);
        selected.setDatasprzedazy(pelnadata);
        FakturaPK fakturaPK = new FakturaPK();
        fakturaPK.setNumerkolejny("wpisz numer");
        fakturaPK.setWystawcanazwa(wpisView.getPodatnikWpisu());
        selected.setFakturaPK(fakturaPK);
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setMiejscewystawienia(FakturaBean.pobierzmiejscewyst(podatnikobiekt));
        selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(podatnikobiekt, pelnadata));
        selected.setNrkontabankowego(FakturaBean.pobierznumerkonta(podatnikobiekt));
        selected.setPodpis(FakturaBean.pobierzpodpis(wpisView));
        selected.setAutor(wpisView.getWprowadzil().getLogin());
        setPokazfakture(true);
        selected.setWystawca(podatnikobiekt);
        selected.setRok(String.valueOf(wpisView.getRokWpisu()));
        selected.setMc(wpisView.getMiesiacWpisu());
    }

    public void dodaj() {
        try {
            FakturaBean.ewidencjavat(selected, evewidencjaDAO);
            if (fakturakorekta) {
                FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd podczas tworzenia rejestru VAT. Nie zachowano faktury");
            return;
        }
        selected.setKontrahent_nip(selected.getKontrahent().getNip());
        if (selected.getNazwa()!=null && selected.getNazwa().equals("")) {
            selected.setNazwa(null);
        }
        try {
            fakturaDAO.dodaj(selected);
            init();
            Msg.msg("i", "Dodano fakturę.");
            pokazfakture = false;
            fakturaxxl = false;
            fakturakorekta = false;
            fakturaniemiecka = false;
            selected = new Faktura();
            
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Błąd. Faktura o takim numerze i dla takiego kontrahenta już istnieje");
        }
//        RequestContext.getCurrentInstance().execute("PF('dokTableFaktury').sort();");
    }
    
    public void edytuj() {
        try {
            FakturaBean.ewidencjavat(selected, evewidencjaDAO);
            if (fakturakorekta) {
                FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd podczas tworzenia rejestru VAT. Nie zachowano faktury");
            return;
        }
        selected.setKontrahent_nip(selected.getKontrahent().getNip());
        selected.setRok(String.valueOf(wpisView.getRokWpisu()));
        selected.setMc(wpisView.getMiesiacWpisu());
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && wpisView.getPodatnikObiekt().getWystawcafaktury().equals("brak")) {
            selected.setPodpis("");
        } else if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && !wpisView.getPodatnikObiekt().getWystawcafaktury().equals("")) {
            selected.setPodpis(wpisView.getPodatnikObiekt().getWystawcafaktury());
        }  else {
            selected.setPodpis(podatnikobiekt.getImie() + " " + podatnikobiekt.getNazwisko());
        }
        if (selected.getNazwa()!=null && selected.getNazwa().equals("")) {
            selected.setNazwa(null);
        }
        try {
            fakturaDAO.edit(selected);
            if (selected.isWygenerowanaautomatycznie() == true) {
                   selected.setWygenerowanaautomatycznie(false);
            }
            fakturaDAO.edit(selected);
            faktury = new ArrayList<>();
            init();
            Msg.msg("i", "Wyedytowano fakturę.");
            pokazfakture = false;
            fakturaxxl = false;
            fakturaniemiecka = false;
            fakturazwykla = false;
            fakturakorekta = false;
            selected = new Faktura();
            
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Błąd. Niedokonano edycji faktury.");
        }
        RequestContext.getCurrentInstance().update("akordeon:formstworz");
        RequestContext.getCurrentInstance().update("akordeon:formsporzadzone");  
//        RequestContext.getCurrentInstance().execute("PF('dokTableFaktury').sort();");
    }

    
    
    public void skierujfakturedoedycji(Faktura faktura) {
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
        fakturaxxl = faktura.isFakturaxxl();
        fakturaniemiecka = faktura.isFakturaniemiecka13b();
        fakturakorekta = faktura.getPozycjepokorekcie() != null;
        aktywnytab = 0;
        pokazfakture = true;
        zapis0edycja1 = true;
        kontrahentstworz.findComponent(faktura.getKontrahent().getNpelna());
//        String funkcja = "PF('tworzenieklientapolenazwy').search('"+faktura.getKontrahent_nip()+"');";
//        RequestContext.getCurrentInstance().execute(funkcja);
//        funkcja = "PF('tworzenieklientapolenazwy').activate();";
//        RequestContext.getCurrentInstance().execute(funkcja);
       }
    
    public void skierujfakturedokorekty(Faktura faktura) {
        Msg.msg("Tworzenie korekty faktury");
        selected = serialclone.SerialClone.clone(faktura);
        selected.setZaksiegowana(false);
        selected.setWyslana(false);
        selected.setPrzyczynakorekty("Korekta faktury nr "+faktura.getFakturaPK().getNumerkolejny()+" z dnia "+faktura.getDatawystawienia()+" z powodu: ");
        fakturazwykla = faktura.isFakturaNormalna();
        fakturaxxl = faktura.isFakturaxxl();
        fakturaniemiecka = faktura.isFakturaniemiecka13b();
        fakturakorekta = true;
        aktywnytab = 0;
        pokazfakture = true;
        zapis0edycja1 = false;
        selected.getFakturaPK().setNumerkolejny(selected.getFakturaPK().getNumerkolejny()+"/KOR");
        selected.setPozycjepokorekcie(SerialClone.clone(faktura.getPozycjenafakturze()));
//        String funkcja = "PF('tworzenieklientapolenazwy').search('"+faktura.getKontrahent_nip()+"');";
//        RequestContext.getCurrentInstance().execute(funkcja);
//        funkcja = "PF('tworzenieklientapolenazwy').activate();";
//        RequestContext.getCurrentInstance().execute(funkcja);
       }

    private void waloryzacjakwoty(Faktura faktura, double procent) throws Exception {
        kwotaprzedwaloryzacja = faktura.getBrutto();
        List<Pozycjenafakturzebazadanych> pozycje = faktura.getPozycjenafakturze();
        double netto = 0.0;
        double vat = 0.0;
        double brutto = 0.0;
        ArrayList<Evewidencja> ew = new ArrayList<>();
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
        List<EVatwpis> el = new ArrayList<>();
        for (Pozycjenafakturzebazadanych p : pozycje) {
            double nowacena = p.getBrutto() * procent;
            nowacena = nowacena + p.getBrutto();
            nowacena = Math.round(nowacena);
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
        faktura.setNetto(netto);
        faktura.setVat(vat);
        double wartosc = brutto * 100;
        wartosc = Math.round(wartosc);
        wartosc = wartosc / 100;
        faktura.setBrutto(wartosc);

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
                fakturaDAO.destroy(p);
                faktury.remove(p);
                if (fakturyFiltered != null) {
                    fakturyFiltered.remove(p);
                }
                if (p.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(p);
                }
                Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getFakturaPK().getNumerkolejny());
            } catch (Exception e) { E.e(e); 
                Msg.msg("e", "Nie usunięto faktury sporządzonej: " + p.getFakturaPK().getNumerkolejny()+". Problem z aktualizacją okresowych.");
            }
        }
    }

    public void wymusdestroysporzadzone() {
        for (Faktura p : gosciwybral) {
            try {
                fakturaDAO.destroy(p);
                faktury.remove(p);
                if (fakturyFiltered != null) {
                    fakturyFiltered.remove(p);
                }
                if (p.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(p);
                }
                Msg.msg("i", "Usunięto fakturę sporządzoną: " + p.getFakturaPK().getNumerkolejny());
            } catch (Exception e) { E.e(e); 
                Msg.msg("e", "Nie usunięto faktury sporządzonej: " + p.getFakturaPK().getNumerkolejny());
            }
        }
    }

    public void destroyarchiwalne() {
        for (Faktura p : gosciwybral) {
            try {
                fakturaDAO.destroy(p);
                fakturyarchiwum.remove(p);
                if (fakturyFiltered != null) {
                    fakturyFiltered.remove(p);
                }
                if (p.isWygenerowanaautomatycznie() == true) {
                    zaktualizujokresowa(p);
                }
                Msg.msg("i", "Usunięto fakturę archiwalną: " + p.getFakturaPK().getNumerkolejny());
                usunfakturezaksiegowana(p);
            } catch (Exception e) { E.e(e); 
                Msg.msg("e", "Nie usunięto faktury archiwalnej: " + p.getFakturaPK().getNumerkolejny());
            }
            
        }
    }
    
    private void usunfakturezaksiegowana(Faktura p) {
        try {
                Dok znajdka = dokDAO.findFaktWystawione(p.getWystawca().getNazwapelna(), p.getKontrahent(), p.getFakturaPK().getNumerkolejny(), p.getBrutto());
                dokDAO.destroy(znajdka);
                Msg.msg("i", "Usunięto księgowanie faktury: " + p.getFakturaPK().getNumerkolejny());
            } catch (EJBException e1) {
                Msg.msg("w", "Faktura nie była zaksięgowana");
            } catch (Exception e2) {
                Msg.msg("e", "Błąd! Nie usunięto księgowania faktury: " + p.getFakturaPK().getNumerkolejny());
            }
    }

    private void zaktualizujokresowa(Faktura nowa) {
        String klientnip = nowa.getKontrahent_nip();
        Double brutto = nowa.getBrutto();
        Podatnik wystawca = nowa.getWystawca();
        String rok = nowa.getRok();
        Fakturywystokresowe fakturaokresowa = null;
        if (nowa.getIdfakturaokresowa() > 0) {
            fakturaokresowa = fakturywystokresoweDAO.findFakturaOkresowaById(nowa.getIdfakturaokresowa());
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
        fakturywystokresoweDAO.edit(fakturaokresowa);
        fakturyokresowe = fakturywystokresoweDAO.findPodatnik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        Msg.msg("i", "Zaktualizowano okresowa");
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:dokumentyOkresowe");
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
        RequestContext.getCurrentInstance().update("akordeon:formstworz:panel");
    }
    
    public void dodajwierszk() {
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        poz.setPodatek(23);
        poz.setIlosc(1);
        selected.getPozycjepokorekcie().add(poz);
        RequestContext.getCurrentInstance().update("akordeon:formstworz:panelkorekty");
    }

    public void usunwiersz() {
        if (!selected.getPozycjenafakturze().isEmpty()) {
            selected.getPozycjenafakturze().remove(selected.getPozycjenafakturze().size() - 1);
            RequestContext.getCurrentInstance().update("akordeon:formstworz:panel");
            String nazwafunkcji = "wybierzrzadfaktury()";
            RequestContext.getCurrentInstance().execute(nazwafunkcji);
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
            RequestContext.getCurrentInstance().execute(nazwafunkcji);
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
            RequestContext.getCurrentInstance().execute(nazwafunkcji);
        }
    }
    
    public void usunwierszk() {
        if (!selected.getPozycjepokorekcie().isEmpty()) {
            selected.getPozycjepokorekcie().remove(selected.getPozycjepokorekcie().size() - 1);
            RequestContext.getCurrentInstance().update("akordeon:formstworz:panelkorekty");
            String nazwafunkcji = "wybierzrzadfakturykorekta()";
            RequestContext.getCurrentInstance().execute(nazwafunkcji);
        }
    }

    public void zaksieguj() throws Exception {
        if (wpisView.getPodatnikObiekt().getFirmafk() == 1) {
            for (Faktura p : gosciwybral) {
                ksiegowanieFK(p);
            }
            Msg.msg("i", "Dokument zaksięgowany");
        } else if (wpisView.getPodatnikObiekt().getFirmafk() == 0) {
            for (Faktura p : gosciwybral) {
                ksiegowaniePkpir(p);
            }
            Msg.msg("i", "Dokument zaksięgowany");
        } else {
            if (wpisView.isKsiegirachunkowe() == true) {
                for (Faktura p : gosciwybral) {
                    ksiegowanieFK(p);
                }
                Msg.msg("i", "Dokument zaksięgowany");
            } else {
                for (Faktura p : gosciwybral) {
                    ksiegowaniePkpir(p);
                }
                Msg.msg("i", "Dokument zaksięgowany");
            }
        }
    }
    
    private void ksiegowanieFK(Faktura p) {
        Dokfk dokument = FDfkBean.stworznowydokument(FDfkBean.oblicznumerkolejny("SZ", dokDAOfk, wpisView),p, "SZ", wpisView, rodzajedokDAO, tabelanbpDAO, walutyDAOfk, kontoDAOfk, kliencifkDAO);
        try {
            dokument.setImportowany(true);
            dokDAOfk.dodaj(dokument);
            p.setZaksiegowana(true);
            fakturaDAO.edit(p);
            Msg.msg("Zaksięgowano dokument SZ o nr własnym"+dokument.getNumerwlasnydokfk());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu SZ");
        }
    }
    private void ksiegowaniePkpir(Faktura p) {
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
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(faktura.getDatawystawienia());
            selDokument.setDataSprz(faktura.getDatawystawienia());
            selDokument.setKontr(faktura.getKontrahent());
            selDokument.setRodzTrans("sprzedaz");
            selDokument.setTypdokumentu("SZ");
            selDokument.setNrWlDk(faktura.getFakturaPK().getNumerkolejny());
            selDokument.setOpis(faktura.getPozycjenafakturze().get(0).getNazwa());
            List<KwotaKolumna1> listaX = new ArrayList<>();
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(faktura.getNetto());
            tmpX.setVat(faktura.getVat());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(faktura.getBrutto()));
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(tmpX.getNetto());
            selDokument.setBrutto(tmpX.getBrutto());
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = new ArrayList<>();
            for (EVatwpis r : faktura.getEwidencjavat()) {
                EVatwpis1 eVatwpis1 = new EVatwpis1(r.getEwidencja(), r.getNetto(), r.getVat(), r.getEstawka());
                eVatwpis1.setDok(selDokument);
                ewidencjaTransformowana.add(eVatwpis1);
            }
            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
            try {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                String wiadomosc = "Zaksięgowano fakturę sprzedaży nr: " + selDokument.getNrWlDk() + ", kontrahent: " + selDokument.getKontr().getNpelna() + ", kwota: " + selDokument.getBrutto();
                Msg.msg("i", wiadomosc);
                faktura.setZaksiegowana(true);
                fakturaDAO.edit(faktura);
            } catch (Exception e) { E.e(e); 
                
            }
            RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
    }

    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception {
        Dok tmp = dokDAO.znajdzDuplikat(selD, selD.getPkpirR());
        if (tmp != null) {
            String wiadomosc = "Dokument dla tego klienta, o takim numerze i kwocie jest juz zaksiegowany: " + tmp.getDataK()+". Edytuje ksiegowanbie";
            throw new Exception(wiadomosc);
        } else {
        }
    }

    public void wgenerujnumerfaktury() throws IOException {
        if (zapis0edycja1 == false && fakturakorekta == false) {
            String nazwaklienta = (String) Params.params("akordeon:formstworz:acForce_input");
            if (!nazwaklienta.equals("nowy klient")) {
                if (selected.getKontrahent().getNskrocona() == null) {
                    Msg.msg("e", "Brak nazwy skróconej kontrahenta " + selected.getKontrahent().getNpelna() + ", nie mogę poprawnie wygenerować numeru faktury. Uzupełnij dane odbiorcy faktury.");
                    RequestContext.getCurrentInstance().execute("PF('nazwaskroconafaktura').show();");
                    RequestContext.getCurrentInstance().execute("$(document.getElementById(\"formkontowybor:wybormenu\")).focus();");
                } else {
                    FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
                }
            }
        }
    }
    
    

    public void dodajfaktureokresowa() {
        for (Faktura p : gosciwybral) {
            String podatnik = wpisView.getPodatnikWpisu();
            Fakturywystokresowe nowafakturaokresowa = new Fakturywystokresowe();
            nowafakturaokresowa.setDokument(p);
            nowafakturaokresowa.setPodatnik(podatnik);
            naznaczmiesiacnafakturzeokresowej(nowafakturaokresowa, p);
            nowafakturaokresowa.setBrutto(p.getBrutto());
            nowafakturaokresowa.setNipodbiorcy(p.getKontrahent_nip());
            String rok = p.getDatasprzedazy().split("-")[0];
            nowafakturaokresowa.setRok(rok);
            //nie ma czego sprawdzac bo zlikwidowalismy rozroznianie
//            try {
//                Fakturywystokresowe fakturatmp = null;
//                if (kwotaprzedwaloryzacja > 0) {
//                    fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik, p.getBrutto());
//                    //no bo jak sie juz zrobi z waloryzacja a potem usuwa to jest zaktualizowane
//                    if (fakturatmp == null) {
//                        fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik, p.getBrutto());
//                    } else {
//                        Msg.msg("e", "Faktura okresowa o parametrach: kontrahent - " + p.getKontrahent().getNpelna() + ", przedmiot - " + p.getPozycjenafakturze().get(0).getNazwa() + ", kwota - " + kwotaprzedwaloryzacja + " już istnienie!");
//                    }
//                } else {
//                    fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik, p.getBrutto());
//                }
//                if (fakturatmp != null) {
//                    if (kwotaprzedwaloryzacja > 0) {
//                        fakturatmp.setBrutto(p.getBrutto());
//                        fakturywystokresoweDAO.edit(fakturatmp);
//                    } else {
//                        Msg.msg("e", "Faktura okresowa o parametrach: kontrahent - " + p.getKontrahent().getNpelna() + ", przedmiot - " + p.getPozycjenafakturze().get(0).getNazwa() + ", kwota - " + p.getBrutto() + " już istnienie!");
//                    }
//                }
//            } catch (Exception ef) {
//                Msg.msg("w", "Błąd podczas wyszukiwania poprzedniej faktury");
//            }
            try {
                fakturywystokresoweDAO.dodaj(nowafakturaokresowa);
                fakturyokresowe.add(nowafakturaokresowa);
                Msg.msg("i", "Dodano fakturę okresową");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e", "Błąd podczas zachowania faktury w bazie danych " + e.getMessage());
            }
        }
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:dokumentyOkresowe");
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
    }
    
    public void dodajfaktureokresowanowyrok(Faktura p) {
        String podatnik = wpisView.getPodatnikWpisu();
        Fakturywystokresowe fakturyokr = new Fakturywystokresowe();
        fakturyokr.setDokument(p);
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
                fakturyokresowe = fakturywystokresoweDAO.findPodatnik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                Collections.sort(fakturyokresowe, new Fakturyokresowecomparator());
            } else {
                throw new Exception();
            }
        } catch (Exception ef) {
            fakturyokr.setM1(1);
            fakturyokresowe.add(fakturyokr);
            fakturywystokresoweDAO.dodaj(fakturyokr);
            Msg.msg("i", "Dodano fakturę okresową");
        }
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:dokumentyOkresowe");
    }

    public void usunfaktureokresowa() {
        for (Fakturywystokresowe p : gosciwybralokres) {
            fakturywystokresoweDAO.destroy(p);
            fakturyokresowe.remove(p);
            if (fakturyokresoweFiltered != null) {
                fakturyokresoweFiltered.remove(p);
            }
            Msg.msg("i", "Usunięto fakturę okresową");
        }
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:dokumentyOkresowe");
    }

    public void wygenerujzokresowychwaloryzacja() {
        wygenerujzokresowych();
        waloryzajca = 0.0;
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:kwotawaloryzacji");
        RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:dokumentyOkresowe");

    }

    public void wygenerujzokresowych() {
        for (Fakturywystokresowe p : gosciwybralokres) {
            Faktura nowa = SerialClone.clone(p.getDokument());
            if (waloryzajca > 0) {
                try {
                    waloryzacjakwoty(nowa, waloryzajca);
                } catch (Exception e) { E.e(e); 
                    Msg.msg("e", "Nieudane generowanie faktury okresowej z waloryzacją FakturaView:wygenerujzokresowych");
                }
            } else if (waloryzajca == -1) {
                try {
                    FakturaBean.ewidencjavat(nowa, evewidencjaDAO);
                    Msg.msg("i", "Generowanie nowej ewidencji vat");
                } catch (Exception e) { E.e(e); 
                    Msg.msg("e", "Nieudane generowanie nowej ewidencji vat dla faktury generowanej z okresowej FakturaView:wygenerujzokresowych");
                }
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
            nowa.setWygenerowanaautomatycznie(true);
            nowa.setIdfakturaokresowa(p.getId());
            nowa.setWyslana(false);
            nowa.setDatazaplaty(null);
            nowa.setZaksiegowana(false);
            nowa.setZatwierdzona(false);
            nowa.setAutor(wpisView.getWprowadzil().getLogin());
            int fakturanowyrok = 0;
            FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, nowa, wpisView);
            String datasprzedazy = nowa.getDatasprzedazy();
            String miesiacsprzedazy = datasprzedazy.substring(5, 7);
            String roksprzedazy = datasprzedazy.substring(0, 4);
            nowa.setRok(roksprzedazy);
            nowa.setMc(miesiacsprzedazy);
            try {
                fakturaDAO.dodaj(nowa);
                faktury.add(nowa);
                if (fakturanowyrok == 0) {
                    String datawystawienia = nowa.getDatawystawienia();
                    String miesiac = datawystawienia.substring(5, 7);
                    switch (miesiac) {
                        case "01":
                            p.setM1(p.getM1() + 1);
                            break;
                        case "02":
                            p.setM2(p.getM2() + 1);
                            break;
                        case "03":
                            p.setM3(p.getM3() + 1);
                            break;
                        case "04":
                            p.setM4(p.getM4() + 1);
                            break;
                        case "05":
                            p.setM5(p.getM5() + 1);
                            break;
                        case "06":
                            p.setM6(p.getM6() + 1);
                            break;
                        case "07":
                            p.setM7(p.getM7() + 1);
                            break;
                        case "08":
                            p.setM8(p.getM8() + 1);
                            break;
                        case "09":
                            p.setM9(p.getM9() + 1);
                            break;
                        case "10":
                            p.setM10(p.getM10() + 1);
                            break;
                        case "11":
                            p.setM11(p.getM11() + 1);
                            break;
                        case "12":
                            p.setM12(p.getM12() + 1);
                            break;
                    }
                    fakturywystokresoweDAO.edit(p);
                }
                Msg.msg("i", "Generuje bieżącą fakturę z okresowej. Kontrahent: " + nowa.getKontrahent().getNpelna());
            } catch (Exception e) { 
                E.e(e); 
                Faktura nibyduplikat = fakturaDAO.findbyNumerPodatnik(nowa.getFakturaPK().getNumerkolejny(), nowa.getFakturaPK().getWystawcanazwa());
                Msg.msg("e", "Faktura o takim numerze istnieje juz w bazie danych: data-" + nibyduplikat.getDatawystawienia()+" numer-"+nibyduplikat.getFakturaPK().getNumerkolejny()+" wystawca-"+nibyduplikat.getFakturaPK().getWystawcanazwa());
            }
        }
        RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
        RequestContext.getCurrentInstance().update("akordeon:formokresowe:dokumentyOkresowe");

    }

    public void resetujbiezacymiesiac() {
        if (gosciwybralokres.isEmpty()) {
            Msg.msg("e", "Nie wybrano faktury do resetu");
            return;
        }
        for (Fakturywystokresowe p : gosciwybralokres) {
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
            fakturywystokresoweDAO.edit(okresowe);
        }
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
        for (Faktura r : faktury) {
            if (r.getBrutto() == 123.0) {
                System.out.println("");
            }
            for (Fakturywystokresowe p : this.fakturyokresowe) {
                if (p.getDokument().getKontrahent().equals(r.getKontrahent()) && p.getDokument().getBrutto() == r.getBrutto()) {
                    naniesoznaczenienaokresowa(p);
                    break;
                }
            }

        }
    }
    
    private void naniesoznaczenienaokresowa(Fakturywystokresowe p) {
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
    
    public void skopiujdoNowegoroku() {
        for (Fakturywystokresowe stara : gosciwybralokres) {
            Fakturywystokresowe p = SerialClone.clone(stara);
            p.setRok(wpisView.getRokNastepnySt());
            p.setM1(0);
            p.setM2(0);
            p.setM3(0);
            p.setM4(0);
            p.setM5(0);
            p.setM6(0);
            p.setM7(0);
            p.setM8(0);
            p.setM9(0);
            p.setM10(0);
            p.setM11(0);
            p.setM12(0);
            fakturywystokresoweDAO.dodaj(p);
        }
        Msg.msg("Skopiowano okresowe do nowego roku");
    }

    public void sumawartosciwybranych() {
        podsumowaniewybranychbrutto = 0.0;
        podsumowaniewybranychnetto = 0.0;
        podsumowaniewybranychvat = 0.0;
        if (gosciwybral.size() > 0) {
            iloscwybranych = gosciwybral.size();
            for (Faktura p : gosciwybral) {
                if (p.getPozycjepokorekcie() == null) {
                    podsumowaniewybranychnetto += p.getNetto();
                    podsumowaniewybranychvat += p.getVat();
                    podsumowaniewybranychbrutto += p.getBrutto();
                } else {
                    podsumowaniewybranychnetto += (p.getNettopk()-p.getNetto());
                    podsumowaniewybranychvat += (p.getVatpk()-p.getVat());
                    podsumowaniewybranychbrutto += (p.getBruttopk()-p.getBrutto());
                }
            }
        } else {
            iloscwybranych = gosciwybralokres.size();
            for (Fakturywystokresowe p : gosciwybralokres) {
                podsumowaniewybranychnetto += p.getNetto();
                podsumowaniewybranychvat += p.getVat();
                podsumowaniewybranychbrutto += p.getBrutto();
            }
        }
    }
    
    

    public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        fakturyarchiwum.clear();
        aktualizuj();
        init();
        Msg.msg("i", "Udana zamiana klienta. Aktualny klient to: " + wpisView.getPodatnikWpisu() + " okres rozliczeniowy: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), "form:messages");
    }
    
     public void aktualizujTabeleTabelaGuest(AjaxBehaviorEvent e) throws IOException {
        fakturyarchiwum.clear();
        aktualizuj();
        init();
        Msg.msg("i", "Udana zamiana klienta. Aktualny klient to: " + wpisView.getPodatnikWpisu() + " okres rozliczeniowy: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), "form:messages");
    }
     
     

   
    private void aktualizuj() {
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpisView.naniesDaneDoWpis();
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpisView.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
    }
    
    public void mailfaktura() {
        try {
            pdfFaktura.drukujmail(gosciwybral, wpisView);
            Fakturadodelementy stopka = fakturadodelementyDAO.findFaktStopkaPodatnik(wpisView.getPodatnikWpisu());
            MailOther.faktura(gosciwybral, wpisView, fakturaDAO, wiadomoscdodatkowa, stopka.getTrescelementu(), SMTPBean.pobierzSMTP(sMTPSettingsDAO, wpisView.getWprowadzil()), sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Błąd podczas wysyłki faktury "+e.getMessage());
            System.out.println("Błąd podczas wysyłki faktury "+e.getMessage());
        }
    }
    
    public void pdffaktura() {
        try {
            pdfFaktura.drukuj(gosciwybral, wpisView);
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public void drukujfaktura() {
        try {
            pdfFaktura.drukujPrinter(gosciwybral, wpisView);
        } catch (Exception e) { E.e(e); 
        }
    }
    
    
    
    public void oznaczonejakowyslane() {
        OznaczFaktBean.oznaczonejakowyslane(gosciwybral, fakturaDAO);
    }
    
    public void oznaczonejakozaksiegowane() {
        OznaczFaktBean.oznaczonejakozaksiegowane(gosciwybral, fakturaDAO);
    }
    
    public void drukujokresowa() {
        try {
            pdfFaktura.drukujokresowa(gosciwybralokres);
        } catch (Exception e) { E.e(e); 
        }
    }

    public void edytujdanepodatnika () {
        podatnikDAO.edit(wpisView.getPodatnikObiekt());
        wpisView.initpublic();
        Msg.msg("Naniesiono dane do faktur.");
    }
    
    public void edytujnazwaskroconapodatnika() {
        selected.getKontrahent().setNskrocona(nazwaskroconafaktura.toUpperCase());
        podatnikDAO.edit(selected.getKontrahent());
        Msg.msg("Dopisano nazwę skróconą podatnika");
        FakturaOkresowaGenNum.wygenerujnumerfaktury(fakturaDAO, selected, wpisView);
    }
    
    
    public void dopasujterminplatnosci(ValueChangeEvent e) {
        String data = (String) e.getNewValue();
        if (data.matches("\\d{4}-\\d{2}-\\d{2}")) {
            selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(wpisView.getPodatnikObiekt(), data, selected.getDnizaplaty()));
            RequestContext.getCurrentInstance().update("akordeon:formstworz:terminzaplaty");
        }
    }
    
     public void dopasujterminplatnoscidni(ValueChangeEvent e) {
        int dnizaplaty = (int) e.getNewValue();
        selected.setTerminzaplaty(FakturaBean.obliczterminzaplaty(wpisView.getPodatnikObiekt(), selected.getDatawystawienia(), dnizaplaty));
        RequestContext.getCurrentInstance().update("akordeon:formstworz:terminzaplaty");
    }
     
    public int sortZaksiegowaneFaktury(Object o1, Object o2) {
        return FakturaSortBean.sortZaksiegowaneDok(o1, o2, wpisView);
    }
    
    public void drukujfakturysporzadzone() {
        try {
            String nazwapliku = "fakturysporzadzone-" + wpisView.getPodatnikWpisu() + ".pdf";
            File file = Plik.plik(nazwapliku, true);
            if (file.isFile()) {
                file.delete();
            }
            PdfFakturySporzadzone.drukujzapisy(wpisView, gosciwybral);
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
           String[] tabelanumer = p.getFakturaPK().getNumerkolejny().split("/");
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
           fakturaDAO.destroy(gosciwybral.get(i));
           p.getFakturaPK().setNumerkolejny(nowynumerfakt);
           fakturaDAO.dodaj(p);
        }
        gosciwybral = new ArrayList<>();
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
           String[] tabelanumer = p.getFakturaPK().getNumerkolejny().split("/");
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
           fakturaDAO.destroy(gosciwybral.get(i));
           p.getFakturaPK().setNumerkolejny(nowynumerfakt);
           fakturaDAO.dodaj(p);
        }
        gosciwybral = new ArrayList<>();
        init();
        Msg.msg("Przenumerowałem faktury");
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

    public boolean isFakturazwykla() {
        return fakturazwykla;
    }

    public void setFakturazwykla(boolean fakturazwykla) {
        this.fakturazwykla = fakturazwykla;
    }

    public AutoComplete getKontrahentstworz() {
        return kontrahentstworz;
    }

    public void setKontrahentstworz(AutoComplete kontrahentstworz) {
        this.kontrahentstworz = kontrahentstworz;
    }
    
    
//</editor-fold>

    
   

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
    
//   public void zrobfakt() {
//       List<Faktura> faktury = fakturaDAO.findAll();
//       for (Faktura p : faktury) {
//           p.setKontrahentID(p.getKontrahent().getId());
//           p.setWystawcaNIP(p.getWystawca().getNip());
//           fakturaDAO.edit(p);
//           System.out.println(p.getKontrahentID());
//           System.out.println(p.getWystawcaNIP());
//       }
//   }
    
}
