/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import dao.AmoDokDAO;
import dao.DokDAO;
import dao.EVatOpisDAO;
import dao.EvewidencjaDAO;
import dao.RodzajedokDAO;
import dao.StornoDokDAO;
import embeddable.EVatwpis;
import embeddable.Kolmn;
import embeddable.Mce;
import embeddable.Rozrachunek;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.EVatOpis;
import entity.Evewidencja;
import entity.Klienci;
import entity.Rodzajedok;
import entity.SrodekTrw;
import entity.StornoDok;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.convert.NumberConverter;
import javax.faces.el.ValueBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.security.auth.message.callback.PrivateKeyCallback;
import javax.servlet.http.HttpServletRequest;


import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.behavior.ajax.AjaxBehavior;
import org.primefaces.component.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;
import org.primefaces.extensions.component.inputnumber.InputNumber;
import serialclone.SerialClone;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokumentView")
@RequestScoped
public class DokView implements Serializable{
    private HtmlSelectOneMenu pkpirLista;
    private HtmlInputText kontrahentNazwa;
    private HtmlInputText kontrahentNIP;
    private HtmlSelectOneMenu srodkitrwalewyposazenie;

    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    
    @Inject
    private Dok selDokument;
    @Inject
    private Dok wysDokument;
    @Inject
    private Klienci selectedKontr;
    @Inject
    private AmoDokDAO amoDokDAO;
    
    private static Klienci przekazKontr;

    /*pkpir*/
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Kolmn kolumna; 
    private String opis;
    /*pkpir*/
    /* Rozliczenia vat*/
    private String opis1;
    private double netto1;
    private double vat1;
    private String opis2;
    private double netto2;
    private double vat2;
    private String opis3;
    private double netto3;
    private double vat3;
    private String opis4;
    private double netto4;
    private double vat4;
    private String opizw;
    
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
    private String test;
   
    private String nazwaSTR;
    private String dataPrzSTR;
    private String symbolKST;
    private String stawkaKST;
    private String typKST;
    private Double umorzeniepoczatkowe;
   
    //edycja platnosci
    @Inject
    private Rozrachunek rozrachunek;
   //automatyczne ksiegowanie Storna
    @Inject private StornoDokDAO stornoDokDAO;
    private boolean rozliczony;
    @Inject
    private StornoDok stornoDok;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    //przechowuje ostatni dokumnet
    private static String typdokumentu;
    
    public DokView() {
        setPokazSTR(false);
        opis = "ewidencja opis";
        setWysDokument(null);
    }
 
    /**
     * wybiera odpowiedni zestaw kolumn pkpir do podpiecia w zaleznosci od tego
     * czy to transakcja zakupu czy sprzedazy
     */
        public void podepnijListe(AjaxBehaviorEvent e) {
        pkpirLista.getChildren().clear();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        String transakcjiRodzaj = RodzajedokView.getRodzajedokMapS().get(skrot);
        List valueList = new ArrayList();
        UISelectItems ulista = new UISelectItems();
        List dopobrania = new ArrayList();
        switch (transakcjiRodzaj) {
            case "ryczałt":
                dopobrania = kolumna.getKolumnRyczalt();
                break;
            case "zakup":
                dopobrania = kolumna.getKolumnKoszty();
                break;
            case "srodek trw":
                dopobrania = kolumna.getKolumnST();
                break;
            default:
                dopobrania = kolumna.getKolumnPrzychody();
                break;
        }
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
        selDokument.setNrWlDk("");
        ulista.setValue(valueList);
        pkpirLista.getChildren().add(ulista);
        podepnijEwidencjeVat(transakcjiRodzaj);
        try{
        wygenerujnumerkolejny();
        } catch (Exception ef){}
    }

    public void podepnijEwidencjeVat(String transakcjiRodzaj) {
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
            case("WDT"):
                opisewidencji = evat.getWdtVList();
                break;
            case("WNT"):
                opisewidencji = evat.getWntVList();
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
            if ((transakcjiRodzaj.equals("sprzedaz")||transakcjiRodzaj.equals("ryczałt")) && tmp.equals("op/zw")) {
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
            if (opis1 == null) {
                setOpis1(poz);
            } else if (opis2 == null) {
                setOpis2(poz);
            } else if (opis3 == null) {
                setOpis3(poz);
            } else {
                setOpis4(poz);
            }
            final String bindingA = "#{DokumentView.opis" + i + "}";
            ValueExpression ve = ef.createValueExpression(elContext, bindingA, String.class);
            otX.setValueExpression("value", ve);
            grid1.getChildren().add(otX);
            HtmlInputText ew = new HtmlInputText();
            final String binding = "#{DokumentView.netto" + i + "}";
            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
            ew.setValueExpression("value", ve2);
            grid1.getChildren().add(ew);
            HtmlInputText ewX = new HtmlInputText();
            final String bindingX = "#{DokumentView.vat" + i + "}";
            ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
            ewX.setValueExpression("value", ve2X);
            grid1.getChildren().add(ewX);
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
        EVatOpis eVO = new EVatOpis(opis1, opis2, opis3, opis4);
        eVatOpisDAO.dodaj(eVO);
        RequestContext.getCurrentInstance().update("dodWiad:grid1");

    }
    
    public void wygenerujnumerkolejny(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("dodWiad:rodzajTrans");
        String nowynumer = "";
        Rodzajedok rodzajdok = rodzajedokDAO.find(skrot);
        String wzorzec = rodzajdok.getWzorzec();
        //odnajdywanie podzielnika;
        String separator = null;
                if(wzorzec.contains("/")){
                    separator = "/";
                }
         String[] elementy;
         elementy = wzorzec.split(separator);
         Dok ostatnidokument = dokDAO.find(skrot,wpisView.getPodatnikWpisu() , wpisView.getRokWpisu());
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
                    nowynumer = nowynumer.concat(wpisView.getMiesiacWpisu()).concat(separator);
                    break;
                case "r":
                    nowynumer = nowynumer.concat(wpisView.getRokWpisu().toString()).concat(separator);
                    break;
                case "s":
                    nowynumer = nowynumer.concat(elementyold[i]).concat(separator);
                    break;
            }
         }
         if(nowynumer.endsWith(separator)){
          nowynumer = nowynumer.substring(0,nowynumer.lastIndexOf(separator));
         }
         selDokument.setNrWlDk(nowynumer);
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
            grid3.getChildren().add(ew);
            
            HtmlOutputText ot1 = new HtmlOutputText();
            ot1.setValue("data przyjecia");
            grid3.getChildren().add(ot1);
            HtmlInputText ew1 = new HtmlInputText();
            final String binding1 = "#{DokumentView.dataPrzSTR}";
            ValueExpression ve1 = ef.createValueExpression(elContext, binding1, String.class);
            ew1.setValueExpression("value", ve1);
            grid3.getChildren().add(ew1);
            
            HtmlOutputText ot3 = new HtmlOutputText();
            ot3.setValue("symbol KST");
            grid3.getChildren().add(ot3);
            HtmlInputText ew3 = new HtmlInputText();
            final String binding3 = "#{DokumentView.symbolKST}";
            ValueExpression ve3 = ef.createValueExpression(elContext, binding3, String.class);
            ew3.setValueExpression("value", ve3);
            grid3.getChildren().add(ew3);
            
            HtmlOutputText ot4 = new HtmlOutputText();
            ot4.setValue("stawka amort");
            grid3.getChildren().add(ot4);
            HtmlInputText ew4 = new HtmlInputText();
            final String binding4 = "#{DokumentView.stawkaKST}";
            ValueExpression ve4 = ef.createValueExpression(elContext, binding4, String.class);
            ew4.setValueExpression("value", ve4);
            grid3.getChildren().add(ew4);
            
            HtmlOutputText ot5 = new HtmlOutputText();
            ot5.setValue("dotychczasowe umorzenie");
            grid3.getChildren().add(ot5);
            HtmlInputText ew5 = new HtmlInputText();
            final String binding5 = "#{DokumentView.umorzeniepoczatkowe}";
            ValueExpression ve5 = ef.createValueExpression(elContext, binding5, String.class);
            ew5.setValueExpression("value", ve5);
            grid3.getChildren().add(ew5);
            umorzeniepoczatkowe = 0.0;
            
            UISelectItems ulista = new UISelectItems();
            List valueList = new ArrayList();
            SelectItem selectItem = new SelectItem("srodek trw.", "srodek trw.");
            valueList.add(selectItem);
            selectItem = new SelectItem("wyposazenie", "wyposazenie");
            valueList.add(selectItem);
            ulista.setValue(valueList);
            final String bindingX = "#{DokumentView.typKST}";
            ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
            HtmlSelectOneMenu htmlSelectOneMenu = new HtmlSelectOneMenu();
            htmlSelectOneMenu.setValueExpression("value", ve2X);
            htmlSelectOneMenu.setStyle("min-width: 150px");
            htmlSelectOneMenu.getChildren().add(ulista);
            grid3.getChildren().add(htmlSelectOneMenu);
            
        RequestContext.getCurrentInstance().update("dodWiad:grid3");
    }

    public void wygenerujNowaKolumnePkpir() {
        /*wyswietlamy ewidencje VAT*/
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String transakcjiRodzaj = params.get("dodWiad:rodzajTrans");
        List valueList = new ArrayList();
        UISelectItems ulista = new UISelectItems();
        List dopobrania = new ArrayList();
        if (transakcjiRodzaj.equals("zakup")) {
            dopobrania = kolumna.getKolumnKoszty();
        } else if (transakcjiRodzaj.equals("srodek trw")) {
            dopobrania = kolumna.getKolumnST();
        } else {
            dopobrania = kolumna.getKolumnPrzychody();
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
//            HtmlInputText ew = new HtmlInputText();
//            final String binding = "#{DokumentView.selDokument.kwotaX}";
//            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
//            ew.setValueExpression("value", ve2);
//            ew.setStyle("width: 120px");
//            NumberConverter nc = new NumberConverter();
//            nc.setPattern("###,##");
//            ew.setConverter(nc);
//            ew.setId("kwotaPkpirX");
//            AjaxBehavior dragStart = new AjaxBehavior();
//            dragStart.setGlobal(false);
//            MethodExpression me = ef.createMethodExpression(elContext, "#{DokumentView.przeniesKwotaDoNettoX}", String.class, new Class[0]);
//            dragStart.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(me,me));
//            dragStart.setUpdate(":dodWiad:grid1");
//            ew.addClientBehavior("blur", dragStart);
//            grid2.getChildren().add(ew);
        //generowanie tego nowego roszerzenia
            InputNumber ew = new InputNumber();
            final String binding = "#{DokumentView.selDokument.kwotaX}";
            ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
            ew.setValueExpression("value", ve2);
            ew.setStyle("width: 120px");
            NumberConverter nc = new NumberConverter();
            nc.setPattern("###,##");
            ew.setConverter(nc);
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
            grid2.getChildren().add(ew);
        final String bindingX = "#{DokumentView.selDokument.pkpirKolX}";
        ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
        HtmlSelectOneMenu htmlSelectOneMenu = new HtmlSelectOneMenu();
        htmlSelectOneMenu.setValueExpression("value", ve2X);
        htmlSelectOneMenu.setStyle("min-width: 150px");
        htmlSelectOneMenu.getChildren().add(ulista);
        grid2.getChildren().add(htmlSelectOneMenu);
        RequestContext.getCurrentInstance().update("dodWiad:grid2");
    }

    public void przeniesKwotaDoNetto(AjaxBehaviorEvent e) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String tmp = params.get("dodWiad:kwotaPkpir_hinput");
        tmp = tmp.replace(",", ".");
        netto1 = Double.parseDouble(tmp);
        BigDecimal tmp1 = BigDecimal.valueOf(netto1);
        tmp1 = tmp1.multiply(BigDecimal.valueOf(0.23));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        vat1 = Double.parseDouble(tmp1.toString());
        RequestContext.getCurrentInstance().update("dodWiad:grid1");
    }

    public void przeniesKwotaDoNettoX(AjaxBehaviorEvent e) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String tmp = params.get("dodWiad:kwotaPkpir_hinput");
        String tmpX = params.get("dodWiad:kwotaPkpirX_hinput");
        tmp = tmp.replace(",", ".");
        netto1 = Double.parseDouble(tmp);
        if (!tmpX.equals("")) {
            tmpX = tmpX.replace(",", ".");
            netto1 = netto1 + Double.parseDouble(tmpX);
        }
        BigDecimal tmp1 = BigDecimal.valueOf(netto1);
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        netto1 = Double.parseDouble(tmp1.toString());
        tmp1 = tmp1.multiply(BigDecimal.valueOf(0.23));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        vat1 = Double.parseDouble(tmp1.toString());
        RequestContext.getCurrentInstance().update("dodWiad:grid1");
    }

    /**
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

    public void dodaj() {
        try {
            ArrayList<Evewidencja> ew = (ArrayList<Evewidencja>) evewidencjaDAO.getDownloaded();
            EVatOpis eVO = (EVatOpis) eVatOpisDAO.getDownloaded().get(0);
            List<String> pobierzOpisy = new ArrayList<>();
            pobierzOpisy.add(eVO.getOpis1());
            pobierzOpisy.add(eVO.getOpis2());
            pobierzOpisy.add(eVO.getOpis3());
            pobierzOpisy.add(eVO.getOpis4());
            List<Double> pobierzNetto = new ArrayList<>();
            pobierzNetto.add(netto1);
            pobierzNetto.add(netto2);
            pobierzNetto.add(netto3);
            pobierzNetto.add(netto4);
            List<Double> pobierzVat = new ArrayList<>();
            pobierzVat.add(vat1);
            pobierzVat.add(vat2);
            pobierzVat.add(vat3);
            pobierzVat.add(vat4);
            List<EVatwpis> el = new ArrayList<>();
            int i = 0;
            while (i < evewidencjaDAO.getDownloaded().size()) {
                int j = 0;
                while (j < 4 && (pobierzOpisy.get(j) != null)) {
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
            if (!selDokument.isDokumentProsty()) {
                selDokument.setEwidencjaVAT(el);
            } else {
                selDokument.setEwidencjaVAT(null);
            }
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setStatus("bufor");
            selDokument.setTypdokumentu(typdokumentu);
            selDokument.setRodzTrans(RodzajedokView.getRodzajedokMapS().get(selDokument.getTypdokumentu()));
            selDokument.setOpis(selDokument.getOpis().toLowerCase());
            if(selDokument.getKwotaX()!=null){
                selDokument.setNetto(selDokument.getKwota()+selDokument.getKwotaX());
            } else {
                selDokument.setNetto(selDokument.getKwota());
            }
            dodajdatydlaStorno();
            //dodaje zaplate faktury gdy faktura jest uregulowana
            if(selDokument.getRozliczony()==true){
                Double kwota = selDokument.getKwota();
                try{
                kwota = kwota + selDokument.getKwotaX();
                } catch (Exception e){}
                Rozrachunek rozrachunekx = new Rozrachunek(selDokument.getTerminPlatnosci(), kwota, 0.0, selDokument.getWprowadzil(),new Date());
                ArrayList<Rozrachunek> lista = new ArrayList<>();
                lista.add(rozrachunekx);
                selDokument.setRozrachunki(lista);
            }
            sprawdzCzyNieDuplikat(selDokument);
            dokDAO.dodaj(selDokument);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy dokument zachowany" +selDokument, null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd. Dokument nie został zaksiegowany " + e.getStackTrace().toString(),null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
         } 
         if(pokazSTR){
            try {
                   selectedSTR.setNetto(selDokument.getKwota());
                   BigDecimal tmp1 = BigDecimal.valueOf(selDokument.getKwota());
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
            wysDokument = SerialClone.clone(selDokument);
            selDokument = new Dok();
            RequestContext.getCurrentInstance().update("@form");
    }
    //dodaje wyliczone daty platnosci dla obliczenia pozniej czy trzeba stornowac
    public void dodajdatydlaStorno() throws ParseException{
        String dataWyst = selDokument.getDataWyst();
        String dataPlat = selDokument.getTerminPlatnosci();
        Calendar c = Calendar.getInstance();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datawystawienia = (Date) formatter.parse(dataWyst);
        Date terminplatnosci = (Date) formatter.parse(dataPlat);
       if(roznicaDni(datawystawienia,terminplatnosci)==true){
         c.setTime(terminplatnosci);
         c.add(Calendar.DAY_OF_MONTH, 30);
         String nd30 = formatter.format(c.getTime());
         selDokument.setTermin30(nd30);
         selDokument.setTermin90("");
        } else {
          c.setTime(terminplatnosci);
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
                if(wynik<=60){
                    return true;
                } else {
                    return false;
                }
     }
    
    public void dodajNowyWpisAutomatyczny() {
            double kwotaumorzenia = 0.0;
            List<Amodok> lista = new ArrayList<Amodok>();
            lista.addAll(amoDokDAO.getDownloaded());
            Amodok amodokPoprzedni = null;
            Amodok amodok = null;
            Iterator itx;
            itx = lista.iterator();
            while(itx.hasNext()){
                Amodok tmp = (Amodok) itx.next();
                Integer mctmp = tmp.getMc();
                String mc = Mce.getMapamcy().get(mctmp);
                Integer rok = tmp.getRok();
                if(wpisView.getRokWpisu().equals(rok)&&wpisView.getMiesiacWpisu().equals(mc)){
                    amodok = tmp;
                    break;
                }
                amodokPoprzedni = tmp;
            }
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
            selDokument.setRodzTrans("amortyzacja");
            selDokument.setTypdokumentu("AMO");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu().toString());
            selDokument.setOpis("umorzenie za miesiac");
            selDokument.setKwota(kwotaumorzenia);
            selDokument.setPkpirKol("poz. koszty");
            selDokument.setRozliczony(true);
            sprawdzCzyNieDuplikat(selDokument);
            if(selDokument.getKwota()>0){
            dokDAO.dodaj(selDokument);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy dokument umorzenia zachowany", selDokument.getIdDok().toString());
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
            selDokument.setKwota(kwotastorno);
            selDokument.setPkpirKol("poz. koszty");
            selDokument.setRozliczony(true);
            selDokument.setTypdokumentu(typdokumentu);
            //sprawdzCzyNieDuplikat(selDokument);
            if(selDokument.getKwota()!=0){
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
    public void sprawdzczyjestwpisuprzedni() throws Exception{
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        if(mc==1){
            rok--;
            mc=12;
        } else {
            mc--;
        }
       Dok tmp = dokDAO.znajdzPoprzednika(rok, mc);
        if (tmp == null) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie zaksiegowano amortyzacji w poprzednim miesiacu", null);
            FacesContext.getCurrentInstance().addMessage("wprowadzenieNowego", msg);
            RequestContext.getCurrentInstance().update("messageserror");
            throw new Exception();
        } else {
            System.out.println("Nie znaleziono duplikatu");
        }
    }
    
    
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
    }
    
   
    public void przekazKontrahenta(ValueChangeEvent e) throws Exception {
        AutoComplete anAutoComplete = (AutoComplete) e.getComponent();
        przekazKontr = (Klienci) anAutoComplete.getValue();
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
   
    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) {
        RequestContext.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
        RequestContext.getCurrentInstance().update("westWpis:westWpisWidok");

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
    
    public void skopiujdoTerminuPlatnosci(AjaxBehaviorEvent e){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        selDokument.setTerminPlatnosci(params.get("dodWiad:dataPole"));
        RequestContext.getCurrentInstance().update("dodWiad:dataTPole");
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
                kwota = -selDokument.getKwota();
                try{
                kwota = kwota - selDokument.getKwotaX();
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
        if(pozostalo==0&&rozliczony==true){
            selDokument.setRozliczony(rozliczony);
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

    public HtmlInputText getKontrahentNazwa() {
        return kontrahentNazwa;
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

    public void setKontrahentNazwa(HtmlInputText kontrahentNazwa) {
        this.kontrahentNazwa = kontrahentNazwa;
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
        DokView.typdokumentu = typdokumentu;
    }

    public Double getUmorzeniepoczatkowe() {
        return umorzeniepoczatkowe;
    }

    public void setUmorzeniepoczatkowe(Double umorzeniepoczatkowe) {
        this.umorzeniepoczatkowe = umorzeniepoczatkowe;
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
}
