/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.Kolmn;
import entity.Dok;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean(name="PodatekView")
@RequestScoped
public class PodatekView implements Serializable{
    @ManagedProperty(value="#{DokumentView.obiektDOKmrjsfSel}")
    private ArrayList<Dok> oDOK;
   
    private BigDecimal przychody;
    private BigDecimal koszty;
    private BigDecimal inwestycje;
    private BigDecimal dochód;
    private BigDecimal podatek;

    public PodatekView() {
        przychody = BigDecimal.valueOf(0);
        koszty = BigDecimal.valueOf(0);
    }

    
    
    public BigDecimal getPrzychody() {
        return przychody;
    }

    public void setPrzychody(BigDecimal przychody) {
        this.przychody = przychody;
    }

    public BigDecimal getKoszty() {
        return koszty;
    }

    public void setKoszty(BigDecimal koszty) {
        this.koszty = koszty;
    }

    public BigDecimal getInwestycje() {
        return inwestycje;
    }

    public void setInwestycje(BigDecimal inwestycje) {
        this.inwestycje = inwestycje;
    }

    public BigDecimal getDochód() {
        return dochód;
    }

    public void setDochód(BigDecimal dochód) {
        this.dochód = dochód;
    }

    public BigDecimal getPodatek() {
        return podatek;
    }

    public void setPodatek(BigDecimal podatek) {
        this.podatek = podatek;
    }

    public ArrayList<Dok> getoDOK() {
        return oDOK;
    }

    public void setoDOK(ArrayList<Dok> oDOK) {
        this.oDOK = oDOK;
    }
    
    
    
    public void sprawozdaniePodatkowe(){
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);
        Iterator it;
        List<Dok> lista = new ArrayList<Dok>();
        lista.addAll(getoDOK());
        it = lista.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            Kolmn kolmn = new Kolmn();
            if(tmp.getPkpirKol().equals(kolmn.getKolumnPrzychody().get(0) )||tmp.getPkpirKol().equals(kolmn.getKolumnPrzychody().get(1) )){
                przychody = przychody.add(BigDecimal.valueOf(tmp.getKwota())).setScale(2, RoundingMode.HALF_EVEN);
            } else if (tmp.getPkpirKol().equals(kolmn.getKolumnST().get(0) )||tmp.getPkpirKol().equals(kolmn.getKolumnST().get(1) )) {
                inwestycje = inwestycje.add(BigDecimal.valueOf(tmp.getKwota())).setScale(2, RoundingMode.HALF_EVEN);
            } else {
                koszty = koszty.add(BigDecimal.valueOf(tmp.getKwota())).setScale(2, RoundingMode.HALF_EVEN);
            }
        }
        
        dochód = (przychody.subtract(koszty));
        dochód = dochód.setScale(0, RoundingMode.HALF_EVEN);
        podatek = (dochód.multiply(BigDecimal.valueOf(0.18)));
        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
    }
}
