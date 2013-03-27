/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Srodkikst;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.KlView;
import view.SrodkikstView;

/**
 *
 * @author Osito
 */
public class SrodkiConv implements javax.faces.convert.Converter{
    
    
    
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        SrodkikstView srodkikstView = new SrodkikstView();
        List<Srodkikst> kl = srodkikstView.getLista();
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                String number = submittedValue;  
  
                for (Srodkikst p : kl) {  
                    if (p.getNazwa().equals(number)) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid klient"));  
            }  
        }  
  
        return null;  
    }  
  
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Srodkikst) value).getNazwa());  
        }  
    }  
    
}
