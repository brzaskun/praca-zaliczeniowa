/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dok;
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
public class Dokcomparator implements Comparator<Dok> {

    @Override
    public int compare(Dok o1, Dok o2) {
        String datao1 = o1.getDataWyst();
        String datao2 = o2.getDataWyst();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
             datao1date = formatter.parse(datao1);
        } catch (ParseException ex) {
            Logger.getLogger(Dokcomparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            datao2date = formatter.parse(datao2);
        } catch (ParseException ex) {
            Logger.getLogger(Dokcomparator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
    }
    
}
