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
    
    public static boolean czysiemiesci(String pierwszydzienmiesiaca, String ostatnidzienmiesiaca, String zmiennadataod, String zmiennadatado) {
        boolean zwrot = true;
        //czy data poczatkowa zmiennej jest starsza od daty koncowej kalendarza
        if (zmiennadatado==null||zmiennadatado.equals("")) {
            zmiennadatado = "2055-01-01";
        }
        boolean zaczynasieprzedpoczatkiem = Data.czyjestprzed(pierwszydzienmiesiaca, zmiennadataod);
        boolean konczysieprzedpoczatkiem = Data.czyjestprzed(pierwszydzienmiesiaca, zmiennadatado);
        boolean zaczynasiepokoncu = Data.czyjestpo(ostatnidzienmiesiaca, zmiennadataod);
        boolean konczysiepokoncu = Data.czyjestpo(ostatnidzienmiesiaca, zmiennadatado);
        if (zmiennadatado==null) {
            konczysieprzedpoczatkiem = false;
            konczysiepokoncu = true;
        }
        if (zaczynasieprzedpoczatkiem&&konczysieprzedpoczatkiem) {
            zwrot = false;
        }
        if (zaczynasiepokoncu&&konczysiepokoncu) {
            zwrot = false;
        }
        if (ostatnidzienmiesiaca.equals(zmiennadataod)) {
            zwrot = true;
        }
        if (pierwszydzienmiesiaca.equals(zmiennadatado)) {
            zwrot = true;
        }
        return zwrot;
    }
    
    public static boolean czysiemiescidzien(String databadana, String datagranicznaod, String datagranicznado) {
        boolean zwrot = false;
        //czy data poczatkowa zmiennej jest starsza od daty koncowej kalendarza
        if (datagranicznado==null) {
            boolean czyjestpopoczatku = Data.czyjestpo(datagranicznaod, databadana);
            if (czyjestpopoczatku) {
                zwrot = true;
            }
        } else {
            boolean czyjestprzedkoncem = Data.czyjestprzed(datagranicznado, databadana);
            boolean czyjestpopoczatku = Data.czyjestpo(datagranicznaod, databadana);
            if (czyjestprzedkoncem&&czyjestpopoczatku) {
                zwrot = true;
            }
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
    
    public static String dataodString(String datazmiennej, String rok, String mc) {
        String zwrot = null;
        String datakalendarza = Data.pierwszyDzien(rok, mc);
        int jestwokresie = Data.compare(datakalendarza, datazmiennej);
        if (datazmiennej!=null&&!datazmiennej.equals("")&&Data.getRok(datazmiennej).equals(rok)&&Data.getMc(datazmiennej).equals(mc)) {
            zwrot = datazmiennej;
        } else if (jestwokresie>0) {
            zwrot = datakalendarza;
        } else {
            zwrot = "2055-01-01";
        }
        return zwrot;
    }
    
    public static int datado(String data, String rok, String mc) {
        String datado = Data.ostatniDzien(rok, mc);
        if (data!=null&&!data.equals("")&&Data.getRok(data).equals(rok)&&Data.getMc(data).equals(mc)) {
            datado = data;
        }
        int dziendozmienna = Data.getDzienI(datado);
        return dziendozmienna;
    }
    
     public static String datadoString(String datazmiennej, String rok, String mc) {
        String zwrot = null;
        String datakalendarza = Data.ostatniDzien(rok, mc);
        int jestwokresie = Data.compare(datazmiennej, datakalendarza);
        if (datazmiennej!=null&&!datazmiennej.equals("")&&Data.getRok(datazmiennej).equals(rok)&&Data.getMc(datazmiennej).equals(mc)) {
            zwrot = datazmiennej;
        } else if (jestwokresie<0) {
            zwrot = datakalendarza;
        } else {
            zwrot = datakalendarza;
        }
        return zwrot;
    }
    
    public static void main(String[]args) {
        Kalendarzmiesiac kalendarz = KalendarzmiesiacBean.create();
        String dataod = "2020-11-01";
        String datado = "2020-11-30";
        boolean czy = czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2020-11-01";
        datado = "2020-12-31";
        czy = czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2020-12-03";
        datado = "2020-12-13";
        czy = czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2020-12-05";
        datado = "2021-01-13";
        czy = czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod, datado);
        System.out.println("odpowiedz: "+czy);
        dataod = "2021-01-01";
        datado = "2021-01-31";
        czy = czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), dataod, datado);
        
    }
}
