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
import javax.xml.datatype.DatatypeConfigurationException;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018Bean {

    public static TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych naglowek(String datasporzadzenia, String okresod, String okresdo) {
        TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych naglowek = new TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych();
        try {
            naglowek.dataSporzadzenia = Data.dataoddo(datasporzadzenia);
            naglowek.okresOd = Data.dataoddo(okresod);
            naglowek.okresDo = Data.dataoddo(okresdo);
            naglowek.wariantSprawozdania = "1";
            naglowek.kodSprawozdania = pobierzkodsprawozdania();
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(SprawozdanieFin2018Bean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return naglowek;
        }
    }

    private static TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych.KodSprawozdania pobierzkodsprawozdania() {
        TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych.KodSprawozdania kodsprawozdania = new TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych.KodSprawozdania();
        kodsprawozdania.kodSystemowy = kodsprawozdania.getKodSystemowy();
        kodsprawozdania.wersjaSchemy = kodsprawozdania.getWersjaSchemy();
        kodsprawozdania.value = "SprFinJednostkaInnaWZlotych";
        return  kodsprawozdania;
    }

    public static sprawozdania.v12.JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego wprowadzenieDoSprawozdaniaFinansowego(Podatnik podatnik, String okresod, String okresdo) {
        JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego wprowadzenie = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego();
        try {
            JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P1 p1 = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P1();
            p1.p1A = zrobnazwasiedziba(podatnik);
            p1.p1B = zrobadres(podatnik);
            p1.p1C = pobierzpodstawowadzialalnosc(podatnik);
            p1.p1D = zrobidentyfikatorpodmiotuNIP(podatnik);
            p1.p1E = zrobidentyfikatorpodmiotuKRS(podatnik);
            wprowadzenie.p1 = p1;
            wprowadzenie.setP3(pobierzzakresdat(okresod, okresdo));
            JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P5 p5 = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P5();
            p5.p5A = true;
            p5.p5B = true;
            wprowadzenie.p5 = p5;
            //tego nie bo dotyczy czy po polaczenu spolek
            //        JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P6 p6 = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P6();
            //        p6.p6A = true;
            JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P7 p7 = uzupelnijp7();
            wprowadzenie.p7 = p7;
        } catch (Exception ex) {
            // Logger.getLogger(SprawozdanieFin2018Bean.class.getName()).log(Level.SEVERE, null, ex);
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

    private static PKDPodstawowaDzialalnosc pobierzpodstawowadzialalnosc(Podatnik podatnik) {
        PKDPodstawowaDzialalnosc dzialanosc = new PKDPodstawowaDzialalnosc();
        List<String> kodPKD = dzialanosc.getKodPKD();
        kodPKD.add(podatnik.getKodPKD());
        return dzialanosc;
    }

    private static String zrobidentyfikatorpodmiotuNIP(Podatnik podatnik) {
        String zwrot = podatnik.getNip();
        //tylko jeden numer jest wymagany
        //id.nip = "8511005008";
        return zwrot;
    }
    
    private static String zrobidentyfikatorpodmiotuKRS(Podatnik podatnik) {
        String zwrot = podatnik.getImie();
        //tylko jeden numer jest wymagany
        //id.nip = "8511005008";
        return zwrot;
    }

    private static JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P7 uzupelnijp7() {
        JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P7 p7 = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P7();
        p7.getP7A().add("Szczegółowy opis polityki rachunkowości znajduje się w sekcji \"Dodatkowe informacje i objaśnienia\", w Załączniku 1 (plik polityka_rachunkowosci.pdf)");
        p7.getP7B().add("Kompletny opis wyceny aktywów i pasywów znajduje się w Załączniku 1 (plik \"polityka_rachunkowosci.pdf). W szczególności metody wyceny: ");
        p7.getP7B().add("Wartości niematerialnych i prawnych: por. Załącznik 1, sekcja III");
        p7.getP7B().add("Środków trwałych:  por. Załącznik 1, sekcja IV");
        p7.getP7B().add("Inwestycje w nieruchomości i prawa wycenia się w cenie nabycia");
        p7.getP7B().add("Należności: po. Załącznik 1, sekcja VI");
        p7.getP7B().add("Zapasy: por. Załącznik 1, sekcja VII");
        p7.getP7B().add("Kosztów działalności operacyjnej: por. Załącznik 1, skecja VIII");
        p7.getP7B().add("Aktywa i pasywa w walutach obcych: por. Załącznik 1, sekcja IX");
        p7.getP7C().add("Zgodnie z polityką rachunkowości przedsiębiorstwa - patrz Załącznik 1.");
        p7.getP7D().add("Zgodnie z polityką rachunkowości przedsiębiorstwa - patrz Załącznik 1.");
        return p7;
    }

    private static TZakresDatSF pobierzzakresdat(String okresod, String okresdo) {
        TZakresDatSF zakres = new TZakresDatSF();
        try {
            zakres.dataOd = Data.dataoddo(okresod);
            zakres.dataDo = Data.dataoddo(okresdo);
        } catch (DatatypeConfigurationException ex) {
            // Logger.getLogger(SprawozdanieFin2018Bean.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsumaplusminus(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.add(b.kwotaA).subtract(c.kwotaA);
        d.kwotaB = a.kwotaB.add(b.kwotaB).subtract(c.kwotaB);
        return d;
    }
    
        
    public static TPozycjaSprawozdania zrobTPoztchaSprawozdania(String pozycjaString, List<PozycjaRZiSBilans> l) {
        TPozycjaSprawozdania a = new TPozycjaSprawozdania();
        naniesKwotyAB(pozycjaString, a, l);
        return a;
    }

    
}
