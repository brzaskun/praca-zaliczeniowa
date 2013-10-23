/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package params;

import java.io.Serializable;
import java.util.Map;
import javax.faces.context.FacesContext;

/**
 *
 * @author Osito
 */
public class Params  implements Serializable {
    
    public static Object params(String parametr) {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Object wynik = params.get(parametr);;
        if ( wynik == null) {
            return "niewłaściwa nazwa pola";
        } else {
            return wynik;
        }
    }
    
    public static Map<String, String> paramsAll() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        return params;
    }
}
