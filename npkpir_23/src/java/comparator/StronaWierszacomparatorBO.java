/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.StronaWiersza;
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
public class StronaWierszacomparatorBO implements Comparator<StronaWiersza> {

    @Override
    public int compare(StronaWiersza o1, StronaWiersza o2) {
        String datao1 = o1.getDokfk().getDatadokumentu();
        String datao2 = o2.getDokfk().getDatadokumentu();
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
        int zwrot = datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1);
        if (zwrot == 0 && o1.getWiersz().getDataWalutyWiersza() != null && o2.getWiersz().getDataWalutyWiersza() != null) {
            int datawiersza1 = Integer.parseInt(o1.getWiersz().getDataWalutyWiersza());
            int datawiersza2 = Integer.parseInt(o2.getWiersz().getDataWalutyWiersza());
            zwrot = datawiersza1 < datawiersza2 ? -1 : (datawiersza1 == datawiersza2 ? 0 : 1);
        }
        return zwrot;
    }
    
}
