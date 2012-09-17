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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UISelectItems;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import session.DokFacade;
import view.DokView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokDAO")
@RequestScoped
public class DokDAO implements Serializable{
    private WpisSet wpisSet;
    @EJB
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
    private static Dok dokObject;
    private String dokString;
    private Dok selDokument;
    private DokView dokView;
    private KlDAO klDAO;
    private Kl selectedKontr;
    private static String klientNip;
    private Pod pod;
    private HtmlInputText kontrahentNazwa;
    private HtmlInputText kontrahentNIP;
    private HtmlSelectOneListbox pkpirLista;
    
    
    
    public DokDAO() {
        dokHashTable = new HashMap<String, Dok>();
        downloadedDok = new ArrayList<Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        obiektDOKjsfSel = new ArrayList<Dok>();
        selDokument = new Dok();
        dokView = new DokView();
        klDAO = new KlDAO();
        selectedKontr = new Kl();
        pod = new Pod();
        wpisSet = new WpisSet();
        pkpirLista = new HtmlSelectOneListbox();
     }
    
    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = dokFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
    }
            downloadedDok.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            kluczDOKjsf.add(tmp.getIdDok().toString());
            obiektDOKjsf.add(tmp);
            if(tmp.getPodatnik().equals(wpisSet.getPodatnikWpisu())){
            obiektDOKjsfSel.add(tmp);
            }
            dokHashTable.put(tmp.getIdDok().toString(),tmp);
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
 
    
    
    public void refresh(){
        downloadedDok.clear();
        kluczDOKjsf.clear();
        obiektDOKjsf.clear();
        dokHashTable.clear();
        obiektDOKjsfSel.clear();
        Collection c = null;    
        try {
            c = dokFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedDok.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            kluczDOKjsf.add(tmp.getIdDok().toString());
            obiektDOKjsf.add(tmp);
            dokHashTable.put(tmp.getIdDok().toString(),tmp);
            if(tmp.getPodatnik().equals(wpisSet.getPodatnikWpisu())){
            obiektDOKjsfSel.add(tmp);
            }
            dokHashTable.put(tmp.getIdDok().toString(),tmp);
            }
    }
    
     public void dodajNowyWpis(){
            if(selectedKontr.getNIP().equals("nowy klient")){
                
            }
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
   
    
     public void destroy() {
        try {
        dokFacade.remove(selDokument);
        refresh();
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selDokument.getIdDok()+" "+e.toString());
        }
    }
     public void edit(){
        try {
            sformatuj();
            dokFacade.edit(selDokument);
            refresh();
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany", selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     public void autoDate() throws ParseException{
        DateFormat formatter;
        Date date = null;
        try {
            String str_date = "11-07-2007";
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            System.out.println("Today is " + date);
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }
        System.out.println("Pobrano is ") ;
        if(selDokument.getDataWyst().toString().equals("-")){
            DateFormat formatter1;
            Date date1 = null;
            String str_date1 = "11-07-2007";
            formatter1 = new SimpleDateFormat("dd-MM-yyyy");
            date1 = (Date) formatter1.parse(str_date1);
            
        }
        FacesMessage msg = new FacesMessage("Pobrano is ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
     
      public void autoData(){
        DateFormat formatter;
        Date date = null;
        try {
            String str_date = "11-07-2007";
            formatter = new SimpleDateFormat("dd-MM-yyyy");
            date = (Date) formatter.parse(str_date);
            System.out.println("Today is " + date);
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
        }
        System.out.println("ValueChangeListener aktywny ") ;
        FacesMessage msg = new FacesMessage("ValueChangeListener aktywny ");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        
    }
}

