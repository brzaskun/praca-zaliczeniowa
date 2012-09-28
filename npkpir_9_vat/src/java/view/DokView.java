/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import embeddable.Kl;
import embeddable.Kolmn;
import entity.Dok;
import java.io.Serializable;
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
import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.component.autocomplete.AutoComplete;
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
    private org.primefaces.component.panelgrid.PanelGrid grid1;
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
   
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    @Inject
    private DokDAO dokDAO;
    
    @Inject
    private Kolmn kolumna; 
    
    @Inject
    private EVatView evat;
    
    private String opis;

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

    public org.primefaces.component.panelgrid.PanelGrid getGrid1() {
        return grid1;
    }

    public void setGrid1(org.primefaces.component.panelgrid.PanelGrid grid1) {
        this.grid1 = grid1;
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

   
    
    public DokView() {
        dokHashTable = new HashMap<String, Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        obiektDOKjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSel = new ArrayList<Dok>();
        opis="ewidencja opis";
        
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
          /*wyswietlamy ewidencje VAT*/
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
          int i=0;
          while (itx.hasNext()) {
              String poz = (String) itx.next();
              HtmlOutputText otX = new HtmlOutputText();
              otX.setValue(poz);
              grid1.getChildren().add(otX);
              HtmlInputText ew = new HtmlInputText();
              final String binding = "#{DokumentView.opis"+i+"}";
              ValueExpression ve2 = ef.createValueExpression(binding, String.class);
              ew.setValueExpression("value", ve2);
              grid1.getChildren().add(ew);
              HtmlInputText ewX = new HtmlInputText();
              final String bindingX = "#{DokumentView.mopis"+i+"}";
              ValueExpression ve2X = ef.createValueExpression(bindingX, String.class);
              ewX.setValueExpression("value", ve2X);
              grid1.getChildren().add(ewX);
          }
          ctx.getCurrentInstance().update("dodWiad:grid1");

      }
      
        /**
     *zmienia wlasciwosci pol wprowadzajacych dane kontrahenta
     */
    public void reset(AjaxBehaviorEvent e){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
 	String ktNazwa = params.get("dodWiad:acForce_input");
        String ktNIP = params.get("dodWiad:acForcex_input");
        if(ktNazwa.length()>0){
        kontrahentNIP.setReadonly(true);
        }
        if(ktNIP.length()>0){
        kontrahentNazwa.setReadonly(true);
        }
      }
    
    public void sformatuj(){
        //String formatka=null;
        //selDokument.setLogi(selDokument.getIdDok().toLowerCase());
        //selDokument.setImie(selDokument.getImie().substring(0,1).toUpperCase()+selDokument.getImie().substring(1).toLowerCase());
        //selDokument.setNazw(selDokument.getNazw().substring(0,1).toUpperCase()+selDokument.getNazw().substring(1).toLowerCase());
    }
    
    @PostConstruct
    public void init(){
        if(wpisView.getPodatnikWpisu()!=null){
        Collection c = null;
        try {
            c = dokDAO.getDownloadedDok();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        if(c!=null){
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
        }
        }
         
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
      
      public void generujEvat(){
         
        }
}
