/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package converter;

import dao.WalutyDAOfk;
import entityfk.Waluty;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */

@Named
@ViewScoped
public class WalutyConv implements javax.faces.convert.Converter, Serializable{
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private List<Waluty> listaWalut;
    
    @PostConstruct
    public void init() { //E.m(this);
        listaWalut = walutyDAOfk.findAll();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                String number = submittedValue;
                return listaWalut.stream().filter(p -> p.getSymbolwaluty().equals(submittedValue)).findAny().orElse(null);
//                for (Waluty p : listaWalut) {  
//                    if (p.getSymbolwaluty().equals(number)) {  
//                        return p;  
//                    }  
//                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid waluta"));  
            }  
        }  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((Waluty) value).getSymbolwaluty());  
        }  
    }  
    
    
}
