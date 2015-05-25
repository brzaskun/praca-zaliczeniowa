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
public class PozycjaBRKontaView implements Serializable {

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
    private UkladBR ukladzrodlowykonta;
    private UkladBR ukladdocelowykonta;

    public PozycjaBRKontaView() {
        this.kontabezprzydzialu = new ArrayList<>();
        this.rootProjektKontaRZiS = new TreeNodeExtended("root", null);
        this.rootProjektKontaBilans = new TreeNodeExtended("root", null);
        this.pozycje = new ArrayList<>();
        this.przyporzadkowanekonta = new ArrayList<>();
    }

    public void pobierzukladkontoR() {
        try {
            przyporzadkowanekonta = new ArrayList<>();
            wyczyscKonta("wynikowe");
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, false, "wynikowe");
            pozycje = new ArrayList<>();
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
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
            przyporzadkowanekonta = new ArrayList<>();
            wyczyscKonta("bilansowe");
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, false, "bilansowe");
            pozycje = new ArrayList<>();
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
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView, "0", "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiRZiS(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView, false);
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void drugiinitbilansowe() {
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView, "0", "bilansowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiBilans(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView, aktywa0pasywa1, false);
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(kontoDAO, p, pozycjaBilansDAO, wpisView, aktywa0pasywa1, false, uklad);
        }
    }

    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneRLista(kontoDAO, p, pozycjaRZiSDAO, wpisView, false, uklad);
        }
    }

    public void onKontoDropR(Konto konto, String br) {
        boolean wzorcowy = false;
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
                        onKontoDropKontaSpecjalneIstniejeKPRZiS(wzorcowy, uklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneIstniejeKPRZiS(wzorcowy, uklad);
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
                kp.setSyntetykaanalityka("wynikowe");
                kp.setKontoID(konto);
                kp.setUkladBR(uklad);
                konto.setKontopozycjaID(kp);
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView, wzorcowy, "wynik");
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, wzorcowy);
                }
            }
            uzupelnijpozycjeOKontaR(pozycje);
        }
    }

    public void onKontoDropB(Konto konto, String br) {
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
                        onKontoDropKontaSpecjalneIstniejeKP(wzorcowy, uklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneIstniejeKP(wzorcowy, uklad);
                    }
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
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
                kp.setWynik0bilans1(true);
                kp.setKontoID(konto);
                kp.setUkladBR(uklad);
                kp.setWynik0bilans1(true);
                konto.setKontopozycjaID(kp);
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView, wzorcowy, "bilans");
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView, wzorcowy);
                }
            }
            uzupelnijpozycjeOKonta(pozycje);
        }

    }

    public void onKontoDropKontaSpecjalneRZiS(boolean wzorcowy, UkladBR ukladpodatnika) {
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
                if (wnmaPrzypisywanieKont.equals("wn")) {
                    kp.setPozycjaWn(wybranapozycja);
                    kp.setStronaWn("88");
                    kp.setSyntetykaanalityka("szczególne");
                    kp.setKontoID(konto);
                    kp.setUkladBR(ukladpodatnika);
                    konto.setKontopozycjaID(kp);
                } else {
                    kp.setPozycjaMa(wybranapozycja);
                    kp.setStronaMa("88");
                    kp.setSyntetykaanalityka("szczególne");
                    kp.setKontoID(konto);
                    kp.setUkladBR(ukladpodatnika);
                    konto.setKontopozycjaID(kp);
                }
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, wzorcowy);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
                }
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
    }

    public void onKontoDropKontaSpecjalneIstniejeKPRZiS(boolean wzorcowy, UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            System.out.println("Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta onKontoDropKontaSpecjalneIstniejeKPRZiS");
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //trzeba wyszukac konto bo nie odswiezalem listy i w zwiazku z tym encja z listy nie zgadza sie z encja z bazy;
            Konto konto = kontoDAO.findKonto(boxNaKonto.getPelnynumer(), wpisView);
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                KontopozycjaBiezaca kp = konto.getKontopozycjaID();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1, wzorcowy);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
                }
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
    }

    public void onKontoDropKontaSpecjalne(boolean wzorcowy, UkladBR ukladpodatnika) {
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
                    kp.setUkladBR(ukladpodatnika);
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
                    kp.setUkladBR(ukladpodatnika);
                    konto.setKontopozycjaID(kp);
                }
                kontoDAO.edit(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, wzorcowy);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
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
                    kp.setUkladBR(ukladpodatnika);
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
                    kp.setUkladBR(ukladpodatnika);
                    konto.setKontopozycjaID(kp);
                }
                kontoDAO.edit(konto);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, wzorcowy);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
                }
            }
        }
        uzupelnijpozycjeOKonta(pozycje);
    }

    public void onKontoDropKontaSpecjalneIstniejeKP(boolean wzorcowy, UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //trzeba wyszukac konto bo nie odswiezalem listy i w zwiazku z tym encja z listy nie zgadza sie z encja z bazy;
            Konto konto = kontoDAO.findKonto(boxNaKonto.getPelnynumer(), wpisView);
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1, wzorcowy);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1, wzorcowy);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
                }
            }
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView, wnma, false);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, false);
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView, wnma, false);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, false);
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView, false, "bilans");
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, false);
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
        RequestContext.getCurrentInstance().update("formbilansuklad:dataList");
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView, wnma, false);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, false);
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView, false, "wynik");
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, false);
            }
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
        uzupelnijpozycjeOKontaR(pozycje);
        RequestContext.getCurrentInstance().update("form:dataList");
    }

    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja, wpisView, aktywa0pasywa1, false, uklad));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }

    public void wybranopozycjeBilans() {
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja, wpisView, aktywa0pasywa1, false, uklad));
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
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(wpisView, konto.getPelnynumer());
        if (lista.size() > 0) {
            kontabezprzydzialu.addAll(kontoDAO.findKontaPotomnePodatnik(wpisView, konto.getPelnynumer()));
            kontabezprzydzialu.remove(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void zwinrzadanalityki(Konto konto) {
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzanePodatnik(wpisView, konto.getMacierzyste());
        List<Konto> listaPotomne = new ArrayList<>();
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomnePodatnik(wpisView, t));
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
                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
            }
        }
        if (rb.equals("b")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
            }
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje");
    }
    
    public void zaksiegujzmianypozycjiWzorcowy(String rb, UkladBR uklad) {
        if (rb.equals("r")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
            }
        }
        if (rb.equals("b")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
            }
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje");
    }

    public void importujwzorcoweprzyporzadkowanie(String rb) {
        if (uklad == null) {
            Msg.msg("e", "Nie wybrano układu. Nie można zaimplementować przyporządkowania.");
        }
        List<UkladBR> ukladyPodatnika = ukladBRDAO.findPodatnik(wpisView.getPodatnikWpisu());
        if (ukladyPodatnika != null && ukladyPodatnika.size() > 0) {
            UkladBR czyJestTakiUklad = sprawdzNazwyUkladu(ukladyPodatnika, uklad);
            if (czyJestTakiUklad == null) {
                Msg.msg("e", "W układach podatnika nie ma układu o takiej nazwie jak wzorcowy.  Nie można zaimplementować przyporządkowania");
            }
            Msg.msg("Rozpoczynam implementacje");
            UkladBR ukladwzorcowy = znajdzUkladWzorcowy(uklad);
            if (ukladwzorcowy == null) {
                Msg.msg("e", "Nie odnaleziono odpowiadającego układu wzorcowego. Przewywam implementację");
            } else {
                if (rb.equals("r")) {
                    skopiujPozycje(rb, uklad, ukladwzorcowy);
                    List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
                    kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
                    for (KontopozycjaBiezaca p : pozycjebiezace) {
                        kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                    }
                    pobierzukladkontoR();
                } else {
                    skopiujPozycje(rb, uklad, ukladwzorcowy);
                    List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
                    kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
                    for (KontopozycjaBiezaca p : pozycjebiezace) {
                        kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                    }
                    pobierzukladkontoB("aktywa");
                }
            }
        } else {
            Msg.msg("e", "Podatnik nie posiada zdefiniowanych układów Bilansu i RZiS. Nie można zaimplementować przyporządkowania.");
        }
    }

    public void kopiujwzorcoweprzyporzadkowanie(String rb) {
        if (ukladdocelowykonta.equals(ukladzrodlowykonta)) {
            Msg.msg("e", "Nie można kopiować układu w ten sam układ");
            return;
        }
        if (rb.equals("r")) {
            Msg.msg("Rozpoczynam kopiowanie przyporządkowania kont wzorcowych-wynikowych");
            skopiujPozycjeWzorcowe(rb, ukladdocelowykonta, ukladzrodlowykonta);
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
            }
            pobierzukladkontoR();
        } else {
            Msg.msg("Rozpoczynam kopiowanie przyporządkowania kont wzorcowych-bilansowych");
            skopiujPozycjeWzorcowe(rb, ukladdocelowykonta, ukladzrodlowykonta);
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
            }
            pobierzukladkontoB("aktywa");
        }
    }
    
    
    
    private UkladBR znajdzUkladWzorcowy(UkladBR ukladpodatnika) {
        List<UkladBR> lista = ukladBRDAO.findPodatnik("Wzorcowy");
        for (UkladBR p : lista) {
            if (p.getUklad().equals(ukladpodatnika.getUklad()) && p.getRok().equals(ukladpodatnika.getRok())) {
                return p;
            }
        }
        return null;
    }

    private void skopiujPozycje(String rb, UkladBR ukladpodatnika, UkladBR ukladwzorcowy) {
        if (rb.equals("r")) {
            wyczyscKonta("wynikowe");
            kontabezprzydzialu = new ArrayList<>();
            przyporzadkowanekonta = new ArrayList<>();
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "wynikowe");
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladwzorcowy, "wynikowe");
            for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
                if (!p.getSyntetykaanalityka().equals("analityka")) {
                    if (p.getSyntetykaanalityka().equals("wynikowe") || p.getSyntetykaanalityka().equals("szczególne")) {
                        System.out.println("Szukam konta " + p.getKontoID().toString());
                        try {
                            Konto kontouzytkownika = kontoDAO.findKonto(p.getKontoID().getPelnynumer(), wpisView);
                            boxNaKonto = kontouzytkownika;
                            if (kontouzytkownika != null && kontouzytkownika.getBilansowewynikowe().equals("wynikowe")) {
                                if (kontouzytkownika.getZwyklerozrachszczegolne().equals("szczególne")) {
                                    wybranapozycja = p.getPozycjaWn();
                                    wnmaPrzypisywanieKont = "wn";
                                    onKontoDropRAutoSzczegolne(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, false);
                                    wybranapozycja = p.getPozycjaMa();
                                    onKontoDropRAutoSzczegolne(kontouzytkownika, p.getPozycjaMa(), ukladpodatnika, false);
                                } else {
                                    onKontoDropRAutoZwykle(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, false);
                                }
                            }
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                } else {
                    System.out.println("konto przyporzadkowane analityka");
                }
            }
        }
        if (rb.equals("b")) {
            wyczyscKonta("bilansowe");
            kontabezprzydzialu = new ArrayList<>();
            przyporzadkowanekonta = new ArrayList<>();
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "bilansowe");
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladwzorcowy, "bilansowe");
            for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
               if (!p.getSyntetykaanalityka().equals("analityka")) {
                    if (!p.getSyntetykaanalityka().equals("syntetyka")) {//wykluczam potomkow podlaczanych automatycznie
                         System.out.println("Szukam konta "+p.getKontoID().toString());
                         try {
                             Konto kontouzytkownika = kontoDAO.findKonto(p.getKontoID().getPelnynumer(), wpisView);
                             boxNaKonto = kontouzytkownika;
                             if (kontouzytkownika != null && kontouzytkownika.getBilansowewynikowe().equals("bilansowe")) {
                                  if (!kontouzytkownika.getZwyklerozrachszczegolne().equals("zwykłe")) {
                                     wybranapozycja = p.getPozycjaWn();
                                     wnmaPrzypisywanieKont = "wn";
                                     aktywa0pasywa1 = p.getStronaWn().equals("0") ? false : true;
                                     onKontoDropBAutoSzczegolne(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, false);
                                     wybranapozycja = p.getPozycjaMa();
                                     aktywa0pasywa1 = p.getStronaMa().equals("0") ? false : true;
                                     onKontoDropBAutoSzczegolne(kontouzytkownika, p.getPozycjaMa(), ukladpodatnika, false);
                                  } else {
                                      aktywa0pasywa1 = p.getStronaWn().equals("0") ? false : true;
                                      onKontoDropBAutoZwykle(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, aktywa0pasywa1, false);
                                  }
                             }
                         } catch (Exception e) {
                             E.e(e);
                         }
                     }
               } else {
                    System.out.println("konto przyporzadkowane analityka");
               }
            }
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje");
    }
    
    private void skopiujPozycjeWzorcowe(String rb, UkladBR ukladpodatnika, UkladBR ukladwzorcowy) {
        if (rb.equals("r")) {
            wyczyscKontaWzorcowy("wynikowe");
            kontabezprzydzialu = new ArrayList<>();
            przyporzadkowanekonta = new ArrayList<>();
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "wynikowe");
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladwzorcowy, "wynikowe");
            for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
                if (!p.getSyntetykaanalityka().equals("analityka")) {
                    if (p.getSyntetykaanalityka().equals("wynikowe") || p.getSyntetykaanalityka().equals("szczególne")) {
                        System.out.println("Szukam konta " + p.getKontoID().toString());
                        try {
                            Konto kontouzytkownika = kontoDAO.findKontoWzorcowy(p.getKontoID().getPelnynumer(), ukladpodatnika);
                            boxNaKonto = kontouzytkownika;
                            if (kontouzytkownika != null && kontouzytkownika.getBilansowewynikowe().equals("wynikowe")) {
                                if (kontouzytkownika.getZwyklerozrachszczegolne().equals("szczególne")) {
                                    wybranapozycja = p.getPozycjaWn();
                                    wnmaPrzypisywanieKont = "wn";
                                    onKontoDropRAutoSzczegolne(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, true);
                                    wybranapozycja = p.getPozycjaMa();
                                    onKontoDropRAutoSzczegolne(kontouzytkownika, p.getPozycjaMa(), ukladpodatnika, true);
                                } else {
                                    onKontoDropRAutoZwykle(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, true);
                                }
                            }
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                } else {
                    System.out.println("konto przyporzadkowane analityka");
                }
            }
            zaksiegujzmianypozycjiWzorcowy("r", ukladpodatnika);
        }
        if (rb.equals("b")) {
            wyczyscKontaWzorcowy("bilansowe");
            kontabezprzydzialu = new ArrayList<>();
            przyporzadkowanekonta = new ArrayList<>();
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladpodatnika, "bilansowe");
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladwzorcowy, "bilansowe");
            for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
               if (!p.getSyntetykaanalityka().equals("analityka")) {
                    if (!p.getSyntetykaanalityka().equals("syntetyka")) {//wykluczam potomkow podlaczanych automatycznie
                         System.out.println("Szukam konta "+p.getKontoID().toString());
                         try {
                             Konto kontouzytkownika = kontoDAO.findKontoWzorcowy(p.getKontoID().getPelnynumer(), ukladpodatnika);
                             boxNaKonto = kontouzytkownika;
                             if (kontouzytkownika != null && kontouzytkownika.getBilansowewynikowe().equals("bilansowe")) {
                                  if (!kontouzytkownika.getZwyklerozrachszczegolne().equals("zwykłe")) {
                                     wybranapozycja = p.getPozycjaWn();
                                     wnmaPrzypisywanieKont = "wn";
                                     aktywa0pasywa1 = p.getStronaWn().equals("0") ? false : true;
                                     onKontoDropBAutoSzczegolne(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, true);
                                     wybranapozycja = p.getPozycjaMa();
                                     aktywa0pasywa1 = p.getStronaMa().equals("0") ? false : true;
                                     onKontoDropBAutoSzczegolne(kontouzytkownika, p.getPozycjaMa(), ukladpodatnika, true);
                                  } else {
                                      aktywa0pasywa1 = p.getStronaWn().equals("0") ? false : true;
                                      onKontoDropBAutoZwykle(kontouzytkownika, p.getPozycjaWn(), ukladpodatnika, aktywa0pasywa1, true);
                                  }
                             }
                         } catch (Exception e) {
                             E.e(e);
                         }
                     }
               } else {
                    System.out.println("konto przyporzadkowane analityka");
               }
            }
            zaksiegujzmianypozycjiWzorcowy("b", ukladpodatnika);
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje");
    }
    
    public void onKontoDropRAutoSzczegolne(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
                if (konto.getKontopozycjaID() == null) {
                        onKontoDropKontaSpecjalneRZiS(wzorcowy, ukladpodatnika);
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalneIstniejeKPRZiS(wzorcowy, ukladpodatnika);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneIstniejeKPRZiS(wzorcowy, ukladpodatnika);
                    }
                }
    }
    
    public void onKontoDropRAutoZwykle(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
        przyporzadkowanekonta.add(konto);
        Collections.sort(przyporzadkowanekonta, new Kontocomparator());
        kontabezprzydzialu.remove(konto);
        //czesc przekazujaca przyporzadkowanie do konta do wymiany
        KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
        kp.setPozycjaWn(pozycja);
        kp.setPozycjaMa(pozycja);
        kp.setStronaWn("99");
        kp.setStronaMa("99");
        kp.setSyntetykaanalityka("wynikowe");
        kp.setKontoID(konto);
        kp.setWynik0bilans1(false);
        kp.setUkladBR(ukladpodatnika);
        konto.setKontopozycjaID(kp);
        kontoDAO.edit(konto);
        //czesc nanoszaca informacje na potomku
        if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView, wzorcowy, "wynik");
        }
        //czesc nanoszaca informacje na macierzyste
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
        }
    }
    
    public void onKontoDropBAutoSzczegolne(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean wzorcowy) {
                if (konto.getKontopozycjaID() == null) {
                        onKontoDropKontaSpecjalne(wzorcowy, ukladpodatnika);
                } else {
                    if (konto.getKontopozycjaID().getPozycjaWn() != null) {
                        wnmaPrzypisywanieKont = "ma";
                        onKontoDropKontaSpecjalneIstniejeKP(wzorcowy, ukladpodatnika);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneIstniejeKP(wzorcowy, ukladpodatnika);
                    }
                }
    }
    
    public void onKontoDropBAutoZwykle(Konto konto, String pozycja, UkladBR ukladpodatnika, boolean ap, boolean wzorcowy) {
        przyporzadkowanekonta.add(konto);
        Collections.sort(przyporzadkowanekonta, new Kontocomparator());
        kontabezprzydzialu.remove(konto);
        //czesc przekazujaca przyporzadkowanie do konta do wymiany
        KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
        kp.setPozycjaWn(pozycja);
        kp.setPozycjaMa(pozycja);
        if (ap == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
            kp.setStronaWn("0");
            kp.setStronaMa("0");
        } else {
            kp.setStronaWn("1");
            kp.setStronaMa("1");
        }
        kp.setSyntetykaanalityka("zwykłe");
        kp.setKontoID(konto);
        kp.setUkladBR(ukladpodatnika);
        kp.setWynik0bilans1(true);
        konto.setKontopozycjaID(kp);
        kontoDAO.edit(konto);
        //czesc nanoszaca informacje na potomku
        if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView, wzorcowy, "bilans");
        }
        //czesc nanoszaca informacje na macierzyste
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, ukladpodatnika, kontoDAO, wpisView, wzorcowy);
        }
    }
    
   

    private void wyczyscKonta(String rb) {
        if (rb.equals("wynikowe")) {
            kontoDAO.wyzerujPozycjeWKontach(wpisView,"wynikowe");
        } else {
            kontoDAO.wyzerujPozycjeWKontach(wpisView,"bilansowe");
        }
    }
    
    private void wyczyscKontaWzorcowy(String rb) {
        if (rb.equals("wynikowe")) {
            kontoDAO.wyzerujPozycjeWKontach(wpisView,"wynikowe");
        } else {
            kontoDAO.wyzerujPozycjeWKontach(wpisView,"bilansowe");
        }
    }

    private List<KontopozycjaZapis> pobiezpozycjeZapisane(UkladBR ukladwzorcowy, String rb) {
        return kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(ukladwzorcowy, rb);
    }

    private UkladBR sprawdzNazwyUkladu(List<UkladBR> ukladyPodatnika, UkladBR ukladBR) {
        for (UkladBR p : ukladyPodatnika) {
            if (p.isImportowany() == true && p.getUklad().equals(uklad.getUklad()) && p.getRok().equals(ukladBR.getRok())) {
                return p;
            }
        }
        return null;
    }
    
    public void drukujBilansKonta() {
        if (aktywa0pasywa1 == false) {
            PdfBilans.drukujBilansKonta(rootProjektKontaBilans, wpisView, "a");
        } else {
            PdfBilans.drukujBilansKonta(rootProjektKontaBilans, wpisView, "p");
        }
    }
    
    public void drukujWynikKonta() {
            PdfRZiS.drukujRZiSKonta(rootProjektKontaRZiS, wpisView);
    }
//     private boolean sprawdzPozycjeUkladow(UkladBR ukladklienta, UkladBR ukladwzorcowy) {
//        List<String> nazwyUkladuKlienta = new ArrayList<>();
//        List<String> nazwyUkladuWzorcowego = new ArrayList();
//        ArrayList<PozycjaRZiSBilans> pozycjeRZiS = new ArrayList<>();
//        ArrayList<PozycjaRZiSBilans> pozycjeAktywa = new ArrayList<>();
//        ArrayList<PozycjaRZiSBilans> pozycjePasywa = new ArrayList<>();
//        pozycjeRZiS.addAll(pozycjaRZiSDAO.findRzisuklad(ukladklienta));
//        pozycjeAktywa.addAll(pozycjaBilansDAO.findBilansukladAktywa(ukladklienta));
//        pozycjePasywa.addAll(pozycjaBilansDAO.findBilansukladPasywa(ukladklienta));
//        ArrayList<PozycjaRZiSBilans> pozycjeRZiSWzorcowy = new ArrayList<>();
//        ArrayList<PozycjaRZiSBilans> pozycjeAktywaWzorcowy = new ArrayList<>();
//        ArrayList<PozycjaRZiSBilans> pozycjePasywaWzorcowy = new ArrayList<>();
//        pozycjeRZiSWzorcowy.addAll(pozycjaRZiSDAO.findRzisuklad(ukladwzorcowy));
//        pozycjeAktywaWzorcowy.addAll(pozycjaBilansDAO.findBilansukladAktywa(ukladwzorcowy));
//        pozycjePasywaWzorcowy.addAll(pozycjaBilansDAO.findBilansukladPasywa(ukladwzorcowy));
//        return false;
//     }
//<editor-fold defaultstate="collapsed" desc="comment">

    public ArrayList<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
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
        System.out.println("k");
    }
}
