/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.UzDAO;
import entity.Uz;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;
import view.UzView;

/**
 *
 * @author Osito
 */
public class UzConv implements javax.faces.convert.Converter{
    
   
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        FacesContext context = FacesContext.getCurrentInstance();
        UzView ukladBRView = (UzView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"UzView");
        List<Uz> uzytkownicy = ukladBRView.getListaUzytkownikow();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                for (Uz p : uzytkownicy) {  
                    if (p.getLogin().equals(submittedValue)) {  
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
            return String.valueOf(((Uz) value).getLogin());  
        }  
    }  
    
}
