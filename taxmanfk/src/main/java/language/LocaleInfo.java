/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package language;

import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.inject.Singleton;

/**
 *
 * @author Osito
 */
@Singleton
public class LocaleInfo {
    public static String getLocale() {
         Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
         return locale.getLanguage();
    }
}
