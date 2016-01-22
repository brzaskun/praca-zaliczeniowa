/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFaktura;

import dao.FakturaDAO;
import entity.Faktura;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class FakturaOkresowaGenNum {

    public static void wygenerujnumerfaktury(FakturaDAO fakturaDAO, Faktura selected, WpisView wpisView) {
        List<String> numerypobranych = new ArrayList<>();
        List<Faktura> wykazfaktur = fakturaDAO.findbyKontrahentNipRok(selected.getKontrahent().getNip(), wpisView.getPodatnikWpisu(), String.valueOf(wpisView.getRokWpisu()));
        int istniejafakturykontrahenta = 0;
        try {
            if (wykazfaktur.size() > 0) {
                for (Faktura p : wykazfaktur) {
                    numerypobranych.add(p.getFakturaPK().getNumerkolejny());
                }
                istniejafakturykontrahenta = 1;
            }
        } catch (Exception er) {
        }
        Integer istniejafakturyrok = fakturaDAO.liczFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu()).intValue();
        if (wpisView.getPodatnikObiekt().getSchematnumeracji() != null && !wpisView.getPodatnikObiekt().getSchematnumeracji().equals("")) {
            if (istniejafakturyrok == 0) {
                String numer = FakturaBean.uzyjwzorcagenerujpierwszynumerFaktura(wpisView.getPodatnikObiekt().getSchematnumeracji(), wpisView);
                selected.getFakturaPK().setNumerkolejny(numer);
                selected.setLp(1);
                Msg.msg("i", "Generuje nową serie numerów faktury");
            } else {
                String numer = FakturaBean.uzyjwzorcagenerujnumerFaktura(wpisView.getPodatnikObiekt().getSchematnumeracji(), wpisView, fakturaDAO);
                selected.getFakturaPK().setNumerkolejny(numer);
                Faktura ostatnidokument = fakturaDAO.findOstatniaFakturaByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
                selected.setLp(ostatnidokument.getLp() + 1);
                Msg.msg("i", "Generuje kolejny numer faktury");
            }
            RequestContext.getCurrentInstance().update("akordeon:formstworz:nrfaktury");
            RequestContext.getCurrentInstance().execute("przeskoczdoceny();");
        } else {
            if (istniejafakturykontrahenta == 0) {
                int dlugoscnazwy = selected.getKontrahent().getNskrocona().length();
                String nazwadofaktury = dlugoscnazwy > 4 ? selected.getKontrahent().getNskrocona().substring(0, 4) : selected.getKontrahent().getNskrocona();
                String numer = "1/" + wpisView.getRokWpisu().toString() + "/" + nazwadofaktury;
                numer = sprawdzserie(nazwadofaktury, numer, fakturaDAO, wpisView, selected.getKontrahent().getNskrocona());
                selected.getFakturaPK().setNumerkolejny(numer);
                Msg.msg("i", "Generuje nową serie numerów faktury");
            } else {
                String ostatniafaktura = wykazfaktur.get(wykazfaktur.size() - 1).getFakturaPK().getNumerkolejny();
                String separator = "/";
                String[] elementy = ostatniafaktura.split(separator);
                String nowypoczateknumeru = elementy[0];
                String nowynumer = null;
                do {
                    nowypoczateknumeru = zwiekszNumer(nowypoczateknumeru);
                    nowynumer = generujNumer(nowypoczateknumeru, elementy);
                    selected.getFakturaPK().setNumerkolejny(nowynumer);
                } while (czynumerjestnaliscie(nowynumer, numerypobranych));
                Msg.msg("i", "Generuje kolejny numer faktury");
            }
            RequestContext.getCurrentInstance().update("akordeon:formstworz:nrfaktury");
            RequestContext.getCurrentInstance().execute("przeskoczdoceny();");
        }
    }

    private static String zwiekszNumer(String stnumer) {
        int starynumer = Integer.parseInt(stnumer);
        starynumer++;
        return String.valueOf(starynumer);
    }

    private static String generujNumer(String poczatek, String[] elementy) {
        String nowynumer = poczatek;
        for (int i = 1; i < elementy.length; i++) {
            nowynumer += "/" + elementy[i];
        }
        return nowynumer;
    }

    private static boolean czynumerjestnaliscie(String nowynumer, List<String> numerypobranych) {
        return numerypobranych.contains(nowynumer);
    }

    private static String sprawdzserie(String nf, String numer, FakturaDAO fakturaDAO, WpisView wpisView, String nazwaskrocona) {
        String nowynumer = numer;
        List<Faktura> fakturyutworzone = fakturaDAO.findFakturyByRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
        boolean oblicznowa = false;
        for (Faktura p : fakturyutworzone) {
            if (p.getFakturaPK().getNumerkolejny().equals(numer)) {
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
