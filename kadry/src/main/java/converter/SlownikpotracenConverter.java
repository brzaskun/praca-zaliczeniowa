/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.SlownikpotraceniaFacade;
import entity.Slownikpotracenia;
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
public class SlownikpotracenConverter implements javax.faces.convert.Converter {
    
    private List<Slownikpotracenia> lista;
    @Inject
    private SlownikpotraceniaFacade slownikpotraceniaFacade;
    
    @PostConstruct
    private void init() {
        lista = slownikpotraceniaFacade.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Slownikpotracenia p : lista) {
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
            return String.valueOf(((Slownikpotracenia) value).getId());
        }
    }
}
