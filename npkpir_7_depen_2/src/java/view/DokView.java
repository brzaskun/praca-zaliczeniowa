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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokumentView")
@ViewScoped
public class DokView implements Serializable{
    private HtmlSelectOneMenu pkpirLista;
    private HtmlInputText kontrahentNazwa;
    private HtmlInputText kontrahentNIP;
    @Inject
    private Dok selDokument;
    @Inject
    private Kl selectedKontr;
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
    private Kolmn k; 
    
    public HtmlSelectOneMenu getPkpirLista() {
        return pkpirLista;
    }

    public void setPkpirLista(HtmlSelectOneMenu pkpirLista) {
        this.pkpirLista = pkpirLista;
    }

    public HtmlInputText getKontrahentNazwa() {
        return kontrahentNazwa;
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

    public DokView() {
        dokHashTable = new HashMap<String, Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        obiektDOKjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSel = new ArrayList<Dok>();
    }
    
    
    
    /**
     *wybiera odpowiedni zestaw kolumn pkpir do podpiecia w zaleznosci od tego
     * czy to transakcja zakupu czy sprzedazy
     */
      public void podepnijListe(AjaxBehaviorEvent e){
        pkpirLista.getChildren().clear();
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
 	String transakcjiRodzaj = params.get("dodWiad:rodzajTrans");
        List valueList = new ArrayList();
        UISelectItems ulista = new UISelectItems();
        List dopobrania = new ArrayList();
        if(transakcjiRodzaj.equals("zakup")){
        dopobrania = k.getKolumnKoszty();
        } else if (transakcjiRodzaj.equals("srodek trw")){
        dopobrania = k.getKolumnST();
        } else {
        dopobrania = k.getKolumnPrzychody();
        }
        Iterator it;
        it = dopobrania.iterator();
        while(it.hasNext()){
        String poz = (String) it.next();
        SelectItem selectItem = new SelectItem(poz, poz);
        valueList.add(selectItem);
        }
        ulista.setValue(valueList);
        pkpirLista.getChildren().add(ulista);
      
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
    
//     public void refresh(){
//        kluczDOKjsf.clear();
//        obiektDOKjsf.clear();
//        dokHashTable.clear();
//        obiektDOKjsfSel.clear();
//        obiektDOKmrjsfSel.clear();
//        Collection c = null;
//        try {
//             c = dokDAO.getDownloadedDok();
//        } catch (Exception e) {
//            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
//        }
//        Iterator it;
//        it = c.iterator();
//        while (it.hasNext()) {
//            Dok tmp = (Dok) it.next();
//            kluczDOKjsf.add(tmp.getIdDok().toString());
//            obiektDOKjsf.add(tmp);
//            dokHashTable.put(tmp.getIdDok().toString(), tmp);
//            if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
//                obiektDOKjsfSel.add(tmp);
//            }
//            dokHashTable.put(tmp.getIdDok().toString(), tmp);
//        }
//        Iterator itx;
//        itx = obiektDOKjsfSel.iterator();
//        while (itx.hasNext()) {
//            Dok tmpx = (Dok) itx.next();
//            String m = wpisView.getMiesiacWpisu();
//            Integer r = wpisView.getRokWpisu();
//            if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
//                obiektDOKmrjsfSel.add(tmpx);
//            }
//        }
//
//    }
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
            selDokument.setPkpirM(wpisView.getMiesiacWpisu());
            selDokument.setPkpirR(wpisView.getRokWpisu().toString());
            selDokument.setPodatnik(wpisView.getPodatnikWpisu());
            selDokument.setWprowadzil(wpisView.getWprowadzil());
            selDokument.setStatus("bufor");
            dokDAO.dodajNowyWpis(selDokument);
            FacesMessage msg = new FacesMessage("Nowy dokument zachowany", selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
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
        try {
        obiektDOKjsfSel.remove(selDokument);
        obiektDOKmrjsfSel.remove(selDokument);
        dokDAO.destroy(selDokument);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getIdDok()+" "+e.toString());
        }
          FacesMessage msg = new FacesMessage("Dokument usunięty", selDokument.getIdDok().toString());
          FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
