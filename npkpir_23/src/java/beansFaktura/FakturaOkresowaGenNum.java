/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFaktura;

import comparator.FakturaNumercomparator;
import dao.FakturaDAO;
import entity.Faktura;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
 import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class FakturaOkresowaGenNum {

    public static void wygenerujnumerfaktury(FakturaDAO fakturaDAO, Faktura selected, WpisView wpisView) {
        List<String> numerypobranych = Collections.synchronizedList(new ArrayList<>());
        int istniejafakturykontrahenta = 0;
        List<Faktura> wykazfaktur = new ArrayList<>();
        if (selected.getKontrahent()!=null) {
            wykazfaktur = fakturaDAO.findbyKontrahentNipRok(selected.getKontrahent().getNip(), wpisView.getPodatnikObiekt(), String.valueOf(wpisView.getRokWpisu()));
            Collections.sort(wykazfaktur, new FakturaNumercomparator());
            if (selected.isProforma()) {
                for (Iterator<Faktura> it = wykazfaktur.iterator(); it.hasNext(); ) {
                    Faktura fa = it.next();
                    if (!fa.isProforma()) {
                        it.remove();
                    }
                }
            } else {
                for (Iterator<Faktura> it = wykazfaktur.iterator(); it.hasNext(); ) {
                    Faktura fa = it.next();
                    if (fa.isProforma()) {
                        it.remove();
                    }
                }
            }
            try {
                if (wykazfaktur.size() > 0) {
                    for (Faktura p : wykazfaktur) {
                        numerypobranych.add(p.getNumerkolejny());
                    }
                    istniejafakturykontrahenta = 1;
                }
            } catch (Exception er) {
            }
        }
        String schematnumeracji = wpisView.getPodatnikObiekt().getSchematnumeracji();
        if (wpisView.getUzer().getFakturanumeracja()!=null&&!wpisView.getUzer().getFakturanumeracja().equals("")) {
            schematnumeracji= wpisView.getUzer().getFakturanumeracja();
        }
        if (schematnumeracji != null && !schematnumeracji.equals("")) {
            Faktura ostatnidokument  = fakturaDAO.findOstatniaFakturaByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
            if (wpisView.getUzer().getFakturagrupa()!=null&&!wpisView.getUzer().getFakturagrupa().isEmpty()) {
                ostatnidokument = fakturaDAO.findOstatniaFakturaByRokPodatnikGrupa(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt(), wpisView.getUzer().getFakturagrupa());
            }
            if (ostatnidokument == null) {
                String numer = FakturaBean.uzyjwzorcagenerujpierwszynumerFaktura(schematnumeracji, wpisView);
                selected.setNumerkolejny(numer);
                selected.setLp(1);
                Msg.msg("i", "Generuje nową serie numerów faktury");
            } else {
                String numer = FakturaBean.uzyjwzorcagenerujnumerFaktura(schematnumeracji, wpisView, fakturaDAO, ostatnidokument);
                selected.setNumerkolejny(numer);
                selected.setLp(ostatnidokument.getLp() + 1);
                Msg.msg("i", "Generuje kolejny numer faktury");
            }
            PrimeFaces.current().ajax().update("akordeon:formstworz:nrfaktury");
            PrimeFaces.current().executeScript("przeskoczdoceny();");
        } else {
            if (selected.getKontrahent()!=null&&istniejafakturykontrahenta == 0) {
                String nazwaddo = selected.getKontrahent().getNskrocona()!=null?selected.getKontrahent().getNskrocona().replace("\"", ""):selected.getKontrahent().getNpelna().replace("\"", "");
                int dlugoscnazwy = nazwaddo.length();
                String nazwadofaktury = dlugoscnazwy > 4 ? nazwaddo.substring(0, 4) : nazwaddo;
                String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                numer = sprawdzserie(nazwadofaktury, numer, fakturaDAO, wpisView, selected.getKontrahent().getNskrocona());
                selected.setNumerkolejny(numer);
                Msg.msg("i", "Generuje nową serie numerów faktury");
            } else if (selected.getKontrahent()!=null){
                String ostatniafaktura = wykazfaktur.get(wykazfaktur.size() - 1).getNumerkolejny();
                String separator = "/";
                String[] elementy = ostatniafaktura.split(separator);
                String nowypoczateknumeru = elementy[0];
                String nowynumer = null;
                do {
                    nowypoczateknumeru = zwiekszNumer(nowypoczateknumeru);
                    String nazwaddo = selected.getKontrahent().getNskrocona()!=null?selected.getKontrahent().getNskrocona().replace("\"", "").replaceAll("\\s+","") :selected.getKontrahent().getNpelna().replace("\"", "").replaceAll("\\s+","");
                    int dlugoscnazwy = nazwaddo.length();
                    String nazwadofaktury = dlugoscnazwy > 4 ? nazwaddo.substring(0, 4) : nazwaddo;
                    String numer = nowypoczateknumeru+"/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                    String nyp = selected.getKontrahent().getNip();
                    String nip = nyp!=null?nyp.substring(nyp.length()-3, nyp.length()-1):"";
                    nowynumer = numer+nip;
                    selected.setNumerkolejny(nowynumer);
                } while (czynumerjestnaliscie(nowynumer, numerypobranych));
                Msg.msg("i", "Generuje kolejny numer faktury");
            }
            PrimeFaces.current().ajax().update("akordeon:formstworz:nrfaktury");
            PrimeFaces.current().executeScript("przeskoczdoceny();");
        }
    }

    private static String zwiekszNumer(String stnumer) {
        int starynumer = Integer.parseInt(stnumer);
        starynumer++;
        return String.valueOf(starynumer);
    }

    //do sprawdzenia
    private static String generujNumer(String poczatek, String[] elementy, String nip) {
        String nowynumer = poczatek;
        for (int i = 1; i < elementy.length; i++) {
            nowynumer += "/" + elementy[i];
            //nowynumer += "/" + elementy[i]+nip;
        }
        return nowynumer;
    }

    private static boolean czynumerjestnaliscie(String nowynumer, List<String> numerypobranych) {
        return numerypobranych.contains(nowynumer);
    }

    private static String sprawdzserie(String nf, String numer, FakturaDAO fakturaDAO, WpisView wpisView, String nazwaskrocona) {
        String nowynumer = numer;
        List<Faktura> fakturyutworzone = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        boolean oblicznowa = false;
        for (Faktura p : fakturyutworzone) {
            if (p.getNumerkolejny().toLowerCase().equals(numer.toLowerCase())) {
                oblicznowa = true;
                break;
            }
        }
        if (oblicznowa == true) {
            int dlugoscnazwy = nazwaskrocona.length();
            String nazwadofaktury = dlugoscnazwy > nf.length()+1 ? nazwaskrocona.substring(0, nf.length()+1).trim() : nazwaskrocona.trim();
            if (nazwadofaktury.equals(nf)) {
                nazwadofaktury = nazwadofaktury + "A";
            }
            nowynumer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
            nowynumer = sprawdzserie(nazwadofaktury, nowynumer, fakturaDAO, wpisView, nazwaskrocona);
        }
        return nowynumer;
    }
}
