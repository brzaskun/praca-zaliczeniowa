/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import data.Data;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018Bean {

    static TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych naglowek(String datasporzadzenia, String okresod, String okresdo) {
        TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych naglowek = new TNaglowekSprawozdaniaFinansowegoJednostkaInnaWZlotych();
        try {
            naglowek.dataSporzadzenia = Data.dataoddo(datasporzadzenia);
            naglowek.okresOd = Data.dataoddo(okresod);
            naglowek.okresDo = Data.dataoddo(okresdo);
            naglowek.wariantSprawozdania = "1";
            naglowek.kodSprawozdania = pobierzkodsprawozdania();
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(SprawozdanieFin2018Bean.class.getName()).log(Level.SEVERE, null, ex);
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

    static JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego wprowadzenieDoSprawozdaniaFinansowego(String okresod, String okresdo) {
        JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego wprowadzenie = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego();
        try {
            JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P1 p1 = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P1();
            p1.p1A = zrobnazwasiedziba();
            p1.p1B = zrobadres();
            p1.p1C = pobierzpodstawowadzialalnosc();
            p1.p1D = zrobidentyfikatorpodmiotu();
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
            Logger.getLogger(SprawozdanieFin2018Bean.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return wprowadzenie;
        }
    }

    private static TNazwaSiedziba zrobnazwasiedziba() {
        TNazwaSiedziba nazwasiedziba = new TNazwaSiedziba();
        nazwasiedziba.nazwaFirmy = "Nazwa firmy";
        nazwasiedziba.siedziba = zrobsiedziba();
        return nazwasiedziba;
    }

    private static TSiedziba zrobsiedziba() {
        TSiedziba siedziba = new TSiedziba();
        siedziba.wojewodztwo = "Zachodniopomorskie";
        siedziba.powiat = "M.Szczecin";
        siedziba.gmina = "Szczecin";
        siedziba.miejscowosc = "Szczecin";
        return siedziba;
    }

    private static TAdresZOpcZagranicznym zrobadres() {
        TAdresZOpcZagranicznym adres = new TAdresZOpcZagranicznym();
        adres.adres = zrobadrespolski();
        return adres;
    }

    private static TAdresPolski zrobadrespolski() {
        TAdresPolski adres = new TAdresPolski();
        adres.kodKraju = zrobkodkraju();
        adres.wojewodztwo = "Zachodniopomorskie";
        adres.powiat = "Powiat";
        adres.gmina = "Gmina";
        adres.poczta = "Poczta";
        adres.kodPocztowy = "70-100";
        adres.miejscowosc = "Miejscowosc";
        adres.ulica = "Ulica";
        adres.nrDomu = "5";
        adres.nrLokalu = "12";
        return adres;
    }

    private static TKodKraju zrobkodkraju() {
        return TKodKraju.PL;
    }

    private static PKDPodstawowaDzialalnosc pobierzpodstawowadzialalnosc() {
        PKDPodstawowaDzialalnosc dzialanosc = new PKDPodstawowaDzialalnosc();
        List<String> kodPKD = dzialanosc.getKodPKD();
        kodPKD.add("7911A");
        return dzialanosc;
    }

    private static TIdentyfikatorPodmiotu zrobidentyfikatorpodmiotu() {
        TIdentyfikatorPodmiotu id = new TIdentyfikatorPodmiotu();
        id.krs = "0000009673";
        //tylko jeden numer jest wymagany
        //id.nip = "8511005008";
        return id;
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
            Logger.getLogger(SprawozdanieFin2018Bean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zakres;
    }

    public static <T extends TKwotyPozycji> T naniesKwotyAB(T a, BigDecimal kwotaA, BigDecimal kwotaB) {
        a.kwotaA = kwotaA;
        a.kwotaB = kwotaB;
        return a;
    }

    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsuma(T a, T b) {
        TPozycjaSprawozdania c = new TPozycjaSprawozdania();
        c.kwotaA = a.kwotaA.min(b.kwotaA);
        c.kwotaB = a.kwotaB.min(b.kwotaB);
        return c;
    }

    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsuma(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.add(b.kwotaA).min(c.kwotaA);
        d.kwotaB = a.kwotaB.add(b.kwotaB).min(c.kwotaB);
        return d;
    }
    
    public static <T extends TKwotyPozycji> TPozycjaSprawozdania zrobsumaminus(T a, T b, T c) {
        TPozycjaSprawozdania d = new TPozycjaSprawozdania();
        d.kwotaA = a.kwotaA.min(b.kwotaA).min(c.kwotaA);
        d.kwotaB = a.kwotaB.min(b.kwotaB).min(c.kwotaB);
        return d;
    }
}
