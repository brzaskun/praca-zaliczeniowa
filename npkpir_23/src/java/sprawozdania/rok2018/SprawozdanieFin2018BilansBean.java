/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import java.math.BigDecimal;
import static sprawozdania.rok2018.SprawozdanieFin2018Bean.naniesKwotyAB;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static BilansJednostkaInna.Aktywa.AktywaB generyj_AktywaB() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static TPozycjaSprawozdania generyj_AktywaC() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static TPozycjaSprawozdania generyj_AktywaD() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
