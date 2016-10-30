/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.MiejscePrzychodowDAO;
import daoFK.SkladkaCzlonekDAO;
import data.Data;
import entityfk.MiejscePrzychodow;
import entityfk.MiejsceSuper;
import entityfk.SkladkaCzlonek;
import entityfk.StowNaliczenie;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StowNaliczenieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<StowNaliczenie> lista;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private SkladkaCzlonekDAO skladkaCzlonekDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranakategoria;


    public StowNaliczenieView() {
        this.lista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        for (MiejscePrzychodow p : czlonkowiestowarzyszenia) {
            lista.add(new StowNaliczenie(p));
        }
    }
    
    public void pobierz() {
        switch (wybranakategoria) {
            case "składka":
                generujskladki();
                break;
        }
    }
    
    private void generujskladki() {
        List<SkladkaCzlonek> listaskladki = skladkaCzlonekDAO.findAll();
        for (StowNaliczenie p : lista) {
            if (nalicz(p)) {
                double kwota = pobierzkwote(listaskladki,p);
                naniesdane(p, kwota);
            }
        }
    }
    
    private boolean nalicz(StowNaliczenie p) {
        String dataOd = ((MiejscePrzychodow) p.getMiejsce()).getPoczatek();
        String dataDo = ((MiejscePrzychodow) p.getMiejsce()).getKoniec();
        boolean jestpo = Data.czyjestpo(dataOd,wpisView);
        boolean jestprzed = Data.czyjestprzed(dataDo,wpisView);
        return jestpo && jestprzed;
    }
    
    private void naniesdane(StowNaliczenie p, double kwota) {
        p.setKategoria(wybranakategoria);
        p.setMc(wpisView.getMiesiacWpisu());
        p.setRok(wpisView.getRokWpisuSt());
        p.setPrzych0koszt1(false);
        p.setKwota(kwota);
    }
    
    private double pobierzkwote(List<SkladkaCzlonek> listaskladki, StowNaliczenie p) {
        SkladkaCzlonek skladkaCzlonek = pobierzpozycje(listaskladki, p.getMiejsce());
        return skladkaCzlonek.wyliczkwote();
    }
    
    private SkladkaCzlonek pobierzpozycje(List<SkladkaCzlonek> listaskladki, MiejsceSuper czlonek) {
        SkladkaCzlonek zwrot = null;
        for (SkladkaCzlonek p : listaskladki) {
            if (p.getCzlonek().equals(czlonek)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<StowNaliczenie> getLista() {
        return lista;
    }
    
    public void setLista(List<StowNaliczenie> lista) {
        this.lista = lista;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public String getWybranakategoria() {
        return wybranakategoria;
    }
    
    public void setWybranakategoria(String wybranakategoria) {
        this.wybranakategoria = wybranakategoria;
    }
    
    
//</editor-fold>

   
    
    
    
}
