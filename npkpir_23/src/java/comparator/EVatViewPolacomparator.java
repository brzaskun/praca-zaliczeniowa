/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.EVatViewPola;
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
public class EVatViewPolacomparator implements Comparator<EVatViewPola> {

    @Override
    public int compare(EVatViewPola o1, EVatViewPola o2) {
        String datao1 = o1.getDataWyst();
        String datao2 = o2.getDataWyst();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
        } catch (ParseException ex) {
            E.e(ex);
            return 1;
        }
        try {
            datao2date = formatter.parse(datao2);
        } catch (ParseException ex) {
            E.e(ex);
            return 1;
        }
        return (datao1date.before(datao2date) ? -1 : (datao1date.equals(datao2date) ? 0 : 1));
    }

}
