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
    
    private List<Evewidencja> listaEwidencji;

    public EVatwpisFKConv () {
       FacesContext context = FacesContext.getCurrentInstance();
       EVatwpisFKConverterView eVatwpisFKConverterView = (EVatwpisFKConverterView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"eVatwpisFKConverterView");
       listaEwidencji = eVatwpisFKConverterView.getListaEwidencji();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Evewidencja kl = new Evewidencja();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String nazwa = submittedValue;  
                for (Evewidencja p : listaEwidencji) {  
                    if (p.getNazwa().equals(nazwa)) {  
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
            return String.valueOf(((Evewidencja) value).getNazwa());  
        }  
    }  
    
}
