/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Kliencifk;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PlanKontFKBean {
   
     public static int dodajsyntetyczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("syntetyczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(2014);
         nowekonto.setMacierzyste("0");
         nowekonto.setLevel(0);
         nowekonto.setMacierzysty(0);
         nowekonto.setMapotomkow(false);
         nowekonto.setPelnynumer(nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, wpisView);
     }
     
     public static int dodajanalityczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(2014);
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(oblicznumerkonta(macierzyste, kontoDAOfk, wpisView.getPodatnikWpisu()));
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajanalityczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String numerkonta,WpisView wpisView, Integer rok) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(rok);
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(numerkonta);
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, wpisView);
    }
     
    public static int dodajslownik(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setNazwapelna("Słownik kontrahenci");
         nowekonto.setNazwaskrocona("Kontrahenci");
         nowekonto.setBlokada(true);
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setSlownikowe(true);
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta("0");
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajelementyslownika(Konto kontomacierzyste, KontoDAOfk kontoDAO, KliencifkDAO kliencifkDAO, WpisView wpisView, Integer rok) {
        List<Kliencifk> listaprzyporzadkowanychklientow = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        if (listaprzyporzadkowanychklientow != null) {
            for (Kliencifk p : listaprzyporzadkowanychklientow) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getNazwa());
                nowekonto.setNazwaskrocona(p.getNip());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView, rok);
                if (wynikdodania == 1) {
                    return 1;
                }
            }
            return 0;
        } else {
            return 0;
        }
    }
    public static int aktualizujslownik(Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView, Integer rok) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu());
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(kliencifk.getNazwa());
            nowekonto.setNazwaskrocona(kliencifk.getNip());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(nowekonto, p, kontoDAO, kliencifk.getNrkonta(), wpisView, rok);
            if (wynikdodania == 1) {
                return 1;
            }
        }
        return 0;
    }
    
    public static void porzadkujslowniki(Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView, Integer rok) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu());
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(kliencifk.getNazwa());
            nowekonto.setNazwaskrocona(kliencifk.getNip());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            PlanKontFKBean.dodajanalityczne(nowekonto, p, kontoDAO, kliencifk.getNrkonta(), wpisView, rok);
        }
    }
            
    public static int usunelementyslownika(String kontomacierzyste, KontoDAOfk kontoDAO, String podatnik) {
        List<Konto> listakont = kontoDAO.findKontaPotomnePodatnik(podatnik, kontomacierzyste);
        if (listakont != null) {
            for (Konto p : listakont) {
                kontoDAO.destroy(p);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static void zablokujKontoMacierzysteSlownik(Konto macierzyste, KontoDAOfk kontoDAOfk) {
        macierzyste.setBlokada(true);
        macierzyste.setMapotomkow(true);
        macierzyste.setMaslownik(true);
        kontoDAOfk.edit(macierzyste);
    }
    
    public static void zablokujKontoMacierzysteNieSlownik(Konto macierzyste, KontoDAOfk kontoDAOfk) {
        macierzyste.setMapotomkow(true);
        kontoDAOfk.edit(macierzyste);
    }
    
    public static void odswiezroot(TreeNodeExtended<Konto> r, KontoDAOfk kontoDAO, WpisView wpisView) {
        if (czywzorcowe(r.getChildren().get(0))) {
            r.reset();
            r.createTreeNodesForElement(kontoDAO.findWszystkieKontaWzorcowy());
        } else {
            r.reset();
            List<Konto> listakontpodatnika = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            if (listakontpodatnika != null && !listakontpodatnika.isEmpty()) {
                r.createTreeNodesForElement(listakontpodatnika);
            }
        }
        r.expandAll();
    }

    public static boolean czywzorcowe(TreeNode nodeR) {
        Konto konto = (Konto) nodeR.getData();
        return konto.getPodatnik().equals("Wzorcowy");      
    }
    
    private static int obliczlevel(String macierzyste) {
         int i = 1;
         i += StringUtils.countMatches(macierzyste, "-");
         return i;
    }
     
    private static int znajdzduplikat(Konto nowe, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        if (wykazkont.contains(nowe)) {
            return 1;
        } else {
            return 0;
        }
    }

    private static int zachowajkonto(Konto nowekonto, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        if (0 == znajdzduplikat(nowekonto, kontoDAOfk, wpisView)) {
            kontoDAOfk.dodaj(nowekonto);
            return 0;
        } else {
            return 1;
        }
    }
    
    private static String oblicznumerkonta(Konto macierzyste, KontoDAOfk kontoDAOfk, String podatnik) {
        int liczbakont = kontoDAOfk.policzPotomne(podatnik, macierzyste.getPelnynumer());
        if (liczbakont > 0) {
            return String.valueOf(liczbakont+1);
        } else {
            return "1";
        }
    }

   public static boolean sprawdzczymacierzystymapotomne(String podatnik, Konto doUsuniecia, KontoDAOfk kontoDAO) {
        List<Konto> kontapotomne = new ArrayList<>();
        kontapotomne.addAll(kontoDAO.findKontaPotomnePodatnik(podatnik, doUsuniecia.getMacierzyste()));
        kontapotomne.remove(doUsuniecia);
        return !kontapotomne.isEmpty();
    }
}
