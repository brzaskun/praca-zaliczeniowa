/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KodyzawodowFacade;
import entity.Kodyzawodow;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
public class KodyzawodowConverter implements javax.faces.convert.Converter {
    
    private List<Kodyzawodow> lista;
    @Inject
    private KodyzawodowFacade kodyzawodowFacade;
    
    @PostConstruct
    private void init() {
        lista = kodyzawodowFacade.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
            for (Kodyzawodow p : lista) {
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
            return String.valueOf(((Kodyzawodow) value).getId());
        }
    }
    
    public List<Kodyzawodow> complete(String query) {  
        List<Kodyzawodow> results = new ArrayList<>();
        query = query.toLowerCase(new Locale("pl"));
        for(Kodyzawodow p : lista) {  
            String pl = p.getNazwa().toLowerCase(new Locale("pl"));
            if(pl.contains(query)) {
                results.add(p);
            }
        }
        return results;
    }
}
