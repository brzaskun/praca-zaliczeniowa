/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.SlownikiBean;
import comparator.Kliencifkcomparator;
import dao.KlienciDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.UkladBRDAO;
import entity.Klienci;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
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
public class KliencifkView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private Klienci wybranyklient;
    private Klienci wybranyklient1;
    private List<Klienci> listawszystkichklientow;
    private List<Kliencifk> listawszystkichklientowFk;
    @Inject
    private Kliencifk klientMaKonto;
    @Inject
    private Kliencifk klientBezKonta;
    @Inject
    private KliencifkDAO kliencifkDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private Kliencifk selected;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{dokfkView}")
    private DokfkView dokfkView;
    @ManagedProperty(value = "#{planKontCompleteView}")
    private PlanKontCompleteView planKontCompleteView;
    private boolean makonto0niemakonta1;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    private boolean zapisz0edytuj1;

    public KliencifkView() {
        listawszystkichklientow = new ArrayList<>();
        listawszystkichklientowFk = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        listawszystkichklientow = klienciDAO.findAll();
        listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
    }

    public void dolaczanieKontrDoSlownikowych() {
        int wynik = pobieraniekontaFK();
        if (wynik == 1) {
            pobieraniekontaFKWpisCD();
        }
    }
    
    public void pobieraniekontaFKWpis() {
        if (dokfkView.isNiedodawajkontapole() == false) {
            if (dokfkView.getRodzajBiezacegoDokumentu() != 0 && dokfkView.getRodzajBiezacegoDokumentu() != 5) {
                wybranyklient = dokfkView.selected.getKontr();
                if (wybranyklient == null) {
                    wybranyklient = new Klienci("222222222222222222222", "BRAK FIRMY JAKO KONTRAHENTA!!!");
                } else if (!wybranyklient.getNpelna().equals("nowy klient")) {
                    int wynik = pobieraniekontaFK();
                    if (wynik == 1) {
                        makonto0niemakonta1 = true;
                    }
                }
            }
        }
    }

    public void resetujmakontoniemakonta() {
        makonto0niemakonta1 = false;
        RequestContext.getCurrentInstance().update("formwpisdokument:wybranawaluta");
    }

    public void pobieraniekontaFKWpisCD() {
        //tworzenie nowego
        klientBezKonta = new Kliencifk();
        klientBezKonta.setNazwa(wybranyklient.getNpelna());
        klientBezKonta.setNip(wybranyklient.getNip());
        klientBezKonta.setPodatniknazwa(wpisView.getPodatnikWpisu());
        klientBezKonta.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
        klientBezKonta.setNrkonta(pobierznastepnynumer());
        przyporzadkujdokonta();
        resetujmakontoniemakonta();
        planKontCompleteView.init();
    }

    public int pobieraniekontaFK() {
        if (wybranyklient instanceof Klienci && !wybranyklient.getNpelna().equals("nowy klient")) {
            try {
                klientMaKonto = kliencifkDAO.znajdzkontofk(wybranyklient.getNip(), wpisView.getPodatnikObiekt().getNip());
                if (klientMaKonto != null) {
                    return 0;
                } else {
                    klientBezKonta = new Kliencifk();
                    klientBezKonta.setNazwa(wybranyklient.getNpelna());
                    klientBezKonta.setNip(wybranyklient.getNip());
                    klientBezKonta.setPodatniknazwa(wpisView.getPodatnikWpisu());
                    klientBezKonta.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
                    klientBezKonta.setNrkonta(pobierznastepnynumer());
                    return 1;
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
        return -1;
    }

    public void przyporzadkujdokonta() {
        try {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            kliencifkDAO.dodaj(klientBezKonta);
            int wynik = PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientBezKonta, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nieudane przyporządkowanie klienta do konta");
        }
        wybranyklient = new Klienci();
        klientMaKonto = new Kliencifk();
        klientBezKonta = new Kliencifk();
    }
    
    public void kopiujwybranyklient(ValueChangeEvent e) {
        wybranyklient1 = serialclone.SerialClone.clone((Klienci) e.getNewValue());
        System.out.println("e");
    }

    private String pobierznastepnynumer() {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            Collections.sort(przyporzadkowani, new Kliencifkcomparator());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size() - 1).getNrkonta()) + 1);
        } catch (Exception e) {
            E.e(e);
            return "1";
        }
    }

    public int sortKliencifk(Object o1, Object o2) {
        int nr1 = Integer.parseInt(((Kliencifk) o1).getNrkonta());
        int nr2 = Integer.parseInt(((Kliencifk) o2).getNrkonta());
        if (nr1 > nr2) {
            return 1;
        } else if (nr1 < nr2) {
            return -1;
        }
        return 0;
    }

    public void remove(Kliencifk klientkontodousuniecia) {
        try {
            kliencifkDAO.destroy(klientkontodousuniecia);
            int wynik = PlanKontFKBean.aktualizujslownikKontrahenciRemove(klientkontodousuniecia, kontoDAOfk, wpisView);
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            Msg.msg("Usunięto konta słownikowe dla klienta " + klientkontodousuniecia.getNazwa());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił problem z usuwaniem kont słownikowych dla klienta");
        }
        wybranyklient = new Klienci();
        klientMaKonto = new Kliencifk();
        klientBezKonta = new Kliencifk();
    }

    public boolean isMakonto0niemakonta1() {
        return makonto0niemakonta1;
    }
    
    public void edytuj(Kliencifk kliencifk) {
        selected = kliencifk;
        zapisz0edytuj1 = true;
    }
    
    public void zapiszedycje() {
        kliencifkDAO.edit(selected);
        SlownikiBean.aktualizujkontapoedycji(selected, 1, wpisView, kontoDAOfk);
        selected = new Kliencifk();
        listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        zapisz0edytuj1 = false;
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public void setMakonto0niemakonta1(boolean makonto0niemakonta1) {
        this.makonto0niemakonta1 = makonto0niemakonta1;
    }

    public List<Klienci> getListawszystkichklientow() {
        return listawszystkichklientow;
    }

    public Klienci getWybranyklient1() {
        return wybranyklient1;
    }

    public void setWybranyklient1(Klienci wybranyklient1) {
        this.wybranyklient1 = wybranyklient1;
    }

    public Kliencifk getSelected() {
        return selected;
    }

    public void setSelected(Kliencifk selected) {
        this.selected = selected;
    }

    public void setListawszystkichklientow(List<Klienci> listawszystkichklientow) {
        this.listawszystkichklientow = listawszystkichklientow;
    }

    public DokfkView getDokfkView() {
        return dokfkView;
    }

    public void setDokfkView(DokfkView dokfkView) {
        this.dokfkView = dokfkView;
    }

    public Klienci getWybranyklient() {
        return wybranyklient;
    }

    public void setWybranyklient(Klienci wybranyklient) {
        this.wybranyklient = wybranyklient;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Kliencifk getKlientMaKonto() {
        return klientMaKonto;
    }

    public void setKlientMaKonto(Kliencifk klientMaKonto) {
        this.klientMaKonto = klientMaKonto;
    }

    public Kliencifk getKlientBezKonta() {
        return klientBezKonta;
    }

    public void setKlientBezKonta(Kliencifk klientBezKonta) {
        this.klientBezKonta = klientBezKonta;
    }

    public PlanKontCompleteView getPlanKontCompleteView() {
        return planKontCompleteView;
    }

    public void setPlanKontCompleteView(PlanKontCompleteView planKontCompleteView) {
        this.planKontCompleteView = planKontCompleteView;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public List<Kliencifk> getListawszystkichklientowFk() {
        return listawszystkichklientowFk;
    }

    public void setListawszystkichklientowFk(List<Kliencifk> listawszystkichklientowFk) {
        this.listawszystkichklientowFk = listawszystkichklientowFk;
    }

//</editor-fold>
}
