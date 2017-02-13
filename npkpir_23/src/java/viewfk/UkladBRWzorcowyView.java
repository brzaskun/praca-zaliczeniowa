/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.UkladBRcomparator;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
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
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class UkladBRWzorcowyView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<UkladBR> lista;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    @Inject
    private UkladBR selected;
    private String nazwanowegoukladu;
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
    
    

    public UkladBRWzorcowyView() {
         E.m(this);
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
       try {
            lista =  ukladBRDAO.findPodatnik("Wzorcowy");
            Collections.sort(lista, new UkladBRcomparator());
        } catch (Exception e) {  E.e(e);} 
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getUklad());
    }
    
    public void dodaj() {
        try {
            UkladBR ukladBR = new UkladBR();
            ukladBR.setPodatnik("Wzorcowy");
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setUklad(nazwanowegoukladu);
            ukladBRDAO.dodaj(ukladBR);
            lista.add(ukladBR);
            nazwanowegoukladu = "";
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nieudana próba dodania układu. "+e.getMessage());
        }
    }
    
  
    
    private UkladBR pobierzzlistyWzorcowy() {
        for (UkladBR p : lista) {
            if (p.getUklad().equals(nazwanowegoukladu)) {
                return p;
            }
        }
        return null;
    }


    public void usun(UkladBR ukladBR) {
        try {
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
            kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
            ukladBRDAO.destroy(ukladBR);
            lista.remove(ukladBR);
            pozycjaRZiSDAO.findRemoveRzisuklad(ukladBR);
            pozycjaBilansDAO.findRemoveBilansuklad(ukladBR);
            Msg.msg("i", "Usunięto wybrany układ");
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Nieudana próba usuniecia układu."+e.getMessage());
        }
    }
    
    public void kopiujukladwzorcowy() {
        if (ukladzrodlowy != null) {
            try {
                UkladBR ukladdocelowy = new UkladBR(ukladzrodlowy);
                ukladdocelowy.setUklad(ukladdocelowynazwa);
                ukladdocelowy.setRok(ukladdocelowyrok);
                ukladBRDAO.dodaj(ukladdocelowy);
                lista.add(ukladdocelowy);
                implementujRZiS(ukladzrodlowy,ukladdocelowy);
                implementujBilans(ukladzrodlowy,ukladdocelowy);
                Msg.msg("Skopiowano ukłąd wzorcowy z pozycjami");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie skopiowano nowego układu.");
            }
        }
    }
    
    
    
        
     private void implementujRZiS(UkladBR ukladzrodlowy, UkladBR ukladdocelowy) {
        List<PozycjaRZiS> pozycje = pozycjaRZiSDAO.findRzisuklad(ukladzrodlowy);
        List<PozycjaRZiS> macierzyste = skopiujlevel0RZiS(pozycje, ukladdocelowy);
        Integer maxlevel = pozycjaRZiSDAO.findMaxLevelPodatnik(ukladzrodlowy);
        for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelRZiS(pozycje, macierzyste,i, ukladdocelowy);
        }
        System.out.println("Kopiuje RZiS");
    }
     
      private void implementujBilans(UkladBR ukladzrodlowy, UkladBR ukladdocelowy) {
        List<PozycjaBilans> pozycje = pozycjaBilansDAO.findBilansukladAktywa(ukladzrodlowy);
        List<PozycjaBilans> macierzyste = skopiujlevel0Bilans(pozycje, ukladdocelowy);
        Integer maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikAktywa(ukladzrodlowy);
        for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelBilans(pozycje, macierzyste,i, ukladdocelowy);
        }
        pozycje = pozycjaBilansDAO.findBilansukladPasywa(ukladzrodlowy);
        macierzyste = skopiujlevel0Bilans(pozycje, ukladdocelowy);
        maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikPasywa(ukladzrodlowy);
        for(int i = 1; i <= maxlevel;i++) {
                macierzyste = skopiujlevelBilans(pozycje, macierzyste,i, ukladdocelowy);
        }
        System.out.println("Kopiuje Bilans");
    }
     
      private List<PozycjaRZiS> skopiujlevel0RZiS(List<PozycjaRZiS> pozycje, UkladBR ukladdocelowy) {
        List<PozycjaRZiS> macierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPrzyporzadkowanekonta(null);
                r.setPrzyporzadkowanestronywiersza(null);
                r.setPodatnik("Wzorcowy");
                r.setUklad(ukladdocelowy.getUklad());
                r.setRok(ukladdocelowy.getRok());
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
      
      private List<PozycjaBilans> skopiujlevel0Bilans(List<PozycjaBilans> pozycje, UkladBR ukladdocelowy) {
        List<PozycjaBilans> macierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPrzyporzadkowanekonta(null);
                r.setPrzyporzadkowanestronywiersza(null);
                r.setPodatnik("Wzorcowy");
                r.setUklad(ukladdocelowy.getUklad());
                r.setRok(ukladdocelowy.getRok());
                try {
                    pozycjaBilansDAO.dodaj(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaRZiS> skopiujlevelRZiS(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i, UkladBR ukladdocelowy) {
         List<PozycjaRZiS> nowemacierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPrzyporzadkowanekonta(null);
                    r.setPrzyporzadkowanestronywiersza(null);
                    r.setPodatnik("Wzorcowy");
                    r.setUklad(ukladdocelowy.getUklad());
                    r.setRok(ukladdocelowy.getRok());
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
    
    private List<PozycjaBilans> skopiujlevelBilans(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i, UkladBR ukladdocelowy) {
         List<PozycjaBilans> nowemacierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPrzyporzadkowanekonta(null);
                    r.setPrzyporzadkowanestronywiersza(null);
                    r.setPodatnik("Wzorcowy");
                    r.setUklad(ukladdocelowy.getUklad());
                    r.setRok(ukladdocelowy.getRok());
                    r.setLp(null);
                    PozycjaBilans macierzyste = wyszukajmacierzysteBilans(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
                    pozycjaBilansDAO.dodaj(r);
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

//    private void implementujBilans(UkladBR ukladBR) {
//        List<PozycjaBilans> pozycje = pozycjaBilansDAO.findBilansukladAktywa(ukladBR);
//        List<PozycjaBilans> macierzyste = skopiujlevel0B(pozycje);
//        int maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikAktywa(ukladBR);
//        for(int i = 1; i <= maxlevel;i++) {
//                macierzyste = skopiujlevelB(pozycje, macierzyste,i);
//        }
//        pozycje = pozycjaBilansDAO.findBilansukladPasywa(ukladBR);
//        macierzyste = skopiujlevel0B(pozycje);
//        maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikPasywa(ukladBR);
//        for(int i = 1; i <= maxlevel;i++) {
//                macierzyste = skopiujlevelB(pozycje, macierzyste,i);
//        }
//    }
    
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
    public String getNazwanowegoukladu() {
        return nazwanowegoukladu;
    }
    
    public void setNazwanowegoukladu(String nazwanowegoukladu) {
        this.nazwanowegoukladu = nazwanowegoukladu;
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
    
   
    
//</editor-fold>

//    private List<PozycjaRZiS> skopiujlevel0(List<PozycjaRZiS> pozycje) {
//        List<PozycjaRZiS> macierzyste = new ArrayList<>();
//        for (PozycjaRZiS p : pozycje) {
//            if (p.getLevel()==0) {
//                PozycjaRZiS r = serialclone.SerialClone.clone(p);
//                r.setPodatnik(wpisView.getPodatnikWpisu());
//                r.setRok(wpisView.getRokWpisuSt());
//                try {
//                    pozycjaRZiSDAO.dodaj(r);
//                } catch (Exception e) {  E.e(e);
//                    
//                }
//                macierzyste.add(r);
//            }
//        }
//        return macierzyste;
//    }
//
//    private List<PozycjaRZiS> skopiujlevel(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i) {
//         List<PozycjaRZiS> nowemacierzyste = new ArrayList<>();
//        for (PozycjaRZiS p : pozycje) {
//            if (p.getLevel()==i) {
//                try {
//                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
//                    r.setPodatnik(wpisView.getPodatnikWpisu());
//                    r.setRok(wpisView.getRokWpisuSt());
//                    r.setLp(null);
//                    PozycjaRZiS macierzyste = wyszukajmacierzyste(p, macierzystelista);
//                    r.setMacierzysty(macierzyste.getLp());
//                    pozycjaRZiSDAO.dodaj(r);
//                    nowemacierzyste.add(r);
//                } catch (Exception e) {  E.e(e);
//                    
//                }
//            }
//        }
//        return nowemacierzyste;
//    }
//
//     private PozycjaRZiS wyszukajmacierzyste(PozycjaRZiS macierzyste, List<PozycjaRZiS> macierzystelista) {
//        PozycjaRZiS mac = pozycjaRZiSDAO.findRzisLP(macierzyste.getMacierzysty());
//        for (PozycjaRZiS p : macierzystelista) {
//            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
//                return p;
//            }
//        }
//        return null;
//    }
//   
//    private List<PozycjaBilans> skopiujlevel0B(List<PozycjaBilans> pozycje) {
//        List<PozycjaBilans> macierzyste = new ArrayList<>();
//        for (PozycjaBilans p : pozycje) {
//            if (p.getLevel()==0) {
//                PozycjaBilans r = serialclone.SerialClone.clone(p);
//                r.setPodatnik(wpisView.getPodatnikWpisu());
//                r.setRok(wpisView.getRokWpisuSt());
//                try {
//                    pozycjaRZiSDAO.dodaj(r);
//                } catch (Exception e) {  E.e(e);
//                    
//                }
//                macierzyste.add(r);
//            }
//        }
//        return macierzyste;
//    }
//
//    private List<PozycjaBilans> skopiujlevelB(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i) {
//         List<PozycjaBilans> nowemacierzyste = new ArrayList<>();
//        for (PozycjaBilans p : pozycje) {
//            if (p.getLevel()==i) {
//                try {
//                    PozycjaBilans r = serialclone.SerialClone.clone(p);
//                    r.setPodatnik(wpisView.getPodatnikWpisu());
//                    r.setRok(wpisView.getRokWpisuSt());
//                    r.setLp(null);
//                    PozycjaBilans macierzyste = wyszukajmacierzysteB(p, macierzystelista);
//                    r.setMacierzysty(macierzyste.getLp());
//                    pozycjaBilansDAO.dodaj(r);
//                    nowemacierzyste.add(r);
//                } catch (Exception e) {  E.e(e);
//                    
//                }
//            }
//        }
//        return nowemacierzyste;
//    }
//    
//    
//    private PozycjaBilans wyszukajmacierzysteB(PozycjaBilans macierzyste, List<PozycjaBilans> macierzystelista) {
//        PozycjaBilans mac = pozycjaBilansDAO.findBilansLP(macierzyste.getMacierzysty());
//        for (PozycjaBilans p : macierzystelista) {
//            if (p.getNazwa().equals(mac.getNazwa()) && p.getPozycjaString().equals(mac.getPozycjaString())) {
//                return p;
//            }
//        }
//        return null;
//    }
//   
//   
//    
    
}
