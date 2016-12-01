/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import comparator.Uzcomparator;
import dao.PodatnikDAO;
import dao.SprawaDAO;
import dao.UzDAO;
import entity.Podatnik;
import entity.Sprawa;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.text.Collator;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprawaView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject
    private SprawaDAO sprawaDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private Sprawa nowa;
    private List<Sprawa> sprawy;
    private List<Uz> odbiorcy;
    private List<Podatnik> klienci;
    
    
    @PostConstruct
    private void init() {
        sprawy = sprawaDAO.findSprawaByOdbiorca(wpisView.getWprowadzil());
        odbiorcy = uzDAO.findAll();
        Collections.sort(odbiorcy, new Uzcomparator());
        klienci = podatnikDAO.findAll();
        Collections.sort(klienci, new Podatnikcomparator());
    }
    
    public void dodaj() {
        try {
            nowa.setDatasporzadzenia(new Date());
            nowa.setNadawca(wpisView.getWprowadzil());
            nowa.setStatus("wysłana");
            sprawaDAO.dodaj(nowa);
            nowa = new Sprawa();
            Msg.msg("Dodano sprawę");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Nie udało się dodać sprawę");
        }
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Sprawa> getSprawy() {
        return sprawy;
    }

    public void setSprawy(List<Sprawa> sprawy) {
        this.sprawy = sprawy;
    }

    public Sprawa getNowa() {
        return nowa;
    }

    public void setNowa(Sprawa nowa) {
        this.nowa = nowa;
    }

    public List<Uz> getOdbiorcy() {
        return odbiorcy;
    }

    public void setOdbiorcy(List<Uz> odbiorcy) {
        this.odbiorcy = odbiorcy;
    }

    public List<Podatnik> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Podatnik> klienci) {
        this.klienci = klienci;
    }
    
    
    
}
