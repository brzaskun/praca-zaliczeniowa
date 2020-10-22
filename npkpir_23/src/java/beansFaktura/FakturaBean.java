/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFaktura;

import beansFK.TabelaNBPBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.FakturaDAO;
import dao.FakturaStopkaNiemieckaDAO;
import daoFK.TabelanbpDAO;
import data.Data;
import embeddable.EVatwpis;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Dok;
import entity.Evewidencja;
import entity.Faktura;
import entity.FakturaStopkaNiemiecka;
import entity.FakturaWalutaKonto;
import entity.Podatnik;
import entityfk.Tabelanbp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Named;
import msg.Msg;
import org.joda.time.DateTime;
import org.joda.time.IllegalFieldValueException;
import org.joda.time.LocalDate;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class FakturaBean {

    public static String uzyjwzorcagenerujnumerDok(String wzorzec, String skrot, WpisView wpisView, DokDAO dokDAO) {
        String wygenerowanynumer = "";
        String separator = znajdzseparator(wzorzec);
        Dok ostatnidokument = dokDAO.find(skrot, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        if (ostatnidokument != null) {
            String[] elementypoprzedniafakt = elementydokumentu(ostatnidokument, separator);
            wygenerowanynumer = generowanie(wzorzec, separator, elementypoprzedniafakt, wpisView, 0);
        }
        return wygenerowanynumer;
    }
    
    public static String uzyjwzorcagenerujnumerFaktura(String wzorzec, WpisView wpisView, FakturaDAO faktDAO) {
        String separator = znajdzseparator(wzorzec);
        Faktura ostatnidokument = faktDAO.findOstatniaFakturaByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        String mcostatniejfaktury = ostatnidokument.getMc();
        String[] elementypoprzedniafakt = elementydokumentu(ostatnidokument, separator);
        String numerwstepny;
        Faktura istnieje = null;
        do {
            if (mcostatniejfaktury.equals(wpisView.getMiesiacWpisu())) {
                numerwstepny = generowanie(wzorzec, separator, elementypoprzedniafakt, wpisView, 0);
            } else {
                numerwstepny = generowanie(wzorzec, separator, elementypoprzedniafakt, wpisView, 1);
            }
            istnieje = faktDAO.findbyNumerPodatnik(numerwstepny, wpisView.getPodatnikObiekt());
            if (istnieje != null) {
                elementypoprzedniafakt = elementydokumentu(istnieje, separator);
                mcostatniejfaktury = istnieje.getMc();
            }
        } while (istnieje != null);
        return numerwstepny;
    }
    
    public static String uzyjwzorcagenerujpierwszynumerFaktura(String wzorzec, WpisView wpisView) {
        String separator = znajdzseparator(wzorzec);
        return generowaniepierwszynumer(wzorzec, separator, wpisView);
    }
    
      private static String generowaniepierwszynumer(String wzorzec, String separator, WpisView wpisView) {
        String[] elementywzorca = elementywzorca(wzorzec, separator);
        String nowynumer = pierwszynumer(elementywzorca, wpisView, separator);
        return trimmnowynumer(nowynumer, separator);
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
        return ostatnidokument.getNumerkolejny().split(separator);
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
 
  
    private static String pierwszynumer(String[] elementywzorca, WpisView wpisView, String separator) {
        String nowynumer = "";
        for (int i = 0; i < elementywzorca.length; i++) {
            String typ = elementywzorca[i];
            switch (typ) {
                case "n":
                    nowynumer = nowynumer.concat("1").concat(separator);
                    break;
                case "N":
                    nowynumer = nowynumer.concat("1").concat(separator);
                    break;
                case "m":
                    nowynumer = nowynumer.concat(wpisView.getMiesiacWpisu()).concat(separator);
                    break;
                case "r":
                    nowynumer = nowynumer.concat(wpisView.getRokWpisuSt()).concat(separator);
                    break;
                //to jest wlasna wstawka typu FVZ
                case "s":
                    nowynumer = nowynumer.concat(elementywzorca[i]).concat(separator);
                    break;
                default:
                    nowynumer = nowynumer.concat(elementywzorca[i]).concat(separator);
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

    public static String obliczdatawystawienia(WpisView wpisView) {
        String rokmiesiac = wpisView.getRokWpisuSt() + "-" + wpisView.getMiesiacWpisu() + "-";
        String dzien = String.valueOf((new DateTime()).getDayOfMonth());
        dzien = dzien.length() == 1 ? "0" + dzien : dzien;
        return sprawdzDateDlaFaktury(rokmiesiac + dzien, wpisView);
    }
    
    private static String sprawdzDateDlaFaktury(String data, WpisView wpisView) {
        String nowadata = data;
        try {
            DateTime dt = new DateTime(data);
            
        } catch (IllegalFieldValueException e) {
            nowadata = Data.ostatniDzien(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        }
        return nowadata;
    }
    
    public static void main(String[] args) {
        try {
            String dzien = "2015-09-31";
            DateTime dt = new DateTime(dzien);
        } catch (Exception ex) {
            // Logger.getLogger(FakturaBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String obliczterminzaplaty(Podatnik podatnikobiekt, String pelnadata) {
        DateTime dt = new DateTime(pelnadata);
        LocalDate firstDate = dt.toLocalDate();
        try {
            LocalDate terminplatnosci = firstDate.plusDays(Integer.parseInt(podatnikobiekt.getPlatnoscwdni()));
            return terminplatnosci.toString();
        } catch (Exception ep) {
            LocalDate terminplatnosci = firstDate.plusDays(14);
            return terminplatnosci.toString();
        }
    }
    
    public static String obliczterminzaplaty(Podatnik podatnikobiekt, String pelnadata, int dnizaplaty) {
        DateTime dt = new DateTime(pelnadata);
        LocalDate firstDate = dt.toLocalDate();
        try {
            LocalDate terminplatnosci = firstDate.plusDays(dnizaplaty);
            return terminplatnosci.toString();
        } catch (Exception ep) {
            LocalDate terminplatnosci = firstDate.plusDays(14);
            return terminplatnosci.toString();
        }
    }

    public static String pobierznumerkonta(Podatnik podatnikobiekt) {
        try {
            String nrkonta = podatnikobiekt.getNrkontabankowego();
            if (nrkonta != null) {
                return nrkonta;
            } else {
                return "brak numeru konta bankowego";
            }
        } catch (Exception es) {
            Msg.msg("w", "Brak numeru konta bankowego");
            return "brak numeru konta bankowego";
        }
    }
    
    
    public static void wielekont(Faktura selected, List<FakturaWalutaKonto> konta, FakturaStopkaNiemieckaDAO fakturaStopkaNiemieckaDAO, Podatnik podatnik) {
        String waluta = selected.getWalutafaktury();
        if (konta != null) {
            for (FakturaWalutaKonto p : konta) {
                if (p.getWaluta().getSymbolwaluty().equals(waluta)) {
                   selected.setNrkontabankowego(p.getNrkonta());
                   selected.setSwift(p.getSwift());
                   try {
                        FakturaStopkaNiemiecka fakturaStopkaNiemiecka = fakturaStopkaNiemieckaDAO.findByPodatnik(podatnik);
                        if (fakturaStopkaNiemiecka != null) {
                            fakturaStopkaNiemiecka.setBank(p.getNazwabanku());
                            fakturaStopkaNiemiecka.setBlz(p.getBlz());
                            fakturaStopkaNiemiecka.setBic(p.getSwift());
                            fakturaStopkaNiemiecka.setKtonr(p.getNrkonta());
                            fakturaStopkaNiemiecka.setIban(p.getIban());
                            fakturaStopkaNiemieckaDAO.edit(fakturaStopkaNiemiecka);
                        }
                    } catch (Exception e) {
                       
                    }
                   break;
                }
            }
        }
    }
    

    public static String pobierzpodpis(WpisView wpisView) {
        if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && wpisView.getPodatnikObiekt().getWystawcafaktury().equals("brak")) {
            return "";
        } else if (wpisView.getPodatnikObiekt().getWystawcafaktury() != null && !wpisView.getPodatnikObiekt().getWystawcafaktury().equals("")) {
            return wpisView.getPodatnikObiekt().getWystawcafaktury();
        }  else {
            return wpisView.getPodatnikObiekt().getImie() + " " + wpisView.getPodatnikObiekt().getNazwisko();
        }
    }

    public static List<Pozycjenafakturzebazadanych> inicjacjapozycji(Podatnik podatnikobiekt) {
        List<Pozycjenafakturzebazadanych> lista = Collections.synchronizedList(new ArrayList<>());
        Pozycjenafakturzebazadanych poz = new Pozycjenafakturzebazadanych();
        poz.setPodatek(23);
        if (podatnikobiekt.getWierszwzorcowy() != null) {
            Pozycjenafakturzebazadanych wierszwzorcowy = podatnikobiekt.getWierszwzorcowy();
            poz.setNazwa(wierszwzorcowy.getNazwa());
            poz.setPKWiU(wierszwzorcowy.getPKWiU());
            poz.setJednostka(wierszwzorcowy.getJednostka());
            poz.setIlosc(wierszwzorcowy.getIlosc());
            poz.setPodatek(wierszwzorcowy.getPodatek());
        } else {
            poz.setIlosc(1);
        }
        lista.add(poz);
        return lista;
    }

    public static String pobierzmiejscewyst(Podatnik podatnikobiekt) {
         try {
            return podatnikobiekt.getMiejscewystawienia().isEmpty() ? "nie ustawiono miejsca" : podatnikobiekt.getMiejscewystawienia();
        } catch (Exception et) {
            return "nie ustawiono miejsca";
        }
    }
    
    public static void ewidencjavat(Faktura selected, EvewidencjaDAO evewidencjaDAO) {
        //tu obliczamy wartosc netto wiersza
        List<Pozycjenafakturzebazadanych> pozycje = selected.getPozycjenafakturze();
        if (pozycje != null && !pozycje.isEmpty()) {
            List<Evewidencja> ew = Collections.synchronizedList(new ArrayList<>());
            ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
            ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz Niemcy"));
            ew.addAll(evewidencjaDAO.znajdzpotransakcji("usługi poza ter."));
            List<EVatwpis> el = Collections.synchronizedList(new ArrayList<>());
            Map<String, Double> sumy = przetworzpozycje(ew, el, pozycje, selected);
            if (selected.isFakturavatmarza() || selected.isRachunek()) {
                selected.setEwidencjavat(new ArrayList<>());
                selected.setNetto(sumy.get("netto"));
                selected.setVat(0.0);
                selected.setBrutto(Z.z(sumy.get("netto")));
            } else {
                selected.setEwidencjavat(el);
                selected.setNetto(sumy.get("netto"));
                selected.setVat(Z.z(sumy.get("vat")));
                selected.setBrutto(Z.z(sumy.get("brutto")));
                selected.setNettopln(sumy.get("nettopln"));
                selected.setVatpln(sumy.get("vatpln"));
                selected.setBruttopln(Z.z(sumy.get("bruttopln")));;
            }
        }
    }
    
   
    public static void ewidencjavatkorekta(Faktura selected, EvewidencjaDAO evewidencjaDAO) {
        //tu obliczamy wartosc netto wiersza
        List<Pozycjenafakturzebazadanych> pozycje = selected.getPozycjepokorekcie();
        List<Evewidencja> ew = Collections.synchronizedList(new ArrayList<>());
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz"));
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("sprzedaz Niemcy"));
        ew.addAll(evewidencjaDAO.znajdzpotransakcji("usługi poza ter."));
        List<EVatwpis> el = Collections.synchronizedList(new ArrayList<>());
        Map<String, Double> sumy = przetworzpozycje(ew, el, pozycje, selected);
        if (selected.isFakturavatmarza() || selected.isRachunek()) {
            selected.setEwidencjavatpk(new ArrayList<>());
            selected.setNettopk(sumy.get("netto"));
            selected.setVatpk(0.0);
            selected.setBruttopk(Z.z(sumy.get("netto")));
        } else {
            selected.setEwidencjavatpk(el);
            selected.setNettopk(sumy.get("netto"));
            selected.setVatpk(Z.z(sumy.get("vat")));
            selected.setBruttopk(Z.z(sumy.get("brutto")));
            selected.setNettopkpln(sumy.get("nettopln"));
            selected.setVatpkpln(sumy.get("vatpln"));
            selected.setBruttopkpln(Z.z(sumy.get("bruttopln")));;
        }
    }
    
    private static Map<String, Double> przetworzpozycje(List<Evewidencja> ew, List<EVatwpis> el, List<Pozycjenafakturzebazadanych> pozycje, 
        Faktura selected) {
        Map<String, Double> sumy = new ConcurrentHashMap<>();
        double netto = 0.0;
        double vat = 0.0;
        double brutto = 0.0;
        //tu oblicza sie wierszw tj. wartosc netto podatek i vat
        for (Pozycjenafakturzebazadanych p : pozycje) {
            double ilosc = p.getIlosc();
            double cena = 0.0;
            double wartosc = 0.0;
            if (selected.isFakturaxxl()) {
                cena += p.getCenajedn0();
                cena += p.getCenajedn1();
                cena += p.getCenajedn2();
                cena += p.getCenajedn3();
                cena += p.getCenajedn4();
                cena += p.getCenajedn5();
                wartosc = Z.z(cena);
            } else {
                cena += p.getCena();
                wartosc = Z.z(ilosc * cena);
            }
            if (selected.isLiczodwartoscibrutto()) {
                brutto += wartosc;
                p.setBrutto(Z.z(wartosc));
                double podatekstawka = p.getPodatek() > -1 ? p.getPodatek() : 0;
                double podatek = (wartosc * podatekstawka) / (100+podatekstawka);
                vat += Z.z(podatek);
                p.setPodatekkwota(Z.z(podatek));
                double nettop = Z.z(wartosc-podatek);
                netto += Z.z(nettop);
                p.setNetto(Z.z(nettop));
            } else {
                netto += wartosc;
                p.setNetto(wartosc);
                double podatekstawka = p.getPodatek() > -1 ? p.getPodatek() : 0;
                double podatek = (wartosc * podatekstawka) / 100;
                vat += Z.z(podatek);
                p.setPodatekkwota(Z.z(podatek));
                double bruttop = Z.z(wartosc + podatek);
                brutto += Z.z(bruttop);
                p.setBrutto(Z.z(bruttop));
            }
                EVatwpis eVatwpis = new EVatwpis();
                Evewidencja ewidencja = zwrocewidencje(ew, p);
                //jezeli el bedzie juz wypelnione dana ewidencja to tylko zwieksz wartosc
                //jesli nie to dodaj nowy wiersz
                boolean jesttakaewiedencja = false;
                for (EVatwpis r : el) {
                    if (r.getEwidencja().equals(ewidencja)) {
                        eVatwpis = r;
                        jesttakaewiedencja = true;
                    }
                }
                if (jesttakaewiedencja == false) {
                    eVatwpis.setEwidencja(ewidencja);
                    eVatwpis.setNetto(Z.z(p.getNetto()));
                    eVatwpis.setVat(Z.z(p.getPodatekkwota()));
                    eVatwpis.setNettopln(Z.z(p.getNetto(selected.getTabelanbp())));
                    eVatwpis.setVatpln(Z.z(p.getPodatekkwota(selected.getTabelanbp())));
                    eVatwpis.setEstawka(String.valueOf(p.getPodatek()));
                    el.add(eVatwpis);
                } else {
                    eVatwpis.setNetto(Z.z(eVatwpis.getNetto() + p.getNetto()));
                    eVatwpis.setVat(Z.z(eVatwpis.getVat() + p.getPodatekkwota()));
                    eVatwpis.setNettopln(Z.z(eVatwpis.getNettopln()+ p.getNetto(selected.getTabelanbp())));
                    eVatwpis.setVatpln(Z.z(eVatwpis.getVatpln()+ p.getPodatekkwota(selected.getTabelanbp())));
                }
            }
        if (selected.getTabelanbp()!=null) {
            sumy.put("netto", Z.z(netto));
            sumy.put("vat", Z.z(vat));
            sumy.put("brutto", Z.z(brutto));
            sumy.put("nettopln", przeliczkwote(Z.z(netto), selected.getTabelanbp()));
            sumy.put("vatpln", przeliczkwote(Z.z(vat), selected.getTabelanbp()));
            sumy.put("bruttopln", przeliczkwote(Z.z(brutto), selected.getTabelanbp()));
        } else {
            sumy.put("netto", Z.z(netto));
            sumy.put("vat", Z.z(vat));
            sumy.put("brutto", Z.z(brutto));
            sumy.put("nettopln", Z.z(netto));
            sumy.put("vatpln", Z.z(vat));
            sumy.put("bruttopln", Z.z(brutto));
        }
        return sumy;
    }
    
    private static Double przeliczkwote(double z, Tabelanbp tabelanbp) {
        double kurs = tabelanbp.getKurssredniPrzelicznik();
        return Z.z(z*kurs);
    }
    
    private static Evewidencja zwrocewidencje(List<Evewidencja> ewidencje, Pozycjenafakturzebazadanych p) {
        for (Evewidencja r : ewidencje) {
            if (p.getPodatek() == -1) {
                if (r.getNazwa().equals("usługi świad. poza ter.kraju")) {
                    return r;
                }
            }
            if ((int) p.getPodatek() == 19) {
                if (r.getNazwa().equals("sprzedaż Niemcy")) {
                    return r;
                }
            }
            if (p.getPodatek() == -2) {
                if (r.getNazwa().equals("sprzedaż zw")) {
                    return r;
                }
            }
            if (r.getNazwa().contains(String.valueOf((int) p.getPodatek()))) {
                return r;
            }
        }
        return null;
    }
  
//    public static void main(String[] a) {
//        double netto = 1000.0/3.0;
//        double stawka = 0.23;
//        netto = Z.z(netto);
//        double vat = Z.z(netto*stawka);
//        error.E.s(vat);
//    }

    public static void dodajtabelenbp(Faktura selected, TabelanbpDAO tabelanbpDAO) {
        String nazwawaluty = selected.getWalutafaktury();
        if (!nazwawaluty.equals("PLN")) {
            DateTime dzienposzukiwany = new DateTime(selected.getDatawystawienia());
            Tabelanbp tabela = TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, nazwawaluty);
            selected.setTabelanbp(tabela);
        } else {
            selected.setTabelanbp(null);
        }
    }

    

    
}
