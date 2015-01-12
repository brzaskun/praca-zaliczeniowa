/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFaktura;

import dao.DokDAO;
import dao.FakturaDAO;
import daoFK.DokDAOfk;
import entity.Dok;
import entity.Faktura;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class FakturaBean {

    public static String uzyjwzorcagenerujnumerDok(String wzorzec, String skrot, WpisView wpisView, DokDAO dokDAO) {
        String separator = znajdzseparator(wzorzec);
        Dok ostatnidokument = dokDAO.find(skrot, wpisView.getPodatnikWpisu(), wpisView.getRokWpisu());
        String[] elementypoprzedniafakt = elementydokumentu(ostatnidokument, separator);
        return generowanie(wzorzec, separator, elementypoprzedniafakt, wpisView, 0);
    }
    
    public static String uzyjwzorcagenerujnumerFaktura(String wzorzec, WpisView wpisView, FakturaDAO faktDAO) {
        String separator = znajdzseparator(wzorzec);
        Faktura ostatnidokument = faktDAO.findOstatniaFakturaByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
        String mcostatniejfaktury = ostatnidokument.getMc();
        String[] elementypoprzedniafakt = elementydokumentu(ostatnidokument, separator);
        if (mcostatniejfaktury.equals(wpisView.getMiesiacWpisu())) {
            return generowanie(wzorzec, separator, elementypoprzedniafakt, wpisView, 0);
        } 
            return generowanie(wzorzec, separator, elementypoprzedniafakt, wpisView, 1);
    }
    
        
    private static String generowanie (String wzorzec, String separator, String[] elementypoprzedniafakt, WpisView wpisView, int nowanumeracjamc) {
        String[] elementywzorca = elementywzorca(wzorzec, separator);
        String nowynumer = zwieksznumer(elementywzorca, elementypoprzedniafakt, wpisView, separator, nowanumeracjamc);
        return trimmnowynumer(nowynumer, separator);
    }
    
  
    private static String znajdzseparator(String wzorzec) {
        String separator = null;
         if (wzorzec.contains("/")) {
            separator = "/";
        }
        return separator;
    }
    
    private static String[] elementywzorca(String wzorzec, String separator) {
        return wzorzec.split(separator);
    }
    
    private static String[] elementydokumentu(Dok ostatnidokument, String separator) {
        return ostatnidokument.getNrWlDk().split(separator);
    }
    
    private static String[] elementydokumentu(Faktura ostatnidokument, String separator) {
        return ostatnidokument.getFakturaPK().getNumerkolejny().split(separator);
    }
    
    private static String zwieksznumer(String[] elementywzorca, String[] elementypoprzedniafakt, WpisView wpisView, String separator, int nowanumeracjamc) {
        String nowynumer = "";
        for (int i = 0; i < elementywzorca.length; i++) {
            String typ = elementywzorca[i];
            switch (typ) {
                case "n":
                    String tmp = elementypoprzedniafakt[i];
                    Integer tmpI = Integer.parseInt(tmp);
                    tmpI++;
                    nowynumer = nowynumer.concat(tmpI.toString()).concat(separator);
                    break;
                case "N":
                    if (nowanumeracjamc == 0) {
                        String tmp1 = elementypoprzedniafakt[i];
                        Integer tmp1I = Integer.parseInt(tmp1);
                        tmp1I++;
                        nowynumer = nowynumer.concat(tmp1I.toString()).concat(separator);
                    } else {
                        nowynumer = nowynumer.concat("1").concat(separator);
                    }
                    break;
                case "m":
                    nowynumer = nowynumer.concat(wpisView.getMiesiacWpisu()).concat(separator);
                    break;
                case "r":
                    nowynumer = nowynumer.concat(wpisView.getRokWpisuSt()).concat(separator);
                    break;
                //to jest wlasna wstawka typu FVZ
                case "s":
                    nowynumer = nowynumer.concat(elementypoprzedniafakt[i]).concat(separator);
                    break;
                default:
                    nowynumer = nowynumer.concat(elementypoprzedniafakt[i]).concat(separator);
                    break;
            }
        }
        return nowynumer;
    }
    
    
    
    private static String trimmnowynumer(String nowynumer, String separator) {
        String numer = null;
        if (nowynumer.endsWith(separator)) {
            numer = nowynumer.substring(0, nowynumer.lastIndexOf(separator));
        }
        return numer;
    }
}
