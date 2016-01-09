/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import entityfk.KontopozycjaBiezaca;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
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
    private List<UkladBR> listaWzorcowy;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

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
    private UkladBR ukladzrodlowy;
    private String ukladdocelowynazwa;
    private String ukladdocelowyrok;

    public UkladBRView() {
        lista = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            lista = ukladBRDAO.findPodatnik(wpisView.getPodatnikWpisu());
            listaWzorcowy = ukladBRDAO.findPodatnik("Wzorcowy");
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
        }
    }

    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad " + selected.getUklad());
    }

    public void dodaj() {
        for (UkladBR p : listaWzorcowy) {
            if (p.getUklad().equals(wybranyukladwzorcowy)) {
                Msg.msg("e", "Nazwa nowego układu jest już wykorzystana we wzorcach. Nie można dodać układu.");
                return;
            }
        }
        try {
            UkladBR ukladBR = new UkladBR();
            ukladBR.setPodatnik(wpisView.getPodatnikWpisu());
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setUklad(wybranyukladwzorcowy.getUklad());
            ukladBRDAO.dodaj(ukladBR);
            lista.add(ukladBR);
            wybranyukladwzorcowy = null;
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            Msg.msg("e", "Nieudana próba dodania układu. " + e.getMessage());
        }
    }
    
    public void kopiujuklad() {
        try {
            UkladBR ukladBR = serialclone.SerialClone.clone(ukladzrodlowy);
            ukladBR.setUklad(ukladdocelowynazwa);
            ukladBR.setPodatnik(wpisView.getPodatnikWpisu());
            ukladBR.setRok(ukladdocelowyrok);
            ukladBR.setImportowany(true);
            ukladBRDAO.dodaj(ukladBR);
            implementujRZiS(ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladdocelowynazwa);
            implementujBilans(ukladzrodlowy, wpisView.getPodatnikWpisu(), ukladdocelowyrok, ukladdocelowynazwa);
            skopiujPozycje("r", ukladBR, ukladzrodlowy);
            zaksiegujzmianypozycji("r", ukladBR);
            skopiujPozycje("b", ukladBR, ukladzrodlowy);
            zaksiegujzmianypozycji("b", ukladBR);
            lista.add(ukladBR);
            wybranyukladwzorcowy = null;
            Msg.msg("i", "Skopiowano układ podatnika");
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
            wybranyukladwzorcowy = null;
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {
            System.out.println("Blad " + e.getStackTrace()[0].toString());
            Msg.msg("e", "Nieudana próba dodania układu. " + e.getMessage());
        }
    }

    public void usun(UkladBR ukladBR) {
        try {
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
            ukladBRDAO.destroy(ukladBR);
            lista.remove(ukladBR);
            pozycjaRZiSDAO.findRemoveRzisuklad(ukladBR);
            pozycjaBilansDAO.findRemoveBilansuklad(ukladBR);
            Msg.msg("i", "Usunięto wybrany układ");
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
            System.out.println("Kopiuje");
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

    public UkladBR getUkladzrodlowy() {
        return ukladzrodlowy;
    }

    public void setUkladzrodlowy(UkladBR ukladzrodlowy) {
        this.ukladzrodlowy = ukladzrodlowy;
    }

    public String getUkladdocelowynazwa() {
        return ukladdocelowynazwa;
    }

    public void setUkladdocelowynazwa(String ukladdocelowynazwa) {
        this.ukladdocelowynazwa = ukladdocelowynazwa;
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
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {
                    System.out.println("Blad " + e.getStackTrace()[0].toString());

                }
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
                    r.setLp(null);
                    PozycjaRZiS macierzyste = wyszukajmacierzyste(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
                    pozycjaRZiSDAO.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    System.out.println("Blad " + e.getStackTrace()[0].toString());

                }
            }
        }
        return nowemacierzyste;
    }

    private PozycjaRZiS wyszukajmacierzyste(PozycjaRZiS macierzyste, List<PozycjaRZiS> macierzystelista) {
        PozycjaRZiS mac = pozycjaRZiSDAO.findRzisLP(macierzyste.getMacierzysty());
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
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {
                    System.out.println("Blad " + e.getStackTrace()[0].toString());

                }
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
                    r.setLp(null);
                    PozycjaBilans macierzyste = wyszukajmacierzysteB(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
                    pozycjaBilansDAO.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    System.out.println("Blad " + e.getStackTrace()[0].toString());

                }
            }
        }
        return nowemacierzyste;
    }

    private PozycjaBilans wyszukajmacierzysteB(PozycjaBilans macierzyste, List<PozycjaBilans> macierzystelista) {
        PozycjaBilans mac = pozycjaBilansDAO.findBilansLP(macierzyste.getMacierzysty());
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
                ukladdocelowy.setUklad(ukladdocelowynazwa);
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
                    r.setLp(null);
                    PozycjaRZiS macierzyste = wyszukajmacierzysteRZiS(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
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
                    r.setLp(null);
                    PozycjaBilans macierzyste = wyszukajmacierzysteBilans(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
                    pozycjaRZiSDAO.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {  E.e(e);
                    
                }
            }
        }
        return nowemacierzyste;
    }
    
    private PozycjaRZiS wyszukajmacierzysteRZiS(PozycjaRZiS macierzyste, List<PozycjaRZiS> macierzystelista) {
        PozycjaRZiS mac = pozycjaRZiSDAO.findRzisLP(macierzyste.getMacierzysty());
        for (PozycjaRZiS p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }
    
    private PozycjaBilans wyszukajmacierzysteBilans(PozycjaBilans macierzyste, List<PozycjaBilans> macierzystelista) {
        PozycjaBilans mac = pozycjaBilansDAO.findBilansLP(macierzyste.getMacierzysty());
        for (PozycjaBilans p : macierzystelista) {
            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
                return p;
            }
        }
        return null;
    }

}
