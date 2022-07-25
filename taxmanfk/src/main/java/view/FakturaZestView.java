/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import comparator.FakturaZestawieniecomparator;
import comparator.Kliencicomparator;
import dao.FakturaDAO;
import dao.FakturaWaloryzacjaDAO;
import dao.PodatnikDAO;
import embeddable.FZTresc;
import embeddable.FakturaZestawienie;
import entity.Faktura;
import entity.FakturaWaloryzacja;
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
public class FakturaZestView implements Serializable {
    @Inject
    private WpisView wpisView;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private FakturaWaloryzacjaDAO fakturaWaloryzacjaDAO;
    //faktury z bazy danych
    private List<Faktura> fakturyWystawione;
    //listaprzetworzona
    private List<FakturaZestawienie> fakturyZestawienie;
    //lista podatnikow
    private List<Podatnik> podatnicyWProgramie;
    private List<Klienci> klienci;
    private Klienci szukanyklient;
    private List<FZTresc> filtertresc;
    private boolean pobierzwszystkielataKlienta;
    @Inject
    private FakturaWaloryzacja fakturaWaloryzacja;
    private Podatnik podatnik;

    public FakturaZestView() {
        fakturyWystawione = Collections.synchronizedList(new ArrayList<>());
        fakturyZestawienie = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init1() {
        podatnicyWProgramie = podatnikDAO.findAll();
        klienci = new ArrayList<>();
        List<Klienci> pobierzkontrahentow = pobierzkontrahentow();
        if (pobierzkontrahentow!=null) {
            klienci.addAll(pobierzkontrahentow);
        }
        Collections.sort(klienci, new Kliencicomparator());
    }
    
    private List<Klienci> pobierzkontrahentow() {
        List<Klienci> p = fakturaDAO.findKontrahentFaktury(wpisView.getPodatnikObiekt());
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
        if (pobierzwszystkielataKlienta==true) {
            if (szukanyklient!=null) {
                init(1);
                fakturaWaloryzacja = pobierzwaloryzacje(wpisView.getPodatnikObiekt(), szukanyklient, wpisView.getRokWpisuSt());
                Msg.msg("Pobrano dane");
            } else {
                fakturyZestawienie = new ArrayList<>();
                Msg.msg("e","Nie wybrano klienta");
            }
        } else {
            init(0);
            fakturaWaloryzacja = pobierzwaloryzacje(wpisView.getPodatnikObiekt(), szukanyklient, wpisView.getRokWpisuSt());
            Msg.msg("Pobrano dane");
        }
    }
    
    public void przelicz() {
        if (fakturaWaloryzacja!=null) {
            if (fakturaWaloryzacja.getProcentzmian()>0.0) {
                double procentzmian = 1+Z.z4(fakturaWaloryzacja.getProcentzmian()/100);
                fakturaWaloryzacja.setKwotabiezacanettoN(Z.z0(fakturaWaloryzacja.getKwotabiezacanetto()*procentzmian));
                fakturaWaloryzacja.setUmowaopraceN(Z.z0(fakturaWaloryzacja.getUmowaoprace()*procentzmian));
                fakturaWaloryzacja.setUmowazlecenieN(Z.z0(fakturaWaloryzacja.getUmowazlecenie()*procentzmian));
                fakturaWaloryzacja.setSprawozdanieroczneN(Z.z0(fakturaWaloryzacja.getSprawozdanieroczne()*procentzmian));
                fakturaWaloryzacja.setObsluganiemcyN(Z.z0(fakturaWaloryzacja.getObsluganiemcy()*procentzmian));
                fakturaWaloryzacja.setOddelegowanieN(Z.z0(fakturaWaloryzacja.getOddelegowanie()*procentzmian));
                Msg.msg("Przeliczono kwoty");
            }
        }
    }
    
    public void zapiszwaloryzacje() {
        if (fakturaWaloryzacja!=null) {
            fakturaWaloryzacjaDAO.edit(fakturaWaloryzacja);
            Msg.msg("Zapisano zmiany waloryzacji");
        } else {
            Msg.msg("e","Błąd. Nie wybrano waloryzacji");
        }
    }
    
    private FakturaWaloryzacja pobierzwaloryzacje(Podatnik podatnikObiekt, Klienci szukanyklient, String rokWpisuSt) {
        FakturaWaloryzacja zwrot = fakturaWaloryzacjaDAO.findByPodatnikKontrahentRok(podatnikObiekt, szukanyklient, rokWpisuSt);
        if (zwrot==null) {
            zwrot = new FakturaWaloryzacja(podatnikObiekt, szukanyklient, rokWpisuSt);
            fakturaWaloryzacjaDAO.create(zwrot);
        }
        podatnik = podatnikDAO.findPodatnikByNIP(szukanyklient.getNip());
        return  zwrot;
    }
    public List<Faktura> pobierzfaktury(int o) {
        List<Faktura> zwrot = new ArrayList<>();
        if (szukanyklient!=null) {
            if (o==0) {
                zwrot = fakturaDAO.findbyKontrahentNipRok(szukanyklient.getNip(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            } else {
                for (int i = wpisView.getRokWpisu();i>2015;i--) {
                    try {
                        zwrot.addAll(fakturaDAO.findbyKontrahentNipRok(szukanyklient.getNip(), wpisView.getPodatnikObiekt(), String.valueOf(i)));
                    } catch (Exception e){}
                }
            }
        } else {
            zwrot = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        }
        return zwrot;
    }
    
    public void wszyscypodatnicy() {
        szukanyklient = null;
        init(0);
    }
    
    
    public void init(int o) { //E.m(this);
        fakturyZestawienie = Collections.synchronizedList(new ArrayList<>());
        fakturyWystawione = pobierzfaktury(o);
        //List<Podatnik> podatnicy = podatnikDAO.findAll();
        Map<String,FakturaZestawienie> odnalezione = new ConcurrentHashMap<>();
        if (fakturyWystawione != null) {
            fakturyWystawione.stream().forEach((p)->{
                if (p.isTylkodlaokresowej()==false) {
                    String nipkontrahenta = p.getKontrahent().getNip();
                    FakturaZestawienie fakturazestawienie = new FakturaZestawienie();
                    if (odnalezione.keySet().contains(nipkontrahenta)) {
                        fakturazestawienie = odnalezione.get(nipkontrahenta);
                        FZTresc ft = new FZTresc();
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
                        ft.setIloscwierszy(p.getPozycjenafakturze().size());
                        ft.setData(p.getDatawystawienia());
                        ft.setFaktura(p);
                        ft.setWaluta(p.getWalutafaktury());
                        fakturazestawienie.getTrescfaktury().add(ft);
                    } else {
                        Podatnik odnalezionyPodatnik = null;
                        try {
                            odnalezionyPodatnik = znajdzpodattniknip(nipkontrahenta);
                        } catch (Exception e) { E.e(e); }
                        FZTresc ft = new FZTresc();
                        if (odnalezionyPodatnik != null) {
                            fakturazestawienie.setPodatnik(odnalezionyPodatnik);
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
                            ft.setIloscwierszy(p.getPozycjenafakturze().size());
                            ft.setData(p.getDatawystawienia());
                            ft.setFaktura(p);
                            ft.setWaluta(p.getWalutafaktury());
                            fakturazestawienie.getTrescfaktury().add(ft);
                        } else {
                            fakturazestawienie.setKontrahent(p.getKontrahent());
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
                            ft.setIloscwierszy(p.getPozycjenafakturze().size());
                            ft.setData(p.getDatawystawienia());
                            ft.setFaktura(p);
                            ft.setWaluta(p.getWalutafaktury());
                            fakturazestawienie.getTrescfaktury().add(ft);
                        }
                        odnalezione.put(nipkontrahenta,fakturazestawienie);
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

    public List<FZTresc> getFiltertresc() {
        return filtertresc;
    }

    public void setFiltertresc(List<FZTresc> filtertresc) {
        this.filtertresc = filtertresc;
    }

    public boolean isPobierzwszystkielataKlienta() {
        return pobierzwszystkielataKlienta;
    }

    public void setPobierzwszystkielataKlienta(boolean pobierzwszystkielataKlienta) {
        this.pobierzwszystkielataKlienta = pobierzwszystkielataKlienta;
    }

    public FakturaWaloryzacja getFakturaWaloryzacja() {
        return fakturaWaloryzacja;
    }

    public void setFakturaWaloryzacja(FakturaWaloryzacja fakturaWaloryzacja) {
        this.fakturaWaloryzacja = fakturaWaloryzacja;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    

    
    
    
    
}
