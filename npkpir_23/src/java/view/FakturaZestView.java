/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import comparator.FakturaZestawieniecomparator;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import embeddable.FakturaZestawienie;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
import msg.Msg;
import waluty.Z;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturaZestView implements Serializable {
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    //faktury z bazy danych
    private List<Faktura> fakturyWystawione;
    //listaprzetworzona
    private List<FakturaZestawienie> fakturyZestawienie;
    //lista podatnikow
    private List<Podatnik> podatnicyWProgramie;
    private List<Klienci> klienci;
    private Klienci szukanyklient;

    public FakturaZestView() {
        fakturyWystawione = Collections.synchronizedList(new ArrayList<>());
        fakturyZestawienie = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init1() {
        podatnicyWProgramie = podatnikDAO.findAll();
        klienci = new ArrayList<>();
        klienci.addAll(pobierzkontrahentow());
    }
    
    private Collection<? extends Klienci> pobierzkontrahentow() {
        Collection p = fakturaDAO.findKontrahentFakturyRO(wpisView.getPodatnikObiekt());
        for (Iterator<Klienci> it = p.iterator(); it.hasNext();) {
            Klienci k = it.next();
            if (k == null) {
                it.remove();
            } else if (k.isAktywnydlafaktrozrachunki() == false) {
                it.remove();
            }
        }
        return p;
    }
    
    public void pobierzwszystkoKlienta() {
        init();
        szukanyklient = null;
    }
    
    public List<Faktura> pobierzfaktury() {
        List<Faktura> zwrot = new ArrayList<>();
        if (szukanyklient!=null) {
            zwrot = fakturaDAO.findbyKontrahentNipRok(szukanyklient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        } else {
            zwrot = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        }
        return zwrot;
    }
    
    public void init() { //E.m(this);
        fakturyZestawienie = Collections.synchronizedList(new ArrayList<>());
        fakturyWystawione = pobierzfaktury();
        //List<Podatnik> podatnicy = podatnikDAO.findAll();
        Map<String,FakturaZestawienie> odnalezione = new ConcurrentHashMap<>();
        if (fakturyWystawione != null) {
            fakturyWystawione.stream().forEach((p)->{
                if (p.isTylkodlaokresowej()==false) {
                    String n = p.getKontrahent().getNip();
                    FakturaZestawienie f = new FakturaZestawienie();
                    if (odnalezione.keySet().contains(n)) {
                        f = odnalezione.get(n);
                        FakturaZestawienie.FZTresc ft = f.new FZTresc();
                        ft.setMc(p.getMc());
                        ft.setNrfakt(p.getNumerkolejny());
                        if (p.getPozycjepokorekcie()!=null&&p.getPozycjepokorekcie().size()>0) {
                            ft.setNetto(Z.z(p.getNettopk()-p.getNetto()));
                            ft.setVat(Z.z(p.getVatpk()-p.getVat()));
                            ft.setBrutto(Z.z(p.getBruttopk()-p.getBrutto()));
                            ft.setOpis(p.getPrzyczynakorekty());
                        } else {
                            ft.setNetto(p.getNetto());
                            ft.setVat(p.getVat());
                            ft.setBrutto(p.getBrutto());
                            ft.setOpis(p.getPozycjenafakturze().get(0).getNazwa());
                        }
                        ft.setData(p.getDatawystawienia());
                        ft.setFaktura(p);
                        f.getTrescfaktury().add(ft);
                    } else {
                        Podatnik odnalezionyPodatnik = null;
                        try {
                            odnalezionyPodatnik = znajdzpodattniknip(n);
                        } catch (Exception e) { E.e(e); }
                        FakturaZestawienie.FZTresc ft = f.new FZTresc();
                        if (odnalezionyPodatnik != null) {
                            f.setPodatnik(odnalezionyPodatnik);
                            ft.setMc(p.getMc());
                            ft.setNrfakt(p.getNumerkolejny());
                            if (p.getPozycjepokorekcie()!=null&&p.getPozycjepokorekcie().size()>0) {
                                ft.setNetto(Z.z(p.getNettopk()-p.getNetto()));
                                ft.setVat(Z.z(p.getVatpk()-p.getVat()));
                                ft.setBrutto(Z.z(p.getBruttopk()-p.getBrutto()));
                                ft.setOpis(p.getPrzyczynakorekty());
                            } else {
                                ft.setNetto(p.getNetto());
                                ft.setVat(p.getVat());
                                ft.setBrutto(p.getBrutto());
                                ft.setOpis(p.getPozycjenafakturze().get(0).getNazwa());
                            }
                            ft.setData(p.getDatawystawienia());
                            ft.setFaktura(p);
                            f.getTrescfaktury().add(ft);
                        } else {
                            f.setKontrahent(p.getKontrahent());
                            ft.setMc(p.getMc());
                            ft.setNrfakt(p.getNumerkolejny());
                            if (p.getPozycjepokorekcie()!=null&&p.getPozycjepokorekcie().size()>0) {
                                ft.setNetto(Z.z(p.getNettopk()-p.getNetto()));
                                ft.setVat(Z.z(p.getVatpk()-p.getVat()));
                                ft.setBrutto(Z.z(p.getBruttopk()-p.getBrutto()));
                                ft.setOpis(p.getPrzyczynakorekty());
                            } else {
                                ft.setNetto(p.getNetto());
                                ft.setVat(p.getVat());
                                ft.setBrutto(p.getBrutto());
                                ft.setOpis(p.getPozycjenafakturze().get(0).getNazwa());
                            }
                            ft.setData(p.getDatawystawienia());
                            ft.setFaktura(p);
                            f.getTrescfaktury().add(ft);
                        }
                        odnalezione.put(n,f);
                    }
                }
            });
        }
        List<FakturaZestawienie> lista = new ArrayList(odnalezione.values());
        Collections.sort(lista, new FakturaZestawieniecomparator());
        fakturyZestawienie.addAll(lista);
        Msg.msg("Pobrano faktury");
    }
    private Podatnik znajdzpodattniknip(String n) {
        Podatnik zwrot = null;
        for (Podatnik p : podatnicyWProgramie) {
            if (p.getNip().equals(n)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
  
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public FakturaDAO getFakturaDAO() {
        return fakturaDAO;
    }

    public void setFakturaDAO(FakturaDAO fakturaDAO) {
        this.fakturaDAO = fakturaDAO;
    }

    public List<FakturaZestawienie> getFakturyZestawienie() {
        return fakturyZestawienie;
    }

    public void setFakturyZestawienie(List<FakturaZestawienie> fakturyZestawienie) {
        this.fakturyZestawienie = fakturyZestawienie;
    }

    public List<Klienci> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public Klienci getSzukanyklient() {
        return szukanyklient;
    }

    public void setSzukanyklient(Klienci szukanyklient) {
        this.szukanyklient = szukanyklient;
    }

    
    
    
    
}
