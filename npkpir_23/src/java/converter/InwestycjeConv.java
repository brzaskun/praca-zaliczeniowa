/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Inwestycje;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.InwestycjeView;

/**
 *
 * @author Osito
 */
public class InwestycjeConv implements javax.faces.convert.Converter, Serializable {
    
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
       FacesContext context = FacesContext.getCurrentInstance();
       InwestycjeView inwestycjeView = (InwestycjeView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"inwestycjeView");
       List<Inwestycje> listaKlientow = inwestycjeView.getInwestycjerozpoczete();
        Inwestycje kl = new Inwestycje();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String number = submittedValue;  
                for (Inwestycje p : listaKlientow) {  
                    if (p.getId().equals(Integer.parseInt(number))) {  
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
            return String.valueOf(((Inwestycje) value).getId());  
        }  
    }  
    
}
