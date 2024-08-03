/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.EVatwpisFKcomparator;
import comparator.EVatwpisSupercomparator;
import dao.EVatwpis1DAO;
import dao.EVatwpisDedraDAO;
import dao.EVatwpisFKDAO;
import dao.EVatwpisIncydentalniDAO;
import dao.EVatwpisSumaDAO;
import dao.EvewidencjaDAO;
import dao.PlatnoscWalutaDAO;
import dao.RodzajedokDAO;
import dao.SMTPSettingsDAO;
import dao.StronaWierszaDAO;
import dao.TransakcjaDAO;
import dao.WniosekVATZDEntityDAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import entity.Dok;
import entity.EVatwpis1;
import entity.EVatwpisKJPK;
import entity.EVatwpisSuma;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.PlatnoscWaluta;
import entity.Podatnik;
import entity.WniosekVATZDEntity;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.MailOther;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.UnselectEvent;
import pdf.PdfVAT;
 import pdf.PdfVATsuma;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class EwidencjaVatView implements Serializable {

    private HashMap<String, List<EVatwpisSuper>> listaewidencji;
    private List<String> nazwyewidencji;
    private List<List<EVatwpis1>> ewidencje;
    private List<List<EVatwpisFK>> ewidencjeFK;
    private HashMap<String, EVatwpisSuma> sumaewidencji;
    private List<EVatwpisSuma> goscwybralsuma;
    private List<EVatwpisSuma> sumydowyswietleniasprzedaz;
    private List<EVatwpisSuma> sumydowyswietleniazakupy;
    private List<EVatwpisSuma> sumydowyswietleniasprzedazNiemcy;
    private List<EVatwpisSuma> sumydowyswietleniazakupyNiemcy;
    private List<EVatwpisSuma> sumydowyswietleniasprzedazNiemcyRok;
    private List<EVatwpisSuma> sumydowyswietleniazakupyNiemcyRok;
    private BigDecimal wynikOkresu;
    private BigDecimal wynikOkresuNiemcy;
    private BigDecimal wynikOkresuNiemcyRok;
    private List<EVatwpisSuper> listadokvatprzetworzona;
    private List<EVatwpisFK> listaprzesunietychKoszty;
    private List<EVatwpisFK> listaprzesunietychPrzychody;
    private List<EVatwpisFK> listaprzesunietychBardziej;
    private List<EVatwpisFK> listaprzesunietychBardziejPrzychody;
    private double sumaprzesunietych;
    private double sumaprzesunietychprzychody;
    private double sumaprzesunietychBardziej;
    private double sumaprzesunietychBardziejPrzychody;
    @Inject
    private Dok selected;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private PlatnoscWalutaDAO platnoscWalutaDAO;
    @Inject
    private TransakcjaDAO transakcjaDAO;
    //elementy niezbedne do generowania ewidencji vat
    private TabView akordeon;
    @Inject
    private EVatwpisFKDAO eVatwpisFKDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private EVatwpisSumaDAO eVatwpisSumaDAO;
    @Inject
    private WpisView wpisView;
    private List<EVatwpisSuper> goscwybral;
    private List<EVatwpisSuper> filtered;
    private List<String> listanowa;
    private Double suma1;
    private Double suma2;
    private Double suma3;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private String nazwaewidencjiMail;
    private List<EVatwpisSuper> wybranewierszeewidencji;
    private List<EVatwpisSuper> filteredwierszeewidencji;
    private List<EVatwpisSuper> zachowanewybranewierszeewidencji;
    private Evewidencja ewidencjazakupu;
//    private String ewidencjadosprawdzania;
    private List<EVatwpisSuper> wybranaewidencja;
    private boolean pobierzmiesiacdlajpk;
    @Inject
    private EVatwpisDedraDAO eVatwpisDedraDAO;
    @Inject
    private EVatwpisIncydentalniDAO eVatwpisIncydentalniDAO;
    @Inject
    private WniosekVATZDEntityDAO wniosekVATZDEntityDAO;

    public EwidencjaVatView() {
        nazwyewidencji = Collections.synchronizedList(new ArrayList<>());
        ewidencje = Collections.synchronizedList(new ArrayList<>());
        ewidencjeFK = new ArrayList<>();
        listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
        sumaewidencji = new HashMap<>();
        goscwybral = Collections.synchronizedList(new ArrayList<>());
        listanowa = Collections.synchronizedList(new ArrayList<>());
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
    }

    @PostConstruct
    private void init() { //E.m(this);
        try {
            if (wpisView.getMiesiacWpisu().equals("CR")) {
                wpisView.setMiesiacWpisu(Data.aktualnyMc());
                wpisView.wpisAktualizuj();
            }

        } catch (Exception e) { E.e(e); 

       }
    }

    public void aktualizujTabeleTabela(boolean polska0niemcy1) {
        init();
        stworzenieEwidencjiZDokumentow(wpisView.getPodatnikObiekt(), polska0niemcy1);
        Msg.msg("i","Udana zamiana mieiąca");
    }
    
    private void aktualizuj(){
         wpisView.naniesDaneDoWpisOkres();
    }
    
    public void wybranewierszeewidencjiczysc() {
        wybranewierszeewidencji = null;
        filteredwierszeewidencji = null;
        zachowanewybranewierszeewidencji = null;
        PrimeFaces.current().ajax().update("form:akordeon:akordeon2");
    }
    
    private void zerujListy() {
        ewidencje = Collections.synchronizedList(new ArrayList<>());
        ewidencjeFK = new ArrayList<>();
        goscwybral = Collections.synchronizedList(new ArrayList<>());
        nazwyewidencji = Collections.synchronizedList(new ArrayList<>());
        listaewidencji = new HashMap<>();
        sumydowyswietleniasprzedaz = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniazakupy = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniasprzedazNiemcy = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniazakupyNiemcy = Collections.synchronizedList(new ArrayList<>());
        listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
        sumaewidencji = new HashMap<>();
        listaprzesunietychKoszty = Collections.synchronizedList(new ArrayList<>());
    }
    
    private org.primefaces.component.tabview.TabView iTabPanel;

    public org.primefaces.component.tabview.TabView getiTabPanel() {
        return iTabPanel;
    }

    public void setiTabPanel(org.primefaces.component.tabview.TabView iTabPanel) {
        this.iTabPanel = iTabPanel;
    }
    
    public void usunpozycje(List<EVatwpis1> l, EVatwpis1 p) {
        eVatwpis1DAO.remove(p);
        l.remove(p);
        Msg.dP();
    }
    
            
    public void fakturasprawdzanie(int l, List<EVatwpisFK> lista) {
        EVatwpisFK w = null;
        if (zachowanewybranewierszeewidencji!= null && zachowanewybranewierszeewidencji.size()==1) {
            w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(0);
        } else {
            w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(zachowanewybranewierszeewidencji.size()-1);
        }
        if (w!=null) {
            int rowek = 0;
            for (EVatwpisFK s : lista) {
                if (s.equals(w)) {
                    s.setSprawdzony(l);
                    if (s.isDuplikat()) {
                        Msg.msg("w", "Oznaczono zapis zduplikowany. Zmiany nie zostaną zachowane w bazie", "grmes");
                    } else {
                        eVatwpisFKDAO.edit(s);
                    }
                    break;
                }
                rowek++;
            }
            String p = "form:akordeon:akordeon2:"+iTabPanel.getActiveIndex()+":tabela:"+rowek+":polespr";
            PrimeFaces.current().ajax().update(p);
        }
    }
    
    public void fakturasprawdzanieD(int l, List<EVatwpisFK> lista) {
        EVatwpisFK w = null;
        if (zachowanewybranewierszeewidencji!= null && zachowanewybranewierszeewidencji.size()==1) {
            w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(0);
        } else {
            w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(zachowanewybranewierszeewidencji.size()-1);
        }
        if (w!=null) {
            int rowek = 0;
            for (EVatwpisFK s : lista) {
                if (s.equals(w)) {
                    s.setSprawdzony(l);
                    if (s.isDuplikat()) {
                        Msg.msg("w", "Oznaczono zapis zduplikowany. Zmiany nie zostaną zachowane w bazie", "grmes");
                    } else {
                        eVatwpisFKDAO.edit(s);
                    }
                    break;
                }
                rowek++;
            }
            String p = "formm:akordeon:akordeon2:"+iTabPanel.getActiveIndex()+":tabela:"+rowek+":polespr";
            PrimeFaces.current().ajax().update(p);
        }
    }
    
    
    public void fakturavatoznaczanieD() {
        EVatwpisFK w = null;
        if (zachowanewybranewierszeewidencji!=null) {
            if (zachowanewybranewierszeewidencji.size()==1) {
                w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(0);
            } else {
                w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(zachowanewybranewierszeewidencji.size()-1);
            }
            if (w!=null) {
                int rowek = 0;
                List<EVatwpisFK> lista = ewidencjeFK.get(iTabPanel.getActiveIndex());
                for (EVatwpisFK s : lista) {
                    if (s.equals(w)) {
                        s.setSprawdzony(s.getSprawdzony()==0?1:s.getSprawdzony()==1?2:0);
                        if (s.isDuplikat()) {
                            Msg.msg("w", "Oznaczono zapis zduplikowany. Zmiany nie zostaną zachowane w bazie", "grmes");
                        } else {
                            eVatwpisFKDAO.edit(s);
                        }
                        break;
                    }
                    rowek++;
                }
                String p = "formm:akordeon:akordeon2:"+iTabPanel.getActiveIndex()+":tabela:"+rowek+":polespr";
                PrimeFaces.current().ajax().update(p);
            }
        }
    }
    
    public void fakturavatoznaczanie() {
        EVatwpisFK w = null;
        if (zachowanewybranewierszeewidencji!=null) {
            if (zachowanewybranewierszeewidencji.size()==1) {
                w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(0);
            } else {
                w = (EVatwpisFK) zachowanewybranewierszeewidencji.get(zachowanewybranewierszeewidencji.size()-1);
            }
            if (w!=null) {
                int rowek = 0;
                List<EVatwpisFK> lista = ewidencjeFK.get(iTabPanel.getActiveIndex());
                for (EVatwpisFK s : lista) {
                    if (s.equals(w)) {
                        s.setSprawdzony(s.getSprawdzony()==0?1:s.getSprawdzony()==1?2:0);
                        if (s.isDuplikat()) {
                            Msg.msg("w", "Oznaczono zapis zduplikowany. Zmiany nie zostaną zachowane w bazie", "grmes");
                        } else {
                            eVatwpisFKDAO.edit(s);
                        }
                        break;
                    }
                    rowek++;
                }
                String p = "form:akordeon:akordeon2:"+iTabPanel.getActiveIndex()+":tabela:"+rowek+":polespr";
                PrimeFaces.current().ajax().update(p);
            }
        }
    }
    
    
    
    

    public void stworzenieEwidencjiZDokumentow(Podatnik podatnik, boolean polska0niemcy1) {
        try {
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            int vatokres = wpisView.getVatokres();
            if (pobierzmiesiacdlajpk) {
                vatokres = 1;
            }
            if (wpisView.sprawdzczyue() && vatokres==0) {
                vatokres = 1;
            }
            pobierzEVATwpis1zaOkres(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            ReentrantLock lock = new ReentrantLock();
            if (listadokvatprzetworzona != null) {
                List<EVatwpisSuper> synchronizedList = Collections.synchronizedList(listadokvatprzetworzona);

                listadokvatprzetworzona = synchronizedList.parallelStream()
                    .filter(it -> {
                        EVatwpis1 p = (EVatwpis1) it;
                        lock.lock();
                        try {
                            return (p.getDok() == null || !p.getDok().getRodzajedok().isTylkojpk() && p.getEwidencja().isNiemcy()==polska0niemcy1) ;
                        } finally {
                            lock.unlock();
                        }
                    })
                    .collect(Collectors.toList());
            }
            przejrzyjEVatwpis1Lista();
            stworzenieEwidencjiCzescWspolna();
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            for (List p : listaewidencji.values()) {
                ewidencje.add(p);
            }
            pobierzmiesiacdlajpk = false;

        } catch (Exception e) { 
            Msg.msg("e","Błąd przy tworzeniu ewidencji z dokumentów");
            E.e(e);
        }
        //drukuj ewidencje
    }
    
    
    
     public void stworzenieEwidencjiZDokumentowJPK(Podatnik podatnik) {
        try {
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            int vatokres = wpisView.getVatokres();
            if (pobierzmiesiacdlajpk) {
                vatokres = 1;
            }
            if (wpisView.sprawdzczyue() && vatokres==0) {
                vatokres = 1;
            }
            pobierzEVATwpis1zaOkres(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            przejrzyjEVatwpis1Lista();
             
            //PrimeFaces.current().ajax().update("formVatZestKsiegowa");
            //Msg.msg("Sporządzono ewidencje");
        } catch (Exception e) { 
            Msg.msg("e","Błąd przy tworzeniu ewidencji z dokumentów");
            E.e(e);
        }
        //drukuj ewidencje
    }
    
    public void stworzenieEwidencjiZDokumentowDedra(Podatnik podatnik) {
        try {
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            int vatokres = wpisView.getVatokres();
            if (pobierzmiesiacdlajpk) {
                vatokres = 1;
            }
            pobierzEVatwpisDedrazaOkres(podatnik, vatokres);
            //przejrzyjEVatwpis1Lista();
            stworzenieEwidencjiCzescWspolnaDedra();
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            for (List p : listaewidencji.values()) {
                ewidencje.add(p);
            }
            pobierzmiesiacdlajpk = false;
            PrimeFaces.current().ajax().update("formVatZestKsiegowa");
            Msg.msg("Sporządzono deklarację VAT");
        } catch (Exception e) { 
            Msg.dPe();
            E.e(e);
        }
        //drukuj ewidencje
    }

  
    public void stworzenieEwidencjiZDokumentowFK(Podatnik podatnik, WniosekVATZDEntity wniosekVATZDEntity) {
        try {
            listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            String vatokres = sprawdzjakiokresvat();
            if (pobierzmiesiacdlajpk) {
                vatokres = "miesięczne";
            }
            if (wpisView.getPodatnikObiekt().getMetodakasowa().equals("tak")) {
                listadokvatprzetworzona = przetworzRozliczenia(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
            } else {
                listadokvatprzetworzona.addAll(pobierzEVatRokFK(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), false));
                Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
                listaprzesunietychKoszty = pobierzEVatRokFKNastepnyOkres(vatokres);
                wyluskajzlisty(listaprzesunietychKoszty, "koszty");
                sumaprzesunietych = sumujprzesuniete(listaprzesunietychKoszty);
                listaprzesunietychBardziej = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
                wyluskajzlisty(listaprzesunietychBardziej, "koszty");
                sumaprzesunietychBardziej = sumujprzesuniete(listaprzesunietychBardziej);
                listaprzesunietychPrzychody = pobierzEVatRokFKNastepnyOkres(vatokres);
                wyluskajzlisty(listaprzesunietychPrzychody, "przychody");
                sumaprzesunietychprzychody = sumujprzesuniete(listaprzesunietychPrzychody);
                listaprzesunietychBardziejPrzychody = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
                wyluskajzlisty(listaprzesunietychBardziejPrzychody, "przychody");
                sumaprzesunietychBardziejPrzychody = sumujprzesuniete(listaprzesunietychBardziejPrzychody);
            }
           ReentrantLock lock = new ReentrantLock();
            if (listadokvatprzetworzona != null) {
                List<EVatwpisSuper> synchronizedList = Collections.synchronizedList(listadokvatprzetworzona);

                listadokvatprzetworzona = synchronizedList.parallelStream()
                    .filter(it -> {
                        EVatwpis1 p = (EVatwpis1) it;
                        lock.lock();
                        try {
                            return p.getDok() == null || !p.getDok().getRodzajedok().isTylkojpk();
                        } finally {
                            lock.unlock();
                        }
                    })
                    .collect(Collectors.toList());
            }
            //w uproszczonej tego nie ma
            //ale musi byc bo w fk sa faktury wdt wnt np vintis
            listadokvatprzetworzona.addAll(pobierzEVatIncydentalni(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            przejrzyjEVatwpis1Lista();
            dodajwierszeVATZD(wniosekVATZDEntity);
            
            stworzenieEwidencjiCzescWspolnaFK();
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            String l = locale.getLanguage();
            for (List p : listaewidencji.values()) {
                if (l.equals("de")) {
                    tlumaczewidencje(p);
                }
                ewidencjeFK.add(p);
            }
            pobierzmiesiacdlajpk = false;
            //PrimeFaces.current().ajax().update("@form");
            //PrimeFaces.current().ajax().update("formEwidencjeGuest");
            //PrimeFaces.current().ajax().update("form_dialog_ewidencjevat_sprawdzanie");
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
    }
    public void stworzenieEwidencjiZDokumentowFKD(Podatnik podatnik, WniosekVATZDEntity wniosekVATZDEntity) {
        try {
            listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            String vatokres = sprawdzjakiokresvat();
            if (pobierzmiesiacdlajpk) {
                vatokres = "miesięczne";
            }
            if (wpisView.getPodatnikObiekt().getMetodakasowa().equals("tak")) {
                listadokvatprzetworzona = przetworzRozliczenia(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
            } else {
                listadokvatprzetworzona.addAll(pobierzEVatRokFK(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), false));
                Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
//                listaprzesunietychKoszty = pobierzEVatRokFKNastepnyOkres(vatokres);
//                wyluskajzlisty(listaprzesunietychKoszty, "koszty");
//                sumaprzesunietych = sumujprzesuniete(listaprzesunietychKoszty);
//                listaprzesunietychBardziej = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
//                wyluskajzlisty(listaprzesunietychBardziej, "koszty");
//                sumaprzesunietychBardziej = sumujprzesuniete(listaprzesunietychBardziej);
//                listaprzesunietychPrzychody = pobierzEVatRokFKNastepnyOkres(vatokres);
//                wyluskajzlisty(listaprzesunietychPrzychody, "przychody");
//                sumaprzesunietychprzychody = sumujprzesuniete(listaprzesunietychPrzychody);
//                listaprzesunietychBardziejPrzychody = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
//                wyluskajzlisty(listaprzesunietychBardziejPrzychody, "przychody");
//                sumaprzesunietychBardziejPrzychody = sumujprzesuniete(listaprzesunietychBardziejPrzychody);
            }
            if (listadokvatprzetworzona != null) {
                for (Iterator<EVatwpisSuper> it = listadokvatprzetworzona.iterator(); it.hasNext();) {
                    EVatwpisFK p = (EVatwpisFK) it.next();
                    if (p.getDokfk() != null && p.getDokfk().getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
            }
            //w uproszczonej tego nie ma
            //ale musi byc bo w fk sa faktury wdt wnt np vintis
            //listadokvatprzetworzona.addAll(pobierzEVatIncydentalni(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
            przejrzyjEVatwpis1Lista();
            //dodajwierszeVATZD(wniosekVATZDEntity);
            stworzenieEwidencjiCzescWspolnaFK();
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
            String l = locale.getLanguage();
            for (List p : listaewidencji.values()) {
                if (l.equals("de")) {
                    tlumaczewidencje(p);
                }
                ewidencjeFK.add(p);
            }
            pobierzmiesiacdlajpk = false;
            //PrimeFaces.current().ajax().update("@form");
            //PrimeFaces.current().ajax().update("formEwidencjeGuest");
            //PrimeFaces.current().ajax().update("form_dialog_ewidencjevat_sprawdzanie");
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
    }
    
    public void stworzenieEwidencjiZDokumentowFKJPK(Podatnik podatnik, WniosekVATZDEntity wniosekVATZDEntity) {
        try {
            listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            String vatokres = sprawdzjakiokresvat();
            if (pobierzmiesiacdlajpk) {
                vatokres = "miesięczne";
            }
            if (wpisView.getPodatnikObiekt().getMetodakasowa().equals("tak")) {
                listadokvatprzetworzona = przetworzRozliczenia(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
            } else {
                listadokvatprzetworzona.addAll(pobierzEVatRokFK(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), true));
                Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
                listaprzesunietychKoszty = pobierzEVatRokFKNastepnyOkres(vatokres);
                wyluskajzlisty(listaprzesunietychKoszty, "koszty");
                sumaprzesunietych = sumujprzesuniete(listaprzesunietychKoszty);
                listaprzesunietychBardziej = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
                wyluskajzlisty(listaprzesunietychBardziej, "koszty");
                sumaprzesunietychBardziej = sumujprzesuniete(listaprzesunietychBardziej);
                listaprzesunietychPrzychody = pobierzEVatRokFKNastepnyOkres(vatokres);
                wyluskajzlisty(listaprzesunietychPrzychody, "przychody");
                sumaprzesunietychprzychody = sumujprzesuniete(listaprzesunietychPrzychody);
                listaprzesunietychBardziejPrzychody = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
                wyluskajzlisty(listaprzesunietychBardziejPrzychody, "przychody");
                sumaprzesunietychBardziejPrzychody = sumujprzesuniete(listaprzesunietychBardziejPrzychody);
            }
            List<EVatwpisSuper> wierszedodatkowe = Collections.synchronizedList(new ArrayList<>());
            for (EVatwpisSuper ewid : listadokvatprzetworzona) {
                if (ewid.getNazwaewidencji().getTypewidencji().equals("sz") && !ewid.isNieduplikuj()) {
                    wierszedodatkowe.add(beansVAT.EwidencjaVATSporzadzanie.duplikujEVatwpisSuper(ewid,ewidencjazakupu));
                }
            }
            listadokvatprzetworzona.addAll(wierszedodatkowe);
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
    }
    
    
    private List<EVatwpisSuper> przetworzRozliczenia(Podatnik podatnik, String vatokres, String rokWpisuSt, String miesiacWpisu) {
        List<EVatwpisSuper> zwrot = new ArrayList<>();
        //faktyczne platnosci
        List<Transakcja> transakcje = pobierzEVatRokFKTransakcjeKasowa(podatnik, vatokres, rokWpisuSt, miesiacWpisu);
        zwrot.addAll(stworzevatwpisRozl(transakcje));
        //konto 149-3
        List<StronaWiersza> wiersze = pobierzEVatRokFKSWKasowa(podatnik, vatokres, rokWpisuSt, miesiacWpisu);
        zwrot.addAll(stworzevatwpisMK(wiersze));
        //dokumenty miedzynarodowe == zasada ogolna
        zwrot.addAll(pobierzEVatRokFKMiedzynarKasowa(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
        error.E.s("");
        return zwrot;
    }
    
     private Collection<? extends EVatwpisSuper> stworzevatwpisRozl(List<Transakcja> lista) {
        List<EVatwpisFK> zwrot = new ArrayList<>();
        for (Transakcja p : lista) {
            try {
                if (p.getNowaTransakcja()!=null) {
                    if (p.getNowaTransakcja().getDokfk().getEwidencjaVAT()!=null&&p.getNowaTransakcja().getDokfk().getEwidencjaVAT().size()>0&&p.getNowaTransakcja().getDokfk().memorailowo0kasowo1()) {
                        List<EVatwpisFK> zwrotw = naniesPlatnoscRozl(p);
                        if (zwrotw != null) {
                            for (EVatwpisFK t : zwrotw) {
                                if (!zwrot.contains(t)) {
                                    zwrot.add(t);
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                Msg.msg("e","Błąd przay generowaniu ewidencji"+E.e(e));
                System.out.println("");
            }
        }
        for (EVatwpisFK r : zwrot) {
            rozliczPlatnoscRozl(r);
        }
        return zwrot;
    }

    private Collection<? extends EVatwpisSuper> stworzevatwpisMK(List<StronaWiersza> wiersze) {
        List<EVatwpisFK> zwrot = new ArrayList<>();
        Set<EVatwpisFK> wybrane = new HashSet<>();
        for (StronaWiersza p : wiersze) {
            if (p.getWiersz().geteVatwpisFK()!=null) {
                wybrane.add(p.getWiersz().geteVatwpisFK());
            } else {
                if (p.getWiersz().getDokfk().getEwidencjaVAT()!=null) {
                    wybrane.addAll(p.getWiersz().getDokfk().getEwidencjaVAT());
                }
            }
        }
        zwrot = new ArrayList<>(wybrane);
        return zwrot;
    }
     
    private List<EVatwpisFK> naniesPlatnoscRozl(Transakcja p) {
        Dokfk dok = p.getNowaTransakcja().getDokfk();
        List<EVatwpisFK> zwrot = dok.getEwidencjaVAT();
        for (EVatwpisFK s : zwrot) {
            s.setSumatransakcji(Z.z(s.getSumatransakcji()+p.getKwotatransakcji()));
        }
        return zwrot;
    }
    
    private void rozliczPlatnoscRozl(EVatwpisFK p) {
        Dokfk dok = p.getDokfk();
        double rozliczono =0.0;
        rozliczono = Z.z(p.getSumatransakcji());
        double zostalo = Z.z(dok.getWartoscdokumentu()-rozliczono);
        if (zostalo>=0.0) {
            double procent = Z.z6(p.getSumatransakcji()/dok.getWartoscdokumentu());
            p.setNetto(Z.z(p.getNetto()*procent));
            p.setVat(Z.z(p.getVat()*procent));
            p.setBrutto(Z.z(p.getBrutto()*procent));
        }
    }
    
    private void wyluskajzlisty(List<EVatwpisFK> listaprzesunietych, String przychodykoszty) {
        if (przychodykoszty.equals("koszty")) {
            for (Iterator<EVatwpisFK> it = listaprzesunietych.iterator(); it.hasNext();) {
                if (it.next().getEwidencja().getTypewidencji().equals("s")) {
                    it.remove();
                }
            }
        } else {
            for (Iterator<EVatwpisFK> it = listaprzesunietych.iterator(); it.hasNext();) {
                if (it.next().getEwidencja().getTypewidencji().equals("z")) {
                    it.remove();
                }
            }
        }
    }

    public void stworzenieEwidencjiCzescWspolnaDedra() {
        try {
            //rozdziela zapisy na poszczególne ewidencje
            rozdzielEVatwpisDedraNaEwidencje();
            rozdzielsumeEwidencjiNaPodlisty();
            /**
             * dodajemy wiersze w tab sumowanie
             */
            uzupelnijSumyEwidencji();
            /**
             * Dodaj sumy do ewidencji dla wydruku
             */
            dodajsumyDoEwidencjiEVatwpis1();
            obliczwynikokresu();
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
    }
    
    public void stworzenieEwidencjiCzescWspolna() {
        try {
            //rozdziela zapisy na poszczególne ewidencje
            rozdzielEVatwpis1NaEwidencje();
            rozdzielsumeEwidencjiNaPodlisty();
            rozdzielsumeEwidencjiNaPodlistyNiemcy();
            /**
             * dodajemy wiersze w tab sumowanie
             */
            uzupelnijSumyEwidencji();
             uzupelnijSumyEwidencjiNiemcy();
            /**
             * Dodaj sumy do ewidencji dla wydruku
             */
            dodajsumyDoEwidencjiEVatwpis1();
            obliczwynikokresu();
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
    }
    
    public void stworzenieEwidencjiCzescWspolnaFK() {
        try {
            //rozdziela zapisy na poszczególne ewidencje
            rozdzielEVatwpis1NaEwidencje();
            rozdzielsumeEwidencjiNaPodlisty();
            /**
             * dodajemy wiersze w tab sumowanie
             */
            uzupelnijSumyEwidencji();
            /**
             * Dodaj sumy do ewidencji dla wydruku
             */
            dodajsumyDoEwidencjiEVatwpisFK();
            obliczwynikokresu();
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
    }

    private void dodajwierszeVATZD(WniosekVATZDEntity wniosekVATZDEntity) {
        List<EVatwpisSuper> nowa = Collections.synchronizedList(new ArrayList<>());
        if (wniosekVATZDEntity != null) {
            for (Dokfk d : wniosekVATZDEntity.getZawierafk()) {
                if (d.getRodzajedok().getKategoriadokumentu()==2) {
                    List<EVatwpisFK> poz = d.getEwidencjaVAT();
                    for (EVatwpisFK e : poz) {
                        e.setNetto(-e.getNetto());
                        e.setVat(-e.getVat());
                        e.setBrutto(-e.getBrutto());
                        e.setMcEw(wpisView.getMiesiacWpisu());
                        e.setRokEw(wpisView.getRokWpisuSt());
                        nowa.add(e);
                    }
                }
            }
        }
        listadokvatprzetworzona.addAll(nowa);
    }
    
    private void obliczwynikokresu() {
        wynikOkresu = new BigDecimal(BigInteger.ZERO);
        wynikOkresuNiemcy = new BigDecimal(BigInteger.ZERO);
        for (EVatwpisSuma p : sumaewidencji.values()) {
            if (p.getEwidencja().isNiemcy()==false) {
                switch (p.getEwidencja().getTypewidencji()) {
                    case "s":
                        wynikOkresu = wynikOkresu.add(p.getVat());
                        break;
                    case "z":
                        wynikOkresu = wynikOkresu.subtract(p.getVat());
                        break;
                    case "sz":
                        wynikOkresu = wynikOkresu.add(p.getVat());
                        break;
                }
            } else {
                switch (p.getEwidencja().getTypewidencji()) {
                    case "s":
                        wynikOkresuNiemcy = wynikOkresuNiemcy.add(p.getVat());
                        break;
                    case "z":
                        wynikOkresuNiemcy = wynikOkresuNiemcy.subtract(p.getVat());
                        break;
                    case "sz":
                        wynikOkresuNiemcy = wynikOkresuNiemcy.add(p.getVat());
                        break;
                }
            }
        }
    }
    
    private void obliczwynikokresuRok() {
        wynikOkresuNiemcyRok = new BigDecimal(BigInteger.ZERO);
        for (EVatwpisSuma p : sumydowyswietleniasprzedazNiemcyRok) {
            switch (p.getEwidencja().getTypewidencji()) {
                case "s":
                    wynikOkresuNiemcyRok = wynikOkresuNiemcyRok.add(p.getVat());
                    break;
                case "z":
                    wynikOkresuNiemcyRok = wynikOkresuNiemcyRok.subtract(p.getVat());
                    break;
                case "sz":
                    wynikOkresuNiemcyRok = wynikOkresuNiemcyRok.add(p.getVat());
                    break;
            }
        }
        for (EVatwpisSuma p : sumydowyswietleniazakupyNiemcyRok) {
            switch (p.getEwidencja().getTypewidencji()) {
                case "s":
                    wynikOkresuNiemcyRok = wynikOkresuNiemcyRok.add(p.getVat());
                    break;
                case "z":
                    wynikOkresuNiemcyRok = wynikOkresuNiemcyRok.subtract(p.getVat());
                    break;
                case "sz":
                    wynikOkresuNiemcyRok = wynikOkresuNiemcyRok.add(p.getVat());
                    break;
            }
        }
    }

   

    private void rozdzielsumeEwidencjiNaPodlisty() {
        sumydowyswietleniasprzedaz = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniazakupy = Collections.synchronizedList(new ArrayList<>());
        for (EVatwpisSuma ew : sumaewidencji.values()) {
            String typeewidencji = ew.getEwidencja().getTypewidencji();
            if (ew.getEwidencja().isNiemcy()==false) {
                switch (typeewidencji) {
                    case "s":
                        sumydowyswietleniasprzedaz.add(ew);
                        break;
                    case "z":
                        sumydowyswietleniazakupy.add(ew);
                        break;
                    case "sz":
                        sumydowyswietleniasprzedaz.add(ew);
                        //wywalamy to bo pobieranie wpisow generuje duplikaty z ewidencja zakup
                        //sumydowyswietleniazakupy.add(ew);
                        break;
                }
            }
        }
    }
    
    private void rozdzielsumeEwidencjiNaPodlistyNiemcy() {
        sumydowyswietleniasprzedazNiemcy = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniazakupyNiemcy = Collections.synchronizedList(new ArrayList<>());
        for (EVatwpisSuma ew : sumaewidencji.values()) {
            String typeewidencji = ew.getEwidencja().getTypewidencji();
            if (ew.getEwidencja().isNiemcy()==true) {
                switch (typeewidencji) {
                    case "s":
                        sumydowyswietleniasprzedazNiemcy.add(ew);
                        break;
                    case "z":
                        sumydowyswietleniazakupyNiemcy.add(ew);
                        break;
                    case "sz":
                        sumydowyswietleniasprzedazNiemcy.add(ew);
                        //wywalamy to bo pobieranie wpisow generuje duplikaty z ewidencja zakup
                        //sumydowyswietleniazakupy.add(ew);
                        break;
                }
            }
        }
    }
    
    private void pobierzEVATwpis1zaOkres(Podatnik podatnik, int vatokres, String rok, String mc) {
        try {
            listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
            if (wpisView.getPodatnikObiekt().getMetodakasowa()!=null&&wpisView.getPodatnikObiekt().getMetodakasowa().equals("tak")) {
                if (vatokres==1) {
                    List<PlatnoscWaluta> lista = platnoscWalutaDAO.findByPodRokMc(podatnik, rok, mc);
                    listadokvatprzetworzona.addAll(stworzevatwpis(lista));
                    listadokvatprzetworzona.addAll(eVatwpis1DAO.zwrocBiezacegoKlientaRokMcKasowe(podatnik, rok, mc));
                } else {
                    List<PlatnoscWaluta> lista = platnoscWalutaDAO.findByPodRokKw(podatnik, rok, mc);
                    listadokvatprzetworzona.addAll(stworzevatwpis(lista));
                    listadokvatprzetworzona.addAll(eVatwpis1DAO.zwrocBiezacegoKlientaRokKWKasowe(podatnik, rok, mc));
                }
            } else {
                if (vatokres==1) {
                    listadokvatprzetworzona.addAll(eVatwpis1DAO.zwrocBiezacegoKlientaRokMc(podatnik, rok, mc));
                } else {
                    listadokvatprzetworzona.addAll(eVatwpis1DAO.zwrocBiezacegoKlientaRokKW(podatnik, rok, mc));
                }
            }
            Collections.sort(listadokvatprzetworzona, new EVatwpisSupercomparator());
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private Collection<? extends EVatwpisSuper> stworzevatwpis(List<PlatnoscWaluta> lista) {
        List<EVatwpis1> zwrot = new ArrayList<>();
        for (PlatnoscWaluta p : lista) {
            if (p.getDokument().getEwidencjaVAT1()!=null&&p.getDokument().getEwidencjaVAT1().size()>0) {
                List<EVatwpis1> zwrotw = przetworzPlatnosc(p);
                if (zwrotw != null) {
                    zwrot.addAll(zwrotw);
                }
            }
        }
        return zwrot;
    }
    
    private List<EVatwpis1> przetworzPlatnosc(PlatnoscWaluta p) {
        Dok dok = p.getDokument();
            double rozliczono =0.0;
        List<PlatnoscWaluta> platnosciwaluta = dok.getPlatnosciwaluta();
        for (PlatnoscWaluta r : platnosciwaluta) {
            if (!r.equals(p)) {
                rozliczono = Z.z(p.getKwota());
            }
        }
        double zostalo = Z.z(dok.getBruttoWaluta()-rozliczono);
        List<EVatwpis1> zwrot = dok.getEwidencjaVAT1();
        if (zostalo>=p.getKwota()) {
            double procent = Z.z4(p.getKwota()/dok.getBrutto());
            for (EVatwpis1 s : zwrot) {
                s.setNetto(Z.z(s.getNetto()*procent));
                s.setVat(Z.z(s.getVat()*procent));
                s.setBrutto(Z.z(s.getBrutto()*procent));
            }
        } else {
            zwrot = null;
        }
        return zwrot;
    }
    
    private void pobierzEVatwpisDedrazaOkres(Podatnik podatnik, int vatokres) {
        try {
            listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
            if (vatokres==1) {
                listadokvatprzetworzona.addAll(eVatwpisDedraDAO.findWierszePodatnikMc(wpisView));
            } else {
                listadokvatprzetworzona.addAll(eVatwpisDedraDAO.findWierszePodatnikMc(wpisView));
            }
            Collections.sort(listadokvatprzetworzona, new EVatwpisSupercomparator());
        } catch (Exception e) { 
            E.e(e); 
        }
    }

 private List<EVatwpisKJPK> pobierzEVatIncydentalni(Podatnik podatnik, String vatokres, String rok, String mc) {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": 
                    return eVatwpisIncydentalniDAO.findWierszePodatnikMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(mc)));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    return eVatwpisIncydentalniDAO.findPodatnikMc(podatnik, rok, miesiacewkwartale.get(0), miesiacewkwartale.get(2));
            }
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    private List<EVatwpisFK> pobierzEVatRokFK(Podatnik podatnik, String vatokres, String rok, String mc, boolean tylkodlajpk) {
        List<EVatwpisFK>  zwrot = null;
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    zwrot = new ArrayList<>();
                    //throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": 
                    zwrot = eVatwpisFKDAO.findPodatnikMc(podatnik, rok, mc, mc);
                    break;
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(mc)));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    zwrot = eVatwpisFKDAO.findPodatnikMc(podatnik, rok, miesiacewkwartale.get(0), miesiacewkwartale.get(2));
                    break;
            }
        } catch (Exception e) { E.e(e); 
            
        }
        if (zwrot!=null && tylkodlajpk) {
            for (Iterator<EVatwpisFK> it = zwrot.iterator(); it.hasNext();) {
                EVatwpisFK e = it.next();
                if (e.isTylkodlajpk()) {
                    it.remove();
                }
            }
        }
        return zwrot;
    }
    
    private List<StronaWiersza>  pobierzEVatRokFKSWKasowa(Podatnik podatnik, String vatokres, String rok, String mc) {
        List<StronaWiersza>  zwrot = new ArrayList<>();
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": 
                    zwrot = stronaWierszaDAO.findStronaByPodatnikRokMetodaKasowa(podatnik, rok, mc);
                    break;
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(mc)));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    zwrot = stronaWierszaDAO.findStronaByPodatnikRokKWMetodaKasowa(podatnik, rok, miesiacewkwartale.get(0), miesiacewkwartale.get(2));
                    break;
            }
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    private List<Transakcja>  pobierzEVatRokFKTransakcjeKasowa(Podatnik podatnik, String vatokres, String rok, String mc) {
        List<Transakcja>  zwrot = new ArrayList<>();
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": 
                    zwrot = transakcjaDAO.findPodatnikRokMcRozliczajacy(podatnik, rok, mc);
                    break;
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(mc)));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    zwrot = transakcjaDAO.findPodatnikRokKWRozliczajacy(podatnik, rok, miesiacewkwartale.get(0), miesiacewkwartale.get(2));
                    break;
            }
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
    }
    
    
    private List<EVatwpisFK> pobierzEVatRokFKMiedzynarKasowa(Podatnik podatnik, String vatokres, String rok, String mc) {
        List<EVatwpisFK> zwrot = new ArrayList<>();
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": 
                    zwrot = eVatwpisFKDAO.findPodatnikMc(podatnik, rok, mc, mc);
                    break;
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(mc)));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    zwrot = eVatwpisFKDAO.findPodatnikMc(podatnik, rok, miesiacewkwartale.get(0), miesiacewkwartale.get(2));
                    break;
            }
            for (Iterator<EVatwpisFK> it = zwrot.iterator(); it.hasNext();) {
                EVatwpisFK p = it.next();
                //dodano do kasowej zzvat bo to sa dokumenty z poprzedniego roku recznie wprowadzone przy nowych klientach
                 if (!p.getDokfk().getSeriadokfk().equals("SZVAT")&&!p.getDokfk().getSeriadokfk().equals("ZZVAT")&&!p.getDokfk().getSeriadokfk().equals("RK")&&p.getDokfk().memorailowo0kasowo1()) {
                    it.remove();
                }
            }
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
    private List<EVatwpisFK> pobierzEVatRokFKNastepnyOkres(String vatokres) {
        //pobiera nie dokumenty ale ewidencje vat, a wiec ewidencja z roku x moze sama miec rokx+1, a my szukamy wlasnie roku x+1
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne":
                    return eVatwpisFKDAO.findPodatnikMcInnyOkres(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wpisView.getMiesiacWpisu());
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    return eVatwpisFKDAO.findPodatnikMcInnyOkres(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), miesiacewkwartale.get(0), miesiacewkwartale.get(2));
            }
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    private List<EVatwpisFK> pobierzEVatRokFKNastepnyOkresBardziej(String vatokres) {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne":
                    return eVatwpisFKDAO.findPodatnikDalszeMce(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wpisView.getMiesiacWpisu());
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    return eVatwpisFKDAO.findPodatnikDalszeMce(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), miesiacewkwartale.get(0), miesiacewkwartale.get(2));
            }
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    private void uzupelnijSumyEwidencji() {
        EVatwpisSuma sumauptk = new EVatwpisSuma(new Evewidencja("suma upkt"), BigDecimal.ZERO, BigDecimal.ZERO, "", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        EVatwpisSuma sumasprzedaz = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (EVatwpisSuma ew : sumydowyswietleniasprzedaz) {
            sumasprzedaz.setNetto(sumasprzedaz.getNetto().add(ew.getNetto()));
            sumasprzedaz.setVat(sumasprzedaz.getVat().add(ew.getVat()));
            if (ew.getEwidencja().getNazwa().contains("usługi świad.")) {
                sumauptk.setNetto(sumauptk.getNetto().add(ew.getNetto()));
            }
        }
        sumydowyswietleniasprzedaz.add(sumasprzedaz);
        sumydowyswietleniasprzedaz.add(sumauptk);
        EVatwpisSuma sumazakup = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (EVatwpisSuma ew : sumydowyswietleniazakupy) {
            sumazakup.setNetto(sumazakup.getNetto().add(ew.getNetto()));
            sumazakup.setVat(sumazakup.getVat().add(ew.getVat()));
        }
        sumydowyswietleniazakupy.add(sumazakup);
    }
    
    private void uzupelnijSumyEwidencjiNiemcy() {
        EVatwpisSuma sumauptk = new EVatwpisSuma(new Evewidencja("suma upkt"), BigDecimal.ZERO, BigDecimal.ZERO, "", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        EVatwpisSuma sumasprzedaz = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (EVatwpisSuma ew : sumydowyswietleniasprzedazNiemcy) {
            sumasprzedaz.setNetto(sumasprzedaz.getNetto().add(ew.getNetto()));
            sumasprzedaz.setVat(sumasprzedaz.getVat().add(ew.getVat()));
            if (ew.getEwidencja().getNazwa().contains("usługi świad.")) {
                sumauptk.setNetto(sumauptk.getNetto().add(ew.getNetto()));
            }
        }
        sumydowyswietleniasprzedazNiemcy.add(sumasprzedaz);
        sumydowyswietleniasprzedazNiemcy.add(sumauptk);
        EVatwpisSuma sumazakup = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (EVatwpisSuma ew : sumydowyswietleniazakupyNiemcy) {
            sumazakup.setNetto(sumazakup.getNetto().add(ew.getNetto()));
            sumazakup.setVat(sumazakup.getVat().add(ew.getVat()));
        }
        sumydowyswietleniazakupyNiemcy.add(sumazakup);
    }

//    private void rozdzielEVatwpis1NaEwidencje() {
//        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMap();
//        Map<String, List<EVatwpisSuper>> listaewidencji = new ConcurrentHashMap<>();
//        Map<String, EVatwpisSuma> sumaewidencji = new ConcurrentHashMap<>();
//        listadokvatprzetworzona.stream().forEach(vatwiersz->{
//            if (vatwiersz.getNetto() != 0.0 || vatwiersz.getVat() != 0.0) {
//                //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
//                String nazwaewidencji = vatwiersz.getNazwaewidencji().getNazwa();
//                if (!listaewidencji.containsKey(nazwaewidencji)) {
//                    listaewidencji.put(nazwaewidencji, new ArrayList<EVatwpisSuper>());
//                    Evewidencja nowaEv = ewidencje.get(nazwaewidencji);
//                    sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, vatwiersz.getOpizw()));
//                }
//                listaewidencji.get(nazwaewidencji).add(vatwiersz);
//                EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
//                BigDecimal sumanetto = ew.getNetto().add(BigDecimal.valueOf(vatwiersz.getNetto()).setScale(2, RoundingMode.HALF_EVEN));
//                ew.setNetto(sumanetto);
//                BigDecimal sumavat = ew.getVat().add(BigDecimal.valueOf(vatwiersz.getVat()).setScale(2, RoundingMode.HALF_EVEN));
//                ew.setVat(sumavat);
//            }
//        });
//        this.listaewidencji = new HashMap<String, List<EVatwpisSuper>>(listaewidencji);
//        this.sumaewidencji = new HashMap<String, EVatwpisSuma>(sumaewidencji);
//    }
    
    private void rozdzielEVatwpis1NaEwidencje() {
        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMap();
        Map<String, List<EVatwpisSuper>> listaewidencji = new ConcurrentHashMap<>();
        Map<String, EVatwpisSuma> sumaewidencji = new ConcurrentHashMap<>();

        listadokvatprzetworzona.stream()
                .filter(vatwiersz -> vatwiersz.getNetto() != 0.0 || vatwiersz.getVat() != 0.0)
                .forEach(vatwiersz -> {
                    String nazwaewidencji = vatwiersz.getNazwaewidencji().getNazwa();

                    // Synchronizuj operacje na mapie sumaewidencji
                    synchronized (sumaewidencji) {
                        listaewidencji.computeIfAbsent(nazwaewidencji, k -> {
                            Evewidencja nowaEv = ewidencje.get(k);
                            sumaewidencji.put(k, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, vatwiersz.getOpizw(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
                            return Collections.synchronizedList(new ArrayList<>());
                        }).add(vatwiersz);

                        EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
                        ew.setNetto(ew.getNetto().add(BigDecimal.valueOf(vatwiersz.getNetto()).setScale(2, RoundingMode.HALF_EVEN)));
                        ew.setVat(ew.getVat().add(BigDecimal.valueOf(vatwiersz.getVat()).setScale(2, RoundingMode.HALF_EVEN)));
                    }
                });

        this.listaewidencji = new HashMap<>(listaewidencji);
        this.sumaewidencji = new HashMap<>(sumaewidencji);
    }
//    private void rozdzielEVatwpis1NaEwidencje() {
//        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMap();
//        Map<String, List<EVatwpisSuper>> listaewidencji = new ConcurrentHashMap<>();
//        Map<String, EVatwpisSuma> sumaewidencji = new ConcurrentHashMap<>();
//
//        listadokvatprzetworzona.parallelStream()
//                .filter(vatwiersz -> vatwiersz.getNetto() != 0.0 || vatwiersz.getVat() != 0.0)
//                .forEach(vatwiersz -> {
//                    String nazwaewidencji = vatwiersz.getNazwaewidencji().getNazwa();
//                    listaewidencji.computeIfAbsent(nazwaewidencji, k -> {
//                        Evewidencja nowaEv = ewidencje.get(k);
//                        sumaewidencji.put(k, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, vatwiersz.getOpizw()));
//                        return new ArrayList<>();
//                    }).add(vatwiersz);
//
//                    EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
//                    ew.setNetto(ew.getNetto().add(BigDecimal.valueOf(vatwiersz.getNetto()).setScale(2, RoundingMode.HALF_EVEN)));
//                    ew.setVat(ew.getVat().add(BigDecimal.valueOf(vatwiersz.getVat()).setScale(2, RoundingMode.HALF_EVEN)));
//                });
//
//        this.listaewidencji = new HashMap<>(listaewidencji);
//        this.sumaewidencji = new HashMap<>(sumaewidencji);
//    }
    
    private void rozdzielEVatwpisDedraNaEwidencje() {
        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMap();
        for (EVatwpisSuper wierszogolny : listadokvatprzetworzona) {
            if (wierszogolny.getNetto() != 0.0 || wierszogolny.getVat() != 0.0) {
                //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
                String nazwaewidencji = wierszogolny.getEwidencja().getNazwa();
                if (!listaewidencji.containsKey(nazwaewidencji)) {
                    listaewidencji.put(nazwaewidencji, new ArrayList<EVatwpisSuper>());
                    Evewidencja nowaEv = ewidencje.get(nazwaewidencji);
                    sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, wierszogolny.getOpizw(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
                }
                listaewidencji.get(nazwaewidencji).add(wierszogolny);
                EVatwpisSuma ew = sumaewidencji.get(nazwaewidencji);
                BigDecimal sumanetto = ew.getNetto().add(BigDecimal.valueOf(wierszogolny.getNetto()).setScale(2, RoundingMode.HALF_EVEN));
                ew.setNetto(sumanetto);
                BigDecimal sumavat = ew.getVat().add(BigDecimal.valueOf(wierszogolny.getVat()).setScale(2, RoundingMode.HALF_EVEN));
                ew.setVat(sumavat);
            }
        }
    }

//     private  Map<String, Evewidencja> przejrzyjEVatwpis1Lista() {
//        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMapByPole();
//        List<EVatwpisSuper> wierszedodatkowe = Collections.synchronizedList(new ArrayList<>());
//        List<EVatwpisSuper> dousuniecia = new ArrayList<>();
//        for (EVatwpisSuper ewid : listadokvatprzetworzona) {
//            if (ewid.getNazwaewidencji().getTypewidencji().equals("sz") && !ewid.isNieduplikuj()) {
//                wierszedodatkowe.add(beansVAT.EwidencjaVATSporzadzanie.duplikujEVatwpisSuper(ewid,ewidencjazakupu));
//            }
////            if (dlaewidencja && ewid.getDokfk().getRodzajedok() != null &&  ewid.getDokfk().getRodzajedok().isTylkovat()) {
////                dousuniecia.add(ewid);
////            }
//// to nie ma prawa dzialac funkcja ta jest w miejscyu beansvat vatdeklaracja przyporzadkujPozycjeSzczegoloweNowe            
////            if (ewid.getNazwaewidencji().getNazwapola() != null && ewid.getNazwaewidencji().getNazwapola().getMacierzysty() != null){
////                wierszedodatkowe.add(duplikujsubwiersze(ewid, ewidencje));
////            }
//        }
//        listadokvatprzetworzona.removeAll(dousuniecia);
//        listadokvatprzetworzona.addAll(wierszedodatkowe);
//        return ewidencje;
//    }
//    
    
    
    //era chatgtp 03.08.2024. gdzie te czasy gdy googlalo sie jakies forma po angielsku
    private Map<String, Evewidencja> przejrzyjEVatwpis1Lista() {
      Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMapByPole();

      List<EVatwpisSuper> wierszedodatkowe = listadokvatprzetworzona.parallelStream()
          .filter(ewid -> ewid.getNazwaewidencji().getTypewidencji().equals("sz") && !ewid.isNieduplikuj())
          .map(ewid -> beansVAT.EwidencjaVATSporzadzanie.duplikujEVatwpisSuper(ewid, ewidencjazakupu))
          .collect(Collectors.toList());

//      listadokvatprzetworzona = listadokvatprzetworzona.parallelStream()
//          .filter(ewid -> !(ewid.getNazwaewidencji().getTypewidencji().equals("sz") && !ewid.isNieduplikuj()))
//          .collect(Collectors.toList());

      listadokvatprzetworzona.addAll(wierszedodatkowe);
      return ewidencje;
  }
    
    
    private double sumujprzesuniete(List<EVatwpisFK> l ) {
        double suma = 0.0;
        if (l.size() > 0) {
            for (EVatwpisFK r : l) {
                suma += r.getVat();
            }
        }
        return Z.z(suma);
    }
    
    

    private void dodajsumyDoEwidencjiEVatwpis1() {
        Set<String> klucze = sumaewidencji.keySet();
        for (String p : klucze) {
            EVatwpis1 wiersz = new EVatwpis1();
            wiersz.setId(9999);
            wiersz.setKontr(null);
            wiersz.setOpis("podsumowanie");
            wiersz.setNazwaewidencji(sumaewidencji.get(p).getEwidencja());
            wiersz.setNetto(sumaewidencji.get(p).getNetto().doubleValue());
            wiersz.setVat(sumaewidencji.get(p).getVat().doubleValue());
            listaewidencji.get(p).add(wiersz);
        }
    }
    
    private void dodajsumyDoEwidencjiEVatwpisFK() {
        Set<String> klucze = sumaewidencji.keySet();
        for (String p : klucze) {
            EVatwpisFK wiersz = new EVatwpisFK();
            wiersz.setId(9999);
            wiersz.setKontr(null);
            wiersz.setOpis("podsumowanie");
            wiersz.setNazwaewidencji(sumaewidencji.get(p).getEwidencja());
            wiersz.setNetto(sumaewidencji.get(p).getNetto().doubleValue());
            wiersz.setVat(sumaewidencji.get(p).getVat().doubleValue());
            listaewidencji.get(p).add(wiersz);
        }
    }
    
    private EVatwpisSuper dodajsumyDoEwidencji(double netto, double vat, Class c) {
        EVatwpisSuper wiersz = c.getCanonicalName().equals("entityfk.EVatwpisFK") ? new EVatwpisFK(): new EVatwpis1();
        wiersz.setId(9999);
        wiersz.setKontr(null);
        wiersz.setOpis("podsumowanie");
        wiersz.setNazwaewidencji(null);
        wiersz.setNetto(netto);
        wiersz.setVat(vat);
        return wiersz;
    }

    public String przekierowanieEwidencji() {
        return "/ksiegowa/ksiegowaVATzest.xhtml?faces-redirect=true";
    }

    public String przekierowanieEwidencjiGuest() {
        return "/guest/ksiegowaVATzest.xhtml?faces-redirect=true";
    }

    public String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }

    

    public void sumujwybrane() {
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
        for (EVatwpisSuma p : goscwybralsuma) {
            suma1 += p.getNetto().doubleValue();
            suma2 += p.getVat().doubleValue();
        }
        suma3 = suma1 + suma2;
        Msg.msg("i", "Sumuję ewidencje vat");
    }

    public void sumujwybrane1() {
        suma1 = 0.0;
        suma2 = 0.0;
        suma3 = 0.0;
        for (EVatwpisSuper p : goscwybral) {
            suma1 += p.getNetto();
            suma2 += p.getVat();
        }
        suma3 = suma1 + suma2;
    }

    public void odsumujwybrane1(UnselectEvent event) {
        EVatwpisSuper p = (EVatwpisSuper) event.getObject();
        suma1 -= p.getNetto();
        suma2 -= p.getVat();
        suma3 -= suma1 + suma2;
    }

    public void vatewidencja() {
        try {
            MailOther.vatewidencja(wpisView, nazwaewidencjiMail, sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 

        }
    }

    public void ustawNazwaewidencji(String nazwa) {
        String nowanazwa;
        if (nazwa.contains("sprzedaż")) {
            nowanazwa = nazwa.substring(0, nazwa.length() - 1);
        } else {
            nowanazwa = nazwa;
        }
        nazwaewidencjiMail = nowanazwa;
    }

    public void drukujPdfSuma() {
        try {
            PdfVATsuma.drukuj(sumaewidencji, wpisView, false);
        } catch (Exception e) { E.e(e); 

        }
    }
    //kom
    public void pobierzEwidSumabazaNiemcyRoczna() {
        List<EVatwpisSuma> lista = eVatwpisSumaDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        rozdzielsumeEwidencjiNaPodlistyNiemcyRok(lista);
        sumujIZastapListy(sumydowyswietleniasprzedazNiemcyRok);
        sumujIZastapListy(sumydowyswietleniazakupyNiemcyRok);
        obliczwynikokresuRok();
    }
    
    private void sumujIZastapListy(List<EVatwpisSuma> lista) {
        if (lista == null || lista.isEmpty()) {
            return;
        }

        BigDecimal sumaNetto = BigDecimal.ZERO;
        BigDecimal sumaVat = BigDecimal.ZERO;
        Evewidencja ewidencja = null;
        Podatnik podatnik = null;
        String rok = null;
        String mc = null;

        for (EVatwpisSuma ew : lista) {
            sumaNetto = sumaNetto.add(ew.getNetto());
            sumaVat = sumaVat.add(ew.getVat());
            if (ewidencja == null) {
                ewidencja = ew.getEwidencja();
            }
            if (podatnik == null) {
                podatnik = ew.getPodatnik();
            }
            if (rok == null) {
                rok = ew.getRok();
            }
            if (mc == null) {
                mc = ew.getMc();
            }
        }

        lista.clear();

        EVatwpisSuma suma = new EVatwpisSuma(ewidencja, sumaNetto, sumaVat, "suma", podatnik, rok, mc);
        lista.add(suma);
    }
    
    private void rozdzielsumeEwidencjiNaPodlistyNiemcyRok(List<EVatwpisSuma> lista) {
        sumydowyswietleniasprzedazNiemcyRok = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniazakupyNiemcyRok = Collections.synchronizedList(new ArrayList<>());
        for (EVatwpisSuma ew : sumaewidencji.values()) {
            String typeewidencji = ew.getEwidencja().getTypewidencji();
            if (ew.getEwidencja().isNiemcy()==true) {
                switch (typeewidencji) {
                    case "s":
                        sumydowyswietleniasprzedazNiemcyRok.add(ew);
                        break;
                    case "z":
                        sumydowyswietleniazakupyNiemcyRok.add(ew);
                        break;
                    case "sz":
                        sumydowyswietleniasprzedazNiemcyRok.add(ew);
                        break;
                }
            }
        }
    }
    
    public void zachowajEwidSumabazaNiemcy() {
        if (sumydowyswietleniasprzedazNiemcy.isEmpty()==false) {
            eVatwpisSumaDAO.usunPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            try {
                sumydowyswietleniasprzedazNiemcy.removeIf(it->it.getEwidencja().getNazwa().equals("podsumowanie"));
                sumydowyswietleniasprzedazNiemcy.removeIf(it->it.getEwidencja().getNazwa().equals("suma upkt"));
                eVatwpisSumaDAO.createEditList(sumydowyswietleniasprzedazNiemcy);
            } catch (Exception ex){
                E.m(ex);
            }
        }
        if (sumydowyswietleniazakupyNiemcy.isEmpty()==false) {
            try {
                sumydowyswietleniazakupyNiemcy.removeIf(it->it.getEwidencja().getNazwa().equals("podsumowanie"));
                eVatwpisSumaDAO.createEditList(sumydowyswietleniazakupyNiemcy);
            } catch (Exception ex){
                E.m(ex);
            }
        }
        Msg.msg("Zachowano sumy ewidencji do deklaracji");
    }
    
     public void drukujPdfSumaNiemcy() {
        try {
            PdfVATsuma.drukuj(sumaewidencji, wpisView, true);
        } catch (Exception e) { E.e(e); 

        }
    }

    public void drukujPdfEwidencje(String nazwaewidencji) {
        try {
            if (zachowanewybranewierszeewidencji != null && zachowanewybranewierszeewidencji.size() > 0) {
                if (zachowanewybranewierszeewidencji.size() > 0) {
                    podsumujwybranewierszeprzedwydrukiem(zachowanewybranewierszeewidencji);
                }
                PdfVAT.drukujewidencjeWybrane(wpisView, listaewidencji, nazwaewidencji, false, zachowanewybranewierszeewidencji);
            } else {
                PdfVAT.drukujewidencje(wpisView, listaewidencji, nazwaewidencji, false);
            }
        } catch (Exception e) { E.e(e); 

        }
    }
    public void drukujPdfEwidencjeWartosc(String nazwaewidencji) {
        try {
            if (zachowanewybranewierszeewidencji != null && zachowanewybranewierszeewidencji.size() > 0) {
                if (zachowanewybranewierszeewidencji.size() > 1) {
                    podsumujwybranewierszeprzedwydrukiem(zachowanewybranewierszeewidencji);
                }
                PdfVAT.drukujewidencjeWybrane(wpisView, listaewidencji, nazwaewidencji, true, zachowanewybranewierszeewidencji);
            } else {
                PdfVAT.drukujewidencje(wpisView, listaewidencji, nazwaewidencji, true);
            }
        } catch (Exception e) { E.e(e); 

        }
    }
    
    private void podsumujwybranewierszeprzedwydrukiem(List<EVatwpisSuper> zachowanewybranewierszeewidencji) {
        Class c = zachowanewybranewierszeewidencji.get(0).getClass();
        double netto = 0.0;
        double vat = 0.0;
        for (Iterator<EVatwpisSuper> it = zachowanewybranewierszeewidencji.iterator(); it.hasNext();) {
            EVatwpisSuper p = it.next();
            if (p.getEwidencja()!= null) {
                netto += p.getNetto();
                vat += p.getVat();
            } else {
                it.remove();
            }
        }
        zachowanewybranewierszeewidencji.add(dodajsumyDoEwidencji(Z.z(netto), Z.z(vat), c));
    }
    
    private void ewidencjazamc (Podatnik podatnik, String rok, String mc) {
        listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
        ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
        zerujListy();
        String vatokres = sprawdzjakiokresvat();
        listadokvatprzetworzona.addAll(pobierzEVatRokFK(podatnik, vatokres, rok, mc, false));
        Collections.sort(listadokvatprzetworzona,new EVatwpisFKcomparator());
        listaprzesunietychKoszty = pobierzEVatRokFKNastepnyOkres(vatokres);
        wyluskajzlisty(listaprzesunietychKoszty, "koszty");
        sumaprzesunietych = sumujprzesuniete(listaprzesunietychKoszty);
        listaprzesunietychBardziej = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
        wyluskajzlisty(listaprzesunietychBardziej, "koszty");
        sumaprzesunietychBardziej = sumujprzesuniete(listaprzesunietychBardziej);
        listaprzesunietychPrzychody = pobierzEVatRokFKNastepnyOkres(vatokres);
        wyluskajzlisty(listaprzesunietychPrzychody, "przychody");
        sumaprzesunietychprzychody = sumujprzesuniete(listaprzesunietychPrzychody);
        listaprzesunietychBardziejPrzychody = pobierzEVatRokFKNastepnyOkresBardziej(vatokres);
        wyluskajzlisty(listaprzesunietychBardziejPrzychody, "przychody");
        sumaprzesunietychBardziejPrzychody = sumujprzesuniete(listaprzesunietychBardziejPrzychody);
        przejrzyjEVatwpis1Lista();
        List<WniosekVATZDEntity> wniosekVATZDEntity = wniosekVATZDEntityDAO.findByPodatnikRokMcFK(podatnik, rok, mc);
        if (wniosekVATZDEntity!=null && wniosekVATZDEntity.size()>0) {
            dodajwierszeVATZD(wniosekVATZDEntity.get(0));
        }
        stworzenieEwidencjiCzescWspolnaFK();
        
    }
    
     public void ewidencjazamcPkpir(Podatnik podatnik, String rok, String mc) {
        try {
            ewidencjazakupu = evewidencjaDAO.znajdzponazwie("zakup");
            zerujListy();
            int vatokres = wpisView.getVatokres();
            pobierzEVATwpis1zaOkres(podatnik, vatokres, rok, mc);
            przejrzyjEVatwpis1Lista();
            stworzenieEwidencjiCzescWspolna();
        } catch (Exception e) { 
            Msg.dPe();
            E.e(e);
        }
        //drukuj ewidencje
    }
    
    public void drukujPdfWszystkieRok() {
        try {
            String vatokres = sprawdzjakiokresvat();
            List<String> mnce = null;
            if (vatokres.equals("miesięczne")) {
                mnce = Mce.getMceListS();
            } else {
                mnce = Mce.getMceListKW();
            }
            for (String mc : mnce) {
                if (wpisView.isKsiegirachunkowe()) {
                    ewidencjazamc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mc);
                } else {
                    ewidencjazamcPkpir(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mc);
                }
                if (listaewidencji!=null && listaewidencji.size() > 0) {
                    String nazwa = "vatcalyrok"+wpisView.getPodatnikObiekt().getNip()+mc+wpisView.getRokWpisuSt();
                    String plik = nazwa+ ".pdf";
                    PdfVAT.drukujewidencjenajednejkartce(plik, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mc, listaewidencji, false);
                    String co = "pokazwydruk('"+nazwa+"')";
                    PrimeFaces.current().executeScript(co);
                } else {
                    break;
                }
            }
        } catch (Exception e) { 
            E.e(e); 

        }
    }
    
    
    
    public void drukujPdfWszystkie() {
        try {
            PdfVAT.drukujewidencjenajednejkartce(null, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), listaewidencji, false);
        } catch (Exception e) { 
            E.e(e); 

        }
    }
    
     public void drukujPdfWszystkieWartosc() {
        try {
            PdfVAT.drukujewidencjenajednejkartce(null, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), listaewidencji, true);
        } catch (Exception e) { E.e(e); 

        }
    }
     
//     public void wybierzewidencje() {
//         wybranaewidencja = listaewidencji.get(ewidencjadosprawdzania);
//     }
     @Inject
     private EVatwpis1DAO eVatwpis1DAO;
             
     public void edycjaewidencji() {
         List<EVatwpis1> lista = eVatwpis1DAO.findAll();
         int i = 1;
         for (EVatwpis1 p  : lista) {
            if (p.getDok() != null) {
                if (p.getDok().getVatR() != null) {
                    p.setRokEw(p.getDok().getVatR());
                }
                if (p.getDok().getVatM() != null) {
                    p.setMcEw(p.getDok().getVatM());
                }
            }
             
         }
         eVatwpis1DAO.editList(lista);
     }

     
     public int sortujzaksiegowane(Object obP, Object obW) {
        int ret = 0;
        String dok1 = ((String) obP).split("/")[0];
        String dok2 = ((String) obW).split("/")[0];
        ret = dok1.compareTo(dok2);
        if (ret == 0) {
            ret = porownajdalej((String) obP, (String) obW);
        }
        return ret;
    }

    private int porownajdalej(String obP, String obW) {
        int ret = 0;
        Integer dok1 = Integer.parseInt(obP.split("/")[1]);
        Integer dok2 = Integer.parseInt(obW.split("/")[1]);
        if (dok1 < dok2) {
            ret = -1;
        } else if (dok1 > dok2) {
            ret = 1;
        }
        return ret;
    }
    
    
    
    
    
    public String getNazwaewidencjiMail() {
        return nazwaewidencjiMail;
    }

    public void setNazwaewidencjiMail(String nazwaewidencjiMail) {
        this.nazwaewidencjiMail = nazwaewidencjiMail;
    }

    

    public List<EVatwpisSuper> getFilteredwierszeewidencji() {
        return filteredwierszeewidencji;
    }

    public void setFilteredwierszeewidencji(List<EVatwpisSuper> filteredwierszeewidencji) {
        this.filteredwierszeewidencji = filteredwierszeewidencji;
    }


    public Dok getSelected() {
        return selected;
    }

    public void setSelected(Dok selected) {
        this.selected = selected;
    }

    public List<EVatwpisSuper> getListadokvatprzetworzona() {
        return listadokvatprzetworzona;
    }

    public void setListadokvatprzetworzona(List<EVatwpisSuper> listadokvatprzetworzona) {
        this.listadokvatprzetworzona = listadokvatprzetworzona;
    }

    public HashMap<String, List<EVatwpisSuper>> getListaewidencji() {
        return listaewidencji;
    }

    public void setListaewidencji(HashMap<String, List<EVatwpisSuper>> listaewidencji) {
        this.listaewidencji = listaewidencji;
    }


    public TabView getAkordeon() {
        return akordeon;
    }

    public List<EVatwpisSuma> getSumydowyswietleniasprzedazNiemcy() {
        return sumydowyswietleniasprzedazNiemcy;
    }

    public void setSumydowyswietleniasprzedazNiemcy(List<EVatwpisSuma> sumydowyswietleniasprzedazNiemcy) {
        this.sumydowyswietleniasprzedazNiemcy = sumydowyswietleniasprzedazNiemcy;
    }

    public List<EVatwpisSuma> getSumydowyswietleniazakupyNiemcy() {
        return sumydowyswietleniazakupyNiemcy;
    }

    public void setSumydowyswietleniazakupyNiemcy(List<EVatwpisSuma> sumydowyswietleniazakupyNiemcy) {
        this.sumydowyswietleniazakupyNiemcy = sumydowyswietleniazakupyNiemcy;
    }

    public void setAkordeon(TabView akordeon) {
        this.akordeon = akordeon;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public double getSumaprzesunietychBardziej() {
        return sumaprzesunietychBardziej;
    }

    public void setSumaprzesunietychBardziej(double sumaprzesunietychBardziej) {
        this.sumaprzesunietychBardziej = sumaprzesunietychBardziej;
    }

    public List<EVatwpisSuper> getGoscwybral() {
        return goscwybral;
    }

    public void setGoscwybral(List<EVatwpisSuper> goscwybral) {
        this.goscwybral = goscwybral;
    }

    public HashMap<String, EVatwpisSuma> getSumaewidencji() {
        return sumaewidencji;
    }

    public void setSumaewidencji(HashMap<String, EVatwpisSuma> sumaewidencji) {
        this.sumaewidencji = sumaewidencji;
    }

    public List<String> getListanowa() {
        return listanowa;
    }

    public void setListanowa(List<String> listanowa) {
        this.listanowa = listanowa;
    }

    public List<EVatwpisSuma> getGoscwybralsuma() {
        return goscwybralsuma;
    }

    public void setGoscwybralsuma(List<EVatwpisSuma> goscwybralsuma) {
        this.goscwybralsuma = goscwybralsuma;
    }

    public List<EVatwpisSuma> getSumydowyswietleniasprzedaz() {
        return sumydowyswietleniasprzedaz;
    }

    public void setSumydowyswietleniasprzedaz(List<EVatwpisSuma> sumydowyswietleniasprzedaz) {
        this.sumydowyswietleniasprzedaz = sumydowyswietleniasprzedaz;
    }

    public Double getSuma1() {
        return suma1;
    }

    public void setSuma1(Double suma1) {
        this.suma1 = suma1;
    }

    public Double getSuma2() {
        return suma2;
    }

    public void setSuma2(Double suma2) {
        this.suma2 = suma2;
    }

    public Double getSuma3() {
        return suma3;
    }

    public void setSuma3(Double suma3) {
        this.suma3 = suma3;
    }

    public List<EVatwpisSuma> getSumydowyswietleniazakupy() {
        return sumydowyswietleniazakupy;
    }

    public void setSumydowyswietleniazakupy(List<EVatwpisSuma> sumydowyswietleniazakupy) {
        this.sumydowyswietleniazakupy = sumydowyswietleniazakupy;
    }

    public List<EVatwpisFK> getListaprzesunietychKoszty() {
        return listaprzesunietychKoszty;
    }

    public void setListaprzesunietychKoszty(List<EVatwpisFK> listaprzesunietychKoszty) {
        this.listaprzesunietychKoszty = listaprzesunietychKoszty;
    }

    public double getSumaprzesunietych() {
        return sumaprzesunietych;
    }

    public void setSumaprzesunietych(double sumaprzesunietych) {
        this.sumaprzesunietych = sumaprzesunietych;
    }

    public BigDecimal getWynikOkresu() {
        return wynikOkresu;
    }

    public void setWynikOkresu(BigDecimal wynikOkresu) {
        this.wynikOkresu = wynikOkresu;
    }

    public List<EVatwpisFK> getListaprzesunietychBardziej() {
        return listaprzesunietychBardziej;
    }

    public void setListaprzesunietychBardziej(List<EVatwpisFK> listaprzesunietychBardziej) {
        this.listaprzesunietychBardziej = listaprzesunietychBardziej;
    }

    public List<EVatwpisSuper> getFiltered() {
        return filtered;
    }

    public void setFiltered(List<EVatwpisSuper> filtered) {
        this.filtered = filtered;
    }

    public List<List<EVatwpis1>> getEwidencje() {
        return ewidencje;
    }

    public void setEwidencje(List<List<EVatwpis1>> ewidencje) {
        this.ewidencje = ewidencje;
    }

    public List<List<EVatwpisFK>> getEwidencjeFK() {
        return ewidencjeFK;
    }

    public void setEwidencjeFK(List<List<EVatwpisFK>> ewidencjeFK) {
        this.ewidencjeFK = ewidencjeFK;
    }

    

    public List<EVatwpisSuper> getZachowanewybranewierszeewidencji() {
        return zachowanewybranewierszeewidencji;
    }

    public void setZachowanewybranewierszeewidencji(List<EVatwpisSuper> zachowanewybranewierszeewidencji) {
        this.zachowanewybranewierszeewidencji = zachowanewybranewierszeewidencji;
    }

    public List<EVatwpisFK> getListaprzesunietychPrzychody() {
        return listaprzesunietychPrzychody;
    }

    public void setListaprzesunietychPrzychody(List<EVatwpisFK> listaprzesunietychPrzychody) {
        this.listaprzesunietychPrzychody = listaprzesunietychPrzychody;
    }

    public List<EVatwpisFK> getListaprzesunietychBardziejPrzychody() {
        return listaprzesunietychBardziejPrzychody;
    }

    public void setListaprzesunietychBardziejPrzychody(List<EVatwpisFK> listaprzesunietychBardziejPrzychody) {
        this.listaprzesunietychBardziejPrzychody = listaprzesunietychBardziejPrzychody;
    }

    public double getSumaprzesunietychprzychody() {
        return sumaprzesunietychprzychody;
    }

    public void setSumaprzesunietychprzychody(double sumaprzesunietychprzychody) {
        this.sumaprzesunietychprzychody = sumaprzesunietychprzychody;
    }

    public double getSumaprzesunietychBardziejPrzychody() {
        return sumaprzesunietychBardziejPrzychody;
    }

    public List<String> getNazwyewidencji() {
        return nazwyewidencji;
    }

    public void setNazwyewidencji(List<String> nazwyewidencji) {
        this.nazwyewidencji = nazwyewidencji;
    }
//
//    public String getEwidencjadosprawdzania() {
//        return ewidencjadosprawdzania;
//    }
//
//    public void setEwidencjadosprawdzania(String ewidencjadosprawdzania) {
//        this.ewidencjadosprawdzania = ewidencjadosprawdzania;
//    }

    public void setSumaprzesunietychBardziejPrzychody(double sumaprzesunietychBardziejPrzychody) {
        this.sumaprzesunietychBardziejPrzychody = sumaprzesunietychBardziejPrzychody;
    }

    public List<EVatwpisSuper> getWybranewierszeewidencji() {
        return wybranewierszeewidencji;
    }

    public List<EVatwpisSuper> getWybranaewidencja() {
        return wybranaewidencja;
    }

    public void setWybranaewidencja(List<EVatwpisSuper> wybranaewidencja) {
        this.wybranaewidencja = wybranaewidencja;
    }

   
    
    public void setWybranewierszeewidencji(List<EVatwpisSuper> wybranewierszeewidencji) {
        this.wybranewierszeewidencji = wybranewierszeewidencji;
        if (wybranewierszeewidencji != null && wybranewierszeewidencji.size() > 0) {
            this.zachowanewybranewierszeewidencji = serialclone.SerialClone.clone(wybranewierszeewidencji);
        }
    }
    
    

//</editor-fold>
    public static void main(String[] args) {
        String wiersz = "35.23 zł";
        String prices = wiersz.replaceAll("\\s", "");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
        }
    }

    private void tlumaczewidencje(List<EVatwpisSuper> l) {
        
    }

    public boolean isPobierzmiesiacdlajpk() {
        return pobierzmiesiacdlajpk;
    }

    public void setPobierzmiesiacdlajpk(boolean pobierzmiesiacdlajpk) {
        this.pobierzmiesiacdlajpk = pobierzmiesiacdlajpk;
    }

    public BigDecimal getWynikOkresuNiemcy() {
        return wynikOkresuNiemcy;
    }

    public void setWynikOkresuNiemcy(BigDecimal wynikOkresuNiemcy) {
        this.wynikOkresuNiemcy = wynikOkresuNiemcy;
    }

    public List<EVatwpisSuma> getSumydowyswietleniasprzedazNiemcyRok() {
        return sumydowyswietleniasprzedazNiemcyRok;
    }

    public void setSumydowyswietleniasprzedazNiemcyRok(List<EVatwpisSuma> sumydowyswietleniasprzedazNiemcyRok) {
        this.sumydowyswietleniasprzedazNiemcyRok = sumydowyswietleniasprzedazNiemcyRok;
    }

    public List<EVatwpisSuma> getSumydowyswietleniazakupyNiemcyRok() {
        return sumydowyswietleniazakupyNiemcyRok;
    }

    public void setSumydowyswietleniazakupyNiemcyRok(List<EVatwpisSuma> sumydowyswietleniazakupyNiemcyRok) {
        this.sumydowyswietleniazakupyNiemcyRok = sumydowyswietleniazakupyNiemcyRok;
    }

    public BigDecimal getWynikOkresuNiemcyRok() {
        return wynikOkresuNiemcyRok;
    }

    public void setWynikOkresuNiemcyRok(BigDecimal wynikOkresuNiemcyRok) {
        this.wynikOkresuNiemcyRok = wynikOkresuNiemcyRok;
    }

    

    

    

   

    

    
}
