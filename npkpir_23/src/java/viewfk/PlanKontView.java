/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.KontaFKBean;
import beansFK.PlanKontFKBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.UkladBRBean;
import comparator.Kontocomparator;
import converter.KontoConv;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.DelegacjaDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.MiejsceKosztowDAO;
import daoFK.MiejscePrzychodowDAO;
import daoFK.PojazdyDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Delegacja;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.MiejscePrzychodow;
import entityfk.Pojazdy;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import entityfk.WierszBO;
import enumy.Slownik;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import lazyDataModel.LazyKontoDataModel;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import pdffk.PdfPlanKont;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PlanKontView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int levelBiezacy = 0;
    private List<Konto> wykazkont;
    private LazyDataModel wykazkontlazy;
    private List<Konto> wykazkontwzor;
    @Inject
    private Konto selected;
    @Inject
    private Konto noweKonto;
    @Inject
    private KliencifkDAO kliencifkDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private Konto selectednodekonto;
    private Konto selectednodekontowzorcowy;
    private String wewy;
    private boolean czyoddacdowzorca;
    @Inject
    private PodatnikDAO podatnikDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private PojazdyDAO pojazdyDAO;
    @Inject
    private MiejsceKosztowDAO miejsceKosztowDAO;
    @Inject
    private MiejscePrzychodowDAO miejscePrzychodowDAO;
    @Inject
    private DelegacjaDAO delegacjaDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private KontoDAOfk kontoDAO;
    private String infozebrakslownikowych;
    @ManagedProperty(value = "#{planKontCompleteView}")
    private PlanKontCompleteView planKontCompleteView;
    @ManagedProperty(value = "#{kontoConv}")
    private KontoConv kontoConv;
    private boolean bezslownikowych;
    private boolean tylkosyntetyka;
    private String kontadowyswietlenia;
    private String elementslownika_nazwapelna;
    private String elementslownika_nazwaskrocona;
    private String elementslownika_numerkonta;
    private String styltabeliplankont;
    private boolean usunprzyporzadkowanie;
    private List<UkladBR> listaukladow;
    private UkladBR wybranyuklad;
    private List<UkladBR> listaukladowwzorcowy;
    private UkladBR wybranyukladwzorcowy;
    private String wybranapozycja_wiersz;
    

    public PlanKontView() {
        bezslownikowych = true;
        kontadowyswietlenia = "wszystkie";
         E.m(this);
    }

    @PostConstruct
    public void init() {
//        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        listaukladow = ukladBRDAO.findPodatnikRok(wpisView);
        wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
        if (wybranyuklad != null) {
            PozycjaRZiSFKBean.zmianaukladu("bilansowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
            PozycjaRZiSFKBean.zmianaukladu("wynikowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        }
//<editor-fold defaultstate="collapsed" desc="comment">
//        int czysaslownikowe = sprawdzkonta();
//        if (czysaslownikowe == 0) {
//            infozebrakslownikowych = " Brak podłączonych słowników do kont rozrachunkowych! Nie można księgować kontrahentów.";
//            //RequestContext.getCurrentInstance().update("dialogpierwszy");
//        } else if (czysaslownikowe == 1) {
//            infozebrakslownikowych = " Brak planu kont na dany rok";
//            //RequestContext.getCurrentInstance().update("dialogpierwszy");
//        } else {
//            infozebrakslownikowych = "";
//        }
//</editor-fold>
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
        wykazkontlazy = new LazyKontoDataModel(wykazkont);
        //root = rootInit(wykazkont);
        listaukladowwzorcowy = ukladBRDAO.findukladBRWzorcowyRok(wpisView.getRokWpisuSt());
        wybranyukladwzorcowy = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladowwzorcowy);
        if (wybranyukladwzorcowy != null) {
            PozycjaRZiSFKBean.zmianaukladuwzorcowy("bilansowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
            PozycjaRZiSFKBean.zmianaukladuwzorcowy("wynikowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        }
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        styltabeliplankont = opracujstylwierszatabeli();
        //rootwzorcowy = rootInit(wykazkontwzor);
    }
    
    public void zmienuklad() {
        listaukladow = ukladBRDAO.findPodatnik(wpisView.getPodatnikWpisu());
        for (UkladBR p : listaukladow) {
            p.setAktualny(false);
        }
        ukladBRDAO.editList(listaukladow);
        wybranyuklad.setAktualny(true);
        ukladBRDAO.edit(wybranyuklad);
        PozycjaRZiSFKBean.zmianaukladu("bilansowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        PozycjaRZiSFKBean.zmianaukladu("wynikowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
    }
    
    public void zmienukladwzorcowy() {
        listaukladowwzorcowy = ukladBRDAO.findukladBRWzorcowyRok(wpisView.getRokWpisuSt());
        for (UkladBR p : listaukladowwzorcowy) {
            p.setAktualny(false);
        }
        ukladBRDAO.editList(listaukladowwzorcowy);
        wybranyukladwzorcowy.setAktualny(true);
        ukladBRDAO.edit(wybranyukladwzorcowy);
        PozycjaRZiSFKBean.zmianaukladuwzorcowy("bilansowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        PozycjaRZiSFKBean.zmianaukladuwzorcowy("wynikowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        Collections.sort(wykazkontwzor, new Kontocomparator());
    }
    //tworzy nody z bazy danych dla tablicy nodow plan kont

    private int sprawdzkonta() {
        int zwrot = 0;
        if (wykazkont == null || wykazkont.size() == 0) {
            zwrot = 1;
        } else {
            for (Konto p : wykazkont) {
                if (p.isSlownikowe() == true) {
                    zwrot = 2;
                    break;
                }
            }
        }
        return zwrot;
    }
    
    public void planBezSlownikowychSyntetyczne() {
        if (bezslownikowych == true && tylkosyntetyka == true) {
            wykazkont = kontoDAOfk.findKontazLevelu(wpisView,0);
        } else if (bezslownikowych == true) {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        } else if (tylkosyntetyka == true) {
            wykazkont = kontoDAOfk.findKontazLevelu(wpisView,0);
        } else {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        if (kontadowyswietlenia.equals("bilansowe")) {
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("wynikowe")) {
                    it.remove();
                }
            }
        }
        if (kontadowyswietlenia.equals("wynikowe")) {
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("bilansowe")) {
                    it.remove();
                }
            }
        }
        Collections.sort(wykazkont, new Kontocomparator());
         wykazkontlazy = new LazyKontoDataModel(wykazkont);
        styltabeliplankont = opracujstylwierszatabeli();
    }
    
    public void planBezSlownikowychSyntetyczneWzorcowy() {
        if (bezslownikowych == true && tylkosyntetyka == true) {
            wykazkontwzor = kontoDAOfk.findKontazLeveluWzorcowy(wpisView,0);
        } else if (bezslownikowych == true) {
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(null, wpisView.getRokWpisuSt());
        } else if (tylkosyntetyka == true) {
            wykazkontwzor = kontoDAOfk.findKontazLeveluWzorcowy(wpisView,0);
        } else {
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(null, wpisView.getRokWpisuSt());
        }
        if (kontadowyswietlenia.equals("bilansowe")) {
            for (Iterator it = wykazkontwzor.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("wynikowe")) {
                    it.remove();
                }
            }
        }
        if (kontadowyswietlenia.equals("wynikowe")) {
            for (Iterator it = wykazkontwzor.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("bilansowe")) {
                    it.remove();
                }
            }
        }
        Collections.sort(wykazkontwzor, new Kontocomparator());
        styltabeliplankont = opracujstylwierszatabeli();
    }
    
    public void planTylkoSyntetyczne() {
        if (tylkosyntetyka == true) {
            wykazkont = kontoDAOfk.findKontazLevelu(wpisView,0);
            Collections.sort(wykazkont, new Kontocomparator());
        } else {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Collections.sort(wykazkont, new Kontocomparator());
        }
         wykazkontlazy = new LazyKontoDataModel(wykazkont);
    }

    private TreeNodeExtended<Konto> rootInit(List<Konto> wykazKont) {
        TreeNodeExtended<Konto> r = new TreeNodeExtended("root", null);
        if (!wykazKont.isEmpty()) {
            r.createTreeNodesForElement(wykazKont);
        }
        return r;
    }

    //zostawilem bo duzo zmiennych malo linijek
    private int ustalLevel(TreeNodeExtended<Konto> r) {
        int level = 0;
        if (PlanKontFKBean.czywzorcowe(r.getChildren().get(0))) {
            level = r.ustaldepthDT(wykazkontwzor);
        } else {
            level = r.ustaldepthDT(wykazkont);
        }
        return level;
    }

    public void rozwinwszystkie(TreeNodeExtended<Konto> r) {
        if (r.getChildCount() > 0) {
            levelBiezacy = ustalLevel(r);
            r.expandAll();
        }
    }

    public void rozwin(TreeNodeExtended<Konto> r) {
        int maxpoziom = ustalLevel(r);
        if (levelBiezacy < --maxpoziom) {
            r.expandLevel(levelBiezacy++);
        } else {
            Msg.msg("Osiągnięto maksymalny poziom szczegółowości analityki");
        }
    }

    public void zwinwszystkie(TreeNodeExtended<Konto> r) {
        r.foldAll();
        levelBiezacy = 0;
    }

    public void zwin(TreeNodeExtended<Konto> r) {
        if (levelBiezacy != 0) {
            r.foldLevel(--levelBiezacy);
        } else {
            Msg.msg("Wyświetlane są tylko konta syntetyczne");
        }
    }

    public void dodajdowzorca() {
        czyoddacdowzorca = true;
    }

    public void dodajsyntetyczne() {
        Podatnik podatnik;
        if (czyoddacdowzorca == true) {
            podatnik = null;
        } else {
            podatnik = wpisView.getPodatnikObiekt();
        }
        Konto kontomacierzyste = selectednodekonto;
        if (noweKonto.getBilansowewynikowe() != null) {
            int wynikdodaniakonta = 1;
            if (podatnik.equals(null)) {
                wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczneWzorzec(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            }
            if (czyoddacdowzorca == true) {
                wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
            } else {
                planBezSlownikowychSyntetyczne();
            }
            if (wynikdodaniakonta == 0) {
                noweKonto = new Konto();
                Msg.msg("i", "Dodano konto syntetyczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto syntetyczne o takim numerze juz istnieje!", "formX:messages");
            }
        }
    }

    public void dodajanalityczne() {
        Podatnik podatnik;
        Konto kontomacierzyste = null;
        boolean czysasiostry = false;
        if (czyoddacdowzorca == true) {
            podatnik = null;
            kontomacierzyste = selectednodekontowzorcowy;
        } else {
            podatnik = wpisView.getPodatnikObiekt();
            kontomacierzyste = selectednodekonto;
        }
        if (kontomacierzyste.isBlokada() == false) {
            czysasiostry = kontomacierzyste.isMapotomkow();
            int wynikdodaniakonta = 1;
            if (podatnik == null) {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczneWzorzec(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            }
            if (wynikdodaniakonta == 0) {
                try {
                    UkladBR uklad = czyoddacdowzorca ? wybranyukladwzorcowy : wybranyuklad;
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, uklad);
                    if (kpo != null) {
                        if (kpo.getSyntetykaanalityka().equals("analityka")) {
                            Msg.msg("w", "Konto przyporządkowane z poziomu analityki!");
                        } else {
                            PlanKontFKBean.naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", uklad);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            //przeksięgowuje zapisy z syntetyki, gdy dodawane jest pierwwsze analityczne
            if (wynikdodaniakonta == 0 && czysasiostry == false && czyoddacdowzorca == false) {
                List<StronaWiersza> zapisyrok = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkie(wpisView.getPodatnikObiekt(), kontomacierzyste, wpisView.getRokWpisuSt());
                if (zapisyrok != null) {
                    for (StronaWiersza p : zapisyrok) {
                        p.setKonto(noweKonto);
                    }
                    stronaWierszaDAO.editList(zapisyrok);
                }
                List<WierszBO> wierszeBO = wierszBODAO.findPodatnikRokKonto(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontomacierzyste);
                if (wierszeBO != null) {
                    for (WierszBO p : wierszeBO) {
                        p.setKonto(noweKonto);
                    }
                    wierszBODAO.editList(wierszeBO);
                }
            }
            if (wynikdodaniakonta == 0) {
                PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAOfk);
                if (czyoddacdowzorca == true) {
                    wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
                } else {
                    planBezSlownikowychSyntetyczne();
                }
                noweKonto = new Konto();
                //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
            }
        } else {
            Msg.msg("w", "Nie można dodawać kont analitycznych. Istnieją zapisy z BO");
        }
    }

    
    
    public void dodajanalityczneWpis() {
        String nrmacierzystego = PlanKontFKBean.modyfikujnranalityczne(noweKonto.getPelnynumer());
        Konto kontomacierzyste = PlanKontFKBean.wyszukajmacierzyste(wpisView, kontoDAOfk, nrmacierzystego);
        if (kontomacierzyste != null && kontomacierzyste.getIdslownika() == 0) {
            int wynikdodaniakonta = 1;
            wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            if (wynikdodaniakonta == 0) {
                try {
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, wybranyuklad);
                    if (kpo.getSyntetykaanalityka().equals("analityka")) {
                        Msg.msg("w", "Konto przyporządkowane z poziomu analityki!");
                    } else {
                        PlanKontFKBean.naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", wybranyuklad);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            if (wynikdodaniakonta == 0) {
                PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAOfk);
//                if (czyoddacdowzorca == true) {
//                    wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
//                } else {
//                    //  MEGAZOR
//                    wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//                }
                noweKonto = new Konto();
                //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
            }
            planKontCompleteView.init();
            kontoConv.init();
        } else {
            Msg.msg("e", "Niewłaściwy numer konta lub próba zmiany konta słownikowego. Nie dodano nowej analityki");
        }
        }
    
    public void dodajSlownikWpis() {
        String nrmacierzystego = PlanKontFKBean.modyfikujnrslownik(elementslownika_numerkonta);
        Konto kontomacierzyste = PlanKontFKBean.wyszukajmacierzyste(wpisView, kontoDAOfk, nrmacierzystego);
        List<Konto> potomne = PlanKontFKBean.pobierzpotomne(kontomacierzyste, kontoDAOfk);
        Collections.sort(potomne, new Kontocomparator());
        if (potomne != null && potomne.size() > 0) {
            Konto slownik = potomne.get(0);
            String nazwaslownika = slownik.getNazwapelna();
            if (nazwaslownika.equals("Słownik miejsca przychodów")) {
                System.out.println("");
                MiejscePrzychodow mp = new MiejscePrzychodow();
                mp.setOpismiejsca(elementslownika_nazwapelna);
                mp.setOpisskrocony(elementslownika_nazwaskrocona);
                mp.setAktywny(true);
                mp.uzupelnij(wpisView.getPodatnikObiekt(), pobierzkolejnynumerMP());
                mp.setRok(wpisView.getRokWpisu());
                miejscePrzychodowDAO.dodaj(mp);
                if (kontomacierzyste != null) {
                    int wynikdodaniakonta = 0;
                    wynikdodaniakonta = PlanKontFKBean.aktualizujslownikMiejscaPrzychodow(wykazkont, miejscePrzychodowDAO, mp, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        if (czyoddacdowzorca == true) {
                            wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
                        noweKonto = new Konto();
                        //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                        Msg.msg("i", "Dodaje konto słownikowe", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Konto słownikowe o takim numerze juz istnieje!", "formX:messages");
                    }
                    elementslownika_nazwapelna = null;
                    elementslownika_nazwaskrocona = null;
                    elementslownika_numerkonta = null;
                    planKontCompleteView.init();
                    kontoConv.init();
                } else {
                    Msg.msg("e", "Niewłaściwy numer konta lub próba zmiany konta słownikowego. Nie dodano nowej analityki");
                }
            }
        }
    }
    
     private String pobierzkolejnynumerMP() {
        List miejsca = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        int max = 0;
        for (Iterator<MiejscePrzychodow> it = miejsca.iterator(); it.hasNext();) {
            MiejscePrzychodow m = it.next();
            int nr = Integer.parseInt(m.getNrkonta());
            if (max < nr) {
                max = nr;
            }
        }
        String zwrot = String.valueOf(max+1);
        return zwrot;
    }
    /*
    KONTR = 1
    MIEJS = 2
    SAMOC = 3
    MIESI = 4
    DELEK = 5
    DELEZ = 6
    MIEJP = 7
    */
    public void dodajslownik() {
        Konto kontomacierzyste = selectednodekonto;
        List<Konto> kontapodpiete = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontomacierzyste.getPelnynumer());
        if (kontapodpiete.size() > 0) {
            Msg.msg("e", "Konto już ma podpiętą zwyczajną analitykę, nie można dodać słownika");
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                //oznaczenie okntr - znacdzy ze dodajemy slownik z kontrahentami
                if (noweKonto.getNrkonta().equals("kontr")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikKontrahenci(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 1);
                        Msg.msg("i", "Dodaje słownik kontrahentów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika kontrahentów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaKontrahenci(wykazkont, kontomacierzyste, kontoDAOfk, kliencifkDAO, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika kontrahentów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika kontrahentów");
                    }
                } else if (noweKonto.getNrkonta().equals("miejs")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiejscaKosztow(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 2);
                        Msg.msg("i", "Dodaje słownik miejsc powstawania kosztów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miejsc powstawania kosztów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiejscaKosztow(wykazkont, kontomacierzyste, kontoDAOfk, miejsceKosztowDAO, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania kosztów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania kosztów");
                    }
                } else if (noweKonto.getNrkonta().equals("samoc")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikPojazdyiMaszyny(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 3);
                        Msg.msg("i", "Dodaje słownik pojazdy i maszyny", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika pojazdy i maszyny!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaPojazdy(wykazkont, kontomacierzyste, kontoDAOfk, pojazdyDAO, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania kosztów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania kosztów");
                    }
                } else if (noweKonto.getNrkonta().equals("miesi")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiesiace(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 4);
                        Msg.msg("i", "Dodaje słownik miesięcy", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miesięcy!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiesiace(wykazkont, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miesiące");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miesięcy");
                    }
                } else if (noweKonto.getNrkonta().equals("deleK")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikDelegacjeKrajowe(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 5);
                        Msg.msg("i", "Dodaje słownik delegacji krajowych", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownik delegacji krajowych!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaDelegacje(wykazkont, kontomacierzyste, kontoDAOfk, delegacjaDAO, wpisView, false, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika delegacji krajowych");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika delegacji krajowych");
                    }
                } else if (noweKonto.getNrkonta().equals("deleZ")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikDelegacjeZagraniczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 6);
                        Msg.msg("i", "Dodaje słownik delegacji zagranicznych", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownik delegacji zagranicznych!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaDelegacje(wykazkont, kontomacierzyste, kontoDAOfk, delegacjaDAO, wpisView, true, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika delegacji zagranicznych");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika delegacji zagranicznych");
                    }
                } else if (noweKonto.getNrkonta().equals("miejp")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiejscaPrzychodow(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 7);
                        Msg.msg("i", "Dodaje słownik miejsc powstawania przychodów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miejsc powstawania przychodów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiejscaPrzychodow(wykazkont, kontomacierzyste, kontoDAOfk, miejscePrzychodowDAO, wpisView, kontopozycjaZapisDAO, wybranyuklad);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania przychodów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania przychodów");
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    public void implementacjaKontWzorcowych() {
        if (!wykazkontwzor.isEmpty()) {
            for (Konto p : wykazkontwzor) {
                if (wpisView.isParamCzworkiPiatki() == false && p.getPelnynumer().startsWith("5")) {
                    System.out.println("Nie implementuje konta 5");
                } else {
                    p.setMapotomkow(false);
                    p.setBlokada(false);
                    p.setRok(wpisView.getRokWpisu());
                    p.setPodatnik(wpisView.getPodatnikObiekt());
                    try {
                        kontoDAOfk.dodaj(p);
                    } catch (PersistenceException e) {
                        Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + p.getPelnynumer());
                    } catch (Exception ef) {
                        Msg.msg("e", "Wystąpił błąd podczas dodawania konta. Nie dodano: " + p.getPelnynumer());
                    }
                }
            }
            List<Konto> wykazkonttmp = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            //a teraz trzeba pozmieniac id macierzystych bo inaczej sie nie odnajda
            if (wykazkonttmp != null) {
                for (Konto p : wykazkonttmp) {
                    if (!p.getMacierzyste().equals("0")) {
                        Konto macierzyste = kontoDAOfk.findKonto(p.getMacierzyste(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        p.setMacierzysty(macierzyste.getId());
                        p.setKontomacierzyste(macierzyste);
                    }
                }
                kontoDAOfk.editList(wykazkonttmp);
            }
            porzadkowanieKontPodatnika();
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            RequestContext.getCurrentInstance().update("form:dataList");
            Msg.msg("Zakonczono z sukcesem implementacje kont wzorcowych u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }

    public void implementacjaJednegoKontaWzorcowego() {
        if (selectednodekontowzorcowy != null) {
            try {
                List<Podatnik> listapodatnikowfk = podatnikDAO.findPodatnikFK();
                for (Podatnik p : listapodatnikowfk) {
                    Konto kontowzorcowe = selectednodekontowzorcowy;
                    try {
                        kontowzorcowe.setPodatnik(p);
                        Konto macierzyste = kontoDAOfk.findKonto(kontowzorcowe.getMacierzyste(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        if (!kontowzorcowe.getMacierzyste().equals("0")) {
                            kontowzorcowe.setMacierzysty(macierzyste.getId());
                            kontowzorcowe.setKontomacierzyste(macierzyste);
                            macierzyste.setMapotomkow(true);
                            macierzyste.setBlokada(true);
                            kontoDAOfk.edit(macierzyste);
                        } else {
                            kontowzorcowe.setMapotomkow(false);
                            kontowzorcowe.setBlokada(false);
                        }
                        kontoDAOfk.dodaj(kontowzorcowe);
                    } catch (RollbackException e) {

                    } catch (PersistenceException x) {
                        Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + kontowzorcowe.getPelnynumer());
                    } catch (Exception ef) {
                    }
                }
                wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego u wszystkich klientów FK");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }

    public void implementacjaJednegoKontaWzorcowegoZAnalitykom() {
        if (selectednodekonto != null) {
            try {
                List<Podatnik> listapodatnikowfk = podatnikDAO.findPodatnikFK();
                for (Podatnik p : listapodatnikowfk) {
                    Konto konto = selectednodekonto;
                    dodajpojedynczekoto(konto, wpisView.getPodatnikObiekt());
                    List<Konto> potomne = kontoDAOfk.findKontaPotomnePodatnik(null, wpisView.getRokWpisu(), konto.getPelnynumer());
                    for (Konto r : potomne) {
                        dodajpojedynczekoto(r, wpisView.getPodatnikObiekt());
                    }
                }
                wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                //root = rootInit(wykazkont);
                //rozwinwszystkie(root);
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego z analityką u wszystkich klientów FK");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }

    public void implementacjaJednegoKontadoWzorcowychZAnalitykom() {
        if (selectednodekonto != null) {
            try {
                Konto konto = selectednodekonto;
                dodajpojedynczekoto(konto, null);
                List<Konto> potomne = kontoDAOfk.findKontaPotomne(wpisView, konto.getPelnynumer(), konto.getBilansowewynikowe());
                for (Konto r : potomne) {
                    dodajpojedynczekotoWzorcowy(r, konto.getPelnynumer());
                }
                wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(null, wpisView.getRokWpisuSt());
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta z analityką do Wzorcowych");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do zaimplementowania jest pusta.");
        }
    }

    private void dodajpojedynczekoto(Konto konto, Podatnik podatnik) {
        konto.setPodatnik(podatnik);
        if (!konto.getMacierzyste().equals("0")) {
            Konto macierzyste = kontoDAOfk.findKonto(konto.getMacierzyste(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            konto.setMacierzysty(macierzyste.getId());
            konto.setKontomacierzyste(macierzyste);
            macierzyste.setMapotomkow(true);
            macierzyste.setBlokada(true);
            kontoDAOfk.edit(macierzyste);
        } else {
            konto.setMapotomkow(false);
            konto.setBlokada(false);
        }
        try {
            kontoDAOfk.dodaj(konto);
        } catch (RollbackException e) {

        } catch (PersistenceException x) {
            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
        } catch (Exception ef) {
        }
    }

    private void dodajpojedynczekotoWzorcowy(Konto konto, String pelnynumer) {
        konto.setPodatnik(null);
        if (!konto.getMacierzyste().equals("0")) {
            Konto macierzyste = kontoDAOfk.findKonto(pelnynumer, null, wpisView.getRokWpisu());
            konto.setMacierzysty(macierzyste.getId());
            konto.setKontomacierzyste(macierzyste);
            macierzyste.setMapotomkow(true);
            macierzyste.setBlokada(true);
            kontoDAOfk.edit(macierzyste);
        } else {
            konto.setMapotomkow(false);
            konto.setBlokada(false);
        }
        try {
            kontoDAOfk.dodaj(konto);
        } catch (RollbackException e) {

        } catch (PersistenceException x) {
            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
        } catch (Exception ef) {
        }
    }

    public void usunieciewszystkichKontPodatnika() {
        if (!wykazkont.isEmpty()) {
            List<UkladBR> uklady = ukladBRDAO.findukladBRPodatnikRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            for (UkladBR ukladpodatnika : uklady) {
                wyczyscKonta("wynikowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "wynikowe");
                wyczyscKonta("bilansowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "bilansowe");
            }
            wierszBODAO.deletePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<Rodzajedok> rodzajeDokPodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
            if (!rodzajeDokPodatnika.isEmpty()) {
                for (Rodzajedok r : rodzajeDokPodatnika) {
                    r.setKontoRZiS(null);
                    r.setKontorozrachunkowe(null);
                    r.setKontovat(null);
                }
                rodzajedokDAO.editList(rodzajeDokPodatnika);
            }
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto p = (Konto) it.next();
                if (!p.getPodatnik().equals(null)) {
                    try {
                        kontoDAOfk.destroy(p);
                        it.remove();
                    } catch (Exception e) {
                        E.e(e);
                        Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Na nieusuniętych kontach istnieją zapisy. Przerywam wykonywanie funkcji");
                    }
                } else {
                    Msg.msg("e", "Próbujesz usunąć konta wzorcowe. Przerywam działanie.");
                    return;
                }
            }
            wykazkont = new ArrayList<>();
            RequestContext.getCurrentInstance().update("form_dialog_plankont");
            Msg.msg("Zakonczono z sukcesem usuwanie kont u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }

    private void wyczyscKonta(String rb) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (Konto p : listakont) {
                p.setKontopozycjaID(null);
            }
            if (listakont != null) {
                kontoDAOfk.editList(listakont);
            }
        } else {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (Konto p : listakont) {
                p.setKontopozycjaID(null);
            }
            if (listakont != null) {
                kontoDAOfk.editList(listakont);
            }
        }
    }

    public void usunieciewszystkichKontWzorcowy() {
        if (!wykazkontwzor.isEmpty()) {
            List<UkladBR> uklady = ukladBRDAO.findukladBRWzorcowyRok(wpisView.getRokWpisuSt());
            for (UkladBR u : uklady) {
                wyczyscKonta("wynikowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(u, "wynikowe");
                wyczyscKonta("bilansowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(u, "bilansowe");
            }
            for (Konto p : wykazkontwzor) {
                try {
                    kontoDAOfk.destroy(p);
                } catch (Exception e) {
                    E.e(e);
                    Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Na nieusuniętych kontach istnieją zapisy. Przerywam wykonywanie funkcji");
                }
            }
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(null, wpisView.getRokWpisuSt());
            RequestContext.getCurrentInstance().update("formwzorcowy");
            Msg.msg("Zakonczono z sukcesem usuwanie kont wzorocwych");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }

    public void porzadkowanieKontPodatnika() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        //resetuj kolumne macierzyste
        kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(wybranyuklad, "wynikowe");
        kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(wybranyuklad, "bilansowe");
        resetujpozycjebiezace();
        //tutaj nanosi czy ma potomkow
        KontaFKBean.ustawCzyMaPotomkow(wykazkont, kontoDAOfk, wpisView, kontopozycjaZapisDAO, wybranyuklad);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (Konto p : wykazkont) {
            KontopozycjaZapis kpo = PlanKontFKBean.naniesprzyporzadkowanie(p, wpisView, kontoDAOfk, kontopozycjaZapisDAO, wybranyuklad);
            if (p.isMapotomkow() == true && kpo != null && !kpo.getSyntetykaanalityka().equals("analityka")) {
                if (p.getBilansowewynikowe().equals("wynikowe")) {
                    if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p, p.getKontopozycjaID(), kontoDAOfk, wpisView.getPodatnikObiekt(), "wnma", wpisView.getRokWpisu());
                    } else {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(p.getPelnynumer(), p.getKontopozycjaID(), kontoDAOfk, wpisView.getPodatnikObiekt(), "wynik", wpisView.getRokWpisu());
                    }
                } else if (p.getZwyklerozrachszczegolne().equals("rozrachunkowe") || p.getZwyklerozrachszczegolne().equals("vat") || p.getZwyklerozrachszczegolne().equals("szczególne")) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p, p.getKontopozycjaID(), kontoDAOfk, wpisView.getPodatnikObiekt(), "wnma", wpisView.getRokWpisu());
                } else {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(p.getPelnynumer(), p.getKontopozycjaID(), kontoDAOfk, wpisView.getPodatnikObiekt(), "bilans", wpisView.getRokWpisu());
                }
            }
        }
        kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(wybranyuklad, "wynikowe");
        kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(wybranyuklad, "bilansowe");
        List<KontopozycjaZapis> nowepozycje = new ArrayList<>();
        for (Konto p : wykazkont) {
            try {
                nowepozycje.add(new KontopozycjaZapis(p.getKontopozycjaID()));
            } catch (Exception e) {
                E.e(e);
            }
        }
        kontopozycjaZapisDAO.editList(nowepozycje);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        listaukladow = ukladBRDAO.findPodatnikRok(wpisView);
        wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
        PozycjaRZiSFKBean.zmianaukladu("bilansowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        PozycjaRZiSFKBean.zmianaukladu("wynikowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, kontoDAO, wpisView);
        Collections.sort(wykazkont, new Kontocomparator());
        wykazkontlazy = new LazyKontoDataModel(wykazkont);
        Msg.msg("Zakończono porządkowanie kont");
    }
    
    private void resetujpozycjebiezace() {
        for (Konto p : wykazkont) {
            p.setKontopozycjaID(null);
        }
    }

    public void porzadkowanieKontWzorcowych() {
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        //resetuj kolumne macierzyste
        KontaFKBean.ustawCzyMaPotomkowWzorcowy(wykazkontwzor, kontoDAOfk, null, wpisView, kontopozycjaZapisDAO, wybranyuklad);
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
    }

    public void usunZsubkontami(String klientWzor) {
        Konto kontoDoUsuniecia = selectednodekonto != null ? selectednodekonto : selectednodekontowzorcowy;
        List<Rodzajedok> rodzajedokumentowpodatnika = null;
        if (klientWzor.equals("K")) {
            rodzajedokumentowpodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
        }
        List<Konto> zwrot = new ArrayList<>();
        listapotomnych(kontoDoUsuniecia, zwrot);
        int maxlevel = pobierzmaxlevel(zwrot);
        for (int i = maxlevel; i >= 0 ; i--) {
            List<Konto> kontazlevelu = pobierzlevel(zwrot,i);
            for (Konto r : kontazlevelu) {
                usunpojedynczekonto(r, klientWzor, rodzajedokumentowpodatnika);
            }
        }
        usunpojedynczekonto(kontoDoUsuniecia, klientWzor, rodzajedokumentowpodatnika);
        if (kontoDoUsuniecia.isMapotomkow() == true) {
            if (!kontoDoUsuniecia.getMacierzyste().equals("0")) {
                boolean sadzieci = PlanKontFKBean.sprawdzczymacierzystymapotomne(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDoUsuniecia, kontoDAOfk);
                oznaczbraksiostr(sadzieci, kontoDoUsuniecia, klientWzor);
            }
        }
    }
    
    private void usunpojedynczekonto(Konto kontoDoUsuniecia, String klientWzor, List<Rodzajedok> rodzajedokumentowpodatnika) {
         try {
                usunpozycje(kontoDoUsuniecia);
                usunuzyciewdokumencie(kontoDoUsuniecia, rodzajedokumentowpodatnika);
                usunkontozbazy(kontoDoUsuniecia, klientWzor);
            } catch (Exception e) {
                E.e(e);
            }
    }
    
    private List<Konto> pobierzlevel(List<Konto> zwrot, int level) {
        List<Konto> z = new ArrayList<>();
        for (Konto p : zwrot) {
            if (p.getLevel() == level) {
                z.add(p);
            }
        }
        return z;
    }
    
    private void listapotomnych(Konto macierzyste, List<Konto> zwrot) {
        List<Konto> potomne = PlanKontFKBean.pobierzpotomne(macierzyste, kontoDAOfk);
        for (Konto p : potomne) {
            zwrot.add(p);
            listapotomnych(p, zwrot);
        }
    }
    
    private int pobierzmaxlevel(List<Konto> lista) {
        int maxlevel = 0;
        for (Konto p : lista) {
            maxlevel = maxlevel < p.getLevel() ? p.getLevel() : maxlevel;
        }
        return maxlevel;
    }

    private void usunrekurencja(String klientWzor, Konto kontoDoUsuniecia, List<Rodzajedok> dokumenty) {
        if (kontoDoUsuniecia.isMapotomkow() == false) {
            try {
                usunpozycje(kontoDoUsuniecia);
                usunuzyciewdokumencie(kontoDoUsuniecia, dokumenty);
                usunkontozbazy(kontoDoUsuniecia, klientWzor);
            } catch (Exception e) {
                E.e(e);
            }
        } else {
            try {
                usunpozycje(kontoDoUsuniecia);
                usunuzyciewdokumencie(kontoDoUsuniecia, dokumenty);
                usunkontozbazy(kontoDoUsuniecia, klientWzor);
                List<Konto> dzieci = PlanKontFKBean.pobierzpotomne(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDoUsuniecia, kontoDAOfk);
                if (dzieci != null && dzieci.size() > 0) {
                    for (Konto p : dzieci) {
                        usunrekurencja(klientWzor, p, dokumenty);
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    

    private void usunuzyciewdokumencie(Konto kontoDoUsuniecia, List<Rodzajedok> dokumenty) {
        try {
            for (Rodzajedok p : dokumenty) {
                if (p.getKontoRZiS() != null && p.getKontoRZiS().equals(kontoDoUsuniecia)) {
                    p.setKontoRZiS(null);
                    rodzajedokDAO.edit(p);
                }
                if (p.getKontorozrachunkowe() != null && p.getKontorozrachunkowe().equals(kontoDoUsuniecia)) {
                    p.setKontorozrachunkowe(null);
                    rodzajedokDAO.edit(p);
                }
                if (p.getKontovat() != null && p.getKontovat().equals(kontoDoUsuniecia)) {
                    p.setKontovat(null);
                    rodzajedokDAO.edit(p);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void usunpozycje(Konto kontoDoUsuniecia) {
        try {
            KontopozycjaZapis p = kontopozycjaZapisDAO.findByKonto(kontoDoUsuniecia, wybranyuklad);
            if (p != null) {
                kontopozycjaZapisDAO.destroy(p);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void usunkontozbazy(Konto kontoDoUsuniecia, String klientWzor) {
        kontoDoUsuniecia.setKontomacierzyste(null);
        kontoDAOfk.edit(kontoDoUsuniecia);
        kontoDAOfk.destroy(kontoDoUsuniecia);
        if (klientWzor.equals("W")) {
            wykazkontwzor.remove(kontoDoUsuniecia);
        } else {
            wykazkont.remove(kontoDoUsuniecia);
        }
    }

    private void oznaczbraksiostr(boolean sadzieci, Konto kontoDoUsuniecia, String klientWzor) {
        Konto kontomacierzyste = kontoDoUsuniecia.getKontomacierzyste();
        List<Konto> siostry = sprawdzczysasiostry(klientWzor, kontomacierzyste);
        if (siostry.size() < 1) {
            //jak nie ma wiecej dzieci podpietych pod konto macierzyse usuwanego to zaznaczamy to na koncie macierzystym;
            odznaczmacierzyste(sadzieci, kontomacierzyste, kontoDoUsuniecia);
        }
    }

    private void usunslownikowe(Konto kontoDoUsuniecia) {
        int wynik = PlanKontFKBean.usunelementyslownika(kontoDoUsuniecia.getMacierzyste(), kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), wykazkont, kontopozycjaZapisDAO, wybranyuklad);
        if (wynik == 0) {
            Konto kontomacierzyste = kontoDoUsuniecia.getKontomacierzyste();
            kontomacierzyste.setBlokada(false);
            kontomacierzyste.setMapotomkow(false);
            kontomacierzyste.setIdslownika(0);
            kontoDAOfk.edit(kontomacierzyste);
            Msg.msg("Usunięto elementy słownika");
        } else {
            Msg.msg("e", "Wystapił błąd i nie usunięto elementów słownika");
        }
    }

    public void usun(String klientWzor) {
        Konto kontoDoUsuniecia = selectednodekonto != null ? selectednodekonto : selectednodekontowzorcowy;
        if (kontoDoUsuniecia != null) {
            if (kontoDoUsuniecia.isBlokada() == true) {
                Msg.msg("e", "Konto zablokowane. Na koncie istnieją zapisy. Nie można go usunąć");
            } else if (kontoDoUsuniecia.isMapotomkow() == true && !kontoDoUsuniecia.getNrkonta().equals("0")) {
                Msg.msg("e", "Konto ma analitykę, nie można go usunąć.");
            } else {
                try {
                    usunpozycje(kontoDoUsuniecia);
                    usunkontozbazy(kontoDoUsuniecia, klientWzor);
                    if (kontoDoUsuniecia.getNrkonta().equals("0")) {
                        usunslownikowe(kontoDoUsuniecia);
                    } else {
                        boolean sadzieci = PlanKontFKBean.sprawdzczymacierzystymapotomne(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontoDoUsuniecia, kontoDAOfk);
                        oznaczbraksiostr(sadzieci, kontoDoUsuniecia, klientWzor);
                    }
                    Msg.msg("i", "Usuwam konto");
                } catch (Exception e) {
                    E.e(e);
                    Msg.msg("e", "Istnieją zapisy na koncie lub konto użyte jest jako definicja dokumentu, nie można go usunąć.");
                }
            }
        } else {
            Msg.msg("e", "Nie wybrano konta");
        }
    }

    private List<Konto> sprawdzczysasiostry(String klientWzor, Konto kontomacierzyste) {
        if (klientWzor.equals("W")) {
            return kontoDAOfk.findKontaPotomnePodatnik(null, wpisView.getRokWpisu(), kontomacierzyste.getPelnynumer());
        } else {
            return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontomacierzyste.getPelnynumer());
        }
    }

    private void odznaczmacierzyste(boolean sadzieci, Konto kontomacierzyste, Konto kontoDoUsuniecia) {
        if (sadzieci == false && kontoDoUsuniecia.getKontomacierzyste()!=null) {
            kontomacierzyste.setBlokada(false);
            kontomacierzyste.setMapotomkow(false);
            kontoDAOfk.edit(kontomacierzyste);
        }
    }

    
    public void obslugaBlokadyKontaWzorcowy() {
        try {
            if (selectednodekontowzorcowy != null) {
                if (selectednodekontowzorcowy.isBlokada() == false) {
                    selectednodekontowzorcowy.setBlokada(true);
                    kontoDAOfk.edit(selectednodekontowzorcowy);
                    Msg.msg("w", "Zabezpieczono konto przed edycją.");
                } else if (selectednodekontowzorcowy.isBlokada() == true) {
                    selectednodekontowzorcowy.setBlokada(false);
                    kontoDAOfk.edit(selectednodekontowzorcowy);
                    Msg.msg("w", "Odblokowano edycję konta.");
                }
            } else {
                Msg.msg("f", "Nie wybrano konta", "formX:messages");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Problem ze zdjęciem blokady");
        }
    }
    public void obslugaBlokadyKonta() {
        try {
            if (selectednodekonto != null) {
                if (selectednodekonto.isBlokada() == false) {
                    selectednodekonto.setBlokada(true);
                    kontoDAOfk.edit(selectednodekonto);
                    Msg.msg("w", "Zabezpieczono konto przed edycją.");
                } else if (selectednodekonto.isBlokada() == true) {
                    selectednodekonto.setBlokada(false);
                    kontoDAOfk.edit(selectednodekonto);
                    Msg.msg("w", "Odblokowano edycję konta.");
                }
            } else {
                Msg.msg("f", "Nie wybrano konta", "formX:messages");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Problem ze zdjęciem blokady");
        }
    }

    public void obslugaBlokadyKontaWszystkie() {
        if (wykazkont != null) {
            for (Konto p : wykazkont) {
                Konto konto = p;
                konto.setBlokada(false);
            }
        kontoDAOfk.edit(wykazkont);
        Msg.msg("w", "Odblokowano edycję kont");
        }
    }

    public void zmiananazwykonta() {
        try {
            List<Konto> kontapotomne = kontoDAOfk.findKontaWszystkiePotomnePodatnik(wpisView, selectednodekonto);
            for (Konto p : kontapotomne) {
                if (p != null) {
                    p.setZwyklerozrachszczegolne(selectednodekonto.getZwyklerozrachszczegolne());
                    p.setBilansowewynikowe(selectednodekonto.getBilansowewynikowe());
                    try {
                        Konto r = wykazkont.get(wykazkont.indexOf(p));
                        if (r != null) {
                            r.setZwyklerozrachszczegolne(selectednodekonto.getZwyklerozrachszczegolne());
                            r.setBilansowewynikowe(selectednodekonto.getBilansowewynikowe());
                        }
                        if (usunprzyporzadkowanie) {
                            r.setKontopozycjaID(null);
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                    if (usunprzyporzadkowanie) {
                        KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(p, wybranyuklad);
                        if (kp != null) {
                            kontopozycjaZapisDAO.destroy(kp);
                        }
                        p.setKontopozycjaID(null);
                    }
                    kontoDAOfk.edit(p);
                }
            }
            if (usunprzyporzadkowanie) {
                KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(selectednodekonto, wybranyuklad);
                if (kp != null) {
                    kontopozycjaZapisDAO.destroy(kp);
                }
                selectednodekonto.setKontopozycjaID(null);
            }
            kontoDAOfk.edit(selectednodekonto);
            usunprzyporzadkowanie = false;
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void zmiananazwykontawzorcowy() {
        try {
            List<Konto> kontapotomne = kontoDAOfk.findKontaWszystkiePotomneWzorcowy(wpisView, selectednodekontowzorcowy);
            for (Konto p : kontapotomne) {
                if (p != null) {
                    p.setZwyklerozrachszczegolne(selectednodekontowzorcowy.getZwyklerozrachszczegolne());
                    p.setBilansowewynikowe(selectednodekontowzorcowy.getBilansowewynikowe());
                    try {
                        Konto r = wykazkontwzor.get(wykazkontwzor.indexOf(p));
                        if (r != null) {
                            r.setZwyklerozrachszczegolne(selectednodekontowzorcowy.getZwyklerozrachszczegolne());
                            r.setBilansowewynikowe(selectednodekontowzorcowy.getBilansowewynikowe());
                        }
                        if (usunprzyporzadkowanie) {
                            r.setKontopozycjaID(null);
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                    if (usunprzyporzadkowanie) {
                        KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(p, wybranyukladwzorcowy);
                        if (kp != null) {
                            kontopozycjaZapisDAO.destroy(kp);
                        }
                        p.setKontopozycjaID(null);
                    }
                    kontoDAOfk.edit(p);
                }
            }
            if (usunprzyporzadkowanie) {
                KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(selectednodekontowzorcowy, wybranyukladwzorcowy);
                if (kp != null) {
                    kontopozycjaZapisDAO.destroy(kp);
                }
                selectednodekontowzorcowy.setKontopozycjaID(null);
            }
            kontoDAOfk.edit(selectednodekontowzorcowy);
            usunprzyporzadkowanie = false;
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void selrow() {
        Map<String,String> par_map = new HashMap<String,String>(); par_map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id=par_map.get("form_dialog_plankont:dataList_selection");
        wybranapozycja_wiersz = "row_"+id;
        Msg.msg("i", "Wybrano: " + selectednodekonto.getPelnynumer() + " " + selectednodekonto.getNazwapelna());
    }

    public void selrowwzorcowy() {
        Msg.msg("i", "Wybrano: " + selectednodekontowzorcowy.getPelnynumer() + " " + selectednodekontowzorcowy.getNazwapelna());
    }

    public void zachowajZmianyWKoncieWzorcowy(Konto konto) {
        kontoDAOfk.edit(konto);
        List<Konto> kontapotomne = kontoDAOfk.findKontaWszystkiePotomneWzorcowy(wpisView, konto);
        for (Konto p : kontapotomne) {
            p.setZwyklerozrachszczegolne(konto.getZwyklerozrachszczegolne());
            kontoDAOfk.edit(p);
        }
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
    }
    /*
    KONTR = 1
    MIEJS = 2
    SAMOC = 3
    MIESI = 4
    DELEK = 5
    DELEZ = 6
    MIEJP = 7
    */
    public void porzadkujSlowniki() {
        List<Konto> kontaslownikowe = kontoDAOfk.findWszystkieKontaSlownikowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        if (kontaslownikowe != null) {
            for (Konto kp : kontaslownikowe) {
                Konto k = kp.getKontomacierzyste();
                switch (kp.getNazwapelna()) {
                    case "Słownik kontrahenci":
                        k.setIdslownika(Slownik.KLIENCI);
                        break;
                    case "Słownik miejsca kosztów":
                        k.setIdslownika(Slownik.KOSZTY);
                        break;
                    case "Słownik pojazdy i maszyny":
                        k.setIdslownika(Slownik.POJAZDY);
                        break;
                    case "Słownik miesiące":
                        k.setIdslownika(Slownik.MCE);
                        break;
                    case "Słownik delegacji krajowych":
                        k.setIdslownika(Slownik.DELKRAJ);
                        break;
                    case "Słownik delegacji zagranicznych":
                        k.setIdslownika(Slownik.DELZAGR);
                        break;
                    case "Słownik miejsca przychodów":
                        k.setIdslownika(Slownik.PRZYCHODY);
                        break;
                }
            }
            kontoDAOfk.editList(kontaslownikowe);
        }
        List<Kliencifk> obecniprzyporzadkowaniklienci = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        boolean sakliencifk = obecniprzyporzadkowaniklienci != null && !obecniprzyporzadkowaniklienci.isEmpty();
        if (sakliencifk) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.KLIENCI);
            for (Kliencifk p : obecniprzyporzadkowaniklienci) {
                try {
                    PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, p.getNazwa(), p.getNip(), Integer.parseInt(p.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.KLIENCI, wybranyuklad);
                } catch (Exception e) {
                    E.e(e);

                }
            }
        }
        List<MiejsceKosztow> miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        boolean samiejscakosztow = miejscakosztow != null && !miejscakosztow.isEmpty();
        if (samiejscakosztow) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.KOSZTY);
            for (MiejsceKosztow r : miejscakosztow) {
                try {
                    PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, r.getOpismiejsca(), r.getOpisskrocony(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.KOSZTY, wybranyuklad);
                } catch (Exception e1) {

                }
            }
        }
        List<MiejscePrzychodow> miejscaprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        boolean samiejscaprzychodow = miejscaprzychodow != null && !miejscaprzychodow.isEmpty();
        if (samiejscaprzychodow) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.PRZYCHODY);
            for (MiejscePrzychodow r : miejscaprzychodow) {
                try {
                    PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, r.getOpismiejsca(), r.getOpisskrocony(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.PRZYCHODY, wybranyuklad);
                } catch (Exception e1) {

                }
            }
        }
        List<Pojazdy> pojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        boolean sapojazdy = pojazdy != null && !pojazdy.isEmpty();
        if (sapojazdy) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.POJAZDY);
            for (Pojazdy r : pojazdy) {
                try {
                    PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, r.getNrrejestracyjny(), r.getNazwapojazdu(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.POJAZDY, wybranyuklad);
                } catch (Exception e1) {

                }
            }
        }
        List<Delegacja> delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView, false);
        boolean sadelegacje = delegacjekrajowe != null && !delegacjekrajowe.isEmpty();
        if (sadelegacje) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.DELKRAJ);
            for (Delegacja r : delegacjekrajowe) {
                try {
                    if (r.getOpisdlugi().equals("113/05/2015/k")) {
                        System.out.println("k");
                    }
                    PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, r.getOpisdlugi(), r.getOpiskrotki(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.DELKRAJ, wybranyuklad);
                } catch (Exception e1) {

                }
            }
        }
        List<Delegacja> delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView, true);
        boolean sadelegacjezagr = delegacjezagraniczne != null && !delegacjezagraniczne.isEmpty();
        if (sadelegacjezagr) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.DELZAGR);
            for (Delegacja r : delegacjezagraniczne) {
                try {
                    PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, r.getOpisdlugi(), r.getOpiskrotki(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.DELZAGR, wybranyuklad);
                } catch (Exception e1) {

                }
            }
        }
        List<String> listamiesiace = Mce.getMcenazwaListSlownik();
        int nrkonta = 1;
        for (String l : listamiesiace) {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), Slownik.MCE);
            PlanKontFKBean.porzadkujslownik(kontamacierzysteZeSlownikiem, wykazkont, l, l, nrkonta, kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk, Slownik.MCE, wybranyuklad);
            nrkonta++;
        }
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Msg.msg("Zakonczono aktualizowanie słowników");
    }

    public void oznaczkontoJakoKosztowe() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1(selectednodekonto, kontoDAOfk, true, wpisView);
            planBezSlownikowychSyntetyczne();
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void oznaczkontoJakoKosztoweWzorcowy() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1Wzorcowy(selectednodekonto, kontoDAOfk, true, wpisView);
            wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void oznaczkontoJakoPrzychodowe() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1(selectednodekonto, kontoDAOfk, false, wpisView);
            planBezSlownikowychSyntetyczne();
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void oznaczkontoJakoPrzychodoweWzorcowy() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1Wzorcowy(selectednodekonto, kontoDAOfk, false, wpisView);
            wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void drukujPlanKont(String parametr) {
        switch (parametr) {
            case "all":
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "wynikowe":
                wykazkont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "bilansowe":
                wykazkont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa0":
                wykazkont = kontoDAOfk.findKontaGrupa0(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa1":
                wykazkont = kontoDAOfk.findKontaGrupa1(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa2":
                wykazkont = kontoDAOfk.findKontaGrupa2(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa3":
                wykazkont = kontoDAOfk.findKontaGrupa3(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa4":
                wykazkont = kontoDAOfk.findKontaGrupa4(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa5":
                wykazkont = kontoDAOfk.findKontaGrupa5(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa6":
                wykazkont = kontoDAOfk.findKontaGrupa6(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa7":
                wykazkont = kontoDAOfk.findKontaGrupa7(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa8":
                wykazkont = kontoDAOfk.findKontaGrupa8(wpisView);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "tłumaczenie":
                usunslownikowe();
                PdfPlanKont.drukujPlanKontTłumaczenie(wykazkont, wpisView);
                break;
        }
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
    }

    private void usunslownikowe() {
        if (bezslownikowych || tylkosyntetyka) {
            for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                Konto p = it.next();
                if (bezslownikowych && p.isSlownikowe() && !tylkosyntetyka) {
                    it.remove();
                }
                if (tylkosyntetyka && !p.getMacierzyste().equals("0") && !bezslownikowych) {
                    it.remove();
                }
                if (bezslownikowych && tylkosyntetyka) {
                    if (p.isSlownikowe() || !p.getMacierzyste().equals("0")) {
                        it.remove();
                    }
                }
            }
        }
    }

    private String opracujstylwierszatabeli() {
        String zwrot = "#{loop.bilansowewynikowe eq 'bilansowe' ? (loop.level eq 0 ? 'row1' : loop.level eq 1 ? 'row2' : loop.level eq 2 ? 'row3' : 'row4') : (loop.level eq 0 ? 'row1w' : loop.level eq 1 ? 'row2w' : loop.level eq 2 ? 'row3w' : 'row4w')}";
        if (kontadowyswietlenia.equals("wynikowe")) {
            zwrot = "#{loop.przychod0koszt1 eq 0 ? 'rowb_szczegolne' : 'rowb_zwykle'}";
        }
        if (kontadowyswietlenia.equals("bilansowe")) {
            zwrot = "#{loop.zwyklerozrachszczegolne eq 'zwykłe ? 'rowb_zwykle' : loop.zwyklerozrachszczegolne eq 'szczególne' ? 'rowb_szczegolne' : 'rowb_rozrachunkowe'}";
        }
        return zwrot;
    }
    
    public void usunslownik() {
        try {
            if (selectednodekonto.isSlownikowe()) {
                List<Konto> kontadousuniecia = kontoDAOfk.findKontaSiostrzanePodatnik(wpisView, selectednodekonto.getMacierzyste());
                kontoDAOfk.destroy(kontadousuniecia);
                for (Konto p : kontadousuniecia) {
                    wykazkont.remove(p);
                }
                Konto macierzyste = kontoDAOfk.findKonto(selectednodekonto.getMacierzyste(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                Konto macierzystelista = wykazkont.get(wykazkont.indexOf(macierzyste));
                macierzystelista.setBlokada(false);
                macierzystelista.setMapotomkow(false);
                kontoDAOfk.edit(macierzystelista);
                selectednodekonto = null;
                Msg.msg("Usunięto słownik z kontami analitycznymi");
            } else {
                Msg.msg("e", "Próbujesz usunąc niewłaściwe konto. Wybierz konto bez numeru rozpoczynające sie od słowa 'Słownik'");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie usunięto słownika z kontami analitycznymi");
        }
    }
    
     public void nanieswnma(Konto p) {
        try {
            kontoDAOfk.edit(p);
            if (p.isMapotomkow()) {
                List<Konto> potomki = pobierzpotomkow(p);
                for (Konto r : potomki) {
                    r.setWnma0wm1ma2(p.getWnma0wm1ma2());
                    kontoDAOfk.edit(r);
                    nanieswnmaWzorcowy(r);
                }
            }
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), macierzyste.getPelnynumer());
          } catch (Exception e) {  E.e(e);
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
    
    public void nanieswnmaWzorcowy(Konto p) {
        kontoDAOfk.edit(p);
        if (p.isMapotomkow()) {
            List<Konto> potomki = pobierzpotomkowWzorcowy(p);
            for (Konto r : potomki) {
                r.setWnma0wm1ma2(p.getWnma0wm1ma2());
                nanieswnmaWzorcowy(r);
            }
            kontoDAOfk.edit(potomki);
        }
    }
    
    private List<Konto> pobierzpotomkowWzorcowy(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomnePodatnik(null, wpisView.getRokWpisu(), macierzyste.getPelnynumer());
          } catch (Exception e) {  E.e(e);
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
    public void implementujwmma0mn1ma0Podatnik() {
        implementujwmma0mn1ma0();
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
    }
    
    public void implementujwmma0mn1ma0() {
        try {
            List<Konto> kontapodatnika = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
            for (Konto p : wykazkontwzor) {
                if (p.getKontomacierzyste() == null) {
                    Konto r = pobierzkontopodatnika(kontapodatnika,p);
                    if (r != null) {
                        nanieswnmaImpl(r, p.getWnma0wm1ma2(), kontapodatnika);
                    }
                }
            }
            kontoDAOfk.editList(kontapodatnika);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    private Konto pobierzkontopodatnika(List<Konto> kontapodatnika, Konto wzorcowe) {
        Konto zwrot = new Konto();
        for (Konto r : kontapodatnika) {
            if (r.getPelnynumer().equals(wzorcowe.getPelnynumer())) {
                zwrot = r;
                break;
            }
        }
        return zwrot;
    }
    
    public void nanieswnmaImpl(Konto p, int wnma0wn1ma2, List<Konto> kontapodatnika) {
        p.setWnma0wm1ma2(wnma0wn1ma2);
        if (p.isMapotomkow()) {
            List<Konto> potomki = pobierzpotomkowImpl(p, kontapodatnika);
            for (Konto r : potomki) {
                r.setWnma0wm1ma2(p.getWnma0wm1ma2());
                nanieswnmaImpl(r,wnma0wn1ma2, kontapodatnika);
            }
        }
    }
    
    private List<Konto> pobierzpotomkowImpl(Konto macierzyste, List<Konto> kontapodatnika) {
        List<Konto> potomne = new ArrayList<>();
          try {
              for (Konto p : kontapodatnika) {
                  if (p.getKontomacierzyste() != null && p.getKontomacierzyste().equals(macierzyste)) {
                      potomne.add(p);
                  }
              }
          } catch (Exception e) {  E.e(e);
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return potomne;
      }
    
    
//    "#{planKontView.kontadowyswietlenia eq 'bilansowe' ?
//    (loop.zwyklerozrachszczegolne eq 'zwykłe ? 'rowb_zwykle' : loop.zwyklerozrachszczegolne eq 'szczególne' ? 'rowb_szczegolne' : 'rowb_rozrachunkowe') :
//    (planKontView.kontadowyswietlenia eq 'wynikowe' ?
//    (loop.przychod0koszt1 eq 0 ? 'rowb_szczegolne' : 'rowb_zwykle' :
//    loop.bilansowewynikowe eq 'bilansowe' ? (loop.level eq 0 ? 'row1' : loop.level eq 1 ? 'row2' : loop.level eq 2 ? 'row3' : 'row4') : (loop.level eq 0 ? 'row1w' : loop.level eq 1 ? 'row2w' : loop.level eq 2 ? 'row3w' : 'row4w')
//    ))}"

    

    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public Konto getSelectednodekonto() {
        return selectednodekonto;
    }

    public void setSelectednodekonto(Konto selectednodekonto) {
        this.selectednodekonto = selectednodekonto;
    }

    public KontoConv getKontoConv() {
        return kontoConv;
    }

    public void setKontoConv(KontoConv kontoConv) {
        this.kontoConv = kontoConv;
    }

   

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public Konto getSelected() {
        return selected;
    }

    public void setSelected(Konto selected) {
        this.selected = selected;
    }

    public Konto getNoweKonto() {
        return noweKonto;
    }

    public void setNoweKonto(Konto noweKonto) {
        this.noweKonto = noweKonto;
    }

    public String getWewy() {
        return wewy;
    }

    public void setWewy(String wewy) {
        this.wewy = wewy;
    }

//   static{
    public String getListajs() {
        return "jeden,dwa,trzy,cztery,piec,szesc,siedem,osiem,dziewiec,dziesiec";
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Konto> getWykazkontwzor() {
        return wykazkontwzor;
    }

    public void setWykazkontwzor(List<Konto> wykazkontwzor) {
        this.wykazkontwzor = wykazkontwzor;
    }

    public Konto getSelectednodekontowzorcowy() {
        return selectednodekontowzorcowy;
    }

    public void setSelectednodekontowzorcowy(Konto selectednodekontowzorcowy) {
        this.selectednodekontowzorcowy = selectednodekontowzorcowy;
    }

    public PlanKontCompleteView getPlanKontCompleteView() {
        return planKontCompleteView;
    }

    public void setPlanKontCompleteView(PlanKontCompleteView planKontCompleteView) {
        this.planKontCompleteView = planKontCompleteView;
    }

    public String getInfozebrakslownikowych() {
        return infozebrakslownikowych;
    }

    public void setInfozebrakslownikowych(String infozebrakslownikowych) {
        this.infozebrakslownikowych = infozebrakslownikowych;
    }

    public boolean isBezslownikowych() {
        return bezslownikowych;
    }

    public void setBezslownikowych(boolean bezslownikowych) {
        this.bezslownikowych = bezslownikowych;
    }

    public String getElementslownika_nazwapelna() {
        return elementslownika_nazwapelna;
    }

    public void setElementslownika_nazwapelna(String elementslownika_nazwapelna) {
        this.elementslownika_nazwapelna = elementslownika_nazwapelna;
    }

    public String getElementslownika_nazwaskrocona() {
        return elementslownika_nazwaskrocona;
    }

    public void setElementslownika_nazwaskrocona(String elementslownika_nazwaskrocona) {
        this.elementslownika_nazwaskrocona = elementslownika_nazwaskrocona;
    }

    public boolean isTylkosyntetyka() {
        return tylkosyntetyka;
    }

    public void setTylkosyntetyka(boolean tylkosyntetyka) {
        this.tylkosyntetyka = tylkosyntetyka;
    }

    public String getElementslownika_numerkonta() {
        return elementslownika_numerkonta;
    }

    public void setElementslownika_numerkonta(String elementslownika_numerkonta) {
        this.elementslownika_numerkonta = elementslownika_numerkonta;
    }

    public String getKontadowyswietlenia() {
        return kontadowyswietlenia;
    }

    public void setKontadowyswietlenia(String kontadowyswietlenia) {
        this.kontadowyswietlenia = kontadowyswietlenia;
    }

    public String getStyltabeliplankont() {
        return styltabeliplankont;
    }

    public void setStyltabeliplankont(String styltabeliplankont) {
        this.styltabeliplankont = styltabeliplankont;
    }

    public List<UkladBR> getListaukladow() {
        return listaukladow;
    }

    public void setListaukladow(List<UkladBR> listaukladow) {
        this.listaukladow = listaukladow;
    }

    public UkladBR getWybranyuklad() {
        return wybranyuklad;
    }

    public void setWybranyuklad(UkladBR wybranyuklad) {
        this.wybranyuklad = wybranyuklad;
    }

    public List<UkladBR> getListaukladowwzorcowy() {
        return listaukladowwzorcowy;
    }

    public void setListaukladowwzorcowy(List<UkladBR> listaukladowwzorcowy) {
        this.listaukladowwzorcowy = listaukladowwzorcowy;
    }

    public UkladBR getWybranyukladwzorcowy() {
        return wybranyukladwzorcowy;
    }

    public void setWybranyukladwzorcowy(UkladBR wybranyukladwzorcowy) {
        this.wybranyukladwzorcowy = wybranyukladwzorcowy;
    }

    public boolean isUsunprzyporzadkowanie() {
        return usunprzyporzadkowanie;
    }

    public void setUsunprzyporzadkowanie(boolean usunprzyporzadkowanie) {
        this.usunprzyporzadkowanie = usunprzyporzadkowanie;
    }

    //</editor-fold>

    public LazyDataModel<Konto> getWykazkontlazy() {
        return wykazkontlazy;
    }

    public void setWykazkontlazy(LazyDataModel<Konto> wykazkontlazy) {
        this.wykazkontlazy = wykazkontlazy;
    }


    
    

    

       

}

