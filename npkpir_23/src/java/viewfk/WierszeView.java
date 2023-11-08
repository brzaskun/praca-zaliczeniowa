/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.CechazapisuDAOfk;
import dao.WierszeDAO;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdffk.PdfWiersz;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WierszeView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private WierszeDAO wierszeDAO;
    private List<Wiersz> wiersze;
    private List<Wiersz> wierszefiltered;
    private List<Wiersz> wybranewierszeWNT;
    private List<Wiersz> wybranewierszeWDT;
    private List<Wiersz> wierszeWNT;
    private List<Wiersz> wierszeWDT;
    private List<Wiersz> wierszezestawienie;
    private List<Cechazapisu> pobranecechypodatnik;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    private Cechazapisu wybranacecha;
    private double sumakg;
    private double sumaszt;
    private double sumawn;
    private double sumama;
    @Inject
    private WpisView wpisView;
    private boolean tylkobezrozrachunkow;

    public WierszeView() {
         
    }
    
//    public void nowewiersze() {
//        List<Wiersz> wierszedo = wierszeDAO.findWierszePodatnikRokMCDo(wpisView.getPodatnikObiekt(), wpisView);
//        if (!wiersze.isEmpty()&&!wpisView.getMiesiacWpisu().equals("01")) {
//            Set<Konto> kontastare = new HashSet<>();
//            wierszedo.stream().forEach(p -> {
//                kontastare.add(p.getKontoWn());
//                kontastare.add(p.getKontoMa());
//            });
//            for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
//                Wiersz w = it.next();
//                if (kontastare.contains(w.getKontoWn())&&kontastare.contains(w.getKontoMa())) {
//                    it.remove();
//                }
//            }
//            Msg.msg("Zakończono usuwanie starych kont");
//        }
//    }
    @PostConstruct
    public void inita() {
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
    }
    
    public void init() { //E.m(this);
        
        sumawn = 0.0;
        sumama = 0.0;
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wiersze = wierszeDAO.findWierszePodatnikRok(wpisView.getPodatnikObiekt(), wpisView);
        } else {
            wiersze = wierszeDAO.findWierszePodatnikMcRok(wpisView.getPodatnikObiekt(), wpisView);
        }
        if (tylkobezrozrachunkow == true) {
            for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
                Wiersz p = (Wiersz) it.next();
                Konto kwn = p.getStronaWn() != null ? p.getStronaWn().getKonto() : null;
                Konto kma = p.getStronaMa() != null ? p.getStronaMa().getKonto() : null;
                boolean kwnbrak = false;
                boolean kmabrak = false;
                if (kwn != null) {
                    sumawn += p.getKwotaWnPLN();
                    if (kwn.getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                        if (p.getStronaWn().getTypStronaWiersza() == 0) {
                            kwnbrak = true;
                        }
                    }
                }
                if (kma != null) {
                    sumama += p.getKwotaMaPLN();
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
        } else {
            for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
                Wiersz p = (Wiersz) it.next();
                Konto kwn = p.getStronaWn() != null ? p.getStronaWn().getKonto() : null;
                Konto kma = p.getStronaMa() != null ? p.getStronaMa().getKonto() : null;
                if (kwn != null) {
                    sumawn += p.getKwotaWnPLN();
                }
                if (kma != null) {
                    sumama += p.getKwotaMaPLN();
                }
            }
        }
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
        w.setDokfk(new Dokfk());
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
        w.setDokfk(new Dokfk());
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
    
    public void naniesceche() {
        if (wybranacecha!=null) {
            if (wierszezestawienie!=null&&wierszezestawienie.size()>0) {
                for (Wiersz w : wierszezestawienie) {
                    List<StronaWiersza> stronyWiersza = w.getStronyWiersza();
                    for (StronaWiersza sw : stronyWiersza) {
                        if (sw.getKonto()!=null&&sw.getKonto().isWynik0bilans1()==false) {
                            List<Cechazapisu> cechazapisuLista = sw.getCechazapisuLista();
                            if (cechazapisuLista==null) {
                                cechazapisuLista = new ArrayList();
                            }
                            cechazapisuLista.add(wybranacecha);
                        }
                    }
                }
                wierszeDAO.editList(wierszezestawienie);
               Msg.msg("Nadano cechę");
            } else {
                Msg.msg("e","Nie wybrano zapisów do nadania cech");
            }
        } else {
            Msg.msg("e","Nie wybrano cechy do nadania");
        }
    }
    
    public void usunceche() {
        if (wybranacecha!=null) {
            if (wierszezestawienie!=null&&wierszezestawienie.size()>0) {
                for (Wiersz w : wierszezestawienie) {
                    List<StronaWiersza> stronyWiersza = w.getStronyWiersza();
                    for (StronaWiersza sw : stronyWiersza) {
                        if (sw.getKonto()!=null&&sw.getKonto().isWynik0bilans1()==false) {
                            List<Cechazapisu> cechazapisuLista = sw.getCechazapisuLista();
                            for (Iterator<Cechazapisu> it = cechazapisuLista.iterator();it.hasNext();)  {
                                Cechazapisu cecha = it.next();
                                if (cecha.equals(wybranacecha)) {
                                    it.remove();
                                }
                            }
                        }
                    }
                }
                wierszeDAO.editList(wierszezestawienie);
               Msg.msg("Usunięto cechę");
            } else {
                Msg.msg("e","Nie wybrano zapisów do usunięcia cech");
            }
        } else {
            Msg.msg("e","Nie wybrano cechy do usunięcia");
        }
    }
    
    
    public void odswiezzaksiegowane() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            init();
        } else {
            wpisView.wpisAktualizuj();
            init();
        }
    }
    
    public void odswiezzaksiegowaneWNT() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wierszeWNT = Collections.synchronizedList(new ArrayList<>());
            wierszeWDT = Collections.synchronizedList(new ArrayList<>());
        } else {
            wpisView.wpisAktualizuj();
            initwierszeWNT();
        }
    }
    
     public void odswiezzaksiegowaneWDT() {
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            wierszeWNT = Collections.synchronizedList(new ArrayList<>());
            wierszeWDT = Collections.synchronizedList(new ArrayList<>());
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
    
    public void drukujWiersze() {
        sumujwiersze();
        Wiersz suma = new Wiersz();
        suma.setStronaWn(new StronaWiersza());
        suma.setStronaMa(new StronaWiersza());
        suma.setOpisWiersza("podsumowanie");
        suma.getStronaWn().setKwota(sumawn);
        suma.getStronaMa().setKwota(sumama);
        if (wierszefiltered != null) {
            wierszefiltered.add(suma);
            PdfWiersz.drukuj(wierszefiltered, wpisView);
        } else {
            wiersze.add(suma);
            PdfWiersz.drukuj(wiersze, wpisView);
        }
        Msg.msg("Wydrukowano wiersz");
    }
    
    public void sumujwiersze() {
    sumawn = 0.0;
    sumama = 0.0;
    List<Wiersz> wybrane = wierszefiltered != null ? wierszefiltered : wiersze;  
    for (Iterator<Wiersz> it = wybrane.iterator(); it.hasNext();) {
                Wiersz p = (Wiersz) it.next();
                Konto kwn = p.getStronaWn() != null ? p.getStronaWn().getKonto() : null;
                Konto kma = p.getStronaMa() != null ? p.getStronaMa().getKonto() : null;
                if (kwn != null) {
                    sumawn += p.getKwotaWnPLN();
                }
                if (kma != null) {
                    sumama += p.getKwotaMaPLN();
                }
            }
    }
    
    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

//
//     public void usunwybranewiersze() {
//        if (wiersze!=null&&wiersze.size()>1) {
//            try {
//                List<Wiersz> dousu = new ArrayList<>();
//                for (Wiersz w : wiersze) {
//                    if (w.getDokfk().getSeriadokfk().equals("BO")) {
//                        if (w.getOpisWiersza().contains("WBPOST")||w.getOpisWiersza().contains("WBPAYPAL")) {
//                            dousu.add(w);
//                        } else if (w.getWierszBOId()==0) {
//                            dousu.add(w);
//                        }
//                    }
//                }
//                wierszeDAO.removeList(dousu);
//                wiersze.removeAll(dousu);
//                Msg.msg("Usunięto wybrane wiersze z dokumentów");
//            } catch (Exception e) {
//                Msg.msg("e","Błąd podczas usuwania wierszy");
//            }
//            
//        }
//    }
//<editor-fold defaultstate="collapsed" desc="comment">
    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {    
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public double getSumawn() {
        return sumawn;
    }
    
    public void setSumawn(double sumawn) {
        this.sumawn = sumawn;
    }
    
    public double getSumama() {
        return sumama;
    }
    
    public void setSumama(double sumama) {
        this.sumama = sumama;
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
    
    public List<Wiersz> getWierszefiltered() {
        return wierszefiltered;
    }
    
    public void setWierszefiltered(List<Wiersz> wierszefiltered) {
        this.wierszefiltered = wierszefiltered;
    }
    
    public boolean isTylkobezrozrachunkow() {
        return tylkobezrozrachunkow;
    }
    
    public void setTylkobezrozrachunkow(boolean tylkobezrozrachunkow) {
        this.tylkobezrozrachunkow = tylkobezrozrachunkow;
    }

    public List<Wiersz> getWierszezestawienie() {
        return wierszezestawienie;
    }

    public void setWierszezestawienie(List<Wiersz> wierszezestawienie) {
        this.wierszezestawienie = wierszezestawienie;
    }
    
    
    
    
//</editor-fold>

    public Cechazapisu getWybranacecha() {
        return wybranacecha;
    }

    public void setWybranacecha(Cechazapisu wybranacecha) {
        this.wybranacecha = wybranacecha;
    }
     
    
}
