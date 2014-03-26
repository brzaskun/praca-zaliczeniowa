/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.Rzisuklad;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.RzisukladView;

/**
 *
 * @author Osito
 */
public class RzisukladConv  implements javax.faces.convert.Converter{
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        List<Rzisuklad> kl = RzisukladView.getListaS();
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                for (Rzisuklad p : kl) {  
                    if (p.getRzisukladPK().getUklad().equals(skrot)) {  
                        return p;  
                    }  
                }  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid rzisuklad"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        try {
            if (value == null || value.equals("")) {  
                return "";  
            } else {  
                return String.valueOf(((Rzisuklad) value).getRzisukladPK().getUklad());  
            }  
        } catch (Exception e) {
            return "";
        }
    }   
}
