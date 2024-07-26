/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import beansDok.OkresBean;
import embeddable.Okres;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ApplicationScoped
public class OkresConverter implements javax.faces.convert.Converter {
    
    private List<Okres> lista;
    @Inject
    private OkresBean okresBean;
    
    @PostConstruct
    private void init() {
        lista = okresBean.getOkresylista();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            for (Okres p : lista) {
                if (p.getRokmc().equals(sub)) {
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
            return String.valueOf(((Okres) value).getRokmc());
        }
    }
    
    
}
