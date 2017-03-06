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
import daoFK.MiejscePrzychodowDAO;
import daoFK.PojazdyDAO;
import daoFK.UkladBRDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Delegacja;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.MiejscePrzychodow;
import entityfk.Pojazdy;
import entityfk.UkladBR;
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

public class PlanKontFKBean {
   
     public static int dodajsyntetyczne(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView) {
         nowekonto.setSyntetyczne("syntetyczne");
         nowekonto.setPodatnik(wpisView.getPodatnikWpisu());
         nowekonto.setRok(wpisView.getRokWpisu());
         nowekonto.setMacierzyste("0");
         nowekonto.setLevel(0);
         nowekonto.setMacierzysty(0);
         nowekonto.setKontomacierzyste(null);
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
         nowekonto.setKontomacierzyste(null);
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
         nowekonto.setKontomacierzyste(macierzyste);
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
         nowekonto.setKontomacierzyste(macierzyste);
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
         nowekonto.setPrzychod0koszt1(macierzyste.isPrzychod0koszt1());
         nowekonto.setMapotomkow(false);
         nowekonto.setMacierzyste(macierzyste.getPelnynumer());
         nowekonto.setMacierzysty(macierzyste.getLp());
         nowekonto.setKontomacierzyste(macierzyste);
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         return zachowajkonto(wykazkont, nowekonto, kontoDAOfk, wpisView);
    }
    
    public static int dodajslownikKontrahenci(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik kontrahenci");
        nowekonto.setNazwaskrocona("Kontrahenci");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    
    public static int dodajslownikMiejscaKosztow(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik miejsca kosztów");
        nowekonto.setNazwaskrocona("Miejsca kosztów");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    
    public static int dodajslownikPojazdyiMaszyny(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik pojazdy i maszyny");
        nowekonto.setNazwaskrocona("Pojazd");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    
    public static int dodajslownikMiesiace(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik miesiące");
        nowekonto.setNazwaskrocona("Miesiąc");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    
    public static int dodajslownikDelegacjeKrajowe(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik delegacji krajowych");
        nowekonto.setNazwaskrocona("Delegacje krajowe");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    
    public static int dodajslownikDelegacjeZagraniczne(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik delegacji zagranicznych");
        nowekonto.setNazwaskrocona("Delegacje zagraniczne");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    
    public static int dodajslownikMiejscaPrzychodow(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        nowekonto.setNazwapelna("Słownik miejsca przychodów");
        nowekonto.setNazwaskrocona("Miejsca przychodów");
        return uzupelnijdaneslownika(wykazkont, nowekonto, macierzyste, kontoDAOfk, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
    }
    private static int uzupelnijdaneslownika(List<Konto> wykazkont, Konto nowekonto, Konto macierzyste, KontoDAOfk kontoDAOfk, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
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
         nowekonto.setKontomacierzyste(macierzyste);
         nowekonto.setPrzychod0koszt1(macierzyste.isPrzychod0koszt1());
         nowekonto.setLevel(obliczlevel(nowekonto.getMacierzyste()));
         nowekonto.setPelnynumer(nowekonto.getMacierzyste() + "-" + nowekonto.getNrkonta());
         int wynikdodaniakonta = zachowajkonto(wykazkont,nowekonto, kontoDAOfk, wpisView);
         naniesPozycjenaKonto(wynikdodaniakonta, kontopozycjaZapisDAO, nowekonto, macierzyste, kontoDAOfk, ukladBRDAO);
         return wynikdodaniakonta;
    }
    
    public static int dodajelementyslownikaKontrahenci(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, KliencifkDAO kliencifkDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
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
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaMiejscaKosztow(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, MiejsceKosztowDAO miejsceKosztowDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
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
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaMiejscaPrzychodow(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, MiejscePrzychodowDAO miejscePrzychodowDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<MiejscePrzychodow> listamiejscprzychodow = miejscePrzychodowDAO.findMiejscaPodatnik(wpisView.getPodatnikObiekt());
        if (listamiejscprzychodow != null) {
            for (MiejscePrzychodow p : listamiejscprzychodow) {
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
                miejscePrzychodowDAO.edit(p);
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaPojazdy(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, PojazdyDAO pojazdyDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
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
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
               
            }
            return 0;
        } else {
            return 0;
        }
    }
    
    public static int dodajelementyslownikaMiesiace(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<String> listamiesiace = Mce.getMcenazwaListSlownik();
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
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
     public static int dodajelementyslownikaDelegacje(List<Konto> wykazkont, Konto kontomacierzyste, KontoDAOfk kontoDAO, DelegacjaDAO delegacjaDAO, WpisView wpisView, boolean krajowa0zagraniczna1, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
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
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            }
            return 0;
        } else {
            return 0;
        }
    }
     
     public static void naniesprzyporzadkowanie(Konto noweKonto, WpisView wpisView, KontoDAOfk kontoDAOfk, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        try {
            Konto macierzyste = pobierzmacierzyste(noweKonto, kontoDAOfk, wpisView);
            if (noweKonto.getPelnynumer().equals("010-3")){
                System.out.println("d");
            }
            KontopozycjaZapis kpo = null;
            if (macierzyste != null) {
                kpo = kontopozycjaZapisDAO.findByKonto(macierzyste, ukladBRDAO);
                if (kpo != null) {
                    naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", ukladBRDAO);
                }
            } else {
                kpo = kontopozycjaZapisDAO.findByKonto(noweKonto, ukladBRDAO);
                if (kpo != null) {
                    naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, kpo.getSyntetykaanalityka(), ukladBRDAO);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
     
      private static Konto pobierzmacierzyste(Konto p, KontoDAOfk kontoDAO, WpisView wpisView) {
        Konto macierzyste = p.getKontomacierzyste();
        if (macierzyste == null) {
            macierzyste = kontoDAO.findKonto(p.getMacierzyste(), wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        }
        return macierzyste;
    }
     
     public static void naniesprzyporzadkowanieWzorcowy(Konto noweKonto, WpisView wpisView, KontoDAOfk kontoDAOfk, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        try {
            Konto macierzyste = kontoDAOfk.findKonto(noweKonto.getMacierzyste(), "Wzorcowy", wpisView.getRokWpisu());
            KontopozycjaZapis kpo = null;
            if (macierzyste != null) {
                kpo = kontopozycjaZapisDAO.findByKonto(macierzyste, ukladBRDAO);
                if (kpo != null) {
                    naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", ukladBRDAO);
                }
            } else {
                kpo = kontopozycjaZapisDAO.findByKonto(noweKonto, ukladBRDAO);
                if (kpo != null) {
                    naniesPrzyporzadkowaniePojedynczeKonto(kpo, noweKonto, kontopozycjaZapisDAO, kontoDAOfk, kpo.getSyntetykaanalityka(), ukladBRDAO);
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
     
    public static int aktualizujslownikKontrahenci(List<Konto> wykazkont, KliencifkDAO kliencifkDAO, Kliencifk kliencifk, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 1);
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
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
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
    
    public static int aktualizujslownikMiejscaKosztow(List<Konto> wykazkont, MiejsceKosztowDAO miejsceKosztowDAO, MiejsceKosztow miejscekosztow, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 2);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(miejscekosztow.getOpismiejsca());
            nowekonto.setNazwaskrocona(miejscekosztow.getOpisskrocony());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, miejscekosztow.getNrkonta(), wpisView);
            naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            miejscekosztow.setAktywny(true);
            miejsceKosztowDAO.edit(miejscekosztow);
        }
        return 0;
    }
    
    public static int aktualizujslownikMiejscaPrzychodow(List<Konto> wykazkont, MiejscePrzychodowDAO miejsceKosztowDAO, MiejscePrzychodow miejsceprzychodow, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        try {
            List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 7);
            for (Konto p : kontamacierzysteZeSlownikiem) {
                Konto nowekonto = new Konto();
                nowekonto.setNazwapelna(miejsceprzychodow.getOpismiejsca());
                nowekonto.setNazwaskrocona(miejsceprzychodow.getOpisskrocony());
                nowekonto.setSlownikowe(true);
                nowekonto.setBlokada(true);
                int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, miejsceprzychodow.getNrkonta(), wpisView);
                naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
                if (wynikdodania == 1) {
                    return 1;
                }
                miejsceprzychodow.setAktywny(true);
                miejsceKosztowDAO.edit(miejsceprzychodow);
            }
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public static int aktualizujslownikPojazdy(List<Konto> wykazkont, PojazdyDAO pojazdyDAO, Pojazdy pojazd, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 3);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(pojazd.getNrrejestracyjny());
            nowekonto.setNazwaskrocona(pojazd.getNazwapojazdu());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, pojazd.getNrkonta(), wpisView);
            naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            pojazd.setAktywny(true);
            pojazdyDAO.edit(pojazd);
        }
        return 0;
    }
    
     public static int aktualizujslownikDelegacjeKrajowe(List<Konto> wykazkont, DelegacjaDAO delegacjaDAO, Delegacja delegacja, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 5);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(delegacja.getOpisdlugi());
            nowekonto.setNazwaskrocona(delegacja.getOpiskrotki());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, delegacja.getNrkonta(), wpisView);
            naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            delegacja.setAktywny(true);
            delegacjaDAO.edit(delegacja);
        }
        return 0;
    }
     
      public static int aktualizujslownikDelegacjeZagraniczne(List<Konto> wykazkont, DelegacjaDAO delegacjaDAO, Delegacja delegacja, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAO.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), 6);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(delegacja.getOpisdlugi());
            nowekonto.setNazwaskrocona(delegacja.getOpiskrotki());
            nowekonto.setSlownikowe(true);
            nowekonto.setBlokada(true);
            int wynikdodania = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, p, kontoDAO, delegacja.getNrkonta(), wpisView);
            naniesprzyporzadkowanie(nowekonto, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO);
            if (wynikdodania == 1) {
                return 1;
            }
            delegacja.setAktywny(true);
            delegacjaDAO.edit(delegacja);
        }
        return 0;
    }
    
    
    public static void porzadkujslownik(List<Konto> kontamacierzysteZeSlownikiem, List<Konto> wykazkont, String npelna, String nskrocona, int numerkonta, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAOfk, int nrslownika, UkladBRDAO ukladBRDAO) {
        for (Konto kontomacierzyste : kontamacierzysteZeSlownikiem) {
            Konto nowekonto = new Konto();
            nowekonto.setNazwapelna(npelna);
            nowekonto.setNazwaskrocona(nskrocona);
            nowekonto.setSlownikowe(true);
            nowekonto.setPrzychod0koszt1(kontomacierzyste.isPrzychod0koszt1());
            int wynikdodaniakonta = 1;
            if (numerkonta == 13) {
                System.out.println("");
            }
            wynikdodaniakonta = PlanKontFKBean.dodajanalityczne(wykazkont, nowekonto, kontomacierzyste, kontoDAO, String.valueOf(numerkonta), wpisView);
            Konto konto = znajdzduplikat(wykazkont, nowekonto, kontoDAOfk, wpisView);
            if (konto != null && konto.getKontopozycjaID() == null) {
                naniesPozycjenaKonto(0, kontopozycjaZapisDAO, konto, kontomacierzyste, kontoDAOfk, ukladBRDAO);
            } else {
                naniesPozycjenaKonto(wynikdodaniakonta, kontopozycjaZapisDAO, nowekonto, kontomacierzyste, kontoDAOfk, ukladBRDAO);
            }
            
        }
    }
    
    private static void naniesPozycjenaKonto(int wynikdodaniakonta, KontopozycjaZapisDAO kontopozycjaZapisDAO, Konto nowekonto, Konto kontomacierzyste, KontoDAOfk kontoDAOfk, UkladBRDAO ukladBRDAO) {
         if (wynikdodaniakonta == 0) {
                try {
                    KontopozycjaZapis kpo = kontopozycjaZapisDAO.findByKonto(kontomacierzyste, ukladBRDAO);
                    if (kpo.getSyntetykaanalityka().equals("analityka")) {
                        Msg.msg("w", "Konto przyporządkowane z poziomu analityki!");
                    } else {
                        PlanKontFKBean.naniesPrzyporzadkowaniePojedynczeKonto(kpo, nowekonto, kontopozycjaZapisDAO, kontoDAOfk, "syntetyka", ukladBRDAO);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
    }
    
    
            
    public static int usunelementyslownika(String kontomacierzyste, KontoDAOfk kontoDAO, String podatnik, Integer rok, List<Konto> wykazkont, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBRDAO ukladBRDAO) {
        List<Konto> listakont = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, kontomacierzyste);
        if (listakont != null) {
            for (Konto p : listakont) {
                kontopozycjaZapisDAO.destroy(kontopozycjaZapisDAO.findByKonto(p, ukladBRDAO));
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
                konto.setPrzychod0koszt1(nowekonto.isPrzychod0koszt1());
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

   public static boolean sprawdzczymacierzystymapotomne(String podatnik, Integer rok, Konto doUsuniecia, KontoDAOfk kontoDAO) {
        List<Konto> kontapotomne = new ArrayList<>();
        kontapotomne.addAll(kontoDAO.findKontaPotomnePodatnik(podatnik, rok, doUsuniecia.getMacierzyste()));
        kontapotomne.remove(doUsuniecia);
        return !kontapotomne.isEmpty();
    }
   
   public static List<Konto> pobierzpotomne(String podatnik, Integer rok, Konto doUsuniecia, KontoDAOfk kontoDAO) {
        List<Konto> kontapotomne = new ArrayList<>();
        kontapotomne.addAll(kontoDAO.findKontaPotomnePodatnik(podatnik, rok, doUsuniecia.getPelnynumer()));
        kontapotomne.remove(doUsuniecia);
        return kontapotomne;
    }
   
    public static List<Konto> pobierzpotomne(Konto doUsuniecia, KontoDAOfk kontoDAO) {
        List<Konto> kontapotomne = new ArrayList<>();
        kontapotomne.addAll(kontoDAO.findKontaPotomnePodatnik(doUsuniecia.getPodatnik(), doUsuniecia.getRok(), doUsuniecia.getPelnynumer()));
        kontapotomne.remove(doUsuniecia);
        return kontapotomne;
    }

    public static void usunelementslownika(Delegacja delegacja, KontoDAOfk kontoDAOfk, WpisView wpisView) {
        List<Konto> kontamacierzysteZeSlownikiem = kontoDAOfk.findKontaMaSlownik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(),6);
        for (Konto p : kontamacierzysteZeSlownikiem) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomne(wpisView, p.getPelnynumer(), p.getBilansowewynikowe());
            for (Konto r : kontapotomne) {
                if (r.getNrkonta().equals(delegacja.getNrkonta())) {
                    kontoDAOfk.destroy(r);
                }
            }
        }
    }

    
    public static void naniesPrzyporzadkowaniePojedynczeKonto(KontopozycjaZapis kpo, Konto noweKonto, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAOfk, String wersja, UkladBRDAO ukladBRDAO) {
        try {
            KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(noweKonto, ukladBRDAO);
            if (kp == null) {
                kp = new KontopozycjaZapis();
                kp.setPozycjaWn(kpo.getPozycjaWn());
                kp.setPozycjaMa(kpo.getPozycjaMa());
                kp.setStronaWn(kpo.getStronaWn());
                kp.setStronaMa(kpo.getStronaMa());
                kp.setSyntetykaanalityka(wersja);
                kp.setKontoID(noweKonto);
                kp.setUkladBR(kpo.getUkladBR());
                kp.setWynik0bilans1(kpo.isWynik0bilans1());
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
                kp.setWynik0bilans1(kpo.isWynik0bilans1());
                kontopozycjaZapisDAO.edit(kp);
                noweKonto.setKontopozycjaID(new KontopozycjaBiezaca(kp));
                kontoDAOfk.edit(noweKonto);
            }
        } catch (Exception e) {
            E.e(e);
            KontopozycjaZapis kp = kontopozycjaZapisDAO.findByKonto(noweKonto, ukladBRDAO);
            if (kp != null) {
                noweKonto.setKontopozycjaID(new KontopozycjaBiezaca(kp));
                kontoDAOfk.edit(noweKonto);
            }
        }
        
    }

    public static Konto wyszukajmacierzyste(WpisView wpisView, KontoDAOfk kontoDAOfk, String pelnynumer) {
        Konto konto = kontoDAOfk.findKonto(pelnynumer, wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        return konto;
    }
    
    public static String modyfikujnrslownik(String pelnynumer) {
        String nrmacierzystego = pelnynumer;
        char[] l = pelnynumer.toCharArray();
        int i = 0;
        for (char p : l) {
            i++;
            if (Character.isLetter(p)) {
                nrmacierzystego = pelnynumer.substring(0, i-1);
                break;
            }
        }
        if (nrmacierzystego.endsWith("-")) {
            nrmacierzystego = nrmacierzystego.substring(0, nrmacierzystego.length()-1);
        }
        return nrmacierzystego;
    }

    public static String modyfikujnranalityczne(String pelnynumer) {
        String nrmacierzystego = pelnynumer;
        char[] l = pelnynumer.toCharArray();
        int i = 0;
        boolean niemaliter = true;
        for (char p : l) {
            i++;
            if (Character.isLetter(p)) {
                nrmacierzystego = pelnynumer.substring(0, i-1);
                niemaliter = false;
                break;
            }
        }
        if (nrmacierzystego.endsWith("-")) {
            nrmacierzystego = nrmacierzystego.substring(0, nrmacierzystego.length()-1);
        }
        if (niemaliter) {
            String[] pola = nrmacierzystego.split("-");
            String koncowka = pola[pola.length-1];
            int indexkoncowy = nrmacierzystego.length()-koncowka.length()-1;
            nrmacierzystego = nrmacierzystego.substring(0, indexkoncowy);
        }
        return nrmacierzystego;
    }
    
    public static void przyporzadkujBilans_kontoszczegolne(String wybranapozycja, Konto konto, UkladBR uklad, KontoDAOfk kontoDAO, WpisView wpisView, boolean wzorcowy, String wnmaPrzypisywanieKont, boolean aktywa0pasywa1, String rodzajkonta) {
        KontopozycjaBiezaca kp = konto.getKontopozycjaID() != null ? konto.getKontopozycjaID() : new KontopozycjaBiezaca();
        if (wnmaPrzypisywanieKont.equals("wn")) {
            if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                kontopozycjaBiezacaWn(kp,wybranapozycja, konto, uklad, "0", rodzajkonta);
            } else {
                kontopozycjaBiezacaWn(kp,wybranapozycja, konto, uklad, "1", rodzajkonta);
            }
        } else {
            if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
                kontopozycjaBiezacaMa(kp,wybranapozycja, konto, uklad, "0", rodzajkonta);
            } else {
                kontopozycjaBiezacaMa(kp,wybranapozycja, konto, uklad, "1", rodzajkonta);
            }
        }
        kp.setWynik0bilans1(true);
        konto.setKontopozycjaID(kp);
        kontoDAO.edit(konto);
        //czesc nanoszaca informacje na potomku
        if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, uklad.getPodatnik(), wnmaPrzypisywanieKont, Integer.parseInt(uklad.getRok()));
        }
        //czesc nanoszaca informacje na macierzyste
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.oznaczmacierzyste(konto, uklad, kontoDAO, uklad.getPodatnik(), Integer.parseInt(uklad.getRok()), true);
        }
    }
    
    
    public static void przyporzadkujBilans_kontozwykle(String wybranapozycja, Konto konto, UkladBR uklad, KontoDAOfk kontoDAO, String podatnik, String wnmaPrzypisywanieKont, boolean aktywa0pasywa1) {
        KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
        if (aktywa0pasywa1 == false) {//jest informacja w jaqkim miejscu winiec byc czy po aktywach czy po pasywach
            kontopozycjaBiezacaWn(kp,wybranapozycja, konto, uklad, "0", "zwykłe");
            kontopozycjaBiezacaMa(kp,wybranapozycja, konto, uklad, "0", "zwykłe");
        } else {
            kontopozycjaBiezacaWn(kp,wybranapozycja, konto, uklad, "1", "zwykłe");
            kontopozycjaBiezacaMa(kp,wybranapozycja, konto, uklad, "1", "zwykłe");
        }
        kp.setWynik0bilans1(true);
        konto.setKontopozycjaID(kp);
        kontoDAO.edit(konto);
        //czesc nanoszaca informacje na potomku
        if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, podatnik, "bilans", Integer.parseInt(uklad.getRok()));
        }
        //czesc nanoszaca informacje na macierzyste
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.oznaczmacierzyste(konto, uklad, kontoDAO, podatnik, Integer.parseInt(uklad.getRok()), true);
        }
    }

    public static void przyporzadkujRZiS_kontozwykle(String wybranapozycja, Konto konto, UkladBR uklad, KontoDAOfk kontoDAO, String podatnik, String wnmaPrzypisywanieKont) {
        KontopozycjaBiezaca kp = new KontopozycjaBiezaca();
        kontopozycjaBiezacaWn(kp,wybranapozycja, konto, uklad, "99", "wynikowe");
        kontopozycjaBiezacaMa(kp,wybranapozycja, konto, uklad, "99", "wynikowe");
        konto.setKontopozycjaID(kp);
        kontoDAO.edit(konto);
        //czesc nanoszaca informacje na potomku
        if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkowZwykle(konto.getPelnynumer(), kp, kontoDAO, podatnik, "wynik", Integer.parseInt(uklad.getRok()));
        }
        //czesc nanoszaca informacje na macierzyste
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.oznaczmacierzyste(konto, uklad, kontoDAO, podatnik, Integer.parseInt(uklad.getRok()), false);
        }
    }
    
    
    public static void przyporzadkujRZiS_kontoszczegolne(String wybranapozycja, Konto konto, UkladBR uklad, KontoDAOfk kontoDAO, String podatnik, String wnmaPrzypisywanieKont) {
        //to jest niezbedne dla kont specjalnych
        KontopozycjaBiezaca kp = null;
        if (konto.getKontopozycjaID() != null) {
            kp = konto.getKontopozycjaID();
        } else {
            kp = new KontopozycjaBiezaca();
            konto.setKontopozycjaID(kp);
        }
        if (wnmaPrzypisywanieKont.equals("wn")) {
            kontopozycjaBiezacaWn(kp,wybranapozycja, konto, uklad, "88", "szczególne");
        } else {
            kontopozycjaBiezacaMa(kp,wybranapozycja, konto, uklad, "88", "szczególne");
        }
        kontoDAO.edit(konto);
         if (konto.isMapotomkow() == true) {
            PozycjaRZiSFKBean.przyporzadkujpotkomkowRozrachunkowe(konto, kp, kontoDAO, podatnik, wnmaPrzypisywanieKont, Integer.parseInt(uklad.getRok()));
        }
        //czesc nanoszaca informacje na macierzyste
        if (konto.getMacierzysty() > 0) {
            PozycjaRZiSFKBean.oznaczmacierzyste(konto, uklad, kontoDAO, podatnik, Integer.parseInt(uklad.getRok()), false);
        }
    }
    
    private static void kontopozycjaBiezacaWn (KontopozycjaBiezaca kp,String wybranapozycja, Konto konto, UkladBR uklad, String numer, String wynikoweszczegolne) {
        kp.setPozycjaWn(wybranapozycja);
        kp.setStronaWn(numer);
        kp.setSyntetykaanalityka(wynikoweszczegolne);
        if (kp.getKontoID() == null) {
            kp.setKontoID(konto);
            kp.setUkladBR(uklad);
        }
    }
    
     private static void kontopozycjaBiezacaMa (KontopozycjaBiezaca kp,String wybranapozycja, Konto konto, UkladBR uklad, String numer, String wynikoweszczegolne) {
        kp.setPozycjaMa(wybranapozycja);
        kp.setStronaMa(numer);
        kp.setSyntetykaanalityka(wynikoweszczegolne);
        if (kp.getKontoID() == null) {
            kp.setKontoID(konto);
            kp.setUkladBR(uklad);
        }
    }
    
    public static Konto findKonto860(List<Konto> plankont) {
        for (Konto p : plankont) {
            if (p.getPelnynumer().equals("860")) {
                return p;
            }
        }
        return null;
    }
    

    public static void main(String[] args) {
        String pelnynumer = "710-1-dd";
        String nowynumer = pelnynumer;
        char[] l = pelnynumer.toCharArray();
        int i = 0;
        for (char p : l) {
            i++;
            if (Character.isLetter(p)) {
                System.out.println(" "+i);
                nowynumer = pelnynumer.substring(0, i-1);
                break;
            }
        }
        if (nowynumer.endsWith("-")) {
            nowynumer = nowynumer.substring(0, nowynumer.length()-1);
        }
        System.out.println(" "+nowynumer);
//        String[] w = str.split("\\s+");
//        System.out.println("w "+w[0]);
//        String koncowka = w[w.length-1];
//        if (w.length ==1) {
//            String[] pola = str.split("-");
//            koncowka = pola[pola.length-1];
//        }
//        int indexkoncowy = str.length()-koncowka.length()-1;
//        String nowynumer = str.substring(0, indexkoncowy);
//        System.out.println("w "+nowynumer);
    }
}

