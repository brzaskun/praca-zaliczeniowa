/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansRegon;

import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.PrimeFaces;
import webservice.GUS;

/**
 *
 * @author Osito
 */
public class SzukajDaneBean {
    
     public static void znajdzdaneregon(String formularz, Klienci selected) {
        String nip = selected.getNip();
        Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
        Matcher m = p.matcher(nip.substring(0,1));  //<-- matcher( not Matcher
        if (selected.getNip() != null && !m.find() && selected.getNip().length()==10) {
            GUS poc = new GUS();
            Map<String, String> dane = poc.pobierz(nip);
            if (dane.size()==0) {
                selected.setNpelna("wystąpił błąd logowania do serwera GUS");
            } else if (dane.size()==1) {
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
            PrimeFaces.current().ajax().update(formularz+":nazwaPole");
            PrimeFaces.current().ajax().update(formularz+":symbolPole");
            PrimeFaces.current().ajax().update(formularz+":kodPole");
            PrimeFaces.current().ajax().update(formularz+":miejscowoscPole");
            PrimeFaces.current().ajax().update(formularz+":ulicaPole");
            PrimeFaces.current().ajax().update(formularz+":domPole");
            PrimeFaces.current().ajax().update(formularz+":lokalPole");
            PrimeFaces.current().ajax().update(formularz+":krajPole");
        }
    }
     
     public static Klienci znajdzdaneregonAutomat(String nip) {
        if (nip.equals("9930282842")) {
            error.E.s("");
        }
        Klienci selected = new Klienci();
        try {
            Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
            Matcher m = p.matcher(nip.substring(0,1));  //<-- matcher( not Matcher
            if (nip != null && !m.find() && nip.length()==10) {
                GUS poc = new GUS();
                Map<String, String> dane = poc.pobierz(nip);
                if (dane.size()==0) {
                    selected.setNpelna("wystąpił błąd logowania do serwera GUS");
                } else if (dane.size()==1) {
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
     
     public static void znajdzdaneregon(String formularz, Podatnik selected) {
        String nip = selected.getNip();
        Pattern p = Pattern.compile("^[a-zA-Z]+$");//<-- compile( not Compile(
        Matcher m = p.matcher(nip.substring(0,1));  //<-- matcher( not Matcher
        if (selected.getNip() != null && !m.find() && selected.getNip().length()==10) {
            GUS poc = new GUS();
            Map<String, String> dane = poc.pobierz(nip);
            if (dane.size()==0) {
                selected.setPrintnazwa("wystąpił błąd logowania do serwera GUS");
            } else if (dane.size()==1) {
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
                    selected.setDatarozpoczecia(dane.get("praw_dataRozpoczeciaDzialalnosci"));
                } else {
                    selected.setDatarozpoczecia(dane.get("fiz_dataRozpoczeciaDzialalnosci"));
                    selected.setNrdomu(dane.get("fiz_adSiedzNumerNieruchomosci"));
                    selected.setPoczta(dane.get("fiz_adSiedzMiejscowoscPoczty_Nazwa"));
                    if (dane.get("fiz_adSiedzNumerLokalu") != null) {
                        selected.setNrlokalu(dane.get("fiz_adSiedzNumerLokalu"));
                    } else {
                        selected.setNrlokalu(null);
                    }
                }
            }
            PrimeFaces.current().ajax().update(formularz+":nazwapelna");
            PrimeFaces.current().ajax().update(formularz+":regon");
            PrimeFaces.current().ajax().update(formularz+":powiat");
            PrimeFaces.current().ajax().update(formularz+":kod");
            PrimeFaces.current().ajax().update(formularz+":wojewodztwo");
            PrimeFaces.current().ajax().update(formularz+":miejscowosc");
            PrimeFaces.current().ajax().update(formularz+":gmina");
            PrimeFaces.current().ajax().update(formularz+":poczta");
            PrimeFaces.current().ajax().update(formularz+":ulica");
            PrimeFaces.current().ajax().update(formularz+":nrdomu");
            PrimeFaces.current().ajax().update(formularz+":nrlokalu");
            PrimeFaces.current().ajax().update(formularz+":datarozpoczecia");
            
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
