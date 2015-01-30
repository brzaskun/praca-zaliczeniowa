/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.MiejsceKosztowDAO;
import daoFK.PojazdyDAO;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
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
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setMacierzyste("0");
         nowekonto.setLevel(0);
         nowekonto.setMacierzysty(0);
         nowekonto.setMapotomkow(false);
         nowekonto.setPelnynumer(nowekonto.getNrkonta());
         return zachowajkonto(nowekonto, kontoDAOfk, wpisView);
     }
     
     public static int dodajsyntetyczneWzorzec(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("syntetyczne");
         nowekonto.setPodatnik("Wzorcowy");
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setMacierzyste("0");
         nowekonto.setLevel(0);
         nowekonto.setMacierzysty(0);
         nowekonto.setMapotomkow(false);
         nowekonto.setPelnynumer(nowekonto.getNrkonta());
         return zachowajkontowzorzec(nowekonto, kontoDAOfk, wpisView);
     }
     
     public static int dodajanalityczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(wpisView.getRokWpisu());
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
     
     public static int dodajanalityczneWzorzec(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik("Wzorcowy");
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(oblicznumerkonta(macierzyste, kontoDAOfk, "Wzorcowy"));
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkontowzorzec(nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajanalityczne(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String numerkonta,WpisView wpisView) {
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(wpisView.getRokWpisu());
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
    
    public static int dodajslownikKontrahenci(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik kontrahenci");
        nowekonto.setNazwaskrocona("Kontrahenci");
        return uzupelnijdaneslownika(nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikMiejscaKosztow(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik miejsca kosztów");
        nowekonto.setNazwaskrocona("Miejsca kosztów");
        return uzupelnijdaneslownika(nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikPojazdyiMaszyny(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik pojazdy i maszyny");
        nowekonto.setNazwaskrocona("Pojazd");
        return uzupelnijdaneslownika(nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    private static int uzupelnijdaneslownika(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
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
    
    public static int dodajelementyslownikaKontrahenci(Konto kontomacierzyste, KontoDAOfk kontoDAO, KliencifkDAO kliencifkDAO, WpisView wpisView) {
        List<Kliencifk> listaprzyporzadkowanychklientow = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        if (listaprzyporzadkowanychklientow != null) {
            for (Kliencifk p : listaprzyporzadkowanychklientow) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getNazwa());
                nowekonto.setNazwaskrocona(p.getNip());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaMiejscaKosztow(Konto kontomacierzyste, KontoDAOfk kontoDAO, MiejsceKosztowDAO miejsceKosztowDAO, WpisView wpisView) {
        List<MiejsceKosztow> listamiejsckosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        if (listamiejsckosztow != null) {
            for (MiejsceKosztow p : listamiejsckosztow) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getOpismiejsca());
                nowekonto.setNazwaskrocona(p.getOpisskrocony());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                p.setAktywny(true);
                miejsceKosztowDAO.edit(p);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaPojazdy(Konto kontomacierzyste, KontoDAOfk kontoDAO, PojazdyDAO pojazdyDAO, WpisView wpisView) {
        List<Pojazdy> listapojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        if (listapojazdy != null) {
            for (Pojazdy p : listapojazdy) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getNrrejestracyjny());
                nowekonto.setNazwaskrocona(p.getNazwapojazdu());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                p.setAktywny(true);
                pojazdyDAO.edit(p);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    
    public static int aktualizujslownikKontrahenci(Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), 1);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(kliencifk.getNazwa());
            nowekonto.setNazwaskrocona(kliencifk.getNip());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(nowekonto, p, kontoDAO, kliencifk.getNrkonta(), wpisView);
            if (wynikdodania == 1) {
                return 1;
            }
        }
        return 0;
    }
    
    public static void porzadkujslownikKontrahenci(Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), 1);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(kliencifk.getNazwa());
            nowekonto.setNazwaskrocona(kliencifk.getNip());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            PlanKontFKBean.dodajanalityczne(nowekonto, p, kontoDAO, kliencifk.getNrkonta(), wpisView);
        }
    }
    
    public static void porzadkujslownikMiejscaKosztow(MiejsceKosztow r, KontoDAOfk kontoDAO, WpisView wpisView) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(),2);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(r.getOpismiejsca());
            nowekonto.setNazwaskrocona(r.getOpisskrocony());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            PlanKontFKBean.dodajanalityczne(nowekonto, p, kontoDAO, p.getNrkonta(), wpisView);
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
    
    public static void zablokujKontoMacierzysteSlownik(Konto macierzyste, KontoDAOfk kontoDAOfk, int idslownika) {
        macierzyste.setBlokada(true);
        macierzyste.setMapotomkow(true);
        macierzyste.setIdslownika(idslownika);
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
    
    private static int znajdzduplikatwzorzec(Konto nowe, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        List<Konto> wykazkont = kontoDAOfk.findWszystkieKontaPodatnika("Wzorcowy", wpisView.getRokWpisuSt());
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
    
    private static int zachowajkontowzorzec(Konto nowekonto, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        if (0 == znajdzduplikatwzorzec(nowekonto, kontoDAOfk, wpisView)) {
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
