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
    private static List<String> kluczUZjsf;
    //tablica obiektów
    private static List<Kontr> obiektUZjsf;
    private static Kontr kontrObject;
    private String kontrString;
    private Kontr selKontrahent;
    
    
    public KontrDAO() {
        kontrHashTable = new HashMap<String, Kontr>();
        downloadedKontr = new ArrayList<Kontr>();
        kluczUZjsf = new ArrayList<String>();
        obiektUZjsf = new ArrayList<Kontr>();
        selKontrahent = new Kontr();
       
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = kontrFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedKontr.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Kontr tmp = (Kontr) it.next();
            kluczUZjsf.add(tmp.getNip().toString());
            obiektUZjsf.add(tmp);
            kontrHashTable.put(tmp.getNip().toString(),tmp);
            }
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
    public List<String> getKluczUZjsf() {
        return kluczUZjsf;
    }
    //tabela obiektow
     public List<Kontr> getObiektUZjsf() {
        return obiektUZjsf;
    }
    //String to Object
    public Kontr getKontrObject(String selectedKey){
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
        kluczUZjsf.clear();
        obiektUZjsf.clear();
        kontrHashTable.clear();
        Collection c = null;    
        try {
            c = kontrFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedKontr.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Kontr tmp = (Kontr) it.next();
            kluczUZjsf.add(tmp.getNip().toString());
            obiektUZjsf.add(tmp);
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

