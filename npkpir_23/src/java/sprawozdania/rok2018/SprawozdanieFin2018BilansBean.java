/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import java.math.BigDecimal;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.naniesKwotyAB;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.zrobTPoztchaSprawozdania;

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018BilansBean {
    static BilansJednostkaInna generujbilans() {
        BilansJednostkaInna bil = new BilansJednostkaInna();
        bil.setAktywa(generujaktywa());
        bil.setPasywa(generujpasywa());
        return bil;
    }

    private static BilansJednostkaInna.Pasywa generujpasywa() {
        BilansJednostkaInna.Pasywa a = new BilansJednostkaInna.Pasywa();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.setPasywaA(generuj_PasywaA());
        a.setPasywaB(generuj_PasywaB());
        return a;
    }

    private static BilansJednostkaInna.Aktywa generujaktywa() {
        BilansJednostkaInna.Aktywa a = new BilansJednostkaInna.Aktywa();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.setAktywaA(generyj_AktywaA());
        a.setAktywaB(generyj_AktywaB());
        a.setAktywaC(generyj_AktywaC());
        a.setAktywaD(generyj_AktywaD());
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaA generuj_PasywaA() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static BilansJednostkaInna.Pasywa.PasywaB generuj_PasywaB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static BilansJednostkaInna.Aktywa.AktywaA generyj_AktywaA() {
        BilansJednostkaInna.Aktywa.AktywaA a = new BilansJednostkaInna.Aktywa.AktywaA();
        a.aktywaAI = generujAktywaAI();
        a.aktywaAII = generujAktywaAII();
        a.aktywaAIII = generujAktywaAIII();
        a.aktywaAIV = generujAktywaAIV();
        a.aktywaAV = generujAktywaAV();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB generyj_AktywaB() {
        BilansJednostkaInna.Aktywa.AktywaB a = new BilansJednostkaInna.Aktywa.AktywaB();
        a.aktywaBI = generujAktywaBI();
        a.aktywaBII = generujAktywaBII();
        a.aktywaBIII = generujAktywaBIII();
        a.aktywaBIV = zrobTPoztchaSprawozdania();
        return a;
    }

    private static TPozycjaSprawozdania generyj_AktywaC() {
        return zrobTPoztchaSprawozdania();
    }

    private static TPozycjaSprawozdania generyj_AktywaD() {
        return zrobTPoztchaSprawozdania();
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAI generujAktywaAI() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAI a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAI();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAI1 = zrobTPoztchaSprawozdania();
        a.aktywaAI2 = zrobTPoztchaSprawozdania();
        a.aktywaAI3 = zrobTPoztchaSprawozdania();
        a.aktywaAI4 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAII generujAktywaAII() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAII a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAII1 = zrobAktywaAII1();
        a.aktywaAII2 = zrobTPoztchaSprawozdania();
        a.aktywaAII3 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1 zrobAktywaAII1() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1 a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1();
        a.aktywaAII1A = zrobTPoztchaSprawozdania();
        a.aktywaAII1B = zrobTPoztchaSprawozdania();
        a.aktywaAII1C = zrobTPoztchaSprawozdania();
        a.aktywaAII1D = zrobTPoztchaSprawozdania();
        a.aktywaAII1E = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII generujAktywaAIII() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAIII1 = zrobTPoztchaSprawozdania();
        a.aktywaAIII2 = zrobTPoztchaSprawozdania();
        a.aktywaAIII3 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV generujAktywaAIV() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAIV1 = zrobTPoztchaSprawozdania();
        a.aktywaAIV2 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3 = zrobAktywaIV3();
        a.aktywaAIV4 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 zrobAktywaIV3() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAIV3A = zrobAIV3A();
        a.aktywaAIV3B = zrobAIV3B();
        a.aktywaAIV3C = zrobAIV3C();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3A zrobAIV3A() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3A a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3A();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAIV3A1 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3A2 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3A3 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3A4 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3B zrobAIV3B() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3B a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3B();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAIV3B1 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3B2 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3B3 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3B4 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3C zrobAIV3C() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3C a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3C();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAIV3C1 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3C2 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3C3 = zrobTPoztchaSprawozdania();
        a.aktywaAIV3C4 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAV generujAktywaAV() {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAV a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAV();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaAV1 = zrobTPoztchaSprawozdania();
        a.aktywaAV2 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBI generujAktywaBI() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBI a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBI();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBI1 = zrobTPoztchaSprawozdania();
        a.aktywaBI2 = zrobTPoztchaSprawozdania();
        a.aktywaBI3 = zrobTPoztchaSprawozdania();
        a.aktywaBI4 = zrobTPoztchaSprawozdania();
        a.aktywaBI5 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII generujAktywaBII() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII1 = zrobAktywaBII1();
        a.aktywaBII2 = zrobAktywaBII2();
        a.aktywaBII3 = zrobAktywaBII3();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1 zrobAktywaBII1() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII1A = zrobAktywaBII1A();
        a.aktywaBII1B = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1.AktywaBII1A zrobAktywaBII1A() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1.AktywaBII1A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1.AktywaBII1A();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII1A1 = zrobTPoztchaSprawozdania();
        a.aktywaBII1A2 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2 zrobAktywaBII2() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII2A = zrobAktywaBII2A();
        a.aktywaBII2B = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2.AktywaBII2A zrobAktywaBII2A() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2.AktywaBII2A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2.AktywaBII2A();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII2A1 = zrobTPoztchaSprawozdania();
        a.aktywaBII2A2 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3 zrobAktywaBII3() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII3A = zrobAktywaBII3A();
        a.aktywaBII3B = zrobTPoztchaSprawozdania();
        a.aktywaBII3C = zrobTPoztchaSprawozdania();
        a.aktywaBII3D = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3.AktywaBII3A zrobAktywaBII3A() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3.AktywaBII3A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3.AktywaBII3A();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBII3A1 = zrobTPoztchaSprawozdania();
        a.aktywaBII3A1 = zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII generujAktywaBIII() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBIII1 = zrobAktywaBIII1();
        a.aktywaBIII2 =  zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 zrobAktywaBIII1() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBIII1A = zrobAktywaBIII1A();
        a.aktywaBIII1B = zrobAktywaBIII1B();
        a.aktywaBIII1C = zrobAktywaBIII1C();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1A zrobAktywaBIII1A() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1A();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBIII1A1 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1A2 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1A3 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1A4 =  zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1B zrobAktywaBIII1B() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1B a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1B();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBIII1B1 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1B2 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1B3 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1B4 =  zrobTPoztchaSprawozdania();
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1C zrobAktywaBIII1C() {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1C a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1C();
        naniesKwotyAB(a, BigDecimal.TEN, BigDecimal.TEN);
        a.aktywaBIII1C1 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1C2 =  zrobTPoztchaSprawozdania();
        a.aktywaBIII1C3 =  zrobTPoztchaSprawozdania();
        return a;
    }

    

    
}
