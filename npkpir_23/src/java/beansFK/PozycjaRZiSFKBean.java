/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import converter.RomNumb;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import embeddablefk.KontoKwota;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontopozycja;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PozycjaRZiSFKBean {
    
    public static void wyluskajNieprzyporzadkowaneAnalityki(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnik) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getKontopozycjaID() != null) {
                if (p.getKontopozycjaID().getPozycjaWn() == null) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                } else if (p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                    wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik);
                }
            } else {
                    wykazkont.add(p);
            }
        }
    }
    
     public static void wyluskajNieprzyporzadkowaneAnalityki(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnik, boolean aktywa0pasywa1) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getKontopozycjaID() != null) {
                if (p.getZwyklerozrachszczegolne().equals("szczególne") && (p.getKontopozycjaID().getPozycjaWn() != null || p.getKontopozycjaID().getPozycjaMa() != null)) {
                    if (!wykazkont.contains(p) && !(p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaMa() != null)) {
                        wykazkont.add(p);
                    }
                    //tu szukamy przyporzadkowanych analitych
                } else if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1);
                    }
                } else if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1);
                    }
                //ta czesc dotyczy rozrachunkowych, to nie bedzie dotyczych zwykłych
                } else if (p.getKontopozycjaID().getPozycjaWn() == null && aktywa0pasywa1 == false) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                } else if (p.getKontopozycjaID().getPozycjaMa() == null && aktywa0pasywa1 == true) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                }

            } else {
               wykazkont.add(p);
            }
        }
    }
     
    public static void ustawRoota(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, List<Konto> plankont) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbers(zapisy, plankont);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaBilans(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilans(plankont, aktywapasywa);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaprojekt(TreeNodeExtended rt, ArrayList<PozycjaRZiSBilans> pz) {
        rt.createTreeNodesForElement(pz);
        rt.expandAll();
    }
    
    public static int ustawLevel(TreeNodeExtended rt, ArrayList<PozycjaRZiSBilans> pozycjeL) {
        return rt.ustaldepthDT(pozycjeL) - 1;
    }
    
    public static void naniesZachowanePozycjeNaKonta(KontoDAOfk kontoDAO, KontopozycjaDAO kontopozycjarzisDAO, UkladBR uklad, WpisView wpisView) {
        List<Konto> kontapobrane = kontoDAO.findWszystkieKontaPodatnika(uklad.getPodatnik(),wpisView.getRokWpisuSt());
        for (Konto p : kontapobrane) {
            p.setKontopozycjaID(null);
            kontoDAO.edit(p);
        }
        List<Kontopozycja> kontopozycjarzis = kontopozycjarzisDAO.findKontaPodatnikUklad(uklad);
        for (Kontopozycja p : kontopozycjarzis) {
            Konto konto = p.getKontoID();
            konto.setKontopozycjaID(p);
            kontoDAO.edit(konto);
        }
    }
    
    public static List<Konto> wyszukajprzyporzadkowane(KontoDAOfk kontoDAO, String pozycja, String podatnik, boolean aktywa0pasywa1) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja, "wynikowe", podatnik, aktywa0pasywa1);
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : lista) {
            if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaWn().equals(pozycja)) {
                if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                     if (!returnlist.contains(p)) {
                        returnlist.add(p);
                     }
                }
            }
            if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getPozycjaMa().equals(pozycja)) {
                if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    if (!returnlist.contains(p)) {
                        returnlist.add(p);
                    }
                }
            }
        }
        return returnlist;

    }
    
    public static List<Konto> wyszukajprzyporzadkowaneB(KontoDAOfk kontoDAO, String pozycja, String podatnik, boolean aktywa0pasywa1) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja, "bilansowe", podatnik, aktywa0pasywa1);
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : lista) {
            if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaWn().equals(pozycja)) {
                if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    if (!returnlist.contains(p)) {
                        returnlist.add(p);
                    }
                }
            }
            if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getPozycjaMa().equals(pozycja)) {
                if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    if (!returnlist.contains(p)) {
                        returnlist.add(p);
                    }
                }
            }
        }
        return returnlist;

    }
    
    public static void wyszukajprzyporzadkowaneBLista(KontoDAOfk kontoDAO, PozycjaRZiSBilans pozycja, PozycjaBilansDAO pozycjaBilansDAO, String podatnik, boolean aktywa0pasywa1) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja.getPozycjaString(), "bilansowe", podatnik, aktywa0pasywa1);
        List<KontoKwota> kontokwotalist = new ArrayList<>();
        for (Konto p : lista) {
            if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                KontoKwota t = new KontoKwota(p,0.0);
                kontokwotalist.add(t);
            }
        }
        pozycja.setPrzyporzadkowanekonta(kontokwotalist);
        pozycjaBilansDAO.edit(pozycja);

    }
    
    public static void wyszukajprzyporzadkowaneRLista(KontoDAOfk kontoDAO, PozycjaRZiSBilans pozycja, PozycjaRZiSDAO pozycjaRZiSDAO, String podatnik, boolean aktywa0pasywa1) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja.getPozycjaString(), "wynikowe", podatnik, aktywa0pasywa1);
        List<KontoKwota> kontokwotalist = new ArrayList<>();
        for (Konto p : lista) {
            if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                KontoKwota t = new KontoKwota(p,0.0);
                kontokwotalist.add(t);
            }
        }
        pozycja.setPrzyporzadkowanekonta(kontokwotalist);
        pozycjaRZiSDAO.edit(pozycja);

    }
    
    public static String zwrocNastepnySymbol(int level) {
        switch (level) {
            case 1:
                return "I";
            case 2:
                return "1";
            case 3:
                return "a";
            case 4:
                return "-(1)";
        }
        return null;
    }
    
    public static String zwrocNastepnySymbol(int level, String pozycjasymbol) {
        switch (level) {
            case 1:
                return RomNumb.romInc(pozycjasymbol);
            case 2:
                return RomNumb.numbInc(pozycjasymbol);
            case 3:
                return RomNumb.alfaInc(pozycjasymbol);
            case 4:
                return RomNumb.otherSign(pozycjasymbol);
        }
        return null;
    }

    public static void odznaczmacierzyste(String macierzyste, String kontoanalizowane, KontoDAOfk kontoDAO, WpisView wpisView) {
        List<Konto> siostry = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), macierzyste);
        if (siostry.size() > 1) {
            boolean sainne = false;
            for (Konto p : siostry) {
                if (p.getKontopozycjaID() != null && !p.getPelnynumer().equals(kontoanalizowane)) {
                    sainne = true;
                }
            }
            if (sainne == false) {
                Konto konto = kontoDAO.findKonto(macierzyste, wpisView);
                konto.setKontopozycjaID(null);
                kontoDAO.edit(konto);
                if (konto.getMacierzysty() > 0) {
                    odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView);
                }
            }
        }
    }
    
    public static void oznaczmacierzyste(Konto dziecko, Kontopozycja kp, UkladBR uklad, KontoDAOfk kontoDAO, WpisView wpisView) {
        Konto kontomacierzyste = kontoDAO.findKonto(dziecko.getMacierzyste(), wpisView);
        if (kontomacierzyste.getKontopozycjaID() == null) {
            kp.setSyntetykaanalityka("analityka");
            kp.setStronaWn(dziecko.getKontopozycjaID().getStronaWn());
            kp.setStronaMa(dziecko.getKontopozycjaID().getStronaMa());
            kp.setKontoID(kontomacierzyste);
            kp.setUkladBR(uklad);
            kontomacierzyste.setKontopozycjaID(kp);
            kontoDAO.edit(kontomacierzyste);
            if (kontomacierzyste.getMacierzysty() > 0) {
                oznaczmacierzyste(kontomacierzyste, kp, uklad, kontoDAO, wpisView);
            }
        }
    }
    
    public static void przyporzadkujpotkomkowZwykle(String konto, Kontopozycja pozycja, KontoDAOfk kontoDAO, String podatnik, KontopozycjaDAO kontopozycjaDAO) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(podatnik, konto);
        for (Konto p : lista) {
            if (pozycja == null) {
                p.setKontopozycjaID(null);
            } else {
                pozycja.setKontoID(p);
                pozycja.setSyntetykaanalityka("syntetyka");
                p.setKontopozycjaID(pozycja);
                
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow() == true) {
                przyporzadkujpotkomkowZwykle(p.getPelnynumer(), pozycja, kontoDAO, podatnik, kontopozycjaDAO);
            }
        }
    }
    
    public static void przyporzadkujpotkomkowRozrachunkowe(Konto konto, Kontopozycja pozycja, KontoDAOfk kontoDAO, String podatnik, String wnma) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(podatnik, konto.getPelnynumer());
        for (Konto p : lista) {
             if (pozycja == null) {
                p.setKontopozycjaID(null);
            } else {
                 if (wnma.equals("wn")) {
                    pozycja.setKontoID(p);
                    pozycja.setStronaWn(konto.getKontopozycjaID().getStronaWn());
                 } else {
                    pozycja.setKontoID(p);
                    pozycja.setStronaMa(konto.getKontopozycjaID().getStronaMa());
                 }
                pozycja.setSyntetykaanalityka("syntetyka");
                p.setKontopozycjaID(pozycja);
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow() == true) {
                przyporzadkujpotkomkowRozrachunkowe(p, pozycja, kontoDAO, podatnik, wnma);
            }
        }
    }
    
    public static void przyporzadkujpotkomkowRozrachunkoweIstniejeKP(Konto konto, Kontopozycja pozycja, KontoDAOfk kontoDAO, String podatnik, String wnma, boolean aktywa0pasywa1) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(podatnik, konto.getPelnynumer());
        for (Konto p : lista) {
             if (pozycja == null) {
                p.setKontopozycjaID(null);
            } else {
                Kontopozycja kp = p.getKontopozycjaID();
                if (wnma.equals("wn")) {
                    kp.setStronaWn(konto.getKontopozycjaID().getStronaWn());
                    kp.setPozycjaWn(pozycja.getPozycjaWn());
                } else {
                    kp.setStronaMa(konto.getKontopozycjaID().getStronaMa());
                    kp.setPozycjaMa(pozycja.getPozycjaMa());
                }
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow() == true) {
                przyporzadkujpotkomkowRozrachunkoweIstniejeKP(p, pozycja, kontoDAO, podatnik, wnma, aktywa0pasywa1);
            }
        }
    }

    public static void sumujObrotyNaKontach(List<StronaWiersza> zapisy, List<Konto> plankont) {
        for (StronaWiersza p : zapisy) {
             //pobiermay dane z poszczegolnego konta
            double kwotaWn = p.getWnma().equals("Wn") ? p.getKwotaPLN(): 0.0;
            double kwotaMa = p.getWnma().equals("Ma") ? p.getKwotaPLN(): 0.0;
            try {
                Konto k = plankont.get(plankont.indexOf(p.getKonto()));
                k.setObrotyWn(k.getObrotyWn()+kwotaWn);
                k.setObrotyMa(k.getObrotyMa()+kwotaMa);
                double sumaObrotyWnBO = k.getObrotyWn()+k.getBoWn();
                double sumaObrotyMaBO = k.getObrotyMa()+k.getBoMa();
                if (sumaObrotyWnBO == sumaObrotyMaBO) {
                    k.setSaldoWn(0.0);
                    k.setSaldoMa(0.0);
                } else {
                    if (sumaObrotyWnBO > sumaObrotyMaBO) {
                        k.setSaldoWn(sumaObrotyWnBO-sumaObrotyMaBO);
                        k.setSaldoMa(0.0);
                    } else {
                        k.setSaldoMa(sumaObrotyMaBO-sumaObrotyWnBO);
                        k.setSaldoWn(0.0);
                    }
                }
            } catch (Exception e) {
                
            }
            
        }
        //a teraz trzeba podsumowac konta bez obrotow ale z bo
        for (Konto r : plankont) {
            if (r.getObrotyWn() == 0 && r.getObrotyMa() == 0) {
                r.setSaldoWn(r.getBoWn());
                r.setSaldoMa(r.getBoMa());
            }
        }
    }

    

}
