/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.EVDAO;
import dao.EVatOpisDAO;
import entity.EVatOpis;
import embeddable.EVatwpis;
import embeddable.EVidencja;
import embeddable.Kl;
import embeddable.Kolmn;
import entity.Dok;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
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
import org.primefaces.event.RowEditEvent;

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
    private PanelGrid grid1;
    private PanelGrid grid2;
    @Inject
    private Dok selDokument;
    @Inject
    private Kl selectedKontr;
    private static Kl przekazKontr;
    private String dataWystawienia;
    private HashMap<String,Dok> dokHashTable;
    //tablica kluczy do obiektów
    private List<String> kluczDOKjsf;
    //tablica obiektów
    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> obiektDOKmrjsfSel;
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Dok> obiektDOKmrjsfSelX;
   
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    @Inject
    private DokDAO dokDAO;
    
    @Inject
    private Kolmn kolumna; 
    
    @Inject
    private EVatView evat;
    
    private String opis;
    
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
    
    @Inject
    private EVatOpisDAO eVatOpisDAO;
    
    @Inject
    private EVidencja eVidencja;
    
    
    private EVatwpis eVatwpis;
    
    

    

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
    
    
    public EVidencja geteVidencja() {
        return eVidencja;
    }

    public void seteVidencja(EVidencja eVidencja) {
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
    
    public Kl getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Kl selectedKontr) {
        this.selectedKontr = selectedKontr;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public List<Dok> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<Dok> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<Dok> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<Dok> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<Dok> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<Dok> obiektDOKmrjsfSel) {
        this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
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

    public Kl getPrzekazKontr() {
        return przekazKontr;
    }

    public void setPrzekazKontr(Kl przekazKontr) {
        this.przekazKontr = przekazKontr;
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

    public List<Dok> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<Dok> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }

    
   
    
    public DokView() {
        dokHashTable = new HashMap<String, Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        obiektDOKjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSelX = new ArrayList<Dok>();
        opis="ewidencja opis";
        
    }
    
    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Collection c = null;
            try {
                c = dokDAO.getDownloadedDok();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                Iterator it;
                it = c.iterator();
                while (it.hasNext()) {
                    Dok tmp = (Dok) it.next();
                    kluczDOKjsf.add(tmp.getIdDok().toString());
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        obiektDOKjsfSel.add(tmp);
                    }
                    dokHashTable.put(tmp.getIdDok().toString(), tmp);
                }
                Iterator itx;
                itx = obiektDOKjsfSel.iterator();
                while (itx.hasNext()) {
                    Dok tmpx = (Dok) itx.next();
                    String m = wpisView.getMiesiacWpisu();
                    Integer r = wpisView.getRokWpisu();
                    if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
                        obiektDOKmrjsfSel.add(tmpx);
                    }
                }
                if (wpisView.getMiesiacOd() != null) {
                obiektDOKmrjsfSelX.clear();
                Iterator itxX;
                itxX = obiektDOKjsfSel.iterator();
                    Integer r = wpisView.getRokWpisu();
                    String mOd = wpisView.getMiesiacOd();
                    Integer mOdI = Integer.parseInt(mOd);
                    String mDo = wpisView.getMiesiacDo();
                    Integer mDoI = Integer.parseInt(mDo);
                    Map<Integer, String> mapa;
                    mapa = new HashMap<Integer, String>();
                    mapa.put(1, "01");
                    mapa.put(2, "02");
                    mapa.put(3, "03");
                    mapa.put(4, "04");
                    mapa.put(5, "05");
                    mapa.put(6, "06");
                    mapa.put(7, "07");
                    mapa.put(8, "08");
                    mapa.put(9, "09");
                    mapa.put(10, "10");
                    mapa.put(11, "11");
                    mapa.put(12, "12");
                    while (itxX.hasNext()) {
                        Dok tmpx = (Dok) itxX.next();
                    for (int i = mOdI; i <= mDoI; i++) {
                        if (tmpx.getPkpirM().equals(mapa.get(i)) && tmpx.getPkpirR().equals(r.toString())) {
                            obiektDOKmrjsfSelX.add(tmpx);
                        }
                    }
                }
                }
            }
        }
    }

    public void srodkiwyswietl(){
         wpisView.setSrodkTrw(true);
         RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:panelek");
    }
    /**
     *wybiera odpowiedni zestaw kolumn pkpir do podpiecia w zaleznosci od tego
     * czy to transakcja zakupu czy sprzedazy
     */
      public void podepnijListe(AjaxBehaviorEvent e){
          
          pkpirLista.getChildren().clear();
          Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
          String transakcjiRodzaj = params.get("dodWiad:rodzajTrans");
          List valueList = new ArrayList();
          UISelectItems ulista = new UISelectItems();
          List dopobrania = new ArrayList();
          if (transakcjiRodzaj.equals("zakup")) {
              dopobrania = kolumna.getKolumnKoszty();
          } else if (transakcjiRodzaj.equals("srodek trw")) {
              dopobrania = kolumna.getKolumnST();
              wpisView.setSrodkTrw(true);
              
          } else {
              dopobrania = kolumna.getKolumnPrzychody();
          }
          /*dodajemy na poczatek zwyczajawa kolumne klienta*/
              String kol = przekazKontr.getPkpirKolumna();
          SelectItem selectI = new SelectItem(kol, kol);
          valueList.add(selectI);
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
      
   public void podepnijEwidencjeVat(){
        /*wyswietlamy ewidencje VAT*/
          Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
          String transakcjiRodzaj = params.get("dodWiad:rodzajTrans");
          FacesContext facesCtx = FacesContext.getCurrentInstance();
          ELContext elContext = facesCtx.getELContext();
          List opisewidencji = new ArrayList();
          if (transakcjiRodzaj.equals("zakup")) {
              opisewidencji = evat.getZakupVList();
          } else if (transakcjiRodzaj.equals("srodek trw")) {
              opisewidencji = evat.getSrodkitrwaleVList();
              
          } else {
              opisewidencji = evat.getSprzedazVList();
          }
          grid1 = getGrid1();
          grid1.getChildren().clear();
          RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:grid1");
          Iterator itx;
          List naglowekewidencji = evat.getNaglowekVList();
          itx = naglowekewidencji.iterator();
          while(itx.hasNext()){
          HtmlOutputText ot = new HtmlOutputText();
          ot.setValue((String) itx.next());
          grid1.getChildren().add(ot);
          }
          ExpressionFactory ef = ExpressionFactory.newInstance();
          itx = opisewidencji.iterator();
          int i=1;
          while (itx.hasNext()) {
              String poz = (String) itx.next();
              HtmlOutputText otX = new HtmlOutputText();
              otX.setValue(poz);
              if(opis1 == null){
                  setOpis1(poz);
              } else if (opis2 == null){
                  setOpis2(poz);
              } else if (opis3 == null){
                  setOpis3(poz);
              }  else {
                  setOpis4(poz);
              }
              final String bindingA = "#{DokumentView.opis"+i+"}";
              ValueExpression ve = ef.createValueExpression(elContext, bindingA, String.class);
              otX.setValueExpression("value", ve);
              grid1.getChildren().add(otX);
              HtmlInputText ew = new HtmlInputText();
              final String binding = "#{DokumentView.netto"+i+"}";
              ValueExpression ve2 = ef.createValueExpression(elContext, binding, String.class);
              ew.setValueExpression("value", ve2);
              grid1.getChildren().add(ew);
              HtmlInputText ewX = new HtmlInputText();
              final String bindingX = "#{DokumentView.vat"+i+"}";
              ValueExpression ve2X = ef.createValueExpression(elContext, bindingX, String.class);
              ewX.setValueExpression("value", ve2X);
              grid1.getChildren().add(ewX);
              i++;
          }
           eVatOpisDAO.clear();
              EVatOpis eVO = new EVatOpis(opis1, opis2, opis3, opis4);
              eVatOpisDAO.dodajNowyWpis(eVO);
          ctx.getCurrentInstance().update("dodWiad:grid1");

   }
   
   public void wygenerujNowaKolumnePkpir(){
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
          RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:grid2");
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
              dragStart.addAjaxBehaviorListener( new AjaxBehaviorListenerImpl(me));
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
          ctx.getCurrentInstance().update("dodWiad:grid2");
  }
   
    public void przeniesKwotaDoNetto(AjaxBehaviorEvent e){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
 	String tmp = params.get("dodWiad:kwotaPkpir");
        tmp = tmp.replace(",",".");
        netto1= Double.parseDouble(tmp);
        BigDecimal tmp1 = BigDecimal.valueOf(netto1);
        tmp1 = tmp1.multiply(BigDecimal.valueOf(0.23));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        vat1 = Double.parseDouble(tmp1.toString());
         RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:grid1");
    }
   
    public void przeniesKwotaDoNettoX(AjaxBehaviorEvent e){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
 	String tmp = params.get("dodWiad:kwotaPkpir");
        String tmpX = params.get("dodWiad:kwotaPkpirX");
        tmp = tmp.replace(",",".");
        netto1= Double.parseDouble(tmp);
        if(!tmpX.equals("")){
            tmpX = tmpX.replace(",",".");
            netto1= netto1+Double.parseDouble(tmpX);
        }
        BigDecimal tmp1 = BigDecimal.valueOf(netto1);
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        netto1 = Double.parseDouble(tmp1.toString());
        tmp1 = tmp1.multiply(BigDecimal.valueOf(0.23));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        vat1 = Double.parseDouble(tmp1.toString());
         RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:grid1");
    }
        /**
     *zmienia wlasciwosci pol wprowadzajacych dane kontrahenta
     */
   
    public void dokumentProstuSchowajEwidencje(){
          netto1 = 0;
          vat1 = 0;
          selDokument.setEwidencjaVAT(null);
          grid1 = getGrid1();
          grid1.getChildren().clear();
          RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:grid1");
    }
    public void sformatuj(){
        //String formatka=null;
        //selDokument.setLogi(selDokument.getIdDok().toLowerCase());
        //selDokument.setImie(selDokument.getImie().substring(0,1).toUpperCase()+selDokument.getImie().substring(1).toLowerCase());
        //selDokument.setNazw(selDokument.getNazw().substring(0,1).toUpperCase()+selDokument.getNazw().substring(1).toLowerCase());
    }
    
 
    public void edit(RowEditEvent ex){
        try {
            //sformatuj();
            dokDAO.edit(selDokument);
            //refresh();
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany "+ex.getObject().toString(), selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
      
          
     public void dodajNowyWpis(){
         try {
            ArrayList<EVidencja> ew = (ArrayList<EVidencja>) EVDAO.getWykazEwidencji();
            EVatOpis eVO = eVatOpisDAO.getDownloadedEVatOpis().get(0);
            List<String> pobierzOpisy = new ArrayList<String>();
            pobierzOpisy.add(eVO.getOpis1());
            pobierzOpisy.add(eVO.getOpis2());
            pobierzOpisy.add(eVO.getOpis3());
            pobierzOpisy.add(eVO.getOpis4());
            List<Double> pobierzNetto = new ArrayList<Double>();
            pobierzNetto.add(netto1);
            pobierzNetto.add(netto2);
            pobierzNetto.add(netto3);
            pobierzNetto.add(netto4);
            List<Double> pobierzVat = new ArrayList<Double>();
            pobierzVat.add(vat1);
            pobierzVat.add(vat2);
            pobierzVat.add(vat3);
            pobierzVat.add(vat4);
            List<EVatwpis> el = new ArrayList<EVatwpis>();
            int i=0;
            while(i<EVDAO.getWykazEwidencji().size()){
                int j=0;
                while(j<4&&(pobierzOpisy.get(j)!=null)){
                    String op = pobierzOpisy.get(j);
                    String naz = ew.get(i).getNazwaEwidencji();
                    if(naz.equals(op)){
                        eVidencja = ew.get(i);
                        eVatwpis = new EVatwpis();
                        eVatwpis.setEwidencja(eVidencja);
                        eVatwpis.setNetto(pobierzNetto.get(j));
                        eVatwpis.setVat(pobierzVat.get(j));
                        eVatwpis.setEstawka("zw");
                        el.add(eVatwpis);
                        eVidencja = null;
                    }
                    j++;
                }
                i++;
            }
            if(!selDokument.isDokumentProsty()){
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
            dokDAO.dodajNowyWpis(selDokument);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokument nie utworzony DokView", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
    }
    
     public void ustawDate(AjaxBehaviorEvent e){
        selDokument.setDokumentProsty(false);
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("dodWiad:dokumentprosty");
        String dataWyst = selDokument.getDataWyst();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        if(dataWyst.equals("-")){
           dataWyst=rok+"-";
        } else if (dataWyst.equals(rok+"--")){
            dataWyst=rok+"-"+mc+"-";
        }
        selDokument.setDataWyst(dataWyst);
     }
     
     
      public void destroy(Dok selDokument) {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
 //       Principal principal = request.getUserPrincipal();
//        if(request.isUserInRole("Administrator")){
        try {
        obiektDOKjsfSel.remove(selDokument);
        obiektDOKmrjsfSel.remove(selDokument);
        dokDAO.destroy(selDokument);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getIdDok()+" "+e.toString());
        }
          FacesMessage msg = new FacesMessage("Dokument usunięty", selDokument.getIdDok().toString());
          FacesContext.getCurrentInstance().addMessage(null, msg);
//    } else {
//            FacesMessage msg = new FacesMessage("Nie masz uprawnien do usuniecia dokumentu", selDokument.getIdDok().toString());
//          FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//     }
}
      public void przekazKontrahenta(ValueChangeEvent e){
        AutoComplete anAutoComplete = (AutoComplete)e.getComponent();
        String aSelection = anAutoComplete.getValue().toString();
        przekazKontr = (Kl) anAutoComplete.getValue();
      }
      
      public void aktualizujTabele(AjaxBehaviorEvent e){
          RequestContext ctx = null;
          ctx.getCurrentInstance().update("form:dokumentyLista");
          ctx.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
          FacesContext facesContext = FacesContext.getCurrentInstance();
          Application application = facesContext.getApplication();
          ValueBinding binding = application.createValueBinding("#{PodatekView}");
          PodatekView podatekView = (PodatekView)binding.getValue(facesContext);
          podatekView.sprawozdaniePodatkowe();
          ctx.getCurrentInstance().update("form:prezentacjaPodatku");
      }
      
       public void aktualizujObroty(AjaxBehaviorEvent e){
          obiektDOKmrjsfSelX.clear();
          RequestContext ctx = null;
          ctx.getCurrentInstance().update("formX");
          ctx.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
      }
      
      public void aktualizujWestWpisWidok(AjaxBehaviorEvent e){
          RequestContext ctx = null;
          ctx.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
          ctx.getCurrentInstance().update("westWpis:westWpisWidok");
          
      }
 
      
}
