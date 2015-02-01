/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entity.Podatnik;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    
    public void kopiujplankont() {
        if (podatnikzrodlowy.equals(podatnikdocelowy)) {
            Msg.msg("e", "Podatnik źródłowy i docelowy jest ten sam");
        } else {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(podatnikzrodlowy.getNazwapelna(), wpisView.getRokWpisuSt());
            List<Konto> macierzyste = skopiujlevel0(wykazkont);
            int maxlevel = kontoDAOfk.findMaxLevelPodatnik(podatnikzrodlowy.getNazwapelna(), wpisView.getRokWpisu());
            for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevel(wykazkont, macierzyste,i);
            }
            System.out.println("Kopiuje");
        }
        System.out.println("Kopiuje");
    }
    
    private List<Konto> skopiujlevel0(List<Konto> wykazkont) {
        List<Konto> macierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel()==0) {
                Konto r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnikdocelowy.getNazwapelna());
                r.setRok(wpisView.getRokWpisu());
                try {
                    kontoDAOfk.dodaj(r);
                } catch (Exception e) {
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
    

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Podatnik getPodatnikzrodlowy() {
        return podatnikzrodlowy;
    }
    
    public void setPodatnikzrodlowy(Podatnik podatnikzrodlowy) {
        this.podatnikzrodlowy = podatnikzrodlowy;
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
    
    
//</editor-fold>

    private List<Konto> skopiujlevel(List<Konto> wykazkont, List<Konto> macierzystelista, int i) {
        List<Konto> nowemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getLevel()==i) {
                Konto r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnikdocelowy.getNazwapelna());
                r.setRok(wpisView.getRokWpisu());
                Konto macierzyste = wyszukajmacierzyste(r.getMacierzyste(), macierzystelista);
                r.setMacierzyste(macierzyste.getPelnynumer());
                r.setMacierzysty(macierzyste.getId());
                try {
                    kontoDAOfk.dodaj(r);
                } catch (Exception e) {
                    
                }
                nowemacierzyste.add(r);
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

   
    
}
