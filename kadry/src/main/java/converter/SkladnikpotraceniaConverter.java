/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.SkladnikPotraceniaFacade;
import entity.Skladnikpotracenia;
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
public class SkladnikpotraceniaConverter implements javax.faces.convert.Converter {
    
    private List<Skladnikpotracenia> lista;
    @Inject
    private SkladnikPotraceniaFacade skladnikPotraceniaFacade;
    
    @PostConstruct
    private void init() {
        lista = skladnikPotraceniaFacade.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Skladnikpotracenia p : lista) {
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
            return String.valueOf(((Skladnikpotracenia) value).getId());
        }
    }
}
