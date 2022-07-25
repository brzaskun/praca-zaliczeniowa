/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import pdf.PdfPodatnicy;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnicyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Podatnik> podatnicy;
    private List<Podatnik> wybranipodatnicy;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() { //E.m(this);
        podatnicy = podatnikDAO.findAll();
    }
    
    public void edytuj(Podatnik p) {
        try {
            podatnikDAO.edit(p);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }

    public void drukujwszystkich() {
        try {
            if (wybranipodatnicy != null && wybranipodatnicy.size() > 0) {
                PdfPodatnicy.drukuj(wybranipodatnicy, wpisView);
            } else {
                PdfPodatnicy.drukuj(podatnicy, wpisView);
            }
        } catch (Exception e) {
            
        }
    }
    
    public List<Podatnik> getPodatnicy() {
        return podatnicy;
    }

    public void setPodatnicy(List<Podatnik> podatnicy) {
        this.podatnicy = podatnicy;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Podatnik> getWybranipodatnicy() {
        return wybranipodatnicy;
    }

    public void setWybranipodatnicy(List<Podatnik> wybranipodatnicy) {
        this.wybranipodatnicy = wybranipodatnicy;
    }
    
}
