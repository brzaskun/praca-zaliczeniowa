/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.BOFKBean;
import dao.STRDAO;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import data.Data;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import entity.SrodekTrw;
import entityfk.Konto;
import entityfk.KontoSrtrw;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SrodkiKontaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    protected STRDAO sTRDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private List<KontoSrtrw>  lista;
    private boolean brakbo;

    @PostConstruct
    private void init() { //E.m(this);
        List<SrodekTrw> srodkizBazy = pobierzsrodkizbazy();
        List<Konto> kontaklienta = pobierzkonta();
        List<SaldoKonto> listaSaldoKonto = Collections.synchronizedList(new ArrayList<>());
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (zapisyBO.isEmpty()) {
            brakbo = true;
        }
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        listaSaldoKonto = przygotowanalistasald(kontaklienta, zapisyBO, zapisyObrotyRozp, null);
        lista = stworzliste(kontaklienta,listaSaldoKonto, srodkizBazy);
        error.E.s("");
    }
    
    private List<SrodekTrw> pobierzsrodkizbazy() {
        List<SrodekTrw> posiadane = Collections.synchronizedList(new ArrayList<>());
        List<SrodekTrw> srodkizBazy = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
        for (SrodekTrw srodek : srodkizBazy) {
            if (srodek.getTyp() != null && !srodek.getTyp().equals("wnip") && !srodek.getTyp().equals("wyposazenie")) {
                if (srodek.getDatasprzedazy() == null || srodek.getDatasprzedazy().equals("")) {
                    String[] okres = Data.nastepnyOkres(wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
                    if (Data.czyjestprzed(srodek.getDataprzek(), okres[0], okres[1])) {
                        posiadane.add(srodek);
                    }
                }
            }
        }
        return posiadane;
    }

    private List<SaldoKonto> przygotowanalistasald(List<Konto> kontaklienta, List<StronaWiersza> zapisyBO, List<StronaWiersza> zapisyObrotyRozp, String rodzajkonta) {
        List<StronaWiersza> zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokMcodMcdo(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(),"01","12");
        Map<String, SaldoKonto> przygotowanalista = new ConcurrentHashMap<>();
        List<StronaWiersza> wierszenieuzupelnione = Collections.synchronizedList(new ArrayList<>());
        kontaklienta.stream().forEach((p) -> {
            SaldoKonto saldoKonto = new SaldoKonto();
            saldoKonto.setKonto(p);
            przygotowanalista.put(p.getPelnynumer(), saldoKonto);
        });
        naniesBOnaKonto(przygotowanalista, zapisyBO);
        naniesZapisyNaKonto(przygotowanalista, zapisyObrotyRozp, wierszenieuzupelnione, false);
        naniesZapisyNaKonto(przygotowanalista, zapisyRok, wierszenieuzupelnione, true);
        przygotowanalista.values().stream().map((s) -> {
            s.sumujBOZapisy();
            return s;
        }).forEachOrdered(SaldoKonto::wyliczSaldo);
//        for (int i = 1; i < przygotowanalista.size()+1; i++) {
//            przygotowanalista.get(i-1).setId(i);
//        }
        List<SaldoKonto> listaSaldoKonto = new ArrayList<>(przygotowanalista.values());
        for (Iterator<SaldoKonto> it = listaSaldoKonto.iterator(); it.hasNext();) {
            SaldoKonto skn = it.next();
            if (skn.getSaldoMa() == 0.0 && skn.getSaldoWn() == 0.0 && skn.getObrotyBoWn() == 0.0 && skn.getObrotyBoMa() == 0.0) {
                it.remove();
            }
        }
        return listaSaldoKonto;
    }

    private List<Konto> pobierzkonta() {
        List<Konto> lista = kontoDAOfk.findKontaGrupaAnalityka(wpisView,"0%");
        for (Iterator<Konto> it= lista.iterator(); it.hasNext();) {
            Konto k = it.next();
            if (k.isMapotomkow() || k.getPelnynumer().startsWith("016") || k.getPelnynumer().startsWith("03") || k.getPelnynumer().startsWith("075")) {
                it.remove();
            }
        }
        return lista;
    }
    
    private void naniesBOnaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyBO) {
        zapisyBO.stream().forEach((r) -> {
            SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (r.getWnma().equals("Wn")) {
                    p.setBoWn(Z.z(p.getBoWn() + r.getKwotaPLN()));
                } else {
                    p.setBoMa(Z.z(p.getBoMa() + r.getKwotaPLN()));
                }
                p.getZapisy().add(r);
            }
        });
    }

    private void naniesZapisyNaKonto(Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> zapisyRok, List<StronaWiersza> wierszenieuzupelnione, boolean obroty0zapisy1) {
        int granicamca = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
        zapisyRok.stream().forEach(r -> {
            if (obroty0zapisy1 == true && !r.getDokfk().getSeriadokfk().equals("BO")) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione);
                }
            } else if (obroty0zapisy1 == false && r.getDokfk().getSeriadokfk().equals("BO")) {
                if (Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac()) <= granicamca) {
                    nanieskonkretnyzapis(r, przygotowanalista, wierszenieuzupelnione);
                }
            }
        });
    }

    private void nanieskonkretnyzapis(StronaWiersza r, Map<String, SaldoKonto> przygotowanalista, List<StronaWiersza> wierszenieuzupelnione) {
        try {
            String mc = wpisView.getMiesiacWpisu();
            SaldoKonto p = przygotowanalista.get(r.getKonto().getPelnynumer());
            if (p != null) {
                if (r.getKonto().equals(p.getKonto())) {
                    if (r.getWnma().equals("Wn")) {
                        if (r.getDokfk().getMiesiac().equals(mc)) {
                            p.setObrotyWnMc(Z.z(p.getObrotyWnMc() + r.getKwotaPLN()));
                        }
                        p.setObrotyWn(Z.z(p.getObrotyWn() + r.getKwotaPLN()));
                    } else {
                        if (r.getDokfk().getMiesiac().equals(mc)) {
                            p.setObrotyMaMc(Z.z(p.getObrotyMaMc() + r.getKwotaPLN()));
                        }
                        p.setObrotyMa(Z.z(p.getObrotyMa() + r.getKwotaPLN()));
                    }
                    p.getZapisy().add(r);
                }
            }
        } catch (Exception e) {
            if (r.getKonto() == null) {
            }
            if (r.getWiersz().getDokfk().getMiesiac() == null) {
            }
            E.e(e);
            if (wierszenieuzupelnione.size() > 0) {
                boolean jest = false;
                for (StronaWiersza t : wierszenieuzupelnione) {
                    if (t.getDokfkS().equals(r.getDokfkS())) {
                        jest = true;
                    }
                }
                if (jest == false) {
                    wierszenieuzupelnione.add(r);
                }
            } else {
                wierszenieuzupelnione.add(r);
            }
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    private List<KontoSrtrw> stworzliste(List<Konto> kontaklienta, List<SaldoKonto> listaSaldoKonto, List<SrodekTrw> posiadane) {
        List<KontoSrtrw> lista = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : kontaklienta) {
            KontoSrtrw zwrot = new KontoSrtrw(p);
            SaldoKonto sk = pobierzsaldokonto(listaSaldoKonto, p);
            if (sk!=null) {
                zwrot.setSaldoWnstr(sk.getSaldoWn());
                zwrot.setSaldoMastr(sk.getSaldoMa());
                sumujsrodki(p,sk,posiadane, zwrot);
            }
            if (niejestzerowe(zwrot)) {
                lista.add(zwrot);
            }
        }
        return lista;
    }

    private SaldoKonto pobierzsaldokonto(List<SaldoKonto> listaSaldoKonto, Konto konto) {
        SaldoKonto zwrot = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().equals(konto)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private void sumujsrodki(Konto p, SaldoKonto sk, List<SrodekTrw> posiadane, KontoSrtrw zwrot) {
        double koszzakupu = 0.0;
        double umorzenia = 0.0;
        List<SrodekTrw> wybrane = new ArrayList<>();
        for (SrodekTrw t : posiadane) {
            try {
                boolean dodac = false;
                if (t.getKontonetto().equals(p)) {
                    koszzakupu += pobierzentto(t);
                    dodac = true;
                }
                if (t.getKontoumorzenie().equals(p)) {
                    umorzenia += pobierzumorzenie(t);
                    dodac = true;
                }
                if (dodac) {
                    wybrane.add(t);
                }
            } catch (Exception e){
                E.e(e);
                Msg.msg("e", "Nie podpięto konta pod środek "+t.getNazwa(), "formtablicaporownawcza:wiad");
            }
        }
        zwrot.setKosztnabycia(koszzakupu);
        zwrot.setOdpisyamo(umorzenia);
        zwrot.setSrodkitrwale(wybrane);
    }

    private double pobierzentto(SrodekTrw t) {
        double zwrot = t.getNetto();
        return zwrot;
    }

    private double pobierzumorzenie(SrodekTrw t) {
        double zwrot = Z.z(t.getNetto()-t.getStrNettoPlan());
        return zwrot;
    }
    
    private boolean niejestzerowe(KontoSrtrw p) {
        boolean zwrot = false;
        if (p.getSaldoWnstr()!=0.0) {
            zwrot=true;
        } else if  (p.getSaldoMastr()!=0.0) {
            zwrot=true;
        } else if (p.getKosztnabycia()!=0.0) {
            zwrot=true;
        } else if (p.getOdpisyamo()!=0.0) {
            zwrot=true;
        } 
        return zwrot;
    }

    public List<KontoSrtrw> getLista() {
        return lista;
    }

    public void setLista(List<KontoSrtrw> lista) {
        this.lista = lista;
    }

    public boolean isBrakbo() {
        return brakbo;
    }

    public void setBrakbo(boolean brakbo) {
        this.brakbo = brakbo;
    }

    

    

    
    
    

}
