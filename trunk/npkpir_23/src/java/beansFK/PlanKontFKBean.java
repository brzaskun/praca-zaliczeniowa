/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.DelegacjaDAO;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.MiejsceKosztowDAO;
import daoFK.PojazdyDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Delegacja;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
import error.E;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.TreeNode;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class PlanKontFKBean {
   
     public static int dodajsyntetyczne(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("syntetyczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setMacierzyste("0");
         nowekonto.setLevel(0);
         nowekonto.setMacierzysty(0);
         nowekonto.setMapotomkow(false);
         nowekonto.setPelnynumer(nowekonto.getNrkonta());
         return zachowajkonto(wykazkont, nowekonto, kontoDAOfk, wpisView);
     }
     
     public static int dodajsyntetyczneWzorzec(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
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
     
     public static int dodajanalityczne(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         if (macierzyste.getLevel()==0) {
             nowekonto.setSyntetycznenumer(macierzyste.getPelnynumer());
         } else {
             nowekonto.setSyntetycznenumer(macierzyste.getSyntetycznenumer());
         }
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(oblicznumerkonta(macierzyste, kontoDAOfk, wpisView));
         nowekonto.setPrzychod0koszt1(macierzyste.isPrzychod0koszt1());
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(wykazkont, nowekonto, kontoDAOfk, wpisView);
    }
     
     public static int dodajanalityczneWzorzec(Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         if (macierzyste.getLevel()==0) {
             nowekonto.setSyntetycznenumer(macierzyste.getPelnynumer());
         } else {
             nowekonto.setSyntetycznenumer(macierzyste.getSyntetycznenumer());
         }
         nowekonto.setSyntetyczne("analityczne");
         nowekonto.setPodatnik("Wzorcowy");
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setBilansowewynikowe(macierzyste.getBilansowewynikowe());
         nowekonto.setZwyklerozrachszczegolne(macierzyste.getZwyklerozrachszczegolne());
         nowekonto.setNrkonta(oblicznumerkontaWzorcowy(macierzyste, kontoDAOfk, wpisView));
         nowekonto.setPrzychod0koszt1(macierzyste.isPrzychod0koszt1());
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkontowzorzec(nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajanalityczne(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, String numerkonta,WpisView wpisView) {
        if (macierzyste.getLevel()==0) {
             nowekonto.setSyntetycznenumer(macierzyste.getPelnynumer());
         } else {
             nowekonto.setSyntetycznenumer(macierzyste.getSyntetycznenumer());
         }
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
         return zachowajkonto(wykazkont, nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikKontrahenci(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik kontrahenci");
        nowekonto.setNazwaskrocona("Kontrahenci");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikMiejscaKosztow(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik miejsca kosztów");
        nowekonto.setNazwaskrocona("Miejsca kosztów");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikPojazdyiMaszyny(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik pojazdy i maszyny");
        nowekonto.setNazwaskrocona("Pojazd");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikMiesiace(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik miesiące");
        nowekonto.setNazwaskrocona("Miesiąc");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikDelegacjeKrajowe(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik delegacji krajowych");
        nowekonto.setNazwaskrocona("Delegacje krajowe");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikDelegacjeZagraniczne(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        nowekonto.setNazwapelna("Słownik delegacji zagranicznych");
        nowekonto.setNazwaskrocona("Delegacje zagraniczne");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView);
    }
    private static int uzupelnijdaneslownika(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        if (macierzyste.getLevel()==0) {
             nowekonto.setSyntetycznenumer(macierzyste.getPelnynumer());
         } else {
             nowekonto.setSyntetycznenumer(macierzyste.getSyntetycznenumer());
         }
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
         return zachowajkonto(wykazkont,nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajelementyslownikaKontrahenci(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, KliencifkDAO kliencifkDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Kliencifk> listaprzyporzadkowanychklientow = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
        if (listaprzyporzadkowanychklientow != null) {
            for (Kliencifk p : listaprzyporzadkowanychklientow) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getNazwa());
                nowekonto.setDe(p.getNazwa());
                nowekonto.setNazwaskrocona(p.getNip());
                nowekonto.setSlownikowe(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaMiejscaKosztow(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, MiejsceKosztowDAO miejsceKosztowDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<MiejsceKosztow> listamiejsckosztow = miejsceKosztowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        if (listamiejsckosztow != null) {
            for (MiejsceKosztow p : listamiejsckosztow) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getOpismiejsca());
                nowekonto.setDe(p.getOpismiejsca());
                nowekonto.setNazwaskrocona(p.getOpisskrocony());
                nowekonto.setSlownikowe(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                p.setAktywny(true);
                miejsceKosztowDAO.edit(p);
                naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaPojazdy(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, PojazdyDAO pojazdyDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Pojazdy> listapojazdy = pojazdyDAO.findPojazdyPodatnik(wpisView.getPodatnikObiekt());
        if (listapojazdy != null) {
            for (Pojazdy p : listapojazdy) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getNrrejestracyjny());
                nowekonto.setDe(p.getNrrejestracyjny());
                nowekonto.setNazwaskrocona(p.getNazwapojazdu());
                nowekonto.setSlownikowe(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                p.setAktywny(true);
                pojazdyDAO.edit(p);
                naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
               
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaMiesiace(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<String> listamiesiace = Mce.getMcenazwaList();
        if (listamiesiace != null) {
            int i = 1;
            for (String p : listamiesiace) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p);
                nowekonto.setDe(Mce.getMce_pl_de().get(p));
                nowekonto.setNazwaskrocona(p);
                nowekonto.setSlownikowe(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, kontomacierzyste, kontoDAO, String.valueOf(i++), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
     public static int dodajelementyslownikaDelegacje(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, DelegacjaDAO delegacjaDAO, WpisView wpisView, boolean krajowa0zagraniczna1, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Delegacja> listadelegacje = delegacjaDAO.findDelegacjaPodatnik(wpisView, krajowa0zagraniczna1);
        if (listadelegacje != null) {
            for (Delegacja p : listadelegacje) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(p.getOpisdlugi());
                nowekonto.setDe(p.getOpisdlugi());
                nowekonto.setNazwaskrocona(p.getOpiskrotki());
                nowekonto.setSlownikowe(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, kontomacierzyste, kontoDAO, p.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                p.setAktywny(true);
                delegacjaDAO.edit(p);
                naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
     
     public static void naniesprzyporzadkowanieSlownikowe(Konto noweKonto, WpisView wpisView, KontoDAOfk kontoDAOfk, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        try {
            Konto macierzyste = kontoDAOfk.findKonto(noweKonto.getMacierzyste(), wpisView);
            if (noweKonto.getPelnynumer().equals("202-1-0")){
                System.out.println("d");
            }
            KontopozycjaZapis kpo = null;
            if (macierzyste != null) {
                kpo = kontopozycjaZapisDAO.findByKonto(macierzyste);
            } else {
                kpo = kontopozycjaZapisDAO.findByKonto(noweKonto);
            }
            if (kpo.getSyntetykaanalityka().equals("analityka")) {
                naniesPrzyporzadkowanie(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "analityka");
            } else {
                naniesPrzyporzadkowanie(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
     
     public static void naniesprzyporzadkowanieSlownikoweWzorcowy(Konto noweKonto, WpisView wpisView, KontoDAOfk kontoDAOfk, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        try {
            Konto macierzyste = kontoDAOfk.findKontoWzorcowy(noweKonto.getMacierzyste(), wpisView);
            KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(macierzyste);
            if (kpo.getSyntetykaanalityka().equals("analityka")) {
                Msg.msg("w","Konto przyporządkowane z poziomu analityki!");
            } else {
                naniesPrzyporzadkowanie(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
     
    public static int aktualizujslownikKontrahenci(List<Konto> wykazkont, KliencifkDAO kliencifkDAO, Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, 1);
        try {
            for (Konto p : kontamacierzysteZeSlownikiem) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(kliencifk.getNazwa());
                nowekonto.setDe(kliencifk.getNazwa());
                nowekonto.setNazwaskrocona(kliencifk.getNip());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, kliencifk.getNrkonta(), wpisView);
                if (wynikdodania == 1) {
                    return 1;
                }
                naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
                kliencifk.setAktywny(true);
                kliencifkDAO.edit(kliencifk);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return 0;
    }
    
    public static int aktualizujslownikKontrahenciRemove(Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView) {
        List<Konto> kontaslownikowaklientfk = kontoDAO.findSlownikoweKlienci(wpisView, kliencifk);
        for (Konto p : kontaslownikowaklientfk) {
            try {
                kontoDAO.destroy(p);
            } catch (Exception e) {
                System.out.println("Blad " + e.getStackTrace()[0].toString());
                System.out.println("blad podczas usuwania konta slownikowego aktualizujslownikKontrahenciRemove()");
            }
        }
        return 0;
    }
    
    public static int aktualizujslownikMiejscaKosztow(List<Konto> wykazkont, MiejsceKosztowDAO miejsceKosztowDAO, MiejsceKosztow miejscekosztow, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, 2);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(miejscekosztow.getOpismiejsca());
            nowekonto.setNazwaskrocona(miejscekosztow.getOpisskrocony());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, miejscekosztow.getNrkonta(), wpisView);
            naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            miejscekosztow.setAktywny(true);
            miejsceKosztowDAO.edit(miejscekosztow);
        }
        return 0;
    }
    
    public static int aktualizujslownikPojazdy(List<Konto> wykazkont, PojazdyDAO pojazdyDAO, Pojazdy pojazd, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, 3);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(pojazd.getNrrejestracyjny());
            nowekonto.setNazwaskrocona(pojazd.getNazwapojazdu());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, pojazd.getNrkonta(), wpisView);
            naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            pojazd.setAktywny(true);
            pojazdyDAO.edit(pojazd);
        }
        return 0;
    }
    
     public static int aktualizujslownikDelegacjeKrajowe(List<Konto> wykazkont, DelegacjaDAO delegacjaDAO, Delegacja delegacja, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, 5);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(delegacja.getOpisdlugi());
            nowekonto.setNazwaskrocona(delegacja.getOpiskrotki());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, delegacja.getNrkonta(), wpisView);
            naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            delegacja.setAktywny(true);
            delegacjaDAO.edit(delegacja);
        }
        return 0;
    }
     
      public static int aktualizujslownikDelegacjeZagraniczne(List<Konto> wykazkont, DelegacjaDAO delegacjaDAO, Delegacja delegacja, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, 6);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(delegacja.getOpisdlugi());
            nowekonto.setNazwaskrocona(delegacja.getOpiskrotki());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, delegacja.getNrkonta(), wpisView);
            naniesprzyporzadkowanieSlownikowe(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            delegacja.setAktywny(true);
            delegacjaDAO.edit(delegacja);
        }
        return 0;
    }
    
//    public static void porzadkujslownikKontrahenci(List<Konto> wykazkont, Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView) {
//        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, 1);
//        for (Konto p : kontamacierzysteZeSlownikiem) {
//            Konto nowekonto = new Konto();
//            nowekonto.setNazwapelna(kliencifk.getNazwa());
//            nowekonto.setNazwaskrocona(kliencifk.getNip());
//            nowekonto.setSlownikowe(true);
//            nowekonto.setBlokada(true);
//            PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, kliencifk.getNrkonta(), wpisView);
//        }
//    }
//    
//    public static void porzadkujslownikMiejscaKosztow(List<Konto> wykazkont, MiejsceKosztow r, KontoDAOfk kontoDAO, WpisView wpisView) {
//        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView,2);
//        for (Konto p : kontamacierzysteZeSlownikiem) {
//            Konto nowekonto = new Konto();
//            nowekonto.setNazwapelna(r.getOpismiejsca());
//            nowekonto.setNazwaskrocona(r.getOpisskrocony());
//            nowekonto.setSlownikowe(true);
//            nowekonto.setBlokada(true);
//            PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, r.getNrkonta(), wpisView);
//        }
//    }
//    
//    public static void porzadkujslownikDelegacjeKrajowe(List<Konto> wykazkont, Delegacja r, KontoDAOfk kontoDAO, WpisView wpisView) {
//        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView,5);
//        for (Konto p : kontamacierzysteZeSlownikiem) {
//            Konto nowekonto = new Konto();
//            nowekonto.setNazwapelna(r.getOpisdlugi());
//            nowekonto.setNazwaskrocona(r.getOpiskrotki());
//            nowekonto.setSlownikowe(true);
//            nowekonto.setBlokada(true);
//            PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, r.getNrkonta(), wpisView);
//        }
//    }
//    
//    public static void porzadkujslownikDelegacjeZagraniczne(List<Konto> wykazkont, Delegacja r, KontoDAOfk kontoDAO, WpisView wpisView) {
//        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView,6);
//        for (Konto p : kontamacierzysteZeSlownikiem) {
//            Konto nowekonto = new Konto();
//            nowekonto.setNazwapelna(r.getOpisdlugi());
//            nowekonto.setNazwaskrocona(r.getOpiskrotki());
//            nowekonto.setSlownikowe(true);
//            nowekonto.setBlokada(true);
//            PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, r.getNrkonta(), wpisView);
//        }
//    }
//    
//    public static void porzadkujslownikMiesiace(List<Konto> wykazkont, String nazwamca, int numerkonta, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAOfk) {
//        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView,4);
//        for (Konto p : kontamacierzysteZeSlownikiem) {
//            Konto nowekonto = new Konto();
//            nowekonto.setNazwapelna(nazwamca);
//            nowekonto.setNazwaskrocona(nazwamca);
//            nowekonto.setSlownikowe(true);
//            nowekonto.setBlokada(true);
//            PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, String.valueOf(numerkonta), wpisView);
//            int wynikdodaniakonta = 1;
//            if (wynikdodaniakonta == 0) {
//                try {
//                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(p);
//                    if (kpo.getSyntetykaanalityka().equals("analityka")) {
//                           Msg.msg("w","Konto przyporządkowane z poziomu analityki!");
//                        }
//                        if (kpo.getSyntetykaanalityka().equals("zwykłe")) {
//                            PlanKontFKBean.naniesPrzyporzadkowanie(kpo, nowekonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka");
//                        }
//                } catch (Exception e) {
//                    E.e(e);
//                }
//            }
//        }
//    }
    
    
    public static void porzadkujslownik(List<Konto> wykazkont, String npelna, String nskrocona, int numerkonta, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAOfk, int nrslownika) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView, nrslownika);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(npelna);
            nowekonto.setNazwaskrocona(nskrocona);
            nowekonto.setSlownikowe(true);
            int wynikdodaniakonta = 1;
            wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, String.valueOf(numerkonta), wpisView);
            if (wynikdodaniakonta == 0) {
                try {
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(p);
                    if (kpo.getSyntetykaanalityka().equals("analityka")) {
                        Msg.msg("w", "Konto przyporządkowane z poziomu analityki!");
                    } else {
                        PlanKontFKBean.naniesPrzyporzadkowanie(kpo, nowekonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka");
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
    }
    
    
            
    public static int usunelementyslownika(String kontomacierzyste, KontoDAOfk kontoDAO, WpisView wpisView, List<Konto> wykazkont, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<Konto> listakont = kontoDAO.findKontaPotomnePodatnik(wpisView, kontomacierzyste);
        if (listakont != null) {
            for (Konto p : listakont) {
                kontopozycjaZapisDAO.destroy(kontopozycjaZapisDAO.findByKonto(p));
                kontoDAO.destroy(p);
                wykazkont.remove(p);
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
            r.createTreeNodesForElement(kontoDAO.findWszystkieKontaWzorcowy(wpisView));
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
     
    private static Konto znajdzduplikat(List<Konto> wykazkont, Konto nowe, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        if (wykazkont.contains(nowe)) {
            return wykazkont.get(wykazkont.indexOf(nowe));
        } else {
            return null;
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

    private static int zachowajkonto(List<Konto> wykazkont, Konto nowekonto, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        Konto konto = znajdzduplikat(wykazkont, nowekonto, kontoDAOfk, wpisView);
        if (konto == null) {
                kontoDAOfk.dodaj(nowekonto);
            return 0;
        } else {
                konto.setNazwapelna(nowekonto.getNazwapelna());
                konto.setNazwaskrocona(nowekonto.getNazwaskrocona());
                kontoDAOfk.edit(konto);
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
    
    private static String oblicznumerkonta(Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        int liczbakont = kontoDAOfk.policzPotomne(wpisView, macierzyste.getPelnynumer());
        if (liczbakont > 0) {
            return String.valueOf(liczbakont+1);
        } else {
            return "1";
        }
    }
    
    private static String oblicznumerkontaWzorcowy(Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        int liczbakont = kontoDAOfk.policzPotomneWzorcowy(wpisView, macierzyste.getPelnynumer());
        if (liczbakont > 0) {
            return String.valueOf(liczbakont+1);
        } else {
            return "1";
        }
    }

   public static boolean sprawdzczymacierzystymapotomne(WpisView wpisView, Konto doUsuniecia, KontoDAOfk kontoDAO) {
        List<Konto> kontapotomne = new ArrayList<>();
        kontapotomne.addAll(kontoDAO.findKontaPotomnePodatnik(wpisView, doUsuniecia.getMacierzyste()));
        kontapotomne.remove(doUsuniecia);
        return !kontapotomne.isEmpty();
    }

    public static void usunelementslownika(Delegacja delegacja, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView,6);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomne(wpisView, p.getPelnynumer(), p.getBilansowewynikowe());
            for (Konto r : kontapotomne) {
                if (r.getNrkonta().equals(delegacja.getNrkonta())) {
                    kontoDAOfk.destroy(r);
                }
            }
        }
    }

    
    public static void naniesPrzyporzadkowanie(KontopozycjaZapis kpo, Konto noweKonto, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAOfk, String wersja) {
        try {
            KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(noweKonto);
            if (kp == null) {
                kp = new KontopozycjaZapis();
                kp.setPozycjaWn(kpo.getPozycjaWn());
                kp.setPozycjaMa(kpo.getPozycjaMa());
                kp.setStronaWn(kpo.getStronaWn());
                kp.setStronaMa(kpo.getStronaMa());
                kp.setSyntetykaanalityka(wersja);
                kp.setKontoID(noweKonto);
                kp.setUkladBR(kpo.getUkladBR());
                kontopozycjaZapisDAO.edit(kp);
                noweKonto.setKontopozycjaID(new KontopozycjaBiezaca(kp));
                kontoDAOfk.edit(noweKonto);
            } else {
                kp.setPozycjaWn(kpo.getPozycjaWn());
                kp.setPozycjaMa(kpo.getPozycjaMa());
                kp.setStronaWn(kpo.getStronaWn());
                kp.setStronaMa(kpo.getStronaMa());
                kp.setSyntetykaanalityka(wersja);
                kp.setUkladBR(kpo.getUkladBR());
                kontopozycjaZapisDAO.edit(kp);
                noweKonto.setKontopozycjaID(new KontopozycjaBiezaca(kp));
                kontoDAOfk.edit(noweKonto);
            }
        } catch (Exception e) {
            E.e(e);
            KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(noweKonto);
            if (kp != null) {
                noweKonto.setKontopozycjaID(new KontopozycjaBiezaca(kp));
                kontoDAOfk.edit(noweKonto);
            }
        }
        
    }

    public static Konto wyszukajmacierzyste(WpisView wpisView, KontoDAOfk kontoDAOfk, String pelnynumer) {
        Konto konto = kontoDAOfk.findKonto(pelnynumer, wpisView);
        return konto;
    }

    public static String modyfikujnr(String pelnynumer) {
        String[] pola = pelnynumer.split("-");
        String koncowka = pola[pola.length-1];
        int indexkoncowy = pelnynumer.length()-koncowka.length()-1;
        String nowynumer = pelnynumer.substring(0, indexkoncowy);
        return nowynumer;
    }

   
    
}
