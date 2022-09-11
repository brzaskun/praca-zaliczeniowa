/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddable.Okres;
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
public class Okrescomparator implements Comparator<Okres> {

    //najstarsza jest pierwsza
    @Override
    public int compare(Okres o1, Okres o2) {
        String datao1 = o1.getRok()+"-"+o1.getMc()+"-"+01;
        String datao2 = o2.getRok()+"-"+o2.getMc()+"-"+01;
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
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
        
        return (datao1date.before(datao2date) ? 1 : (datao1date.equals(datao2date) ? 0 : -1));
    }
    
}
