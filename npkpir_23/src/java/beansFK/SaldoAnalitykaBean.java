/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import embeddablefk.SaldoKonto;
import entityfk.Konto;
import entityfk.StronaWiersza;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SaldoAnalitykaBean extends Thread {

    private Konto p;
    private StronaWiersza r;
    private SaldoKonto saldoKonto;
    private int granicamca;

    public SaldoAnalitykaBean(Konto p, StronaWiersza r, SaldoKonto saldoKonto, int granicamca) {
        this.p = p;
        this.r = r;
        this.saldoKonto = saldoKonto;
        this.granicamca = granicamca;
    }

    @Override
    public void run() {
            if (r.getWnma().equals("Wn")) {
                saldoKonto.setObrotyWn(Z.z(saldoKonto.getObrotyWn() + r.getKwotaPLN()));
            } else {
                saldoKonto.setObrotyMa(Z.z(saldoKonto.getObrotyMa() + r.getKwotaPLN()));
            }
            saldoKonto.getZapisy().add(r);
    }


}
