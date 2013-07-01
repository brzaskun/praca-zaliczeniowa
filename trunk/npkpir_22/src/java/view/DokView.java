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
import dao.OstatnidokumentDAO;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import dao.SrodkikstDAO;
import dao.StornoDokDAO;
import dao.WpisDAO;
import embeddable.EVatwpis;
import embeddable.Kolmn;
import embeddable.KwotaKolumna;
import embeddable.Mce;
import embeddable.Rozrachunek;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.EVatOpis;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
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
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.component.inputnumber.InputNumber;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokumentView")
@ViewScoped
public class DokView implements Serializable{
    private HtmlSelectOneMenu pkpirLista;
    private HtmlInputText kontrahentNIP;
    private HtmlSelectOneMenu srodkitrwalewyposazenie;

    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    
    @Inject private Dok selDokument;
    @Inject private Dok wysDokument;
    @Inject private Klienci selectedKontr;
    @Inject private AmoDokDAO amoDokDAO;
    @Inject private PodatnikDAO podatnikDAO;
    
    
    
    private static Klienci przekazKontr;

    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;
    @Inject private Kolmn kolumna; 
    private String opis;
    /*pkpir*/
    /* Rozliczenia vat*/
    private String opis1;
    private double netto1;
    private double vat1;
    private String vat1S;
    private String opis2;
    private double netto2;
    private double vat2;
    private String vat2S;
    private String opis3;
    private double netto3;
    private double vat3;
    private String vat3S;
    private String opis4;
    private double netto4;
    private double vat4;
    private String vat4S;
    private String opizw;
    private String opis5;
    private double netto5;
    private double vat5;
    private String vat5S;
    
    
    @Inject private EVatView evat;
    @Inject private EVatOpisDAO eVatOpisDAO;
    @Inject private Evewidencja eVidencja;
    @Inject private EvewidencjaDAO evewidencjaDAO;
    private EVatwpis eVatwpis;
    private static String wielkoscopisuewidencji;
    /* Rozliczenia vat*/
    /*Środki trwałe*/
    @Inject private SrodekTrw selectedSTR;       
    /*Środki trwałe*/
    private boolean pokazSTR;
    private boolean pokazEST;//pokazuje wykaz srodkow dla sprzedazy
    private String test;
   
    private String nazwaSTR;
    private String dataPrzSTR;
    private String symbolKST;
    private String stawkaKST;
    private String typKST;
    private Double umorzeniepoczatkowe;
    @Inject private Srodkikst srodekkategoria;
    @Inject private Srodkikst srodekkategoriawynik;
   
    //edycja platnosci
    @Inject private Rozrachunek rozrachunek;
   //automatyczne ksiegowanie Storna
    @Inject private StornoDokDAO stornoDokDAO;
    private boolean rozliczony;
    @Inject private StornoDok stornoDok;
    @Inject private RodzajedokDAO rodzajedokDAO;
    private List<Rodzajedok> rodzajedokKlienta;
    //przechowuje ostatni dokumnet
    private String typdokumentu;
    //przechowuje wprowadzanego podatnika;
    private Podatnik podX;
    private boolean opodatkowanieryczalt;
    //pobieram wykaz KŚT
    @Inject private SrodkikstDAO srodkikstDAO;
    private static String przechowajdatejakdodaje;
    @Inject OstatnidokumentDAO ostatnidokumentDAO;
    @Inject WpisDAO wpisDAO;
    private List opisypkpir;
    
    private List<String> listamiesiecyewidencjavat;
    @Inject private Mce mce;
    /**
     * Lista gdzie przechowywane są wartości netto i opis kolumny wporwadzone w formularzy na stronie add_wiad.xhtml
     */
    List<KwotaKolumna> nettokolumna;
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
    
     private List<Inwestycje> inwestycje;
     @Inject private InwestycjeDAO inwestycjeDAO;

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
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    
     public void liczbaw() {
        KwotaKolumna p = new KwotaKolumna();
        p.setNetto(0.0);
        p.setNazwakolumny("nie ma");
        nettokolumna.add(p);
        liczbawierszy++;
    }
 
    @PostConstruct
    private void init(){
        rodzajedokKlienta = new ArrayList<>();
        Wpis wpistmp = wpisView.findWpisX();
        try{
        inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu());
        String pod = wpistmp.getPodatnikWpisu();
        podX = podatnikDAO.find(pod);
        opisypkpir.addAll(podX.getOpisypkpir());
        ArrayList<Rodzajedok> rodzajedokumentow = (ArrayList<Rodzajedok>) podX.getDokumentyksiegowe();
        Collections.sort(rodzajedokumentow,new Rodzajedokcomparator());
        rodzajedokKlienta.addAll(rodzajedokumentow);
        opodatkowanieryczalt = podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size()-1).getParametr().contains("bez VAT");
        if(podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size()-1).getParametr().contains("VAT")){
            selDokument.setDokumentProsty(true);
        }
        } catch (Exception e){
            String pod = "GRZELCZYK";
            podX = podatnikDAO.find(pod);
            rodzajedokKlienta.addAll(podX.getDokumentyksiegowe());
            opodatkowanieryczalt = podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size()-1).getParametr().contains("bez VAT");
        }
        //pobranie ostatniego dokumentu
        wysDokument = ostatnidokumentDAO.pobierz(wpistmp.getWprowadzil());
        try{
        selDokument.setVatR(wpistmp.getRokWpisu().toString());
        selDokument.setVatM(wpistmp.getMiesiacWpisu());
        } catch (Exception e){}
        ukrocmiesiace();
        
    }
    
    private void ukrocmiesiace(){ 
        int index = 0;
        List<String> listatmp = mce.getMceList();
        String biezacymiesiac = wpisView.getMiesiacWpisu();
        for(String p : listatmp){
            if(p.equals(biezacymiesiac)){
                listamiesiecyewidencjavat.add(p);
                 if(p.equals("12")&&(listamiesiecyewidencjavat.size()==1)){
                listamiesiecyewidencjavat.add("01");
            }
            } else {
            if(listamiesiecyewidencjavat.size()>0&&listamiesiecyewidencjavat.size()<2){
                listamiesiecyewidencjavat.add(p);
            }
            }
        }
    }
    
    //kopiuje ostatni dokument celem wykorzystania przy wpisie
    public void skopiujdokument(){
         try{
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            selDokument.setNetto(0.0);
        } catch (Exception e){}
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }
    
    //edytuje ostatni dokument celem wykorzystania przy wpisie
    public void edytujdokument(){
         try{
            selDokument = ostatnidokumentDAO.pobierz(wpisView.getWprowadzil().getLogin());
            typdokumentu = selDokument.getTypdokumentu();
            dokDAO.destroy(selDokument);
        } catch (Exception e){}
        RequestContext.getCurrentInstance().update("dodWiad:wprowadzanie");
    }
    
    
    /**
     * wybiera odpowiedni zestaw kolumn pkpir do podpiecia w zaleznosci od tego
     * czy to transakcja zakupu czy sprzedazy
     */
     public void podepnijListe() {
        try{
        pkpirLista.getChildren().clear();
        } catch (Exception egf){
        pkpirLista = new HtmlSelectOneMenu();
        }
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        Iterator itd;
        itd = rodzajedokKlienta.iterator();
        String transakcjiRodzaj="";
            while (itd.hasNext()){
            Rodzajedok temp = (Rodzajedok) itd.next();
            if(temp.getSkrot().equals(skrot)){
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
                wygenerujSTRKolumne();
                break;
            case "srodek trw sprzedaz":
                dopobrania = kolumna.getKolumnSTsprz();
                setPokazEST(true);
                RequestContext.getCurrentInstance().update("dodWiad:panelewidencji");
                break;
            case "import usług":
                dopobrania = kolumna.getKolumnKoszty();
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
            case "usługi poza ter.":
                dopobrania = kolumna.getKolumnPrzychody();
                break;
            default:
                dopobrania = kolumna.getKolumnPrzychody();
                break;
        }
        kolumny = dopobrania;
        /*dodajemy na poczatek zwyczajawa kolumne klienta*/
        if(przekazKontr.getPkpirKolumna()!=null){
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
        podepnijEwidencjeVat(transakcjiRodzaj);
    }

    public void podepnijEwidencjeVat(String transakcjiRodzaj) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:dokumentprosty");
        try {
            if(skrot.equals("on")){}
        } catch (Exception e) {
        if(opodatkowanieryczalt==false){
        /*wyswietlamy ewidencje VAT*/
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elContext = facesCtx.getELContext();
        List opisewidencji = new ArrayList();
        switch (transakcjiRodzaj) {
            case ("zakup"):
                opisewidencji = evat.getZakupVList();
                break;
            case("srodek trw"):
                opisewidencji = evat.getSrodkitrwaleVList();
                break;
            case("srodek trw sprzedaz"):
                opisewidencji = evat.getSprzedazVList();
                break;
            case("inwestycja"):
                opisewidencji = evat.getSrodkitrwaleVList();
                break;
            case("WDT"):
                opisewidencji = evat.getWdtVList();
                break;
            case("WNT"):
                opisewidencji = evat.getWntVList();
                break;
            case("import usług"):
                opisewidencji = evat.getImportuslugList();
                break;
            case "usługi poza ter.":
                opisewidencji = evat.getUslugiPTK();
                break;
            default:
                opisewidencji = evat.getSprzedazVList();
        }
        
        grid1 = getGrid1();
        grid1.getChildren().clear();
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("dodWiad:grid1");
        Iterator itx;
        List naglowekewidencji = evat.getNaglowekVList();
        itx = naglowekewidencji.iterator();
        //dodawanie naglowka: rodzaj ewidencji atto vat op/zw
        while (itx.hasNext()) {
            String tmp = (String) itx.next();
            if ((transakcjiRodzaj.equals("sprzedaz")||transakcjiRodzaj.equals("srodek trw sprzedaz")||transakcjiRodzaj.equals("ryczałt")) && tmp.equals("op/zw")) {
            } else {
                HtmlOutputText ot = new HtmlOutputText();
                ot.setValue((String) tmp);
                grid1.getChildren().add(ot);
            }

        }
        //usuwanie ostatniego opisu jak jest sprzedaz

        Integer tmpw = grid1.getChildCount();
        wielkoscopisuewidencji = tmpw.toString();
        ExpressionFactory ef = ExpressionFactory.newInstance();
        itx = opisewidencji.iterator();
        int i = 1;
        while (itx.hasNext()) {
            String poz = (String) itx.next();
            HtmlOutputText otX = new HtmlOutputText();
            otX.setValue(poz);
            //to jest potrzebne zeby wyswietlic ostatnio wpisany dokumnet add_wiad.html
            //to jest problem przy ViewScoped jak nie beda wyzrowane opisy
            if (opis1 == null) {
                setOpis1(poz);
            } else if (opis2 == null) {
                setOpis2(poz);
            } else if (opis3 == null) {
                setOpis3(poz);
            } else if (opis4 == null) {
                setOpis4(poz);
            } else {
                setOpis5(poz);
            }
            final String bindingA = "#{DokumentView.opis" + i + "}";
            ValueExpression ve = ef.createValueExpression(elContext, bindingA, String.class);
            otX.setValueExpression("value", ve);
            grid1.getChildren().add(otX);
            InputNumber ew = new InputNumber();
            final String binding = "#{DokumentView.netto" + i + "}";
            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
            ew.setValueExpression("value", ve2);
            ew.setSymbol(" zł");
            ew.setSymbolPosition("s");
            ew.setDecimalPlaces(".");
            ew.setThousandSeparator(" ");
            ew.setDecimalPlaces("2");
            ew.setMinValue("-10000000");
            String defX = "updatesuma("+i+");";
            ew.setOnblur(defX);
            String lab1 = "netto"+i;
            ew.setId(lab1);
            String klasa ="nettorow";
            ew.setStyleClass(klasa);
            grid1.getChildren().add(ew);
            HtmlInputText ewX = new HtmlInputText();
            final String bindingX = "#{DokumentView.vat" + i + "S}";
            ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
            ewX.setValueExpression("value", ve2X);
            String lab2 = "vat"+i;
            ewX.setId(lab2);
            String def = "updatevat("+i+");";
            ewX.setOnblur(def);
            grid1.getChildren().add(ewX);
            HtmlInputText ewY = new HtmlInputText();
            String lab3 = "brutto"+i;
            ewY.setId(lab3);
            grid1.getChildren().add(ewY);
            if (transakcjiRodzaj.equals("zakup") || transakcjiRodzaj.equals("srodek trw")) {
                UISelectItems ulista = new UISelectItems();
                List valueList = new ArrayList();
                SelectItem selectItem = new SelectItem("sprz.op", "sprz.op");
                valueList.add(selectItem);
                selectItem = new SelectItem("sprzed.op.izw.", "sprzed.op.izw.");
                valueList.add(selectItem);
                ulista.setValue(valueList);
                final String bindingY = "#{DokumentView.opizw}";
                ValueExpression ve2Y = ef.createValueExpression(elContext, bindingY, String.class);
                HtmlSelectOneMenu htmlSelectOneMenu = new HtmlSelectOneMenu();
                htmlSelectOneMenu.setValueExpression("value", ve2Y);
                htmlSelectOneMenu.setStyle("min-width: 150px");
                htmlSelectOneMenu.getChildren().add(ulista);
                grid1.getChildren().add(htmlSelectOneMenu);
            }
            i++;
        }
        //to jest potrzebne zeby wyswietlic ostatnio wpisany dokumnet add_wiad.html
        eVatOpisDAO.clear();
        EVatOpis eVO = new EVatOpis(wpisView.getWprowadzil().getLogin(),opis1, opis2, opis3, opis4, opis5);
        try {
            eVatOpisDAO.dodaj(eVO);
        } catch (Exception ei){
            eVatOpisDAO.edit(eVO);
        }
        opis1 = null;
        opis2 = null;
        opis3 = null;
        opis4 = null;
        opis5 = null;
        netto1 = 0;
        netto2 = 0;
        netto3 = 0;
        netto4 = 0;
        netto5 = 0;
        vat1 = 0;
        vat2 = 0;
        vat3 = 0;
        vat4 = 0;
        vat5 = 0;
        
        RequestContext.getCurrentInstance().update("dodWiad:grid1");
        }
        }
    }
    
    public void wygenerujnumerkolejny(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        String nowynumer = "";
        String pod = wpisView.findWpisX().getPodatnikWpisu();
        Podatnik podX = podatnikDAO.find(pod);
        List<Rodzajedok> listaD = podX.getDokumentyksiegowe();
        Rodzajedok rodzajdok = new Rodzajedok();
        for (Rodzajedok p : listaD){
            if(p.getSkrot().equals(skrot)){
                rodzajdok = p;
                break;
            }
        }
        String wzorzec = rodzajdok.getWzorzec();
        //odnajdywanie podzielnika;
        String separator = null;
                if(wzorzec.contains("/")){
                    separator = "/";
                }
         String[] elementy;
         try{
         elementy = wzorzec.split(separator);
         Dok ostatnidokument = dokDAO.find(skrot, wpisView.findWpisX().getPodatnikWpisu() ,  wpisView.findWpisX().getRokWpisu());
         String[] elementyold;
         elementyold  = ostatnidokument.getNrWlDk().split(separator);
         for(int i = 0; i<elementy.length;i++){
             String typ = elementy[i];
            switch (typ) {
                case "n":
                    String tmp = elementyold[i];
                    Integer tmpI = Integer.parseInt(tmp);
                    tmpI++;
                    nowynumer = nowynumer.concat(tmpI.toString()).concat(separator);
                    break;
                case "m":
                    nowynumer = nowynumer.concat( wpisView.findWpisX().getMiesiacWpisu()).concat(separator);
                    break;
                case "r":
                    nowynumer = nowynumer.concat(wpisView.findWpisX().getRokWpisu().toString()).concat(separator);
                    break;
                    //to jest wlasna wstawka typu FVZ
                case "s":
                    nowynumer = nowynumer.concat(elementyold[i]).concat(separator);
                    break;
            }
         }
         if(nowynumer.endsWith(separator)){
          nowynumer = nowynumer.substring(0,nowynumer.lastIndexOf(separator));
         }} catch (Exception e){
            nowynumer = wzorzec;
         }
         if(!nowynumer.equals("")){
            selDokument.setNrWlDk(nowynumer);
         } else {
             selDokument.setNrWlDk("");
         }
         renderujwyszukiwarke(rodzajdok);
    }

    public boolean renderujwysz;

    public boolean isRenderujwysz() {
        return renderujwysz;
    }

    public void setRenderujwysz(boolean renderujwysz) {
        this.renderujwysz = renderujwysz;
    }
    
    private void renderujwyszukiwarke(Rodzajedok rodzajdok){
        if(rodzajdok.getSkrot().equals("OT")){
            setRenderujwysz(true);
        } else {
            setRenderujwysz(false);
        }
        RequestContext.getCurrentInstance().update("dodWiad:panelwyszukiwarki");
    }
    
    public void wygenerujSTRKolumne() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elContext = facesCtx.getELContext();
        grid3 = getGrid3();
        grid3.getChildren().clear();
        RequestContext.getCurrentInstance().update("dodWiad:grid3");
        ExpressionFactory ef = ExpressionFactory.newInstance();
            HtmlOutputText ot = new HtmlOutputText();
            ot.setValue("nazwa Srodka");
            grid3.getChildren().add(ot);
            HtmlInputText ew = new HtmlInputText();
            final String binding = "#{DokumentView.nazwaSTR}";
            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
            ew.setValueExpression("value", ve2);
            ew.setId("nazwasrodka");
            ew.setAccesskey("t");
            grid3.getChildren().add(ew);
            
            HtmlOutputText ot1 = new HtmlOutputText();
            ot1.setValue("data przyjecia");
            grid3.getChildren().add(ot1);
            HtmlInputText ew1 = new HtmlInputText();
            final String binding1 = "#{DokumentView.dataPrzSTR}";
            ValueExpression ve1 = ef.createValueExpression(elContext, binding1, String.class);
            ew1.setValueExpression("value", ve1);
            ew1.setId("dataprz");
            ew1.setOnblur("ustawDateSrodekTrw();");
            grid3.getChildren().add(ew1);
            
            HtmlOutputText ot3 = new HtmlOutputText();
            ot3.setValue("symbol KST");
            grid3.getChildren().add(ot3);
            HtmlInputText ew3 = new HtmlInputText();
            final String binding3 = "#{DokumentView.symbolKST}";
            ValueExpression ve3 = ef.createValueExpression(elContext, binding3, String.class);
            ew3.setValueExpression("value", ve3);
            ew3.setId("symbolKST");
            grid3.getChildren().add(ew3);
            
//            HtmlOutputText ot4 = new HtmlOutputText();
//            ot4.setValue("wybierz kategoria");
//            grid3.getChildren().add(ot4);
            
//            "id="acForce" value="#{DokumentView.selDokument.kontr}" completeMethod="#{KlView.complete}"
//                                    var="p" itemLabel="#{p.npelna}" itemValue="#{p}" converter="KlientConv" 
//                                    minQueryLength="3" maxResults="10" maxlength="10" converterMessage="Nieudana konwersja Klient"  forceSelection="true" 
//                                    effect="clip"  binding="#{DokumentView.kontrahentNazwa}" valueChangeListener="#{DokumentView.przekazKontrahenta}" 
//                                    required="true" requiredMessage="Pole klienta nie może byc puste" queryDelay="100" onblur="validateK()">
//             "                               
//            AutoComplete autoComplete = new AutoComplete();
//            final String bindingY = "#{DokumentView.srodekkategoria}";
//            ValueExpression ve2Y = ef.createValueExpression(elContext, bindingY, String.class);
//            autoComplete.setValueExpression("value", ve2Y);
//            autoComplete.setVar("p");
//            autoComplete.setItemLabel("#{p.nazwa}");
//            autoComplete.setItemValue("#{p.nazwa}");
//            autoComplete.setMinQueryLength(3);
//            FacesContext context = FacesContext.getCurrentInstance();
//            MethodExpression actionListener = context.getApplication().getExpressionFactory()
//    .createMethodExpression(context.getELContext(), "#{srodkikstView.complete}", null, new Class[] {ActionEvent.class});
//            autoComplete.setCompleteMethod(actionListener);
//            grid3.getChildren().add(autoComplete);
//            
            
            HtmlOutputText ot4 = new HtmlOutputText();
            ot4.setValue("stawka amort");
            grid3.getChildren().add(ot4);
            HtmlInputText ew4 = new HtmlInputText();
            final String binding4 = "#{DokumentView.stawkaKST}";
            ValueExpression ve4 = ef.createValueExpression(elContext, binding4, String.class);
            ew4.setValueExpression("value", ve4);
            ew4.setId("stawkaKST");
            grid3.getChildren().add(ew4);
            
            HtmlOutputText ot5 = new HtmlOutputText();
            ot5.setValue("dotychczasowe umorzenie");
            grid3.getChildren().add(ot5);
            InputNumber ew5 = new InputNumber();
            final String binding5 = "#{DokumentView.umorzeniepoczatkowe}";
            ValueExpression ve5 = ef.createValueExpression(elContext, binding5, String.class);
            ew5.setValueExpression("value", ve5);
            ew5.setSymbol(" zł");
            ew5.setSymbolPosition("s");
            ew5.setDecimalPlaces(".");
            ew5.setThousandSeparator(" ");
            ew5.setDecimalPlaces("2");
            ew5.setValue(0);
            grid3.getChildren().add(ew5);
            umorzeniepoczatkowe = 0.0;
            
            UISelectItems ulistaX = new UISelectItems();
            List valueListX = new ArrayList();
            SelectItem selectItem = new SelectItem("srodek trw.", "srodek trw.");
            valueListX.add(selectItem);
            selectItem = new SelectItem("wyposazenie", "wyposazenie");
            valueListX.add(selectItem);
            ulistaX.setValue(valueListX);
            final String bindingX = "#{DokumentView.typKST}";
            ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
            HtmlSelectOneMenu htmlSelectOneMenuX = new HtmlSelectOneMenu();
            htmlSelectOneMenuX.setValueExpression("value", ve2X);
            htmlSelectOneMenuX.setStyle("min-width: 150px");
            htmlSelectOneMenuX.getChildren().add(ulistaX);
            grid3.getChildren().add(htmlSelectOneMenuX);
            
        RequestContext.getCurrentInstance().update("dodWiad:grid3");
    }
/**
 * Generuje nową dodatkową kolumnę
 */
    public void wygenerujNowaKolumnePkpir() {
        /*wyswietlamy ewidencje VAT*/
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nazwadok = params.get("dodWiad:rodzajTrans");
        Rodzajedok rodzaj = rodzajedokDAO.find(nazwadok);
        String transakcjiRodzaj = rodzaj.getRodzajtransakcji();
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
                wygenerujSTRKolumne();
                break;
            case "inwestycja":
                dopobrania = kolumna.getKolumnST();
                break;
            case "import usług":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            case "WDT":
                dopobrania = kolumna.getKolumnPrzychody();
                break;
            case "WNT":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            default:
                dopobrania = kolumna.getKolumnPrzychody();
                break;
        }
        Iterator it;
        it = dopobrania.iterator();
        while (it.hasNext()) {
            String poz = (String) it.next();
            SelectItem selectItem = new SelectItem(poz, poz);
            valueList.add(selectItem);
        }
        ulista.setValue(valueList);
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        ELContext elContext = facesCtx.getELContext();
        grid2 = getGrid2();
        grid2.getChildren().clear();
        ExpressionFactory ef = ExpressionFactory.newInstance();
        RequestContext.getCurrentInstance().update("dodWiad:grid2");
            InputNumber ew = new InputNumber();
            final String binding = "#{DokumentView.nettopkpir1}";
            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
            ew.setValueExpression("value", ve2); 
            ew.setStyle("width: 120px");
            ew.setId("kwotaPkpirX");
            AjaxBehavior dragStart = new AjaxBehavior();
            dragStart.setGlobal(false);
            MethodExpression me = ef.createMethodExpression(elContext, "#{DokumentView.przeniesKwotaDoNettoX}", String.class, new Class[0]);
            dragStart.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(me,me));
            dragStart.setUpdate(":dodWiad:grid1");
            ew.addClientBehavior("blur", dragStart);
            ew.setSymbol(" zł");
            ew.setSymbolPosition("s");
            ew.setDecimalSeparator(".");
            ew.setDecimalPlaces("2");
            ew.setThousandSeparator(" ");
            ew.setMinValue("-10000000");
            grid2.getChildren().add(ew);
        final String bindingX = "#{DokumentView.opiskolumny1}";
        ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
        HtmlSelectOneMenu htmlSelectOneMenu = new HtmlSelectOneMenu();
        htmlSelectOneMenu.setValueExpression("value", ve2X);
        htmlSelectOneMenu.setStyle("min-width: 150px");
        htmlSelectOneMenu.getChildren().add(ulista);
        htmlSelectOneMenu.setOnblur("updatesum();");
        grid2.getChildren().add(htmlSelectOneMenu);
        RequestContext.getCurrentInstance().update("dodWiad:grid2");
    }

    public void przeniesKwotaDoNetto() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Collection<String> klucze = params.keySet();
        ArrayList<String> kluczeprzebrane = new ArrayList<>();
        for (String p : klucze) {
            if (p.contains("dodWiad:repeat:")&&p.contains("hinput")) {
                kluczeprzebrane.add(p);
            }
        }
        for (String p : kluczeprzebrane) {
            String tmp = params.get(p);
            tmp = tmp.replace(",", ".");
            if (netto1 == 0.0) {
                netto1 = Double.parseDouble(tmp);
            } else {
                netto1 = netto1 + Double.parseDouble(tmp);
            }
            BigDecimal tmp1 = BigDecimal.valueOf(netto1);
            tmp1 = tmp1.multiply(BigDecimal.valueOf(0.23));
            tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
            String transakcja = params.get("dodWiad:rodzajTrans");
            if (transakcja.equals("WDT") || transakcja.equals("UPTK")) {
                vat1 = 0.0;
            } else {
                vat1 = Double.parseDouble(tmp1.toString());
            }
        }
         RequestContext.getCurrentInstance().update("dodWiad:grid1");
            //daje platnosc gotowka domyslnie
            selDokument.setRozliczony(true);
            skopiujdoTerminuPlatnosci();
            RequestContext.getCurrentInstance().update("dodWiad:rozliczony");
    }

 

    /**NE
     * zmienia wlasciwosci pol wprowadzajacych dane kontrahenta
     */
    public void dokumentProstuSchowajEwidencje() {
        netto1 = 0;
        vat1 = 0;
        selDokument.setEwidencjaVAT(null);
        grid1 = getGrid1();
        grid1.getChildren().clear();
        RequestContext.getCurrentInstance().update("dodWiad:grid1");
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
    public void dodaj() {
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
            if((!podtmp.getPodatekdochodowy().get(podtmp.getPodatekdochodowy().size()-1).getParametr().contains("bez VAT"))&&(selDokument.isDokumentProsty()==false)){
            ArrayList<Evewidencja> ew = new ArrayList<>();
            ew.addAll(evewidencjaDAO.findAll());
            Collections.sort(ew,new Evewidencjacomparator());
            EVatOpis eVO = (EVatOpis) eVatOpisDAO.findS(wpisView.getWprowadzil().getLogin());
            List<String> pobierzOpisy = new ArrayList<>();
            pobierzOpisy.add(eVO.getOpis1());
            pobierzOpisy.add(eVO.getOpis2());
            pobierzOpisy.add(eVO.getOpis3());
            pobierzOpisy.add(eVO.getOpis4());
            pobierzOpisy.add(eVO.getOpis5());
            List<Double> pobierzNetto = new ArrayList<>();
            
            pobierzNetto.add(netto1);
            pobierzNetto.add(netto2);
            pobierzNetto.add(netto3);
            pobierzNetto.add(netto4);
            pobierzNetto.add(netto5);
            pobierzVat.add(extractDouble(vat1S));
            try{
            pobierzVat.add(extractDouble(vat2S));
            pobierzVat.add(extractDouble(vat3S));
            pobierzVat.add(extractDouble(vat4S));
            pobierzVat.add(extractDouble(vat5S));
            } catch (Exception e){}
            List<EVatwpis> el = new ArrayList<>();
            
            int i = 0;
            int rozmiar = evewidencjaDAO.findAll().size();
            while (i < rozmiar) {
                int j = 0;
                while (j < 5 && (pobierzOpisy.get(j) != null)) {
                    String op = pobierzOpisy.get(j);
                    String naz = ew.get(i).getNazwa();
                    if (naz.equals(op)) {
                        eVidencja = ew.get(i);
                        eVatwpis = new EVatwpis();
                        eVatwpis.setEwidencja(eVidencja);
                        eVatwpis.setNetto(pobierzNetto.get(j));
                        eVatwpis.setVat(pobierzVat.get(j));
                        eVatwpis.setEstawka(opizw);
                        el.add(eVatwpis);
                        eVidencja = null;
                    }
                    j++;
                }
                i++;
            }
            
            if (opodatkowanieryczalt==true) {
                selDokument.setEwidencjaVAT(null);
            } else if (!selDokument.isDokumentProsty())  {
                selDokument.setEwidencjaVAT(el);
            } else {
                selDokument.setEwidencjaVAT(null);
            }
            }
            selDokument.setStatus("bufor");
            selDokument.setTypdokumentu(typdokumentu);
            Iterator itd;
            itd = rodzajedokKlienta.iterator();
            String transakcjiRodzaj="";
            while (itd.hasNext()){
            Rodzajedok temp = (Rodzajedok) itd.next();
            if(temp.getSkrot().equals(typdokumentu)){
                transakcjiRodzaj = temp.getRodzajtransakcji();
                break;
            }
        }
            selDokument.setRodzTrans(transakcjiRodzaj);
            selDokument.setOpis(selDokument.getOpis().toLowerCase());
            //obliczanie netto
//            List<KwotaKolumna> pobranekwotokolumny = new ArrayList<>();
//            KwotaKolumna element = new KwotaKolumna();
//            KwotaKolumna element1 = new KwotaKolumna();
//            element.setNetto(nettopkpir0);
//            element.setVat(vatpkpir0);
//            element.setBrutto(nettopkpir0+vatpkpir0);
//            element.setNazwakolumny(opiskolumny0);
//            pobranekwotokolumny.add(element);
//            try{
//            element1.setNetto(nettopkpir1);
//            element1.setVat(vatpkpir1);
//            element1.setBrutto(nettopkpir1+vatpkpir1);
//            element1.setNazwakolumny(opiskolumny1);
//            pobranekwotokolumny.add(element1);
//            } catch (Exception e){}
            selDokument.setListakwot(nettokolumna);
            selDokument.setNetto(0.0);
            for(KwotaKolumna p : nettokolumna){
                selDokument.setNetto(selDokument.getNetto()+p.getNetto());
            }
            //koniec obliczania netto
            dodajdatydlaStorno();
            //dodaje zaplate faktury gdy faktura jest uregulowana
            Double kwotavat = 0.0;
            try{
                for(Double p: pobierzVat){
                    kwotavat = kwotavat + p;
                }
            } catch (Exception ex){}
            Double kwota = 0.0;
            for (KwotaKolumna p : nettokolumna){
                kwota = kwota + p.getNetto();
            }
            kwota = kwota + kwotavat;
            kwota = new BigDecimal(kwota).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
            if(selDokument.getRozliczony()==true){
                Rozrachunek rozrachunekx = new Rozrachunek(selDokument.getTerminPlatnosci(), kwota, 0.0, selDokument.getWprowadzil(),new Date());
                ArrayList<Rozrachunek> lista = new ArrayList<>();
                lista.add(rozrachunekx);
                selDokument.setRozrachunki(lista);
            }
            selDokument.setBrutto(kwota);
            selDokument.setUsunpozornie(false);
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
                if(!probsymbolu.equals("wybierz")){
                    aktualizujInwestycje(selDokument);
                }
            } catch (Exception e){
            }
            wysDokument = new Dok();
            wysDokument = ostatnidokumentDAO.pobierz(selDokument.getWprowadzil());
            liczbawierszy = 0;
            RequestContext.getCurrentInstance().update("zobWiad:ostatniUzytkownik");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy dokument zachowany" +selDokument, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
            /**
             * resetowanie pola do wpisywania kwoty netto
             */
            nettokolumna.clear();
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd. Dokument nie został zaksiegowany " + e.getStackTrace().toString(),null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
         } 
        //robienie srodkow trwalych
         if(stawkaKST!=null){
            try {
                   selectedSTR.setNetto(selDokument.getNetto());
                   BigDecimal tmp1 = BigDecimal.valueOf(selDokument.getNetto());
                   tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
                   tmp1 = tmp1.multiply(BigDecimal.valueOf(0.23));
                   tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
                   Double vat = Double.parseDouble(tmp1.toString());
                   selectedSTR.setVat(vat);
                   selectedSTR.setDatazak(selDokument.getDataWyst());
                   selectedSTR.setDataprzek(dataPrzSTR);
                   selectedSTR.setStawka(Double.parseDouble(stawkaKST));
                   selectedSTR.setKst(symbolKST);
                   selectedSTR.setNazwa(nazwaSTR.toLowerCase());
                   selectedSTR.setTyp(typKST);
                   selectedSTR.setUmorzeniepoczatkowe(umorzeniepoczatkowe);
                   selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
                   selectedSTR.setNrwldokzak(selDokument.getNrWlDk());
                   dodajSTR();
                   
            } catch (Exception e){
                
            }
         }
            setPokazSTR(false);
            grid1 = getGrid1();
            grid1.getChildren().clear();
            grid2 = getGrid2();
            grid2.getChildren().clear();
            grid3 = getGrid3();
            grid3.getChildren().clear();
            selDokument = new Dok();
            setRenderujwysz(false);
            RequestContext.getCurrentInstance().update("@form");
    }
    
    private Double extractDouble(String wiersz){
        String prices = wiersz.replaceAll("\\s","");
        Pattern p = Pattern.compile("(-?(\\d+(?:\\.\\d+)))");
        Matcher m = p.matcher(prices);
        while (m.find()) {
            return Double.parseDouble(m.group());
            }
         return 0.0;
    }
    
    //dodaje wyliczone daty platnosci dla obliczenia pozniej czy trzeba stornowac
    public void dodajdatydlaStorno() throws ParseException{
        String data;
            switch (wpisView.getMiesiacWpisu()) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-30";
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
       if(roznicaDni(datawystawienia,terminplatnosci)==true){
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
    
     private boolean roznicaDni(Date date_od, Date date_do){ 
                long x=date_do.getTime(); 
                long y=date_od.getTime(); 
                long wynik=Math.abs(x-y); 
                wynik=wynik/(1000*60*60*24); 
                System.out.println("Roznica miedzy datami to "+wynik+" dni..."); 
                if(wynik<=61){
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
            while(itx.hasNext()){
                Amodok tmp = (Amodok) itx.next();
                Integer mctmp = tmp.getAmodokPK().getMc();
                String mc = Mce.getMapamcy().get(mctmp);
                Integer rok = tmp.getAmodokPK().getRok();
                if(wpisView.getRokWpisu().equals(rok)&&wpisView.getMiesiacWpisu().equals(mc)){
                    amodok = tmp;
                    break;
                }
                amodokPoprzedni = tmp;
            }

            try {
                    boolean temp = amodokPoprzedni.getZaksiegowane();
                    List<Umorzenie> tempX = amodokPoprzedni.getUmorzenia();
            } catch (Exception e){}
            
        try {
        
            if(amodokPoprzedni!=null){
                if(amodokPoprzedni.getZaksiegowane()!=true&&amodokPoprzedni.getUmorzenia().size()>0){
                    throw new Exception();
                }
            }
         
            List<Umorzenie> umorzenia = new ArrayList<>();
            umorzenia.addAll(amodok.getUmorzenia());
            Iterator it;
            it = umorzenia.iterator();
            while(it.hasNext()){
                Umorzenie tmp = (Umorzenie) it.next();
                kwotaumorzenia = kwotaumorzenia + tmp.getKwota().doubleValue();
            }
     
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
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-30";
                    break;
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("","dowód wewnętrzny"));
            selDokument.setRodzTrans("amortyzacja");
            selDokument.setTypdokumentu("AMO");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu().toString());
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
            if(selDokument.getNetto()>0){
            dokDAO.dodaj(selDokument);
            String wiadomosc = "Nowy dokument umorzenia zachowany: "+selDokument.getPkpirR()+"/"+selDokument.getPkpirM()+" kwota: "+selDokument.getNetto();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,wiadomosc, selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            amodok.setZaksiegowane(true);
            amoDokDAO.edit(amodok);
            } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota umorzenia wynosi 0zł. Dokument nie został zaksiegowany", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);    
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd. Nie ma odpisu w porzednim miesiącu!!","");
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
            while(itx.hasNext()){
                Dok tmpx = (Dok) itx.next();
                kwotastorno = kwotastorno+tmpx.getStorno().get(tmpx.getStorno().size()-1).getKwotawplacona();
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
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-30";
                    break;
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("111111111","wlasny"));
            selDokument.setRodzTrans("storno niezapłaconych faktur");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu().toString());
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
            if(selDokument.getNetto()!=0){
            sprawdzCzyNieDuplikat(selDokument);
            dokDAO.dodaj(selDokument);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy dokument storno zachowany", selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            tmp.setZaksiegowane(true);
            stornoDokDAO.edit(tmp);
            } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Kwota storno wynosi 0zł. Dokument nie został zaksiegowany", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);    
            }
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd!!","");
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
    
    
    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception{
        Dok tmp = dokDAO.znajdzDuplikat(selD);
        if (tmp != null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument dla tego klienta, o takim numerze i kwocie jest juz zaksiegowany", null);
            FacesContext.getCurrentInstance().addMessage("wprowadzenieNowego", msg);
            RequestContext.getCurrentInstance().update("messageserror");
            throw new Exception();
        } else {
            System.out.println("Nie znaleziono duplikatu");
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
        if(liczbawierszy<1){
        KwotaKolumna nowa = new KwotaKolumna();
        nowa.setNetto(0.0);
        nowa.setNazwakolumny("");
        nettokolumna.add(nowa);
        RequestContext.getCurrentInstance().update("dodWiad:panel");
        liczbawierszy++;
        }
    }
   
    //przekazuje zeby pobrac jego domyslna kolumne do listy kolumn
    public void przekazKontrahenta(ValueChangeEvent e) throws Exception {
        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
        przekazKontr = (Klienci) anAutoComplete.getValue();
        if(przekazKontr.getNpelna().equals("nowy klient")){
            FacesContext.getCurrentInstance().getExternalContext().redirect("klienci.xhtml");
        }
         if(podX.getPodatekdochodowy().get(podX.getPodatekdochodowy().size()-1).getParametr().contains("VAT")){
            selDokument.setDokumentProsty(true);
            RequestContext.getCurrentInstance().update("dodWiad:dokumentprosty");
        }
    }
    
     public void przekazKontrahentaA(AjaxBehaviorEvent e) throws Exception {
        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
        String aSelection = anAutoComplete.getValue().toString();
        if(aSelection.equals("nowy klient")){
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
       Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu(),"dodWiad:mess_add");
       
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
    
     private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
    }

    public String aktualizujPop() throws IOException {
       return "/manager/managerTerminy.xhtml";
        
    }

    
    public void dodajSTR(){
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
    
    public void skopiujdoTerminuPlatnosci(){
//        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        selDokument.setTerminPlatnosci(params.get("dodWiad:dataPole"));
//        RequestContext.getCurrentInstance().update("dodWiad:dataTPole");
    }
    
    public void zaksiegujPlatnosc(ActionEvent e){
        //pobieranie daty zeby zobaczyc czy nie ma juz dokumentu storno z ta data 
        String data = rozrachunek.getDataplatnosci();
        Integer r = Integer.parseInt(data.substring(0,4));
        String m = data.substring(5, 7);
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            stornoDok = stornoDokDAO.find(r, m, podatnik);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje dokument storno. Za późno wprowadzasz te płatność", stornoDok.getMc().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().update("form:messages");
        } catch (Exception ec){
        ArrayList<Rozrachunek> lista = new ArrayList<>();
        double zostalo = 0;
        double kwota = 0;
        try{
        lista.addAll(selDokument.getRozrachunki());
        zostalo = lista.get(lista.size()-1).getDorozliczenia();
        } catch (Exception ee){}
        if(zostalo==0){
                try{
                kwota = - selDokument.getBrutto();
                } catch (Exception el){}
        } else {
            kwota = zostalo;
        }
        int pozostalo = (int) (kwota+rozrachunek.getKwotawplacona());
        rozrachunek.setDorozliczenia(kwota+rozrachunek.getKwotawplacona());
         HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
        rozrachunek.setWprowadzil(principal.getName());
        rozrachunek.setDatawprowadzenia(new Date());
        lista.add(rozrachunek);
        if(pozostalo==0){
            selDokument.setRozliczony(true);
        }
        selDokument.setRozrachunki(lista);
        try{
        dokDAO.edit(selDokument);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Płatność zachowana" +selDokument, null);
         FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception ex) {
            System.out.println(ex.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Płatność niezachowana " + ex.getStackTrace().toString(),null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    }
    }
     public void usunostatniRozrachunek(ActionEvent e){
        ArrayList<Rozrachunek> lista = new ArrayList<>();
        try{
        lista.addAll(selDokument.getRozrachunki());
        } catch (Exception ee){}
        lista.remove(lista.get(lista.size()-1));
        selDokument.setRozrachunki(lista);
        dokDAO.edit(selDokument);
     }
    
   public void skopiujSTR(){
       Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
       String nazwa = params.get("dodWiad:acForce1_input");
       if(!nazwa.equals("")){
       try{
       srodekkategoriawynik = srodkikstDAO.finsStr1(nazwa);
       symbolKST = srodekkategoriawynik.getSymbol();
       stawkaKST = srodekkategoriawynik.getStawka();
       RequestContext.getCurrentInstance().update("dodWiad:grid3");
       } catch (Exception e){}
      }
   }
     
   public void przekierowanieWpisKLienta() throws IOException{
       FacesContext.getCurrentInstance().getExternalContext().redirect("klienci.xhtml");
   }
   
   public void przekierowanieEdytujDokument(Dok wpis) throws IOException{
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
   
   public void przekierowanieWpis() throws IOException{
       FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
   }
   
   public void przekierowanieWpisKLientacd() throws IOException{
       FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaIndex.xhtml");
       selDokument.setDataWyst(przechowajdatejakdodaje);
       RequestContext.getCurrentInstance().update("dodWiad:dataPole");
   }
   
   private void aktualizujInwestycje(Dok dok) {
          try{
            String symbol = dok.getSymbolinwestycji();
            if(!symbol.equals("wybierz")){
                Inwestycje biezaca = null;
                for(Inwestycje p : inwestycje){
                    if(p.getSymbol().equals(symbol)){
                        biezaca = p;
                        break;
                    }
                }
                biezaca.setTotal(biezaca.getTotal()+dok.getNetto());
                List<Inwestycje.Sumazalata> sumazalata = biezaca.getSumazalata();
                if(sumazalata.isEmpty()){
                    Inwestycje x = new Inwestycje();
                    Inwestycje.Sumazalata sum = x.new Sumazalata();
                    sum.setRok(wpisView.getRokWpisu().toString());
                    sum.setKwota(0.0);
                    sumazalata.add(sum);
                } else {
                    List<String> roki = new ArrayList<>();
                    for(Inwestycje.Sumazalata p : sumazalata){
                        roki.add(p.getRok());
                    }
                    if(!roki.contains(wpisView.getRokWpisu().toString())){
                        Inwestycje x = new Inwestycje();
                        Inwestycje.Sumazalata sum = x.new Sumazalata();
                        sum.setRok(wpisView.getRokWpisu().toString());
                        sum.setKwota(0.0);
                        sumazalata.add(sum);  
                    }
                }
                for(Inwestycje.Sumazalata p : sumazalata){
                    if(p.getRok().equals(dok.getPkpirR())){
                        p.setKwota(p.getKwota()+dok.getNetto());
                        biezaca.setSumazalata(sumazalata);
                        break;
                    }
                }
                biezaca.getDokumenty().add(dok);
                inwestycjeDAO.edit(biezaca);
                Msg.msg("i","Aktualizuje inwestycje "+symbol,"dodWiad:mess_add");
                
            }
        } catch (Exception e){
          Msg.msg("e","Błąd nie zaktualizowałem inwestycji!","dodWiad:mess_add");
        }
            }
   
  
   
    public boolean isPokazSTR() {
        return pokazSTR;
    }

    public void setPokazSTR(boolean pokazSTR) {
        this.pokazSTR = pokazSTR;
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

   
    public PanelGrid getGrid1() {
        return grid1;
    }

    public void setGrid1(PanelGrid grid1) {
        this.grid1 = grid1;
    }

    public PanelGrid getGrid2() {
        return grid2;
    }

    public void setGrid2(PanelGrid grid2) {
        this.grid2 = grid2;
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


    public double getNetto1() {
        return netto1;
    }

    public void setNetto1(double netto1) {
        this.netto1 = netto1;
    }

    public double getVat1() {
        return vat1;
    }

    public void setVat1(double vat1) {
        this.vat1 = vat1;
    }

    public double getNetto2() {
        return netto2;
    }

    public void setNetto2(double netto2) {
        this.netto2 = netto2;
    }

    public double getVat2() {
        return vat2;
    }

    public void setVat2(double vat2) {
        this.vat2 = vat2;
    }

    public double getNetto3() {
        return netto3;
    }

    public void setNetto3(double netto3) {
        this.netto3 = netto3;
    }

    public double getVat3() {
        return vat3;
    }

    public void setVat3(double vat3) {
        this.vat3 = vat3;
    }

    public double getNetto4() {
        return netto4;
    }

    public void setNetto4(double netto4) {
        this.netto4 = netto4;
    }

    public double getVat4() {
        return vat4;
    }

    public void setVat4(double vat4) {
        this.vat4 = vat4;
    }

    public double getNetto5() {
        return netto5;
    }

    public void setNetto5(double netto5) {
        this.netto5 = netto5;
    }

    public double getVat5() {
        return vat5;
    }

    public void setVat5(double vat5) {
        this.vat5 = vat5;
    }

    
    public String getOpis1() {
        return opis1;
    }

    public void setOpis1(String opis1) {
        this.opis1 = opis1;
    }

    public String getOpis2() {
        return opis2;
    }

    public void setOpis2(String opis2) {
        this.opis2 = opis2;
    }

    public String getOpis3() {
        return opis3;
    }

    public void setOpis3(String opis3) {
        this.opis3 = opis3;
    }

    public String getOpis4() {
        return opis4;
    }

    public void setOpis4(String opis4) {
        this.opis4 = opis4;
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

    public PanelGrid getGrid3() {
        return grid3;
    }

    public void setGrid3(PanelGrid grid3) {
        this.grid3 = grid3;
    }

    public String getNazwaSTR() {
        return nazwaSTR;
    }

    public void setNazwaSTR(String nazwaSTR) {
        this.nazwaSTR = nazwaSTR;
    }

    public String getDataPrzSTR() {
        return dataPrzSTR;
    }

    public void setDataPrzSTR(String dataPrzSTR) {
        this.dataPrzSTR = dataPrzSTR;
    }

    public String getSymbolKST() {
        return symbolKST;
    }

    public void setSymbolKST(String symbolKST) {
        this.symbolKST = symbolKST;
    }

    public String getStawkaKST() {
        return stawkaKST;
    }

    public void setStawkaKST(String stawkaKST) {
        this.stawkaKST = stawkaKST;
    }

    public HtmlSelectOneMenu getSrodkitrwalewyposazenie() {
        return srodkitrwalewyposazenie;
    }

    public void setSrodkitrwalewyposazenie(HtmlSelectOneMenu srodkitrwalewyposazenie) {
        this.srodkitrwalewyposazenie = srodkitrwalewyposazenie;
    }

    public String getTypKST() {
        return typKST;
    }

    public void setTypKST(String typKST) {
        this.typKST = typKST;
    }

    public String getOpizw() {
        return opizw;
    }

    public void setOpizw(String opizw) {
        this.opizw = opizw;
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

    public Double getUmorzeniepoczatkowe() {
        return umorzeniepoczatkowe;
    }

    public void setUmorzeniepoczatkowe(Double umorzeniepoczatkowe) {
        this.umorzeniepoczatkowe = umorzeniepoczatkowe;
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

    public String getOpis5() {
        return opis5;
    }
  
    public void setOpis5(String opis5) {
        this.opis5 = opis5;
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

    public String getVat1S() {
        return vat1S;
    }

    public void setVat1S(String vat1S) {
        this.vat1S = vat1S;
    }

    public String getVat2S() {
        return vat2S;
    }

    public void setVat2S(String vat2S) {
        this.vat2S = vat2S;
    }

    public String getVat3S() {
        return vat3S;
    }

    public void setVat3S(String vat3S) {
        this.vat3S = vat3S;
    }

    public String getVat4S() {
        return vat4S;
    }

    public void setVat4S(String vat4S) {
        this.vat4S = vat4S;
    }

    public String getVat5S() {
        return vat5S;
    }

    public void setVat5S(String vat5S) {
        this.vat5S = vat5S;
    }

    public boolean isPokazEST() {
        return pokazEST;
    }

    public void setPokazEST(boolean pokazEST) {
        this.pokazEST = pokazEST;
    }
    
    
    
    
    
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
         Map<String, Object> lolo =  FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
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

    
}
