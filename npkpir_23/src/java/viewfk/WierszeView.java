/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.WierszeDAO;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class WierszeView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private WierszeDAO wierszeDAO;
    private List<Wiersz> wiersze;
    private List<Wiersz> wybranewierszeWNT;
    private List<Wiersz> wybranewierszeWDT;
    private List<Wiersz> wierszeWNT;
    private List<Wiersz> wierszeWDT;
    private double sumakg;
    private double sumaszt;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean tylkobezrozrachunkow;
    
    public void init(){
        wiersze = wierszeDAO.findWierszePodatnikMcRok(wpisView.getPodatnikObiekt(), wpisView);
        if (tylkobezrozrachunkow == true) {
            for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
                Wiersz p = (Wiersz) it.next();
                Konto kwn = p.getStronaWn() != null ? p.getStronaWn().getKonto() : null;
                Konto kma = p.getStronaMa() != null ? p.getStronaMa().getKonto() : null;
                boolean kwnbrak = false;
                boolean kmabrak = false;
                if (kwn != null) {
                    if (kwn.getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                        if (p.getStronaWn().getTypStronaWiersza() == 0) {
                            kwnbrak = true;
                        }
                    }
                }
                if (kma != null) {
                    if (kma.getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                        if (p.getStronaMa().getTypStronaWiersza() == 0) {
                            kwnbrak = true;
                        }
                    }
                }
                if (kwnbrak == false && kmabrak == false) {
                    it.remove();
                }
            }
        }
        System.out.println("d");
    }
    
    public void initwierszeWNT(){
        wierszeWNT = wierszeDAO.findWierszePodatnikMcRokWNTWDT(wpisView.getPodatnikObiekt(), wpisView, "WNT");
        double kg = 0.0;
        double szt = 0.0;
        double wartoscWnPLN = 0.0;
        double wartoscMaPLN = 0.0;
        for(Iterator<Wiersz> it = wierszeWNT.iterator(); it.hasNext();) {
            Wiersz p = (Wiersz) it.next();
            if (p.getIlosc_kg() == 0.0 && p.getIlosc_szt() == 0.0) {
                it.remove();
            }
            kg += p.getIlosc_kg();
            szt += p.getIlosc_szt();
            wartoscWnPLN += p.getKwotaWnPLN();
            wartoscMaPLN += p.getKwotaMaPLN();
        }
        Wiersz w = new Wiersz();
        w.setIdwiersza(wierszeWNT.get(wierszeWNT.size()-1).getIdwiersza()+1);
        w.setIdporzadkowy(wierszeWNT.size());
        w.setDokfk(new Dokfk(new DokfkPK()));
        w.setDataksiegowania("");
        w.getDokfk().setNumerwlasnydokfk("");
        w.setStronaWn(new StronaWiersza(w, "Wn"));
        w.setStronaMa(new StronaWiersza(w, "Ma"));
        w.setOpisWiersza("podsumowanie");
        w.setIlosc_kg(kg);
        w.setIlosc_szt(szt);
        w.getStronaWn().setKwota(0.0);
        w.getStronaMa().setKwota(0.0);
        w.getStronaWn().setKwotaPLN(wartoscWnPLN);
        w.getStronaMa().setKwotaPLN(wartoscMaPLN);
        wierszeWNT.add(w);
    }
    
    public void initwierszeWDT(){
        wierszeWDT = wierszeDAO.findWierszePodatnikMcRokWNTWDT(wpisView.getPodatnikObiekt(), wpisView, "WDT");
        double kg = 0.0;
        double szt = 0.0;
        double wartoscWnPLN = 0.0;
        double wartoscMaPLN = 0.0;
        for(Iterator<Wiersz> it = wierszeWDT.iterator(); it.hasNext();) {
            Wiersz p = (Wiersz) it.next();
            if (p.getIlosc_kg() == 0.0 && p.getIlosc_szt() == 0.0) {
                it.remove();
            }
            kg += p.getIlosc_kg();
            szt += p.getIlosc_szt();
            wartoscWnPLN += p.getKwotaWnPLN();
            wartoscMaPLN += p.getKwotaMaPLN();
        }
        Wiersz w = new Wiersz();
        w.setIdwiersza(wierszeWDT.get(wierszeWDT.size()-1).getIdwiersza()+1);
        w.setIdporzadkowy(wierszeWDT.size());
        w.setDokfk(new Dokfk(new DokfkPK()));
        w.setDataksiegowania("");
        w.getDokfk().setNumerwlasnydokfk("");
        w.setStronaWn(new StronaWiersza(w, "Wn"));
        w.setStronaMa(new StronaWiersza(w, "Ma"));
        w.setOpisWiersza("podsumowanie");
        w.setIlosc_kg(kg);
        w.setIlosc_szt(szt);
        w.getStronaWn().setKwota(0.0);
        w.getStronaMa().setKwota(0.0);
        w.getStronaWn().setKwotaPLN(wartoscWnPLN);
        w.getStronaMa().setKwotaPLN(wartoscMaPLN);
        wierszeWDT.add(w);
    }
    
    
    public void odswiezzaksiegowane() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wiersze = wierszeDAO.findWierszePodatnikRok(wpisView.getPodatnikObiekt(), wpisView);
            RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        } else {
            wpisView.wpisAktualizuj();
            wiersze = wierszeDAO.findWierszePodatnikMcRok(wpisView.getPodatnikObiekt(), wpisView);
            RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        }
    }
    
    public void odswiezzaksiegowaneWNT() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wierszeWNT = new ArrayList<>();
            wierszeWDT = new ArrayList<>();
        } else {
            wpisView.wpisAktualizuj();
            initwierszeWNT();
        }
    }
    
     public void odswiezzaksiegowaneWDT() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wierszeWNT = new ArrayList<>();
            wierszeWDT = new ArrayList<>();
        } else {
            wpisView.wpisAktualizuj();
            initwierszeWDT();
        }
    }

    public void sumazapisowtotalWNT() {
        sumakg = 0.0;
        sumaszt = 0.0;
        for (Wiersz p : wybranewierszeWNT) {
            sumakg += p.getIlosc_kg();
            sumaszt += p.getIlosc_szt();
        }
    }
    
    public void sumazapisowtotalWDT() {
        sumakg = 0.0;
        sumaszt = 0.0;
        for (Wiersz p : wybranewierszeWDT) {
            sumakg += p.getIlosc_kg();
            sumaszt += p.getIlosc_szt();
        }
    }
    
    public List<Wiersz> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<Wiersz> wiersze) {
        this.wiersze = wiersze;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Wiersz> getWierszeWNT() {
        return wierszeWNT;
    }

    public void setWierszeWNT(List<Wiersz> wierszeWNT) {
        this.wierszeWNT = wierszeWNT;
    }

    public List<Wiersz> getWierszeWDT() {
        return wierszeWDT;
    }

    public void setWierszeWDT(List<Wiersz> wierszeWDT) {
        this.wierszeWDT = wierszeWDT;
    }

    public List<Wiersz> getWybranewierszeWNT() {
        return wybranewierszeWNT;
    }

    public void setWybranewierszeWNT(List<Wiersz> wybranewierszeWNT) {
        this.wybranewierszeWNT = wybranewierszeWNT;
    }

    public double getSumakg() {
        return sumakg;
    }

    public void setSumakg(double sumakg) {
        this.sumakg = sumakg;
    }

    public double getSumaszt() {
        return sumaszt;
    }

    public void setSumaszt(double sumaszt) {
        this.sumaszt = sumaszt;
    }

    public List<Wiersz> getWybranewierszeWDT() {
        return wybranewierszeWDT;
    }

    public void setWybranewierszeWDT(List<Wiersz> wybranewierszeWDT) {
        this.wybranewierszeWDT = wybranewierszeWDT;
    }

    public boolean isTylkobezrozrachunkow() {
        return tylkobezrozrachunkow;
    }

    public void setTylkobezrozrachunkow(boolean tylkobezrozrachunkow) {
        this.tylkobezrozrachunkow = tylkobezrozrachunkow;
    }
    
    
     
    
}
