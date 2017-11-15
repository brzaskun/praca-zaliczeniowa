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
        kopiujSlownikowe = true;
        podatnikzrodlowy = wpisView.getPodatnikObiekt();
        podatnikdocelowy = wpisView.getPodatnikObiekt();
    }

    public void kopiujplankont() {
        List<Konto> plankont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (plankont != null && !plankont.isEmpty()) {
            Msg.msg("e", "W bieżącym roku istnieje już plan kont firmy. Usuń go najpierw, aby skopiować plan kont z innego roku/firmy");
        } else if (podatnikzrodlowy.equals(podatnikdocelowy) && rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Podatnik oraz rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy, rokzrodlowy);
            List<Konto> macierzyste = skopiujlevel0(podatnikdocelowy, wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(podatnikzrodlowy, Integer.parseInt(rokzrodlowy));
            for (int biezacylevel = 1; biezacylevel <= maxlevel; biezacylevel++) {
                macierzyste = skopiujlevel(podatnikzrodlowy, podatnikdocelowy, wykazkont, macierzyste, biezacylevel, rokdocelowy);
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
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(null, rokzrodlowy);
            List<Konto> macierzyste = skopiujlevel0(null, wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(null, Integer.parseInt(rokzrodlowy));
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevelWzorcowy(null, wykazkont, macierzyste, i, rokdocelowy);
            }
            kopiujSlownikowe = false;
            System.out.println("Kopiuje");
        }
    }

    public void implementujplankontWzorcowy() {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(null, rokzrodlowy_wzorzec);
        if (wpisView.isParamCzworkiPiatki() == false) {
            for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                Konto p = it.next();
                if (p.getPelnynumer().startsWith("5")) {
                    it.remove();
                }
            }
        }
        List<Konto> macierzyste = skopiujlevel0(wpisView.getPodatnikObiekt(), wykazkont, rokdocelowy);
        int maxlevel = kontoDAOfk.findMaxLevelPodatnik(null, Integer.parseInt(rokzrodlowy_wzorzec));
        for (int i = 1; i <= maxlevel; i++) {
            macierzyste = skopiujlevel(null, wpisView.getPodatnikObiekt(), wykazkont, macierzyste, i, rokdocelowy);
        }
        planKontView.init();
        System.out.println("koniec implementacji");
    }

    private List<Konto> skopiujlevel0(Podatnik podatnikDocelowy, List<Konto> wykazkont, String rokDocelowy) {
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

    private List<Konto> skopiujlevel(Podatnik podatnikzrodlowy, Podatnik podatnikDocelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int biezacylevel, String rokdocelowy) {
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

    private Konto kopiujKonto(Konto p, List<Konto> macierzystelista, Podatnik podatnikDocelowy, boolean slownikowe) {
        if (p.getPelnynumer().equals("010-5")) {
            System.out.println("");
        }
        Konto r = serialclone.SerialClone.clone(p);
        zeruDanekontaBO(r);
        r.setKontopozycjaID(null);
        r.setPodatnik(podatnikDocelowy);
        r.setRok(Integer.parseInt(rokdocelowy));
        r.setSlownikowe(slownikowe);
        Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
        r.setMacierzyste(macierzyste.getPelnynumer());
        r.setMacierzysty(macierzyste.getId());
        r.setKontomacierzyste(macierzyste);
        return r;
    }

    private List<Konto> skopiujlevelWzorcowy(Podatnik docelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int i, String rokdocelowy) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel() == i) {
                try {
                    Konto r = serialclone.SerialClone.clone(p);
                    r.setKontopozycjaID(null);
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
