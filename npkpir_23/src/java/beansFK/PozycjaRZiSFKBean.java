/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import converter.RomNumb;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddablefk.TreeNodeExtended;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import msg.Msg;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
public class PozycjaRZiSFKBean {
    
    public static void wyluskajNieprzyporzadkowaneAnalitykiRZiS(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, Podatnik podatnik, Integer rok) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getPozycjaWn()!=null || p.getPozycjaMa()!=null) {
                if (p.getPozycjaWn() == null || p.getPozycjaMa() == null) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                } else if (p.getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, p);
                    wyluskajNieprzyporzadkowaneAnalitykiRZiS(potomki, wykazkont, kontoDAO, podatnik, rok);
                }
            } else {
                    wykazkont.add(p);
            }
        }
    }
    
     public static void wyluskajNieprzyporzadkowaneAnalitykiBilans(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, Podatnik podatnik, boolean aktywa0pasywa1, Integer rok) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getPelnynumer().equals("010")) {
                System.out.println("");
            }
            if (p.getPozycjaWn()!=null || p.getPozycjaMa()!=null) {
                if (p.getZwyklerozrachszczegolne().equals("szczególne") && ((p.getPozycjaWn() != null || p.getPozycjaMa() != null))) {
                    if (!wykazkont.contains(p) && !(p.getPozycjaWn() != null && p.getPozycjaMa() != null)) {
                        wykazkont.add(p);
                    }
                    //tu szukamy przyporzadkowanych analitych
                } else if (p.getPozycjaWn() != null && p.getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, p);
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalitykiBilans(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1, rok);
                    }
                } else if (p.getPozycjaMa() != null && p.getSyntetykaanalityka().equals("analityka")) {
                    List<Konto> potomki = null;
                    potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, rok, p);
                    for (Konto r : potomki) {
                        wyluskajNieprzyporzadkowaneAnalitykiBilans(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1, rok);
                    }
                //ta czesc dotyczy rozrachunkowych, to nie bedzie dotyczych zwykłych
                } else if (p.getPozycjaWn() == null && aktywa0pasywa1 == false) {
                    if (!wykazkont.contains(p)) {
                        wykazkont.add(p);
                    }
                } else if (p.getPozycjaMa() == null && aktywa0pasywa1 == true) {
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
     
    public static void ustawRoota(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbers(zapisy);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaNar(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, String mckoncowy) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersNar(zapisy, mckoncowy);
        rootL.sumNodesNar(mckoncowy);
        rootL.resolveFormulasNar(mckoncowy);
        rootL.expandAll();
    }
    
    public static void ustawRootaSlot(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, String kolumna) throws Exception {
        if (!pozycjeL.isEmpty()) {
            rootL.createTreeNodesForElement(pozycjeL);
        }
        rootL.addNumbersSlot(zapisy, kolumna);
        rootL.sumNodesSlot(kolumna);
        rootL.resolveFormulasSlot(kolumna);
        rootL.expandAll();
    }
    
    public static void ustawRootaRokPop(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, List<StronaWiersza> zapisyRokPop) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbers(zapisy);
        rootL.addNumbersBO(zapisyRokPop);
        rootL.sumNodes();
        rootL.sumNodesBO();
        rootL.resolveFormulas();
        rootL.resolveFormulasBO();
        rootL.expandAll();
    }
    
    public static void ustawRootaBilans(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilans(plankont, aktywapasywa);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaBilansBOData(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<Konto> plankontBO, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilans(plankont, aktywapasywa);
        rootL.addNumbersBilansBO(plankontBO, aktywapasywa);
        rootL.sumNodes();
        rootL.sumNodesBO();
        rootL.resolveFormulas();
        rootL.resolveFormulasBO();
        rootL.expandAll();
    }
    
    public static void ustawRootaBilansNowy(TreeNodeExtended rootL, List<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, List<Konto> plankont, String aktywapasywa) throws Exception {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbersBilansNowy(zapisy, plankont, aktywapasywa);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaprojekt(TreeNodeExtended rt, List<PozycjaRZiSBilans> pz) {
        List<PozycjaRZiSBilans> pozycjedlaroota = Collections.synchronizedList(new ArrayList<>());
        pozycjedlaroota.addAll(pz);
        rt.createTreeNodesForElement(pozycjedlaroota);
        rt.expandAll();
    }
    
    public static int ustawLevel(TreeNodeExtended rt, List<PozycjaRZiSBilans> pozycjeL) {
        return rt.ustaldepthDT(pozycjeL) - 1;
    }
    
    public static void naniesZachowanePozycjeNaKonta(KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBR uklad, String bilansowewynikowe) {
        try {
            List<KontopozycjaZapis> kontopozycja = Collections.synchronizedList(new ArrayList<>());
            if (bilansowewynikowe.equals("wynikowe")) {
                kontopozycja.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(uklad,"wynikowe"));
            } else {
                kontopozycja.addAll(kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(uklad,"bilansowe"));
            }
            List<Konto> l = Collections.synchronizedList(new ArrayList<>());
            kontopozycja.stream().forEach((p) -> {
                try {
                    Konto konto = p.getKontoID();
                    konto.naniesPozycje(p);
                    l.add(konto);
                } catch (Exception e) {
                    E.e(e);
                }
            });
            kontoDAO.editList(l);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public static List<Konto> wyszukajprzyporzadkowane(KontoDAOfk kontoDAO, String pozycja,boolean aktywa0pasywa1, UkladBR uklad) {
        List<Konto> przyporzadkowane = null;
        przyporzadkowane = kontoDAO.findKontaPrzyporzadkowane(pozycja, "wynikowe", uklad.getPodatnik(), Integer.parseInt(uklad.getRok()), aktywa0pasywa1);
        List<Konto> returnlist = Collections.synchronizedList(new ArrayList<>());
        int level = 0;
        if (przyporzadkowane != null) {
            for (Konto p : przyporzadkowane) {
                if (p.getPozycjaWn() != null && p.getPozycjaWn().equals(pozycja)) {
                    if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                         if (!returnlist.contains(p)) {
                            returnlist.add(p);
                         }
                    }
                }
                if (p.getPozycjaMa() != null && p.getPozycjaMa().equals(pozycja)) {
                    if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                        if (!returnlist.contains(p)) {
                            returnlist.add(p);
                        }
                    }
                }
            }
        }
        return returnlist;

    }
    
    public static List<Konto> wyszukajprzyporzadkowaneB(KontoDAOfk kontoDAO, String pozycja,  boolean aktywa0pasywa1, UkladBR uklad) {
        List<Konto> przyporzadkowane = null;
        przyporzadkowane = kontoDAO.findKontaPrzyporzadkowane(pozycja, "bilansowe", uklad.getPodatnik(), Integer.parseInt(uklad.getRok()), aktywa0pasywa1);
        List<Konto> returnlist = Collections.synchronizedList(new ArrayList<>());
        int level = 0;
        for (Konto p : przyporzadkowane) {
            if (p.getPozycjaWn() != null && p.getPozycjaWn().equals(pozycja)) {
                if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                    if (!returnlist.contains(p)) {
                        returnlist.add(p);
                    }
                }
            }
            if (p.getPozycjaMa() != null && p.getPozycjaMa().equals(pozycja)) {
                if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                    if (!returnlist.contains(p)) {
                        returnlist.add(p);
                    }
                }
            }
        }
        return returnlist;

    }
    
    public static void wyszukajprzyporzadkowaneBLista(List<Konto> lista, PozycjaRZiSBilans pozycja, WpisView wpisView, boolean aktywa0pasywa1) {
        String strona = aktywa0pasywa1 ? "1" : "0";
        List<Konto> kontokwotalist = Collections.synchronizedList(new ArrayList<>());
        if (pozycja.getPozycjaString().equals("A.I")) {
            System.out.println("");
        }
        for (Konto p : lista) {
            try {
                    if (aktywa0pasywa1 && pozycja.isPrzychod0koszt1()) {
                    if (p.getPozycjaMa()!=null && p.getPozycjaMa().equals(pozycja.getPozycjaString()) && p.getStronaMa().equals(strona)) {
                        if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                            p.setKwota(0.0);
                            kontokwotalist.add(p);
                        }
                    }
                } else if (!pozycja.isPrzychod0koszt1()){
                    if (p.getPozycjaWn()!=null && p.getPozycjaWn().equals(pozycja.getPozycjaString()) && p.getStronaWn().equals(strona)) {
                        if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                            p.setKwota(0.0);
                            kontokwotalist.add(p);
                        }
                    }
                }
            } catch (Exception e) {
                E.e(e);
            }
        }
        pozycja.setPrzyporzadkowanekonta(kontokwotalist);
    }
    
    public static void wyszukajprzyporzadkowaneRLista(List<Konto> lista, PozycjaRZiSBilans pozycja) {
        List<Konto> kontokwotalist = Collections.synchronizedList(new ArrayList<>());
        if (lista != null) {
            lista.parallelStream().forEach((p)->{
                if ((p.getPozycjaWn()!=null && p.getPozycjaWn().equals(pozycja.getPozycjaString())) || (p.getPozycjaMa()!=null && p.getPozycjaMa().equals(pozycja.getPozycjaString()))){ 
                    if (!p.getSyntetykaanalityka().equals("syntetyka") && !p.getSyntetykaanalityka().equals("analityka")) {
                        p.setKwota(0.0);
                        kontokwotalist.add(p);
                    }
                }
            });
        }
        pozycja.setPrzyporzadkowanekonta(kontokwotalist);
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
            case 0:
                return RomNumb.alfaInc(pozycjasymbol);
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
    
    public static String zwrocPoprzedniSymbol(int level, String pozycjasymbol) {
        try {
            switch (level) {
                case 0:
                    return RomNumb.alfaIncBack(pozycjasymbol);
                case 1:
                    return RomNumb.romIncBack(pozycjasymbol);
                case 2:
                    return RomNumb.numbIncBack(pozycjasymbol);
                case 3:
                    return RomNumb.alfaIncBack(pozycjasymbol);
                case 4:
                    return RomNumb.otherSignBack(pozycjasymbol);
            }
        } catch (Exception e){}
        return null;
    }
    
    public static String zwrocPierwszySymbol(int level) {
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

    public static void odznaczmacierzyste(Konto macierzyste, String kontoanalizowane, KontoDAOfk kontoDAO, Podatnik podatnik) {
        List<Konto> siostry = null;
        siostry = kontoDAO.findKontaPotomnePodatnik(podatnik, macierzyste.getRok(), macierzyste);
        if (siostry.size() > 1) {
            boolean sainne = false;
            for (Konto p : siostry) {
                if ((p.getPozycjaWn()==null && p.getPozycjaMa()==null) && !p.getPelnynumer().equals(kontoanalizowane)) {
                    sainne = true;
                }
            }
            if (sainne == false) {
                Konto konto = null;
                konto = kontoDAO.findKonto(macierzyste.getPelnynumer(), podatnik,  macierzyste.getRok());
                konto.czyscPozycje();
                kontoDAO.edit(konto);
                if (konto.getKontomacierzyste() != null) {
                    odznaczmacierzyste(konto.getKontomacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
                }
            }
        }
    }
    
    public static void oznaczmacierzyste(Konto dziecko, KontoDAOfk kontoDAO, Podatnik podatnik, boolean wynik0bilans1) {
        Konto kontomacierzyste = dziecko.getKontomacierzyste();
        kontomacierzyste.kopiujPozycje(dziecko);
        kontomacierzyste.setSyntetykaanalityka("analityka");
        kontoDAO.edit(kontomacierzyste);
        if (kontomacierzyste.getKontomacierzyste() != null) {
            oznaczmacierzyste(kontomacierzyste, kontoDAO, podatnik, wynik0bilans1);
        }
    }
    
    public static void przyporzadkujpotkomkowZwykle(Konto macierzyste, KontoDAOfk kontoDAO, Podatnik podatnik, String bilanswynik) {
        List<Konto> potomki = null;
        potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, macierzyste.getRok(), macierzyste);
        if (potomki != null) {
            for (Konto p : potomki) {
                p.kopiujPozycje(macierzyste);
                p.setSyntetykaanalityka("syntetyka");
                kontoDAO.edit(p);
                if (p.isMapotomkow() == true) {
                    przyporzadkujpotkomkowZwykle(p, kontoDAO, podatnik, bilanswynik);
                }
            }
        }
    }
    
    
    
    public static void przyporzadkujpotkomkowRozrachunkowe(Konto konto, KontoDAOfk kontoDAO,  Podatnik podatnik, String wnma) {
        List<Konto> potomki = null;
        potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, konto.getRok(), konto);
        if (potomki != null) {
            for (Konto p : potomki) {
                try {
                    p.setZwyklerozrachszczegolne(konto.getZwyklerozrachszczegolne());
                    p.kopiujPozycje(konto, wnma);
                    p.setSyntetykaanalityka("syntetyka");
                    kontoDAO.edit(p);
                } catch (Exception e) {
                    E.e(e);
                }
                if (p.isMapotomkow() == true) {
                    przyporzadkujpotkomkowRozrachunkowe(p, kontoDAO, podatnik, wnma);
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
                    //                System.out.println(p.getKonto().getPelnynumer());
                    //                if (p.getKonto().getPelnynumer().equals("220-2")) {
                    //                    System.out.println("PozycjaRZiSFKBean.sumujObrotyNaKontach");
                    //                }
                    Konto k = plankont.get(plankont.indexOf(p.getKonto()));
                    k.setObrotyWn(k.getObrotyWn()+kwotaWn);
                    k.setObrotyMa(k.getObrotyMa()+kwotaMa); 
            } catch (Exception e) {
                E.e(e);
            }
        };
        //a teraz trzeba podsumowac konta bez obrotow ale z bo no i z obrotami (wyjalem to z gory)
        for (Konto r : plankont) {
            if (r.getBilansowewynikowe().equals("bilansowe")) {
//                if (r.getPelnynumer().equals("201-2-8")) {
//                    System.out.println("d");
//                }
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

    public static void pobierzPozycje(List<PozycjaRZiSBilans> pozycje, PozycjaRZiSDAO pozycjaRZiSDAO, UkladBR ukladBR) {
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(ukladBR));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }

    public static void wyczyscKonta(String rb, Podatnik podatnik, String rok, KontoDAOfk kontoDAOfk) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAOfk, listakont);
        } else {
            List<Konto> listakont = kontoDAOfk.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAOfk, listakont);
        }
    }
    
    public static void zmianaukladu(String bilansowewynikowe, UkladBR uklad, UkladBRDAO ukladBRDAO, PozycjaRZiSDAO pozycjaRZiSDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAO, Podatnik podatnik, String rok) {
        try {
            UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
            ArrayList<PozycjaRZiSBilans> pozycje = new ArrayList<>();
            PozycjaRZiSFKBean.pobierzPozycje(pozycje, pozycjaRZiSDAO, uklad);
            PozycjaRZiSFKBean.wyczyscKonta(bilansowewynikowe, podatnik, rok, kontoDAO);
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, uklad, bilansowewynikowe);
        } catch (Exception e) {
            E.e(e);
        }
    }
    
     public static void zmianaukladuwzorcowy(String bilansowewynikowe, UkladBR uklad, UkladBRDAO ukladBRDAO, PozycjaRZiSDAO pozycjaRZiSDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, KontoDAOfk kontoDAO, Podatnik podatnik, String rok) {
        try {
            UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
            ArrayList<PozycjaRZiSBilans> pozycje = new ArrayList<>();
            PozycjaRZiSFKBean.pobierzPozycje(pozycje, pozycjaRZiSDAO, uklad);
            PozycjaRZiSFKBean.wyczyscKonta(bilansowewynikowe, podatnik, rok, kontoDAO);
            PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaZapisDAO, uklad, bilansowewynikowe);
        } catch (Exception e) {
            E.e(e);
        }
    }
     
      public static void skopiujPozycje(String rb, UkladBR ukladdocelowy, UkladBR ukladzrodlowy, Podatnik podatnik, KontoDAOfk kontoDAO, KontopozycjaZapisDAO kontopozycjaZapisDAO, WpisView wpisView, PozycjaBilansDAO pozycjaBilansDAO, PozycjaRZiSDAO pozycjaRZiSDAO) {
        if (rb.equals("r")) {
            wyczyscKonta("wynikowe", podatnik, ukladdocelowy.getRok(), kontoDAO);
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladdocelowy, "wynikowe");
            List<PozycjaRZiS> pozycjedoprzejrzenia = pozycjaRZiSDAO.findRzisuklad(ukladdocelowy);
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(ukladzrodlowy, "wynikowe");
            if (zapisanePOzycjezUkladuWzorcowego.isEmpty()) {
                Msg.msg("e","Brak przyporzadkowania kont RZiS do skopiowania");
            } else {
                List<Konto> kontarokudocelowego = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, ukladdocelowy.getRok());
//                List<KontopozycjaZapis> nowekontopozycjazapis = Collections.synchronizedList(new ArrayList<>());
//                if (podatnik.equals(wpisView.getPodatnikwzorcowy())) {
//                    for (Iterator<KontopozycjaZapis> it = zapisanePOzycjezUkladuWzorcowego.iterator();it.hasNext();) {
//                        KontopozycjaZapis p = it.next();
//                        if (!p.getKontoID().getPodatnik().equals(wpisView.getPodatnikwzorcowy())) {
//                            it.remove();
//                            kontopozycjaZapisDAO.destroy(p);
//                        }
//                    }
//                }
                for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
                    if (czypozycjazawiera(pozycjedoprzejrzenia, p) && p.getKontoID().getRok()==ukladzrodlowy.getRokInt() && p.getKontoID().getPodatnik().equals(ukladzrodlowy.getPodatnik())) {
                        Konto nowekonto = pobierzkontozlisty(kontarokudocelowego, p);
                        if (nowekonto != null && p.getPozycjaWn()!=null && p.getPozycjaMa()!=null) {
                            KontopozycjaZapis kp = new KontopozycjaZapis();
                            kp.setKontoID(nowekonto);
                            kp.setPozycjaWn(p.getPozycjaWn());
                            kp.setPozycjaMa(p.getPozycjaMa());
                            kp.setStronaWn(p.getStronaWn());
                            kp.setStronaMa(p.getStronaMa());
                            kp.setSyntetykaanalityka(p.getSyntetykaanalityka());
                            kp.setUkladBR(ukladdocelowy);
                            kp.setWynik0bilans1(false);
                            kontopozycjaZapisDAO.dodaj(kp);
                        }
                    }
                }
               Msg.msg("Zapamiętano przyporządkowane pozycje RZiS");
            }
        }
        if (rb.equals("b")) {
            wyczyscKonta("bilansowe", podatnik, ukladdocelowy.getRok(), kontoDAO);
            kontopozycjaZapisDAO.usunZapisaneKontoPozycjaPodatnikUklad(ukladdocelowy, "bilansowe");
            List<PozycjaBilans> pozycjedoprzejrzenia = pozycjaBilansDAO.findBilansukladAktywaPasywa(ukladdocelowy);
            List<KontopozycjaZapis> zapisanePOzycjezUkladuWzorcowego = kontopozycjaZapisDAO.findKontaPozycjaZapisPodatnikUklad(ukladzrodlowy, "bilansowe");
            if (zapisanePOzycjezUkladuWzorcowego.isEmpty()) {
                Msg.msg("e","Brak pprzyporzadkowania kont bilansu do skopiowania");
            } else {
                List<Konto> kontarokudocelowego = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, ukladdocelowy.getRok());
                
//                if (podatnik.equals(wpisView.getPodatnikwzorcowy())) {
//                    for (Iterator<KontopozycjaZapis> it = zapisanePOzycjezUkladuWzorcowego.iterator();it.hasNext();) {
//                        KontopozycjaZapis p = it.next();
//                        if (!p.getKontoID().getPodatnik().equals(wpisView.getPodatnikwzorcowy())) {
//                            it.remove();
//                            kontopozycjaZapisDAO.destroy(p);
//                        }
//                    }
//                }
                for (KontopozycjaZapis p : zapisanePOzycjezUkladuWzorcowego) {
                    if (p.getKontoID()!=null) {
                        if (czypozycjazawieraBilans(pozycjedoprzejrzenia, p) && p.getKontoID().getRok()==ukladzrodlowy.getRokInt() && p.getKontoID().getPodatnik().equals(ukladzrodlowy.getPodatnik())) {
                            Konto nowekonto = pobierzkontozlisty(kontarokudocelowego, p);
                            if (nowekonto != null) {
                                KontopozycjaZapis kp = new KontopozycjaZapis();
                                kp.setKontoID(nowekonto);
                                kp.setPozycjaWn(p.getPozycjaWn());
                                kp.setPozycjaMa(p.getPozycjaMa());
                                kp.setStronaWn(p.getStronaWn());
                                kp.setStronaMa(p.getStronaMa());
                                kp.setSyntetykaanalityka(p.getSyntetykaanalityka());
                                kp.setUkladBR(ukladdocelowy);
                                kp.setWynik0bilans1(true);
                                kontopozycjaZapisDAO.dodaj(kp);
                            }
                        }
                    }
                }
                
                Msg.msg("Zapamiętano przyporządkowane pozycje bilansu");
            }
        }
    }
    
    private static Konto pobierzkontozlisty(List<Konto> kontarokudocelowego, KontopozycjaZapis stara) {
        Konto nowekonto = null;
        try {
            for (Konto p : kontarokudocelowego) {
                if (stara.getKontoID().getPelnynumer().equals(p.getPelnynumer())) {
                    nowekonto = p;
                    break;
                }
            }
        } catch (Exception e) {}
        return nowekonto;
    }
    
    private static boolean czypozycjazawiera(List<PozycjaRZiS> pozycjedoprzejrzenia, KontopozycjaZapis p) {
        boolean zwrot = false;
        for (PozycjaRZiS r : pozycjedoprzejrzenia) {
            if (r.getPozycjaString().equals(p.getPozycjaWn()) || r.getPozycjaString().equals(p.getPozycjaMa())) {
                zwrot = true;
                break;
            }
        }
        return zwrot;
    }
    
    private static boolean czypozycjazawieraBilans(List<PozycjaBilans> pozycjedoprzejrzenia, KontopozycjaZapis p) {
        boolean zwrot = false;
        for (PozycjaBilans r : pozycjedoprzejrzenia) {
            if (r.getPozycjaString().equals(p.getPozycjaWn()) || r.getPozycjaString().equals(p.getPozycjaMa())) {
                zwrot = true;
                break;
            }
        }
        return zwrot;
    }
   
}
