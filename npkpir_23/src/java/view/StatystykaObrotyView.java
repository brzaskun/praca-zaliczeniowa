/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanStatystyka.StatystykaExt;
import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import dao.StatystykaDAO;
import entity.Podatnik;
import entity.Statystyka;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

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
    private List<Statystyka> stats;
    private List<StatystykaExt> statssymulacja;
    private Podatnik wybranypodatnik;
    private double oczekiwanywspolczynnik;
    
    @PostConstruct
    private void init() {
        podatnicy = podatnikDAO.findAll();
        Collections.sort(podatnicy, new Podatnikcomparator());
    }
    
    public void pobierz() {
        if (wybranypodatnik != null) {
            stats = statystykaDAO.findByPodatnik(wybranypodatnik);
            statssymulacja = new ArrayList<>();
            for (Statystyka p : stats) {
                statssymulacja.add(new StatystykaExt(0.0, 0.0, 0.0, p));
            }
            if (wybranypodatnik != null && oczekiwanywspolczynnik > 0.0) {
                przelicz();
            }
            Msg.msg("Pobrano dane dla podatnika");
        } else {
            Msg.msg("e","Nie wybrano podatnika");
        }
    }
    
    public void przelicz() {
        if (wybranypodatnik != null && oczekiwanywspolczynnik > 0.0) {
            for (StatystykaExt p : statssymulacja) {
                double fakturarok = Z.z0(p.getKwotafaktur()*oczekiwanywspolczynnik/p.getRanking());
                if (p.getPodatnik().getFirmafk()==0) {
                    fakturarok = fakturarok*.75;
                }
                double fakturamc = fakturarok/p.getIloscfaktur();
                double fakturaobecnie = p.getKwotafaktur()/p.getIloscfaktur();
                p.setWspolczynnik(oczekiwanywspolczynnik);
                p.setFakturaobecnie(fakturaobecnie);
                p.setFakturanowa(fakturamc);
            }
            Msg.msg("Naliczono dane dla podatnika");
        } else {
            Msg.msg("w","Nie naliczono danych dla podatnika");
        }
    }
    
    public void zachowajliczbaprac(StatystykaExt p) {
        for (Statystyka s : stats) {
            if (s.getPodatnik().equals(p.getPodatnik()) && s.getRok().equals(p.getRok())) {
                s.setLiczbapracownikow(p.getLiczbapracownikow());
                statystykaDAO.edit(s);
                Msg.msg("Zachowano zmiany");
                break;
            }
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

    public double getOczekiwanywspolczynnik() {
        return oczekiwanywspolczynnik;
    }

    public void setOczekiwanywspolczynnik(double oczekiwanywspolczynnik) {
        this.oczekiwanywspolczynnik = oczekiwanywspolczynnik;
    }

    public List<StatystykaExt> getStatssymulacja() {
        return statssymulacja;
    }

    public void setStatssymulacja(List<StatystykaExt> statssymulacja) {
        this.statssymulacja = statssymulacja;
    }
    
    
}
