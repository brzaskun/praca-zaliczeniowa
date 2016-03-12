/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multi;

import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.util.List;
import java.util.Map;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class UzupelnijWiersz extends Thread {

    private StronaWiersza t;
    private Boolean jest;
    private StronaWiersza r;

    public UzupelnijWiersz(StronaWiersza t, Boolean jest, StronaWiersza r) {
        this.jest = jest;
        this.r = r;
        this.t = t;
    }

    public void run() {
        if (t.getDokfkS().equals(r.getDokfkS())) {
            jest = true;
        }
    }
}
