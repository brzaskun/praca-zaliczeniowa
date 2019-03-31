/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import entityfk.PozycjaRZiSBilans;
import java.math.BigDecimal;
import java.util.List;
import static sprawozdania.rok2018.SprawozdanieFinOP2018Bean.naniesKwotyAB;
import static sprawozdania.rok2018.SprawozdanieFinOP2018Bean.zrobTPoztchaSprawozdania;
import static sprawozdania.rok2018.SprawozdanieFinOP2018Bean.zrobsuma;
import static sprawozdania.rok2018.SprawozdanieFinOP2018Bean.zrobsumaminus;
import static sprawozdania.rok2018.SprawozdanieFinOP2018Bean.zrobsumaplusplusminus;
import static sprawozdania.rok2018.SprawozdanieFinOP2018Bean.zrobsumaplusminusplusminus;

/**
 *
 * @author Osito
 */
public class SprawozdanieFinOP2018RZiSBean {

 
    public static RZiSJednostkaOp generujrzis(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaOp r = new RZiSJednostkaOp();
        r.a = czescA(l);
        r.b = czescB(l);
        r.c = zrobsuma(r.a,r.b);
        r.d = zrobTPoztchaSprawozdania("D", l);
        r.e = zrobTPoztchaSprawozdania("E", l);
        r.f = zrobsuma(r.d,r.e);
        r.g = zrobTPoztchaSprawozdania("G", l);
        r.h = zrobsumaplusplusminus(r.c,r.f,r.g);
        r.i = zrobTPoztchaSprawozdania("I", l);
        r.j = zrobTPoztchaSprawozdania("J", l);
        r.k = zrobTPoztchaSprawozdania("K", l);
        r.l = zrobTPoztchaSprawozdania("K", l);
        r.m = zrobsumaplusminusplusminus(r.h,r.i,r.j,r.k,r.l);
        r.n = zrobTPoztchaSprawozdania("N", l);
        r.o = zrobTPoztchaSprawozdania("O", l);
        return r;
    }

    private static RZiSJednostkaOp.A czescA(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaOp.A a = new RZiSJednostkaOp.A();
        naniesKwotyAB("A", a, l);
        a.ai = zrobTPoztchaSprawozdania("AI", l);
        a.aii = zrobTPoztchaSprawozdania("AII", l);
        a.aiii = zrobTPoztchaSprawozdania("AIII", l);
        return a;
    }

   

    private static RZiSJednostkaOp.B czescB(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaOp.B a = new RZiSJednostkaOp.B();
        naniesKwotyAB("B", a, l);
        a.bi = zrobTPoztchaSprawozdania("BI", l);
        a.bii = zrobTPoztchaSprawozdania("BII", l);
        a.biii = zrobTPoztchaSprawozdania("BIII", l);
        return a;
    }


    
    
    
}
