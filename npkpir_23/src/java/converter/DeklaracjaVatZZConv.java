/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.DeklaracjaVatZZ;
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
@FacesConverter(value = "DeklaracjaVatZZConv", forClass = DeklaracjaVatZZ.class)
public class DeklaracjaVatZZConv implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DeklaracjaVatZZPowodPrzypView deklaracjaVatSchemaView = (DeklaracjaVatZZPowodPrzypView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"deklaracjaVatZZPowodPrzypView");
        DeklaracjaVatZZ p = null;
        if (!value.equals("")) {
            List<DeklaracjaVatZZ> schemyDeklaracjiVatZZ = deklaracjaVatSchemaView.getZalaczniki();
            if (!value.trim().isEmpty()) {  
                try {  
                    String schema = value.trim();  
                      for (DeklaracjaVatZZ s : schemyDeklaracjiVatZZ) {  
                        if (s.getNazwaschemy().equals(schema)) {  
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
            return String.valueOf(((DeklaracjaVatZZ) value).getNazwaschemy());  
        }  
    }
    
}
