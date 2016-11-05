/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import entity.Evpozycja;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.EvpozycjaView;

/**
 *
 * @author Osito
 */
public class EvpozycjaConv implements javax.faces.convert.Converter {

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        EvpozycjaView evpozycjaView = (EvpozycjaView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "evpozycjaView");
        List<Evpozycja> kl = evpozycjaView.getLista();
        if (submittedValue.trim().isEmpty()) {
            return null;
        } else {
            try {
                for (Evpozycja p : kl) {
                    if (p.getNazwapola().equals(submittedValue)) {
                        return p;
                    }
                }
            } catch (NumberFormatException exception) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid Evpozycja"));
            }
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Evpozycja) value).getNazwapola());
        }
    }
}
