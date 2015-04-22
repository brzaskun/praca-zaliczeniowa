/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
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
public class UkladBRView implements Serializable{
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
  

    

    public UkladBRView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
       try {
            lista = ukladBRDAO.findPodatnik(wpisView.getPodatnikWpisu());
            listaWzorcowy = ukladBRDAO.findPodatnik("Wzorcowy");
        } catch (Exception e) {} 
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getUklad());
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
            Msg.msg("e", "Nieudana próba dodania układu. "+e.getMessage());
        }
    }
    
    public void implementuj() {
        try {
            UkladBR ukladBR = serialclone.SerialClone.clone(wybranyukladwzorcowy);
            ukladBR.setPodatnik(wpisView.getPodatnikWpisu());
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setImportowany(true);
            ukladBRDAO.dodaj(ukladBR);
            implementujRZiS(wybranyukladwzorcowy);
            implementujBilans(wybranyukladwzorcowy);
            lista.add(ukladBR);
            wybranyukladwzorcowy = null;
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania układu. "+e.getMessage());
        }
    }
    
 

    public void usun(UkladBR ukladBR) {
        try {
            ukladBRDAO.destroy(ukladBR);
            lista.remove(ukladBR);
            pozycjaRZiSDAO.findRemoveRzisuklad(ukladBR);
            pozycjaBilansDAO.findRemoveBilansuklad(ukladBR);
            Msg.msg("i", "Usunięto wybrany układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba usuniecia układu."+e.getMessage());
        }
    }
    
     private void implementujRZiS(UkladBR ukladBR) {
        List<PozycjaRZiS> pozycje = pozycjaRZiSDAO.findRzisuklad(ukladBR);
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaRZiS> macierzyste = skopiujlevel0(pozycje);
            int maxlevel = pozycjaRZiSDAO.findMaxLevelPodatnik(ukladBR);
            for(int i = 1; i <= maxlevel;i++) {
                    macierzyste = skopiujlevel(pozycje, macierzyste,i);
            }
            System.out.println("Kopiuje");
        } else {
            Msg.msg("e", "Brak pozycji bilansu przyporządkowanych do wybranego układu");
        }
    }

    private void implementujBilans(UkladBR ukladBR) {
        List<PozycjaBilans> pozycje = pozycjaBilansDAO.findBilansukladAktywa(ukladBR);
        if (pozycje != null && pozycje.size() > 0) {
            List<PozycjaBilans> macierzyste = skopiujlevel0B(pozycje);
            int maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikAktywa(ukladBR);
            for(int i = 1; i <= maxlevel;i++) {
                    macierzyste = skopiujlevelB(pozycje, macierzyste,i);
            }
            pozycje = pozycjaBilansDAO.findBilansukladPasywa(ukladBR);
            macierzyste = skopiujlevel0B(pozycje);
            maxlevel = pozycjaBilansDAO.findMaxLevelPodatnikPasywa(ukladBR);
            for(int i = 1; i <= maxlevel;i++) {
                    macierzyste = skopiujlevelB(pozycje, macierzyste,i);
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

    private List<PozycjaRZiS> skopiujlevel0(List<PozycjaRZiS> pozycje) {
        List<PozycjaRZiS> macierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaRZiS r = serialclone.SerialClone.clone(p);
                r.setPodatnik(wpisView.getPodatnikWpisu());
                r.setRok(wpisView.getRokWpisuSt());
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaRZiS> skopiujlevel(List<PozycjaRZiS> pozycje, List<PozycjaRZiS> macierzystelista, int i) {
         List<PozycjaRZiS> nowemacierzyste = new ArrayList<>();
        for (PozycjaRZiS p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaRZiS r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(wpisView.getPodatnikWpisu());
                    r.setRok(wpisView.getRokWpisuSt());
                    r.setLp(null);
                    PozycjaRZiS macierzyste = wyszukajmacierzyste(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
                    pozycjaRZiSDAO.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    
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
   
    private List<PozycjaBilans> skopiujlevel0B(List<PozycjaBilans> pozycje) {
        List<PozycjaBilans> macierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==0) {
                PozycjaBilans r = serialclone.SerialClone.clone(p);
                r.setPodatnik(wpisView.getPodatnikWpisu());
                r.setRok(wpisView.getRokWpisuSt());
                try {
                    pozycjaRZiSDAO.dodaj(r);
                } catch (Exception e) {
                    
                }
                macierzyste.add(r);
            }
        }
        return macierzyste;
    }

    private List<PozycjaBilans> skopiujlevelB(List<PozycjaBilans> pozycje, List<PozycjaBilans> macierzystelista, int i) {
         List<PozycjaBilans> nowemacierzyste = new ArrayList<>();
        for (PozycjaBilans p : pozycje) {
            if (p.getLevel()==i) {
                try {
                    PozycjaBilans r = serialclone.SerialClone.clone(p);
                    r.setPodatnik(wpisView.getPodatnikWpisu());
                    r.setRok(wpisView.getRokWpisuSt());
                    r.setLp(null);
                    PozycjaBilans macierzyste = wyszukajmacierzysteB(p, macierzystelista);
                    r.setMacierzysty(macierzyste.getLp());
                    pozycjaBilansDAO.dodaj(r);
                    nowemacierzyste.add(r);
                } catch (Exception e) {
                    
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

    
   
    
    
}
