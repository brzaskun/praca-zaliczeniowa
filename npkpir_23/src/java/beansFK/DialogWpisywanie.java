/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import error.E;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import msg.Msg;
import viewfk.subroutines.ObslugaWiersza;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class DialogWpisywanie {

    public static int dodajPustyWierszNaKoncu(Dokfk selected) {
        int indexwTabeli = selected.getListawierszy().size() - 1;
        int wynik = dolaczNowyWierszPusty(selected, indexwTabeli, false);
        return wynik;
    }
    
    public static void rozliczkolejnesaldo(Dokfk selected, int indexwTabeli) {
    if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
        Konto kontorozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        Wiersz wierszbiezacy = selected.getListawierszy().get(indexwTabeli);
        double sumapoprzednich = 0.0;
        if (indexwTabeli==0) {
            sumapoprzednich = wierszbiezacy.getSaldoWBRK();
        } else {
            sumapoprzednich = selected.getListawierszy().get(indexwTabeli-1).getSaldoWBRK();
        }
        double kwotawiersza = obliczsaldo(wierszbiezacy, kontorozrachunkowe);
        wierszbiezacy.setSaldoWBRK(sumapoprzednich + kwotawiersza);
    }
}

    public static void rozliczsalda(Dokfk selected, double saldoBO, double saldoinnedok, Konto konto) {
        if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
            double sumapoprzednich = 0;
            for (Wiersz p : selected.getListawierszy()) {
                double kwota = obliczsaldo(p, konto);
                sumapoprzednich += kwota;
                p.setSaldoWBRK(saldoBO + saldoinnedok + sumapoprzednich);
            }
        }
    }
    
    public static int dolaczNowyWierszPusty(Dokfk selected, int wierszbiezacyIndex, boolean przenumeruj) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(wierszbiezacyIndex);
        try {
            boolean czyWszystkoWprowadzono = DokFKBean.sprawdzczyWnRownasieMa(wierszbiezacy);
            int nrgrupy = wierszbiezacy.getLpmacierzystego() == 0 ? wierszbiezacy.getIdporzadkowy() : wierszbiezacy.getLpmacierzystego();
            double roznica = 0.0;
            if (!wierszbiezacy.getDokfk().getDokfkPK().getSeriadokfk().equals("BO")) {
                    roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy, nrgrupy);
            }
            if (roznica == 0 && czyWszystkoWprowadzono == true) {
                 ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 0);
             } else if (roznica != 0.0 && czyWszystkoWprowadzono == true) {
                 ObslugaWiersza.wygenerujWierszRoznicowy(wierszbiezacy, false, nrgrupy, selected);
             }
            return 0;
        } catch (Exception e) {  
            E.e(e);
            return 1;
        }
    }
    
      private static double obliczsaldo(Wiersz w, Konto kontorozrachunkowe) {
        double kwota = 0;
        List<StronaWiersza> strony = w.getStronyWierszaKonto();
        for (StronaWiersza r : strony) {
            if (r.getKonto().equals(kontorozrachunkowe)) {
                if (r.getWnma().equals("Wn")) {
                    kwota += r.getKwota();
                } else {
                    kwota -= r.getKwota();
                }
            }
        }
        return kwota;
    }
}
