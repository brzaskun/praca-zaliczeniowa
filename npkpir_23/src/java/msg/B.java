/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package msg;

import error.E;
import java.util.ResourceBundle;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;

/**
 *
 * @author Osito
 */
@Stateless
public class B {
    
    public static String b(String key) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "mgs");
            return bundle.getString(key);
        } catch (Exception e) {
            E.e(e, "Nie ma klucza "+key);
            return "";
        }
    }
    
}
