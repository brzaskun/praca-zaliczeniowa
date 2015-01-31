/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sortfunction;

import entity.Faktura;
import entityfk.Dokfk;
import entityfk.Konto;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Singleton;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Singleton
public class KontoSortBean {
    public static int sortZaksiegowaneDok(Object o1, Object o2) {
       String numer1 = ((Konto) o1).getPelnynumer();
       int level1 = ((Konto) o1).getLevel();
       String numer2 = ((Konto) o2).getPelnynumer();
       int level2 = ((Konto) o2).getLevel();
       int porownajlevele = porownaj(level1, level2);
       if (porownajlevele==0) {
           int porownajdlugosci = porownaj(numer1.length(), numer2.length());
           return porownajdlugosci;
       }
        return porownajlevele;
    }
    
//    
    private static int porownaj(int o1, int o2) {
        if (o1 < o2) {
            return -1;
        } else if (o1 > o2) {
            return 1;
        } else {
           return 0;
        }
    }
    
    private static Map<String, Integer> rozbijwzor(String nrs) {
        Map<String, Integer> mapa = new HashMap<>();
        String[] rozbicie = nrs.split("/");
        int dl = rozbicie.length;
        for (int i = 0 ; i < dl; i++) {
            switch (rozbicie[i]) {
                case "n":
                case "N":
                    mapa.put("nr", i);
                    break;
                case "m":
                    mapa.put("mc", i);
                    break;
                case "r":
                    mapa.put("rok", i);
                    break;
                case "s":
                    break;
            }
        }
        return mapa;
    }

    
    public static void main(String[] args) {
        Map<String, Integer> mapa = rozbijwzor("N/r/m");
        String nro1 = "1/2015/01";
        String nro2 = "2/2015/01";
        String[] nro1T = nro1.split("/");
        String[] nro2T = nro2.split("/");
        int dlugosc = nro1T.length;
        int wynik = 0;
        int ob1 = 0;
        int ob2 = 0;
        if (mapa.containsKey("rok")) {
            int rok = mapa.get("rok");
            ob1 = Integer.parseInt(nro1T[rok]);
            ob2 = Integer.parseInt(nro2T[rok]);
            wynik = porownaj(ob1, ob2);
            System.out.println(wynik);
        } 
        if (mapa.containsKey("mc")){
            if (wynik == 0 ) {
                int mc = mapa.get("mc");
                ob1 = Integer.parseInt(nro1T[mc]);
                ob2 = Integer.parseInt(nro2T[mc]);
                wynik = porownaj(ob1, ob2);
                System.out.println(wynik);
            }
        }
        if (mapa.containsKey("nr")){
            if (wynik == 0 ) {
            int nr = mapa.get("nr");
                ob1 = Integer.parseInt(nro1T[nr]);
                ob2 = Integer.parseInt(nro2T[nr]);
                wynik = porownaj(ob1, ob2);
                System.out.println(wynik);
            }
        }
       System.out.println("koniec "+wynik);
    }

   //public static int sortZaksiegowaneDok2(Object o1, Object o2) {
//       String nazwa1 = (String) o1;
//       String nazwa2 = (String) o2;
//       int porownajlevele = nazwa1.compareTo(nazwa2);
//       return porownajlevele;
//    }
//    
       
}
