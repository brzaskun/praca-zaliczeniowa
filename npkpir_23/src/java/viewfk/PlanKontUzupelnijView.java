/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import embeddable.Roki;
import entity.Podatnik;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontUzupelnijView implements Serializable {

    private Podatnik podatnikzrodlowy;
    private Podatnik podatnikdocelowy;
    private String rokzrodlowy;
    private String rokdocelowy;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{planKontView}")
    private PlanKontView planKontView;
    @Inject
    private KontoDAOfk kontoDAOfk;

    public PlanKontUzupelnijView() {
         E.m(this);
    }
    
    

    @PostConstruct
    private void init() {
        rokzrodlowy = Roki.rokPoprzedni(wpisView.getRokWpisuSt());
        rokdocelowy = wpisView.getRokWpisuSt();
        podatnikzrodlowy = wpisView.getPodatnikObiekt();
        podatnikdocelowy = wpisView.getPodatnikObiekt();
    }

    public void uzupelnijplankont() {
        if (podatnikzrodlowy.equals(podatnikdocelowy) && rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Podatnik oraz rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> kontazrodlowe = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy.getNazwapelna(), rokzrodlowy);
            List<Konto> kontadocelowe = kontoDAOfk.findWszystkieKontaPodatnika(podatnikdocelowy.getNazwapelna(), rokdocelowy);
            List<Konto> level0 = new ArrayList<>();
            List<Konto> levelinne = new ArrayList<>();
            for (Konto p : kontazrodlowe) {
                if (!kontadocelowe.contains(p)) {
                    if (p.isSlownikowe() == false) {
                        if (p.getMacierzysty() == 0) {
                            level0.add(p);
                        } else {
                            levelinne.add(p);
                        }
                    }
                    if (p.isSlownikowe() == true && p.getNrkonta().equals("0")) {
                        levelinne.add(p);
                    }
                }
            }
            skopiujlevel0(wpisView.getPodatnikWpisu(), level0, rokdocelowy);
            skopiujlevel(wpisView.getPodatnikWpisu(), levelinne, kontadocelowe, rokdocelowy);
            System.out.println("Wydrukowałem brakujące konta");
            //planKontView.init();
            //planKontView.porzadkowanieKontPodatnika();
        }
    }

   

    private List<Konto> skopiujlevel0(String podatnikDocelowy, List<Konto> wykazkont, String rokDocelowy) {
        List<Konto> macierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel() == 0) {
                Konto r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnikDocelowy);
                r.setRok(Integer.parseInt(rokDocelowy));
                zeruDanekontaBO(r);
                macierzyste.add(r);
            }
        }
        kontoDAOfk.createListRefresh(macierzyste);
        return macierzyste;
    }

    private List<Konto> skopiujlevel(String podatnikDocelowy, List<Konto> wykazkont, List<Konto> macierzystelista, String rokdocelowy) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            try {
                nowemacierzyste.add(kopiujKonto(p, macierzystelista, podatnikDocelowy, p.isSlownikowe()));
            } catch (Exception e) {
                E.e(e);
            }
        }
        if (!nowemacierzyste.isEmpty()) {
            kontoDAOfk.createListRefresh(nowemacierzyste);
        }
        return nowemacierzyste;
    }

    private void zeruDanekontaBO(Konto p) {
        p.setId(null);
        p.setBoWn(0);
        p.setBoMa(0);
        p.setObrotyWn(0);
        p.setObrotyMa(0);
        p.setKontopozycjaID(null);
    }

    private Konto kopiujKonto(Konto p, List<Konto> macierzystelista, String podatnikDocelowy, boolean slownikowe) {
        Konto r = serialclone.SerialClone.clone(p);
        zeruDanekontaBO(r);
        r.setPodatnik(podatnikDocelowy);
        r.setRok(Integer.parseInt(rokdocelowy));
        r.setSlownikowe(slownikowe);
        Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
        r.setMacierzyste(macierzyste.getPelnynumer());
        r.setMacierzysty(macierzyste.getId());
        r.setKontomacierzyste(macierzyste);
        return r;
    }

    
    private Konto wyszukajmacierzyste(String macierzyste, List<Konto> macierzystelista) {
        for (Konto p : macierzystelista) {
            if (p.getPelnynumer().equals(macierzyste)) {
                return p;
            }
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Podatnik getPodatnikzrodlowy() {
        return podatnikzrodlowy;
    }

    public void setPodatnikzrodlowy(Podatnik podatnikzrodlowy) {
        this.podatnikzrodlowy = podatnikzrodlowy;
    }

    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }

    public Podatnik getPodatnikdocelowy() {
        return podatnikdocelowy;
    }

    public void setPodatnikdocelowy(Podatnik podatnikdocelowy) {
        this.podatnikdocelowy = podatnikdocelowy;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getRokzrodlowy() {
        return rokzrodlowy;
    }

    public void setRokzrodlowy(String rokzrodlowy) {
        this.rokzrodlowy = rokzrodlowy;
    }

    public String getRokdocelowy() {
        return rokdocelowy;
    }

    public void setRokdocelowy(String rokdocelowy) {
        this.rokdocelowy = rokdocelowy;
    }

//</editor-fold>
}
