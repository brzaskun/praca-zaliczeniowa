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
public class SprawozdanieFinOP2018BilansBean {
    public static BilansJednostkaOp generujbilans(List<PozycjaRZiSBilans> aktywa, List<PozycjaRZiSBilans> pasywa) {
        BilansJednostkaOp bil = new BilansJednostkaOp();
        bil.setAktywa(generujaktywa(aktywa));
        bil.setPasywa(generujpasywa(pasywa));
        return bil;
    }

    private static BilansJednostkaOp.Pasywa generujpasywa(List<PozycjaRZiSBilans> l) {
        BilansJednostkaOp.Pasywa a = new BilansJednostkaOp.Pasywa();
        naniesKwotyAB("Pasywa", a, l);
        a.setPasywaA(generuj_PasywaA(l));
        a.setPasywaB(generuj_PasywaB(l));
        return a;
    }

    private static BilansJednostkaOp.Aktywa generujaktywa(List<PozycjaRZiSBilans> l) {
        BilansJednostkaOp.Aktywa a = new BilansJednostkaOp.Aktywa();
        naniesKwotyAB("Aktywa", a, l);
        a.setAktywaA(generuj_AktywaA(l));
        a.setAktywaB(generuj_AktywaB(l));
        a.setAktywaC(generuj_AktywaC(l));
        return a;
    }

    private static BilansJednostkaOp.Pasywa.PasywaA generuj_PasywaA(List<PozycjaRZiSBilans> l) {
        BilansJednostkaOp.Pasywa.PasywaA a = new BilansJednostkaOp.Pasywa.PasywaA();
        naniesKwotyAB("A", a, l);
        a.pasywaAI = zrobTPoztchaSprawozdania("AI",l);
        a.pasywaAII = zrobTPoztchaSprawozdania("AII",l);
        a.pasywaAIII = zrobTPoztchaSprawozdania("AIII",l);
        a.pasywaAIV = zrobTPoztchaSprawozdania("AIV",l);
        return a;
    }

    private static BilansJednostkaOp.Pasywa.PasywaB generuj_PasywaB(List<PozycjaRZiSBilans> l) {
        BilansJednostkaOp.Pasywa.PasywaB a = new BilansJednostkaOp.Pasywa.PasywaB();
        naniesKwotyAB("b", a, l);
        a.pasywaBI = zrobTPoztchaSprawozdania("BI",l);
        a.pasywaBII = zrobTPoztchaSprawozdania("BII",l);
        a.pasywaBIII = zrobTPoztchaSprawozdania("BIII",l);
        a.pasywaBIV = zrobTPoztchaSprawozdania("BIV",l);
        return a;
    }

    private static BilansJednostkaOp.Aktywa.AktywaA generuj_AktywaA(List<PozycjaRZiSBilans> l) {
        BilansJednostkaOp.Aktywa.AktywaA a = new BilansJednostkaOp.Aktywa.AktywaA();
        naniesKwotyAB("A", a, l);
        a.aktywaAI = zrobTPoztchaSprawozdania("AI", l);
        a.aktywaAII = zrobTPoztchaSprawozdania("AII", l);
        a.aktywaAIII = zrobTPoztchaSprawozdania("AIII", l);
        a.aktywaAIV = zrobTPoztchaSprawozdania("AIV", l);
        a.aktywaAV = zrobTPoztchaSprawozdania("AV", l);
        return a;
    }

    private static BilansJednostkaOp.Aktywa.AktywaB generuj_AktywaB(List<PozycjaRZiSBilans> l) {
        BilansJednostkaOp.Aktywa.AktywaB a = new BilansJednostkaOp.Aktywa.AktywaB();
        naniesKwotyAB("B", a, l);
        a.aktywaBI = zrobTPoztchaSprawozdania("BI", l);
        a.aktywaBII = zrobTPoztchaSprawozdania("BII", l);
        a.aktywaBIII = zrobTPoztchaSprawozdania("BIII", l);
        a.aktywaBIV = zrobTPoztchaSprawozdania("BIV", l);
        return a;
    }

    private static TPozycjaSprawozdania generuj_AktywaC(List<PozycjaRZiSBilans> l) {
        return zrobTPoztchaSprawozdania("C",l);
    }


   

    public static void main(String[] args) {
//         BilansJednostkaOp.Pasywa.PasywaB.PasywaBIII.PasywaBIII3 a = new BilansJednostkaOp.Pasywa.PasywaB.PasywaBIII.PasywaBIII3();
//         String nazwa = a.getClass().getSimpleName();
//         nazwa = nazwa.substring(6);
//         error.E.s("nazwa "+nazwa);
//         BilansJednostkaOp.Aktywa aa = new BilansJednostkaOp.Aktywa();
//         nazwa = aa.getClass().getSimpleName();
//         if (nazwa.length()>6) {
//            nazwa = nazwa.substring(6);
//         }
//         error.E.s("nazwa "+nazwa);
//            Field[] fields = a.getClass().getDeclaredFields();
//            List<Field> l = Arrays.asList(fields);
//            l.forEach((Field la) -> {
//                error.E.s(la.getName());
//            });
//            TPozycjaSprawozdania b = new TPozycjaSprawozdania();
//            a.pasywaBIII3A = b;
//            error.E.s(a.pasywaBIII3A.toString());
//            error.E.s(b.getClass().getSuperclass());
    }

    private static void oo(TPozycjaSprawozdania pasywaBIII3A) {
        error.E.s(pasywaBIII3A.toString());
    }

    
}
