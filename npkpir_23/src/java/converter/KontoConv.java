/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package converter;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoConv implements javax.faces.convert.Converter{
    @Inject
    private KontoDAOfk kontoDAOfk;
    private List<Konto> konta;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        konta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }
     
    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String sub) {
            try {//robie to bo jak edytuje dokument to PlanKontView nie jest zainicjowany i WykazkontS jest pusty
                int submittedValue = Integer.parseInt(sub);
                for (Konto p : konta) {
                    if (p.getId()==submittedValue) {
                        return p;
                    }
                }
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
