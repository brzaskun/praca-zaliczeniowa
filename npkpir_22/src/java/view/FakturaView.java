/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Faktura;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class FakturaView implements Serializable{
    @Inject private Faktura selected;
    private int liczbapozycji;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init(){
        DateTime dt = new DateTime();
        selected.setDatawystawienia(dt.toString());
    }
    
    public Faktura getSelected() {
        return selected;
    }

    public void setSelected(Faktura selected) {
        this.selected = selected;
    }

    public int getLiczbapozycji() {
        return liczbapozycji;
    }

    public void setLiczbapozycji(int liczbapozycji) {
        this.liczbapozycji = liczbapozycji;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
}
