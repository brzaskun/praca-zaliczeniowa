/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.PodmiotDAO;
import entity.Podmiot;
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
public class PodmiotConv implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private PodmiotDAO podmiotDAO;
    private List<Podmiot> lista;
    
    @PostConstruct
    public void init() { //E.m(this);
        try {
            lista = podmiotDAO.findAll();
        } catch (Exception e) {}
    }

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
            try {
                int submittedValue = Integer.parseInt(sub);
                if (lista==null) {
                    lista = podmiotDAO.findAll();
                }
                for (Podmiot p : lista) {
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
            return String.valueOf(((Podmiot) value).getId());
        }
    }

}
