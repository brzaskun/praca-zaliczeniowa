/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.KlienciDAO;
import daoFK.KliencifkDAO;
import entity.Klienci;
import entityfk.Kliencifk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KliencifkView implements Serializable{
    @Inject private KlienciDAO klienciDAO;
    @Inject private Klienci wybranyklient;
    private List<Klienci> listawszystkichklientow;
    @Inject private Kliencifk kliencifk;
    @Inject private KliencifkDAO kliencifkDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public KliencifkView() {
        listawszystkichklientow = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        listawszystkichklientow = klienciDAO.findAll();
    }
    
    public void pobieraniekontaFK(){
        Msg.msg("Pobieram kontofk");
        try {
            kliencifk = kliencifkDAO.znajdzkontofk(wybranyklient.getNip(), "8511005008");
        } catch (Exception e) {
            //tworzenie nowego
        }
    }
    

    public List<Klienci> getListawszystkichklientow() {
        return listawszystkichklientow;
    }

    public void setListawszystkichklientow(List<Klienci> listawszystkichklientow) {
        this.listawszystkichklientow = listawszystkichklientow;
    }

    public Klienci getWybranyklient() {
        return wybranyklient;
    }

    public void setWybranyklient(Klienci wybranyklient) {
        this.wybranyklient = wybranyklient;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Kliencifk getKliencifk() {
        return kliencifk;
    }

    public void setKliencifk(Kliencifk kliencifk) {
        this.kliencifk = kliencifk;
    }

    
    
    
}
