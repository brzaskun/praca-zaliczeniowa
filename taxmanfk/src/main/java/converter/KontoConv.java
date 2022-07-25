/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import dao.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KontoConv implements javax.faces.convert.Converter, Serializable {
    @Inject
    private KontoDAOfk kontoDAOfk;
    private List<Konto> konta;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() { //E.m(this);
        konta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }
     
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
            try {//robie to bo jak edytuje dokument to PlanKontView nie jest zainicjowany i WykazkontS jest pusty
                int submittedValue = Integer.parseInt(sub);
                return konta.stream().filter(p -> p.getId().equals(submittedValue)).findAny().orElse(null);
            } catch (Exception e) {
            }
            return null;
    }
  
    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object value) {  
        if (value == null || value.equals("")) {  
            return "";  
        } else {  
            return ((Konto) value).getPelnynumer() != null ? String.valueOf(((Konto) value).getId()):null;  
        }  
    }  

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Konto> getKonta() {
        return konta;
    }

    public void setKonta(List<Konto> konta) {
        this.konta = konta;
    }
    
    
    
}
