/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.KontoZapisy;
import entityfk.StronaWiersza;
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
public class KontoZapisycomparator1 implements Comparator<StronaWiersza> {

    @Override
    public int compare(StronaWiersza o1, StronaWiersza o2) {
        try {
            String datao1 = o1.getWiersz().getDokfk().getDatadokumentu();
            String datao2 = o2.getWiersz().getDokfk().getDatadokumentu();
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
                    return porownajseriedok(((StronaWiersza) o1), ((StronaWiersza) o2));
                }
            } catch (Exception e) {
                E.e(e);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return 0;

    }

    private int porownajseriedok(StronaWiersza o1, StronaWiersza o2) {
        String seriao1 = o1.getWiersz().getDokfk().getSeriadokfk();
        String seriao2 = o2.getWiersz().getDokfk().getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1, o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }

    private int porownajnrserii(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getDokfk().getNrkolejnywserii();
        int seriao2 = o2.getWiersz().getDokfk().getNrkolejnywserii();
        if (seriao1 == seriao2) {
            return porownajnumerwiersza(o1, o2);
        } else if (seriao1 < seriao2) {
            return -1;
        } else {
            return 1;
        }
    }

    private int porownajnumerwiersza(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getIdporzadkowy();
        int seriao2 = o2.getWiersz().getIdporzadkowy();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2) {
            return -1;
        } else {
            return 1;
        }
    }

}
