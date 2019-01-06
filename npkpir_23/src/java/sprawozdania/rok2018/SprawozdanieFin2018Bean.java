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
    
}
