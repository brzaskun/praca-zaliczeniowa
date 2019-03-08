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
import java.util.List;
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
public class PozycjaBRKontaWzorcowyView implements Serializable {

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
    private UkladBR uklad;
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
    private TreeNode wybranynodekonta;
    

    public PozycjaBRKontaWzorcowyView() {
         E.m(this);
        this.kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
        this.rootProjektKontaRZiS = new TreeNodeExtended("root", null);
        this.rootProjektKontaBilans = new TreeNodeExtended("root", null);
        this.pozycje = Collections.synchronizedList(new ArrayList<>());
        this.przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
    }

    public void pobierzukladkontoR() {
        try {
            uklad.oznaczUkladBR(ukladBRDAO);
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            wyczyscKontaWzorcowy("wynikowe", wpisView.getPodatnikwzorcowy(), uklad.getRok());
            kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
            //kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, true, "wynikowe");
            pozycje = Collections.synchronizedList(new ArrayList<>());
            try {
                pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
                if (pozycje.isEmpty()) {
                    pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
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
        } catch (Exception e) {
            E.e(e);
        }
    }


    public void pobierzukladkontoB(String aktywapasywa) {
        try {
            uklad.oznaczUkladBR(ukladBRDAO);
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            wyczyscKontaWzorcowy("bilansowe", wpisView.getPodatnikwzorcowy(), uklad.getRok());
            kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            //kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, true, "bilansowe");
            pozycje = Collections.synchronizedList(new ArrayList<>());
            try {
                if (aktywapasywa.equals("aktywa")) {
                    aktywa0pasywa1 = false;
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                } else {
                    aktywa0pasywa1 = true;
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
                }
                if (pozycje.isEmpty()) {
                    pozycje.add(new PozycjaBilans(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
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
        } catch (Exception e) {
            E.e(e);
        }
    }

    private void drugiinit() {
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()), null, "wynikowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiRZiS(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void drugiinitbilansowe() {
        kontabezprzydzialu.clear();
        List<Konto> pobraneKontaSyntetyczne = kontoDAO.findKontaPotomne(wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()), null, "bilansowe");
        PozycjaRZiSFKBean.wyluskajNieprzyporzadkowaneAnalitykiBilans(pobraneKontaSyntetyczne, kontabezprzydzialu, kontoDAO, wpisView.getPodatnikwzorcowy(), aktywa0pasywa1, Integer.parseInt(uklad.getRok()));
        Collections.sort(kontabezprzydzialu, new Kontocomparator());
    }

    private void uzupelnijpozycjeOKonta(List<PozycjaRZiSBilans> pozycje) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowaneAll("bilansowe", wpisView.getPodatnikwzorcowy(), uklad.getRokInt());
        if (!lista.isEmpty()) {
            for (PozycjaRZiSBilans p : pozycje) {
                PozycjaRZiSFKBean.wyszukajprzyporzadkowaneBLista(lista, p, wpisView, aktywa0pasywa1);
            }
        }
    }

    private void uzupelnijpozycjeOKontaR(List<PozycjaRZiSBilans> pozycje) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowaneAll("wynikowe", wpisView.getPodatnikwzorcowy(), uklad.getRokInt());
        if (!lista.isEmpty()) {
            pozycje.stream().forEach((p)->{
                PozycjaRZiSFKBean.wyszukajprzyporzadkowaneRLista(lista, p);
            });
            pozycjaBilansDAO.editList(pozycje);
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
                        onKontoDropKontaSpecjalneRZiS(uklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalneRZiS(uklad);
                    }
                    uzupelnijpozycjeOKontaR(pozycje);
                }
                //to duperele porzadkujace sytuacje w okienkach
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                PlanKontFKBean.przyporzadkujRZiS_kontozwykle(wybranapozycja, konto, uklad, kontoDAO, wpisView.getPodatnikwzorcowy(), null);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
            }
            uzupelnijpozycjeOKontaR(pozycje);
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
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
                        onKontoDropKontaSpecjalne(uklad);
                    } else {
                        wnmaPrzypisywanieKont = "wn";
                        onKontoDropKontaSpecjalne(uklad);
                    }
                    uzupelnijpozycjeOKonta(pozycje);
                }
                if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaMa() != null ) {
                    kontabezprzydzialu.remove(konto);
                }
            } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
                PlanKontFKBean.przyporzadkujBilans_kontozwykle(wybranapozycja, konto, uklad, kontoDAO, wpisView.getPodatnikwzorcowy(), null, aktywa0pasywa1);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
                uzupelnijpozycjeOKonta(pozycje);
            }
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
        }

    }

    public void onKontoDropKontaSpecjalneRZiS(UkladBR ukladpodatnika) {
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
                PlanKontFKBean.przyporzadkujRZiS_kontoszczegolne(wybranapozycja,konto, ukladpodatnika, kontoDAO, wpisView.getPodatnikwzorcowy(), wnmaPrzypisywanieKont);
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //wywalamy tylko obustronnnie przyporzadkowane konta
                if (konto.getKontopozycjaID().getPozycjaWn() != null && konto.getKontopozycjaID().getPozycjaMa() != null) {
                    kontabezprzydzialu.remove(konto);
                }
            }
        }
        uzupelnijpozycjeOKontaR(pozycje);
    }

    
    public void onKontoDropKontaSpecjalne(UkladBR ukladpodatnika) {
        if (wybranapozycja == null) {
            Msg.msg("e", "Nie wybrano pozycji rozrachunku, nie można przyporządkowac konta");
        } else {
            Konto konto = boxNaKonto;
            if (przyporzadkowanekonta.contains(konto)) {
                    przyporzadkowanekonta.remove(konto);
                }
            //to duperele porzadkujace sytuacje w okienkach
            if (konto.getZwyklerozrachszczegolne().equals("rozrachunkowe") || konto.getZwyklerozrachszczegolne().equals("vat")) {
                PlanKontFKBean.przyporzadkujBilans_kontoszczegolne(wybranapozycja,konto, ukladpodatnika, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1,"rozrachunkowe/vat", wpisView.getPodatnikwzorcowy());
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                kontabezprzydzialu.remove(konto);
            } else if (konto.getZwyklerozrachszczegolne().equals("szczególne")) {
                //czesc przekazujaca przyporzadkowanie do konta do wymiany
                PlanKontFKBean.przyporzadkujBilans_kontoszczegolne(wybranapozycja,konto, ukladpodatnika, kontoDAO, wpisView, wnmaPrzypisywanieKont, aktywa0pasywa1,"szczególne", wpisView.getPodatnikwzorcowy());
                przyporzadkowanekonta.add(konto);
                Collections.sort(przyporzadkowanekonta, new Kontocomparator());
                //czesc nanoszaca informacje na potomku
            }
            uzupelnijpozycjeOKonta(pozycje);
            RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikwzorcowy(), wnma, Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()));
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikwzorcowy(), wnma, Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto, null, kontoDAO, wpisView.getPodatnikwzorcowy(), "bilans", Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()));
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
                PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, null, kontoDAO, wpisView.getPodatnikwzorcowy(), wnma, Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()));
            }
        } else if (konto.getZwyklerozrachszczegolne().equals("zwykłe")) {
            przyporzadkowanekonta.remove(konto);
            konto.setKontopozycjaID(null);
            kontoDAO.edit(konto);
            //zerujemy potomkow
            if (konto.isMapotomkow() == true) {
                PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto, null, kontoDAO, wpisView.getPodatnikwzorcowy(), "wynik", Integer.parseInt(uklad.getRok()));
            }
            //zajmujemy sie macierzystym, ale sprawdzamy czy nie ma siostr
            if (konto.getMacierzysty() > 0) {
                PozycjaRZiSFKBean.odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView.getPodatnikwzorcowy(), Integer.parseInt(uklad.getRok()));
            }
            kontabezprzydzialu.add(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        }
        uzupelnijpozycjeOKontaR(pozycje);
        RequestContext.getCurrentInstance().update(wybranapozycja_wiersz);
    }

    public void wybranopozycjeRZiS() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        TreeTable table = (TreeTable) ctx.getViewRoot().findComponent("formprzypisywaniekont:dataList");
        String rowkey = table.getRowKey();
        wybranapozycja_wiersz = "formprzypisywaniekont:dataList:"+rowkey+":liczba";
        wybranapozycja = ((PozycjaRZiS) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowane(kontoDAO, wybranapozycja, aktywa0pasywa1, uklad));
        Msg.msg("i", "Wybrano pozycję " + ((PozycjaRZiS) wybranynodekonta.getData()).getNazwa());
    }

    public void wybranopozycjeBilans() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        TreeTable table = (TreeTable) ctx.getViewRoot().findComponent("formdialogbilansukladwzorcowy:dataList");
        String rowkey = table.getRowKey();
        wybranapozycja_wiersz = "formdialogbilansukladwzorcowy:dataList:"+rowkey+":liczba";
        wybranapozycja = ((PozycjaBilans) wybranynodekonta.getData()).getPozycjaString();
        przyporzadkowanekonta.clear();
        przyporzadkowanekonta.addAll(PozycjaRZiSFKBean.wyszukajprzyporzadkowaneB(kontoDAO, wybranapozycja, aktywa0pasywa1, uklad));
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
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikwzorcowy(),Integer.parseInt(uklad.getRok()), konto);
        if (lista.size() > 0) {
            kontabezprzydzialu.addAll(kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikwzorcowy(),Integer.parseInt(uklad.getRok()), konto));
            kontabezprzydzialu.remove(konto);
            Collections.sort(kontabezprzydzialu, new Kontocomparator());
        } else {
            Msg.msg("e", "Konto nie posiada analityk");
        }
    }

    public void zwinrzadanalityki(Konto konto) {
        List<Konto> listaSiostrzane = kontoDAO.findKontaSiostrzanePodatnik(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu(), konto.getKontomacierzyste());
        List<Konto> listaPotomne = Collections.synchronizedList(new ArrayList<>());
        for (Konto t : listaSiostrzane) {
            listaPotomne.addAll(kontoDAO.findKontaWszystkiePotomnePodatnik(new ArrayList<Konto>(), wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu(), t));
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
            Konto macierzyste = kontoDAO.findKonto(konto.getKontomacierzyste().getPelnynumer(), wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu());
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
                try {
                    if (p.isWynik0bilans1() == false) {
                        kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                    }
                } catch (Exception e) {
                    // ma usuwac jak zmienie kwalifikacje przyporzadkowanego juz konta
                    E.e(e);
                    KontopozycjaZapis znajda = kontopozycjaZapisDAO.findByKonto(p.getKontoID(), uklad);
                    if (znajda != null) {
                        kontopozycjaZapisDAO.destroy(znajda);
                        kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                    }
                }
            }
        }
        if (rb.equals("b")) {
            List<KontopozycjaBiezaca> pozycjebiezace = kontopozycjaBiezacaDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            for (KontopozycjaBiezaca p : pozycjebiezace) {
                try {
                    if (p.isWynik0bilans1() == true) {
                        kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                    }
                } catch (Exception e) {
                    // ma usuwac jak zmienie kwalifikacje przyporzadkowanego juz konta
                    E.e(e);
                    KontopozycjaZapis znajda = kontopozycjaZapisDAO.findByKonto(p.getKontoID(), uklad);
                    if (znajda != null) {
                        kontopozycjaZapisDAO.destroy(znajda);
                        kontopozycjaZapisDAO.dodaj(new KontopozycjaZapis(p));
                    }
                }
            }
        }
        Msg.msg("Zapamiętano przyporządkowane pozycje wzorcowe");
    }
    
    public void resetujprzyporzadkowanie(String rb) {
        if (rb.equals("r")) {
            wyczyscKontaWzorcowy("wynikowe", uklad.getPodatnik(), uklad.getRok());
            kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            //kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(uklad, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
            pobierzukladkontoR();
        } else {
            wyczyscKontaWzorcowy("bilansowe", uklad.getPodatnik(), uklad.getRok());
            kontabezprzydzialu = Collections.synchronizedList(new ArrayList<>());
            przyporzadkowanekonta = Collections.synchronizedList(new ArrayList<>());
            //kontopozycjaBiezacaDAO.usunKontoPozycjaBiezacaPodatnikUklad(uklad, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "bilansowe");
            if (aktywa0pasywa1==false) {
                pobierzukladkontoB("aktywa");
            } else {
                pobierzukladkontoB("pasywa");
            }
        }
    }
    
   
   private void wyczyscKontaWzorcowy(String rb, Podatnik podatnik, String rok) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
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
