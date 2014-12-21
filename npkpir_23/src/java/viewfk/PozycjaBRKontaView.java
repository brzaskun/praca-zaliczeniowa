/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PozycjaRZiSFKBean;
import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontopozycja;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
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
    private KontopozycjaDAO kontopozycjaDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private UkladBR uklad;
    private String wnmaPrzypisywanieKont;
    private Konto boxNaKonto;
    private boolean aktywa0pasywa1;
    private List<Konto> wykazkont;
    private ArrayList<Konto> przyporzadkowanekonta;
    private TreeNodeExtended rootProjektKonta;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private int level = 0;
    private String wybranapozycja;
    private TreeNode wybranynodekonta;

    public PozycjaBRKontaView() {
        this.wykazkont = new ArrayList<>();
        this.rootProjektKonta = new TreeNodeExtended("root", null);
        this.pozycje = new ArrayList<>();
        this.przyporzadkowanekonta = new ArrayList<>();
    }

    public void pobierzukladkontoR() {
        przyporzadkowanekonta = new ArrayList<>();
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaDAO, uklad, wpisView);
        pozycje = new ArrayList<>();
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
                pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
        } catch (Exception e) {
        }
        drugiinit();
        uzupelnijpozycjeOKontaR(pozycje);
        rootProjektKonta.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKonta, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(rootProjektKonta, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }

    public void pobierzukladkontoB(String aktywapasywa) {
        przyporzadkowanekonta = new ArrayList<>();
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaDAO, uklad, wpisView);
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
        } catch (Exception e) {
        }
        drugiinitbilansowe();
        uzupelnijpozycjeOKonta(pozycje);
        rootProjektKonta.getChildren().clear();
        PozycjaRZiSFKBean.ustawRootaprojekt(rootProjektKonta, pozycje);
        level = PozycjaRZiSFKBean.ustawLevel(rootProjektKonta, pozycje);
        Msg.msg("i", "Pobrano układ ");
    }

    private void drugiinit() {
        wykazkont.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikWpisu(), "0", "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalityki(pobraneKontaSyntetyczne, wykazkont, kontoDAO, wpisView.getPodatnikWpisu());
        Collections.sort(wykazkont, new Kontocomparator());
    }

    private void drugiinitbilansowe() {
        wykazkont.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikWpisu(), "0", "bilansowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalityki(pobraneKontaSyntetyczne, wykazkont, kontoDAO, wpisView.getPodatnikWpisu(), aktywa0pasywa1);
        Collections.sort(wykazkont, new Kontocomparator());
    }

    private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(kontoDAO, p, pozycjaBilansDAO, wpisView.getPodatnikWpisu(), aktywa0pasywa1);
        }
    }

    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        for (PozycjaRZiSBilans p : pozycje) {
            PozycjaRZiSFKBean.wyszukajprzyporzadkowaneRLista(kontoDAO, p, pozycjaRZiSDAO, wpisView.getPodatnikWpisu(), aktywa0pasywa1);
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
                    RequestContext.getCurrentInstance().execute("PF('kontownmawyborRZiS').show();");
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
                wykazkont.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = new Kontopozycja();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView.getPodatnikWpisu(), kontopozycjaDAO);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
            }
            uzupelnijpozycjeOKontaR(pozycje);
            RequestContext.getCurrentInstance().update("form:dataList");
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
                    RequestContext.getCurrentInstance().execute("PF('kontownmawybor').show();");
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
                wykazkont.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = new Kontopozycja();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, wpisView.getPodatnikWpisu(), kontopozycjaDAO);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            }
            uzupelnijpozycjeOKonta(pozycje);
            RequestContext.getCurrentInstance().update("formbilansuklad:dataList");
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
                Kontopozycja kp = new Kontopozycja();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("form:dostepnekonta");
                RequestContext.getCurrentInstance().update("form:selected");
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
        RequestContext.getCurrentInstance().update("form:dataList");
    }

    public void onKontoDropKontaSpecjalneIstniejeKPRZiS() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //trzeba wyszukac konto bo nie odswiezalem listy i w zwiazku z tym encja z listy nie zgadza sie z encja z bazy;
            Konto konto = kontoDAO.findKonto(boxNaKonto.getPelnynumer(), wpisView.getPodatnikWpisu());
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = konto.getKontopozycjaID();
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
                wykazkont.remove(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont, aktywa0pasywa1);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("form:dostepnekonta");
                RequestContext.getCurrentInstance().update("form:selected");
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
        RequestContext.getCurrentInstance().update("form:dataList");
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
                wykazkont.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = new Kontopozycja();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = new Kontopozycja();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            }
        }
        uzupelnijpozycjeOKonta(pozycje);
        RequestContext.getCurrentInstance().update("formbilansuklad:dataList");
    }

    public void onKontoDropKontaSpecjalneIstniejeKP() {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            //trzeba wyszukac konto bo nie odswiezalem listy i w zwiazku z tym encja z listy nie zgadza sie z encja z bazy;
            Konto konto = kontoDAO.findKonto(boxNaKonto.getPelnynumer(), wpisView.getPodatnikWpisu());
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                wykazkont.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = konto.getKontopozycjaID();
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
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont, aktywa0pasywa1);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                Kontopozycja kp = konto.getKontopozycjaID();
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
                wykazkont.remove(konto);
                //czesc nanoszaca informacje na potomku
                if (konto.isMapotomkow() == true) {
                    PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkoweIstniejeKP(konto, kp, kontoDAO, wpisView.getPodatnikWpisu(), wnmaPrzypisywanieKont, aktywa0pasywa1);
                }
                //czesc nanoszaca informacje na macierzyste
                if (konto.getMacierzysty() > 0) {
                    PozycjaRZiSFKBean.oznaczmacierzyste(konto, kp, uklad, kontoDAO, wpisView.getPodatnikWpisu());
                }
                RequestContext.getCurrentInstance().update("formbilansuklad:dostepnekonta");
                RequestContext.getCurrentInstance().update("formbilansuklad:selected");
            }
        }
        uzupelnijpozycjeOKonta(pozycje);
        RequestContext.getCurrentInstance().update("formbilansuklad:dataList");
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
             if (wykazkont.contains(konto)) {
                    wykazkont.remove(konto);
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                } else {
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                }
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikWpisu(), wnma);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
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
             if (wykazkont.contains(konto)) {
                    wykazkont.remove(konto);
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                } else {
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                }
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikWpisu(), wnma);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikWpisu(), kontopozycjaDAO);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
            }
             if (wykazkont.contains(konto)) {
                    wykazkont.remove(konto);
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                } else {
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
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
                if (wykazkont.contains(konto)) {
                    wykazkont.remove(konto);
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                } else {
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                }
            } else if (konto.getKontopozycjaID().getPozycjaMa() != null && konto.getKontopozycjaID().getPozycjaMa().equals(wybranapozycja)) {
                wnma = "ma";
                konto.getKontopozycjaID().setPozycjaMa(null);
                if (!(konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaWn().equals(wybranapozycja))) {
                    przyporzadkowanekonta.remove(konto);
                }
                if (wykazkont.contains(konto)) {
                    wykazkont.remove(konto);
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                } else {
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                }
            }
            if (konto.getKontopozycjaID().getPozycjaWn() == null && konto.getKontopozycjaID().getPozycjaMa() == null) {
                konto.setKontopozycjaID(null);
                if (wykazkont.contains(konto)) {
                    wykazkont.remove(konto);
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                } else {
                    wykazkont.add(konto);
                    Collections.sort(wykazkont, new Kontocomparator());
                }
            }
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikWpisu(), wnma);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), null, kontoDAO, wpisView.getPodatnikWpisu(), kontopozycjaDAO);
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikWpisu());
            }
            wykazkont.add(konto);
            Collections.sort(wykazkont, new Kontocomparator());
        }
        uzupelnijpozycjeOKontaR(pozycje);
        RequestContext.getCurrentInstance().update("form:dataList");
    }

    public void wybranopozycjeRZiS() {
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja, wpisView.getPodatnikWpisu(), aktywa0pasywa1));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }

    public void wybranopozycjeBilans() {
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja, wpisView.getPodatnikWpisu(), aktywa0pasywa1));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaBilans) wybranynodekonta.getData()).getNazwa());
    }

    public void rozwinwszystkie(TreeNodeExtended root) {
        level = root.ustaldepthDT(pozycje) - 1;
        root.expandAll();
    }

    public void rozwin(TreeNodeExtended root) {
        int maxpoziom = root.ustaldepthDT(pozycje);
        if (level < --maxpoziom) {
            root.expandLevel(level++);
        }
    }

    public void zwinwszystkie(TreeNodeExtended root) {
        root.foldAll();
        level = 0;
    }

    public void zwin(TreeNodeExtended root) {
        root.foldLevel(--level);
    }

    public void rozwinrzadanalityki(Konto konto) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), konto.getPelnynumer());
        if (lista.size() > 0) {
            wykazkont.addAll(kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), konto.getPelnynumer()));
            wykazkont.remove(konto);
            Collections.sort(wykazkont, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void zwinrzadanalityki(Konto konto) {
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzanePodatnik(wpisView.getPodatnikWpisu(), konto.getMacierzyste());
        List<Konto> listaPotomne = new ArrayList<>();
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomnePodatnik(wpisView.getPodatnikWpisu(), t));
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
            Konto macierzyste = kontoDAO.findKonto(konto.getMacierzyste(), wpisView.getPodatnikWpisu());
            for (Konto p : listaSiostrzane) {
                wykazkont.remove(p);
            }
            wykazkont.add(macierzyste);
            Collections.sort(wykazkont, new Kontocomparator());
        }
    }
//<editor-fold defaultstate="collapsed" desc="comment">

    public ArrayList<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
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

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public ArrayList<Konto> getPrzyporzadkowanekonta() {
        return przyporzadkowanekonta;
    }

    public void setPrzyporzadkowanekonta(ArrayList<Konto> przyporzadkowanekonta) {
        this.przyporzadkowanekonta = przyporzadkowanekonta;
    }

    public TreeNodeExtended getRootProjektKonta() {
        return rootProjektKonta;
    }

    public void setRootProjektKonta(TreeNodeExtended rootProjektKonta) {
        this.rootProjektKonta = rootProjektKonta;
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
