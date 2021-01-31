/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import dao.TabelanbpDAO;
import entityfk.Dokfk;
import entityfk.Tabelanbp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */

public class TabelaNBPBean {
    
    public static Tabelanbp pobierzTabeleNBP(DateTime dzienposzukiwany, TabelanbpDAO tabelanbpDAO, String nazwawaluty, Dokfk selected) {
        boolean znaleziono = false;
        int zabezpieczenie = 0;
        Tabelanbp tabelanbppobrana = null;
        while (!znaleziono && (zabezpieczenie < 365)) {
            dzienposzukiwany = dzienposzukiwany.minusDays(1);
            String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
            List<Tabelanbp> pobrane = tabelanbpDAO.findByDateWalutaLista(doprzekazania, nazwawaluty);
            if (pobrane != null && pobrane.size() > 0) {
                for (Tabelanbp r : pobrane) {
                    if (r.getNrtabeli().contains("NBP")) {
                        znaleziono = true;
                        tabelanbppobrana = r;
                        selected.dodajTabeleWalut(r);
                        break;
                    }
                }
            }
            zabezpieczenie++;
        }
        return tabelanbppobrana;
    }
    
    public static Tabelanbp pobierzTabeleNBP(DateTime dzienposzukiwany, TabelanbpDAO tabelanbpDAO, String nazwawaluty) {
        boolean znaleziono = false;
        int zabezpieczenie = 0;
        Tabelanbp tabelanbppobrana = null;
        while (!znaleziono && (zabezpieczenie < 365)) {
            dzienposzukiwany = dzienposzukiwany.minusDays(1);
            String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
            List<Tabelanbp> pobrane = tabelanbpDAO.findByDateWalutaLista(doprzekazania, nazwawaluty);
            if (pobrane != null && pobrane.size() > 0) {
                for (Tabelanbp r : pobrane) {
                    if (r.getNrtabeli().contains("NBP")) {
                        znaleziono = true;
                        tabelanbppobrana = r;
                        break;
                    }
                }
            }
            zabezpieczenie++;
        }
        return tabelanbppobrana;
    }
    
    public static List<Tabelanbp> pobierzTabeleNieNBP(DateTime dzienposzukiwany, TabelanbpDAO tabelanbpDAO, String nazwawaluty) {
        boolean znaleziono = false;
        int zabezpieczenie = 0;
        List<Tabelanbp> tabelanbppobrane = Collections.synchronizedList(new ArrayList<>());
        while (!znaleziono && (zabezpieczenie < 365)) {
            String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
            List<Tabelanbp> pobrane = tabelanbpDAO.findByDateWalutaLista(doprzekazania, nazwawaluty);
            if (pobrane != null && pobrane.size() > 0) {
                for (Tabelanbp r : pobrane) {
                    if (!r.getNrtabeli().contains("NBP")) {
                        znaleziono = true;
                        tabelanbppobrane.add(r);
                    }
                }
            }
            zabezpieczenie++;
        }
        return tabelanbppobrane;
    }
}
