/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.RodzajnieobecnosciFacade;
import entity.Rodzajnieobecnosci;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class RodzajnieobecnosciConverter implements javax.faces.convert.Converter {
    
    private List<Rodzajnieobecnosci> lista;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    
    @PostConstruct
    private void init() {
        lista = rodzajnieobecnosciFacade.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Rodzajnieobecnosci p : lista) {
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
            return String.valueOf(((Rodzajnieobecnosci) value).getId());
        }
    }
}
