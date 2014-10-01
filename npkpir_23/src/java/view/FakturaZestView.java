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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

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
        fakturyWystawione = new ArrayList<>();
        fakturyZestawienie = new ArrayList<>();
        podatnicyWProgramie = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        podatnicyWProgramie = podatnikDAO.findAll();
        fakturyWystawione = fakturaDAO.findFakturyByRok(wpisView.getRokWpisuSt());
        Set<String> odnalezioneNIP = new HashSet<>();
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
                ft.setWartosc(p.getNetto());
                f.getFaktury().add(ft);
            } else {
                Podatnik odnalezionyPodatnik = null;
                try {
                    odnalezionyPodatnik = podatnikDAO.findPodatnikByNIP(n);
                } catch (Exception e) {}
                FakturaZestawienie.FZTresc ft = f.new FZTresc();
                if (odnalezionyPodatnik != null) {
                    f.setPodatnik(odnalezionyPodatnik);
                    ft.setMc(p.getMc());
                    ft.setNrfakt(p.getFakturaPK().getNumerkolejny());
                    ft.setWartosc(p.getNetto());
                    f.getFaktury().add(ft);
                } else {
                    f.setKontrahent(p.getKontrahent());
                    ft.setMc(p.getMc());
                    ft.setNrfakt(p.getFakturaPK().getNumerkolejny());
                    ft.setWartosc(p.getNetto());
                    f.getFaktury().add(ft);
                }
                odnalezioneNIP.add(n);
                fakturyZestawienie.add(f);
            }
        }
        System.out.println("Skoncozne");
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
