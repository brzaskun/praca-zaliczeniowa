/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import daoFK.CechazapisuDAOfk;
import entityfk.Cechazapisu;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class CechaConv implements javax.faces.convert.Converter {

    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private List<Cechazapisu> lista;
    
    @PostConstruct
    public void init() {
         lista = cechazapisuDAOfk.findPodatnikOnly(wpisView.getPodatnikObiekt());
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().isEmpty()) {
            return null;
        } else {
            try {
                String number = submittedValue;
                for (Cechazapisu p : lista) {
                    if (p.getId().equals(Integer.parseInt(number))) {
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
            return String.valueOf(((Cechazapisu) value).getId());
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
}
