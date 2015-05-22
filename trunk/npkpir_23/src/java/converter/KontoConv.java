/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entityfk.Konto;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import viewfk.PlanKontCompleteView;

/**
 *
 * @author Osito
 */
public class KontoConv implements javax.faces.convert.Converter{

     
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.length() > 2) {
            FacesContext context = FacesContext.getCurrentInstance();
            PlanKontCompleteView planKontCompleteView = (PlanKontCompleteView) context.getELContext().getELResolver().getValue(context.getELContext(), null, "planKontCompleteView");
            //System.out.println("Wywołanie KotoConv getAsObject()");
            List<Konto> konta = planKontCompleteView.getListakont();
            try {//robie to bo jak edytuje dokument to PlanKontView nie jest zainicjowany i WykazkontS jest pusty
                if (submittedValue.trim().isEmpty()) {
                    return null;
                } else {
                    try {
                        String number = submittedValue.split(" ")[0];
                        for (Konto p : konta) {
                            if (p.getPelnynumer().equals(number)) {
                                return p;
                            }
                        }

                    } catch (NumberFormatException exception) {
                        throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid klient"));
                    }
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Konto) value).getPelnynumer());  
        }  
    }  
    
}
