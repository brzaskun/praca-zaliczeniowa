/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.rok2018;

import data.Data;
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

    static JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego wprowadzenieDoSprawozdaniaFinansowego() {
        JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego wprowadzenie = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego();
        JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P1 p1 = new JednostkaInna.WprowadzenieDoSprawozdaniaFinansowego.P1();
        p1.p1A = zrobnazwasiedziba();
        p1.p1B = zrobadres();
        wprowadzenie.p1 = p1;
        return wprowadzenie;
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
        adres.nrDomu = "Nrdomu";
        adres.nrLokalu = "Nrlokalu";
        return adres;
    }

    private static TKodKraju zrobkodkraju() {
        return TKodKraju.PL;
    }
    
}
