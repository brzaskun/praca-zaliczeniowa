/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class KontoBO extends Konto{
    private double saldorokpopWn;
    private double saldorokpopMa;
    private double roznicaWn;
    private double roznicaMa;

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

    public double getRoznicaWn() {
        return Z.z(this.getBoWn()-this.saldorokpopWn);
    }

    public double getRoznicaMa() {
        return Z.z(this.getBoMa()-this.saldorokpopMa);
    }

    
    
}
