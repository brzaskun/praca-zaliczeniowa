/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.KontaFKBean;
import beansFK.KontoPozycjaBean;
import beansFK.PlanKontFKBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.UkladBRBean;
import comparator.Kontocomparator;
import converter.KontoConv;
import dao.DelegacjaDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.MiejsceKosztowDAO;
import dao.MiejscePrzychodowDAO;
import dao.PodatnikDAO;
import dao.PojazdyDAO;
import dao.PozycjaRZiSDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import dao.WierszBODAO;
import embeddable.Mce;
import embeddable.Roki;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdffk.PdfPlanKont;
import view.WpisView;
import interceptor.ConstructorInterceptor;
import xls.WriteXLSFile;
import xls.X;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PlanKontView implements Serializable {

    private static final long serialVersionUID = 1L;

    private int levelBiezacy = 0;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontZapas;
//    private LazyDataModel wykazkontlazy;
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
    private Konto selectednodekontoZmiana;
    private String wewy;
    private boolean czyoddacdowzorca;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
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
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    private String infozebrakslownikowych;
    @Inject
    private PlanKontCompleteView planKontCompleteView;
    @Inject
    private KontoConv kontoConv;
    private boolean bezslownikowych;
    private boolean tylkosyntetyka;
    private String kontadowyswietlenia;
    
    private String styltabeliplankont;
    private boolean usunprzyporzadkowanie;
    private List<UkladBR> listaukladow;
    private UkladBR wybranyuklad;
    private List<UkladBR> listaukladowwzorcowy;
    private UkladBR wybranyukladwzorcowy;
    private String wybranapozycja_wiersz;
    private int wybranaseriakont;
    private boolean bezprzyporzadkowania;
    private String rok;
    

    public PlanKontView() {
        bezslownikowych = true;
        kontadowyswietlenia = "wszystkie";
        ////E.m(this);
        wybranaseriakont = 9;
    
    }

    public void aktualizuj() {
        wpisView.setRokWpisuSt(rok);
        wpisView.naniesDaneDoWpisMini();
        init();
    }
    
    @PostConstruct
    public void init() {
        if (rok==null) {
            rok =wpisView.getRokWpisuSt();
        }
//E.m(this);
//        wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), rok);
        listaukladow = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), rok);
        wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
//        if (wybranyuklad != null) {
//            PozycjaRZiSFKBean.zmianaukladu("bilansowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), rok);
//            PozycjaRZiSFKBean.zmianaukladu("wynikowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), rok);
//        }
//<editor-fold defaultstate="collapsed" desc="comment">
//        int czysaslownikowe = sprawdzkonta();
//        if (czysaslownikowe == 0) {
//            infozebrakslownikowych = " Brak podłączonych słowników do kont rozrachunkowych! Nie można księgować kontrahentów.";
//            //PrimeFaces.current().ajax().update("dialogpierwszy");
//        } else if (czysaslownikowe == 1) {
//            infozebrakslownikowych = " Brak planu kont na dany rok";
//            //PrimeFaces.current().ajax().update("dialogpierwszy");
//        } else {
//            infozebrakslownikowych = "";
//        }
//</editor-fold>
        //wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), rok);
        wykazkontZapas = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), rok);
        Collections.sort(wykazkontZapas, new Kontocomparator());
        wykazkont = wykazkontZapas.stream().collect(Collectors.toList());
        //wykazkontlazy = new LazyKontoDataModel(wykazkont);
        //root = rootInit(wykazkont);
        listaukladowwzorcowy = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(), rok);
      wybranyukladwzorcowy = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladowwzorcowy);
//        if (wybranyukladwzorcowy != null) {
//            PozycjaRZiSFKBean.zmianaukladuwzorcowy("bilansowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk,  wpisView.getPodatnikwzorcowy(), rok);
//            PozycjaRZiSFKBean.zmianaukladuwzorcowy("wynikowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk,   wpisView.getPodatnikwzorcowy(), rok);
//        }
        wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), rok);
        styltabeliplankont = opracujstylwierszatabeli();
        //rootwzorcowy = rootInit(wykazkontwzor);
    }
    
    public void naniespozycjeNakontaZbazy() {
        try {
            List<KontopozycjaZapis> zapisane = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "wynikowe");
            zapisane.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "bilansowe"));
            if (zapisane.isEmpty()) {
                  Msg.msg("e","Brak zapisanych pozycji w bazie. Nie można przyporządkować kont");
            } else {
                for(Konto p : wykazkont) {
                    for (KontopozycjaZapis r : zapisane) {
                        if (r.getKontoID().equals(p)) {
                            p.naniesPozycje(r);
                            break;
                        }
                    }
                }
                kontoDAOfk.editList(wykazkont);
                Msg.msg("Przyporzadkowano konta");
            }
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void korygujpozycje() {
        if (wykazkontwzor!=null) {
            List<KontopozycjaZapis> listadousuniecia = new ArrayList<>();
            List<KontopozycjaZapis> listabaza = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "wynikowe");
            listabaza.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "bilansowe"));
            wykazkontwzor.stream().forEach(new Consumer<Konto>() {
                @Override
                public void accept(Konto obj) {
                    //                if (obj.isMapotomkow()==false&&obj.getKontomacierzyste()==null) {
//
//                   obj.setSaldodosprawozdania(true);
//                }
                    if (obj.getBilansowewynikowe().equals("bilansowe")) {
                        obj.setBilansowe(true);
                    }
                   
                }
            });
            kontoDAOfk.editList(wykazkontwzor);
            wykazkontwzor.stream().forEach(obj->{
                if (obj.isSaldodosprawozdania()==false&&(obj.getPozycjaWn()!=null||obj.getPozycjaMa()!=null)) {
                    obj.setPozycjaWn(null);
                    obj.setPozycjaMa(null);
                    KontopozycjaZapis kpozycja = listabaza.parallelStream().filter(p->p.getKontoID().equals(obj)).findFirst().orElse(null);
                    if (kpozycja!=null) {
                        listadousuniecia.add(kpozycja);
                    }
                }
            });
            kontoDAOfk.editList(wykazkontwzor);
            kontopozycjaZapisDAO.removeList(listadousuniecia);
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP("8513169524");
            List<UkladBR>  listaukladow1 = ukladBRDAO.findPodatnikRok(podatnik, rok);
            UkladBR wybranyuklad1 = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow1);
            List<KontopozycjaZapis> listabazapodatnik = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad1, "wynikowe");
            listabazapodatnik.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "bilansowe"));
            List<KontopozycjaZapis> listadoedycji = new ArrayList<>();
            wykazkontwzor.stream()
                .filter(obj -> obj != null && obj.isSaldodosprawozdania() &&
                               (obj.getPozycjaWn() == null || obj.getPozycjaMa() == null || 
                                obj.getPozycjaWn().isEmpty() || obj.getPozycjaMa().isEmpty()))
                .forEach(obj -> {
                    KontopozycjaZapis kpozycja = listabazapodatnik.stream()
                        .filter(p -> p != null && p.getKontoID() != null && 
                                     p.getKontoID().getPelnynumer() != null &&
                                     p.getKontoID().getPelnynumer().equals(obj.getPelnynumer()))
                        .findFirst()
                        .orElse(null);

                    if (kpozycja != null) {
                        obj.naniesPozycje(kpozycja);
                        listadoedycji.add(kpozycja);
                    }
                });

             kontoDAOfk.editList(wykazkontwzor);
             kontopozycjaZapisDAO.editList(listadoedycji);
             System.out.println("Koniec");
            Msg.msg("Zakonczono porządkowanie pozycji");
        }
    }
    
    public void zmianypodatnik() {
        List<UkladBR> listaukladow1 = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), rok);
        UkladBR wybranyuklad1 = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow1);
        List<Konto> kontapodatnika = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<KontopozycjaZapis> listabazapodatnik = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad1, "wynikowe");
        listabazapodatnik.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad1, "bilansowe"));
        List<UkladBR> listaukladow1wzorcowy = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(), rok);
        UkladBR wybranyukladwzorcowy = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow1wzorcowy);
        List<KontopozycjaZapis> listabaza = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyukladwzorcowy, "wynikowe");
        listabaza.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "bilansowe"));
        List<KontopozycjaZapis> listadoedycji = new ArrayList<>();
        List<KontopozycjaZapis> listadododania = new ArrayList<>();
        List<KontopozycjaZapis> listadousuniecia = new ArrayList<>();

        wykazkontwzor.stream().forEach(kontowzorcowe -> {
            if (kontowzorcowe != null) {
                Konto kontopodatnika = kontapodatnika.stream()
                        .filter(item -> kontowzorcowe.getPelnynumer().equals(item.getPelnynumer()))
                        .findFirst()
                        .orElse(null);

                if (kontopodatnika != null) {
                    kontopodatnika.setDwasalda(kontowzorcowe.isDwasalda());
                    kontopodatnika.setRozrachunkowe(kontowzorcowe.isRozrachunkowe());
                    kontopodatnika.setKontovat(kontowzorcowe.isKontovat());
                    kontopodatnika.setSaldodosprawozdania(kontowzorcowe.isSaldodosprawozdania());
                    kontopodatnika.setBilansowe(kontowzorcowe.isBilansowe());

                    if (kontowzorcowe.isSaldodosprawozdania()) {
                        KontopozycjaZapis kpozycjawzorcowa = listabaza.stream()
                                .filter(p -> p.getKontoID()!=null&&p.getKontoID().equals(kontowzorcowe))
                                .findFirst()
                                .orElse(null);

                        KontopozycjaZapis kpozycjapodatnik = listabazapodatnik.stream()
                                .filter(p ->  p.getKontoID()!=null&&p.getKontoID().equals(kontopodatnika))
                                .findFirst()
                                .orElse(null);

                        if (kpozycjawzorcowa!=null&&kpozycjapodatnik != null) {
                            kpozycjapodatnik.edytujzmianywzorzec(kpozycjawzorcowa);
                            listadoedycji.add(kpozycjapodatnik);
                        } else if (kpozycjawzorcowa != null&&kpozycjapodatnik == null) {
                            KontopozycjaZapis nowapozycja = KontoPozycjaBean.kopiujpozycjeWzorNowe(kpozycjawzorcowa, wybranyukladwzorcowy, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontopodatnika);
                            listadododania.add(nowapozycja);
                        } else if (kpozycjawzorcowa == null&&kpozycjapodatnik != null) {
                            listadousuniecia.add(kpozycjapodatnik);
                        }
                    } else {
                        kontopodatnika.setPozycjaWn(null);
                        kontopodatnika.setPozycjaMa(null);
                        kontopodatnika.setStronaWn(null);
                        kontopodatnika.setStronaMa(null);
                        KontopozycjaZapis kpozycja = listabazapodatnik.stream()
                                .filter(p -> p.getKontoID()!=null&&p.getKontoID().equals(kontopodatnika))
                                .findFirst()
                                .orElse(null);
                        if (kpozycja != null) {
                             listadousuniecia.add(kpozycja);
                        }
                    }
                }
                
            }
        });
        kontapodatnika.stream().forEach(kontopodatnika -> {
            if (kontopodatnika.isSaldodosprawozdania()==false) {
                kontopodatnika.setPozycjaWn(null);
                kontopodatnika.setPozycjaMa(null);
                kontopodatnika.setStronaWn(null);
                kontopodatnika.setStronaMa(null);
                KontopozycjaZapis kpozycja = listabazapodatnik.stream()
                        .filter(p -> p.getKontoID()!=null&&p.getKontoID().equals(kontopodatnika))
                        .findFirst()
                        .orElse(null);
                if (kpozycja != null) {
                     listadousuniecia.add(kpozycja);
                }
            }
        });
        kontoDAOfk.editList(kontapodatnika);
        kontopozycjaZapisDAO.removeList(listadousuniecia);
        kontopozycjaZapisDAO.editList(listadoedycji);
        kontopozycjaZapisDAO.createList(listadododania);
        System.out.println("Zakonczono nanoszenie zmian plan kont podatnika");
        Msg.msg("Zakonczono nanoszenie zmian plan kont podatnika");
    }

    
    private Podatnik ustawpodatnika() {
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        if (czyoddacdowzorca == true) {
            podatnik = wpisView.getPodatnikwzorcowy();
        }
        return podatnik;
    }
    
    private UkladBR ustawuklad() {
        UkladBR wybranyuklad = this.wybranyuklad;
        if (czyoddacdowzorca == true) {
            wybranyuklad = this.wybranyukladwzorcowy;
        }
        return wybranyuklad;
    }
    
    private Konto ustawselected() {
        Konto konto = selectednodekonto;
        if (czyoddacdowzorca == true) {
            konto = selectednodekonto;
        }
        return konto;
    }
    
    private Podatnik ustawpodatnikaselected() {
        Podatnik podatnik;
        if (selectednodekonto.getId() == null) {
            podatnik = wpisView.getPodatnikwzorcowy();
        } else {
            podatnik = wpisView.getPodatnikObiekt();
        }
        return podatnik;
    }
    
    
    
    public void zmienuklad() {
        listaukladow = ukladBRDAO.findPodatnik(wpisView.getPodatnikObiekt());
        for (UkladBR p : listaukladow) {
            p.setAktualny(false);
        }
        ukladBRDAO.editList(listaukladow);
        wybranyuklad.setAktualny(true);
        ukladBRDAO.edit(wybranyuklad);
        PozycjaRZiSFKBean.zmianaukladu("bilansowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        PozycjaRZiSFKBean.zmianaukladu("wynikowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
    }
    
    public List<Konto> pobierzlista(int numkont) {
        List<Konto> zwrot = new ArrayList<>();
        wybranaseriakont = numkont;
        String numkonts = numkont+"%";
        zwrot = kontoDAOfk.findKontaGrupa(wpisView, numkonts);
        Collections.sort(zwrot, new Kontocomparator());
        bezslownikowych = false;
        tylkosyntetyka = false;
        this.wykazkont = zwrot;
        return zwrot;
        //wykazkontlazy = new LazyKontoDataModel(wykazkont);
    }
    
    public void pobierzwszystkie() {
        wybranaseriakont = 9;
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(wykazkont, new Kontocomparator());
        //wykazkontlazy = new LazyKontoDataModel(wykazkont);
    }
    
    
    public void zmienukladwzorcowy() {
        if (wybranyukladwzorcowy!=null) {
            listaukladowwzorcowy = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            for (UkladBR p : listaukladowwzorcowy) {
                p.setAktualny(false);
            }
            ukladBRDAO.editList(listaukladowwzorcowy);
            wybranyukladwzorcowy.setAktualny(true);
            ukladBRDAO.edit(wybranyukladwzorcowy);
            PozycjaRZiSFKBean.zmianaukladuwzorcowy("bilansowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            PozycjaRZiSFKBean.zmianaukladuwzorcowy("wynikowe", wybranyukladwzorcowy, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            Collections.sort(wykazkontwzor, new Kontocomparator());
            Msg.msg("Udana zamiana układu");
        } else {
            Msg.msg("e","Nie wybrano układu");
        }
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
    
    public void planBezSlownikowychSyntetyczne(Podatnik podatnik, int lista) {
        wybranaseriakont = lista;
        planBezSlownikowychSyntetyczne(podatnik);
    }
    
    public List<Konto> planBezSlownikowychSyntetyczne(Podatnik podatnik) {
        List<Konto> wykazkontL = new ArrayList<>();
        String wybranaseriakontL = String.valueOf(wybranaseriakont);
        if (wybranaseriakont!=9) {
            wykazkontL = wykazkontZapas.stream().filter(p->p.getPelnynumer().startsWith(wybranaseriakontL)).collect(Collectors.toList());
            Collections.sort(wykazkontL, new Kontocomparator());
            if (bezprzyporzadkowania==true) {
                for (Iterator<Konto> it = wykazkontL.iterator(); it.hasNext();) {
                    Konto p = it.next();
                    if (p.getPozycjaWn()!=null&&p.getPozycjaMa()!=null) {
                        it.remove();
                    }
                }
            } else {
                if (tylkosyntetyka == true) {
                    for (Iterator<Konto> it = wykazkontL.iterator(); it.hasNext();) {
                        Konto p = it.next();
                        if (p.getLevel()>0) {
                            it.remove();
                        }
                    }
                } 

                if (bezslownikowych == true) {
                    for (Iterator<Konto> it = wykazkontL.iterator(); it.hasNext();) {
                        Konto p = it.next();
                        if (p.isSlownikowe()) {
                            it.remove();
                        }
                    }
                }
            }
        } else if (bezprzyporzadkowania==true) {
            wykazkontL = wykazkontZapas.stream().filter(p->p.getPozycjaWn()==null||p.getPozycjaWn()==null).collect(Collectors.toList());
        } else if (bezslownikowych == true && tylkosyntetyka == true) {
            wykazkontL = wykazkontZapas.stream().filter(p->p.getLevel()==0&&p.isSlownikowe()==false).collect(Collectors.toList());
        } else if (bezslownikowych == true) {
            wykazkontL = wykazkontZapas.stream().filter(p->p.isSlownikowe()==false).collect(Collectors.toList());
        } else if (tylkosyntetyka == true) {
            wykazkontL = wykazkontZapas.stream().filter(p->p.getLevel()==0).collect(Collectors.toList());
        } else {
            wykazkontL = wykazkontZapas.stream().collect(Collectors.toList());
        }
        if (kontadowyswietlenia.equals("bilansowe")) {
            for (Iterator it = wykazkontL.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("wynikowe")) {
                    it.remove();
                }
            }
        }
        if (kontadowyswietlenia.equals("wynikowe")) {
            for (Iterator it = wykazkontL.iterator(); it.hasNext();) {
                Konto k = (Konto) it.next();
                if (k.getBilansowewynikowe().equals("bilansowe")) {
                    it.remove();
                }
            }
        }
        Collections.sort(wykazkontL, new Kontocomparator());
        //wykazkontlazy = new LazyKontoDataModel(wykazkont);
        styltabeliplankont = opracujstylwierszatabeli();
        this.wykazkont = wykazkontL;
        return wykazkontL;
    }
    
//    public void planBezSlownikowychSyntetyczneWzorcowy() {
//        if (bezslownikowych == true && tylkosyntetyka == true) {
//            wykazkontwzor = kontoDAOfk.findKontazLeveluWzorcowy(wpisView,0);
//        } else if (bezslownikowych == true) {
//            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(null, wpisView.getRokWpisuSt());
//        } else if (tylkosyntetyka == true) {
//            wykazkontwzor = kontoDAOfk.findKontazLeveluWzorcowy(wpisView,0);
//        } else {
//            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(null, wpisView.getRokWpisuSt());
//        }
//        if (kontadowyswietlenia.equals("bilansowe")) {
//            for (Iterator it = wykazkontwzor.iterator(); it.hasNext();) {
//                Konto k = (Konto) it.next();
//                if (k.getBilansowewynikowe().equals("wynikowe")) {
//                    it.remove();
//                }
//            }
//        }
//        if (kontadowyswietlenia.equals("wynikowe")) {
//            for (Iterator it = wykazkontwzor.iterator(); it.hasNext();) {
//                Konto k = (Konto) it.next();
//                if (k.getBilansowewynikowe().equals("bilansowe")) {
//                    it.remove();
//                }
//            }
//        }
//        Collections.sort(wykazkontwzor, new Kontocomparator());
//        styltabeliplankont = opracujstylwierszatabeli();
//    }
    
//    public void planTylkoSyntetyczne() {
//        if (tylkosyntetyka == true) {
//            wykazkont = kontoDAOfk.findKontazLevelu(wpisView,0);
//            Collections.sort(wykazkont, new Kontocomparator());
//        } else {
//            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//            Collections.sort(wykazkont, new Kontocomparator());
//        }
//         wykazkontlazy = new LazyKontoDataModel(wykazkont);
//    }

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
        Podatnik podatnik = ustawpodatnika();
        if (noweKonto.getBilansowewynikowe() != null) {
            Konto nowododane = null;
            if (czyoddacdowzorca == true) {
                nowododane = PlanKontFKBean.dodajsyntetyczne(podatnik,wykazkontwzor, noweKonto, kontoDAOfk, wpisView.getRokWpisu());
                //wykazkontwzor = planBezSlownikowychSyntetyczne(podatnik);
                wykazkont.add(nowododane);
                Collections.sort(wykazkont, new Kontocomparator());
            } else {
                nowododane = PlanKontFKBean.dodajsyntetyczne(podatnik,wykazkont, noweKonto, kontoDAOfk, wpisView.getRokWpisu());
                //wykazkont = planBezSlownikowychSyntetyczne(podatnik);
                wykazkont.add(nowododane);
                Collections.sort(wykazkont, new Kontocomparator());
            }
            if (nowododane !=null) {
                noweKonto = new Konto();
                Msg.msg("i", "Dodano konto syntetyczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto syntetyczne o takim numerze juz istnieje!", "formX:messages");
            }
        }
    }

    public void dodajanalityczne() {
        Podatnik podatnik = ustawpodatnika();
        Konto kontomacierzyste = ustawselected();
        boolean czysasiostry = false;
            czysasiostry = kontomacierzyste.isMapotomkow();
            int wynikdodaniakonta = 1;
            if (czyoddacdowzorca == true) {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(podatnik, wykazkontwzor, noweKonto, kontomacierzyste, kontoDAOfk, wpisView.getRokWpisu());
            } else {
                wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(podatnik, wykazkont, noweKonto, kontomacierzyste, kontoDAOfk, wpisView.getRokWpisu());
            }
            if (wynikdodaniakonta == 0) {
                try {
                    UkladBR uklad = czyoddacdowzorca ? wybranyukladwzorcowy : wybranyuklad;
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, uklad);
                    if (kpo != null) {
                        if (kpo.getSyntetykaanalityka().equals("analityka")) {
                            Msg.msg("w", "Konto przyporządkowane z poziomu analityki!");
                        } else {
                            PlanKontFKBean.naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, "syntetyka");
                            kontoDAOfk.edit(noweKonto);
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
                    //wykazkontwzor = planBezSlownikowychSyntetyczne(podatnik);
                    wykazkontwzor.add(noweKonto);
                } else {
                    //wykazkont = planBezSlownikowychSyntetyczne(podatnik);
                    wykazkont.add(noweKonto);
                }
                Collections.sort(wykazkont,new Kontocomparator());
                noweKonto = new Konto();
                //PlanKontFKBean.odswiezroot(r, kontoDAOfk, wpisView);
                Msg.msg("i", "Dodaje konto analityczne", "formX:messages");
            } else {
                noweKonto = new Konto();
                Msg.msg("e", "Konto analityczne o takim numerze juz istnieje!", "formX:messages");
            }
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
        List<Konto> kontapodpiete = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontomacierzyste);
        if (kontapodpiete.size() > 0) {
            Msg.msg("e", "Konto już ma podpiętą zwyczajną analitykę, nie można dodać słownika");
        } else {
            //jezeli to nie slownik to wyrzuca blad i dodaje analityke
            try {
                if (wybranyuklad==null) {
                    wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
                }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
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
                        if (wybranaseriakont!=9) {
                           pobierzlista(wybranaseriakont);
                        } else {
                            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                        }
                        Msg.msg("Dodano elementy słownika miejsc powstawania przychodów");
                    } else {
                        Msg.msg("e", "Wystąpił błąd przy dodawaniu elementów słownika miejsc powstawania przychodów");
                    }
                }
            Collections.sort(wykazkont, new Kontocomparator());
            } catch (Exception e) {
                E.e(e);
            }
        }
    }
    
      public void implementacjaNowePorzadki() {
        if (!wykazkontwzor.isEmpty()&&wybranyuklad!=null) {
            List<Konto> kontapodatnika = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<KontopozycjaZapis> pozycjewzorcowe = kontopozycjaZapisDAO.findByUklad(wybranyuklad);
            for (Konto p : wykazkontwzor) {
                if (wpisView.isParamCzworkiPiatki() == false && p.getPelnynumer().startsWith("5")) {
                } else {
                   Predicate<Konto> isQ = item->item.getPelnynumer().equals(p.getPelnynumer());
                   Konto znalezionepodatnika = kontapodatnika.stream().filter(isQ).findFirst().orElse(null);
                   znalezionepodatnika.setSaldodosprawozdania(p.isSaldodosprawozdania());
                   znalezionepodatnika.setDwasalda(p.isDwasalda());
                   znalezionepodatnika.setRozrachunkowe(p.isRozrachunkowe());
                   znalezionepodatnika.setKontovat(p.isKontovat());
                }
            }
            kontoDAOfk.editList(kontapodatnika);
            PrimeFaces.current().ajax().update("form:dataList");
            Msg.msg("Zakonczono z sukcesem implementacje kont wzorcowych u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }

    public void implementacjaKontWzorcowych() {
        if (!wykazkontwzor.isEmpty()) {
            for (Konto p : wykazkontwzor) {
                if (wpisView.isParamCzworkiPiatki() == false && p.getPelnynumer().startsWith("5")) {
                } else {
                    p.setMapotomkow(false);
                    p.setBlokada(false);
                    p.setRok(wpisView.getRokWpisu());
                    p.setPodatnik(wpisView.getPodatnikObiekt());
                    try {
                        kontoDAOfk.create(p);
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
                    if (p.getKontomacierzyste()!=null) {
                        Konto macierzyste = kontoDAOfk.findKonto(p.getKontomacierzyste().getPelnynumer(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        p.setMacierzysty(macierzyste.getId());
                        p.setKontomacierzyste(macierzyste);
                    }
                }
                kontoDAOfk.editList(wykazkonttmp);
            }
            porzadkowanieKontPodatnikaNowe(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PrimeFaces.current().ajax().update("form:dataList");
            Msg.msg("Zakonczono z sukcesem implementacje kont wzorcowych u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont wzorcowych jest pusta.");
        }
    }

    public void implementacjaJednegoKontaWzorcowego() {
        if (selectednodekonto == null) {
            Msg.msg("w", "Nie wybrano konta do powielenia.");
        } else if (selectednodekonto.getPozycjaWn()==null||selectednodekonto.getPozycjaMa()==null) {
            Msg.msg("w", "Konto bez przyporzadkowania");
        } else if (wybranyukladwzorcowy==null) {
            Msg.msg("w", "Nie wybrano domyślnego układu wzorcowego");
        } else {
            try {
                List<Podatnik> listapodatnikowfk = podatnikDAO.findPodatnikFK();
                for (Podatnik p : listapodatnikowfk) {
                    List<Konto> obecnyplantkont = kontoDAOfk.findWszystkieKontaPodatnika(p, wpisView.getRokWpisuSt());
                    if (obecnyplantkont!=null&&!obecnyplantkont.isEmpty()) {
                        Konto kontopodatnik = new Konto(selectednodekonto);
                        try {
                            kontopodatnik.setPodatnik(p);
                            if (kontopodatnik.getKontomacierzyste()!=null) {
                                Konto macierzyste = kontoDAOfk.findKonto(kontopodatnik.getKontomacierzyste().getPelnynumer(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                                kontopodatnik.setMacierzysty(macierzyste.getId());
                                kontopodatnik.setKontomacierzyste(macierzyste);
                                macierzyste.setMapotomkow(true);
                                kontoDAOfk.edit(macierzyste);
                            } else {
                                kontopodatnik.setMapotomkow(false);
                            }
                            kontoDAOfk.create(kontopodatnik);
                            //to chyba jest bez sensu
                            KontoPozycjaBean.duplikujpozycje(ukladBRDAO,wybranyukladwzorcowy.getUklad(), p, wpisView.getRokWpisuSt(), selectednodekonto, kontopodatnik, kontopozycjaZapisDAO);
                        } catch (RollbackException e) {
                            E.e(e);
                        } catch (PersistenceException x) {
                            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + kontopodatnik.getPelnynumer());
                        } catch (Exception ef) {
                            E.e(ef);
                        }
                    }
                }
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego u wszystkich klientów FK");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } 
    }
    
    public void implementacjaJednegoKontaWzorcowegoBiezacy() {
        if (selectednodekonto != null) {
            try {
                Podatnik p = wpisView.getPodatnikObiekt();
                    Konto kontopodatnik = new Konto(selectednodekonto);
                    try {
                        kontopodatnik.setPodatnik(p);
                        Konto macierzyste = kontoDAOfk.findKonto(kontopodatnik.getKontomacierzyste().getPelnynumer(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        if (kontopodatnik.getKontomacierzyste()!=null) {
                            kontopodatnik.setMacierzysty(macierzyste.getId());
                            kontopodatnik.setKontomacierzyste(macierzyste);
                            macierzyste.setMapotomkow(true);
                            macierzyste.setBlokada(true);
                            kontoDAOfk.edit(macierzyste);
                        } else {
                            kontopodatnik.setMapotomkow(false);
                            kontopodatnik.setBlokada(false);
                        }
                        kontoDAOfk.create(kontopodatnik);
                        KontoPozycjaBean.duplikujpozycje(ukladBRDAO,wybranyukladwzorcowy.getUklad(), p, wpisView.getRokWpisuSt(), selectednodekonto, kontopodatnik, kontopozycjaZapisDAO);
                    } catch (RollbackException e) {
                        E.e(e);
                    } catch (PersistenceException x) {
                        Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + kontopodatnik.getPelnynumer());
                    } catch (Exception ef) {
                        E.e(ef);
                    }
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta wzorcowego u bieżącego podatnika FK");
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
                    List<Konto> obecnyplantkont = kontoDAOfk.findWszystkieKontaPodatnika(p, wpisView.getRokWpisuSt());
                    if (obecnyplantkont!=null&&!obecnyplantkont.isEmpty()) {
                        Konto konto = new Konto(selectednodekonto);
                        konto = dodajpojedynczekoto(konto, p);
                        KontoPozycjaBean.duplikujpozycje(ukladBRDAO, wybranyukladwzorcowy.getUklad(), p, wpisView.getRokWpisuSt(), selectednodekonto, konto, kontopozycjaZapisDAO);
                        List<Konto> potomnelista = kontoDAOfk.findKontaPotomne(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu(), selectednodekonto, selectednodekonto.getBilansowewynikowe());
                        if (potomnelista != null) {
                            for (Konto r : potomnelista) {
                                Konto potomne = new Konto(r);
                                potomne = dodajpojedynczekoto(potomne, p);
                                KontoPozycjaBean.duplikujpozycje(ukladBRDAO, wybranyukladwzorcowy.getUklad(), p, wpisView.getRokWpisuSt(), r, potomne, kontopozycjaZapisDAO);
                            }
                        }
                    }
                }
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
                List<Konto> potomne = kontoDAOfk.findKontaPotomne(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu(),  konto, konto.getBilansowewynikowe());
                for (Konto r : potomne) {
                    dodajpojedynczekotoWzorcowy(r, konto.getPelnynumer());
                }
                wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
                Msg.msg("Zakonczono z sukcesem implementacje pojedyńczego konta z analityką do Wzorcowych");
            } catch (Exception e1) {
                Msg.msg("e", "Próbujesz zaimplementować konto analityczne. Zaimplementuj najpierw jego konto macierzyste.");
            }
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do zaimplementowania jest pusta.");
        }
    }

    private Konto dodajpojedynczekoto(Konto konto, Podatnik podatnik) {
        konto.setPodatnik(podatnik);
        if (konto.getKontomacierzyste()!=null) {
            Konto macierzyste = kontoDAOfk.findKonto(konto.getKontomacierzyste().getPelnynumer(), podatnik, wpisView.getRokWpisu());
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
            kontoDAOfk.create(konto);
        } catch (RollbackException e) {

        } catch (PersistenceException x) {
            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
        } catch (Exception ef) {
        }
        return konto;
    }

    private void dodajpojedynczekotoWzorcowy(Konto konto, String pelnynumer) {
        konto.setPodatnik(null);
        if (konto.getKontomacierzyste()!=null) {
            Konto macierzyste = kontoDAOfk.findKonto(pelnynumer, wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu());
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
            kontoDAOfk.create(konto);
        } catch (RollbackException e) {

        } catch (PersistenceException x) {
            Msg.msg("e", "Wystąpił błąd przy implementowaniu kont. Istnieje konto o takim numerze: " + konto.getPelnynumer());
        } catch (Exception ef) {
        }
    }

    public void usunieciewszystkichKontPodatnika() {
        if (!wykazkont.isEmpty()) {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<UkladBR> uklady = ukladBRDAO.findukladBRPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            wyczyscKonta("wynikowe");
            wyczyscKonta("bilansowe");
            usunpozycjezapisane();
            wierszBODAO.deletePodatnikRokBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            wierszBODAO.deletePodatnikRokObroty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<Rodzajedok> rodzajeDokPodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
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
                        kontoDAOfk.remove(p);
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
            PrimeFaces.current().ajax().update("form_dialog_plankont");
            Msg.msg("Zakonczono z sukcesem usuwanie kont u bieżącego podatnika");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }

    private void wyczyscKonta(String rb) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            listakont.forEach((p) -> {
                p.setPozycjaWn(null);
                p.setPozycjaMa(null);
                p.setStronaWn(null);
                p.setStronaMa(null);
                p.setSyntetykaanalityka(null);
            });
            if (listakont != null) {
                kontoDAOfk.editList(listakont);
            }
        } else {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            listakont.forEach((p) -> {
                p.setPozycjaWn(null);
                p.setPozycjaMa(null);
                p.setStronaWn(null);
                p.setStronaMa(null);
                p.setSyntetykaanalityka(null);
            });
            if (listakont != null) {
                kontoDAOfk.editList(listakont);
            }
        }
    }

    public void usunieciewszystkichKontWzorcowy() {
        if (!wykazkontwzor.isEmpty()) {
            List<UkladBR> uklady = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(),wpisView.getRokWpisuSt());
            for (UkladBR u : uklady) {
                wyczyscKonta("wynikowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(u, "wynikowe");
                wyczyscKonta("bilansowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(u, "bilansowe");
            }
            for (Konto p : wykazkontwzor) {
                try {
                    kontoDAOfk.remove(p);
                } catch (Exception e) {
                    E.e(e);
                    Msg.msg("e", "Wystąpił błąd przy usuwaniu wszytskich kont. Na nieusuniętych kontach istnieją zapisy. Przerywam wykonywanie funkcji");
                }
            }
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            PrimeFaces.current().ajax().update("formwzorcowy");
            Msg.msg("Zakonczono z sukcesem usuwanie kont wzorocwych");
        } else {
            Msg.msg("w", "Coś poszło nie tak. Lista kont do usuniecia jest pusta.");
        }
    }
    
    public void porzadkujCzterySiedem() {
        if (wykazkont!=null) {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Collections.sort(wykazkont, new Kontocomparator());
            List<Konto> kontaczteryisiedem = wykazkont.stream().filter(p->(p.getPelnynumer().startsWith("4")||p.getPelnynumer().startsWith("7"))).collect(Collectors.toList());
            if (kontaczteryisiedem.isEmpty()==false) {
                kontaczteryisiedem.forEach(pa->{
                    try {
                        List<Konto> potomne = wykazkont.stream().filter(p->p.isWynik0bilans1()==false&&p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pa)).collect(Collectors.toList());
                        List<Konto> potomne2 = wykazkont.stream().filter(p->p.isWynik0bilans1()==false&&p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pa)&&p.getPozycjaWn().equals(pa.getPozycjaWn())&&p.getPozycjaMa().equals(pa.getPozycjaMa())).collect(Collectors.toList());
                        if (potomne.isEmpty()==false&&potomne2.isEmpty()==false&&potomne.equals(potomne2)) {
                            selectednodekontoZmiana = pa;
                            if (pa.getKontomacierzyste()==null) {
                                selectednodekontoZmiana.setSyntetykaanalityka("wynikowe");
                            } else {
                                selectednodekontoZmiana.setSyntetykaanalityka("syntetyka");
                            }
                            zmiananazwykonta();
                        } else if (potomne.isEmpty()) {
                            pa.setSyntetykaanalityka("wynikowe");
                            kontoDAOfk.edit(pa);
                        }
                    } catch (Exception e) {
                        System.out.println("Błąd  konto "+pa.getPelnynumer());
                    }
                });
                
            }
            List<Konto> kontabilanowe = wykazkont.stream().filter(p->p.isWynik0bilans1()==true&&p.getKontomacierzyste()==null&&p.getSyntetykaanalityka()!=null
                    &&(p.getSyntetykaanalityka().equals("analityka")||p.getSyntetykaanalityka().equals("zwykłe"))).collect(Collectors.toList());
            if (kontabilanowe.isEmpty()==false) {
                kontabilanowe.forEach(pa->{
                    try {
                        List<Konto> potomne = wykazkont.stream().filter(p->p.isWynik0bilans1()&&p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pa)&&(p.getSyntetykaanalityka().equals("analityka")||p.getSyntetykaanalityka().equals("zwykłe"))).collect(Collectors.toList());
                        List<Konto> potomne2 = wykazkont.stream().filter(p->p.isWynik0bilans1()&&p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pa)&&p.getPozycjaWn().equals(pa.getPozycjaWn())&&p.getPozycjaMa().equals(pa.getPozycjaMa())).collect(Collectors.toList());
                         if (potomne.isEmpty()==false&&potomne2.isEmpty()==false&&potomne.equals(potomne2)) {
                            pa.setMapotomkow(true);
                            pa.setSyntetykaanalityka("zwykłe");
                            potomne.forEach(po-> {
                               po.setSyntetykaanalityka("syntetyka");
                            });
                            kontoDAOfk.editList(potomne);
                        }
                    } catch (Exception e){}
                });
                kontoDAOfk.editList(kontabilanowe);
            }
            List<KontopozycjaZapis> zapisanepozycje = kontopozycjaZapisDAO.findByUklad(wybranyuklad);
//            List<Konto> kontapotomneDwiescie = wykazkont.stream().filter(p->p.getKontomacierzyste()!=null).collect(Collectors.toList());
//             if (kontapotomneDwiescie.isEmpty()==false) {
//                for (Konto p : kontapotomneDwiescie) {
//                    try {
//                        selectednodekonto = p;
//                        porzadkowanieWybranegoKontaPodatnika(zapisanepozycje);
//                    } catch (Exception e) {
//                        E.e(e);
//                    }
//                }
//             }
            for (int i = 0;i<4;i++) {
                final int j = i;
                List<Konto> kontamacierzyste = wykazkont.stream().filter(p->p.getLevel()==j).collect(Collectors.toList());
                if (kontamacierzyste.isEmpty()==false) {
                   for (Konto pi : kontamacierzyste) {
                       try {
                           List<Konto> potomne = wykazkont.stream().filter(p->p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pi)
                                   &&(p.getPozycjaWn()==null||p.getPozycjaMa()==null)).collect(Collectors.toList());
                           if (potomne.isEmpty()==false) {
                                selectednodekonto = pi;
                                porzadkowanieWybranegoKontaPodatnika(zapisanepozycje, pi);
                           }
                       } catch (Exception e) {
                           E.e(e);
                           System.out.println("Błąd  konto "+pi.getPelnynumer());
                       }
                   }
                }
            }
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Collections.sort(wykazkont, new Kontocomparator());
//            Collections.sort(wykazkont, new Kontocomparator());   
        }
    }
    
    public void porzadkujCzterySiedemUzer() {
        if (wykazkont!=null) {
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Collections.sort(wykazkont, new Kontocomparator());
            List<Konto> kontabilanowe = wykazkont.stream().filter(p->p.isWynik0bilans1()==true&&p.getKontomacierzyste()==null&&p.getSyntetykaanalityka()!=null&&p.getPelnynumer().startsWith("20")).collect(Collectors.toList());
            kontabilanowe.addAll(wykazkont.stream().filter(p->p.isWynik0bilans1()==true&&p.getKontomacierzyste()==null&&p.getSyntetykaanalityka()!=null&&p.getPelnynumer().startsWith("3")).collect(Collectors.toList()));
            if (kontabilanowe.isEmpty()==false) {
                kontabilanowe.forEach(pa->{
                    try {
                        List<Konto> potomne = wykazkont.stream().filter(p->p.isWynik0bilans1()&&p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pa)).collect(Collectors.toList());
                         if (potomne.isEmpty()==false) {
                            pa.setMapotomkow(true);
                            pa.setSyntetykaanalityka("zwykłe");
                            potomne.forEach(po-> {
                               po.setSyntetykaanalityka("syntetyka");
                               List<Konto> potomne2 = wykazkont.stream().filter(p->p.isWynik0bilans1()&&p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(po)).collect(Collectors.toList());
                                if (potomne2.isEmpty()==false) {
                                   po.setMapotomkow(true);
                                   po.setSyntetykaanalityka("syntetyka");
                                   potomne2.forEach(po1-> {
                                       po.setMapotomkow(false);
                                      po1.setSyntetykaanalityka("syntetyka");
                                   });
                                   kontoDAOfk.editList(potomne2);
                               }
                            });
                            kontoDAOfk.editList(potomne);
                        }
                    } catch (Exception e){}
                });
                kontoDAOfk.editList(kontabilanowe);
            }
            List<KontopozycjaZapis> zapisanepozycje = kontopozycjaZapisDAO.findByUklad(wybranyuklad);
//            List<Konto> kontapotomneDwiescie = wykazkont.stream().filter(p->p.getKontomacierzyste()!=null).collect(Collectors.toList());
//             if (kontapotomneDwiescie.isEmpty()==false) {
//                for (Konto p : kontapotomneDwiescie) {
//                    try {
//                        selectednodekonto = p;
//                        porzadkowanieWybranegoKontaPodatnika(zapisanepozycje);
//                    } catch (Exception e) {
//                        E.e(e);
//                    }
//                }
//             }
            for (int i = 0;i<4;i++) {
                final int j = i;
                List<Konto> kontamacierzyste = wykazkont.stream().filter(p->p.getLevel()==j).collect(Collectors.toList());
                if (kontamacierzyste.isEmpty()==false) {
                   for (Konto pi : kontamacierzyste) {
                       try {
                           List<Konto> potomne = wykazkont.stream().filter(p->p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(pi)
                                   &&(p.getPozycjaWn()==null||p.getPozycjaMa()==null)).collect(Collectors.toList());
                           if (potomne.isEmpty()==false) {
                                selectednodekonto = pi;
                                porzadkowanieWybranegoKontaPodatnika(zapisanepozycje, pi);
                           }
                       } catch (Exception e) {
                           E.e(e);
                           System.out.println("Błąd  konto "+pi.getPelnynumer());
                       }
                   }
                }
            }
            wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Collections.sort(wykazkont, new Kontocomparator());
//            Collections.sort(wykazkont, new Kontocomparator());   
        }
    }
    
    public void edytujkontowzorcowe(Konto konto) {
        try {
             kontoDAOfk.edit(konto);
             Msg.msg("Zapisano zmiany na koncie");
        } catch (Exception e) {
            Msg.msg("e","Błąd podczas zapisywania zmian na koncie");
        }
    }
    
    public void porzadkowanieWybranegoKontaPodatnika(List<KontopozycjaZapis> zapisanepozycje, Konto selectednodekontoL) {
        if (selectednodekontoL != null) {
            Podatnik podatnik = selectednodekontoL.getPodatnik();
            if (!wykazkont.isEmpty()) {
                //tutaj nanosi czy ma potomkow
                if (selectednodekontoL.getSyntetykaanalityka() != null) {
                    boolean kontozwykle = selectednodekontoL.getSyntetykaanalityka().equals("zwykłe");
                    boolean bilansowe= selectednodekontoL.isWynik0bilans1();
                    boolean analityka = selectednodekontoL.getSyntetykaanalityka().equals("analityka");
                    //bylo tak, ale nie nanosilo innego przyporzadkowania jak byly inne niz systetyki
                    //List<Konto> kontapotomne = wykazkont.stream().filter(p -> p.getKontomacierzyste() != null && p.getKontomacierzyste().equals(selectednodekonto) && (p.getPozycjaWn()==null || p.getPozycjaMa()==null)).collect(Collectors.toList());
                    List<Konto> kontapotomne = wykazkont.stream().filter(p -> p.getKontomacierzyste() != null && p.getKontomacierzyste().equals(selectednodekontoL)).collect(Collectors.toList());
                    if (kontapotomne.isEmpty() == false) {
                        selectednodekontoL.setMapotomkow(true);
                        kontoDAOfk.edit(selectednodekontoL);
                        List<Konto> kontapotomnePorzadek = new ArrayList<>();
                        List<KontopozycjaZapis> nowepozycje = Collections.synchronizedList(new ArrayList<>());
                        if (zapisanepozycje == null) {
                            zapisanepozycje = kontopozycjaZapisDAO.findByUklad(wybranyuklad);
                        }
                        for (Konto p : kontapotomne) {
                            p.kopiujPozycje(selectednodekontoL);
                            if (bilansowe==false && analityka==false) {
                                p.setSyntetykaanalityka("wynikowe");
                                if (zapisanepozycje.isEmpty() == false) {
                                    zapisanepozycje.stream().forEach(pz -> {
                                        for (Konto pa : kontapotomnePorzadek) {
                                            if (pa.equals(pz.getKontoID())) {
                                                kontopozycjaZapisDAO.remove(pz);
                                            }
                                         }
                                     });
                                 }
                            } else if (kontozwykle) {
                                p.setSyntetykaanalityka("syntetyka");
                                if (zapisanepozycje.isEmpty() == false) {
                                    zapisanepozycje.stream().forEach(pz -> {
                                        for (Konto pa : kontapotomnePorzadek) {
                                            if (pa.equals(pz.getKontoID())) {
                                                kontopozycjaZapisDAO.remove(pz);
                                            }
                                         }
                                     });
                                 }
                                 nowepozycje.add(new KontopozycjaZapis(p, wybranyuklad));
                            } else {
                                p.setSyntetykaanalityka(selectednodekontoL.getSyntetykaanalityka());
                            }
                            kontapotomnePorzadek.add(p);
                        }
                        selectednodekontoL.setMapotomkow(true);
                        kontoDAOfk.editList(kontapotomnePorzadek);
                        kontopozycjaZapisDAO.editList(nowepozycje);
                        Msg.msg("Przejrzano potomków");
                    } else {
                        selectednodekontoL.setMapotomkow(false);
                        kontoDAOfk.edit(selectednodekontoL);
                        Msg.msg("Konto bez potomków");
                    }
                }
            } else {
                Msg.msg("e", "Brak kont potomnych");
            }
        } else {
            Msg.msg("e", "Nie wybrano konta");
        }
    }
    
    
    
    
    //stare
//    public void porzadkowanieWybranegoKontaPodatnika() {
//        if (selectednodekonto!=null) {
//            Podatnik podatnik = selectednodekonto.getPodatnik();
//            List<Konto> wykazPorzadkowanychKont = new ArrayList<>();
//            //wykazkontf.add(selectednodekonto);
//            kontoDAOfk.findKontaWszystkiePotomnePodatnik(wykazPorzadkowanychKont, podatnik, wpisView.getRokWpisu(), selectednodekonto);
//            if (!wykazPorzadkowanychKont.isEmpty()) {
//                for (Konto k: wykazPorzadkowanychKont) {
//                    k.setBlokada(false);
//                }
//                kontoDAOfk.editList(wykazPorzadkowanychKont);
//                for (Konto k: wykazPorzadkowanychKont) {
//                    if (k.getKontomacierzyste()==null && k.getMacierzysty()!=0) {
//                        k.setKontomacierzyste(KontaFKBean.znajdzemacierzyste(k.getMacierzysty(), kontoDAOfk));
//                        kontoDAOfk.edit(k);
//                    }
//                }
//                //tutaj nanosi czy ma potomkow
//                KontaFKBean.ustawCzyMaPotomkowJedno(selectednodekonto, wykazPorzadkowanychKont, kontoDAOfk);
//                wykazPorzadkowanychKont = new ArrayList<>();
//                kontoDAOfk.findKontaWszystkiePotomnePodatnik(wykazPorzadkowanychKont, podatnik, wpisView.getRokWpisu(), selectednodekonto);
//                for (Konto p : wykazPorzadkowanychKont) {
//                    p.kopiujPozycje(selectednodekonto);
//                    p.setSyntetykaanalityka("syntetyka");
//                }
//                kontopozycjaZapisDAO.usunKontoPozycjaPodatnikUladKonto(wybranyuklad, wykazPorzadkowanychKont);
//                List<KontopozycjaZapis> nowepozycje = Collections.synchronizedList(new ArrayList<>());
//                for (Konto p : wykazPorzadkowanychKont) {
//                    try {
//                        nowepozycje.add(new KontopozycjaZapis(p, wybranyuklad));
//                    } catch (Exception e) {
//                        E.e(e);
//                    }
//                }
//                kontopozycjaZapisDAO.editList(nowepozycje);
//                kontoDAOfk.editList(wykazPorzadkowanychKont);
//                if (podatnik.equals(wpisView.getPodatnikObiekt())) {
//                    wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(podatnik, wpisView.getRokWpisuSt());
//                    Collections.sort(wykazkont, new Kontocomparator());
//                } else {
//                    wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnika(podatnik, wpisView.getRokWpisuSt());
//                    Collections.sort(wykazkontwzor, new Kontocomparator());
//                }
//                listaukladow = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//                // nie wiem czy to jest konieczne
////                wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
////                PozycjaRZiSFKBean.zmianaukladu("bilansowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, podatnik, wpisView.getRokWpisuSt());
////                PozycjaRZiSFKBean.zmianaukladu("wynikowe", wybranyuklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, podatnik, wpisView.getRokWpisuSt());
////                Collections.sort(wykazkont, new Kontocomparator());
//                //wykazkontlazy = new LazyKontoDataModel(wykazkontf);
//                // nie wiem czy to jest konieczne
//                Msg.msg("Zakończono porządkowanie kont");
//            } else {
//                Msg.msg("e","Brak kont potomnych");
//            }
//        } else {
//            Msg.msg("e","Nie wybrano konta");
//        }
//    }
  

//    public void porzadkowanieKontPodatnika(Podatnik podatnik, String rok) {
//        UkladBR uklad = wybranyuklad;
//        if (podatnik.equals(wpisView.getPodatnikwzorcowy())) {
//            uklad = wybranyukladwzorcowy;
//        }
//        List<Konto> kontadoporzadkowania = kontoDAOfk.findWszystkieKontaPodatnikaRO(podatnik, rok);
//        //resetuj kolumne macierzyste
//        for (Konto p : kontadoporzadkowania) {
//            p.czyscPozycje();
//        }
//        //sprawdzmacierzyste
//        KontaFKBean.sprawdzMacierzyste(kontadoporzadkowania, kontoDAOfk);
//        //tutaj nanosi czy ma potomkow
//        KontaFKBean.ustawCzyMaPotomkow(kontadoporzadkowania, kontoDAOfk);
//        kontadoporzadkowania = kontoDAOfk.findWszystkieKontaPodatnikaRO(podatnik, rok);
//        for (Konto p : kontadoporzadkowania) {
//            try {
//                KontopozycjaZapis kpo = PlanKontFKBean.naniesprzyporzadkowanie(p, kontoDAOfk, kontopozycjaZapisDAO, uklad);
//                if (p.isMapotomkow() == true && kpo != null && !kpo.getSyntetykaanalityka().equals("analityka")) {
//                    if (p.getBilansowewynikowe().equals("wynikowe")) {
//                        if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
//                            PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p,kontoDAOfk, podatnik, "wnma");
//                        } else {
//                            PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(p, kontoDAOfk, podatnik, "wynik");
//                        }
//                    } else if (p.isRozrachunkowe() || p..isKontovat() || p.getZwyklerozrachszczegolne().equals("szczególne")) {
//                        PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p,kontoDAOfk, podatnik, "wnma");
//                    } else {
//                        PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(p, kontoDAOfk, podatnik, "bilans");
//                    }
//                }
//            } catch (Exception e) {
//                E.e(e);
//            }
//        }
//        kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
//        kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
//        List<KontopozycjaZapis> nowepozycje = new ArrayList<>();
//        for (Konto p : kontadoporzadkowania) {
//            try {
//                nowepozycje.add(new KontopozycjaZapis(p, wybranyuklad));
//            } catch (Exception e) {
//                E.e(e);
//            }
//        }
//        kontopozycjaZapisDAO.createList(nowepozycje);
//        if (podatnik.equals(wpisView.getPodatnikObiekt())) {
//            listaukladow = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), rok);
//            wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
//            PozycjaRZiSFKBean.zmianaukladu("bilansowe", uklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, podatnik, rok);
//            PozycjaRZiSFKBean.zmianaukladu("wynikowe", uklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, podatnik, rok);
//            wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//            Collections.sort(wykazkont, new Kontocomparator());
//            //wykazkontlazy = new LazyKontoDataModel(wykazkont);
//        } else {
//            listaukladowwzorcowy = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(), rok);
//            wybranyukladwzorcowy = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladowwzorcowy);
//            PozycjaRZiSFKBean.zmianaukladuwzorcowy("bilansowe", uklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikwzorcowy(), rok);
//            PozycjaRZiSFKBean.zmianaukladuwzorcowy("wynikowe", uklad, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikwzorcowy(), rok);
//            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaPobierzRelacje(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
//            Collections.sort(wykazkontwzor, new Kontocomparator());
//        }
//        Msg.msg("Zakończono porządkowanie kont");
//    }
    
    
     public void porzadkowanieKontPodatnikaNowe(Podatnik podatnik, String rok) {
        List<Konto> kontadoporzadkowania = wykazkont.stream().filter(p->p.getPozycjaWn()==null||p.getPozycjaMa()==null).collect(Collectors.toList());
        //sprawdzmacierzyste czy macierzyste jest od tego samego podatnika - nie aktualizukmy
        KontaFKBean.sprawdzMacierzyste(kontadoporzadkowania, kontoDAOfk);
        //tutaj nanosi czy ma potomkow
        KontaFKBean.ustawCzyMaPotomkow(kontadoporzadkowania, kontoDAOfk);
        //nie wiem czy to jest potrzebne 23092023
        //kontadoporzadkowania = kontoDAOfk.findWszystkieKontaPodatnikaRO(podatnik, rok);
        for (Konto p : kontadoporzadkowania) {
            try {
                KontopozycjaZapis kpo = PlanKontFKBean.naniesprzyporzadkowanieNowe(p, kontadoporzadkowania, kontopozycjaZapisDAO, wybranyuklad, kontoDAOfk);
                if (p.isMapotomkow() == true && kpo != null && !kpo.getSyntetykaanalityka().equals("analityka")) {
                    if (p.getBilansowewynikowe().equals("wynikowe")) {
                        if (p.getZwyklerozrachszczegolne().equals("szczególne")) {
                            PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p,kontoDAOfk, podatnik, "wnma");
                        } else {
                            PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykleNowe(p, kontadoporzadkowania);
                        }
                    } else if (p.isRozrachunkowe() || p.isKontovat() || p.getZwyklerozrachszczegolne().equals("szczególne")) {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(p,kontoDAOfk, podatnik, "wnma");
                    } else {
                        PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykleNowe(p, kontadoporzadkowania);
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
        kontoDAOfk.editList(kontadoporzadkowania);
        Msg.msg("Zakończono porządkowanie kont");
    }

//    public void porzadkowanieKontWzorcowych() {
//        wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
//        //resetuj kolumne macierzyste
//        KontaFKBean.ustawCzyMaPotomkowWzorcowy(wykazkontwzor, kontoDAOfk, null, wpisView, kontopozycjaZapisDAO, wybranyuklad);
//        wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
//    }

    public void usunZsubkontami(String klientWzor) {
        Konto kontoDoUsuniecia = selectednodekonto;
        List<Rodzajedok> rodzajedokumentowpodatnika = null;
        if (klientWzor.equals("K")) {
            rodzajedokumentowpodatnika = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        List<Konto> zwrot = Collections.synchronizedList(new ArrayList<>());
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
            if (kontoDoUsuniecia.getKontomacierzyste()!=null) {
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
        List<Konto> z = Collections.synchronizedList(new ArrayList<>());
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
                kontopozycjaZapisDAO.remove(p);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void usunkontozbazy(Konto kontoDoUsuniecia, String klientWzor) {
        kontoDoUsuniecia.setKontomacierzyste(null);
        kontoDAOfk.edit(kontoDoUsuniecia);
        kontoDAOfk.remove(kontoDoUsuniecia);
        if (klientWzor.equals("W")) {
            wykazkontwzor.remove(kontoDoUsuniecia);
        } else {
            wykazkont.remove(kontoDoUsuniecia);
        }
    }

    private void oznaczbraksiostr(boolean sadzieci, Konto kontoDoUsuniecia, String klientWzor) {
        Konto kontomacierzyste = kontoDoUsuniecia.getKontomacierzyste();
        if (kontomacierzyste==null && kontoDoUsuniecia.getLevel()>0) {
            kontomacierzyste = kontoDAOfk.findKonto(kontoDoUsuniecia.getMacierzysty());
        }
        List<Konto> siostry = sprawdzczysasiostry(klientWzor, kontomacierzyste);
        if (siostry.size() < 1) {
            //jak nie ma wiecej dzieci podpietych pod konto macierzyse usuwanego to zaznaczamy to na koncie macierzystym;
            odznaczmacierzyste(sadzieci, kontomacierzyste, kontoDoUsuniecia);
        }
    }

    private void usunslownikowe(Konto kontoDoUsuniecia) {
        int wynik = PlanKontFKBean.usunelementyslownika(kontoDoUsuniecia.getKontomacierzyste(), kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), wykazkont, kontopozycjaZapisDAO, wybranyuklad);
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
        Konto kontoDoUsuniecia = selectednodekonto;
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
            return kontoDAOfk.findKontaPotomnePodatnik(null, wpisView.getRokWpisu(), kontomacierzyste);
        } else {
            return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontomacierzyste);
        }
    }

    private void odznaczmacierzyste(boolean sadzieci, Konto kontomacierzyste, Konto kontoDoUsuniecia) {
        if (sadzieci == false && kontomacierzyste!=null) {
            kontomacierzyste.setBlokada(false);
            kontomacierzyste.setMapotomkow(false);
            kontoDAOfk.edit(kontomacierzyste);
        }
    }

    
    public void obslugaBlokadyKontaWzorcowy() {
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
    
    public void zmiananazwykontawzorcowy() {
        czyoddacdowzorca = true;
        zmiananazwykonta();
        czyoddacdowzorca = false;
    }

    public void zmiananazwykonta() {
        Konto selectednodekontoL = this.selectednodekontoZmiana;
        if (selectednodekontoL!=null) {
            UkladBR wybranyukladL = ustawuklad();
            try {
                List<Konto> kontapotomne = wykazkont.stream().filter(p->p.getKontomacierzyste()!=null&&p.getKontomacierzyste().equals(selectednodekontoL)).collect(Collectors.toList());
                List<KontopozycjaZapis> pozycjedousuniecia = new ArrayList<>();
                List<KontopozycjaZapis> pozycjedoedycji = new ArrayList<>();
                List<KontopozycjaZapis> pozycjerok = kontopozycjaZapisDAO.findByUklad(wybranyukladL);
                for (Konto p : kontapotomne) {
                    if (p != null) {
                        p.setZwyklerozrachszczegolne(selectednodekontoL.getZwyklerozrachszczegolne());
                        p.setDwasalda(selectednodekontoL.isDwasalda());
                        p.setRozrachunkowe(selectednodekontoL.isRozrachunkowe());
                        if (selectednodekontoL.isWynik0bilans1()==true&&selectednodekontoL.getSyntetykaanalityka().equals("zwykłe")) {
                            p.setSyntetykaanalityka("syntetyka");
                            KontopozycjaZapis kp = pozycjerok.stream().filter(r->r.getKontoID().equals(p)).findFirst().orElse(null);
                            if (kp != null) {
                                kp.setSyntetykaanalityka(p.getSyntetykaanalityka());
                                pozycjedoedycji.add(kp);
                            }
                        } else if (selectednodekontoL.isWynik0bilans1()==true&&selectednodekontoL.getSyntetykaanalityka().equals("analityka")) {
                            p.setSyntetykaanalityka("zwykłe");
                            KontopozycjaZapis kp = pozycjerok.stream().filter(r->r.getKontoID().equals(p)).findFirst().orElse(null);
                            if (kp != null) {
                                kp.setSyntetykaanalityka(p.getSyntetykaanalityka());
                                pozycjedoedycji.add(kp);
                            }
                        }  else if (selectednodekontoL.isWynik0bilans1()==true&&selectednodekontoL.getSyntetykaanalityka().equals("syntetyka")) {
                            p.setSyntetykaanalityka("syntetyka");
                            KontopozycjaZapis kp = pozycjerok.stream().filter(r->r.getKontoID().equals(p)).findFirst().orElse(null);
                            if (kp != null) {
                                kp.setSyntetykaanalityka(p.getSyntetykaanalityka());
                                pozycjedoedycji.add(kp);
                            }
                        }
                        
                        p.setBilansowewynikowe(selectednodekontoL.getBilansowewynikowe());
                        if (usunprzyporzadkowanie) {
                            KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(p, wybranyukladL);
                            if (kp != null) {
                                pozycjedousuniecia.add(kp);
                            }
                            p.czyscPozycje();
                        }
                       
                    }
                }
                if (selectednodekontoL.getKontomacierzyste()==null&&selectednodekontoL.getPelnynumer().indexOf("-")>=-1) {
                    int lastIndex = selectednodekontoL.getPelnynumer().lastIndexOf("-");
                        String macierzyste = selectednodekontoL.getPelnynumer().substring(0, lastIndex);
                        Konto kontomacierzyste = kontoDAOfk.findKonto(macierzyste, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        if (kontomacierzyste!=null) {
                            selectednodekontoL.setKontomacierzyste(kontomacierzyste);
                            selectednodekontoL.setMacierzysty(kontomacierzyste.getId());
                            kontomacierzyste.setMapotomkow(true);
                            kontoDAOfk.edit(kontomacierzyste);
                        }
                }
                if (pozycjedoedycji.isEmpty()==false) {
                    kontopozycjaZapisDAO.editList(pozycjedoedycji);
                }
                if (kontapotomne.isEmpty()==false) {
                    kontopozycjaZapisDAO.removeList(pozycjedousuniecia);
                    kontoDAOfk.editList(kontapotomne);
                }
                //bo u gory to tylko potomne czysci
                if (usunprzyporzadkowanie) {
                    KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(selectednodekontoL, wybranyukladL);
                    if (kp != null) {
                        kontopozycjaZapisDAO.remove(kp);
                    }
                    selectednodekontoL.czyscPozycje();
                }
                if (kontapotomne.isEmpty()==false) {
                    selectednodekontoL.setMapotomkow(true);
                }
                czykontomapotomkow(selectednodekontoL, kontoDAOfk, wpisView.getPodatnikObiekt());
                kontoDAOfk.edit(selectednodekontoL);
                usunprzyporzadkowanie = false;
            } catch (Exception e) {
                E.e(e);
            }
        }
    }

    private boolean czykontomapotomkow(Konto konto, KontoDAOfk kontoDAO, Podatnik podatnik) {
        boolean zwrot = false;
         List<Konto> kontaPotomneByPodatnik = kontoDAO.findKontaPotomneByPodatnik(podatnik, konto);
         if (kontaPotomneByPodatnik!=null&&kontaPotomneByPodatnik.size()>0) {
             zwrot = true;
         }
         konto.setMapotomkow(zwrot);
         kontoDAO.edit(konto);
         return zwrot;
    }



    public void selrow() {
        Map<String,String> par_map = new HashMap<String,String>(); par_map=FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String id=par_map.get("form_dialog_plankont:dataList_selection");
        wybranapozycja_wiersz = "row_"+id;
        Msg.msg("i", "Wybrano: " + selectednodekonto.getPelnynumer() + " " + selectednodekonto.getNazwapelna());
    }

    public void selrowwzorcowy() {
        Msg.msg("i", "Wybrano: " + selectednodekonto.getPelnynumer() + " " + selectednodekonto.getNazwapelna());
    }

    public void zachowajZmianyWKoncieWzorcowy(Konto konto) {
        kontoDAOfk.edit(konto);
        List<Konto> kontapotomne = kontoDAOfk.findKontaPotomne(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu(), konto, konto.getBilansowewynikowe());
        for (Konto p : kontapotomne) {
            p.setZwyklerozrachszczegolne(konto.getZwyklerozrachszczegolne());
            p.setDwasalda(konto.isDwasalda());
            p.setRozrachunkowe(konto.isRozrachunkowe());
            kontoDAOfk.edit(p);
        }
        wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
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
        List<Kliencifk> obecniprzyporzadkowaniklienci = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt());
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
        Collections.sort(wykazkont, new Kontocomparator());
        Msg.msg("Zakonczono aktualizowanie słowników");
    }

    public void oznaczkontoJakoKosztowe() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1(selectednodekonto, kontoDAOfk, true, wpisView);
            planBezSlownikowychSyntetyczne(ustawpodatnikaselected());
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void oznaczkontoJakoKosztoweWzorcowy() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1Wzorcowy(selectednodekonto, kontoDAOfk, true, wpisView);
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void oznaczkontoJakoPrzychodowe() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1(selectednodekonto, kontoDAOfk, false, wpisView);
            planBezSlownikowychSyntetyczne(ustawpodatnikaselected());
            Msg.msg("Naniesiono oznaczenia na konta");
        }
    }

    public void oznaczkontoJakoPrzychodoweWzorcowy() {
        if (selectednodekonto.getId() == null) {
            Msg.msg("e", "Nie wybrano konta");
        } else {
            KontaFKBean.oznaczkontoPrzychod0Koszt1Wzorcowy(selectednodekonto, kontoDAOfk, false, wpisView);
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
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
            case "tłumaczenie":
                usunslownikowe();
                PdfPlanKont.drukujPlanKontTłumaczenie(wykazkont, wpisView);
                break;
            default:
                String numerkontas = parametr+"%";
                wykazkont = kontoDAOfk.findKontaGrupa(wpisView, numerkontas);
                usunslownikowe();
                PdfPlanKont.drukujPlanKont(wykazkont, wpisView);
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
                if (tylkosyntetyka && p.getKontomacierzyste()!=null && !bezslownikowych) {
                    it.remove();
                }
                if (bezslownikowych && tylkosyntetyka) {
                    if (p.isSlownikowe() || p.getKontomacierzyste()!=null) {
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
                List<Konto> kontadousuniecia = kontoDAOfk.findKontaSiostrzanePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), selectednodekonto.getKontomacierzyste());
                kontoDAOfk.remove(kontadousuniecia);
                for (Konto p : kontadousuniecia) {
                    wykazkont.remove(p);
                }
                Konto macierzyste = kontoDAOfk.findKonto(selectednodekonto.getKontomacierzyste().getPelnynumer(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
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
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), macierzyste);
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
                r.setDwasalda(p.isDwasalda());
                r.setRozrachunkowe(p.isRozrachunkowe());
                nanieswnmaWzorcowy(r);
            }
            kontoDAOfk.edit(potomki);
        }
    }
    
    private List<Konto> pobierzpotomkowWzorcowy(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomnePodatnik(null, wpisView.getRokWpisu(), macierzyste);
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
            wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
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
                r.setDwasalda(p.isDwasalda());
                r.setRozrachunkowe(p.isRozrachunkowe());
                nanieswnmaImpl(r,wnma0wn1ma2, kontapodatnika);
            }
        }
    }
    
    private List<Konto> pobierzpotomkowImpl(Konto macierzyste, List<Konto> kontapodatnika) {
        List<Konto> potomne = Collections.synchronizedList(new ArrayList<>());
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

    public boolean isBezprzyporzadkowania() {
        return bezprzyporzadkowania;
    }

    public void setBezprzyporzadkowania(boolean bezprzyporzadkowania) {
        this.bezprzyporzadkowania = bezprzyporzadkowania;
    }

    public int getWybranaseriakont() {
        return wybranaseriakont;
    }

    public void setWybranaseriakont(int wybranaseriakont) {
        this.wybranaseriakont = wybranaseriakont;
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

    public List<Konto> getWykazkontZapas() {
        return wykazkontZapas;
    }

    public void setWykazkontZapas(List<Konto> wykazkontZapas) {
        this.wykazkontZapas = wykazkontZapas;
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

      public boolean isTylkosyntetyka() {
        return tylkosyntetyka;
    }

    public void setTylkosyntetyka(boolean tylkosyntetyka) {
        this.tylkosyntetyka = tylkosyntetyka;
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

    public Konto getSelectednodekontoZmiana() {
        return selectednodekontoZmiana;
    }

    public void setSelectednodekontoZmiana(Konto selectednodekontoZmiana) {
        this.selectednodekontoZmiana = selectednodekontoZmiana;
    }

    
    
    //</editor-fold>

//    public LazyDataModel<Konto> getWykazkontlazy() {
//        return wykazkontlazy;
//    }
//
//    public void setWykazkontlazy(LazyDataModel<Konto> wykazkontlazy) {
//        this.wykazkontlazy = wykazkontlazy;
//    }


    
 public void implementujwmma0mn1ma0Wszystkie() {
        try {
            List<Podatnik> podatnicy = podatnikDAO.findPodatnikFK();
            List<Integer> lata = Roki.getRokiListS();
            for (Integer rok : lata) {
                wykazkontwzor = kontoDAOfk.findWszystkieKontaPodatnikaBezSlownik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
                if (rok!=null && rok > 2015) {
                    for (Podatnik pod : podatnicy) {
                        List<Konto> kontapodatnika = kontoDAOfk.findWszystkieKontaPodatnika(pod, String.valueOf(rok));
                        if (kontapodatnika!=null && kontapodatnika.size()>0) {
                            for (Konto p : wykazkontwzor) {
                                if (p.getKontomacierzyste() == null) {
                                    Konto r = pobierzkontopodatnika(kontapodatnika,p);
                                    if (r != null) {
                                        nanieswnmaImpl(r, p.getWnma0wm1ma2(), kontapodatnika);
                                    }
                                }
                            }
                            kontoDAOfk.editList(kontapodatnika);

                        }
                    }
                }
            }
            Msg.dP();
            error.E.s("koniec implementujwmma0mn1ma0Wszystkie");
        } catch (Exception e) {
            Msg.dPe();
            error.E.s("BLAD implementujwmma0mn1ma0Wszystkie");
        }
    }    

 
    private void usunpozycjezapisane() {
        List<KontopozycjaZapis> pozycje = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (!pozycje.isEmpty()) {
            kontopozycjaZapisDAO.remove(pozycje);
        }
    }

    

     //IMPORT KONT
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName()).toLowerCase();
            if (extension.equals("xls")||extension.equals("xlsx")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes = uploadedFile.getContents();
                getListafaktur();
                //plikinterpaper = uploadedFile.getContents();
                Msg.msg("Sukces. Plik xls " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Niewłaściwy typ pliku");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
     private byte[] pobraneplikibytes;
     private List<Konto> kontalista;

    public byte[] getPobraneplikibytes() {
        return pobraneplikibytes;
    }

    public void setPobraneplikibytes(byte[] pobraneplikibytes) {
        this.pobraneplikibytes = pobraneplikibytes;
    }

    public List<Konto> getKontalista() {
        return kontalista;
    }

    public void setKontalista(List<Konto> kontalista) {
        this.kontalista = kontalista;
    }
    

     public void zachowajPlanKontwXLS() {
        try {
            List<Konto> plankont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Collections.sort(plankont, new Kontocomparator());
            Map<String, List> listy = new ConcurrentHashMap<>();
            listy.put("plankont", plankont);
            Workbook workbook = WriteXLSFile.zachowajPlanKontImportXLS(listy, wpisView);
            // Prepare response.
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.setResponseContentType("application/vnd.ms-excel");
            String filename = "plankont"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xlsx";
            externalContext.setResponseHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            // Write file to response body.
            workbook.write(externalContext.getResponseOutputStream());
            // Inform JSF that response is completed and it thus doesn't have to navigate.
            facesContext.responseComplete();
        } catch (IOException ex) {
            // Logger.getLogger(XLSPlanKontView.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
     public void getListafaktur() {
         try {
            kontalista = new ArrayList<>();
            List<Konto> obecnyplantkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            InputStream file = new ByteArrayInputStream(pobraneplikibytes);
             //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                System.out.println(i);
                if (row.getCell(5)!=null&&row.getCell(5).getRowIndex()>1) {
                    String nazwapelna = row.getCell(5).getStringCellValue();
                    if (nazwapelna.isEmpty()==false) {
                        Object cell = X.x(row.getCell(1));
                        if (cell==null||cell.equals("")) {
                            String kontomacierzyste = X.xString(row.getCell(4));
                            if (!kontomacierzyste.equals("")) {
                                Konto macierzyste = kontoDAOfk.findKonto(kontomacierzyste, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                                String opiskonta = row.getCell(5).getStringCellValue();
                                String nazwaskrocona = row.getCell(6)!=null?row.getCell(6).getStringCellValue():opiskonta;
                                Konto nowekonto = new Konto();
                                nowekonto.setNazwapelna(opiskonta);
                                nowekonto.setNazwaskrocona(nazwaskrocona);
                                nowekonto.setMacierzysty(-999);
                                if (macierzyste!=null) {
                                    nowekonto.setKontomacierzyste(macierzyste);
                                    nowekonto.setMacierzysty(macierzyste.getLp());
                                    macierzyste.setMapotomkow(true);
                                    macierzyste.setBlokada(true);
                                    nowekonto.setSyntetyczne("analityczne");
                                    nowekonto.setPodatnik(wpisView.getPodatnikObiekt());
                                    nowekonto.setRok(wpisView.getRokWpisu());
                                    if (macierzyste.getLevel()==0) {
                                        nowekonto.setSyntetycznenumer(macierzyste.getPelnynumer());
                                    } else {
                                        nowekonto.setSyntetycznenumer(macierzyste.getSyntetycznenumer());
                                    }
                                    nowekonto.setSyntetyczne("analityczne");
                                    nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
                                    nowekonto.setNrkonta(oblicznumerkonta(macierzyste, obecnyplantkont));
                                    nowekonto.setPrzychod0koszt1(macierzyste.isPrzychod0koszt1());
                                    nowekonto.setMapotomkow(false);
                                    nowekonto.setLevel(PlanKontFKBean.obliczlevel(macierzyste.getPelnynumer()));
                                    nowekonto.setPelnynumer(macierzyste.getPelnynumer() + "-" + nowekonto.getNrkonta());
                                    nowekonto.setWnma0wm1ma2(macierzyste.getWnma0wm1ma2());
                                    nowekonto.setDwasalda(macierzyste.isDwasalda());
                                    nowekonto.setRozrachunkowe(macierzyste.isRozrachunkowe());
                                    int przetworzkonto = PlanKontFKBean.przetworzkonto(obecnyplantkont, nowekonto);
                                    if (przetworzkonto==0) {
                                        obecnyplantkont.add(nowekonto);
                                    } else {
                                        
                                    }
                                } else {
                                    nowekonto.setPelnynumer(kontomacierzyste+ "-brak");
                                }
                                kontalista.add(nowekonto);
                            }
                        } else {
                            int cellid = Integer.parseInt((String) X.x(row.getCell(1)));
                            String nazwapelna2 = X.xString(row.getCell(5));
                            Konto kontoznalezione = null;
                            for (Konto o : obecnyplantkont) {
                                try {
                                    if (o.getId()==cellid||o.getNazwapelna().equals(nazwapelna2)) {
                                        kontoznalezione = o;
                                    }
                                } catch (Exception e){}
                            }
                            if(kontoznalezione!=null&&!kontoznalezione.getNazwapelna().equals(nazwapelna2)) {
                                kontoznalezione.setNazwapelna(nazwapelna2);
                                kontoznalezione.setNazwaskrocona(nazwapelna2);
                                kontalista.add(kontoznalezione); 
                        }
                    }
                        //kontoDAOfk.edit(macierzyste);
                        //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                        
                    } 
                    i++;
                    //dokumenty.add(interpaperXLS);
                 }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
            Msg.msg("e",E.e(e));
        }
    }

    private String oblicznumerkonta(Konto macierzyste, List<Konto> obecnyplantkont) {
        int ilosc = 0;
        for (Konto k : obecnyplantkont) {
            if (k.getKontomacierzyste()!=null&&k.getKontomacierzyste().equals(macierzyste)) {
                ilosc = ilosc +1;
            }
        }
        int nowynumer = ilosc+1;
        return String.valueOf(nowynumer);
    }
    
    public void zapiszPlanKontwXLS() {
        if (kontalista!=null) {
            for (Konto p : kontalista) {
                if (p.getId()==null) {
                    kontoDAOfk.create(p);
                } else {
                    kontoDAOfk.edit(p);
                }
            }
            Msg.msg("Zachowano listę kont");
        }
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    
     
    
  
}

