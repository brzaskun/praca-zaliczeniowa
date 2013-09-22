/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Dokfk;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.DokfkView;

/**
 *
 * @author Osito
 */
public class DokfkConv implements javax.faces.convert.Converter{
    
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        List<Dokfk> dokument = DokfkView.getDokfklistS();
        if (submittedValue.trim().equals("")) {  
            return null;  
        } else {  
            try {  
                int number = Integer.parseInt(submittedValue);  
                for (Dokfk p : dokument ){  
                    if (p.getIddok()== number) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid dokument"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
         if (value == null || value.equals("")) {  
            return "";  
        } else if  (!(value instanceof Dokfk)) {  
            return "";  
        } else {  
            try {
                return (((Dokfk) value).getIddok()).toString();  
            } catch (Exception e){
                return "";
            }
        }  
    }  
    
}
