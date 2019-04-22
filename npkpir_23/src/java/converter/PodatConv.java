/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
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
public class PodatConv implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> lista;
    
    @PostConstruct
    public void init() {
        lista = podatnikDAO.findAll();
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
            try {
                int submittedValue = Integer.parseInt(sub);
                for (Podatnik p : lista) {
                    if (p.getId()==submittedValue) {
                        return p;
                    }
                }
            } catch (Exception exception) {
                
            }
        return null;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {
            return "";
        } else {
            return String.valueOf(((Podatnik) value).getId());
        }
    }

}
