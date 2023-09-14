/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Dokfk;
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
public class Dokfkcomparator implements Comparator<Dokfk> {

    @Override
    public int compare(Dokfk o1, Dokfk o2) {
        int zwrot = 0;
        try {
            String datao1 = ((Dokfk) o1).getDatadokumentu();
            String datao2 = ((Dokfk) o2).getDatadokumentu();
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date datao1date = null;
            Date datao2date = null;
            if (datao1!=null&&datao1.length()==10 && datao2!=null&&datao2.length()==10) {
                datao1date = formatter.parse(datao1);
                datao2date = formatter.parse(datao2);
                if (datao1date.before(datao2date)) {
                    zwrot = -1;
                } else if (datao1date.after(datao2date)) {
                    zwrot =  1;
                } else {
                    zwrot = porownajseriedok(((Dokfk) o1),((Dokfk) o2));
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    private static int porownajseriedok(Dokfk o1, Dokfk o2) {
        String seriao1 = o1.getSeriadokfk();
        String seriao2 = o2.getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1,o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }
    
    private static int porownajnrserii(Dokfk o1, Dokfk o2) {
        int seriao1 = o1.getLp();
        int seriao2 = o2.getLp();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
}
