/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;

import embeddablefk.SaldoKonto;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class SaldoKontocomparator implements Comparator<SaldoKonto> {

    
    @Override
    public int compare(SaldoKonto o1, SaldoKonto o2) {
        try {
            String numer1 = o1.getKonto().getPelnynumer();
            String numer2 = o2.getKonto().getPelnynumer();
            String[] splitnumer1 = numer1.split("-");
            String[] splitnumer2 = numer2.split("-");
            int wynik = 0;
            wynik = porownaj(splitnumer1.length, splitnumer2.length);
            if (wynik == 0) {
                for (int i = 0; i < splitnumer1.length; i++) {
                    int n1 = Integer.parseInt(splitnumer1[i]);
                    int n2 = Integer.parseInt(splitnumer2[i]);
                    int pornumer = porownaj(n1, n2);
                    if (pornumer == 0) {
                        wynik = 0;
                    } else {
                        wynik = pornumer;
                        break;
                    }
                }
            } else if (wynik == -1) {
                for (int i = 0; i < splitnumer1.length; i++) {
                    int n1 = Integer.parseInt(splitnumer1[i]);
                    int n2 = Integer.parseInt(splitnumer2[i]);
                    int pornumer = porownaj(n1, n2);
                    if (pornumer == 0) {
                        wynik = -1;
                    } else {
                        wynik = pornumer;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < splitnumer2.length; i++) {
                    int n1 = Integer.parseInt(splitnumer1[i]);
                    int n2 = Integer.parseInt(splitnumer2[i]);
                    int pornumer = porownaj(n1, n2);
                    if (pornumer == 0) {
                        wynik = 1;
                    } else {
                        wynik = pornumer;
                        break;
                    }
                }
            }
            return wynik;
        } catch (Exception e) {
            return 0;
        }
    }

  
    private static int porownaj(int o1, int o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 > o2) {
            return 1;
        } else {
            return 0;
        }
    }
//    @Override
//    public int compare(Konto o1, Konto o2) {
//        String datao1 = o1.getPelnynumer();
//        String datao2 = o2.getPelnynumer();
//        String[] o1l  = datao1.split("-");
//        String[] o2l = datao2.split("-");
//        if (o1l.length > o2l.length) {
//            return 1;
//        } else if ((o1l.length < o2l.length)) {
//            return -1;
//        } else {
//            return porownaj(datao1, datao2);
//        }
//    }
//
//    private int porownaj(String datao1, String datao2) {
//        String[] o1l  = datao1.split("-");
//        String[] o2l = datao2.split("-");
//        int dlugosc = o1l.length;
//        int odpowiedz = 0;
//        for (int i = 0 ; i < dlugosc; i++) {
//            Integer o1 = Integer.parseInt(o1l[i]);
//            Integer o2 = Integer.parseInt(o2l[i]);
//            if (odpowiedz == 0) {
//                if (o1 > o2) {
//                    odpowiedz = 1;
//                } else if ((o1 < o2)) {
//                    odpowiedz = -1;
//                } else {
//                    odpowiedz = 0;
//                }
//            }
//        }
//        return odpowiedz;
//    }
//    
}
