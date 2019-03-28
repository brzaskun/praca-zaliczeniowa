/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKKopiujBean;
import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import embeddable.Roki;
import entity.Podatnik;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
         //E.m(this);
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
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(podatnikzrodlowy, rokzrodlowy);
            Collections.sort(wykazkont, new Kontocomparator());
            List<Konto> macierzyste = PlanKontFKKopiujBean.skopiujlevel0(kontoDAOfk,podatnikdocelowy, wykazkont, rokdocelowy);
            Collections.sort(macierzyste, new Kontocomparator());
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(podatnikzrodlowy, Integer.parseInt(rokzrodlowy));
            for (int biezacylevel = 1; biezacylevel <= maxlevel; biezacylevel++) {
                macierzyste = PlanKontFKKopiujBean.skopiujlevel(kontoDAOfk,podatnikzrodlowy, podatnikdocelowy, wykazkont, macierzyste, biezacylevel, rokdocelowy, kopiujSlownikowe);
            }
            planKontView.init();
            planKontView.porzadkowanieKontPodatnika(wpisView.getPodatnikObiekt());
            kopiujSlownikowe = false;
            Msg.msg("Skopiowano plan kont z firmy do firmy");
        }
    }

    public void kopiujplankontWzorcowy() {
        try {
            if (rokzrodlowy.equals(rokdocelowy)) {
                Msg.msg("e", "Rok źródłowy i docelowy jest ten sam");
            } else {
                List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(wpisView.getPodatnikwzorcowy(), rokzrodlowy);
                List<Konto> macierzyste = PlanKontFKKopiujBean.skopiujlevel0(kontoDAOfk, wpisView.getPodatnikwzorcowy(), wykazkont, rokdocelowy);
                int maxlevel = kontoDAOfk.findMaxLevelPodatnik(wpisView.getPodatnikwzorcowy(), Integer.parseInt(rokzrodlowy));
                for (int i = 1; i <= maxlevel; i++) {
                    macierzyste = skopiujlevelWzorcowy(wpisView.getPodatnikwzorcowy(), wykazkont, macierzyste, i, rokdocelowy);
                }
                kopiujSlownikowe = false;
                Msg.msg("Skopiowano wzorcowy plan kont");
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie skopiowano wzorcowego planu kont");
        }
    }

    public void implementujplankontWzorcowy() {
        List<Konto> wykazkontpodatnika = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(wpisView.getPodatnikObiekt(), rokdocelowy);
        if (wykazkontpodatnika.isEmpty()) {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(wpisView.getPodatnikwzorcowy(), rokzrodlowy_wzorzec);
            if (wpisView.isParamCzworkiPiatki() == false) {
                for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                    Konto p = it.next();
                    if (p.getPelnynumer().startsWith("5")) {
                        it.remove();
                    }
                }
            }
            List<Konto> macierzyste = PlanKontFKKopiujBean.skopiujlevel0(kontoDAOfk, wpisView.getPodatnikObiekt(), wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(wpisView.getPodatnikwzorcowy(), Integer.parseInt(rokzrodlowy_wzorzec));
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = PlanKontFKKopiujBean.skopiujlevel(kontoDAOfk, wpisView.getPodatnikwzorcowy(), wpisView.getPodatnikObiekt(), wykazkont, macierzyste, i, rokdocelowy, kopiujSlownikowe);
            }
            planKontView.init();
            Msg.msg("Zaimplementowano wzorcowy plan kont z roku "+rokzrodlowy_wzorzec);
        } else {
            
            Msg.msg("w","Istnieje plan kont podatnika. Dokonam uzupełnienia kont i sprawdzę nazwy z roku "+rokzrodlowy_wzorzec);
        }
    }

    

    private List<Konto> skopiujlevelWzorcowy(Podatnik docelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int i, String rokdocelowy) {
        List<Konto> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : wykazkont) {
            if (p.getLevel() == i) {
                try {
                    Konto r = serialclone.SerialClone.clone(p);
                    r.setKontopozycjaID(null);
                    r.setPodatnik(docelowy);
                    r.setRok(Integer.parseInt(rokdocelowy));
                    Konto macierzyste = PlanKontFKKopiujBean.wyszukajmacierzyste(r.getKontomacierzyste().getPelnynumer(), macierzystelista);
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
