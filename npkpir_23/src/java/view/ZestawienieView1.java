/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.CechazapisuDAOfk;
import dao.DokDAO;
import dao.PodatnikDAO;
import embeddable.Mce;
import embeddable.WierszPkpir;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entityfk.Cechazapisu;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
@Named(value = "zestawienieView1")
@ViewScoped
public class ZestawienieView1 implements Serializable {

    private static final long serialVersionUID = 1L;
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
    private WierszPkpir styczen;
    private WierszPkpir luty;
    private WierszPkpir marzec;
    private WierszPkpir kwiecien;
    private WierszPkpir maj;
    private WierszPkpir czerwiec;
    private WierszPkpir lipiec;
    private WierszPkpir sierpien;
    private WierszPkpir wrzesien;
    private WierszPkpir pazdziernik;
    private WierszPkpir listopad;
    private WierszPkpir grudzien;
    private WierszPkpir Ipolrocze;
    private WierszPkpir IIpolrocze;
    private WierszPkpir rok;
    private List<WierszPkpir> zebranieMcy;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Dok> lista;
    private String komunikatblad;
    private List<Cechazapisu> pobranecechypodatnik;
    private Cechazapisu wybranacechadok;
    private Podatnik taxman;
    private Podatnik podatnik;
    private String roks;

    private int flaga = 0;


    public ZestawienieView1() {

    }

    @PostConstruct
    public void init() { //E.m(this);
        styczen = new WierszPkpir(1, roks, "01", "styczeń");
        luty = new WierszPkpir(2, roks, "02", "luty");
        marzec = new WierszPkpir(3, roks, "03", "marzec");
        kwiecien = new WierszPkpir(4, roks, "04", "kwiecień");
        maj = new WierszPkpir(5, roks, "05", "maj");
        czerwiec = new WierszPkpir(6, roks, "06", "czerwiec");
        lipiec = new WierszPkpir(7, roks, "07", "lipiec");
        sierpien = new WierszPkpir(8, roks, "08", "sierpień");
        wrzesien = new WierszPkpir(9, roks, "09", "wrzesień");
        pazdziernik = new WierszPkpir(10, roks, "10", "październik");
        listopad = new WierszPkpir(11, roks, "11", "listopad");
        grudzien = new WierszPkpir(12, roks, "12", "grudzień");
        Ipolrocze = new WierszPkpir(13, roks, "13", "I półrocze");
        IIpolrocze = new WierszPkpir(14, roks, "14", "II półrocze");
        rok = new WierszPkpir(15, roks, "15", "rok");
        taxman = podatnikDAO.findPodatnikByNIP("8511005008");
        zebranieMcy = Collections.synchronizedList(new ArrayList<>());
        sumowaniemiesiecy(podatnik, roks);
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
    }
    
    private void sumowaniemiesiecy(Podatnik podatnik, String rokint) {
        try {
            styczen = new WierszPkpir(1, rokint, "01", "styczeń");
            luty = new WierszPkpir(2, rokint, "02", "luty");
            marzec = new WierszPkpir(3, rokint, "03", "marzec");
            kwiecien = new WierszPkpir(4, rokint, "04", "kwiecień");
            maj = new WierszPkpir(5, rokint, "05", "maj");
            czerwiec = new WierszPkpir(6, rokint, "06", "czerwiec");
            lipiec = new WierszPkpir(7, rokint, "07", "lipiec");
            sierpien = new WierszPkpir(8, rokint, "08", "sierpień");
            wrzesien = new WierszPkpir(9, rokint, "09", "wrzesień");
            pazdziernik = new WierszPkpir(10, rokint, "10", "październik");
            listopad = new WierszPkpir(11, rokint, "11", "listopad");
            grudzien = new WierszPkpir(12, rokint, "12", "grudzień");
            Ipolrocze = new WierszPkpir(13, rokint, "13", "I półrocze");  
            IIpolrocze = new WierszPkpir(14, rokint, "14", "II półrocze");
            rok = new WierszPkpir(15, rokint, "15", "rok");
            zebranieMcy = Collections.synchronizedList(new ArrayList<>());
            try {
                lista = dokDAO.zwrocBiezacegoKlientaRokOdMcaDoMca(podatnik, rok.toString(), "12", "01");
                Collections.sort(lista, new Dokcomparator());
            } catch (Exception e) { 
                E.e(e); 
            }
                if (wybranacechadok != null) {
                    for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                        Dok p = it.next();
                        if (p.getListaCech() != null && !p.getListaCech().contains(wybranacechadok.getNazwacechy())) {
                            it.remove();
                        }
                    }
                }
                for (Iterator<Dok> it = lista.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
            if (lista != null) {
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
                                int mcint = Mce.getMiesiacToNumber().get(selekcja) - 1;
                                WierszPkpir listabiezaca = zebranieMcy.get(mcint);
                                switch (selekcja2) {
                                    case "przych. sprz":
                                        temp = listabiezaca.getKolumna7() + kwota;
                                        listabiezaca.setKolumna7(temp);
                                        break;
                                    case "pozost. przych.":
                                        temp = listabiezaca.getKolumna8() + kwota;
                                        listabiezaca.setKolumna8(temp);
                                        break;
                                    case "zakup tow. i mat.":
                                        temp = listabiezaca.getKolumna10() + kwota;
                                        listabiezaca.setKolumna10(temp);
                                        break;
                                    case "koszty ub.zak.":
                                        temp = listabiezaca.getKolumna11() + kwota;
                                        listabiezaca.setKolumna11(temp);
                                        break;
                                    case "wynagrodzenia":
                                        temp = listabiezaca.getKolumna12() + kwota;
                                        listabiezaca.setKolumna12(temp);
                                        break;
                                    case "poz. koszty":
                                        temp = listabiezaca.getKolumna13() + kwota;
                                        listabiezaca.setKolumna13(temp);
                                        break;
                                    case "inwestycje":
                                        temp = listabiezaca.getKolumna15() + kwota;
                                        listabiezaca.setKolumna15(temp);
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
                for (WierszPkpir p : zebranieMcy) {
                    if (p.getId() < 7) {
                        Ipolrocze.dodaj(p);
                    } else {
                        IIpolrocze.dodaj(p);
                    }
                    rok.dodaj(p);
                }
            }
    }

    private void naniesnaliste(List<Double> wstyczen, WierszPkpir get) {
        wstyczen.add(get.getKolumna7());
        wstyczen.add(get.getKolumna8());
        wstyczen.add(get.getKolumna10());
        wstyczen.add(get.getKolumna11());
        wstyczen.add(get.getKolumna12());
        wstyczen.add(get.getKolumna13());
        wstyczen.add(get.getKolumna15());
        wstyczen.add(get.getRazemprzychody());
        wstyczen.add(get.getRazemkoszty());
        wstyczen.add(get.getRazemdochod());
    }



    private BigDecimal obliczprzychod(Pitpoz pitpoz) {
        try {
            BigDecimal suma = new BigDecimal(0);
            String selekcja = "12";
            int granica = Mce.getMiesiacToNumber().get(selekcja);
            for (WierszPkpir p : zebranieMcy) {
                if (p.getId() < granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemprzychody()));
                } else if (p.getId() == granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemprzychody()));
                    pitpoz.setPrzychodymc(Z.z(p.getRazemprzychody()));
                } else {
                    break;
                }
            }
            return suma;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie było w tym roku żadnych przychodów");
            return new BigDecimal(BigInteger.ZERO);
        }
    }
    

    private BigDecimal obliczkoszt(Pitpoz pitpoz) {
        try {
            BigDecimal suma = new BigDecimal(0);
            String selekcja = "12";
            int granica = Mce.getMiesiacToNumber().get(selekcja);
            for (WierszPkpir p : zebranieMcy) {
                if (p.getId() < granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemkoszty()));
                } else if (p.getId() == granica) {
                    suma = suma.add(BigDecimal.valueOf(p.getRazemkoszty()));
                    pitpoz.setKosztymc(Z.z(p.getRazemkoszty()));
                } else {
                    break;
                }
            }
            return suma;
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie było w tym roku żadnych kosztów");
            return new BigDecimal(BigInteger.ZERO);
        }
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


    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
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

    public WierszPkpir getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(WierszPkpir wrzesien) {
        this.wrzesien = wrzesien;
    }

    public String getKomunikatblad() {
        return komunikatblad;
    }

    public void setKomunikatblad(String komunikatblad) {
        this.komunikatblad = komunikatblad;
    }

    public CechazapisuDAOfk getCechazapisuDAOfk() {
        return cechazapisuDAOfk;
    }

    public void setCechazapisuDAOfk(CechazapisuDAOfk cechazapisuDAOfk) {
        this.cechazapisuDAOfk = cechazapisuDAOfk;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public Cechazapisu getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(Cechazapisu wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public WierszPkpir getStyczen() {
        return styczen;
    }

    public void setStyczen(WierszPkpir styczen) {
        this.styczen = styczen;
    }

    public WierszPkpir getLuty() {
        return luty;
    }

    public void setLuty(WierszPkpir luty) {
        this.luty = luty;
    }

    public WierszPkpir getMarzec() {
        return marzec;
    }

    public void setMarzec(WierszPkpir marzec) {
        this.marzec = marzec;
    }

    public WierszPkpir getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(WierszPkpir kwiecien) {
        this.kwiecien = kwiecien;
    }

    public WierszPkpir getMaj() {
        return maj;
    }

    public void setMaj(WierszPkpir maj) {
        this.maj = maj;
    }

    public WierszPkpir getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(WierszPkpir czerwiec) {
        this.czerwiec = czerwiec;
    }

    public WierszPkpir getLipiec() {
        return lipiec;
    }

    public void setLipiec(WierszPkpir lipiec) {
        this.lipiec = lipiec;
    }

    public WierszPkpir getSierpien() {
        return sierpien;
    }

    public void setSierpien(WierszPkpir sierpien) {
        this.sierpien = sierpien;
    }

    public WierszPkpir getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(WierszPkpir pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public WierszPkpir getListopad() {
        return listopad;
    }

    public void setListopad(WierszPkpir listopad) {
        this.listopad = listopad;
    }

    public WierszPkpir getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(WierszPkpir grudzien) {
        this.grudzien = grudzien;
    }

    public WierszPkpir getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(WierszPkpir Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public WierszPkpir getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(WierszPkpir IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public WierszPkpir getRok() {
        return rok;
    }

    public void setRok(WierszPkpir rok) {
        this.rok = rok;
    }

    

}
