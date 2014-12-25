/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WierszeDAO;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private Konto wybranekonto;
    private List<Wiersz> listazapisownakoncie;
    @Inject private WierszeDAO wierszeDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private KontoDAOfk kontoDAO;
    private List<Konto> listakont;
    private List<Konto> listakontSrodkiTrwale;
    
    public KontoView() {
        this.listakont = new ArrayList<>();
        this.listakontSrodkiTrwale = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listakont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        listakontSrodkiTrwale = kontoDAO.findKontaGrupa0(wpisView.getPodatnikWpisu());
    }
    
     public List<Konto> complete(String query) {  
         List<Konto> results = new ArrayList<>();
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
     
//    public void pobierzZapisyNaKoncie() {
//        listazapisownakoncie = new ArrayList<>();
//        listazapisownakoncie = wierszeDAO.findWierszeZapisy(wpisView.getPodatnikWpisu(), wybranekonto.getPelnynumer());
//    }

    //<editor-fold defaultstate="collapsed" desc="comment">
     
    public List<Konto> getListakontSrodkiTrwale() {
        return listakontSrodkiTrwale;
    }

    public void setListakontSrodkiTrwale(List<Konto> listakontSrodkiTrwale) {
        this.listakontSrodkiTrwale = listakontSrodkiTrwale;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
     
    public List<Wiersz> getListazapisownakoncie() {
        return listazapisownakoncie;
    }

     
    public void setListazapisownakoncie(List<Wiersz> listazapisownakoncie) {
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
