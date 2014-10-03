/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import converter.RomNumb;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjarzisDAO;
import embeddablefk.TreeNodeExtended;
import entityfk.Bilansuklad;
import entityfk.Konto;
import entityfk.Kontopozycjarzis;
import entityfk.PozycjaBilans;
import entityfk.Rzisuklad;
import entityfk.StronaWiersza;
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
public class PozycjaBilansFKBean {
    
    public static void wyluskajNieprzyporzadkowaneAnalityki(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnik) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getPozycja() == null) {
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
            } else if (p.getPozycja().equals("analit")) {
                List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                for (Konto r : potomki) {
                    wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik);
                }
            }
        }
    }
     
    public static void ustawRoota(TreeNodeExtended rootL, ArrayList<PozycjaBilans> pozycjeL, List<StronaWiersza> zapisy, List<Konto> plankont) {
        rootL.createTreeNodesForElement(pozycjeL);
        rootL.addNumbers(zapisy, plankont);
        rootL.sumNodes();
        rootL.resolveFormulas();
        rootL.expandAll();
    }
    
    public static void ustawRootaprojekt(TreeNodeExtended rt, ArrayList<PozycjaBilans> pz) {
        rt.createTreeNodesForElement(pz);
        rt.expandAll();
    }
    
    public static int ustawLevel(TreeNodeExtended rt, ArrayList<PozycjaBilans> pozycjeL) {
        return rt.ustaldepthDT(pozycjeL) - 1;
    }
    
    public static void pobierzzachowanepozycjedlakont(KontoDAOfk kontoDAO, KontopozycjarzisDAO kontopozycjarzisDAO, Bilansuklad bilansuklad) {
        List<Kontopozycjarzis> kontopozycjarzis = kontopozycjarzisDAO.findKontaPodatnikUklad(bilansuklad);
        for (Kontopozycjarzis p : kontopozycjarzis) {
            int konto_id = p.getKontopozycjarzisPK().getKontoId();
            Konto konto = kontoDAO.findKonto(p.getKontopozycjarzisPK().getKontoId());
            konto.setPozycja(p.getPozycjastring());
            konto.setPozycjonowane(p.isPozycjonowane());
            kontoDAO.edit(konto);
        }
    }
    
    public static List<Konto> wyszukajprzyporzadkowane(KontoDAOfk kontoDAO, String pozycja) {
        List<Konto> lista = kontoDAO.findKontaPrzyporzadkowane(pozycja, "wynikowe");
        List<Konto> returnlist = new ArrayList<>();
        int level = 0;
        for (Konto p : lista) {
            if (p.getPozycja().equals(pozycja)) {
                returnlist.add(p);
            }
        }
        return returnlist;

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
                return "-";
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
                return "-";
        }
        return null;
    }

    public static void odznaczmacierzyste(String macierzyste, String kontoanalizowane, KontoDAOfk kontoDAO, String podatnik) {
        List<Konto> siostry = kontoDAO.findKontaPotomnePodatnik(podatnik, macierzyste);
        if (siostry.size() > 1) {
            boolean sainne = false;
            for (Konto p : siostry) {
                if (p.isPozycjonowane() == true && !p.getPelnynumer().equals(kontoanalizowane)) {
                    sainne = true;
                }
            }
            if (sainne == false) {
                Konto konto = kontoDAO.findKonto(macierzyste, podatnik);
                konto.setPozycja(null);
                kontoDAO.edit(konto);
                if (konto.getMacierzysty() > 0) {
                    odznaczmacierzyste(konto.getMacierzyste(), konto.getPelnynumer(), kontoDAO, podatnik);
                }
            }
        }
    }
    
    public static void oznaczmacierzyste(String macierzyste, KontoDAOfk kontoDAO, String podatnik) {
        Konto konto = kontoDAO.findKonto(macierzyste, podatnik);
        konto.setPozycja("analit");
        kontoDAO.edit(konto);
        if (konto.getMacierzysty() > 0) {
            oznaczmacierzyste(konto.getMacierzyste(), kontoDAO, podatnik);
        }
    }
    
    public static void przyporzadkujpotkomkow(String konto, String pozycja, KontoDAOfk kontoDAO, String podatnik) {
        List<Konto> lista = kontoDAO.findKontaPotomnePodatnik(podatnik, konto);
        for (Konto p : lista) {
            if (pozycja == null) {
                p.setPozycja(null);
            } else {
                p.setPozycja(pozycja);
            }
            kontoDAO.edit(p);
            if (p.isMapotomkow() == true) {
                przyporzadkujpotkomkow(p.getPelnynumer(), pozycja, kontoDAO, podatnik);
            }
        }
    }


}
