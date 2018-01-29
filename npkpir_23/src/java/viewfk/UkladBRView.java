/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.UkladBRcomparator;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class UkladBRView implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<UkladBR> lista;
    private List<UkladBR> listarokbiezacy;
    private List<UkladBR> listaWzorcowy;
    private List<UkladBR> listaWzorcowyBiezacy;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{pozycjaBRKontaView}")
    private PozycjaBRKontaView pozycjaBRKontaView;
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
    private KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private UkladBR ukladzrodlowy;
    private String ukladdocelowyrok;
    private String nazwanowegoukladu;

    public UkladBRView() {
         E.m(this);
        lista = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        try {
            lista = ukladBRDAO.findPodatnik(wpisView.getPodatnikWpisu());
            listarokbiezacy = ukladBRDAO.findPodatnikRok(wpisView);
            listaWzorcowy = ukladBRDAO.findPodatnik("Wzorcowy");
            listaWzorcowyBiezacy = ukladBRDAO.findukladBRWzorcowyRok(wpisView.getRokWpisuSt());
            Collections.sort(listaWzorcowy, new UkladBRcomparator());
            ukladdocelowyrok = wpisView.getRokWpisuSt();
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
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
            ukladBR.setPodatnik(wpisView.getPodatnikWpisu());
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setUklad(nazwanowegoukladu);
            ukladBR.setZwykly0wzrocowy1(false);
            ukladBRDAO.dodaj(ukladBR);
            lista.add(ukladBR);
            nazwanowegoukladu = null;
            Msg.msg("i", "Dodano nowy układ podatnika");
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            Msg.msg("e", "Nieudana próba dodania układu dla podatnika. " + e.getMessage());
        }
    }
    
    public void kopiujuklad() {
        try {
            List<Konto> kontagrupa0 = kontoDAOfk.findKontaGrupa0(wpisView);
            if (kontagrupa0 == null || kontagrupa0.isEmpty()) {
                Msg.msg("e", "Brak planu kont w bieżacym roku. Nie można kopiować układu");
            } else if (!ukladdocelowyrok.equals(wpisView.getRokWpisuSt())) {
                Msg.msg("e", "Rok docelowy jest inny od roku bieżącego. Nie można kopiować układu");
            } else {
                UkladBR ukladBR = serialclone.SerialClone.clone(ukladzrodlowy);
                ukladBR.setPodatnik(wpisView.getPodatnikWpisu());
                ukladBR.setRok(ukladdocelowyrok);
                ukladBR.setImportowany(true);
                ukladBRDAO.dodaj(ukladBR);
                implementujRZiS(ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladzrodlowy.getUklad());
                implementujBilans(ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladzrodlowy.getUklad());
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
                //pozycjaBRKontaView.kopiujprzyporzadkowaniekont("r", true);
                //pozycjaBRKontaView.kopiujprzyporzadkowaniekont("b", true);
                Msg.msg("i", "Skopiowano przyporządkowanie kont z układu wzorcowego");
            }
        } catch (EJBException ejb) {
            Msg.msg("e", "Nieudana próba skopiowania układu. Układ za dany rok już istnieje " + ejb.getMessage());
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            Msg.msg("e", "Nieudana próba skopiowania układu. " + e.getMessage());
        }
    }

    public void implementujWzorcowy() {
        try {
            UkladBR ukladBR = serialclone.SerialClone.clone(wybranyukladwzorcowy);
            ukladBR.setPodatnik(wpisView.getPodatnikWpisu());
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setImportowany(true);
            ukladBRDAO.dodaj(ukladBR);
            implementujRZiS(wybranyukladwzorcowy, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wybranyukladwzorcowy.getUklad());
            implementujBilans(wybranyukladwzorcowy, wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wybranyukladwzorcowy.getUklad());
            lista.add(ukladBR);
            init();
            Msg.msg("i", "Zaimplementowano układ wzorcowy jako nowy układ podatnika");
            pozycjaBRKontaView.init();
            pozycjaBRKontaView.importujwzorcoweprzyporzadkowanie("r");
            pozycjaBRKontaView.importujwzorcoweprzyporzadkowanie("b");
            Msg.msg("i", "Skopiowano przyporządkowanie kont z układu wzorcowego");
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            Msg.msg("e", "Nieudana próba dodania implementacji układu wzorcowego. " + e.getMessage());
        }
    }

    public void usun(UkladBR ukladBR) {
        try {
            if (ukladBR != null) {
                List<Konto> konta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                for (Konto k : konta) {
                    k.setKontopozycjaID(null);
                }
                kontoDAOfk.editList(konta);
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
                kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
                kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
                kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
                ukladBRDAO.destroy(ukladBR);
                lista.remove(ukladBR);
                pozycjaRZiSDAO.findRemoveRzisuklad(ukladBR);
                pozycjaBilansDAO.findRemoveBilansuklad(ukladBR);
                Msg.msg("i", "Usunięto wybrany układ");
            } else {
                Msg.msg("e", "Nie wybrano układu do usunięcia");
            }
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            Msg.msg("e", "Nieudana próba usuniecia układu." + e.getMessage());
        }
    }

    private void implementujRZiS(UkladBR ukladBR, String podatnik, String rok, String uklad) {
        List<PozycjaRZiS> pozycje = pozycjaRZiSDAO.findRzisuklad(ukladBR);
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaRZiS> macierzyste = skopiujlevel0(pozycje, podatnik, rok, uklad);
            int maxlevel = pozycjaRZiSDAO.findMaxLevelPodatnik(ukladBR);
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevel(pozycje, macierzyste, i, podatnik, rok, uklad);
            }
            pozycjaRZiSDAO.dodaj(pozycje);
            System.out.println("ImplementujRZiS UkladBRView");
        } else {
            Msg.msg("e", "Brak pozycji bilansu przyporządkowanych do wybranego układu");
        }
    }

    private void implementujBilans(UkladBR ukladBR, String podatnik, String rok, String uklad) {
        List<PozycjaBilans> pozycje = pozycjaBilansDAO.findBilansukladAktywa(ukladBR);
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaBilans> macierzyste = skopiujlevel0B(pozycje, podatnik, rok, uklad);
            int maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikAktywa(ukladBR);
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevelB(pozycje, macierzyste, i, podatnik, rok, uklad);
            }
            pozycje = pozycjaBilansDAO.findBilansukladPasywa(ukladBR);
            macierzyste = skopiujlevel0B(pozycje, podatnik, rok, uklad);
            maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikPasywa(ukladBR);
            for (int i = 1; i <= maxlevel; i++) {
                macierzyste = skopiujlevelB(pozycje, macierzyste, i, podatnik, rok, uklad);
            }
            pozycjaBilansDAO.dodaj(pozycje);
        } else {
            Msg.msg("e", "Brak pozycji bilansu przyporządkowanych do wybranego układu");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public UkladBR getSelected() {
        return selected;
    }

    public void setSelected(UkladBR selected) {
        this.selected = selected;
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

    public List<UkladBR> getListaWzorcowy() {
        return listaWzorcowy;
    }

    public void setListaWzorcowy(List<UkladBR> listaWzorcowy) {
        this.listaWzorcowy = listaWzorcowy;
    }

//</editor-fold>
    private List<PozycjaRZiS> skopiujlevel0(List<PozycjaRZiS> pozycje, String podatnik, String rok, String uklad) {
        List<PozycjaRZiS> macierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel() == 0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnik);
                r.setRok(rok);
                r.setUklad(uklad);
                r.setPrzyporzadkowanekonta(null);
                r.setPrzyporzadkowanestronywiersza(null);
//                try {
//                    pozycjaRZiSDAO.dodaj(r);
//                } catch (Exception e) {
//                    System.out.println("Blad " + e.getStackTrace()[0].toString());
//
//                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaRZiS> skopiujlevel(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i, String podatnik, String rok, String uklad) {
        List<PozycjaRZiS> nowemacierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel() == i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(podatnik);
                    r.setRok(rok);
                    r.setUklad(uklad);
                    r.setPrzyporzadkowanekonta(null);
                    r.setPrzyporzadkowanestronywiersza(null);
                    PozycjaRZiS macierzyste = wyszukajmacierzyste(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    System.out.println("Blad " + e.getStackTrace()[0].toString());

                }
            }
        }
        return nowemacierzyste;
    }

    private PozycjaRZiS wyszukajmacierzyste(PozycjaRZiS macierzyste, List<PozycjaRZiS> macierzystelista) {
        PozycjaRZiS mac = macierzyste.getMacierzysta();
        for (PozycjaRZiS p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }

    private List<PozycjaBilans> skopiujlevel0B(List<PozycjaBilans> pozycje, String podatnik, String rok, String uklad) {
        List<PozycjaBilans> macierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel() == 0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPodatnik(podatnik);
                r.setRok(rok);
                r.setUklad(uklad);
//                try {
//                    pozycjaRZiSDAO.dodaj(r);
//                } catch (Exception e) {
//                    System.out.println("Blad " + e.getStackTrace()[0].toString());
//
//                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaBilans> skopiujlevelB(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i, String podatnik, String rok, String uklad) {
        List<PozycjaBilans> nowemacierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel() == i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(podatnik);
                    r.setRok(rok);
                    r.setUklad(uklad);
                    PozycjaBilans macierzyste = wyszukajmacierzysteB(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    System.out.println("Blad " + e.getStackTrace()[0].toString());

                }
            }
        }
        return nowemacierzyste;
    }

    private PozycjaBilans wyszukajmacierzysteB(PozycjaBilans macierzyste, List<PozycjaBilans> macierzystelista) {
        PozycjaBilans mac = macierzyste.getMacierzysta();
        for (PozycjaBilans p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }

    public void kopiujukladwzorcowy() {
        if (ukladzrodlowy != null) {
            try {
                UkladBR ukladdocelowy = new UkladBR(ukladzrodlowy);
                ukladdocelowy.setRok(ukladdocelowyrok);
                ukladBRDAO.dodaj(ukladdocelowy);
                lista.add(ukladdocelowy);
                implementujRZiS(ukladzrodlowy, ukladdocelowyrok);
                implementujBilans(ukladzrodlowy, ukladdocelowyrok);
                Msg.msg("Skopiowano ukłąd wzorcowy z pozycjami");
            } catch (Exception e) {
                System.out.println("Blad " + e.getStackTrace()[0].toString());
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
        System.out.println("Kopiuje");
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
        System.out.println("Kopiuje");
    }
     
      private List<PozycjaRZiS> skopiujlevel0RZiS(List<PozycjaRZiS> pozycje, String rok) {
        List<PozycjaRZiS> macierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPodatnik(wpisView.getPodatnikWpisu());
                r.setRok(rok);
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
      
      private List<PozycjaBilans> skopiujlevel0Bilans(List<PozycjaBilans> pozycje, String rok) {
        List<PozycjaBilans> macierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPodatnik(wpisView.getPodatnikWpisu());
                r.setRok(rok);
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaRZiS> skopiujlevelRZiS(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i, String rok) {
         List<PozycjaRZiS> nowemacierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(wpisView.getPodatnikWpisu());
                    r.setRok(rok);
                    PozycjaRZiS macierzyste = wyszukajmacierzysteRZiS(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    pozycjaRZiSDAO.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {  E.e(e);
                    
                }
            }
        }
        return nowemacierzyste;
    }
    
    private List<PozycjaBilans> skopiujlevelBilans(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i, String rok) {
         List<PozycjaBilans> nowemacierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(wpisView.getPodatnikWpisu());
                    r.setRok(rok);
                    PozycjaBilans macierzyste = wyszukajmacierzysteBilans(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    pozycjaRZiSDAO.dodaj(r);
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
                        System.out.println(""+p.getLp()+" mac"+mac.getLp());
                    }
                } catch (Exception r) {
                    System.out.println(" "+p);
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
                        System.out.println(""+p.getLp()+" mac"+mac.getLp());
                    }
                } catch (Exception r) {
                    System.out.println(" "+p);
                }
            }
        }
        pozycjaRZiSDAO.editList(wynikowe);
        Msg.msg("Wynikowe");
        System.out.println("koniec");
    }
    
    
}
