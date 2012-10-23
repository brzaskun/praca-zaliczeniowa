/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UzDAO;
import entity.Uz;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name="UzView")
@RequestScoped
public class UzView implements Serializable{
    @Inject
    private UzDAO uzDAO;
    //tablica obiektów
    private static List<Uz> obiektUZjsf;
    private static Uz uzObject;
    private String uzString;
    private Uz selUzytkownik;
       
   
    public UzView() {
        obiektUZjsf = new ArrayList<Uz>();
        selUzytkownik = new Uz();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = uzDAO.getDownloadedUz();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala"+e.toString());
            }
            Iterator it;
            it =  c.iterator();
            while(it.hasNext()){
            Uz tmp = (Uz) it.next();
            obiektUZjsf.add(tmp);
            }
    }
    //tabela obiektow
     public List<Uz> getObiektUZjsf() {
        return obiektUZjsf;
    }
   

    public UzDAO getUzDAO() {
        return uzDAO;
    }

    public Uz getSelUzytkownik() {
        return selUzytkownik;
    }

    public void setSelUzytkownik(Uz selUzytkownik) {
        this.selUzytkownik = selUzytkownik;
    }


     public void dodajNowyWpis(){
        try {
            System.out.println("Wpis do bazy zaczynam");
            sformatuj();
            haszuj();
            uzDAO.dodajNowyWpis(selUzytkownik);
            FacesMessage msg = new FacesMessage("Nowy uzytkownik zachowany View", selUzytkownik.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie utworzony View", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
   
    public void sformatuj(){
        String formatka=null;
        //selUzytkownik.setLogin(selUzytkownik.getLogin().toLowerCase());
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
        uzDAO.destroy(selUzytkownik);
        } catch (Exception e) {
            System.out.println("Nie usnieto "+selUzytkownik.getLogin()+" "+e.toString());
        }
    }
     public void edit(RowEditEvent ex){
        try {
            sformatuj();
            uzDAO.edit(selUzytkownik);
            FacesMessage msg = new FacesMessage("Nowy uzytkownik edytowany View", selUzytkownik.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany View", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
}
