/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.Tabelanbp;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.TabelaNBPView;

/**
 *
 * @author Osito
 */

public class TabelaNBPConv implements javax.faces.convert.Converter{
    
    
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        FacesContext context = FacesContext.getCurrentInstance();
        TabelaNBPView tabelaNBPView = (TabelaNBPView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"tabelaNBPView");
        List<Tabelanbp> listanpb = tabelaNBPView.getListanpb();
        try {  
            for (Tabelanbp p : listanpb) {  
                if (String.valueOf(p.getIdtabelanbp()).equals(submittedValue)) {  
                    return p;  
                }  
            }  

        } catch(NumberFormatException exception) {  
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid tabelanbp"));  
        }  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Tabelanbp) value).getIdtabelanbp());  
        }  
    }  
    
    
}
