/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.TabelaNBPBean;
import dao.DokDAO;
import dao.PlatnoscWalutaDAO;
import daoFK.TabelanbpDAO;
import entity.Dok;
import entity.PlatnoscWaluta;
import entityfk.Waluty;
import error.E;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;import org.joda.time.DateTime;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlatnosciWalutyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PlatnoscWalutaDAO platnoscWalutaDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<Dok> dokumenty;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private Dok selected;
    private List<String> kontrahentypodatnika;
    private List<String> skrotwalutywdokum;
    private List<Waluty> walutywdokum;
    private List<String> dokumentypodatnika;
    private List<Dok> dokumentyFiltered;
    private List<PlatnoscWaluta> platnosci;
    private List<PlatnoscWaluta> wszystkieplatnosci;
    @Inject
    private PlatnoscWaluta nowa;
    private double dorozliczenia;
    private boolean ukryjrozliczone;
    private double kwotadorozliczenia;
    private double sumadokum;
    private double sumaplatnosci;
    private double sumaprzychplus;
    private double sumaprzychminus;
    private double sumakosztplus;
    private double sumakosztminus;
    
    @PostConstruct
    private void init() {
        ukryjrozliczone = true;
        kontrahentypodatnika = Collections.synchronizedList(new ArrayList<>());
        skrotwalutywdokum = Collections.synchronizedList(new ArrayList<>());
        dokumentypodatnika = Collections.synchronizedList(new ArrayList<>());
        walutywdokum = Collections.synchronizedList(new ArrayList<>());
        zmienliste();
        Set<String> k = new HashSet<>();
        Set<String> w = new HashSet<>();
        Set<String> d = new HashSet<>();
        Set<Waluty> wd = new HashSet<>();
        for (Dok tmpx  : dokumenty) {
            k.add(tmpx.getKontr().getNpelna());
            w.add(tmpx.getWalutadokumentu() != null ? tmpx.getWalutadokumentu().getSymbolwaluty() : "PLN");
            if (tmpx.getWalutadokumentu() != null) {
                wd.add(tmpx.getWalutadokumentu());
            }
            d.add(tmpx.getRodzajedok().getSkrot());
        }
        kontrahentypodatnika.addAll(k);
        skrotwalutywdokum.addAll(w);
        dokumentypodatnika.addAll(d);
        walutywdokum.addAll(wd);
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        Collections.sort(kontrahentypodatnika, collator);
        Collections.sort(skrotwalutywdokum, collator);
        Collections.sort(dokumentypodatnika, collator);
        
    }

    public void pobierzplatnosci() {
        List<PlatnoscWaluta> p = platnoscWalutaDAO.findByDok(selected);
        nowa = new PlatnoscWaluta(selected, wpisView);
        dorozliczenia = selected.getNettoWaluta();
        if (p != null) {
            platnosci = p;
            for (PlatnoscWaluta r : p) {
                dorozliczenia -= r.getKwota();
            }
            dorozliczenia = Z.z(dorozliczenia);
        } else {
            platnosci = Collections.synchronizedList(new ArrayList<>());
        }
    }
    
    public void pobierzkursNBP(ValueChangeEvent el) {
        try {
            Waluty wal = (Waluty) el.getNewValue();
            if (!wal.getSymbolwaluty().equals("PLN")) {
                DateTime dzienposzukiwany = new DateTime(nowa.getDataplatnosci());
                nowa.setTabelanbp(TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, wal.getSymbolwaluty()));
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void przelicz(ValueChangeEvent e) {
        if (selected != null) {
            if (dorozliczenia > 0.0) {
                double kwota = (double) e.getNewValue();
                if (kwota > dorozliczenia) {
                    kwota = dorozliczenia;
                    Msg.msg("w", "Ograniczono kwotę rozliczenia do makimum");
                }
                nowa.setKwota(kwota);
                double kwotapln = Z.z(kwota * nowa.getTabelanbp().getKurssredni());
                nowa.setKwotapln(kwotapln);
                double roznice = Z.z(kwota * selected.getTabelanbp().getKurssredni()) - kwotapln;
                nowa.setRoznice(-roznice);
            } else {
                Msg.msg("e", "Wszystko już rozliczono");
            }
        } else {
            Msg.msg("e", "Nie wybrano faktury");
        }
    }
    
    public void dodaj() {
        if (selected != null) {
            if (dorozliczenia > 0.0) {
                if (selected.getPlatnosciwaluta() == null) {
                    selected.setPlatnosciwaluta(new ArrayList<>());
                }
                selected.getPlatnosciwaluta().add(nowa);
                dokDAO.edit(selected);
                platnosci.add(nowa);
                dorozliczenia -= nowa.getKwota();
                dorozliczenia = Z.z(dorozliczenia);
                nowa = new PlatnoscWaluta();
                kwotadorozliczenia = 0.0;
                Msg.msg("i","Dodano płatność");
            } else {
                Msg.msg("w","Całość została już wcześniej rozliczona. Nie dodano pozycji");
            }
        } else {
            Msg.msg("e","Nie wybrano faktury");
        }
    }
    
    public void usun(PlatnoscWaluta p) {
        if (p != null) {
            selected.getPlatnosciwaluta().remove(p);
            dokDAO.edit(selected);
            platnosci.remove(p);
            dorozliczenia += p.getKwota();
            dorozliczenia = Z.z(dorozliczenia);
            Msg.msg("i","Usunięto płatność");
        } else {
            Msg.msg("e","Nie udane usunięcie płatności");
        }
    }
    
    public void zmienliste() {
        dokumenty = dokDAO.zwrocBiezacegoKlientaRokMCWaluta(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (ukryjrozliczone) {
            for (Iterator<Dok> it = dokumenty.iterator(); it.hasNext();) {
                double suma = obliczsume(it.next());
                if (suma == 0.0) {
                    it.remove();
                }
            }
        }
    }
    
    public double obliczsume(Dok dok) {
        double suma = dok.getNettoWaluta();
        List<PlatnoscWaluta> p = dok.getPlatnosciwaluta();
        for (PlatnoscWaluta r : p) {
            suma -= r.getKwota();
        }
        return Z.z(suma);
    }
    
    
    public void pobierzwszystkie() {
        sumadokum = 0.0;
        sumaplatnosci = 0.0;
        sumaprzychplus = 0.0;
        sumaprzychminus = 0.0;
        sumakosztplus = 0.0;
        sumakosztminus  = 0.0;
        wszystkieplatnosci = platnoscWalutaDAO.findByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        for (PlatnoscWaluta p : wszystkieplatnosci) {
            sumadokum += p.getDokument().getNetto();
            sumaplatnosci += p.getKwotapln();
            sumaprzychplus += p.getSprzedazPlus();
            sumaprzychminus += p.getSprzedazMinus();
            sumakosztplus += p.getZakupPlus();
            sumakosztminus += p.getZakupMinus();
        }
    }
    
    public List<Dok> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<Dok> dokumenty) {
        this.dokumenty = dokumenty;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dok getSelected() {
        return selected;
    }

    public void setSelected(Dok selected) {
        this.selected = selected;
    }

    public List<String> getKontrahentypodatnika() {
        return kontrahentypodatnika;
    }

    public void setKontrahentypodatnika(List<String> kontrahentypodatnika) {
        this.kontrahentypodatnika = kontrahentypodatnika;
    }

    public List<String> getSkrotwalutywdokum() {
        return skrotwalutywdokum;
    }

    public void setSkrotwalutywdokum(List<String> skrotwalutywdokum) {
        this.skrotwalutywdokum = skrotwalutywdokum;
    }

    public List<Dok> getDokumentyFiltered() {
        return dokumentyFiltered;
    }

    public void setDokumentyFiltered(List<Dok> dokumentyFiltered) {
        this.dokumentyFiltered = dokumentyFiltered;
    }

    public List<String> getDokumentypodatnika() {
        return dokumentypodatnika;
    }

    public void setDokumentypodatnika(List<String> dokumentypodatnika) {
        this.dokumentypodatnika = dokumentypodatnika;
    }

    public List<PlatnoscWaluta> getPlatnosci() {
        return platnosci;
    }

    public void setPlatnosci(List<PlatnoscWaluta> platnosci) {
        this.platnosci = platnosci;
    }

    public List<Waluty> getWalutywdokum() {
        return walutywdokum;
    }

    public void setWalutywdokum(List<Waluty> walutywdokum) {
        this.walutywdokum = walutywdokum;
    }

    public PlatnoscWaluta getNowa() {
        return nowa;
    }

    public void setNowa(PlatnoscWaluta nowa) {
        this.nowa = nowa;
    }

    public double getDorozliczenia() {
        return dorozliczenia;
    }

    public void setDorozliczenia(double dorozliczenia) {
        this.dorozliczenia = dorozliczenia;
    }

    public boolean isUkryjrozliczone() {
        return ukryjrozliczone;
    }

    public void setUkryjrozliczone(boolean ukryjrozliczone) {
        this.ukryjrozliczone = ukryjrozliczone;
    }

    public double getKwotadorozliczenia() {
        return kwotadorozliczenia;
    }

    public void setKwotadorozliczenia(double kwotadorozliczenia) {
        this.kwotadorozliczenia = kwotadorozliczenia;
    }

    public List<PlatnoscWaluta> getWszystkieplatnosci() {
        return wszystkieplatnosci;
    }

    public void setWszystkieplatnosci(List<PlatnoscWaluta> wszystkieplatnosci) {
        this.wszystkieplatnosci = wszystkieplatnosci;
    }

    public double getSumadokum() {
        return Z.z(sumadokum);
    }

    public void setSumadokum(double sumadokum) {
        this.sumadokum = sumadokum;
    }

    public double getSumaplatnosci() {
        return Z.z(sumaplatnosci);
    }

    public void setSumaplatnosci(double sumaplatnosci) {
        this.sumaplatnosci = sumaplatnosci;
    }

    public double getSumaprzychplus() {
        return Z.z(sumaprzychplus);
    }

    public void setSumaprzychplus(double sumaprzychplus) {
        this.sumaprzychplus = sumaprzychplus;
    }

    public double getSumaprzychminus() {
        return Z.z(sumaprzychminus);
    }

    public void setSumaprzychminus(double sumaprzychminus) {
        this.sumaprzychminus = sumaprzychminus;
    }

    public double getSumakosztplus() {
        return Z.z(sumakosztplus);
    }

    public void setSumakosztplus(double sumakosztplus) {
        this.sumakosztplus = sumakosztplus;
    }

    public double getSumakosztminus() {
        return Z.z(sumakosztminus);
    }

    public void setSumakosztminus(double sumakosztminus) {
        this.sumakosztminus = sumakosztminus;
    }

    
    
    
    
}
