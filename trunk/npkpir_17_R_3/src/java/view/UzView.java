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
import mail.Mail;
import msg.Msg;
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
    private String confPassword;
    private String login;
    private String firstPassword;
       
   
    public UzView() {
        obiektUZjsf = new ArrayList<Uz>();
        selUzytkownik = new Uz();
    }
    
    @PostConstruct
    public void init(){
        Collection c = null;    
        try {
            c = uzDAO.getDownloaded();
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
      

     public void dodaj(){
         System.out.println("Wpis do bazy zaczynam");
         selUzytkownik.setUprawnienia("Noobie");
         selUzytkownik.setLogin(selUzytkownik.getLogin().toLowerCase());
         sformatuj();
         if (validateData()) {
             try {
                 haszuj();
                 uzDAO.dodaj(selUzytkownik);
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejestracja udana. Administrator musi teraz nadac Ci uprawnienia. Nastąpi to w ciągu najbliższej godziny. Dopiero wtedy będzie możliwe zalogowanie się.", selUzytkownik.getLogin());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 Mail.nadajMail(selUzytkownik.getEmail(), selUzytkownik.getLogin());
                 
             } catch (Exception e) {
                 System.out.println(e.getStackTrace().toString());
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzytkownik o takim loginie już istnieje. Wprowadź inny login.", e.getStackTrace().toString());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
             }
         }
    }
     
     public String dodajnowe(){
         System.out.println("Wpis do bazy zaczynam");
         try {
            selUzytkownik = uzDAO.find(login);
            selUzytkownik.setHaslo(firstPassword);
        } catch (Exception e) {
            Msg.msg("e","Podany login: '"+login+"' nie istnieje","formlog1:logowanie");
            login = null;
            return "failure";
        }
         if (validateData()) {
             try {
                 haszuj();
                 uzDAO.edit(selUzytkownik);
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reset hasła udany.", selUzytkownik.getLogin());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 return "failure";
                 
             } catch (Exception e) {
                 System.out.println(e.getStackTrace().toString());
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd. Reset hasła nie udany.", e.getStackTrace().toString());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
             }
         }
         return "failure";
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
            Mail.nadanoUprawniednia(selUzytkownik.getEmail(), selUzytkownik.getLogin(),selUzytkownik.getUprawnienia());
            FacesMessage msg = new FacesMessage("Nowy uzytkownik edytowany View", selUzytkownik.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany View", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
    private boolean validateData() {
        boolean toReturn = true;
        FacesContext ctx = FacesContext.getCurrentInstance();

//        // check emailConfirm is same as email
//        if (!emailConfirm.equals(person.getEmail())) {
//            ctx.addMessage("registerForm:emailConfirm",
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                    msg.getMessage("errorEmailConfirm"), null));
//            toReturn = false;
//        }
        // check passwordConfirm is same as password
        if (!confPassword.equals(selUzytkownik.getHaslo())) {
            ctx.addMessage("registerForm:passwordConfirm",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,"Hasła nie pasuja. Sprawdź.", null));
            toReturn = false;
        }
        return toReturn;
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

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

}
