/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PozycjaRZiSFKBean;
import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddablefk.TreeNodeExtended;
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
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;
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
public class PozycjaBRKontaWzorcowyView implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<PozycjaRZiSBilans> pozycje;
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
    private UkladBR uklad;
    private String wnmaPrzypisywanieKont;
    private Konto boxNaKonto;
    private boolean aktywa0pasywa1;
    private List<Konto> kontabezprzydzialu;
    private ArrayList<Konto> przyporzadkowanekonta;
    private TreeNodeExtended rootProjektKontaRZiS;
    private TreeNodeExtended rootProjektKontaBilans;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private int level = 0;
    private String wybranapozycja;
    private TreeNode wybranynodekonta;
    

    public PozycjaBRKontaWzorcowyView() {
        this.kontabezprzydzialu = new ArrayList<>();
        this.rootProjektKontaRZiS = new TreeNodeExtended("root", null);
        this.rootProjektKontaBilans = new TreeNodeExtended("root", null);
        this.pozycje = new ArrayList<>();
        this.przyporzadkowanekonta = new ArrayList<>();
    }

    public void pobierzukladkontoR() {
        przyporzadkowanekonta = new ArrayList<>();
        wyczyscKontaWzorcowy(uklad, "wynikowe");
        //kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, true, "wynikowe");
        pozycje = new ArrayList<>();
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
        } catch (Exception e) {  E.e(e);
        }
        drugiinit();
        uzupelnijpozycjeOKontaR(pozycje);
        rootProjektKontaRZiS.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKontaRZiS, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(rootProjektKontaRZiS, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }


    public void pobierzukladkontoB(String aktywapasywa) {
        przyporzadkowanekonta = new ArrayList<>();
        wyczyscKontaWzorcowy(uklad, "bilansowe");
        //kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, true, "bilansowe");
        pozycje = new ArrayList<>();
        try {
            if (aktywapasywa.equals("aktywa")) {
                aktywa0pasywa1 = false;
                pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
            } else {
                aktywa0pasywa1 = true;
                pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
            }
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
        } catch (Exception e) {  E.e(e);
        }
        drugiinitbilansowe();
        uzupelnijpozycjeOKonta(pozycje);
        rootProjektKontaBilans.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKontaBilans, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(rootProjektKontaBilans, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }

    private void drugiinit() {
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomneWzorcowy(Integer.parseInt(uklad.getRok()), "0", "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiRZiS(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView, true, Integer.parseInt(uklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void drugiinitbilansowe() {
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomneWzorcowy(Integer.parseInt(uklad.getRok()), "0", "bilansowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiBilans(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView, aktywa0pasywa1, true, Integer.parseInt(uklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(kontoDAO, p, pozycjaBilansDAO, wpisView, aktywa0pasywa1, true, uklad);
        }
    }

    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneRLista(kontoDAO, p, pozycjaRZiSDAO, wpisView, true, uklad);
        }
    }

    public void onKontoDropR(Konto konto, String br) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            boxNaKonto = konto;
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (konto.getKontopozycjaID() == null) {
                    RequestContext.getCurrentInstance().update("kontownmawyborRZiS");
                    RequestContext.getCurrentInstance().execute("PF('dialog_rzis_konta_wnma_wzorcowy').show();");
                    Msg.msg("Konto niezwykle");
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalneIstniejeKPRZiS();
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneIstniejeKPRZiS();
                    }
                }
                //to duperele porzadkujace sytuacje w okienkach
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
                kp.setPozycjaWn(wybranapozycja);
                kp.setPozycjaMa(wybranapozycja);
                kp.setStronaWn("99");
                kp.setStronaMa("99");
                kp.setWynik0bilans1(false);
                kp.setSyntetykaanalityka("wynikowe");
                kp.setKontoID(konto);
                kp.setUkladBR(uklad);
                konto.setKontopozycjaID(kp);
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView, true, "wynik", Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            }
            uzupelnijpozycjeOKontaR(pozycje);
        }
    }

    public void onKontoDropB(Konto konto, String br) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            boxNaKonto = konto;
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("szczególne") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                if (konto.getKontopozycjaID() == null) {
                    RequestContext.getCurrentInstance().update("kontownmawybor");
                    RequestContext.getCurrentInstance().execute("PF('dialog_bilans_konta_wnma_wzorcowy').show();");
                    Msg.msg("Konto niezwykle");
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalneIstniejeKP();
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneIstniejeKP();
                    }
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
                kp.setWynik0bilans1(true);
                kp.setPozycjaWn(wybranapozycja);
                kp.setPozycjaMa(wybranapozycja);
                if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                    kp.setStronaWn("0");
                    kp.setStronaMa("0");
                } else {
                    kp.setStronaWn("1");
                    kp.setStronaMa("1");
                }
                kp.setSyntetykaanalityka("zwykłe");
                kp.setKontoID(konto);
                kp.setUkladBR(uklad);
                konto.setKontopozycjaID(kp);
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView, true, "bilans", Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            }
            uzupelnijpozycjeOKonta(pozycje);
        }

    }

    public void onKontoDropKontaSpecjalneRZiS() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Konto konto = boxNaKonto;
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
                kp.setWynik0bilans1(false);
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    kp.setStronaWn("88");
                    kp.setSyntetykaanalityka("szczególne");
                    kp.setKontoID(konto);
                    kp.setUkladBR(uklad);
                    konto.setKontopozycjaID(kp);
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    kp.setStronaMa("88");
                    kp.setSyntetykaanalityka("szczególne");
                    kp.setKontoID(konto);
                    kp.setUkladBR(uklad);
                    konto.setKontopozycjaID(kp);
                }
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, true, Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
    }

    public void onKontoDropKontaSpecjalneIstniejeKPRZiS() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //trzeba wyszukac konto bo nie odswiezalem listy i w zwiazku z tym encja z listy nie zgadza sie z encja z bazy;
            Konto konto = boxNaKonto;
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = konto.getKontopozycjaID();
                kp.setWynik0bilans1(false);
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    kp.setStronaWn("88");
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    kp.setStronaMa("88");
                }
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1, true, Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
    }

    public void onKontoDropKontaSpecjalne() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Konto konto = boxNaKonto;
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
                kp.setWynik0bilans1(true);
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaWn("0");
                    } else {
                        kp.setStronaWn("1");
                    }
                    kp.setSyntetykaanalityka("rozrachunkowe/vat");
                    kp.setKontoID(konto);
                    kp.setUkladBR(uklad);
                    konto.setKontopozycjaID(kp);
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaMa("0");
                    } else {
                        kp.setStronaMa("1");
                    }
                    kp.setSyntetykaanalityka("rozrachunkowe/vat");
                    kp.setKontoID(konto);
                    kp.setUkladBR(uklad);
                    konto.setKontopozycjaID(kp);
                }
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, true, Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
                kp.setWynik0bilans1(true);
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaWn("0");
                    } else {
                        kp.setStronaWn("1");
                    }
                    kp.setSyntetykaanalityka("szczególne");
                    kp.setKontoID(konto);
                    kp.setUkladBR(uklad);
                    konto.setKontopozycjaID(kp);
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaMa("0");
                    } else {
                        kp.setStronaMa("1");
                    }
                    kp.setSyntetykaanalityka("szczególne");
                    kp.setKontoID(konto);
                    kp.setUkladBR(uklad);
                    konto.setKontopozycjaID(kp);
                }
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, true, Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            }
        }
        uzupelnijpozycjeOKonta(pozycje);
    }

    public void onKontoDropKontaSpecjalneIstniejeKP() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //trzeba wyszukac konto bo nie odswiezalem listy i w zwiazku z tym encja z listy nie zgadza sie z encja z bazy;
            Konto konto = kontoDAO.findKontoWzorcowy(boxNaKonto.getPelnynumer(), wpisView);
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = konto.getKontopozycjaID();
                kp.setWynik0bilans1(true);
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaWn("0");
                    } else {
                        kp.setStronaWn("1");
                    }
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaMa("0");
                    } else {
                        kp.setStronaMa("1");
                    }
                }
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1, true, Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = konto.getKontopozycjaID();
                kp.setWynik0bilans1(true);
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaWn("0");
                    } else {
                        kp.setStronaWn("1");
                    }
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                        kp.setStronaMa("0");
                    } else {
                        kp.setStronaMa("1");
                    }
                }
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1, true, Integer.parseInt(uklad.getRok()));
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, true);
                }
            }
        }
        uzupelnijpozycjeOKonta(pozycje);
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView, wnma, true, Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, true, Integer.parseInt(uklad.getRok()));
            }
        }
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView, wnma, true, Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, true, Integer.parseInt(uklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView, true, "bilans", Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, true, Integer.parseInt(uklad.getRok()));
            }
             if (kontabezprzydzialu.contains(konto)) {
                    kontabezprzydzialu.remove(konto);
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                } else {
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                }
        } else {
            Msg.msg("Konto niezwykle");
        }
        uzupelnijpozycjeOKonta(pozycje);
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
                if (kontabezprzydzialu.contains(konto)) {
                    kontabezprzydzialu.remove(konto);
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                } else {
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                }
            } else if (konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.getKontopozycjaID().setPozycjaMa(null);
                if (!(konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
                if (kontabezprzydzialu.contains(konto)) {
                    kontabezprzydzialu.remove(konto);
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                } else {
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                }
            }
            if (konto.getKontopozycjaID().getPozycjaWn() == null && konto.getKontopozycjaID().getPozycjaMa() == null) {
                konto.setKontopozycjaID(null);
                if (kontabezprzydzialu.contains(konto)) {
                    kontabezprzydzialu.remove(konto);
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                } else {
                    kontabezprzydzialu.add(konto);
                    Collections.sort(kontabezprzydzialu, new Kontocomparator());
                }
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView, wnma, true, Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, true, Integer.parseInt(uklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView, true, "wynik", Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, true, Integer.parseInt(uklad.getRok()));
            }
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
        uzupelnijpozycjeOKontaR(pozycje);
    }

    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja, wpisView, aktywa0pasywa1, true, uklad));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }

    public void wybranopozycjeBilans() {
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja, wpisView, aktywa0pasywa1, true, uklad));
        //Msg.msg("i", "Wybrano pozycję " + ((PozycjaBilans) wybranynodekonta.getData()).getNazwa());
    }

    public void rozwinwszystkie(TreeNodeExtended root) {
        try {
            level = root.ustaldepthDT(pozycje) - 1;
            root.expandAll();
        } catch (Exception e) {  E.e(e);
            
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
        } catch (Exception e) {  E.e(e);
            
        }
    }

    public void zwin(TreeNodeExtended root) {
        root.foldLevel(--level);
    }

    public void rozwinrzadanalityki(Konto konto) {
        List<Konto> lista = kontoDAO.findKontaPotomneWzorcowy(Integer.parseInt(uklad.getRok()), konto.getPelnynumer());
        if (lista.size() > 0) {
            kontabezprzydzialu.addAll(kontoDAO.findKontaPotomneWzorcowy(Integer.parseInt(uklad.getRok()), konto.getPelnynumer()));
            kontabezprzydzialu.remove(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void zwinrzadanalityki(Konto konto) {
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzaneWzorcowy(wpisView, konto.getMacierzyste());
        List<Konto> listaPotomne = new ArrayList<>();
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomneWzorcowy(wpisView, t));
        }
        listaSiostrzane.addAll(listaPotomne);
        boolean jestprzypisane = false;
        List<String> analitykinazwy = new ArrayList<>();
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
            Konto macierzyste = kontoDAO.findKonto(konto.getMacierzyste(), wpisView);
            for (Konto p : listaSiostrzane) {
                kontabezprzydzialu.remove(p);
            }
            kontabezprzydzialu.add(macierzyste);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
    }
    
     public void zaksiegujzmianypozycji(String rb) {
        if (rb.equals("r")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                if (p.isWynik0bilans1() == false) {
                    kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                }
            }
        }
        if (rb.equals("b")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                if (p.isWynik0bilans1() == true) {
                    kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                }
            }
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje wzorcowe");
    }
    
    public void resetujprzyporzadkowanie(String rb) {
        if (rb.equals("r")) {
            wyczyscKontaWzorcowy(uklad, "wynikowe");
            kontabezprzydzialu = new ArrayList<>();
            przyporzadkowanekonta = new ArrayList<>();
            //kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            pobierzukladkontoR();
        } else {
            wyczyscKontaWzorcowy(uklad, "bilansowe");
            kontabezprzydzialu = new ArrayList<>();
            przyporzadkowanekonta = new ArrayList<>();
            //kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            if (aktywa0pasywa1==false) {
                pobierzukladkontoB("aktywa");
            } else {
                pobierzukladkontoB("pasywa");
            }
        }
    }
    
   private void wyczyscKonta(String rb) {
        if (rb.equals("wynikowe")) {
            kontoDAO.wyzerujPozycjeWKontach(wpisView,"wynikowe");
        } else {
            kontoDAO.wyzerujPozycjeWKontach(wpisView,"bilansowe");
        }
    }
   
   private void wyczyscKontaWzorcowy(UkladBR uklad, String rb) {
        List<Konto> list = kontoDAO.findWszystkieKontaWzorcowy(wpisView);
        for (Konto p : list) {
            if (rb.equals("wynikowe") && p.getBilansowewynikowe().equals("wynikowe")) {
                p.setKontopozycjaID(null);
                kontoDAO.edit(p);
            } else if (rb.equals("bilansowe") && p.getBilansowewynikowe().equals("bilansowe")) {
                p.setKontopozycjaID(null);
                kontoDAO.edit(p);
            }
        }
    }
    
     public void drukujBilansKonta() {
        if (aktywa0pasywa1 == false) {
            PdfBilans.drukujBilansKonta(rootProjektKontaBilans, wpisView, "a", 0);
        } else {
            PdfBilans.drukujBilansKonta(rootProjektKontaBilans, wpisView, "p", 0);
        }
    }
     
     public void drukujWynikKonta() {
            PdfRZiS.drukujRZiSKonta(rootProjektKontaRZiS, wpisView);
    }
     
    
//<editor-fold defaultstate="collapsed" desc="comment">

    public ArrayList<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
    }

    public TreeNodeExtended getRootProjektKontaBilans() {
        return rootProjektKontaBilans;
    }

    public void setRootProjektKontaBilans(TreeNodeExtended rootProjektKontaBilans) {
        this.rootProjektKontaBilans = rootProjektKontaBilans;
    }

    public UkladBR getUklad() {
        return uklad;
    }

    public void setUklad(UkladBR uklad) {
        this.uklad = uklad;
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

    public ArrayList<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(ArrayList<Konto> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    public TreeNodeExtended getRootProjektKontaRZiS() {
        return rootProjektKontaRZiS;
    }

    public void setRootProjektKontaRZiS(TreeNodeExtended rootProjektKontaRZiS) {
        this.rootProjektKontaRZiS = rootProjektKontaRZiS;
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
}
