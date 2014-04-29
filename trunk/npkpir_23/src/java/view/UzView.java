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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import mail.Mail;
import msg.Msg;
import org.primefaces.event.RowEditEvent;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean(name="UzView")
@ViewScoped
public class UzView implements Serializable{
    //tablica obiektów
    private List<Uz> obiektUZjsf;
    private List<Uz> obiektUZjsfselected;
    private Uz uzObject;
    @Inject
    private UzDAO uzDAO;
    private String uzString;
    @Inject private Uz selUzytkownik;
    private String confPassword;
    private String login;
    private String firstPassword;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
       
   
    public UzView() {
        obiektUZjsf = new ArrayList<>();
    }
    
    @PostConstruct
    public void init(){
        List<Uz> c = new ArrayList<>();;
        try {
            c.addAll(uzDAO.findAll());
            } catch (Exception e) {
            }
            for(Uz p : c){
            obiektUZjsf.add(p);
            }
        selUzytkownik = wpisView.getWprowadzil();
    }
      

     public void dodaj(){
         selUzytkownik.setUprawnienia("Noobie");
         selUzytkownik.setLogin(selUzytkownik.getLogin().toLowerCase());
         selUzytkownik.setIloscwierszy("12");
         selUzytkownik.setTheme("redmond");
         sformatuj();
         if (validateData()) {
             try {
                 haszuj();
                 uzDAO.dodaj(selUzytkownik);
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Rejestracja udana. Administrator musi teraz nadac Ci uprawnienia. Nastąpi to w ciągu najbliższej godziny. Dopiero wtedy będzie możliwe zalogowanie się.", selUzytkownik.getLogin());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 Mail.nadajMailRejestracjaNowegoUzera(selUzytkownik.getEmail(), selUzytkownik.getLogin());
                 
             } catch (Exception e) {
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzytkownik o takim loginie już istnieje. Wprowadź inny login.", e.getStackTrace().toString());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
             }
         }
    }
     
     public String dodajnowe(){
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
    }
    
     public void destroy(Uz uzytkownik) {
        try {
        uzDAO.destroy(uzytkownik);
        obiektUZjsf.remove(uzytkownik);
        obiektUZjsfselected.remove(uzytkownik);
        Msg.msg("i", "Usunąłem użytkownika "+uzytkownik.getLogin());
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie usunąłem użytkownika "+uzytkownik.getLogin());
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
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany View", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
     }
     
     public void wybranoUzytkownika() {
         Msg.msg("Wybrano użytkownika: "+selUzytkownik.getLogin());
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
    
    public void zmienustawienia(){
        try{
            uzDAO.edit(selUzytkownik);
            Msg.msg("i","Dane zmienione dla:"+selUzytkownik.getLogin(),"form:mess_add");
        } catch (Exception e){
            Msg.msg("e","Błąd! Dane nie zmienione","form:mess_add");
        }
    }
    
    public void sprawdzloginduplikat(AjaxBehaviorEvent e){
       String login = (String) Params.params("pole:login");
       try {
           Uz user = uzDAO.find(login);
           if ( user == null) {
           } else {
               Msg.msg("w", "Użytkownik o takim loginie już istnienie. Wpisz inny.");
           }
       } catch (Exception ef) {
           //Msg.msg("e", "Nie można sprawdzić loginu. Wsytąpił błąd!");
       }
    }
    

    
    //<editor-fold defaultstate="collapsed" desc="comment">
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
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    

    public List<Uz> getObiektUZjsfselected() {
        return obiektUZjsfselected;
    }

    public void setObiektUZjsfselected(List<Uz> obiektUZjsfselected) {
        this.obiektUZjsfselected = obiektUZjsfselected;
    }
    
    
    
    //</editor-fold>
   
}
