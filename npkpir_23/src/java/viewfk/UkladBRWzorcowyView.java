/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PozycjaRZiSFKBean;
import comparator.UkladBRcomparator;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.UkladBRDAO;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class UkladBRWzorcowyView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<UkladBR> lista;
    @Inject
    private WpisView wpisView;
    @Inject
    private UkladBRView ukladBRView;
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
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBR ukladzrodlowy;
    @Inject
    private UkladBR ukladdocelowy;
    private String ukladdocelowynazwa;
    private String ukladdocelowyrok;
    private boolean kopiujbilans;
    private boolean kopiujrzis;
    
    

    public UkladBRWzorcowyView() {

    }
    
    @PostConstruct
    private void init() { //E.m(this);
       try {
            lista =  ukladBRDAO.findPodatnik(wpisView.getPodatnikwzorcowy());
            Collections.sort(lista, new UkladBRcomparator());
        } catch (Exception e) {  E.e(e);} 
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getUklad());
    }
    
    public void dodaj() {
        try {
            UkladBR ukladBR = new UkladBR();
            ukladBR.setPodatnik(wpisView.getPodatnikwzorcowy());
            ukladBR.setZwykly0wzrocowy1(true);
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setUklad(nazwanowegoukladu);
            ukladBRDAO.create(ukladBR);
            lista.add(ukladBR);
            ukladBRView.init();
            init();
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


    public void kopiujnazweukladu() {
        ukladdocelowynazwa = ukladzrodlowy.getUklad();
    }
    
    public void usun(UkladBR ukladBR) {
        try {
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "bilansowe");
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladBR, "wynikowe");
            ukladBRDAO.remove(ukladBR);
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
                ukladdocelowy.setZwykly0wzrocowy1(true);
                ukladBRDAO.create(ukladdocelowy);
                lista.add(ukladdocelowy);
                Collections.sort(lista, new UkladBRcomparator());
                implementujRZiS(ukladzrodlowy,ukladdocelowy);
                implementujBilans(ukladzrodlowy,ukladdocelowy);
                Msg.msg("Skopiowano ukłąd wzorcowy z pozycjami");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie skopiowano nowego układu.");
            }
        }
    }
    
    public void kopiujukladwzorcowywybiorczo() {
        if (ukladzrodlowy != null && ukladdocelowy != null) {
            try {
                if (kopiujrzis == false && kopiujbilans == false) {
                    Msg.msg("e", "Nie wybrano części układu do skopiowanai");
                } else {
                    if (kopiujrzis) {
                        implementujRZiS(ukladzrodlowy,ukladdocelowy);
                    }
                    if (kopiujbilans) {
                        implementujBilans(ukladzrodlowy,ukladdocelowy);
                    }
                    Msg.msg("Skopiowano ukłąd wzorcowy z pozycjami");
                }
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
        PozycjaRZiSFKBean.skopiujPozycje("r", ukladdocelowy, ukladzrodlowy, wpisView.getPodatnikwzorcowy(), kontoDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
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
        PozycjaRZiSFKBean.skopiujPozycje("b", ukladdocelowy, ukladzrodlowy, wpisView.getPodatnikwzorcowy(), kontoDAO, kontopozycjaZapisDAO, wpisView, pozycjaBilansDAO, pozycjaRZiSDAO);
    }
     
      private List<PozycjaRZiS> skopiujlevel0RZiS(List<PozycjaRZiS> pozycje, UkladBR ukladdocelowy) {
        List<PozycjaRZiS> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPrzyporzadkowanekonta(null);
                r.setPrzyporzadkowanestronywiersza(null);
                r.setPodatnik(ukladdocelowy.getPodatnik().getNazwapelna());
                r.setUklad(ukladdocelowy.getUklad());
                r.setRok(ukladdocelowy.getRok());
                try {
                    pozycjaRZiSDAO.create(r);
                } catch (Exception e) {  E.e(e);
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }
      
      private List<PozycjaBilans> skopiujlevel0Bilans(List<PozycjaBilans> pozycje, UkladBR ukladdocelowy) {
        List<PozycjaBilans> macierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPrzyporzadkowanekonta(null);
                r.setPrzyporzadkowanestronywiersza(null);
                r.setPodatnik(ukladdocelowy.getPodatnik().getNazwapelna());
                r.setUklad(ukladdocelowy.getUklad());
                r.setRok(ukladdocelowy.getRok());
                try {
                    pozycjaBilansDAO.create(r);
                } catch (Exception e) {  
                    E.e(e);
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaRZiS> skopiujlevelRZiS(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i, UkladBR ukladdocelowy) {
         List<PozycjaRZiS> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPrzyporzadkowanekonta(null);
                    r.setPrzyporzadkowanestronywiersza(null);
                    r.setPodatnik(ukladdocelowy.getPodatnik().getNazwapelna());
                    r.setUklad(ukladdocelowy.getUklad());
                    r.setRok(ukladdocelowy.getRok());
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
    
    private List<PozycjaBilans> skopiujlevelBilans(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i, UkladBR ukladdocelowy) {
         List<PozycjaBilans> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPrzyporzadkowanekonta(null);
                    r.setPrzyporzadkowanestronywiersza(null);
                    r.setPodatnik(ukladdocelowy.getPodatnik().getNazwapelna());
                    r.setUklad(ukladdocelowy.getUklad());
                    r.setRok(ukladdocelowy.getRok());
                    PozycjaBilans macierzyste = wyszukajmacierzysteBilans(p, macierzystelista);
                    r.setMacierzysta(macierzyste);
                    pozycjaBilansDAO.create(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {  
                    E.e(e);
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

    public boolean isKopiujbilans() {
        return kopiujbilans;
    }

    public UkladBR getUkladdocelowy() {
        return ukladdocelowy;
    }

    public void setUkladdocelowy(UkladBR ukladdocelowy) {
        this.ukladdocelowy = ukladdocelowy;
    }

    public void setKopiujbilans(boolean kopiujbilans) {
        this.kopiujbilans = kopiujbilans;
    }

    public boolean isKopiujrzis() {
        return kopiujrzis;
    }

    public void setKopiujrzis(boolean kopiujrzis) {
        this.kopiujrzis = kopiujrzis;
    }

    public UkladBRView getUkladBRView() {
        return ukladBRView;
    }

    public void setUkladBRView(UkladBRView ukladBRView) {
        this.ukladBRView = ukladBRView;
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
//        List<PozycjaRZiS> macierzyste = Collections.synchronizedList(new ArrayList<>());
//        for (PozycjaRZiS p : pozycje) {
//            if (p.getLevel()==0) {
//                PozycjaRZiS r = serialclone.SerialClone.clone(p);
//                r.setPodatnik(wpisView.getPodatnikWpisu());
//                r.setRok(wpisView.getRokWpisuSt());
//                try {
//                    pozycjaRZiSDAO.create(r);
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
//         List<PozycjaRZiS> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
//        for (PozycjaRZiS p : pozycje) {
//            if (p.getLevel()==i) {
//                try {
//                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
//                    r.setPodatnik(wpisView.getPodatnikWpisu());
//                    r.setRok(wpisView.getRokWpisuSt());
//                    r.setLp(null);
//                    PozycjaRZiS macierzyste = wyszukajmacierzyste(p, macierzystelista);
//                    r.setMacierzysty(macierzyste.getLp());
//                    pozycjaRZiSDAO.create(r);
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
//        List<PozycjaBilans> macierzyste = Collections.synchronizedList(new ArrayList<>());
//        for (PozycjaBilans p : pozycje) {
//            if (p.getLevel()==0) {
//                PozycjaBilans r = serialclone.SerialClone.clone(p);
//                r.setPodatnik(wpisView.getPodatnikWpisu());
//                r.setRok(wpisView.getRokWpisuSt());
//                try {
//                    pozycjaRZiSDAO.create(r);
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
//         List<PozycjaBilans> nowemacierzyste = Collections.synchronizedList(new ArrayList<>());
//        for (PozycjaBilans p : pozycje) {
//            if (p.getLevel()==i) {
//                try {
//                    PozycjaBilans r = serialclone.SerialClone.clone(p);
//                    r.setPodatnik(wpisView.getPodatnikWpisu());
//                    r.setRok(wpisView.getRokWpisuSt());
//                    r.setLp(null);
//                    PozycjaBilans macierzyste = wyszukajmacierzysteB(p, macierzystelista);
//                    r.setMacierzysty(macierzyste.getLp());
//                    pozycjaBilansDAO.create(r);
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
