/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.WalutyFKBean;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

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
    private List<Waluty> pobraneRodzajeWalut;
    private List<Waluty> walutywuzyciu;
    private List<String> symboleWalut;
    private List<Tabelanbp> pobranekursy;
    private List<Tabelanbp> pobranekursyRok;
    private List<Tabelanbp> wprowadzonekursyRok;
    @Inject
    private Tabelanbp kurswprowadzonyrecznie;
    @Inject
    private Waluty nowawaluta;
    @ManagedProperty(value = "#{walutyFKBean}")
    private WalutyFKBean walutyFKBean;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public WalutyViewFK() {
        pobraneRodzajeWalut = new ArrayList<>();
        pobranekursy = new ArrayList<>();
        walutywuzyciu = new ArrayList<>();
        symboleWalut = new ArrayList<>();
        wprowadzonekursyRok = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        pobraneRodzajeWalut = walutyDAOfk.findAll();
        pobranekursy = tabelanbpDAO.findAll();
        pobranekursyRok = tabelanbpDAO.findKursyRokNBP(wpisView.getRokWpisuSt());
        wprowadzonekursyRok = tabelanbpDAO.findKursyRokNieNBP(wpisView.getRokWpisuSt());
        for (Waluty w : pobraneRodzajeWalut) {
            symboleWalut.add(w.getSymbolwaluty());
            if (!w.getSymbolwaluty().equals("PLN")) {
                walutywuzyciu.add(w);
            }
        }
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
        List<Tabelanbp> wierszepobranezNBP = new ArrayList<>();
        wierszepobranezNBP.addAll(walutyFKBean.pobierzkursy(tabelanbpDAO, walutyDAOfk));
        for (Tabelanbp p : wierszepobranezNBP) {
            pobranekursy.add(p);
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
    
    public void dodajkurs(Tabelanbp tabelanbp) {
        try {
            tabelanbp.setNrtabeli(tabelanbp.getNrtabeli().toUpperCase(new Locale("PL")));
            tabelanbp.setRecznie(true);
            tabelanbpDAO.dodaj(tabelanbp);
            wprowadzonekursyRok.add(tabelanbp);
            kurswprowadzonyrecznie = new Tabelanbp();
            Msg.msg("Dodałem tabelę NBP");
            RequestContext.getCurrentInstance().update("formkursrecznie");
        } catch (Exception e) {  
            E.e(e);
            List<Tabelanbp> kursypokrewne = new ArrayList<>();
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
            RequestContext.getCurrentInstance().execute("r('formkursrecznie:dataKursReczny:0:numertabeli').focus();");
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

    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<Waluty> getWalutywuzyciu() {
        return walutywuzyciu;
    }

    public void setWalutywuzyciu(List<Waluty> walutywuzyciu) {
        this.walutywuzyciu = walutywuzyciu;
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

    public List<Tabelanbp> getPobranekursy() {
        return pobranekursy;
    }

    public void setPobranekursy(List<Tabelanbp> pobranekursy) {
        this.pobranekursy = pobranekursy;
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

    

    public List<Tabelanbp> getWprowadzonekursyRok() {
        return wprowadzonekursyRok;
    }

    public void setWprowadzonekursyRok(List<Tabelanbp> wprowadzonekursyRok) {
        this.wprowadzonekursyRok = wprowadzonekursyRok;
    }
    
    
    
    
    //</editor-fold>
}
