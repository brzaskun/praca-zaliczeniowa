/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansRegon;

import entity.Klienci;
import gus.GUSView;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
public class SzukajDaneBean {
    
     public static void znajdzdaneregon(String formularz, Klienci selected, GUSView gUSView) {
        String nip = selected.getNip();
        Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
        Matcher m = p.matcher(nip.substring(0,1));  //<-- matcher( not Matcher
        if (selected.getNip() != null && !m.find() && selected.getNip().length()==10) {
            Map<String, String> dane = gUSView.pobierzDane(selected.getNip());
            selected.setNpelna(dane.get("Nazwa"));
            selected.setNskrocona(dane.get("Nazwa"));
            selected.setKodpocztowy(dane.get("KodPocztowy"));
            selected.setMiejscowosc(dane.get("Miejscowosc"));
            selected.setUlica(dane.get("Ulica"));
            String typ = dane.get("Typ");
            if (typ.equals("P")) {
                selected.setDom(dane.get("praw_adSiedzNumerNieruchomosci"));
                if (dane.get("praw_adSiedzNumerLokalu") != null) {
                    selected.setLokal(dane.get("praw_adSiedzNumerLokalu"));
                } else {
                    selected.setLokal(null);
                }
            } else {
                selected.setDom(dane.get("fiz_adSiedzNumerNieruchomosci"));
                if (dane.get("fiz_adSiedzNumerLokalu") != null) {
                    selected.setLokal(dane.get("fiz_adSiedzNumerLokalu"));
                } else {
                    selected.setLokal(null);
                }
            }
            selected.setKrajnazwa("Polska");
            RequestContext.getCurrentInstance().update(formularz+":nazwaPole");
            RequestContext.getCurrentInstance().update(formularz+":symbolPole");
            RequestContext.getCurrentInstance().update(formularz+":kodPole");
            RequestContext.getCurrentInstance().update(formularz+":miejscowoscPole");
            RequestContext.getCurrentInstance().update(formularz+":ulicaPole");
            RequestContext.getCurrentInstance().update(formularz+":domPole");
            RequestContext.getCurrentInstance().update(formularz+":lokalPole");
        }
    }
}
