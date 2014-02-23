/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Evewidencjacomparator;
import comparator.Rodzajedokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.EVatOpisDAO;
import dao.EvewidencjaDAO;
import dao.InwestycjeDAO;
import dao.KlienciDAO;
import dao.OstatnidokumentDAO;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import dao.SrodkikstDAO;
import dao.StornoDokDAO;
import dao.WpisDAO;
import data.Data;
import embeddable.EVatwpis;
import embeddable.EwidencjaAddwiad;
import embeddable.Kolmn;
import embeddable.KwotaKolumna;
import embeddable.Mce;
import embeddable.PanstwaSymb1;
import embeddable.Rozrachunek;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.Evewidencja;
import entity.Inwestycje;
import entity.Inwestycje.Sumazalata;
import entity.Klienci;
import entity.Ostatnidokument;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.SrodekTrw;
import entity.Srodkikst;
import entity.StornoDok;
import entity.Wpis;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import msg.Msg;


import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokumentView")
@ViewScoped
public class DokView implements Serializable {

    private HtmlSelectOneMenu pkpirLista;
    private HtmlInputText kontrahentNIP;
    private HtmlSelectOneMenu srodkitrwalewyposazenie;
    @Inject
    private Dok selDokument;
    @Inject
    private Dok wysDokument;
    @Inject
    private Klienci selectedKontr;
    @Inject
    private AmoDokDAO amoDokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private static Klienci przekazKontr;

    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
//    @ManagedProperty(value = "#{DokTabView}")
//    private DokTabView dokTabView;
    @ManagedProperty(value = "#{KlView}")
    private KlView klView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Kolmn kolumna;
    private String opis;
    /*pkpir*/
    @Inject
    private EVatView evat;
    @Inject
    private EVatOpisDAO eVatOpisDAO;
    @Inject
    private Evewidencja eVidencja;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    private EVatwpis eVatwpis;
    private static String wielkoscopisuewidencji;
    /* Rozliczenia vat*/
    /*Środki trwałe*/
    @Inject
    private SrodekTrw selectedSTR;
    /*Środki trwałe*/
    private boolean pokazSTR;
    private boolean pokazEST;//pokazuje wykaz srodkow dla sprzedazy
    private String test;
    @Inject
    private Srodkikst srodekkategoria;
    @Inject
    private Srodkikst srodekkategoriawynik;
    //edycja platnosci
    @Inject
    private Rozrachunek rozrachunek;
    //automatyczne ksiegowanie Storna
    @Inject
    private StornoDokDAO stornoDokDAO;
    private boolean rozliczony;
    @Inject
    private StornoDok stornoDok;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    private List<Rodzajedok> rodzajedokKlienta;
    //przechowuje ostatni dokumnet
    private String typdokumentu;
    //przechowuje wprowadzanego podatnika;
    private Podatnik podX;
    private boolean opodatkowanieryczalt;
    //pobieram wykaz KŚT
    @Inject
    private SrodkikstDAO srodkikstDAO;
    private static String przechowajdatejakdodaje;
    @Inject
    OstatnidokumentDAO ostatnidokumentDAO;
    @Inject
    WpisDAO wpisDAO;
    private List opisypkpir;
    private List<String> listamiesiecyewidencjavat;
    @Inject
    private Mce mce;
    /**
     * Lista gdzie przechowywane są wartości netto i opis kolumny wporwadzone w
     * formularzy na stronie add_wiad.xhtml
     */
    List<KwotaKolumna> nettokolumna;
    /**
     * Lista gdzie przechowywane są wartości ewidencji vat wprowadzone w
     * formularzy na stronie add_wiad.xhtml
     */
    private List<EwidencjaAddwiad> ewidencjaAddwiad;
    private double sumbrutto;
    /**
     * pola pobierajace dane
     */
    private double nettopkpir0;
    private double vatpkpir0;
    private String opiskolumny0;
    private double nettopkpir1;
    private double vatpkpir1;
    private String opiskolumny1;
    private List<String> rows;
    private int liczbawierszy;
    private List<String> kolumny;
    @Inject
    private InwestycjeDAO inwestycjeDAO;

    public List<String> getKolumny() {
        return kolumny;
    }

    public void setKolumny(List<String> kolumny) {
        this.kolumny = kolumny;
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public DokView() {
        setPokazSTR(false);
        opis = "ewidencja opis";
        setWysDokument(null);
        wpisView = new WpisView();
        opisypkpir = new ArrayList();
        listamiesiecyewidencjavat = new ArrayList<>();
        nettokolumna = new ArrayList<>();
        ewidencjaAddwiad = new ArrayList<>();
        kl1 = new ArrayList<>();
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public void dodajwierszpkpir() {
        if (liczbawierszy < 4) {
            KwotaKolumna p = new KwotaKolumna();
            p.setNetto(0.0);
            p.setNazwakolumny("nie ma");
            nettokolumna.add(p);
            liczbawierszy++;
        } else {
            Msg.msg("w", "Osiągnięto maksymalną liczbę wierszy", "dodWiad:mess_add");
        }
    }

    public void usunwierszpkpir() {
        if (liczbawierszy > 1) {
            int wielkosctabeli = nettokolumna.size();
            nettokolumna.remove(wielkosctabeli - 1);
            liczbawierszy--;
        } else {
            Msg.msg("w", "Osiągnięto minimalną liczbę wierszy", "dodWiad:mess_add");
        }
    }

    @PostConstruct
    private void init() {
        kl1.addAll(klDAO.findAll());
        rodzajedokKlienta = new ArrayList<>();
        Wpis wpistmp = wpisView.findWpisX();
        try {
            String pod = wpistmp.getPodatnikWpisu();
            podX = podatnikDAO.find(pod);
            opisypkpir.addAll(podX.getOpisypkpir());
            ArrayList<Rodzajedok> rodzajedokumentow = (ArrayList<Rodzajedok>) podX.getDokumentyksiegowe();
            Collections.sort(rodzajedokumentow, new Rodzajedokcomparator());
            rodzajedokKlienta.addAll(rodzajedokumentow);
            opodatkowanieryczalt = podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("bez VAT");
            if (podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("VAT")) {
                selDokument.setDokumentProsty(true);
            }
        } catch (Exception e) {
            String pod = "GRZELCZYK";
            podX = podatnikDAO.find(pod);
            rodzajedokKlienta.addAll(podX.getDokumentyksiegowe());
            opodatkowanieryczalt = podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("bez VAT");
        }
        //pobranie ostatniego dokumentu
        wysDokument = ostatnidokumentDAO.pobierz(wpistmp.getWprowadzil());
        try {
            selDokument.setVatR("");
            selDokument.setVatM("");
        } catch (Exception e) {
        }
        //ukrocmiesiace();

    }

    private void ukrocmiesiace() {
        int index = 0;
        List<String> listatmp = mce.getMceList();
        String biezacymiesiac = wpisView.getMiesiacWpisu();
        for (String p : listatmp) {
            if (p.equals(biezacymiesiac)) {
                listamiesiecyewidencjavat.add(p);
                if (p.equals("12") && (listamiesiecyewidencjavat.size() == 1)) {
                    listamiesiecyewidencjavat.add("01");
                }
            } else {
                if (listamiesiecyewidencjavat.size() > 0 && listamiesiecyewidencjavat.size() < 2) {
                    listamiesiecyewidencjavat.add(p);
                }
            }
        }
    }

    //edytuje ostatni dokument celem wykorzystania przy wpisie
    public void edytujdokument() {
        try {
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            typdokumentu = selDokument.getTypdokumentu();
            dokDAO.destroy(selDokument);
        } catch (Exception e) {
        }
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }

    /**
     * wybiera odpowiedni zestaw kolumn pkpir do podpiecia w zaleznosci od tego
     * czy to transakcja zakupu czy sprzedazy
     */
    /**
     * Ta funckja jest gdy wpisuje dokument i dziala
     * params.get("dodWiad:rodzajTrans")
     */
    public void podepnijListe() {
        try {
            pkpirLista.getChildren().clear();
        } catch (Exception egf) {
            pkpirLista = new HtmlSelectOneMenu();
        }
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        podepnijListecd(skrot);
    }

    /**
     * Ta funckja jest gdy edytuje dokument i dziala
     * params.get("dodWiad:rodzajTrans")
     */
    public void podepnijListe(String skrot) {
        try {
            pkpirLista.getChildren().clear();
        } catch (Exception egf) {
            pkpirLista = new HtmlSelectOneMenu();
        }
        podepnijListecd(skrot);
    }

    public void podepnijListecd(String skrot) {
        Iterator itd;
        itd = rodzajedokKlienta.iterator();
        String transakcjiRodzaj = "";
        while (itd.hasNext()) {
            Rodzajedok temp = (Rodzajedok) itd.next();
            if (temp.getSkrot().equals(skrot)) {
                transakcjiRodzaj = temp.getRodzajtransakcji();
                break;
            }
        }
        List valueList = new ArrayList();
        UISelectItems ulista = new UISelectItems();
        List dopobrania = new ArrayList();
        switch (transakcjiRodzaj) {
            case "ryczałt":
                dopobrania = kolumna.getKolumnRyczalt();
                break;
            case "ryczałt bez VAT":
                dopobrania = kolumna.getKolumnRyczalt();
                break;
            case "zakup":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            case "srodek trw":
                dopobrania = kolumna.getKolumnST();
                setPokazSTR(true);
                //wygenerujSTRKolumne();
                break;
            case "srodek trw sprzedaz":
                dopobrania = kolumna.getKolumnSTsprz();
                setPokazEST(true);
                RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
                break;
            case "import usług":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            case "eksport towarów":
                dopobrania = kolumna.getKolumnPrzychody();
                break;
            case "inwestycja":
                dopobrania = kolumna.getKolumnST();
                break;
            case "WDT":
                dopobrania = kolumna.getKolumnPrzychody();
                break;
            case "WNT":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            case "odwrotne obciążenie":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            case "usługi poza ter.":
                dopobrania = kolumna.getKolumnPrzychody();
                break;
            default:
                dopobrania = kolumna.getKolumnPrzychody();
                break;
        }
        kolumny = dopobrania;
        /*dodajemy na poczatek zwyczajawa kolumne klienta*/
        if (przekazKontr.getPkpirKolumna() != null) {
            String kol = przekazKontr.getPkpirKolumna();
            SelectItem selectI = new SelectItem(kol, kol);
            valueList.add(selectI);
        }
        /**/
        Iterator it;
        it = dopobrania.iterator();
        while (it.hasNext()) {
            String poz = (String) it.next();
            SelectItem selectItem = new SelectItem(poz, poz);
            valueList.add(selectItem);
        }
        //nie wiem dlaczego to tu było
        //selDokument.setNrWlDk("");
        ulista.setValue(valueList);
        pkpirLista.getChildren().add(ulista);
    }

    public void podepnijEwidencjeVat() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:tabelapkpir2:0:dokumentprosty");
        String skrotRT = params.get("dodWiad:rodzajTrans");
        String transakcjiRodzaj = "";
        for (Rodzajedok temp : rodzajedokKlienta) {
            if (temp.getSkrot().equals(skrotRT)) {
                transakcjiRodzaj = temp.getRodzajtransakcji();
                break;
            }
        }
        //takie gupie rozwiazanie dla umozliwienia dzialania przy edycji dokumentu
        try {
            if (skrot.equals(test));
        } catch (Exception e1) {
            if (selDokument.isDokumentProsty() == true) {
                skrot = "on";
            } else {
                skrot = null;
            }
        }
        try {
            if (skrot.equals("on")) {
                for (KwotaKolumna p : nettokolumna) {
                    sumbrutto += p.getNetto();
                }
                RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir2:0:sumbrutto");
            }
        } catch (Exception e) {
            if (opodatkowanieryczalt == false) {
                /*wyswietlamy ewidencje VAT*/
                List opisewidencji = new ArrayList();
                switch (transakcjiRodzaj) {
                    case ("zakup"):
                        opisewidencji = evat.getZakupVList();
                        break;
                    case ("srodek trw"):
                        opisewidencji = evat.getSrodkitrwaleVList();
                        break;
                    case ("srodek trw sprzedaz"):
                        opisewidencji = evat.getSprzedazVList();
                        break;
                    case ("inwestycja"):
                        opisewidencji = evat.getSrodkitrwaleVList();
                        break;
                    case ("WDT"):
                        opisewidencji = evat.getWdtVList();
                        break;
                    case ("odwrotne obciążenie"):
                        opisewidencji = evat.getRvcVList();
                        break;
                    case ("WNT"):
                        opisewidencji = evat.getWntVList();
                        break;
                    case ("import usług"):
                        opisewidencji = evat.getImportuslugList();
                        break;
                    case "usługi poza ter.":
                        opisewidencji = evat.getUslugiPTK();
                        break;
                    case "eksport towarów":
                        opisewidencji = evat.getEksporttowarow();
                        break;
                    default:
                        opisewidencji = evat.getSprzedazVList();
                }
                int iloscwierszypkpir = nettokolumna.size();
                double sumanetto = 0.0;
                for (int j=0 ; j < iloscwierszypkpir; j++) {
                    String wiersz = "dodWiad:tabelapkpir:"+j+":kwotaPkpir_input";
                    String trescwiersza = ((String) Params.params(wiersz)).replaceAll(" ", "");
                    double kwota = Double.parseDouble(trescwiersza.substring(0, trescwiersza.length()-2));
                    sumanetto += kwota;
                }
                ewidencjaAddwiad = new ArrayList<>();
                int k = 0;
                for (Object p : opisewidencji ) {
                    EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
                    ewidencjaAddwiad.setLp(k++);
                    ewidencjaAddwiad.setOpis((String) p);
                    ewidencjaAddwiad.setNetto(0.0);
                    ewidencjaAddwiad.setVat(0.0);
                    ewidencjaAddwiad.setBrutto(0.0);
                    ewidencjaAddwiad.setOpzw("op");
                    this.ewidencjaAddwiad.add(ewidencjaAddwiad);
                }
                //obliczam 23% dla pierwszego
                ewidencjaAddwiad.get(0).setNetto(sumanetto);
                if(transakcjiRodzaj.equals("WDT") || transakcjiRodzaj.equals("usługi poza ter.")  || transakcjiRodzaj.equals("eksport towarów")) {
                    ewidencjaAddwiad.get(0).setVat(0.0);
                } else {
                    ewidencjaAddwiad.get(0).setVat(sumanetto*0.23);
                }
                ewidencjaAddwiad.get(0).setBrutto(ewidencjaAddwiad.get(0).getNetto()+ewidencjaAddwiad.get(0).getVat());
                sumbrutto = ewidencjaAddwiad.get(0).getBrutto();
                RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
                RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir2:0:sumbrutto");
            }
        }
    }
   public void updatenetto(EwidencjaAddwiad e) {
       int lp = e.getLp();
       String stawkavat = ewidencjaAddwiad.get(lp).getOpis().replaceAll("[^\\d]", "" );
       try {
           double stawkaint = Double.parseDouble(stawkavat) / 100;
           ewidencjaAddwiad.get(lp).setVat(e.getNetto()*stawkaint);
       } catch (Exception ex) {
           String opis = ewidencjaAddwiad.get(lp).getOpis();
           if(opis.contains("WDT") || opis.contains("UPTK")  || opis.contains("EXP")) {
              ewidencjaAddwiad.get(0).setVat(0.0);
           } else {
              ewidencjaAddwiad.get(0).setVat(ewidencjaAddwiad.get(0).getNetto()*0.23);
           }
       }
       ewidencjaAddwiad.get(lp).setBrutto(e.getNetto()+e.getVat());
       sumbruttoAddwiad();
       String update = "dodWiad:tablicavat:"+lp+":vat";
       RequestContext.getCurrentInstance().update(update);
       update = "dodWiad:tablicavat:"+lp+":brutto";
       RequestContext.getCurrentInstance().update(update);
       update = "dodWiad:tabelapkpir2:0:sumbrutto";
       RequestContext.getCurrentInstance().update(update);
       String activate = "document.getElementById('dodWiad:tablicavat:"+lp+":vat_input').select();";
       RequestContext.getCurrentInstance().execute(activate);
   }
   
   public void updatevat(EwidencjaAddwiad e) {
       int lp = e.getLp();
       ewidencjaAddwiad.get(lp).setBrutto(e.getNetto()+e.getVat());
       sumbruttoAddwiad();
       String update = "dodWiad:tablicavat:"+lp+":brutto";
       RequestContext.getCurrentInstance().update(update);
       update = "dodWiad:tabelapkpir2:0:sumbrutto";
       RequestContext.getCurrentInstance().update(update);
       String activate = "document.getElementById('dodWiad:tablicavat:"+lp+":brutto_input').select();";
       RequestContext.getCurrentInstance().execute(activate);
   }
   
   private void sumbruttoAddwiad() {
       sumbrutto = 0.0;
       for (EwidencjaAddwiad p : ewidencjaAddwiad) {
            sumbrutto += p.getBrutto();
       }
   }

    public void pobierzwprowadzonynumer() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String wprowadzonynumer = params.get("dodWiad:numerwlasny");
        selDokument.setNrWlDk(wprowadzonynumer);
    }
    
    public void wybranydokument() {
        for (Rodzajedok p : rodzajedokKlienta) {
            if (p.getSkrot().equals((String) Params.params("dodWiad:rodzajTrans"))) {
                Msg.msg("i", p.getNazwa());
                break;
            }
        }
    }

   public void wygenerujnumerkolejny() {
        String zawartosc = "";
        try {
            zawartosc = selDokument.getNrWlDk();
        } catch (Exception ex) {
            selDokument.setNrWlDk("");
        }
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        String wprowadzonynumer = "";
        if (params.get("dodWiad:numerwlasny")!=null) {
            wprowadzonynumer = params.get("dodWiad:numerwlasny");
        }
        if (!wprowadzonynumer.equals("")) {
        } else {
            String nowynumer = "";
            Podatnik podX = wpisView.getPodatnikObiekt();
            String podatnikString = wpisView.getPodatnikWpisu();
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
            Rodzajedok rodzajdok = new Rodzajedok();
            for (Rodzajedok p : listaD) {
                if (p.getSkrot().equals(skrot)) {
                    rodzajdok = p;
                    break;
                }
            }
            String wzorzec = rodzajdok.getWzorzec();
            //odnajdywanie podzielnika;
            String separator = null;
            if (wzorzec.contains("/")) {
                separator = "/";
            }
            String[] elementy;
            try {
                elementy = wzorzec.split(separator);
                Dok ostatnidokument = dokDAO.find(skrot, podatnikString, rok);
                String[] elementyold;
                elementyold = ostatnidokument.getNrWlDk().split(separator);
                for (int i = 0; i < elementy.length; i++) {
                    String typ = elementy[i];
                    switch (typ) {
                        case "n":
                            String tmp = elementyold[i];
                            Integer tmpI = Integer.parseInt(tmp);
                            tmpI++;
                            nowynumer = nowynumer.concat(tmpI.toString()).concat(separator);
                            break;
                        case "m":
                            nowynumer = nowynumer.concat(mc).concat(separator);
                            break;
                        case "r":
                            nowynumer = nowynumer.concat(rok.toString()).concat(separator);
                            break;
                        //to jest wlasna wstawka typu FVZ
                        case "s":
                            nowynumer = nowynumer.concat(elementyold[i]).concat(separator);
                            break;
                    }
                }
                if (nowynumer.endsWith(separator)) {
                    nowynumer = nowynumer.substring(0, nowynumer.lastIndexOf(separator));
                }
            } catch (Exception e) {
                nowynumer = wzorzec;
            }
            renderujwyszukiwarke(rodzajdok);
            renderujtabele(rodzajdok);
            if (!nowynumer.equals("") && selDokument.getNrWlDk() == null) {
                selDokument.setNrWlDk(nowynumer);
                RequestContext.getCurrentInstance().update("dodWiad:numerwlasny");
            }
            if (!nowynumer.equals("") && selDokument.getNrWlDk().equals("")) {
                selDokument.setNrWlDk(nowynumer);
                RequestContext.getCurrentInstance().update("dodWiad:numerwlasny");
            }
        }

    }
    public boolean renderujwysz;

    public boolean isRenderujwysz() {
        return renderujwysz;
    }

    public void setRenderujwysz(boolean renderujwysz) {
        this.renderujwysz = renderujwysz;
    }

    private void renderujwyszukiwarke(Rodzajedok rodzajdok) {
        if (rodzajdok.getSkrot().equals("OT")) {
            setRenderujwysz(true);
        } else {
            setRenderujwysz(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelwyszukiwarki");
        RequestContext.getCurrentInstance().update("dodWiad:nowypanelsrodki");
    }

    private void renderujtabele(Rodzajedok rodzajdok) {
        if (rodzajdok.getSkrot().equals("OTS")) {
            setPokazEST(true);
        } else {
            setPokazEST(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
    }

//    public void wygenerujSTRKolumne() {
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        FacesContext facesCtx = FacesContext.getCurrentInstance();
//        ELContext elContext = facesCtx.getELContext();
//        grid3 = getGrid3();
//        grid3.getChildren().clear();
//        RequestContext.getCurrentInstance().update("dodWiad:grid3");
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//        HtmlOutputText ot = new HtmlOutputText();
//        ot.setValue("nazwa Srodka");
//        grid3.getChildren().add(ot);
//        HtmlInputText ew = new HtmlInputText();
//        final String binding = "#{DokumentView.nazwaSTR}";
//        ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
//        ew.setValueExpression("value", ve2);
//        ew.setId("nazwasrodka");
//        ew.setAccesskey("t");
//        grid3.getChildren().add(ew);
//
//        HtmlOutputText ot1 = new HtmlOutputText();
//        ot1.setValue("data przyjecia");
//        grid3.getChildren().add(ot1);
//        HtmlInputText ew1 = new HtmlInputText();
//        final String binding1 = "#{DokumentView.dataPrzSTR}";
//        ValueExpression ve1 = ef.createValueExpression(elContext, binding1, String.class);
//        ew1.setValueExpression("value", ve1);
//        ew1.setId("dataprz");
//        ew1.setOnblur("ustawDateSrodekTrw();");
//        grid3.getChildren().add(ew1);
//
//        HtmlOutputText ot3 = new HtmlOutputText();
//        ot3.setValue("symbol KST");
//        grid3.getChildren().add(ot3);
//        HtmlInputText ew3 = new HtmlInputText();
//        final String binding3 = "#{DokumentView.symbolKST}";
//        ValueExpression ve3 = ef.createValueExpression(elContext, binding3, String.class);
//        ew3.setValueExpression("value", ve3);
//        ew3.setId("symbolKST");
//        grid3.getChildren().add(ew3);
//
////            HtmlOutputText ot4 = new HtmlOutputText();
////            ot4.setValue("wybierz kategoria");
////            grid3.getChildren().add(ot4);
//
////            "id="acForce" value="#{DokumentView.selDokument.kontr}" completeMethod="#{KlView.complete}"
////                                    var="p" itemLabel="#{p.npelna}" itemValue="#{p}" converter="KlientConv" 
////                                    minQueryLength="3" maxResults="10" maxlength="10" converterMessage="Nieudana konwersja Klient"  forceSelection="true" 
////                                    effect="clip"  binding="#{DokumentView.kontrahentNazwa}" valueChangeListener="#{DokumentView.przekazKontrahenta}" 
////                                    required="true" requiredMessage="Pole klienta nie może byc puste" queryDelay="100" onblur="validateK()">
////             "                               
////            AutoComplete autoComplete = new AutoComplete();
////            final String bindingY = "#{DokumentView.srodekkategoria}";
////            ValueExpression ve2Y = ef.createValueExpression(elContext, bindingY, String.class);
////            autoComplete.setValueExpression("value", ve2Y);
////            autoComplete.setVar("p");
////            autoComplete.setItemLabel("#{p.nazwa}");
////            autoComplete.setItemValue("#{p.nazwa}");
////            autoComplete.setMinQueryLength(3);
////            FacesContext context = FacesContext.getCurrentInstance();
////            MethodExpression actionListener = context.getApplication().getExpressionFactory()
////    .createMethodExpression(context.getELContext(), "#{srodkikstView.complete}", null, new Class[] {ActionEvent.class});
////            autoComplete.setCompleteMethod(actionListener);
////            grid3.getChildren().add(autoComplete);
////            
//
//        HtmlOutputText ot4 = new HtmlOutputText();
//        ot4.setValue("stawka amort");
//        grid3.getChildren().add(ot4);
//        HtmlInputText ew4 = new HtmlInputText();
//        final String binding4 = "#{DokumentView.stawkaKST}";
//        ValueExpression ve4 = ef.createValueExpression(elContext, binding4, String.class);
//        ew4.setValueExpression("value", ve4);
//        ew4.setId("stawkaKST");
//        grid3.getChildren().add(ew4);
//
//        HtmlOutputText ot5 = new HtmlOutputText();
//        ot5.setValue("dotychczasowe umorzenie");
//        grid3.getChildren().add(ot5);
//        InputNumber ew5 = new InputNumber();
//        final String binding5 = "#{DokumentView.umorzeniepoczatkowe}";
//        ValueExpression ve5 = ef.createValueExpression(elContext, binding5, String.class);
//        ew5.setValueExpression("value", ve5);
//        ew5.setSymbol(" zł");
//        ew5.setSymbolPosition("s");
//        ew5.setDecimalPlaces(".");
//        ew5.setThousandSeparator(" ");
//        ew5.setDecimalPlaces("2");
//        ew5.setValue(0);
//        grid3.getChildren().add(ew5);
//        umorzeniepoczatkowe = 0.0;
//
//        UISelectItems ulistaX = new UISelectItems();
//        List valueListX = new ArrayList();
//        SelectItem selectItem = new SelectItem("srodek trw.", "srodek trw.");
//        valueListX.add(selectItem);
//        selectItem = new SelectItem("wyposazenie", "wyposazenie");
//        valueListX.add(selectItem);
//        ulistaX.setValue(valueListX);
//        final String bindingX = "#{DokumentView.typKST}";
//        ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
//        HtmlSelectOneMenu htmlSelectOneMenuX = new HtmlSelectOneMenu();
//        htmlSelectOneMenuX.setValueExpression("value", ve2X);
//        htmlSelectOneMenuX.setStyle("min-width: 150px");
//        htmlSelectOneMenuX.getChildren().add(ulistaX);
//        grid3.getChildren().add(htmlSelectOneMenuX);
//
//        RequestContext.getCurrentInstance().update("dodWiad:grid3");
//    }

   

    /**
     * NE zmienia wlasciwosci pol wprowadzajacych dane kontrahenta
     */
    public void dokumentProstuSchowajEwidencje() {
        selDokument.setEwidencjaVAT(null);
        ewidencjaAddwiad.clear();
        RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
    }

    public void sformatuj() {
        //String formatka=null;
        //selDokument.setLogi(selDokument.getIdDok().toLowerCase());
        //selDokument.setImie(selDokument.getImie().substring(0,1).toUpperCase()+selDokument.getImie().substring(1).toLowerCase());
        //selDokument.setNazw(selDokument.getNazw().substring(0,1).toUpperCase()+selDokument.getNazw().substring(1).toLowerCase());
    }

    /**
     * Dodawanie dokumentu wprowadzonego w formularzu na stronie add_wiad.html
     */
    public void dodaj(int rodzajdodawania) {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        selDokument.setWprowadzil(principal.getName());
        Wpis wpisbiezacy = wpisDAO.find(selDokument.getWprowadzil());
        selDokument.setPkpirM(wpisbiezacy.getMiesiacWpisu());
        selDokument.setPkpirR(wpisbiezacy.getRokWpisu().toString());
        selDokument.setPodatnik(wpisbiezacy.getPodatnikWpisu());
        Podatnik podtmp = podatnikDAO.find(wpisbiezacy.getPodatnikWpisu());
        List<Double> pobierzVat = new ArrayList<>();
        try {
            if ((!podtmp.getPodatekdochodowy().get(podtmp.getPodatekdochodowy().size() - 1).getParametr().contains("bez VAT")) && (selDokument.isDokumentProsty() == false)) {
                ArrayList<Evewidencja> ew = new ArrayList<>();
                ew.addAll(evewidencjaDAO.findAll());
                Collections.sort(ew, new Evewidencjacomparator());
                List<EVatwpis> el = new ArrayList<>();
                int i = 0;
                int rozmiar = evewidencjaDAO.findAll().size();
                int rozmiarewvatwprowadzonej = ewidencjaAddwiad.size();
                while (i < rozmiar) {
                    int j = 0;
                    while (j < 5 && j < rozmiarewvatwprowadzonej) {
                        String op = ewidencjaAddwiad.get(j).getOpis();
                        String naz = ew.get(i).getNazwa();
                        if (naz.equals(op)) {
                            eVidencja = ew.get(i);
                            eVatwpis = new EVatwpis();
                            eVatwpis.setEwidencja(eVidencja);
                            eVatwpis.setNetto(ewidencjaAddwiad.get(j).getNetto());
                            eVatwpis.setVat(ewidencjaAddwiad.get(j).getVat());
                            eVatwpis.setEstawka(ewidencjaAddwiad.get(j).getOpzw());
                            el.add(eVatwpis);
                            eVidencja = null;
                        }
                        j++;
                    }
                    i++;
                }

                if (opodatkowanieryczalt == true) {
                    selDokument.setEwidencjaVAT(null);
                } else if (!selDokument.isDokumentProsty()) {
                    selDokument.setEwidencjaVAT(el);
                } else {
                    selDokument.setEwidencjaVAT(null);
                }
            }
            selDokument.setStatus("bufor");
            selDokument.setTypdokumentu(typdokumentu);
            Iterator itd;
            itd = rodzajedokKlienta.iterator();
            String transakcjiRodzaj = "";
            while (itd.hasNext()) {
                Rodzajedok temp = (Rodzajedok) itd.next();
                if (temp.getSkrot().equals(typdokumentu)) {
                    transakcjiRodzaj = temp.getRodzajtransakcji();
                    break;
                }
            }
            selDokument.setRodzTrans(transakcjiRodzaj);
            selDokument.setOpis(selDokument.getOpis().toLowerCase());
            selDokument.setListakwot(nettokolumna);
            selDokument.setNetto(0.0);
            for (KwotaKolumna p : nettokolumna) {
                selDokument.setNetto(selDokument.getNetto() + p.getNetto());
            }
            //koniec obliczania netto
            dodajdatydlaStorno();
            //dodaje zaplate faktury gdy faktura jest uregulowana
            Double kwotavat = 0.0;
            try {
                for (Double p : pobierzVat) {
                    kwotavat = kwotavat + p;
                }
            } catch (Exception ex) {
            }
            Double kwota = 0.0;
            for (KwotaKolumna p : nettokolumna) {
                kwota = kwota + p.getNetto();
            }
            kwota = kwota + kwotavat;
            kwota = new BigDecimal(kwota).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            if (selDokument.getRozliczony() == true) {
                Rozrachunek rozrachunekx = new Rozrachunek(selDokument.getTerminPlatnosci(), kwota, 0.0, selDokument.getWprowadzil(), new Date());
                ArrayList<Rozrachunek> lista = new ArrayList<>();
                lista.add(rozrachunekx);
                selDokument.setRozrachunki(lista);
            }
            selDokument.setBrutto(kwota);
            selDokument.setUsunpozornie(false);

            //jezeli jest edytowany dokument to nie dodaje a edytuje go w bazie danych
            if (rodzajdodawania == 1) {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                //wpisywanie do bazy ostatniego dokumentu
                request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                principal = request.getUserPrincipal();
                Ostatnidokument temp = new Ostatnidokument();
                temp.setUzytkownik(principal.getName());
                temp.setDokument(selDokument);
                ostatnidokumentDAO.edit(temp);
                try {
                    String probsymbolu = selDokument.getSymbolinwestycji();
                    if (!probsymbolu.equals("wybierz")) {
                        aktualizujInwestycje(selDokument);
                    }
                } catch (Exception e) {
                }
                wysDokument = new Dok();
                wysDokument = ostatnidokumentDAO.pobierz(selDokument.getWprowadzil());
                liczbawierszy = 0;
                RequestContext.getCurrentInstance().update("zobWiad:ostatniUzytkownik");
                Msg.msg("i", "Nowy dokument zachowany" + selDokument);
                /**
                 * resetowanie pola do wpisywania kwoty netto
                 */
                nettokolumna.clear();
            } else {
                dokDAO.edit(selDokument);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            Msg.msg("e", "Wystąpił błąd. Dokument nie został zaksiegowany "+ e.getMessage() + " " + e.getStackTrace().toString());
        }
        //robienie srodkow trwalych
        if (selectedSTR.getStawka() != null) {
            try {
                selectedSTR.setNetto(selDokument.getNetto());
                BigDecimal tmp1 = BigDecimal.valueOf(selDokument.getNetto());
                double vat = 0.0;
                for (EVatwpis p : selDokument.getEwidencjaVAT()) {
                    vat += p.getVat();
                }
                selectedSTR.setVat(vat);
                selectedSTR.setDatazak(selDokument.getDataWyst());
                selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
                selectedSTR.setNrwldokzak(selDokument.getNrWlDk());
                selectedSTR.setZlikwidowany(0);
                selectedSTR.setDatasprzedazy("");
                dodajSTR();

            } catch (Exception e) {
            }
        }
        if (rodzajdodawania == 1) {
            setPokazSTR(false);
            selDokument = new Dok();
            selectedSTR = new SrodekTrw();
            ewidencjaAddwiad.clear();
            setRenderujwysz(false);
            setPokazEST(false);
            RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
            RequestContext.getCurrentInstance().update("form:dokumentyLista");
        } else {
            setPokazSTR(false);
            selectedSTR = new SrodekTrw();
            ewidencjaAddwiad.clear();
            RequestContext.getCurrentInstance().update("dodWiad:tablicavat");
            setRenderujwysz(false);
            setPokazEST(false);
        }
    }

    private Double extractDouble(String wiersz) {
        String prices = wiersz.replaceAll("\\s", "");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
            return Double.parseDouble(m.group());
        }
        return 0.0;
    }

    //dodaje wyliczone daty platnosci dla obliczenia pozniej czy trzeba stornowac
    public void dodajdatydlaStorno() throws ParseException {
        String data;
        switch (wpisView.getMiesiacWpisu()) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                break;
            case "02":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                break;
            default:
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                break;
        }
        String dataWyst = selDokument.getDataWyst();
        String dataPlat = selDokument.getTerminPlatnosci();
        Calendar c = Calendar.getInstance();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datawystawienia = (Date) formatter.parse(dataWyst);
        Date terminplatnosci = (Date) formatter.parse(dataPlat);
        Date dataujeciawkosztach = (Date) formatter.parse(data);
        if (roznicaDni(datawystawienia, terminplatnosci) == true) {
            c.setTime(terminplatnosci);
            c.add(Calendar.DAY_OF_MONTH, 30);
            String nd30 = formatter.format(c.getTime());
            selDokument.setTermin30(nd30);
            selDokument.setTermin90("");
        } else {
            c.setTime(dataujeciawkosztach);
            c.add(Calendar.DAY_OF_MONTH, 90);
            String nd90 = formatter.format(c.getTime());
            selDokument.setTermin90(nd90);
            selDokument.setTermin30("");
        }
        c.setTime(terminplatnosci);
        c.add(Calendar.DAY_OF_MONTH, 150);
        String nd150 = formatter.format(c.getTime());
        selDokument.setTermin150(nd150);
    }

    private boolean roznicaDni(Date date_od, Date date_do) {
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = Math.abs(x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        System.out.println("Roznica miedzy datami to " + wynik + " dni...");
        if (wynik <= 61) {
            return true;
        } else {
            return false;
        }
    }
    //generowanie dokumentu amortyzacji

    public void dodajNowyWpisAutomatyczny() {
        double kwotaumorzenia = 0.0;
        List<Amodok> lista = new ArrayList<Amodok>();
        lista.addAll(amoDokDAO.amodokklient(wpisView.getPodatnikWpisu()));
        Amodok amodokPoprzedni = null;
        Amodok amodok = null;
        Iterator itx;
        itx = lista.iterator();
        while (itx.hasNext()) {
            Amodok tmp = (Amodok) itx.next();
            Integer mctmp = tmp.getAmodokPK().getMc();
            String mc = Mce.getMapamcy().get(mctmp);
            Integer rok = tmp.getAmodokPK().getRok();
            if (wpisView.getMiesiacWpisu().equals("01")&&rok==wpisView.getRokWpisu()) {
                rok = rok - 1; 
            }
            if (wpisView.getRokWpisu().equals(rok) && wpisView.getMiesiacWpisu().equals(mc)) {
                amodok = tmp;
                break;
            }
            amodokPoprzedni = tmp;
        }
//nie wiem co to. trzeba chyba usunac
//        try {
//            boolean temp = amodokPoprzedni.getZaksiegowane();
//            List<Umorzenie> tempX = amodokPoprzedni.getUmorzenia();
//        } catch (Exception e) {
//        }
        //wyliczam kwote umorzenia
         List<Umorzenie> umorzenia = new ArrayList<>();
            umorzenia.addAll(amodok.getUmorzenia());
            Iterator it;
            it = umorzenia.iterator();
            while (it.hasNext()) {
                Umorzenie tmp = (Umorzenie) it.next();
                kwotaumorzenia = kwotaumorzenia + tmp.getKwota().doubleValue();
            }
        try {
            if (amodokPoprzedni != null) {
                if (amodokPoprzedni.getZaksiegowane() != true && amodokPoprzedni.getUmorzenia().size() > 0) {
                    //szukamy w dokumentach a nuz jest. jak jest to naprawiam ze nie naniesiono ze zaksiegowany
                    Dok znaleziony = dokDAO.findDokMC("AMO", wpisView.getPodatnikWpisu(), String.valueOf(amodokPoprzedni.getAmodokPK().getRok()), Mce.getMapamcy().get(amodokPoprzedni.getAmodokPK().getMc()));
                    if (znaleziony instanceof Dok && znaleziony.getNetto() == kwotaumorzenia) {
                        amodokPoprzedni.setZaksiegowane(true);
                        amoDokDAO.edit(amodokPoprzedni);
                    } else {
                        throw new Exception();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Msg.msg("e", "Wystąpił błąd. Nie ma zaksięgowanego odpisu w poprzednim miesiącu, a jest dokumet umorzeniowy za ten okres!");
            return;
        }
        try {
            selDokument.setEwidencjaVAT(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setVatM(wpisView.getMiesiacWpisu());
            selDokument.setVatR(wpisView.getRokWpisu().toString());
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            String data;
            switch (wpisView.getMiesiacWpisu()) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                    break;
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("", "dowód wewnętrzny"));
            selDokument.setRodzTrans("amortyzacja");
            selDokument.setTypdokumentu("AMO");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
            selDokument.setOpis("umorzenie za miesiac");
            List<KwotaKolumna> listaX = new ArrayList<>();
            KwotaKolumna tmpX = new KwotaKolumna();
            tmpX.setNetto(kwotaumorzenia);
            tmpX.setVat(0.0);
            tmpX.setNazwakolumny("poz. koszty");
            listaX.add(tmpX);
            selDokument.setListakwot(listaX);
            selDokument.setNetto(kwotaumorzenia);
            selDokument.setRozliczony(true);
            sprawdzCzyNieDuplikat(selDokument);
            if (selDokument.getNetto() > 0) {
                dokDAO.dodaj(selDokument);
                String wiadomosc = "Nowy dokument umorzenia zachowany: " + selDokument.getPkpirR() + "/" + selDokument.getPkpirM() + " kwota: " + selDokument.getNetto();
                Msg.msg("i", wiadomosc);
                amodok.setZaksiegowane(true);
                amoDokDAO.edit(amodok);
                Msg.msg("i", "Informacje naniesione na dokumencie umorzenia");
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota umorzenia wynosi 0zł. Dokument nie został zaksiegowany", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return;
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd, dokument AMO nie zaksięgowany!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void dodajNowyWpisAutomatycznyStorno() {
        selDokument = new Dok();
        double kwotastorno = 0.0;
        ArrayList<Dok> lista = new ArrayList<>();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        StornoDok tmp = new StornoDok();
        try {
            tmp = stornoDokDAO.find(rok, mc, podatnik);
            lista = (ArrayList<Dok>) tmp.getDokument();
            Iterator itx;
            itx = lista.iterator();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                kwotastorno = kwotastorno + tmpx.getStorno().get(tmpx.getStorno().size() - 1).getKwotawplacona();
            }

            selDokument.setEwidencjaVAT(null);
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setVatM("");
            selDokument.setVatR("");
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            String data;
            switch (wpisView.getMiesiacWpisu()) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                    break;
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("111111111", "wlasny"));
            selDokument.setRodzTrans("storno niezapłaconych faktur");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu() + "/" + wpisView.getRokWpisu().toString());
            selDokument.setOpis("storno za miesiac");
            List<KwotaKolumna> listaX = new ArrayList<>();
            KwotaKolumna tmpX = new KwotaKolumna();
            tmpX.setNetto(kwotastorno);
            tmpX.setVat(0.0);
            tmpX.setNazwakolumny("poz. koszty");
            listaX.add(tmpX);
            selDokument.setListakwot(listaX);
            selDokument.setRozliczony(true);
            selDokument.setTypdokumentu(typdokumentu);
            selDokument.setUsunpozornie(false);
            //sprawdzCzyNieDuplikat(selDokument);
            if (selDokument.getNetto() != 0) {
                sprawdzCzyNieDuplikat(selDokument);
                dokDAO.dodaj(selDokument);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Nowy dokument storno zachowany", selDokument.getIdDok().toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                tmp.setZaksiegowane(true);
                stornoDokDAO.edit(tmp);
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota storno wynosi 0zł. Dokument nie został zaksiegowany", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd, dokument strono nie zaksięgowany!", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
        }
    }
    //zaimplementowac!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//    public void sprawdzczyjestwpisuprzedni() throws Exception{
//        Integer rok = wpisView.getRokWpisu();
//        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
//        if(mc==1){
//            rok--;
//            mc=12;
//        } else {
//            mc--;
//        }
//       
//       Dok tmp = dokDAO.znajdzPoprzednika(rok, mc);
//        if (tmp == null) {
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie zaksiegowano amortyzacji w poprzednim miesiacu", null);
//            FacesContext.getCurrentInstance().addMessage("wprowadzenieNowego", msg);
//            RequestContext.getCurrentInstance().update("messageserror");
//            throw new Exception();
//        } else {
//            System.out.println("Nie znaleziono duplikatu");
//        }
//    }

    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception {
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikat(selD, wpisView.getRokWpisuSt());
        if (tmp instanceof Dok) {
            String wiadomosc = "Dokument typu "+selD.getTypdokumentu()+" dla tego klienta, o numerze "+selD.getNrWlDk()+" i kwocie netto "+selD.getNetto()+" jest juz zaksiegowany u podatnika: " + selD.getPodatnik();
            Msg.msg("e", wiadomosc);
            throw new Exception();
        } else {
            System.out.println("Nie znaleziono duplikatu");
        }
    }
    
     public void sprawdzCzyNieDuplikatwtrakcie(AjaxBehaviorEvent ex) {
        try {
            Dok selD = null;
            selD = dokDAO.znajdzDuplikatwtrakcie(selDokument, wpisView.getPodatnikObiekt().getNazwapelna(), (String) Params.params("dodWiad:rodzajTrans"));
            if (selD instanceof Dok){
                String wiadomosc = "Dokument typu "+selD.getTypdokumentu()+" dla tego klienta, o numerze "+selD.getNrWlDk()+" i kwocie netto "+selD.getNetto()+" jest juz zaksiegowany u podatnika: " + selD.getPodatnik();
                Msg.msg("e", wiadomosc);
                RequestContext.getCurrentInstance().execute("$('#dodWiad\\\\:numerwlasny').select();");
            } else {
                System.out.println("Nie znaleziono duplikatu");
            }
        } catch (Exception e) {
            Msg.msg("w", "Blad w DokView sprawdzCzyNieDuplikatwtrakcie");
        }
    }
    

    public void ustawDate(AjaxBehaviorEvent e) {
        selDokument.setDokumentProsty(false);
        RequestContext.getCurrentInstance().update("dodWiad:dokumentprosty");
        String dataWyst = selDokument.getDataWyst();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        if (dataWyst.equals("-")) {
            dataWyst = rok + "-";
        } else if (dataWyst.equals(rok + "--")) {
            dataWyst = rok + "-" + mc + "-";
        }
        selDokument.setDataWyst(dataWyst);
        selDokument.setDataSprz(dataWyst);
        selDokument.setVatM(mc);
        RequestContext.getCurrentInstance().update("dodWiad:vatm");
        selDokument.setVatR(rok.toString());
        RequestContext.getCurrentInstance().update("dodWiad:vatr");
        przechowajdatejakdodaje = dataWyst;
    }
    //nie ustawia daty tylko pokazuje pierwsza kolumne pkpir

    public void ustawDate2() {
        if (liczbawierszy < 1) {
            nettokolumna.add(new KwotaKolumna());
            RequestContext.getCurrentInstance().update("dodWiad:tabelapkpir");
            liczbawierszy++;
        }
    }

    //przekazuje zeby pobrac jego domyslna kolumne do listy kolumn
    public void przekazKontrahenta(ValueChangeEvent e) throws Exception {
        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
        przekazKontr = (Klienci) anAutoComplete.getValue();
        selDokument.setKontr(przekazKontr);
        RequestContext.getCurrentInstance().update("dodWiad:acForce");
        if (podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size() - 1).getParametr().contains("VAT")) {
            selDokument.setDokumentProsty(true);
            RequestContext.getCurrentInstance().update("dodWiad:dokumentprosty");
        }
    }
    
    public void zmienokresVAT() {
        String datafaktury = (String) Params.params("dodWiad:dataPole");
        String dataobowiazku = (String) Params.params("dodWiad:dataSPole");
        int porownaniedat = Data.compare(datafaktury, dataobowiazku);
        String rok;
        String mc;
        if (porownaniedat >= 0) {
            rok = dataobowiazku.substring(0,4);
            mc = dataobowiazku.substring(5,7);
        } else {
            rok = datafaktury.substring(0,4);
            mc = datafaktury.substring(5,7);
        }
        selDokument.setVatR(rok);
        selDokument.setVatM(mc);
        RequestContext.getCurrentInstance().update("dodWiad:ostatnipanel");
    }

    public void przekazKontrahentaA(AjaxBehaviorEvent e) throws Exception {
        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
        String aSelection = anAutoComplete.getValue().toString();
        if (aSelection.equals("nowy klient")) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "nowy kontrahent", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
        }
    }

    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaZestawienieRok.xhtml");
    }

    public void aktualizujWestWpisWidokIndex(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
        Msg.msg("i", "Udana zamiana klienta. Aktualny klient to: " + wpisView.getPodatnikWpisu(), "dodWiad:mess_add");

    }

    public void aktualizujZamkniecie(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaZamkniecie.xhtml");
    }

    public void aktualizujVAT(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaVATzest.xhtml");
    }

    public void aktualizujPit(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaPit.xhtml");

    }

    private void aktualizuj() {
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.findWpis();
    }

    public String aktualizujPop() throws IOException {
        return "/manager/managerTerminy.xhtml";

    }

    public void dodajSTR() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ValueBinding binding = application.createValueBinding("#{SrodkiTrwaleView}");
        STRView sTRView = (STRView) binding.getValue(facesContext);
        String podatnik = wpisView.getPodatnikWpisu();
        String name = podatnik;
        selectedSTR.setPodatnik(podatnik);
        sTRView.dodajSrodekTrwaly(selectedSTR);
        RequestContext.getCurrentInstance().update("srodki:panelekXA");
    }

    public void skopiujdoTerminuPlatnosci() {
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        selDokument.setTerminPlatnosci(params.get("dodWiad:dataPole"));
//        RequestContext.getCurrentInstance().update("dodWiad:dataTPole");
    }

    public void zaksiegujPlatnosc(ActionEvent e) {
        //pobieranie daty zeby zobaczyc czy nie ma juz dokumentu storno z ta data 
        String data = rozrachunek.getDataplatnosci();
        Integer r = Integer.parseInt(data.substring(0, 4));
        String m = data.substring(5, 7);
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            stornoDok = stornoDokDAO.find(r, m, podatnik);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje dokument storno. Za późno wprowadzasz te płatność", stornoDok.getMc().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:messages");
        } catch (Exception ec) {
            ArrayList<Rozrachunek> lista = new ArrayList<>();
            double zostalo = 0;
            double kwota = 0;
            try {
                lista.addAll(selDokument.getRozrachunki());
                zostalo = lista.get(lista.size() - 1).getDorozliczenia();
            } catch (Exception ee) {
            }
            if (zostalo == 0) {
                try {
                    kwota = -selDokument.getBrutto();
                } catch (Exception el) {
                }
            } else {
                kwota = zostalo;
            }
            int pozostalo = (int) (kwota + rozrachunek.getKwotawplacona());
            rozrachunek.setDorozliczenia(kwota + rozrachunek.getKwotawplacona());
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            rozrachunek.setWprowadzil(principal.getName());
            rozrachunek.setDatawprowadzenia(new Date());
            lista.add(rozrachunek);
            if (pozostalo == 0) {
                selDokument.setRozliczony(true);
            }
            selDokument.setRozrachunki(lista);
            try {
                dokDAO.edit(selDokument);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Płatność zachowana" + selDokument, null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception ex) {
                System.out.println(ex.getStackTrace().toString());
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Płatność niezachowana " + ex.getStackTrace().toString(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void usunostatniRozrachunek(ActionEvent e) {
        ArrayList<Rozrachunek> lista = new ArrayList<>();
        try {
            lista.addAll(selDokument.getRozrachunki());
        } catch (Exception ee) {
        }
        lista.remove(lista.get(lista.size() - 1));
        selDokument.setRozrachunki(lista);
        dokDAO.edit(selDokument);
    }

    public void skopiujSTR() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nazwa = params.get("dodWiad:acForce1_input");
        if (!nazwa.equals("")) {
            try {
                srodekkategoriawynik = srodkikstDAO.finsStr1(nazwa);
                selectedSTR.setKst(srodekkategoriawynik.getSymbol());
                selectedSTR.setUmorzeniepoczatkowe(0.0);
                selectedSTR.setStawka(Double.parseDouble(srodekkategoriawynik.getStawka()));
                RequestContext.getCurrentInstance().update("dodWiad:nowypanelsrodki");
            } catch (Exception e) {
            }
        }
    }

    public void przekierowanieWpisKLienta() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("klienci.xhtml");
    }

    /**
     * stare do edycji dokumentu
     *
     * @param wpis
     * @throws IOException
     */
    public void przekierowanieEdytujDokument(Dok wpis) throws IOException {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        Ostatnidokument temp = new Ostatnidokument();
        temp.setUzytkownik(principal.getName());
        temp.setDokument(wpis);
        try {
            ostatnidokumentDAO.dodaj(temp);
        } catch (Exception e) {
            ostatnidokumentDAO.edit(temp);
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
    }

    public void przekierowanieWpis() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
    }

    public void przekierowanieWpisKLientacd() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
        selDokument.setDataWyst(przechowajdatejakdodaje);
        RequestContext.getCurrentInstance().update("dodWiad:dataPole");
    }

    private void aktualizujInwestycje(Dok dok) {
        try {
            List<Inwestycje> inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
            String symbol = dok.getSymbolinwestycji();
            if (!symbol.equals("wybierz")) {
                Inwestycje biezaca = null;
                for (Inwestycje p : inwestycje) {
                    if (p.getSymbol().equals(symbol)) {
                        biezaca = p;
                        break;
                    }
                }
                biezaca.setTotal(biezaca.getTotal() + dok.getNetto());
                List<Inwestycje.Sumazalata> sumazalata = biezaca.getSumazalata();
                if (sumazalata.isEmpty()) {
                    Inwestycje x = new Inwestycje();
                    Inwestycje.Sumazalata sum = x.new Sumazalata();
                    sum.setRok(wpisView.getRokWpisu().toString());
                    sum.setKwota(0.0);
                    sumazalata.add(sum);
                } else {
                    List<String> roki = new ArrayList<>();
                    for (Inwestycje.Sumazalata p : sumazalata) {
                        roki.add(p.getRok());
                    }
                    if (!roki.contains(wpisView.getRokWpisu().toString())) {
                        Inwestycje x = new Inwestycje();
                        Inwestycje.Sumazalata sum = x.new Sumazalata();
                        sum.setRok(wpisView.getRokWpisu().toString());
                        sum.setKwota(0.0);
                        sumazalata.add(sum);
                    }
                }
                for (Inwestycje.Sumazalata p : sumazalata) {
                    if (p.getRok().equals(dok.getPkpirR())) {
                        p.setKwota(p.getKwota() + dok.getNetto());
                        biezaca.setSumazalata(sumazalata);
                        break;
                    }
                }
                biezaca.getDokumenty().add(dok);
                inwestycjeDAO.edit(biezaca);
                Msg.msg("i", "Aktualizuje inwestycje " + symbol, "dodWiad:mess_add");

            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd nie zaktualizowałem inwestycji!", "dodWiad:mess_add");
        }
    }

    //kopiuje ostatni dokument celem wykorzystania przy wpisie
    public void skopiujdokument() {
        try {
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            ustawDate2();
            String skrot = selDokument.getTypdokumentu();
            String nowynumer = "";
            String pod = wpisView.findWpisX().getPodatnikWpisu();
            Podatnik podX = podatnikDAO.find(pod);
            List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
            Rodzajedok rodzajdok = new Rodzajedok();
            for (Rodzajedok p : listaD) {
                if (p.getSkrot().equals(skrot)) {
                    rodzajdok = p;
                    break;
                }
            }
            typdokumentu = skrot;
            przekazKontr = selDokument.getKontr();
            podepnijListe(skrot);
            nettokolumna.clear();
            for (KwotaKolumna p : selDokument.getListakwot()) {
                nettokolumna.add(p);
            }
            renderujwyszukiwarke(rodzajdok);
            renderujtabele(rodzajdok);
        } catch (Exception e) {
        }
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }


    public void skopiujdoedycji() {
        selDokument = DokTabView.getGosciuwybralS().get(0);
        //Msg.msg("i", "Wybrano fakturę " + selDokument.getNrWlDk() + " do edycji");
    }
    
    private void skopiujdoedycjidane() {
        selDokument = DokTabView.getGosciuwybralS().get(0);
        ustawDate2();
        String skrot = selDokument.getTypdokumentu();
        String nowynumer = "";
        String pod = wpisView.findWpisX().getPodatnikWpisu();
        Podatnik podX = podatnikDAO.find(pod);
        List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
        Rodzajedok rodzajdok = new Rodzajedok();
        for (Rodzajedok p : listaD) {
            if (p.getSkrot().equals(skrot)) {
                rodzajdok = p;
                break;
            }
        }
        typdokumentu = skrot;
        przekazKontr = selDokument.getKontr();
        podepnijListe(skrot);
        nettokolumna.clear();
        for (KwotaKolumna p : selDokument.getListakwot()) {
            nettokolumna.add(p);
        }
        ewidencjaAddwiad.clear();;
        int j = 1;
        for (EVatwpis s : selDokument.getEwidencjaVAT()) {
            EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
            ewidencjaAddwiad.setOpis(s.getEwidencja().getNazwa());
            ewidencjaAddwiad.setOpzw(s.getEwidencja().getRodzajzakupu());
            ewidencjaAddwiad.setNetto(s.getNetto());
            ewidencjaAddwiad.setVat(s.getVat());
            ewidencjaAddwiad.setBrutto(s.getNetto()+s.getVat());
            ewidencjaAddwiad.setLp(j++);
            this.ewidencjaAddwiad.add(ewidencjaAddwiad);
        }
        renderujwyszukiwarke(rodzajdok);
        renderujtabele(rodzajdok);
        RequestContext.getCurrentInstance().update("dialogEdycja");
    }

    public void sprawdzczywybranodokumentdoedycji() {
        skopiujdoedycjidane();
        if (selDokument.getTypdokumentu().equals("OT")) {
            Msg.msg("e", "Nie można edytować dokumnetu zakupu środków trwałych!");
            RequestContext.getCurrentInstance().execute("dlg123.hide();");
            return;
        }
        if (selDokument.getNetto() != null) {
            RequestContext.getCurrentInstance().execute("dlg123.show();");
        } else {
            Msg.msg("e", "Nie wybrano dokumentu do edycji!");
            RequestContext.getCurrentInstance().execute("dlg123.hide();");
        }
    }

    @Inject private Klienci selectedKlient;
    @Inject private KlienciDAO klDAO;
    private static ArrayList<Klienci> kl1;
    @Inject PanstwaSymb1 ps1;
    
     public void dodajKlienta(){
      try {
        if(selectedKlient.getNip().equals("")){
            wygenerujnip();
        }
        //Usunalem formatowanie pelnej nazwy klienta bo przeciez imie i nazwiko pisze sie wielkimi a ten zmniejszal wszystko
//        String formatka = selectedKlient.getNpelna().substring(0, 1).toUpperCase();
//        formatka = formatka.concat(selectedKlient.getNpelna().substring(1).toLowerCase());
//        selectedKlient.setNpelna(formatka);
        String formatka = selectedKlient.getNskrocona().toUpperCase();
        selectedKlient.setNskrocona(formatka);
        formatka = selectedKlient.getUlica().substring(0, 1).toUpperCase();
        formatka = formatka.concat(selectedKlient.getUlica().substring(1).toLowerCase());
        selectedKlient.setUlica(formatka);
        try {
            selectedKlient.getKrajnazwa();
        } catch (Exception e){
            selectedKlient.setKrajnazwa("Polska");
        }
        String kraj = selectedKlient.getKrajnazwa();
        String symbol = ps1.getWykazPanstwSX().get(kraj);
        selectedKlient.setKrajkod(symbol);
        poszukajnip();
        klDAO.dodaj(selectedKlient);
        kl1.add(selectedKlient);
        selDokument.setKontr(selectedKlient);
        RequestContext.getCurrentInstance().update("formX:");
        RequestContext.getCurrentInstance().update("formY:tabelaKontr");
        RequestContext.getCurrentInstance().update("dodWiad:acForce");
        Msg.msg("i","Dodano nowego klienta"+selectedKlient.getNpelna(),"formX:mess_add");
        } catch (Exception e) {
        Msg.msg("e","Nie dodano nowego klienta. Klient o takim Nip juz istnieje","formX:mess_add");
        }
         
         
   }
     
       private void poszukajnip() throws Exception {
         String nippoczatkowy = selectedKlient.getNip();
         if(!nippoczatkowy.equals("0000000000")){
         List<Klienci> kliencitmp  = new ArrayList<>();
         kliencitmp = klDAO.findAll();     
         List<String> kliencinip = new ArrayList<>();
         for (Klienci p : kliencitmp){
             if(p.getNip().equals(nippoczatkowy)){
                 System.out.println("tak nip juz jest, wyrzucam blad");
                 throw new Exception();
             }
         }
         }
    }
     
    
     private void wygenerujnip() {
       List<Klienci> kliencitmp  = klDAO.findAll();
       List<Klienci> kliencinip = new ArrayList<>();
       //odnajduje klientow jednorazowych
       for (Klienci p : kliencitmp){
           if(p.getNip().startsWith("XX")){
               kliencinip.add(p);
           }
       }
       //wyciaga nipy
       List<Integer> nipy = new ArrayList<>();
       for (Klienci p : kliencinip){
             nipy.add(Integer.parseInt(p.getNip().substring(2)));
       }
       Collections.sort(nipy);
       Integer max;
       if(nipy.size()>0){
          max = nipy.get(nipy.size()-1);
          max++;
       } else {
          max = 0;
       }
       //uzupelnia o zera i robi stringa;
       String wygenerowanynip = max.toString();
       while(wygenerowanynip.length()<10){
           wygenerowanynip = "0"+wygenerowanynip;
       }
       wygenerowanynip = "XX"+wygenerowanynip;
       selectedKlient.setNip(wygenerowanynip);
    }

     public List<Klienci> completeKL(String query) {  
        List<Klienci> results = new ArrayList<>();
        try{
            String q = query.substring(0,1);
            int i = Integer.parseInt(q);
            for(Klienci p : kl1) {  
             if(p.getNip().startsWith(query)) {
                 results.add(p);
             }
            }
        } catch (NumberFormatException e){
            for(Klienci p : kl1) {
            if(p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                 results.add(p);
             }
            }
        }  
        results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
        return results;  
    }  
     
  
    public Klienci getSelectedKlient() {
        return selectedKlient;
    }

    public void setSelectedKlient(Klienci selectedKlient) {
        this.selectedKlient = selectedKlient;
    }

    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public boolean isPokazSTR() {
        return pokazSTR;
    }

    public void setPokazSTR(boolean pokazSTR) {
        this.pokazSTR = pokazSTR;
    }

    public List<EwidencjaAddwiad> getEwidencjaAddwiad() {
        return ewidencjaAddwiad;
    }

    public void setEwidencjaAddwiad(List<EwidencjaAddwiad> ewidencjaAddwiad) {
        this.ewidencjaAddwiad = ewidencjaAddwiad;
    }

    
    public KlView getKlView() {
        return klView;
    }

    public void setKlView(KlView klView) {
        this.klView = klView;
    }

    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public EVatwpis geteVatwpis() {
        return eVatwpis;
    }

    public void seteVatwpis(EVatwpis eVatwpis) {
        this.eVatwpis = eVatwpis;
    }

    public Evewidencja geteVidencja() {
        return eVidencja;
    }

    public void seteVidencja(Evewidencja eVidencja) {
        this.eVidencja = eVidencja;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public HtmlSelectOneMenu getPkpirLista() {
        return pkpirLista;
    }

    public void setPkpirLista(HtmlSelectOneMenu pkpirLista) {
        this.pkpirLista = pkpirLista;
    }

    public HtmlInputText getKontrahentNIP() {
        return kontrahentNIP;
    }

    public void setKontrahentNIP(HtmlInputText kontrahentNIP) {
        this.kontrahentNIP = kontrahentNIP;
    }

    public Klienci getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Klienci selectedKontr) {
        this.selectedKontr = selectedKontr;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public static Klienci getPrzekazKontr() {
        return przekazKontr;
    }

    public static void setPrzekazKontr(Klienci przekazKontr) {
        DokView.przekazKontr = przekazKontr;
    }

   
    public Dok getWysDokument() {
        return wysDokument;
    }

    public void setWysDokument(Dok wysDokument) {
        this.wysDokument = wysDokument;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
   
    public HtmlSelectOneMenu getSrodkitrwalewyposazenie() {
        return srodkitrwalewyposazenie;
    }

    public void setSrodkitrwalewyposazenie(HtmlSelectOneMenu srodkitrwalewyposazenie) {
        this.srodkitrwalewyposazenie = srodkitrwalewyposazenie;
    }

  
    public String getWielkoscopisuewidencji() {
        return wielkoscopisuewidencji;
    }

    public void setWielkoscopisuewidencji(String wielkoscopisuewidencji) {
        this.wielkoscopisuewidencji = wielkoscopisuewidencji;
    }

    public Rozrachunek getRozrachunek() {
        return rozrachunek;
    }

    public void setRozrachunek(Rozrachunek rozrachunek) {
        this.rozrachunek = rozrachunek;
    }

    public boolean isRozliczony() {
        return rozliczony;
    }

    public void setRozliczony(boolean rozliczony) {
        this.rozliczony = rozliczony;
    }

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

  
    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
    }

    public Srodkikst getSrodekkategoriawynik() {
        return srodekkategoriawynik;
    }

    public void setSrodekkategoriawynik(Srodkikst srodekkategoriawynik) {
        this.srodekkategoriawynik = srodekkategoriawynik;
    }

   

    public List<String> getListamiesiecyewidencjavat() {
        return listamiesiecyewidencjavat;
    }

    public void setListamiesiecyewidencjavat(List<String> listamiesiecyewidencjavat) {
        this.listamiesiecyewidencjavat = listamiesiecyewidencjavat;
    }

    public List<KwotaKolumna> getNettokolumna() {
        return nettokolumna;
    }

    public void setNettokolumna(List<KwotaKolumna> nettokolumna) {
        this.nettokolumna = nettokolumna;
    }

    public double getNetto0() {
        return nettopkpir0;
    }

    public void setNetto0(double netto0) {
        this.nettopkpir0 = netto0;
    }

    public double getVat0() {
        return vatpkpir0;
    }

    public void setVat0(double vat0) {
        this.vatpkpir0 = vat0;
    }

    public String getOpiskolumny0() {
        return opiskolumny0;
    }

    public void setOpiskolumny0(String opiskolumny0) {
        this.opiskolumny0 = opiskolumny0;
    }

    public double getNettopkpir0() {
        return nettopkpir0;
    }

    public void setNettopkpir0(double nettopkpir0) {
        this.nettopkpir0 = nettopkpir0;
    }

    public double getNettopkpir1() {
        return nettopkpir1;
    }

    public void setNettopkpir1(double nettopkpir1) {
        this.nettopkpir1 = nettopkpir1;
    }

    public String getOpiskolumny1() {
        return opiskolumny1;
    }

    public void setOpiskolumny1(String opiskolumny1) {
        this.opiskolumny1 = opiskolumny1;
    }

    

    public boolean isPokazEST() {
        return pokazEST;
    }

    public void setPokazEST(boolean pokazEST) {
        this.pokazEST = pokazEST;
    }

    public double getSumbrutto() {
        return sumbrutto;
    }

    public void setSumbrutto(double sumbrutto) {
        this.sumbrutto = sumbrutto;
    }

    
//   public DokTabView getDokTabView() {
//       return dokTabView;
//   }
//   
//   public void setDokTabView(DokTabView dokTabView) {
//       this.dokTabView = dokTabView;
//   }
//   
    //    public static void main(String[] args) throws ParseException{
    //        String data = "2012-02-02";
    //        Calendar c = Calendar.getInstance();
    //        DateFormat formatter;
    //        formatter = new SimpleDateFormat("yyyy-MM-dd");
    //        Date terminplatnosci = (Date) formatter.parse(data);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 30);
    //        String nd30 = formatter.format(c.getTime());
    ////        selDokument.setTermin30(nd30);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 90);
    //        String nd90 = formatter.format(c.getTime());
    //      //  selDokument.setTermin90(nd90);
    //        c.setTime(terminplatnosci);
    //        c.add(Calendar.DAY_OF_MONTH, 150);
    //        String nd150 = formatter.format(c.getTime());
    //        //selDokument.setTermin150(nd150);
    //    }
    public static void main(String[] args) {
        Map<String, Object> lolo = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        //        addDays("2008-03-08");
        //        addDays("2009-03-07");
        //        addDays("2010-03-13");
    }
    //
    //    public static void addDays(String dateString) {
    //        System.out.println("Got dateString: " + dateString);
    //
    //        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    //
    //        Calendar calendar = Calendar.getInstance();
    //        try {
    //            calendar.setTime(sdf.parse(dateString));
    //            Date day1 = calendar.getTime();
    //            System.out.println("  day1 = " + sdf.format(day1));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day2 = calendar.getTime();
    //            System.out.println("  day2 = " + sdf.format(day2));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day3 = calendar.getTime();
    //            System.out.println("  day3 = " + sdf.format(day3));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day4 = calendar.getTime();
    //            System.out.println("  day4 = " + sdf.format(day4));
    //
    //            // Skipping a few days ahead:
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 235);
    //            Date day5 = calendar.getTime();
    //            System.out.println("  day5 = " + sdf.format(day5));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day6 = calendar.getTime();
    //            System.out.println("  day6 = " + sdf.format(day6));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day7 = calendar.getTime();
    //            System.out.println("  day7 = " + sdf.format(day7));
    //
    //            calendar.add(java.util.Calendar.DAY_OF_MONTH, 1);
    //            Date day8 = calendar.getTime();
    //            System.out.println("  day8 = " + sdf.format(day8));
    //
    //        } catch (Exception e) {
    //        }
    //    }
    //      public void uporzadkujbrutto(){
    //          List<Dok> lista = dokDAO.findAll();
    //          for(Dok sel : lista){
    //                Double kwota = sel.getKwota();
    //                try{
    //                kwota = kwota + sel.getKwotaX();
    //                } catch (Exception e){}
    //
    //                double kwotavat = 0;
    //                try{
    //                    List<EVatwpis> listavat = sel.getEwidencjaVAT();
    //                    for(EVatwpis p : listavat){
    //                        kwotavat = kwotavat + p.getVat();
    //                    }
    //                } catch (Exception e){}
    //                try{
    //                kwota = kwota + kwotavat;
    //                } catch (Exception e){}
    //                sel.setBrutto(kwota);
    //                dokDAO.edit(sel);
    //          }
    //      }
    //       public void uporzadkujekstra(){
    //          List<Dok> lista = dokDAO.zwrocBiezacegoKlienta("EKSTRA S.C.");
    //          for(Dok sel : lista){
    //                Double kwota = sel.getKwota();
    //                if(sel.getPodatnik().equals("EKSTRA S.C.")){
    //                    sel.setPodatnik("EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK");
    //                }
    //                System.out.println("Zmienilem dokument");
    //                dokDAO.edit(sel);
    //          }
    //      }
    //      public void przeksiegujkwoty(){
    //          List<Dok> lista = dokDAO.findAll();
    //          for(Dok p : lista){
    //              List<KwotaKolumna> wiersz = new ArrayList<>();
    //              KwotaKolumna pierwszy = new KwotaKolumna();
    //              KwotaKolumna drugi = new KwotaKolumna();
    //              try {
    //                  pierwszy.setNetto(p.getKwota());
    //                  BigDecimal tmp1 = BigDecimal.valueOf((p.getBrutto()-p.getNetto()));
    //                  tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
    //                  pierwszy.setVat(tmp1.doubleValue());
    //                  tmp1 = BigDecimal.valueOf(p.getBrutto());
    //                  tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
    //                  pierwszy.setBrutto(tmp1.doubleValue());
    //                  pierwszy.setNazwakolumny(p.getPkpirKol());
    //                  wiersz.add(pierwszy);
    //              } catch (Exception e){}
    //              try {
    //                  drugi.setNetto(p.getKwotaX());
    //                  drugi.setVat(0.0);
    //                  drugi.setBrutto(p.getKwotaX().doubleValue());
    //                  drugi.setNazwakolumny(p.getPkpirKolX());
    //                  drugi.setDowykorzystania("dosprawdzenia");
    //                  wiersz.add(drugi);
    //              } catch (Exception e){}
    //              p.setListakwot(wiersz);
    //              dokDAO.edit(p);
    //              System.out.println("Przearanżowano "+p.getNrWlDk()+" - "+p.getPodatnik());
    //          }
    //      }
    //</editor-fold>
}
