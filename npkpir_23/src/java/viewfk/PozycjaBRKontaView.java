/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.UkladBRBean;
import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.model.TreeNode;
import pdffk.PdfBilans;
import pdffk.PdfRZiS;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaBRKontaView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<PozycjaRZiSBilans> pozycje;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private UkladBR wybranyuklad;
    private String wnmaPrzypisywanieKont;
    private Konto boxNaKonto;
    private boolean aktywa0pasywa1;
    private List<Konto> kontabezprzydzialu;
    private List<Konto> przyporzadkowanekonta;
    private TreeNodeExtended rootProjektKontaRZiS;
    private TreeNodeExtended rootProjektKontaBilans;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private int level = 0;
    private String wybranapozycja;
    private String wybranapozycja_wiersz;
    private int lpwybranapozycja;
    private TreeNode wybranynodekonta;
    private UkladBR ukladzrodlowykonta;
    private UkladBR ukladdocelowykonta;
    private List<UkladBR> listaukladow;

    public PozycjaBRKontaView() {
         ////E.m(this);
        this.kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
        this.rootProjektKontaRZiS = new TreeNodeExtended("root", null);
        this.rootProjektKontaBilans = new TreeNodeExtended("root", null);
        this.pozycje = Collections.synchronizedList(new ArrayList<>());
        this.przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        listaukladow = ukladBRDAO.findPodatnik(wpisView.getPodatnikObiekt());
        wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
        if (listaukladow != null && wybranyuklad != null) {
            for (UkladBR p : listaukladow) {
                if (p.getRok().equals(wpisView.getRokUprzedniSt()) && p.getUklad().equals(wybranyuklad.getUklad())) {
                    ukladzrodlowykonta = p;
                    break;
                }
            }
        } else {
            listaukladow = Collections.synchronizedList(new ArrayList<>());
        }
    }
    
    public void pobierzukladkontoR() {
        try {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            wybranyuklad.oznaczUkladBR(ukladBRDAO);
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            PozycjaRZiSFKBean.wyczyscKonta("wynikowe", podatnik, wpisView.getRokWpisuSt(), kontoDAO);
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, wybranyuklad, "wynikowe");
            pozycje = Collections.synchronizedList(new ArrayList<>());
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(wybranyuklad));
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            drugiinit();
            uzupelnijpozycjeOKontaR(pozycje);
            rootProjektKontaRZiS.getChildren().clear();
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKontaRZiS, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(rootProjektKontaRZiS, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
        }

    }

    public void pobierzukladkontoB(String aktywapasywa) {
        try {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            wybranyuklad.oznaczUkladBR(ukladBRDAO);
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            PozycjaRZiSFKBean.wyczyscKonta("bilansowe", podatnik, wpisView.getRokWpisuSt(), kontoDAO);
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, wybranyuklad,"bilansowe");
            pozycje = Collections.synchronizedList(new ArrayList<>());
            if (aktywapasywa.equals("aktywa")) {
                aktywa0pasywa1 = false;
                pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(wybranyuklad));
            } else {
                aktywa0pasywa1 = true;
                pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(wybranyuklad));
            }
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            drugiinitbilansowe();
            uzupelnijpozycjeOKonta(pozycje);
            rootProjektKontaBilans.getChildren().clear();
            PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKontaBilans, pozycje);
            level = PozycjaRZiSFKBean.ustawLevel(rootProjektKontaBilans, pozycje);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
        }

    }

    private void drugiinit() {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(podatnik, wpisView.getRokWpisu(), null, "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiRZiS(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, podatnik, Integer.parseInt(wybranyuklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void drugiinitbilansowe() {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(podatnik, wpisView.getRokWpisu(), null, "bilansowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiBilans(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO,  podatnik, aktywa0pasywa1, Integer.parseInt(wybranyuklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowaneAll("bilansowe", podatnik, wpisView.getRokWpisu());
        if (!lista.isEmpty()) {
            pozycje.stream().forEach((p)->{
                PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(lista, p, wpisView, aktywa0pasywa1);
            });
            pozycjaBilansDAO.editList(pozycje);
        }
    }

    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowaneAll("wynikowe", podatnik, wpisView.getRokWpisu());
        if (!lista.isEmpty()) {
            pozycje.stream().forEach((p)->{
                PozycjaRZiSFKBean.wyszukajprzyporzadkowaneRLista(lista, p);
            });
            pozycjaBilansDAO.editList(pozycje);
        }
    }

    public void onKontoDropR(Konto konto, String br) {
        //to jest dla rachunku zyskow i strat wiec konto moze byc jedynie zwykle lub szczegolne
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            boxNaKonto = konto;
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (konto.getPozycjaWn() == null && konto.getPozycjaMa()==null) {
                    PrimeFaces.current().ajax().update("kontownmawyborRZiS");
                    PrimeFaces.current().executeScript("PF('kontownmawyborRZiS').show();");
                    Msg.msg("Konto niezwykle");
                } else {
                    if (konto.getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalneRZiS(wybranyuklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneRZiS(wybranyuklad);
                    }
                    uzupelnijpozycjeOKontaR(pozycje);   
                }
                //to duperele porzadkujace sytuacje w okienkach
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                PlanKontFKBean.przyporzadkujRZiS_kontozwykle(wybranapozycja, konto, kontoDAO, podatnik, null, wybranyuklad, kontopozycjaZapisDAO);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                uzupelnijpozycjeOKontaR(pozycje);   
            }
            PrimeFaces.current().ajax().update(wybranapozycja_wiersz);
        }
    }

    public void onKontoDropB(Konto konto) {
        boolean wzorcowy = false;
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            boxNaKonto = konto;
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("szczególne") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                if (konto.getPozycjaWn() == null && konto.getPozycjaMa()==null) {
                    PrimeFaces.current().ajax().update("kontownmawybor");
                    PrimeFaces.current().executeScript("PF('kontownmawybor').show();");
                    Msg.msg("Konto niezwykle");
                } else {
                    if (konto.getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalne(wzorcowy, wybranyuklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalne(wzorcowy, wybranyuklad);
                    }
                    if (konto.getPozycjaWn() != null && konto.getPozycjaMa() != null ) {
                        kontabezprzydzialu.remove(konto);
                    }
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                PlanKontFKBean.przyporzadkujBilans_kontozwykle(wybranapozycja, konto, kontoDAO, podatnik, null, aktywa0pasywa1, wybranyuklad, kontopozycjaZapisDAO);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                uzupelnijpozycjeOKonta(pozycje);
            }
            PrimeFaces.current().ajax().update(wybranapozycja_wiersz);
            PrimeFaces.current().ajax().update(".ui-state-highlight");
        }

    }

    public void onKontoDropKontaSpecjalneRZiS(UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            //musi byc boxNaKonto bo ta funcja jest dostepna tez z okienka wyboru strony a tam nie ma info o koncie
            Konto konto = boxNaKonto;
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                PlanKontFKBean.przyporzadkujRZiS_kontoszczegolne(wybranapozycja,konto, kontoDAO, podatnik, wnmaPrzypisywanieKont, wybranyuklad, kontopozycjaZapisDAO);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //wywalamy tylko obustronnnie przyporzadkowane konta
                if (konto.getPozycjaWn() != null && konto.getPozycjaMa() != null) {
                    kontabezprzydzialu.remove(konto);
                }
                //czesc nanoszaca informacje na potomku
            }
            uzupelnijpozycjeOKontaR(pozycje);
            PrimeFaces.current().ajax().update(wybranapozycja_wiersz);
        }
    }

   

    public void onKontoDropKontaSpecjalne(boolean wzorcowy, UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            Konto konto = boxNaKonto;
            if (konto.getPelnynumer().equals("201") || konto.getPelnynumer().equals("201-1") || konto.getPelnynumer().equals("201-1-0")){
            }
            if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
            }
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                PlanKontFKBean.przyporzadkujBilans_kontoszczegolne(wybranapozycja,konto, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1,"rozrachunkowe/vat", podatnik, wybranyuklad, kontopozycjaZapisDAO);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                PlanKontFKBean.przyporzadkujBilans_kontoszczegolne(wybranapozycja,konto, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1,"szczególne", podatnik, wybranyuklad, kontopozycjaZapisDAO);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
            }
            uzupelnijpozycjeOKonta(pozycje);
            PrimeFaces.current().ajax().update(wybranapozycja_wiersz);
            PrimeFaces.current().ajax().update("formbilansuklad:dostepnekonta");
        }
    }

   
    public void onKontoRemoveB(Konto konto, String br) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
            przyporzadkowanekonta.remove(konto);
            String wnma = "";
            if (konto.getPozycjaWn() != null && konto.getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.setPozycjaWn(null);
            } else if (konto.getPozycjaMa() != null && konto.getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.setPozycjaMa(null);
            }
            if (konto.getPozycjaWn() == null && konto.getPozycjaMa() == null) {
                konto.czyscPozycje();
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kontoDAO, podatnik, wnma);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getKontomacierzyste() != null) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
            String wnma = "";
            if (konto.getPozycjaWn() != null && konto.getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.setPozycjaWn(null);
                if (!(konto.getPozycjaMa() != null && konto.getPozycjaMa().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            } else if (konto.getPozycjaMa() != null && konto.getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.setPozycjaMa(null);
                if (!(konto.getPozycjaWn() != null && konto.getPozycjaWn().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            }
            if (konto.getPozycjaWn() == null && konto.getPozycjaMa() == null) {
                konto.czyscPozycje();
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto,kontoDAO, podatnik, wnma);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.czyscPozycje();
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto, kontoDAO, podatnik, "bilans");
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
            }
            
        } else {
            Msg.msg("Konto niezwykle");
        }
        if (kontabezprzydzialu.contains(konto)) {
            kontabezprzydzialu.remove(konto);
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        } else {
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
        uzupelnijpozycjeOKonta(pozycje);
        PrimeFaces.current().ajax().update(wybranapozycja_wiersz);
    }

    public void onKontoRemoveR(Konto konto, String br) {
        if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            String wnma = "";
            if (konto.getPozycjaWn() != null && konto.getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.setPozycjaWn(null);
                if (!(konto.getPozycjaMa() != null && konto.getPozycjaMa().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            } else if (konto.getPozycjaMa() != null && konto.getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.setPozycjaMa(null);
                if (!(konto.getPozycjaWn() != null && konto.getPozycjaWn().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            }
            if (konto.getPozycjaWn() == null && konto.getPozycjaMa() == null) {
                konto.czyscPozycje();
            }
            kontoDAO.edit(konto);
            if (kontabezprzydzialu.contains(konto)) {
                kontabezprzydzialu.remove(konto);
                kontabezprzydzialu.add(konto);
                Collections.sort(kontabezprzydzialu, new Kontocomparator());
            } else {
                kontabezprzydzialu.add(konto);
                Collections.sort(kontabezprzydzialu, new Kontocomparator());
            }
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kontoDAO, podatnik, wnma);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            Podatnik podatnik = wybranyuklad.getPodatnik();
            przyporzadkowanekonta.remove(konto);
            konto.czyscPozycje();
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto, kontoDAO, podatnik, "wynik");
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
            }
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
        uzupelnijpozycjeOKontaR(pozycje);
        PrimeFaces.current().ajax().update(wybranapozycja_wiersz);
    }

    public void wybranopozycjeRZiS() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        TreeTable table = (TreeTable) ctx.getViewRoot().findComponent("formrzisuklad:dataList");
        String rowkey = table.getRowKey();
        wybranapozycja_wiersz = "formrzisuklad:dataList:"+rowkey+":liczba";
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja, aktywa0pasywa1, wybranyuklad));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }

    public void wybranopozycjeBilans() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        TreeTable table = (TreeTable) ctx.getViewRoot().findComponent("formbilansuklad:dataList");
        String rowkey = table.getRowKey();
        wybranapozycja_wiersz = "formbilansuklad:dataList:"+rowkey+":liczba";
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja, aktywa0pasywa1, wybranyuklad));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaBilans) wybranynodekonta.getData()).getNazwa());
    }

    public void rozwinwszystkie(TreeNodeExtended root) {
        try {
            level = root.ustaldepthDT(pozycje) - 1;
            root.expandAll();
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void rozwin(TreeNodeExtended root) {
        int maxpoziom = root.ustaldepthDT(pozycje);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }

    public void zwinwszystkie(TreeNodeExtended root) {
        try {
            root.foldAll();
            level = 0;
        } catch (Exception e) {
            E.e(e);

        }
    }

    public void zwin(TreeNodeExtended root) {
        root.foldLevel(--level);
    }

    public void rozwinrzadanalityki(Konto konto) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(podatnik, wpisView.getRokWpisu(), konto);
        if (lista.size() > 0) {
            kontabezprzydzialu.addAll(kontoDAO.findKontaPotomnePodatnik(podatnik, wpisView.getRokWpisu(), konto));
            kontabezprzydzialu.remove(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void zwinrzadanalityki(Konto konto) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzanePodatnik(podatnik, wpisView.getRokWpisu(), konto.getKontomacierzyste());
        List<Konto> listaPotomne = Collections.synchronizedList(new ArrayList<>());
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomnePodatnik(new ArrayList<Konto>(), podatnik, wpisView.getRokWpisu(), t));
        }
        listaSiostrzane.addAll(listaPotomne);
        boolean jestprzypisane = false;
        List<String> analitykinazwy = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : listaSiostrzane) {
            if (p.getPozycjaWn() != null || p.getPozycjaMa() !=null) {
                jestprzypisane = true;
                analitykinazwy.add(p.getPelnynumer());
            }
        }
        if (jestprzypisane) {
            String result = StringUtils.join(analitykinazwy, ", ");
            Msg.msg("e", "Nie można zwinąć analityk. Istnieją analityki przypisane do kont: " + result);
        } else {
            Konto macierzyste = kontoDAO.findKonto(konto.getKontomacierzyste().getPelnynumer(), podatnik, wpisView.getRokWpisu());
            macierzyste.setPozycjaWn(null);
            macierzyste.setPozycjaMa(null);
            macierzyste.setStronaWn(null);
            macierzyste.setStronaMa(null);
            macierzyste.setSyntetykaanalityka(null);
            kontoDAO.edit(macierzyste);
            for (Konto p : listaSiostrzane) {
                p.setPozycjaWn(null);
                p.setPozycjaMa(null);
                p.setStronaWn(null);
                p.setStronaMa(null);
                p.setSyntetykaanalityka(null);
                kontabezprzydzialu.remove(p);
                KontopozycjaZapis poz = kontopozycjaZapisDAO.findByKonto(p, wybranyuklad);
                if (poz!=null) {
                    kontopozycjaZapisDAO.destroy(poz);
                }
            }
            kontoDAO.editList(listaSiostrzane);
            kontabezprzydzialu.add(macierzyste);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
    }
    
    public void zwinrzadanalitykiwymus(Konto konto) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzanePodatnik(podatnik, wpisView.getRokWpisu(), konto.getKontomacierzyste());
        List<Konto> listaPotomne = Collections.synchronizedList(new ArrayList<>());
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomnePodatnik(new ArrayList<Konto>(), podatnik, wpisView.getRokWpisu(), t));
        }
        listaSiostrzane.addAll(listaPotomne);
        for (Konto p : listaSiostrzane) {
            p.setPozycjaWn(null);
            p.setPozycjaMa(null);
            p.setStronaWn(null);
            p.setStronaMa(null);
            p.setSyntetykaanalityka(null);
            kontabezprzydzialu.remove(p);
            KontopozycjaZapis poz = kontopozycjaZapisDAO.findByKonto(p, wybranyuklad);
            if (poz!=null) {
                kontopozycjaZapisDAO.destroy(poz);
            }
            
        }
        Konto macierzyste = kontoDAO.findKonto(konto.getKontomacierzyste().getPelnynumer(), podatnik, wpisView.getRokWpisu());
        macierzyste.setPozycjaWn(null);
        macierzyste.setPozycjaMa(null);
        macierzyste.setStronaWn(null);
        macierzyste.setStronaMa(null);
        macierzyste.setSyntetykaanalityka(null);
        kontoDAO.edit(macierzyste);
        kontoDAO.editList(listaSiostrzane);
        kontabezprzydzialu.add(macierzyste);
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    public void zaksiegujzmianypozycji(String rb, UkladBR ukladdocelowy) {
        Podatnik podatnik = wybranyuklad.getPodatnik();
        if (rb.equals("r")) {
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladdocelowy, "wynikowe");
            List<Konto> kontapodatnikarok = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, wpisView.getRokWpisuSt());
            List<KontopozycjaZapis> nowezapispozycje = Collections.synchronizedList(new ArrayList<>());
            for (Konto p : kontapodatnikarok) {
                try {
                    if (p.isWynik0bilans1() == false) {
                        nowezapispozycje.add(new KontopozycjaZapis(p, ukladdocelowy));
                    }
                } catch (Exception e) {
                    // ma usuwac jak zmienie kwalifikacje przyporzadkowanego juz konta
                    E.e(e);
                }
            }
            kontopozycjaZapisDAO.dodaj(nowezapispozycje);
        }
        if (rb.equals("b")) {
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladdocelowy, "bilansowe", aktywa0pasywa1);
            List<Konto> kontapodatnikarok = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, wpisView.getRokWpisuSt());
            List<KontopozycjaZapis> nowezapispozycje = Collections.synchronizedList(new ArrayList<>());
            for (Konto p : kontapodatnikarok) {
                try {
                    if (p.isWynik0bilans1() == true) {
                        nowezapispozycje.add(new KontopozycjaZapis(p, ukladdocelowy));
                    }
                } catch (Exception e) {
                    // ma usuwac jak zmienie kwalifikacje przyporzadkowanego juz konta
                    E.e(e);
                }
            }
            kontopozycjaZapisDAO.dodaj(nowezapispozycje);
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje");
    }
    
//    public void zaksiegujzmianypozycjiWzorcowy(String rb, UkladBR wybranyuklad) {
//        if (rb.equals("r")) {
//            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "wynikowe");
//            kontopozycjaZapisDAO.usunKontoPozycjaBiezacaPodatnikUklad(wybranyuklad, "wynikowe");
//            for (KontopozycjaBiezaca p : pozycjebiezace) {
//                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
//            }
//        }
//        if (rb.equals("b")) {
//            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaZapisPodatnikUklad(wybranyuklad, "bilansowe");
//            kontopozycjaZapisDAO.usunKontoPozycjaBiezacaPodatnikUklad(wybranyuklad, "bilansowe");
//            for (KontopozycjaBiezaca p : pozycjebiezace) {
//                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
//            }
//        }
//        Msg.msg("Zapamiętano przyporządkowane pozycje");
//    }

    public void importujwzorcoweprzyporzadkowanie(String rb) {
        if (wybranyuklad == null) {
            Msg.msg("e", "Nie wybrano układu. Nie można zaimplementować przyporządkowania.");
        }
        Podatnik podatnik = wybranyuklad.getPodatnik();
        List<UkladBR> ukladyPodatnika = ukladBRDAO.findPodatnik(podatnik);
        if (ukladyPodatnika != null && ukladyPodatnika.size() > 0) {
            UkladBR czyJestTakiUklad = sprawdzNazwyUkladu(ukladyPodatnika, wybranyuklad);
            if (czyJestTakiUklad == null) {
                Msg.msg("e", "W układach podatnika nie ma układu o takiej nazwie jak wzorcowy.  Nie można zaimplementować przyporządkowania");
            }
            Msg.msg("Rozpoczynam implementacje");
            UkladBR ukladwzorcowy = znajdzUkladWzorcowy(wybranyuklad);
            if (ukladwzorcowy == null) {
                Msg.msg("e", "Nie odnaleziono odpowiadającego układu wzorcowego. Przewywam implementację");
            } else {
                if (rb.equals("r")) {
                    PozycjaRZiSFKBean.skopiujPozycje(rb, wybranyuklad, ukladwzorcowy, podatnik, kontoDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
                    pobierzukladkontoR();
                } else {
                    PozycjaRZiSFKBean.skopiujPozycje(rb, wybranyuklad, ukladwzorcowy, podatnik, kontoDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
                    pobierzukladkontoB("aktywa");
                }
            }
        } else {
            Msg.msg("e", "Podatnik nie posiada zdefiniowanych układów Bilansu i RZiS. Nie można zaimplementować przyporządkowania.");
        }
    }
//r, true
    public void kopiujprzyporzadkowaniekont(String rb, boolean wzorcowe0podatnik1) {
        try {
            Podatnik podatnik = wzorcowe0podatnik1 == false ? wpisView.getPodatnikwzorcowy(): wpisView.getPodatnikObiekt();
            if (ukladdocelowykonta.equals(ukladzrodlowykonta)) {
                Msg.msg("e", "Nie można kopiować układu w ten sam układ");
                return;
            }
            if (!ukladdocelowykonta.getRok().equals(wpisView.getRokWpisuSt())) {
                Msg.msg("e", "Układ docelowy nie jest bieżącym rokiem wpisu. Nie można skopiować");
                return;
            }
            if (rb.equals("r")) {
                Msg.msg("Rozpoczynam kopiowanie przyporządkowania kont wzorcowych-wynikowych");
                PozycjaRZiSFKBean.skopiujPozycje(rb, ukladdocelowykonta, ukladzrodlowykonta, podatnik, kontoDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
                //wybranyuklad = ukladdocelowykonta;
                //zaksiegujzmianypozycji("r", wybranyuklad);
                pobierzukladkontoR();
            } else {
                Msg.msg("Rozpoczynam kopiowanie przyporządkowania kont wzorcowych-bilansowych");
                PozycjaRZiSFKBean.skopiujPozycje(rb, ukladdocelowykonta, ukladzrodlowykonta, podatnik, kontoDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
                //wybranyuklad = ukladdocelowykonta;
                //zaksiegujzmianypozycji("b", wybranyuklad);
                pobierzukladkontoB("aktywa");
            }
            Msg.msg("Zakończono kopiowanie przyporządkowania");
                    
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    
    
    private UkladBR znajdzUkladWzorcowy(UkladBR ukladpodatnika) {
        List<UkladBR> lista = ukladBRDAO.findPodatnik(wpisView.getPodatnikwzorcowy());
        for (UkladBR p : lista) {
            if (p.getUklad().equals(ukladpodatnika.getUklad()) && p.getRok().equals("2018")) {
                return p;
            }
        }
        return null;
    }

   
    
    
    
//    private void skopiujPozycjeWzorcowe(String rb, UkladBR ukladdocelowy, UkladBR ukladzrodlowy) {
//        if (rb.equals("r")) {
//            wyczyscKonta("wynikowe", "Wzorcowy", wpisView.getRokWpisuSt());
//            kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
//            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
//            //kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(ukladdocelowy, "wynikowe");
//            kontopozycjaZapisDAO.usunKontoPozycjaBiezacaPodatnikUklad(ukladdocelowy, "wynikowe");
//            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(ukladzrodlowy, "wynikowe");
//            for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
//                    if (p.getSyntetykaanalityka().equals("wynikowe") || p.getSyntetykaanalityka().equals("szczególne")) {
//                        System.out.println("Szukam konta " + p.getKontoID().toString());
//                        try {
//                            Konto kontouzytkownika = kontoDAO.findKonto(p.getKontoID().getPelnynumer(), "Wzorcowy", ukladdocelowy.getRok());
//                            kontouzytkownika.setZwyklerozrachszczegolne(p.getKontoID().getZwyklerozrachszczegolne());
//                            if (kontouzytkownika.getPelnynumer().equals("407")) {
//                               System.out.println("dd");
//                            }
//                            boxNaKonto = kontouzytkownika;
//                            if (kontouzytkownika != null && kontouzytkownika.getBilansowewynikowe().equals("wynikowe")) {
//                                if (kontouzytkownika.getZwyklerozrachszczegolne().equals("szczególne")) {
//                                    wybranapozycja = p.getPozycjaWn();
//                                    wnmaPrzypisywanieKont = "wn";
//                                    onKontoDropRAutoSzczegolne(kontouzytkownika, p.getPozycjaWn(), ukladdocelowy, true);
//                                    wybranapozycja = p.getPozycjaMa();
//                                    wnmaPrzypisywanieKont = "ma";
//                                    Konto kontouzytkownika1 = kontoDAO.findKonto(kontouzytkownika.getId());
//                                    boxNaKonto = kontouzytkownika1;
//                                    onKontoDropRAutoSzczegolne(kontouzytkownika1, p.getPozycjaMa(), ukladdocelowy, true);
//                                } else {
//                                    onKontoDropRAutoZwykle(kontouzytkownika, p.getPozycjaWn(), ukladdocelowy, true);
//                                }
//                            }
//                        } catch (Exception e) {
//                            E.e(e);
//                        }
//                    }
//        }
//            zaksiegujzmianypozycji("r", ukladdocelowy);
//        }
//        if (rb.equals("b")) {
//            wyczyscKonta("bilansowe", "Wzorcowy", wpisView.getRokWpisuSt());
//            kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
//            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
//            //kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(ukladdocelowy, "bilansowe");
//            kontopozycjaZapisDAO.usunKontoPozycjaBiezacaPodatnikUklad(ukladdocelowy, "bilansowe");
//            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(ukladzrodlowy, "bilansowe");
//            for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
//                    if (!p.getSyntetykaanalityka().equals("syntetyka")) {//wykluczam potomkow podlaczanych automatycznie
//                         System.out.println("Szukam konta "+p.getKontoID().toString());
//                         try {
//                             Konto kontouzytkownika = kontoDAO.findKonto(p.getKontoID().getPelnynumer(), "Wzorcowy", ukladdocelowy.getRok());
//                             kontouzytkownika.setZwyklerozrachszczegolne(p.getKontoID().getZwyklerozrachszczegolne());
//                             boxNaKonto = kontouzytkownika;
//                             if (kontouzytkownika != null && kontouzytkownika.getBilansowewynikowe().equals("bilansowe")) {
//                                  if (!kontouzytkownika.getZwyklerozrachszczegolne().equals("zwykłe")) {
//                                     wybranapozycja = p.getPozycjaWn();
//                                     wnmaPrzypisywanieKont = "wn";
//                                     aktywa0pasywa1 = p.getStronaWn().equals("0") ? false : true;
//                                     onKontoDropBAutoSzczegolne(kontouzytkownika, p.getPozycjaWn(), ukladdocelowy, true);
//                                     wybranapozycja = p.getPozycjaMa();
//                                     wnmaPrzypisywanieKont = "ma";
//                                     aktywa0pasywa1 = p.getStronaMa().equals("0") ? false : true;
//                                     Konto kontouzytkownika1 = kontoDAO.findKonto(kontouzytkownika.getId());
//                                     boxNaKonto = kontouzytkownika1;
//                                     onKontoDropBAutoSzczegolne(kontouzytkownika1, p.getPozycjaMa(), ukladdocelowy, true);
//                                  } else {
//                                      aktywa0pasywa1 = p.getStronaWn().equals("0") ? false : true;
//                                      onKontoDropBAutoZwykle(kontouzytkownika, p.getPozycjaWn(), ukladdocelowy, aktywa0pasywa1, true);
//                                  }
//                             }
//                         } catch (Exception e) {
//                             E.e(e);
//                         }
//                     }
//            }
//            zaksiegujzmianypozycji("b", ukladdocelowy);
//        }
//        Msg.msg("Zapamiętano przyporządkowane pozycje");
//    }
    
//    public void onKontoDropRAutoSzczegolne(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
//        if (konto.getPozycjaWn() == null && konto.getPozycjaMa()==null) {
//                onKontoDropKontaSpecjalneRZiS(ukladpodatnika);
//        } else {
//            if (konto.getPozycjaWn() != null) {
//                wnmaPrzypisywanieKont = "ma";
//                onKontoDropKontaSpecjalneRZiS(ukladpodatnika);
//            } else {
//                wnmaPrzypisywanieKont = "wn";
//                onKontoDropKontaSpecjalneRZiS(ukladpodatnika);
//            }
//        }
//    }
//    
//    public void onKontoDropRAutoZwykle(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy, Podatnik podatnik) {
//        PlanKontFKBean.przyporzadkujRZiS_kontozwykle(pozycja, konto, kontoDAO, podatnik, null);
//    }
//    
//    public void onKontoDropBAutoSzczegolne(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
//        if (konto.getPozycjaWn() == null && konto.getPozycjaMa()==null) {
//                onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
//        } else {
//            if (konto.getPozycjaWn() != null) {
//                wnmaPrzypisywanieKont = "ma";
//                onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
//            } else {
//                wnmaPrzypisywanieKont = "wn";
//                onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
//            }
//        }
//    }
//    
//    public void onKontoDropBAutoZwykle(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean ap, boolean wzorcowy) {
//        Podatnik podatnik = wybranyuklad.getPodatnik();
//        PlanKontFKBean.przyporzadkujBilans_kontozwykle(pozycja, konto, kontoDAO, podatnik, null, ap);
//    }
    
   

    
    

 
    private UkladBR sprawdzNazwyUkladu(List<UkladBR> ukladyPodatnika, UkladBR ukladBR) {
        for (UkladBR p : ukladyPodatnika) {
            if (p.isImportowany() == true && p.getUklad().equals(wybranyuklad.getUklad()) && p.getRok().equals(ukladBR.getRok())) {
                return p;
            }
        }
        return null;
    }
    
    public void drukujBilansKonta() {
        if (aktywa0pasywa1 == false) {
            PdfBilans.drukujBilansKonta(rootProjektKontaBilans, wpisView, "a", 0, false);
        } else {
            PdfBilans.drukujBilansKonta(rootProjektKontaBilans, wpisView, "p", 0, false);
        }
    }
    
    public void drukujWynikKonta() {
            PdfRZiS.drukujRZiSKonta(rootProjektKontaRZiS, wpisView);
    }

//<editor-fold defaultstate="collapsed" desc="comment">

    public List<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
    }

    public String getWybranapozycja_wiersz() {
        return wybranapozycja_wiersz;
    }

    public void setWybranapozycja_wiersz(String wybranapozycja_wiersz) {
        this.wybranapozycja_wiersz = wybranapozycja_wiersz;
    }

    public List<UkladBR> getListaukladow() {
        return listaukladow;
    }

    public void setListaukladow(List<UkladBR> listaukladow) {
        this.listaukladow = listaukladow;
    }

    public UkladBR getUkladzrodlowykonta() {
        return ukladzrodlowykonta;
    }

    public void setUkladzrodlowykonta(UkladBR ukladzrodlowykonta) {
        this.ukladzrodlowykonta = ukladzrodlowykonta;
    }

    public UkladBR getUkladdocelowykonta() {
        return ukladdocelowykonta;
    }

    public void setUkladdocelowykonta(UkladBR ukladdocelowykonta) {
        this.ukladdocelowykonta = ukladdocelowykonta;
    }

    public UkladBR getWybranyuklad() {
        return wybranyuklad;
    }

    public void setWybranyuklad(UkladBR wybranyuklad) {
        this.wybranyuklad = wybranyuklad;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public boolean isAktywa0pasywa1() {
        return aktywa0pasywa1;
    }

    public void setAktywa0pasywa1(boolean aktywa0pasywa1) {
        this.aktywa0pasywa1 = aktywa0pasywa1;
    }

    public List<Konto> getKontabezprzydzialu() {
        return kontabezprzydzialu;
    }

    public void setKontabezprzydzialu(List<Konto> kontabezprzydzialu) {
        this.kontabezprzydzialu = kontabezprzydzialu;
    }

    public List<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(List<Konto> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    public TreeNodeExtended getRootProjektKontaRZiS() {
        return rootProjektKontaRZiS;
    }

    public void setRootProjektKontaRZiS(TreeNodeExtended rootProjektKontaRZiS) {
        this.rootProjektKontaRZiS = rootProjektKontaRZiS;
    }

    public TreeNodeExtended getRootProjektKontaBilans() {
        return rootProjektKontaBilans;
    }

    public void setRootProjektKontaBilans(TreeNodeExtended rootProjektKontaBilans) {
        this.rootProjektKontaBilans = rootProjektKontaBilans;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getWybranapozycja() {
        return wybranapozycja;
    }

    public void setWybranapozycja(String wybranapozycja) {
        this.wybranapozycja = wybranapozycja;
    }

    public TreeNode getWybranynodekonta() {
        return wybranynodekonta;
    }

    public void setWybranynodekonta(TreeNode wybranynodekonta) {
        this.wybranynodekonta = wybranynodekonta;
    }

    public Konto getBoxNaKonto() {
        return boxNaKonto;
    }

    public void setBoxNaKonto(Konto boxNaKonto) {
        this.boxNaKonto = boxNaKonto;
    }

    public String getWnmaPrzypisywanieKont() {
        return wnmaPrzypisywanieKont;
    }

    public void setWnmaPrzypisywanieKont(String wnmaPrzypisywanieKont) {
        this.wnmaPrzypisywanieKont = wnmaPrzypisywanieKont;
    }

//</editor-fold>
    public static void main(String[] args) {
    }

    
}
