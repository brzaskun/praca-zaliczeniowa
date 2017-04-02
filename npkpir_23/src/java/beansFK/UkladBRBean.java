/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KontoDAOfk;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class UkladBRBean {

    public static int czyscPozycjeKont(KontoDAOfk kontoDAO, List<Konto> listakont) {
        int zwrot = 0;
        for (Konto p : listakont) {
            p.setKontopozycjaID(null);
        }
        try {
            kontoDAO.editList(listakont);
        } catch (Exception e) {
            zwrot = 1;
            E.e(e);
        }

        return zwrot;
    }

    public static ArrayList<PozycjaRZiSBilans> pobierzpozycje(PozycjaRZiSDAO pozycjaRZiSDAO, PozycjaBilansDAO pozycjaBilansDAO, UkladBR uklad, String aktywapasywa, String br) {
        ArrayList pozycje = new ArrayList<>();
        if (br.equals("r")) {
                pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            } else {
                if (aktywapasywa.equals("aktywa")) {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladAktywa(uklad));
                } else {
                    pozycje.addAll(pozycjaBilansDAO.findBilansukladPasywa(uklad));
                }
                if (pozycje.isEmpty()) {
                   pozycje.add(new PozycjaBilans(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                    Msg.msg("i", "Dodaje pusta pozycje");
                }
            }
        return pozycje;
    }
    
    public static void czyscPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        try {
           for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanekonta(null);
                p.setPrzyporzadkowanestronywiersza(null);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    public static void ustawAktywny(UkladBR ukladBR, UkladBRDAO ukladBRDAO) {
        if (ukladBR != null) {
            ukladBRDAO.ustawnieaktywne(ukladBR.getPodatnik());
            ukladBR.setAktualny(true);
            ukladBRDAO.edit(ukladBR);
        }
    }
    
    public static UkladBR pobierzukladaktywny(UkladBRDAO ukladBRDAO, WpisView wpisView) {
        UkladBR wybrany = null;
        boolean mamaktualny = false;
        List<UkladBR> listaukladow = ukladBRDAO.findPodatnikRok(wpisView);
        if (listaukladow != null) {
            for (UkladBR p : listaukladow) {
                if (p.isAktualny() && mamaktualny == false) {
                    wybrany = p;
                    mamaktualny = true;
                } else if (mamaktualny == true) {
                    p.setAktualny(false);
                }
            }
            if (wybrany == null) {
                for (UkladBR p : listaukladow) {
                    if (p.getUklad().contains("Podstawowy")) {
                       p.setAktualny(true);
                       wybrany = p;
                    }
                }
            }
            ukladBRDAO.editList(listaukladow);
        }
        return wybrany;
    }
}
