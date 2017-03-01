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
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class PozycjaRZiSFKBean {
    
    public static void wyluskajNieprzyporzadkowaneAnalitykiRZiS(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnik, Integer rok) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getKontopozycjaID() != null) {
                if (p.getKontopozycjaID().getPozycjaWn() == null || p.getKontopozycjaID().getPozycjaMa() == null) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                } else if (p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, p.getPelnynumer());
                    wyluskajNieprzyporzadkowaneAnalitykiRZiS(potomki, wykazkont, kontoDAO, podatnik, rok);
                }
            } else {
                    wykazkont.add(p);
            }
        }
    }
    
     public static void wyluskajNieprzyporzadkowaneAnalitykiBilans(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnik, boolean aktywa0pasywa1, Integer rok) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getPelnynumer().equals("201")) {
                System.out.println("");
            }
            if (p.getKontopozycjaID() != null) {
                if (p.getZwyklerozrachszczegolne().equals("szczególne") && (p.getKontopozycjaID().getPozycjaWn() != null || p.getKontopozycjaID().getPozycjaMa() != null)) {
                    if (!wykazkont.contains(p) && !(p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaMa() != null)) {
                        wykazkont.add(p);
                    }
                    //tu szukamy przyporzadkowanych analitych
                } else if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, p.getPelnynumer());
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalitykiBilans(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1, rok);
                    }
                } else if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, p.getPelnynumer());
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalitykiBilans(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1, rok);
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
    
    public static void ustawRootaRokPop(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, List<Konto> plankont, List<StronaWiersza> zapisyRokPop, List<Konto> plankontRokPop) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbers(zapisy, plankont);
        rootL.addNumbersBO(zapisyRokPop, plankont);
        rootL.sumNodes();
        rootL.sumNodesBO();
        rootL.resolveFormulas();
        rootL.resolveFormulasBO();
        rootL.expandAll();
    }
    
    public static void ustawRootaBilans(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilans(plankont, aktywapasywa);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaBilansBOData(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<Konto> plankontBO, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilans(plankont, aktywapasywa);
        rootL.addNumbersBilansBO(plankontBO, aktywapasywa);
        rootL.sumNodes();
        rootL.sumNodesBO();
        rootL.resolveFormulas();
        rootL.resolveFormulasBO();
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
        ArrayList<PozycjaRZiSBilans> pozycjedlaroota = new ArrayList<>();
        pozycjedlaroota.addAll(pz);
        rt.createTreeNodesForElement(pozycjedlaroota);
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
            try {
                if (!p.getSyntetykaanalityka().equals("syntetyka")) {
                    //System.out.println("d");
                }
                Konto konto = p.getKontoID();
                konto.setKontopozycjaID(new KontopozycjaBiezaca(p));
                l.add(konto);
            } catch (Exception e) {
                E.e(e);
            }
        }
        kontoDAO.editList(l);
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
            try {
                if (!p.getKontopozycjaID().getSyntetykaanalityka().equals("syntetyka") && !p.getKontopozycjaID().getSyntetykaanalityka().equals("analityka")) {
                    KontoKwota t = new KontoKwota(p,0.0);
                    kontokwotalist.add(t);
                }
            } catch (Exception e) {
                E.e(e);
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

    public static void odznaczmacierzyste(String macierzyste, String kontoanalizowane, KontoDAOfk kontoDAO, String podatnik, Integer rok) {
        List<Konto> siostry = null;
        siostry = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, macierzyste);
        if (siostry.size() > 1) {
            boolean sainne = false;
            for (Konto p : siostry) {
                if (p.getKontopozycjaID() != null && !p.getPelnynumer().equals(kontoanalizowane)) {
                    sainne = true;
                }
            }
            if (sainne == false) {
                Konto konto = null;
                konto = kontoDAO.findKonto(macierzyste, podatnik, rok);
                konto.setKontopozycjaID(null);
                kontoDAO.edit(konto);
                if (konto.getMacierzysty() > 0) {
                    odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik, rok);
                }
            }
        }
    }
    
    public static void oznaczmacierzyste(Konto dziecko, UkladBR uklad, KontoDAOfk kontoDAO, String podatnik, Integer rok, boolean wynik0bilans1) {
        Konto kontomacierzyste = null;
        kontomacierzyste = kontoDAO.findKonto(dziecko.getMacierzyste(), podatnik, rok);
        KontopozycjaBiezaca kp = kontomacierzyste.getKontopozycjaID() != null ? kontomacierzyste.getKontopozycjaID() : new KontopozycjaBiezaca();
        if (kp.getIdKP() == null) {
            kp.setSyntetykaanalityka("analityka");
            kp.setStronaWn(dziecko.getKontopozycjaID().getStronaWn());
            kp.setStronaMa(dziecko.getKontopozycjaID().getStronaMa());
            kp.setPozycjaWn(dziecko.getKontopozycjaID().getPozycjaWn());
            kp.setPozycjaMa(dziecko.getKontopozycjaID().getPozycjaMa());
            kp.setKontoID(kontomacierzyste);
            kp.setWynik0bilans1(wynik0bilans1);
            kp.setUkladBR(uklad);
            kontomacierzyste.setKontopozycjaID(kp);
            kontoDAO.edit(kontomacierzyste);
            if (kontomacierzyste.getMacierzysty() > 0) {
                oznaczmacierzyste(kontomacierzyste, uklad, kontoDAO, podatnik, rok, wynik0bilans1);
            }
        } else {
            kp.setSyntetykaanalityka("analityka");
            kp.setStronaWn(dziecko.getKontopozycjaID().getStronaWn());
            kp.setStronaMa(dziecko.getKontopozycjaID().getStronaMa());
            kp.setPozycjaWn(dziecko.getKontopozycjaID().getPozycjaWn());
            kp.setPozycjaMa(dziecko.getKontopozycjaID().getPozycjaMa());
            kontoDAO.edit(kontomacierzyste);
            if (kontomacierzyste.getMacierzysty() > 0) {
                oznaczmacierzyste(kontomacierzyste, uklad, kontoDAO, podatnik, rok, wynik0bilans1);
            }
        }
    }
    
    public static void przyporzadkujpotkomkowZwykle(String macierzyste, KontopozycjaBiezaca pozycja, KontoDAOfk kontoDAO, String podatnik, String bilanswynik, Integer rok) {
        List<Konto> potomki = null;
        if (macierzyste.equals("407")) {
            System.out.println("dd");
        }
        potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, macierzyste);
        if (potomki != null) {
            for (Konto p : potomki) {
                if (pozycja == null) {
                    p.setKontopozycjaID(null);
                } else {
                    KontopozycjaBiezaca kp = p.getKontopozycjaID() != null ? p.getKontopozycjaID() : new KontopozycjaBiezaca();
                    if (kp.getIdKP() == null) {
                        kp.setKontoID(p);
                        kp.setSyntetykaanalityka("syntetyka");
                        kp.setUkladBR(pozycja.getUkladBR());
                        if (bilanswynik.equals("bilans")) {
                            kp.setWynik0bilans1(true);
                        } else {
                            kp.setWynik0bilans1(false);
                        }
                        p.setKontopozycjaID(kp);
                    }
                    kp.setPozycjaWn(pozycja.getPozycjaWn());
                    kp.setPozycjaMa(pozycja.getPozycjaMa());
                    kp.setStronaWn(pozycja.getStronaWn());
                    kp.setStronaMa(pozycja.getStronaMa());
                }
                kontoDAO.edit(p);
                if (p.isMapotomkow() == true) {
                    przyporzadkujpotkomkowZwykle(p.getPelnynumer(), pozycja, kontoDAO, podatnik, bilanswynik, rok);
                }
            }
        }
    }
    
    public static void przyporzadkujpotkomkowRozrachunkowe(Konto konto, KontopozycjaBiezaca pozycja, KontoDAOfk kontoDAO,  String podatnik, String wnma, Integer rok) {
        List<Konto> potomki = null;
        potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, konto.getPelnynumer());
        if (potomki != null) {
            for (Konto p : potomki) {
                try {
                    p.setZwyklerozrachszczegolne(konto.getZwyklerozrachszczegolne());
                    if (pozycja == null) {
                       p.setKontopozycjaID(null);
                   } else {
                        KontopozycjaBiezaca kp = p.getKontopozycjaID() != null ? p.getKontopozycjaID() : new KontopozycjaBiezaca();
                        if (kp.getIdKP() == null) {
                           kp.setKontoID(p);
                           kp.setSyntetykaanalityka("syntetyka");
                           kp.setWynik0bilans1(konto.getKontopozycjaID().isWynik0bilans1());
                           kp.setUkladBR(konto.getKontopozycjaID().getUkladBR());
                       } 
                        if (wnma.equals("wn")) {
                           kp.setPozycjaWn(konto.getKontopozycjaID().getPozycjaWn());
                           kp.setStronaWn(konto.getKontopozycjaID().getStronaWn());
                        } else if (wnma.equals("ma")){
                           kp.setPozycjaMa(konto.getKontopozycjaID().getPozycjaMa());
                           kp.setStronaMa(konto.getKontopozycjaID().getStronaMa());
                        } else {
                           kp.setKontoID(p);
                           kp.setPozycjaWn(konto.getKontopozycjaID().getPozycjaWn());
                           kp.setPozycjaMa(konto.getKontopozycjaID().getPozycjaMa());
                           kp.setStronaWn(konto.getKontopozycjaID().getStronaWn());
                           kp.setStronaMa(konto.getKontopozycjaID().getStronaMa());
                        }
                       kp.setKontoID(p);
                       kp.setSyntetykaanalityka("syntetyka");
                       p.setKontopozycjaID(kp);
                   }
                    kontoDAO.edit(p);
                } catch (Exception e) {
                    E.e(e);
                }
                if (p.isMapotomkow() == true) {
                    przyporzadkujpotkomkowRozrachunkowe(p, pozycja, kontoDAO, podatnik, wnma, rok);
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
                if (p.getKonto().getPelnynumer().equals("220-2")) {
                    System.out.println("PozycjaRZiSFKBean.sumujObrotyNaKontach");
                }
                Konto k = plankont.get(plankont.indexOf(p.getKonto()));
                k.setObrotyWn(k.getObrotyWn()+kwotaWn);
                k.setObrotyMa(k.getObrotyMa()+kwotaMa);
            } catch (Exception e) {
                E.e(e);
                System.out.println("Blad sumujObrotyNaKontach");
            }
            
        }
        //a teraz trzeba podsumowac konta bez obrotow ale z bo no i z obrotami (wyjalem to z gory)
        for (Konto r : plankont) {
            if (r.getBilansowewynikowe().equals("bilansowe")) {
                if (r.getPelnynumer().equals("201-2-8")) {
                    System.out.println("d");
                }
//                if (r.getObrotyWn() == 0 && r.getObrotyMa() == 0) {
//                    r.setSaldoWn(r.getBoWn());
//                    r.setSaldoMa(r.getBoMa());
//                } else {
                    double sumaObrotyWnBO = r.getObrotyWn() + r.getBoWn();
                    double sumaObrotyMaBO = r.getObrotyMa() + r.getBoMa();
                    if (sumaObrotyWnBO == sumaObrotyMaBO) {
                        r.setSaldoWn(0.0);
                        r.setSaldoMa(0.0);
                    } else {
                        if (sumaObrotyWnBO > sumaObrotyMaBO) {
                            r.setSaldoWn(Z.z(sumaObrotyWnBO-sumaObrotyMaBO));
                            r.setSaldoMa(0.0);
                        } else {
                            r.setSaldoMa(Z.z(sumaObrotyMaBO-sumaObrotyWnBO));
                            r.setSaldoWn(0.0);
                        }
                    }
                //}
            }
        }
    }

    

}
