/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import dao.StatystykaDAO;
import entity.Podatnik;
import entity.Statystyka;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StatystykaObrotyView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private StatystykaDAO statystykaDAO;
    private List<Podatnik> podatnicy;
    private List<Statystyka> stats = new ArrayList<>();
    private Podatnik wybranypodatnik;
    
    @PostConstruct
    private void init() {
        podatnicy = podatnikDAO.findAll();
        Collections.sort(podatnicy, new Podatnikcomparator());
    }
    
    public void pobierz() {
        if (wybranypodatnik != null) {
            stats = statystykaDAO.findByPodatnik(wybranypodatnik);
            Msg.msg("Pobrano dane dla podatnika");
        } else {
            Msg.msg("e","Nie wybrano podatnika");
        }
    }

    public List<Statystyka> getStats() {
        return stats;
    }

    public void setStats(List<Statystyka> stats) {
        this.stats = stats;
    }

    public List<Podatnik> getPodatnicy() {
        return podatnicy;
    }

    public void setPodatnicy(List<Podatnik> podatnicy) {
        this.podatnicy = podatnicy;
    }

    public Podatnik getWybranypodatnik() {
        return wybranypodatnik;
    }

    public void setWybranypodatnik(Podatnik wybranypodatnik) {
        this.wybranypodatnik = wybranypodatnik;
    }
    
    
}
