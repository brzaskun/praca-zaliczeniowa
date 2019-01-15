/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import java.math.BigDecimal;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.naniesKwotyAB;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.zrobTPoztchaSprawozdania;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.zrobsuma;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.zrobsumaminus;

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
        r.e = czescE();
        r.f = zrobsuma(r.d,r.e);
        r.g = czescG();
        r.h = czescH();
        r.i = zrobsuma(r.f,r.g,r.h);
        r.j = zrobTPoztchaSprawozdania();
        r.k = zrobTPoztchaSprawozdania();
        r.l = zrobsumaminus(r.f,r.g,r.h);
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
    
    private static RZiSJednostkaInna.RZiSPor.E czescE() {
        RZiSJednostkaInna.RZiSPor.E a = new RZiSJednostkaInna.RZiSPor.E();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.ei = zrobTPoztchaSprawozdania();
        a.eii = zrobTPoztchaSprawozdania();
        a.eiii = zrobTPoztchaSprawozdania();
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G czescG() {
        RZiSJednostkaInna.RZiSPor.G a = new RZiSJednostkaInna.RZiSPor.G();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.gi = zrobGI();
        a.gii = zrobGII();
        a.giii = zrobGIII();
        a.giv = zrobTPoztchaSprawozdania();
        a.giii = zrobGIII();
        return a;
    }

    
    private static RZiSJednostkaInna.RZiSPor.G.GI zrobGI() {
        RZiSJednostkaInna.RZiSPor.G.GI a = new RZiSJednostkaInna.RZiSPor.G.GI();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.gia = zrobGIA();
        a.gib = zrobGIB();
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.G.GI.GIA zrobGIA() {
        RZiSJednostkaInna.RZiSPor.G.GI.GIA a = new RZiSJednostkaInna.RZiSPor.G.GI.GIA();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.gia1 = zrobTPoztchaSprawozdania();
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G.GI.GIB zrobGIB() {
        RZiSJednostkaInna.RZiSPor.G.GI.GIB a = new RZiSJednostkaInna.RZiSPor.G.GI.GIB();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.gib1 = zrobTPoztchaSprawozdania();
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G.GII zrobGII() {
        RZiSJednostkaInna.RZiSPor.G.GII a = new RZiSJednostkaInna.RZiSPor.G.GII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.giij = zrobTPoztchaSprawozdania();
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G.GIII zrobGIII() {
        RZiSJednostkaInna.RZiSPor.G.GIII a = new RZiSJednostkaInna.RZiSPor.G.GIII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.giiij = zrobTPoztchaSprawozdania();
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.H czescH() {
        RZiSJednostkaInna.RZiSPor.H a = new RZiSJednostkaInna.RZiSPor.H();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.hi = zrobHI();
        a.hii = zrobHII();
        a.hiii = zrobTPoztchaSprawozdania();
        a.hiv = zrobTPoztchaSprawozdania();
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.H.HI zrobHI() {
        RZiSJednostkaInna.RZiSPor.H.HI a = new RZiSJednostkaInna.RZiSPor.H.HI();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.hij = zrobTPoztchaSprawozdania();
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.H.HII zrobHII() {
        RZiSJednostkaInna.RZiSPor.H.HII a = new RZiSJednostkaInna.RZiSPor.H.HII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.hiij = zrobTPoztchaSprawozdania();
        return a;
    }

    

    

    

    
    
    
}
