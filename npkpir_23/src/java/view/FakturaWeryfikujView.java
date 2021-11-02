/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaWeryfikujView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Klienci> lista;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private WpisView wpisView;
    
    public void sprawdzmc() {
        List<Podatnik> podatnicy  = podatnikDAO.findAktywny();
        List<Klienci> p = new ArrayList<>();
        for (Podatnik po : podatnicy) {
            Klienci findKlientByNip = klienciDAO.findKlientByNip(po.getNip());
            if (findKlientByNip!=null) {
                p.add(findKlientByNip);
            }
        }
        List<Faktura> faktury = new ArrayList<>();
        List<Faktura> fakturytmp = fakturaDAO.findbyPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu());
        for (Faktura fakt : fakturytmp) {
            if (!fakt.isTylkodlaokresowej()) {
                faktury.add(fakt);
            }
        }
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci l = it.next();
            boolean jest = false;
            for (Faktura f : faktury) {
                if (f.getKontrahent().equals(l) && f.isWygenerowanaautomatycznie()) {
                    jest = true;
                    break;
                }
            }
            if (jest) {
                it.remove();
            }
        }
        lista = new ArrayList<>(p);
        Msg.msg("Wygenerowano listę braków.");
    }

    public void aktywacjakontrahenta(Klienci k) {
        k.setAktywnydlafaktrozrachunki(false);
        klienciDAO.edit(k);
        lista.remove(k);
        Msg.msg("Dezaktywowano klienta "+k.getNpelna());
    }
    
    public List<Klienci> getLista() {
        return lista;
    }

    public void setLista(List<Klienci> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
