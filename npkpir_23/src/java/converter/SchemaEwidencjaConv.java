/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.SchemaEwidencja;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.DeklaracjaVatSchemaView;


/**
 *
 * @author Osito
 */

public class SchemaEwidencjaConv implements javax.faces.convert.Converter{
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        DeklaracjaVatSchemaView deklaracjaVatSchemaView = (DeklaracjaVatSchemaView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null,"deklaracjaVatSchemaView"); 
        List<SchemaEwidencja> kl = deklaracjaVatSchemaView.getSchemaewidencjalista();
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String skrot = submittedValue;  
                  for (SchemaEwidencja p : kl) {  
                    if (String.valueOf(p.getId()).equals(skrot)) {  
                        return p;  
                    }  
                }  
              } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid schemaewid"));  
            }  
        }  
          return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((SchemaEwidencja) value).getId());  
        }  
    }  
    
}
