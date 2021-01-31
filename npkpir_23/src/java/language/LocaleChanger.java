    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import dao.UzDAO;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class LocaleChanger implements Serializable{
    
    private Locale locale;
    @Inject
    private UzDAO uzDAO;

    public LocaleChanger() {
        this.locale = new Locale("pl_PL");
    }

    
    
    
    @PostConstruct
    private void init() { //E.m(this);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uzytkownik = request.getRemoteUser();
        if (uzytkownik == null) {
            locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        } else {
            ustawLocale(uzytkownik);
        }
        if (locale == null) {
            locale = new Locale("pl_PL");
        }
    }
    
    private void ustawLocale(String uzytk) {
        try {
            Uz uz = uzDAO.findUzByLogin(uzytk);
            if (uz != null) {
                switch (uz.getLocale()) {
                    default:
                    case "pl":
                        polishAction();
                        break;
                    case "de":
                        deutschAction();
                        break;
                    case "en":
                        englishAction();
                        break;
                    case "cz":
                        czechAction();
                        break;
                }
            } else {
                polishAction();
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    
    //mozna zaaplikowac do zmiany jezyka - piesn przyszlosci
    public void polishAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        this.locale = new Locale("pl");
        
    }
    
    public void polishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        this.locale = new Locale("pl");
        
    }
    
    public void englishAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        this.locale = Locale.ENGLISH;
        
    }
    
    public void englishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        this.locale = Locale.ENGLISH;
        
    }
   
     public void czechAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("cs"));
        this.locale = new Locale("cs");
        
    }
    
    public void czechAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("cs"));
        this.locale = new Locale("cs");
        
    }
    
    public void deutschAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.GERMAN);
        this.locale = Locale.GERMAN;
        
    }
    
     public void deutschAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.GERMAN);
        this.locale = Locale.GERMAN;
        
    }
     
     

    public Locale getLocale() {
        return locale;
    }

       
}
