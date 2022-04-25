/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KlienciDAO;
import entity.Klienci;
import java.io.Serializable;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import view.KlienciConverterView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KlientConvNoRO implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private KlienciConverterView klienciConverterView;
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        Klienci zwrot = null;
        try {
            int submittedValue = Integer.parseInt(sub);
            if (submittedValue==-2){  
                zwrot = klienciConverterView.getKlientautomat();
            } else {
                zwrot = klienciDAO.findAllID(sub);
            }
//            for (Klienci p : listaKlientow) {  
//                if (p.getId()==submittedValue) {  
//                    return p;  
//                }  
//            }  
        } catch (NumberFormatException exception) {
            
        }
        return zwrot;
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
