/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.KontopozycjaZapisDAO;
import dao.UkladBRDAO;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.UkladBR;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Osito
 */
public class KontoPozycjaBean {
    
    
    public static void duplikujpozycje(UkladBRDAO ukladBRDAO, String nazwadomyslanukladu,  Podatnik podatnik, String rok, Konto starekonto, Konto nowekonto, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        List<KontopozycjaZapis> zwrot = pozycjewzorca(kontopozycjaZapisDAO, starekonto);
        if (zwrot != null) {
            List<KontopozycjaZapis> nowe = Collections.synchronizedList(new ArrayList<>());
            for (KontopozycjaZapis p : zwrot) {
                KontopozycjaZapis r = kopiujpozycje(p, ukladBRDAO, nazwadomyslanukladu, podatnik, rok, nowekonto);
                if (r != null) {
                    nowe.add(r);
                }
            }
            kontopozycjaZapisDAO.createList(nowe);
        }
    }
    
    
    private static List<KontopozycjaZapis> pozycjewzorca(KontopozycjaZapisDAO kontopozycjaZapisDAO, Konto konto) {
        List<KontopozycjaZapis> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
            zwrot = kontopozycjaZapisDAO.findByKontoOnly(konto);
        } catch (Exception e){}
        return zwrot;
    }
    
    private static KontopozycjaZapis kopiujpozycje(KontopozycjaZapis starapozycja, UkladBRDAO ukladBRDAO, String nazwadomyslna, Podatnik podatnik, String rok, Konto nowekonto) {
        UkladBR ukladdomyslny = pobierzUkladDomyslny(ukladBRDAO, nazwadomyslna, podatnik, rok);
        KontopozycjaZapis zwrot = null;
        if (nowekonto != null && ukladdomyslny != null) {
            zwrot = new KontopozycjaZapis(starapozycja, nowekonto, ukladdomyslny);
        }
        return zwrot;
    }

    private static UkladBR pobierzUkladDomyslny(UkladBRDAO ukladBRDAO, String ukladdomyslny, Podatnik podatnik, String rok) {
        List<UkladBR> odnalezione = ukladBRDAO.findPodatnikRok(podatnik, rok);
        UkladBR znaleziony = null;
        for (UkladBR p : odnalezione) {
            if (p.getRok().equals(rok) && p.getUklad().equals(ukladdomyslny)) {
                znaleziony = p;
            }
        }
        return znaleziony;
    }
}
