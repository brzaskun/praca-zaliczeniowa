/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.FakturaDodatkowaPozycjaDAO;
import entity.FakturaDodatkowaPozycja;
import java.io.Serializable;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaDodatkowaPozycjaConv implements javax.faces.convert.Converter, Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaDodatkowaPozycjaDAO fakturaDodatkowaPozycjaDAO;
    
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
        List<FakturaDodatkowaPozycja> listaKlientow = fakturaDodatkowaPozycjaDAO.findAll();
        FakturaDodatkowaPozycja znaleziony = null;
        for (FakturaDodatkowaPozycja p : listaKlientow) {  
            if (p.getId()==Integer.parseInt(sub)) {  
                znaleziony = p;
                break;
            }  
        }  
        return znaleziony;
    }
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {
            return ((FakturaDodatkowaPozycja) value).getId() != 0 ? String.valueOf(((FakturaDodatkowaPozycja) value).getId()) : null;  
        }  
    }  

   
    
}
