/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import xls.ImportowanyPlik;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class ImportowanyPlikConv implements javax.faces.convert.Converter{

    private List<ImportowanyPlik> rodzajeimportulista;
    
    @PostConstruct
    public void init() { //E.m(this);
        rodzajeimportulista = zrobrodzajeimportu();
    }
    
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String submittedValue) {
        if (submittedValue.trim().isEmpty()) {  
            return null;  
        } else {  
            try {  
                for (ImportowanyPlik p : rodzajeimportulista) {  
                    if (p.getOpis().equals(submittedValue)) {  
                        return p;  
                    }  
                }  
  
            } catch(NumberFormatException exception) {  
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid klient"));  
            }  
        }  
  
        return null;  
    }  
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return String.valueOf(((ImportowanyPlik) value).getOpis());  
        }  
    }  
    
    private List<ImportowanyPlik> zrobrodzajeimportu() {
        List<ImportowanyPlik> zwrot = new ArrayList<>();
        zwrot.add(new ImportowanyPlik("Bank PeKaO SA xml","xml", 1));
        zwrot.add(new ImportowanyPlik("Santander csv ;","csv",";",2));
        zwrot.add(new ImportowanyPlik("Mbank csv ;","csv",";",3));
        zwrot.add(new ImportowanyPlik("MT940 csv ;","csv",";",4));
        //to dotyczy importowanych faktur
        zwrot.add(new ImportowanyPlik("Interpaper csv ;","csv",1));
        zwrot.add(new ImportowanyPlik("Zorint xlsx","xls","",2));
        zwrot.add(new ImportowanyPlik("Tomtech xlsx","xls","",3));
        return zwrot;
    }
}
