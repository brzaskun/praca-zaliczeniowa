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
        boolean zwrot = true;
        String pierwszydzienmiesiaca = kalendarz.getPierwszyDzien();
        String ostatnidzienmiesiaca = kalendarz.getOstatniDzien();
        //czy data poczatkowa zmiennej jest starsza od daty koncowej kalendarza
        boolean zaczynasieprzedpoczatkiem = Data.czyjestprzed(pierwszydzienmiesiaca, zmiennadataod);
        boolean konczysieprzedpoczatkiem = Data.czyjestprzed(pierwszydzienmiesiaca, zmiennadatado);
        boolean zaczynasiepokoncu = Data.czyjestpo(ostatnidzienmiesiaca, zmiennadataod);
        boolean konczysiepokoncu = Data.czyjestpo(ostatnidzienmiesiaca, zmiennadatado);
        if (zaczynasieprzedpoczatkiem&&konczysieprzedpoczatkiem) {
            zwrot = false;
        }
        if (zaczynasiepokoncu&&konczysiepokoncu) {
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
        String dataod = "2020-11-01";
        String datado = "2020-11-30";
        boolean czy = czysiemiesci(kalendarz, dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2020-11-01";
        datado = "2020-12-31";
        czy = czysiemiesci(kalendarz, dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2020-12-03";
        datado = "2020-12-13";
        czy = czysiemiesci(kalendarz, dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2020-12-05";
        datado = "2021-01-13";
        czy = czysiemiesci(kalendarz, dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2021-01-01";
        datado = "2021-01-31";
        czy = czysiemiesci(kalendarz, dataod, datado);
        
    }
}
