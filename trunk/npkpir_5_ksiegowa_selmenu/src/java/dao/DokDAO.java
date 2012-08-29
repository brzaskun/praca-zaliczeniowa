/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Kl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import session.DokFacade;
import view.DokView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="DokDAO")
@RequestScoped
public class DokDAO implements Serializable{
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
    private static Dok dokObject;
    private String dokString;
    private Dok selDokument;
    private DokView dokView;
    private KlDAO klDAO;
    
    
    public DokDAO() {
        dokHashTable = new HashMap<String, Dok>();
        downloadedDok = new ArrayList<Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        selDokument = new Dok();
        dokView = new DokView();
        klDAO = new KlDAO();
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
 
    public void refresh(){
        downloadedDok.clear();
        kluczDOKjsf.clear();
        obiektDOKjsf.clear();
        dokHashTable.clear();
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
            }
    }
    
     public void dodajNowyWpis(){
        try {
            System.out.println("Wpis do bazy zaczynam");
            sformatuj();
            Kl kl = klDAO.toObject();
            selDokument.setKontr(kl);
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
}

