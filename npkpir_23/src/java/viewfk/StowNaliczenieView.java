/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokumentFKBean;
import comparator.Kontocomparator;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KontoDAOfk;
import dao.MiejscePrzychodowDAO;
import dao.RodzajedokDAO;
import dao.SkladkaCzlonekDAO;
import dao.StowNaliczenieDAO;
import dao.TabelanbpDAO;
import data.Data;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.MiejsceSuper;
import entityfk.SkladkaCzlonek;
import entityfk.StowNaliczenie;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class StowNaliczenieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<StowNaliczenie> lista;
    private List<StowNaliczenie> listaselected;
    private List<Konto> konta;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private SkladkaCzlonekDAO skladkaCzlonekDAO;
    @Inject
    private StowNaliczenieDAO stowNaliczenieDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private WpisView wpisView;
    private String wybranakategoria;
    private Konto kontoWn;
    private Konto kontoMa;
    private double kwotadlawszystkich;


    public StowNaliczenieView() {
        this.lista = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() { //E.m(this);
        try {
            if (wpisView.getFormaprawna().equals("STOWARZYSZENIE")) {
                ////E.m(this);
                //przychody
                konta = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 7);
                Konto kontoprzychodypo = kontoDAOfk.findKonto("251", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                if (kontoprzychodypo != null) {
                    konta.add(kontoprzychodypo);
                }
                Collections.sort(konta, new Kontocomparator());
                List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
                for (MiejscePrzychodow p : czlonkowiestowarzyszenia) {
                    if (Data.czyjestpomiedzy(p.getPoczatek(), p.getKoniec(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu())) {
                        lista.add(new StowNaliczenie(p));
                    }
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void pobierz() {
        try {
            if (wybranakategoria.equals("wybierz")) {
                Msg.msg("e", "Nie wybrano kategorii, nie można naliczyć kwot");
            } else {
                lista = stowNaliczenieDAO.findByMcKategoria(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wybranakategoria);
                lista.addAll(dodajpuste());
                Msg.dP();
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void kopiuj() {
        try {
            if (wybranakategoria.equals("wybierz")) {
                Msg.msg("e", "Nie wybrano kategorii, nie można naliczyć kwot");
            } else {
                String mcpop = wpisView.getMiesiacUprzedni();
                if (!mcpop.equals("12")) {
                    List<StowNaliczenie> naliczenia =  stowNaliczenieDAO.findByMcKategoria(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mcpop, wybranakategoria);
                    for (StowNaliczenie p : naliczenia) {
                       p.setId(null);
                       p.setMc(wpisView.getMiesiacWpisu());
                       p.setDataksiegowania(null);
                    }
                    lista = Collections.synchronizedList(new ArrayList<>());
                    lista.addAll(naliczenia);
                    Msg.dP();
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void oblicz() {
        if (wybranakategoria.equals("wybierz")) {
            Msg.msg("e", "Nie wybrano kategorii, nie można naliczyć kwot");
        } else {
            switch (wybranakategoria) {
                case "składka":
                    obliczprzychod();
                    break;
                default:
                    obliczkoszty();
                    break;
            }
            Msg.dP();
        }
    }
    
    private List<StowNaliczenie> dodajpuste() {
        List<StowNaliczenie> nowenaliczenia = Collections.synchronizedList(new ArrayList<>());
        List<StowNaliczenie> naliczenia = generujnaliczanie();
        if (naliczenia != null) {
            for (StowNaliczenie p : naliczenia) {
                boolean dodaj = true;
                for (StowNaliczenie r : lista) {
                    if (p.getMiejsce().equals(r.getMiejsce())) {
                        dodaj = false;
                        break;
                    }
                }
                if (dodaj) {
                    nowenaliczenia.add(p);
                }
            }
        }
        return nowenaliczenia;
    }
    
    private List<StowNaliczenie> generujnaliczanie() {
        List<StowNaliczenie> naliczenia = Collections.synchronizedList(new ArrayList<>());
        List<MiejscePrzychodow> czlonkowiestowarzyszenia = miejscePrzychodowDAO.findCzlonkowieStowarzyszenia(wpisView.getPodatnikObiekt());
        for (MiejscePrzychodow p : czlonkowiestowarzyszenia) {
            if (Data.czyjestpomiedzy(p.getPoczatek(), p.getKoniec(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu())) {
                naliczenia.add(new StowNaliczenie(p));
            }
        }
        return naliczenia;
    }
    
    public void obliczprzychod() {
        lista = Collections.synchronizedList(new ArrayList<>());
        lista.addAll(generujnaliczanie());
        generujskladki();
    }
    
    public void obliczkoszty() {
        try {
            if (listaselected != null && listaselected.size() > 0) {
                for (StowNaliczenie p : lista) {
                    p.setKwota(0.0);
                }
                for (StowNaliczenie p : listaselected) {
                    p.setKwota(kwotadlawszystkich);
                }
                Msg.dP();
            } else {
                Msg.msg("e", "Nie wybrano członków stowarzyszenia do naliczenia");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }

    private void generujskladki() {
        try {
            List<SkladkaCzlonek> listaskladki = skladkaCzlonekDAO.findPodatnikRok(wpisView);
            if (listaskladki.isEmpty()) {
                Msg.msg("w","Nie zdefiniowano składek, pobieram kwotę globalną");
            }
            for (StowNaliczenie p : lista) {
                if (nalicz(p)) {
                    if (kwotadlawszystkich != 0.0) {
                        p.setKwota(kwotadlawszystkich);
                    } else {
                        if (listaskladki.isEmpty()) {
                            p.setKwota(kwotadlawszystkich);
                        } else {
                            double kwota = pobierzkwote(listaskladki,p);
                            p.setKwota(kwota);
                        }
                    }
                }
            }
            Msg.dP();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd, sprawdź czy zdefiniowano składki");
           
        }
    }
    
    private boolean nalicz(StowNaliczenie p) {
        String dataOd = p.getMiejsce().getPoczatek();
        String dataDo = p.getMiejsce().getKoniec();
        boolean jestpo = Data.czyjestpo(dataOd, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        boolean jestprzed = Data.czydatajestprzed(dataDo, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        return jestpo && jestprzed;
    }
    
     
    private void usupelnijdane(boolean przychod0koszt1) {
        for (StowNaliczenie p : lista) {
            p.setKategoria(wybranakategoria);
            p.setMc(wpisView.getMiesiacWpisu());
            p.setRok(wpisView.getRokWpisuSt());
            p.setPrzych0koszt1(przychod0koszt1);
        }
    }
    
    private double pobierzkwote(List<SkladkaCzlonek> listaskladki, StowNaliczenie p) {
        SkladkaCzlonek skladkaCzlonek = pobierzpozycje(listaskladki, p.getMiejsce());
        return skladkaCzlonek.getSkladka().getKwota();
    }
    
    private SkladkaCzlonek pobierzpozycje(List<SkladkaCzlonek> listaskladki, MiejsceSuper czlonek) {
        SkladkaCzlonek zwrot = null;
        for (SkladkaCzlonek p : listaskladki) {
            if (p.getCzlonek().equals(czlonek)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
    public void zachowaj() {
        try {
            stowNaliczenieDAO.usunnaliczeniemc(wpisView,wybranakategoria);
        } catch (Exception e) {
        }
        try {
            switch (wybranakategoria) {
                case "składka":
                    usupelnijdane(false);
                    break;
                default:
                    usupelnijdane(true);
                    break;
            }
            for(Iterator<StowNaliczenie> it = lista.iterator();it.hasNext();) {
                StowNaliczenie t = it.next();
                t.setDataksiegowania(new Date());
                stowNaliczenieDAO.edit(t);
            }
            Msg.msg("Zachowano listę");
        } catch (Exception e) {
        }
    }
    
    
    
    
    public void generujPK() {
        if (kontoWn == null || kontoMa == null) {
            Msg.msg("e", "Nie wybrano konta, nie można wygenerować dokumentu");
        } else {
            try {
                Dokfk dokfk = null;
                switch (wybranakategoria) {
                    case "składka":
                        dokfk = DokumentFKBean.generujdokumentSkladki(wpisView, klienciDAO, "PK", "naliczenie - "+wybranakategoria, rodzajedokDAO, tabelanbpDAO, kontoWn, kontoMa, kontoDAOfk, lista, dokDAOfk);
                    default:
                        dokfk = DokumentFKBean.generujdokumentSkladki(wpisView, klienciDAO, "PK", "przypis - "+wybranakategoria, rodzajedokDAO, tabelanbpDAO, kontoWn, kontoMa, kontoDAOfk, lista, dokDAOfk);
                        break;
                }
                dokDAOfk.create(dokfk);
                Msg.msg("Zaksięgowano dokument PK");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu PK");
            }
        }
    }

    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public List<StowNaliczenie> getLista() {
        return lista;
    }
    
    public void setLista(List<StowNaliczenie> lista) {
        this.lista = lista;
    }

    public List<StowNaliczenie> getListaselected() {
        return listaselected;
    }

    public void setListaselected(List<StowNaliczenie> listaselected) {
        this.listaselected = listaselected;
    }

    public List<Konto> getKonta() {
        return konta;
    }

    public void setKonta(List<Konto> konta) {
        this.konta = konta;
    }

    public Konto getKontoWn() {
        return kontoWn;
    }

    public void setKontoWn(Konto kontoWn) {
        this.kontoWn = kontoWn;
    }

    public Konto getKontoMa() {
        return kontoMa;
    }

    public void setKontoMa(Konto kontoMa) {
        this.kontoMa = kontoMa;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    public double getKwotadlawszystkich() {
        return kwotadlawszystkich;
    }

    public void setKwotadlawszystkich(double kwotadlawszystkich) {
        this.kwotadlawszystkich = kwotadlawszystkich;
    }

    public String getWybranakategoria() {
        return wybranakategoria;
    }
    
    public void setWybranakategoria(String wybranakategoria) {
        this.wybranakategoria = wybranakategoria;
    }
    
    
//</editor-fold>

    
   
    
    
    
}
