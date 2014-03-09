/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beans.PlanKontBean;
import dao.KlienciDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
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
    private List<Kliencifk> listawszystkichklientowFk;
    @Inject private Kliencifk kliencifk;
    @Inject private Kliencifk nowekliencifk;
    @Inject private KliencifkDAO kliencifkDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public KliencifkView() {
        listawszystkichklientow = new ArrayList<>();
        listawszystkichklientowFk = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        listawszystkichklientow = klienciDAO.findAll();
        listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient("8511005008");
    }
    
    public void pobieraniekontaFK(){
        kliencifk = new Kliencifk();
        nowekliencifk = new Kliencifk();
        Msg.msg("Pobieram kontofk");
        try {
            kliencifk = kliencifkDAO.znajdzkontofk(wybranyklient.getNip(), "8511005008");
        } catch (Exception e) {
            //tworzenie nowego
            nowekliencifk.setNazwa(wybranyklient.getNpelna());
            nowekliencifk.setNip(wybranyklient.getNip());
            nowekliencifk.setPodatniknazwa("Podatnik");
            nowekliencifk.setPodatniknip("8511005008");
            nowekliencifk.setNrkonta(pobierznastepnynumer());
        }
    }
    
    public void przyporzadkujdokonta(){
        try {
            klienciDAO.dodaj(nowekliencifk);
            int wynik = PlanKontBean.aktualizujslownik(kliencifk, kontoDAOfk);
            Msg.msg("Przyporządkowano klienta do konta");
        } catch (Exception e) {
            Msg.msg("e", "Nieudane przyporządkowanie klienta do konta");
        }
        kliencifk = new Kliencifk();
        nowekliencifk = new Kliencifk();
    }

    private String pobierznastepnynumer() {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient("8511005008");
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size()-1).getNrkonta())+1);
        } catch (Exception e) {
            return "1";
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

    public Kliencifk getNowekliencifk() {
        return nowekliencifk;
    }

    public void setNowekliencifk(Kliencifk nowekliencifk) {
        this.nowekliencifk = nowekliencifk;
    }

    public List<Kliencifk> getListawszystkichklientowFk() {
        return listawszystkichklientowFk;
    }

    public void setListawszystkichklientowFk(List<Kliencifk> listawszystkichklientowFk) {
        this.listawszystkichklientowFk = listawszystkichklientowFk;
    }

    
    
    
    
}
