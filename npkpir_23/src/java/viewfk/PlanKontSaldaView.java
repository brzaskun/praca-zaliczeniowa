/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import pdffk.PdfPlanKont;
import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontSaldaView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<Konto> wykazkont;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    private String infozebrakslownikowych;
    private Konto selectednodekonto;
    private boolean bezslownikowych;
    private boolean tylkosyntetyka;
    private String kontadowyswietlenia;

    public void init() { //E.m(this);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        int czysaslownikowe = sprawdzkonta();
        if (czysaslownikowe == 0) {
            infozebrakslownikowych = " Brak podłączonych słowników do kont rozrachunkowych! Nie można księgować kontrahentów.";
        } else if (czysaslownikowe == 1) {
            infozebrakslownikowych = " Brak planu kont na dany rok";
        } else {
            infozebrakslownikowych = "";
        }
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownikKsiegi(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
        bezslownikowych = true;
        kontadowyswietlenia = "wszystkie";
    }

    private int sprawdzkonta() {
        int zwrot = 0;
        if (wykazkont == null || wykazkont.size() == 0) {
            zwrot = 1;
        } else {
            for (Konto p : wykazkont) {
                if (p.isSlownikowe() == true) {
                    zwrot = 2;
                    break;
                }
            }
        }
        return zwrot;
    }

    public void planBezSlownikowychSyntetyczne() {
        if (bezslownikowych == true && tylkosyntetyka == true) {
            wykazkont = kontoDAOfk.findKontazLevelu(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(),0);
        } else if (bezslownikowych == true) {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownikKsiegi(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        } else if (tylkosyntetyka == true) {
            wykazkont = kontoDAOfk.findKontazLevelu(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(),0);
        } else {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaKsiegi(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        if (kontadowyswietlenia.equals("bilansowe")) {
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("wynikowe")) {
                    it.remove();
                }
            }
        }
        if (kontadowyswietlenia.equals("wynikowe")) {
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("bilansowe")) {
                    it.remove();
                }
            }
        }
        Collections.sort(wykazkont, new Kontocomparator());
    }
    public void drukujPlanKont(String parametr) {
        switch (parametr) {
            case "all":
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "wynikowe":
                wykazkont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "bilansowe":
                wykazkont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "tłumaczenie":
                PdfPlanKont.drukujPlanKontTłumaczenie(wykazkont, wpisView);
                break;
            default:
                String kontonrs = "%"+parametr;
                wykazkont = kontoDAOfk.findKontaGrupa(wpisView, kontonrs);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;

        }
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
    }
    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isBezslownikowych() {
        return bezslownikowych;
    }

    public void setBezslownikowych(boolean bezslownikowych) {
        this.bezslownikowych = bezslownikowych;
    }

    public boolean isTylkosyntetyka() {
        return tylkosyntetyka;
    }

    public void setTylkosyntetyka(boolean tylkosyntetyka) {
        this.tylkosyntetyka = tylkosyntetyka;
    }

    public String getKontadowyswietlenia() {
        return kontadowyswietlenia;
    }

    public void setKontadowyswietlenia(String kontadowyswietlenia) {
        this.kontadowyswietlenia = kontadowyswietlenia;
    }

    public Konto getSelectednodekonto() {
        return selectednodekonto;
    }

    public void setSelectednodekonto(Konto selectednodekonto) {
        this.selectednodekonto = selectednodekonto;
    }

    public String getInfozebrakslownikowych() {
        return infozebrakslownikowych;
    }

    public void setInfozebrakslownikowych(String infozebrakslownikowych) {
        this.infozebrakslownikowych = infozebrakslownikowych;
    }

}
