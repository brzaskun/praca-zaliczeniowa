/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Dok;
import error.E;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Dokcomparator implements Comparator<Dok> {

    @Override
    public int compare(Dok o1, Dok o2) {
        int zwrot = 0;
        String datao1 = o1.getDataWyst();
        String datao2 = o2.getDataWyst();
        if (datao1!=null&&datao2!=null) {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date datao1date = null;
            Date datao2date = null;
            try {
                 datao1date = formatter.parse(datao1);
            } catch (ParseException ex) {
                E.e(ex);
            }
            try {
                datao2date = formatter.parse(datao2);
            } catch (ParseException ex) {
                E.e(ex);
            }
            zwrot = (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
        }
        // Jeśli dataWyst są równe, porównanie według dataK
        if (zwrot == 0) {
            Date datao1dateK = o1.getDataK();
            Date datao2dateK = o2.getDataK();
            if (datao1dateK != null && datao2dateK != null) {
                zwrot = datao1dateK.before(datao2dateK) ? -1 : (datao1dateK.equals(datao2dateK) ? 0 : 1);
            }
        }
        return zwrot;
    }
    
}
