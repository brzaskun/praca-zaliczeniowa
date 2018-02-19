/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.MiejscePrzychodow;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.StowRozrachCzlonkView;

/**
 *
 * @author Osito
 */
public class MiejscePrzychodowConv  implements javax.faces.convert.Converter{
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        StowRozrachCzlonkView stowRozrachCzlonkView = (StowRozrachCzlonkView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null,"stowRozrachCzlonkView"); 
        List<MiejscePrzychodow> kl = stowRozrachCzlonkView.getCzlonkowiestowarzyszenia();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                  for (MiejscePrzychodow p : kl) {
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
            return String.valueOf(((MiejscePrzychodow) value).getId());  
        }  
    }  
}
