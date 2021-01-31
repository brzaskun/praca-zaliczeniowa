/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZamkniecieRokuEtapDAO;
import dao.ZamkniecieRokuRozliczenieDAO;
import entity.ZamkniecieRokuEtap;
import entity.ZamkniecieRokuRozliczenie;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZamkniecieRokuRozliczenieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<ZamkniecieRokuRozliczenie> lista;
    @Inject
    private ZamkniecieRokuRozliczenieDAO zamkniecieRokuRozliczenieDAO;
    @Inject
    private ZamkniecieRokuEtapDAO zamkniecieRokuEtapDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() { //E.m(this);
        lista = zamkniecieRokuRozliczenieDAO.findByRokPodatnik(wpisView);
        List<ZamkniecieRokuEtap> elementywszyscy = zamkniecieRokuEtapDAO.findByRok(wpisView.getRokWpisuSt());
        utworzliste(elementywszyscy);
    }
    
    private void utworzliste(List<ZamkniecieRokuEtap> elementywszyscy) {
        if (lista == null) {
            lista = Collections.synchronizedList(new ArrayList<>());
        }
        for (ZamkniecieRokuEtap p : elementywszyscy) {
            boolean brak = true;
            for (ZamkniecieRokuRozliczenie r : lista) {
                if (r.getZamkniecieRokuEtap().equals(p)) {
                    brak = false;
                }
            }
            if (brak) {
                ZamkniecieRokuRozliczenie s = new ZamkniecieRokuRozliczenie(p, wpisView.getPodatnikObiekt());
                dodajwiersz(s);
            }
        }
    }
    
    private void dodajwiersz(ZamkniecieRokuRozliczenie s) {
        boolean pkpir = wpisView.isKsiegaryczalt();
        boolean ryczalt = !wpisView.isKsiegaryczalt();
        boolean ksiegi  = wpisView.isKsiegirachunkowe();
        ZamkniecieRokuEtap e = s.getZamkniecieRokuEtap();
        boolean dodaj = false;
        if (pkpir == true && e.isPkpir() == true) {
            dodaj = true;
        }
        if (ryczalt == true && e.isRyczalt() == true) {
            dodaj = true;
        }
        if (ksiegi == true && e.isKsiegi() == true) {
            dodaj = true;
        }
        if (dodaj) {
            lista.add(s);
        }
    }
    
    public void nanies(ZamkniecieRokuRozliczenie p) {
        try {
            if (wpisView.getUzer().getLogin().equals("szef")) {
                p.setZatwierdzono(new Date());
            }
            p.setData(new Date());
            p.setWprowadzil(wpisView.getUzer());
            zamkniecieRokuRozliczenieDAO.edit(p);
            msg.Msg.dP();
        } catch (Exception e) {
            E.e(e);
            msg.Msg.dPe();
        }
    }
    

    public List<ZamkniecieRokuRozliczenie> getLista() {
        return lista;
    }

    public void setLista(List<ZamkniecieRokuRozliczenie> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    

   

    
    
    
    
}
