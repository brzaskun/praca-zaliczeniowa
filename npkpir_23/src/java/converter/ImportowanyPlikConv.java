/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.inject.Named;
import xls.BankImportWykaz;
import xls.ImportowanyPlik;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class ImportowanyPlikConv implements javax.faces.convert.Converter,Serializable {
    private static final long serialVersionUID = 1L;

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
        zwrot.add(new ImportowanyPlik("Interpaper csv ;","csv",1));
        zwrot.add(new ImportowanyPlik("Zorint xls","xls","",2));
        zwrot.add(new ImportowanyPlik("Tomtech xls","xls","",3));
        zwrot.add(new ImportowanyPlik("Exolight xls","xls","",4));
        zwrot.add(new ImportowanyPlik("Murawski xls","xls","",5));
        zwrot.add(new ImportowanyPlik("Mucha xls","xls","",6));
        zwrot.add(new ImportowanyPlik("Zorin nowy xml","xml","",7));
        zwrot.add(new ImportowanyPlik("Seven xml","xml","",8));
        zwrot.add(new ImportowanyPlik("K3F xml","xml","",9));
        zwrot.add(new ImportowanyPlik("Amazon taxraport csv","csv","",10));
        //to dotyczy importowanych bankow
        zwrot.addAll(BankImportWykaz.getWYKAZ());
        return zwrot;
    }
}
