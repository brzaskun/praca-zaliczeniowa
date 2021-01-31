/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontFKKopiujBean;
import beansFK.PozycjaRZiSFKBean;
import comparator.UkladBRNamecomparator;
import comparator.UkladBRcomparator;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.UkladBRDAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class UkladBRView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<UkladBR> lista;
    private List<UkladBR> listawszyscyrokbiezacy;
    private List<UkladBR> listarokbiezacy;
    private List<UkladBR> listarokuprzedni;
    private List<UkladBR> listaWzorcowy;
    private List<UkladBR> listaWzorcowyBiezacy;
    private List<UkladBR> listaWzorcowyUprzedni;
    @Inject
    private WpisView wpisView;
    @Inject
    private PozycjaBRKontaView pozycjaBRKontaView;
    @Inject
    private PlanKontView planKontView;
    @Inject
    private UkladBR selected;
    private UkladBR wybranyukladwzorcowy;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    private UkladBR ukladzrodlowy;
    private String ukladdocelowyrok;
    private String nazwanowegoukladu;

    public UkladBRView() {
         ////E.m(this);
        lista = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        try {
            lista = ukladBRDAO.findPodatnik(wpisView.getPodatnikObiekt());
            listarokbiezacy = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            listarokuprzedni = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
            listaWzorcowy = ukladBRDAO.findPodatnik(wpisView.getPodatnikwzorcowy());
            listawszyscyrokbiezacy = ukladBRDAO.findUkladByRok(wpisView.getRokWpisuSt());
            Collections.sort(listawszyscyrokbiezacy, new UkladBRNamecomparator());
            listaWzorcowyUprzedni = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(), wpisView.getRokUprzedniSt());
            listaWzorcowyBiezacy = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            Collections.sort(listaWzorcowy, new UkladBRcomparator());
            ukladdocelowyrok = wpisView.getRokWpisuSt();
        } catch (Exception e) {
        }
    }

    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad " + selected.getUklad());
    }

    public void dodaj() {
        for (UkladBR p : listaWzorcowy) {
            if (p.getUklad().equals(nazwanowegoukladu)) {
                Msg.msg("e", "Nazwa nowego układu jest już wykorzystana we wzorcach. Nie można dodać układu.");
                return;
            }
        }
        try {
            UkladBR ukladBR = new UkladBR();
            ukladBR.setPodatnik(wpisView.getPodatnikObiekt());
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setUklad(nazwanowegoukladu);
            ukladBR.setZwykly0wzrocowy1(false);
            ukladBRDAO.create(ukladBR);
            lista.add(ukladBR);
            nazwanowegoukladu = null;
            Msg.msg("i", "Dodano nowy układ podatnika");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania układu dla podatnika. " + e.getMessage());
        }
    }
    
    public void kopiujuklad() {
        try {
            List<Konto> kontagrupa1 = kontoDAOfk.findKontaGrupa(wpisView,"1%");
            if (kontagrupa1 == null || kontagrupa1.isEmpty()) {
                Msg.msg("e", "Brak planu kont w bieżacym roku. Nie można kopiować układu");
            } else if (!ukladdocelowyrok.equals(wpisView.getRokWpisuSt())) {
                Msg.msg("e", "Rok docelowy jest inny od roku bieżącego. Nie można kopiować układu");
            } else {
                UkladBR ukladBR = serialclone.SerialClone.clone(ukladzrodlowy);
                ukladBR.setPodatnik(wpisView.getPodatnikObiekt());
                ukladBR.setRok(ukladdocelowyrok);
                ukladBR.setImportowany(true);
                ukladBRDAO.create(ukladBR);
                PlanKontFKKopiujBean.implementujRZiS(pozycjaRZiSDAO, ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladzrodlowy.getUklad());
                PlanKontFKKopiujBean.implementujBilans(pozycjaBilansDAO, ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladzrodlowy.getUklad());
    //            skopiujPozycje("r", ukladBR, ukladzrodlowy);
    //            zaksiegujzmianypozycji("r", ukladBR);
    //            skopiujPozycje("b", ukladBR, ukladzrodlowy);
    //            zaksiegujzmianypozycji("b", ukladBR);
                lista.add(ukladBR);
                Collections.sort(lista, new UkladBRcomparator());
                wybranyukladwzorcowy = null;
                init();
                Msg.msg("i", "Skopiowano układ podatnika");
                pozycjaBRKontaView.init();
                pozycjaBRKontaView.setUkladdocelowykonta(ukladBR);
                pozycjaBRKontaView.setUkladzrodlowykonta(ukladzrodlowy);
                pozycjaBRKontaView.kopiujprzyporzadkowaniekont("r", true);
                pozycjaBRKontaView.kopiujprzyporzadkowaniekont("b", true);
                Msg.msg("i", "Skopiowano przyporządkowanie kont z układu pierwotnego");
            }
        } catch (EJBException ejb) {
            Msg.msg("e", "Nieudana próba skopiowania układu. Układ za dany rok już istnieje " + ejb.getMessage());
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba skopiowania układu. " + e.getMessage());
        }
    }

    public void implementujWzorcowy() {
        try {
            UkladBR ukladBR = serialclone.SerialClone.clone(wybranyukladwzorcowy);
            ukladBR.setPodatnik(wpisView.getPodatnikObiekt());
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setImportowany(true);
            ukladBRDAO.create(ukladBR);
            PlanKontFKKopiujBean.implementujRZiS(pozycjaRZiSDAO, wybranyukladwzorcowy, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wybranyukladwzorcowy.getUklad());
            PlanKontFKKopiujBean.implementujBilans(pozycjaBilansDAO, wybranyukladwzorcowy, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wybranyukladwzorcowy.getUklad());
            lista.add(ukladBR);
            init();
            Msg.msg("i", "Zaimplementowano układ wzorcowy jako nowy układ podatnika");
            pozycjaBRKontaView.init();
            pozycjaBRKontaView.importujwzorcoweprzyporzadkowanie("r");
            pozycjaBRKontaView.importujwzorcoweprzyporzadkowanie("b");
            planKontView.porzadkowanieKontPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PozycjaRZiSFKBean.zmianaukladu("bilansowe", ukladBR, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PozycjaRZiSFKBean.zmianaukladu("wynikowe", ukladBR, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Msg.msg("i", "Skopiowano przyporządkowanie kont z układu wzorcowego");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania implementacji układu wzorcowego. " + e.getMessage());
        }
    }
    
    public void implementujWzorcowySF(Podatnik podatnik, String rok) {
        try {
            UkladBR ukladBR = new UkladBR();
            ukladBR.setUklad("Podstawowy");
            ukladBR.setPodatnik(podatnik);
            ukladBR.setRok(rok);
            ukladBR.setImportowany(true);
            ukladBRDAO.create(ukladBR);
            PlanKontFKKopiujBean.implementujRZiS(pozycjaRZiSDAO, wybranyukladwzorcowy, podatnik.getNazwapelna(), rok, wybranyukladwzorcowy.getUklad());
            PlanKontFKKopiujBean.implementujBilans(pozycjaBilansDAO, wybranyukladwzorcowy, podatnik.getNazwapelna(), rok, wybranyukladwzorcowy.getUklad());
            planKontView.porzadkowanieKontPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PozycjaRZiSFKBean.zmianaukladu("bilansowe", ukladBR, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PozycjaRZiSFKBean.zmianaukladu("wynikowe", ukladBR, ukladBRDAO, pozycjaRZiSDAO, kontopozycjaZapisDAO, kontoDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Msg.msg("i", "Zaimplementowano układ wzorcowy jako nowy układ podatnika");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania implementacji układu wzorcowego. " + e.getMessage());
        }
    }

    public void usun() {
        try {
            if (selected != null) {
                List<Konto> konta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                for (Konto k : konta) {
                    k.czyscPozycje();
                }
                kontoDAOfk.editList(konta);
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(selected, "wynikowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(selected, "bilansowe");
                ukladBRDAO.remove(selected);
                lista.remove(selected);
                pozycjaRZiSDAO.findRemoveRzisuklad(selected);
                pozycjaBilansDAO.findRemoveBilansuklad(selected);
                selected = null;
                Msg.msg("i", "Usunięto wybrany układ");
            } else {
                Msg.msg("e", "Nie wybrano układu do usunięcia");
            }
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba usuniecia układu." + e.getMessage());
        }
    }

    

    

    //<editor-fold defaultstate="collapsed" desc="comment">
    public UkladBR getSelected() {
        return selected;
    }

    public void setSelected(UkladBR selected) {
        this.selected = selected;
    }

    public List<UkladBR> getListarokuprzedni() {
        return listarokuprzedni;
    }

    public void setListarokuprzedni(List<UkladBR> listarokuprzedni) {
        this.listarokuprzedni = listarokuprzedni;
    }

    public PlanKontView getPlanKontView() {
        return planKontView;
    }

    public void setPlanKontView(PlanKontView planKontView) {
        this.planKontView = planKontView;
    }


    public PozycjaBRKontaView getPozycjaBRKontaView() {
        return pozycjaBRKontaView;
    }

    public void setPozycjaBRKontaView(PozycjaBRKontaView pozycjaBRKontaView) {
        this.pozycjaBRKontaView = pozycjaBRKontaView;
    }

    public String getNazwanowegoukladu() {
        return nazwanowegoukladu;
    }

    public void setNazwanowegoukladu(String nazwanowegoukladu) {
        this.nazwanowegoukladu = nazwanowegoukladu;
    }

    public UkladBR getUkladzrodlowy() {
        return ukladzrodlowy;
    }

    public void setUkladzrodlowy(UkladBR ukladzrodlowy) {
        this.ukladzrodlowy = ukladzrodlowy;
    }

    public String getUkladdocelowyrok() {
        return ukladdocelowyrok;
    }

    public void setUkladdocelowyrok(String ukladdocelowyrok) {
        this.ukladdocelowyrok = ukladdocelowyrok;
    }

    public UkladBR getWybranyukladwzorcowy() {
        return wybranyukladwzorcowy;
    }

    public void setWybranyukladwzorcowy(UkladBR wybranyukladwzorcowy) {
        this.wybranyukladwzorcowy = wybranyukladwzorcowy;
    }

    public List<UkladBR> getLista() {
        return lista;
    }

    public void setLista(List<UkladBR> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public UkladBRDAO getUkladBRDAO() {
        return ukladBRDAO;
    }

    public void setUkladBRDAO(UkladBRDAO ukladBRDAO) {
        this.ukladBRDAO = ukladBRDAO;
    }

    public List<UkladBR> getListarokbiezacy() {
        return listarokbiezacy;
    }

    public void setListarokbiezacy(List<UkladBR> listarokbiezacy) {
        this.listarokbiezacy = listarokbiezacy;
    }

    public List<UkladBR> getListaWzorcowyBiezacy() {
        return listaWzorcowyBiezacy;
    }

    public void setListaWzorcowyBiezacy(List<UkladBR> listaWzorcowyBiezacy) {
        this.listaWzorcowyBiezacy = listaWzorcowyBiezacy;
    }

    public List<UkladBR> getListawszyscyrokbiezacy() {
        return listawszyscyrokbiezacy;
    }

    public void setListawszyscyrokbiezacy(List<UkladBR> listawszyscyrokbiezacy) {
        this.listawszyscyrokbiezacy = listawszyscyrokbiezacy;
    }

    public List<UkladBR> getListaWzorcowy() {
        return listaWzorcowy;
    }

    public void setListaWzorcowy(List<UkladBR> listaWzorcowy) {
        this.listaWzorcowy = listaWzorcowy;
    }

//</editor-fold>
   

   

    

    public void kopiujukladwzorcowy() {
        if (ukladzrodlowy != null) {
            try {
                UkladBR ukladdocelowy = new UkladBR(ukladzrodlowy);
                ukladdocelowy.setRok(ukladdocelowyrok);
                ukladBRDAO.create(ukladdocelowy);
                lista.add(ukladdocelowy);
                implementujRZiS(ukladzrodlowy, ukladdocelowyrok);
                implementujBilans(ukladzrodlowy, ukladdocelowyrok);
                Msg.msg("Skopiowano ukłąd wzorcowy z pozycjami");
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd. Nie skopiowano nowego układu.");
            }
        }
    }
    private void implementujRZiS(UkladBR ukladzrodlowy, String rok) {
        List<PozycjaRZiS> pozycje = pozycjaRZiSDAO.findRzisuklad(ukladzrodlowy);
        List<PozycjaRZiS> macierzyste = skopiujlevel0RZiS(pozycje, rok);
        int maxlevel = pozycjaRZiSDAO.findMaxLevelPodatnik(ukladzrodlowy);
        for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelRZiS(pozycje, macierzyste,i, rok);
        }
    }
     
      private void implementujBilans(UkladBR ukladzrodlowy, String rok) {
        List<PozycjaBilans> pozycje = pozycjaBilansDAO.findBilansukladAktywa(ukladzrodlowy);
        List<PozycjaBilans> macierzyste = skopiujlevel0Bilans(pozycje, rok);
        int maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikAktywa(ukladzrodlowy);
        for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelBilans(pozycje, macierzyste,i, rok);
        }
        pozycje = pozycjaBilansDAO.findBilansukladPasywa(ukladzrodlowy);
        macierzyste = skopiujlevel0Bilans(pozycje, rok);
        maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikPasywa(ukladzrodlowy);
        for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelBilans(pozycje, macierzyste,i, rok);
        }
    }
     
      private List<PozycjaRZiS> skopiujlevel0RZiS(List<PozycjaRZiS> pozycje, String rok) {
        List<PozycjaRZiS> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPodatnik(wpisView.getPodatnikWpisu());
                r.setRok(rok);
                try {
                    pozycjaRZiSDAO.create(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
      
      private List<PozycjaBilans> skopiujlevel0Bilans(List<PozycjaBilans> pozycje, String rok) {
        List<PozycjaBilans> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPodatnik(wpisView.getPodatnikWpisu());
                r.setRok(rok);
                try {
                    pozycjaRZiSDAO.create(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaRZiS> skopiujlevelRZiS(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i, String rok) {
         List<PozycjaRZiS> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(wpisView.getPodatnikWpisu());
                    r.setRok(rok);
                    PozycjaRZiS macierzyste = wyszukajmacierzysteRZiS(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    pozycjaRZiSDAO.create(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {  E.e(e);
                    
                }
            }
        }
        return nowemacierzyste;
    }
    
    private List<PozycjaBilans> skopiujlevelBilans(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i, String rok) {
         List<PozycjaBilans> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(wpisView.getPodatnikWpisu());
                    r.setRok(rok);
                    PozycjaBilans macierzyste = wyszukajmacierzysteBilans(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    pozycjaRZiSDAO.create(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {  E.e(e);
                    
                }
            }
        }
        return nowemacierzyste;
    }
    
    private PozycjaRZiS wyszukajmacierzysteRZiS(PozycjaRZiS macierzyste, List<PozycjaRZiS> macierzystelista) {
        PozycjaRZiS mac = macierzyste.getMacierzysta();
        for (PozycjaRZiS p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }
    
    private PozycjaBilans wyszukajmacierzysteBilans(PozycjaBilans macierzyste, List<PozycjaBilans> macierzystelista) {
        PozycjaBilans mac = macierzyste.getMacierzysta();
        for (PozycjaBilans p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }

    public void uzupelnijpozycje() {
        Msg.msg("Start");
        List<PozycjaBilans> bilansowe = pozycjaBilansDAO.findAll();
        for (PozycjaBilans p : bilansowe) {
            if (p.getMacierzysta() != null) {
                try {
                    PozycjaBilans mac = p.getMacierzysta();
                    if (mac != null) {
                        p.setMacierzysta(mac);
                    }
                } catch (Exception r) {
                }
            }
        }
        pozycjaBilansDAO.editList(bilansowe);
        Msg.msg("Bilansowe");
        List<PozycjaRZiS> wynikowe = pozycjaRZiSDAO.findAll();
        for (PozycjaRZiS p : wynikowe) {
            if (p.getMacierzysta() != null) {
                try {
                    PozycjaRZiS mac = p.getMacierzysta();
                    if (mac != null) {
                        p.setMacierzysta(mac);
                    }
                } catch (Exception r) {
                }
            }
        }
        pozycjaRZiSDAO.editList(wynikowe);
        Msg.msg("Wynikowe");
    }

    void ustawukladwzorcowySF(String rok) {
        UkladBR zwrot = null;
        for (UkladBR p : listaWzorcowy) {
            if (p.getUklad().equals("Podstawowy") && p.getRok().equals(rok)) {
                zwrot = p;
                this.setWybranyukladwzorcowy(p);
                Msg.msg("Wybrano układ wzorcowy");
                break;
            }
        }
        if (zwrot==null) {
            Msg.msg("Nie znaleziono odpowiedniego ukłądu wzorcowego");
            return;
        }
    }
    
    
}
