/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.JPKOznaczeniaDAO;
import entity.JPKoznaczenia;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class JPKoznaczeniaConv implements javax.faces.convert.Converter {

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
