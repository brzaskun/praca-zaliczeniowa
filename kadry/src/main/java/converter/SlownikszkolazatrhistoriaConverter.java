/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.SlownikszkolazatrhistoriaFacade;
import entity.Slownikszkolazatrhistoria;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class SlownikszkolazatrhistoriaConverter implements javax.faces.convert.Converter, Serializable {
    
    private List<Slownikszkolazatrhistoria> lista;
    @Inject
    private SlownikszkolazatrhistoriaFacade slownikszkolazatrhistoriaFacade;
    
    @PostConstruct
    private void init() {
        lista = slownikszkolazatrhistoriaFacade.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Slownikszkolazatrhistoria p : lista) {
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
            return String.valueOf(((Slownikszkolazatrhistoria) value).getId());
        }
    }
}
