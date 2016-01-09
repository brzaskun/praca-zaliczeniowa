/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.KontaFKBean;
import beansFK.PlanKontFKBean;
import beansFK.PozycjaRZiSFKBean;
import comparator.Kontocomparator;
import dao.PodatnikDAO;
import daoFK.DelegacjaDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.MiejsceKosztowDAO;
import daoFK.PojazdyDAO;
import daoFK.UkladBRDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Delegacja;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import msg.Msg;
import org.primefaces.context.RequestContext;
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
    private List<Konto> wykazkontwzor;
    @Inject
    private Konto selected;
    @Inject
    private Konto noweKonto;
    @Inject
    private KliencifkDAO kliencifkDAO;
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
    private DelegacjaDAO delegacjaDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    private String infozebrakslownikowych;
    @ManagedProperty(value = "#{planKontCompleteView}")
    private PlanKontCompleteView planKontCompleteView;
    
    
    public PlanKontView() {
    }

    @PostConstruct
    public void init() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
        int czysaslownikowe = sprawdzkonta();
        if (czysaslownikowe==0) {
            infozebrakslownikowych = " Brak podłączonych słowników do kont rozrachunkowych! Nie można księgować kontrahentów.";
            RequestContext.getCurrentInstance().update("dialogpierwszy");
        } else if (czysaslownikowe == 1) {
            infozebrakslownikowych = " Brak planu kont na dany rok";
            RequestContext.getCurrentInstance().update("dialogpierwszy");
        } else {
            infozebrakslownikowych = "";
        }
        //root = rootInit(wykazkont);
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        //rootwzorcowy = rootInit(wykazkontwzor);
    }
    //tworzy nody z bazy danych dla tablicy nodow plan kont

    private int sprawdzkonta() {
        int zwrot = 0;
        if (wykazkont == null || wykazkont.size() == 0) {
            zwrot = 1;
        } else {
            for (Konto p: wykazkont) {
                if (p.isSlownikowe()==true) {
                    zwrot = 2;
                }
            }
        }
        return zwrot;
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
        String podatnik;
        if (czyoddacdowzorca == true) {
            podatnik = "Wzorcowy";
        } else {
            podatnik = wpisView.getPodatnikWpisu();
        }
        Konto kontomacierzyste = selectednodekonto;
        if (noweKonto.getBilansowewynikowe() != null) {
            int wynikdodaniakonta = 1;
            if (podatnik.equals("Wzorcowy")) {
                wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczneWzorzec(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajsyntetyczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            }
            if (czyoddacdowzorca == true) {
                wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
            } else {
                wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
        String podatnik;
        Konto kontomacierzyste = null;
        if (czyoddacdowzorca == true) {
            podatnik = "Wzorcowy";
            kontomacierzyste = selectednodekontowzorcowy;
        } else {
            podatnik = wpisView.getPodatnikWpisu();
            kontomacierzyste = selectednodekonto;
        }
        if (kontomacierzyste.isBlokada() == false) {
            int wynikdodaniakonta = 1;
            if (podatnik.equals("Wzorcowy")) {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczneWzorzec(noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
            }
            if (wynikdodaniakonta == 0) {
                try {
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, ukladBRDAO);
                    if (kpo.getSyntetykaanalityka().equals("analityka")) {
                           Msg.msg("w","Konto przyporządkowane z poziomu analityki!");
                        } else {
                            PlanKontFKBean.naniesPrzyporzadkowanie(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", ukladBRDAO);
                        }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            if (wynikdodaniakonta == 0) {
                PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAOfk);
                if (czyoddacdowzorca == true) {
                    wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
                } else {
                    wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
        String nrmacierzystego = PlanKontFKBean.modyfikujnr(noweKonto.getPelnynumer());
        Konto kontomacierzyste = PlanKontFKBean.wyszukajmacierzyste(wpisView, kontoDAOfk, nrmacierzystego);
        if (kontomacierzyste != null && kontomacierzyste.getIdslownika() == 0) {
                int wynikdodaniakonta = 1;
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView);
                if (wynikdodaniakonta == 0) {
                    try {
                        KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, ukladBRDAO);
                        if (kpo.getSyntetykaanalityka().equals("analityka")) {
                           Msg.msg("w","Konto przyporządkowane z poziomu analityki!");
                        } else {
                            PlanKontFKBean.naniesPrzyporzadkowanie(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", ukladBRDAO);
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
                if (wynikdodaniakonta == 0) {
                    PlanKontFKBean.zablokujKontoMacierzysteNieSlownik(kontomacierzyste, kontoDAOfk);
                    if (czyoddacdowzorca == true) {
                        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
                    } else {
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                    }
                    noweKonto = new Konto();
                    //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                    Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
                } else {
                    noweKonto = new Konto();
                    Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
                }
                planKontCompleteView.init();
        } else {
            Msg.msg("e", "Niewłaściwy numer konta lub próba zmiany konta słownikowego. Nie dodano nowej analityki");
        }
    }
    
    public void dodajslownik() {
        Konto kontomacierzyste = selectednodekonto;
        List<Konto> kontapodpiete = kontoDAOfk.findKontaPotomnePodatnik(wpisView, kontomacierzyste.getPelnynumer());
        if (kontapodpiete.size() > 0) {
            Msg.msg("e", "Konto już ma podpiętą zwyczajną analitykę, nie można dodać słownika");
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                //oznaczenie okntr - znacdzy ze dodajemy slownik z kontrahentami
                if (noweKonto.getNrkonta().equals("kontr")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikKontrahenci(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 1);
                        Msg.msg("i", "Dodaje słownik kontrahentów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika kontrahentów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaKontrahenci(wykazkont, kontomacierzyste, kontoDAOfk, kliencifkDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika kontrahentów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika kontrahentów");
                    }
                } else if (noweKonto.getNrkonta().equals("miejs")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiejscaKosztow(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 2);
                        Msg.msg("i", "Dodaje słownik miejsc powstawania kosztów", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miejsc powstawania kosztów!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiejscaKosztow(wykazkont, kontomacierzyste, kontoDAOfk, miejsceKosztowDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania kosztów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania kosztów");
                    }
                } else if (noweKonto.getNrkonta().equals("samoc")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikPojazdyiMaszyny(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 3);
                        Msg.msg("i", "Dodaje słownik pojazdy i maszyny", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika pojazdy i maszyny!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaPojazdy(wykazkont, kontomacierzyste, kontoDAOfk, pojazdyDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miejsc powstawania kosztów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania kosztów");
                    }
                } else if (noweKonto.getNrkonta().equals("miesi")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikMiesiace(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 4);
                        Msg.msg("i", "Dodaje słownik miesięcy", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownika miesięcy!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaMiesiace(wykazkont, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika miesiące");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miesięcy");
                    }
                } else if (noweKonto.getNrkonta().equals("deleK")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikDelegacjeKrajowe(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 5);
                        Msg.msg("i", "Dodaje słownik delegacji krajowych", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownik delegacji krajowych!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaDelegacje(wykazkont, kontomacierzyste, kontoDAOfk, delegacjaDAO, wpisView, false, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika delegacji krajowych");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika delegacji krajowych");
                    }
                } else if (noweKonto.getNrkonta().equals("deleZ")) {
                    //to mozna podpiac slownik bo nie ma innych kont tylko slownikowe.
                    int wynikdodaniakonta = PlanKontFKBean.dodajslownikDelegacjeZagraniczne(wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        PlanKontFKBean.zablokujKontoMacierzysteSlownik(kontomacierzyste, kontoDAOfk, 6);
                        Msg.msg("i", "Dodaje słownik delegacji zagranicznych", "formX:messages");
                    } else {
                        noweKonto = new Konto();
                        Msg.msg("e", "Nie można dodać słownik delegacji zagranicznych!", "formX:messages");
                        return;
                    }
                    wynikdodaniakonta = PlanKontFKBean.dodajelementyslownikaDelegacje(wykazkont, kontomacierzyste, kontoDAOfk, delegacjaDAO, wpisView, true, kontopozycjaZapisDAO, ukladBRDAO);
                    if (wynikdodaniakonta == 0) {
                        noweKonto = new Konto();
                        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
                        Msg.msg("Dodano elementy słownika delegacji zagranicznych");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika delegacji zagranicznych");
                    }
                }
            } catch (Exception e) {  E.e(e);
            }
        }
    }

    
    public void implementacjaKontWzorcowych() {
        if (!wykazkontwzor.isEmpty()) {
            for (Konto p : wykazkontwzor) {
                if (wpisView.isFKpiatki() == false && p.getPelnynumer().startsWith("5")){
                    System.out.println("Nie implementuje konta 5");
                } else {
                    p.setMapotomkow(false);
                    p.setBlokada(false);
                    p.setRok(wpisView.getRokWpisu());
                    p.setPodatnik(wpisView.getPodatnikWpisu());
                    try {
                        kontoDAOfk.dodaj(p);
                    } catch (PersistenceException e) {
                        Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + p.getPelnynumer());
                    } catch (Exception ef) {
                        Msg.msg("e", "Wystąpił błąd podczas dodawania konta. Nie dodano: " + p.getPelnynumer());
                    }
                }
            }
            List<Konto> wykazkonttmp = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            //a teraz trzeba pozmieniac id macierzystych bo inaczej sie nie odnajda
            for (Konto p : wykazkonttmp) {
                if (!p.getMacierzyste().equals("0")) {
                    Konto macierzyste = kontoDAOfk.findKonto(p.getMacierzyste(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
                    p.setMacierzysty(macierzyste.getId());
                    kontoDAOfk.edit(p);
                }
            }
            porzadkowanieKontPodatnika();
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
                    Konto konto = selectednodekontowzorcowy;
                    try {
                        konto.setPodatnik(p.getNazwapelna());
                        Konto macierzyste = kontoDAOfk.findKonto(konto.getMacierzyste(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
                        if (!konto.getMacierzyste().equals("0")) {
                            konto.setMacierzysty(macierzyste.getId());
                            macierzyste.setMapotomkow(true);
                            macierzyste.setBlokada(true);
                            kontoDAOfk.edit(macierzyste);
                        } else {
                            konto.setMapotomkow(false);
                            konto.setBlokada(false);
                        }
                        kontoDAOfk.dodaj(konto);
                    } catch (RollbackException e) {

                    } catch (PersistenceException x) {
                        Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
                    } catch (Exception ef) {
                    }
                }
                wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
                dodajpojedynczekoto(konto, wpisView.getPodatnikWpisu());
                List<Konto> potomne = kontoDAOfk.findKontaPotomneWzorcowy(wpisView.getRokWpisu(), konto.getPelnynumer());
                for (Konto r : potomne) {
                    dodajpojedynczekoto(r, wpisView.getPodatnikWpisu());
                }
            }
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
                dodajpojedynczekoto(konto, "Wzorcowy");
                List<Konto> potomne = kontoDAOfk.findKontaPotomne(wpisView, konto.getPelnynumer(),konto.getBilansowewynikowe());
                for (Konto r : potomne) {
                    dodajpojedynczekotoWzorcowy(r, konto.getPelnynumer());
                }
                wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", wpisView.getRokWpisuSt());
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta z analityką do Wzorcowych");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do zaimplementowania jest pusta.");
        }
    }
     
     private void dodajpojedynczekoto(Konto konto, String podatnik) {
        konto.setPodatnik(podatnik);
        if (!konto.getMacierzyste().equals("0")) {
            Konto macierzyste = kontoDAOfk.findKonto(konto.getMacierzyste(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
            konto.setMacierzysty(macierzyste.getId());
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
        konto.setPodatnik("Wzorcowy");
        if (!konto.getMacierzyste().equals("0")) {
            Konto macierzyste = kontoDAOfk.findKonto(pelnynumer, "Wzorcowy", wpisView.getRokWpisu());
            konto.setMacierzysty(macierzyste.getId());
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
            for (Iterator it = wykazkont.iterator(); it.hasNext();) {
                Konto p = (Konto) it.next();
                if (!p.getPodatnik().equals("Wzorcowy")) {
                    try {
                        kontoDAOfk.destroy(p);
                        it.remove();
                    } catch (Exception e) {  E.e(e);
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
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(wpisView);
            for (Konto p : listakont) {
                p.setKontopozycjaID(null);
                try {
                    kontoDAOfk.edit(p);
                } catch (Exception e) {
                    E.e(e);
                }
            }
        } else {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(wpisView);
            for (Konto p : listakont) {
                p.setKontopozycjaID(null);
                try {
                    kontoDAOfk.edit(p);
                } catch (Exception e) {
                    E.e(e);
                }
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
                } catch (Exception e) {  E.e(e);
                    Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Na nieusuniętych kontach istnieją zapisy. Przerywam wykonywanie funkcji");
                }
            }
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", wpisView.getRokWpisuSt());
            RequestContext.getCurrentInstance().update("formwzorcowy");
            Msg.msg("Zakonczono z sukcesem usuwanie kont wzorocwych");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }
    

    public void porzadkowanieKontPodatnika() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        //resetuj kolumne macierzyste
        kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(null, wewy);
        for (Konto p : wykazkont) {
            p.setKontopozycjaID(null);
        }
        //tutaj nanosi czy ma potomkow
        KontaFKBean.czyszczenieKont(wykazkont, kontoDAOfk, wpisView, kontopozycjaZapisDAO);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        for (Konto p : wykazkont) {
            if (p.getPelnynumer().equals("010")) {
                System.out.println("s");
            }
            PlanKontFKBean.naniesprzyporzadkowanieSlownikowe(p, wpisView, kontoDAOfk, kontopozycjaZapisDAO, ukladBRDAO);
            if (p.isMapotomkow() == true) {
                if (p.getBilansowewynikowe().equals("wynikowe")) {
                    if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p, p.getKontopozycjaID(), kontoDAOfk, wpisView, "wnma", false, wpisView.getRokWpisu());
                    } else {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(p.getPelnynumer(), p.getKontopozycjaID(), kontoDAOfk, wpisView, false, "wynik", wpisView.getRokWpisu());
                    }
                } else {
                    if (p.getZwyklerozrachszczegolne().equals("rozrachunkowe") || p.getZwyklerozrachszczegolne().equals("vat") || p.getZwyklerozrachszczegolne().equals("szczególne")) {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p, p.getKontopozycjaID(), kontoDAOfk, wpisView, "wnma", false, wpisView.getRokWpisu());
                    } else {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(p.getPelnynumer(), p.getKontopozycjaID(), kontoDAOfk, wpisView, false, "bilans", wpisView.getRokWpisu());
                    }
                }
            }
        }
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
    }

    public void porzadkowanieKontWzorcowych() {
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
        //resetuj kolumne macierzyste
        KontaFKBean.czyszczenieKont(wykazkontwzor, kontoDAOfk, "Wzorcowy", wpisView, kontopozycjaZapisDAO, ukladBRDAO);
        wykazkontwzor = kontoDAOfk.findWszystkieKontaWzorcowy(wpisView);
    }

    public void usun(String klientWzor) {
        Konto dousuniecia = selectednodekonto != null? selectednodekonto : selectednodekontowzorcowy;
        String podatnik = null;
        if (klientWzor.equals("W")) {
            podatnik = "Wzorcowy";
        } else {
            podatnik = wpisView.getPodatnikWpisu();
        }
        if (dousuniecia != null) {
            Konto kontoDoUsuniecia = dousuniecia;
            if (kontoDoUsuniecia.isBlokada() == true) {
                Msg.msg("e", "Konto zablokowane. Na koncie istnieją zapisy. Nie można go usunąć");
            } else if (kontoDoUsuniecia.isMapotomkow() == true && !kontoDoUsuniecia.getNrkonta().equals("0")) {
                Msg.msg("e", "Konto ma analitykę, nie można go usunąć.");
            } else {
                try {
                    KontopozycjaZapis p = kontopozycjaZapisDAO.findByKonto(kontoDoUsuniecia, ukladBRDAO);
                    if (p != null) {
                        kontopozycjaZapisDAO.destroy(p);
                    }
                    kontoDAOfk.destroy(kontoDoUsuniecia);
                    if (klientWzor.equals("W")) {
                        wykazkontwzor.remove(kontoDoUsuniecia);
                    } else {
                        wykazkont.remove(kontoDoUsuniecia);
                    }
                    if (kontoDoUsuniecia.getNrkonta().equals("0")) {
                        int wynik = PlanKontFKBean.usunelementyslownika(kontoDoUsuniecia.getMacierzyste(), kontoDAOfk, wpisView, wykazkont, kontopozycjaZapisDAO, ukladBRDAO);
                        if (wynik == 0) {
                            Konto kontomacierzyste = kontoDAOfk.findKonto(kontoDoUsuniecia.getMacierzysty());
                            kontomacierzyste.setBlokada(false);
                            kontomacierzyste.setMapotomkow(false);
                            kontomacierzyste.setIdslownika(0);
                            kontoDAOfk.edit(kontomacierzyste);
                            Msg.msg("Usunięto elementy słownika");
                        } else {
                            Msg.msg("e", "Wystapił błąd i nie usunięto elementów słownika");
                        }
                    } else {
                        boolean sadzieci = PlanKontFKBean.sprawdzczymacierzystymapotomne(wpisView, kontoDoUsuniecia, kontoDAOfk);
                        List<Konto> siostry = null;
                        Konto kontomacierzyste = kontoDAOfk.findKonto(kontoDoUsuniecia.getMacierzysty());
                        if (klientWzor.equals("W")) {
                            siostry = kontoDAOfk.findKontaPotomneWzorcowy(wpisView.getRokWpisu(), kontomacierzyste.getPelnynumer());
                        } else {
                            siostry = kontoDAOfk.findKontaPotomnePodatnik(wpisView, kontomacierzyste.getPelnynumer());
                        }
                        if (siostry.size() < 1) {
                        //jak nie ma wiecej dzieci podpietych pod konto macierzyse usuwanego to zaznaczamy to na koncie macierzystym;
                            if (sadzieci == false && !kontoDoUsuniecia.getMacierzyste().equals("0")) {
                                kontomacierzyste.setBlokada(false);
                                kontomacierzyste.setMapotomkow(false);
                                kontoDAOfk.edit(kontomacierzyste);
                            }
                        }
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

    public void obslugaBlokadyKonta() {
        try {
            if (selectednodekonto != null) {
                Konto konto = selectednodekonto;
                if (konto.isBlokada() == false) {
                    konto.setBlokada(true);
                    kontoDAOfk.edit(konto);
                    Msg.msg("w", "Zabezpieczono konto przed edycją.");
                } else if (konto.isBlokada() == true) {
                    konto.setBlokada(false);
                    kontoDAOfk.edit(konto);
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
        for (Konto p : wykazkont) {
            Konto konto = p;
                konto.setBlokada(false);
                kontoDAOfk.edit(konto);

        }
        Msg.msg("w", "Odblokowano edycję kont");
    }

    
    public void zmiananazwykonta() {
        try {
            List<Konto> kontapotomne = kontoDAOfk.findKontaWszystkiePotomnePodatnik(wpisView, selectednodekonto);
            for (Konto p : kontapotomne) {
                p.setZwyklerozrachszczegolne(selectednodekonto.getZwyklerozrachszczegolne());
                p.setBilansowewynikowe(selectednodekonto.getBilansowewynikowe());
                kontoDAOfk.edit(p);
                Konto r = wykazkont.get(wykazkont.indexOf(p));
                if (r != null) {
                    r.setZwyklerozrachszczegolne(selectednodekonto.getZwyklerozrachszczegolne());
                    r.setBilansowewynikowe(selectednodekonto.getBilansowewynikowe());
                }
            }
            kontoDAOfk.edit(selectednodekonto);
        } catch (Exception e) {  E.e(e);
            
        }
    }
    
    public void zmiananazwykontawzorcowy() {
        try {
            List<Konto> kontapotomne = kontoDAOfk.findKontaWszystkiePotomneWzorcowy(wpisView, selectednodekontowzorcowy);
            for (Konto p : kontapotomne) {
                p.setZwyklerozrachszczegolne(selectednodekontowzorcowy.getZwyklerozrachszczegolne());
                p.setBilansowewynikowe(selectednodekontowzorcowy.getBilansowewynikowe());
                kontoDAOfk.edit(p);
                Konto r = wykazkontwzor.get(wykazkontwzor.indexOf(p));
                if (r != null) {
                    r.setZwyklerozrachszczegolne(selectednodekontowzorcowy.getZwyklerozrachszczegolne());
                    r.setBilansowewynikowe(selectednodekontowzorcowy.getBilansowewynikowe());
                }
            }
            kontoDAOfk.edit(selectednodekontowzorcowy);
        } catch (Exception e) {  E.e(e);
            
        }
    }
    

    public void selrow() {
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
    
     public void porzadkujSlowniki() {
        List<Kliencifk> obecniprzyporzadkowaniklienci = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        boolean sakliencifk = obecniprzyporzadkowaniklienci != null && !obecniprzyporzadkowaniklienci.isEmpty();
        if (sakliencifk) {
            for (Kliencifk p : obecniprzyporzadkowaniklienci) {
                try {
                    PlanKontFKBean.porzadkujslownik(wykazkont, p.getNazwa(), p.getNip(), Integer.parseInt(p.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk,1, ukladBRDAO);
                } catch (Exception e) {  E.e(e);
                    
                }
            }
        }
        List<MiejsceKosztow> miejscakosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        boolean samiejscakosztow = miejscakosztow != null && !miejscakosztow.isEmpty();
        if (samiejscakosztow) {
            for (MiejsceKosztow r : miejscakosztow) {
                try {
                    PlanKontFKBean.porzadkujslownik(wykazkont, r.getOpismiejsca(), r.getOpisskrocony(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk,2, ukladBRDAO);
                } catch (Exception e1) {
                    
                }
            }
        }
        List<Delegacja> delegacjekrajowe = delegacjaDAO.findDelegacjaPodatnik(wpisView, false);
        boolean sadelegacje = delegacjekrajowe != null && !delegacjekrajowe.isEmpty();
        if (sadelegacje) {
            for (Delegacja r : delegacjekrajowe) {
                try {
                    if (r.getOpisdlugi().equals("113/05/2015/k")) {
                        System.out.println("k");
                    }
                    PlanKontFKBean.porzadkujslownik(wykazkont, r.getOpisdlugi(), r.getOpiskrotki(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk,5, ukladBRDAO);
                } catch (Exception e1) {
                    
                }
            }
        }
        List<Delegacja> delegacjezagraniczne = delegacjaDAO.findDelegacjaPodatnik(wpisView, true);
        boolean sadelegacjezagr = delegacjezagraniczne != null && !delegacjezagraniczne.isEmpty();
        if (sadelegacjezagr) {
            for (Delegacja r : delegacjezagraniczne) {
                try {
                    PlanKontFKBean.porzadkujslownik(wykazkont, r.getOpisdlugi(), r.getOpiskrotki(), Integer.parseInt(r.getNrkonta()), kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk,6, ukladBRDAO);
                } catch (Exception e1) {
                    
                }
            }
        }
        List<String> listamiesiace = Mce.getMcenazwaListSlownik();
        int nrkonta = 1;
        for (String l : listamiesiace) {
            PlanKontFKBean.porzadkujslownik(wykazkont, l, l, nrkonta, kontoDAOfk, wpisView, kontopozycjaZapisDAO, kontoDAOfk,4, ukladBRDAO);
            nrkonta++;
        }
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        Msg.msg("Zakonczono aktualizowanie słowników");
    }
     
    public void oznaczkontoJakoKosztowe() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1(selectednodekonto, kontoDAOfk, true, wpisView);
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "wynikowe":
                wykazkont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "bilansowe":
                wykazkont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa0":
                wykazkont = kontoDAOfk.findKontaGrupa0(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa1":
                wykazkont = kontoDAOfk.findKontaGrupa1(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa2":
                wykazkont = kontoDAOfk.findKontaGrupa2(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa3":
                wykazkont = kontoDAOfk.findKontaGrupa3(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa4":
                wykazkont = kontoDAOfk.findKontaGrupa4(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa5":
                wykazkont = kontoDAOfk.findKontaGrupa5(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa6":
                wykazkont = kontoDAOfk.findKontaGrupa6(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa7":
                wykazkont = kontoDAOfk.findKontaGrupa7(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "grupa8":
                wykazkont = kontoDAOfk.findKontaGrupa8(wpisView);
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
                break;
            case "tłumaczenie":
                PdfPlanKont.drukujPlanKontTłumaczenie(wykazkont, wpisView);
                break;
        }

    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public Konto getSelectednodekonto() {
        return selectednodekonto;
    }

    public void setSelectednodekonto(Konto selectednodekonto) {
        this.selectednodekonto = selectednodekonto;
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
    
//</editor-fold>

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

    
    
}
