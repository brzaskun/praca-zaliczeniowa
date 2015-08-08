/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import converter.RomNumb;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import embeddablefk.KontoKwota;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class PozycjaRZiSFKBean {
    
    public static void wyluskajNieprzyporzadkowaneAnalitykiRZiS(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, WpisView wpisView, boolean wzorcowy, Integer rok) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getKontopozycjaID() != null) {
                if (p.getKontopozycjaID().getPozycjaWn() == null || p.getKontopozycjaID().getPozycjaMa() == null) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                } else if (p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    if (wzorcowy) {
                        potomki = kontoDAO.findKontaPotomneWzorcowy(rok, p.getPelnynumer());
                    } else {
                        potomki = kontoDAO.findKontaPotomnePodatnik(wpisView, p.getPelnynumer());
                    }
                    wyluskajNieprzyporzadkowaneAnalitykiRZiS(potomki, wykazkont, kontoDAO, wpisView, wzorcowy, rok);
                }
            } else {
                    wykazkont.add(p);
            }
        }
    }
    
     public static void wyluskajNieprzyporzadkowaneAnalitykiBilans(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, WpisView wpisView, boolean aktywa0pasywa1, boolean wzorcowy, Integer rok) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getKontopozycjaID() != null) {
                if (p.getZwyklerozrachszczegolne().equals("szczególne") && (p.getKontopozycjaID().getPozycjaWn() != null || p.getKontopozycjaID().getPozycjaMa() != null)) {
                    if (!wykazkont.contains(p) && !(p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaMa() != null)) {
                        wykazkont.add(p);
                    }
                    //tu szukamy przyporzadkowanych analitych
                } else if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    if (wzorcowy) {
                        potomki = kontoDAO.findKontaPotomneWzorcowy(rok, p.getPelnynumer());
                    } else {
                        potomki = kontoDAO.findKontaPotomnePodatnik(wpisView, p.getPelnynumer());
                    }
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalitykiBilans(potomki, wykazkont, kontoDAO, wpisView, aktywa0pasywa1, wzorcowy, rok);
                    }
                } else if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    if (wzorcowy) {
                        potomki = kontoDAO.findKontaPotomneWzorcowy(rok, p.getPelnynumer());
                    } else {
                        potomki = kontoDAO.findKontaPotomnePodatnik(wpisView, p.getPelnynumer());
                    }
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalitykiBilans(potomki, wykazkont, kontoDAO, wpisView, aktywa0pasywa1, wzorcowy, rok);
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
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
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
    
    public static void ustawRootaBilansNowy(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilansNowy(zapisy, plankont, aktywapasywa);
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
    
    public static void naniesZachowanePozycjeNaKonta(KontoDAOfk kontoDAO, KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBR uklad, WpisView wpisView, boolean wzorcowy, String bilansowewynikowe) {
        List<KontopozycjaZapis> kontopozycja = new ArrayList<>();
        if (bilansowewynikowe.equals("wynikowe")) {
            kontopozycja.addAll(kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad,"wynikowe"));
        } else {
            kontopozycja.addAll(kontopozycjaZapisDAO.findKontaPozycjaBiezacaPodatnikUklad(uklad,"bilansowe"));
        }
        List<Konto> l = new ArrayList<>();
        for (KontopozycjaZapis p : kontopozycja) {
            if (!p.getSyntetykaanalityka().equals("syntetyka")) {
                System.out.println("d");
            }
            Konto konto = p.getKontoID();
            konto.setKontopozycjaID(new KontopozycjaBiezaca(p));
            l.add(konto);
            try {
                kontoDAO.edit(konto);
            } catch (Exception e) {
                E.e(e);
            }
        }
        List<Konto> konta = kontoDAO.findWszystkieKontaWzorcowy(wpisView);
        List<Konto> konta2 = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
    }
    
    public static List<Konto> wyszukajprzyporzadkowane(KontoDAOfk kontoDAO, String pozycja, WpisView wpisView, boolean aktywa0pasywa1, boolean wzorcowy, UkladBR uklad) {
        List<Konto> przyporzadkowane = null;
        if (wzorcowy) {
            przyporzadkowane = kontoDAO.findKontaPrzyporzadkowaneWzorcowy(pozycja, "wynikowe", Integer.parseInt(uklad.getRok()), aktywa0pasywa1);
        } else {
            przyporzadkowane = kontoDAO.findKontaPrzyporzadkowane(pozycja, "wynikowe", wpisView, aktywa0pasywa1);
        }
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        if (przyporzadkowane != null) {
            for (Konto p : przyporzadkowane) {
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
        }
        return returnlist;

    }
    
    public static List<Konto> wyszukajprzyporzadkowaneB(KontoDAOfk kontoDAO, String pozycja,  WpisView wpisView, boolean aktywa0pasywa1, boolean wzorcowy, UkladBR uklad) {
        List<Konto> przyporzadkowane = null;
        if (wzorcowy) {
            przyporzadkowane = kontoDAO.findKontaPrzyporzadkowaneWzorcowy(pozycja, "bilansowe", Integer.parseInt(uklad.getRok()), aktywa0pasywa1);
        } else {
            przyporzadkowane = kontoDAO.findKontaPrzyporzadkowane(pozycja, "bilansowe", wpisView, aktywa0pasywa1);
        }
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : przyporzadkowane) {
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
    
    public static void wyszukajprzyporzadkowaneBLista(KontoDAOfk kontoDAO, PozycjaRZiSBilans pozycja, PozycjaBilansDAO pozycjaBilansDAO,  WpisView wpisView, boolean aktywa0pasywa1, boolean wzorcowy, UkladBR uklad) {
        List<Konto> lista = new ArrayList<>();
        if (wzorcowy) {
            lista = kontoDAO.findKontaPrzyporzadkowaneWzorcowy(pozycja.getPozycjaString(), "bilansowe", Integer.parseInt(uklad.getRok()), aktywa0pasywa1);
        } else {
            lista = kontoDAO.findKontaPrzyporzadkowane(pozycja.getPozycjaString(), "bilansowe", wpisView, aktywa0pasywa1);
        }
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
    
    public static void wyszukajprzyporzadkowaneRLista(KontoDAOfk kontoDAO, PozycjaRZiSBilans pozycja, PozycjaRZiSDAO pozycjaRZiSDAO, WpisView wpisView, boolean wzorcowy, UkladBR uklad) {
        List<Konto> lista = new ArrayList<>();
        if (wzorcowy) {
            lista = kontoDAO.findKontaPrzyporzadkowaneWzorcowy(pozycja.getPozycjaString(), "wynikowe", Integer.parseInt(uklad.getRok()), wzorcowy);
        } else {
            lista = kontoDAO.findKontaPrzyporzadkowane(pozycja.getPozycjaString(), "wynikowe", wpisView, wzorcowy);
        }
        if (lista.size() > 1) {
            System.out.println("jest lista");
        }
        List<KontoKwota> kontokwotalist = new ArrayList<>();
        if (lista != null) {
            for (Konto p : lista) {
                if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    KontoKwota t = new KontoKwota(p,0.0);
                    kontokwotalist.add(t);
                }
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

    public static void odznaczmacierzyste(String macierzyste, String kontoanalizowane, KontoDAOfk kontoDAO, WpisView wpisView, boolean wzorcowy, Integer rok) {
        List<Konto> siostry = null;
        if (wzorcowy) {
            siostry = kontoDAO.findKontaPotomneWzorcowy(rok, macierzyste);
        } else {
            siostry = kontoDAO.findKontaPotomnePodatnik(wpisView, macierzyste);
        }
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
                    odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, wpisView, wzorcowy, rok);
                }
            }
        }
    }
    
    public static void oznaczmacierzyste(Konto dziecko, KontopozycjaBiezaca kp, UkladBR uklad, KontoDAOfk kontoDAO, WpisView wpisView, boolean wzorcowy) {
        Konto kontomacierzyste = null;
        if (wzorcowy) {
            kontomacierzyste = kontoDAO.findKontoWzorcowy(dziecko.getMacierzyste(), uklad);
        } else {
            kontomacierzyste = kontoDAO.findKonto(dziecko.getMacierzyste(), wpisView);
        }
        if (kontomacierzyste.getKontopozycjaID() == null) {
            kp.setSyntetykaanalityka("analityka");
            kp.setStronaWn(dziecko.getKontopozycjaID().getStronaWn());
            kp.setStronaMa(dziecko.getKontopozycjaID().getStronaMa());
            kp.setKontoID(kontomacierzyste);
            kp.setUkladBR(uklad);
            kontomacierzyste.setKontopozycjaID(kp);
            kontoDAO.edit(kontomacierzyste);
            if (kontomacierzyste.getMacierzysty() > 0) {
                oznaczmacierzyste(kontomacierzyste, kp, uklad, kontoDAO, wpisView, wzorcowy);
            }
        }
    }
    
    public static void przyporzadkujpotkomkowZwykle(String macierzyste, KontopozycjaBiezaca pozycja, KontoDAOfk kontoDAO, WpisView wpisView, boolean wzorcowy, String bilanswynik, Integer rok) {
        List<Konto> potomki = null;
        if (macierzyste.equals("407")) {
            System.out.println("dd");
        }
        if (wzorcowy) {
            potomki = kontoDAO.findKontaPotomneWzorcowy(rok, macierzyste);
        } else {
            potomki = kontoDAO.findKontaPotomnePodatnik(wpisView, macierzyste);
        }
        if (potomki != null) {
            for (Konto p : potomki) {
                if (pozycja == null) {
                    p.setKontopozycjaID(null);
                } else {
                    pozycja.setKontoID(p);
                    pozycja.setSyntetykaanalityka("syntetyka");
                    if (bilanswynik.equals("bilans")) {
                        pozycja.setWynik0bilans1(true);
                    } else {
                        pozycja.setWynik0bilans1(false);
                    }
                    p.setKontopozycjaID(pozycja);

                }
                kontoDAO.edit(p);
                if (p.isMapotomkow() == true) {
                    przyporzadkujpotkomkowZwykle(p.getPelnynumer(), pozycja, kontoDAO, wpisView, wzorcowy, bilanswynik, rok);
                }
            }
        }
    }
    
    public static void przyporzadkujpotkomkowRozrachunkowe(Konto konto, KontopozycjaBiezaca pozycja, KontoDAOfk kontoDAO, WpisView wpisView, String wnma, boolean wzorcowy, Integer rok) {
         List<Konto> potomki = null;
        if (wzorcowy) {
            potomki = kontoDAO.findKontaPotomneWzorcowy(rok, konto.getPelnynumer());
        } else {
            potomki = kontoDAO.findKontaPotomnePodatnik(wpisView, konto.getPelnynumer());
        }
        if (potomki != null) {
            for (Konto p : potomki) {
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
                    przyporzadkujpotkomkowRozrachunkowe(p, pozycja, kontoDAO, wpisView, wnma, wzorcowy, rok);
                }
            }
        }
    }
    
    

    public static void sumujObrotyNaKontach(List<StronaWiersza> zapisy, List<Konto> plankont) {
        for (StronaWiersza p : zapisy) {
             //pobiermay dane z poszczegolnego konta
            double kwotaWn = p.getWnma().equals("Wn") ? p.getKwotaPLN(): 0.0;
            double kwotaMa = p.getWnma().equals("Ma") ? p.getKwotaPLN(): 0.0;
            try {
                System.out.println(p.getKonto().getPelnynumer());
                if (p.getKonto().getPelnynumer().equals("262-1")) {
                    System.out.println("d");
                }
                Konto k = plankont.get(plankont.indexOf(p.getKonto()));
                k.setObrotyWn(k.getObrotyWn()+kwotaWn);
                k.setObrotyMa(k.getObrotyMa()+kwotaMa);
            } catch (Exception e) {
                //System.out.println("Blad " + e.getStackTrace()[0].toString()+" "+e.toString());
            }
            
        }
        //a teraz trzeba podsumowac konta bez obrotow ale z bo no i z obrotami (wyjalem to z gory)
        for (Konto r : plankont) {
            if (r.getPelnynumer().equals("262-1")) {
                System.out.println("d");
            }
            if (r.getObrotyWn() == 0 && r.getObrotyMa() == 0) {
                r.setSaldoWn(r.getBoWn());
                r.setSaldoMa(r.getBoMa());
            } else {
                double sumaObrotyWnBO = r.getObrotyWn();
                double sumaObrotyMaBO = r.getObrotyMa();
                if (sumaObrotyWnBO == sumaObrotyMaBO) {
                    r.setSaldoWn(0.0);
                    r.setSaldoMa(0.0);
                } else {
                    if (sumaObrotyWnBO > sumaObrotyMaBO) {
                        r.setSaldoWn(sumaObrotyWnBO-sumaObrotyMaBO);
                        r.setSaldoMa(0.0);
                    } else {
                        r.setSaldoMa(sumaObrotyMaBO-sumaObrotyWnBO);
                        r.setSaldoWn(0.0);
                    }
                }
            }
        }
    }

    

}
