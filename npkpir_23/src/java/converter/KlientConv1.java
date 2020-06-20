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
public class KlientConv1 implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private KlienciDAO klienciDAO;
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        List<Klienci> listaKlientow = new ArrayList<>();
        Klienci znaleziony = null;
        try {
            znaleziony = klienciDAO.findAllReadOnlyID(sub);
        } catch (Exception e) {
            error.E.s("");  
        }
        return znaleziony;
    }

  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            return ((Klienci) value).getId() != null ? String.valueOf(((Klienci) value).getId()) : null;  
        }  
    }  

    
    
}