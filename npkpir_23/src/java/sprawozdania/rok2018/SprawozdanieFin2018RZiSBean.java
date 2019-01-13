/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import java.math.BigDecimal;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.naniesKwotyAB;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.zrobsuma;

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018RZiSBean {

    static RZiSJednostkaInna generujrzis() {
        RZiSJednostkaInna r = new RZiSJednostkaInna();
        r.rZiSPor = sporzadzrzisporowawczy();
        return r;
    }

    private static RZiSJednostkaInna.RZiSPor sporzadzrzisporowawczy() {
        RZiSJednostkaInna.RZiSPor r = new RZiSJednostkaInna.RZiSPor();
        r.a = czescA();
        r.b = czescB();
        r.c = zrobsuma(r.a,r.b);
        r.d = czescD();
        return r;
    }

    private static RZiSJednostkaInna.RZiSPor.A czescA() {
        RZiSJednostkaInna.RZiSPor.A a = new RZiSJednostkaInna.RZiSPor.A();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.ai = zrobTPoztchaSprawozdania();
        a.aii = zrobTPoztchaSprawozdania();
        a.aiii = zrobTPoztchaSprawozdania();
        a.aiv = zrobTPoztchaSprawozdania();
        return a;
    }

    private static TPozycjaSprawozdania zrobTPoztchaSprawozdania() {
        TPozycjaSprawozdania a = new TPozycjaSprawozdania();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.B czescB() {
        RZiSJednostkaInna.RZiSPor.B a = new RZiSJednostkaInna.RZiSPor.B();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.bi = zrobTPoztchaSprawozdania();
        a.bii = zrobTPoztchaSprawozdania();
        a.biii = zrobTPoztchaSprawozdania();
        a.biv = zrobBIV();
        a.bv = zrobTPoztchaSprawozdania();
        a.bvi = zrobBVI();
        a.bvii = zrobTPoztchaSprawozdania();
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.B.BIV zrobBIV() {
        RZiSJednostkaInna.RZiSPor.B.BIV a = new RZiSJednostkaInna.RZiSPor.B.BIV();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.biv1 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.B.BVI zrobBVI() {
        RZiSJednostkaInna.RZiSPor.B.BVI a = new RZiSJednostkaInna.RZiSPor.B.BVI();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.bvi1 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.D czescD() {
        RZiSJednostkaInna.RZiSPor.D a = new RZiSJednostkaInna.RZiSPor.D();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.di = zrobTPoztchaSprawozdania();
        a.dii = zrobTPoztchaSprawozdania();
        a.diii = zrobTPoztchaSprawozdania();
        a.div = zrobTPoztchaSprawozdania();
        return a;
    }

    
    
    
    
}
