/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortfunction;

import entityfk.Konto;
import java.util.Comparator;

/**
 *
 * @author Osito
 */
public class KontonumerComparator implements Comparator<Konto>{

    private int porownaj(int o1, int o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 > o2) {
            return 1;
        } else {
           return 0;
        }
    }

    @Override
    public int compare(Konto o1, Konto o2) {
       String numer1 = o1.getPelnynumer();
       String numer2 = o2.getPelnynumer();
       String[] splitnumer1 = numer1.split("-");
       String[] splitnumer2 = numer2.split("-");
       int wynik = 0;
       wynik = porownaj(splitnumer1.length,splitnumer2.length);
       if (wynik == 0) {
          for (int i = 0; i < splitnumer1.length;i++) {
                int n1 = Integer.parseInt(splitnumer1[i]);
                int n2 = Integer.parseInt(splitnumer2[i]);
                int pornumer = porownaj(n1,n2);
                if (pornumer == 0) {
                    wynik = 0;
                } else {
                    wynik = pornumer;
                    break;
                }
            }
       } else if (wynik == -1) {
            for (int i = 0; i < splitnumer1.length;i++) {
                int n1 = Integer.parseInt(splitnumer1[i]);
                int n2 = Integer.parseInt(splitnumer2[i]);
                int pornumer = porownaj(n1,n2);
                if (pornumer == 0) {
                    wynik = -1;
                } else {
                    wynik = pornumer;
                    break;
                }
            }
        } else {
           for (int i = 0; i < splitnumer2.length;i++) {
                int n1 = Integer.parseInt(splitnumer1[i]);
                int n2 = Integer.parseInt(splitnumer2[i]);
                int pornumer = porownaj(n1,n2);
                if (pornumer == 0) {
                    wynik = 1;
                } else {
                    wynik = pornumer;
                    break;
                }
            }
       }
        return wynik;
    }

       
}
