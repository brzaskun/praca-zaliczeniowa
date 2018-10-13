/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import dao.FakturaDAO;
import dao.PodatnikDAO;
import embeddable.FakturaZestawienie;
import entity.Faktura;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
public class FakturaZestView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    //faktury z bazy danych
    private List<Faktura> fakturyWystawione;
    //listaprzetworzona
    private List<FakturaZestawienie> fakturyZestawienie;
    //lista podatnikow
    private List<Podatnik> podatnicyWProgramie;

    public FakturaZestView() {
        fakturyWystawione = Collections.synchronizedList(new ArrayList<>());
        fakturyZestawienie = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init1() {
        podatnicyWProgramie = podatnikDAO.findAll();
    }
    
    
    public void init() {
        fakturyZestawienie = Collections.synchronizedList(new ArrayList<>());
        fakturyWystawione = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
        List<Podatnik> podatnicy = podatnikDAO.findAll();
        Set<String> odnalezioneNIP = new HashSet<>();
        if (fakturyWystawione != null) {
            for (Faktura p : fakturyWystawione) {
                String n = p.getKontrahent().getNip();
                FakturaZestawienie f = new FakturaZestawienie();
                if (odnalezioneNIP.contains(n)) {
                    for (FakturaZestawienie r : fakturyZestawienie) {
                        if (r.getKontrahent() != null && r.getKontrahent().getNip().equals(n)) {
                            f = r;
                            break;
                        } else if (r.getPodatnik() != null && r.getPodatnik().getNip().equals(n)) {
                            f = r;
                            break;
                        }
                    }
                    FakturaZestawienie.FZTresc ft = f.new FZTresc();
                    ft.setMc(p.getMc());
                    ft.setNrfakt(p.getFakturaPK().getNumerkolejny());
                    ft.setNetto(p.getNetto());
                    ft.setVat(p.getVat());
                    ft.setBrutto(p.getBrutto());
                    ft.setData(p.getDatawystawienia());
                    ft.setOpis(p.getPozycjenafakturze().get(0).getNazwa());
                    ft.setFaktura(p);
                    f.getTrescfaktury().add(ft);
                } else {
                    Podatnik odnalezionyPodatnik = null;
                    try {
                        odnalezionyPodatnik = znajdzpodattniknip(n);
                    } catch (Exception e) { E.e(e); }
                    FakturaZestawienie.FZTresc ft = f.new FZTresc();
                    if (odnalezionyPodatnik != null) {
                        f.setPodatnik(odnalezionyPodatnik);
                        ft.setMc(p.getMc());
                        ft.setNrfakt(p.getFakturaPK().getNumerkolejny());
                        ft.setNetto(p.getNetto());
                        ft.setVat(p.getVat());
                        ft.setBrutto(p.getBrutto());
                        ft.setData(p.getDatawystawienia());
                        ft.setOpis(p.getPozycjenafakturze().get(0).getNazwa());
                        ft.setFaktura(p);
                        f.getTrescfaktury().add(ft);
                    } else {
                        f.setKontrahent(p.getKontrahent());
                        ft.setMc(p.getMc());
                        ft.setNrfakt(p.getFakturaPK().getNumerkolejny());
                        ft.setNetto(p.getNetto());
                        ft.setVat(p.getVat());
                        ft.setBrutto(p.getBrutto());
                        ft.setData(p.getDatawystawienia());
                        ft.setOpis(p.getPozycjenafakturze().get(0).getNazwa());
                        ft.setFaktura(p);
                        f.getTrescfaktury().add(ft);
                    }
                    odnalezioneNIP.add(n);
                    fakturyZestawienie.add(f);
                }
            }
        }
        Msg.msg("Pobrano faktury");
    }
    private Podatnik znajdzpodattniknip(String n) {
        Podatnik zwrot = null;
        for (Podatnik p : podatnicyWProgramie) {
            if (p.getNip().equals(n)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
  
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public FakturaDAO getFakturaDAO() {
        return fakturaDAO;
    }

    public void setFakturaDAO(FakturaDAO fakturaDAO) {
        this.fakturaDAO = fakturaDAO;
    }

    public List<FakturaZestawienie> getFakturyZestawienie() {
        return fakturyZestawienie;
    }

    public void setFakturyZestawienie(List<FakturaZestawienie> fakturyZestawienie) {
        this.fakturyZestawienie = fakturyZestawienie;
    }

    
    
    
    
}
