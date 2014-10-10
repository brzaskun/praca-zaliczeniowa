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
                } else if (p.getKontopozycjaID().getStronaWn().equals("analityka")) {
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
            if (p.getZwyklerozrachszczegolne().equals("szczególne") && (p.getKontopozycjaID().getPozycjaWn() != null || p.getKontopozycjaID().getPozycjaMa() != null)) {
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
            } else if (p.getKontopozycjaID().getPozycjaWn() == null && aktywa0pasywa1 == false) {
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
            } else if (p.getKontopozycjaID().getPozycjaMa() == null && aktywa0pasywa1 == true) {
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
            } else if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaWn().equals("analit")) {
                List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                for (Konto r : potomki) {
                    wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1);
                }
            } else if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getPozycjaMa().equals("analit")) {
                List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                for (Konto r : potomki) {
                    wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik, aktywa0pasywa1);
                }
            }
        }
    }
     
    public static void ustawRoota(TreeNodeExtended rootL, ArrayList<PozycjaRZiSBilans> pozycjeL, List<StronaWiersza> zapisy, List<Konto> plankont) {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbers(zapisy, plankont);
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
    
    public static void naniesZachowanePozycjeNaKonta(KontoDAOfk kontoDAO, KontopozycjaDAO kontopozycjarzisDAO, UkladBR uklad) {
        List<Konto> kontapobrane = kontoDAO.findWszystkieKontaPodatnika(uklad.getPodatnik());
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
    
    public static List<Konto> wyszukajprzyporzadkowane(KontoDAOfk kontoDAO, String pozycja, String podatnik) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja, "wynikowe", podatnik);
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : lista) {
            if (p.getKontopozycjaID().getPozycjaWn().equals(pozycja) || p.getKontopozycjaID().getPozycjaMa().equals(pozycja)) {
                if (!p.getKontopozycjaID().getStronaWn().equals("syntetyka") && !p.getKontopozycjaID().getStronaWn().equals("analityka")) {
                    returnlist.add(p);
                }
            }
        }
        return returnlist;

    }
    
    public static List<Konto> wyszukajprzyporzadkowaneB(KontoDAOfk kontoDAO, String pozycja, String podatnik) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja, "bilansowe", podatnik);
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : lista) {
            if (p.getKontopozycjaID().getPozycjaWn() != null && p.getKontopozycjaID().getPozycjaWn().equals(pozycja)) {
                returnlist.add(p);
            }
            if (p.getKontopozycjaID().getPozycjaMa() != null && p.getKontopozycjaID().getPozycjaMa().equals(pozycja)) {
                returnlist.add(p);
            }
        }
        return returnlist;

    }
    
    public static void wyszukajprzyporzadkowaneBLista(KontoDAOfk kontoDAO, PozycjaRZiSBilans pozycja, PozycjaBilansDAO pozycjaBilansDAO, String podatnik) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja.getPozycjaString(), "bilansowe", podatnik);
        List<KontoKwota> kontokwotalist = new ArrayList<>();
        for (Konto p : lista) {
            KontoKwota t = new KontoKwota(p,0.0);
            kontokwotalist.add(t);
        }
        pozycja.setPrzyporzadkowanekonta(kontokwotalist);
        pozycjaBilansDAO.edit(pozycja);

    }
    
    public static void wyszukajprzyporzadkowaneRLista(KontoDAOfk kontoDAO, PozycjaRZiSBilans pozycja, PozycjaRZiSDAO pozycjaRZiSDAO, String podatnik) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja.getPozycjaString(), "wynikowe", podatnik);
        List<KontoKwota> kontokwotalist = new ArrayList<>();
        for (Konto p : lista) {
            if (!p.getKontopozycjaID().getStronaWn().equals("syntetyka") && !p.getKontopozycjaID().getStronaWn().equals("analityka")) {
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

    public static void odznaczmacierzyste(String macierzyste, String kontoanalizowane, KontoDAOfk kontoDAO, String podatnik) {
        List<Konto> siostry = kontoDAO.findKontaPotomnePodatnik(podatnik, macierzyste);
        if (siostry.size() > 1) {
            boolean sainne = false;
            for (Konto p : siostry) {
                if (p.getKontopozycjaID() != null && !p.getPelnynumer().equals(kontoanalizowane)) {
                    sainne = true;
                }
            }
            if (sainne == false) {
                Konto konto = kontoDAO.findKonto(macierzyste, podatnik);
                konto.setKontopozycjaID(null);
                kontoDAO.edit(konto);
                if (konto.getMacierzysty() > 0) {
                    odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
                }
            }
        }
    }
    
    public static void oznaczmacierzyste(String macierzyste, Kontopozycja kp, UkladBR uklad, KontoDAOfk kontoDAO, String podatnik) {
        Konto konto = kontoDAO.findKonto(macierzyste, podatnik);
        if (konto.getKontopozycjaID() == null) {
            kp.setStronaWn("analityka");
            kp.setStronaMa("analityka");
            kp.setPozycjonowane(true);
            kp.setKontoID(konto);
            kp.setUkladBR(uklad);
            konto.setKontopozycjaID(kp);
            kontoDAO.edit(konto);
            if (konto.getMacierzysty() > 0) {
                oznaczmacierzyste(konto.getMacierzyste(), kp, uklad, kontoDAO, podatnik);
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
                pozycja.setStronaWn("syntetyka");
                pozycja.setStronaMa("syntetyka");
                p.setKontopozycjaID(pozycja);
                
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow() == true) {
                przyporzadkujpotkomkowZwykle(p.getPelnynumer(), pozycja, kontoDAO, podatnik, kontopozycjaDAO);
            }
        }
    }
    
    public static void przyporzadkujpotkomkowRozrachunkowe(String konto, String pozycja, KontoDAOfk kontoDAO, String podatnik, String wnma) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(podatnik, konto);
        for (Konto p : lista) {
            if (wnma.equals("wn")) {
                if (pozycja == null) {
                    p.getKontopozycjaID().setPozycjaWn(null);
                } else {
                    p.getKontopozycjaID().setPozycjaWn(pozycja);
                }
            } else {
                if (pozycja == null) {
                    p.getKontopozycjaID().setPozycjaMa(null);
                } else {
                    p.getKontopozycjaID().setPozycjaMa(pozycja);
                }
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow() == true) {
                przyporzadkujpotkomkowRozrachunkowe(p.getPelnynumer(), pozycja, kontoDAO, podatnik, wnma);
            }
        }
    }


}
