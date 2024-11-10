package converter;

import dao.FakturaDAO;
import entity.Faktura;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;

@Named
@ViewScoped
public class FakturaConverter implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private WpisView wpisView;
    private List<Faktura> lista;
    
    @PostConstruct
    public void init() { //E.m(this);
         lista = fakturaDAO.findbyPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }

    @Override
    public Faktura getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        int id = Integer.valueOf(value);
        for (Faktura p : lista) {
              if (p.getId()==id) {
                  return p;
              }
         }
        return null;
    }

    
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            return ((Faktura) value).getId() != null ? String.valueOf(((Faktura) value).getId()) : null;  
        }  
    }  
    
}
