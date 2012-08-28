/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Kontr;
import java.io.Serializable;
import java.util.*;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.KontrFacade;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KontrDAO")
@RequestScoped
public class KontrDAO implements Serializable{
     @EJB
    private KontrFacade kontrFacade;
    //tablica wciagnieta z bazy danych
    private static List<Kontr> downloadedKontr;
    //tablica obiektów
    private static HashMap<String,Kontr> kontrHashTable;
    //tablica kluczy do obiektów
    private static List<String> kluczDOKjsf;
    //tablica obiektów
    private static List<Kontr> obiektDOKjsf;
    private static Kontr kontrObject;
    private String kontrString;
    private Kontr selKontrahent;
    
    
    public KontrDAO() {
        kontrHashTable = new HashMap<String, Kontr>();
        downloadedKontr = new ArrayList<Kontr>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Kontr>();
        selKontrahent = new Kontr();
        kontrFacade = new KontrFacade();
       
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = kontrFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu Kontrahentow. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedKontr.addAll(c);
            System.out.println("Pobrano Kontr z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Kontr tmp = (Kontr) it.next();
            kluczDOKjsf.add(tmp.getNip().toString());
            obiektDOKjsf.add(tmp);
            kontrHashTable.put(tmp.getNip().toString(),tmp);
            }
             System.out.println("W hasztable jest."+kontrHashTable.toString());
    }
    
    //hashtable z indeksami
    public HashMap<String, Kontr> getKontrHashTable() {
        return kontrHashTable;
    }
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloadedKontr
    public List<Kontr> getDownloadedKontr() {
        return downloadedKontr;
    }
    
    //tabela indeksow
    public List<String> getKluczDOKjsf() {
        return kluczDOKjsf;
    }
    //tabela obiektow
     public List<Kontr> getObiektDOKjsf() {
        return obiektDOKjsf;
    }
    //String to Object
    public Kontr getKontrObject(String selectedKey){
        System.out.println("Klucz do wyszukania kontrahenta: "+selectedKey);
        refresh();
        if(kontrHashTable.size()==0){
           System.out.println("Hashlista kont pusta");
        }System.out.println("Hashlista niepusta?"+kontrHashTable.toString());
        Kontr pobrano = kontrHashTable.get(selectedKey);
        System.out.println("Zlaneziono obiekt: "+pobrano.getNip()+" "+pobrano.getMiasto());
        return kontrHashTable.get(selectedKey);
    }
    //Object to String
    public String getKontrString(Kontr selectedObject) {
        return selectedObject.getNip();
    }

    public KontrFacade getKontrFacade() {
        return kontrFacade;
    }

    public Kontr getSelKontrytkownik() {
        return selKontrahent;
    }

    public void setSelKontrytkownik(Kontr selKontrahent) {
        this.selKontrahent = selKontrahent;
    }

    public void refresh(){

        downloadedKontr.clear();
        kluczDOKjsf.clear();
        obiektDOKjsf.clear();
        kontrHashTable.clear();
        Collection c = null;    
        try {
            c = kontrFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w odswiezaniu Kontrahentow bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedKontr.addAll(c);
            System.out.println("Odsiwezono kontrahentow z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Kontr tmp = (Kontr) it.next();
            kluczDOKjsf.add(tmp.getNip().toString());
            obiektDOKjsf.add(tmp);
            kontrHashTable.put(tmp.getNip().toString(),tmp);
            }
    }
    
     public void dodajNowyWpis(){
        try {
            System.out.println("Wpis do bazy zaczynam "+selKontrahent.getNip()+" "+selKontrahent.getNpelna());
            sformatuj();
            kontrFacade.create(selKontrahent);
            refresh();
            FacesMessage msg = new FacesMessage("Nowy kontrytkownik zachowany", selKontrahent.getNip());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Kontrahent nie utworzony", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
   
    public void sformatuj(){
        String formatka=null;
//        selKontrahent.setLogi(selKontrahent.getNip().toLowerCase());
//        selKontrahent.setImie(selKontrahent.getNpelna().substring(0,1).toUpperCase()+selKontrahent.getNpelna().substring(1).toLowerCase());
//        selKontrahent.setNazw(selKontrahent.getNazw().substring(0,1).toUpperCase()+selKontrahent.getNazw().substring(1).toLowerCase());
//        System.out.println("sformatowane "+selKontrahent.getNip());
    }
    
    
     public void destroy() {
        try {
        kontrFacade.remove(selKontrahent);
        refresh();
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selKontrahent.getNip()+" "+e.toString());
        }
    }
     public void edit(){
        try {
            sformatuj();
            kontrFacade.edit(selKontrahent);
            refresh();
            FacesMessage msg = new FacesMessage("Nowy kontrahent edytowany", selKontrahent.getNip());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Kontrahent nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
      public void dodajEdytuj(){
          Kontr obecny = null;
          obecny = kontrFacade.find(selKontrahent.getNip());
          if(obecny!=null){
              edit();
          } else {
              dodajNowyWpis();
          }
      }
    
    }

