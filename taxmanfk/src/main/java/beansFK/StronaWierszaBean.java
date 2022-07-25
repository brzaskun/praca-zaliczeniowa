/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import data.Data;
import embeddable.Mce;
import entity.Podatnik;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class StronaWierszaBean {
    
    public static boolean czyKontoJestRozrachunkowe(StronaWiersza aktualnyWierszDlaRozrachunkow, String stronawiersza) {
        if (stronawiersza.equals("Wn")) {
                return (aktualnyWierszDlaRozrachunkow).getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe");
            } else {
                return (aktualnyWierszDlaRozrachunkow).getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe");
            }
    }
       
    public static double przeliczWalutyWn(Wiersz wiersz) {
        double kwota = wiersz.getStronaWn().getKwota();
            double zwrot = 0.0;
            if (kwota != 0.0) {
                wiersz.getStronaWn().setKwotaWaluta(kwota);
                double kurs = wiersz.getTabelanbp().getKurssredni();
                double przelicznik = wiersz.getTabelanbp().getWaluta().getPrzelicznik();
                double kwotazlotowki = wiersz.getStronaWn().getKwota();
                zwrot  = Z.z(kwotazlotowki * kurs / przelicznik);
            }
            return zwrot;
        }
     
     public static double przeliczWalutyMa(Wiersz wiersz) {
            double kwota = wiersz.getStronaMa().getKwota();
            double zwrot = 0.0;
            if (kwota != 0.0) {
                wiersz.getStronaMa().setKwotaWaluta(kwota);
                double kurs = wiersz.getTabelanbp().getKurssredni();
                double przelicznik = wiersz.getTabelanbp().getWaluta().getPrzelicznik();
                double kwotazlotowki = wiersz.getStronaMa().getKwota();
                zwrot = Z.z(kwotazlotowki * kurs / przelicznik);
            }
            return zwrot;
        }
     
     public static double przeliczWalutyWnBO(Wiersz wiersz) {
            double kwota = wiersz.getStronaWn().getKwota();
            double zwrot = 0.0;
            if (kwota != 0.0) {
                wiersz.getStronaWn().setKwotaWaluta(wiersz.getStronaWn().getKwota());
                double kurs = wiersz.getStronaWn().getKursBO();
                double przelicznik = wiersz.getTabelanbp().getWaluta().getPrzelicznik();
                double kwotazlotowki = wiersz.getStronaWn().getKwota();
                zwrot = Z.z(kwotazlotowki * kurs / przelicznik);
            }
            return zwrot;
        }
     
     public static double przeliczWalutyMaBO(Wiersz wiersz) {
            double kwota = wiersz.getStronaWn().getKwota();
            double zwrot = 0.0;
            if (kwota != 0.0) {
                wiersz.getStronaMa().setKwotaWaluta(wiersz.getStronaMa().getKwota());
                double kurs = wiersz.getStronaMa().getKursBO();
                double przelicznik = wiersz.getTabelanbp().getWaluta().getPrzelicznik();
                double kwotazlotowki = wiersz.getStronaMa().getKwota();
                zwrot = Z.z(kwotazlotowki * kurs / przelicznik);
            }
            return zwrot;
        }
   
//     public static void pobraniegranica(List<StronaWiersza> lista, String mc) {
//        int granicagorna = Mce.getMiesiacToNumber().get(mc) == 1 ? 13 : Mce.getMiesiacToNumber().get(mc);
//            for (Iterator<StronaWiersza> it = lista.iterator(); it.hasNext();) {
//                StronaWiersza p = it.next();
//                if (Mce.getMiesiacToNumber().get(p.getDokfk().getMiesiac()) > granicagorna) {
//                    it.remove();
//                }
//            }
//    }
     
       
    public static List<StronaWiersza> pobraniezapisowwynikowe(StronaWierszaDAO stronaWierszaDAO, Podatnik podatnik, String rok, String mc) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikRokWynik(podatnik, rok, mc);
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobraniezapisowwynikoweMCRok(StronaWierszaDAO stronaWierszaDAO, Podatnik podatnik, String rok, String mc) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikRokMcWynik(podatnik, rok, mc);
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobraniezapisowwynikowe(StronaWierszaDAO stronaWierszaDAO, String mc, String rok, Podatnik podatnik) {
        int granicagorna = Mce.getMiesiacToNumber().get(mc);
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikRokWynik(podatnik, rok, mc);
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobraniezapisowwynikoweBO(StronaWierszaDAO stronaWierszaDAO, WpisView wpisView) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikRokWynikBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        return pobranezapisy;
    }
    
       public static List<StronaWiersza> pobraniezapisowwynikoweCecha(StronaWierszaDAO stronaWierszaDAO, WpisView wpisView) {
        List<StronaWiersza> pobranezapisy = Collections.synchronizedList(new ArrayList<>());
        pobranezapisy.addAll(stronaWierszaDAO.findStronaByPodatnikWynikCechaRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
        String[] mce = Data.poprzedniOkres(wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
        pobranezapisy.addAll(stronaWierszaDAO.findStronaByPodatnikWynikCechaRokMc(wpisView.getPodatnikObiekt(), mce[1], mce[0]));
        return pobranezapisy;
    }
     
//    public static List<StronaWiersza> pobraniezapisowwynikoweCecha(StronaWierszaDAO stronaWierszaDAO, WpisView wpisView) {
//        int granicagorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu());
//        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikWynikCecha(wpisView.getPodatnikObiekt());
//        for (Iterator<StronaWiersza> it = pobranezapisy.iterator(); it.hasNext(); ) {
//            StronaWiersza p = it.next();
//            int mc = Mce.getMiesiacToNumber().get(p.getDokfk().getMiesiac());
//            int rok = Integer.parseInt(p.getDokfk().getRok());
//            if (rok == wpisView.getRokWpisu() && mc > granicagorna) {
//                it.remove();
//            }
//            if ((rok < wpisView.getRokWpisu() && mc < 11) || rok > wpisView.getRokWpisu()) {
//                it.remove();
//            }
//        }
//        return pobranezapisy;
//    }
     
     public static List<StronaWiersza> pobraniezapisowbilansowe(StronaWierszaDAO stronaWierszaDAO, WpisView wpisView) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        StronaWiersza p = null;
        try {
            for (Iterator<StronaWiersza> it = pobranezapisy.iterator(); it.hasNext(); ) {
                p = it.next();
                if ((p.getDokfk().getRodzajedok().getSkrot().equals("BO"))) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        return pobranezapisy;
    }
     
     public static List<StronaWiersza> pobraniezapisowbilansowe(StronaWierszaDAO stronaWierszaDAO, String mc, String rok, Podatnik podatnik) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikRokBilans(podatnik, rok, mc);
        StronaWiersza p = null;
        try {
            for (Iterator<StronaWiersza> it = pobranezapisy.iterator(); it.hasNext(); ) {
                p = it.next();
                if (p.getDokfk().getRodzajedok().getSkrot().equals("BO")) {
                    it.remove();
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        return pobranezapisy;
    }
    
    public static boolean niemacech(List<StronaWiersza> sw) {
        boolean zwrot = true;
        for (StronaWiersza w : sw) {
            if (w.getCechazapisuLista() != null && w.getCechazapisuLista().size() > 0) {
                zwrot = false;
                break;  
            }
        }
        return zwrot;
    }
     
     public static void main(String[] args) {
            double kwotawaluta = 6851.63;
            double kurs = 4.2295;
            double kwotazlotowki = 0.0;
            kwotazlotowki = Math.round(kwotawaluta * kurs * 100);
            kwotazlotowki /= 100;
            error.E.s(kwotazlotowki);
            kwotawaluta = 137032.50;
            kwotazlotowki = 0.0;
            kwotazlotowki = Math.round(kwotawaluta * kurs * 100);
            kwotazlotowki /= 100;
            double vatpln = kwotazlotowki * 0.05;
     }
}
