/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KontoDAOfk;
import daoFK.PozycjaBilansDAO;
import daoFK.PozycjaRZiSDAO;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
import error.E;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import msg.Msg;

/**
 *
 * @author Osito
 */

public class UkladBRBean {
    
    public static int czyscPozycjeKont(KontoDAOfk kontoDAO, List<Konto> listakont) {
        int zwrot = 0;
        for (Konto p : listakont) {
                p.setKontopozycjaID(null);
                try {
                    kontoDAO.edit(p);
                } catch (Exception e) {
                    zwrot = 1;
                    E.e(e);
                }
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
    
}
