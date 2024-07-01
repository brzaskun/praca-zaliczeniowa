/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKKopiujBean;
import comparator.Kontocomparator;
import dao.KontoDAOfk;
import embeddable.Roki;
import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PlanKontKopiujView implements Serializable {

    private Podatnik podatnikzrodlowy;
    private Podatnik podatnikdocelowy;
    private String rokzrodlowy_wzorzec;
    private String rokzrodlowy;
    private String rokdocelowy;
    private boolean kopiujSlownikowe;
    @Inject
    private WpisView wpisView;
    @Inject
    private PlanKontView planKontView;
    @Inject
    private KontoDAOfk kontoDAOfk;

    public PlanKontKopiujView() {
         ////E.m(this);
    }
    
    

    @PostConstruct
    private void init() { //E.m(this);
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
            planKontView.porzadkowanieKontPodatnikaNowe(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
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
                    macierzyste = PlanKontFKKopiujBean.skopiujlevelWzorcowy(kontoDAOfk, wpisView.getPodatnikwzorcowy(), wykazkont, macierzyste, i, rokdocelowy);
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
//            List<Konto> kontazrodlowe = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(podatnikzrodlowy, rokzrodlowy);
//            List<Konto> kontadocelowe = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(podatnikdocelowy, rokdocelowy);
//            List<Konto> brakujacelevel0 = Collections.synchronizedList(new ArrayList<>());
//            List<Konto> brakujacelevelinne = Collections.synchronizedList(new ArrayList<>());
//            for (Konto p : kontazrodlowe) {
//                if (!kontadocelowe.contains(p)) {
//                    if (p.isSlownikowe() == false) {
//                        if (p.getKontomacierzyste() == null) {
//                            brakujacelevel0.add(p);
//                        } else {
//                            brakujacelevelinne.add(p);
//                        }
//                    }
//                    if (p.isSlownikowe() == true && p.getNrkonta().equals("0")) {
//                        brakujacelevelinne.add(p);
//                    }
//                }
//            }
//            skopiujlevel0(wpisView.getPodatnikObiekt(), brakujacelevel0, rokdocelowy);
//            kontadocelowe = kontoDAOfk.findWszystkieKontaPodatnika(podatnikdocelowy, rokdocelowy);
//            skopiujlevel(wpisView.getPodatnikObiekt(), brakujacelevelinne, kontadocelowe, rokdocelowy);
//            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = Collections.synchronizedList(new ArrayList<>());
//            List<Konto> brakujacekonta = Collections.synchronizedList(new ArrayList<>());
//            brakujacekonta.addAll(brakujacelevel0);
//            brakujacekonta.addAll(brakujacelevelinne);
//            for (Konto p : brakujacekonta) {
//                zapisanePOzycjezUkladuWzorcowego.addAll(kontopozycjaZapisDAO.findByKontoOnly(p));
//            }
//            List<KontopozycjaZapis> nowekontopozycjazapis = Collections.synchronizedList(new ArrayList<>());
//            for (KontopozycjaZapis r : zapisanePOzycjezUkladuWzorcowego) {
//                UkladBR uklad = odnajdzuklad(ukladBRDAO, r.getUkladBR(), wpisView.getRokWpisuSt());
//                if (uklad == null || uklad.getUklad() == null) {
//                    break;
//                }
//                Konto starekonto = pobierzkontozlisty(brakujacekonta, r);
//                Konto nowekonto = kontoDAOfk.findKonto(starekonto.getPelnynumer(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
//                if (starekonto != null && nowekonto != null) {
//                    KontopozycjaZapis kp = new KontopozycjaZapis(r, nowekonto, uklad);
//                    nowekontopozycjazapis.add(kp);
//                }
//            }
//            kontopozycjaZapisDAO.create(nowekontopozycjazapis);
            Msg.msg("w","Istnieje plan kont podatnika. Dokonam uzupełnienia kont i sprawdzę nazwy z roku "+rokzrodlowy_wzorzec);
        }
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
