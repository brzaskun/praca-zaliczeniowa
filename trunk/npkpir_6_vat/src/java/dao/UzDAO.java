/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Uz;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import session.UzFacade;
import view.UzView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="UzDAO")
@RequestScoped
public class UzDAO implements Serializable{
     @EJB
    private UzFacade uzFacade;
    //tablica wciagnieta z bazy danych
    private static List<Uz> downloadedUz;
    //tablica obiektów
    private static HashMap<String,Uz> uzHashTable;
    //tablica kluczy do obiektów
    private static List<String> kluczUZjsf;
    //tablica obiektów
    private static List<Uz> obiektUZjsf;
    private static Uz uzObject;
    private String uzString;
    private Uz selUzytkownik;
    private UzView uzView;
    
    
    public UzDAO() {
        uzHashTable = new HashMap<String, Uz>();
        downloadedUz = new ArrayList<Uz>();
        kluczUZjsf = new ArrayList<String>();
        obiektUZjsf = new ArrayList<Uz>();
        selUzytkownik = new Uz();
        uzView = new UzView();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = uzFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedUz.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Uz tmp = (Uz) it.next();
            kluczUZjsf.add(tmp.getLogi().toString());
            obiektUZjsf.add(tmp);
            uzHashTable.put(tmp.getLogi().toString(),tmp);
            }
    }
    
    //hashtable z indeksami
    public HashMap<String, Uz> getUzHashTable() {
        return uzHashTable;
    }
    //pobieranie danych z bazy danych i wklejanie ich do ArrayList o nazwie downloadedUz
    public List<Uz> getDownloadedUz() {
        return downloadedUz;
    }
    
    //tabela indeksow
    public List<String> getKluczUZjsf() {
        return kluczUZjsf;
    }
    //tabela obiektow
     public List<Uz> getObiektUZjsf() {
        return obiektUZjsf;
    }
    //String to Object
    public Uz getUzObject(String selectedKey){
        return uzHashTable.get(selectedKey);
    }
    //Object to String
    public String getUzString(Uz selectedObject) {
        return selectedObject.getLogi();
    }

    public UzFacade getUzFacade() {
        return uzFacade;
    }

    public Uz getSelUzytkownik() {
        return selUzytkownik;
    }

    public void setSelUzytkownik(Uz selUzytkownik) {
        this.selUzytkownik = selUzytkownik;
    }
 
    public void refresh(){
        downloadedUz.clear();
        kluczUZjsf.clear();
        obiektUZjsf.clear();
        uzHashTable.clear();
        Collection c = null;    
        try {
            c = uzFacade.findAll();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            downloadedUz.addAll(c);
            System.out.println("Pobrano z bazy danych."+c.toString());
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Uz tmp = (Uz) it.next();
            kluczUZjsf.add(tmp.getLogi().toString());
            obiektUZjsf.add(tmp);
            uzHashTable.put(tmp.getLogi().toString(),tmp);
            }
    }
    
     public void dodajNowyWpis(){
        try {
            System.out.println("Wpis do bazy zaczynam");
            sformatuj();
            haszuj();
            uzFacade.create(selUzytkownik);
            refresh();
            FacesMessage msg = new FacesMessage("Nowy uzytkownik zachowany", selUzytkownik.getLogi());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie utworzony", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
   
    public void sformatuj(){
        String formatka=null;
        selUzytkownik.setLogi(selUzytkownik.getLogi().toLowerCase());
        selUzytkownik.setImie(selUzytkownik.getImie().substring(0,1).toUpperCase()+selUzytkownik.getImie().substring(1).toLowerCase());
        selUzytkownik.setNazw(selUzytkownik.getNazw().substring(0,1).toUpperCase()+selUzytkownik.getNazw().substring(1).toLowerCase());
    }
    
    public void haszuj() throws NoSuchAlgorithmException{
        String password = getSelUzytkownik().getHaslo();
 
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
 
        byte byteData[] = md.digest();
 
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        getSelUzytkownik().setHaslo(sb.toString());
        System.out.println("Hex format : " + sb.toString());
    }
    
     public void destroy() {
        try {
        uzFacade.remove(selUzytkownik);
        refresh();
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selUzytkownik.getLogi()+" "+e.toString());
        }
    }
     public void edit(){
        try {
            sformatuj();
            uzFacade.edit(selUzytkownik);
            refresh();
            FacesMessage msg = new FacesMessage("Nowy uzytkownik edytowany", selUzytkownik.getLogi());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
}

