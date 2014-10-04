/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.Bilansuklad;
import entityfk.Rzisuklad;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.BilansukladView;
import viewfk.RzisukladView;

/**
 *
 * @author Osito
 */
public class BilansukladConv  implements javax.faces.convert.Converter{
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        List<Bilansuklad> kl = BilansukladView.getListaS();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                for (Bilansuklad p : kl) {  
                    if (p.getBilansukladPK().getUklad().equals(skrot)) {  
                        return p;  
                    }  
                }  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid bilansuklad"));  
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
                return String.valueOf(((Bilansuklad) value).getBilansukladPK().getUklad());  
            }  
        } catch (Exception e) {
            return "";
        }
    }   
}
