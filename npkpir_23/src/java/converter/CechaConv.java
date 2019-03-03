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
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Cechazapisu p : lista) {
                if (p.getId()==submittedValue) {
                    return p;
                }
            }
        } catch (NumberFormatException exception) {
            
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
