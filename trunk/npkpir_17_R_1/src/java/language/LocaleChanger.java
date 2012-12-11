/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "localeChanger")
@SessionScoped
public class LocaleChanger {
    //mozna zaaplikowac do zmiany jezyka - piesn przyszlosci
    public String polishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        return null;
    }
    public String englishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        return null;
    }
}
