/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import daoFK.KontoDAOfk;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.RMK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RMKView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RMK rmk;
    private List<Konto> listakontRMK;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private Dokfk dokfk;

    public RMKView() {
        this.listakontRMK = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listakontRMK = kontoDAO.findKontaGrupa6(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
    }
    
    public void dodajNoweRMKDokfk(Dokfk wybranydok) {
        try {
            this.dokfk = wybranydok;
        } catch (Exception e) {
        }
    }
    
    
    public void dodajRMK() {
        listakontRMK = kontoDAO.findKontaGrupa6(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        System.out.println("lolo");
        Msg.msg("Dodaje msg "+dokfk.getDokfkPK().toString());
    }
    
   
    public RMK getRmk() {
        return rmk;
    }

    public void setRmk(RMK rmk) {
        this.rmk = rmk;
    }

    public List<Konto> getListakontRMK() {
        return listakontRMK;
    }

    public void setListakontRMK(List<Konto> listakontRMK) {
        this.listakontRMK = listakontRMK;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public KontoDAOfk getKontoDAO() {
        return kontoDAO;
    }

    public void setKontoDAO(KontoDAOfk kontoDAO) {
        this.kontoDAO = kontoDAO;
    }

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    
   
    
}
