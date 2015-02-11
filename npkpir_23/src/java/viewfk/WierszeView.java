/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WierszeDAO;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
public class WierszeView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private WierszeDAO wierszeDAO;
    private List<Wiersz> wiersze;
    private List<Wiersz> wybranewierszeWNT;
    private List<Wiersz> wybranewierszeWDT;
    private List<Wiersz> wierszeWNT;
    private List<Wiersz> wierszeWDT;
    private double sumakg;
    private double sumaszt;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    public void init(){
        wiersze = wierszeDAO.findWierszePodatnikMcRok(wpisView.getPodatnikObiekt(), wpisView);
    }
    
    public void initwierszeWNT(){
        wierszeWNT = wierszeDAO.findWierszePodatnikMcRokWNTWDT(wpisView.getPodatnikObiekt(), wpisView, "WNT");
        for(Iterator<Wiersz> it = wierszeWNT.iterator(); it.hasNext();) {
            Wiersz p = (Wiersz) it.next();
            if (p.getIlosc_kg() == 0.0 && p.getIlosc_szt() == 0.0) {
                it.remove();
            }
        }
    }
    
    public void initwierszeWDT(){
        wierszeWDT = wierszeDAO.findWierszePodatnikMcRokWNTWDT(wpisView.getPodatnikObiekt(), wpisView, "WDT");
        for(Iterator<Wiersz> it = wierszeWDT.iterator(); it.hasNext();) {
            Wiersz p = (Wiersz) it.next();
            if (p.getIlosc_kg() == 0.0 && p.getIlosc_szt() == 0.0) {
                it.remove();
            }
        }
    }
    
    public void odswiezzaksiegowane() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wiersze = wierszeDAO.findWierszePodatnikRok(wpisView.getPodatnikObiekt(), wpisView);
            wierszeWNT = new ArrayList<>();
            wierszeWDT = new ArrayList<>();
            RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        } else {
            wpisView.wpisAktualizuj();
            wiersze = wierszeDAO.findWierszePodatnikMcRok(wpisView.getPodatnikObiekt(), wpisView);
            initwierszeWNT();
            initwierszeWDT();
            RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        }
    }

    public void sumazapisowtotalWNT() {
        sumakg = 0.0;
        sumaszt = 0.0;
        for (Wiersz p : wybranewierszeWNT) {
            sumakg += p.getIlosc_kg();
            sumaszt += p.getIlosc_szt();
        }
    }
    
    public void sumazapisowtotalWDT() {
        sumakg = 0.0;
        sumaszt = 0.0;
        for (Wiersz p : wybranewierszeWDT) {
            sumakg += p.getIlosc_kg();
            sumaszt += p.getIlosc_szt();
        }
    }
    
    public List<Wiersz> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<Wiersz> wiersze) {
        this.wiersze = wiersze;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Wiersz> getWierszeWNT() {
        return wierszeWNT;
    }

    public void setWierszeWNT(List<Wiersz> wierszeWNT) {
        this.wierszeWNT = wierszeWNT;
    }

    public List<Wiersz> getWierszeWDT() {
        return wierszeWDT;
    }

    public void setWierszeWDT(List<Wiersz> wierszeWDT) {
        this.wierszeWDT = wierszeWDT;
    }

    public List<Wiersz> getWybranewierszeWNT() {
        return wybranewierszeWNT;
    }

    public void setWybranewierszeWNT(List<Wiersz> wybranewierszeWNT) {
        this.wybranewierszeWNT = wybranewierszeWNT;
    }

    public double getSumakg() {
        return sumakg;
    }

    public void setSumakg(double sumakg) {
        this.sumakg = sumakg;
    }

    public double getSumaszt() {
        return sumaszt;
    }

    public void setSumaszt(double sumaszt) {
        this.sumaszt = sumaszt;
    }

    public List<Wiersz> getWybranewierszeWDT() {
        return wybranewierszeWDT;
    }

    public void setWybranewierszeWDT(List<Wiersz> wybranewierszeWDT) {
        this.wybranewierszeWDT = wybranewierszeWDT;
    }
    
    
     
    
}
