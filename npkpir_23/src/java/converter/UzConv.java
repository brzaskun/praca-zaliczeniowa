/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.UzDAO;
import entity.Uz;
import error.E;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class UzConv implements javax.faces.convert.Converter{
    @Inject
    private UzDAO uzDAO;
    private List<Uz> uzytkownicy;
    
    @PostConstruct
    public void init() { //E.m(this);
        uzytkownicy = uzDAO.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
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
