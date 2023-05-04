/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entity.Stanowiskoprac;
import error.E;
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
public class Stanowiskocomparator implements Comparator<Stanowiskoprac> {

    //najstarsza jest pierwsza
    @Override
    public int compare(Stanowiskoprac o1, Stanowiskoprac o2) {
        int zwrot = -11;
        String datao1 = o1.getDataod();
        String datao2 = o2.getDataod();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        if (datao1!=null&&datao2!=null&&!datao1.equals("")&&!datao2.equals("")) {
            try {
                 datao1date = formatter.parse(datao1);
            } catch (Exception ex) {
                E.e(ex);
            }
            try {
                datao2date = formatter.parse(datao2);
            } catch (Exception ex) {
                E.e(ex);
            }
            zwrot = (datao1date.before(datao2date) ? 1 : (datao1date.equals(datao2date) ? 0 : -1));
        }
        return zwrot;
    }
    
}
