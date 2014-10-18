/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.PlanKontFKBean;
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
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KliencifkView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject private KlienciDAO klienciDAO;
    @Inject private Klienci wybranyklient;
    private List<Klienci> listawszystkichklientow;
    private List<Kliencifk> listawszystkichklientowFk;
    @Inject private Kliencifk klientMaKonto;
    @Inject private Kliencifk klientBezKonta;
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
        listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
    }
    
    public void pobieraniekontaFKWpis(ValueChangeEvent e){
        wybranyklient = (Klienci) e.getNewValue();
        int wynik = pobieraniekontaFK();
        if (wynik == 1) {
            //RequestContext.getCurrentInstance().update("czydodackonto");
            RequestContext.getCurrentInstance().execute("PF('czydodackonto').show();");
        }
    }
    
    public void pobieraniekontaFKWpisCD() {
        przyporzadkujdokonta();
    }
    
    public int pobieraniekontaFK(){
        if (wybranyklient instanceof Klienci) {
            klientMaKonto = new Kliencifk();
            klientBezKonta = new Kliencifk();
            Msg.msg("Pobieram kontofk");
            try {
                klientMaKonto = kliencifkDAO.znajdzkontofk(wybranyklient.getNip(), wpisView.getPodatnikObiekt().getNip());
                return 0;
            } catch (Exception e) {
                //tworzenie nowego
                klientBezKonta.setNazwa(wybranyklient.getNpelna());
                klientBezKonta.setNip(wybranyklient.getNip());
                klientBezKonta.setPodatniknazwa(wpisView.getPodatnikWpisu());
                klientBezKonta.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
                klientBezKonta.setNrkonta(pobierznastepnynumer());
                return 1;
            }
        }
        return -1;
    }
    
    public void przyporzadkujdokonta(){
        try {
            klienciDAO.dodaj(klientBezKonta);
            int wynik = PlanKontFKBean.aktualizujslownik(klientBezKonta, kontoDAOfk, wpisView.getPodatnikWpisu());
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            Msg.msg("Przyporządkowano klienta do konta");
        } catch (Exception e) {
            Msg.msg("e", "Nieudane przyporządkowanie klienta do konta");
        }
        klientMaKonto = new Kliencifk();
        klientBezKonta = new Kliencifk();
    }

    private String pobierznastepnynumer() {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size()-1).getNrkonta())+1);
        } catch (Exception e) {
            return "1";
        }
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    
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
    
    public Kliencifk getKlientMaKonto() {
        return klientMaKonto;
    }
    
    public void setKlientMaKonto(Kliencifk klientMaKonto) {
        this.klientMaKonto = klientMaKonto;
    }
    
    public Kliencifk getKlientBezKonta() {
        return klientBezKonta;
    }
    
    public void setKlientBezKonta(Kliencifk klientBezKonta) {
        this.klientBezKonta = klientBezKonta;
    }
    
    public List<Kliencifk> getListawszystkichklientowFk() {
        return listawszystkichklientowFk;
    }
    
    public void setListawszystkichklientowFk(List<Kliencifk> listawszystkichklientowFk) {
        this.listawszystkichklientowFk = listawszystkichklientowFk;
    }
    
    
    
//</editor-fold>
    
    
}
