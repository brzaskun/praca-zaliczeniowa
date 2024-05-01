/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;

/**
 *
 * @author Osito
 */

public class DFKWiersze {
    public static void zaznaczNowaTrasakcja(Wiersz wiersz, String wnma) {
        StronaWiersza wn = null;
        StronaWiersza ma = null;
        try {
            wn = wiersz.getStronaWn();
            ma = wiersz.getStronaMa();
        }  catch (Exception e) {
        }
        StronaWiersza stw = null;
        if (wnma.equals("Wn") && wn != null) {
            stw = wn;
        }
        if (wnma.equals("Ma") && ma != null) {
            stw = ma;
        }
        if (stw != null) {
            Konto konorozrachunkowe = stw.getKonto();
            if (konorozrachunkowe != null && stw.getKwota() > 0.0 && konorozrachunkowe.isRozrachunkowe()) {
                stw.setNowatransakcja(true);
                stw.setTypStronaWiersza(1);
            }
        } else {
        }
    }
}
