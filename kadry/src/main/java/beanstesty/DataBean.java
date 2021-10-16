/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import entity.Kalendarzmiesiac;

/**
 *
 * @author Osito
 */
public class DataBean {
    
    public static boolean czysiemiesci(Kalendarzmiesiac kalendarz, String zmiennadataod, String zmiennadatado) {
        boolean zwrot = false;
        String pierwszydzienmiesiaca = Data.odejmijdni(kalendarz.getPierwszyDzien(),1);
        String ostatnidzienmiesiaca = Data.dodajdni(kalendarz.getOstatniDzien(),1);
        //czy data poczatkowa zmiennej jest starsza od daty koncowej kalendarza
        boolean zaczynasieprzedkoncem = Data.czyjestprzed(ostatnidzienmiesiaca, zmiennadataod);
        zmiennadatado = zmiennadatado!=null ? zmiennadatado : Data.getDzien(Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc()));
        boolean konczysiepopopoczatku = Data.czyjestpo(pierwszydzienmiesiaca, zmiennadatado);
        boolean konczysieprzedpoczatkiem = Data.czyjestpo(zmiennadatado, pierwszydzienmiesiaca);
        zwrot = zaczynasieprzedkoncem||konczysiepopopoczatku;
        if (konczysieprzedpoczatkiem) {
            zwrot = false;
        } else if (konczysiepopopoczatku&&!zaczynasieprzedkoncem) {
            zwrot = false;
        }
        return zwrot;
    }
    
    public static int dataod(String data, String rok, String mc) {
        String dataod = Data.pierwszyDzien(rok, mc);
        if (data!=null&&!data.equals("")&&Data.getRok(data).equals(rok)&&Data.getMc(data).equals(mc)) {
            dataod = data;
        }
        int dziendozmienna = Data.getDzienI(dataod);
        return dziendozmienna;
    }
    
    public static int datado(String data, String rok, String mc) {
        String datado = Data.ostatniDzien(rok, mc);
        if (data!=null&&!data.equals("")&&Data.getRok(data).equals(rok)&&Data.getMc(data).equals(mc)) {
            datado = data;
        }
        int dziendozmienna = Data.getDzienI(datado);
        return dziendozmienna;
    }
    
    public static void main(String[]args) {
        Kalendarzmiesiac kalendarz = KalendarzmiesiacBean.create();
        String dataod = "2020-12-31";
        String datado = "2021-12-31";
        boolean czy = czysiemiesci(kalendarz, dataod, datado);
        System.out.println("odpowiedz: "+czy);
        
    }
}
