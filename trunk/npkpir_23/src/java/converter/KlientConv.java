/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Klienci;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.DokView;
import view.KlView;
import view.KlienciConverterView;
import viewfk.PlanKontConverterView;

/**
 *
 * @author Osito
 */
public class KlientConv implements javax.faces.convert.Converter{
    
    private List<Klienci> listaKlientow;

    public KlientConv() {
       FacesContext context = FacesContext.getCurrentInstance();
       KlienciConverterView klienciConverterView = (KlienciConverterView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"klienciConverterView");
       listaKlientow = klienciConverterView.getListaKlientow();
    }
    
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        Klienci kl = new Klienci();
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
