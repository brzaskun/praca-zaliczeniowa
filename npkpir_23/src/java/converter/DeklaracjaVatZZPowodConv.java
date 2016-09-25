/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.DeklaracjaVatZZ;
import entity.DeklaracjaVatZZPowod;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import view.DeklaracjaVatZZPowodPrzypView;

/**
 *
 * @author Osito
 */
@FacesConverter(value = "DeklaracjaVatZZPowodConv", forClass = DeklaracjaVatZZ.class)
public class DeklaracjaVatZZPowodConv implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DeklaracjaVatZZPowodPrzypView deklaracjaVatSchemaView = (DeklaracjaVatZZPowodPrzypView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"deklaracjaVatZZPowodPrzypView");
        DeklaracjaVatZZPowod p = null;
        if (!value.equals("")) {
            List<DeklaracjaVatZZPowod> schemyDeklaracjiVatZZpowod = deklaracjaVatSchemaView.getPowodysource();
            if (!value.trim().isEmpty()) {  
                try {  
                    String schema = value.trim();  
                      for (DeklaracjaVatZZPowod s : schemyDeklaracjiVatZZpowod) {  
                        if (s.getId().toString().equals(schema)) {  
                            p = s;  
                            break;
                        }  
                    }  
                  } catch(NumberFormatException exception) {  
                    throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid schema"));  
                  }  
            }
        }
        return p;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         if (value == null || value.equals("")) {  
            return "wybierz";  
        } else {  
            return String.valueOf(((DeklaracjaVatZZPowod) value).getId());  
        }  
    }
    
}
