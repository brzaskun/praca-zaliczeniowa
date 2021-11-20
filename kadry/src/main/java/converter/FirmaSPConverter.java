/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import DAOsuperplace.FirmaFacade;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Firma;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class FirmaSPConverter implements javax.faces.convert.Converter, Serializable {
    
    private List<Firma> lista;
    @Inject
    private FirmaFacade firmaFacade;
    
    @PostConstruct
    private void init() {
        lista = firmaFacade.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Firma p : lista) {
                if (p.getFirSerial()==submittedValue) {
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
            return String.valueOf(((Firma) value).getFirSerial());
        }
    }
}
