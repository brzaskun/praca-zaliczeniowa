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
public class PlanKontKopiujView implements Serializable {

    private Podatnik podatnikzrodlowy;
    private Podatnik podatnikdocelowy;
    private String rokzrodlowy_wzorzec;
    private String rokzrodlowy;
    private String rokdocelowy;
    private boolean kopiujSlownikowe;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{planKontView}")
    private PlanKontView planKontView;
    @Inject
    private KontoDAOfk kontoDAOfk;

    public PlanKontKopiujView() {
         E.m(this);
    }
    
    

    @PostConstruct
    private void init() {
        rokzrodlowy = Roki.rokPoprzedni(wpisView.getRokWpisuSt());
        rokzrodlowy_wzorzec = wpisView.getRokWpisuSt();
        rokdocelowy = wpisView.getRokWpisuSt();
        kopiujSlownikowe = false;
        podatnikzrodlowy = wpisView.getPodatnikObiekt();
        podatnikdocelowy = wpisView.getPodatnikObiekt();
    }

    public void kopiujplankont() {
        if (podatnikzrodlowy.equals(podatnikdocelowy) && rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Podatnik oraz rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy.getNazwapelna(), rokzrodlowy);
            List<Konto> macierzyste = skopiujlevel0(podatnikdocelowy.getNazwapelna(), wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(podatnikzrodlowy.getNazwapelna(), Integer.parseInt(rokzrodlowy));
            for (int biezacylevel = 1; biezacylevel <= maxlevel; biezacylevel++) {
                macierzyste = skopiujlevel(podatnikzrodlowy.getNazwapelna(), podatnikdocelowy.getNazwapelna(), wykazkont, macierzyste, biezacylevel, rokdocelowy);
            }
            System.out.println("Kopiuje");
            planKontView.init();
            planKontView.porzadkowanieKontPodatnika();
            kopiujSlownikowe = false;
        }
    }

    public void kopiujplankontWzorcowy() {
        if (rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", rokzrodlowy);
            List<Konto> macierzyste = skopiujlevel0("Wzorcowy", wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik("Wzorcowy", Integer.parseInt(rokzrodlowy));
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevelWzorcowy("Wzorcowy", wykazkont, macierzyste, i, rokdocelowy);
            }
            kopiujSlownikowe = false;
            System.out.println("Kopiuje");
        }
    }

    public void implementujplankontWzorcowy() {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", rokzrodlowy);
        if (wpisView.isFKpiatki() == false) {
            for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                Konto p = it.next();
                if (p.getPelnynumer().startsWith("5")) {
                    it.remove();
                }
            }
        }
        List<Konto> macierzyste = skopiujlevel0(wpisView.getPodatnikWpisu(), wykazkont, rokdocelowy);
        int maxlevel = kontoDAOfk.findMaxLevelPodatnik("Wzorcowy", Integer.parseInt(rokzrodlowy));
        for (int i = 1; i <= maxlevel; i++) {
            macierzyste = skopiujlevel("Wzorcowy", wpisView.getPodatnikWpisu(), wykazkont, macierzyste, i, rokdocelowy);
        }
        planKontView.init();
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

    private List<Konto> skopiujlevel(String podatnikzrodlowy, String podatnikDocelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int biezacylevel, String rokdocelowy) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel() == biezacylevel) {
                try {
                    if (!podatnikzrodlowy.equals(podatnikDocelowy) && p.isSlownikowe()) {
                        System.out.println("nie powielam słownikowego");
                    } else if (p.isSlownikowe() == true && kopiujSlownikowe) {
                        nowemacierzyste.add(kopiujKonto(p, macierzystelista, podatnikDocelowy, true));
                    } else if (p.isSlownikowe() == false) {
                        nowemacierzyste.add(kopiujKonto(p, macierzystelista, podatnikDocelowy, false));
                    }
                } catch (Exception e) {
                    E.e(e);
                }
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
        if (p.getPelnynumer().equals("010-5")) {
            System.out.println("");
        }
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

    private List<Konto> skopiujlevelWzorcowy(String docelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int i, String rokdocelowy) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel() == i) {
                try {
                    Konto r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(docelowy);
                    r.setRok(Integer.parseInt(rokdocelowy));
                    Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
                    r.setMacierzyste(macierzyste.getPelnynumer());
                    r.setMacierzysty(macierzyste.getId());
                    r.setKontomacierzyste(macierzyste);
                    kontoDAOfk.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    E.e(e);

                }
            }
        }
        return nowemacierzyste;
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

    public String getRokzrodlowy_wzorzec() {
        return rokzrodlowy_wzorzec;
    }

    public void setRokzrodlowy_wzorzec(String rokzrodlowy_wzorzec) {
        this.rokzrodlowy_wzorzec = rokzrodlowy_wzorzec;
    }

    public boolean isKopiujSlownikowe() {
        return kopiujSlownikowe;
    }

    public void setKopiujSlownikowe(boolean kopiujSlownikowe) {
        this.kopiujSlownikowe = kopiujSlownikowe;
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
