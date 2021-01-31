/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.CechazapisuDAOfk;
import entityfk.Cechazapisu;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class CechaConv implements javax.faces.convert.Converter, Serializable {

    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject
    private WpisView wpisView;
    private List<Cechazapisu> lista;
    
    @PostConstruct
    public void init() { //E.m(this);
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
