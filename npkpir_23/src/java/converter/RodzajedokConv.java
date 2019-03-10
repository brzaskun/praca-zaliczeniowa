/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.RodzajedokDAO;
import entity.Rodzajedok;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import view.RodzajedokView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RodzajedokConv implements javax.faces.convert.Converter{
    
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    private List<Rodzajedok> lista;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        lista = rodzajedokDAO.findListaPodatnikRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
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
