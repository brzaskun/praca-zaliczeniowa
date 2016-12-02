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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
    private static final List<String> status;
    
    static {
        status = new ArrayList<>();
        status.add("przeczytana");
        status.add("załatwiana");
        status.add("gotowa");
        status.add("musi czekać");
    }
    
    
    @PostConstruct
    private void init() {
        Uz login = wpisView.getWprowadzil().getLoginglowny() != null ? wpisView.getWprowadzil().getLoginglowny() : wpisView.getWprowadzil();
        sprawy = sprawaDAO.findSprawaByOdbiorca(login);
        for (Iterator<Sprawa> it = sprawy.iterator();it.hasNext();) {
            Sprawa p = it.next();
            if (p.getStatus().equals("gotowa")) {
                it.remove();
            }
        }
        List<Sprawa> nadane = sprawaDAO.findSprawaByNadawca(login);
        for (Iterator<Sprawa> it = nadane.iterator();it.hasNext();) {
            Sprawa p = it.next();
            if (p.isUsunieta()) {
                it.remove();
            }
        }
        sprawy.addAll(nadane);
        odbiorcy = uzDAO.findAll();
        for(Iterator<Uz> it = odbiorcy.iterator(); it.hasNext();) {
            Uz s = it.next();
            if (s.getLoginglowny() != null) {
                it.remove();
            } else if (s.getUprawnienia().equals("Guest")||s.getUprawnienia().equals("GuestFK")||s.getUprawnienia().equals("GuestFaktura")||s.getUprawnienia().equals("Multiuser")||s.getUprawnienia().equals("Dedra")) {
                it.remove();
            }
        }
        Collections.sort(odbiorcy, new Uzcomparator());
        klienci = podatnikDAO.findAll();
        Collections.sort(klienci, new Podatnikcomparator());
    }
    
    public void dodaj() {
        try {
            Uz login = wpisView.getWprowadzil().getLoginglowny() != null ? wpisView.getWprowadzil().getLoginglowny() : wpisView.getWprowadzil();
            nowa.setDatasporzadzenia(new Date());
            nowa.setNadawca(login);
            nowa.setStatus("wysłana");
            sprawaDAO.dodaj(nowa);
            nowa = new Sprawa();
            Msg.msg("Dodano sprawę");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Nie udało się dodać sprawę");
        }
    }
    
    public void nanies(Sprawa sprawa) {
        sprawa.setDatastatusu(new Date());
        sprawaDAO.edit(sprawa);
        Msg.msg("Odnotowano zmianę statusu");
    }
    
    public void niepokazuj(Sprawa sprawa) {
        sprawaDAO.edit(sprawa);
        Msg.msg("Ukryto załatwioną sprawę");
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

    public List<String> getStatus() {
        return status;
    }
    
    
    
}
