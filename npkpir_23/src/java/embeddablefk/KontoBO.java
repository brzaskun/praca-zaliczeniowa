/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;

/**
 *
 * @author Osito
 */
public class KontoBO extends Konto{
    private double saldorokpopWn;
    private double saldorokpopMa;

    public KontoBO(Konto konto) {
        super(konto);
    }

    public double getSaldorokpopWn() {
        return saldorokpopWn;
    }

    public void setSaldorokpopWn(double saldorokpopWn) {
        this.saldorokpopWn = saldorokpopWn;
    }

    public double getSaldorokpopMa() {
        return saldorokpopMa;
    }

    public void setSaldorokpopMa(double saldorokpopMa) {
        this.saldorokpopMa = saldorokpopMa;
    }
    
    
}
