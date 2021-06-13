/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.SlownikiBean;
import comparator.Kliencifkcomparator;
import converter.KontoConv;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PodatnikDAO;
import dao.UkladBRDAO;
import entity.Klienci;
import entity.Podatnik;
import entityfk.Dokfk;
import entityfk.Kliencifk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
 import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
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
    @Inject
    private WpisView wpisView;
    @Inject
    private PlanKontCompleteView planKontCompleteView;
    @Inject
    private KontoConv kontoConv;
    private boolean makonto0niemakonta1;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    private boolean zapisz0edytuj1;
    private boolean listawszystkichklientowFkaktualna0nieaktualna1;

    public KliencifkView() {
         ////E.m(this);
        listawszystkichklientow = Collections.synchronizedList(new ArrayList<>());
        listawszystkichklientowFk = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() { //E.m(this);
        if (wpisView.isKsiegirachunkowe()) {
            listawszystkichklientow = klienciDAO.findAll();
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        }
    }

    public void dolaczanieKontrDoSlownikowych() {
        int wynik = 0;
        wynik = pobieraniekontaFK();
        if (wynik == 1) {
            pobieraniekontaFKWpisCD();
        }
    }
    
    public void pobieraniekontaFKWpis(boolean niedodawajkontapole, Dokfk selected) {
        if (niedodawajkontapole == false) {
            //Msg.msg("pobieraniekontaFKWpis");
            if (selected.getRodzajedok().getKategoriadokumentu() != 0 && selected.getRodzajedok().getKategoriadokumentu() != 5) {
                wybranyklient = selected.getKontr();
                if (wybranyklient!=null && !wybranyklient.getNpelna().equals("nowy klient")) {
                    int wynik = pobieraniekontaFK();
                    if (wynik == 1 && !wybranyklient.getNip().equals(wpisView.getPodatnikObiekt().getNip())) {
                        makonto0niemakonta1 = true;
                    } else {
                        makonto0niemakonta1 = false;
                    }
                }
            }
        }
    }

    public void resetujmakontoniemakonta() {
        makonto0niemakonta1 = false;
        PrimeFaces.current().ajax().update("formwpisdokument:wybranawaluta");
    }

    public void pobieraniekontaFKWpisCD() {
        //tworzenie nowego
        klientBezKonta = new Kliencifk();
        if (!wybranyklient.getNpelna().equals("dodaj klienta automatycznie") && !wybranyklient.getNpelna().equals("nowy klienta") && !wybranyklient.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
            klientBezKonta.setNazwa(wybranyklient.getNpelna());
            klientBezKonta.setNip(wybranyklient.getNip());
            klientBezKonta.setPodatniknazwa(wpisView.getPodatnikWpisu());
            klientBezKonta.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
            klientBezKonta.setNrkonta(pobierznastepnynumer());
            przyporzadkujdokonta();
            resetujmakontoniemakonta();
            planKontCompleteView.init();
            kontoConv.init();
        }
    }

    public int pobieraniekontaFK() {
        if (wybranyklient instanceof Klienci && !wybranyklient.getNpelna().equals("nowy klient") && !wybranyklient.getNpelna().equals("dodaj klienta automatycznie")) {
            try {
                klientMaKonto = pobierzklientafk(wybranyklient.getNip());
                //klientMaKonto = kliencifkDAO.znajdzkontofk(wybranyklient.getNip(), wpisView.getPodatnikObiekt().getNip());
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
        } else if (wybranyklient instanceof Klienci && wybranyklient.getNpelna().equals("nowy klient")) {
            klientBezKonta = new Kliencifk();
        }
        return -1;
    }
    
    private Kliencifk pobierzklientafk(String nip) {
        Kliencifk zwrot = null;
        for (Kliencifk p : listawszystkichklientowFk) {
            if (p.getNip().equals(nip)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    public void przyporzadkujdokonta() {
        if (klientBezKonta.getNip().isEmpty()) {
            Msg.msg("e", "Kontrahent nie posiada numeru NIP/Pesel. Nie można podpiąć kont");
            wybranyklient = new Klienci();
            klientMaKonto = new Kliencifk();
            klientBezKonta = new Kliencifk();
            return;
        }
        try {
            List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            kliencifkDAO.create(klientBezKonta);
            PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientBezKonta, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
            Msg.msg("Zaktualizowano konta słownikowe");
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
            int wynik = PlanKontFKBean.aktualizujslownikKontrahenciRemove(klientkontodousuniecia, kontoDAOfk, wpisView);
            if (wynik == 0) {
                kliencifkDAO.remove(klientkontodousuniecia);
                listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
                Msg.msg("Usunięto konta słownikowe dla klienta " + klientkontodousuniecia.getNazwa());
            } else {
                Msg.msg("e", "Istnieją zapisy na kontach tego kontrahenta, nie można usunąć go ze słownika");
            }
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
        SlownikiBean.aktualizujkontapoedycji(selected, 1, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDAOfk);
        selected = new Kliencifk();
        listawszystkichklientowFk = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        zapisz0edytuj1 = false;
        Msg.msg("Naniesiono zmiany");
    }

    @Inject
    private PodatnikDAO podatnikDAO;
    
    public void kliencifkzmiana() {
        List<Kliencifk> kliencifkAll = kliencifkDAO.findAll();
        List<Podatnik> podatnicyAll  = podatnikDAO.findAll();
        Map<String,Podatnik> nippodatnik = new HashMap<>();
        System.out.println("poczatek");
        for (Kliencifk k : kliencifkAll) {
            String nip = k.getPodatniknip();
            Podatnik podatnik = nippodatnik.get(nip);
            if (podatnik==null) {
                podatnik = podatnikDAO.findPodatnikByNIP(nip);
                nippodatnik.put(nip, podatnik);
            }
            if (podatnik!=null) {
                k.setPodatnik(podatnik);
                kliencifkDAO.edit(k);
            }
        }
        System.out.println("koniec");
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    public void setMakonto0niemakonta1(boolean makonto0niemakonta1) {
        this.makonto0niemakonta1 = makonto0niemakonta1;
    }

    public List<Klienci> getListawszystkichklientow() {
        return listawszystkichklientow;
    }

    public KontoConv getKontoConv() {
        return kontoConv;
    }

    public void setKontoConv(KontoConv kontoConv) {
        this.kontoConv = kontoConv;
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
