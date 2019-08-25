/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KlienciDAO;
import daoFK.DokDAOfk;
import entity.Klienci;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
public class GuestFKDokumenty implements Serializable{
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    private List<Dokfk> dokumenty;

    public GuestFKDokumenty() {
         ////E.m(this);
        this.dokumenty = Collections.synchronizedList(new ArrayList<>());
    }

    public void pobierz() {
        dokumenty = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        boolean zmiany = false;
        Klienci k = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
        for (Dokfk p : dokumenty) {
            if (p.getWartoscdokumentu() == 0.0) {
                p.przeliczKwotyWierszaDoSumyDokumentu();
                dokDAOfk.edit(p);
                zmiany = true;
            }
            if (p.getRodzajedok().getKategoriadokumentu() != 1 && p.getRodzajedok().getKategoriadokumentu() != 2) {
                if (!p.getKontr().equals(k)) {
                    p.setKontr(k);
                    dokDAOfk.edit(p);
                    zmiany = true;
                }
            }
        }
        if (zmiany) {
            dokumenty = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        }
    }
    
    
    public List<Dokfk> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<Dokfk> dokumenty) {
        this.dokumenty = dokumenty;
    }
  
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
