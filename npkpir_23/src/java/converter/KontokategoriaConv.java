/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.Kontokategoria;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.KontokategoriaPrzypView;

/**
 *
 * @author Osito
 */
public class KontokategoriaConv implements javax.faces.convert.Converter{
    
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
       FacesContext context = FacesContext.getCurrentInstance();
       KontokategoriaPrzypView kontokategoriaPrzypView = (KontokategoriaPrzypView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"kontokategoriaPrzypView");
       List<Kontokategoria> lista = kontokategoriaPrzypView.getLista();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                Integer number = Integer.parseInt(submittedValue);
                  for (Kontokategoria p : lista) {  
                    if (p.getId() == number) {  
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
            return String.valueOf(((Kontokategoria) value).getId());  
        }  
    }  
    
}
