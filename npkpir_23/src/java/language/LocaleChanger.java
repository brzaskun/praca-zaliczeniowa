    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import dao.UzDAO;
import entity.Uz;
import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class LocaleChanger implements Serializable{
    
    private Locale locale;
    @Inject
    private UzDAO uzDAO;

    public LocaleChanger() {
        this.locale = new Locale("pl_PL");
    }

    
    
    
    @PostConstruct
    private void init() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String uzytkownik = request.getRemoteUser();
        if (uzytkownik == null) {
            locale = FacesContext.getCurrentInstance().getExternalContext().getRequestLocale();
        } else {
            ustawLocale(uzytkownik);
        }
        if (locale == null) {
            locale = new Locale("pl");
        }
    }
    
    private void ustawLocale(String uzytk) {
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
    }

    
    //mozna zaaplikowac do zmiany jezyka - piesn przyszlosci
    public String polishAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        this.locale = new Locale("pl");
        return null;
    }
    
    public String polishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        this.locale = new Locale("pl");
        return null;
    }
    
    public String englishAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        this.locale = Locale.ENGLISH;
        return null;
    }
    
    public String englishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        this.locale = Locale.ENGLISH;
        return null;
    }
   
     public String czechAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("cs"));
        this.locale = new Locale("cs");
        return null;
    }
    
    public String czechAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("cs"));
        this.locale = new Locale("cs");
        return null;
    }
    
    public String deutschAction(ActionEvent e){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.GERMAN);
        this.locale = Locale.GERMAN;
        return null;
    }
    
     public String deutschAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.GERMAN);
        this.locale = Locale.GERMAN;
        return null;
    }
     
     

    public Locale getLocale() {
        return locale;
    }

       
}
