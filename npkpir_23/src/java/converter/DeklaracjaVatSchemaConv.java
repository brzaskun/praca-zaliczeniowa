/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.DeklaracjaVatSchema;
import entity.SchemaEwidencja;
import entity.Srodkikst;
import entityfk.Waluty;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import view.DeklaracjaVatSchemaView;
import viewfk.WalutyViewFK;

/**
 *
 * @author Osito
 */
@FacesConverter(value = "DeklaracjaVatSchemaConv", forClass = SchemaEwidencja.class)
public class DeklaracjaVatSchemaConv implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        DeklaracjaVatSchemaView deklaracjaVatSchemaView = (DeklaracjaVatSchemaView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"deklaracjaVatSchemaView");
        List<DeklaracjaVatSchema> schemyDeklaracjiVat = deklaracjaVatSchemaView.getSchemyDeklaracjiVat();
        DeklaracjaVatSchema p = null;
        if (!value.trim().isEmpty()) {  
            try {  
                String schema = value.trim();  
                  for (DeklaracjaVatSchema s : schemyDeklaracjiVat) {  
                    if (s.getNazwaschemy().equals(schema)) {  
                        p = s;  
                        break;
                    }  
                }  
              } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid schema"));  
              }  
        }
        return p;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
         if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((DeklaracjaVatSchema) value).getNazwaschemy());  
        }  
    }
    
}
