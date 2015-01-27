/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFaktura.FakturaBean;
import static com.sun.faces.el.ELUtils.createValueExpression;
import comparator.Fakturyokresowecomparator;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturywystokresoweDAO;
import dao.PodatnikDAO;
import dao.WpisDAO;
import embeddable.EVatwpis;
import embeddable.Mce;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Faktura;
import entity.FakturaPK;
import entity.Fakturywystokresowe;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Wpis;
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
import org.primefaces.component.column.Column;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;
import params.Params;
import pdf.PdfFaktura;
import serialclone.SerialClone;

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
    @Inject
    private FakturaPK fakturaPK;
    private boolean pokazfakture;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
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
    //tego potrzebuje zeby zachowac wiersz wzorcowy
    @Inject
    private PodatnikDAO podatnikDAO;
    private Double podsumowaniewybranychbrutto;
    private Double podsumowaniewybranychnetto;
    private Double podsumowaniewybranychvat;
    //do usuuwania faktur zaksiegowanych
    private double waloryzajca;
    private double kwotaprzedwaloryzacja;
    //wlasna data dla faktur
    private String datawystawienia;
    @Inject
    private WpisDAO wpisDAO;
    private String wiadomoscdodatkowa;
    private int aktywnytab;
    private boolean zapis0edycja1;
    private String nazwaskroconafaktura;
    private DataTable dataTablepozycjenafakturze;
    private DataTable dataTablepozycjenafakturzekorekta;
    private boolean fakturaxxl;
    private boolean fakturakorekta;
    private AutoComplete kontrahentstworz;
    

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
        faktury = new ArrayList<>();
        fakturyarchiwum = new ArrayList<>();
        fakturyokresoweFiltered = null;
        fakturyFiltered = null;
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
        fakturaxxl = false;
        fakturakorekta = false;
        inicjalizacjaczesciwspolne();
        dataTablepozycjenafakturze.setStyle("width: 740px;");
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setRodzajdokumentu("faktura");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę. Należy uzupełnić pozostałe elementy.");
    }
    
    public void przygotujfakturexxl() {
        fakturaxxl = true;
        fakturakorekta = false;
        inicjalizacjaczesciwspolne();
        dataTablepozycjenafakturze.setStyle("width: 1280px;");
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setRodzajdokumentu("faktura xxl");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę XXL. Należy uzupełnić pozostałe elementy.");
    }
    
     public void przygotujfakturekorekte() {
        fakturaxxl = false;
        fakturakorekta = true;
        inicjalizacjaczesciwspolne();
        dataTablepozycjenafakturze.setStyle("width: 740px;");
        dataTablepozycjenafakturzekorekta.setStyle("width: 740px;");
        Podatnik podatnikobiekt = wpisView.getPodatnikObiekt();
        selected.setPozycjenafakturze(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setPozycjepokorekcie(FakturaBean.inicjacjapozycji(podatnikobiekt));
        selected.setRodzajdokumentu("faktura korekta");
        selected.setRodzajtransakcji("sprzedaż");
        zapis0edycja1 = false;
        Msg.msg("i", "Przygotowano wstępnie fakturę. Należy uzupełnić pozostałe elementy.");
    }
     
    public void przygotujfakturekorektexxl() {
        fakturaxxl = true;
        fakturakorekta = true;
        inicjalizacjaczesciwspolne();
        dataTablepozycjenafakturze.setStyle("width: 1280px;");
        dataTablepozycjenafakturzekorekta.setStyle("width: 1280px;");
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
        selected.setDnizaplaty(Integer.parseInt(wpisView.getPodatnikObiekt().getPlatnoscwdni()));
        String pelnadata = FakturaBean.obliczdatawystawienia(wpisView);
        selected.setDatawystawienia(pelnadata);
        selected.setDatasprzedazy(pelnadata);
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

    public void dodaj() throws Exception {
        FakturaBean.ewidencjavat(selected, evewidencjaDAO);
        if (fakturakorekta) {
            FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
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
            selected = new Faktura();
            
        } catch (Exception e) {
            Msg.msg("e", "Błąd. Faktura o takim numerze i dla takiego kontrahenta już istnieje");
        }
        RequestContext.getCurrentInstance().update("akordeon:formstworz");
        RequestContext.getCurrentInstance().update("akordeon:formsporzadzone");  
//        RequestContext.getCurrentInstance().execute("PF('dokTableFaktury').sort();");
    }
    
    public void edytuj() throws Exception {
        FakturaBean.ewidencjavat(selected, evewidencjaDAO);
        if (fakturakorekta) {
            FakturaBean.ewidencjavatkorekta(selected, evewidencjaDAO);
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
                   zaktualizujokresowa(selected);
                   selected.setWygenerowanaautomatycznie(false);
            }
            fakturaDAO.edit(selected);
            init();
            Msg.msg("i", "Wyedytowano fakturę.");
            pokazfakture = false;
            fakturaxxl = false;
            fakturakorekta = false;
            selected = new Faktura();
            
        } catch (Exception e) {
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
            dataTablepozycjenafakturze.setStyle("width: 1280px;");
        } else {
            dataTablepozycjenafakturze.setStyle("width: 740px;");
        }
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
        selected.setPrzyczynakorekty("Korekta faktury nr "+faktura.getFakturaPK().getNumerkolejny()+" z dnia "+faktura.getDatawystawienia()+" z powodu: ");
        fakturaxxl = faktura.isFakturaxxl();
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
            } catch (Exception e) {
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
        Fakturywystokresowe fakturaokresowa = fakturywystokresoweDAO.findOkresowa(rok, klientnip, wystawca.getNazwapelna());
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
        selected.getPozycjenafakturze().add(poz);
        RequestContext.getCurrentInstance().update("akordeon:formstworz:panel");
    }
    
    public void dodajwierszk() {
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        poz.setPodatek(23);
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
    
    public void usunwierszk() {
        if (!selected.getPozycjepokorekcie().isEmpty()) {
            selected.getPozycjepokorekcie().remove(selected.getPozycjepokorekcie().size() - 1);
            RequestContext.getCurrentInstance().update("akordeon:formstworz:panelkorekty");
            String nazwafunkcji = "wybierzrzadfakturykorekta()";
            RequestContext.getCurrentInstance().execute(nazwafunkcji);
        }
    }

    public void zaksieguj() throws Exception {
        for (Faktura p : gosciwybral) {
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
            double wartosc = faktura.getBrutto() * 100;
            wartosc = Math.round(wartosc);
            wartosc = wartosc / 100;
            tmpX.setBrutto(wartosc);
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
            } catch (Exception e) {
                
            }
            RequestContext.getCurrentInstance().update("akordeon:formsporzadzone:dokumentyLista");
        }
        Msg.msg("i", "Dokument zaksięgowany");
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
                   wygenerujnumerfakturycd();
                }
            }
        }
    }
    
    private void wygenerujnumerfakturycd() {
         List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahentNipRok(selected.getKontrahent().getNip(), wpisView.getPodatnikWpisu(), String.valueOf(wpisView.getRokWpisu()));
                int istniejafakturykontrahenta = 0;
                try {
                    if (wykazfaktur.size() > 0) {
                        istniejafakturykontrahenta = 1;
                    }
                } catch (Exception er) {
                }
                List<Faktura> wykazfakturogolem = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
                int istniejafakturyrok = 0;
                try {
                    if (wykazfakturogolem.size() > 0) {
                        istniejafakturyrok = 1;
                    }
                } catch (Exception er) {
                }
         if (wpisView.getPodatnikObiekt().getSchematnumeracji() != null && !wpisView.getPodatnikObiekt().getSchematnumeracji().equals("")) {
                        if (istniejafakturyrok == 0) {
                            String numer = FakturaBean.uzyjwzorcagenerujpierwszynumerFaktura(wpisView.getPodatnikObiekt().getSchematnumeracji(), wpisView);
                            selected.getFakturaPK().setNumerkolejny(numer);
                            selected.setLp(1);
                            Msg.msg("i", "Generuje nową serie numerów faktury");
                        } else {
                            String numer = FakturaBean.uzyjwzorcagenerujnumerFaktura(wpisView.getPodatnikObiekt().getSchematnumeracji(), wpisView, fakturaDAO);
                            selected.getFakturaPK().setNumerkolejny(numer);
                            Faktura ostatnidokument = fakturaDAO.findOstatniaFakturaByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
                            selected.setLp(ostatnidokument.getLp()+1);
                            Msg.msg("i", "Generuje kolejny numer faktury");
                        }
                        RequestContext.getCurrentInstance().update("akordeon:formstworz:nrfaktury");
                        RequestContext.getCurrentInstance().execute("przeskoczdoceny();");
                    } else {
                        if (istniejafakturykontrahenta == 0) {
                            int dlugoscnazwy = selected.getKontrahent().getNskrocona().length();
                            String nazwadofaktury = dlugoscnazwy > 4 ? selected.getKontrahent().getNskrocona().substring(0, 4) : selected.getKontrahent().getNskrocona();
                            String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                            selected.getFakturaPK().setNumerkolejny(numer);
                            Msg.msg("i", "Generuje nową serie numerów faktury");
                        } else {
                            String ostatniafaktura = wykazfaktur.get(wykazfaktur.size() - 1).getFakturaPK().getNumerkolejny();
                            String separator = "/";
                            String[] elementy;
                            elementy = ostatniafaktura.split(separator);
                            int starynumer = Integer.parseInt(elementy[0]);
                            starynumer++;
                            String numer = String.valueOf(starynumer);
                            int i = 0;
                            for (String p : elementy) {
                                if (i > 0) {
                                    numer += "/" + p;
                                }
                                i++;
                            }
                            selected.getFakturaPK().setNumerkolejny(numer);
                            Msg.msg("i", "Generuje kolejny numer faktury");
                        }
                        RequestContext.getCurrentInstance().update("akordeon:formstworz:nrfaktury");
                        RequestContext.getCurrentInstance().execute("przeskoczdoceny();");
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
            try {
                Fakturywystokresowe fakturatmp = null;
                if (kwotaprzedwaloryzacja > 0) {
                    fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik);
                    //no bo jak sie juz zrobi z waloryzacja a potem usuwa to jest zaktualizowane
                    if (fakturatmp == null) {
                        fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik);
                    } else {
                        Msg.msg("e", "Faktura okresowa o parametrach: kontrahent - " + p.getKontrahent().getNpelna() + ", przedmiot - " + p.getPozycjenafakturze().get(0).getNazwa() + ", kwota - " + kwotaprzedwaloryzacja + " już istnienie!");
                    }
                } else {
                    fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik);
                }
                if (fakturatmp != null) {
                    if (kwotaprzedwaloryzacja > 0) {
                        fakturatmp.setBrutto(p.getBrutto());
                        fakturywystokresoweDAO.edit(fakturatmp);
                    } else {
                        Msg.msg("e", "Faktura okresowa o parametrach: kontrahent - " + p.getKontrahent().getNpelna() + ", przedmiot - " + p.getPozycjenafakturze().get(0).getNazwa() + ", kwota - " + p.getBrutto() + " już istnienie!");
                    }
                }
            } catch (Exception ef) {
                Msg.msg("w", "Błąd podczas wyszukiwania poprzedniej faktury");
            }
            try {
                fakturywystokresoweDAO.dodaj(nowafakturaokresowa);
                fakturyokresowe.add(nowafakturaokresowa);
                Msg.msg("i", "Dodano fakturę okresową");
            } catch (Exception e) {
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
                fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik);
                //no bo jak sie juz zrobi z waloryzacja a potem usuwa to jest zaktualizowane
                if (fakturatmp == null) { 
                    fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik);
                }
            } else {
                fakturatmp = fakturywystokresoweDAO.findOkresowa(rok, p.getKontrahent_nip(), podatnik);
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
                } catch (Exception e) {
                    Msg.msg("e", "Nieudane generowanie faktury okresowej z waloryzacją FakturaView:641");
                }
            } else if (waloryzajca == -1) {
                try {
                    FakturaBean.ewidencjavat(nowa, evewidencjaDAO);
                    Msg.msg("i", "Generowanie nowej ewidencji vat");
                } catch (Exception e) {
                    Msg.msg("e", "Nieudane generowanie nowej ewidencji vat dla faktury generowanej z okresowej FakturaView:691");
                }
            }
            int dniDoZaplaty = Integer.parseInt(wpisView.getPodatnikObiekt().getPlatnoscwdni());
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
            nowa.setWyslana(false);
            nowa.setZaksiegowana(false);
            nowa.setZatwierdzona(false);
            nowa.setAutor(wpisView.getWprowadzil().getLogin());
            List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahentNipRok(nowa.getKontrahent().getNip(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            int fakturanowyrok = 0;
            if (wykazfaktur.isEmpty()) {
                int dlugoscnazwy = nowa.getKontrahent().getNskrocona().length();
                String nazwadofaktury = dlugoscnazwy > 4 ? nowa.getKontrahent().getNskrocona().substring(0,4) : nowa.getKontrahent().getNskrocona();
                String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                nowa.getFakturaPK().setNumerkolejny(numer);
            } else {
                int nrkolejny = 0;
                String separator = "/";
                String[] elementy = null;
                for (Faktura f : wykazfaktur) {
                    if (nrkolejny == 0) {
                        elementy = f.getFakturaPK().getNumerkolejny().split(separator);
                        nrkolejny = Integer.parseInt(elementy[0]);
                    } else {
                        String[] elementytmp = f.getFakturaPK().getNumerkolejny().split(separator);
                        int nrkolejnytmp = Integer.parseInt(elementytmp[0].replaceAll("[^\\d.]", ""));
                        if (nrkolejnytmp > nrkolejny) {
                            nrkolejny = nrkolejnytmp;
                            elementy  = f.getFakturaPK().getNumerkolejny().split(separator);
                        }
                    }
                }
                //sprawdzamy czy nie ma zmiany roku
                String biezacyrok = wpisView.getRokWpisu().toString();
                String rokzestarejfaktury = elementy[1];
                if (biezacyrok.equals(rokzestarejfaktury)) {
                    int starynumer = Integer.parseInt(elementy[0]);
                    starynumer++;
                    String numer = String.valueOf(starynumer);
                    int i = 0;
                    for (String r : elementy) {
                        if (i > 0) {
                            numer += "/" + r;
                        }
                        i++;
                    }
                    nowa.getFakturaPK().setNumerkolejny(numer);
                    nowa.setWygenerowanaautomatycznie(true);
                } else {
                    int dlugoscnazwy = nowa.getKontrahent().getNskrocona().length();
                    String nazwadofaktury = dlugoscnazwy > 4 ? nowa.getKontrahent().getNskrocona().substring(0,4) : nowa.getKontrahent().getNskrocona();
                    String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                    nowa.getFakturaPK().setNumerkolejny(numer);
                    nowa.setWygenerowanaautomatycznie(true);
                    dodajfaktureokresowanowyrok(nowa);
                    fakturanowyrok = 1;
                }
            }
            String datasprzedazy = nowa.getDatasprzedazy();
            String miesiacsprzedazy = datasprzedazy.substring(5, 7);
            String roksprzedazy = datasprzedazy.substring(0, 4);
            nowa.setRok(roksprzedazy);
            nowa.setMc(miesiacsprzedazy);
            try {
                fakturaDAO.dodaj(nowa);
                faktury.add(nowa);
                if (fakturanowyrok == 0) {
                    Fakturywystokresowe okresowe = p;
                    String datawystawienia = nowa.getDatawystawienia();
                    String miesiac = datawystawienia.substring(5, 7);
                    switch (miesiac) {
                        case "01":
                            okresowe.setM1(okresowe.getM1() + 1);
                            break;
                        case "02":
                            okresowe.setM2(okresowe.getM2() + 1);
                            break;
                        case "03":
                            okresowe.setM3(okresowe.getM3() + 1);
                            break;
                        case "04":
                            okresowe.setM4(okresowe.getM4() + 1);
                            break;
                        case "05":
                            okresowe.setM5(okresowe.getM5() + 1);
                            break;
                        case "06":
                            okresowe.setM6(okresowe.getM6() + 1);
                            break;
                        case "07":
                            okresowe.setM7(okresowe.getM7() + 1);
                            break;
                        case "08":
                            okresowe.setM8(okresowe.getM8() + 1);
                            break;
                        case "09":
                            okresowe.setM9(okresowe.getM9() + 1);
                            break;
                        case "10":
                            okresowe.setM10(okresowe.getM10() + 1);
                            break;
                        case "11":
                            okresowe.setM11(okresowe.getM11() + 1);
                            break;
                        case "12":
                            okresowe.setM12(okresowe.getM12() + 1);
                            break;
                    }
                    fakturywystokresoweDAO.edit(okresowe);
                }
                Msg.msg("i", "Generuje bieżącą fakturę z okresowej. Kontrahent: " + nowa.getKontrahent().getNpelna());
            } catch (Exception e) {
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
            for (Fakturywystokresowe p : gosciwybralokres) {
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
        wpisView.findWpis();
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
            MailOther.faktura(gosciwybral, wpisView, fakturaDAO, wiadomoscdodatkowa);
        } catch (Exception e) {
            Msg.msg("e","Błąd podczas wysyłki faktury "+e.getMessage());
            System.out.println("Błąd podczas wysyłki faktury "+e.getMessage());
        }
    }
    
    public void pdffaktura() {
        try {
            pdfFaktura.drukuj(gosciwybral, wpisView);
        } catch (Exception e) {
        }
    }
    
    public void drukujfaktura() {
        try {
            pdfFaktura.drukujPrinter(gosciwybral, wpisView);
        } catch (Exception e) {
        }
    }
    
    
    
    public void oznaczonejakowyslane() {
        MailOther.oznaczonejakowyslane(gosciwybral, fakturaDAO);
    }
    
    public void oznaczonejakozaksiegowane() {
        MailOther.oznaczonejakozaksiegowane(gosciwybral, fakturaDAO);
    }
    
    public void drukujokresowa() {
        try {
            pdfFaktura.drukujokresowa(gosciwybralokres);
        } catch (Exception e) {
        }
    }

    public void edytujdanepodatnika () {
        podatnikDAO.edit(wpisView.getPodatnikObiekt());
        Msg.msg("Naniesiono dane do faktur.");
    }
    
    public void edytujnazwaskroconapodatnika() {
        selected.getKontrahent().setNskrocona(nazwaskroconafaktura.toUpperCase());
        podatnikDAO.edit(selected.getKontrahent());
        Msg.msg("Dopisano nazwę skróconą podatnika");
        wygenerujnumerfakturycd();
    }
    
    public void dodajkolumne() {
        Column dodkolumna = new Column();
        dodkolumna.setHeaderText("cena netto");
        InputText t = new InputText();
        t.setValueExpression("value", createValueExpression("#{row.nowakolumna}", String.class));
        dodkolumna.getChildren().add(t);
        dataTablepozycjenafakturze.getChildren().add(dodkolumna);
        Msg.msg("Dodaję kolumnę");
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

    public FakturaPK getFakturaPK() {
        return fakturaPK;
    }

    public void setFakturaPK(FakturaPK fakturaPK) {
        this.fakturaPK = fakturaPK;
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

    public DataTable getDataTablepozycjenafakturze() {
        return dataTablepozycjenafakturze;
    }

    public void setDataTablepozycjenafakturze(DataTable dataTablepozycjenafakturze) {
        this.dataTablepozycjenafakturze = dataTablepozycjenafakturze;
    }

    public AutoComplete getKontrahentstworz() {
        return kontrahentstworz;
    }

    public void setKontrahentstworz(AutoComplete kontrahentstworz) {
        this.kontrahentstworz = kontrahentstworz;
    }
    
    
//</editor-fold>

    public DataTable getDataTablepozycjenafakturzekorekta() {
        return dataTablepozycjenafakturzekorekta;
    }

    public void setDataTablepozycjenafakturzekorekta(DataTable dataTablepozycjenafakturzekorekta) {
        this.dataTablepozycjenafakturzekorekta = dataTablepozycjenafakturzekorekta;
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
    
    
}
