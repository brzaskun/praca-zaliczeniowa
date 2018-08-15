/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import embeddable.Stornodoch;
import entity.Dok;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class Rozrachunki {

    public static void dodajdatydlaStorno(WpisView wpisView, Dok tmpDok) throws ParseException {
        String data;
        switch (wpisView.getMiesiacWpisu()) {
            case "01":
            case "03":
            case "05":
            case "07":
            case "08":
            case "10":
            case "12":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-31";
                break;
            case "02":
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-28";
                break;
            default:
                data = wpisView.getRokWpisu().toString() + "-" + wpisView.getMiesiacWpisu() + "-30";
                break;
        }
        String dataWyst = tmpDok.getDataWyst();
        String dataPlat = tmpDok.getTerminPlatnosci();
        Calendar c = Calendar.getInstance();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datawystawienia = formatter.parse(dataWyst);
        Date terminplatnosci = formatter.parse(dataPlat);
        Date dataujeciawkosztach = formatter.parse(data);
        if (roznicaDni(datawystawienia, terminplatnosci) == true) {
            c.setTime(terminplatnosci);
            c.add(Calendar.DAY_OF_MONTH, 30);
            String nd30 = formatter.format(c.getTime());
            tmpDok.setTermin30(nd30);
            tmpDok.setTermin90("");
        } else {
            c.setTime(dataujeciawkosztach);
            c.add(Calendar.DAY_OF_MONTH, 90);
            String nd90 = formatter.format(c.getTime());
            tmpDok.setTermin90(nd90);
            tmpDok.setTermin30("");
        }
        c.setTime(terminplatnosci);
        c.add(Calendar.DAY_OF_MONTH, 150);
        String nd150 = formatter.format(c.getTime());
        tmpDok.setTermin150(nd150);
    }

    private static boolean roznicaDni(Date date_od, Date date_do) {
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = Math.abs(x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        if (wynik <= 61) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean sprawdzczyniemarozrachunkow(Dok dok) {
        List<Stornodoch> temp = Collections.synchronizedList(new ArrayList<>());
        try {
            temp = dok.getStorno();
            if (temp.size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }
}
