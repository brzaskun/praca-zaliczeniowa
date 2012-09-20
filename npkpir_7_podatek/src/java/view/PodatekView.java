/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import embeddable.Kolmn;
import entity.Dok;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name="PodatekView")
@SessionScoped
public class PodatekView implements Serializable{
    
    private double przychody;
    private double koszty;
    private double inwestycje;
    private double dochód;
    private double podatek;

    public double getPrzychody() {
        return przychody;
    }

    public void setPrzychody(double przychody) {
        this.przychody = przychody;
    }

    public double getKoszty() {
        return koszty;
    }

    public void setKoszty(double koszty) {
        this.koszty = koszty;
    }

    public double getInwestycje() {
        return inwestycje;
    }

    public void setInwestycje(double inwestycje) {
        this.inwestycje = inwestycje;
    }

    public double getDochód() {
        return dochód;
    }

    public void setDochód(double dochód) {
        this.dochód = dochód;
    }

    public double getPodatek() {
        return podatek;
    }

    public void setPodatek(double podatek) {
        this.podatek = podatek;
    }
    
    
    
    public void sprawozdaniePodatkowe(){
        Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
 	String ktNazwa = params.get("dodWiad:acForce_input");
        DokDAO dokDAO = new DokDAO();
        dokDAO.refresh();
        Iterator it;
        List<Dok> lista = null;
        lista.addAll(dokDAO.getObiektDOKmrjsfSel());
        it = lista.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            Kolmn kolmn = new Kolmn();
            if(tmp.getPkpirKol().equals(kolmn.getKolumnPrzychody().get(0) )||tmp.getPkpirKol().equals(kolmn.getKolumnPrzychody().get(1) )){
                przychody = przychody + tmp.getKwota();
            } else if (tmp.getPkpirKol().equals(kolmn.getKolumnST().get(0) )||tmp.getPkpirKol().equals(kolmn.getKolumnST().get(1) )) {
                inwestycje = inwestycje + tmp.getKwota();
            } else {
                koszty = koszty + tmp.getKwota();
            }
        }
        dochód = przychody - koszty;
        podatek = dochód * 0.18;
    }
}
