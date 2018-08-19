/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansRegon;

import entity.Klienci;
import entity.Podatnik;
import error.E;
import gus.GUSView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
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
            if (dane.size()==1) {
                selected.setNpelna("nie znaleziono firmy w bazie Regon");
            } else {
                selected.setNpelna(dane.get("Nazwa"));
                selected.setNskrocona(dane.get("Nazwa"));
                selected.setKodpocztowy(dane.get("KodPocztowy"));
                selected.setMiejscowosc(dane.get("Miejscowosc"));
                String ulica = dane.get("Ulica") != null ? dane.get("Ulica") : "-";
                selected.setUlica(ulica);
                selected.setKrajkod("PL");
                selected.setKrajnazwa("Polska");
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
            }
            RequestContext.getCurrentInstance().update(formularz+":nazwaPole");
            RequestContext.getCurrentInstance().update(formularz+":symbolPole");
            RequestContext.getCurrentInstance().update(formularz+":kodPole");
            RequestContext.getCurrentInstance().update(formularz+":miejscowoscPole");
            RequestContext.getCurrentInstance().update(formularz+":ulicaPole");
            RequestContext.getCurrentInstance().update(formularz+":domPole");
            RequestContext.getCurrentInstance().update(formularz+":lokalPole");
            RequestContext.getCurrentInstance().update(formularz+":krajPole");
        }
    }
     
     public static Klienci znajdzdaneregonAutomat(String nip, GUSView gUSView) {
        Klienci selected = new Klienci();
        try {
            Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
            Matcher m = p.matcher(nip.substring(0,1));  //<-- matcher( not Matcher
            if (nip != null && !m.find() && nip.length()==10) {
                Map<String, String> dane = gUSView.pobierzDane(nip);
                if (dane.size()==1) {
                    selected.setNpelna("nie znaleziono firmy w bazie Regon");
                } else {
                    selected.setNip(nip);
                    selected.setNpelna(dane.get("Nazwa"));
                    selected.setNskrocona(dane.get("Nazwa"));
                    selected.setKodpocztowy(dane.get("KodPocztowy"));
                    selected.setMiejscowosc(dane.get("Miejscowosc"));
                    String ulica = dane.get("Ulica") != null ? dane.get("Ulica") : "-";
                    selected.setUlica(ulica);
                    selected.setKrajkod("PL");
                    selected.setKrajnazwa("Polska");
                    String typ = dane.get("Typ");
                    if (typ.equals("P")) {
                        selected.setDom(dane.get("praw_adSiedzNumerNieruchomosci"));
                        if (dane.get("praw_adSiedzNumerLokalu") != null) {
                            selected.setLokal(dane.get("praw_adSiedzNumerLokalu"));
                        } else {
                            selected.setLokal("-");
                        }
                    } else {
                        selected.setDom(dane.get("fiz_adSiedzNumerNieruchomosci"));
                        if (dane.get("fiz_adSiedzNumerLokalu") != null) {
                            selected.setLokal(dane.get("fiz_adSiedzNumerLokalu"));
                        } else {
                            selected.setLokal("-");
                        }
                    }
                }
            }
        } catch (Exception ex){
            E.e(ex);
        }
        return selected;
    }
     
     public static void znajdzdaneregon(String formularz, Podatnik selected, GUSView gUSView) {
        String nip = selected.getNip();
        Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
        Matcher m = p.matcher(nip.substring(0,1));  //<-- matcher( not Matcher
        if (selected.getNip() != null && !m.find() && selected.getNip().length()==10) {
            Map<String, String> dane = gUSView.pobierzDane(selected.getNip());
            if (dane.size()==1) {
                selected.setPrintnazwa("nie znaleziono firmy w bazie Regon");
            } else {
                selected.setPrintnazwa(dane.get("Nazwa"));
                selected.setRegon(dane.get("Regon"));
                selected.setKodpocztowy(dane.get("KodPocztowy"));
                selected.setWojewodztwo(StringUtils.lowerCase(dane.get("Wojewodztwo")));
                selected.setMiejscowosc(dane.get("Miejscowosc"));
                selected.setPowiat(dane.get("Powiat"));
                selected.setGmina(dane.get("Gmina"));
                selected.setUlica(dane.get("Ulica"));
                String typ = dane.get("Typ");
                if (typ.equals("P")) {
                    selected.setNrdomu(dane.get("praw_adSiedzNumerNieruchomosci"));
                    selected.setPoczta(dane.get("praw_adSiedzMiejscowoscPoczty_Nazwa"));
                    if (dane.get("praw_adSiedzNumerLokalu") != null) {
                        selected.setNrlokalu(dane.get("praw_adSiedzNumerLokalu"));
                    } else {
                        selected.setNrlokalu(null);
                    }
                } else {
                    selected.setNrdomu(dane.get("fiz_adSiedzNumerNieruchomosci"));
                    selected.setPoczta(dane.get("fiz_adSiedzMiejscowoscPoczty_Nazwa"));
                    if (dane.get("fiz_adSiedzNumerLokalu") != null) {
                        selected.setNrlokalu(dane.get("fiz_adSiedzNumerLokalu"));
                    } else {
                        selected.setNrlokalu(null);
                    }
                }
            }
            RequestContext.getCurrentInstance().update(formularz+":nazwapelna");
            RequestContext.getCurrentInstance().update(formularz+":regon");
            RequestContext.getCurrentInstance().update(formularz+":powiat");
            RequestContext.getCurrentInstance().update(formularz+":kod");
            RequestContext.getCurrentInstance().update(formularz+":wojewodztwo");
            RequestContext.getCurrentInstance().update(formularz+":miejscowosc");
            RequestContext.getCurrentInstance().update(formularz+":gmina");
            RequestContext.getCurrentInstance().update(formularz+":poczta");
            RequestContext.getCurrentInstance().update(formularz+":ulica");
            RequestContext.getCurrentInstance().update(formularz+":nrdomu");
            RequestContext.getCurrentInstance().update(formularz+":nrlokalu");
        }
    }
     
    public static void main(String[] args) {
       List ob = new ArrayList();
        for (int i = 0; i < 10000000; i++) {
            getRandomNumberInRange(16, 20);
        }
    }

    private static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
                throw new IllegalArgumentException("max must be greater than min");
        }
        return (int)(Math.random() * ((max - min) + 1)) + min;
    }

}
