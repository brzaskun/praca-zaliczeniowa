/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import entityfk.Konto;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class Kontocomparator implements Comparator<Konto> {

    @Override
    public int compare(Konto o1, Konto o2) {
        String datao1 = o1.getPelnynumer();
        String datao2 = o2.getPelnynumer();
        String[] o1l  = datao1.split("-");
        String[] o2l = datao2.split("-");
        if (o1l.length > o2l.length) {
            return 1;
        } else if ((o1l.length < o2l.length)) {
            return -1;
        } else {
            return porownaj(datao1, datao2);
        }
    }

    private int porownaj(String datao1, String datao2) {
        String[] o1l  = datao1.split("-");
        String[] o2l = datao2.split("-");
        int dlugosc = o1l.length;
        int odpowiedz = 0;
        for (int i = 0 ; i < dlugosc; i++) {
            Integer o1 = Integer.parseInt(o1l[i]);
            Integer o2 = Integer.parseInt(o2l[i]);
            if (odpowiedz == 0) {
                if (o1 > o2) {
                    odpowiedz = 1;
                } else if ((o1 < o2)) {
                    odpowiedz = -1;
                } else {
                    odpowiedz = 0;
                }
            }
        }
        return odpowiedz;
    }
    
}
