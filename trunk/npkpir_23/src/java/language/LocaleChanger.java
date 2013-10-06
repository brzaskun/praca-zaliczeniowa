    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class LocaleChanger {
    //mozna zaaplikowac do zmiany jezyka - piesn przyszlosci
    public String polishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("pl"));
        RequestContext.getCurrentInstance().execute("plbuttonOn();");
        return null;
    }
    public String englishAction(){
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
        RequestContext.getCurrentInstance().execute("enbuttonOn();");
        return null;
    }
}
