/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KlienciDAO;
import entity.Klienci;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import view.KlienciConverterView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KlientConv implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KlienciDAO klienciDAO;
    @ManagedProperty(value = "#{klienciConverterView}")
    private KlienciConverterView klienciConverterView;
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        List<Klienci> listaKlientow = new ArrayList<>();
        Klienci znaleziony = klienciDAO.findAllReadOnlyID(sub);
        if (znaleziony != null) {
            listaKlientow.add(znaleziony);
        }
        int submittedValue = Integer.parseInt(sub);
        if (submittedValue==-2){  
            listaKlientow.add(klienciConverterView.getKlientautomat());
        }
        try {
            return listaKlientow.stream().filter(p -> p.getId().equals(submittedValue)).findAny().orElse(null);
//            for (Klienci p : listaKlientow) {  
//                if (p.getId()==submittedValue) {  
//                    return p;  
//                }  
//            }  
        } catch (NumberFormatException exception) {
            return null;
        }
    }
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            return ((Klienci) value).getId() != null ? String.valueOf(((Klienci) value).getId()) : null;  
        }  
    }  

    public KlienciConverterView getKlienciConverterView() {
        return klienciConverterView;
    }

    public void setKlienciConverterView(KlienciConverterView klienciConverterView) {
        this.klienciConverterView = klienciConverterView;
    }
    
    
}
