/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.RodzajCzlonkostwa;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.RodzajeCzlonkostwaView;

/**
 *
 * @author Osito
 */
public class RodzajCzlonkostwaConv  implements javax.faces.convert.Converter, Serializable {
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        RodzajeCzlonkostwaView rodzajeCzlonkostwaView = (RodzajeCzlonkostwaView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null,"rodzajeCzlonkostwaView"); 
        List<RodzajCzlonkostwa> kl = rodzajeCzlonkostwaView.getRodzajCzlonkostwaLista();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                  for (RodzajCzlonkostwa p : kl) {
                    Integer w = Integer.parseInt(submittedValue);
                    if (p.getId().equals(w)) {  
                        return p;  
                    }  
                }  
              } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid RodzajCzlonkostwa"));  
            }  
        }  
          return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((RodzajCzlonkostwa) value).getId());  
        }  
    }  
}
