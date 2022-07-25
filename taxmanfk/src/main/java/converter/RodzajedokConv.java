/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.RodzajedokDAO;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RodzajedokConv implements javax.faces.convert.Converter, Serializable{
    
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    private List<Rodzajedok> lista;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() { //E.m(this);
        lista = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        try {
            int submittedValue = Integer.parseInt(sub);
              for (Rodzajedok p : lista) {  
                if (p.getId()==submittedValue) {  
                    return p;  
                }  
            }  
          } catch(Exception exception) {  
        }  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Rodzajedok) value).getId());  
        }  
    }  

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
}
