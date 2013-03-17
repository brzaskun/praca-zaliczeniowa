/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.PitDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.ZobowiazanieDAO;
import embeddable.Kolmn;
import embeddable.KwotaKolumna;
import embeddable.Straty;
import embeddable.Udzialy;
import entity.Dok;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Podstawki;
import entity.Zobowiazanie;
import entity.Zusstawki;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "ZestawienieRyczaltView")
@RequestScoped
public class ZestawienieRyczaltView implements Serializable {

    @Inject
    private DokDAO dokDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    //bieżący pit
    private Pitpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Pitpoz narPitpoz;
    //lista pitow
    private List<Pitpoz> listapit;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    List<Double> styczen;
    List<Double> luty;
    List<Double> marzec;
    List<Double> kwiecien;
    List<Double> maj;
    List<Double> czerwiec;
    List<Double> lipiec;
    List<Double> sierpien;
    List<Double> wrzesien;
    List<Double> pazdziernik;
    List<Double> listopad;
    List<Double> grudzien;
    List<Double> Ipolrocze;
    List<Double> IIpolrocze;
    List<Double> rok;
    private List<Dok> lista;
    private List<Pitpoz> pobierzPity;
    private List<List> zebranieMcy;
    @Inject
    private Pitpoz biezacyPit;
    @Inject
    private PodStawkiDAO podstawkiDAO;
    @Inject
    private ZobowiazanieDAO zobowiazanieDAO;
    //dane niezbedne do wyliczania pit
    private String wybranyudzialowiec;
    private String wybranyprocent;
    private List<String> listawybranychudzialowcow;

    public ZestawienieRyczaltView() {
        styczen = Arrays.asList(new Double[4]);
        styczen = Arrays.asList(new Double[4]);
        luty = Arrays.asList(new Double[4]);
        marzec = Arrays.asList(new Double[4]);
        kwiecien = Arrays.asList(new Double[4]);
        maj = Arrays.asList(new Double[4]);
        czerwiec = Arrays.asList(new Double[4]);
        lipiec = Arrays.asList(new Double[4]);
        sierpien = Arrays.asList(new Double[4]);
        wrzesien = Arrays.asList(new Double[4]);
        pazdziernik = Arrays.asList(new Double[4]);
        listopad = Arrays.asList(new Double[4]);
        grudzien = Arrays.asList(new Double[4]);
        pobierzPity = new ArrayList<>();
        zebranieMcy = new ArrayList<>();
        listapit = new ArrayList<>();
        listawybranychudzialowcow = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Podatnik pod = podatnikDAO.find(wpisView.getPodatnikWpisu());
            try {
                for (Udzialy p : pod.getUdzialy()) {
                    listawybranychudzialowcow.add(p.getNazwiskoimie());

                }
            } catch (Exception e) {
                Msg.msg("e", "Nie uzupełnione parametry podatnika", "formpit:messages");
            }
            Collection c = null;
            try {
                c = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                for (int i = 0; i < 4; i++) {
                    styczen.set(i, 0.0);
                    luty.set(i, 0.0);
                    marzec.set(i, 0.0);
                    kwiecien.set(i, 0.0);
                    maj.set(i, 0.0);
                    czerwiec.set(i, 0.0);
                    lipiec.set(i, 0.0);
                    sierpien.set(i, 0.0);
                    wrzesien.set(i, 0.0);
                    pazdziernik.set(i, 0.0);
                    listopad.set(i, 0.0);
                    grudzien.set(i, 0.0);
                }
                lista = new ArrayList<>();
                lista.addAll(c);
                for (Dok dokument : lista) {
                    List<KwotaKolumna> szczegol = dokument.getListakwot();
                    for (KwotaKolumna tmp : szczegol) {
                        String selekcja = dokument.getPkpirM();
                        String selekcja2 = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (selekcja) {
                            case "01":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = styczen.get(0) + kwota;
                                        styczen.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = styczen.get(1) + kwota;
                                        styczen.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = styczen.get(2) + kwota;
                                        styczen.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = styczen.get(3) + kwota;
                                        styczen.set(3, temp);
                                        break;
                                }
                                break;
                            case "02":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = luty.get(0) + kwota;
                                        luty.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = luty.get(1) + kwota;
                                        luty.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = luty.get(2) + kwota;
                                        luty.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = luty.get(3) + kwota;
                                        luty.set(3, temp);
                                        break;
                                }
                                break;
                            case "03":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = marzec.get(0) + kwota;
                                        marzec.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = marzec.get(1) + kwota;
                                        marzec.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = marzec.get(2) + kwota;
                                        marzec.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = marzec.get(3) + kwota;
                                        marzec.set(3, temp);
                                        break;
                                }
                                break;
                            case "04":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = kwiecien.get(0) + kwota;
                                        kwiecien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = kwiecien.get(1) + kwota;
                                        kwiecien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = kwiecien.get(2) + kwota;
                                        kwiecien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = kwiecien.get(3) + kwota;
                                        kwiecien.set(3, temp);
                                        break;
                                }
                                break;
                            case "05":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = maj.get(0) + kwota;
                                        maj.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = maj.get(1) + kwota;
                                        maj.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = maj.get(2) + kwota;
                                        maj.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = maj.get(3) + kwota;
                                        maj.set(3, temp);
                                        break;
                                }
                                break;
                            case "06":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = czerwiec.get(0) + kwota;
                                        czerwiec.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = czerwiec.get(1) + kwota;
                                        czerwiec.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = czerwiec.get(2) + kwota;
                                        czerwiec.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = czerwiec.get(3) + kwota;
                                        czerwiec.set(3, temp);
                                        break;
                                }
                                break;
                            case "07":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = lipiec.get(0) + kwota;
                                        lipiec.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = lipiec.get(1) + kwota;
                                        lipiec.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = lipiec.get(2) + kwota;
                                        lipiec.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = lipiec.get(3) + kwota;
                                        lipiec.set(3, temp);
                                        break;
                                }
                                break;
                            case "08":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = sierpien.get(0) + kwota;
                                        sierpien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = sierpien.get(1) + kwota;
                                        sierpien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = sierpien.get(2) + kwota;
                                        sierpien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = sierpien.get(3) + kwota;
                                        sierpien.set(3, temp);
                                        break;
                                }
                                break;
                            case "09":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = wrzesien.get(0) + kwota;
                                        wrzesien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = wrzesien.get(1) + kwota;
                                        wrzesien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = wrzesien.get(2) + kwota;
                                        wrzesien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = wrzesien.get(3) + kwota;
                                        wrzesien.set(3, temp);
                                        break;
                                }
                                break;
                            case "10":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = pazdziernik.get(0) + kwota;
                                        pazdziernik.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = pazdziernik.get(1) + kwota;
                                        pazdziernik.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = pazdziernik.get(2) + kwota;
                                        pazdziernik.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = pazdziernik.get(3) + kwota;
                                        pazdziernik.set(3, temp);
                                        break;
                                }
                                break;
                            case "11":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = listopad.get(0) + kwota;
                                        listopad.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = listopad.get(1) + kwota;
                                        listopad.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = listopad.get(2) + kwota;
                                        listopad.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = listopad.get(3) + kwota;
                                        listopad.set(3, temp);
                                        break;
                                }
                                break;
                            case "12":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = grudzien.get(0) + kwota;
                                        grudzien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = grudzien.get(1) + kwota;
                                        grudzien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = grudzien.get(2) + kwota;
                                        grudzien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = grudzien.get(3) + kwota;
                                        grudzien.set(3, temp);
                                        break;
                                }
                                break;
                        }
                    }
                    //pobierzPity();
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
                }

                Ipolrocze = new ArrayList<>();
                IIpolrocze = new ArrayList<>();
                rok = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    Ipolrocze.add(styczen.get(i) + luty.get(i) + marzec.get(i) + kwiecien.get(i) + maj.get(i) + czerwiec.get(i));
                    IIpolrocze.add(lipiec.get(i) + sierpien.get(i) + wrzesien.get(i) + pazdziernik.get(i) + listopad.get(i) + grudzien.get(i));
                    rok.add(Ipolrocze.get(i) + IIpolrocze.get(i));
                }
            }
        }
    }

    //oblicze pit i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        if (!wybranyudzialowiec.equals("wybierz osobe")) {
            try {
                Podatnik tmpP = podatnikDAO.find(wpisView.getPodatnikWpisu());
                List<Udzialy> lista = tmpP.getUdzialy();
                for (Udzialy p : lista) {
                    if (p.getNazwiskoimie().equals(wybranyudzialowiec)) {
                        wybranyprocent = p.getUdzial();
                        break;
                    }
                }
                biezacyPit = new Pitpoz();
                biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
                biezacyPit.setPkpirR(wpisView.getRokWpisu().toString());
                biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
                biezacyPit.setPrzychody(obliczprzychod());
                double procent = Double.parseDouble(wybranyprocent) / 100;
                biezacyPit.setPrzychodyudzial(biezacyPit.getPrzychody().multiply(new BigDecimal(procent)));
                biezacyPit.setKoszty(obliczkoszt());
                biezacyPit.setKosztyudzial(biezacyPit.getKoszty().multiply(new BigDecimal(procent)));
                biezacyPit.setWynik(biezacyPit.getPrzychodyudzial().subtract(biezacyPit.getKosztyudzial()));
                biezacyPit.setUdzialowiec(wybranyudzialowiec);
                biezacyPit.setUdzial(wybranyprocent);
                rozliczstrate(tmpP);
                String poszukiwany = wpisView.getPodatnikWpisu();
                Podatnik selected = podatnikDAO.find(poszukiwany);
                Iterator it;
                it = selected.getZusparametr().iterator();
                while (it.hasNext()) {
                    Zusstawki tmpX = (Zusstawki) it.next();
                    if (tmpX.getZusstawkiPK().getRok().equals(wpisView.getRokWpisu().toString())
                            && tmpX.getZusstawkiPK().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                        if (selected.getOdliczaczus51() == true) {
                            if (tmpX.getZus51ch() != null) {
                                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51ch()));
                            } else {
                                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51bch()));
                            }
                        } else {
                            biezacyPit.setZus51(new BigDecimal(0));
                        }
                        biezacyPit.setZus52(BigDecimal.valueOf(tmpX.getZus52odl()));
                        break;
                    }
                }

                Pitpoz sumapoprzednichmcy = skumulujpity(biezacyPit.getPkpirM(), wybranyudzialowiec);
                if (selected.getOdliczaczus51() == true) {
                    biezacyPit.setZus51(biezacyPit.getZus51().add(sumapoprzednichmcy.getZus51()));
                }
                BigDecimal pierwszepomniejszenie = biezacyPit.getWynik().subtract(biezacyPit.getStrata());
                BigDecimal tmp = pierwszepomniejszenie.subtract(biezacyPit.getZus51());
                tmp = tmp.setScale(0, RoundingMode.HALF_EVEN);
                if (tmp.signum() == -1) {
                    biezacyPit.setPodstawa(BigDecimal.ZERO);
                } else {
                    //wyliczenie podatku poczatek
                    biezacyPit.setPodstawa(tmp);
                }
                int index = selected.getPodatekdochodowy().size() - 1;
                String opodatkowanie = selected.getPodatekdochodowy().get(index).getParametr();
                String rodzajop = opodatkowanie;
                Double stawka = 0.0;
                BigDecimal podatek = BigDecimal.ZERO;
                BigDecimal dochód = biezacyPit.getPodstawa();
                BigDecimal przychody = biezacyPit.getPrzychody();
                Podstawki tmpY;
                tmpY = podstawkiDAO.find(Integer.parseInt(biezacyPit.getPkpirR()));
                switch (rodzajop) {
                    case "zasady ogólne":
                        stawka = tmpY.getStawka1();
                        podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.subtract(BigDecimal.valueOf(tmpY.getKwotawolna()));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                        break;
                    case "zasady ogólne bez VAT":
                        stawka = tmpY.getStawka1();
                        podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.subtract(BigDecimal.valueOf(tmpY.getKwotawolna()));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                        break;
                    case "podatek liniowy":
                        stawka = tmpY.getStawkaliniowy();
                        podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                        break;
                    case "podatek liniowy bez VAT":
                        stawka = tmpY.getStawkaliniowy();
                        podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                        break;
                    case "ryczałt":
                        stawka = tmpY.getStawkaryczalt1();
                        podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                        break;
                    case "ryczałt bez VAT":
                        stawka = tmpY.getStawkaryczalt1();
                        podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                        break;
                }
                if (podatek.signum() == 1) {
                    biezacyPit.setPodatek(podatek);
                } else {
                    biezacyPit.setPodatek(BigDecimal.ZERO);
                }
                biezacyPit.setZus52(biezacyPit.getZus52().add(sumapoprzednichmcy.getZus52()));
                BigDecimal tmpX = podatek.subtract(biezacyPit.getZus52());
                tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
                if (tmpX.signum() == -1) {
                    biezacyPit.setPododpoczrok(BigDecimal.ZERO);
                } else {
                    biezacyPit.setPododpoczrok(tmpX);
                }
                //wyliczenie podatku koniec

                biezacyPit.setNalzalodpoczrok(sumapoprzednichmcy.getNalzalodpoczrok());
                biezacyPit.setNaleznazal(biezacyPit.getPododpoczrok().subtract(biezacyPit.getNalzalodpoczrok()));
                if (biezacyPit.getNaleznazal().compareTo(BigDecimal.ZERO) == 1) {
                    biezacyPit.setDozaplaty(biezacyPit.getNaleznazal());
                } else {
                    biezacyPit.setDozaplaty(BigDecimal.ZERO);
                }
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych paramterów!! Nie można przeliczyć PIT za: ", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                biezacyPit = new Pitpoz();
            }
            try {
                Zobowiazanie data = zobowiazanieDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
                biezacyPit.setTerminwplaty(data.getZobowiazaniePK().getRok() + "-" + data.getZobowiazaniePK().getMc() + "-" + data.getPitday());
            } catch (Exception e) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych dat zobowiazan!! Nie można przeliczyć PIT za: ", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                biezacyPit = new Pitpoz();
                RequestContext.getCurrentInstance().update("formpit:");
            }

        }
    }

    private void rozliczstrate(Podatnik tmp) {
        List<Straty> straty = tmp.getStratyzlatub();
        double sumastrat = 0.0;
        try {
            for (Straty p : straty) {
                Double wyliczmaks = Double.parseDouble(p.getZostalo()) - Double.parseDouble(p.getPolowakwoty());
                if (wyliczmaks > 0) {
                    sumastrat += Double.parseDouble(p.getPolowakwoty());
                } else {
                    sumastrat += Double.parseDouble(p.getZostalo());
                }
            }
            if (biezacyPit.getWynik().signum() == 1) {
                BigDecimal stratadoujecia = biezacyPit.getWynik().subtract(new BigDecimal(sumastrat));
                if (stratadoujecia.signum() == -1) {
                    biezacyPit.setStrata(biezacyPit.getWynik());
                } else {
                    biezacyPit.setStrata(new BigDecimal(sumastrat));
                }
            } else {
                biezacyPit.setStrata(BigDecimal.ZERO);
            }
        } catch (Exception e) {
            biezacyPit.setStrata(BigDecimal.ZERO);
        }
    }

    public void zachowajPit() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        if (biezacyPit.getWynik() != null) {
            try {
                Pitpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec());
                pitDAO.destroy(find);
                pitDAO.dodaj(biezacyPit);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edytowano PIT " + biezacyPit.getUdzialowiec() + " za m-c:", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) {
                pitDAO.dodaj(biezacyPit);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zachowano PIT " + biezacyPit.getUdzialowiec() + " za m-c:", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie można zachować. PIT nie wypełniony", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public Pitpoz skumulujpity(String mcDo, String udzialowiec) {
        Pitpoz tmp = new Pitpoz();
        tmp.setZus51(BigDecimal.ZERO);
        tmp.setZus52(BigDecimal.ZERO);
        tmp.setNalzalodpoczrok(BigDecimal.ZERO);
        try {
            Collection c = pitDAO.findAll();
            Iterator it;
            it = c.iterator();

            while (it.hasNext()) {
                Pitpoz tmpX = (Pitpoz) it.next();
                if (!tmpX.getPkpirM().equals(mcDo) && tmpX.getPodatnik().equals(wpisView.getPodatnikWpisu()) && tmpX.getUdzialowiec().equals(udzialowiec)) {
                    tmp.setZus51(tmp.getZus51().add(tmpX.getZus51()));
                    tmp.setZus52(tmp.getZus52().add(tmpX.getZus52()));
                    tmp.setNalzalodpoczrok(tmp.getNalzalodpoczrok().add(tmpX.getNaleznazal()));
                }
            }

            return tmp;
        } catch (Exception e) {

            return tmp;
        }

    }

    private BigDecimal obliczprzychod() {
        BigDecimal suma = new BigDecimal(0);
        String selekcja = wpisView.getMiesiacWpisu();
        switch (selekcja) {
            case "01":
                for (int i = 0; i < 1; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "02":
                for (int i = 0; i < 2; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "03":
                for (int i = 0; i < 3; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "04":
                for (int i = 0; i < 4; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "05":
                for (int i = 0; i < 5; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "06":
                for (int i = 0; i < 6; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "07":
                for (int i = 0; i < 7; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "08":
                for (int i = 0; i < 8; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "09":
                for (int i = 0; i < 9; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "10":
                for (int i = 0; i < 10; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "11":
                for (int i = 0; i < 11; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
            case "12":
                for (int i = 0; i < 12; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(0).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(1).toString())));
                }
                break;
        }
        return suma;
    }

    private BigDecimal obliczkoszt() {
        BigDecimal suma = new BigDecimal(0);
        String selekcja = wpisView.getMiesiacWpisu();
        switch (selekcja) {
            case "01":
                for (int i = 0; i < 1; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "02":
                for (int i = 0; i < 2; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "03":
                for (int i = 0; i < 3; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "04":
                for (int i = 0; i < 4; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "05":
                for (int i = 0; i < 5; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "06":
                for (int i = 0; i < 6; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "07":
                for (int i = 0; i < 7; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "08":
                for (int i = 0; i < 8; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "09":
                for (int i = 0; i < 9; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "10":
                for (int i = 0; i < 10; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "11":
                for (int i = 0; i < 11; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
            case "12":
                for (int i = 0; i < 12; i++) {
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(2).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(3).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(4).toString())));
                    suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(i).get(5).toString())));
                }
                break;
        }
        return suma;
    }

    public void pobierzPity() {
        try {
            pobierzPity.addAll(pitDAO.findAll());
        } catch (Exception e) {
        }
        narPitpoz = new Pitpoz();
        int index = 0;
        Iterator it;
        it = pobierzPity.iterator();
        while (it.hasNext()) {
            Pitpoz tmpX = (Pitpoz) it.next();
            if (tmpX.getPkpirR().equals(wpisView.getRokWpisu().toString())
                    && tmpX.getPkpirM().equals(wpisView.getMiesiacWpisu())) {
                index = tmpX.getId() - 1;
                break;
            }
        }
        //narPitpoz = pobierzPity.get(Mce.getMapamcyX().get(wpisView.getMiesiacWpisu()));
        narPitpoz = pobierzPity.get(index);
        biezacyPit = narPitpoz;
    }

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public List<Double> getStyczen() {
        return styczen;
    }

    public void setStyczen(List<Double> styczen) {
        this.styczen = styczen;
    }

    public List<Double> getLuty() {
        return luty;
    }

    public void setLuty(List<Double> luty) {
        this.luty = luty;
    }

    public List<Double> getMarzec() {
        return marzec;
    }

    public void setMarzec(List<Double> marzec) {
        this.marzec = marzec;
    }

    public List<Double> getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(List<Double> kwiecien) {
        this.kwiecien = kwiecien;
    }

    public List<Double> getMaj() {
        return maj;
    }

    public void setMaj(List<Double> maj) {
        this.maj = maj;
    }

    public List<Double> getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(List<Double> czerwiec) {
        this.czerwiec = czerwiec;
    }

    public List<Double> getLipiec() {
        return lipiec;
    }

    public void setLipiec(List<Double> lipiec) {
        this.lipiec = lipiec;
    }

    public List<Double> getSierpien() {
        return sierpien;
    }

    public void setSierpien(List<Double> sierpien) {
        this.sierpien = sierpien;
    }

    public List<Double> getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(List<Double> wrzesien) {
        this.wrzesien = wrzesien;
    }

    public List<Double> getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(List<Double> pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public List<Double> getListopad() {
        return listopad;
    }

    public void setListopad(List<Double> listopad) {
        this.listopad = listopad;
    }

    public List<Double> getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(List<Double> grudzien) {
        this.grudzien = grudzien;
    }

    public List<Double> getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(List<Double> Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public List<Double> getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(List<Double> IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public List<Double> getRok() {
        return rok;
    }

    public void setRok(List<Double> rok) {
        this.rok = rok;
    }

    public Pitpoz getPitpoz() {
        return pitpoz;
    }

    public void setPitpoz(Pitpoz pitpoz) {
        this.pitpoz = pitpoz;
    }

    public Pitpoz getNarPitpoz() {
        return narPitpoz;
    }

    public void setNarPitpoz(Pitpoz narPitpoz) {
        this.narPitpoz = narPitpoz;
    }

    public List<Pitpoz> getPobierzPity() {
        return pobierzPity;
    }

    public void setPobierzPity(List<Pitpoz> pobierzPity) {
        this.pobierzPity = pobierzPity;
    }

    public List<List> getZebranieMcy() {
        return zebranieMcy;
    }

    public void setZebranieMcy(List<List> zebranieMcy) {
        this.zebranieMcy = zebranieMcy;
    }

    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public PodStawkiDAO getPodstawkiDAO() {
        return podstawkiDAO;
    }

    public void setPodstawkiDAO(PodStawkiDAO podstawkiDAO) {
        this.podstawkiDAO = podstawkiDAO;
    }

    public ZobowiazanieDAO getZobowiazanieDAO() {
        return zobowiazanieDAO;
    }

    public void setZobowiazanieDAO(ZobowiazanieDAO zobowiazanieDAO) {
        this.zobowiazanieDAO = zobowiazanieDAO;
    }

    public String getWybranyudzialowiec() {
        return wybranyudzialowiec;
    }

    public void setWybranyudzialowiec(String wybranyudzialowiec) {
        this.wybranyudzialowiec = wybranyudzialowiec;
    }

    public String getWybranyprocent() {
        return wybranyprocent;
    }

    public void setWybranyprocent(String wybranyprocent) {
        this.wybranyprocent = wybranyprocent;
    }

    public List<String> getListawybranychudzialowcow() {
        return listawybranychudzialowcow;
    }

    public void setListawybranychudzialowcow(List<String> listawybranychudzialowcow) {
        this.listawybranychudzialowcow = listawybranychudzialowcow;
    }
}
