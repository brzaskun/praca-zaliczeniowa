/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
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
public class PlanKontKopiujView implements Serializable{
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
    
    @PostConstruct
    private void init() {
        rokzrodlowy = wpisView.getRokWpisuSt();
        rokdocelowy = wpisView.getRokWpisuSt();
    }
    
    public void kopiujplankont() {
        if (podatnikzrodlowy.equals(podatnikdocelowy) && rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Podatnik oraz rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy.getNazwapelna(), rokzrodlowy);
            List<Konto> macierzyste = skopiujlevel0(podatnikdocelowy.getNazwapelna(),wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(podatnikzrodlowy.getNazwapelna(), Integer.parseInt(rokzrodlowy));
            for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevel(podatnikzrodlowy.getNazwapelna(), podatnikdocelowy.getNazwapelna(), wykazkont, macierzyste,i, rokdocelowy);
            }
            System.out.println("Kopiuje");
        }
        System.out.println("Kopiuje");
    }
    
    public void kopiujplankontWzorcowy() {
        if (rokzrodlowy.equals(rokdocelowy)) {
            Msg.msg("e", "Rok źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", rokzrodlowy);
            List<Konto> macierzyste = skopiujlevel0("Wzorcowy", wykazkont, rokdocelowy);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik("Wzorcowy", Integer.parseInt(rokzrodlowy));
            for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelWzorcowy("Wzorcowy",wykazkont, macierzyste,i, rokdocelowy);
            }
            System.out.println("Kopiuje");
        }
    }
    
    public void implementujplankontWzorcowy() {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", rokzrodlowy);
        if (wpisView.isFKpiatki() == false) {
            for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                Konto p = it.next();
                if (p.getNrkonta().startsWith("5")) {
                    it.remove();
                }
            }
        }
        List<Konto> macierzyste = skopiujlevel0(wpisView.getPodatnikWpisu(), wykazkont, rokdocelowy);
        int maxlevel = kontoDAOfk.findMaxLevelPodatnik("Wzorcowy", Integer.parseInt(rokzrodlowy));
        for(int i = 1; i <= maxlevel;i++) {
            macierzyste = skopiujlevel("Wzorcowy", wpisView.getPodatnikWpisu(),wykazkont, macierzyste,i, rokdocelowy);
        }
        System.out.println("Implementuje plan kont");
        planKontView.init();
    }
    
    
    
    private List<Konto> skopiujlevel0(String podatnikDocelowy, List<Konto> wykazkont, String rokDocelowy) {
        List<Konto> macierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel()==0) {
                Konto r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnikDocelowy);
                r.setRok(Integer.parseInt(rokDocelowy));
                try {
                    kontoDAOfk.dodaj(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
     private List<Konto> skopiujlevel(String podatnikzrodlowy, String podatnikDocelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int i, String rokdocelowy) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel()==i) {
                try {
                    if (!podatnikzrodlowy.equals(podatnikdocelowy) && p.isSlownikowe()) {
                        System.out.println("nie powielam słownikowego");
                    } else {
                        Konto r = serialclone.SerialClone.clone(p);
                        r.setPodatnik(podatnikDocelowy);
                        r.setRok(Integer.parseInt(rokdocelowy));
                        Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
                        r.setMacierzyste(macierzyste.getPelnynumer());
                        r.setMacierzysty(macierzyste.getId());
                        kontoDAOfk.dodaj(r);
                        nowemacierzyste.add(r);
                    }
                } catch (Exception e) {  E.e(e);
                    
                }
            }
        }
        return nowemacierzyste;
    }
     
      private List<Konto> skopiujlevelWzorcowy(String docelowy, List<Konto> wykazkont, List<Konto> macierzystelista, int i, String rokdocelowy) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel()==i) {
                try {
                    Konto r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(docelowy);
                    r.setRok(Integer.parseInt(rokdocelowy));
                    Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
                    r.setMacierzyste(macierzyste.getPelnynumer());
                    r.setMacierzysty(macierzyste.getId());
                    kontoDAOfk.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {  E.e(e);
                    
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
