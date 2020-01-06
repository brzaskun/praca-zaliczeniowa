/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.v12;

import entityfk.PozycjaRZiSBilans;
import java.util.List;
import static sprawozdania.v12.SprawozdanieFin2018Bean.naniesKwotyAB;
import static sprawozdania.v12.SprawozdanieFin2018Bean.zrobTPoztchaSprawozdania;
import static sprawozdania.v12.SprawozdanieFin2018Bean.zrobsuma;
import static sprawozdania.v12.SprawozdanieFin2018Bean.zrobsumaminus;
import static sprawozdania.v12.SprawozdanieFin2018Bean.zrobsumaplusminus;

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018RZiSBean {

    public static RZiSJednostkaInna generujrzis(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna r = new RZiSJednostkaInna();
        r.rZiSPor = sporzadzrzisporowawczy(l);
        return r;
    }

    private static RZiSJednostkaInna.RZiSPor sporzadzrzisporowawczy(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor r = new RZiSJednostkaInna.RZiSPor();
        r.a = czescA(l);
        r.b = czescB(l);
        r.c = zrobsuma(r.a,r.b);
        r.d = czescD(l);
        r.e = czescE(l);
        r.f = zrobsumaplusminus(r.c,r.d,r.e);
        r.g = czescG(l);
        r.h = czescH(l);
        r.i = zrobsuma(r.f,r.g,r.h);
        r.j = zrobTPoztchaSprawozdania("J", l);
        r.k = zrobTPoztchaSprawozdania("K", l);
        r.l = zrobsumaminus(r.i,r.j,r.k);
        return r;
    }

    private static RZiSJednostkaInna.RZiSPor.A czescA(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.A a = new RZiSJednostkaInna.RZiSPor.A();
        naniesKwotyAB("A", a, l);
        a.ai = zrobTPoztchaSprawozdania("AI", l);
        a.aii = zrobTPoztchaSprawozdania("AII", l);
        a.aiii = zrobTPoztchaSprawozdania("AIII", l);
        a.aiv = zrobTPoztchaSprawozdania("AIV", l);
        return a;
    }

   

    private static RZiSJednostkaInna.RZiSPor.B czescB(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.B a = new RZiSJednostkaInna.RZiSPor.B();
        naniesKwotyAB("B", a, l);
        a.bi = zrobTPoztchaSprawozdania("BI", l);
        a.bii = zrobTPoztchaSprawozdania("BII", l);
        a.biii = zrobTPoztchaSprawozdania("BIII", l);
        a.biv = zrobBIV(l);
        a.bv = zrobTPoztchaSprawozdania("BV", l);
        a.bvi = zrobBVI(l);
        a.bvii = zrobTPoztchaSprawozdania("BVII", l);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.B.BIV zrobBIV(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.B.BIV a = new RZiSJednostkaInna.RZiSPor.B.BIV();
        naniesKwotyAB("BIV", a, l);
        a.biv1 = zrobTPoztchaSprawozdania("BIV1", l);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.B.BVI zrobBVI(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.B.BVI a = new RZiSJednostkaInna.RZiSPor.B.BVI();
        naniesKwotyAB("BVI", a, l);
        a.bvi1 = zrobTPoztchaSprawozdania("NVI1", l);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.D czescD(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.D a = new RZiSJednostkaInna.RZiSPor.D();
        naniesKwotyAB("D", a, l);
        a.di = zrobTPoztchaSprawozdania("DI", l);
        a.dii = zrobTPoztchaSprawozdania("DII", l);
        a.diii = zrobTPoztchaSprawozdania("DIII", l);
        a.div = zrobTPoztchaSprawozdania("DIV", l);
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.E czescE(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.E a = new RZiSJednostkaInna.RZiSPor.E();
        naniesKwotyAB("E", a, l);
        a.ei = zrobTPoztchaSprawozdania("EI", l);
        a.eii = zrobTPoztchaSprawozdania("EII", l);
        a.eiii = zrobTPoztchaSprawozdania("EIII", l);
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G czescG(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.G a = new RZiSJednostkaInna.RZiSPor.G();
        naniesKwotyAB("G", a, l);
        a.gi = zrobGI(l);
        a.gii = zrobGII(l);
        a.giii = zrobGIII(l);
        a.giv = zrobTPoztchaSprawozdania("GIV", l);
        a.giii = zrobGIII(l);
        return a;
    }

    
    private static RZiSJednostkaInna.RZiSPor.G.GI zrobGI(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.G.GI a = new RZiSJednostkaInna.RZiSPor.G.GI();
        naniesKwotyAB("GI", a, l);
        a.gia = zrobGIA(l);
        a.gib = zrobGIB(l);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.G.GI.GIA zrobGIA(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.G.GI.GIA a = new RZiSJednostkaInna.RZiSPor.G.GI.GIA();
        naniesKwotyAB("GIA", a, l);
        a.gia1 = zrobTPoztchaSprawozdania("GIA1", l);
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G.GI.GIB zrobGIB(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.G.GI.GIB a = new RZiSJednostkaInna.RZiSPor.G.GI.GIB();
        naniesKwotyAB("GIB", a, l);
        a.gib1 = zrobTPoztchaSprawozdania("", l);
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G.GII zrobGII(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.G.GII a = new RZiSJednostkaInna.RZiSPor.G.GII();
        naniesKwotyAB("GII", a, l);
        a.giij = zrobTPoztchaSprawozdania("GIIJ", l);
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.G.GIII zrobGIII(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.G.GIII a = new RZiSJednostkaInna.RZiSPor.G.GIII();
        naniesKwotyAB("GIII", a, l);
        a.giiij = zrobTPoztchaSprawozdania("GIIIJ", l);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.H czescH(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.H a = new RZiSJednostkaInna.RZiSPor.H();
        naniesKwotyAB("H", a, l);
        a.hi = zrobHI(l);
        a.hii = zrobHII(l);
        a.hiii = zrobTPoztchaSprawozdania("HIII", l);
        a.hiv = zrobTPoztchaSprawozdania("HIV", l);
        return a;
    }

    private static RZiSJednostkaInna.RZiSPor.H.HI zrobHI(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.H.HI a = new RZiSJednostkaInna.RZiSPor.H.HI();
        naniesKwotyAB("HI", a, l);
        a.hij = zrobTPoztchaSprawozdania("HIJ", l);
        return a;
    }
    
    private static RZiSJednostkaInna.RZiSPor.H.HII zrobHII(List<PozycjaRZiSBilans> l) {
        RZiSJednostkaInna.RZiSPor.H.HII a = new RZiSJednostkaInna.RZiSPor.H.HII();
        naniesKwotyAB("HII", a, l);
        a.hiij = zrobTPoztchaSprawozdania("HIIJ", l);
        return a;
    }

    

    

    

    
    
    
}
