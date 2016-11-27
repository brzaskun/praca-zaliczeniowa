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
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

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
    @Inject
    private PlatnoscWaluta nowa;
    
    @PostConstruct
    private void init() {
        kontrahentypodatnika = new ArrayList<>();
        skrotwalutywdokum = new ArrayList<>();
        dokumentypodatnika = new ArrayList<>();
        walutywdokum = new ArrayList<>();
        dokumenty = dokDAO.zwrocBiezacegoKlientaRokMCWaluta(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
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
            d.add(tmpx.getTypdokumentu());
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
        nowa = new PlatnoscWaluta(selected);
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

    
    
    
    
}
