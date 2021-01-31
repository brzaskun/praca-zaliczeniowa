/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.SkladkaStowarzyszenie;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.SkladkaStowarzyszenieView;

/**
 *
 * @author Osito
 */
public class SkladkaStowarzyszenieConv implements javax.faces.convert.Converter, Serializable {
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        SkladkaStowarzyszenieView skladkaStowarzyszenieView = (SkladkaStowarzyszenieView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null,"skladkaStowarzyszenieView"); 
        List<SkladkaStowarzyszenie> kl = skladkaStowarzyszenieView.getSkladkaStowarzyszenieLista();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                  for (SkladkaStowarzyszenie p : kl) {  
                    Integer w = Integer.parseInt(submittedValue);
                    if (p.getId() == w) {  
                        return p;  
                    }  
                }  
              } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid rodzajdok"));  
            }  
        }  
          return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((SkladkaStowarzyszenie) value).getId());  
        }  
    }  
    
}
