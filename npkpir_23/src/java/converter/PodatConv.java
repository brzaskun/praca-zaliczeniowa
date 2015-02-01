/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.PodatnikDAO;
import entity.Podatnik;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import view.PodatnikView;
import viewfk.PlanKontConverterView;

/**
 *
 * @author Osito
 */
public class PodatConv implements javax.faces.convert.Converter {

    private List<Podatnik> lista;

    public PodatConv() {
        FacesContext context = FacesContext.getCurrentInstance();
        PodatnikView podatnikView = (PodatnikView) context.getELContext().getELResolver().getValue(context.getELContext(), null, "podatnikView");
        lista = podatnikView.getLi();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().isEmpty()) {
            return null;
        } else {
            try {
                String number = submittedValue;
                for (Podatnik p : lista) {
                    if (p.getNazwapelna().equals(number)) {
                        return p;
                    }
                }
            } catch (NumberFormatException exception) {
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
            return String.valueOf(((Podatnik) value).getNazwapelna());
        }
    }

}
