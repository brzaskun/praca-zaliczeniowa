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
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
       FacesContext context = FacesContext.getCurrentInstance();
       KlienciConverterView klienciConverterView = (KlienciConverterView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"klienciConverterView");
       List<Klienci> listaKlientow = klienciConverterView.getListaKlientow();
        int submittedValue = Integer.parseInt(sub);
        if (submittedValue==-2){  
            listaKlientow.add(klienciConverterView.getKlientautomat());
        } 
        try {  
            for (Klienci p : listaKlientow) {  
                if (p.getId()==submittedValue) {  
                    return p;  
                }  
            }  
        } catch(NumberFormatException exception) {  
            return null;
        }  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            return ((Klienci) value).getId() != null ? String.valueOf(((Klienci) value).getId()) : null;  
        }  
    }  
    
}
