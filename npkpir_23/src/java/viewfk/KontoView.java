/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoView  implements Serializable {
    private Konto wybranekonto;
    @Inject private KontoDAOfk kontoDAO;

     public List<Konto> complete(String query) {  
        List<Konto> results = new ArrayList<>();
        List<Konto> listakont = kontoDAO.findAll();
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
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public Konto getWybranekonto() {
        return wybranekonto;
    }
    
    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }
    
    
    //</editor-fold>
}
