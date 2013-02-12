/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dok;
import entity.Podatnik;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Podatnikcomparator implements Comparator<Podatnik> {

    @Override
    public int compare(Podatnik o1, Podatnik o2) {
        String datao1 = o1.getNazwapelna();
        String datao2 = o2.getNazwapelna();
        return datao1.compareTo(datao2);
    }
    
}
