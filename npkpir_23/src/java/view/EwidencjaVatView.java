/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.EVatwpisFKcomparator;
import comparator.EVatwpisSupercomparator;
import dao.EVatwpis1DAO;
import dao.EvewidencjaDAO;
import dao.RodzajedokDAO;
import dao.SMTPSettingsDAO;
import dao.WniosekVATZDEntityDAO;
import daoFK.EVatwpisDedraDAO;
import daoFK.EVatwpisFKDAO;
import data.Data;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import entity.Dok;
import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.Podatnik;
import entity.WniosekVATZDEntity;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import mail.MailOther;
import msg.Msg; import org.primefaces.PrimeFaces;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.UnselectEvent;
import pdf.PdfVAT;
import pdf.PdfVATsuma;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
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
    private BigDecimal wynikOkresu;
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
    //elementy niezbedne do generowania ewidencji vat
    private TabView akordeon;
    @Inject
    private EVatwpisFKDAO eVatwpisFKDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private List<EVatwpisSuper> goscwybral;
    private List<EVatwpisSuper> filtered;
    private List<String> listanowa;
    private Double suma1;
    private Double suma2;
    private Double suma3;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
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

    public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        init();
        stworzenieEwidencjiZDokumentow(wpisView.getPodatnikObiekt());
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(),"form:messages");
    }
    
    private void aktualizuj(){
         wpisView.naniesDaneDoWpis();
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
                            Msg.msg("Oznaczono wiersz");
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
    
    
    
    

    public void stworzenieEwidencjiZDokumentow(Podatnik podatnik) {
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
            stworzenieEwidencjiCzescWspolna();
            for (String k : listaewidencji.keySet()) {
                nazwyewidencji.add(k);
            }
            for (List p : listaewidencji.values()) {
                ewidencje.add(p);
            }
            pobierzmiesiacdlajpk = false;
            //PrimeFaces.current().ajax().update("formVatZestKsiegowa");
            //Msg.msg("Sporządzono ewidencje");
        } catch (Exception e) { 
            Msg.dPe();
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
            Msg.msg("Sporządzono ewidencje");
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
            listadokvatprzetworzona.addAll(pobierzEVatRokFK(podatnik, vatokres, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
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
            PrimeFaces.current().ajax().update("form");
            PrimeFaces.current().ajax().update("formEwidencjeGuest");
            PrimeFaces.current().ajax().update("form_dialog_ewidencjevat_sprawdzanie");
        } catch (Exception e) { 
            E.e(e); 
        }
        //drukuj ewidencje
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
        for (EVatwpisSuma p : sumaewidencji.values()) {
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
        }
    }

   

    private void rozdzielsumeEwidencjiNaPodlisty() {
        sumydowyswietleniasprzedaz = Collections.synchronizedList(new ArrayList<>());
        sumydowyswietleniazakupy = Collections.synchronizedList(new ArrayList<>());
        for (EVatwpisSuma ew : sumaewidencji.values()) {
            String typeewidencji = ew.getEwidencja().getTypewidencji();
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
    
    private void pobierzEVATwpis1zaOkres(Podatnik podatnik, int vatokres, String rok, String mc) {
        try {
            listadokvatprzetworzona = Collections.synchronizedList(new ArrayList<>());
            if (vatokres==1) {
                listadokvatprzetworzona.addAll(eVatwpis1DAO.zwrocBiezacegoKlientaRokMc(podatnik, rok, mc));
            } else {
                listadokvatprzetworzona.addAll(eVatwpis1DAO.zwrocBiezacegoKlientaRokKW(podatnik, rok, mc));
            }
            Collections.sort(listadokvatprzetworzona, new EVatwpisSupercomparator());
        } catch (Exception e) { 
            E.e(e); 
        }
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



    private List<EVatwpisFK> pobierzEVatRokFK(Podatnik podatnik, String vatokres, String rok, String mc) {
        try {
            switch (vatokres) {
                case "blad":
                    Msg.msg("e", "Nie ma ustawionego parametru vat za dany okres. Nie można sporządzić ewidencji VAT.");
                    throw new Exception("Nie ma ustawionego parametru vat za dany okres");
                case "miesięczne": 
                    return eVatwpisFKDAO.findPodatnikMc(podatnik, rok, mc, mc);
                default:
                    Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(mc)));
                    List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                    return eVatwpisFKDAO.findPodatnikMc(podatnik, rok, miesiacewkwartale.get(0), miesiacewkwartale.get(2));
            }
        } catch (Exception e) { E.e(e); 
            return null;
        }
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
        EVatwpisSuma sumauptk = new EVatwpisSuma(new Evewidencja("suma upkt"), BigDecimal.ZERO, BigDecimal.ZERO, "");
        EVatwpisSuma sumasprzedaz = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "");
        for (EVatwpisSuma ew : sumydowyswietleniasprzedaz) {
            sumasprzedaz.setNetto(sumasprzedaz.getNetto().add(ew.getNetto()));
            sumasprzedaz.setVat(sumasprzedaz.getVat().add(ew.getVat()));
            if (ew.getEwidencja().getNazwa().contains("usługi świad.")) {
                sumauptk.setNetto(sumauptk.getNetto().add(ew.getNetto()));
            }
        }
        sumydowyswietleniasprzedaz.add(sumasprzedaz);
        sumydowyswietleniasprzedaz.add(sumauptk);
        EVatwpisSuma sumazakup = new EVatwpisSuma(new Evewidencja("podsumowanie"), BigDecimal.ZERO, BigDecimal.ZERO, "");
        for (EVatwpisSuma ew : sumydowyswietleniazakupy) {
            sumazakup.setNetto(sumazakup.getNetto().add(ew.getNetto()));
            sumazakup.setVat(sumazakup.getVat().add(ew.getVat()));
        }
        sumydowyswietleniazakupy.add(sumazakup);
    }

    private void rozdzielEVatwpis1NaEwidencje() {
        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMap();
        for (EVatwpisSuper wierszogolny : listadokvatprzetworzona) {
            if (wierszogolny.getNetto() != 0.0 || wierszogolny.getVat() != 0.0) {
                //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
                String nazwaewidencji = wierszogolny.getNazwaewidencji().getNazwa();
                if (!listaewidencji.containsKey(nazwaewidencji)) {
                    listaewidencji.put(nazwaewidencji, new ArrayList<EVatwpisSuper>());
                    Evewidencja nowaEv = ewidencje.get(nazwaewidencji);
                    sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, wierszogolny.getOpizw()));
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
    
    private void rozdzielEVatwpisDedraNaEwidencje() {
        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMap();
        for (EVatwpisSuper wierszogolny : listadokvatprzetworzona) {
            if (wierszogolny.getNetto() != 0.0 || wierszogolny.getVat() != 0.0) {
                //sprawdza nazwe ewidencji zawarta w wierszu ogolnym i dodaje do listy
                String nazwaewidencji = wierszogolny.getEwidencja().getNazwa();
                if (!listaewidencji.containsKey(nazwaewidencji)) {
                    listaewidencji.put(nazwaewidencji, new ArrayList<EVatwpisSuper>());
                    Evewidencja nowaEv = ewidencje.get(nazwaewidencji);
                    sumaewidencji.put(nazwaewidencji, new EVatwpisSuma(nowaEv, BigDecimal.ZERO, BigDecimal.ZERO, wierszogolny.getOpizw()));
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

    private  Map<String, Evewidencja> przejrzyjEVatwpis1Lista() {
        Map<String, Evewidencja> ewidencje = evewidencjaDAO.findAllMapByPole();
        List<EVatwpisSuper> wierszedodatkowe = Collections.synchronizedList(new ArrayList<>());
        List<EVatwpisSuper> dousuniecia = new ArrayList<>();
        for (EVatwpisSuper ewid : listadokvatprzetworzona) {
            if (ewid.getNazwaewidencji().getTypewidencji().equals("sz") && !ewid.isNieduplikuj()) {
                wierszedodatkowe.add(beansVAT.EwidencjaVATSporzadzanie.duplikujEVatwpisSuper(ewid,ewidencjazakupu));
            }
//            if (dlaewidencja && ewid.getDokfk().getRodzajedok() != null &&  ewid.getDokfk().getRodzajedok().isTylkojpk()) {
//                dousuniecia.add(ewid);
//            }
// to nie ma prawa dzialac funkcja ta jest w miejscyu beansvat vatdeklaracja przyporzadkujPozycjeSzczegoloweNowe            
//            if (ewid.getNazwaewidencji().getNazwapola() != null && ewid.getNazwaewidencji().getNazwapola().getMacierzysty() != null){
//                wierszedodatkowe.add(duplikujsubwiersze(ewid, ewidencje));
//            }
        }
        listadokvatprzetworzona.removeAll(dousuniecia);
        listadokvatprzetworzona.addAll(wierszedodatkowe);
        return ewidencje;
    }
    
    //*****************DOZROBIENIA
    //duplikjowanie do deklaraccji ???
//     private EVatwpisSuper duplikujsubwiersze(EVatwpisSuper wiersz, Map<String, Evewidencja> ewidencje) {
//        Evpozycja macierzysty = wiersz.getNazwaewidencji().getNazwapola().getMacierzysty();
//        Evewidencja ewidencja = ewidencje.get(macierzysty.getNazwapola());
//         EVatwpisSuper duplikat = null;
//        if (wiersz instanceof EVatwpis1) {
//            duplikat = new EVatwpis1((EVatwpis1) wiersz);
//        } else {
//            duplikat = new EVatwpisFK((EVatwpisFK) wiersz);
//        }
//        //wpisuje pola zakupu
//        duplikat.setNazwaewidencji(ewidencja);
//        duplikat.setDuplikat(true);
//        return duplikat;
//    }
//    
     

//    private List transferujEVatwpisFKDoEVatwpisSuper(List<EVatwpisFK> listaprzetworzona, String vatokres) throws Exception {
//        List<EVatwpisSuper> przetransferowane = Collections.synchronizedList(new ArrayList<>());
//        int k = 1;
//        for (EVatwpisFK ewidwiersz : listaprzetworzona) {
//            if (ewidwiersz.getVat() != 0 || ewidwiersz.getNetto() != 0) {
//                EVatwpisSuper eVatViewPole = new EVatwpisSuper();
//                if (ewidwiersz.getWiersz() != null) {
//                    eVatViewPole.setDataSprz(ewidwiersz.getDataoperacji());
//                    eVatViewPole.setNumerwlasnydokfk(ewidwiersz.getNumerwlasnydokfk());
//                    eVatViewPole.setDataWyst(ewidwiersz.getDatadokumentu());
//                    eVatViewPole.setKontr(ewidwiersz.getKlient());
//                    String nrdok = ewidwiersz.getDokfk().toString2() + ", poz: " + ewidwiersz.getWiersz().getIdporzadkowy();
//                    eVatViewPole.setNrKolejny(nrdok);
//                    eVatViewPole.setNrWlDk(ewidwiersz.getDokfk().getNumerwlasnydokfk());
//                    eVatViewPole.setOpis(ewidwiersz.getWiersz().getOpisWiersza());
//                } else {
//                    eVatViewPole.setDataSprz(ewidwiersz.getDokfk().getDataoperacji());
//                    eVatViewPole.setNumerwlasnydokfk(ewidwiersz.getNumerwlasnydokfk());
//                    eVatViewPole.setDataWyst(ewidwiersz.getDokfk().getDatadokumentu());
//                    eVatViewPole.setKontr(ewidwiersz.getDokfk().getKontr());
//                    String nrdok = ewidwiersz.getDokfk().toString2();
//                    eVatViewPole.setNrKolejny(nrdok);
//                    eVatViewPole.setNrWlDk(ewidwiersz.getDokfk().getNumerwlasnydokfk());
//                    eVatViewPole.setOpis(ewidwiersz.getDokfk().getOpisdokfk());
//                    
//                }
//                eVatViewPole.setDokfk(ewidwiersz.getDokfk());
//                eVatViewPole.setProcentvat(ewidwiersz.getDokfk().getRodzajedok().getProcentvat());
//                if (ewidwiersz.isPaliwo()) {
//                    eVatViewPole.setProcentvat(50.0);
//                }
//                eVatViewPole.setId(k++);
//                eVatViewPole.setNazwaewidencji(ewidwiersz.getEwidencja());
//                eVatViewPole.setNrpolanetto(ewidwiersz.getEwidencja().getNrpolanetto());
//                eVatViewPole.setNrpolavat(ewidwiersz.getEwidencja().getNrpolavat());
//                eVatViewPole.setNetto(ewidwiersz.getNetto());
//                eVatViewPole.setVat(ewidwiersz.getVat());
//                eVatViewPole.setOpizw(ewidwiersz.getEstawka());
//                eVatViewPole.setInnymc(ewidwiersz.getDokfk().getVatM());
//                eVatViewPole.setInnyrok(ewidwiersz.getDokfk().getVatR());
//                przetransferowane.add(eVatViewPole);
//                if (eVatViewPole.getNazwaewidencji().getTypewidencji().equals("sz")) {
//                    EVatwpisSuper eVatViewPoleD = duplikujEVatwpisSuper(eVatViewPole);
//                    eVatViewPoleD.setId(k++);
//                    przetransferowane.add(eVatViewPoleD);
//                }
//            }
//        }
//        return przetransferowane;
//    }
    
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
            wiersz.setNazwaewidencji(null);
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
            wiersz.setNazwaewidencji(null);
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
            PdfVATsuma.drukuj(sumaewidencji, wpisView);
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
        listadokvatprzetworzona.addAll(pobierzEVatRokFK(podatnik, vatokres, rok, mc));
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

   

    

    
}
