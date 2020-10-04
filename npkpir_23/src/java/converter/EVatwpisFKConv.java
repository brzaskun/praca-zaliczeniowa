/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import entity.Evewidencja;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.EVatwpisFKConverterView;

/**
 *
 * @author Osito
 */
public class EVatwpisFKConv  implements javax.faces.convert.Converter{
    
    
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
       FacesContext context = FacesContext.getCurrentInstance();
       EVatwpisFKConverterView eVatwpisFKConverterView = (EVatwpisFKConverterView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"eVatwpisFKConverterView");
       List<Evewidencja> listaEwidencji = eVatwpisFKConverterView.getListaEwidencji();
       if (submittedValue!=null) {
            Integer idew = Integer.parseInt(submittedValue);
            try {  
                for (Evewidencja p : listaEwidencji) {  
                    if (p.getId()==idew) {  
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
            return String.valueOf(((Evewidencja) value).getId());  
        }  
    }  
    
}
