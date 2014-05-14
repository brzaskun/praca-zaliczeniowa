/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WierszeDAO;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoView  implements Serializable {
    private Konto wybranekonto;
    private List<Wiersze> listazapisownakoncie;
    @Inject private WierszeDAO wierszeDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    
    
    
    @Inject private KontoDAOfk kontoDAO;

     public KontoView() {
         
     }
    
     public List<Konto> complete(String query) {  
         List<Konto> results = new ArrayList<>();
         List<Konto> listakont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
         try{
             String q = query.substring(0,1);
             int i = Integer.parseInt(q);
             for(Konto p : listakont) {
                 if(query.length()==4&&!query.contains("-")){
                     //wstawia - do ciagu konta
                     query = query.substring(0,3)+"-"+query.substring(3,4);
                 }
                 if(p.getPelnynumer().startsWith(query)) {
                     results.add(p);
                 }
             }
         } catch (Exception e){
             for(Konto p : listakont) {
                 if(p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                     results.add(p);
                 }
             }
         }
         return results;
     }
     
    public void pobierzZapisyNaKoncie() {
        listazapisownakoncie = new ArrayList<>();
        listazapisownakoncie = wierszeDAO.findWierszeZapisy(wpisView.getPodatnikWpisu(), wybranekonto.getPelnynumer());
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
     
    public List<Wiersze> getListazapisownakoncie() {
        return listazapisownakoncie;
    }

     
    public void setListazapisownakoncie(List<Wiersze> listazapisownakoncie) {
        this.listazapisownakoncie = listazapisownakoncie;
    }

    public Konto getWybranekonto() {
        return wybranekonto;
    }
    
    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }
    //</editor-fold>
}
