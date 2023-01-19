/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.PodatnikDAO;
import embeddable.Mce;
import embeddable.RyczaltPodatek;
import embeddable.WierszRyczalt;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named(value = "zestawienieRyczaltView1")
@ViewScoped
public class ZestawienieRyczaltView1 implements Serializable {

    //dane niezbedne do wyliczania pit
    private List<Double> wstyczen;
    private List<Double> wluty;
    private List<Double> wmarzec;
    private List<Double> wkwiecien;
    private List<Double> wmaj;
    private List<Double> wczerwiec;
    private List<Double> wlipiec;
    private List<Double> wsierpien;
    private List<Double> wwrzesien;
    private List<Double> wpazdziernik;
    private List<Double> wlistopad;
    private List<Double> wgrudzien;
    private List<Double> wIpolrocze;
    private List<Double> wIIpolrocze;
    private List<Double> wrok;
    private WierszRyczalt styczen;
    private WierszRyczalt luty;
    private WierszRyczalt marzec;
    private WierszRyczalt kwiecien;
    private WierszRyczalt maj;
    private WierszRyczalt czerwiec;
    private WierszRyczalt lipiec;
    private WierszRyczalt sierpien;
    private WierszRyczalt wrzesien;
    private WierszRyczalt pazdziernik;
    private WierszRyczalt listopad;
    private WierszRyczalt grudzien;
    private WierszRyczalt Ipolrocze;
    private WierszRyczalt IIpolrocze;
    private WierszRyczalt rok;
    private PodatnikUdzialy wybranyudzialowiec;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Dok> lista;
    private Podatnik taxman;
    private List<WierszRyczalt> zebranieMcy;
    private Podatnik podatnik;
    private String roks;
    

    public ZestawienieRyczaltView1() {
        zebranieMcy = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        taxman = podatnikDAO.findPodatnikByNIP("8511005008");
        if (podatnik != null) {
            styczen = new WierszRyczalt(1, roks, "01", "styczeń");
            luty = new WierszRyczalt(2, roks, "02", "luty");
            marzec = new WierszRyczalt(3, roks, "03", "marzec");
            kwiecien = new WierszRyczalt(4, roks, "04", "kwiecień");
            maj = new WierszRyczalt(5, roks, "05", "maj");
            czerwiec = new WierszRyczalt(6, roks, "06", "czerwiec");
            lipiec = new WierszRyczalt(7, roks, "07", "lipiec");
            sierpien = new WierszRyczalt(8, roks, "08", "sierpień");
            wrzesien = new WierszRyczalt(9, roks, "09", "wrzesień");
            pazdziernik = new WierszRyczalt(10, roks, "10", "październik");
            listopad = new WierszRyczalt(11, roks, "11", "listopad");
            grudzien = new WierszRyczalt(12, roks, "12", "grudzień");
            zebranieMcy = Collections.synchronizedList(new ArrayList<>());
            try {
                sumowaniemiesiecy();
                wstyczen = new ArrayList<>();
                wluty = new ArrayList<>();
                wmarzec = new ArrayList<>();
                wkwiecien = new ArrayList<>();
                wmaj = new ArrayList<>();
                wczerwiec = new ArrayList<>();
                wlipiec = new ArrayList<>();
                wsierpien = new ArrayList<>();
                wwrzesien = new ArrayList<>();
                wpazdziernik = new ArrayList<>();
                wlistopad = new ArrayList<>();
                wgrudzien = new ArrayList<>();
                wIpolrocze = new ArrayList<>();
                wIIpolrocze = new ArrayList<>();
                wrok = new ArrayList<>();
                naniesnaliste(wstyczen, zebranieMcy.get(0));
                naniesnaliste(wluty, zebranieMcy.get(1));
                naniesnaliste(wmarzec, zebranieMcy.get(2));
                naniesnaliste(wkwiecien, zebranieMcy.get(3));
                naniesnaliste(wmaj, zebranieMcy.get(4));
                naniesnaliste(wczerwiec, zebranieMcy.get(5));
                naniesnaliste(wlipiec, zebranieMcy.get(6));
                naniesnaliste(wsierpien, zebranieMcy.get(7));
                naniesnaliste(wwrzesien, zebranieMcy.get(8));
                naniesnaliste(wpazdziernik, zebranieMcy.get(9));
                naniesnaliste(wlistopad, zebranieMcy.get(10));
                naniesnaliste(wgrudzien, zebranieMcy.get(11));
                naniesnaliste(wIpolrocze, Ipolrocze);
                naniesnaliste(wIIpolrocze, IIpolrocze);
                naniesnaliste(wrok, rok);
            } catch (Exception ef) {}
        }
    }

    private void sumowaniemiesiecy() {
        try {
             try {
                lista = dokDAO.zwrocBiezacegoKlientaRokOdMcaDoMca(podatnik, roks, "12", "01");
                Collections.sort(lista, new Dokcomparator());
            } catch (Exception e) { 
                E.e(e); 
            }
        } catch (Exception e) {
            E.e(e);
        }
        if (lista != null) {
            styczen = new WierszRyczalt(1, roks, "01", "styczeń");
            luty = new WierszRyczalt(2, roks, "02", "luty");
            marzec = new WierszRyczalt(3, roks, "03", "marzec");
            kwiecien = new WierszRyczalt(4, roks, "04", "kwiecień");
            maj = new WierszRyczalt(5, roks, "05", "maj");
            czerwiec = new WierszRyczalt(6, roks, "06", "czerwiec");
            lipiec = new WierszRyczalt(7, roks, "07", "lipiec");
            sierpien = new WierszRyczalt(8, roks, "08", "sierpień");
            wrzesien = new WierszRyczalt(9, roks, "09", "wrzesień");
            pazdziernik = new WierszRyczalt(10, roks, "10", "październik");
            listopad = new WierszRyczalt(11, roks, "11", "listopad");
            grudzien = new WierszRyczalt(12, roks, "12", "grudzień");
            zebranieMcy = Collections.synchronizedList(new ArrayList<>());
            zebranieMcy.add(styczen);
            zebranieMcy.add(luty);
            zebranieMcy.add(marzec);
            zebranieMcy.add(kwiecien);
            zebranieMcy.add(maj);
            zebranieMcy.add(czerwiec);
            zebranieMcy.add(lipiec);
            zebranieMcy.add(sierpien);
            zebranieMcy.add(wrzesien);
            zebranieMcy.add(pazdziernik);
            zebranieMcy.add(listopad);
            zebranieMcy.add(grudzien);
            for (Dok dokument : lista) {
                try {
                    List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                    for (KwotaKolumna1 tmp : szczegol) {
                        Integer miesiac = Mce.getMiesiacToNumber().get(dokument.getPkpirM()) - 1;
                        WierszRyczalt miesiace = zebranieMcy.get(miesiac);
                        String nazwakolumny = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (nazwakolumny) {
                            case "17%":
                                temp = miesiace.getKolumna_17i0() + kwota;
                                miesiace.setKolumna_17i0(temp);
                                break;
                            case "15%":
                                temp = miesiace.getKolumna_15i0() + kwota;
                                miesiace.setKolumna_15i0(temp);
                                break;
                            case "14%":
                                temp = miesiace.getKolumna_14i0() + kwota;
                                miesiace.setKolumna_14i0(temp);
                                break;
                            case "12.5%":
                                temp = miesiace.getKolumna_12i5() + kwota;
                                miesiace.setKolumna_12i5(temp);
                                break;
                            case "12%":
                                temp = miesiace.getKolumna_12i0() + kwota;
                                miesiace.setKolumna_12i0(temp);
                                break;
                            case "10%":
                                temp = miesiace.getKolumna_10i0() + kwota;
                                miesiace.setKolumna_10i0(temp);
                                break;
                            case "8.5%":
                                temp = miesiace.getKolumna_8i5() + kwota;
                                miesiace.setKolumna_8i5(temp);
                                break;
                            case "5.5%":
                                temp = miesiace.getKolumna_5i5() + kwota;
                                miesiace.setKolumna_5i5(temp);
                                break;
                            case "3%":
                                temp = miesiace.getKolumna_3i0() + kwota;
                                miesiace.setKolumna_3i0(temp);
                                break;
                            case "2%":
                                temp = miesiace.getKolumna_2i0() + kwota;
                                miesiace.setKolumna_2i0(temp);
                                break;
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            Ipolrocze = new WierszRyczalt(13, roks, "13", "I półrocze");
            IIpolrocze = new WierszRyczalt(14, roks, "14", "II półrocze");
            rok = new WierszRyczalt(15, roks, "15", "rok");
            for (WierszRyczalt p : zebranieMcy) {
                if (p.getId() < 7) {
                    Ipolrocze.dodaj(p);
                } else {
                    IIpolrocze.dodaj(p);
                }
                rok.dodaj(p);
            }
        }
    }

    private void naniesnaliste(List<Double> wstyczen, WierszRyczalt get) {
        wstyczen.add(get.getKolumna_17i0());
        wstyczen.add(get.getKolumna_15i0());
        wstyczen.add(get.getKolumna_14i0());
        wstyczen.add(get.getKolumna_12i5());
        wstyczen.add(get.getKolumna_12i0());
        wstyczen.add(get.getKolumna_10i0());
        wstyczen.add(get.getKolumna_8i5());
        wstyczen.add(get.getKolumna_5i5());
        wstyczen.add(get.getKolumna_3i0());
        wstyczen.add(get.getKolumna_2i0());
        wstyczen.add(get.getRazem());
    }

    private void obliczprzychod() {
        List<RyczaltPodatek> podatkibiezace = Collections.synchronizedList(new ArrayList<>());
        String selekcja = "12";
        int miesiacint = Mce.getMiesiacToNumber().get(selekcja) - 1;
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 17%", 0.17, miesiacint, 0));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 15%", 0.15, miesiacint, 1));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 14%", 0.14, miesiacint, 2));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 12,5%", 0.125, miesiacint, 3));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 12%", 0.12, miesiacint, 4));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 10%", 0.10, miesiacint, 5));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 8,5%", 0.085, miesiacint, 6));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 5,5%", 0.055, miesiacint, 7));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 3%", 0.03, miesiacint, 8));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 2%", 0.02, miesiacint, 9));

    }

    private RyczaltPodatek pobranieprzychodu(String opis, double stawka, int miesiac, int pozycja) {
        BigDecimal suma = new BigDecimal(0);
        BigDecimal podatek = new BigDecimal(0);
        if (zebranieMcy != null && zebranieMcy.size() > 0) {
            switch (pozycja) {
                case 0:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_17i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 1:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_15i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 2:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_14i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 3:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_12i5()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 4:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_12i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 5:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_10i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 6:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_8i5()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 7:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_5i5()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 8:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_3i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 9:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getKolumna_2i0()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;
                case 10:
                    suma = suma.add(BigDecimal.valueOf(zebranieMcy.get(miesiac).getRazem()));
                    suma = suma.setScale(2, RoundingMode.HALF_EVEN);
                    break;

            }
        }
        RyczaltPodatek podtk = new RyczaltPodatek();
        podtk.setOpis(opis);
        podtk.setStawka(stawka);
        podtk.setPrzychod(suma.doubleValue());
        podtk.setUdzialprocentowy(0.0);
        podtk.setPodstawa(0.0);
        podtk.setZmniejszenie(0.0);
        podtk.setPodatek(0.0);
        return podtk;
    }

   
    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

     public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public List<Double> getWstyczen() {
        return wstyczen;
    }

    public void setWstyczen(List<Double> wstyczen) {
        this.wstyczen = wstyczen;
    }

    public List<Double> getWluty() {
        return wluty;
    }

    public void setWluty(List<Double> wluty) {
        this.wluty = wluty;
    }

    public List<Double> getWmarzec() {
        return wmarzec;
    }

    public void setWmarzec(List<Double> wmarzec) {
        this.wmarzec = wmarzec;
    }

    public List<Double> getWkwiecien() {
        return wkwiecien;
    }

    public void setWkwiecien(List<Double> wkwiecien) {
        this.wkwiecien = wkwiecien;
    }

    public List<Double> getWmaj() {
        return wmaj;
    }

    public void setWmaj(List<Double> wmaj) {
        this.wmaj = wmaj;
    }

    public List<Double> getWczerwiec() {
        return wczerwiec;
    }

    public void setWczerwiec(List<Double> wczerwiec) {
        this.wczerwiec = wczerwiec;
    }

    public List<Double> getWlipiec() {
        return wlipiec;
    }

    public void setWlipiec(List<Double> wlipiec) {
        this.wlipiec = wlipiec;
    }

    public List<Double> getWsierpien() {
        return wsierpien;
    }

    public void setWsierpien(List<Double> wsierpien) {
        this.wsierpien = wsierpien;
    }

    public List<Double> getWwrzesien() {
        return wwrzesien;
    }

    public void setWwrzesien(List<Double> wwrzesien) {
        this.wwrzesien = wwrzesien;
    }

    public List<Double> getWpazdziernik() {
        return wpazdziernik;
    }

    public void setWpazdziernik(List<Double> wpazdziernik) {
        this.wpazdziernik = wpazdziernik;
    }

    public List<Double> getWlistopad() {
        return wlistopad;
    }

    public void setWlistopad(List<Double> wlistopad) {
        this.wlistopad = wlistopad;
    }

    public List<Double> getWgrudzien() {
        return wgrudzien;
    }

    public void setWgrudzien(List<Double> wgrudzien) {
        this.wgrudzien = wgrudzien;
    }

    public List<Double> getwIpolrocze() {
        return wIpolrocze;
    }

    public void setwIpolrocze(List<Double> wIpolrocze) {
        this.wIpolrocze = wIpolrocze;
    }

    public List<Double> getwIIpolrocze() {
        return wIIpolrocze;
    }

    public void setwIIpolrocze(List<Double> wIIpolrocze) {
        this.wIIpolrocze = wIIpolrocze;
    }

    public List<Double> getWrok() {
        return wrok;
    }

    public void setWrok(List<Double> wrok) {
        this.wrok = wrok;
    }

    public WierszRyczalt getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(WierszRyczalt wrzesien) {
        this.wrzesien = wrzesien;
    }


    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }


    public PodatnikUdzialy getWybranyudzialowiec() {
        return wybranyudzialowiec;
    }

    public void setWybranyudzialowiec(PodatnikUdzialy wybranyudzialowiec) {
        this.wybranyudzialowiec = wybranyudzialowiec;
    }


}
