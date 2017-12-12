/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Klienci;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.KlienciConverterView;

/**
 *
 * @author Osito
 */
public class KlientConv implements javax.faces.convert.Converter{
    
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
       FacesContext context = FacesContext.getCurrentInstance();
       KlienciConverterView klienciConverterView = (KlienciConverterView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"klienciConverterView");
       List<Klienci> listaKlientow = klienciConverterView.getListaKlientow();
        Klienci kl = new Klienci();
        if (submittedValue.equals("dodaj klienta automatycznie")){  
            listaKlientow.add(klienciConverterView.getKlientautomat());
        } 
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {
            try {  
                String number = submittedValue;  
                for (Klienci p : listaKlientow) {  
                    if (p.getNpelna().equals(number)) {  
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
            return String.valueOf(((Klienci) value).getNpelna());  
        }  
    }  
    
}
