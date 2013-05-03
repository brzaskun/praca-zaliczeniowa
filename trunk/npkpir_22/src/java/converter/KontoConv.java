/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KontoDAO;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import sun.net.www.content.text.plain;
import view.PlanKontView;

/**
 *
 * @author Osito
 */
public class KontoConv implements javax.faces.convert.Converter{
    
    @Inject private KontoDAO kontoDAO;
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        List<Konto> konta = PlanKontView.getWykazkontS();
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                String number = submittedValue;  
                for (Konto p : konta ){  
                    if (p.getPelnynumer().equals(number)) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid klient"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Konto) value).getPelnynumer());  
        }  
    }  
    
}
