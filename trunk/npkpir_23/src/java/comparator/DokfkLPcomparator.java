/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Dokfk;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class DokfkLPcomparator implements Comparator<Dokfk> {

    @Override
    public int compare(Dokfk o1, Dokfk o2) {
        String datao1 = ((Dokfk) o1).getDatadokumentu();
        String datao2 = ((Dokfk) o2).getDatadokumentu();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
            datao2date = formatter.parse(datao2);
            if (datao1date.before(datao2date)) {
                return -1;
            } else if (datao1date.after(datao2date)) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
       
}
