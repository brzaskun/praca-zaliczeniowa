/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import dao.AmoDokDAO;
import dao.DokDAO;
import dao.EVatOpisDAO;
import dao.EvewidencjaDAO;
import embeddable.EVatwpis;
import embeddable.Kolmn;
import embeddable.Mce;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.EVatOpis;
import entity.Evewidencja;
import entity.Klienci;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.autocomplete.AutoComplete;
import org.primefaces.component.behavior.ajax.AjaxBehaviorListenerImpl;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;
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
    private String dataWystawienia;
    private String dataSprzedazy;
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
        String transakcjiRodzaj = params.get("dodWiad:rodzajTrans");
        List valueList = new ArrayList();
        UISelectItems ulista = new UISelectItems();
        List dopobrania = new ArrayList();
        switch (transakcjiRodzaj) {
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
        ulista.setValue(valueList);
        pkpirLista.getChildren().add(ulista);
        podepnijEwidencjeVat();


    }

    public void podepnijEwidencjeVat() {
        /*wyswietlamy ewidencje VAT*/
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String transakcjiRodzaj = params.get("dodWiad:rodzajTrans");
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
            if (transakcjiRodzaj.equals("sprzedaz") && tmp.equals("op/zw")) {
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
        eVatOpisDAO.dodajNowyWpis(eVO);
        RequestContext.getCurrentInstance().update("dodWiad:grid1");

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
        HtmlInputText ew = new HtmlInputText();
        final String binding = "#{DokumentView.selDokument.kwotaX}";
        ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
        ew.setValueExpression("value", ve2);
        ew.setStyle("width: 120px");
        NumberConverter nc = new NumberConverter();
        nc.setPattern("###,##");
        ew.setConverter(nc);
        ew.setId("kwotaPkpirX");
        org.primefaces.component.behavior.ajax.AjaxBehavior dragStart = new org.primefaces.component.behavior.ajax.AjaxBehavior();
        dragStart.setGlobal(false);
        MethodExpression me = ef.createMethodExpression(elContext, "#{DokumentView.przeniesKwotaDoNettoX}", String.class, new Class[0]);
        dragStart.addAjaxBehaviorListener(new AjaxBehaviorListenerImpl(me));
        dragStart.setUpdate(":dodWiad:grid1");
        ew.addClientBehavior("blur", dragStart);
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
        String tmp = params.get("dodWiad:kwotaPkpir");
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
        String tmp = params.get("dodWiad:kwotaPkpir");
        String tmpX = params.get("dodWiad:kwotaPkpirX");
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

    public void dodajNowyWpis() {
        try {
            ArrayList<Evewidencja> ew = (ArrayList<Evewidencja>) evewidencjaDAO.getDownloaded();
            EVatOpis eVO = eVatOpisDAO.getDownloadedEVatOpis().get(0);
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
            selDokument.setOpis(selDokument.getOpis().toLowerCase());
            sprawdzCzyNieDuplikat(selDokument);
            dokDAO.dodajNowyWpis(selDokument);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd. Dokument nie został zaksiegowany", e.getStackTrace().toString());
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
    
    public void dodajNowyWpisAutomatyczny() {
            double kwotaumorzenia = 0.0;
            List<Amodok> lista = new ArrayList<Amodok>();
            lista.addAll(amoDokDAO.getAmoDokList());
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
            List<Umorzenie> umorzenia = new ArrayList<Umorzenie>();
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
            if(wpisView.getMiesiacWpisu().equals("01")||wpisView.getMiesiacWpisu().equals("03")
                    ||wpisView.getMiesiacWpisu().equals("05")||wpisView.getMiesiacWpisu().equals("07")
                    ||wpisView.getMiesiacWpisu().equals("08")||wpisView.getMiesiacWpisu().equals("10")
                    ||wpisView.getMiesiacWpisu().equals("12")){ 
                data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-31";
            } else if (wpisView.getMiesiacWpisu().equals("02")){
                data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-28";
            } else {
                data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-30";
            }
            selDokument.setDataWyst(data);
            selDokument.setKontr(new Klienci("111111111","wlasny"));
            selDokument.setRodzTrans("amortyzacja");
            selDokument.setNrWlDk(wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu().toString());
            selDokument.setOpis("umorzenie za miesiac");
            selDokument.setKwota(kwotaumorzenia);
            selDokument.setPkpirKol("poz. koszty");
            sprawdzCzyNieDuplikat(selDokument);
            if(selDokument.getKwota()>0){
            dokDAO.dodajNowyWpis(selDokument);
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
    
//    public void sprawdzczyjestwpisuprzedni() throws Exception{
//        Integer rok = wpisView.getRokWpisu();
//        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
//        if(mc==1){
//            rok--;
//            mc=12;
//        } else {
//            mc--;
//        }
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
//    
    public void sprawdzCzyNieDuplikat(Dok selD) throws Exception {
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

    public String getDataWystawienia() {
        return dataWystawienia;
    }

    public void setDataWystawienia(String dataWyst) {
      
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

    public String getDataSprzedazy() {
        return dataSprzedazy;
    }

    public void setDataSprzedazy(String dataSprzedazy) {
        this.dataSprzedazy = dataSprzedazy;
    }

    public String getWielkoscopisuewidencji() {
        return wielkoscopisuewidencji;
    }

    public void setWielkoscopisuewidencji(String wielkoscopisuewidencji) {
        this.wielkoscopisuewidencji = wielkoscopisuewidencji;
    }
    
    
    
}
