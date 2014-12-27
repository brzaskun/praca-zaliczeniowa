/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import daoFK.KontoDAOfk;
import daoFK.RMKDAO;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.RMK;
import java.io.Serializable;
import java.text.DecimalFormat;
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
import waluty.Z;

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
    private List<RMK> listarmk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private Dokfk dokfk;
    @Inject
    private RMKDAO rmkdao;

    public RMKView() {
        this.listakontRMK = new ArrayList<>();
        this.listarmk = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listakontRMK = kontoDAO.findKontaGrupa6(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        listarmk = rmkdao.findRMKByPodatnikRok(wpisView);
    }
    
    public void dodajNoweRMKDokfk(Dokfk wybranydok) {
        try {
            this.dokfk = wybranydok;
        } catch (Exception e) {
        }
    }
    
    
    public void dodajRMK() {
        rmk.setDokfk(dokfk);
        double kwotamiesieczna = Z.z(rmk.getKwotacalkowita()/rmk.getLiczbamiesiecy());
        rmk.setKwotamiesieczna(kwotamiesieczna);
        rmk.setDataksiegowania(dokfk.getDataoperacji());
        rmk.setMckosztu(dokfk.getMiesiac());
        rmk.setRokkosztu(dokfk.getDokfkPK().getRok());
        double kwotamax = rmk.getKwotacalkowita();
        Double narastajaco = 0.0;
        while (kwotamax - narastajaco > 0) {
            double odpisbiezacy = (kwotamax - narastajaco) > rmk.getKwotamiesieczna() ? rmk.getKwotamiesieczna() : kwotamax - narastajaco;
            if((kwotamax - narastajaco) < rmk.getKwotamiesieczna()){
                rmk.getPlanowane().add(Z.z(kwotamax - narastajaco));
                break;
            } else {
                rmk.getPlanowane().add(odpisbiezacy);
            }
            narastajaco = narastajaco + odpisbiezacy;
        }
        rmkdao.dodaj(rmk);
        System.out.println("rmk dodaje");
        Msg.msg("Dodano rozliczenie międzyokresowe");
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

    public List<RMK> getListarmk() {
        return listarmk;
    }

    public void setListarmk(List<RMK> listarmk) {
        this.listarmk = listarmk;
    }

    
    
   
    
}
