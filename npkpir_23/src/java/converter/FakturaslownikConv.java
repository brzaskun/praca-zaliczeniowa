/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.FakturaSlownikDAO;
import entity.Fakturaslownik;
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
public class FakturaslownikConv implements javax.faces.convert.Converter, Serializable {

    @Inject
    private FakturaSlownikDAO fakturaSlownikDAO;
    @Inject
    private WpisView wpisView;
    private List<Fakturaslownik> lista;
    
    @PostConstruct
    public void init() { //E.m(this);
         lista = fakturaSlownikDAO.findByPodatnik(wpisView.getPodatnikObiekt());
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Fakturaslownik p : lista) {
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
        } if (value != null && !value.getClass().getName().equals("entity.Fakturaslownik")) {
            return value.toString();
        } else {
            return String.valueOf(((Fakturaslownik) value).getId());
        }
    }

       
    
}
