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

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018BilansBean {
    public static BilansJednostkaInna generujbilans(List<PozycjaRZiSBilans> aktywa, List<PozycjaRZiSBilans> pasywa) {
        BilansJednostkaInna bil = new BilansJednostkaInna();
        bil.setAktywa(generujaktywa(aktywa));
        bil.setPasywa(generujpasywa(pasywa));
        return bil;
    }

    private static BilansJednostkaInna.Pasywa generujpasywa(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa a = new BilansJednostkaInna.Pasywa();
        naniesKwotyAB("Pasywa", a, l);
        a.setPasywaA(generuj_PasywaA(l));
        a.setPasywaB(generuj_PasywaB(l));
        return a;
    }

    private static BilansJednostkaInna.Aktywa generujaktywa(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa a = new BilansJednostkaInna.Aktywa();
        naniesKwotyAB("Aktywa", a, l);
        a.setAktywaA(generuj_AktywaA(l));
        a.setAktywaB(generuj_AktywaB(l));
        a.setAktywaC(generuj_AktywaC(l));
        a.setAktywaD(generuj_AktywaD(l));
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaA generuj_PasywaA(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaA a = new BilansJednostkaInna.Pasywa.PasywaA();
        naniesKwotyAB("A", a, l);
        a.pasywaAI = zrobTPoztchaSprawozdania("AI",l);
        a.pasywaAII = generujPasywaAII(l);
        a.pasywaAIII = generujPasywaAIII(l);
        a.pasywaAIV = generujPasywaAIV(l);
        a.pasywaAV = zrobTPoztchaSprawozdania("AV", l);
        a.pasywaAVI = zrobTPoztchaSprawozdania("AVI", l);
        a.pasywaAVII = zrobTPoztchaSprawozdania("AVII", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB generuj_PasywaB(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB a = new BilansJednostkaInna.Pasywa.PasywaB();
        naniesKwotyAB("b", a, l);
        a.pasywaBI = generujPasywaBI(l);
        a.pasywaBII = generujPasywaBII(l);
        a.pasywaBIII = generujPasywaBIII(l);
        a.pasywaBIV = generujPasywaBIV(l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA generuj_AktywaA(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA a = new BilansJednostkaInna.Aktywa.AktywaA();
        naniesKwotyAB("A", a, l);
        a.aktywaAI = generujAktywaAI(l);
        a.aktywaAII = generujAktywaAII(l);
        a.aktywaAIII = generujAktywaAIII(l);
        a.aktywaAIV = generujAktywaAIV(l);
        a.aktywaAV = generujAktywaAV(l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB generuj_AktywaB(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB a = new BilansJednostkaInna.Aktywa.AktywaB();
        naniesKwotyAB("B", a, l);
        a.aktywaBI = generujAktywaBI(l);
        a.aktywaBII = generujAktywaBII(l);
        a.aktywaBIII = generujAktywaBIII(l);
        a.aktywaBIV = zrobTPoztchaSprawozdania("BIV", l);
        return a;
    }

    private static TPozycjaSprawozdania generuj_AktywaC(List<PozycjaRZiSBilans> l) {
        return zrobTPoztchaSprawozdania("C",l);
    }

    private static TPozycjaSprawozdania generuj_AktywaD(List<PozycjaRZiSBilans> l) {
        return zrobTPoztchaSprawozdania("D",l);
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAI generujAktywaAI(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAI a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAI();
        naniesKwotyAB("AI",a,l);
        a.aktywaAI1 = zrobTPoztchaSprawozdania("AI1", l);
        a.aktywaAI2 = zrobTPoztchaSprawozdania("AI2", l);
        a.aktywaAI3 = zrobTPoztchaSprawozdania("AI3", l);
        a.aktywaAI4 = zrobTPoztchaSprawozdania("AI4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAII generujAktywaAII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAII a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAII();
        naniesKwotyAB("AII", a, l);
        a.aktywaAII1 = zrobAktywaAII1(l);
        a.aktywaAII2 = zrobTPoztchaSprawozdania("AII2", l);
        a.aktywaAII3 = zrobTPoztchaSprawozdania("AII3", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1 zrobAktywaAII1(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1 a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAII.AktywaAII1();
        naniesKwotyAB("AII1", a, l);
        a.aktywaAII1A = zrobTPoztchaSprawozdania("AII1A", l);
        a.aktywaAII1B = zrobTPoztchaSprawozdania("AII1B", l);
        a.aktywaAII1C = zrobTPoztchaSprawozdania("AII1C", l);
        a.aktywaAII1D = zrobTPoztchaSprawozdania("AII1D", l);
        a.aktywaAII1E = zrobTPoztchaSprawozdania("AII1E", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII generujAktywaAIII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIII();
        naniesKwotyAB("AIII", a, l);
        a.aktywaAIII1 = zrobTPoztchaSprawozdania("AIII1", l);
        a.aktywaAIII2 = zrobTPoztchaSprawozdania("AIII2", l);
        a.aktywaAIII3 = zrobTPoztchaSprawozdania("AIII3", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV generujAktywaAIV(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV();
        naniesKwotyAB("AIV", a, l);
        a.aktywaAIV1 = zrobTPoztchaSprawozdania("AIV1", l);
        a.aktywaAIV2 = zrobTPoztchaSprawozdania("AIV2", l);
        a.aktywaAIV3 = zrobAktywaIV3(l);
        a.aktywaAIV4 = zrobTPoztchaSprawozdania("AIV4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 zrobAktywaIV3(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3 a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3();
        naniesKwotyAB("AIV3", a, l);
        a.aktywaAIV3A = zrobAIV3A(l);
        a.aktywaAIV3B = zrobAIV3B(l);
        a.aktywaAIV3C = zrobAIV3C(l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3A zrobAIV3A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3A a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3A();
        naniesKwotyAB("AIV3A", a, l);
        a.aktywaAIV3A1 = zrobTPoztchaSprawozdania("AIV3A1", l);
        a.aktywaAIV3A2 = zrobTPoztchaSprawozdania("AIV3A2", l);
        a.aktywaAIV3A3 = zrobTPoztchaSprawozdania("AIV3A3", l);
        a.aktywaAIV3A4 = zrobTPoztchaSprawozdania("AIV3A4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3B zrobAIV3B(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3B a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3B();
        naniesKwotyAB("AIV3B", a, l);
        a.aktywaAIV3B1 = zrobTPoztchaSprawozdania("AIV3B1", l);
        a.aktywaAIV3B2 = zrobTPoztchaSprawozdania("AIV3B2", l);
        a.aktywaAIV3B3 = zrobTPoztchaSprawozdania("AIV3B3", l);
        a.aktywaAIV3B4 = zrobTPoztchaSprawozdania("AIV3B4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3C zrobAIV3C(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3C a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAIV.AktywaAIV3.AktywaAIV3C();
        naniesKwotyAB("AIV3C", a, l);
        a.aktywaAIV3C1 = zrobTPoztchaSprawozdania("AIV3C1", l);
        a.aktywaAIV3C2 = zrobTPoztchaSprawozdania("AIV3C2", l);
        a.aktywaAIV3C3 = zrobTPoztchaSprawozdania("AIV3C3", l);
        a.aktywaAIV3C4 = zrobTPoztchaSprawozdania("AIV3C4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaA.AktywaAV generujAktywaAV(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaA.AktywaAV a = new BilansJednostkaInna.Aktywa.AktywaA.AktywaAV();
        naniesKwotyAB("AV", a, l);
        a.aktywaAV1 = zrobTPoztchaSprawozdania("AV1", l);
        a.aktywaAV2 = zrobTPoztchaSprawozdania("AV2", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBI generujAktywaBI(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBI a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBI();
        naniesKwotyAB("BI", a, l);
        a.aktywaBI1 = zrobTPoztchaSprawozdania("BI1", l);
        a.aktywaBI2 = zrobTPoztchaSprawozdania("BI2", l);
        a.aktywaBI3 = zrobTPoztchaSprawozdania("BI3", l);
        a.aktywaBI4 = zrobTPoztchaSprawozdania("BI4", l);
        a.aktywaBI5 = zrobTPoztchaSprawozdania("BI5", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII generujAktywaBII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII();
        naniesKwotyAB("BII", a, l);
        a.aktywaBII1 = zrobAktywaBII1(l);
        a.aktywaBII2 = zrobAktywaBII2(l);
        a.aktywaBII3 = zrobAktywaBII3(l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1 zrobAktywaBII1(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1();
        naniesKwotyAB("BII1", a, l);
        a.aktywaBII1A = zrobAktywaBII1A(l);
        a.aktywaBII1B = zrobTPoztchaSprawozdania("BII1B", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1.AktywaBII1A zrobAktywaBII1A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1.AktywaBII1A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII1.AktywaBII1A();
        naniesKwotyAB("BII1A", a, l);
        a.aktywaBII1A1 = zrobTPoztchaSprawozdania("BII1A1", l);
        a.aktywaBII1A2 = zrobTPoztchaSprawozdania("BII1A2", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2 zrobAktywaBII2(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2();
        naniesKwotyAB("BII2", a, l);
        a.aktywaBII2A = zrobAktywaBII2A(l);
        a.aktywaBII2B = zrobTPoztchaSprawozdania("BII2B", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2.AktywaBII2A zrobAktywaBII2A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2.AktywaBII2A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII2.AktywaBII2A();
        naniesKwotyAB("BII2A", a, l);
        a.aktywaBII2A1 = zrobTPoztchaSprawozdania("BII2A1", l);
        a.aktywaBII2A2 = zrobTPoztchaSprawozdania("BII2A2", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3 zrobAktywaBII3(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3();
        naniesKwotyAB("BII3", a, l);
        a.aktywaBII3A = zrobAktywaBII3A(l);
        a.aktywaBII3B = zrobTPoztchaSprawozdania("BII3B", l);
        a.aktywaBII3C = zrobTPoztchaSprawozdania("BII3C", l);
        a.aktywaBII3D = zrobTPoztchaSprawozdania("BII3D", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3.AktywaBII3A zrobAktywaBII3A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3.AktywaBII3A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBII.AktywaBII3.AktywaBII3A();
        naniesKwotyAB("BII3A", a, l);
        a.aktywaBII3A1 = zrobTPoztchaSprawozdania("BII3A1", l);
        a.aktywaBII3A2 = zrobTPoztchaSprawozdania("BII3A2", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII generujAktywaBIII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII();
        naniesKwotyAB("BIII", a, l);
        a.aktywaBIII1 = zrobAktywaBIII1(l);
        a.aktywaBIII2 =  zrobTPoztchaSprawozdania("BIII2", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 zrobAktywaBIII1(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1 a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1();
        naniesKwotyAB("BIII1", a, l);
        a.aktywaBIII1A = zrobAktywaBIII1A(l);
        a.aktywaBIII1B = zrobAktywaBIII1B(l);
        a.aktywaBIII1C = zrobAktywaBIII1C(l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1A zrobAktywaBIII1A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1A a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1A();
        naniesKwotyAB("BIII1A", a, l);
        a.aktywaBIII1A1 =  zrobTPoztchaSprawozdania("BIII1A1", l);
        a.aktywaBIII1A2 =  zrobTPoztchaSprawozdania("BIII1A2", l);
        a.aktywaBIII1A3 =  zrobTPoztchaSprawozdania("BIII1A3", l);
        a.aktywaBIII1A4 =  zrobTPoztchaSprawozdania("BIII1A4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1B zrobAktywaBIII1B(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1B a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1B();
        naniesKwotyAB("BIII1B", a, l);
        a.aktywaBIII1B1 =  zrobTPoztchaSprawozdania("BIII1B1", l);
        a.aktywaBIII1B2 =  zrobTPoztchaSprawozdania("BIII1B2", l);
        a.aktywaBIII1B3 =  zrobTPoztchaSprawozdania("BIII1B3", l);
        a.aktywaBIII1B4 =  zrobTPoztchaSprawozdania("BIII1B4", l);
        return a;
    }

    private static BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1C zrobAktywaBIII1C(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1C a = new BilansJednostkaInna.Aktywa.AktywaB.AktywaBIII.AktywaBIII1.AktywaBIII1C();
        naniesKwotyAB("BIII1C", a, l);
        a.aktywaBIII1C1 =  zrobTPoztchaSprawozdania("BIII1C1", l);
        a.aktywaBIII1C2 =  zrobTPoztchaSprawozdania("BIII1C2", l);
        a.aktywaBIII1C3 =  zrobTPoztchaSprawozdania("BIII1C3", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaA.PasywaAII generujPasywaAII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaA.PasywaAII a = new BilansJednostkaInna.Pasywa.PasywaA.PasywaAII();
        naniesKwotyAB("AII", a, l);
        a.pasywaAII1 =  zrobTPoztchaSprawozdania("AII1", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaA.PasywaAIII generujPasywaAIII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaA.PasywaAIII a = new BilansJednostkaInna.Pasywa.PasywaA.PasywaAIII();
        naniesKwotyAB("AIII", a, l);
        a.pasywaAIII1 =  zrobTPoztchaSprawozdania("AIII1", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaA.PasywaAIV generujPasywaAIV(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaA.PasywaAIV a = new BilansJednostkaInna.Pasywa.PasywaA.PasywaAIV();
        naniesKwotyAB("AIV", a, l);
        a.pasywaAIV1 =  zrobTPoztchaSprawozdania("AIV1", l);
        a.pasywaAIV2 =  zrobTPoztchaSprawozdania("AIV2", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBI generujPasywaBI(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBI a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBI();
        naniesKwotyAB("BI", a, l);
        a.pasywaBI1 =  zrobTPoztchaSprawozdania("BI1", l);
        a.pasywaBI2 = zrobPasywaBI2(l);
        a.pasywaBI3 = zrobPasywaBI3(l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBII generujPasywaBII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBII a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBII();
        naniesKwotyAB("BII", a, l);
        a.pasywaBII1 =  zrobTPoztchaSprawozdania("BII1", l);
        a.pasywaBII2 =  zrobTPoztchaSprawozdania("BII2", l);
        a.pasywaBII3 = zrobPasywaBII3(l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII generujPasywaBIII(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII();
        naniesKwotyAB("BIII", a, l);
        a.pasywaBIII1 = zrobPasywaBIII1(l);
        a.pasywaBIII2 = zrobPasywaBIII2(l);
        a.pasywaBIII3 = zrobPasywaBIII3(l);
        a.pasywaBIII4 =  zrobTPoztchaSprawozdania("BIII4", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIV generujPasywaBIV(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIV a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIV();
        naniesKwotyAB("BIV", a, l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI2 zrobPasywaBI2(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI2 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI2();
        naniesKwotyAB("BI2", a, l);
        a.pasywaBI21 =  zrobTPoztchaSprawozdania("BI21", l);
        a.pasywaBI22 =  zrobTPoztchaSprawozdania("BI22", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI3 zrobPasywaBI3(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI3 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBI.PasywaBI3();
        naniesKwotyAB("BI3", a, l);
        a.pasywaBI31 =  zrobTPoztchaSprawozdania("BI31", l);
        a.pasywaBI32 =  zrobTPoztchaSprawozdania("BI32", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBII.PasywaBII3 zrobPasywaBII3(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBII.PasywaBII3 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBII.PasywaBII3();
        naniesKwotyAB("BII3", a, l);
        a.pasywaBII3A =  zrobTPoztchaSprawozdania("BII3A", l);
        a.pasywaBII3B =  zrobTPoztchaSprawozdania("BII3B", l);
        a.pasywaBII3C =  zrobTPoztchaSprawozdania("BII3C", l);
        a.pasywaBII3D =  zrobTPoztchaSprawozdania("BII3D", l);
        a.pasywaBII3E =  zrobTPoztchaSprawozdania("BII3E", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1 zrobPasywaBIII1(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1();
        naniesKwotyAB("BIII1", a, l);
        a.pasywaBIII1A = zrobPasywaBIII1A(l);
        a.pasywaBIII1B =  zrobTPoztchaSprawozdania("BIII1B", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1.PasywaBIII1A zrobPasywaBIII1A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1.PasywaBIII1A a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII1.PasywaBIII1A();
        naniesKwotyAB("BIII1A", a, l);
        a.pasywaBIII1A1 =  zrobTPoztchaSprawozdania("BIII1A1", l);
        a.pasywaBIII1A2 =  zrobTPoztchaSprawozdania("BIII1A2", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2 zrobPasywaBIII2(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2();
        naniesKwotyAB("BIII2", a, l);
        a.pasywaBIII2A = zrobPasywaBIII2A(l);
        a.pasywaBIII2B =  zrobTPoztchaSprawozdania("BIII2B", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2.PasywaBIII2A zrobPasywaBIII2A(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2.PasywaBIII2A a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII2.PasywaBIII2A();
        naniesKwotyAB("BIII2A", a, l);
        a.pasywaBIII2A1 =  zrobTPoztchaSprawozdania("BIII2A1", l);
        a.pasywaBIII2A2 =  zrobTPoztchaSprawozdania("BIII2A2", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3 zrobPasywaBIII3(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3();
        naniesKwotyAB("BIII3", a, l);
        a.pasywaBIII3A =  zrobTPoztchaSprawozdania("BIII3A", l);
        a.pasywaBIII3B =  zrobTPoztchaSprawozdania("BIII3B", l);
        a.pasywaBIII3C =  zrobTPoztchaSprawozdania("BIII3C", l);
        a.pasywaBIII3D =  zrobPasywaBIII3D(l);
        a.pasywaBIII3E =  zrobTPoztchaSprawozdania("BIII3E", l);
        a.pasywaBIII3F =  zrobTPoztchaSprawozdania("BIII3F", l);
        a.pasywaBIII3G =  zrobTPoztchaSprawozdania("BIII3G", l);
        a.pasywaBIII3H =  zrobTPoztchaSprawozdania("BIII3H", l);
        a.pasywaBIII3I =  zrobTPoztchaSprawozdania("BIII3I", l);
        return a;
    }

    private static BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3.PasywaBIII3D zrobPasywaBIII3D(List<PozycjaRZiSBilans> l) {
        BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3.PasywaBIII3D  a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3.PasywaBIII3D();
        naniesKwotyAB("BIII3D", a, l);
        a.pasywaBIII3D1 =  zrobTPoztchaSprawozdania("BIII3D1", l);
        a.pasywaBIII3D2 =  zrobTPoztchaSprawozdania("BIII3D2", l);
        return a;
    }

    public static void main(String[] args) {
         BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3 a = new BilansJednostkaInna.Pasywa.PasywaB.PasywaBIII.PasywaBIII3();
//         String nazwa = a.getClass().getSimpleName();
//         nazwa = nazwa.substring(6);
//         System.out.println("nazwa "+nazwa);
//         BilansJednostkaInna.Aktywa aa = new BilansJednostkaInna.Aktywa();
//         nazwa = aa.getClass().getSimpleName();
//         if (nazwa.length()>6) {
//            nazwa = nazwa.substring(6);
//         }
//         System.out.println("nazwa "+nazwa);
//            Field[] fields = a.getClass().getDeclaredFields();
//            List<Field> l = Arrays.asList(fields);
//            l.forEach((Field la) -> {
//                System.out.println(la.getName());
//            });
            TPozycjaSprawozdania b = new TPozycjaSprawozdania();
            a.pasywaBIII3A = b;
            System.out.println(a.pasywaBIII3A.toString());
            System.out.println(b.getClass().getSuperclass());
    }

    private static void oo(TPozycjaSprawozdania pasywaBIII3A) {
        System.out.println(pasywaBIII3A.toString());
    }

    
}
