/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;


import entity.EVatwpisSuper;
import entityfk.Wiersz;
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
public class EVatwpisFKcomparator implements Comparator<EVatwpisSuper> {

    @Override
    public int compare(EVatwpisSuper o1, EVatwpisSuper o2) {
        Wiersz wo1 = o1.getWiersz();
        Wiersz wo2 = o2.getWiersz();
        String datao1 = wo1 != null ? o1.getDataoperacji() : o1.getDokfk().getDataoperacji();
        String datao2 = wo2 != null ? o2.getDataoperacji() : o2.getDokfk().getDataoperacji();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
             datao1date = formatter.parse(datao1);
        } catch (ParseException ex) {
            // Logger.getLogger(Dokcomparator.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        try {
            datao2date = formatter.parse(datao2);
        } catch (ParseException ex) {
            // Logger.getLogger(Dokcomparator.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
        return (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
    }
    
}
