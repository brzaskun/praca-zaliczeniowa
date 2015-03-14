/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import daoFK.TabelanbpDAO;
import entity.Klienci;
import entityfk.Tabelanbp;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import view.KlienciConverterView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class TabelaNBPConv implements javax.faces.convert.Converter{
    
    @Inject
    private TabelanbpDAO tabelanbpDAO;
       
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
       List<Tabelanbp> listanpb = tabelanbpDAO.findAll();
        try {  
            for (Tabelanbp p : listanpb) {  
                if (String.valueOf(p.getIdtabelanbp()).equals(submittedValue)) {  
                    return p;  
                }  
            }  

        } catch(NumberFormatException exception) {  
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid tabelanbp"));  
        }  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Tabelanbp) value).getIdtabelanbp());  
        }  
    }  
    
    
}
