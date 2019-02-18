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
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
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
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.component.treetable.TreeTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import pdffk.PdfBilans;
import pdffk.PdfRZiS;
import view.WpisView;

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
    private KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO;
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
         E.m(this);
        this.kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
        this.rootProjektKontaRZiS = new TreeNodeExtended("root", null);
        this.rootProjektKontaBilans = new TreeNodeExtended("root", null);
        this.pozycje = Collections.synchronizedList(new ArrayList<>());
        this.przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() {
        listaukladow = ukladBRDAO.findPodatnik(wpisView.getPodatnikObiekt());
        wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
        if (listaukladow != null && wybranyuklad != null) {
            for (UkladBR p : listaukladow) {
                if (p.getRok().equals(wpisView.getRokUprzedniSt()) && p.getUklad().equals(wybranyuklad.getUklad())) {
                    ukladzrodlowykonta = p;
                }
            }
        } else {
            listaukladow = Collections.synchronizedList(new ArrayList<>());
        }
    }
    
    public void pobierzukladkontoR() {
        try {
            wybranyuklad.oznaczUkladBR(ukladBRDAO);
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            PozycjaRZiSFKBean.wyczyscKonta("wynikowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontoDAO);
            kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(wybranyuklad, "wynikowe");
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, wybranyuklad, wpisView, false, "wynikowe");
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
            wybranyuklad.oznaczUkladBR(ukladBRDAO);
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            PozycjaRZiSFKBean.wyczyscKonta("bilansowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontoDAO);
            kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(wybranyuklad, "bilansowe");
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, wybranyuklad, wpisView, false, "bilansowe");
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
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), "0", "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiRZiS(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView.getPodatnikObiekt(), Integer.parseInt(wybranyuklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void drugiinitbilansowe() {
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), "0", "bilansowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiBilans(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO,  wpisView.getPodatnikObiekt(), aktywa0pasywa1, Integer.parseInt(wybranyuklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowaneAll("bilansowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        if (!lista.isEmpty()) {
            pozycje.parallelStream().forEach((p)->{
                PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(lista, p, wpisView, aktywa0pasywa1);
            });
            pozycjaBilansDAO.editList(pozycje);
        }
    }

    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowaneAll("wynikowe", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        if (!lista.isEmpty()) {
            pozycje.parallelStream().forEach((p)->{
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
            boxNaKonto = konto;
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (konto.getKontopozycjaID() == null) {
                    RequestContext.getCurrentInstance().update("kontownmawyborRZiS");
                    RequestContext.getCurrentInstance().execute("PF('kontownmawyborRZiS').show();");
                    Msg.msg("Konto niezwykle");
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
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
                PlanKontFKBean.przyporzadkujRZiS_kontozwykle(wybranapozycja, konto, wybranyuklad, kontoDAO, wpisView.getPodatnikObiekt(), null);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                uzupelnijpozycjeOKontaR(pozycje);   
            }
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
        }
    }

    public void onKontoDropB(Konto konto) {
        boolean wzorcowy = false;
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            boxNaKonto = konto;
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("szczególne") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                if (konto.getKontopozycjaID() == null) {
                    RequestContext.getCurrentInstance().update("kontownmawybor");
                    RequestContext.getCurrentInstance().execute("PF('kontownmawybor').show();");
                    Msg.msg("Konto niezwykle");
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalne(wzorcowy, wybranyuklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalne(wzorcowy, wybranyuklad);
                    }
                    if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaMa() != null ) {
                        kontabezprzydzialu.remove(konto);
                    }
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                PlanKontFKBean.przyporzadkujBilans_kontozwykle(wybranapozycja, konto, wybranyuklad, kontoDAO, wpisView.getPodatnikObiekt(), null, aktywa0pasywa1);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                uzupelnijpozycjeOKonta(pozycje);
            }
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
            RequestContext.getCurrentInstance().update(".ui-state-highlight");
        }

    }

    public void onKontoDropKontaSpecjalneRZiS(UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //musi byc boxNaKonto bo ta funcja jest dostepna tez z okienka wyboru strony a tam nie ma info o koncie
            Konto konto = boxNaKonto;
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                PlanKontFKBean.przyporzadkujRZiS_kontoszczegolne(wybranapozycja,konto, ukladpodatnika, kontoDAO, wpisView.getPodatnikObiekt(), wnmaPrzypisywanieKont);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //wywalamy tylko obustronnnie przyporzadkowane konta
                if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaMa() != null) {
                    kontabezprzydzialu.remove(konto);
                }
                //czesc nanoszaca informacje na potomku
            }
            uzupelnijpozycjeOKontaR(pozycje);
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
        }
    }

   

    public void onKontoDropKontaSpecjalne(boolean wzorcowy, UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Konto konto = boxNaKonto;
            if (konto.getPelnynumer().equals("201") || konto.getPelnynumer().equals("201-1") || konto.getPelnynumer().equals("201-1-0")){
            }
            if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
            }
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                PlanKontFKBean.przyporzadkujBilans_kontoszczegolne(wybranapozycja,konto, ukladpodatnika, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1,"rozrachunkowe/vat", wpisView.getPodatnikObiekt());
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                PlanKontFKBean.przyporzadkujBilans_kontoszczegolne(wybranapozycja,konto, ukladpodatnika, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1,"szczególne", wpisView.getPodatnikObiekt());
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
            }
            uzupelnijpozycjeOKonta(pozycje);
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
            RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
        }
    }

   
    public void onKontoRemoveB(Konto konto, String br) {
        if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
            przyporzadkowanekonta.remove(konto);
            String wnma = "";
            if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.getKontopozycjaID().setPozycjaWn(null);
            } else if (konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.getKontopozycjaID().setPozycjaMa(null);
            }
            if (konto.getKontopozycjaID().getPozycjaWn() == null && konto.getKontopozycjaID().getPozycjaMa() == null) {
                konto.setKontopozycjaID(null);
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikObiekt(), wnma, Integer.parseInt(wybranyuklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getKontomacierzyste() != null) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikObiekt(), Integer.parseInt(wybranyuklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
            String wnma = "";
            if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.getKontopozycjaID().setPozycjaWn(null);
                if (!(konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            } else if (konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.getKontopozycjaID().setPozycjaMa(null);
                if (!(konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            }
            if (konto.getKontopozycjaID().getPozycjaWn() == null && konto.getKontopozycjaID().getPozycjaMa() == null) {
                konto.setKontopozycjaID(null);
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikObiekt(), wnma, Integer.parseInt(wybranyuklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikObiekt(), Integer.parseInt(wybranyuklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikObiekt(), "bilans", Integer.parseInt(wybranyuklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikObiekt(), Integer.parseInt(wybranyuklad.getRok()));
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
        RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
    }

    public void onKontoRemoveR(Konto konto, String br) {
        if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
            String wnma = "";
            if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja)) {
                wnma = "wn";
                konto.getKontopozycjaID().setPozycjaWn(null);
                if (!(konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            } else if (konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.getKontopozycjaID().setPozycjaMa(null);
                if (!(konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
            }
            if (konto.getKontopozycjaID().getPozycjaWn() == null && konto.getKontopozycjaID().getPozycjaMa() == null) {
                konto.setKontopozycjaID(null);
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikObiekt(), wnma, Integer.parseInt(wybranyuklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikObiekt(), Integer.parseInt(wybranyuklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikObiekt(), "wynik", Integer.parseInt(wybranyuklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikObiekt(), Integer.parseInt(wybranyuklad.getRok()));
            }
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
        uzupelnijpozycjeOKontaR(pozycje);
        RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
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
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), konto.getPelnynumer());
        if (lista.size() > 0) {
            kontabezprzydzialu.addAll(kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), konto.getPelnynumer()));
            kontabezprzydzialu.remove(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void zwinrzadanalityki(Konto konto) {
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzanePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), konto.getMacierzyste());
        List<Konto> listaPotomne = Collections.synchronizedList(new ArrayList<>());
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomnePodatnik(new ArrayList<Konto>(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), t));
        }
        listaSiostrzane.addAll(listaPotomne);
        boolean jestprzypisane = false;
        List<String> analitykinazwy = Collections.synchronizedList(new ArrayList<>());
        for (Konto p : listaSiostrzane) {
            if (p.getKontopozycjaID() != null) {
                jestprzypisane = true;
                analitykinazwy.add(p.getPelnynumer());
            }
        }
        if (jestprzypisane) {
            String result = StringUtils.join(analitykinazwy, ", ");
            Msg.msg("e", "Nie można zwinąć analityk. Istnieją analityki przypisane do kont: " + result);
        } else {
            Konto macierzyste = kontoDAO.findKonto(konto.getMacierzyste(), wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            for (Konto p : listaSiostrzane) {
                kontabezprzydzialu.remove(p);
            }
            kontabezprzydzialu.add(macierzyste);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
    }

    public void zaksiegujzmianypozycji(String rb, UkladBR ukladdocelowy) {
        if (rb.equals("r")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladdocelowy, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladdocelowy, "wynikowe");
            List<KontopozycjaZapis> nowezapispozycje = Collections.synchronizedList(new ArrayList<>());
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                try {
                    if (p.isWynik0bilans1() == false) {
                        nowezapispozycje.add(new KontopozycjaZapis(p));
                    }
                } catch (Exception e) {
                    // ma usuwac jak zmienie kwalifikacje przyporzadkowanego juz konta
                    E.e(e);
                }
            }
            kontopozycjaZapisDAO.editList(nowezapispozycje);
        }
        if (rb.equals("b")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladdocelowy, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladdocelowy, "bilansowe");
            List<KontopozycjaZapis> nowezapispozycje = Collections.synchronizedList(new ArrayList<>());
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                try {
                    if (p.isWynik0bilans1() == true) {
                        nowezapispozycje.add(new KontopozycjaZapis(p));
                    }
                } catch (Exception e) {
                    // ma usuwac jak zmienie kwalifikacje przyporzadkowanego juz konta
                    E.e(e);
                }
            }
            kontopozycjaZapisDAO.editList(nowezapispozycje);
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
        List<UkladBR> ukladyPodatnika = ukladBRDAO.findPodatnik(wpisView.getPodatnikObiekt());
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
                    PozycjaRZiSFKBean.skopiujPozycje(rb, wybranyuklad, ukladwzorcowy, wpisView.getPodatnikObiekt(), kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
                    pobierzukladkontoR();
                } else {
                    PozycjaRZiSFKBean.skopiujPozycje(rb, wybranyuklad, ukladwzorcowy, wpisView.getPodatnikObiekt(), kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
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
                PozycjaRZiSFKBean.skopiujPozycje(rb, ukladdocelowykonta, ukladzrodlowykonta, podatnik, kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
                //wybranyuklad = ukladdocelowykonta;
                //zaksiegujzmianypozycji("r", wybranyuklad);
                pobierzukladkontoR();
            } else {
                Msg.msg("Rozpoczynam kopiowanie przyporządkowania kont wzorcowych-bilansowych");
                PozycjaRZiSFKBean.skopiujPozycje(rb, ukladdocelowykonta, ukladzrodlowykonta, podatnik, kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
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
    
    public void onKontoDropRAutoSzczegolne(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
                if (konto.getKontopozycjaID() == null) {
                        onKontoDropKontaSpecjalneRZiS(ukladpodatnika);
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalneRZiS(ukladpodatnika);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneRZiS(ukladpodatnika);
                    }
                }
    }
    
    public void onKontoDropRAutoZwykle(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy, Podatnik podatnik) {
        PlanKontFKBean.przyporzadkujRZiS_kontozwykle(pozycja, konto, ukladpodatnika, kontoDAO, podatnik, null);
    }
    
    public void onKontoDropBAutoSzczegolne(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
        if (konto.getKontopozycjaID() == null) {
                onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
        } else {
            if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                wnmaPrzypisywanieKont = "ma";
                onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
            } else {
                wnmaPrzypisywanieKont = "wn";
                onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
            }
        }
    }
    
    public void onKontoDropBAutoZwykle(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean ap, boolean wzorcowy) {
        PlanKontFKBean.przyporzadkujBilans_kontozwykle(pozycja, konto, ukladpodatnika, kontoDAO, wpisView.getPodatnikObiekt(), null, ap);
    }
    
   

    
    

 
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
        KontopozycjaBiezaca kb = new KontopozycjaBiezaca();
        KontopozycjaZapis kz = new KontopozycjaZapis(kb);
    }

    
}
