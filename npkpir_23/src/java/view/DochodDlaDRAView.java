/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.KsiegaBean;
import comparator.Podatnikcomparator;
import dao.DokDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
import data.Data;
import embeddable.Mce;
import embeddable.WierszDRA;
import embeddable.WierszPkpir;
import embeddable.WierszRyczalt;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import error.E;
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
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DochodDlaDRAView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    private String rok;
    private String mc;
    private List<WierszDRA> wiersze;
    private WierszDRA selected;

    @PostConstruct
    public void start() {
        rok = "2022";
    }
    
    
    public void init() {
        if (rok!=null&&mc!=null) {
            List<Podatnik> podatnicy = podatnikDAO.findPodatnikNieFK();
            Collections.sort(podatnicy, new Podatnikcomparator());
            double podatnikprocentudzial = 100.0;
            wiersze = new ArrayList<>();
            int i = 1;
            for (Podatnik p : podatnicy) {
                PodatnikOpodatkowanieD opodatkowanie = zwrocFormaOpodatkowania(p, rok, mc);
                if (opodatkowanie!=null) {
                    String formaopodatkowania = opodatkowanie.getFormaopodatkowania();
                    List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findUdzialyPodatnik(p);
                    if (udzialy != null && udzialy.size() > 0) {
                        for (PodatnikUdzialy u : udzialy) {
                            WierszDRA wiersz = new WierszDRA(i, p, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                            wiersz.setOpodatkowanie(formaopodatkowania);
                            wiersz.setImienazwisko(u.getNazwiskoimie());
                            wiersz.setUdzial(Double.parseDouble(u.getUdzial()));
                            if (formaopodatkowania.contains("ryczałt")) {
                                //oblicz przychod
                                double przychod = pobierzprzychod(p, rok, mc, wiersz);
                                if (podatnikprocentudzial != 100.0) {
                                    przychod = Z.z(przychod * podatnikprocentudzial / 100.0);
                                }
                                wiersz.setPrzychod(przychod);
                                Msg.msg("Obliczono przychód za mc");
                            } else {
                                //oblicz dochod
                                double dochod = pobierzdochod(p, rok, mc, wiersz);
                                if (podatnikprocentudzial != 100.0) {
                                    dochod = Z.z(dochod * podatnikprocentudzial / 100.0);
                                }
                                wiersz.setDochod(dochod);
                                Msg.msg("Obliczono dochód za mc");
                            }
                            wiersze.add(wiersz);
                        }
                    } else {
                        WierszDRA wiersz = new WierszDRA(i, p, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                        wiersz.setOpodatkowanie(formaopodatkowania);
                        wiersz.setImienazwisko(p.getNazwisko()+" "+p.getImie());
                        wiersz.setUdzial(100);
                        if (formaopodatkowania.contains("ryczałt")) {
                            //oblicz przychod
                            double przychod = pobierzprzychod(p, rok, mc, wiersz);
                            wiersz.setPrzychod(przychod);
                            Msg.msg("Obliczono przychód za mc");
                        } else {
                            //oblicz dochod
                            double dochod = pobierzdochod(p, rok, mc, wiersz);
                            wiersz.setDochod(dochod);
                            Msg.msg("Obliczono dochód za mc");
                        }
                        wiersze.add(wiersz);
                    }
                } else {
                    WierszDRA wiersz = new WierszDRA(i, p, rok, mc, Mce.getStringToNazwamiesiaca().get(mc));
                    wiersz.setOpodatkowanie("brak opodatkowania");
                    wiersz.setImienazwisko(p.getNazwisko()+" "+p.getImie());
                    wiersz.setUdzial(100);
                    wiersze.add(wiersz);
                    Msg.msg("e", "Brak opodatkowania");
                }
                i++;
            }
            Msg.msg("Pobrano dane");
        } else {
            Msg.msg("e","Nie wybrano okresu");
        }
    }

 

    public PodatnikOpodatkowanieD zwrocFormaOpodatkowania(Podatnik podatnik, String rok, String mc) {
        PodatnikOpodatkowanieD zwrot = null;
        boolean jedno = ilerodzajowopodatkowania(podatnik, rok);
        if (jedno) {
            zwrot = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(podatnik, rok);
        } else {
            List<PodatnikOpodatkowanieD> lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnik, rok);
            for (PodatnikOpodatkowanieD p : lista) {
                boolean jestmiedzy = Data.czyjestpomiedzy(p.getDatarozpoczecia(), p.getDatazakonczenia(), rok, mc);
                if (jestmiedzy) {
                    zwrot = p;
                    break;
                }
            }
        }
        return zwrot;
    }

    private boolean ilerodzajowopodatkowania(Podatnik podatnik, String rok) {
        boolean jedno = true;
        List lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnik, rok);
        if (lista != null && lista.size() > 1) {
            jedno = false;
        }
        return jedno;
    }

    private double pobierzdochod(Podatnik podatnik, String rok, String mc, WierszDRA wiersz) {
        double dochod = 0.0;
        try {
            List<Dok> lista = KsiegaBean.pobierzdokumentyRok(dokDAO, podatnik, Integer.parseInt(rok), mc, mc);
            if (lista!=null&&!lista.isEmpty()) {
                for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
                WierszPkpir wierszPkpir = new WierszPkpir(1, rok, mc, "dla DRA");
                for (Dok dokument : lista) {
                    try {
                        if (dokument.getUsunpozornie() == false) {
                            List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                            for (KwotaKolumna1 tmp : szczegol) {
                                String selekcja = dokument.getPkpirM();
                                String selekcja2 = tmp.getNazwakolumny();
                                if (selekcja2 == null) {
                                    error.E.s("");
                                }
                                Double kwota = tmp.getNetto();
                                Double temp = 0.0;
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = wierszPkpir.getKolumna7() + kwota;
                                        wierszPkpir.setKolumna7(temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = wierszPkpir.getKolumna8() + kwota;
                                        wierszPkpir.setKolumna8(temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = wierszPkpir.getKolumna10() + kwota;
                                        wierszPkpir.setKolumna10(temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = wierszPkpir.getKolumna11() + kwota;
                                        wierszPkpir.setKolumna11(temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = wierszPkpir.getKolumna12() + kwota;
                                        wierszPkpir.setKolumna12(temp);
                                        break;
                                    case "poz. koszty":
                                        temp = wierszPkpir.getKolumna13() + kwota;
                                        wierszPkpir.setKolumna13(temp);
                                        break;
                                    case "inwestycje":
                                        temp = wierszPkpir.getKolumna15() + kwota;
                                        wierszPkpir.setKolumna15(temp);
                                        break;
                                }
                            }
                            //pobierzPity();
                        } else {
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
                dochod = wierszPkpir.getRazemdochod();
            } else {
                wiersz.setBrakdokumentow(true);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return dochod;
    }

    private double pobierzprzychod(Podatnik podatnik, String rok, String mc, WierszDRA wiersz) {
        double przychod = 0.0;
        List<Dok> lista = KsiegaBean.pobierzdokumentyRok(dokDAO, podatnik, Integer.parseInt(rok), mc, mc);
        if (lista!=null&&!lista.isEmpty()) {
            for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                Dok tmpx = it.next();
                if (tmpx.getRodzajedok().isTylkojpk()) {
                    it.remove();
                }
            }
            WierszRyczalt wierszRyczalt = new WierszRyczalt(1, rok, mc, "dla DRA");
            for (Dok dokument : lista) {
                try {
                    List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                    for (KwotaKolumna1 tmp : szczegol) {
                        String nazwakolumny = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (nazwakolumny) {
                            case "17%":
                                temp = wierszRyczalt.getKolumna_17i0() + kwota;
                                wierszRyczalt.setKolumna_17i0(temp);
                                break;
                            case "15%":
                                temp = wierszRyczalt.getKolumna_15i0() + kwota;
                                wierszRyczalt.setKolumna_15i0(temp);
                                break;
                            case "14%":
                                temp = wierszRyczalt.getKolumna_14i0() + kwota;
                                wierszRyczalt.setKolumna_14i0(temp);
                                break;
                            case "12.5%":
                                temp = wierszRyczalt.getKolumna_12i5() + kwota;
                                wierszRyczalt.setKolumna_12i5(temp);
                                break;
                            case "12%":
                                temp = wierszRyczalt.getKolumna_12i0() + kwota;
                                wierszRyczalt.setKolumna_12i0(temp);
                                break;
                            case "10%":
                                temp = wierszRyczalt.getKolumna_10i0() + kwota;
                                wierszRyczalt.setKolumna_10i0(temp);
                                break;
                            case "8.5%":
                                temp = wierszRyczalt.getKolumna_8i5() + kwota;
                                wierszRyczalt.setKolumna_8i5(temp);
                                break;
                            case "5.5%":
                                temp = wierszRyczalt.getKolumna_5i5() + kwota;
                                wierszRyczalt.setKolumna_5i5(temp);
                                break;
                            case "3%":
                                temp = wierszRyczalt.getKolumna_3i0() + kwota;
                                wierszRyczalt.setKolumna_3i0(temp);
                                break;
                            case "2%":
                                temp = wierszRyczalt.getKolumna_2i0() + kwota;
                                wierszRyczalt.setKolumna_2i0(temp);
                                break;
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                przychod = wierszRyczalt.getRazem();
            }
        } else {
            wiersz.setBrakdokumentow(true);
        }
        return przychod;
    }

    public List<WierszDRA> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<WierszDRA> wiersze) {
        this.wiersze = wiersze;
    }

    public WierszDRA getSelected() {
        return selected;
    }

    public void setSelected(WierszDRA selected) {
        this.selected = selected;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    
    
}
