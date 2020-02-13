/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.WalutyFKBean;
import comparator.Tabelanbpcomparator;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszDAO;
import entity.Podatnik;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.text.ParseException;
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
import javax.inject.Inject;
import msg.Msg;import org.primefaces.event.RowEditEvent;
import view.WpisView; import org.primefaces.PrimeFaces;
import waluty.WalutyNBP;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class WalutyViewFK implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WierszDAO wierszDAO;
    private List<Waluty> pobraneRodzajeWalut;
    private List<Waluty> walutywuzyciu;
    private List<String> symboleWalut;
    private List<Tabelanbp> pobranekursyRok;
    private List<Tabelanbp> wprowadzonekursyRok;
    private List<String> symboleTabelRecznie;
    private String symbolRecznie;
    @Inject 
    private Tabelanbp kurswprowadzonyrecznie;
    @Inject
    private Waluty nowawaluta;
    @ManagedProperty(value = "#{walutyFKBean}")
    private WalutyFKBean walutyFKBean;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{dokfkView}")
    private DokfkView dokfkView;
    private String datawstepna;
    private String nrtabeli;
    
    
    @Inject
    private WalutyNBP walutyNBP;

    public WalutyViewFK() {
         ////E.m(this);
        pobraneRodzajeWalut = Collections.synchronizedList(new ArrayList<>());
        walutywuzyciu = Collections.synchronizedList(new ArrayList<>());
        symboleWalut = Collections.synchronizedList(new ArrayList<>());
        wprowadzonekursyRok = Collections.synchronizedList(new ArrayList<>());
        symboleTabelRecznie = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        pobraneRodzajeWalut = walutyDAOfk.findAll();
        pobranekursyRok = tabelanbpDAO.findKursyRokNBP(wpisView.getRokWpisuSt());
        wprowadzonekursyRok = tabelanbpDAO.findKursyRokNieNBP(wpisView.getRokWpisuSt());
        for (Waluty w : pobraneRodzajeWalut) {
            symboleWalut.add(w.getSymbolwaluty());
            if (!w.getSymbolwaluty().equals("PLN")) {
                walutywuzyciu.add(w);
            }
            if (w.getSymbolwaluty().equals("EUR")) {
                kurswprowadzonyrecznie.setWaluta(w);
            }
        }
        Set<String> st = new HashSet<>();
        for(Tabelanbp p : wprowadzonekursyRok) {
            st.add(p.getNrtabeli().split("/")[2]);
        }
        symboleTabelRecznie.addAll(st);
        Collections.sort(symboleTabelRecznie);
        if (symboleTabelRecznie.size()>0) {
            symbolRecznie = symboleTabelRecznie.get(0);
        }
        if (!symboleTabelRecznie.isEmpty()) {
            kurswprowadzonyrecznie.setNrtabeli(generujNumerTabeli(symbolRecznie, wprowadzonekursyRok));
        }
    }
    
    public void pobierzkursyWalutaRecznie() {
        try {
            ////E.m(this);
            Integer numertabeli = Integer.valueOf(nrtabeli);
            List<Tabelanbp> wierszepobranezNBP = Collections.synchronizedList(new ArrayList<>());
            try {
                for (Waluty p : pobraneRodzajeWalut) {
                    wierszepobranezNBP.addAll(walutyNBP.pobierzjedenpliknbp(datawstepna, numertabeli, p.getSymbolwaluty()));
                }
                if (wierszepobranezNBP.isEmpty()) {
                    Msg.msg("e", "Nie ma takiej tabeli. Nie udalo sie pobrac kursow walut z internetu");
                } else {
                    try {
                        tabelanbpDAO.dodaj(wierszepobranezNBP);
                    } catch (Exception e) { 
                        E.e(e);
                    }
                    Msg.msg("i", "Udalo sie pobrac kursow walut z internetu");
                    init();
                }
            } catch (Exception e) {
                Msg.msg("e", "Nie udalo sie pobrac kursow walut z internetu");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void generujNumerTabeli() {
        kurswprowadzonyrecznie.setNrtabeli(generujNumerTabeli(symbolRecznie, wprowadzonekursyRok));
    }
    
    private String generujNumerTabeli(String symbolRecznie, List<Tabelanbp> wprowadzonekursyRok) {
        List<Tabelanbp> odnalezioneTabele = Collections.synchronizedList(new ArrayList<>());
        if (wprowadzonekursyRok.size() > 0) {
            for (Tabelanbp p : wprowadzonekursyRok) {
                if (p.getNrtabeli().contains(symbolRecznie)) {
                    odnalezioneTabele.add(p);
                }
            }
        }
        if (odnalezioneTabele.size() > 2) {
            Collections.sort(odnalezioneTabele, new Tabelanbpcomparator());
            return numerzwiekszonyojeden(odnalezioneTabele.get(odnalezioneTabele.size()-1));
        } else if (odnalezioneTabele.size() == 1) {
            return numerzwiekszonyojeden(odnalezioneTabele.get(0));
        } else {
            return nowynumer(symbolRecznie);
        }
    }

    private String nowynumer(String symbolRecznie) {
        String s = symbolRecznie.toUpperCase();
        symboleTabelRecznie.add(s);
        return "001/A/"+s+"/"+wpisView.getRokWpisuSt();
    }

    private String numerzwiekszonyojeden(Tabelanbp get) {
        String poczatek = get.getNrtabeli().substring(0,3);
        Integer numer = Integer.parseInt(poczatek);
        int nowy = 0;
        String zwrot = null;
        if (numer < 9) {
            nowy = numer +1;
            zwrot = "00"+nowy+get.getNrtabeli().substring(3);
        } else if (numer < 99) {
            nowy = numer +1;
            zwrot = "0"+nowy+get.getNrtabeli().substring(3);
        } else {
            nowy = numer +1;
            zwrot = nowy+get.getNrtabeli().substring(3);
        }
        return zwrot;
    }
    
    public void dodajnowawalute() {
        try {
            nowawaluta.setSymbolwaluty(nowawaluta.getSymbolwaluty().toUpperCase(new Locale("pl")));
            nowawaluta.setNazwawaluty(nowawaluta.getNazwawaluty().toLowerCase(new Locale("pl")));
            walutyDAOfk.dodaj(nowawaluta);
            pobraneRodzajeWalut.add(nowawaluta);
            nowawaluta = new Waluty();
            Msg.msg("i", "Dodano nową walute");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nie dodano nowej waluty");
        }
    }
    
    public void pobierzkursy() throws ParseException {
        List<Tabelanbp> wierszepobranezNBP = Collections.synchronizedList(new ArrayList<>());
        wierszepobranezNBP.addAll(walutyFKBean.pobierzkursy(tabelanbpDAO, walutyDAOfk));
        for (Tabelanbp p : wierszepobranezNBP) {
            pobranekursyRok.add(p);
        }
    }
    
    public void usunwalute(Waluty waluty) {
        try {
            walutyDAOfk.destroy(waluty);
            pobraneRodzajeWalut.remove(waluty);
            Msg.msg("Usunięto walutę.");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e","Istnieją zapisy w walucie, nie można jej usunąć!");
        }
    }
    
    public void dodajkurs(Tabelanbp tabelanbp, Podatnik podatnik) {
        try {
            tabelanbp.setNrtabeli(tabelanbp.getNrtabeli().toUpperCase(new Locale("PL")));
            tabelanbp.setRecznie(true);
            tabelanbp.setPodatnik(podatnik);
            tabelanbpDAO.dodaj(tabelanbp);
            wprowadzonekursyRok.add(tabelanbp);
            dokfkView.zamienkursnareczny(tabelanbp);
            kurswprowadzonyrecznie = new Tabelanbp();
            kurswprowadzonyrecznie.setNrtabeli(generujNumerTabeli(tabelanbp.getNrtabeli().split("/")[2], wprowadzonekursyRok));
            Msg.msg("Dodałem tabelę NBP");
            PrimeFaces.current().ajax().update(":formkursrecznie :formwpisdokument:panelTabelaNBP :wprowadzonekursyrecznie");
            PrimeFaces.current().executeScript("PF('dialogkursrecznie').hide()");
            PrimeFaces.current().executeScript("powrotpozmianietabeli()");
        } catch (Exception e) {  
            E.e(e);
            List<Tabelanbp> kursypokrewne = Collections.synchronizedList(new ArrayList<>());
            for (Tabelanbp p : wprowadzonekursyRok) {
                if (p.getNrtabeli().contains(tabelanbp.getNrtabeli().substring(3))) {
                    kursypokrewne.add(p);
                }
            }
            int max = 0;
            for (Tabelanbp t : kursypokrewne) {
                int numer = Integer.parseInt(t.getNrtabeli().substring(0,3));
                if (numer > max) {
                    max = numer;
                }
            }
            String numerstring = null;
            if (max < 10) {
                numerstring = "00"+String.valueOf(max);
            } else if (max < 99) {
                numerstring = "0"+String.valueOf(max);
            } else {
                numerstring = String.valueOf(max);
            }
            Msg.msg("e","Ostatni numer zapisany w bazie to "+numerstring+". Nie można wprowadzić kursu.");
            PrimeFaces.current().executeScript("r('formkursrecznie:dataKursReczny:0:numertabeli').focus();");
        }
    }
    
    public void usuntabele(Tabelanbp tabelanbp) {
        tabelanbpDAO.destroy(tabelanbp);
        wprowadzonekursyRok.remove(tabelanbp);
    }
    
    public void handleSave (Waluty w) {
        if (w != null) {
            walutyDAOfk.edit(w);
        }
    }
    
    public void onRowEdit(RowEditEvent event) {
        try {
            Tabelanbp rzad = (Tabelanbp) event.getObject();
            tabelanbpDAO.edit(rzad);
            przewalutujwiersze(rzad);
            Msg.msg("Zmieniono kurs waluty. Zamknij i otwórz ponownie dokument");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się zmienić kursu waluty");
        }
    }
    
    private void przewalutujwiersze(Tabelanbp tabela) {
        List<Wiersz> wiersze = wierszDAO.pobierzWiersze(tabela, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (Wiersz p : wiersze) {
            if (p.getTypWiersza() == 0 || p.getTypWiersza() == 1) {
                if (p.getStronaWn().getKwota() != 0.0) {
                    double kwota = p.getStronaWn().getKwota();
                    p.getStronaWn().setKwotaPLN(Z.z(kwota * tabela.getKurssredniPrzelicznik()));
                }
            }
            if (p.getTypWiersza() == 0 || p.getTypWiersza() == 2) {
                if (p.getStronaMa().getKwota() != 0.0) {
                    double kwota = p.getStronaMa().getKwota();
                    p.getStronaMa().setKwotaPLN(Z.z(kwota * tabela.getKurssredniPrzelicznik()));
                }
            }
            wierszDAO.edit(p);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<Waluty> getWalutywuzyciu() {
        return walutywuzyciu;
    }

    public void setWalutywuzyciu(List<Waluty> walutywuzyciu) {
        this.walutywuzyciu = walutywuzyciu;
    }

   

    public String getDatawstepna() {
        return datawstepna;
    }

    public void setDatawstepna(String datawstepna) {
        this.datawstepna = datawstepna;
    }

    public String getNrtabeli() {
        return nrtabeli;
    }

    public void setNrtabeli(String nrtabeli) {
        this.nrtabeli = nrtabeli;
    }

    
    public Tabelanbp getKurswprowadzonyrecznie() {
        return kurswprowadzonyrecznie;
    }

    public void setKurswprowadzonyrecznie(Tabelanbp kurswprowadzonyrecznie) {
        this.kurswprowadzonyrecznie = kurswprowadzonyrecznie;
    }
    
    
    public WalutyFKBean getWalutyFKBean() {
        return walutyFKBean;
    }

    public void setWalutyFKBean(WalutyFKBean walutyFKBean) {
        this.walutyFKBean = walutyFKBean;
    }

    public List<String> getSymboleWalut() {
        return symboleWalut;
    }

    public void setSymboleWalut(List<String> symboleWalut) {
        this.symboleWalut = symboleWalut;
    }

    public List<Waluty> getPobraneRodzajeWalut() {
        return pobraneRodzajeWalut;
    }

    public void setPobraneRodzajeWalut(List<Waluty> pobraneRodzajeWalut) {
        this.pobraneRodzajeWalut = pobraneRodzajeWalut;
    }

    public Waluty getNowawaluta() {
        return nowawaluta;
    }

    public void setNowawaluta(Waluty nowawaluta) {
        this.nowawaluta = nowawaluta;
    }
    
    
    public List<Tabelanbp> getPobranekursyRok() {
        return pobranekursyRok;
    }

    public void setPobranekursyRok(List<Tabelanbp> pobranekursyRok) {
        this.pobranekursyRok = pobranekursyRok;
    }
    
    

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getSymbolRecznie() {
        return symbolRecznie;
    }

    public void setSymbolRecznie(String symbolRecznie) {
        this.symbolRecznie = symbolRecznie;
    }

    public List<String> getSymboleTabelRecznie() {
        return symboleTabelRecznie;
    }

    public void setSymboleTabelRecznie(List<String> symboleTabelRecznie) {
        this.symboleTabelRecznie = symboleTabelRecznie;
    }

    public DokfkView getDokfkView() {
        return dokfkView;
    }

    public void setDokfkView(DokfkView dokfkView) {
        this.dokfkView = dokfkView;
    }

    

    public List<Tabelanbp> getWprowadzonekursyRok() {
        return wprowadzonekursyRok;
    }

    public void setWprowadzonekursyRok(List<Tabelanbp> wprowadzonekursyRok) {
        this.wprowadzonekursyRok = wprowadzonekursyRok;
    }
    
    
    
    
    //</editor-fold>

    

   
}
