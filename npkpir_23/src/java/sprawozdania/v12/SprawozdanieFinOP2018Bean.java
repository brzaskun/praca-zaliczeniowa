/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.v12;


import data.Data;
import entity.Podatnik;
import entityfk.PozycjaRZiSBilans;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SprawozdanieFinOP2018Bean {

    public static TNaglowekSprawozdaniaFinansowegoOpWZlotych naglowek(String datasporzadzenia, String okresod, String okresdo) {
        TNaglowekSprawozdaniaFinansowegoOpWZlotych naglowek = new TNaglowekSprawozdaniaFinansowegoOpWZlotych();
        try {
            naglowek.dataSporzadzenia = Data.dataStringToXMLGregorian(datasporzadzenia);
            naglowek.okresOd = Data.dataStringToXMLGregorian(okresod);
            naglowek.okresDo = Data.dataStringToXMLGregorian(okresdo);
            naglowek.wariantSprawozdania = "1";
            naglowek.kodSprawozdania = pobierzkodsprawozdania();
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(SprawozdanieFinOP2018Bean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return naglowek;
        }
    }

    private static TNaglowekSprawozdaniaFinansowegoOpWZlotych.KodSprawozdania pobierzkodsprawozdania() {
        TNaglowekSprawozdaniaFinansowegoOpWZlotych.KodSprawozdania kodsprawozdania = new TNaglowekSprawozdaniaFinansowegoOpWZlotych.KodSprawozdania();
        kodsprawozdania.kodSystemowy = kodsprawozdania.getKodSystemowy();
        kodsprawozdania.wersjaSchemy = kodsprawozdania.getWersjaSchemy();
        kodsprawozdania.value = "SprFinOpWZlotych";
        return  kodsprawozdania;
    }

    public static JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp wprowadzenieDoSprawozdaniaFinansowego(Podatnik podatnik, String okresod, String okresdo) {
        JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp wprowadzenie = new JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp();
        try {
            JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P1 p1 = new JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P1();
            p1.p1A = zrobnazwasiedziba(podatnik);
            p1.p1B = zrobadres(podatnik);
            p1.p1C = zrobidentyfikatorpodmiotu(podatnik);
            wprowadzenie.p1 = p1;
            wprowadzenie.setP3(pobierzzakresdat(okresod, okresdo));
            JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P4 p4 = new JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P4();
            p4.p4A = true;
            p4.p4B = true;
            wprowadzenie.p4 = p4;
            //tego nie bo dotyczy czy po polaczenu spolek
            //        JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P6 p6 = new JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P6();
            //        p6.p6A = true;
            JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P5 p5 = uzupelnijp5();
            wprowadzenie.p5 = p5;
        } catch (Exception ex) {
            // Logger.getLogger(SprawozdanieFinOP2018Bean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return wprowadzenie;
        }
    }

    private static TNazwaSiedziba zrobnazwasiedziba(Podatnik podatnik) {
        TNazwaSiedziba nazwasiedziba = new TNazwaSiedziba();
        nazwasiedziba.nazwaFirmy = podatnik.getNazwaRejestr();
        nazwasiedziba.siedziba = zrobsiedziba(podatnik);
        return nazwasiedziba;
    }

    private static TSiedziba zrobsiedziba(Podatnik podatnik) {
        TSiedziba siedziba = new TSiedziba();
        siedziba.wojewodztwo = podatnik.getWojewodztwo();
        siedziba.powiat = podatnik.getPowiat();
        siedziba.gmina = podatnik.getGmina();
        siedziba.miejscowosc = podatnik.getMiejscowosc();
        return siedziba;
    }

    private static TAdresZOpcZagranicznym zrobadres(Podatnik podatnik) {
        TAdresZOpcZagranicznym adres = new TAdresZOpcZagranicznym();
        adres.adres = zrobadrespolski(podatnik);
        return adres;
    }

    private static TAdresPolski2 zrobadrespolski(Podatnik podatnik) {
        TAdresPolski2 adres = new TAdresPolski2();
        adres.kodKraju = zrobkodkraju();
        adres.wojewodztwo = podatnik.getWojewodztwo();
        adres.powiat = podatnik.getPowiat();
        adres.gmina = podatnik.getGmina();
        adres.poczta = podatnik.getPoczta();
        adres.kodPocztowy = podatnik.getKodpocztowy();
        adres.miejscowosc = podatnik.getMiejscowosc();
        adres.ulica = podatnik.getUlica();
        adres.nrDomu = podatnik.getNrdomu();
        adres.nrLokalu = podatnik.getNrlokalu();
        return adres;
    }

    private static TKodKraju zrobkodkraju() {
        return TKodKraju.PL;
    }


    private static String zrobidentyfikatorpodmiotu(Podatnik podatnik) {
        String zwrot = podatnik.getNip();
        //tylko jeden numer jest wymagany
        //id.nip = "8511005008";
        return zwrot;
    }

    private static JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P5 uzupelnijp5() {
        JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P5 p5 = new JednostkaOp.WprowadzenieDoSprawozdaniaFinansowegoJednostkaOp.P5();
        p5.getP5A().add("Organizacja posada środki finansowe w walucie polskiej wyceniane według wartości nominalnej. Wyposażenie i środki trwałe wyceniane są wg nominalnych cen zakupu. Amortyzacja dokonywana jest według zasad określonych w ustawie o podatku dochodowym osób prawnych.");
        p5.getP5B().add("Wynik finansowych ustalany jest z uwzględnieniem wyodrębnienia rodzajów działań określonych w ustawie o działalności pożytku publicznego i o wolontariacie");
        p5.getP5C().add("Organizacja sporządza sprawozdanie finansowe dla organizacji pozarządowych, określonych w art. 3 ust. 2 ustawy o działalności pożytku publicznego i o wolontariacie, zgodnie z załącznikiem 6 do ustawy o rachunkowości.");
        return p5;
    }

    private static TZakresDatSF pobierzzakresdat(String okresod, String okresdo) {
        TZakresDatSF zakres = new TZakresDatSF();
        try {
            zakres.dataOd = Data.dataStringToXMLGregorian(okresod);
            zakres.dataDo = Data.dataStringToXMLGregorian(okresdo);
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(SprawozdanieFinOP2018Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zakres;
    }

    public static <T extends TKwotyPozycji> T naniesKwotyAB(T a, BigDecimal kwotaA, BigDecimal kwotaB) {
        a.kwotaA = kwotaA;
        a.kwotaB = kwotaB;
        return a;
    }

    public static <T extends TKwotyPozycji> T naniesKwotyAB(String pozycjaString, T a, List<PozycjaRZiSBilans> l) {
        BigDecimal kwotaA = BigDecimal.ZERO;
        BigDecimal kwotaB = BigDecimal.ZERO;
        pozycjaString = pozycjaString.toLowerCase();
        for (Iterator<PozycjaRZiSBilans> it = l.iterator(); it.hasNext();) {
            PozycjaRZiSBilans p = it.next();
            String poz = p.getPozycjaString().replace(".", "");
            poz = poz.replace("-", "");
            poz = poz.replace("(", "");
            poz = poz.replace(")", "");
            poz = poz.toLowerCase();
            if (poz.equals(pozycjaString)) {
                kwotaA = new BigDecimal(Z.z(p.getKwota())).setScale(2, BigDecimal.ROUND_HALF_UP);
                kwotaB = new BigDecimal(Z.z(p.getKwotabo())).setScale(2, BigDecimal.ROUND_HALF_UP);
                it.remove();
                break;
            }
        }
        a.kwotaA = kwotaA;
        a.kwotaB = kwotaB;
        return a;
    }
    
    
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsuma(T a, T b) {
        TPozycjaSprawozdania c = new TPozycjaSprawozdania();
        c.kwotaA = a.kwotaA.subtract(b.kwotaA);
        c.kwotaB = a.kwotaB.subtract(b.kwotaB);
        return c;
    }

    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsuma(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.add(b.kwotaA).subtract(c.kwotaA);
        d.kwotaB = a.kwotaB.add(b.kwotaB).subtract(c.kwotaB);
        return d;
    }
    
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsumaminus(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.subtract(b.kwotaA).subtract(c.kwotaA);
        d.kwotaB = a.kwotaB.subtract(b.kwotaB).subtract(c.kwotaB);
        return d;
    }
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsumaplusplusminus(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.add(b.kwotaA).subtract(c.kwotaA);
        d.kwotaB = a.kwotaB.add(b.kwotaB).subtract(c.kwotaB);
        return d;
    }
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsumaplusminus(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.add(b.kwotaA).subtract(c.kwotaA);
        d.kwotaB = a.kwotaB.add(b.kwotaB).subtract(c.kwotaB);
        return d;
    }
    
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsumaplusminusplusminus(T a, T b, T c, T d, T e) {
        TPozycjaSprawozdania z = new TPozycjaSprawozdania();
        z.kwotaA = a.kwotaA.add(b.kwotaA).subtract(c.kwotaA).add(d.kwotaA).subtract(e.kwotaA);
        z.kwotaB = a.kwotaB.add(b.kwotaB).subtract(c.kwotaB).add(d.kwotaB).subtract(e.kwotaB);
        return z;
    }
     
     
        
    public static TPozycjaSprawozdania zrobTPoztchaSprawozdania(String pozycjaString, List<PozycjaRZiSBilans> l) {
        TPozycjaSprawozdania a = new TPozycjaSprawozdania();
        naniesKwotyAB(pozycjaString, a, l);
        return a;
    }

    
}
