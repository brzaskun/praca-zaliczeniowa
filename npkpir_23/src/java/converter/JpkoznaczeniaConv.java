/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.JPKOznaczeniaDAO;
import entity.JPKoznaczenia;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class JpkoznaczeniaConv implements javax.faces.convert.Converter, Serializable {

    @Inject
    private JPKOznaczeniaDAO jPKOznaczeniaDAO;
    private List<JPKoznaczenia> lista;
    
    @PostConstruct
    public void init() { //E.m(this);
         lista = jPKOznaczeniaDAO.findAll();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (JPKoznaczenia p : lista) {
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
            return String.valueOf(((JPKoznaczenia) value).getId());
        }
    }

      
    
}
