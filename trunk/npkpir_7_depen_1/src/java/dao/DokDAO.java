/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kl;
import embeddable.Kolmn;
import embeddable.Pod;
import embeddable.WpisSet;
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
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;
import session.DokFacade;
import view.DokView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokDAO")
@ViewScoped
public class DokDAO implements Serializable{
    @ManagedProperty(value="#{WpisSet}")
    private WpisSet wpisSet;
    @Inject
    private DokFacade dokFacade;
    //tablica wciagnieta z bazy danych
    private static List<Dok> downloadedDok;
    //tablica obiektów
    private static HashMap<String,Dok> dokHashTable;
    //tablica kluczy do obiektów
    private static List<String> kluczDOKjsf;
    //tablica obiektów
    private static List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private static List<Dok> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private static List<Dok> obiektDOKmrjsfSel;
    private static Dok dokObject;
    private String dokString;
    private Dok selDokument;
    private DokView dokView;
    private Kl selectedKontr;
    private static String klientNip;
    private HtmlInputText kontrahentNazwa;
    private HtmlInputText kontrahentNIP;
    private HtmlSelectOneListbox pkpirLista;
    
    
    
    public DokDAO() {
        dokHashTable = new HashMap<String, Dok>();
        downloadedDok = new ArrayList<Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        obiektDOKjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSel = new ArrayList<Dok>();
        selDokument = new Dok();
        dokView = new DokView();
        selectedKontr = new Kl();
        pkpirLista = new HtmlSelectOneListbox();
     }
    
    @PostConstruct
    public void init(){
        if(wpisSet.getPodatnikWpisu()!=null){
        Collection c = null;
        try {
            c = dokFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        downloadedDok.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        Iterator it;
        it = c.iterator();
        while (it.hasNext()) {
            Dok tmp = (Dok) it.next();
            kluczDOKjsf.add(tmp.getIdDok().toString());
            obiektDOKjsf.add(tmp);
            if (tmp.getPodatnik().equals(wpisSet.getPodatnikWpisu())) {
                obiektDOKjsfSel.add(tmp);
            }
            dokHashTable.put(tmp.getIdDok().toString(), tmp);
        }
        Iterator itx;
        itx = obiektDOKjsfSel.iterator();
        while (itx.hasNext()) {
            Dok tmpx = (Dok) itx.next();
            String m = wpisSet.getMiesiacWpisu();
            Integer r = wpisSet.getRokWpisu();
            if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
                obiektDOKmrjsfSel.add(tmpx);
            }
        }
        }
    }
           
    
    
    //hashtable z indeksami
    public HashMap<String, Dok> getDokHashTable() {
        return dokHashTable;
    }
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloadedDok
    public List<Dok> getDownloadedDok() {
        return downloadedDok;
    }
    
    //tabela indeksow
    public List<String> getKluczDOKjsf() {
        return kluczDOKjsf;
    }
    //tabela obiektow
     public List<Dok> getObiektDOKjsf() {
        return obiektDOKjsf;
    }
    //String to Object
    public Dok getDokObject(String selectedKey){
        return dokHashTable.get(selectedKey);
    }
    //Object to String
    public String getDokString(Dok selectedObject) {
        return selectedObject.getIdDok().toString();
    }

    public DokFacade getDokFacade() {
        return dokFacade;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public Kl getSelectedKontr() {
        return selectedKontr;
    }

    public void setSelectedKontr(Kl selectedKontr) {
        this.selectedKontr = selectedKontr;
    }

    public String getKlientNip() {
        return klientNip;
    }

    public void setKlientNip(String klientNip) {
        DokDAO.klientNip = klientNip;
    }

    public List<Dok> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<Dok> obiektDOKjsfSel) {
        DokDAO.obiektDOKjsfSel = obiektDOKjsfSel;
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

    public HtmlSelectOneListbox getPkpirLista() {
        return pkpirLista;
    }

    public void setPkpirLista(HtmlSelectOneListbox pkpirLista) {
        this.pkpirLista = pkpirLista;
    }

    public List<Dok> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<Dok> obiektDOKmrjsfSel) {
        DokDAO.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
    }

    public WpisSet getWpisSet() {
        return wpisSet;
    }

    public void setWpisSet(WpisSet wpisSet) {
        this.wpisSet = wpisSet;
    }
 
    
    
    public void refresh(){
        downloadedDok.clear();
        kluczDOKjsf.clear();
        obiektDOKjsf.clear();
        dokHashTable.clear();
        obiektDOKjsfSel.clear();
        obiektDOKmrjsfSel.clear();
        Collection c = null;
        try {
            c = dokFacade.findAll();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
        downloadedDok.addAll(c);
        System.out.println("Pobrano z bazy danych." + c.toString());
        Iterator it;
        it = c.iterator();
        while (it.hasNext()) {
            Dok tmp = (Dok) it.next();
            kluczDOKjsf.add(tmp.getIdDok().toString());
            obiektDOKjsf.add(tmp);
            dokHashTable.put(tmp.getIdDok().toString(), tmp);
            if (tmp.getPodatnik().equals(wpisSet.getPodatnikWpisu())) {
                obiektDOKjsfSel.add(tmp);
            }
            dokHashTable.put(tmp.getIdDok().toString(), tmp);
        }
        Iterator itx;
        itx = obiektDOKjsfSel.iterator();
        while (itx.hasNext()) {
            Dok tmpx = (Dok) itx.next();
            String m = wpisSet.getMiesiacWpisu();
            Integer r = wpisSet.getRokWpisu();
            if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
                obiektDOKmrjsfSel.add(tmpx);
            }
        }

    }
    
    
     public void dodajNowyWpis(){
        try {
            System.out.println("Wpis do bazy zaczynam");
            sformatuj();
            Kl kl = new Kl();
            selDokument.setWprowadzil(wpisSet.getWprowadzil());
            selDokument.setPodatnik(wpisSet.getPodatnikWpisu());
            selDokument.setPkpirR(String.valueOf(wpisSet.getRokWpisu()));
            selDokument.setPkpirM(String.valueOf(wpisSet.getMiesiacWpisu()));
            selDokument.setKontr(selectedKontr);
            selDokument.setStatus("bufor");
            dokFacade.create(selDokument);
            refresh();
            FacesMessage msg = new FacesMessage("Nowy dokument zachowany", selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie utworzony", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            
        }
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
        Kolmn k = new Kolmn();
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
    
    public void sformatuj(){
        //String formatka=null;
        //selDokument.setLogi(selDokument.getIdDok().toLowerCase());
        //selDokument.setImie(selDokument.getImie().substring(0,1).toUpperCase()+selDokument.getImie().substring(1).toLowerCase());
        //selDokument.setNazw(selDokument.getNazw().substring(0,1).toUpperCase()+selDokument.getNazw().substring(1).toLowerCase());
    }
   
    
     public void destroy(Dok wpis) {
        try {
        dokFacade.remove(wpis);
        refresh();
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getIdDok()+" "+e.toString());
        }
    }
     public String postdestroy(){
         return "tablica";
     }
     
     public void edit(RowEditEvent ex){
        try {
            
            //sformatuj();
            dokFacade.edit(selDokument);
            //refresh();
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany "+ex.getObject().toString(), selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
   
}

